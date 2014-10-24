/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tgg.interpreter.gui;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EContentsEList;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformationImpl;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.TggUtil;

//
//...
//class FamilyJob extends Job {
//   ...
//}

public class TranslationJob extends Job {

	protected static final String targetExt = "java";

	protected TggTransformationImpl tggTransformation;
	protected TggEngineImpl emfEngine;

	protected URI inputURI;
	protected URI xmiURI;
	protected URI outputURI;
	protected TGG module= null;
	protected String trFileName;
	protected List<EObject> inputEObjects=null;
	
	protected Map<String, ExecutionTimes> executionTimesMap = null; 

	
	public TranslationJob(IFile inputFile, boolean useOutputFolder) {
		super("Translating " + inputFile.getName());
		this.inputURI = URI.createPlatformResourceURI(inputFile.
				getFullPath().toString(), true);
		this.xmiURI = this.inputURI.trimFileExtension().
				appendFileExtension("xmi");
		this.outputURI = this.inputURI.trimFileExtension().
				appendFileExtension(targetExt);
		if(useOutputFolder){
			this.outputURI = outputURI.trimSegments(1).appendSegment("output").appendSegment(outputURI.lastSegment());
		}
	}
	
	protected IStatus run(IProgressMonitor monitor) {
		// check that grammar is loaded
		if (LoadHandler.trSystems.size()==0){
			return 
					new Status(RUNNING, "tgg-plugin", "Transformation System was not loaded");
		}
			
		
		// clear list of rules from previous executions
		TggUtil.initClassConversions();
		ExecutionTimes executionTimes = new ExecutionTimes();
		try {
			monitor.beginTask("Translating " + inputURI.lastSegment(), 3);
			System.out.println("=====");
			System.out.println("Translating: " + inputURI.lastSegment());
			long time0 = System.currentTimeMillis();
			monitor.subTask("Loading input");
			ResourceSet resSet = new ResourceSetImpl();
			HashMap<String,Object> options = new HashMap<String,Object>();
			options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
			options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
			options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
			options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
			options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
			options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

			resSet.getLoadOptions().putAll(options);
			Resource res = resSet.getResource(inputURI, true);

			// Print out syntax parsing errors 
			// and abort translation if errors occur
			EList<Diagnostic> errors = res.getErrors();
			if (!errors.isEmpty()) {
				String msg = "===========================\n";
				msg += "Translation failed. No output was generated. The following syntax errors occured while parsing:\n";
				for (Diagnostic d : errors) {
					msg += "(" + inputURI.lastSegment() + ") line " + d.getLine() + ": " + d.getMessage() + "\n";
					msg += "-------------------------------\n";
				}
				msg += "===========================\n";
				throw new RuntimeException(msg);
			}
			
			EObject inputRoot = (EObject) res.getContents().get(0);
			inputEObjects = res.getContents(); // add all of root
			tggTransformation = new TggTransformationImpl();

			tggTransformation.setInput(inputEObjects);
			tggTransformation.opRulesList.clear();

			// Validate input AST based on custom constraints 
			org.eclipse.emf.common.util.Diagnostic validation_result = Diagnostician.INSTANCE.validate(inputRoot);
			if (!validation_result.getChildren().isEmpty()) {
				String msg = "===========================\n";
				msg += "Translation failed. No output was generated. The following syntax errors occured while parsing:\n";
				for (org.eclipse.emf.common.util.Diagnostic d : validation_result.getChildren()) {
					msg += "(" + inputURI.lastSegment() + ") " + d.getMessage() + " (" + d.getSource() + ")\n";
					msg += "-------------------------------\n";
				}
				msg += "===========================\n";
				throw new RuntimeException(msg);
			}
			
			if (emfEngine != null) {
				emfEngine.clearCache();
			}
			emfEngine = tggTransformation.getEmfEngine();
			long time1 = System.currentTimeMillis();
			long stage1 = time1 - time0;
			System.out.println("Stage 1 -- Loading: " + stage1 + " ms");
			monitor.worked(1);
			if (monitor.isCanceled()) {
				monitor.done();
				return Status.CANCEL_STATUS;
			}

			Iterator<TGG> moduleIt = LoadHandler.trSystems.iterator();
			Iterator<String> fileNames = LoadHandler.trFileNames.iterator();

			while (moduleIt.hasNext() && fileNames.hasNext()) {
				module = moduleIt.next();
				trFileName = fileNames.next();
				addFTRules(module);
				tggTransformation.setNullValueMatching(module.isNullValueMatching());

				monitor.subTask("Applying " + trFileName);

				tggTransformation.applyRules(monitor,"Applying " + trFileName);
				monitor.worked(1);
				if (monitor.isCanceled()) {
					monitor.done();
					return Status.CANCEL_STATUS;
				}
			}

			long time2 = System.currentTimeMillis();
			long stage2 = time2 - time1;
			System.out.println("Stage 2 -- Transformation: " + stage2 + " ms");
			monitor.subTask("Saving result");
			List<EObject> roots = tggTransformation.getGraph().getRoots();

			Iterator<EObject> it = roots.iterator();
			EObject targetRoot = null;
			EObject current = null;
			//TGG tgg = LoadHandler.layoutModels.get(0);
			boolean targetRootFound=false;
			while (it.hasNext() && !targetRootFound) {
				current = it.next();
				if (NodeUtil.isTargetClass(module, current.eClass())) {
					targetRoot = current;
					targetRootFound = true;
				}
			}

			if (targetRoot != null) {

				// remove all backreferences
				TreeIterator<EObject> nodesIt = targetRoot.eAllContents();
				EObject targetObject=targetRoot;
				//AbstractTarget tNode;
				
				removeT2C(targetObject);
				
				while(nodesIt.hasNext()){
					targetObject=nodesIt.next();
					removeT2C(targetObject);
				}
				//Export.saveModel(resSet, roots, xmiURI);
				Export.saveTargetModel(resSet, targetRoot, outputURI);
			} else {
				System.out.println("No target root!");
			}
			monitor.worked(1);

			Import.unloadModel(resSet,  outputURI);
			resSet.getResource(inputURI, true).unload();

			try {
				if (outputURI.isPlatformResource()) {
					String platformString = outputURI.toPlatformString(true);
					IFile file = (IFile) ResourcesPlugin.getWorkspace().getRoot().
							findMember(platformString);
					Path path = Paths.get(file.getLocation().toString());
					Charset charset = StandardCharsets.UTF_8;
					String content = new String(Files.readAllBytes(path), charset);
					CodeFormatter cf = new DefaultCodeFormatter();
					TextEdit te = cf.format(CodeFormatter.K_UNKNOWN, content, 0, content.length(), 0, null);
					IDocument dc = new Document(content);
					te.apply(dc);
					Files.write(path, dc.get().getBytes(charset));
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
			long time3 = System.currentTimeMillis();
			long stage3 = time3 - time2;
			System.out.println("Stage 3 -- Saving: " + stage3 + " ms");
			executionTimes.stage1=stage1;
			executionTimes.stage2=stage2;
			executionTimes.stage3=stage3;
			executionTimes.overall=stage1+stage2+stage3;
		} finally {
			monitor.done();
		}
		// put the execution time for this file in the global list
		synchronized(this) {executionTimesMap.put(inputURI.path(), executionTimes);}
		for (Module	m : LoadHandler.trSystems) {
			cleanGrammar(m);
		}
		return Status.OK_STATUS;
	}

	
	protected void removeT2C(EObject targetObject) {
		EContentsEList.FeatureIterator featureIterator = (EContentsEList.FeatureIterator) targetObject
				.eCrossReferences().iterator();
		EReference eReference = null;
		EReference t2cEReference = null;
		while (featureIterator.hasNext()) {
			featureIterator.next();
			if (featureIterator.feature() instanceof EReference) {
				eReference = (EReference) featureIterator.feature();
				if (eReference.getName().endsWith("2c"))
					t2cEReference = eReference;
			}
		}
		if (t2cEReference != null)
			targetObject.eUnset(t2cEReference);
	}

	public static void cleanGrammar(Module m){
		TreeIterator<EObject> treeIterator = m.eAllContents();
		while (treeIterator.hasNext()){
			EObject eObject = treeIterator.next();
			if (!eObject.eAdapters().isEmpty()) {
				for (Iterator<Adapter> itr = eObject.eAdapters().iterator();itr.hasNext();){
					Adapter next = itr.next();
					try {
						Field field = next.getClass().getDeclaredField("this$0");
						field.setAccessible(true);
						Object object = field.get(next);
						if (object instanceof Engine){
							itr.remove();
						}
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void setTimesMap(Map<String, ExecutionTimes> executionTimesMap2) {
		this.executionTimesMap=executionTimesMap2;
		
	}
	
	public boolean belongsTo(Object family) {
		return family == TransHandler.TRANSLATION_JOB_FAMILY;
	}

	
	protected void getAllRules(List<Rule> units,IndependentUnit folder){
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				getAllRules(units, (IndependentUnit) unit);
			} else {
				units.add((Rule) unit);
			}
			
		}
	}
	
	public void addFTRules(Module module) {

		if (module == null)
			return;
		String name_OP_RULE_FOLDER = "FTRuleFolder";
		IndependentUnit opRuleFolder = (IndependentUnit) module.getUnit(name_OP_RULE_FOLDER);
		List<Rule> opRules = new Vector<Rule>();
		getAllRules(opRules, opRuleFolder);
		tggTransformation.setOpRuleList(opRules);
		

	}
	

}

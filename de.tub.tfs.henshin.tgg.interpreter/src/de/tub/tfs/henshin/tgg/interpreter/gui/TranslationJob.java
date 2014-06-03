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

import lu.uni.snt.secan.ttc_java.tTC_Java.Model;

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
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.interpreter.TGGEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.TggUtil;
import de.tub.tfs.henshin.tgg.interpreter.Transformation;

public class TranslationJob extends Job {

	private static final String targetExt = "java";

	private TGGEngineImpl emfEngine;

	

	
	private URI inputURI;
	private URI xmiURI;
	private URI outputURI;
	private Module module= null;
	private String trFileName;
	private EObject inputRoot=null;

	public TranslationJob(IFile inputFile) {
		super("Translating " + inputFile.getName());
		this.inputURI = URI.createPlatformResourceURI(inputFile.
				getFullPath().toString(), true);
		this.xmiURI = this.inputURI.trimFileExtension().
				appendFileExtension("xmi");
		this.outputURI = this.inputURI.trimFileExtension().
				appendFileExtension(targetExt);
	}
	
	
	protected IStatus run(IProgressMonitor monitor) {
		// clear list of rules from previous executions
		TggUtil.initClassConversions();
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
			
			inputRoot = (EObject)
					res.
					getContents().get(0);
			Transformation trans = new Transformation(inputRoot);
			trans.fTRuleList.clear();

			// Validate FIXML AST based on custom constraints we defined in TTC_XMLValidator
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
			emfEngine = trans.getEmfEngine();
			long time1 = System.currentTimeMillis();
			long stage1 = time1 - time0;
			System.out.println("Stage 1 -- Loading: " + stage1 + " ms");
			monitor.worked(1);
			if (monitor.isCanceled()) {
				monitor.done();
				return Status.CANCEL_STATUS;
			}

			Iterator<Module> moduleIt = LoadHandler.trSystems.iterator();
			Iterator<TGG> layoutIt = LoadHandler.layoutModels.iterator();
			Iterator<String> fileNames = LoadHandler.trFileNames.iterator();

			while (moduleIt.hasNext() && layoutIt.hasNext() && fileNames.hasNext()) {
				module = moduleIt.next();
				trFileName = fileNames.next();
				trans.addFTRules(module);

				monitor.subTask("Applying " + trFileName);

				trans.applyRules(monitor,"Applying " + trFileName);
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
			List<EObject> roots = trans.getGraph().getRoots();

			Iterator<EObject> it = roots.iterator();
			EObject targetRoot = null;
			EObject current = null;
			while (it.hasNext()) {
				current = it.next();
				if (current instanceof Model)
					targetRoot = current;
			}

			if (targetRoot != null) {
				Export.saveModel(resSet, roots, xmiURI);
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
				e.printStackTrace();
			}
			long time3 = System.currentTimeMillis();
			long stage3 = time3 - time2;
			System.out.println("Stage 3 -- Saving: " + stage3 + " ms");
		} finally {
			monitor.done();
		}
		for (Module	m : LoadHandler.trSystems) {
			cleanGrammar(m);
		}
		return Status.OK_STATUS;
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

	


	


	

	
}

package de.tub.tfs.henshin.tgg.interpreter.gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

import lu.uni.snt.secan.ttc_java.tTC_Java.Model;
import lu.uni.snt.secan.ttc_xml.tTC_XML.XMLNode;
import lu.uni.snt.secan.ttc_xml.validation.TTC_XMLValidator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.text.edits.TextEdit;
import org.eclipse.ui.handlers.HandlerUtil;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.commands.OpRuleAttributeConstraintEMF;
import de.tub.tfs.henshin.tggeditor.commands.OpRuleEdgeConstraintEMF;
import de.tub.tfs.henshin.tggeditor.commands.OpRuleNodeConstraintEMF;
import de.tub.tfs.henshin.tggeditor.commands.TGGEngineImpl;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;

public class TransHandler extends AbstractHandler implements IHandler {
	private static final String sourceExt = "xml";
	private static final String targetExt = "java";

	private TGGEngineImpl emfEngine;
	private EGraph graph;
	private List<Rule> fTRuleList = new Vector<Rule>();

	private HashMap<EObject, Boolean> isTranslatedNodeMap= new HashMap<EObject, Boolean>();
	private HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedAttributeMap= new HashMap<EObject, HashMap<EAttribute,Boolean>>();
	private HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap = new HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>>();

	synchronized static boolean saveTargetModel(ResourceSet resSet,
			EObject root, URI uri) {
		// has to be synchronised since XText serialisation is not thread-safe
		Resource res = resSet.createResource(uri);
		res.getContents().add(root);
		try {
			res.save(null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;			
		}
		InputStream inputStream;
		try {
			URL url = new URL(uri.toString());
			URLConnection connection = url.openConnection();
			inputStream = connection.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputString = in.readLine();
			String fileName = FileLocator.toFileURL(url).toString();
			File f  = new File( fileName.substring(5) );
			String outStr = "";
			do {
				if (inputString != null && !inputString.trim().isEmpty()){
					outStr += inputString + "\r\n";
				}
				inputString = in.readLine();
			} while (inputString != null);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			out.write(outStr);
			in.close();
			out.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	synchronized static void saveModel(ResourceSet resSet,
			List<EObject> roots, URI uri) {
		// has to be synchronised since XText serialisation is not thread-safe
		Resource res = resSet.createResource(uri);
		for(EObject root:roots){
			res.getContents().add(root);
		}
		try {
			res.save(null);	
		} catch (IOException e) {
			try {
				res.unload();
			} catch (Exception e2){
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	synchronized static void unloadModel(ResourceSet resSet, URI uri) {
		// has to be synchronised since XText serialisation is not thread-safe
		Resource res = resSet.getResource(uri, false);
		try {
			if (res != null)
				res.unload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	synchronized static void saveModel(ResourceSet resSet,
			List<EObject> roots, URI uri, EClass eClass) {
		// has to be synchronised since XText serialisation is not thread-safe
		Resource res = resSet.createResource(uri);
		for(EObject root:roots) {
			if (eClass.isInstance(root))
				res.getContents().add(root);
		}
		try {
			res.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private class TranslationJob extends Job {
		private URI inputURI;
		private URI outputURI;
		private Module module= null;
		private String trFileName;

		public TranslationJob(IFile inputFile) {
			super("Translating " + inputFile.getName());
			this.inputURI = URI.createPlatformResourceURI(inputFile.
					getFullPath().toString(), true);
			this.outputURI = this.inputURI.trimFileExtension().
					appendFileExtension(targetExt);
		}

		protected IStatus run(IProgressMonitor monitor) {
			// clear list of rules from previous executions
			fTRuleList.clear();
			isTranslatedNodeMap .clear();
			isTranslatedAttributeMap.clear();
			isTranslatedEdgeMap.clear();
			TreeEditor.initClassConversions();
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
				
				EObject inputRoot = (EObject)
						res.
						getContents().get(0);

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
				
				TripleGraph g = TggFactory.eINSTANCE.createTripleGraph();
				if (graph != null)
					graph.clear();
				graph = new TggHenshinEGraph(g); 
				graph.addGraph(inputRoot);

				// initially fill isTranslatedNodeMap with all source nodes to be not yet translated 
				// and put them also in the isTranslatedAttributeMap as keys
				TreeIterator<EObject> graphNodesIterator = inputRoot.eAllContents();
				EObject currentEObject;
				isTranslatedNodeMap.put(inputRoot, false);
				isTranslatedAttributeMap.put(inputRoot, new HashMap<EAttribute,Boolean>());
				isTranslatedEdgeMap.put(inputRoot, new HashMap<EReference,HashMap<EObject,Boolean>>());

				while(graphNodesIterator.hasNext()){
					currentEObject= graphNodesIterator.next();
					isTranslatedNodeMap.put(currentEObject, false);
					isTranslatedAttributeMap.put(currentEObject, new HashMap<EAttribute,Boolean>());
					isTranslatedEdgeMap.put(currentEObject, new HashMap<EReference,HashMap<EObject,Boolean>>());
				}

				final Set<EObject> sourceNode =new HashSet<EObject>(isTranslatedNodeMap.keySet());
				if (emfEngine != null) {
					emfEngine.clearCache();
				}
				emfEngine = new TGGEngineImpl(graph) {	
					@Override
					public UnaryConstraint createUserConstraints(Attribute attribute) {
						return new OpRuleAttributeConstraintEMF(attribute, sourceNode,
								isTranslatedNodeMap, isTranslatedAttributeMap);
					}

					@Override
					public BinaryConstraint createUserConstraints(Edge edge) {
						return new OpRuleEdgeConstraintEMF(edge, sourceNode,
								isTranslatedEdgeMap);
					}

					@Override
					public UnaryConstraint createUserConstraints(Node node) {
						return new OpRuleNodeConstraintEMF(node, sourceNode,
								isTranslatedNodeMap);
					}
				};

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
					retrieveFTRules();

					monitor.subTask("Applying " + trFileName);

					applyRules(monitor,"Applying " + trFileName);
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
				List<EObject> roots = graph.getRoots();

				Iterator<EObject> it = roots.iterator();
				EObject targetRoot = null;
				EObject current = null;
				while (it.hasNext()) {
					current = it.next();
					if (current instanceof Model)
						targetRoot = current;
				}

				if (targetRoot != null) {
					saveTargetModel(resSet, targetRoot, outputURI);
				} else {
					System.out.println("No target root!");
				}
				monitor.worked(1);

				unloadModel(resSet,  outputURI);
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

		protected void retrieveFTRules() {
			TGGRule rule = null;

			if (module == null)
				return;
			EList<Unit> units = module.getUnits();
			for (Unit u : units) {
				if (!(u instanceof TGGRule))
					continue;
				rule = (TGGRule) u;
				if (rule.getMarkerType() == null) {
					System.out.println("### ERROR: marker of Rule "
							+ rule.getName() + " in grammar " + module.getName() + " is missing ###");
					continue;
				}
				if (rule.getMarkerType().equals(RuleUtil.TGG_FT_RULE)) {
					fTRuleList.add((Rule) u);
				}
			}
		}

	}

	@SuppressWarnings("unchecked")
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Get currently active shell:
		Shell shell = HandlerUtil.getActiveWorkbenchWindow(event).getShell();
		// Load the transformation units during first run:
		if (LoadHandler.trSystems == null) {
			MessageDialog.openError(shell, "No Translator loaded",
					"Please load the translator.");
		}
		// Find files to translate:
		Queue<IFile> transQueue = new LinkedList<IFile>();
		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel != null && sel instanceof IStructuredSelection) {
			IStructuredSelection structSel = (IStructuredSelection) sel;
			for (Iterator<Object> it = structSel.iterator(); it.hasNext();) {
				Object obj = it.next();
				if (obj instanceof IFile) {
					IFile file = (IFile) obj;
					transQueue.add(file);
				}
				if (obj instanceof IContainer) {
					IResource[] resArr;
					try {
						resArr = ((IContainer) obj).members();
						for (int i=0; i<resArr.length; i++) {
							if (resArr[i] instanceof IFile) {
								IFile file = (IFile) resArr[i];
								if (file.getFileExtension().equals(sourceExt)) {
									transQueue.add(file);
								}
							}
						}
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// Start jobs for all input files:
		for (IFile inputFile: transQueue) {
			TranslationJob job = new TranslationJob(inputFile);
			job.setRule(new TransSchedulingRule());
			job.schedule();
		}
		return null;
	}

	private void applyRules(IProgressMonitor monitor, String msg) {
		// check if any rule can be applied
		long startTimeOneStep=System.nanoTime();
		long endTimeOneStep=System.nanoTime();
		long duration=0;
		RuleApplicationImpl ruleApplication = null;
		boolean foundApplication = true;
		while (foundApplication) {
			foundApplication = false;
			// apply all rules on graph
			Rule currentRule = null;
			try {
				for (Rule rule : fTRuleList) {
					startTimeOneStep=System.nanoTime();
					monitor.subTask(msg + " (" + rule.getName() + ")");
					currentRule=rule;
					ruleApplication = new RuleApplicationImpl(emfEngine);
					ruleApplication.setEGraph(graph);
					ruleApplication.setRule(rule);
					/*
					 * Apply a rule as long as it's possible and add each successful
					 * application to ruleApplicationlist. Then fill the
					 * isTranslatedTable Start with a fresh match.
					 */
					Boolean matchesToCheck = true;
					while (matchesToCheck) {
						Iterator<Match> matchesIterator = emfEngine
								.findMatches(rule, graph, new MatchImpl(rule))
								.iterator();
						if (!matchesIterator.hasNext()) {
							matchesToCheck = false;
						}
						while (matchesIterator.hasNext()) {
							ruleApplication.setPartialMatch(matchesIterator
									.next());
							try {
								foundApplication = executeOneStep(ruleApplication,
										foundApplication, rule);
								if (foundApplication) System.out.println("Executed: "+ rule.getName());
							} catch (RuntimeException e){
								matchesToCheck = false;
							}
						}
					}
					endTimeOneStep=System.nanoTime();
					duration=(endTimeOneStep-startTimeOneStep)/(1000000);
					if(duration>10)
						System.out.println("Rule " + rule.getName() + ":" + duration + "ms");
//					startTimeOneStep=System.nanoTime();
				}

			} catch (RuntimeException e) {
				System.out.println("Rule "
						+ currentRule.getName()
						+ " caused a runtime exception. Check input parameter settings: "
						+ e.getMessage());
			}
		}
	}

	private boolean executeOneStep(RuleApplicationImpl ruleApplication,
			boolean foundApplication, Rule rule) {
		if (ruleApplication.execute(null)) {
			foundApplication = true;
			// fill isTranslatedNodeMap
			List<Node> rhsNodes = rule.getRhs().getNodes();
			Match resultMatch = ruleApplication.getResultMatch();
			for (Node n: rhsNodes) {
				TNode ruleNodeRHS = (TNode) n;
				EObject nodeEObject = resultMatch.getNodeTarget(ruleNodeRHS);
				if (ruleNodeRHS.getMarkerType() != null
						&& ruleNodeRHS.getMarkerType().equals(
								RuleUtil.Translated)) {
					isTranslatedNodeMap.put(nodeEObject, true);
					isTranslatedEdgeMap
					.put(nodeEObject,
							new HashMap<EReference, HashMap<EObject, Boolean>>());
					isTranslatedAttributeMap.put(nodeEObject,
							new HashMap<EAttribute, Boolean>());
					fillTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					fillTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				} else {
					// context node, thus check whether the edges
					// and attributes are translated
					fillTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					fillTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				}
			}
			emfEngine.postProcess(resultMatch);
		} else {
			throw new RuntimeException("Match NOT applicable!");
		}
		return foundApplication;
	}

	private void fillTranslatedAttributeMap(Node ruleNodeRHS, EObject graphNodeEObject) {
		// fill isTranslatedAttributeMap
		// scan the contained attributes for <tr>
		for (Attribute ruleAttribute : ruleNodeRHS.getAttributes()) {
			String isMarked=((TAttribute) ruleAttribute).getMarkerType();
			if (isMarked!=null && isMarked.equals(RuleUtil.Translated)) {
				//mark this attribute to be translated
				HashMap<EAttribute,Boolean> attributeMap = isTranslatedAttributeMap.get(graphNodeEObject);
				if (attributeMap==null) {
					waitForEnter("Translated attribute map is missing node entry.");
					return;
				}
				isTranslatedAttributeMap.get(graphNodeEObject).put(ruleAttribute.getType(), true);
			}
		}			
	}

	private void fillTranslatedEdgeMap(Node ruleNode, EObject sourceNodeEObject, Match resultMatch) {
		// fill isTranslatedEdgeMap
		EObject targetNodeeObject;
		// scan the outgoing edges for <tr>
		for (Edge ruleEdge : ruleNode.getOutgoing()) {
			if (RuleUtil.Translated.equals( (((TEdge) ruleEdge).getMarkerType()))) {
				Node ruleTarget = ruleEdge.getTarget();
				targetNodeeObject = resultMatch.getNodeTarget(ruleTarget);
				// put edge in hashmap
				HashMap<EReference,HashMap<EObject,Boolean>> edgeMap = isTranslatedEdgeMap.get(sourceNodeEObject);
				if(edgeMap==null) {
					waitForEnter("Translated edge map is missing node entry.");
					return;
				}
				if(!edgeMap.containsKey(ruleEdge.getType()))
					edgeMap.put(ruleEdge.getType(),new HashMap<EObject,Boolean>());
				edgeMap.get(ruleEdge.getType()).put(targetNodeeObject, true);
			}
		}		
	}

	public static void waitForEnter(String message, Object... args) {
		Console c = System.console();
		if (c != null) {
			// printf-like arguments
			if (message != null)
				c.format(message, args);
			c.format("\nPress ENTER to proceed.\n");
			c.readLine();
		}
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
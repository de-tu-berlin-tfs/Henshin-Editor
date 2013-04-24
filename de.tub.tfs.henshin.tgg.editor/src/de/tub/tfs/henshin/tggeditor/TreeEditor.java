package de.tub.tfs.henshin.tggeditor;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.actions.GenericTGGGraphLayoutAction;
import de.tub.tfs.henshin.tggeditor.actions.RestrictGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.TGGGenericCopyAction;
import de.tub.tfs.henshin.tggeditor.actions.TGGGenericCutAction;
import de.tub.tfs.henshin.tggeditor.actions.TGGGenericPasteAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateNACAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateParameterAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleFolderAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.exports.ExportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportCorrAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelActionWithDefaultValues;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportSourceAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportTargetAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.LoadReconstructXMLForSource;
import de.tub.tfs.henshin.tggeditor.actions.imports.LoadXMLXSDmodel;
import de.tub.tfs.henshin.tggeditor.actions.validate.CheckRuleConflictAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidateAllRulesAction;
import de.tub.tfs.henshin.tggeditor.editparts.tree.HenshinTreeEditFactory;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.views.graphview.CriticalPairPage;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.actions.GenericCopyAction;
import de.tub.tfs.muvitor.actions.GenericCutAction;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;


public class TreeEditor extends MuvitorTreeEditor {
	
	public static final String GRAPH_VIEW_ID = "tggeditor.views.graphview.GraphicalView";
	public static final String RULE_VIEW_ID = "tggeditor.views.ruleview.RuleGraphicalView";
	public static final String CONDITION_VIEW_ID = "tggeditor.views.ruleview.NACGraphicalView";
	public static final String CRITICAL_PAIR_VIEW_ID = "tggeditor.views.graphview.CriticalPairView";
														
	
	static {
		HenshinFactory einstance = HenshinFactory.eINSTANCE;
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Node", TggPackage.Literals.TNODE);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Graph", TggPackage.Literals.TRIPLE_GRAPH);
		
		
		
		
		
		//ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put("xml", new GenericXMLResourceFactoryImpl());
		
	
	}
	

	
	
	private TGG layout;

	private final String layoutExtension = "tgg";
	
	private HashMap<Rule, RuleGraphicalPage> ruleToPage = new HashMap<Rule, RuleGraphicalPage>();
	private HashMap<CritPair, CriticalPairPage> critPairToPage = new HashMap<CritPair, CriticalPairPage>();

	private EMFModelManager layoutModelManager = new EMFModelManager(
			layoutExtension);

	private IPath layoutFilePath;
	


	/* (non-Javadoc)
	 * @see de.tub.tfs.muvitor.ui.MuvitorTreeEditor#registerViewIDs()
	 */
	@Override
	protected void registerViewIDs() {
		super.registerViewIDs();
//		registerViewID(HenshinPackage.Literals.GRAPH, GRAPH_VIEW_ID);
		registerViewID(TggPackage.Literals.TRIPLE_GRAPH, GRAPH_VIEW_ID);
		registerViewID(HenshinPackage.Literals.RULE, RULE_VIEW_ID);
		registerViewID(HenshinPackage.Literals.NESTED_CONDITION, CONDITION_VIEW_ID);
		registerViewID(TggPackage.Literals.CRIT_PAIR, CRITICAL_PAIR_VIEW_ID);		
	}

	
	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			TreeViewer viewer) {
		return new TreeContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
		registerAction(new CreateGraphAction(this));
		registerAction(new LoadReconstructXMLForSource(this));
		registerAction(new LoadXMLXSDmodel(this));
		//registerAction(new ImportEMFModelAction(this));
		registerAction(new ImportSourceAction(this));
		registerAction(new ImportTargetAction(this));
		registerAction(new ImportCorrAction(this));
		registerAction(new CreateAttributeAction(this));
		registerAction(new CreateRuleAction(this));
		registerAction(new CreateRuleFolderAction(this));
		registerAction(new CreateNACAction(this));
		registerAction(new GraphValidAction(this));
		registerAction(new RuleValidAction(this));
		registerAction(new CheckRuleConflictAction(this));
		registerAction(new CreateParameterAction(this));
		registerAction(new GenerateFTRuleAction(this));
		registerAction(new GenerateFTRulesAction(this));
		registerAction(new RuleValidateAllRulesAction(this));
		registerAction(new ExecuteFTRulesAction(this));
        registerAction(new TGGGenericCopyAction(this));
        registerAction(new GenericCutAction(this));
        registerAction(new TGGGenericPasteAction(this)); 
		registerAction(new ImportInstanceModelAction(this));  
		registerAction(new ImportInstanceModelActionWithDefaultValues(this));  
		registerAction(new ExportInstanceModelAction(this));
		registerActionOnToolBar(new GenericTGGGraphLayoutAction(this));
		registerActionOnToolBar(new RestrictGraphAction(this));

	}

	@Override
	protected EObject createDefaultModel() {
		HenshinFactory factory = HenshinFactory.eINSTANCE;
		Module transSys = factory.createModule();
		transSys.setName("Transformation System");
		TripleGraph graph = TggFactory.eINSTANCE.createTripleGraph();
		graph.setName("Graph1");
		transSys.getInstances().add(graph);
		return transSys;
	}

	@Override
	protected EditPartFactory createTreeEditPartFactory() {
		return new HenshinTreeEditFactory();
	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void setInput(IEditorInput input) {

		super.setInput(input);
		
		// open layout model
		final IFile file = ((IFileEditorInput) input).getFile();
		layoutFilePath = file.getFullPath().removeFileExtension().addFileExtension(layoutExtension);

		List<EObject> list=new ArrayList<EObject>();
		list = layoutModelManager.load(layoutFilePath, Arrays.asList((EObject)TggFactory.eINSTANCE.createTGG()));

		if (list == null || list.isEmpty() || !(list.get(0) instanceof TGG)){
					
		} else {
			layout = (TGG) list.get(0);
			
//			Module trafo = ModelUtil.getTransSystem(layout.getGraphlayouts().get(0));
//			if (layout.getSource()!=null)
//			if (!trafo.getImports().contains(layout.getSource())) layout.setSource(null);
//			if (layout.getTarget()!=null)
//			if (!trafo.getImports().contains(layout.getTarget())) layout.setTarget(null);
//			if (layout.getCorresp()!=null)
//			if (!trafo.getImports().contains(layout.getCorresp())) layout.setCorresp(null);

			repairTGGModel();
			
		}
		this.getModelRoots().add(layout);
	}
	
//	@Override
//	protected void setPerspectiveID() {
//		perspectiveID =
//		TGGEditorActivator.getUniqueExtensionAttributeValue(
//				"org.eclipse.ui.perspectives", "id");
//	}

	private void repairTGGModel() {
		
		
		Iterator<NodeLayout> nodeLayoutIter=layout.getNodelayouts().iterator();
		while(nodeLayoutIter.hasNext()){
			NodeLayout layout=nodeLayoutIter.next();
			if (layout.getNode()==null){
				nodeLayoutIter.remove();
				continue;
			}
			if (layout.getNode().getGraph()==null){
				nodeLayoutIter.remove();
				continue;
			}
			
			// migrate deprecated node layout information 
			NodeUtil.refreshLayout(layout.getNode(),layout);
			// TODO: migrate markers
			if (layout.getLhsTranslated()!=null) {
			}
			nodeLayoutIter.remove();

		}
		
		Iterator<EdgeLayout> edgeIter=layout.getEdgelayouts().iterator();
		while(edgeIter.hasNext()){
			EdgeLayout layout=edgeIter.next();
			if (layout.getRhsedge()==null){
				edgeIter.remove();
				continue;
			}
			if (layout.getRhsedge().getGraph()==null){
				edgeIter.remove();
				continue;
			}
		}
		
		Iterator<GraphLayout> graphIter=layout.getGraphlayouts().iterator();
		while(graphIter.hasNext()){
			GraphLayout layout=graphIter.next();
			if (layout.getGraph()==null){
				graphIter.remove();
				continue;
			}

			if (layout.getGraph().getName()==null){
				// name is missing, thus - graph is corrupted
				graphIter.remove();
				continue;
			}

			// graph is found, thus create a new triple graph for it
			Graph graph = layout.getGraph();
			migrateToTripleGraph(graph);
			// remove current graphlayout from list
			graphIter.remove();
		}
		
		Iterator<TRule> ruleIter=layout.getTRules().iterator();
		while(ruleIter.hasNext()){
			TRule layout=ruleIter.next();
			if (layout.getRule()==null){
				ruleIter.remove();
				continue;
			}
			if (layout.getRule().getName()==null){
				// name is not available, thus, rule is corrupted
				ruleIter.remove();
				continue;
			}
		}
		
		
		Module module = (Module) getPrimaryModelRoot();
		TreeIterator<EObject> moduleIter= module.eAllContents();
		EObject currentObject;
		List<Graph> graphsToMigrate = new Vector<Graph>(); 
		List<Edge> danglingEdges = new Vector<Edge>(); 
		while(moduleIter.hasNext()){
			currentObject=moduleIter.next();
			if(currentObject instanceof Graph && !(currentObject instanceof TripleGraph))
			{
				Graph graph = (Graph) currentObject;
				graphsToMigrate.add(graph);
			}
			// remove dangling edges
			if(currentObject instanceof Edge)
				if(((Edge) currentObject).getSource()==null || ((Edge) currentObject).getTarget()==null)
			{
				danglingEdges.add((Edge)currentObject);
			}

			
		}
		
		for(Graph graph: graphsToMigrate){
			migrateToTripleGraph(graph);
		}
		for(Edge edge: danglingEdges){
			edge.setGraph(null);
		}
		
		module.eSetDeliver(false);
		TransformationSystemTreeEditPart.sortRulesIntoCategories(module);
		module.eSetDeliver(true);
	}

//	private void migrateToTNode(Node node,
//			TreeIterator<EObject> moduleIter) {
//		// copy node contents
//		TNode tNode = NodeUtil.nodeToTNode(node);
//		
//		
//		// replace the node with the node with the new TNode in its container
//		if(node.eContainer()!=null){
//			Object containingFeature = node.eContainer().eGet(node.eContainingFeature());
//			// remove old node first from the iterator
//			moduleIter.remove();
//			if (containingFeature instanceof EList){
//				((EList<EObject>)containingFeature).add(tNode);	
//			}
//			else
//				node.eContainer().eSet(node.eContainingFeature(),tNode);
//		}
//
//	}


	private void migrateToTripleGraph(Graph graph) {
		// copy graph contents
		TripleGraph tripleGraph = GraphUtil.graphToTripleGraph(graph);
		// copy divider information
		if(graph==null) //
			return;
		GraphLayout divSC=GraphUtil.getGraphLayout(graph, true);
		GraphLayout divCT=GraphUtil.getGraphLayout(graph, false);
		if(divSC!=null && divCT!=null){
			tripleGraph.setDividerSC_X(divSC.getDividerX());
			tripleGraph.setDividerCT_X(divCT.getDividerX());
			tripleGraph.setDividerMaxY(divSC.getMaxY());
			// deconnect the deviders from the graph
			divSC.setGraph(null);
			divCT.setGraph(null);
		}
		// replace the graph with the new triple graph in its container
		if(graph.eContainer()!=null){
			Object containingFeature = graph.eContainer().eGet(graph.eContainingFeature());
			if (containingFeature instanceof EList){
				((EList<EObject>)containingFeature).add(tripleGraph);
				((EList<EObject>)containingFeature).remove(graph);
			}
			else
				graph.eContainer().eSet(graph.eContainingFeature(),tripleGraph);
		}
	}


	public TGG getLayout() {
		return layout;
	}
	
	@Override
	protected void save(IFile file, IProgressMonitor monitor)
			throws CoreException {
		
		repairTGGModel();
		super.save(file, monitor);
		monitor.beginTask("Saving " + file, 2);
		// save model to file
		try {
			layoutFilePath = file.getFullPath().removeFileExtension().addFileExtension(layoutExtension);
			layoutModelManager.save(layoutFilePath);
			monitor.worked(1);
			file.refreshLocal(IResource.DEPTH_ZERO, new SubProgressMonitor(
					monitor, 1));
			monitor.done();
		} catch (final FileNotFoundException e) {
			MuvitorActivator.logError("Error writing file.", e);
		} catch (final IOException e) {
			MuvitorActivator.logError("Error writing file.", e);
		}
	}
	
	
	public void addRulePage(Rule rule, RuleGraphicalPage rulePage){
		ruleToPage.put(rule, rulePage);
	}

	public RuleGraphicalPage getRulePage(Rule rule){
		return ruleToPage.get(rule);
	}
	
	public void addCritPairPage(CritPair crit, CriticalPairPage page) {
		critPairToPage.put(crit, page);
	}
	
	public CriticalPairPage getCritPairPage(CritPair crit){
		return critPairToPage.get(crit);
	}
}

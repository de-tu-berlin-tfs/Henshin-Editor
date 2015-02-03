/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.core.internal.resources.Workspace;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.TggUtil;
import de.tub.tfs.henshin.tggeditor.actions.AbstractTggActionFactory;
import de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.GenericTGGGraphLayoutAction;
import de.tub.tfs.henshin.tggeditor.actions.RestrictGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.TGGGenericCopyAction;
import de.tub.tfs.henshin.tggeditor.actions.TGGGenericPasteAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateAttributeConditonAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateNACAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateParameterAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreatePrototypeRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRecPrototypeRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleFolderAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateAllOpRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateAllOpRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateConcurrentRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateITRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateITRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteCCRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteITRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.RemoveMarkersAction;
import de.tub.tfs.henshin.tggeditor.actions.exports.ExportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportCorrAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelActionWithDefaultValues;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportSourceAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportTargetAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportTargetInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.LoadReconstructXMLForSource;
import de.tub.tfs.henshin.tggeditor.actions.validate.CheckRuleConflictAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidateAllRulesAction;
import de.tub.tfs.henshin.tggeditor.editparts.tree.HenshinTreeEditFactory;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.ui.TGGEditorConstants;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.views.graphview.CriticalPairPage;
import de.tub.tfs.henshin.tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.actions.GenericCutAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;


public class TreeEditor extends MuvitorTreeEditor {
	
	public static final String GRAPH_VIEW_ID = "tggeditor.views.graphview.GraphicalView";
	public static final String RULE_VIEW_ID = "tggeditor.views.ruleview.RuleGraphicalView";
	public static final String CONDITION_VIEW_ID = "tggeditor.views.ruleview.NACGraphicalView";
	public static final String CRITICAL_PAIR_VIEW_ID = "tggeditor.views.graphview.CriticalPairView";
					
	
	private TGG deprecatedLayout;
	private TGG tggModule;
	/**
	 * @return the module
	 */
	public TGG getModule() {
		return tggModule;
	}

	/**
	 * @param module the module to set
	 */
	public void setModule(TGG module) {
		this.tggModule = module;
	}

	private final String layoutExtension = "tgg";
	
	private HashMap<Rule, RuleGraphicalPage> ruleToPage = new HashMap<Rule, RuleGraphicalPage>();
	private HashMap<CritPair, CriticalPairPage> critPairToPage = new HashMap<CritPair, CriticalPairPage>();

	private EMFModelManager layoutModelManager = EMFModelManager.createModelManager(
			layoutExtension);

	private IPath layoutFilePath;
	private Thread saveThread;

	
	public TreeEditor() {
		super.cleanUp();
		TGGEditorConstants.updateFonts();
		//init = false;
		TggUtil.initClassConversions();	
		
	}
	
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
		markerID = "de.tub.tfs.tgg.marker.validationMarker";
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
		//registerAction(new LoadXMLXSDmodel(this));
		//registerAction(new ImportEMFModelAction(this));
		registerAction(new ImportSourceAction(this));
		registerAction(new ImportTargetAction(this));
		registerAction(new ImportCorrAction(this));
		registerAction(new CreateAttributeAction(this));
		registerAction(new CreateRuleAction(this));
		registerAction(new CreatePrototypeRulesAction(this));
		registerAction(new CreateRecPrototypeRulesAction(this));
		registerAction(new CreateRuleFolderAction(this));
		registerAction(new CreateNACAction(this));
		registerAction(new GraphValidAction(this));
		registerAction(new RuleValidAction(this));
		registerAction(new CheckRuleConflictAction(this));
		registerAction(new CreateParameterAction(this));
		registerAction(new CreateAttributeConditonAction(this));
		registerAction(new EditAttributeAction(this));
		//NEW
		registerAction(new GenerateITRuleAction(this));
		registerAction(new GenerateFTRuleAction(this));
		registerAction(new GenerateBTRuleAction(this));
		registerAction(new GenerateCCRuleAction(this));
		registerAction(new GenerateAllOpRuleAction(this)); // NEW SUSANN
		//NEW
		registerAction(new GenerateITRulesAction(this));
		registerAction(new GenerateConcurrentRulesAction(this, false));
		registerAction(new GenerateConcurrentRulesAction(this, true));
		registerAction(new GenerateFTRulesAction(this));
		registerAction(new GenerateBTRulesAction(this));
		registerAction(new GenerateCCRulesAction(this));
		registerAction(new GenerateAllOpRulesAction(this)); // NEW SUSANN
		registerAction(new RuleValidateAllRulesAction(this));
		//NEW
		registerAction(new ExecuteITRulesAction(this));
		registerAction(new ExecuteFTRulesAction(this));
		registerAction(new ExecuteBTRulesAction(this));
		registerAction(new ExecuteCCRulesAction(this));
		registerAction(new RemoveMarkersAction(this));
	    registerAction(new TGGGenericCopyAction(this));
        registerAction(new GenericCutAction(this));
        registerAction(new TGGGenericPasteAction(this)); 
		registerAction(new ImportInstanceModelAction(this));  
		registerAction(new ImportInstanceModelActionWithDefaultValues(this));  
		registerAction(new ExportInstanceModelAction(this));
		//NEW
		registerAction(new ImportTargetInstanceModelAction(this));
		
		registerActionOnToolBar(new GenericTGGGraphLayoutAction(this));
		registerActionOnToolBar(new RestrictGraphAction(this));

		

		IExtensionRegistry reg = Platform.getExtensionRegistry();
	    IExtensionPoint ep = reg.getExtensionPoint("de.tub.tfs.henshin.tgg.editor.graph.actions");
        IExtension[] extensions = ep.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
        	IExtension ext = extensions[i];
        	IConfigurationElement[] ce = 
        			ext.getConfigurationElements();
        	for (int j = 0; j < ce.length; j++) {

        		try {
        			AbstractTggActionFactory obj = (AbstractTggActionFactory) ce[j].createExecutableExtension("class");

        			registerAction(obj.createAction(this));

        		} catch (CoreException e) {
        			
        		}


        	}
        }
	}

	@Override
	protected EObject createDefaultModel() {
		// creates a TGG, which inherits from Module
		TggFactory factory = TggFactory.eINSTANCE;
		TGG transSys = factory.createTGG();
		transSys.setName("Transformation System");
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

		EObject primaryModelRoot = getPrimaryModelRoot();
		if(primaryModelRoot instanceof TGG)
			tggModule = (TGG) getPrimaryModelRoot();


		repairTGGModel();

		
		// open layout model
		final IFile file = ((IFileEditorInput) input).getFile();
		layoutFilePath = file.getFullPath().removeFileExtension().addFileExtension(layoutExtension);

		List<EObject> list=new ArrayList<EObject>(); 
		list = layoutModelManager.load(layoutFilePath, new ArrayList<EObject>()); // do not create default TGG

		if (list == null || list.isEmpty() || !(list.get(0) instanceof TGG)){
					
		} else {
			deprecatedLayout = (TGG) list.get(0);
			// check tgg annotation at root element
			if (TggUtil.getElemAnnotationValue(tggModule,
					TggUtil.HENSHIN_TGG_PKG_KEY) == null) {
				migrateToVersion_ONE_ZERO();
			}
			

			
		}
		
		//this.getModelRoots().add(deprecatedLayout);
		
		// re-registers this editors now with all model roots loaded
		IDUtil.registerEditor(this);

	}
	
//	@Override
//	protected void setPerspectiveID() {
//		perspectiveID =
//		TGGEditorActivator.getUniqueExtensionAttributeValue(
//				"org.eclipse.ui.perspectives", "id");
//	}

	/**
	 * ***************************************************************
	 * methods for restoring consistency of model if it is corrupted
	 * ***************************************************************
	 */
	
	private void repairTGGModel() {
		
		

		
		
		Module module = (Module) getPrimaryModelRoot();
		TreeIterator<EObject> moduleIter= module.eAllContents();
		EObject currentObject;
		List<Edge> danglingEdges = new Vector<Edge>(); 
		while(moduleIter.hasNext()){
			currentObject=moduleIter.next();

			// remove dangling edges
			if(currentObject instanceof Edge)
				if(((Edge) currentObject).getSource()==null || ((Edge) currentObject).getTarget()==null)
			{
				danglingEdges.add((Edge)currentObject);
			}
			// update lhs attribute values, if inconsistent
			if(currentObject instanceof TAttribute){
				updateLHSAttribute((TAttribute) currentObject);				
			}
		}
		
		for(Edge edge: danglingEdges){
			edge.setGraph(null);
		}
		
		module.eSetDeliver(false);
		TransformationSystemTreeEditPart.sortRulesIntoCategories(module);
		module.eSetDeliver(true);
	}
	
	
	/**
	 * ***************************************************************
	 * methods for migrating old files to the current version
	 * ***************************************************************
	 */

	private void migrateToVersion_ONE_ZERO() {

		
//		// remove all instances from module and put them as instanceGraphs in
//		// TGG
//		Iterator<Graph> instances = tggModule.getInstances().iterator();
//		List<Graph> graphsForTGG = new ArrayList<Graph>();
//		while (instances.hasNext()) {
//			Graph g = instances.next();
//			if (g instanceof TripleGraph)
//				graphsForTGG.add(g);
//		}
//		TripleGraph tGraph = null;
//		for(Graph g: graphsForTGG){
//			if (g instanceof TripleGraph){
//				tGraph = (TripleGraph) g;
//				tggModule.getInstances().add((TripleGraph) tGraph);
//			}
//				tggModule.getInstances().remove(g);
//		}
		
		
		// remove all tRules from layout model
		Iterator<TRule> tRuleIter=deprecatedLayout.getTRules().iterator();
		while(tRuleIter.hasNext()){
			tRuleIter.next();
			tRuleIter.remove();
		}
		Iterator<TRule> tRuleIter2=tggModule.getTRules().iterator();
		while(tRuleIter2.hasNext()){
			tRuleIter2.next();
			tRuleIter2.remove();
		}

		

		Iterator<NodeLayout> nodeLayoutIter=deprecatedLayout.getNodelayouts().iterator();
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
			refreshLayout((TNode) layout.getNode(),layout);
			nodeLayoutIter.remove();

		}
		
		Iterator<EdgeLayout> edgeIter=deprecatedLayout.getEdgelayouts().iterator();
		while(edgeIter.hasNext()){
			edgeIter.next();
			edgeIter.remove();
		}
		
		Iterator<GraphLayout> graphIter=deprecatedLayout.getGraphlayouts().iterator();
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
		
		Iterator<TRule> ruleIter=deprecatedLayout.getTRules().iterator();
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
			if (layout.getRule().eContainer() == null){
				ruleIter.remove();
			}
		}
		
		// annotations ==================

		// add annotations to module
		TggUtil.addRootTggAnnotation(tggModule);

		//getModelRoots().add(layout);
		

		// module => TGG
		if(tggModule instanceof TGG && (deprecatedLayout != null))
		{
			migrateModule();
		}

		
		
		TreeIterator<EObject> moduleIter= tggModule.eAllContents();
		EObject currentObject;
		List<Graph> graphsToMigrate = new Vector<Graph>(); 
		while(moduleIter.hasNext()){
			currentObject=moduleIter.next();
			
			// nodes: set component
			if(currentObject instanceof TNode)
			{
				TNode node = (TNode) currentObject;
				node.setComponent(NodeUtil.getComponentFromPosition(node));
			}
			
			// graphs
			if(currentObject instanceof Graph && !(currentObject instanceof TripleGraph))
			{
				Graph graph = (Graph) currentObject;
				graphsToMigrate.add(graph);
			}
		}
		
		for(Graph graph: graphsToMigrate){
			migrateToTripleGraph(graph);
		}
		
		


		
		
	}
	
	private static void refreshLayout(TNode node, NodeLayout nodeLayout) {
		
		// marker value is not available in ruleAttributeRHS, thus compute it
		if (nodeLayout == null) { // no layout is found
			// store coordinates (0,0)
			node.setX(0);
			node.setY(0);
		} else { // layout is found
			// store coordinates
			node.setX(nodeLayout.getX());
			node.setY(nodeLayout.getY());
		}
		return;
		
	}	
	
private void migrateModule() {

	TGG tgg=null;
	if(!(tggModule instanceof TGG))
		return;
	tgg= (TGG) tggModule;
	tgg.getImportedPkgs().addAll(deprecatedLayout.getImportedPkgs());
		
	}


private void updateLHSAttribute(TAttribute rhsAttribute) {
	if (rhsAttribute==null) return;
	// check that attribute is in rhs of a rule
	if (rhsAttribute.getGraph()== null) return;
	if (rhsAttribute.getGraph().getRule()== null) return;
	if (rhsAttribute.getGraph().getRule().getRhs()!=rhsAttribute.getGraph()) return;

	// updates the lhs attribute value if the lhs attribute exists and its value differs from the rhs attribute value
	// if attribute is not created by the rule, then update the corresponding value in LHS as well
	
	if (rhsAttribute.getMarkerType() == null 
			|| !rhsAttribute.getMarkerType().equals(RuleUtil.NEW)) {
		Attribute lhsAttribute = RuleUtil.getLHSAttribute(rhsAttribute);
		if (lhsAttribute!=null
				// lhs attribute has a different value as the rhs attribute
				&& !(lhsAttribute.getValue().equals(rhsAttribute.getValue()))) {
			// update lhs attribute value to current value of rhs attribute
			lhsAttribute.setValue(rhsAttribute.getValue());			
		}
	}

}



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
		if(tggModule instanceof TGG)
			return (TGG) tggModule;
		System.out.println("!WARNING: TGG layout is missing.");
		return null;
	}

	@Override
	protected void save(final IFile file, final IProgressMonitor monitor)
			throws CoreException {
		monitor.beginTask("saving emf model", 6);
		// repairTGGModel();
		if (saveThread != null && saveThread.isAlive())
			System.out.println("waiting for backup save thread to finish.");
		while (saveThread != null && saveThread.isAlive()){
			
			if (!Display.getDefault().readAndDispatch()){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		monitor.worked(1);
		LinkedList<EObject> test = new LinkedList<EObject>();
		test.addAll(getModelRoots());
		final ArrayList<EObject> copy = (ArrayList<EObject>) EcoreUtil.copyAll(test);
		
		saveThread = new Thread() {

			@Override
			public void run() {

				try {
					DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
					Date date = new Date();

					IFile modelFile = (IFile) ((Workspace)file.getWorkspace()).newResource(file.getFullPath().removeFileExtension().append("backup").addFileExtension(dateFormat.format(date)).addFileExtension(fileExtension), 1);
					EMFModelManager.createModelManager(fileExtension).save(modelFile.getFullPath(),copy.get(0));
					Display.getDefault().syncExec(new Runnable() {

						@Override
						public void run() {
							monitor.worked(1);
						}
					});


					IFolder backUpFolder = (IFolder) ((Workspace)file.getWorkspace()).newResource(file.getFullPath().removeFileExtension().append(""), 2);
					backUpFolder.setHidden(true);
					modelFile.setHidden(true);					

				} catch (Exception ex){
					ex.printStackTrace();
				}
				System.out.println("backup file saved.");
			}

		};
		

		TreeEditor.super.save(file, monitor);
		
		System.out.println("main file saved.");
		saveThread.start();
		monitor.done();

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
	
	@Override
	protected void finalize() throws Throwable {
//		init = false;
		super.finalize();
	}
	
	
	
	
	
	/**
	 * ***************************************************************
	 * methods for migrating old files from Version 0.8 to 0.9 - not in use
	 * ***************************************************************
	 */
	
	
	
	
	/**
	 * retrieves all information from deprecated lists of imported packages and stores them in the currrent list of imported packages
	 */
	private void refreshDeprecatedEntries() {
			ImportedPackage pkg;
//	public EPackage getSource(){
			if(tggModule.getSource()!=null){
				pkg = TggFactory.eINSTANCE.createImportedPackage();
				pkg.setPackage(tggModule.getSource());
				pkg.setComponent(TripleComponent.SOURCE);
				tggModule.getImportedPkgs().add(pkg);
				tggModule.setSource(null);
			}
			if(tggModule.getTarget()!=null){
				pkg = TggFactory.eINSTANCE.createImportedPackage();
				pkg.setPackage(tggModule.getTarget());
				pkg.setComponent(TripleComponent.TARGET);
				tggModule.getImportedPkgs().add(pkg);
				tggModule.setTarget(null);
			}
			if(tggModule.getCorresp()!=null){
				pkg = TggFactory.eINSTANCE.createImportedPackage();
				pkg.setPackage(tggModule.getCorresp());
				pkg.setComponent(TripleComponent.CORRESPONDENCE);
				tggModule.getImportedPkgs().add(pkg);
				tggModule.setCorresp(null);
			}
//	
			List<ImportedPackage> pkgs;
			if(tggModule.getSourcePkgs()!=null){
				pkgs =  NodeTypes.getImportedPackagesFromEPackages(tggModule.getSourcePkgs(),TripleComponent.SOURCE);
				markImportedPackages(pkgs,TripleComponent.SOURCE);
				tggModule.getImportedPkgs().addAll(pkgs);
				tggModule.getSourcePkgs().clear();
//		return this.corr;
			}
			if(tggModule.getCorrespondencePkgs()!=null){
				pkgs =  NodeTypes.getImportedPackagesFromEPackages(tggModule.getCorrespondencePkgs(),TripleComponent.CORRESPONDENCE);
				markImportedPackages(pkgs,TripleComponent.CORRESPONDENCE);
				tggModule.getImportedPkgs().addAll(pkgs);
				tggModule.getCorrespondencePkgs().clear();
			}
			if(tggModule.getTargetPkgs()!=null){
				pkgs =  NodeTypes.getImportedPackagesFromEPackages(tggModule.getTargetPkgs(),TripleComponent.TARGET);
				markImportedPackages(pkgs,TripleComponent.TARGET);
				tggModule.getImportedPkgs().addAll(pkgs);
				tggModule.getTargetPkgs().clear();
			}
		}
	private void markImportedPackages(List<ImportedPackage> pkgs,
		TripleComponent component) {
		for(ImportedPackage p: pkgs){
			p.setComponent(component);
		}
	}

	
	
}

package tggeditor;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IFileEditorInput;

import tgg.CritPair;
import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tgg.TGGPackage;
import tgg.TRule;
import tggeditor.actions.TGGGenericCopyAction;
import tggeditor.actions.TGGGenericCutAction;
import tggeditor.actions.TGGGenericPasteAction;
import tggeditor.actions.create.graph.CreateAttributeAction;
import tggeditor.actions.create.graph.CreateGraphAction;
import tggeditor.actions.create.rule.CreateNACAction;
import tggeditor.actions.create.rule.CreateParameterAction;
import tggeditor.actions.create.rule.CreateRuleAction;
import tggeditor.actions.create.rule.GenerateFTRuleAction;
import tggeditor.actions.create.rule.GenerateFTRulesAction;
import tggeditor.actions.execution.ExecuteFTRulesAction;
import tggeditor.actions.imports.ImportCorrAction;
import tggeditor.actions.imports.ImportSourceAction;
import tggeditor.actions.imports.ImportTargetAction;
import tggeditor.actions.validate.CheckRuleConflictAction;
import tggeditor.actions.validate.GraphValidAction;
import tggeditor.actions.validate.RuleValidAction;
import tggeditor.editparts.tree.HenshinTreeEditFactory;
import tggeditor.views.criticalview.CriticalPairPage;
import tggeditor.views.ruleview.RuleGraphicalPage;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;


public class TreeEditor extends MuvitorTreeEditor {
	
	public static final String GRAPH_VIEW_ID = "tggeditor.views.graphview.GraphicalView";
	public static final String RULE_VIEW_ID = "tggeditor.views.ruleview.RuleGraphicalView";
	public static final String CONDITION_VIEW_ID = "tggeditor.views.ruleview.NACGraphicalView";
	public static final String CRITICAL_PAIR_VIEW_ID = "tggeditor.views.graphview.CriticalPairView";
	
	private TGG layout;

	private final String layoutExtension = "tgg";
	
	private HashMap<Rule, RuleGraphicalPage> ruleToPage = new HashMap<Rule, RuleGraphicalPage>();
	private HashMap<CritPair, CriticalPairPage> critPairToPage = new HashMap<CritPair, CriticalPairPage>();

	private EMFModelManager layoutModelManager = new EMFModelManager(
			layoutExtension);

	private IPath layoutFilePath;
	
	static {
		registerViewID(HenshinPackage.Literals.GRAPH, GRAPH_VIEW_ID);
		registerViewID(HenshinPackage.Literals.RULE, RULE_VIEW_ID);
		registerViewID(HenshinPackage.Literals.NESTED_CONDITION, CONDITION_VIEW_ID);
		registerViewID(TGGPackage.Literals.CRIT_PAIR, CRITICAL_PAIR_VIEW_ID);
	}

	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			TreeViewer viewer) {
		return new TreeContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
		registerAction(new CreateGraphAction(this));
		//registerAction(new ImportEMFModelAction(this));
		registerAction(new ImportSourceAction(this));
		registerAction(new ImportTargetAction(this));
		registerAction(new ImportCorrAction(this));
		registerAction(new CreateAttributeAction(this));
		registerAction(new CreateRuleAction(this));
		registerAction(new CreateNACAction(this));
		registerAction(new GraphValidAction(this));
		registerAction(new RuleValidAction(this));
		registerAction(new CheckRuleConflictAction(this));
		registerAction(new CreateParameterAction(this));
		registerAction(new GenerateFTRuleAction(this));
		registerAction(new GenerateFTRulesAction(this));
		registerAction(new ExecuteFTRulesAction(this));
        registerAction(new TGGGenericCopyAction(this));
        registerAction(new TGGGenericCutAction(this));
        registerAction(new TGGGenericPasteAction(this)); 
	}

	@Override
	protected EObject createDefaultModel() {
		HenshinFactory factory = HenshinFactory.eINSTANCE;
		TransformationSystem transSys = factory.createTransformationSystem();
		transSys.setName("Transformation System");
		Graph graph = factory.createGraph();
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
		list = layoutModelManager.load(layoutFilePath, Arrays.asList((EObject)TGGFactory.eINSTANCE.createTGG()));

		if (list == null || list.isEmpty() || !(list.get(0) instanceof TGG)){
					
		} else {
			layout = (TGG) list.get(0);
			
//			TransformationSystem trafo = ModelUtil.getTransSystem(layout.getGraphlayouts().get(0));
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
	
	private void repairTGGModel() {
		Iterator<NodeLayout> nodeIter=layout.getNodelayouts().iterator();
		while(nodeIter.hasNext()){
			NodeLayout layout=nodeIter.next();
			if (layout.getNode()==null){
				nodeIter.remove();
				continue;
			}
			if (layout.getNode().getGraph()==null){
				nodeIter.remove();
				continue;
			}
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
		}
		
		Iterator<TRule> ruleIter=layout.getTRules().iterator();
		while(ruleIter.hasNext()){
			TRule layout=ruleIter.next();
			if (layout.getRule()==null){
				ruleIter.remove();
				continue;
			}
		}
		
		Iterator<CritPair> critPairIter = layout.getCritPairs().iterator();
		while (critPairIter.hasNext()) {
			CritPair layout = critPairIter.next();
			if (layout.getOverlapping() == null) {
				critPairIter.remove();
				continue;
			}
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

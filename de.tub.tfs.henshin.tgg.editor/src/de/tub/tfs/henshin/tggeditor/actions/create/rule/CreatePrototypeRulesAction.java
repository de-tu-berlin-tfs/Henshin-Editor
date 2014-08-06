package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.SingleElementListSelectionDialog;


public class CreatePrototypeRulesAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.create.CreatePrototypeRuleAction";
	private Module transSys;
	private IndependentUnit unit = null;
	public CreatePrototypeRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Create Prototype Rules");
		setToolTipText("Create prototype rules for a class");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			if ((editpart instanceof RuleFolderTreeEditPart)) {
				unit = (IndependentUnit) editpart.getModel();
				while (editpart != editpart.getRoot() && !(editpart instanceof TransformationSystemTreeEditPart))
					editpart = editpart.getParent();
				transSys = (Module) editpart.getModel();
				
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		Shell shell = new Shell();
		
		
		
		List<EClass> nodeTypes = new LinkedList<EClass>();
		
		TGG system = GraphicalNodeUtil.getLayoutSystem(transSys);
		
		List<EPackage> epackages = getPackages(system, TripleComponent.SOURCE);
		
		nodeTypes = NodeTypes.getNodeTypesOfEPackages(epackages,false);
		
		EClass eClass = new SingleElementListSelectionDialog<EClass>(shell,
				new LabelProvider() {
					@Override
					public String getText(Object element) {
						return ((EClass) element).getName();
					}

//					@Override
//					public Image getImage(Object element) {
//						return IconUtil.getIcon("node18.png");
//					}
				}, nodeTypes.toArray(new EClass[nodeTypes.size()]),
				"Node Type Selection",
				"Select a EClass for the prototype rules:").run();
				
		shell.close();		
		
		
		List<Rule> rules = buildPrototype(eClass);
		
		transSys.getUnits().addAll(rules);
		
		super.run();
	}
	
	private List<Rule> buildPrototype(EClass eClass){
		List<Rule> rules = new LinkedList<Rule>();
		rules.add(buildRule(eClass));
		for (EReference ref : eClass.getEReferences()) {
			rules.add(buildRule(eClass, ref));
			if (ref.isContainment()){
				//if (ref.getEType() instanceof EClass)
				//	rules.addAll(buildPrototype((EClass) ref.getEType()));
			}
		}	
		
		return rules;
	}
	
	private Rule buildRule(EClass eClass,EStructuralFeature feat){
		Rule rule = createEmptyRule(eClass, feat);
		TNode sourceNode = addSourceNode(eClass, rule, false);
		TNode targetNode = addSourceNode((EClass) feat.getEType(), rule, true);
		addFeature(sourceNode, targetNode, feat);
		return rule;
	}
	
	
	private Rule buildRule(EClass root){
		Rule rule = createEmptyRule(root);
		addSourceNode(root, rule, true);
		return rule;
	}
	
	private Rule createEmptyRule(EClass eClass,EStructuralFeature feat){
		TGGRule r = TggFactory.eINSTANCE.createTGGRule();
		r.setMarkerType(RuleUtil.TGG_RULE);
		r.setName("Proto" + eClass.getName() + "_" + feat.getName() + "2SPELL");
		TripleGraph lhs = TggFactory.eINSTANCE.createTripleGraph();
		TripleGraph rhs = TggFactory.eINSTANCE.createTripleGraph();
		lhs.setName("lhs");
		rhs.setName("rhs");
		r.setLhs(lhs);
		r.setRhs(rhs);
		return r;
	}
	
	private Rule createEmptyRule(EClass eClass){
		TGGRule r = TggFactory.eINSTANCE.createTGGRule();
		r.setMarkerType(RuleUtil.TGG_RULE);
		r.setName("Proto" + eClass.getName() + "2SPELL");
		TripleGraph lhs = TggFactory.eINSTANCE.createTripleGraph();
		TripleGraph rhs = TggFactory.eINSTANCE.createTripleGraph();
		lhs.setName("lhs");
		rhs.setName("rhs");
		r.setLhs(lhs);
		r.setRhs(rhs);
		return r;
	}
	
	private TNode addSourceNode(EClass eClass,Rule rule,boolean create){
		TNode tNode = TggFactory.eINSTANCE.createTNode();
		tNode.setType(eClass);
		if (create){
			rule.getRhs().getNodes().add(tNode);
			tNode.setMarkerType(RuleUtil.NEW);
		} else {
			rule.getRhs().getNodes().add(tNode);
			TNode lhsNode = TggFactory.eINSTANCE.createTNode();
			lhsNode.setType(eClass);
			rule.getLhs().getNodes().add(lhsNode);
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping(lhsNode, tNode);
			rule.getMappings().add(mapping);
			tNode.setMarkerType(null);
		}
		return tNode;
	}
	
	private TEdge addFeature(TNode source,TNode target,EStructuralFeature feat){
		TEdge tEdge = TggFactory.eINSTANCE.createTEdge();
		tEdge.setType((EReference) feat);
		tEdge.setMarkerType(RuleUtil.NEW);
		tEdge.setSource(source);
		tEdge.setTarget(target);
		source.getGraph().getEdges().add(tEdge);
		
		return tEdge;
		
	}
	
	public static List<EPackage> getPackages(TGG layoutModel, TripleComponent type) {
		
		if (layoutModel == null) {ExceptionUtil.error("Layout model is missing"); return null;}
		return NodeTypes.getEPackagesOfComponent(layoutModel.getImportedPkgs(),type);
	}
	

}

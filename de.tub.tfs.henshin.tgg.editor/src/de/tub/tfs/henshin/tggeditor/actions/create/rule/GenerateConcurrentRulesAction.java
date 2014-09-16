/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateAttributeConditionCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateNACCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterMappingCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.InsertConcurrentRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleList;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;
//NEW GERARD
/**The class GenerateConcurrentRulesAction generates concurrent rules from selcted TGG rules. The action
 * is registered in the Contextmenu of the tree editor containing the TGG rules folder.
 * This action merges selected rules and creates concurrent rules, 
 * which are necessary for the integration of the source and target graphs.
 * 
 * 
 * @author Gerard Kirpach
 */
public class GenerateConcurrentRulesAction extends SelectionAction {
	
	public static boolean calcInProgress = false;
	
	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateConcurrentRulesAction";
	
	
	/** The Constant DESC for the description. */
	protected String DESC = "Generate Concurrent Rules";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Generates Concurrent Rules from selected Rules";
	
	/**
	 * The layout System
	 */
	protected TGG layoutSystem;
	
	/**
	 * The transformatin System module containing the selected objects
	 */
	protected Module transSystem;
	
	/**
	 * The selected rules from which the Concurrent Rules are generated.
	 */
	protected Map<Rule, RuleFolderTreeEditPart> sRule2Folder;
	
	/**
	 * The commands which are used to execute the generation of a concurrent rules from the selected TGG rules
	 */
	protected CompoundCommand command;
	
	/**
	 * All TGG rules present in the environment.
	 */
	private List<Rule> allRules;
	
	/**
	 * All Transformation rules present in the environment
	 */
	private List<TRule> tRules;
	
	//there are two insertion methods available that are chosen depending on this flag
	// private final static boolean EXPLICIT_INSERTION = false;
	
	/**
	 * The constructor of the GenerateConcurrentTGGRulesAction
	 * @param part
	 */
	public GenerateConcurrentRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setDescription(DESC);
		setText(DESC);
		setToolTipText(TOOLTIP);
	}

	
	/** Is only enabled for the context menu of a rule.
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		final List<?> selectedObjects = getSelectedObjects();
		sRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
		
		RuleFolderTreeEditPart sFolderTEP = null;
		List<Unit> sSubRules = null;
		
		RuleTreeEditPart sRuleTEP = null;
		Rule sRule = null;

		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return false;
		}
		
		for (Object selectedObject : selectedObjects) {
			if (selectedObject == null || !(selectedObject instanceof EditPart)) {
				continue; // filter out non EditParts
			}
			
			if (transSystem == null) { // get TransformationSystem
				EditPart editpart = (EditPart) selectedObject;
				while (editpart != editpart.getRoot()
						&& !(editpart instanceof TransformationSystemTreeEditPart)) {
					editpart = editpart.getParent();
				}
				// editpart is the TransformationSystemTreeEditPart
				transSystem = (Module) editpart.getModel();
			}
			
			// case 1 : selected object is a rule
			if ((selectedObject instanceof RuleTreeEditPart)
					&& (((RuleTreeEditPart) selectedObject).getModel() instanceof Rule)) {
				sRuleTEP = (RuleTreeEditPart) selectedObject;
				sRule = (Rule) sRuleTEP.getModel();
				if (tRules == null) {
					layoutSystem = GraphicalNodeUtil.getLayoutSystem(sRule);
					if (layoutSystem == null) {
						return false;
					}
					tRules = layoutSystem.getTRules();
				}

				// check whether selected rule is a transformation rule
				if (isTRule(sRule)) {
					return false;
				}
				sRule2Folder.put(sRule,
						(RuleFolderTreeEditPart) sRuleTEP.getParent());
				// case 2 : selected object is a folder
			} else if (selectedObject instanceof RuleFolderTreeEditPart) {
				sFolderTEP = (RuleFolderTreeEditPart) selectedObject;
				sSubRules = new LinkedList<Unit>();
				addAllSubRules(sFolderTEP, sSubRules);

				// get transformation rules
				if (tRules == null
						&& !(sSubRules.isEmpty() && sRule2Folder
								.isEmpty())) {
					Unit rule = null;
					if (!sRule2Folder.isEmpty()) {
						rule = (Unit) (sRule2Folder.keySet().toArray()[0]);
					}
					if (rule == null) {
						rule = (Unit) sSubRules.get(0);
					}
					layoutSystem = GraphicalNodeUtil.getLayoutSystem(rule);
					if (layoutSystem == null) {
						continue;
					}
					tRules = layoutSystem.getTRules();
				}
				
				if (tRules == null) {
					continue;
				}

				for (Unit subRule : sSubRules) {
					sRule = (Rule) subRule;
					if (isTRule(sRule)) {
						return false;
					}
				}
			}
		}
		return ((sRule2Folder.size() > 1) && setAllRules());
	}
	
	protected boolean isTRule(Rule rule) {
		if (tRules == null || rule == null) {
			// we suppose worst case
			return true; 
		}
		for (TRule tRule : tRules) {
			if (rule.equals(tRule.getRule()) || rule == tRule) {
				return true;
			}
		}
		return false;
	}
	
	protected boolean setAllRules() {
		allRules = new LinkedList<Rule> ();
		List<Unit> units = transSystem.getUnits();
		if (units == null || tRules == null) {
			return false;
		}
		
		Rule rule = null;
		for (Unit unit : units) {
			if (unit instanceof Rule) {
				rule = (Rule) unit;
				if (!isTRule(rule)) {
					RuleUtil.setLhsCoordinatesAndLayout(rule);
					allRules.add(rule);
				}
			}
		}
		return true;
	}
	
	/**
	 * Given the RuleFolderTreeEditPart selectedFolderTEP of a root folder, this
	 * method fills selectedFolderSubRules with containing rules and subfolder
	 * rules.
	 * 
	 */
	protected void addAllSubRules(
			RuleFolderTreeEditPart selectedFolderTEP,
			List<Unit> selectedFolderSubRules) {
		Rule selectedRule = null;
		if (sRule2Folder == null) {
			return;
		}
		for (Object child : selectedFolderTEP.getChildren()) {
			if (child instanceof EditPart) {
				if (child instanceof RuleFolderTreeEditPart) {
					addAllSubRules((RuleFolderTreeEditPart) child,
							selectedFolderSubRules);
				} else if (child instanceof RuleTreeEditPart) {
					Object childRule = ((RuleTreeEditPart) child)
							.getModel();
					if (childRule instanceof Rule) {
						selectedRule = (Rule) childRule;
						selectedFolderSubRules.add(selectedRule);
						sRule2Folder.put(selectedRule, selectedFolderTEP);
					}
				}
			}
		}
	}
	
	/** 
	 * Executes the concurrent rule generation and insertion.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (sRule2Folder == null) {
			return;
		}
		calcInProgress = true;
		// concurrent rules created in previous round
		Map<Rule, RuleFolderTreeEditPart> prevConRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
		// concurrent rules created in current round
		Map<Rule, RuleFolderTreeEditPart> currConRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
//		// names of concurrent rules generated in current round
//		Set<String> currConRuleNames = new HashSet<String>();
		// newly generated concurrent rules during all the rounds
		Map<Rule, RuleFolderTreeEditPart> newConRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
		// temporarily created concurrent rule that potentially doesn't collide with existing rules
		List<Rule> newConRules = null;
		// temporarily created concurrent rules that don't collide with existing rules
		List<Rule> uniqueNewConRules = new LinkedList<Rule>();
		//parent folder of new rule
		RuleFolderTreeEditPart parentFolder = null;
		//indicates whether equivalent rule already exists
		boolean collision;
		// flag that defines the order of the rules to be merged
		boolean swap = true;
		//temporary Rule for swap
		Rule tmpRule = null;
		boolean firstRound = true;
// ...
for (Rule sRule : sRule2Folder.keySet()){ // copy selected rules
	currConRule2Folder.put(sRule, sRule2Folder.get(sRule));
}
while (!currConRule2Folder.isEmpty()) { // while new rules generated
	prevConRule2Folder = currConRule2Folder;
	currConRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
	for (Rule sRule : sRule2Folder.keySet()) {
		for (Rule prevConRule : prevConRule2Folder.keySet()) {
			if (firstRound && (sRule == prevConRule)) { 
				continue; 
			} 
			swap = firstRound; // initially true
			do { // executed once/twice in initial/successive round(s)
				if (ConcurrentRuleUtil.isConcurrent(sRule, prevConRule) 
						&& !sRule.getName().contains(prevConRule.getName())
						&& !prevConRule.getName().contains(sRule.getName())) {
					newConRules = new ConcurrentRuleList(sRule, prevConRule);
					uniqueNewConRules.clear();
					for (Rule newConRule : newConRules) {
						collision = false;
						for (Rule rule : allRules) {
							if (ConcurrentRuleUtil.equivalent(newConRule, rule)) {
								collision = true;
								break;
							}
						}
						if (!collision) {
							uniqueNewConRules.add(newConRule);
						}
					}
					parentFolder = getCommonParentFolder(
							(swap ? sRule2Folder.get(prevConRule) 
								  : sRule2Folder.get(sRule)),
							(swap ? prevConRule2Folder.get(sRule) 
							      : prevConRule2Folder.get(prevConRule)));
					for (Rule uniqueNewConRule : uniqueNewConRules) {
						currConRule2Folder.put(uniqueNewConRule, parentFolder);
						newConRule2Folder.put(uniqueNewConRule, parentFolder);
						allRules.add(0, uniqueNewConRule);
					}
				}
				// swap rules for 2. round of inner loop (order matters) 
				tmpRule = sRule;
				sRule = prevConRule;
				prevConRule = tmpRule;
				swap = !swap;
			} while (swap); 
			// swap == false after first loop in round 1
			// swap == false after second loop in round > 1 
		}
	}
	firstRound = false;
}
// ...

		command = new CompoundCommand();
		for (Rule cRule : newConRule2Folder.keySet()) {
			RuleFolderTreeEditPart folder = newConRule2Folder.get(cRule);
			// if (EXPLICIT_INSERTION) insertConcurrentRule(cRule,
			// (IndependentUnit) folder.getModel()); else
			command.add(new InsertConcurrentRuleCommand(cRule,
					(IndependentUnit) folder.getModel(), transSystem, folder));
		}
		// if (!EXPLICIT_INSERTION)
		command.execute();
		calcInProgress = false;
	}
	
	/***
	 * This method returns deepest common parent folder of both folders.
	 * @param ruleFolderEditPartLeft
	 * @param ruleFolderEditPartRight
	 * @return
	 */
	
	protected RuleFolderTreeEditPart getCommonParentFolder(RuleFolderTreeEditPart ruleFolderEditPartLeft, RuleFolderTreeEditPart ruleFolderEditPartRight){
		if (ruleFolderEditPartLeft==null || ruleFolderEditPartRight==null) return null;
		EditPart editPartLeft = ruleFolderEditPartLeft;
		EditPart editPartRight = ruleFolderEditPartRight;
		RootEditPart root = editPartLeft.getRoot();
		EditPart top = root;
		boolean found = true;
		while (found && includesSubEditPart(top, editPartLeft) && includesSubEditPart(top, editPartRight)){
			found = false;
			for (Object child : top.getChildren()){
				if (child instanceof EditPart 
						&& includesSubEditPart((EditPart) child, editPartLeft) 
						&& includesSubEditPart((EditPart) child, editPartRight)){
					top = (EditPart) child;
					found = true;
					break;
				}
			}
		}
		if (!(top.getModel() instanceof IndependentUnit)) {
			return null;
		}
		return (RuleFolderTreeEditPart) top;
	}
	
	
	protected boolean includesSubEditPart(EditPart top, EditPart toFind){
		if (top==toFind) return true;
		for (Object child : top.getChildren()){
			if (child instanceof EditPart){
				if (includesSubEditPart((EditPart) child, toFind)) return true;
			}
		}
		return false;
	}
	
	
	/* unimportant section*/
	
	//constructs and inserts the given rule into the folder 
//	private void insertConcurrentRule(Rule rule, IndependentUnit folder){
//		Rule newRule = executeCreateEmptyRule(rule, folder);
//		Graph2GraphCopyMappingList graphR2CopyWithoutEdges = new Graph2GraphCopyMappingList(rule.getRhs());
//		HashMap<Node, Node> mappingGraphR2CopyWithoutEdges = new HashMap<Node, Node>();
//		Graph graphWithoutEdges = graphR2CopyWithoutEdges.getGraphCopy();
//		for (Node nodeR : rule.getRhs().getNodes()){
//			TNode tNodeR = (TNode)nodeR;
//			TNode nodeWithoutEdges = (TNode)graphR2CopyWithoutEdges.getImage(tNodeR);
//			nodeWithoutEdges.getAttributes().clear();
//			nodeWithoutEdges.setMarkerType(null);
//			mappingGraphR2CopyWithoutEdges.put(tNodeR, nodeWithoutEdges);
//			while(graphWithoutEdges.getEdges().size()!=0){
//				graphWithoutEdges.removeEdge(graphWithoutEdges.getEdges().get(0));
//			}
//			
//			Point p = new Point();
//			p.x = tNodeR.getX();
//			p.y = tNodeR.getY();
//			TripleComponent tc = NodeUtil.guessTC(tNodeR);
//			//TNode newNode = 
//			this.executeCreateNode(nodeWithoutEdges, p, tc);
//						
//			if (nodeWithoutEdges.getMarkerType()!=null){
//				this.executeMarkNode(nodeWithoutEdges);
//			}
//			
//			do{
//				this.executeMarkNode(nodeWithoutEdges);	
//			}while(nodeWithoutEdges.getMarkerType()!=tNodeR.getMarkerType());
//			
//			
//			for (Attribute oldAttr : tNodeR.getAttributes()){
//				TAttribute newAttr = this.executeCreateAttribute(oldAttr);
//				if (((TAttribute)oldAttr).getMarkerType()!=((TAttribute)newAttr).getMarkerType()){
//					this.executeMarkAttribute(newAttr);
//				}
//			}
//		}
//		
//		for (int i = 0; i < rule.getRhs().getEdges().size(); i++) {
//			TEdge oldEdge = (TEdge) rule.getRhs().getEdges().get(i);
//			TEdge newEdge = executeCreateEdge(
//					mappingGraphR2CopyWithoutEdges.get(oldEdge.getSource()),
//					mappingGraphR2CopyWithoutEdges.get(oldEdge.getTarget()),
//					oldEdge.getType());
//
//			// mark, if it was marked before
//			if (oldEdge.getMarkerType() != newEdge.getMarkerType())
//				executeMarkEdge(newEdge);
//		}
//		
//		Iterator<Parameter> paramIterator = rule.getParameters().iterator();
//		while (paramIterator.hasNext()){
//			Parameter param = paramIterator.next();
//			paramIterator.remove();
//			param.setUnit(newRule);
//			this.executeCreateParameter(param);
//		}
//	}
//	
//	/**
//	 * command for empty rule creation
//	 */
//	private CreateRuleCommand ruleCommand;
//	/**
//	 * command for attributeConditionCreation
//	 */
//	private CreateAttributeConditionCommand attributeConditionCommand;
//	
//	private CreateNACCommand nacCommand;
//	private CreateParameterCommand parameterCommand;
//	private CreateParameterMappingCommand parameterMappingCommand;
//	
//	private CreateRuleAttributeCommand attributeCommand;
//	private CreateRuleNodeCommand nodeCommand;
//	private CreateRuleEdgeCommand edgeCommand;
//	
//	private MarkAttributeCommand markAttributeCommand;
//	private MarkEdgeCommand markEdgeCommand;
//	private MarkCommand markNodeCommand;
//	
//	//this method is currently not in use because concurrent rules should not be deleted 
//	//if other concurrent rules are created (otherwise one could not create concurrent rules one by one)
//	//in case this featzure is required it is available in the following method
//	private void cleanUpOldContainer(IndependentUnit ruleFolder, CompoundCommand cmd) {
//		if (ruleFolder == null) return;
//		for (Unit unit : ruleFolder.getSubUnits()) {
//			if (unit instanceof IndependentUnit) {
//				//cleanUpOldContainer((IndependentUnit) unit,cmd);
//			} else if (unit instanceof Rule){
//				Rule rule = (Rule) unit;
//				//only delete concurrent rules from folder
//				if (rule.getName().contains("Concurrent")){
//					cmd.add(new DeleteOpRuleCommand(rule, ""));
//				}
//			}
//		}
//	}
//	
//	private void executeMarkNode(Node node){
//		markNodeCommand = new MarkCommand(node);
//		execute(markNodeCommand);
//	}
//	
//	private Rule executeCreateEmptyRule(Rule rule, IndependentUnit folder){
//		ruleCommand = new CreateRuleCommand(transSys, rule.getName(), folder);
//		execute(ruleCommand);
//		((TripleGraph)ruleCommand.getRhsGraph()).setDividerCT_X(((TripleGraph)rule.getRhs()).getDividerCT_X());
//		((TripleGraph)ruleCommand.getRhsGraph()).setDividerSC_X(((TripleGraph)rule.getRhs()).getDividerSC_X());
//		return ruleCommand.getRule();
//	}
//	
//	private TNode executeCreateNode(TNode nodeWithoutEdges, Point p, TripleComponent tc){
//		ruleCommand.getRhsGraph().getNodes().add(nodeWithoutEdges);
//		nodeWithoutEdges.setGraph(ruleCommand.getRhsGraph());
//		nodeCommand = new CreateRuleNodeCommand(nodeWithoutEdges, ruleCommand.getRhsGraph(), p, tc);
//		execute(nodeCommand);
//		return ((TNode)nodeCommand.getNode());
//	}
//	
//	private TEdge executeCreateEdge(Node source, Node target, EReference type){
//		edgeCommand = new CreateRuleEdgeCommand(ruleCommand.getRhsGraph(), source, target, type);
//		execute(edgeCommand);
//		return (TEdge)edgeCommand.getEdge();
//	}
//	
//	private void executeMarkEdge(Edge edge){
//		markEdgeCommand = new MarkEdgeCommand(edge);
//		execute(markEdgeCommand);
//	}
//	
//	private TAttribute executeCreateAttribute(Attribute attribute){
//		attributeCommand = new CreateRuleAttributeCommand(nodeCommand.getNode(), attribute.getType().getName());
//		attributeCommand.setAttributeType(attribute.getType());
//		attributeCommand.setValue(attribute.getValue());
//		attributeCommand.execute();
//		return (TAttribute)attributeCommand.getAttribute();
//	}
//	
//	private void executeMarkAttribute(Attribute attribute){
//		markAttributeCommand = new MarkAttributeCommand(attribute);//attributeCommand.getAttribute());
//		execute(markAttributeCommand);
//	}
//	
//	private void executeCreateParameter(Parameter parameter){
//		this.parameterCommand = new CreateParameterCommand(parameter.getUnit(), parameter);//attributeCommand.getAttribute());
//		execute(parameterCommand);
//	}

}

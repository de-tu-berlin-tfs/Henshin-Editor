package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
import de.tub.tfs.henshin.tgg.TGGRule;
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
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleComparator;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleList;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;
/**The class GenerateConcurrentRulesAction generates concurrent rules from selcted TGG rules. The action
 * is registered in the Contextmenu of the tree editor containing the TGG rules folder.
 * This action merges selected rules and creates concurrent rules, 
 * which are necessary for the integration of the source and target graphs.
 * 
 * 
 * @author Gérard Kirpach
 */
public class GenerateConcurrentRulesAction extends SelectionAction {
	
	public static boolean calcInProgress = false;
	
	/**
	 * The fully qualified ID.
	 */
	public static final String ID_1 = "de.tub.tfs.henshin.tggeditor.actions.GenerateConcurrentRulesAction";
	public static final String ID_2 = "de.tub.tfs.henshin.tggeditor.actions.GenerateConcurrentRulesAction.Recursive";
	
	
	/** The Constant DESC for the description. */
	protected String DESC = "Generate Concurrent Rules";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Generates Concurrent Rules from selected Rules";
	
	protected String DESC_SINGLE = "Generate Concurrent Rule";
	protected String TOOLTIP_SINGLE = "Generates Concurrent Rule from selected Rule";
	
	
	private boolean recursively = false;
	
	/**
	 * Indicated whether a single Rule is selected
	 */
	protected boolean singleRule = false;

	/**
	 * The selected rules from which the Concurrent Rules are generated.
	 */
	protected LinkedHashMap<TGGRule, RuleFolderTreeEditPart> sRule2Folder = new LinkedHashMap<TGGRule, RuleFolderTreeEditPart>();
	
	/**
	 * The transformatin System module containing the selected objects
	 */
	protected Module transSystem;
	
	/**
	 * All TGG rules initially present in the environment.
	 */
	private List<TGGRule> initialRules = new LinkedList<TGGRule>();
	
	
	/**
	 * This attribute is used to memorize the order in which the rules appear in the RuleFolder. 
	 * Based on this order, its helper functions decide the order of the newly generated methods with respect
	 * to the order of the initial rules.
	 */
	private ConcurrentRuleComparator ruleComparator;
	
	//there are two insertion methods available that are chosen depending on this flag
	// private final static boolean EXPLICIT_INSERTION = false;
	/**
	 * The commands which are used to execute the generation of a concurrent rules from the selected TGG rules
	 */
	protected CompoundCommand command;
	
	/**
	 * The constructor of the GenerateConcurrentTGGRulesAction
	 * @param part
	 */
	public GenerateConcurrentRulesAction(IWorkbenchPart part, boolean recursively) {
		super(part);
		
		setId(ID_1);
		if (recursively){
			DESC += " (recursively)";
			TOOLTIP += " (recursively)";
			setId(ID_2);
		}
		setDescription(DESC);
		setText(DESC);
		setToolTipText(TOOLTIP);
		this.recursively = recursively;
	}

	
	/** Is only enabled for the context menu of a rule.
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		final List<?> selectedObjects = getSelectedObjects();
		sRule2Folder.clear();
		initialRules.clear();
		transSystem = null;
		
		RuleFolderTreeEditPart sFolderTEP = null;
		List<Unit> sSubRules = null;
		
		RuleTreeEditPart sRuleTEP = null;
		TGGRule sRule = null;
		EditPart editpart = null;

		if (selectedObjects == null || selectedObjects.isEmpty()) {
			return false;
		}
		singleRule = (selectedObjects.size() == 1 &&  (selectedObjects.get(0) instanceof RuleTreeEditPart));
		if (singleRule && recursively) return false;
	
		setDescription((singleRule ? DESC_SINGLE : DESC));
		setText((singleRule ? DESC_SINGLE : DESC));
		setToolTipText((singleRule ? TOOLTIP_SINGLE : TOOLTIP));
		
		for (Object selectedObject : selectedObjects) {
			if (selectedObject == null || !(selectedObject instanceof EditPart)) {
				continue; // filter out non EditParts
			}
			editpart = (EditPart) selectedObject;
			
			if (transSystem == null) { // get TransformationSystem
				editpart = (EditPart) selectedObject;
				while (editpart != editpart.getRoot()
						&& editpart != null
						&& !(editpart instanceof TransformationSystemTreeEditPart)) {
					editpart = editpart.getParent();
				}
				if (editpart == null) return false;
				// editpart is the TransformationSystemTreeEditPart
				if (! (editpart.getModel() instanceof Module)) return false; // selection is not within the tree
				transSystem = (Module) editpart.getModel();
			}
			
			// case 1 : selected object is a rule
			if ((selectedObject instanceof RuleTreeEditPart)
					&& (((RuleTreeEditPart) selectedObject).getModel() instanceof TGGRule)) {
				sRuleTEP = (RuleTreeEditPart) selectedObject;
				sRule = (TGGRule) sRuleTEP.getModel();
				// check whether selected rule is a transformation rule
				if (!RuleUtil.TGG_RULE.equals(sRule.getMarkerType()))
					return false;
				sRule2Folder.put(sRule,
						(RuleFolderTreeEditPart) sRuleTEP.getParent());
				// case 2 : selected object is a folder
			} else if (selectedObject instanceof RuleFolderTreeEditPart) {
				sFolderTEP = (RuleFolderTreeEditPart) selectedObject;
				sSubRules = new LinkedList<Unit>();
				addAllSubRules(sFolderTEP, sSubRules);

				for (Unit subRule : sSubRules) {
					sRule = (TGGRule) subRule;
					if (!RuleUtil.TGG_RULE.equals(sRule.getMarkerType())) {
						return false;
					}
				}
			}
		}
		if(transSystem==null) return false;
		getInitialRules((IndependentUnit) transSystem.getUnit("RuleFolder"));
		ruleComparator = new ConcurrentRuleComparator(initialRules);
		return true;
	}
	

	
	private void getInitialRules(IndependentUnit folder){
		if (folder == null) return;
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				getInitialRules((IndependentUnit) unit);
			} else if (unit instanceof TGGRule && RuleUtil.TGG_RULE.equals(((TGGRule) unit).getMarkerType())){
				initialRules.add((TGGRule) unit);
			}
		}
	}
	
//	protected boolean setAllRules() {
//		allRules = new LinkedList<Rule> ();
//		List<Unit> units = transSystem.getUnits();
//		if (units == null || tRules == null) {
//			return false;
//		}
//		
//		Rule rule = null;
//		for (Unit unit : units) {
//			if (unit instanceof Rule) {
//				rule = (Rule) unit;
//				if (!isTRule(rule)) {
//					RuleUtil.setLhsCoordinatesAndLayout(rule);
//					allRules.add(rule);
//				}
//			}
//		}
//		return true;
//	}
	
	/**
	 * Given the RuleFolderTreeEditPart selectedFolderTEP of a root folder, this
	 * method fills selectedFolderSubRules with containing rules and subfolder
	 * rules.
	 * 
	 */
	protected void addAllSubRules(
			RuleFolderTreeEditPart selectedFolderTEP,
			List<Unit> selectedFolderSubRules) {
		TGGRule selectedRule = null;
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
					if (childRule instanceof TGGRule) {
						selectedRule = (TGGRule) childRule;
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
		if ((sRule2Folder == null) || (initialRules == null) ) {
			return;
		}
		calcInProgress = true;
		// concurrent rules created in previous round
		Map<TGGRule, RuleFolderTreeEditPart> prevConRule2Folder = new LinkedHashMap<TGGRule, RuleFolderTreeEditPart>();
		// concurrent rules created in current round
		Map<TGGRule, RuleFolderTreeEditPart> currConRule2Folder = new LinkedHashMap<TGGRule, RuleFolderTreeEditPart>();
		// newly generated concurrent rules during all the rounds
		Map<TGGRule, RuleFolderTreeEditPart> newConRule2Folder = new LinkedHashMap<TGGRule, RuleFolderTreeEditPart>();
		// temporarily created concurrent rule that potentially doesn't collide with existing rules
		List<TGGRule> candidateConRules = null;
		
		// temporarily created concurrent rules that don't collide with existing rules
		List<TGGRule> uniqueConRules = new LinkedList<TGGRule>();
		//indicates whether equivalent rule already exists
		boolean collision;
		//parent folder of new rule
		RuleFolderTreeEditPart parentFolder = null;
		// flag that defines the sequence order of the rules to be merged
		
		
		
		//temporary Rule for swap
		TGGRule tmpRule = null;
		
// ...
boolean firstRound = true;
boolean swapped = true;
for (TGGRule sRule : sRule2Folder.keySet()){ // copy selected rules
	currConRule2Folder.put(sRule, sRule2Folder.get(sRule));
}
while (!currConRule2Folder.isEmpty()) { // while new rules generated
	prevConRule2Folder = currConRule2Folder;
	currConRule2Folder = new HashMap<TGGRule, RuleFolderTreeEditPart>();
	for (TGGRule ruleL : sRule2Folder.keySet()) {
		for (TGGRule ruleR : prevConRule2Folder.keySet()) {
			do { // executed once/ twice in initial/ successive round(s)
				if (!singleRule && (ruleL == ruleR) ||
					recursively && !firstRound && 
						(ruleL.getName().contains(ruleR.getName()) 
								|| ruleR.getName().contains(ruleL.getName()))) {
					break;
				}
				if (ConcurrentRuleUtil.isConcurrent(ruleL, ruleR)) {
					candidateConRules = new ConcurrentRuleList(ruleL, ruleR);
					uniqueConRules.clear();
					for (TGGRule candidate : candidateConRules) { //check uniqueness
						collision = false;
						for (TGGRule rule : newConRule2Folder.keySet()){
							if (ConcurrentRuleUtil.equivalent(candidate, rule)) {
								collision = true; 
								break;
							}
						}
						if (collision) { break; }
						for (TGGRule rule : initialRules) {
							if (ConcurrentRuleUtil.equivalent(candidate, rule)) {
								collision = true;
								break;
							}
						}
						if (!collision) {
							uniqueConRules.add(candidate);
						}
					}
					parentFolder = (!swapped 
									? sRule2Folder.get(ruleL) 
									: prevConRule2Folder.get(ruleL));
					for (TGGRule uniqueConRule : uniqueConRules) { // update
						currConRule2Folder.put(uniqueConRule, parentFolder);
						newConRule2Folder.put(uniqueConRule, parentFolder);
					}
				}
				tmpRule = ruleL; // swap rules
				ruleL = ruleR;
				ruleR = tmpRule;
				swapped = !swapped; // flip  
			} while (swapped); // false after 1./ 2. loop in round 1/ >1
		}
	}
	if (!recursively) { break; }
	firstRound = false;
} // ...
		command = new CompoundCommand();
		//for (int index = 0; index < newConRule2Folder.keySet().size() - 1;  index++) {//;
		//	for (Entry<Rule, RuleFolderTreeEditPart> nCREntry : newConRule2Folder.entrySet()) {
		for (TGGRule newConRule : newConRule2Folder.keySet()) {
			//tmpRule = allRules.get(index);
			RuleFolderTreeEditPart folder = newConRule2Folder.get(newConRule);
			// if (EXPLICIT_INSERTION) insertConcurrentRule(cRule,
			// (IndependentUnit) folder.getModel()); else
			command.add(new InsertConcurrentRuleCommand(newConRule,
					(IndependentUnit) folder.getModel(), transSystem, ruleComparator));
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
	
	private RuleFolderTreeEditPart getCommonParentFolder(RuleFolderTreeEditPart ruleFolderEditPartLeft, RuleFolderTreeEditPart ruleFolderEditPartRight){
		if (ruleFolderEditPartLeft==null || ruleFolderEditPartRight==null) return null;
		EditPart editPartLeft = ruleFolderEditPartLeft;
		EditPart editPartRight = ruleFolderEditPartRight;
		RootEditPart root = editPartLeft.getRoot();
		EditPart top = root;
		boolean found = true;
		while (found && containsSubEditPart(top, editPartLeft) && containsSubEditPart(top, editPartRight)){
			found = false;
			for (Object child : top.getChildren()){
				if (child instanceof EditPart 
						&& containsSubEditPart((EditPart) child, editPartLeft) 
						&& containsSubEditPart((EditPart) child, editPartRight)){
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
	
	
	private boolean containsSubEditPart(EditPart top, EditPart toFind){
		if (top==toFind) return true;
		for (Object child : top.getChildren()){
			if (child instanceof EditPart){
				if (containsSubEditPart((EditPart) child, toFind)) return true;
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

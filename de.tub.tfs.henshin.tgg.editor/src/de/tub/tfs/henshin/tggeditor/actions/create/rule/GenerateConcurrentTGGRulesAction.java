package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.henshin.model.impl.RuleImpl;
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
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteFoldercommand;
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
public class GenerateConcurrentTGGRulesAction extends SelectionAction {
	
	public static boolean calcInProgress = false;
	
	//there are two insertion methods available that are chosen depending on this flag
	private final static boolean EXPLICIT_INSERTION = false;
	
	/** The Constant DESC for the description. */
	protected String DESC = "Generate Concurrent Rules";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Generates concurrent Rules from selected TGG Rules";
	

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateConcurrentRulesAction";
	

	/**
	 * commands which are used to execute the generation of a concurrent rules from selcted TGG rules
	 */
	
	protected CompoundCommand command;
	
	protected Module module;
	
	
	/**
	 * The rules from which the Concurrent Rules are generated.
	 */
	//protected Map<RuleFolderTreeEditPart, List<Unit>> folders2Rules;
	protected Map<Rule, RuleFolderTreeEditPart> rule2Folder;
	
	/**
	 * The layout System
	 */
	protected TGG layoutSystem;
	
	protected Module transSys;

	//protected IndependentUnit ruleFolder;
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateConcurrentTGGRulesAction(IWorkbenchPart part) {
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
		if (selectedObjects == null || selectedObjects.isEmpty()) return false;
		
		//folders2Rules = new HashMap<RuleFolderTreeEditPart, List<Unit>>();
		rule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
		
		EList<TRule> tRules = null;
		for (Object selectedObject : selectedObjects) {
			
			if (selectedObject==null || !(selectedObject instanceof EditPart)) continue;
			
			if (transSys==null){
				EditPart editpart = (EditPart) selectedObject;
				while (editpart != editpart.getRoot() && !(editpart instanceof TransformationSystemTreeEditPart)){
					editpart = editpart.getParent();
				}
				transSys = (Module) editpart.getModel();
			}
			
			//case: selected object is a folder
			if (selectedObject instanceof RuleFolderTreeEditPart) {
				RuleFolderTreeEditPart ruleFolderTreeEditPart = (RuleFolderTreeEditPart) selectedObject;
				List<Unit> subRules = new LinkedList<Unit>(); 
				addAllSubFolderRules(ruleFolderTreeEditPart, subRules);
				if ((!subRules.isEmpty() || !rule2Folder.isEmpty()) && tRules==null){//!folders2Rules.isEmpty()) && tRules==null){
					Unit rule = null;
					//if (!folders2Rules.isEmpty())
					if (!rule2Folder.isEmpty())
					//rule = ((List<Unit>)(folders2Rules.values().toArray()[0])).get(0);
						rule = (Unit) (rule2Folder.keySet().toArray()[0]);
					if (rule==null)
					rule = (Unit)subRules.get(0);
					layoutSystem = GraphicalNodeUtil.getLayoutSystem(rule);
					if (layoutSystem == null) continue;
					tRules = layoutSystem.getTRules();
				}
				for(TRule temp: tRules){
					for (Unit subRule : subRules){
						if (module==null) module = subRule.getModule();
						if (temp.getRule().equals((Rule)subRule)) return false;
					}
				}
				continue;
			}
			
			//o is of type RuleTreeEditPart, now remove all transformation rules
			if ((selectedObject instanceof RuleTreeEditPart) && (((RuleTreeEditPart)selectedObject).getModel() instanceof Rule)){
				if (tRules == null){
					layoutSystem = GraphicalNodeUtil.getLayoutSystem((Rule)((RuleTreeEditPart)selectedObject).getModel());
					if (layoutSystem == null) return false;
					tRules = layoutSystem.getTRules();
				}
				if (module ==null) module = ((Rule)((RuleTreeEditPart)selectedObject).getModel()).getModule();
				
				for (TRule temp: tRules){
					if (temp.getRule().equals((Rule)((RuleTreeEditPart)selectedObject).getModel())) return false;
				}
				if (((RuleTreeEditPart)selectedObject).getModel() instanceof Rule)
				rule2Folder.put((Rule)((RuleTreeEditPart)selectedObject).getModel(), 
						(RuleFolderTreeEditPart)((RuleTreeEditPart)selectedObject).getParent());
				/**
				List<Unit> rls = folders2Rules.get((RuleFolderTreeEditPart)((RuleTreeEditPart)selectedObject).getParent());
				if (rls == null){
					rls = new LinkedList<Unit>();
					rls.add((Unit)((RuleTreeEditPart)selectedObject).getModel());
					folders2Rules.put((RuleFolderTreeEditPart)((RuleTreeEditPart)selectedObject).getParent(), rls);
				}else{
					rls.add((Unit)((RuleTreeEditPart)selectedObject).getModel());
				}**/
			}
		}
		/**
		int count = 0;
		for (RuleFolderTreeEditPart ep : folders2Rules.keySet()){
			List<Unit> units = folders2Rules.get(ep);
			count+=units.size();
			if (count>1)return true;
		}
		return false;**/
		return rule2Folder.size()>1;
	}
	
	//given a root folder, this method fills rules with (sub)folder(s) and their corresponding list of rules 
	protected void addAllSubFolderRules(RuleFolderTreeEditPart ruleFolder, List<Unit> subRules){
		/**
		if (folders2Rules ==null) return;
		for (Object child : ruleFolder.getChildren()){
			if (child instanceof EditPart){
				if (child instanceof RuleFolderTreeEditPart){
					addSubFolders2Rules((RuleFolderTreeEditPart) child, subRules);
				}else if (child instanceof RuleTreeEditPart){
					Object childRule = ((RuleTreeEditPart)child).getModel();
					if (childRule instanceof Unit){
						subRules.add((Unit)childRule);
						List<Unit> childRules = folders2Rules.get(ruleFolder);
						if (childRules==null){
							childRules = new LinkedList<Unit>();
							childRules.add((Unit) childRule);
							folders2Rules.put(ruleFolder, childRules);
						}else{
							childRules.add((Unit)childRule);
						}
					}
				}
			}
		}**/
		if (rule2Folder ==null) return;
		for (Object child : ruleFolder.getChildren()){
			if (child instanceof EditPart){
				if (child instanceof RuleFolderTreeEditPart){
					addAllSubFolderRules((RuleFolderTreeEditPart) child, subRules);
				}else if (child instanceof RuleTreeEditPart){
					Object childRule = ((RuleTreeEditPart)child).getModel();
					if (childRule instanceof Rule){
						subRules.add((Rule)childRule);
						rule2Folder.put((Rule)childRule, ruleFolder);
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
		//if (folders2Rules == null) return;
		if (rule2Folder == null) return;
		calcInProgress = true;
		/**
		Map<IndependentUnit, List<Rule>> folder2ConcurrentRules = new HashMap<IndependentUnit, List<Rule>>();
		for (RuleFolderTreeEditPart ruleFolderEditPartLeft : folders2Rules.keySet()){
			List<Unit> rulesLeft = folders2Rules.get(ruleFolderEditPartLeft);
			for (Unit ruleLeft : rulesLeft){
				if (!(ruleLeft instanceof Rule)) continue;
				for (RuleFolderTreeEditPart ruleFolderEditPartRight : folders2Rules.keySet()){
					List<Unit> rulesRight = folders2Rules.get(ruleFolderEditPartRight);
					for (Unit ruleRight : rulesRight){
						if (!(ruleRight instanceof Rule) || ruleLeft==ruleRight) continue;
						if (ConcurrentRuleList.isConcurrent((Rule)ruleLeft, (Rule)ruleRight)){
							List<Rule> concurrentRules = new ConcurrentRuleList((Rule)ruleLeft, (Rule)ruleRight);
							IndependentUnit topFolder = getDeepestParentFolder(ruleFolderEditPartLeft, ruleFolderEditPartRight);
							List<Rule> cRules = folder2ConcurrentRules.get(topFolder);
							if (cRules==null){
								folder2ConcurrentRules.put(topFolder, concurrentRules);
							}else{
								cRules.addAll(concurrentRules);
							}
						}
					}
				}
			}
		}
		
		command = new CompoundCommand();
		//generate the concurrent rules
		for (IndependentUnit folder : folder2ConcurrentRules.keySet()){
			List<Rule> concurrentRules = folder2ConcurrentRules.get(folder);
			for (Rule concurrentRule : concurrentRules){
				if (EXPLICIT_INSERTION)
				insertConcurrentRule(concurrentRule, folder);
				else
				command.add(new InsertConcurrentRuleCommand(concurrentRule, folder, module));
			}
		}
		if (!EXPLICIT_INSERTION)
		command.execute();
		*/
		
		Map<Rule, RuleFolderTreeEditPart> cRule2Folder = new HashMap<Rule, RuleFolderTreeEditPart>();
		for (Rule ruleL : rule2Folder.keySet()){
			for (Rule ruleR : rule2Folder.keySet()){
				if (ruleL==ruleR) continue;
				if (ConcurrentRuleUtil.isConcurrent(ruleL, ruleR)){
					List<Rule> concurrentRules = new ConcurrentRuleList(ruleL, ruleR);
					RuleFolderTreeEditPart topFolder = getDeepestParentFolder(rule2Folder.get(ruleL), rule2Folder.get(ruleR));
					for (Rule rule : concurrentRules){
						cRule2Folder.put(rule, topFolder);
					}
				}
			}
		}
		
		List<Rule> nCRules = new LinkedList<Rule>();
		boolean collision = false;
		RuleFolderTreeEditPart topFolder=null;
		
		while (nCRules!=null){
			nCRules=null;
			topFolder=null;
			for (Rule cRule : cRule2Folder.keySet()){
				collision = true;
				for (Rule rule :  rule2Folder.keySet()){
					collision = true;
					if (ConcurrentRuleUtil.isConcurrent(cRule, rule) && !ConcurrentRuleUtil.intersectingAtomicNames(cRule, rule)){
						collision =false;
						for (Rule cRule2 : cRule2Folder.keySet()){
							if (ConcurrentRuleUtil.concatOfRuleNamesEquivalentToConcurrentRuleName(cRule, rule, cRule2)){
								collision = true;
								break;
							}
						}
						if (!collision){
							nCRules = new ConcurrentRuleList(cRule, rule);
							if (nCRules.isEmpty()){
								nCRules = null;
								collision=true;
							}
							topFolder = getDeepestParentFolder(rule2Folder.get(rule), cRule2Folder.get(cRule));
						}
					}
					if (!collision) break;
				 }
				if (!collision)break;
			}
			
			if (nCRules==null){
				for (Rule cRule : cRule2Folder.keySet()){
					collision = true;
					for (Rule rule :  rule2Folder.keySet()){
						collision = true;
						if (ConcurrentRuleUtil.isConcurrent(rule, cRule) && !ConcurrentRuleUtil.intersectingAtomicNames(rule, cRule)){
							collision =false;
							for (Rule cRule2 : cRule2Folder.keySet()){
								if (ConcurrentRuleUtil.concatOfRuleNamesEquivalentToConcurrentRuleName(rule, cRule, cRule2)){
									collision = true;
									break;
								}
							}
							if (!collision){
								nCRules = new ConcurrentRuleList(rule, cRule);
								if (nCRules.isEmpty()){
									nCRules = null;
									collision=true;
								}
								topFolder = getDeepestParentFolder(rule2Folder.get(rule), cRule2Folder.get(cRule));
							}
						}
						if (!collision) break;
					 }
					if (!collision)break;
				}
			}
			
			if (nCRules!=null){
				for (Rule nCRule : nCRules){
					cRule2Folder.put(nCRule, topFolder);
				}
			}
		}
		
		command = new CompoundCommand();
		//generate the concurrent rules
		for (Rule cRule : cRule2Folder.keySet()){
			RuleFolderTreeEditPart folder = cRule2Folder.get(cRule);
			if (EXPLICIT_INSERTION)
				insertConcurrentRule(cRule, (IndependentUnit) folder.getModel());
			else
				command.add(new InsertConcurrentRuleCommand(cRule, (IndependentUnit) folder.getModel(), module, folder));
		}
		if (!EXPLICIT_INSERTION) command.execute();
		calcInProgress = false;
	}
	
	
	protected RuleFolderTreeEditPart getDeepestParentFolder(RuleFolderTreeEditPart ruleFolderEditPartLeft, RuleFolderTreeEditPart ruleFolderEditPartRight){
		if (ruleFolderEditPartLeft==null || ruleFolderEditPartRight==null) return null;
		EditPart editPartLeft = ruleFolderEditPartLeft;
		EditPart editPartRight = ruleFolderEditPartRight;
		RootEditPart root =  editPartLeft.getRoot();
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
		if (!(top.getModel() instanceof IndependentUnit)) return null;
		//return (IndependentUnit) top.getModel();
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
	
	//constructs and inserts the given rule into the folder 
	private void insertConcurrentRule(Rule rule, IndependentUnit folder){
		Rule newRule = executeCreateEmptyRule(rule, folder);
		Graph2GraphCopyMappingList graphR2CopyWithoutEdges = new Graph2GraphCopyMappingList(rule.getRhs());
		HashMap<Node, Node> mappingGraphR2CopyWithoutEdges = new HashMap<Node, Node>();
		Graph graphWithoutEdges = graphR2CopyWithoutEdges.getGraphCopy();
		for (Node nodeR : rule.getRhs().getNodes()){
			TNode tNodeR = (TNode)nodeR;
			TNode nodeWithoutEdges = (TNode)graphR2CopyWithoutEdges.getImage(tNodeR);
			nodeWithoutEdges.getAttributes().clear();
			nodeWithoutEdges.setMarkerType(null);
			mappingGraphR2CopyWithoutEdges.put(tNodeR, nodeWithoutEdges);
			while(graphWithoutEdges.getEdges().size()!=0){
				graphWithoutEdges.removeEdge(graphWithoutEdges.getEdges().get(0));
			}
			
			Point p = new Point();
			p.x = tNodeR.getX();
			p.y = tNodeR.getY();
			TripleComponent tc = GraphicalNodeUtil.guessTC(tNodeR);
			//TNode newNode = 
			this.executeCreateNode(nodeWithoutEdges, p, tc);
						
			if (nodeWithoutEdges.getMarkerType()!=null){
				this.executeMarkNode(nodeWithoutEdges);
			}
			
			do{
				this.executeMarkNode(nodeWithoutEdges);	
			}while(nodeWithoutEdges.getMarkerType()!=tNodeR.getMarkerType());
			
			
			for (Attribute oldAttr : tNodeR.getAttributes()){
				TAttribute newAttr = this.executeCreateAttribute(oldAttr);
				if (((TAttribute)oldAttr).getMarkerType()!=((TAttribute)newAttr).getMarkerType()){
					this.executeMarkAttribute(newAttr);
				}
			}
		}
		
		for (int i = 0; i < rule.getRhs().getEdges().size(); i++) {
			TEdge oldEdge = (TEdge) rule.getRhs().getEdges().get(i);
			TEdge newEdge = executeCreateEdge(
					mappingGraphR2CopyWithoutEdges.get(oldEdge.getSource()),
					mappingGraphR2CopyWithoutEdges.get(oldEdge.getTarget()),
					oldEdge.getType());

			// mark, if it was marked before
			if (oldEdge.getMarkerType() != newEdge.getMarkerType())
				executeMarkEdge(newEdge);
		}
		
		Iterator<Parameter> paramIterator = rule.getParameters().iterator();
		while (paramIterator.hasNext()){
			Parameter param = paramIterator.next();
			paramIterator.remove();
			param.setUnit(newRule);
			this.executeCreateParameter(param);
		}
	}
	
	/**
	 * command for empty rule creation
	 */
	private CreateRuleCommand ruleCommand;
	/**
	 * command for attributeConditionCreation
	 */
	private CreateAttributeConditionCommand attributeConditionCommand;
	
	private CreateNACCommand nacCommand;
	private CreateParameterCommand parameterCommand;
	private CreateParameterMappingCommand parameterMappingCommand;
	
	private CreateRuleAttributeCommand attributeCommand;
	private CreateRuleNodeCommand nodeCommand;
	private CreateRuleEdgeCommand edgeCommand;
	
	private MarkAttributeCommand markAttributeCommand;
	private MarkEdgeCommand markEdgeCommand;
	private MarkCommand markNodeCommand;
	
	//this method is currently not in use because concurrent rules should not be deleted 
	//if other concurrent rules are created (otherwise one could not create concurrent rules one by one)
	//in case this featzure is required it is available in the following method
	private void cleanUpOldContainer(IndependentUnit ruleFolder, CompoundCommand cmd) {
		if (ruleFolder == null) return;
		for (Unit unit : ruleFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				//cleanUpOldContainer((IndependentUnit) unit,cmd);
			} else if (unit instanceof Rule){
				Rule rule = (Rule) unit;
				//only delete concurrent rules from folder
				if (rule.getName().contains("Concurrent")){
					cmd.add(new DeleteOpRuleCommand(rule, ""));
				}
			}
		}
	}
	
	private void executeMarkNode(Node node){
		markNodeCommand = new MarkCommand(node);
		execute(markNodeCommand);
	}
	
	private Rule executeCreateEmptyRule(Rule rule, IndependentUnit folder){
		ruleCommand = new CreateRuleCommand(transSys, rule.getName(), folder);
		execute(ruleCommand);
		((TripleGraph)ruleCommand.getRhsGraph()).setDividerCT_X(((TripleGraph)rule.getRhs()).getDividerCT_X());
		((TripleGraph)ruleCommand.getRhsGraph()).setDividerSC_X(((TripleGraph)rule.getRhs()).getDividerSC_X());
		return ruleCommand.getRule();
	}
	
	private TNode executeCreateNode(TNode nodeWithoutEdges, Point p, TripleComponent tc){
		ruleCommand.getRhsGraph().getNodes().add(nodeWithoutEdges);
		nodeWithoutEdges.setGraph(ruleCommand.getRhsGraph());
		nodeCommand = new CreateRuleNodeCommand(nodeWithoutEdges, ruleCommand.getRhsGraph(), p, tc);
		execute(nodeCommand);
		return ((TNode)nodeCommand.getNode());
	}
	
	private TEdge executeCreateEdge(Node source, Node target, EReference type){
		edgeCommand = new CreateRuleEdgeCommand(ruleCommand.getRhsGraph(), source, target, type);
		execute(edgeCommand);
		return (TEdge)edgeCommand.getEdge();
	}
	
	private void executeMarkEdge(Edge edge){
		markEdgeCommand = new MarkEdgeCommand(edge);
		execute(markEdgeCommand);
	}
	
	private TAttribute executeCreateAttribute(Attribute attribute){
		attributeCommand = new CreateRuleAttributeCommand(nodeCommand.getNode(), attribute.getType().getName());
		attributeCommand.setAttributeType(attribute.getType());
		attributeCommand.setValue(attribute.getValue());
		attributeCommand.execute();
		return (TAttribute)attributeCommand.getAttribute();
	}
	
	private void executeMarkAttribute(Attribute attribute){
		markAttributeCommand = new MarkAttributeCommand(attribute);//attributeCommand.getAttribute());
		execute(markAttributeCommand);
	}
	
	private void executeCreateParameter(Parameter parameter){
		this.parameterCommand = new CreateParameterCommand(parameter.getUnit(), parameter);//attributeCommand.getAttribute());
		execute(parameterCommand);
	}

}

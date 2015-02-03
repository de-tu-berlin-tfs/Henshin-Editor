/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteFoldercommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;


/**
 * The class GenerateOpRuleAction generates the operational rule from a TGG rule. The action
 * is registered in the Contextmenu of the tree editor.
 * @see GenerateOpRuleToolBarAction
 * @see ProcessRuleCommand
 */
public abstract class GenerateOpRulesAction extends SelectionAction {
	public static boolean calcInProgress = false;
	
	
	/** The Constant DESC for the description. */
	protected String DESC = "Generate All Op_Rules";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Generates operational rules for all TGG Rules";

	/**
	 * The command which is used to execute the generation of a Transformation-rule from a rule
	 */
	protected GenerateOpRuleCommand command;
	
	/**
	 * The rules which are used as base for the Transformation-Rules.
	 */
	private List<Unit> rules;
	
	/**
	 * The layout System
	 */
	private TGG layoutSystem;

	protected IndependentUnit ruleFolder;
	protected String opRuleTypeUpperCase = "";
	protected String opRuleType = "";
	
	
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateOpRulesAction(IWorkbenchPart part) {
		super(part);
	}

	
	protected void getAllUnits(List<Unit> units,IndependentUnit folder){
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				units.add( unit);
				getAllUnits(units, (IndependentUnit) unit);
			} else {
				units.add( unit);
			}
		}
	}
	
	
	/** Is only enabled for the context menu of a rule.
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart instanceof RuleFolderTreeEditPart) {

				ruleFolder = (IndependentUnit) editpart.getModel();
				rules = new LinkedList<Unit>();
				getAllUnits(rules, ruleFolder);
				if (!rules.isEmpty()) {
					if (!calcInProgress) {
						  for (Unit unit : rules) {
	                            if (unit == null) 
	                            	return false;
	                            if (unit instanceof TGGRule &&
	                            		!RuleUtil.TGG_RULE.equals(((TGGRule) unit)
	                                    .getMarkerType()))
	                            	return false;
	                        }
						  return true;
					}
				}
			}
		}
		return false;
	}
	
	//NEW comment changed: generalized from FT to OP
	/** 
	 * Executes the GenerateOPRuleCommand.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rules != null) {

			CompoundCommand cmd = new CompoundCommand();
			calcInProgress = true;
			
			IndependentUnit folder = getOpRuleFolder();
			// delete current operational rules
			cleanUpOldContainer(folder, cmd);
			
			cmd.execute();

			// generate the new operational rules
			
			Iterator<Unit> iterator = rules.iterator();
			Unit unit=null;
			Rule rule=null;
			for (int idx = 0 ; idx < rules.size();idx++){
				unit = iterator.next();
				if (unit instanceof IndependentUnit){
					continue;
				}
				if (unit instanceof Rule)
					rule = (Rule) unit;
				if (idx % 10 == 0)
					System.out.println("generate " + opRuleTypeUpperCase + "-Rule #" + idx + "-" + Math.min(idx + 10,rules.size()) + " of " + rules.size());
				
				IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder")  ,rule);

				setCommand(rule, container);
				
				super.execute(command);
			}
			calcInProgress = false;
		}
	}
	
	protected abstract void setCommand(Rule rule, IndependentUnit container);

	
	protected IndependentUnit getOpRuleFolder() {
		IndependentUnit folder = (IndependentUnit) ((Module) EcoreUtil
				.getRootContainer(ruleFolder)).getUnit(opRuleTypeUpperCase
				+ "_" + ruleFolder.getName());
		if (folder == null && ruleFolder.getName().equals("RuleFolder"))
			folder = (IndependentUnit) ((Module) EcoreUtil
					.getRootContainer(ruleFolder)).getUnit(opRuleTypeUpperCase
					+ "RuleFolder");
		return folder;
	}

	protected void cleanUpOldContainer(IndependentUnit opRuleFolder,CompoundCommand cmd) {
		if (opRuleFolder == null)
			return;
		for (Unit unit : opRuleFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				cleanUpOldContainer((IndependentUnit) unit,cmd);
				cmd.add(new DeleteFoldercommand((IndependentUnit) unit));
			} else if (unit instanceof Rule)
				cmd.add(new DeleteOpRuleCommand((Rule) unit, opRuleType));
		}
	}
	
	
	protected IndependentUnit findContainer(IndependentUnit opRuleFolder, Object obj) {
		for (Unit unit : opRuleFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				IndependentUnit u = findContainer((IndependentUnit) unit, obj);
				if (u != null)
					return u;
			} else if (unit.equals(obj))
				return opRuleFolder;
		}
		return null;
	}
	
}

package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import com.sun.swing.internal.plaf.metal.resources.metal;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteFoldercommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


/**
 * The class GenerateFTRuleAction generates Forward-Translation-Rule from a simple Rule. The Action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateFTRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateBTRulesAction extends SelectionAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRulesAction";
	
	/** The Constant DESC for the description. */
	static private final String DESC = "Generate All BT_Rules";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Generates Backward Translation Rules for all TGG Rules";
	
	/**
	 * The rule which is used as base for the FT-Rule.
	 */
	private List<Unit> rules;
	
	/**
	 * The layout System
	 */
	private TGG layoutSystem;

	private IndependentUnit ruleFolder;
	
	
	
	/**
	 * the constructor
	 * @param part
	 */
	public GenerateBTRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
	}

	
	private void getAllUnits(List<Unit> units,IndependentUnit folder){
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
				
				getAllUnits(rules,ruleFolder);
				
				if (!rules.isEmpty()) {
					layoutSystem = NodeUtil.getLayoutSystem(rules.get(0));
	
					if(layoutSystem == null) return false;
					EList<TRule> tRules = layoutSystem.getTRules();
					if (!GenerateFTRulesAction.calcInProgress)
					for(TRule temp: tRules) {
						for (Unit rule : rules) {
							if(temp.getRule().equals(rule)) return false;
						}
					}
				} else { return false; }
				return true;
			}
		}
		return false;
	}
	
	/** 
	 * Executes the GenerateFTRuleCommand.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rules != null) {

			// delete current FT rules
			CompoundCommand cmd = new CompoundCommand();
			//for (TRule tr : layoutSystem.getTRules()) {
			//		cmd.add(new DeleteFTRuleCommand(tr.getRule(),findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(tr.getRule())).getUnit("FTRuleFolder")  ,tr.getRule())));
			//}
			GenerateFTRulesAction.calcInProgress = true;
			
			IndependentUnit folder = (IndependentUnit) ((Module)EcoreUtil.getRootContainer(ruleFolder)).getUnit("BT_" + ruleFolder.getName());
			if (folder == null && ruleFolder.getName().equals("RuleFolder")) 
				folder = (IndependentUnit) ((Module)EcoreUtil.getRootContainer(ruleFolder)).getUnit("BTRuleFolder");
			cleanUpOldContainer(folder, cmd);
			
			cmd.execute();
						// generate the new FT rules
			for (Unit rule : rules) {
				if (rule instanceof IndependentUnit){
					continue;
				}
				IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder")  ,rule);

				ProcessRuleCommand command = new GenerateBTRuleCommand((Rule)rule,container);		
				
				super.execute(command);
			}
			
			GenerateFTRulesAction.calcInProgress = false;
		}
	}
	
	private void cleanUpOldContainer(IndependentUnit ftFolder,CompoundCommand cmd) {
		if (ftFolder == null)
			return;
		for (Unit unit : ftFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				
				cleanUpOldContainer((IndependentUnit) unit,cmd);
				
				cmd.add(new DeleteFoldercommand((IndependentUnit) unit));
			} else if (unit instanceof Rule)
				cmd.add(new DeleteBTRuleCommand((Rule) unit));
		}
	}
	
	private IndependentUnit findContainer(IndependentUnit ftFolder, Object obj) {
		for (Unit unit : ftFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				IndependentUnit u = findContainer((IndependentUnit) unit, obj);
				if (u != null)
					return u;
			} else if (unit.equals(obj))
				return ftFolder;
		}

		return null;
		
		
	}
	
}

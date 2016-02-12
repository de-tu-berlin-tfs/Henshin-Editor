/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.MultiUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateBTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateCCRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateITRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.ProcessRuleCommand;


/**
 * The class GenerateFTRuleAction generates the forward-translation-rule from a TGG rule. The action
 * is registered in the Contextmenu of the Tree Editor.
 * @see GenerateAllOpRuleToolBarAction
 * @see ProcessRuleCommand
 */
public class GenerateAllOpRulesAction extends GenerateOpRulesAction {

	/**
	 * The fully qualified ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.GenerateAllOpRuleAction";
	
	protected ArrayList<GenerateOpRuleCommand> commandList;

	/**
	 * the constructor
	 * @param part
	 */
	public GenerateAllOpRulesAction(IWorkbenchPart part) {
		super(part);
		DESC 	= "Generate all FT, BT, IT, CC Rules";
		TOOLTIP = "Generates all operational translation rules for all TGG rules";
		setId(ID);
		setText(DESC);
		setToolTipText(TOOLTIP);
		
		commandList = new ArrayList<GenerateOpRuleCommand>();
	}


	@Override
	protected void setCommand(Rule rule, MultiUnit container) {
		commandList.add(new GenerateFTRuleCommand(rule,container));
		commandList.add(new GenerateBTRuleCommand(rule,container));
		commandList.add(new GenerateITRuleCommand(rule,container));
		commandList.add(new GenerateCCRuleCommand(rule,container));
	}

	/** 
	 * Creates and executes all Generate..RuleCommands.
	 * @see GenerateAllOpRulesAction
	 * @see org.eclipse.jface.action.Action#run()
	 */
/*	@Override
	public void run() {
		IndependentUnit ruleFolder = getOpRuleFolder();
		IndependentUnit ruleFolder2 = null;
		try {
			// If we have chosen the generate all op rules action for all rules from a sub folder, try to get this sub folder.
			// If successful use this sub folder, otherwise use RuleFolder.
			ruleFolder2 = (IndependentUnit) ((Module)EcoreUtil.getRootContainer(ruleFolder)).getUnit(this.getSelection().toString());
		} catch(Exception e) {
			System.out.println(e + "\n\n" + "Folder " + this.getSelection().toString() + " is not available. -> Continue with RuleFolder.");
		}
		if(ruleFolder2 != null) {
			ruleFolder = ruleFolder2;
		}
		
		for (Unit unit : ruleFolder.getSubUnits()) {
			if(unit instanceof Rule) {
				//IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder"), rule);
				setCommand((Rule) unit, ruleFolder);
			}
		}
		
		// For each command, set opRuleType variables
		for (GenerateOpRuleCommand c : commandList) {
			
			if(c instanceof GenerateFTRuleCommand) {
				opRuleTypeUpperCase = "FT";
				opRuleType=RuleUtil.TGG_FT_RULE;
			}
			if(c instanceof GenerateBTRuleCommand) {
				opRuleTypeUpperCase = "BT";
				opRuleType=RuleUtil.TGG_BT_RULE;
			}
			if(c instanceof GenerateITRuleCommand) {
				opRuleTypeUpperCase = "IT";
				opRuleType=RuleUtil.TGG_IT_RULE;
			}
			if(c instanceof GenerateCCRuleCommand) {
				opRuleTypeUpperCase = "CC";
				opRuleType=RuleUtil.TGG_CC_RULE;
			}
				
			super.execute(c);
		}
	}*/
	
	protected void cleanUpOldContainers() {
		EList<Unit> folders = ((Module) EcoreUtil.getRootContainer(ruleFolder)).getUnits();

		// We need a list of four CompoundCommands -> for cleaning BT, FT, IT and CC folders.
		for(Unit folder : folders) {
			// Do not clean our main RuleFolder!
			if(folder.getName().equals("RuleFolder"))
				continue;
			// We can't work with other objects except IndependenUnit
			if( !(folder instanceof IndependentUnit) )
					continue;
			// Just clean up folders starting with FT, BT, IT or CC
			if (!( (folder.getName().startsWith("FT")) ||
			       (folder.getName().startsWith("BT")) ||
			       (folder.getName().startsWith("IT")) ||
			       (folder.getName().startsWith("CC")) ) )
				continue;
			
			System.out.println("Clean folder " + folder.getName());
			
			// Clean all other folders
			CompoundCommand cmd = new CompoundCommand();
			// delete current operational rules
			cleanUpOldContainer((IndependentUnit) folder, cmd);
			
			cmd.execute();
		}
	}
	
	/** 
	 * Executes the GenerateOPRuleCommand.
	 * @see ProcessRuleCommand
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		// Iterate through all rules in RuleFolder and create the corresponding GenerateOpRuleCommand
		List<Unit> rules = new LinkedList<Unit>();
		getAllUnits(rules, ruleFolder);
		
		if (rules != null) {
			calcInProgress = true;

			// clean up all old folders
			cleanUpOldContainers();
			
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
				// Just an output for informing the user of the current generation status
				if (idx % 10 == 0)
					System.out.println("generate all operational rules of rule #" + idx + "-" + Math.min(idx + 10,rules.size()) + " of " + rules.size());
				
				//IndependentUnit container = findContainer((IndependentUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder"), rule);
				MultiUnit container = findContainer((MultiUnit) ((Module)EcoreUtil.getRootContainer(rule)).getUnit("RuleFolder"), rule);

				// Here: set command list!
				setCommand(rule, container);
				
				// For each command, set opRuleType variables
				for (GenerateOpRuleCommand c : commandList) {
					
					if(c instanceof GenerateFTRuleCommand) {
						opRuleTypeUpperCase = "FT";
						opRuleType=RuleUtil.TGG_FT_RULE;
					}
					if(c instanceof GenerateBTRuleCommand) {
						opRuleTypeUpperCase = "BT";
						opRuleType=RuleUtil.TGG_BT_RULE;
					}
					if(c instanceof GenerateITRuleCommand) {
						opRuleTypeUpperCase = "IT";
						opRuleType=RuleUtil.TGG_IT_RULE;
					}
					if(c instanceof GenerateCCRuleCommand) {
						opRuleTypeUpperCase = "CC";
						opRuleType=RuleUtil.TGG_CC_RULE;
					}
						
					super.execute(c);
				}
			}
			calcInProgress = false;
		}
	}
		

}

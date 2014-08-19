package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.ConcurrentRuleUtil;

//NEW GERARD
public class InsertConcurrentRuleCommand extends Command{

	protected IndependentUnit unit;
	protected Rule rule;
	protected Module module;
	protected RuleFolderTreeEditPart folderTEP;
	
	public InsertConcurrentRuleCommand(Rule rule, IndependentUnit folderUnit, Module module, RuleFolderTreeEditPart folder) {
		super();
		this.folderTEP = folder;
		this.unit = folderUnit;
		this.rule = rule;
		this.module = module;
	}
	

	@Override
	public void execute() {
		// set rule marker to indicate that the new concurrent rule becomes a rule of the TGG
		((TGGRule) rule).setMarkerType(RuleUtil.TGG_RULE);
		if (unit != null){
			EList<Unit> subUnits = unit.getSubUnits();
			int index = unit.getSubUnits().size()-1;
			Rule oldRule;
			for (Unit sunit :  subUnits){
				if (sunit instanceof Rule){
					oldRule = ((Rule)sunit);
					if (ConcurrentRuleUtil.strictGreater(rule, oldRule)){
						index = subUnits.indexOf(oldRule);
						break;
					}
				}
			}
			System.out.println("Add rule "+rule.getName()+" index "+index);
			unit.getSubUnits().add(index, rule);
		}
		
		EList<Unit> units = module.getUnits();
		int index = 0;
		for (Unit unit :  module.getUnits()){
			if (unit instanceof Rule){
				Rule oldRule = ((Rule)unit);
				if (ConcurrentRuleUtil.strictGreater(rule, oldRule)){
					index = units.indexOf(oldRule);
					break;
				}
			}
		}
		module.getUnits().add(index, rule);
	}

}

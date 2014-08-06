package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteFTRuleCommand deletes a forward translation rule.
 *
 */
public class DeleteOpRuleCommand extends CompoundCommand {

	private IndependentUnit cont = null;
	private Rule rule;

	/**
	 * Constructor
	 * @param Rule r
	 */
	public DeleteOpRuleCommand(Rule r, String ruleType){
		if (r.eContainer() == null)
			return;
		if (getTRule(r,ruleType) != null)
			add(new SimpleDeleteEObjectCommand(getTRule(r,ruleType)));
		add(new DeleteRuleCommand(r));
		this.rule = r;
	}

	public DeleteOpRuleCommand(Rule r,IndependentUnit container,String ruleType){
		if (r.eContainer() == null)
			return;
		add(new SimpleDeleteEObjectCommand(getTRule(r,ruleType)));
		add(new DeleteRuleCommand(r));
		this.cont = container;
	}

	/**
	 * Returns the FT rule which is derived from the rule
	 * @param rule
	 * @return TRUle
	 */
	private TRule getTRule(Rule rule, String ruleType){
		
		
		TGG tgg  = GraphicalNodeUtil.getLayoutSystem(rule);
		List<TRule> tRules = tgg.getTRules();
		for(TRule tr: tRules){
			if(tr.getRule() == rule && tr.getType().equals(ruleType))
				return tr;
		}
		return null;
	}

	
	
	@Override
	public void execute() {
		super.execute();
		if (cont != null)
			cont.getSubUnits().remove(rule);
	}
}

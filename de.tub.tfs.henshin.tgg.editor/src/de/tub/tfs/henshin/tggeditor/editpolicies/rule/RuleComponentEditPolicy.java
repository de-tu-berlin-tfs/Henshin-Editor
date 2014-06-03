package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;


public class RuleComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		// FIXME: generalise to operational rules other than FT rule
		if(ModelUtil.isFTRule((Rule) getHost().getModel())){
			return new DeleteOpRuleCommand((Rule) getHost().getModel(), RuleUtil.TGG_FT_RULE);
		}
		return new DeleteRuleCommand((Rule) getHost().getModel());
	}
	

}

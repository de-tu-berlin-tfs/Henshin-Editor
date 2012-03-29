package tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import tggeditor.commands.delete.rule.DeleteRuleCommand;
import tggeditor.util.ModelUtil;

public class RuleComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if(ModelUtil.isFTRule((Rule) getHost().getModel())){
			return new DeleteFTRuleCommand((Rule) getHost().getModel());
		}
		return new DeleteRuleCommand((Rule) getHost().getModel());
	}
	

}

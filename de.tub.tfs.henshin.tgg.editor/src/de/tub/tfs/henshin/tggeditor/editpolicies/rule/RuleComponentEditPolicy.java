package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;


public class RuleComponentEditPolicy extends ComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if(ModelUtil.isFTRule((Rule) getHost().getModel())){
			return new DeleteFTRuleCommand((Rule) getHost().getModel());
		}
		return new DeleteRuleCommand((Rule) getHost().getModel());
	}
	

}

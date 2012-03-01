/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.rule.DeleteRuleCommand;

/**
 * The Class RuleComponentEditPolicy.
 * 
 * @author Johann
 */
public class RuleComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		final Rule rule = (Rule) getHost().getModel();

		return new DeleteRuleCommand(rule, rule.eContainer());
	}

}

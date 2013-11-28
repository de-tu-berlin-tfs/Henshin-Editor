package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteParameterCommand;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


public class AttributeConditionComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new SimpleDeleteEObjectCommand( (EObject) getHost().getModel());
	}
}

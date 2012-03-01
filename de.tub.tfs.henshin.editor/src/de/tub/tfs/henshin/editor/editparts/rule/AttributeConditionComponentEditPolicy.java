/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class ConditionComponentEditPolicy.
 * 
 * @author Angeline Warning
 */
public class AttributeConditionComponentEditPolicy extends ComponentEditPolicy
		implements EditPolicy {

	/**
	 * Create a delete command to delete a formula, only if the parent is a
	 * graph or a formula.
	 * 
	 * @param deleteRequest
	 *            A request to delete a formula.
	 * @return Command Delete command
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new SimpleDeleteEObjectCommand((EObject) getHost().getModel());
	}

}

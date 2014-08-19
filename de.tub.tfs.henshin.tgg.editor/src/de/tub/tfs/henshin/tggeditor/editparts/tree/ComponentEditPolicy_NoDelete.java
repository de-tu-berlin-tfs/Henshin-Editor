/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

/**
 * The Class ComponentEditPolicy_NoDelete.
 *
 * @author Johann
 */
public class ComponentEditPolicy_NoDelete extends ComponentEditPolicy {
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(org.eclipse.gef.requests.GroupRequest)
	 */
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return null;
	}

}

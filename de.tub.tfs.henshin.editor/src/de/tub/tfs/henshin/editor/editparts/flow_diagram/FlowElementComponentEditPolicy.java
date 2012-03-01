/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.DeleteFlowElementCommand;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;

/**
 * @author nam
 * 
 */
public class FlowElementComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#getDeleteCommand(org
	 * .eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Object hostModel = getHost().getModel();

		if (hostModel instanceof FlowElement) {
			return new DeleteFlowElementCommand((FlowElement) hostModel);
		}

		return super.createDeleteCommand(deleteRequest);
	}
}

/**
 * ParameterGraphicalEditPolicy.java
 *
 * Created 26.12.2011 - 11:47:54
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateParameterMappingCommand;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;

/**
 * @author nam
 * 
 */
public class ParameterGraphicalNodeEditPolicy extends GraphicalNodeEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCompleteCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		Command cmd = request.getStartCommand();

		if (cmd instanceof CreateParameterMappingCommand) {
			Object hostModel = (Parameter) getHost().getModel();
			CreateParameterMappingCommand castedCmd = (CreateParameterMappingCommand) cmd;

			if (hostModel instanceof Parameter) {
				castedCmd.setTarget((Parameter) hostModel);
			}
		}

		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		Object newObject = request.getNewObject();
		CreateParameterMappingCommand cmd = null;

		if (newObject instanceof ParameterMapping) {
			ParameterMapping newMapping = (ParameterMapping) newObject;
			Object hostModel = (Parameter) getHost().getModel();
			FlowDiagram diagram = (FlowDiagram) getHost().getParent()
					.getModel();

			if (hostModel instanceof Parameter) {
				cmd = new CreateParameterMappingCommand(newMapping,
						(Parameter) hostModel, diagram);

				request.setStartCommand(cmd);
			}
		}

		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}

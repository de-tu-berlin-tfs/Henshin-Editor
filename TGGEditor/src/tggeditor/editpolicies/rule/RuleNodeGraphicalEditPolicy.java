package tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import tggeditor.commands.create.CreateEdgeCommand;
import tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;

public class RuleNodeGraphicalEditPolicy extends NodeGraphicalEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCompleteCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		final Command command = request.getStartCommand();
		
		if (command instanceof CreateEdgeCommand) {
			final CreateRuleEdgeCommand createRuleEdgeCommand = (CreateRuleEdgeCommand) command;
			if (createRuleEdgeCommand.isSourcePlace()) {

				createRuleEdgeCommand.setTarget((Node) getHost().getModel());
				return createRuleEdgeCommand;
			}
		}

		return null;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getConnectionCreateCommand
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		Command command;
		
		final Object requestingObject = request.getNewObject();
		if (requestingObject instanceof Edge) {
			final Node node = (Node) getHost().getModel();
//			final Graph graph = (Graph) node.eContainer();
//			if (ModelUtil.graphInKernelRule(graph)) {
//				command = new CreateKernelEdgeCommand(node, (Edge) requestingObject);
//			}
//			if {
				command = new CreateRuleEdgeCommand(node, (Edge) requestingObject);	
//			}
			
			request.setStartCommand(command);
			return command;
		}
		return null;
	}
}

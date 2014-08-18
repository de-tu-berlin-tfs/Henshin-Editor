package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateConnectionRequest;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateNodeMappingCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveNodeObjectCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.ReconnectedEdgeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;


/**
 * The Class NodeGraphicalEditPartPolicy.
 */
public class RuleGraphicalEditPolicy extends GraphicalNodeEditPolicy
		implements EditPolicy {

	PolylineConnection dummyConnect;
	Command command;
	
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
		command = request.getStartCommand();
		
		if (command instanceof CreateRuleEdgeCommand) {
			//final CreateRuleEdgeCommand createEdgeCommand = (CreateRuleEdgeCommand) command;
			//TNode n = TggFactory.eINSTANCE.createTNode();
			//TripleGraph graph =  (TripleGraph) getHost().getModel();
			//graph.getNodes().add(n);
			
			//createEdgeCommand.setTarget((Node) getHost().getModel());
			
			return null;
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
		command = null;
		
				
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getSourceConnectionAnchor
	 * (org.eclipse.gef.requests.CreateConnectionRequest)
	 */
	@Override
	protected ConnectionAnchor getSourceConnectionAnchor(
			CreateConnectionRequest request) {
		
		return super.getSourceConnectionAnchor(request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#getCommand(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		return super.getCommand(request);
	}

	/**
	 * @param a_request
	 */
	protected Command getAligmentCommand(AlignmentRequest a_request) {
		return null;
	}

	/**
	 * Returns a connection to be used as feeback during creates.
	 * 
	 * @param req
	 *            the operation being performed
	 * @return a connection to use as feedback
	 */
	protected Connection createDummyConnection(Request req) {
		dummyConnect = new PolylineConnection();
		if (command != null) {
			if (command instanceof CreateNodeMappingCommand) {
				dummyConnect.setForegroundColor(ColorConstants.white);
//				dummyConnect.setBackgroundColor(ColorConstants.white);
			}
			else {
				dummyConnect.setForegroundColor(ColorConstants.black);
//				dummyConnect.setBackgroundColor(ColorConstants.black);
			}
		}
		return dummyConnect;
	}
}

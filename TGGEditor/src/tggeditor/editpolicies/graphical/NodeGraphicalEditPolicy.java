package tggeditor.editpolicies.graphical;

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

import tgg.NodeLayout;
import tggeditor.commands.create.CreateEdgeCommand;
import tggeditor.commands.create.rule.CreateNodeMappingCommand;
import tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import tggeditor.commands.move.MoveNodeObjectCommand;
import tggeditor.commands.move.ReconnectedEdgeCommand;
import tggeditor.editparts.graphical.NodeObjectEditPart;
import tggeditor.editparts.rule.RuleNodeEditPart;
import tggeditor.util.NodeUtil;

/**
 * The Class NodeGraphicalEditPartPolicy.
 */
public class NodeGraphicalEditPolicy extends GraphicalNodeEditPolicy
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
			final CreateRuleEdgeCommand createEdgeCommand = (CreateRuleEdgeCommand) command;
			createEdgeCommand.setTarget((Node) getHost().getModel());
			return createEdgeCommand;
		}
		if (command instanceof CreateEdgeCommand) {
			final CreateEdgeCommand createEdgeCommand = (CreateEdgeCommand) command;
			createEdgeCommand.setTarget((Node) getHost().getModel());
			return createEdgeCommand;		
		}
		
		if (command instanceof CreateNodeMappingCommand){
			final CreateNodeMappingCommand c = (CreateNodeMappingCommand ) command;
			if(c.getOrigial() != null){
				Node original = c.getOrigial();
				final Node image = (Node) getHost().getModel();
				if(original.getType() == image.getType()
						&& !(image.eContainer().eContainer() instanceof Rule)) {
					c.setImage(image);
					c.setActualMappings(((NestedCondition)image.getGraph().eContainer()).getMappings());
					c.getStartMappingEditPart().setNumberForMapping(c.getMappingNumber());
					((NodeObjectEditPart) getHost()).setNumberForMapping(c.getMappingNumber());
					c.setEndMappingEditPart((NodeObjectEditPart)this.getHost());
					return c;
				}
				
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
		command = null;
		
		final Object requestingObject = request.getNewObject();
		if (requestingObject instanceof Edge) {
			if (getHost() instanceof RuleNodeEditPart) {
				CreateRuleEdgeCommand c = new CreateRuleEdgeCommand(
						(Node)getHost().getModel(),(Edge)requestingObject);
				request.setStartCommand(c);
				command = c;
				return c;
			}
			else {
				CreateEdgeCommand c = new CreateEdgeCommand(
						(Node)getHost().getModel(),(Edge)requestingObject);
				request.setStartCommand(c);
				command = c;
				return c;
			}
		}
		
		if(requestingObject instanceof Mapping && getHost() instanceof RuleNodeEditPart){
			final Mapping mapping = (Mapping)requestingObject;
			NodeLayout nl = NodeUtil.getNodeLayout((Node) getHost().getModel());
			if(nl.getLhsnode() != null){
				Node lhs= nl.getLhsnode();
				CreateNodeMappingCommand c = new CreateNodeMappingCommand(nl.getLhsnode(), mapping);
				int index = lhs.getGraph().getNodes().indexOf(lhs);
				c.setMappingNumber(index);
				c.setStartMappingEditPart((NodeObjectEditPart)this.getHost());
				request.setStartCommand(c);
				command = c;
				return c;
			}
		}
			
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
		//getHost().getParent().getViewer().deselectAll();
		getHost().getViewer().select(getHost());
		return new ChopboxAnchor(getHostFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.eclipse.gef.editpolicies.GraphicalNodeEditPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		Edge edge = (Edge) request.getConnectionEditPart().getModel();
		Node target = edge.getTarget();
		Node source = (Node) getHost().getModel();
		if (edge.getSource() != source)
			return new ReconnectedEdgeCommand(edge, source, target);
		else
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
		Edge edge = (Edge) request.getConnectionEditPart().getModel();
		Node target = (Node) getHost().getModel();
		Node source = edge.getSource();
		if (target != edge.getTarget())
			return new ReconnectedEdgeCommand(edge, source, target);
		else
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
		if (REQ_ALIGN.equals(request.getType())) {
			AlignmentRequest a_request = (AlignmentRequest) request;
			return getAligmentCommand(a_request);
		}
		return super.getCommand(request);
	}

	/**
	 * @param a_request
	 */
	protected Command getAligmentCommand(AlignmentRequest a_request) {
		Node node = (Node) getHost().getModel();
		NodeLayout nL = NodeUtil.getNodeLayout(node);
		switch (a_request.getAlignment()) {
		case PositionConstants.LEFT:
			return new MoveNodeObjectCommand(nL,
					a_request.getAlignmentRectangle().x, nL.getY());
		case PositionConstants.RIGHT:
			return new MoveNodeObjectCommand(nL,
					a_request.getAlignmentRectangle().x
							+ a_request.getAlignmentRectangle().width
							- getHostFigure().getBounds().width, nL.getY());
		case PositionConstants.TOP:
			return new MoveNodeObjectCommand(nL, nL.getX(),
					a_request.getAlignmentRectangle().y);
		case PositionConstants.BOTTOM:
			return new MoveNodeObjectCommand(nL, nL.getX(),
					a_request.getAlignmentRectangle().y
							+ a_request.getAlignmentRectangle().height
							- getHostFigure().getBounds().height);
		case PositionConstants.CENTER:
			int newCenter = a_request.getAlignmentRectangle().x+a_request.getAlignmentRectangle().width/2;
			int x = newCenter - getHostFigure().getBounds().width/2;
			return new MoveNodeObjectCommand(
					nL,x, nL.getY());
		case PositionConstants.MIDDLE:
			int newMidle = a_request.getAlignmentRectangle().y+a_request.getAlignmentRectangle().height/2;
			int y = newMidle - getHostFigure().getBounds().height/2;

			return new MoveNodeObjectCommand(
					nL,
					nL.getX(),
					y);
		}
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

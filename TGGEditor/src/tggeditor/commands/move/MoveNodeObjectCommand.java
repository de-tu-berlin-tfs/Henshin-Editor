package tggeditor.commands.move;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import tgg.NodeLayout;
import tggeditor.editparts.graphical.NodeObjectEditPart;

public class MoveNodeObjectCommand extends Command {
	/** The request. */
	ChangeBoundsRequest request;

	/** The node */
	Node node;
	
	/** The node layout of node. */
	NodeLayout nL;

	/** The EditPart of node */
	NodeObjectEditPart nodeEditPart;

	/** The old x position value */
	private int oldX;

	/** The old y position value */
	private int oldY;

	/** The x position value */
	private int x;

	/** The y position value */
	private int y;

	/**
	 * Instantiates a new move node command.
	 *
	 * @param nodeEditPart the node edit part
	 * @param request the request
	 */
	public MoveNodeObjectCommand(NodeObjectEditPart nodeEditPart, ChangeBoundsRequest request) {
		this.request = request;
		this.nodeEditPart = nodeEditPart;
		nL = (NodeLayout) nodeEditPart.getLayoutModel();
		this.node = nL.getNode();
		oldX = nL.getX();
		oldY = nL.getY();
		this.x=nL.getX() + request.getMoveDelta().x;
		this.y=nL.getY() + request.getMoveDelta().y;
	}


	/**
	 * Instantiates a new move node command.
	 *
	 * @param nL the n l
	 * @param x the Coordinate x
	 * @param y the Coordinate y
	 */
	public MoveNodeObjectCommand(NodeLayout nL, int x, int y) {
		super();
		this.nL = nL;
		this.node = nL.getNode();
		this.x = x;
		this.y = y;
		oldX = nL.getX();
		oldY = nL.getY();

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (oldX!=x){
			nL.setX(x);
		}
		if (oldY!=y){
			nL.setY(y);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		if (oldX!=x){
			nL.setX(oldX);
		}
		if (oldY!=y){
			nL.setY(oldY);
		}
	}



	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 * will be calculated of canMoveNode in GraphXYLayoutEditPolicy because divider are movable
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * gets the node layout corresponding to this node
	 * @return the node layout of this node
	 */
	public NodeLayout getLayoutModel() {
		return this.nodeEditPart.getLayoutModel();
	}

	/**
	 * gets new x value of this command
	 * @return x position value
	 */
	public int getX() {
		return this.x;
	}
}

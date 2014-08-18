package de.tub.tfs.henshin.tggeditor.commands.move;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class MoveNodeObjectCommand extends Command {
	/** The request. */
	ChangeBoundsRequest request;

	/** The node */
	Node node;
	
//	/** The node layout of node. */
//	NodeLayout nL;

	/** The EditPart of node */
	TNodeObjectEditPart nodeEditPart;

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
	public MoveNodeObjectCommand(TNodeObjectEditPart nodeEditPart, ChangeBoundsRequest request) {
		this.request = request;
		this.nodeEditPart = nodeEditPart;
//		nL = (NodeLayout) nodeEditPart.getLayoutModel();
		this.node = nodeEditPart.getCastedModel();

		oldX = ((TNode) node).getX();
		oldY = ((TNode) node).getY();
		this.x=((TNode) node).getX() + request.getMoveDelta().x;
		this.y=((TNode) node).getY() + request.getMoveDelta().y;
	}


	/**
	 * Instantiates a new move node command.
	 *
	 * @param nL the n l
	 * @param x the Coordinate x
	 * @param y the Coordinate y
	 */
	public MoveNodeObjectCommand(Node node,TNodeObjectEditPart np, int x, int y) {
		super();
//		this.nL = nL;
		this.nodeEditPart = np;
		this.node = node;
		this.x = x;
		this.y = y;
		oldX = ((TNode) node).getX();
		oldY = ((TNode) node).getY();

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (oldX!=x){
			((TNode) node).setX(x);
		}
		if (oldY!=y){
			((TNode) node).setY(y);
		}
		if (node instanceof TNode)
			((TNode) node).setComponent(NodeUtil.getComponentFromPosition((TNode)node));
		this.nodeEditPart.getFigure().invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		
		if (oldX!=x){
			((TNode) node).setX(oldX);
		}
		if (oldY!=y){
			((TNode) node).setY(oldY);
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
	public Node getNode() {
		return this.node;
	}

	/**
	 * gets new x value of this command
	 * @return x position value
	 */
	public int getX() {
		return this.x;
	}
}

package de.tub.tfs.henshin.tggeditor.commands.move;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;



/**
 * The Class MoveDividerCommand.
 */
public class MoveDividerCommand extends Command {
	
	/** The request. */
	ChangeBoundsRequest request;
	
	/** The n l. */
	GraphLayout gL;
	
	/** The old x, y. */
	private int oldX, oldY;
		
	/** The x, y. */
	private int x, y;
	

	/**
	 * Instantiates a new move divider command.
	 *
	 * @param dividerEditPart the divider edit part
	 * @param request the request
	 */
	public MoveDividerCommand(DividerEditPart dividerEditPart, ChangeBoundsRequest request) {
		this.request = request;
		gL = (GraphLayout) dividerEditPart.getModel();
		oldX = gL.getDividerX();
		this.x=gL.getDividerX() + request.getMoveDelta().x;
		oldY = gL.getMaxY();
		this.y=gL.getMaxY() + request.getMoveDelta().y;
	}

	
	/**
	 * Instantiates a new move divider command.
	 *
	 * @param gL the gL
	 * @param x the Coordinate x
	 */
	public MoveDividerCommand(GraphLayout gL, int x, int y) {
		super();
		this.gL = gL;
		this.x = x;
		oldX = gL.getDividerX();
		this.y = y;
		oldY = gL.getMaxY();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (oldX!=x){
			gL.setDividerX(x);
		}
		if (oldY!=y){
			gL.setMaxY(y);
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
			gL.setDividerX(oldX);
		}
		if (oldY!=y){
			gL.setMaxY(oldY);
		}
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return gL != null && x > 100;
	}
	
	public int getX(){
		return this.x;
	}
}

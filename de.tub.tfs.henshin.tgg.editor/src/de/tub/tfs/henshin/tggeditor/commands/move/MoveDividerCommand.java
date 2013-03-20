package de.tub.tfs.henshin.tggeditor.commands.move;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.Divider;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;



/**
 * The Class MoveDividerCommand.
 */
public class MoveDividerCommand extends Command {
	
	/** The request. */
	ChangeBoundsRequest request;
	
	/** The divider. */
	Divider divider;
	
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
		this.divider=dividerEditPart.getCastedModel();
		oldX = getDividerX();
		this.x=getDividerX() + request.getMoveDelta().x;
		oldY = divider.getTripleGraph().getDividerMaxY();
		this.y=divider.getTripleGraph().getDividerMaxY() + request.getMoveDelta().y;
	}

	
	/**
	 * retrieves the x coordinate of the divider
	 * @return
	 */
	private int getDividerX() {
		if(divider.isSC())
			return divider.getTripleGraph().getDividerSC_X();
		else
			return divider.getTripleGraph().getDividerCT_X();
	}

	/**
	 * sets the x coordinate of the divider
	 * @return
	 */
	private void setDividerX(int x) {
		if(divider.isSC())
			divider.getTripleGraph().setDividerSC_X(x);
		else
			divider.getTripleGraph().setDividerCT_X(x);
	}


	/**
	 * Instantiates a new move divider command.
	 *
	 * @param divider the triple graph
	 * @param x the Coordinate x
	 */
	public MoveDividerCommand(Divider divider, int x, int y) {
		super();
		this.divider = divider;
		this.x = x;
		oldX = getDividerX();
		this.y = y;
		oldY = divider.getTripleGraph().getDividerMaxY();
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (oldX!=x){
			setDividerX(x);
		}
		if (oldY!=y){
			divider.getTripleGraph().setDividerMaxY(y);
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
			setDividerX(oldX);
		}
		if (oldY!=y){
			divider.getTripleGraph().setDividerMaxY(oldY);
		}
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return divider != null && x > 100;
	}
	
	public int getX(){
		return this.x;
	}
}

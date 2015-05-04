package de.tub.tfs.henshin.tggeditor.commands.move.constraint;

import org.eclipse.gef.requests.ChangeBoundsRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveNodeObjectCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;

public class MoveNodeCommand extends MoveNodeObjectCommand {

	public MoveNodeCommand(TNodeObjectEditPart nodeEditPart,
			ChangeBoundsRequest request) {
		super(nodeEditPart, request);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		if (oldX!=x){
			((TNode) node).setX(x);
		}
		if (oldY!=y){
			((TNode) node).setY(y);
		}
		this.nodeEditPart.getFigure().invalidate();
	}

}

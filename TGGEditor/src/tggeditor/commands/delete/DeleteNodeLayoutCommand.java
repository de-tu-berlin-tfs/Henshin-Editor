package tggeditor.commands.delete;

import org.eclipse.gef.commands.Command;

import tgg.NodeLayout;
import tgg.TGG;

public class DeleteNodeLayoutCommand extends Command {

	/*
	 * TODO
	 * bin mir nicht sicher, ob das so funktioniert
	 * Kann es zB mehrere Knoten mit dem selben referenzierten NodeLayout gibt?
	 * Dann müsste man zuvor prüfen, ob das der letzte knoten ist, der diese layout benutzt
	 */
	
	private NodeLayout nodeLayout;
	private TGG layoutSystem;
	
	public DeleteNodeLayoutCommand(TGG layoutSystem, NodeLayout nodeLayout) {
		this.layoutSystem = layoutSystem;
		this.nodeLayout = nodeLayout;
	}
	
	@Override
	public boolean canExecute() {
		if (this.layoutSystem != null && this.nodeLayout != null) {
			return true;
		}
		return false;
	}
	
	@Override
	public void execute() {
		this.layoutSystem.getNodelayouts().remove(this.nodeLayout);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		this.layoutSystem.getNodelayouts().add(this.nodeLayout);
	}

}

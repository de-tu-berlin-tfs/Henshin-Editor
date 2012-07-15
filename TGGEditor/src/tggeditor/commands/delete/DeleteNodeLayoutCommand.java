package tggeditor.commands.delete;

import org.eclipse.gef.commands.Command;

import tgg.NodeLayout;
import tgg.TGG;

public class DeleteNodeLayoutCommand extends Command {
	
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

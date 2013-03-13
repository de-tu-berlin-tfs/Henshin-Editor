package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggPackage;


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
		// notify the nodeLayout about the recreation to refresh visuals 
//		if (nodeLayout.getNode() != null)
//			nodeLayout.getNode().eNotify(
//					new ENotificationImpl((InternalEObject) nodeLayout.getNode(),
//							Notification.ADD, TggPackage.NODE_LAYOUT__NEW,
//							null, nodeLayout));
	}

}

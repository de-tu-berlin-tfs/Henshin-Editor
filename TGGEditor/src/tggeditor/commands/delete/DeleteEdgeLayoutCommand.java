package tggeditor.commands.delete;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

import tgg.EdgeLayout;
import tgg.TGGPackage;
import tgg.TGG;

/**
 * The class DeleteEdgeLayoutCommand deletes the layout of an edge.
 */
public class DeleteEdgeLayoutCommand extends CompoundCommand {

	/**
	 * the layout
	 */
	private EdgeLayout edgeLayout;
	/**
	 * the tgg layout system
	 */
	private TGG layoutSystem;
	
	
	/**
	 * the constructor
	 * @param layoutSystem the layout system containing the edge
	 * @param edgeLayout the edge to be deleted
	 */
	public DeleteEdgeLayoutCommand(TGG layoutSystem, EdgeLayout edgeLayout) {
//		this.layoutSystem = layoutSystem;
//		this.edgeLayout = edgeLayout;
		add(new SimpleDeleteEObjectCommand(edgeLayout));
		
	}
	
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.Command#canExecute()
//	 */
//	@Override
//	public boolean canExecute() {
//		if (this.layoutSystem != null && this.edgeLayout != null) {
//			return true;
//		}
//		return false;
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.Command#execute()
//	 */
//	@Override
//	public void execute() {
//		this.layoutSystem.getEdgelayouts().remove(this.edgeLayout);
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.Command#undo()
//	 */
//	@Override
//	public void undo() {
//		this.layoutSystem.getEdgelayouts().add(this.edgeLayout);
//	}

	
}

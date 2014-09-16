/**
 * HenshinCutAction.java
 *
 * Created 21.01.2012 - 13:10:00
 */
package de.tub.tfs.henshin.editor.actions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

/**
 * @author nam
 * 
 */
public class HenshinCutAction extends HenshinCopyAction {

	/**
	 * @param part
	 */
	public HenshinCutAction(IWorkbenchPart part) {
		super(part);

		setId(ActionFactory.CUT.getId());
		setText("Cut");
		setDescription("Cut parts to clipboard");
		setToolTipText("Cuts the selected parts to the clipboard");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.actions.transSys.HenshinCopyAction#run()
	 */
	@Override
	public void run() {
		super.run();

		GroupRequest req = new GroupRequest(RequestConstants.REQ_DELETE);
		CompoundCommand cmd = new CompoundCommand("Cut Objects");

		for (EditPart p : selection) {
			cmd.add(p.getCommand(req));
		}

		execute(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.actions.transSys.HenshinCopyAction#calculateEnabled
	 * ()
	 */
	@Override
	protected boolean calculateEnabled() {
		boolean canCopy = super.calculateEnabled();
		boolean canDelete = true;

		GroupRequest req = new GroupRequest(RequestConstants.REQ_DELETE);

		for (EditPart p : selection) {
			if (p.getCommand(req) == null) {
				canDelete = false;

				break;
			}
		}

		return canCopy && canDelete;
	}
}

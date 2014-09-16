/**
 * HenshinCopyAction.java
 *
 * Created 16.01.2012 - 18:15:50
 */
package de.tub.tfs.henshin.editor.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.CopyRequest;
import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;

/**
 * @author nam
 * 
 */
public class HenshinCopyAction extends SelectionAction {

	protected List<EditPart> selection;

	/**
	 * @param part
	 */
	public HenshinCopyAction(IWorkbenchPart part) {
		super(part);

		selection = new LinkedList<EditPart>();

		setId(ActionFactory.COPY.getId());
		setText("Copy");
		setDescription("Copy parts to clipboard");
		setToolTipText("Copies the selected parts to the clipboard");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_COPY));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CopyRequest req = new CopyRequest();

		for (EditPart p : selection) {
			ClipboardEditPolicy copyPolicy = (ClipboardEditPolicy) p
					.getEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE);
			
			copyPolicy.performCopy(req);
		}

		Clipboard.getDefault().setContents(req.getContents());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		this.selection.clear();

		for (Object o : selection) {
			if (!(o instanceof EditPart)) {
				return false;
			}

			EditPart part = (EditPart) o;
			Object model = part.getModel();

			if (!(model instanceof EObject)) {
				return false;
			}

			if (part.understandsRequest(new CopyRequest())) {
				this.selection.add(part);
			}
		}

		return !this.selection.isEmpty();
	}

}

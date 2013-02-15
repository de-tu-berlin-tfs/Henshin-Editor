/**
 * DeleteEPackageAction.java
 *
 * Created 19.12.2011 - 21:24:00
 */
package de.tub.tfs.henshin.editor.actions.transSys;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.editparts.epackage.EPackageTreeEditpart;
import de.tub.tfs.henshin.editor.util.HenshinUtil;

/**
 * @author nam
 * 
 */
public class DeleteEPackageAction extends DeleteAction {

	/**
	 * An unique ID.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.transSys.DeleteEPackageAction";

	private List<EPackage> models = new LinkedList<EPackage>();

	private Module rootModel;

	/**
	 * @param part
	 */
	public DeleteEPackageAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Delete EPackage(s)");
		setToolTipText("Delete meta model(s).");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		StringBuffer errMsg = new StringBuffer();

		for (EPackage model : models) {
			List<NamedElement> refs = HenshinUtil.INSTANCE
					.getEPackageReferences(model, rootModel);

			for (NamedElement ref : refs) {
				errMsg.append("  '"
						+ model.getName()
						+ "' is referenced by '"
						+ ref.getName()
						+ (ref instanceof Graph ? "' (Graph).\n"
								: "' (Rule).\n"));
			}
		}

		if (!errMsg.toString().isEmpty()) {
			MessageDialog.openWarning(getWorkbenchPart().getSite().getShell(),
					"EPackage Deletion Warning",
					"Some package(s) will not be deleted, due to following references:\n\n"
							+ errMsg.toString()
							+ "\nPlease remove all references first.");
		}

		super.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		rootModel = null;

		if (!selectedObjects.isEmpty()) {
			boolean onlyEPackagesSelected = selectedObjects.get(0) instanceof EPackageTreeEditpart;

			models.clear();

			for (Object selected : selectedObjects) {
				if (selected instanceof EPackageTreeEditpart) {
					EPackage model = ((EPackageTreeEditpart) selected)
							.getCastedModel();

					this.models.add(model);

					onlyEPackagesSelected &= true;
				} else {
					onlyEPackagesSelected = false;

					break;
				}
			}

			if (onlyEPackagesSelected) {
				rootModel = (Module) ((EditPart) selectedObjects
						.get(0)).getParent().getParent().getModel();
			}
		}

		return rootModel != null;
	}
}

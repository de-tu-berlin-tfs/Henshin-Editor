/**
 * 
 */
package de.tub.tfs.henshin.editor.actions;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * The Class HenshinDeleteAction.
 * 
 * @author nam
 */
public class HenshinDeleteAction extends DeleteAction {

	/**
	 * Instantiates a new henshin delete action.
	 * 
	 * @param part
	 *            the part
	 */
	public HenshinDeleteAction(IWorkbenchPart part) {
		super(part);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.ui.actions.DeleteAction#createDeleteCommand(java.util
	 * .List)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Command createDeleteCommand(List objects) {
		CompoundCommand cmd = (CompoundCommand) super
				.createDeleteCommand(objects);

		if (cmd != null) {
			if (cmd.getCommands().size() == 1) {
				cmd.setLabel(((Command) cmd.getCommands().get(0)).getLabel());
			} else {
				cmd.setLabel("Delete Objects");
			}
		}

		return cmd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#run()
	 */
	@Override
	public void run() {
		if (!getSelectedObjects().isEmpty()) {
			final Object targetObject = getSelectedObjects().get(0);
			if (targetObject instanceof EditPart) {
				final Object model = ((EditPart) targetObject).getModel();
				if (model instanceof EPackage) {
					if (!canDeleteEPackage(targetObject, (EPackage) model)) {
						return;
					}
				} else if (model instanceof Rule) {
					// TODO:
					//if (model.getMulti)
					// && canDeleteRule(targetObject, (Rule) model)) {
					// return;
					// }
				} 
			}
		}

		super.run();
	}

	private boolean canDeleteEPackage(Object targetObject, EPackage ePackage) {
		Module rootModel = (Module) ((EditPart) targetObject)
				.getParent().getParent().getModel();
		String errMsg = ModelUtil.getEPackageReferences(ePackage, rootModel);

		if (errMsg != null) {
			MessageDialog.open(MessageDialog.INFORMATION, null,
					"Delete Failed", "EPackage [" + ePackage.getName()
							+ "] is used in:\n\n" + errMsg
							+ "\n\nPlease delete all references first.",
					SWT.NONE);
			return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if (!getSelectedObjects().isEmpty()) {
			Object targetObject = getSelectedObjects().get(0);
			if (targetObject instanceof EditPart) {
				Object model = ((EditPart) targetObject).getModel();

				if (model instanceof EPackage) {
					return true;
				}
				
				if (model instanceof Node) {
					Node node = (Node) model;
				
									
				}
			}
		}
		return super.calculateEnabled();
	}
}

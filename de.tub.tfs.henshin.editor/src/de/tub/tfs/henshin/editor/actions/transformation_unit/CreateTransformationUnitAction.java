/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.List;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateTransformationUnitAction.
 */
public abstract class CreateTransformationUnitAction extends SelectionAction {

	/** The trans sys. */
	protected Module transformationSystem;

	/**
	 * Instantiates a new creates the transformation unit action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateTransformationUnitAction(IWorkbenchPart part) {
		super(part);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof Module) {
					transformationSystem = (Module) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Unit.class) != null) {
					transformationSystem = (Module) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}
}

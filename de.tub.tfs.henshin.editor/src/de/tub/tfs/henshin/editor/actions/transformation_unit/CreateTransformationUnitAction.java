/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.List;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateTransformationUnitAction.
 */
public abstract class CreateTransformationUnitAction extends SelectionAction {

	/** The trans sys. */
	protected TransformationSystem transformationSystem;

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

				if (model instanceof TransformationSystem) {
					transformationSystem = (TransformationSystem) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(TransformationUnit.class) != null) {
					transformationSystem = (TransformationSystem) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}
}

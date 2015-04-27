/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;

/**
 * The Class CreateTransformationUnitWithContentAction.
 */
public class CreateTransformationUnitWithContentAction extends SelectionAction {

	/** The max content count. */
	protected int maxContentCount;

	/** The trans sys. */
	protected Module transSys;

	/** The selected trans units. */
	protected List<Unit> selectedTransUnits;

	/** The parent object. */
	protected Object parentObject;

	/**
	 * Instantiates a new creates the transformation unit with content action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateTransformationUnitWithContentAction(IWorkbenchPart part) {
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
		if (selectedObjects.size() > 0
				&& (selectedObjects.size() <= maxContentCount || maxContentCount == -1)) {
			selectedTransUnits.clear();
			if (selectedObjects.get(0) instanceof EditPart) {
				EditPart editpart = (EditPart) selectedObjects.get(0);
				EditPart parent = editpart.getParent();
				parentObject = parent.getModel();
				if (editpart.getModel() instanceof EObject) {
					EObject eContainer = ((EObject) editpart.getModel())
							.eContainer();
					if (eContainer instanceof Module) {
						transSys = (Module) eContainer;
						for (Object selectedObject : selectedObjects) {
							if (selectedObject instanceof EditPart) {
								editpart = (EditPart) selectedObject;
								if (!(editpart.getModel() instanceof Unit)
										|| editpart.getParent() != parent
										|| editpart.getModel() instanceof ConditionalUnitPart) {
									return false;
								}
								selectedTransUnits
										.add((Unit) editpart
												.getModel());
							} else {
								return false;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}

}

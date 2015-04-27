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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transSys.MoveEObjectCommand;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class MoveUpTransformationUnitAction.
 */
public class MoveUpTransformationUnitAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.MoveUpTransformationUnitAction";

	/** The list. */
	private EList<EObject> list;

	/** The index. */
	private int index;

	/**
	 * Instantiates a new move up transformation unit action.
	 * 
	 * @param part
	 *            the part
	 */
	public MoveUpTransformationUnitAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Move up transformation unit");
		setToolTipText("Move up transformation unit");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart.getModel() instanceof Unit
					&& editpart.getParent().getModel() instanceof Unit) {
				list = null;
				Unit parent = (Unit) editpart
						.getParent().getModel();
				EStructuralFeature feature = TransformationUnitUtil
						.getSubUnitsFeature(parent);
				if (feature != null) {
					if (feature.isMany()) {
						list = (EList<EObject>) parent.eGet(feature);
						index = list.indexOf(editpart.getModel());
						if (index > 0) {
							return true;
						}
					}
				}
			}

		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		Command command = new MoveEObjectCommand(list, index, index - 1);
		execute(command);
	}

}

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
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;

/**
 * @author nam
 * 
 */
public class DeleteActivityContentAction extends SelectionAction {

	/**
	 * An unique id for this {@link Action}
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.DeleteActivityContentAction";

	/**
	 * 
	 */
	private Activity model;

	/**
	 * @param part
	 */
	public DeleteActivityContentAction(IWorkbenchPart part) {
		super(part);

		setText("Delete Content");
		setId(ID);
		setDescription("Remove the content of this Activity");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjs = getSelectedObjects();

		if (selectedObjs.size() == 1) {
			Object selected = selectedObjs.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof Activity) {
					this.model = (Activity) model;

					return this.model.getContent() != null;
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
		execute(new SetActivityContentCommand(model, null));
	}
}

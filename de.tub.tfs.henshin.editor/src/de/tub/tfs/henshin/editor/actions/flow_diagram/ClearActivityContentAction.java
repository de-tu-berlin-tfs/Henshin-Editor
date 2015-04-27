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
 * ClearActivityContent.java
 *
 * Created 08.01.2012 - 11:27:39
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.ActivityEditPart;
import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;

/**
 * @author nam
 * 
 */
public class ClearActivityContentAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.ClearActivityContent";//$NON-NLS-1$

	private LinkedList<Activity> models;

	/**
	 * @param part
	 */
	public ClearActivityContentAction(IWorkbenchPart part) {
		super(part);

		models = new LinkedList<Activity>();

		setId(ID);
		setText("Clear Content");
		setDescription("Remove all contents the selected Activities.");
		setImageDescriptor(ResourceUtil.ICONS.CLEAR.descr(18));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand cmd = new CompoundCommand("Clear Activity Content");

		for (Activity a : models) {
			cmd.add(new SetActivityContentCommand(a, null));
		}

		execute(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		models.clear();

		List<?> selection = getSelectedObjects();
		Class<?> selectionType = JavaUtil.getDataType(selection);

		if (ActivityEditPart.class.equals(selectionType)) {

			for (Object o : selection) {
				Activity a = (Activity) ((EditPart) o).getModel();

				if (a.getContent() != null) {
					models.add(a);
				}
			}
		}

		return !models.isEmpty();
	}
}

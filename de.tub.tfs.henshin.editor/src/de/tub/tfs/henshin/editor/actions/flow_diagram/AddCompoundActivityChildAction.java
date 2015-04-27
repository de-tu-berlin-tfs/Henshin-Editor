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
 * AddCompoundActivityChildAction.java
 *
 * Created 27.12.2011 - 22:14:30
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.AddCompoundActivityChildCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Start;

/**
 * @author nam
 * 
 */
public class AddCompoundActivityChildAction extends SelectionAction {

	/**
	 * An unique static ID of this {@link Action action}.
	 */
	public static String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.AddCompoundActivityChildAction";

	private CompoundActivity model;

	/**
	 * @param part
	 */
	public AddCompoundActivityChildAction(IWorkbenchPart part) {
		super(part);

		setText("Add Activity...");
		setId(ID);
		setDescription("Add a Child to this Compound Activity");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		this.model = null;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				EditPart part = (EditPart) selected;
				Object model = part.getModel();

				if (model instanceof CompoundActivity) {
					this.model = (CompoundActivity) model;
				}
			}
		}

		return this.model != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		FlowDiagram diagram = model.getDiagram();

		if (diagram != null) {
			List<Activity> activities = new ArrayList<Activity>();

			for (FlowElement e : diagram.getElements()) {
				if (e instanceof Activity && !(e instanceof Start)
						&& !(e instanceof End) && e != model) {
					activities.add((Activity) e);
				}
			}

			ExtendedElementListSelectionDialog<Object> diag = new ExtendedElementListSelectionDialog<Object>(
					getWorkbenchPart().getSite().getShell(),
					new LabelProvider() {
						@Override
						public String getText(Object element) {
							Activity a = (Activity) element;

							if (a.getContent() != null) {
								return "Activity: " + a.getContent().getName();
							}

							return "Activity: <empty>";
						}
					}, activities.toArray(), "Activity Selection",
					"Please select one or more activities.");

			Object[] result = diag.runMulti();

			if (result != null) {
				CompoundCommand cmd = new CompoundCommand(
						"Change Compound Content");

				for (Object o : result) {
					cmd.add(new AddCompoundActivityChildCommand((Activity) o,
							model));
				}

				execute(cmd);
			}
		}
	}
}

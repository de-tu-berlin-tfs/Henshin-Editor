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
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Listener;

/**
 * The Class ContainerSelectionGroup.
 * 
 * @author nam
 */
public class ContainerSelectionGroup extends ResourceSelectionGroup {

	/**
	 * Instantiates a new container selection group.
	 * 
	 * @param composite
	 *            the composite
	 * @param listener
	 *            the listener
	 * @param allowNew
	 *            the allow new
	 * @param msg
	 *            the msg
	 */
	public ContainerSelectionGroup(Composite composite, Listener listener,
			boolean allowNew, String msg) {
		super(composite, listener, allowNew, new ContainterValidator(), msg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see transeditor.ui.dialog.resources.ResourceSelectionGroup#validate()
	 */
	@Override
	protected void validate() {
		super.validate();

		if (allowNewResource) {
			String projName = getResourceFullPath().segment(0);
			if (projName != null
					&& !projName.isEmpty()
					&& !projName.equals("/")
					&& !(ResourcesPlugin.getWorkspace().getRoot()
							.findMember(projName) instanceof IProject)) {
				errorMessage = "The specified project does not exist.";
				isValid = false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transeditor.ui.dialog.resources.ResourceSelectionGroup#modifyText(org
	 * .eclipse.swt.events.ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		super.modifyText(e);

		if (!resourceInput.getText().endsWith("/")) {
			IResource res = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(getResourceFullPath());

			if (res != null) {
				treeViewer.removeSelectionChangedListener(this);
				treeViewer.setSelection(new StructuredSelection(res), true);
				treeViewer.addSelectionChangedListener(this);
				treeViewer.expandToLevel(res, 1);
			}
		}
	}
}
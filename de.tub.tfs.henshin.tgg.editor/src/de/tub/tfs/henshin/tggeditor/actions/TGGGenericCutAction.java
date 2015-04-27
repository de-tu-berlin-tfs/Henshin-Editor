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
package de.tub.tfs.henshin.tggeditor.actions;

import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.GroupRequest;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.muvitor.actions.GenericCutAction;

/**
 * @author Tony
 * 
 */
public class TGGGenericCutAction extends GenericCutAction {

	/**
	 *
	 */
	public TGGGenericCutAction(final IWorkbenchPart part) {
		super(part);
		setId(ActionFactory.CUT.getId());
		super.setText("Cut");
		super.setDescription("Cut parts to clipboard");
		super.setToolTipText("Cuts the selected parts to the clipboard");
	}

	@Override
	public void run() {
		super.run();
		final CompoundCommand compCommand = new CompoundCommand();
		final GroupRequest request = new GroupRequest(
				RequestConstants.REQ_DELETE);
		for (final Object element : getSelectedObjects()) {
			// calculateEnabled ensures that all selected elements are EditParts
			final EditPart editPart = (EditPart) element;
			compCommand.add(editPart.getCommand(request));
		}
		execute(compCommand);
	}
}

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
package de.tub.tfs.muvitor.gef.directedit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.DirectEditPolicy;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.swt.widgets.Text;

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;

/**
 * The direct edit policy for {@link IGraphicalDirectEditPart}s. Creates a
 * {@link SetEObjectFeatureValueCommand} and updates the figure while typing.
 * 
 * @author Tony Modica
 */
public class MuvitorDirectEditPolicy extends DirectEditPolicy {
	
	/*
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest edit) {
		final String labelText = (String) edit.getCellEditor().getValue();
		if (labelText != null) {
			final IGraphicalDirectEditPart graphicalDirectEditPart = (IGraphicalDirectEditPart) getHost();
			return new SetEObjectFeatureValueCommand((EObject) getHost().getModel(),
					labelText.trim(), graphicalDirectEditPart.getDirectEditFeatureID());
		}
		return null;
	}
	
	/*
	 * @see DirectEditPolicy#showCurrentEditValue(DirectEditRequest)
	 */
	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
		/*
		 * getCellEditor().getValue() returns null when invalid, so get the text
		 * directly
		 */
		final String value = ((Text) request.getCellEditor().getControl()).getText();
		((IGraphicalDirectEditPart) getHost()).updateValueDisplay(value);
		// hack to prevent async layout from placing the cell editor twice.
		getHostFigure().getUpdateManager().performUpdate();
	}
	
}

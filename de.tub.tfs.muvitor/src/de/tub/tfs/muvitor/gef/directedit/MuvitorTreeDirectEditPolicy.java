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

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

/**
 * The direct edit policy for {@link IDirectEditPart}s. Creates a
 * {@link SetEObjectFeatureValueCommand}.
 * 
 * @author Tony Modica
 */
public class MuvitorTreeDirectEditPolicy extends DirectEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest request) {
		// In MuvitorDirectEditManager we use the request's direct
		// edit feature to transmit the new name! We do this because the cell
		// editor will already be disposed at this time.
		final IDirectEditPart directEditPart = (IDirectEditPart) getHost();
		final String newValue = ((String) request.getDirectEditFeature()).trim();
		if (directEditPart.getDirectEditFeatureID() > Integer.MIN_VALUE){
			return new SetEObjectFeatureValueCommand((EObject) getHost().getModel(), newValue,
					directEditPart.getDirectEditFeatureID());
		} else {
			return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see DirectEditPolicy#showCurrentEditValue(DirecEditRequest)
	 */
	@Override
	protected void showCurrentEditValue(final DirectEditRequest request) {
	}
	
}

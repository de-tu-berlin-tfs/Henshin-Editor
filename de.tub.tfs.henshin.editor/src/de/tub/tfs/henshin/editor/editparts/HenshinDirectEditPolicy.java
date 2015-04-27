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
package de.tub.tfs.henshin.editor.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.directedit.MuvitorDirectEditPolicy;

/**
 * The Class HenshinDirectEditPolicy.
 */
public class HenshinDirectEditPolicy extends MuvitorDirectEditPolicy {

	/*
	 * @see DirectEditPolicy#getDirectEditCommand(DirectEditRequest)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.directedit.MuvitorDirectEditPolicy#getDirectEditCommand
	 * (org.eclipse.gef.requests.DirectEditRequest)
	 */
	@Override
	protected Command getDirectEditCommand(final DirectEditRequest edit) {
		final String labelText = (String) edit.getCellEditor().getValue();

		if (labelText != null) {
			final IGraphicalDirectEditPart graphicalDirectEditPart = (IGraphicalDirectEditPart) getHost();
			return new SetEObjectFeatureValueCommand((EObject) getHost()
					.getModel(), labelText.trim(),
					graphicalDirectEditPart.getDirectEditFeatureID());
		}

		return null;
	}

}

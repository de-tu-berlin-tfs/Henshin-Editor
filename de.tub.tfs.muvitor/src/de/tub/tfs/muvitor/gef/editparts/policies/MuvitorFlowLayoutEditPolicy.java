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
package de.tub.tfs.muvitor.gef.editparts.policies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.FlowLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.emf.clipboard.core.ClipboardUtil;

import de.tub.tfs.muvitor.actions.GenericPasteAction.PasteCommand;

/**
 * This layout editpolicy installs {@link GhostFigureNonResizableEditPolicy}s on
 * an editpart's children, which will automatically show a ghost figure on
 * dragging for all children's figures that implement
 * {@link IGhostFigureProvider}. Furthermore, it returns a generic command for
 * clone requests.
 * 
 * @author Tony Modica
 */
public abstract class MuvitorFlowLayoutEditPolicy extends FlowLayoutEditPolicy {
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#createChildEditPolicy(org
	 * .eclipse.gef.EditPart)
	 */
	@Override
	protected EditPolicy createChildEditPolicy(final EditPart child) {
		return new GhostFigureNonResizableEditPolicy();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected Command getCloneCommand(final ChangeBoundsRequest request) {
		final ArrayList<EObject> copies = new ArrayList<EObject>();
		for (final EditPart editPart : (List<EditPart>) request.getEditParts()) {
			copies.add(EcoreUtil.copy((EObject) editPart.getModel()));
		}
		final String copyString = ClipboardUtil.copyElementsToString(copies, null,
				new NullProgressMonitor());
		final PasteCommand pasteCommand = new PasteCommand((EObject) getHost().getModel(),
				copyString);
		return pasteCommand;
	}
	
}

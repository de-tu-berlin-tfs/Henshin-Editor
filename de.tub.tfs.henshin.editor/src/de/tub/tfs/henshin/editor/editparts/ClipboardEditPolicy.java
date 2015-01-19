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
 * ClipboardEditPolicy.java
 *
 * Created 21.12.2011 - 16:11:11
 */
package de.tub.tfs.henshin.editor.editparts;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.editparts.PasteRequest.IPasteRule;

/**
 * @author nam
 * 
 */
public class ClipboardEditPolicy extends AbstractEditPolicy {

	/**
	 * @param clipBoard
	 */
	public void performCopy(CopyRequest req) {
		req.getContents().add(EcoreUtil.copy((EObject) getHost().getModel()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#understandsRequest(org
	 * .eclipse.gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		Object type = req.getType();

		return (HenshinRequests.REQ_COPY.equals(type) && canCopy())
				|| (HenshinRequests.REQ_PASTE.equals(type) && canPaste(((PasteRequest) req)
						.getPastedObject()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.AbstractEditPolicy#getCommand(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public Command getCommand(Request request) {
		if (request instanceof PasteRequest) {
			return createPasteCommand((PasteRequest) request);
		}

		return super.getCommand(request);
	}

	/**
	 * @param req
	 * @return
	 */
	public EObject getPasteTarget(PasteRequest req) {
		return (EObject) getHost().getModel();
	}

	/**
	 * @param req
	 * @return
	 */
	protected EStructuralFeature getPasteFeature(PasteRequest req) {
		Object o = req.getPastedObject();

		if (o instanceof EObject) {
			EObject model = (EObject) getHost().getModel();

			for (EStructuralFeature f : model.eClass()
					.getEAllStructuralFeatures()) {
				if (f.getEType().isInstance(o)) {
					return f;
				}
			}

			// for convenient, tries to paste into container.
			if (model.eContainer() != null) {
				for (EStructuralFeature f : model.eContainer().eClass()
						.getEAllStructuralFeatures()) {
					if (f.getEType().isInstance(o)) {
						return f;
					}
				}
			}

			// tries to paste into target
			EObject target = getPasteTarget(req);

			if (target != null) {
				for (EStructuralFeature f : target.eClass()
						.getEAllStructuralFeatures()) {
					if (f.getEType().isInstance(o)) {
						return f;
					}
				}
			}
		}

		return null;
	}

	/**
	 * @param o
	 * @return
	 */
	protected boolean canPaste(Object o) {
		return true;
	}

	/**
	 * @return
	 */
	protected boolean canCopy() {
		return true;
	}

	/**
	 * @param req
	 * @return
	 */
	private Command createPasteCommand(PasteRequest req) {
		EObject toPaste = (EObject) req.getPastedObject();

		IPasteRule pasteRule = req.getPasteRule(toPaste.eClass());

		if (pasteRule != null) {
			pasteRule.preparePaste(toPaste, getHost());
		}

		return new SimpleAddEObjectCommand<EObject, EObject>(toPaste,
				getPasteFeature(req), getPasteTarget(req));
	}
}

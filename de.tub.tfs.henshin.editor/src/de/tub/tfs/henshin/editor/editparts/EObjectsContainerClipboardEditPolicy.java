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
 * EObjectsContainerClipboardEditPolicy.java
 *
 * Created 20.01.2012 - 16:36:17
 */
package de.tub.tfs.henshin.editor.editparts;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;

/**
 * @author nam
 * 
 */
public final class EObjectsContainerClipboardEditPolicy extends
		ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#canPaste(java
	 * .lang.Object)
	 */
	@Override
	protected boolean canPaste(Object o) {
		for (Object t : ((EContainerDescriptor) getHost().getModel())
				.getContainmentMap().keySet()) {
			if (((EClassifier) t).isInstance(o)) {
				return true;
			}
		}

		return o instanceof Layout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteTarget
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		EContainerDescriptor model = (EContainerDescriptor) getHost()
				.getModel();

		Object toPastedObject = req.getPastedObject();

		if (toPastedObject instanceof Layout) {
			return HenshinLayoutUtil.INSTANCE.getLayoutSystem(model
					.getContainer());
		}

		return model.getContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteFeature
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	protected EStructuralFeature getPasteFeature(PasteRequest req) {
		Object toPastedObject = req.getPastedObject();

		if (toPastedObject instanceof EObject) {
			EObject e = (EObject) toPastedObject;

			if (e instanceof Layout) {
				return HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS;
			}

			EContainerDescriptor model = (EContainerDescriptor) getHost()
					.getModel();

			for (Object o : model.getContainmentMap().keySet()) {
				EClassifier clazz = (EClassifier) o;

				if (clazz.isInstance(e)) {
					return (EStructuralFeature) model.getContainmentMap().get(
							clazz);
				}
			}
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#canCopy()
	 */
	@Override
	protected boolean canCopy() {
		return false;
	}
}
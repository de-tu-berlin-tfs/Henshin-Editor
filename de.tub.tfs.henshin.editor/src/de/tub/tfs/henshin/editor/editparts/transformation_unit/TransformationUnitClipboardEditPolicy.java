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
 * TransformationUnitClipboardEditPolicy.java
 *
 * Created 22.01.2012 - 00:47:50
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.util.HenshinUtil;

/**
 * @author nam
 * 
 */
public final class TransformationUnitClipboardEditPolicy extends
		ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteTarget
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		Unit model = (Unit) getHost().getModel();

		return HenshinUtil.INSTANCE.getTransformationSystem(model);
	}
}
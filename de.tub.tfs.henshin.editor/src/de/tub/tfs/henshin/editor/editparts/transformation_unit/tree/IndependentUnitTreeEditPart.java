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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class IndependentUnitTreeEditPart.
 */
public class IndependentUnitTreeEditPart extends
		TransformationUnitTreeEditPart<IndependentUnit> {

	/**
	 * Instantiates a new independent unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public IndependentUnitTreeEditPart(IndependentUnit model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		// Ressource nicht vorhanden, oder fehlerhaft, dann lieber kein Bild,
		// als Absturz
		// TODO Neue Image
		try {
			return IconUtil.getIcon("independent16.png");
		} catch (Exception e) {
			return null;
		}
	}

}

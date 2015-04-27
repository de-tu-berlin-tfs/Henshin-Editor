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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.TransformationUnitTreeEditPart;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author nam
 * 
 */
public class LoopUnitTreeEditPart extends
		TransformationUnitTreeEditPart<LoopUnit> {

	/**
	 * @param model
	 */
	public LoopUnitTreeEditPart(LoopUnit model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.LOOP.img(16);
	}
}

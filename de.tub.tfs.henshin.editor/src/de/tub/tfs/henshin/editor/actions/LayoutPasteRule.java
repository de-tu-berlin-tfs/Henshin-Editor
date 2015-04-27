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
 * LayoutPasteRule.java
 *
 * Created 21.01.2012 - 20:04:11
 */
package de.tub.tfs.henshin.editor.actions;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.EditPart;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.editparts.PasteRequest.IPasteRule;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.Layout;

public class LayoutPasteRule implements IPasteRule {

	/**
	 * @param container
	 */
	public LayoutPasteRule() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.PasteRequest.IPasteRule#preparePaste
	 * (java.lang.Object)
	 */
	@Override
	public void preparePaste(Object o, EditPart target) {
		Layout l = (Layout) o;

		ClipboardEditPolicy policy = (ClipboardEditPolicy) target
				.getEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE);

		Point p = HenshinLayoutUtil.INSTANCE.calcNodeInsertPosition(
				policy.getPasteTarget(new PasteRequest(l.getModel())),
				l.getX(), l.getY());

		l.setX(p.x());
		l.setY(p.y());
	}

}
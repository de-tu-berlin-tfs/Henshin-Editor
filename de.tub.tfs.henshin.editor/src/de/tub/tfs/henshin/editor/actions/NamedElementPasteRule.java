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
 * NamedElementPasteRule.java
 *
 * Created 21.01.2012 - 20:04:06
 */
package de.tub.tfs.henshin.editor.actions;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.gef.EditPart;

import de.tub.tfs.henshin.editor.editparts.PasteRequest.IPasteRule;
import de.tub.tfs.henshin.editor.util.ModelUtil;

public class NamedElementPasteRule implements IPasteRule {
	private int feature;

	private EObject root;

	/**
	 * @param feature
	 * @param root
	 */
	public NamedElementPasteRule(int feature, EObject root) {
		super();

		this.feature = feature;
		this.root = root;
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
		String name = ModelUtil.getNewChildDistinctName(root, feature,
				((NamedElement) o).getName() + "_copy");

		((NamedElement) o).setName(name);
	}
}
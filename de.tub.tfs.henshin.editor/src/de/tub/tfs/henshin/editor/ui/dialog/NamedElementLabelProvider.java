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
 * NamedElementLabelProvider.java
 *
 * Created 31.12.2011 - 14:36:11
 */
package de.tub.tfs.henshin.editor.ui.dialog;

import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * @author nam
 * 
 */
public class NamedElementLabelProvider extends LabelProvider {

	private Image icon;

	/**
	 * @param icon
	 */
	public NamedElementLabelProvider(Image icon) {
		super();

		this.icon = icon;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (icon != null) {
			return icon;
		}

		return super.getImage(element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof NamedElement) {
			return ((NamedElement) element).getName();
		}

		return super.getText(element);
	}
}

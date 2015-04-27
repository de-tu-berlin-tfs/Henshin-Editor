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
package de.tub.tfs.henshin.editor.model.properties.transformation_unit;

import java.util.ArrayList;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.tub.tfs.henshin.editor.internal.TransformationUnitPart;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class ConditionalUnitPartPropertySource.
 * 
 * @author Johann
 */
public class TransformationUnitPartPropertySource extends
		AbstractPropertySource<TransformationUnitPart<?>> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME
	}

	/**
	 * Instantiates a new conditional unit part property source.
	 * 
	 * @param model
	 *            the model
	 */
	public TransformationUnitPartPropertySource(TransformationUnitPart<?> model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.properties.AbstractPropertySource#createPropertyDescriptors()
	 */
	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		descriptorList.add(new PropertyDescriptor(ID.NAME, "Name"));
		return descriptorList.toArray(new IPropertyDescriptor[] {});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof ID) {
			switch ((ID) id) {
			case NAME:
				return getModel().getName();
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {

	}

}

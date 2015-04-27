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

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class ParameterPropertySource.
 * 
 * @author Johann
 */
public class ParameterPropertySource extends AbstractPropertySource<Parameter> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,

		DESCRIPTION

	}

	/**
	 * Instantiates a new parameter property source.
	 * 
	 * @param model
	 *            the model
	 */
	public ParameterPropertySource(Parameter model) {
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
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				ID.NAME, "Name");
		nameDescriptor.setValidator(new NameEditValidator(getModel().getUnit(),
				HenshinPackage.UNIT__PARAMETERS, getModel(),
				true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(ID.DESCRIPTION,
				"Description"));
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

			case DESCRIPTION:
				if (getModel().getDescription() == null) {
					return "";
				} else {
					return getModel().getDescription();
				}
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
		if (id instanceof ID) {
			switch ((ID) id) {
			case NAME:
				getModel().setName((String) value);
				break;
			case DESCRIPTION:
				getModel().setDescription((String) value);
				break;

			}
		}

	}

}

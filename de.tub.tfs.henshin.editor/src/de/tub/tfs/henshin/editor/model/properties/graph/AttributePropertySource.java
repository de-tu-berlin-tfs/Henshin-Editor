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
package de.tub.tfs.henshin.editor.model.properties.graph;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.AttributeTypes;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class AttributePropertySource.
 */
public class AttributePropertySource extends AbstractPropertySource<Attribute> {

	/** The e attributes. */
	private Vector<EAttribute> eAttributes;

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,
		/** The TYPE. */
		TYPE,
		/** The VALUE. */
		VALUE
	}

	/**
	 * The Enum ValueType.
	 */
	private static enum ValueType {

		/** The BOOLEAN. */
		BOOLEAN,
		/** The OTHER. */
		OTHER
	}

	/** The value type. */
	private ValueType valueType;

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };

	/**
	 * Instantiates a new attribute property source.
	 * 
	 * @param model
	 *            the model
	 */
	public AttributePropertySource(Attribute model) {
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
		eAttributes = new Vector<EAttribute>();
		eAttributes.addAll(AttributeTypes.getFreeAttributeTypes(getModel()
				.getNode(), getModel().getType()));
		descriptorList.add(new PropertyDescriptor(ID.NAME, "Name"));
		descriptorList.add(new PropertyDescriptor(ID.TYPE, "Type"));
		valueType = getValueType();

		final Node node = (Node) getModel().eContainer();
		final NodeLayout layout = HenshinLayoutUtil.INSTANCE.getLayout(node);
		if (layout != null) {
			if (layout.isEnabled()) {
				switch (valueType) {
				case BOOLEAN:
					descriptorList.add(new ComboBoxPropertyDescriptor(ID.VALUE,
							"Value", booleanValue));
					break;
				case OTHER:
					TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
							ID.VALUE, "Value");
					nameDescriptor.setValidator(new TypeEditorValidator(
							getModel()));
					descriptorList.add(nameDescriptor);
				}
			} else {
				descriptorList.add(new PropertyDescriptor(ID.VALUE, "Value"));
			}
		}

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
				if (getModel().getType() != null) {
					return getModel().getType().getName();
				}
				return "";
			case TYPE:
				if (getModel().getType() != null) {
					return getModel().getType().getEAttributeType().getName();
				}
				return "";
			case VALUE:
				switch (valueType) {
				case BOOLEAN:
					if (getModel().getValue().equals("true"))
						return 0;
					else if (getModel().getValue().equals("false"))
						return 1;
					else
						return -1;
				case OTHER:
					return getModel().getValue();
				}
				break;
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
			case VALUE:
				switch (valueType) {
				case BOOLEAN:
					getModel().setValue(booleanValue[(Integer) value]);
					break;
				case OTHER:
					getModel().setValue((String) value);
					break;
				}
				break;
			}

		}
	}

	/**
	 * Gets the value type.
	 * 
	 * @return Index für BOOLEAN oder OTHER
	 */
	private ValueType getValueType() {
		if (getModel().getType().getEAttributeType().getName()
				.equals("EBoolean")) {
			if ((new TypeEditorValidator(getModel())).parametersAllowed())
				return ValueType.OTHER;
			else
				return ValueType.BOOLEAN;
		}
		return ValueType.OTHER;
	}
}

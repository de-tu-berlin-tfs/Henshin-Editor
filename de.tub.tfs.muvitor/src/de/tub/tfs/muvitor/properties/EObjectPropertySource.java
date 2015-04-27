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
package de.tub.tfs.muvitor.properties;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * This class is intended to be a generic property source for all the objects in
 * the application's model. It currently only supports String and boolean types,
 * and doesn't support reset or nested properties. It uses information in the
 * EMF EAttribute and EDataType classes to get the id, display name, and type
 * information.
 * 
 */
public class EObjectPropertySource implements IPropertySource {
	private final EObject object;
	
	public EObjectPropertySource(final EObject obj) {
		object = obj;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
	 */
	@Override
	public Object getEditableValue() {
		return object;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
	 */
	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		final Collection<PropertyDescriptor> descriptors = new ArrayList<PropertyDescriptor>();
		for (final EAttribute attr : object.eClass().getEAllAttributes()) {
			final EDataType type = attr.getEAttributeType();
			final String featureID = Integer.toString(object.eClass().getFeatureID(attr));
			final String name = attr.getName();
			if (attr.isID()) {
				// shouldn't be editable
				descriptors.add(new PropertyDescriptor(featureID, name));
			} else if (type.getInstanceClass() == String.class) {
				descriptors.add(new TextPropertyDescriptor(featureID, name));
			} else if (type.getInstanceClass() == boolean.class) {
				descriptors.add(new CheckboxPropertyDescriptor(featureID, name));
			} else if (type.getInstanceClass() == int.class) {
				descriptors.add(new TextPropertyDescriptor(featureID, name));
			}
			
		}
		return descriptors.toArray(new IPropertyDescriptor[] {});
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public Object getPropertyValue(final Object idobj) {
		final int id = Integer.parseInt((String) idobj);
		final EStructuralFeature feature = object.eClass().getEStructuralFeature(id);
		final Object result = object.eGet(feature);
		return result != null ? result : "";
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang
	 * .Object)
	 */
	@Override
	public boolean isPropertySet(final Object id) {
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySouce#resetPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public void resetPropertyValue(final Object id) {
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(final Object id, final Object value) {
		final EStructuralFeature feature = object.eClass().getEStructuralFeature(
				Integer.parseInt((String) id));
		object.eSet(feature, value);
	}
	
}

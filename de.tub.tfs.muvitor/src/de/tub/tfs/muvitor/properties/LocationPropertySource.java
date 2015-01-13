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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Property source for location data.
 * 
 * @author Tony Modica
 */
public class LocationPropertySource extends AbstractPropertySource<Point> {
	
	private static enum ID {
		X, Y
	}
	
	public LocationPropertySource(final int x, final int y) {
		super(new Point(x, y));
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public Object getPropertyValue(final Object id) {
		switch ((ID) id) {
			case X:
				return String.valueOf(getModel().x);
			case Y:
				return String.valueOf(getModel().y);
			default:
				return null;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(final Object id, final Object value) {
		switch ((ID) id) {
			case X:
				getModel().x = Integer.valueOf((String) value).intValue();
				break;
			case Y:
				getModel().y = Integer.valueOf((String) value).intValue();
				break;
			default:
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point(" + getModel().x + "," + getModel().y + ")";//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
	}
	
	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		// Input for the x- and y-Coordinates should be validated if it is a
		// number.
		final PropertyDescriptor xProp = new TextPropertyDescriptor(ID.X, "x");
		xProp.setValidator(NumberCellEditorValidator.instance(false));
		final PropertyDescriptor yProp = new TextPropertyDescriptor(ID.Y, "y");
		yProp.setValidator(NumberCellEditorValidator.instance(false));
		return new IPropertyDescriptor[] { xProp, yProp };
	}
	
}

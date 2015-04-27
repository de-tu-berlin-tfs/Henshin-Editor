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

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;
import de.tub.tfs.muvitor.properties.NumberCellEditorValidator;

/**
 * The Class NodePropertySource.
 */
public class NodePropertySource extends AbstractPropertySource<Node> {

	/** The node layout. */
	private NodeLayout nodeLayout;

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,
		/** The TYPE. */
		TYPE,
		/** The X. */
		X,
		/** The Y. */
		Y
	}

	/**
	 * Instantiates a new node property source.
	 * 
	 * @param model
	 *            the model
	 */
	public NodePropertySource(Node model) {
		super(model);
		nodeLayout = null;
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
		nameDescriptor.setValidator(new NameEditValidator(
				getModel().getGraph(), HenshinPackage.GRAPH__NODES, getModel(),
				false));
		descriptorList.add(nameDescriptor);

		descriptorList.add(new PropertyDescriptor(ID.TYPE, "Type"));
		TextPropertyDescriptor xDescriptor = new TextPropertyDescriptor(ID.X,
				"X");
		xDescriptor.setValidator(new NumberCellEditorValidator(true));
		descriptorList.add(xDescriptor);
		TextPropertyDescriptor yDescriptor = new TextPropertyDescriptor(ID.Y,
				"Y");
		yDescriptor.setValidator(new NumberCellEditorValidator(true));
		descriptorList.add(yDescriptor);
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
				if (getModel().getName() == null) {
					return new String();
				}
				return new String(getModel().getName());
			case TYPE:
				return getModel().getType().getName();
			case X:
				if (getLayout() != null) {
					return new String(
							(new Integer(getLayout().getX())).toString());
				}
				return new String("0");
			case Y:
				if (getLayout() != null) {
					return new String(
							(new Integer(getLayout().getY())).toString());
				}
				return new String("0");
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
			case X:
				getLayout().setX(Integer.parseInt((String) value));
				break;

			case Y:
				getLayout().setY(Integer.parseInt((String) value));
				break;

			}
		}
	}

	/**
	 * Gets the layout.
	 * 
	 * @return the layout
	 */
	private NodeLayout getLayout() {
		if (nodeLayout == null) {
			nodeLayout = HenshinLayoutUtil.INSTANCE.getLayout(getModel());
		}
		return nodeLayout;
	}

}

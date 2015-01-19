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
 * FlowDiagramPropertySource.java
 * created on 15.04.2012 11:12:48
 */
package de.tub.tfs.henshin.editor.model.properties.flow_diagram;

import java.util.ArrayList;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditorValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 *
 */
public class FlowDiagramPropertySource extends
		AbstractPropertySource<FlowDiagram> {
	
	private static enum ID {
		
		NAME,
		
		STRICT,
		
		ROLLBACK,
		
		FLOW_ELEMENTS,
		
		TRANSITIONS,
		
		PARAMETER_MAPPINGS
	}

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };
	
	public FlowDiagramPropertySource(FlowDiagram model) {
		super(model);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof ID) {
			int numberOfFLowElements = getModel().getElements().size();
			int numberOfTransitions = getModel().getTransitions().size();
			int numberOfParameterMappings = getModel().getParameterMappings().size();
			switch ((ID) id) {
			case NAME:
				return getModel().getName();
			case STRICT:
				return getModel().isStrict() ? 0 : 1;
			case ROLLBACK:
				return getModel().isRollback() ? 0 : 1;
			case FLOW_ELEMENTS:
				return numberOfFLowElements;
			case TRANSITIONS:
				return numberOfTransitions;
			case PARAMETER_MAPPINGS:
				return numberOfParameterMappings;
			}
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id instanceof ID) {
			switch ((ID) id) {
			case NAME:
				getModel().setName((String) value);
				break;
			case STRICT:
				if (((Integer) value) == 0) {
					getModel().setStrict(true);
				}
				else {
					getModel().setStrict(false);
				}
				break;
			case ROLLBACK:
				if (((Integer) value) == 0) {
					getModel().setRollback(true);
				}
				else {
					getModel().setRollback(false);
				}
				break;
			}
		}
	}

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				ID.NAME, Messages.PROPERTY_NAME);
		nameDescriptor.setValidator(new NameEditorValidator(FlowControlUtil.INSTANCE.getFlowControlSystem((EObject) getModel()), FlowControlPackage.FLOW_DIAGRAM, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.STRICT, Messages.PROPERTY_STRICT, booleanValue));
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.ROLLBACK, Messages.PROPERTY_ROLLBACK, booleanValue));
		descriptorList.add(new PropertyDescriptor(ID.FLOW_ELEMENTS, Messages.PROPERTY_NUMBER_OF_FLOW_ELEMENTS));
		descriptorList.add(new PropertyDescriptor(ID.TRANSITIONS, Messages.PROPERTY_NUMBER_OF_TRANSITIONS));
		descriptorList.add(new PropertyDescriptor(ID.PARAMETER_MAPPINGS, Messages.PROPERTY_NUMBER_OF_PARAMETER_MAPPINGS));
		return null;
	}

}

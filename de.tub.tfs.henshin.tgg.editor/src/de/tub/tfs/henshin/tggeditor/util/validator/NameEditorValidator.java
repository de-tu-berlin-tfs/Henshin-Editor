/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.validator;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;

public class NameEditorValidator implements ICellEditorValidator,
		IInputValidator {

	/** The parent. */
	private final EObject parent;
	
	/** The child feature id. */
	private final int childFeatureID;
	
	/** The not empty. */
	private boolean notEmpty;
	
	/** The named element. */
	private final NamedElement namedElement;

	/**
	 * Instantiates a new name editor validator.
	 *
	 * @param parent the parent
	 * @param childFeatureID the child feature id
	 * @param element the element
	 * @param notEmpty the not empty
	 */
	public NameEditorValidator(EObject parent, int childFeatureID,NamedElement element,boolean notEmpty) {
		super();
		this.namedElement = element;
		this.parent = parent;
		this.childFeatureID = childFeatureID;
		this.notEmpty = notEmpty;
	}

	
	/**
	 * Instantiates a new name editor validator.
	 *
	 * @param parent the parent
	 * @param childFeatureID the child feature id
	 * @param notEmpty the not empty
	 */
	public NameEditorValidator(EObject parent, int childFeatureID, boolean notEmpty) {
		super();
		this.namedElement = null;
		this.parent = parent;
		this.childFeatureID = childFeatureID;
		this.notEmpty = notEmpty;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
	 */
	@Override
	public String isValid(Object value) {
		if (value instanceof String) {
			return isValid((String) value);
		}
		return "Is not String!";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String isValid(String text) {
		String disallowed = "+-:;!\"§$%&/()=?*/#~<>|.,";
		for (int i = 0; i < text.length(); i++) {
			if (disallowed.indexOf(text.charAt(i)) >= 0) {
				return "Wrong charakter in name!";
			}
		}

		if ((text == null || text.trim().length() == 0) && notEmpty) {
			return "Name can not be empty!";
		}

		EStructuralFeature feature = parent.eClass().getEStructuralFeature(
				childFeatureID);
		if (feature != null) {
			if (feature.isMany()) {
				try {
					Collection<NamedElement> list = (Collection<NamedElement>) parent
							.eGet(feature);
					for (NamedElement element : list) {
						if (element!=namedElement && text.equals(element.getName())) {
							return "The name \""
									+ text
									+ "\" exist already! Please enter another one.";
						}
					}
				} catch (ClassCastException e) {

				}
			}
		}
		return null;
	}

}

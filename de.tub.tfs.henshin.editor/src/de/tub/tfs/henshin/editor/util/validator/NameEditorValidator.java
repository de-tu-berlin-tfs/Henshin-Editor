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
package de.tub.tfs.henshin.editor.util.validator;

import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;

/**
 * The Class NameEditorValidator.
 */
public class NameEditorValidator implements ICellEditorValidator,
		IInputValidator {

	/** The parent. */
	private final EObject parent;

	/** The child feature id. */
	private final int childFeatureID;

	/** The not empty. */
	private boolean notEmpty;

	/** The named element. */
	private final EObject namedElement;

	private final int nameFeatureID;

	/**
	 * @param parent
	 * @param childFeatureID
	 * @param notEmpty
	 * @param namedElement
	 * @param nameFeatureID
	 */
	public NameEditorValidator(EObject parent, int childFeatureID,
			boolean notEmpty, EObject namedElement, int nameFeatureID) {
		super();

		this.parent = parent;
		this.childFeatureID = childFeatureID;
		this.notEmpty = notEmpty;
		this.namedElement = namedElement;
		this.nameFeatureID = nameFeatureID;
	}

	/**
	 * Instantiates a new name editor validator.
	 * 
	 * @param parent
	 *            the parent
	 * @param childFeatureID
	 *            the child feature id
	 * @param element
	 *            the element
	 * @param notEmpty
	 *            the not empty
	 */
	public NameEditorValidator(EObject parent, int childFeatureID,
			NamedElement element, boolean notEmpty) {
		this(parent, childFeatureID, notEmpty, element,
				HenshinPackage.NAMED_ELEMENT__NAME);
	}

	/**
	 * Instantiates a new name editor validator.
	 * 
	 * @param parent
	 *            the parent
	 * @param childFeatureID
	 *            the child feature id
	 * @param notEmpty
	 *            the not empty
	 */
	public NameEditorValidator(EObject parent, int childFeatureID,
			boolean notEmpty) {
		this(parent, childFeatureID, notEmpty, null,
				HenshinPackage.NAMED_ELEMENT__NAME);
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

		return "Is not String.";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String isValid(String text) {
		if ((text == null || text.trim().length() == 0) && notEmpty) {
			return "Name can not be empty.";
		}
		
		String disallowed = "+-:;!\"�$%&/()=?*/#~<>|.,";
		for (int i = 0; i < text.length(); i++) {
			if (disallowed.indexOf(text.charAt(i)) >= 0) {
				return "Name contains unknown character (" + text.charAt(i)
						+ ").";
			}
		}

		EStructuralFeature feature = parent.eClass().getEStructuralFeature(
				childFeatureID);

		if (feature != null) {
			if (feature.isMany()) {
				try {
					Collection<EObject> list = (Collection<EObject>) parent
							.eGet(feature);

					for (EObject element : list) {
						EStructuralFeature nameFeature = element.eClass()
								.getEStructuralFeature(nameFeatureID);
						String name = (String) element.eGet(nameFeature);

						if (element != namedElement && text.equals(name)) {
							return "The name \""
									+ text
									+ "\" already exists, please provide another one.";
						}
					}
				} catch (ClassCastException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

}

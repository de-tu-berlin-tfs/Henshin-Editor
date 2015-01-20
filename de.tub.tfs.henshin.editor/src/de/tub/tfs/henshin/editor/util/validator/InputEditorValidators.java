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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IInputValidator;

/**
 * The Class InputEditorValidators.
 */
public class InputEditorValidators implements IInputValidator {

	/** The validators. */
	private List<IInputValidator> validators;

	/**
	 * Instantiates a new input editor validators.
	 * 
	 * @param validators
	 *            the validators
	 */
	public InputEditorValidators(List<IInputValidator> validators) {
		super();
		this.validators = validators;
	}

	/**
	 * Instantiates a new input editor validators.
	 */
	public InputEditorValidators() {
		super();
		this.validators = new ArrayList<IInputValidator>();
	}

	/**
	 * Instantiates a new input editor validators.
	 * 
	 * @param validator
	 *            the validator
	 */
	public InputEditorValidators(IInputValidator validator) {
		super();
		this.validators = new ArrayList<IInputValidator>();
		this.validators.add(validator);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	@Override
	public String isValid(String newText) {
		for (IInputValidator validator : validators) {
			String result = validator.isValid(newText);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Adds the validator.
	 * 
	 * @param validator
	 *            the validator
	 */
	public void addValidator(IInputValidator validator) {
		this.validators.add(validator);
	}
}

package de.tub.tfs.henshin.tggeditor.util.validator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IInputValidator;

public class InputEditorValidators implements IInputValidator {

	/** The validators. */
	private List<IInputValidator> validators;
	
	
	
	/**
	 * Instantiates a new input editor validators.
	 *
	 * @param validators the validators
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
	 * @param validator the validator
	 */
	public InputEditorValidators(IInputValidator validator) {
		super();
		this.validators = new ArrayList<IInputValidator>();
		this.validators.add(validator);
	}

	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	@Override
	public String isValid(String newText) {
		for (IInputValidator validator:validators){
			String result=validator.isValid(newText);
			if (result!=null){
				return result;
			}
		}
		return null;
	}

	
	/**
	 * Adds the validator.
	 *
	 * @param validator the validator
	 */
	public void addValidator(IInputValidator validator){
		this.validators.add(validator);
	}

}

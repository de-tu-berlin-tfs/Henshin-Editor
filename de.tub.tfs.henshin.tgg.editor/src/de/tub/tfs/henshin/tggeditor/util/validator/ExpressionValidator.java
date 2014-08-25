/*******************************************************************************
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.util.validator;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tggeditor.util.ModelUtil;


/**
 * The Class ExpressionValidator.
 */
public class ExpressionValidator {

	/** The parameters. */
	private List<String> parameters;

	/** The used parameters. */
	private List<String> usedParameters;

	/** The script engine manager. */
	private ScriptEngineManager scriptEngineManager;

	/** The type. */
	private EAttribute type;

	/** The expression. */
	private String expression;

	/**
	 * Instantiates a new expression validator.
	 * 
	 * @param attribute
	 *            the attribute
	 */
	public ExpressionValidator(Attribute attribute) {
		super();
		this.type = attribute.getType();
		this.expression = attribute.getValue();
		this.scriptEngineManager = new ScriptEngineManager();
		Rule rule = ModelUtil.getRule(attribute);
		parameters = new Vector<String>();
		if (rule != null) {
			for (Parameter param: rule.getParameters())
				parameters.add(param.getName());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	/**
	 * Checks if is valid.
	 * 
	 * @param paramter2Object
	 *            the paramter2 object
	 * @return the string
	 */
	public String isValid(Map<String, Object> paramter2Object) {
		if (expression.isEmpty()) {
			return type.getName() + " can not be empty!";
		}
		for (String parameter : getUsedParameters(expression)) {
			if (!paramter2Object.containsKey(parameter)) {
				return null;
			}
		}

		ScriptEngine scriptEngine = scriptEngineManager
				.getEngineByName("JavaScript");
		for (String name : paramter2Object.keySet()) {
			scriptEngine.put(name, paramter2Object.get(name));
		}
		try {
			Object object = scriptEngine.eval(expression);
			String valueString = object.toString();
			if (valueString.endsWith(".0"))
				valueString = valueString
						.substring(0, valueString.length() - 2);

			object = EcoreUtil.createFromString(type.getEAttributeType(),
					valueString);
			return null;
		} catch (Exception e) {

		}
		return "Value for " + type.getName() + "is not valid!";
	}

	/**
	 * Gets the used parameters.
	 * 
	 * @param value
	 *            the value
	 * @return the used parameters
	 */
	public synchronized List<String> getUsedParameters(String value) {
		usedParameters = new Vector<String>();
		String disallowed = "+-:;!\"�$%&/()=?*/#~<>|.,";
		for (String var : parameters) {
			int index = value.indexOf(var);
			if (index != -1) {
				if (index == 0
						|| disallowed.indexOf(value.charAt(index - 1)) != -1) {
					int lastIndex = index + var.length();
					if (lastIndex > value.length() - 1
							|| disallowed.indexOf(value.charAt(lastIndex)) != -1) {
						usedParameters.add(var);
					}
				}
			}
		}
		return usedParameters;
	}

	public Object getObject(String value) {
		Object object;
		try {
			object = EcoreUtil
					.createFromString(type.getEAttributeType(), value);
		} catch (Exception e) {
			object = value;
		}
		return object;
	}

}

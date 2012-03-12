/**
 * 
 */
package tggeditor.util.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.viewers.ICellEditorValidator;

import tggeditor.util.ModelUtil;

/**
 * The Class TypeEditorValidator.
 */
public class TypeEditorValidator implements ICellEditorValidator {

	/** The parameters. */
	private List<String> parameters;
	
	/** The used parameters. */
	private List<String> usedParameters;
	
	/** The type. */
	private EAttribute type;
	
	/** The operations allowed. */
	private boolean operationsAllowed;

	/**
	 * Instantiates a new type editor validator.
	 *
	 * @param node the node
	 * @param type the type
	 */
	public TypeEditorValidator(Node node, EAttribute type) {
		super();
		newTypeEditorValidator(node, type);

	}

	/**
	 * Instantiates a new type editor validator.
	 *
	 * @param attribute the attribute
	 */
	public TypeEditorValidator(Attribute attribute) {
		super();
		newTypeEditorValidator(attribute.getNode(), attribute.getType());
	}

	/**
	 * New type editor validator.
	 *
	 * @param node the node
	 * @param type the type
	 */
	private void newTypeEditorValidator(final Node node, final EAttribute type) {
		this.parameters = new ArrayList<String>();
		this.usedParameters = new Vector<String>();
		this.operationsAllowed = false;
		this.type = type;
		Rule rule = ModelUtil.getRule(node);
		if (rule != null) {
			for (Parameter par: rule.getParameters())
				parameters.add(par.getName());
			if (rule.getRhs() == node.getGraph()) {
				operationsAllowed = true;
			}
		}
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
			String valueString = (String) value;
			if (valueString.isEmpty()) {
				return type.getName() + " can not be empty!";
			}
			if (operationsAllowed) {
				return null;
			} else {
				usedParameters.clear();
				if (!isParameter(valueString)) {
					try {
						EcoreUtil.createFromString(type.getEAttributeType(),
								(String) value);
						return null;
					} catch (Exception e) {

					}
				} else {
					return null;
				}
			}
		}
		return "Value for " + type.getName() + " is not valid!";

	}

	/**
	 * Checks if is parameter.
	 *
	 * @param name the name
	 * @return true, if is parameter
	 */
	private boolean isParameter(String name) {
		if (parameters.contains(name)) {
			usedParameters.add(name);
			return true;
		}
		return false;
	}

	/**
	 * Parameters allowed.
	 *
	 * @return true, if successful
	 */
	public boolean parametersAllowed() {
		return parameters.size() > 0;
	}

	/**
	 * Gets the used parameters.
	 *
	 * @return the used parameters
	 */
	public synchronized List<String> getUsedParameters() {
		return usedParameters;
	}

	/**
	 * Gets the default value.
	 *
	 * @return the default value
	 */
	public String getDefaultValue() {
		if (type.getEAttributeType().getName().equals("EString")
				|| type.getEAttributeType().getName().equals("String")) {
			return "\"\"";
		}
		if (type.getDefaultValue()!=null){
			return type.getDefaultValue().toString();
		}
		else{
			return "";
		}
	}

	/**
	 * Gets the default value object.
	 *
	 * @return the default value object
	 */
	public Object getDefaultValueObject() {
		if (type.getEAttributeType().getName().equals("EString")
				|| type.getEAttributeType().getName().equals("String")) {
			return new String();
		}
		return type.getDefaultValue();
	}

}

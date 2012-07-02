/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.rule;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class RulePropertySource.
 * 
 * @author Johann
 */
public class RulePropertySource extends AbstractPropertySource<Rule> {


	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };

	/**
	 * Instantiates a new rule property source.
	 * 
	 * @param model
	 *            the model
	 */
	public RulePropertySource(Rule model) {
		super(model);
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
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				index++, Messages.PROPERTY_NAME);
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE.getTransformationSystem(getModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(index++,
				Messages.PROPERTY_DESCRIPTION));

		descriptorList.add(new ComboBoxPropertyDescriptor(index++,
				Messages.PROPERTY_ACTIVATED, booleanValue));
		
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_ATTRIBUTE_CONDITIONS));
		
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_MAPPINGS));
		
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_PARAMETERS));

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
		if (id instanceof Integer) {
			int numberOfAttributeConditions = getModel().getAttributeConditions().size();
			int numberOfMappings = getModel().getMappings().size();
			int numberOfParameters = getModel().getParameters().size();
			switch ((Integer) id) {
			case 0:
				return getModel().getName();
			case 1:
				return getModel().getDescription();
			case 2:
				if (getModel().isActivated())
					return 0;
				else
					return 1;
			case 3: 
				return numberOfAttributeConditions;
			case 4: 
				return numberOfMappings;
			case 5:
				return numberOfParameters;
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
		if (id instanceof Integer) {
			switch ((Integer) id) {
			case 0:
				getModel().setName((String) value);
				break;
			case 1:
				getModel().setDescription((String) value);
				break;
			case 2:
				if (((Integer) value) == 0) {
					getModel().setActivated(true);
				} else {
					getModel().setActivated(false);
				}
			}
		}

	}

}

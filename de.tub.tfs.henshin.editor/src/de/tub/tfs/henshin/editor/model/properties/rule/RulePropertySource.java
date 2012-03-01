/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.rule;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class RulePropertySource.
 * 
 * @author Johann
 */
public class RulePropertySource extends AbstractPropertySource<Rule> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,

		DESCRIPTION,
		/** The AKTIVATED. */
		ACTIVATED

	}

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
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				ID.NAME, "Name");
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE.getTransformationSystem(getModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(ID.DESCRIPTION,
				"Description"));

		descriptorList.add(new ComboBoxPropertyDescriptor(ID.ACTIVATED,
				"Aktivated", booleanValue));

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
				return getModel().getName();
			case DESCRIPTION:
				if (getModel().getDescription() == null) {
					return "";
				} else {
					return getModel().getDescription();
				}
			case ACTIVATED:
				if (getModel().isActivated())
					return 0;
				else
					return 1;
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
			case DESCRIPTION:
				getModel().setDescription((String) value);
				break;
			case ACTIVATED:
				if (((Integer) value) == 0) {
					getModel().setActivated(true);
				} else {
					getModel().setActivated(false);
				}
			}
		}

	}

}

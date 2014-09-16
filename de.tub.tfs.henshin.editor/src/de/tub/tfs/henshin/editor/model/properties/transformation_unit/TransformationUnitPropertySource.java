/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.transformation_unit;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class TransformationUnitPropertySource.
 * 
 * @author Johann
 */
public class TransformationUnitPropertySource extends
		AbstractPropertySource<Unit> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,

		DESCRIPTION,

		/** The ACTIVATED. */
		ACTIVATED,
		
		IF,
		
		THEN,
		
		ELSE,
		
		SUBUNIT,
		
		PARAMETER
	}

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };

	/**
	 * Instantiates a new transformation unit property source.
	 * 
	 * @param model
	 *            the model
	 */
	public TransformationUnitPropertySource(Unit model) {
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
				ID.NAME, Messages.PROPERTY_NAME);
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE
				.getTransformationSystem(getModel()),
				HenshinPackage.MODULE__UNITS,
				getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(ID.DESCRIPTION,
				Messages.PROPERTY_DESCRIPTION));
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.ACTIVATED,
				Messages.PROPERTY_ACTIVATED, booleanValue));

		if (getModel() instanceof ConditionalUnit) {
			descriptorList.add(new PropertyDescriptor(ID.IF, Messages.PROPERTY_IF));
			descriptorList.add(new PropertyDescriptor(ID.THEN, Messages.PROPERTY_THEN));
			descriptorList.add(new PropertyDescriptor(ID.ELSE, Messages.PROPERTY_ELSE));
		}
		else {
			descriptorList.add(new PropertyDescriptor(ID.SUBUNIT, Messages.PROPERTY_NUMBER_OF_SUBUNITS));
		}
		
		descriptorList.add(new PropertyDescriptor(ID.PARAMETER, Messages.PROPERTY_NUMBER_OF_PARAMETERS));
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
			int numberOfSubunits = getModel().getSubUnits(false).size();
			int numberOfParameters = getModel().getParameters().size();
			String ifSubunit = "";
			String thenSubunit = "";
			String elseSubunit = "";
			if (getModel() instanceof ConditionalUnit) {
				ConditionalUnit conditionalUnit = (ConditionalUnit) getModel();
				ifSubunit = conditionalUnit.getIf() == null ? "" : conditionalUnit.getIf().getName();
				thenSubunit = conditionalUnit.getThen() == null ? "" : conditionalUnit.getThen().getName();
				elseSubunit = conditionalUnit.getElse() == null ? "" : conditionalUnit.getElse().getName();
			}
			switch ((ID) id) {
			case NAME:
				return getModel().getName();
			case DESCRIPTION:
				return getModel().getDescription();
			case ACTIVATED:
				if (getModel().isActivated())
					return 0;
				else
					return 1;
			case IF:
				return ifSubunit;
			case THEN:
				return thenSubunit;
			case ELSE:
				return elseSubunit;
			case SUBUNIT:
				return numberOfSubunits;
			case PARAMETER:
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

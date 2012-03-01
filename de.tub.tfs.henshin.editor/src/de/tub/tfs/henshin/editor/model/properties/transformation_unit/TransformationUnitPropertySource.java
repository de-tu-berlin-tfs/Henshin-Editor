/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.transformation_unit;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class TransformationUnitPropertySource.
 * 
 * @author Johann
 */
public class TransformationUnitPropertySource extends
		AbstractPropertySource<TransformationUnit> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,

		DESCRIPTION,

		/** The AKTIVATED. */
		AKTIVATED
	}

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };

	/**
	 * Instantiates a new transformation unit property source.
	 * 
	 * @param model
	 *            the model
	 */
	public TransformationUnitPropertySource(TransformationUnit model) {
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
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE
				.getTransformationSystem(getModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(ID.DESCRIPTION,
				"Description"));
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.AKTIVATED,
				"Activated", booleanValue));

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
			case AKTIVATED:
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
			case AKTIVATED:
				if (((Integer) value) == 0) {
					getModel().setActivated(true);
				} else {
					getModel().setActivated(false);
				}
			}
		}

	}

}

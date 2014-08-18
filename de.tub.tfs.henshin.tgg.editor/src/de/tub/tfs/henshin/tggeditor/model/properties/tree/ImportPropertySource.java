/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;


/**
 * The Class AttributePropertySource.
 */
public class ImportPropertySource extends AbstractPropertySource<ImportedPackage> {

	/** The e attributes. */
	private Vector<EAttribute> eAttributes;

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The NAME. */
		NAME,
		/** The URI. */
		URI,
		/** The indicator whether instance model shall be loaded with default values. */
		LOAD_WITH_DEFAULT_VALUES
		
	}

	/**
	 * The Enum ValueType.
	 */
	private static enum ValueType {

		/** The BOOLEAN. */
		BOOLEAN,
		/** The OTHER. */
		OTHER
	}

	/** The value type. */
	private ValueType valueType;

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "true", "false" };

	/**
	 * Instantiates a new attribute property source.
	 *
	 * @param model the model
	 */
	public ImportPropertySource(ImportedPackage model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * @see muvitorkit.properties.AbstractPropertySource#createPropertyDescriptors()
	 */
	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();

		descriptorList.add(new PropertyDescriptor(ID.NAME, "Name"));
		descriptorList.add(new PropertyDescriptor(ID.URI, "nsURI"));
//		descriptorList.add(new PropertyDescriptor(ID.LOAD_WITH_DEFAULT_VALUES, "Load with default values"));
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.LOAD_WITH_DEFAULT_VALUES,
				"Load with default values", booleanValue));

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
				if (getModel().getPackage() != null) {
					return getModel().getPackage().getName();
				}
				return "";
			case LOAD_WITH_DEFAULT_VALUES:
				if (getModel() != null) {
					if (getModel().isLoadWithDefaultValues() == true)
						return 0;
					else if (getModel().isLoadWithDefaultValues()==false)
						return 1;
					else
						return -1;
				}
				return "";
			case URI:
				if (getModel().getPackage() != null) {
					return getModel().getPackage().getNsURI();
				}
				return "";
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
				getModel().getPackage().setName((String)value);
			case URI:
				getModel().getPackage().setNsURI((String)value);
			case LOAD_WITH_DEFAULT_VALUES:
				if ((Integer)value==0)
					getModel().setLoadWithDefaultValues(true);
				if ((Integer)value==1)
					getModel().setLoadWithDefaultValues(false);
			default:
				break;
			}
		}
	}

	


	
//	/**
//	 * Gets the value type.
//	 * 
//	 * @return Index für BOOLEAN oder OTHER
//	 */
//	private ValueType getValueType() {
//		if (getModel().getType().getEAttributeType().getName().equals(
//				"EBoolean")) {
//			if ((new TypeEditorValidator(getModel())).parametersAllowed())
//				return ValueType.OTHER;
//			else
//				return ValueType.BOOLEAN;
//		}
//		return ValueType.OTHER;
//	}
}

/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.condition;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.model.properties.graph.GraphPropertySource;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.SendNotify;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * The Class GraphPropertySource.
 * 
 * @author Angeline Warning
 */
public class ApplicationConditionPropertySource extends GraphPropertySource {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The application condition's name. */
		NAME,
		/** The negated value. */
		NEGATED
	}

	/** The Constant booleanValue. */
	static final String[] booleanValue = { "false", "true" };

	/**
	 * Instantiates a new graph property source.
	 * 
	 * @param model
	 *            the model
	 */
	public ApplicationConditionPropertySource(Graph model) {
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
		final TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				ID.NAME, "Name");
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE
				.getTransformationSystem(getModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new ComboBoxPropertyDescriptor(ID.NEGATED,
				"Negated", booleanValue));

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
			final NestedCondition nc = (NestedCondition) getModel()
					.eContainer();
			switch ((ID) id) {
			case NAME:
				getModel().setName((String) value);
				break;
			}

			SendNotify.sendAddFormulaNotify(nc.eContainer(), nc);
		}
	}

}

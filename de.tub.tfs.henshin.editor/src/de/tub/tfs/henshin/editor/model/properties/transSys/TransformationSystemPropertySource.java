/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.transSys;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class TransformationSystemPropertySource.
 * 
 * @author Johann
 */
public class TransformationSystemPropertySource extends
		AbstractPropertySource<TransformationSystem> {

	/**
	 * Konstruktor erhält Transformationssystem.
	 * 
	 * @param model
	 *            TransformationsSystem
	 */
	public TransformationSystemPropertySource(TransformationSystem model) {
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
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(0,
				"Name");
		nameDescriptor.setValidator(new NameEditValidator(getModel(),
				HenshinPackage.TRANSFORMATION_SYSTEM, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(1, "Description"));
		for (int i = 0; i < getModel().getImports().size(); i++) {
			descriptorList.add(new PropertyDescriptor(i + 2, "EMF-" + (i + 1)));
		}
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
			switch ((Integer) id) {
			case 0:
				return getModel().getName();
			case 1:
				if (getModel().getDescription() == null)
					return new String();
				else
					return getModel().getDescription();
			}
			if (((Integer) id) - 2 < getModel().getImports().size()) {
				return getModel().getImports().get(((Integer) id) - 2)
						.getName();
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
			}
		}

	}

}

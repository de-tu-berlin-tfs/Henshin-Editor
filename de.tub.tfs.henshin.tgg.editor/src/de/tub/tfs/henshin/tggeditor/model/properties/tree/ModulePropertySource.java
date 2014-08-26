/**
 * ModulePropertySource.java
 * created on 04.05.2013 09:05:57
 */
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 */
public class ModulePropertySource extends AbstractPropertySource<Module>{

	public ModulePropertySource(Module model) {
		super(model);
	}

	@Override
	@SuppressWarnings("deprecation")
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
			
			
			switch (((Integer) id) - getModel().getImports().size()) {
			case 2:
				return getModel().getInstances().size();
			case 3:
				return getModel().getRules().size();
			default:
				break;
			}

		}
		return null;
	}

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

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(index++,
				"Name");
		nameDescriptor.setValidator(new NameEditorValidator(getModel(),
				HenshinPackage.MODULE, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(index++, "Description"));
		for (int i = 0; i < getModel().getImports().size(); i++) {
			descriptorList.add(new PropertyDescriptor(index++, "EMF-" + (i + 1)));
		}
		descriptorList.add(new PropertyDescriptor(index++, "Number of graphs"));
		descriptorList.add(new PropertyDescriptor(index++, "Number of rules"));
		return descriptorList.toArray(new IPropertyDescriptor[] {});
	}

}

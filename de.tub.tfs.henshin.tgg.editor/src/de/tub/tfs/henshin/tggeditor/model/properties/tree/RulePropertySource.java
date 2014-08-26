/**
 * RulePropertySource.java
 * created on 04.05.2013 13:49:07
 */
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 */
public class RulePropertySource extends AbstractPropertySource<Rule>{
	
	static final String[] booleanValue = { "true", "false" };

	public RulePropertySource(Rule model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			int numberOfNACs = 0;
			List<NestedCondition> nestedConditions = getModel().getLhs().getNestedConditions();
			for (NestedCondition nestedCondition : nestedConditions) {
				if (nestedCondition.isNAC()) {
					numberOfNACs++;
				}
			}
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
				return numberOfNACs;
			case 4:
				return numberOfParameters;
			}
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
				index++, "Name");
		nameDescriptor.setValidator(new NameEditorValidator(EcoreUtil.getRootContainer(getModel()),
				HenshinPackage.MODULE__UNITS, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(index++,
				"Description"));

		descriptorList.add(new ComboBoxPropertyDescriptor(index++,
				"Activated", booleanValue));
		
		descriptorList.add(new PropertyDescriptor(index++, "Number of NACs"));
		
		descriptorList.add(new PropertyDescriptor(index++, "Number of parameters"));

		return descriptorList.toArray(new IPropertyDescriptor[] {});
	}

}

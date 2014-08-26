/**
 * RuleFolderPropertySource.java
 * created on 04.05.2013 14:53:16
 */
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolder;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 */
public class RuleFolderPropertySource extends AbstractPropertySource<RuleFolder>{

	public RuleFolderPropertySource(RuleFolder model) {
		super(model);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			return getModel().getRules().size();
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		return new IPropertyDescriptor[] {new PropertyDescriptor(0, "Number of rules")};
	}
	
}

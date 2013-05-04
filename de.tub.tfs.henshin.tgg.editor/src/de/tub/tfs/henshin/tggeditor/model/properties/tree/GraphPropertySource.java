/**
 * GraphPropertySource.java
 * created on 04.05.2013 13:07:29
 */
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import java.util.ArrayList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.tggeditor.util.validator.NameEditorValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 */
public class GraphPropertySource extends AbstractPropertySource<Graph> {

	public GraphPropertySource(Graph model) {
		super(model);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			int numberOfEdges = getModel().getEdges().size();
			int numberOfNodes = getModel().getNodes().size();
			switch ((Integer) id) {
			case 0:
				return getModel().getName();
			case 1:
				return numberOfNodes;
			case 2:
				return numberOfEdges;
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
			}
		}		
	}

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(index++,
				"Name");
		nameDescriptor.setValidator(new NameEditorValidator(EcoreUtil.getRootContainer(getModel()),
				HenshinPackage.MODULE__INSTANCES, getModel(),
				true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new PropertyDescriptor(index++, "Number of nodes"));
		descriptorList.add(new PropertyDescriptor(index++, "Number of edges"));
		
		return descriptorList.toArray(new IPropertyDescriptor[] {});
	}

}

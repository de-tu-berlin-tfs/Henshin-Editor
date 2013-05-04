/**
 * GraphFolderPropertySource.java
 * created on 04.05.2013 15:02:21
 */
package de.tub.tfs.henshin.tggeditor.model.properties.tree;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * @author huuloi
 */
public class GraphFolderPropertySource extends AbstractPropertySource<GraphFolder>{

	public GraphFolderPropertySource(GraphFolder model) {
		super(model);
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			return getModel().getGraphs().size();
		}
		return null;
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		return new IPropertyDescriptor[] {new PropertyDescriptor(0, "Number of graphs")};
	}

}

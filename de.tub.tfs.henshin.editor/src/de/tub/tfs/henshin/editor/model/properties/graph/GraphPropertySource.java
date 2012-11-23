/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.graph;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class GraphPropertySource.
 * 
 * @author Johann
 */
public class GraphPropertySource extends AbstractPropertySource<Graph> {

	/**
	 * Instantiates a new graph property source.
	 * 
	 * @param model
	 *            the model
	 */
	public GraphPropertySource(Graph model) {
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
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(index++,
				Messages.PROPERTY_NAME);
		nameDescriptor.setValidator(new NameEditValidator(HenshinUtil.INSTANCE.getTransformationSystem(getModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES, getModel(),
				true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_NODES));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_EDGES));
		
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
			int numberOfNodes = getModel().getNodes().size();
			int numberOfEdges = getModel().getEdges().size();
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
			}
		}

	}

}

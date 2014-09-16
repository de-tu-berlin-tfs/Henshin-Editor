package de.tub.tfs.henshin.editor.model.properties.graph;

import java.util.ArrayList;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class EdgePropertySource.
 */
public class EdgePropertySource extends AbstractPropertySource<Edge> {

	/**
	 * The Enum ID.
	 */
	private static enum ID {

		/** The SOURCE. */
		SOURCE,
		/** The TARGET. */
		TARGET,
		/** The TYPE. */
		TYPE
	}

	/**
	 * Instantiates a new edge property source.
	 * 
	 * @param model
	 *            the model
	 */
	public EdgePropertySource(Edge model) {
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
		descriptorList.add(new PropertyDescriptor(ID.SOURCE, "Source"));
		descriptorList.add(new PropertyDescriptor(ID.TARGET, "Target"));
		descriptorList.add(new PropertyDescriptor(ID.TYPE, "Type"));
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
			case SOURCE:
				final Node source = getModel().getSource();
				if (source != null) {
					return source.getType().getName();
				}
			case TARGET:
				final Node target = getModel().getTarget();
				if (target != null) {
					return target.getType().getName();
				}
			case TYPE:
				return new String(getModel().getType().getName());
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
	}

}

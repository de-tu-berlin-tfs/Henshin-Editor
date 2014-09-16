/**
 * 
 */
package de.tub.tfs.henshin.editor.internal;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;

/**
 * @author Johann
 * 
 */
public class GraphElementsContainterEObject extends
		AbstractEContainer<GraphElement, Graph> {

	/**
	 * @param model
	 */
	public GraphElementsContainterEObject(Graph model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.tree.AbstractListEObject#getElements
	 * ()
	 */
	@Override
	public Collection<GraphElement> getElements() {
		Collection<GraphElement> graphElements = new ArrayList<GraphElement>();

		graphElements.addAll(parent.getEdges());
		graphElements.addAll(parent.getNodes());

		return graphElements;
	}
}

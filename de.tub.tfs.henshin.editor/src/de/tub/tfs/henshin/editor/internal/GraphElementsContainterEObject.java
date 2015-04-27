/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
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

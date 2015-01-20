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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class SequentialUnitTreeEditPart.
 */
public class SequentialUnitTreeEditPart extends
		TransformationUnitTreeEditPart<SequentialUnit> {

	private ArrayList<Integer> counters;

	/**
	 * Instantiates a new sequential unit tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public SequentialUnitTreeEditPart(SequentialUnit model) {
		super(model);
		
		counters = new ArrayList<Integer>();
	}

	/**
	 * @return the counters
	 */
	public List<Integer> getCounters() {
		return Collections.unmodifiableList(counters);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		@SuppressWarnings("unchecked")
		List<Object> children = new LinkedList<Object>(getChildren());

		for (Object o : children) {
			removeChild((EditPart) o);
		}

		List<?> modelChildren = getModelChildren();

		for (Object o : modelChildren) {
			addChild(createChild(o), -1);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.
	 * TransformationUnitTreeEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		updateIsUsedFlag();

		List<Object> children = new LinkedList<Object>();
		SequentialUnit model = getCastedModel();

		Unit subUnit = null;
		int idx = 0;

		counters.clear();

		for (Unit u : model.getSubUnits()) {
			if (subUnit != u) {
				subUnit = u;
				counters.add(Integer.valueOf(idx));
				children.add(u);
			}

			idx++;
		}

		children.add(parameters);

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("seqUnit18.png");
		} catch (Exception e) {
			return null;
		}
	}
}

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
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitFigure;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class SequentialUnitEditPart.
 */
public class SequentialUnitEditPart extends
		TransformationUnitEditPart<SequentialUnit> {

	private ArrayList<Integer> counters;

	/**
	 * Instantiates a new sequential unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param model
	 *            the model
	 */
	public SequentialUnitEditPart(TransUnitPage transUnitPage,
			SequentialUnit model) {
		super(transUnitPage, model);

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
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.getColor(1));
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.SEQUENTIAL_UNIT__NAME:
			((TransformationUnitFigure) getFigure()).setName(getCastedModel()
					.getName());
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * TransformationUnitEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		List<Object> children = new LinkedList<Object>();

		Unit unit = null;
		int idx = 0;

		counters.clear();

		for (Unit u : getCastedModel().getSubUnits()) {
			if (unit != u) {
				unit = u;
				counters.add(Integer.valueOf(idx));
				children.add(u);
			}

			idx++;
		}

		children.addAll(getCastedModel().getParameters());

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void refreshChildren() {
		int i;
		EditPart editPart;
		Object model;

		List children = getChildren();
		int size = children.size();

		List modelObjects = getModelChildren();
		for (i = 0; i < modelObjects.size(); i++) {
			model = modelObjects.get(i);

			// Do a quick check to see if editPart[i] == model[i]
			if (i < children.size()
					&& ((EditPart) children.get(i)).getModel() == model)
				continue;

			editPart = createChild(model);
			addChild(editPart, i);
		}

		// remove the remaining EditParts
		size = children.size();
		if (i < size) {
			List trash = new ArrayList(size - i);
			for (; i < size; i++)
				trash.add(children.get(i));
			for (i = 0; i < trash.size(); i++) {
				EditPart ep = (EditPart) trash.get(i);
				removeChild(ep);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("seqUnit26.png");
		} catch (Exception e) {
			return null;
		}
	}
}

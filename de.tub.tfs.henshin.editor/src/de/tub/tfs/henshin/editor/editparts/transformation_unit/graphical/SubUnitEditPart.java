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

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;
import de.tub.tfs.henshin.editor.figure.transformation_unit.SubUnitFigure;
import de.tub.tfs.henshin.editor.internal.TransformationUnitPart;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.TransformationUnitPropertySource;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class SubUnitEditPart.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class SubUnitEditPart<T extends Unit> extends
		AdapterGraphicalEditPart<T> implements IGraphicalDirectEditPart {

	/** The transformation unit. */
	protected final Unit transformationUnit;

	/** The trans unit page. */
	protected final TransUnitPage transUnitPage;

	/**
	 * Instantiates a new sub unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param transformationUnit
	 *            the transformation unit
	 * @param model
	 *            the model
	 */
	public SubUnitEditPart(TransUnitPage transUnitPage,
			Unit transformationUnit, T model) {
		super(model);
		this.transformationUnit = transformationUnit;
		this.transUnitPage = transUnitPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		SubUnitFigure figure = new SubUnitFigure(this, getText(), 200,
				getImage());
		return figure;
	}

	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	abstract Image getImage();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new TransformationUnitAsSubUnitComponentEditPolicy(
						transformationUnit));
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new SubUnitXYLayoutEditPolicy());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		Unit parent = null;
		if (getParent().getModel() instanceof TransformationUnitPart<?>) {
			parent = ((TransformationUnitPart<?>) getParent().getModel())
					.getModel();
		} else {
			parent = (Unit) getParent().getModel();
		}
		transUnitPage.nextTransUnit(parent, getCastedModel());
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
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.UNIT__NAME:
			((SubUnitFigure) getFigure()).setName(getText());
			break;
		case HenshinPackage.PARAMETER_MAPPING:
			final int type = notification.getEventType();
			switch (type) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshSourceConnections();
				refreshTargetConnections();
			case Notification.SET:
				refreshVisuals();
				break;
			}

		}
	}

	/**
	 * Gets the text.
	 * 
	 * @return the text
	 */
	protected String getText() {
		return getCastedModel().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelSourceConnections() {
		Vector<ParameterMapping> list = new Vector<ParameterMapping>();
		Unit parent = null;
		if (getParent() instanceof ConditionalUnitPartAsSubUnitEditPart) {
			parent = (Unit) getParent().getParent().getModel();
		} else {
			parent = (Unit) getParent().getModel();
		}
		for (ParameterMapping parameterMapping : parent.getParameterMappings()) {
			if (parameterMapping.getSource().getUnit() == getModel()) {
				list.add(parameterMapping);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@Override
	protected List<ParameterMapping> getModelTargetConnections() {
		Vector<ParameterMapping> list = new Vector<ParameterMapping>();
		Unit parent = null;
		if (getParent() instanceof ConditionalUnitPartAsSubUnitEditPart) {
			parent = (Unit) getParent().getParent().getModel();
		} else {
			parent = (Unit) getParent().getModel();
		}
		for (ParameterMapping parameterMapping : parent.getParameterMappings()) {
			if (parameterMapping.getTarget().getUnit() == getModel()) {
				list.add(parameterMapping);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemuvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return ((SubUnitFigure) getFigure()).getNameLabelTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemuvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * updateValueDisplay(java.lang.String)
	 */
	@Override
	public void updateValueDisplay(String value) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.UNIT__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.MODULE__UNITS,
				getCastedModel(), true);
	}

	/**
	 * Sets the model activated.
	 * 
	 * @param activated
	 *            the activated
	 * @return true, if successful
	 */
	public boolean setModelActivated(boolean activated) {
		transUnitPage.setActivated(getCastedModel(), activated);
		return getCastedModel().isActivated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new LinkedList<Object>();

		if (getParent() instanceof SequentialUnitEditPart) {
			SequentialUnitEditPart parent = (SequentialUnitEditPart) getParent();
			SubUnitLayout counterLayout = HenshinLayoutFactory.eINSTANCE
					.createSubUnitLayout();

			List<Integer> counters = parent.getCounters();
			int idx = parent.getChildren().indexOf(this);
			int counter;

			if (idx == counters.size() - 1) {
				counter = parent.getCastedModel().getSubUnits().size()
						- counters.get(idx);

			} else {
				counter = counters.get(idx + 1) - counters.get(idx);
			}

			counterLayout.setIndex(counters.get(idx).intValue());
			counterLayout.setModel(getCastedModel());
			counterLayout.setParent(parent.getCastedModel());
			counterLayout.setCounter(counter);

			children.add(counterLayout);
		}

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#addChildVisual(org
	 * .eclipse.gef.EditPart, int)
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (getParent() instanceof SequentialUnitEditPart) {
			IFigure child = ((GraphicalEditPart) childEditPart).getFigure();

			getContentPane().add(child, new Rectangle(270, 0, 50, 46), index);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationUnitPropertySource(getCastedModel());
	}

}

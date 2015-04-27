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
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinDirectEditPolicy;
import de.tub.tfs.henshin.editor.editparts.SimpleComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.AttributePropertySource;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.NodeUtil;
import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class AttributeEditPart.
 */
public class AttributeEditPart extends AdapterGraphicalEditPart<Attribute>
		implements IGraphicalDirectEditPart {

	/** The text. */
	private Label text = new Label("-");

	/**
	 * Instantiates a new attribute edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public AttributeEditPart(Attribute model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		int weight = NodeUtil.getWidth(getCastedModel().getNode(), true);
		text.setText(getName());
		text.setSize(weight, 15);
		text.setTextAlignment(PositionConstants.LEFT);
		text.setLabelAlignment(PositionConstants.LEFT);
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new SimpleComponentEditPolicy());

		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new HenshinDirectEditPolicy());
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
		if (!(notification.getEventType() == Notification.REMOVING_ADAPTER)) {
			if (notification.getEventType() == HenshinNotification.TREE_SELECTED) {
				getViewer().select(this);

				return;
			}

			final int featureId = notification
					.getFeatureID(HenshinPackage.class);
			switch (featureId) {
			case -1:
			case HenshinPackage.ATTRIBUTE__TYPE:
			case HenshinPackage.ATTRIBUTE__VALUE:
				text.setText(getName());
				refreshVisuals();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#fireSelectionChanged()
	 */
	@Override
	protected void fireSelectionChanged() {
		if (getSelected() == SELECTED_PRIMARY) {
			getCastedModel().eNotify(
					new NotificationImpl(HenshinNotification.SELECTED, false,
							true));
		}

		super.fireSelectionChanged();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#registerVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		((NodeEditPart) getParent()).getFigure().repaint();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel().getNode())){
			return null;
		}
		return new AttributePropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel().getNode())){
			return new Rectangle(text.getBounds().x,text.getBounds().y,0,0);
		}
		return text.getTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
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
		return HenshinPackage.ATTRIBUTE__VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel().getNode())){
			return new ICellEditorValidator() {
				
				@Override
				public String isValid(Object value) {
					// TODO Auto-generated method stub
					return "Editing not allowed!";
				}
			};
		}
		return new TypeEditorValidator(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	private String getName() {
		String s = "";
		String value = getCastedModel().getValue();

		String pre = "";
		String suf = "";

//		if (containerRule != null) {
//			if (containerRule.getParameterByName(value) != null) {
//				pre = "${";
//				suf = "}";
//			}
//		}

		if (getCastedModel().getType().getEType()
				.equals(EcorePackage.Literals.ESTRING)) {
			// if (!value.isEmpty()) {
			value = pre + value + suf;
			// }
		}

		if (getCastedModel().getType() != null) {
			s = ("- " + getCastedModel().getType().getName() + " = " + value);
		}

		return s;
	}
}

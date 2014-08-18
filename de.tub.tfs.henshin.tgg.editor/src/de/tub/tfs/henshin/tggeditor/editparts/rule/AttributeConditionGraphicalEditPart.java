/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.rule;

import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.DragTracker;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RootEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.AttributeComponentEditPolicy;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

public class AttributeConditionGraphicalEditPart extends AdapterGraphicalEditPart<AttributeCondition> implements IGraphicalDirectEditPart {

	/** The text. */
	protected Label text = new Label("");

	/** The model element of the figure */
	protected AttributeCondition attributeCondition;
	
	protected int MAXLENGTH=50;
	/**
	 * Instantiates a new attribute edit part.
	 *
	 * @param model the model
	 */
	public AttributeConditionGraphicalEditPart(AttributeCondition model) {
		super(model);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		setName();		
		attributeCondition = getCastedModel();
		return text;
	}
	
	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performDirectEdit()
	 */
	@Override
	protected void performDirectEdit() {
		super.performDirectEdit();			
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case -1:
		case HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT:
			text.setText(getName());
			refreshVisuals();
			break;
		}

	}
	
	/* (non-Javadoc)
	 * @see de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#performRequest(org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request request) {
		// TODO Auto-generated method stub
		super.performRequest(request);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse.gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		if (req instanceof ChangeBoundsRequest) return false;
		return super.understandsRequest(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#registerVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		setName();
		if(getParent()!=null)
			((TNodeObjectEditPart)getParent()).getFigure().repaint();
		
		super.refreshVisuals();
	}
	
	/*
	 * (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return text.getTextBounds();
	}
	
	/*
	 * (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	protected String getName(){
		String s=getCastedModel().getConditionText();
		
		return s;
	}
	

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttributeComponentEditPolicy());
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	public void updateValueDisplay(String value) {
		setName();
	}
	
	protected void setName() {
		AttributeCondition attribute = getCastedModel();
		String attributeString = "";
	
		attributeString += autoShorten(attribute.getConditionText());
		
		text.setText(attributeString);
		//text.setLabelAlignment(Label.LEFT);
	}
	
	private String autoShorten(String value) {
		if(value.length()>MAXLENGTH)
			value=value.substring(0, MAXLENGTH-3) + "...";
		return value;
	}

	@Override
	protected void performOpen() {
		//super.performOpen();
	}

	/**
	 * Updates attribute marker.
	 */
	protected void updateMarker() {
	}
	
	
}

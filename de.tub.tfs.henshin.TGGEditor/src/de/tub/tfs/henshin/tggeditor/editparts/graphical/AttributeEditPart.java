package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.AttributeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

public class AttributeEditPart extends AdapterGraphicalEditPart<Attribute> implements IGraphicalDirectEditPart {


	
	/** The text. */
	protected Label text = new Label("");

	/** The model element of the figure */
	protected Attribute attribute;
	
	protected int MAXLENGTH=50;
	/**
	 * Instantiates a new attribute edit part.
	 *
	 * @param model the model
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
		setName();		
		attribute = getCastedModel();
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
		case HenshinPackage.ATTRIBUTE__TYPE:
		case HenshinPackage.ATTRIBUTE__VALUE:
			text.setText(getName());
			refreshVisuals();
		case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
			refreshVisuals();
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
		// TODO Auto-generated method stub
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
			((NodeObjectEditPart)getParent()).getFigure().repaint();
		

		if(attribute!=null && attribute.getMarkerType()!=null && attribute.getMarkerType().equals(RuleUtil.Translated_Graph) && attribute.getIsMarked()!=null)
		{
			if(attribute.getIsMarked()){
				text.setFont(new Font(null, "SansSerif", 8, SWT.BOLD));
				text.setForegroundColor(ColorConstants.darkGreen);					
			}
			else {text.setForegroundColor(ColorConstants.red);
			}
		}
		
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
		return HenshinPackage.ATTRIBUTE__VALUE;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	protected String getName(){
		String s="";
		if (getCastedModel().getType() != null) {
			s=("- " + getCastedModel().getType().getName() + "="
					+ getCastedModel().getValue());
		}
		return s;
	}
	

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttributeComponentEditPolicy());
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateValueDisplay(String value) {
		setName();
	}
	
	protected void setName() {
		Attribute attribute = getCastedModel();
		String attributeString = "";
		if (attribute.getType() != null) {
			attributeString += attribute.getType().getName();
		}
		attributeString += "=";
		if (attribute.getValue() != null) {
			attributeString += autoShorten(attribute.getValue());
		}
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
		// TODO Auto-generated method stub
		//super.performOpen();
	}

	/**
	 * Updates attribute marker.
	 */
	protected void updateMarker() {
	}
	
}

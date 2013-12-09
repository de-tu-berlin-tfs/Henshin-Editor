package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.AttributeComponentEditPolicy;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

public class AttributeEditPart extends AdapterGraphicalEditPart<TAttribute> implements IGraphicalDirectEditPart {
	


	/** The marker label */
	protected TextWithMarker labelWithMarker;

	/** The model element of the figure */
	protected TAttribute tAttribute;
	
	protected int MAXLENGTH=50;
	/**
	 * Instantiates a new attribute edit part.
	 *
	 * @param model the model
	 */
	public AttributeEditPart(TAttribute model) {
		super(model);
		tAttribute = getCastedModel();
		createMarker();
	}
	
	protected void createMarker() {
		labelWithMarker=new TextWithMarker();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		setName();
		return labelWithMarker;
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
			labelWithMarker.setText(getName());
			refreshVisuals();
		case TggPackage.TATTRIBUTE__MARKER_TYPE:
			refreshVisuals();
		}

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
		if (getParent() != null)
			((TNodeObjectEditPart) getParent()).getFigure().repaint();
		labelWithMarker.setMarker(tAttribute.getMarkerType());
		super.refreshVisuals();
	}

	


	/*
	 * (non-Javadoc)
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return labelWithMarker.text.getTextBounds();
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
		return null;
	}

	@Override
	public void updateValueDisplay(String value) {
		setName();
	}
	
	protected void setName() {
		try {
			Attribute attribute = getCastedModel();
			String attributeString = "";
			if (attribute.getType() != null) {
				attributeString += attribute.getType().getName();
			}
			attributeString += "=";
			if (attribute.getValue() != null) {
				attributeString += autoShorten(attribute.getValue());
			}
			labelWithMarker.setText(attributeString);
		} catch (ClassCastException ex){
			if (getCastedModel().getNode() != null)
				getCastedModel().getNode().getAttributes().remove(getCastedModel());
		}
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

	
}

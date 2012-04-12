package tggeditor.editparts.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;

import tggeditor.editpolicies.graphical.AttributeComponentEditPolicy;

import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

public class AttributeEditPart extends AdapterGraphicalEditPart<Attribute> implements IGraphicalDirectEditPart {
	
	/** The text. */
	private Label text = new Label("");

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
		}

	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#registerVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		setName();
		((NodeObjectEditPart)getParent()).getFigure().repaint();
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
	private String getName(){
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
	
	private void setName() {
		Attribute attribute = getCastedModel();
		String attributeString = "";
		if (attribute.getType() != null) {
			attributeString += attribute.getType().getName();
		}
		attributeString += ":";
		if (attribute.getValue() != null) {
			attributeString += attribute.getValue();
		}
		text.setText(attributeString);
	}
	
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		//super.performOpen();
	}

}

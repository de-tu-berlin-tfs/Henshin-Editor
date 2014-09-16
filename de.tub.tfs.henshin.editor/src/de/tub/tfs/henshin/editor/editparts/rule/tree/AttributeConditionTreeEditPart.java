/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.tree;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.rule.AttributeConditionComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.rule.AttributeConditionPropertySource;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * @author Johann
 * 
 */
public class AttributeConditionTreeEditPart extends
		AdapterTreeEditPart<AttributeCondition> implements IDirectEditPart {

	/**
	 * @param model
	 */
	public AttributeConditionTreeEditPart(AttributeCondition model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		String text = getCastedModel().getName();
		if (getCastedModel().getConditionText() != null) {
			text += " [" + getCastedModel().getConditionText() + "]";
		}
		return text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.ATTRIBUTE_CONDITION__NAME:
		case HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT:
			refreshVisuals();
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new AttributeConditionPropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new AttributeConditionComponentEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("aCondition22.png");
		} catch (Exception e) {
			return null;
		}
	}

}

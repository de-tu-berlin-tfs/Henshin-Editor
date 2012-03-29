package tggeditor.editparts.tree;


import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tggeditor.editpolicies.graphical.AttributeComponentEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class AttributeTreeEditPart extends AdapterTreeEditPart<Attribute> implements
		IDirectEditPart {

	public AttributeTreeEditPart(Attribute model) {
		super(model);
	}
	
	@Override
	protected String getText() {
		if (getCastedModel() == null)
			return "";
		if (getCastedModel().getValue() == null)
			return "";
		return getCastedModel().getValue();
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.ATTRIBUTE;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttributeComponentEditPolicy());
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.ATTRIBUTE:
				refresh();
				break;
			default:
				break; 
		}
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("attribute16.png");
		} catch (Exception e) {
			return null;
		}
	}
	
}

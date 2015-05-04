package de.tub.tfs.henshin.tggeditor.editparts.constraint;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.constraint.DeleteAttributeCommand;
import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.AttributeComponentEditPolicy;


public class AttributeEditPart extends de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart {

	public AttributeEditPart(TAttribute model) {
		super(model);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AttributeComponentEditPolicy());
	}
		
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
			case HenshinPackage.ATTRIBUTE__VALUE:
				CreateAttributeCommand.setParameter(getCastedModel().getNode(), getCastedModel().getValue());
				EObject constraint = getCastedModel().getNode().getGraph();
				while (!(constraint instanceof Constraint)) {
					constraint = constraint.eContainer();
				}
				DeleteAttributeCommand.removeParameters(((Constraint)constraint).getParameters());
		}
	}
	
}

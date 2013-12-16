package de.tub.tfs.henshin.tggeditor.editparts.rule;


import java.util.ArrayList;



import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.draw2d.ColorConstants;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.RuleObjectTextWithMarker;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.AttributeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleAttributeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The EdgeEditPart class of rules.
 */
public class RuleAttributeEditPart extends AttributeEditPart {

	/**
	 * Instantiates a new rule edge edit part.
	 *
	 * @param model the model
	 */
	public RuleAttributeEditPart(TAttribute model) {
		super(model);
		cleanUpRule();
	}
	


	@Override
	protected void createMarker() {
		labelWithMarker=new RuleObjectTextWithMarker(ColorConstants.black);
	}



	private void cleanUpRule() {
		// remove attribute duplicates in LHS
		
		ArrayList<Attribute> lhsAttributesList = RuleUtil
				.getAllLHSAttributes(tAttribute);

		// remove duplicates
		while (lhsAttributesList.size() > 1) {
			Attribute lhsAttribute = lhsAttributesList.get(0);
			lhsAttributesList.remove(0);
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					lhsAttribute);
			cmd.execute();
		}			
		
			
		
		// remove lhs attribute, if rule creates the attribute
		if(RuleUtil.NEW.equals(tAttribute.getMarkerType()) ){
			if (lhsAttributesList.size()==1) 
			{
				Attribute lhsAttribute = lhsAttributesList.get(0);
				lhsAttributesList.remove(0);
				SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(lhsAttribute);
				cmd.execute();					
			}
		}
		
		// update lhs attribute value, if it is inconsistent to the rhs attribute value
		updateLHSAttribute();
		
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof Attribute) {
			int featureId = notification.getFeatureID(HenshinPackage.class);		
			switch (featureId) {
			case -1:
				return;
			case HenshinPackage.ATTRIBUTE__TYPE:
			case HenshinPackage.ATTRIBUTE__VALUE:
				labelWithMarker.setText(getName());
				updateLHSAttribute();
			case TggPackage.TATTRIBUTE__MARKER_TYPE:
				refreshVisuals();
				return;
			}
		}		
	}

	private void updateLHSAttribute() {
		// updates the lhs attribute value if the lhs attribute exists and its value differs from the rhs attribute value
		if (!(RuleUtil.NEW.equals(tAttribute.getMarkerType())) ) {
			Attribute lhsAttribute = RuleUtil.getLHSAttribute(tAttribute);
			if (lhsAttribute!=null
					// lhs attribute has a different value as the rhs attribute
					&& !(lhsAttribute.getValue().equals(tAttribute.getValue()))) {
				// update lhs attribute value to current value of rhs attribute
				lhsAttribute.setValue(tAttribute.getValue());			
			}
		}
	}



	
	

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RuleAttributeComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AttributeGraphicalEditPolicy());
	}

	
}

package tggeditor.editparts.graphical;

import java.util.HashSet;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;

import tgg.CritPair;
import tgg.TGGPackage;
import tggeditor.MappingConverter;
import tggeditor.editpolicies.graphical.CritPairLayoutEditPolicy;
import tggeditor.editpolicies.rule.RuleXYLayoutEditPolicy;

public class CritPairEditPart extends GraphEditPart {

	Rule rule1;
	Rule rule2;
	Graph overlapping;
	CritPair critPair;
	
	public CritPairEditPart(CritPair model) {
		super(model.getOverlapping());
		registerAdapter(model.getOverlapping());
		registerAdapter(model);
		registerAdapter(model.getRule1());
		registerAdapter(model.getRule2());
		this.critPair = model;
		this.overlapping = model.getOverlapping();
		this.rule1 = model.getRule1();
		this.rule2 = model.getRule2();
		
	}
	
	@Override
	protected void createEditPolicies() {
//		installEditPolicy(EditPolicy.LAYOUT_ROLE, new CritPairLayoutEditPolicy());
	}
	
	@Override
	protected void setFigureNameLabel() {
		nameLabel.setText(critPair.getOverlapping().getName());
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof CritPair) {
			final int featureId = notification.getFeatureID(HenshinPackage.class);
			switch (featureId){
				case TGGPackage.CRIT_PAIR:
					setFigureNameLabel();
					refreshVisuals();
				default:
					break; 
			}
		}
		super.notifyChanged(notification);
	}

}

package tggeditor.editparts.tree.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;

import tgg.CritPair;
import tgg.TGGPackage;
import tggeditor.editpolicies.graphical.CriticalPairEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class CritPairTreeEditPart extends AdapterTreeEditPart<CritPair> {
	
	public CritPairTreeEditPart(CritPair model) {
		super(model);
		registerAdapter(model);
		
	}
	
	@Override
	protected String getText() {
		return "Test";
	}
	
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		if (getCastedModel().getOverlapping() != null) {
			list.add(getCastedModel().getOverlapping());
		}
		list.add(getCastedModel().getRule1());
		list.add(getCastedModel().getRule2());
		return list;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (!this.isActive())  return;
		
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
			case TGGPackage.TGG__CRIT_PAIRS:
			case TGGPackage.CRIT_PAIR:
				refreshVisuals();
			case TGGPackage.CRIT_PAIR__OVERLAPPING:
			case TGGPackage.CRIT_PAIR__RULE1:
			case TGGPackage.CRIT_PAIR__RULE2:
				refreshChildren();
			default:
				break;
		}
		refreshVisuals();
		super.notifyChanged(notification);
	}	
	
	public int getDirectEditFeatureID() {
		return TGGPackage.CRIT_PAIR;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new CriticalPairEditPolicy());
	}
	
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("critpair.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	public void openCritPairView(CritPair crit) {
		this.performOpen();
//		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor((CritPair) getModel());
//		editor.getCritPairPage(getCastedModel()).setRule1((Rule) crit.getRule1());
//		editor.getCritPairPage(getCastedModel()).setRule2((Rule) crit.getRule2());
	}
	
	@Override
	protected void performOpen() {
		super.performOpen();
	}
	
}
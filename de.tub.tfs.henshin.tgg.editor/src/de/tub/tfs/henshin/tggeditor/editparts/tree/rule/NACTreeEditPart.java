/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.NACComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;


/**
 * TreeEditPart for NAC.
 */
public class NACTreeEditPart extends GraphTreeEditPart {

	public NACTreeEditPart(TripleGraph model) {
		super(model);
	}
	
	/**
	 * Call openRuleView methode of RuleTreeEditPart and 
	 * show the NAC above and the relative rule below in the graphical editor.
	 */
	@Override
	protected void performOpen() {
		((RuleTreeEditPart) this.getParent()).openRuleView((Graph) getModel());		
		
	}


	@Override
	public EditPartViewer getViewer() {
		return super.getViewer();
	}


	@Override
	protected void createEditPolicies() {	
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
			new NACComponentEditPolicy());
		super.createEditPolicies();
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.NESTED_CONDITION:
			case HenshinPackage.FORMULA:	
			case HenshinPackage.GRAPH:
				refreshVisuals();			
		}
		super.notifyChanged(notification);
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("nac18.png");
		} catch (Exception e) {
			return null;
		}
	}
}

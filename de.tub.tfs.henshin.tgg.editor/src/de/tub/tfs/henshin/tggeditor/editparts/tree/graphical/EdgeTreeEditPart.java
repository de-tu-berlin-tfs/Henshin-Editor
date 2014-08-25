/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.tree.graphical;


import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class EdgeTreeEditPart extends AdapterTreeEditPart<Edge> implements
		IDirectEditPart {

	public EdgeTreeEditPart(Edge model) {
		super(model);
//		if (EdgeUtil.getEdgeLayout(getCastedModel()) == null) {
//			EdgeLayout edgeLayout = TggFactory.eINSTANCE.createEdgeLayout();
//			TGG tgg = NodeUtil.getLayoutSystem(getCastedModel().getSource().getGraph());
//			if (tgg != null ) tgg.getEdgelayouts().add(edgeLayout);
//		}
	}
	
	@Override
	protected String getText() {
		String name = "";
		if (getCastedModel() == null) {
			return name;
		}
		if (getCastedModel().getType() != null) {
			name += getCastedModel().getType().getName()+":";//+"\n";
		}
		Edge e = getCastedModel();
		if (e.getSource() != null && e.getSource().getType()!=null &&
				e.getTarget() != null && e.getTarget().getType()!=null) {
			name += "   "+e.getSource().getType().getName()+" --> "+e.getTarget().getType().getName();
		}
		return name;
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.EDGE;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new EdgeComponentEditPolicy());
//		installEditPolicy(EditPolicy.NODE_ROLE, new NodeComponentEditPolicy());
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.EDGE:
			case HenshinPackage.EDGE__SOURCE:
			case HenshinPackage.EDGE__TARGET:
			case HenshinPackage.EDGE__TYPE:
					
				refreshVisuals();
				break;
			default:
				break; 
		}
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("edge18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected void performOpen() {
		if (getCastedModel().getGraph().getRule() != null) {
			if (getParent().getParent() instanceof RuleTreeEditPart) {
				RuleTreeEditPart eP = (RuleTreeEditPart) getParent().getParent();
				eP.performOpen();
			}
		}
	}
	
}

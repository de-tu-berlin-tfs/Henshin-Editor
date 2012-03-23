package tggeditor.editparts.tree;


import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tggeditor.editpolicies.graphical.EdgeComponentEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class EdgeTreeEditPart extends AdapterTreeEditPart<Edge> implements
		IDirectEditPart {

	public EdgeTreeEditPart(Edge model) {
		super(model);
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
		if (getCastedModel().getSource() != null && 
				getCastedModel().getTarget() != null) {
			name += "   "+getCastedModel().getSource().getType().getName()+" --> "+getCastedModel().getTarget().getType().getName();
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
	
}

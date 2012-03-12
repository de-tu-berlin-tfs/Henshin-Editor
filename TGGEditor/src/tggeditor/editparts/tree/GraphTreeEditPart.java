package tggeditor.editparts.tree;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tggeditor.editpolicies.graphical.GraphComponentEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;


public class GraphTreeEditPart extends AdapterTreeEditPart<Graph> implements IDirectEditPart {
	
	public GraphTreeEditPart(Graph model) {
		super(model);
	}
	
	@Override
	protected String getText() {
		if (getCastedModel() == null)
			return "";
		return getCastedModel().getName();
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getNodes());
		list.addAll(getCastedModel().getEdges());
		return list;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.GRAPH__NAME:
				refreshVisuals();
			case HenshinPackage.GRAPH__NODES:
			case HenshinPackage.GRAPH__EDGES:
			case HenshinPackage.GRAPH__FORMULA:
				refreshChildren();
			default:
				break; 
		}
		refreshVisuals();
		super.notifyChanged(notification);
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.GRAPH__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new GraphComponentEditPolicy());
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("graph18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
}

package de.tub.tfs.henshin.tggeditor.editparts.tree.graphical;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class NodeTreeEditPart extends AdapterTreeEditPart<TNode> implements
IDirectEditPart {

	public NodeTreeEditPart(TNode model) {
		super(model);
	}

	@Override
	protected String getText() {
		if (getCastedModel() == null) {
			return "";
		}
		String text = "";
		if(getCastedModel().getName() != null) {
			text += getCastedModel().getName();
		}
		text += " :";
		if(getCastedModel().getType() != null) {
			text += getCastedModel().getType().getName();
		}
		return text;
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getAttributes());
		list.addAll(getCastedModel().getAllEdges());
		return list;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
		case HenshinPackage.NODE__ATTRIBUTES:
		case HenshinPackage.NODE__NAME:
		case HenshinPackage.NODE__OUTGOING:
		case HenshinPackage.NODE__INCOMING:
			refresh();
			break;
		default:
			break; 
		}
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.NODE__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("node.png");
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

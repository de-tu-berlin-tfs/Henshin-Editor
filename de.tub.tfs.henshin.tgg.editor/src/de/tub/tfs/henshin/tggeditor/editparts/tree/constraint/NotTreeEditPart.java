package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.NotComponentEditPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class NotTreeEditPart extends AdapterTreeEditPart<Not> {

	public NotTreeEditPart(Not model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getText() {
		return "NOT";
	}
	
	@Override
	protected List<Formula> getModelChildren() {
		List<Formula> child = new ArrayList<Formula>();
		if (getCastedModel().getChild() != null) {
			child.add(getCastedModel().getChild());
		}		
		return child;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NotComponentEditPolicy());
	}
	
}

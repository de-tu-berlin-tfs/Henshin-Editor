package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.AndComponentEditPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class AndTreeEditPart extends AdapterTreeEditPart<And> {

	public AndTreeEditPart(And model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getText() {
		return "AND";
	}
	
	@Override
	protected List<Formula> getModelChildren() {
		List<Formula> childs = new ArrayList<Formula>();
		if (getCastedModel().getLeft() != null) {
			childs.add(getCastedModel().getLeft());
		}
		if (getCastedModel().getRight() != null) {
			childs.add(getCastedModel().getRight());
		}
		return childs;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new AndComponentEditPolicy());
	}
}

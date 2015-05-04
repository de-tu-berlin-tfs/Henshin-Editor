package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.OrComponentEditPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class OrTreeEditPart extends AdapterTreeEditPart<Or> {

	public OrTreeEditPart(Or model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getText() {
		return "OR";
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
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new OrComponentEditPolicy());
	}
	
}

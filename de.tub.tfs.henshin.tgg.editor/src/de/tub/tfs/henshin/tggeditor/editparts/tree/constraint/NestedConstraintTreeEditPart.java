package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.NestedConstraintComponentEditPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class NestedConstraintTreeEditPart extends AdapterTreeEditPart<NestedConstraint> {

	public NestedConstraintTreeEditPart(NestedConstraint model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getText() {
		return getCastedModel().getPremise().getName() + " -> " + ((NestedCondition)getCastedModel().getPremise().getFormula()).getConclusion().getName();
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NestedConstraintComponentEditPolicy());
	}
	
}

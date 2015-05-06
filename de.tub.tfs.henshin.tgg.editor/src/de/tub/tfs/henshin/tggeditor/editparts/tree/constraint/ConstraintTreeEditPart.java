package de.tub.tfs.henshin.tggeditor.editparts.tree.constraint;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tub.tfs.henshin.tggeditor.editparts.tree.TGGTreeContainerEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.ConstraintComponentEditPolicy;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class ConstraintTreeEditPart extends AdapterTreeEditPart<Constraint> implements IDirectEditPart {

	public ConstraintTreeEditPart(Constraint model) {
		super(model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getText() {
		String enabled = "[E]";
		if (!this.getCastedModel().isEnabled()) enabled = "[D]";
		return enabled + "[" + this.getCastedModel().getComponent() + "] " + this.getCastedModel().getName();
	}
	
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.CONSTRAINT__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new ConstraintComponentEditPolicy());
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new TGGTreeContainerEditPolicy());	
	}
	
	@Override
	protected List<Formula> getModelChildren() {
		List<Formula> l = new ArrayList<Formula>();
		if (getCastedModel().getRoot() != null) {
			l.add(getCastedModel().getRoot());
		}
		return l;
	}
	
}

package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteNotCommand extends CompoundCommand {

	private Not not;
	
	public DeleteNotCommand(Not not) {
		super();
		this.not = not;
	}
	
	@Override
	public void execute() {
		EObject container = this.not.eContainer();
		if (container instanceof Constraint) {
			Constraint parent = (Constraint)container;
			parent.setRoot(this.not.getChild());
		}
		if (container instanceof Not) {
			Not parent = (Not)container;
			parent.setChild(this.not.getChild());
		}
		if (container instanceof And) {
			And parent = (And)container;
			if (parent.getLeft().equals(this.not)) {
				parent.setLeft(this.not.getChild());
			}
			if (parent.getRight().equals(this.not)) {
				parent.setRight(this.not.getChild());
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			if (parent.getLeft().equals(this.not)) {
				parent.setLeft(this.not.getChild());
			}
			if (parent.getRight().equals(this.not)) {
				parent.setRight(this.not.getChild());
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (this.not != null &&
				(this.not.eContainer() instanceof Constraint)
				|| (this.not.eContainer() instanceof Not)
				|| (this.not.eContainer() instanceof And)
				|| (this.not.eContainer() instanceof Or)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
}

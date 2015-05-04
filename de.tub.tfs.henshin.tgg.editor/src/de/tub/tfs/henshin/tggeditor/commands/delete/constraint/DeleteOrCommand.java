package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteOrCommand extends CompoundCommand {

	private Or or;
	
	public DeleteOrCommand(Or or) {
		super();
		this.or = or;
	}

	@Override
	public void execute() {
		EObject container = this.or.eContainer();
		if (container instanceof Constraint) {
			Constraint parent = (Constraint)container;
			parent.setRoot(this.or.getLeft());
		}
		if (container instanceof Not) {
			Not parent = (Not)container;
			parent.setChild(this.or.getLeft());
		}
		if (container instanceof And) {
			And parent = (And)container;
			if (parent.getLeft().equals(this.or)) {
				parent.setLeft(this.or.getLeft());
			}
			if (parent.getRight().equals(this.or)) {
				parent.setRight(this.or.getLeft());
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			if (parent.getLeft().equals(this.or)) {
				parent.setLeft(this.or.getLeft());
			}
			if (parent.getRight().equals(this.or)) {
				parent.setRight(this.or.getLeft());
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (this.or != null &&
				(this.or.eContainer() instanceof Constraint)
				|| (this.or.eContainer() instanceof Not)
				|| (this.or.eContainer() instanceof And)
				|| (this.or.eContainer() instanceof Or)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
}

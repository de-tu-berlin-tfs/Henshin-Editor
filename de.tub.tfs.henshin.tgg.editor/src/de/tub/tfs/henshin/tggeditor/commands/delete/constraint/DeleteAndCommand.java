package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.CompoundCommand;

public class DeleteAndCommand extends CompoundCommand {

	private And and;
	
	public DeleteAndCommand(And and) {
		super();
		this.and = and;
	}

	@Override
	public void execute() {
		EObject container = this.and.eContainer();
		if (container instanceof Constraint) {
			Constraint parent = (Constraint)container;
			parent.setRoot(this.and.getLeft());
		}
		if (container instanceof Not) {
			Not parent = (Not)container;
			parent.setChild(this.and.getLeft());
		}
		if (container instanceof And) {
			And parent = (And)container;
			if (parent.getLeft().equals(this.and)) {
				parent.setLeft(this.and.getLeft());
			}
			if (parent.getRight().equals(this.and)) {
				parent.setRight(this.and.getLeft());
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			if (parent.getLeft().equals(this.and)) {
				parent.setLeft(this.and.getLeft());
			}
			if (parent.getRight().equals(this.and)) {
				parent.setRight(this.and.getLeft());
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (this.and != null &&
				(this.and.eContainer() instanceof Constraint)
				|| (this.and.eContainer() instanceof Not)
				|| (this.and.eContainer() instanceof And)
				|| (this.and.eContainer() instanceof Or)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
}

package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.Command;

public class CreateNotCommand extends Command {

	private Formula formula;
	private Not not;
	
	public CreateNotCommand(Formula formula) {
		this.formula = formula;
	}
	
	@Override
	public boolean canExecute() {
		if (this.formula != null && 
				(this.formula.eContainer() instanceof Constraint)
				|| (this.formula.eContainer() instanceof Not)
				|| (this.formula.eContainer() instanceof And)
				|| (this.formula.eContainer() instanceof Or)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void execute() {
		this.not = HenshinFactory.eINSTANCE.createNot();
		EObject container = this.formula.eContainer();
		if (container instanceof Constraint) {
			Constraint parent = (Constraint)container;
			parent.setRoot(null);
			this.not.setChild(this.formula);
			parent.setRoot(this.not);
		}
		if (container instanceof Not) {
			Not parent = (Not)container;
			parent.setChild(null);
			this.not.setChild(this.formula);
			parent.setChild(this.not);
		}
		if (container instanceof And) {
			And parent = (And)container;
			if (parent.getLeft().equals(this.formula)) {
				parent.setLeft(null);
				this.not.setChild(this.formula);
				parent.setLeft(this.not);
			}
			if (parent.getRight().equals(this.formula)) {
				parent.setRight(null);
				this.not.setChild(this.formula);
				parent.setRight(this.not);
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			if (parent.getLeft().equals(this.formula)) {
				parent.setLeft(null);
				this.not.setChild(this.formula);
				parent.setLeft(this.not);
			}
			if (parent.getRight().equals(this.formula)) {
				parent.setRight(null);
				this.not.setChild(this.formula);
				parent.setRight(this.not);
			}
		}
	}
	
}

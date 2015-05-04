package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TggFactory;

public class CreateAndCommand extends Command {

	private Formula formula;
	private And and;
	
	public CreateAndCommand(Formula formula) {
		super();
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
		this.and = HenshinFactory.eINSTANCE.createAnd();
		EObject container = this.formula.eContainer();
		
		NestedConstraint nconstraint = HenshinFactory.eINSTANCE.createNestedConstraint();
		NestedCondition ncondition = HenshinFactory.eINSTANCE.createNestedCondition();
		Random generator = new Random();
		Graph premise = TggFactory.eINSTANCE.createTripleGraph();
		premise.setName("P_" + generator.nextInt(100));
		Graph conclusion = TggFactory.eINSTANCE.createTripleGraph();
		conclusion.setName("C_" + generator.nextInt(100));
		ncondition.setConclusion(conclusion);
		premise.setFormula(ncondition);
		nconstraint.setPremise(premise);
		
		if (container instanceof Constraint) {
			Constraint parent = (Constraint)container;
			parent.setRoot(null);
			this.and.setLeft(this.formula);
			this.and.setRight(nconstraint);
			parent.setRoot(this.and);
		}
		if (container instanceof Not) {
			Not parent = (Not)container;
			parent.setChild(null);
			this.and.setLeft(this.formula);
			this.and.setRight(nconstraint);
			parent.setChild(this.and);
		}
		if (container instanceof And) {
			And parent = (And)container;
			boolean left=true;
			if (parent.getLeft().equals(this.formula)) {
				parent.setLeft(null);
			}
			if (parent.getRight().equals(this.formula)) {
				parent.setRight(null);
				left=false;
			}
			this.and.setLeft(this.formula);
			this.and.setRight(nconstraint);
			if (left) {
				parent.setLeft(this.and);
			}
			if (!left) {
				parent.setRight(this.and);
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			boolean left=true;
			if (parent.getLeft().equals(this.formula)) {
				parent.setLeft(null);
			}
			if (parent.getRight().equals(this.formula)) {
				parent.setRight(null);
				left=false;
			}
			this.and.setLeft(this.formula);
			this.and.setRight(nconstraint);
			if (left) {
				parent.setLeft(this.and);
			}
			if (!left) {
				parent.setRight(this.and);
			}
		}
	}
	
}

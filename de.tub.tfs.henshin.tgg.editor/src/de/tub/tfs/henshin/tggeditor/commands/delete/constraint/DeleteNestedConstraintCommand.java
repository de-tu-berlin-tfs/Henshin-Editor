package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import java.util.Random;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TggFactory;

public class DeleteNestedConstraintCommand extends CompoundCommand {

	private NestedConstraint c;
	
	public DeleteNestedConstraintCommand(NestedConstraint c) {
		this.c = c;
	}

	@Override
	public void execute() {
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
		EObject container = this.c.eContainer();
		if (container instanceof Constraint) {
			((Constraint)container).setRoot(nconstraint);
		}
		if (container instanceof Not) {
			((Not)container).setChild(nconstraint);
		}
		if (container instanceof And) {
			And parent = (And)container;
			if (parent.getLeft().equals(this.c)) {
				parent.setLeft(nconstraint);
			}
			if (parent.getRight().equals(this.c)) {
				parent.setRight(nconstraint);
			}
		}
		if (container instanceof Or) {
			Or parent = (Or)container;
			if (parent.getLeft().equals(this.c)) {
				parent.setLeft(nconstraint);
			}
			if (parent.getRight().equals(this.c)) {
				parent.setRight(nconstraint);
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (this.c != null && 
				(this.c.eContainer() instanceof Constraint)
				|| (this.c.eContainer() instanceof Not)
				|| (this.c.eContainer() instanceof And)
				|| (this.c.eContainer() instanceof Or)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
}

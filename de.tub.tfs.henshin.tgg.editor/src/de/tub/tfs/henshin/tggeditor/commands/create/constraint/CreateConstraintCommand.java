package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import java.util.Random;

import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;

public class CreateConstraintCommand extends Command {

	private TGG tgg;
	private String name;
	private Constraint constraint;
	private TripleComponent component;
	
	public CreateConstraintCommand(TGG tgg, String name, TripleComponent component) {
		super();
		this.tgg = tgg;
		this.name = name;
		this.component = component;
	}
	
	@Override
	public boolean canExecute() {
		return tgg != null && name != null;
	}
	
	@Override
	public boolean canUndo() {
		return this.canExecute();
	}
	
	@Override
	public void execute() {
		this.constraint = HenshinFactory.eINSTANCE.createConstraint();
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
		constraint.setName(name);
		constraint.setRoot(nconstraint);
		constraint.setComponent(component.getLiteral());
		tgg.getConstraints().add(constraint);
	}
	
	@Override
	public void undo() {
		tgg.getConstraints().remove(this.constraint);
	}
	
}

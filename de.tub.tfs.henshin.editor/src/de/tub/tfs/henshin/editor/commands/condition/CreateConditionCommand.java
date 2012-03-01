package de.tub.tfs.henshin.editor.commands.condition;

import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.SendNotify;

/**
 * Command to create a condition to the given premise.
 * 
 * @author Angeline
 */
public class CreateConditionCommand extends Command {

	/** The premise. */
	private Graph premise;

	/** The to add. */
	private Formula formulaToAdd;

	private Formula oldFormula;

	/**
	 * Instantiates a application condition to create and a graph to set as
	 * conclusion. The default graph's name is generated automatically.
	 * 
	 * @param premise
	 *            the premise
	 * @param formula
	 *            the formula to add.
	 */
	public CreateConditionCommand(Graph premise, Formula formula) {
		this.premise = premise;
		this.formulaToAdd = formula;

		if (premise.getFormula() != formula) {
			oldFormula = premise.getFormula();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return formulaToAdd != null && premise != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		setConclusion(formulaToAdd);

		if (oldFormula == null) {
			premise.setFormula(formulaToAdd);
			SendNotify.sendAddFormulaNotify(premise, formulaToAdd);
		} else if (formulaToAdd instanceof NestedCondition) {
			// Creates AND and set the formula to add as AND-child
			And and = HenshinFactory.eINSTANCE.createAnd();
			and.setLeft(formulaToAdd);
			and.setRight(premise.getFormula());
			premise.setFormula(and);
			SendNotify.sendAddFormulaNotify(premise, and);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		premise.setFormula(oldFormula);
		SendNotify.sendAddFormulaNotify(premise, oldFormula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		execute();
	}

	/**
	 * Sets the conclusion.
	 * 
	 * @param formula
	 *            the new conclusion
	 */
	private void setConclusion(Formula formula) {
		if (formula instanceof NestedCondition) {
			Graph conclusion = ((NestedCondition) formula).getConclusion();
			if (conclusion == null) {
				conclusion = HenshinFactory.eINSTANCE.createGraph();
				conclusion.setName(ModelUtil.getDistinctNCName(premise));
				((NestedCondition) formula).setConclusion(conclusion);
			}
		} else if (formula instanceof UnaryFormula) {
			setConclusion(((UnaryFormula) formula).getChild());
		} else if (formula instanceof BinaryFormula) {
			setConclusion(((BinaryFormula) formula).getLeft());
			setConclusion(((BinaryFormula) formula).getRight());
		}
	}

}

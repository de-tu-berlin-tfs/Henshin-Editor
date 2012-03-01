/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.condition;

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
 * Command to create a formula (AND, OR, NOT, Application Condition) and add it
 * to the given parent formula.
 * 
 * @author Angeline Warning
 */
public class CreateFormulaCommand extends Command {

	/** The parent formula. */
	private Formula parentFormula;

	/** The to add. */
	private Formula toAdd;

	/** The first child. */
	private boolean firstChild;

	/** The premise. */
	private Graph premise;

	/**
	 * Per default, a formula to add is a first child of parent formula.
	 * 
	 * @param premise
	 *            the premise
	 * @param parentFormula
	 *            Parent formula.
	 * @param toAdd
	 *            Formula to add to parent formula
	 */
	public CreateFormulaCommand(Graph premise, Formula parentFormula,
			Formula toAdd) {
		this(premise, parentFormula, toAdd, true);
	}

	/**
	 * Instantiates a new creates the formula command.
	 * 
	 * @param premise
	 *            the premise
	 * @param parentFormula
	 *            Parent formula
	 * @param toAdd
	 *            Formula to add.
	 * @param firstChild
	 *            Value {@code true} means the formula to add is a first child
	 *            of parent formula.
	 */
	public CreateFormulaCommand(Graph premise, Formula parentFormula,
			Formula toAdd, boolean firstChild) {
		this.parentFormula = parentFormula;
		this.toAdd = toAdd;
		this.firstChild = firstChild;
		this.premise = premise;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return parentFormula != null && toAdd != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (toAdd instanceof NestedCondition) {
			final NestedCondition ac = (NestedCondition) toAdd;
			if (ac.getConclusion() == null) {
				final Graph graph = HenshinFactory.eINSTANCE.createGraph();
				graph.setName(ModelUtil.getDistinctNCName(premise));
				ac.setConclusion(graph);
			}
		}

		if (parentFormula instanceof UnaryFormula) {
			((UnaryFormula) parentFormula).setChild(toAdd);
		} else if (parentFormula instanceof BinaryFormula) {
			if (firstChild) {
				((BinaryFormula) parentFormula).setLeft(toAdd);
			} else {
				((BinaryFormula) parentFormula).setRight(toAdd);
			}
		}

		SendNotify.sendAddFormulaNotify(parentFormula, toAdd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (parentFormula instanceof UnaryFormula) {
			((UnaryFormula) parentFormula).setChild(null);
		} else if (parentFormula instanceof BinaryFormula) {
			if (firstChild) {
				((BinaryFormula) parentFormula).setLeft(null);
			} else {
				((BinaryFormula) parentFormula).setRight(null);
			}
		}

		SendNotify.sendAddFormulaNotify(parentFormula, null);
	}
}

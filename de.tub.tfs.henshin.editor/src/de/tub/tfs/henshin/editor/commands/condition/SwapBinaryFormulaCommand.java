package de.tub.tfs.henshin.editor.commands.condition;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.SendNotify;

/**
 * This command swap an AND with OR and an OR with AND.
 * 
 * @author angel
 */
public class SwapBinaryFormulaCommand extends Command {

	private BinaryFormula oldFormula;
	private BinaryFormula newFormula;
	private EObject parentObject;

	public SwapBinaryFormulaCommand(final BinaryFormula oldFormula) {
		this.oldFormula = oldFormula;

		parentObject = oldFormula.eContainer();

		newFormula = createFormulaToSet();
		newFormula.setLeft(oldFormula.getLeft());
		newFormula.setRight(oldFormula.getRight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return oldFormula != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (actualizeParent(newFormula, oldFormula)) {
			SendNotify.sendSwapFormulaNotify(newFormula.eContainer(),
					oldFormula, newFormula);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if (actualizeParent(oldFormula, newFormula)) {
			SendNotify.sendSwapFormulaNotify(oldFormula.eContainer(),
					newFormula, oldFormula);
		}
	}

	/**
	 * Sets the given formula to set in parent object of the given formula to
	 * replace.
	 * 
	 * @param formulaToSet
	 *            A binary formula to set to parent.
	 * @param formulaToReplace
	 *            A binary formula to replace from parent.
	 */
	private boolean actualizeParent(final BinaryFormula formulaToSet,
			final BinaryFormula formulaToReplace) {
		if (parentObject instanceof Graph) {
			((Graph) parentObject).setFormula(formulaToSet);
		} else if (parentObject instanceof UnaryFormula) {
			((UnaryFormula) parentObject).setChild(formulaToSet);
		} else {
			final BinaryFormula binaryFormula = (BinaryFormula) parentObject;
			if (binaryFormula.getLeft() == formulaToReplace) {
				binaryFormula.setLeft(formulaToSet);
			} else if (binaryFormula.getRight() == formulaToReplace) {
				binaryFormula.setRight(formulaToSet);
			} else {
				return false;
			}
		}

		return true;
	}

	/**
	 * Checks the type of the formula to replace:<br>
	 * <li>If the formula is from type AND, then create OR-formula and returns
	 * it. <li>If the formula is from type OR, then create AND-formula and
	 * returns it. <li>Otherwise, returns {@code null}.
	 * 
	 * @return A binary formula to set.
	 */
	private BinaryFormula createFormulaToSet() {
		if (oldFormula instanceof And) {
			return HenshinFactory.eINSTANCE.createOr();
		} else {
			return HenshinFactory.eINSTANCE.createAnd();
		}
	}

}

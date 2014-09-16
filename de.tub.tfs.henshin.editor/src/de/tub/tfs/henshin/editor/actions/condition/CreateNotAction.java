/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.condition;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.CreateConditionCommand;
import de.tub.tfs.henshin.editor.commands.condition.CreateFormulaCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * The Class CreateNotAction.
 * 
 * @author angel
 */
public class CreateNotAction extends CreateFormulaAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.CreateNotAction";

	/**
	 * Instantiates a new creates the not action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateNotAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Not-Condition");
		setDescription("Create Not Condition");
		setToolTipText("Create a new not condition for the selected graph.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.condition.CreateConditionTreeAction#run()
	 */
	@Override
	public void run() {
		Not not = HenshinFactory.eINSTANCE.createNot();
		Command command = null;
		if (parentFormula == null) {
			command = new CreateConditionCommand(premise, not);
		} else {
			command = new CreateFormulaCommand(premise, parentFormula, not);

			if (parentFormula instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) parentFormula;
				if (binaryFormula.getLeft() != null
						&& binaryFormula.getRight() == null) {
					command = new CreateFormulaCommand(premise, parentFormula,
							not, false);
				}
			}
		}

		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.actions.condition.CreateFormulaAction#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("not16x16.png");
	}
}

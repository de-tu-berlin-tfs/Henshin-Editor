/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * @author nam
 * 
 */
public class ChangeSubUnitCounterCommand extends CompoundCommand {

	public ChangeSubUnitCounterCommand(int newCounter, final SubUnitLayout model) {
		int diff = newCounter - model.getCounter();

		if (diff > 0) {
			for (int i = 0; i < diff; i++) {
				add(new AddTransformationUnitCommand(
						(TransformationUnit) model.getParent(),
						(TransformationUnit) model.getModel(), model.getIndex()));
			}
		} else {
			SequentialUnit parent = (SequentialUnit) model.getParent();

			for (int i = 0; i > diff; i--) {
				add(new RemoveTransformationUnitCommand(parent, parent
						.getSubUnits().get(model.getIndex() - i)));
			}
		}
	}
}

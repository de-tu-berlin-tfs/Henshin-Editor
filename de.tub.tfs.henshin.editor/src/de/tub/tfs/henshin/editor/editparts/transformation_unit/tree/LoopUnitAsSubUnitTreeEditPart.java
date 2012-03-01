/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.LoopUnitTreeEditPart;

/**
 * @author nam
 * 
 */
public class LoopUnitAsSubUnitTreeEditPart extends LoopUnitTreeEditPart {

	private TransformationUnit parent;

	/**
	 * @param model
	 */
	public LoopUnitAsSubUnitTreeEditPart(TransformationUnit parent,
			LoopUnit model) {
		super(model);

		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.tree.rule.RuleTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new TransformationUnitAsSubUnitComponentEditPolicy(parent));
	}
}

/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;

/**
 * The Class PriorityUnitAsSubUnitTreeEditPart.
 */
public class PriorityUnitAsSubUnitTreeEditPart extends PriorityUnitTreeEditPart {

	/** The transformation unit. */
	private Unit transformationUnit;

	/**
	 * Instantiates a new priority unit as sub unit tree edit part.
	 * 
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 */
	public PriorityUnitAsSubUnitTreeEditPart(Unit context,
			PriorityUnit model) {
		super(model);
		this.transformationUnit = context;
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
				new TransformationUnitAsSubUnitComponentEditPolicy(
						transformationUnit));
	}

}

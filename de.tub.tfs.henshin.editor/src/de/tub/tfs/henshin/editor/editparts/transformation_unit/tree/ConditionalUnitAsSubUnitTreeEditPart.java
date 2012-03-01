/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;

/**
 * The Class ConditionalUnitAsSubUnitTreeEditPart.
 */
public class ConditionalUnitAsSubUnitTreeEditPart extends
		ConditionalUnitTreeEditPart {

	/** The transformation unit. */
	private TransformationUnit transformationUnit;

	/**
	 * Instantiates a new conditional unit as sub unit tree edit part.
	 * 
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 */
	public ConditionalUnitAsSubUnitTreeEditPart(TransformationUnit context,
			ConditionalUnit model) {
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

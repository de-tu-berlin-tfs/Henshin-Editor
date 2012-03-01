/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;

/**
 * The Class IndependentUnitAsSubUnitTreeEditPart.
 */
public class IndependentUnitAsSubUnitTreeEditPart extends
		IndependentUnitTreeEditPart {

	/** The transformation unit. */
	private TransformationUnit transformationUnit;

	/**
	 * Instantiates a new independent unit as sub unit tree edit part.
	 * 
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 */
	public IndependentUnitAsSubUnitTreeEditPart(TransformationUnit context,
			IndependentUnit model) {
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

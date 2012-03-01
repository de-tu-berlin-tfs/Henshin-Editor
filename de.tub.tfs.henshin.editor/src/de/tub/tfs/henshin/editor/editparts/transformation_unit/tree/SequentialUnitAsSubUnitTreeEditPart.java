/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;

/**
 * The Class SeqUnitAsSubUnitTreeEditPart.
 */
public class SequentialUnitAsSubUnitTreeEditPart extends
		SequentialUnitTreeEditPart {

	/** The transformation unit. */
	private TransformationUnit transformationUnit;

	/**
	 * Instantiates a new sequential unit as sub unit tree edit part.
	 * 
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 */
	public SequentialUnitAsSubUnitTreeEditPart(TransformationUnit context,
			SequentialUnit model) {
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

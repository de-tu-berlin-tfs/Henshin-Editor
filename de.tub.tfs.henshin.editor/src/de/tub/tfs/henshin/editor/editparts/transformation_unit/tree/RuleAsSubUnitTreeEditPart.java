package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.rule.tree.RuleTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.TransformationUnitAsSubUnitComponentEditPolicy;

/**
 * The Class RuleAsSubUnitTreeEditPart.
 */
public class RuleAsSubUnitTreeEditPart extends RuleTreeEditPart {

	/** The transformation unit. */
	private TransformationUnit transformationUnit;

	/**
	 * Instantiates a new rule as sub unit tree edit part.
	 * 
	 * @param context
	 *            the context
	 * @param model
	 *            the model
	 */
	public RuleAsSubUnitTreeEditPart(TransformationUnit context, Rule model) {
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
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE, new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.tree.rule.RuleTreeEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Object> getModelChildren() {
		return Collections.EMPTY_LIST;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void registerModel() {
		getViewer().getEditPartRegistry().put(
				"SubEditPartOf" + getModel().toString(), this);

	}

}

/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.tree;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.graph.tree.NodeTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.NodeRuleComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.rule.RuleNodePropertySource;
import de.tub.tfs.henshin.editor.util.validator.RuleNodeNameEditorValidator;

/**
 * The Class NodeRuleTreeEditPart.
 * 
 * @author Johann
 */
public class NodeRuleTreeEditPart extends NodeTreeEditPart {

	/**
	 * Instantiates a new node rule tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public NodeRuleTreeEditPart(Node model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected void createEditPolicies() {
		if (nodeLayout.isEnabled()) {
			installEditPolicy(EditPolicy.COMPONENT_ROLE,
					new NodeRuleComponentEditPolicy());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new RuleNodeNameEditorValidator(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new RuleNodePropertySource(getCastedModel());
	}

}

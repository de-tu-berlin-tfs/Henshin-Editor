/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.AttributeEditPart;
import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;

/**
 * The Class AttributeRuleEditPart.
 * 
 * @author Johann
 */
public class AttributeRuleEditPart extends AttributeEditPart {

	/**
	 * Instantiates a new attribute rule edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public AttributeRuleEditPart(Attribute model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.graphs.AttributeEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new TypeEditorValidator(getCastedModel());
	}

}

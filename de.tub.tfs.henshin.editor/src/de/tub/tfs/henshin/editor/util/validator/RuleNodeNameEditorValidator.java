/**
 * 
 */
package de.tub.tfs.henshin.editor.util.validator;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tub.tfs.henshin.editor.util.FormulaTree;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * @author Johann
 * 
 */
public class RuleNodeNameEditorValidator implements ICellEditorValidator,
		IInputValidator {

	private Rule rule;

	private Node node;

	/**
	 * @param rule
	 */
	public RuleNodeNameEditorValidator(Node node) {
		super();
		this.node = node;
		this.rule = ModelUtil.getRule(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ICellEditorValidator#isValid(java.lang.Object)
	 */
	@Override
	public String isValid(Object value) {
		if (value instanceof String) {
			return isValid((String) value);
		}
		return "Is not String!";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.IInputValidator#isValid(java.lang.String)
	 */
	@Override
	public String isValid(String text) {
		String disallowed = "+-:;!\"§$%&/()=?*/#~<>|.,";
		for (int i = 0; i < text.length(); i++) {
			if (disallowed.indexOf(text.charAt(i)) >= 0) {
				return "Falsches Zeichen im Namen";
			}
		}

		List<Node> nodes = new ArrayList<Node>();
		nodes.addAll(rule.getLhs().getNodes());
		nodes.addAll(rule.getRhs().getNodes());
		if (rule.getLhs().getFormula() != null) {
			for (Graph graph : FormulaTree.getFormulaGraph(rule.getLhs()
					.getFormula())) {
				nodes.addAll(graph.getNodes());
			}
		}
		for (Mapping mapping : rule.getMappings()) {
			if (mapping.getOrigin() == node) {
				nodes.remove(mapping.getImage());
			}
			if (mapping.getImage() == node) {
				nodes.remove(mapping.getOrigin());
			}
		}
		nodes.remove(node);
		for (Node temp : nodes) {
			if (text.equals(temp.getName())) {
				return "The name \"" + text
						+ "\" exist already! Please enter another one.";
			}
		}
		return null;
	}

}

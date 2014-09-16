/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.editor.editparts.condition.graphical.ApplicationConditionEditPart;
import de.tub.tfs.henshin.editor.editparts.condition.graphical.BinaryFormulaEditPart;
import de.tub.tfs.henshin.editor.editparts.condition.graphical.ConditionEditPart;
import de.tub.tfs.henshin.editor.editparts.condition.graphical.UnaryFormulaEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.EdgeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.MultiNodeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.graphical.AttributeRuleEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.graphical.LhsRhsEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.graphical.RuleNodeEditPart;
import de.tub.tfs.henshin.editor.ui.rule.RulePage;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;

/**
 * A {@link EditPartFactory factory} for creating {@link EditPart}s in a rule
 * {@link RulePage page}.
 * 
 * @author Johann
 */
public class RuleEditPartFactory implements EditPartFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Edge) {
			return new EdgeEditPart((Edge) model);
		}
		if (model instanceof Node) {
			final Node node = (Node) model;
			if (HenshinLayoutUtil.INSTANCE.isMultiNode(node)) {
				return new MultiNodeEditPart(node);
			}
			return new RuleNodeEditPart(node);
		}
		if (model instanceof Attribute) {
			return new AttributeRuleEditPart((Attribute) model);
		}

		if (model instanceof Graph) {
			Graph graph = (Graph) model;
			if (graph.eContainer() instanceof Rule) {
				return new LhsRhsEditPart(graph);
			} else if (graph.eContainer() instanceof NestedCondition) {
				return new GraphEditPart(graph);
			}
		}

		if (model instanceof Formula) {
			if (context == null) {
				if (model instanceof NestedCondition) {
					return new GraphEditPart(
							((NestedCondition) model).getConclusion());
				} else {
					return new ConditionEditPart((Formula) model);
				}
			} else {
				if (model instanceof NestedCondition) {
					return new ApplicationConditionEditPart(
							(NestedCondition) model);
				} else if (model instanceof UnaryFormula) {
					return new UnaryFormulaEditPart((UnaryFormula) model);
				} else if (model instanceof BinaryFormula) {
					return new BinaryFormulaEditPart((BinaryFormula) model);
				}
			}
		}

		return null;
	}

}

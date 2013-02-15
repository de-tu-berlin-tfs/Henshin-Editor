package de.tub.tfs.henshin.editor.editparts;

import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.editor.editparts.condition.tree.ApplicationConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.condition.tree.ConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.epackage.EPackageTreeEditpart;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.tree.FlowDiagramTreeEditpart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.AttributeTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.EdgeTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.NodeTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.AttributeConditionTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.NodeRuleTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.RuleTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transSys.TransformationSystemTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.LoopUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.ConditionalUnitAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.ConditionalUnitPartTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.ConditionalUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.IndependentUnitAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.IndependentUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.LoopUnitAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.PriorityUnitAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.PriorityUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.RuleAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.SequentialUnitAsSubUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.SequentialUnitTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.parameter.ParameterTreeEditPart;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * A factory for creating tree {@link EditPart}s for the
 * {@link HenshinTreeEditor}.
 * 
 * @author Johann, Angeline, Nam
 */
public class HenshinTreeEditPartFactory implements EditPartFactory {

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
			return new EdgeTreeEditPart((Edge) model);
		}
		if (model instanceof Attribute) {
			return new AttributeTreeEditPart((Attribute) model);
		}
		if (model instanceof Node) {
			if (context instanceof LhsRhsTreeEditPart
					|| context instanceof ApplicationConditionTreeEditPart) {
				return new NodeRuleTreeEditPart((Node) model);
			}
			return new NodeTreeEditPart((Node) model);
		}

		if (model instanceof Graph) {
			if (context instanceof RuleTreeEditPart) {
				Rule rule = (Rule) context.getModel();
				if (rule.getLhs() == model || rule.getRhs() == model) {
					return new LhsRhsTreeEditPart((Graph) model);
				}
			}

			return new GraphTreeEditPart((Graph) model);
		}

		if (model instanceof NestedCondition) {
			return new ApplicationConditionTreeEditPart(
					((NestedCondition) model).getConclusion());
		}

		if (model instanceof Formula) {
			return new ConditionTreeEditPart((Formula) model);
		}

		if (model instanceof Rule) {
			if (context.getModel() instanceof Unit) {
				return new RuleAsSubUnitTreeEditPart(
						(Unit) context.getModel(), (Rule) model);
			}
			return new RuleTreeEditPart((Rule) model);
		}

		if (model instanceof Module) {
			return new TransformationSystemTreeEditPart(
					(Module) model);
		}

		if (model instanceof EPackage) {
			return new EPackageTreeEditpart((EPackage) model);
		}

		if (model instanceof LoopUnit) {
			if (context.getModel() instanceof Unit) {
				return new LoopUnitAsSubUnitTreeEditPart(
						(Unit) context.getModel(),
						(LoopUnit) model);
			}

			return new LoopUnitTreeEditPart((LoopUnit) model);
		}

		if (model instanceof SequentialUnit) {
			if (context.getModel() instanceof Unit) {
				return new SequentialUnitAsSubUnitTreeEditPart(
						(Unit) context.getModel(),
						(SequentialUnit) model);
			}
			return new SequentialUnitTreeEditPart((SequentialUnit) model);
		}

		if (model instanceof ConditionalUnit) {
			if (context.getModel() instanceof Unit) {
				return new ConditionalUnitAsSubUnitTreeEditPart(
						(Unit) context.getModel(),
						(ConditionalUnit) model);
			}
			return new ConditionalUnitTreeEditPart((ConditionalUnit) model);
		}

		if (model instanceof IndependentUnit) {
			if (context.getModel() instanceof Unit) {
				return new IndependentUnitAsSubUnitTreeEditPart(
						(Unit) context.getModel(),
						(IndependentUnit) model);
			}
			return new IndependentUnitTreeEditPart((IndependentUnit) model);
		}
		if (model instanceof PriorityUnit) {
			if (context.getModel() instanceof Unit) {
				return new PriorityUnitAsSubUnitTreeEditPart(
						(Unit) context.getModel(),
						(PriorityUnit) model);
			}
			return new PriorityUnitTreeEditPart((PriorityUnit) model);
		}

		if (model instanceof Parameter) {
			return new ParameterTreeEditPart((Parameter) model);
		}

		if (model instanceof ConditionalUnitPart) {
			return new ConditionalUnitPartTreeEditPart(
					(ConditionalUnitPart) model);
		}

		if (model instanceof AttributeCondition) {
			return new AttributeConditionTreeEditPart(
					(AttributeCondition) model);
		}

		if (model instanceof FlowDiagram) {
			return new FlowDiagramTreeEditpart((FlowDiagram) model);
		}

		if (model instanceof EContainerDescriptor) {
			return createContainerTreeEdiparts((EContainerDescriptor) model,
					context);
		}

		Assert.isTrue(model == null,
				"TreeEditPartFactory could not create an EditPart for model element"
						+ model);

		return null;
	}

	private EditPart createContainerTreeEdiparts(EContainerDescriptor model,
			Object context) {
		Set<?> contentTypes = model.getContainmentMap().keySet();

		if (contentTypes.size() == 1) {
			EClass contentType = (EClass) contentTypes.toArray()[0];

			if (HenshinPackage.Literals.PARAMETER.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Parameters",
						ResourceUtil.ICONS.PARAMETER_FOLDER.img(16),
						model);
			}

			if (FlowControlPackage.Literals.PARAMETER.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Parameters",
						ResourceUtil.ICONS.PARAMETER_FOLDER.img(16),
						model);
			}

			if (EcorePackage.Literals.EPACKAGE.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Imports",
						ResourceUtil.ICONS.EPACKAGE_FOLDER.img(16),
						model);
			}

			if (HenshinPackage.Literals.GRAPH.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Instances",
						ResourceUtil.ICONS.GRAPH_FOLDER.img(16), model);
			}

			if (HenshinPackage.Literals.RULE.equals(contentType)) {
				if (context instanceof RuleTreeEditPart){
					return new EObjectsContainerTreeEditPart("Multi Rules",
							ResourceUtil.ICONS.RULE_FOLDER.img(16), model);	
				} else {
					return new EObjectsContainerTreeEditPart("Rules",
							ResourceUtil.ICONS.RULE_FOLDER.img(16), model);
				}
			}

			if (HenshinPackage.Literals.UNIT.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Tranformation Units",
						ResourceUtil.ICONS.TRANS_UNIT_FOLDER.img(16),
						model);
			}

			if (FlowControlPackage.Literals.FLOW_DIAGRAM.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Flow Diagrams",
						ResourceUtil.ICONS.FLOW_DIAGRAM_FOLDER.img(16),
						model);
			}

			if (HenshinPackage.Literals.ATTRIBUTE_CONDITION.equals(contentType)) {
				return new EObjectsContainerTreeEditPart("Attribute Condition",
						ResourceUtil.ICONS.DEFAULT_FOLDER.img(16),
						model);
			}
		} else {
			return new EObjectsContainerTreeEditPart("Graph Elements",
					ResourceUtil.ICONS.DEFAULT_FOLDER.img(16), model);
		}

		return null;
	}
}

/** 	
 * 
 * 
 */
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.DeleteTransformationUnit;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;

/**
 * The Class DeleteRuleCommand.
 * 
 * @author Johann, Angeline Warning
 */
public class DeleteRuleCommand extends CompoundCommand {

	private Rule rule;


	/**
	 * Constructs a new {@link DeleteRuleCommand} for a given {@link Rule model}
	 * .
	 * 
	 * @param rule
	 *            the {@link Rule} to be deleted.
	 * @param parentModel
	 *            the containing {@link Module}.
	 */
	public DeleteRuleCommand(Rule rule, EObject parentModel) {
		this.rule = rule;
		/*
		 * Clears activities with content referenced to rule
		 */
		FlowControlSystem flowSystem = FlowControlUtil.INSTANCE
				.getFlowControlSystem(rule);

		if (flowSystem != null) {
			TreeIterator<EObject> it = flowSystem.eAllContents();

			while (it.hasNext()) {
				EObject eOjb = it.next();

				if (eOjb instanceof Activity) {
					Activity a = (Activity) eOjb;

					if (a.getContent() == rule) {
						add(new SetActivityContentCommand(a, null));
					}
				}
			}
		}

		/*
		 * Deletes all rule nodes in RHS
		 */
		for (Node node : rule.getRhs().getNodes()) {
			add(new DeleteRuleNodeCommand(node));
		}

		/*
		 * Deletes all rule nodes and formulas in LHS
		 */
		Graph lhs = rule.getLhs();
		for (Node node : lhs.getNodes()) {
			add(new DeleteRuleNodeCommand(node));
		}

		Formula formula = lhs.getFormula();
		if (formula != null) {
			add(new DeleteFormulaCommand(formula, lhs));
		}

		add(new DeleteTransformationUnit(rule));
	}

	
	@Override
	public boolean canExecute() {
		if (rule.eContainer() instanceof Rule)
			return true;
		return super.canExecute();
	}
}

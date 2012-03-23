package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import tggeditor.commands.delete.DeleteNodeCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteRuleCommand deletes the rule.
 */
public class DeleteRuleCommand extends CompoundCommand {

	/**
	 * the constructor
	 * @param rule the rule to be deleted
	 */
	public DeleteRuleCommand(Rule rule) {
		for (Node n : rule.getLhs().getNodes()) {
			add(new DeleteNodeCommand(n));
		}
		for (Node n : rule.getRhs().getNodes()) {
			add(new DeleteNodeCommand(n));
		}
		add(new SimpleDeleteEObjectCommand(rule));
	}

}

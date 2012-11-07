package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.GraphLayout;
import tggeditor.commands.delete.DeleteGraphCommand;
import tggeditor.commands.delete.DeleteNodeCommand;
import tggeditor.util.GraphUtil;

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
		GraphLayout divSC = GraphUtil.getGraphLayout(rule.getRhs(), true);
		GraphLayout divCT = GraphUtil.getGraphLayout(rule.getRhs(), false);
		if (divSC != null) add (new SimpleDeleteEObjectCommand(divSC));
		if (divCT != null) add (new SimpleDeleteEObjectCommand(divCT));
		add(new SimpleDeleteEObjectCommand(rule));
	}

}

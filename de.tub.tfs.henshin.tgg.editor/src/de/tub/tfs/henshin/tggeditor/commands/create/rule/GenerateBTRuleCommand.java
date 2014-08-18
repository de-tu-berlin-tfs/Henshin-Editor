package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;

public class GenerateBTRuleCommand extends GenerateOpRuleCommand {

	public GenerateBTRuleCommand(Rule rule) {
		this(rule,null);
	}
	
	public GenerateBTRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		prefix = "BT_";
		OP_RULE_CONTAINER_PREFIX = "BTRule_";
		OP_RULE_FOLDER = "BTRuleFolder";
		OP_RULES_PNG = "BTRules.png";
	}
	
	@Override
	protected void deleteTRule(Rule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr,null,RuleUtil.TGG_BT_RULE);
		deleteCommand.execute();
	}

	@Override
	protected void addNodeProcessors(){
		// process all nodes in the source component
		nodeProcessors.put(TripleComponent.TARGET, new OpRuleNodeProcessor());
	};

	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_BT_RULE;
	}
	@Override
	protected boolean filterNode(TNode node) {
		return NodeUtil.isTargetNode(node);
	}
}

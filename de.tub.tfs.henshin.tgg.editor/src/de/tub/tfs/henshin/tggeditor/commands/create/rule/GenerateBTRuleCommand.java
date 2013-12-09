package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

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
	protected void deleteTRule(TRule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr.getRule(),null,RuleUtil.TGG_BT_RULE);
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
	protected boolean filterNode(Node node) {
		return NodeUtil.isTargetNode(node);
	}
}

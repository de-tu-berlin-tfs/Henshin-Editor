package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

public class GenerateCCRuleCommand extends GenerateOpRuleCommand {

	public GenerateCCRuleCommand(Rule rule) {
		this(rule,null);
	}
	
	public GenerateCCRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		prefix = "CC_";
		OP_RULE_CONTAINER_PREFIX = "CCRule_";
		OP_RULE_FOLDER = "CCRuleFolder";
		OP_RULES_PNG = "CCRules.png";
	}
	
	@Override
	protected void deleteTRule(TRule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr.getRule(),null,RuleUtil.TGG_CC_RULE);
		deleteCommand.execute();
	}

	@Override
	protected void addNodeProcessors(){
		// process all nodes in the source component
		nodeProcessors.put(TripleComponent.SOURCE, new OpRuleNodeProcessor());
		nodeProcessors.put(TripleComponent.CORRESPONDENCE, new OpRuleNodeProcessor());
		nodeProcessors.put(TripleComponent.TARGET, new OpRuleNodeProcessor());
	};

	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_CC_RULE;
	}

	@Override
	protected boolean filterNode(Node node) {
		return true; // all triple components are handled
	}
}

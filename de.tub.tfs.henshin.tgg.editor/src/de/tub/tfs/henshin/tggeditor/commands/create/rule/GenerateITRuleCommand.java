package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.GenerateOpRuleCommand.OpRuleNodeProcessor;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;
//NEW GERARD
public class GenerateITRuleCommand extends GenerateOpRuleCommand {

	public GenerateITRuleCommand(Rule rule) {
		this(rule,null);
		
	}
	
	//REF 4
	public GenerateITRuleCommand(Rule rule,IndependentUnit unit) {
		super(rule,unit);
		prefix = "IT_";
		OP_RULE_CONTAINER_PREFIX = "ITRule_";
		OP_RULE_FOLDER = "ITRuleFolder";
		OP_RULES_PNG = "ITRules.png";
	}
	
	//REF 5
	protected void deleteTRule(Rule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr,null,RuleUtil.TGG_IT_RULE);
		deleteCommand.execute();
	}


	@Override
	protected void addNodeProcessors(){
		// process all nodes in the source and target component
		nodeProcessors.put(TripleComponent.SOURCE, new OpRuleNodeProcessor());
		nodeProcessors.put(TripleComponent.TARGET, new OpRuleNodeProcessor());
	};

	
	
	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_IT_RULE;
	}
	
	@Override
	protected boolean filterNode(TNode node) {
		return NodeUtil.isSourceNode(node) || NodeUtil.isTargetNode(node);
	}
}

/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteOpRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;

public class GenerateRuleCommand extends GenerateOpRuleCommand {

	// FIXME: can we delete this class?
	public GenerateRuleCommand(Rule rule1) {
		this(rule1, null);
		
	}
	

	public GenerateRuleCommand(Rule rule1, IndependentUnit unit) {
		super(rule1,  unit);
		prefix = "";
		OP_RULE_CONTAINER_PREFIX = "Rule_";
		OP_RULE_FOLDER = "RuleFolder";
		OP_RULES_PNG = "Rules.png";
	}
	
	@Override
	protected void deleteTRule(Rule tr) {
		DeleteOpRuleCommand deleteCommand = new DeleteOpRuleCommand(
				tr,null,RuleUtil.TGG_RULE);
		deleteCommand.execute();
	}


	@Override
	protected void addNodeProcessors(){
		// process all nodes in the source component
		//nodeProcessors.put(TripleComponent.SOURCE, new OpRuleNodeProcessor());
	};

	
	
	@Override
	protected String getRuleMarker() {
		return RuleUtil.TGG_RULE;
	}
	@Override
	protected boolean filterNode(TNode node) {
		return false;
	}
}

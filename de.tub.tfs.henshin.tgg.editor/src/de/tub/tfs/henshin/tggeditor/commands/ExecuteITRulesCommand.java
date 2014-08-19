package de.tub.tfs.henshin.tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;

//NEW GERARD
/**
 * The Class ExecuteITRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link ITRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteITRulesCommand extends ExecuteOpRulesCommand {

	/**the constructor
	 * @param graph {@link ExecuteITRulesCommand#graph}
	 * @param iTRuleList {@link ExecuteITRulesCommand#opRuleList}
	 */
	public ExecuteITRulesCommand(Graph graph, List<Rule> iTRuleList) {
		super(graph,iTRuleList);
		consistencyType = "Source-Target";
		consistencyTypeLowerCase = "source-target";
	}
}

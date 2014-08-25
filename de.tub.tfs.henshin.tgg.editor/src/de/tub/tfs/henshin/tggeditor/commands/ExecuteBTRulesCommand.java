package de.tub.tfs.henshin.tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;


import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;

/**
 * The Class ExecuteFTRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link FTRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteBTRulesCommand extends ExecuteOpRulesCommand {


	
	
	/**the constructor
	 * @param graph {@link ExecuteBTRulesCommand#graph}
	 * @param fTRuleList {@link ExecuteBTRulesCommand#fTRuleList}
	 */
	public ExecuteBTRulesCommand(Graph graph, List<Rule> fTRuleList) {
		super(graph,fTRuleList);
		consistencyType = "Target";
		consistencyTypeLowerCase = "target";
	}
}

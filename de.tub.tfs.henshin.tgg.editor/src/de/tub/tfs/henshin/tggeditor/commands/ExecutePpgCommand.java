package de.tub.tfs.henshin.tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;


/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public abstract class ExecutePpgCommand extends ExecuteOpRulesCommand {

	
	/**the constructor
	 * @param graph {@link ExecutePpgCommand#graph}
	 * @param opRuleList {@link ExecutePpgCommand#opRuleList}
	 */
	public ExecutePpgCommand(Graph graph, List<Rule> opRuleList) {
		super(graph,opRuleList);
	}
	
	
	
}
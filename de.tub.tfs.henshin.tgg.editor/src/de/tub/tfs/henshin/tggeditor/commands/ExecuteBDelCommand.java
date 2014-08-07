package de.tub.tfs.henshin.tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.NodeUtil;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class ExecuteBDelCommand extends ExecuteDelCommand {

	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecuteBDelCommand#graph}
	 * @param opRuleList {@link ExecuteBDelCommand#opRuleList}
	 */
	public ExecuteBDelCommand(Graph graph, List<Rule> opRuleList) {
		super(graph,opRuleList);
	}

	
	protected boolean isInTranslationComponent(TNode node){
		return NodeUtil.isTargetNode(node);
	};
	
	


}

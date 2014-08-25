package de.tub.tfs.henshin.tggeditor.commands;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tggeditor.util.NodeUtil;

/**
 * The Class ExecutionInitFTCommand creates the initial marking for executing the operational FT rules on a given graph. 
 */
public class ExecutionInitFTCommand extends ExecutionInitCommand {


	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;
	
	/**the constructor
	 * @param graph {@link ExecutionInitFTCommand#graph}
	 * @param opRuleList {@link ExecutionInitFTCommand#opRuleList}
	 */
	public ExecutionInitFTCommand(Graph graph) {
		super(graph);
		this.graph = graph;
	}

	protected boolean isInMarkedComponent(Node node){
		return NodeUtil.isSourceNode(node);
	};

}

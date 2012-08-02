package tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UserConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import tgg.TRule;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeTypes.NodeGraphType;
import tggeditor.util.NodeUtil;

/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class FTRuleConstraint implements UserConstraint {

	/**
	 * This hashmap will be filled furing executing all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. In the hashmap are all the already transalted nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private Node node;
	/**
	 * Refers to this.node.
	 */
	private Boolean nodeIsTranslated;

	/**
	 * the constructor
	 * @param node see {@link FTRuleConstraint#node}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleConstraint(Node node, HashMap<Node, Boolean> isTranslatedMap) {
		this.node = node;
		this.isTranslatedMap = isTranslatedMap;
		this.nodeIsTranslated = NodeUtil.getNodeLayout(this.node).getLhsTranslated();
	}

	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	public boolean check(Node graphNode) {
		if (isSourceNode(graphNode)) {
			if (this.node.eContainer().eContainer() instanceof Rule) {
				if ((nodeIsTranslated && isTranslatedMap.containsKey(graphNode))
						|| (!nodeIsTranslated && !isTranslatedMap.containsKey(graphNode))) {
					return true;
				}
			}
			// for NAC nodes (evntl only not mapped from LHS to NAC graph ???)
			else if (isTranslatedMap.containsKey(graphNode)) {
					return true;
			}
			// else
			return false;
		}
		
		// for TARGET and CORRESPONDENCE 
		return true;
	}

	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */
	private boolean isSourceNode(Node graphNode) {
		NodeGraphType type = NodeTypes.getNodeGraphType(graphNode);
		return type == NodeGraphType.SOURCE;
	}

	@Override
	public boolean check(DomainSlot slot, EGraph graph) {
		return check(((HenshinEGraph)graph).getObject2NodeMap().get(slot.getValue()));
	}

	@Override
	public boolean unlock(Variable sender, DomainSlot slot) {
		// TODO Auto-generated method stub
		return false;
	}

}

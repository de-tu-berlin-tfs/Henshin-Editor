package de.tub.tfs.henshin.tgg.interpreter;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;

public class NodeUtil {

	public NodeUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * checks whether a node belongs to the source component
	 * @param node the graph node to be analysed
	 * @return true, if the node belongs to the source component
	 */
	public static boolean isSourceNodeByPosition(TNode node) {
		if (node==null) return false;
		//return guessTripleComponent(node) == TripleComponent.SOURCE;
		// position has to be left of SC divider
		TripleGraph tripleGraph =(TripleGraph) node.getGraph();
		return node.getX() <= tripleGraph.getDividerSC_X();
	}

}

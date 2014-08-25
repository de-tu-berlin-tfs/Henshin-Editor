package de.tub.tfs.henshin.tggeditor.commands;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;

/**
 * The Class ExecuteOpRulesCommand executes all the given Rules ({@link TRule}) on a given graph. For the
 * execution are mainly the classes from org.eclipse.emf.henshin.interpreter used. The mapping 
 * of the RuleApplication will be checked with the class {@link OpRuleConstraint}.
 * There will be also the layouts for nodes and edges created.
 */
public class RemoveMarkersCommand extends Command {

	/**
	 * The graph on which all the rules will be applied.
	 */
	protected Graph graph;

	
	
	/**the constructor
	 * @param graph {@link RemoveMarkersCommand#graph}
	 * @param opRuleList {@link RemoveMarkersCommand#opRuleList}
	 */
	public RemoveMarkersCommand(Graph graph) {
		super();
		this.graph = graph;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (graph != null);
	}
	
	/** Executes all the rules on the graph as long as it's possible. The choosing of the sequence 
	 * of RuleApplications is determinated by the order in the {@link RemoveMarkersCommand#opRuleList}. 
	 * So when you execute the command twice without changing the order in the list, the same sequence
	 * of applications is chosen.
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		for (Node n : graph.getNodes()) {
			TNode node = (TNode) n;
				// set marker type to mark the nodes
				node.setMarkerType(null);

				// check contained attributes
				for (Attribute at: node.getAttributes()){
					// set marker type to mark the attributes
					TAttribute a =(TAttribute) at;
					a.setMarkerType(null);
				}
			}
		for (Edge e : graph.getEdges()) {
			TEdge edge = (TEdge) e;
				// set marker type to mark the edges
				edge.setMarkerType(null);
		}
	}

	
	



	



}

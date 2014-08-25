package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand;



/**
 * The class CreateRuleNodeCommand creates a node in a rule. Better: it creates in the rule the
 * rhs node, the lhs node and the mapping. It also sets attributes in the rulenodelayout 
 * accordingly.
 */
public class CreateRuleNodeCommand extends CreateNodeCommand {
	
	/**
	 * the lsh node
	 */
	private Node lhsNode;
	/**
	 * the rhs node
	 */
	private Node rhsNode;
	/**
	 * the rhs graph
	 */
	private TripleGraph rhsGraph;
	/**
	 * the mapping between lhs and rhs node
	 */
	private Mapping mapping;
	/**
	 * the container rule
	 */
	private Rule rule;
	/**
	 * the lhs graph
	 */
	private Graph lhsGraph;
	
	/**
	 * the constructor
	 * @param n the rhs node
	 * @param rhsGraph the rhs graph
	 * @param location the location for the node layout
	 * @param nodeGraphType nodeGraphType can be source, correspondence or target
	 */
	public CreateRuleNodeCommand(TNode n, TripleGraph rhsGraph, Point location, TripleComponent tripleComponent) {
		super(n, rhsGraph, location, tripleComponent);
		rhsNode = n;
		this.rhsGraph = rhsGraph;
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateNodeCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
		
		rule = rhsGraph.getRule();
		
		lhsNode = TggFactory.eINSTANCE.createTNode();
		lhsNode.setType(rhsNode.getType());
		lhsNode.setName(rhsNode.getName());
		lhsGraph = rule.getLhs();
		lhsGraph.getNodes().add(lhsNode);
		((TNode) rhsNode).setMarkerType(null);
		
		
		mapping = HenshinFactory.eINSTANCE.createMapping(lhsNode,rhsNode);
		rule.getMappings().add(mapping);
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateNodeCommand#undo()
	 */
	@Override
	public void undo() {
		rule.getMappings().remove(mapping);
		rule.getLhs().getNodes().remove(lhsNode);
		rhsGraph.getNodes().remove(rhsNode);
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateNodeCommand#redo()
	 */
	@Override
	public void redo() {
		rule.getLhs().getNodes().add(lhsNode);
		rhsGraph.getNodes().add(rhsNode);
		rule.getMappings().add(mapping);
	}
}

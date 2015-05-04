package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.SendNotify;

public class CreateNodeCommand extends Command {

	private Graph graph;
	private TNode node;
	private int x,y;
	private String nodeName;
	private EClass nodeType;
	private TGG tgg;
	TripleComponent component;
	
	public CreateNodeCommand(TNode n, Graph graph, Point location, String component) {
		this.graph = graph;
		this.node = n;
		this.setLocation(location);
		this.tgg = GraphicalNodeUtil.getLayoutSystem(graph);
		this.component = TripleComponent.getByName(component);
	}
	
	public void setLocation(Point location) {
		if(location != null) {
			this.x = location.x;
			this.y = location.y;
		}
	}
	
	@Override
	public boolean canExecute() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void execute() {
		if (nodeType != null) {
			// create a node in the premise OR conclusion
			node.setType(nodeType);
			node.setName(nodeName);
			node.setX(x);
			node.setY(y);
			node.setComponent(component);
			
			graph.getNodes().add(node);
			
			// if node is node of premise, then also create a node in the conclusion with mapping between premise and conclusion
			if (graph.eContainer() instanceof NestedConstraint) {
				TNode cNode = TggFactory.eINSTANCE.createTNode();
				cNode.setType(node.getType());
				cNode.setName(node.getName());
				cNode.setX(node.getX());
				cNode.setY(node.getY());
				cNode.setComponent(node.getComponent());
				
				((NestedCondition)graph.getFormula()).getConclusion().getNodes().add(cNode);
				
				Mapping mapping = HenshinFactory.eINSTANCE.createMapping(node,cNode);
				((NestedCondition)graph.getFormula()).getMappings().add(mapping);
				SendNotify.sendAddMappingNotify(mapping);
			}
		}
	}
	
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public void setNodeType(EClass nodeType) {
		this.nodeType = nodeType;
	}

	public Graph getGraph() {
		return graph;
	}

	public TGG getTgg() {
		return tgg;
	}
	
	public TripleComponent getNodeTripleComponent() {
		return component;
	}

}

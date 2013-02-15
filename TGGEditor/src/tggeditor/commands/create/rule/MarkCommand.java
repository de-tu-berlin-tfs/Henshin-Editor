package tggeditor.commands.create.rule;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tggeditor.commands.delete.DeleteAttributeCommand;
import tggeditor.commands.delete.DeleteEdgeCommand;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class MarkCommand can mark a node in a rule as new or not new. It makes
 * all the needed changes in the model of the rule and in the tgg layouts.
 * When executed it either deletes the lhs node plus its mapping or it creates
 * a new lhs node plus its mapping. Also the edges are handled.
 */
public class MarkCommand extends CompoundCommand {
	
	/**
	 * the rhs node
	 */
	private Node rhsNode;
	/**
	 * the mapping between the rhs and lhs node
	 */
	private Mapping mapping;
	/**
	 * the lhs node
	 */
	private Node lhsNode;
	/**
	 * the nodelayout of both lhs and rhs node
	 */
	private NodeLayout nodeLayout;
	/**
	 * the rule containing lhs and rhs node 
	 */
	private Rule rule;
	/**
	 * the lhs graph
	 */
	private Graph lhsGraph;
	/**
	 * the node layout
	 */
	private NodeLayout nodelayout;

	/**
	 * The constructor
	 * @param newMapping the mapping between the rhs and lhs node
	 * @param rhsNode  the rhs node
	 */
	public MarkCommand(Mapping newMapping, Node rhsNode) {
		this.mapping = newMapping;
		this.rhsNode = rhsNode;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		getCommands().clear();
		Mapping oldMapping = NodeUtil.getNodeMapping(rhsNode);
		
		// case: node is currently preserved and shall be marked as new
		if (oldMapping != null) {
		
			// case: node is used for a NAC, then marking is not possible - should be put in canExecute
			if(NodeUtil.hasNodeNacMapping(oldMapping.getOrigin())) {
				Shell shell = new Shell();
				MessageDialog.openError(shell, "Node has NAC mapping", 
						"A 'new' Marker could not be created, because the node "+rhsNode.getName()+" has a NAC mapping.");
				shell.dispose();
			}
			else {
				mapping = oldMapping;
				mark();

			}
			
		// case: node is currently created by the rule, thus demark it and crete a node in the lhs of the rule
		}else {
			demark();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		execute();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}
	
	/**
	 * marks a rule node as new and changes the model accordingly
	 */
	private void mark() {
		rhsNode = mapping.getImage();
		lhsNode = mapping.getOrigin();
		nodeLayout = NodeUtil.getNodeLayout(rhsNode);
		nodeLayout.setNew(true);
		nodeLayout.setLhsnode(null);
		
		
		Iterator<Edge> iter = lhsNode.getIncoming().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			
			EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(edge);
			edgeLayout.setNew(true);
			edgeLayout.setLhsedge(null);
			
			add(new DeleteEdgeCommand(edge));
		}
		iter = lhsNode.getOutgoing().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			
			EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(edge);
			edgeLayout.setNew(true);
			edgeLayout.setLhsedge(null);
			
			add(new DeleteEdgeCommand(edge));
		}
		Iterator<Attribute> iterAtt = lhsNode.getAttributes().iterator();
		while (iter.hasNext()) {
			Attribute attr = iterAtt.next();
			add(new DeleteAttributeCommand(attr));
		}
		add(new SimpleDeleteEObjectCommand(lhsNode));
		add(new SimpleDeleteEObjectCommand(mapping));
		super.execute();
	}
	
	/**
	 * marks a node as not new and changes the model accordingly
	 */
	private void demark() {
		lhsNode = HenshinFactory.eINSTANCE.createNode();
		rule = rhsNode.getGraph().getContainerRule();
		
		lhsGraph = rhsNode.getGraph().getContainerRule().getLhs();
		lhsGraph.getNodes().add(lhsNode);

		lhsNode.setName(rhsNode.getName());
		lhsNode.setType(rhsNode.getType());
		
		nodelayout = NodeUtil.getNodeLayout(rhsNode);
		nodelayout.setLhsnode(lhsNode);
		nodelayout.setNew(false);
		
		mapping.setImage(rhsNode);
		mapping.setOrigin(lhsNode);
		rule.getMappings().add(mapping);
		
		
		
//		// demarking all edges - this is not necessary -> edges can be created at existing nodes
//		Iterator<Edge> iter = rhsNode.getIncoming().iterator();
//		while (iter.hasNext()) {
//			Edge rhsEdge = iter.next();
//			Node otherLhsNode = NodeUtil.getNodeLayout(
//					rhsEdge.getSource()).getLhsnode();
//			if (otherLhsNode != null) {//otherLhsNode is not a marked node
//				Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge();
//				
//				EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(rhsEdge);
//				edgeLayout.setNew(false);
//				edgeLayout.setLhsedge(lhsEdge);
//				
//				lhsEdge.setType(rhsEdge.getType());
//				lhsEdge.setSource(otherLhsNode);
//				lhsEdge.setTarget(lhsNode);
//				
//				lhsGraph.getEdges().add(lhsEdge);
//			}
//		}
//		iter = rhsNode.getOutgoing().iterator();
//		while (iter.hasNext()) {
//			Edge rhsEdge = iter.next();
//			Node otherLhsNode = NodeUtil.getNodeLayout(
//					rhsEdge.getTarget()).getLhsnode();
//			if (otherLhsNode != null) {//otherLhsNode is not a marked node
//				Edge lhsEdge = HenshinFactory.eINSTANCE.createEdge();
//				
//				EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(rhsEdge);
//				edgeLayout.setNew(false);
//				edgeLayout.setLhsedge(lhsEdge);
//				
//				lhsEdge.setType(rhsEdge.getType());
//				lhsEdge.setTarget(otherLhsNode);
//				lhsEdge.setSource(lhsNode);
//				
//				lhsGraph.getEdges().add(lhsEdge);
//			}
//		}

		Iterator<Attribute> iterAtt = rhsNode.getAttributes().iterator();
		while (iterAtt.hasNext()) {
			Attribute rhsAttr = iterAtt.next();
			Attribute lhsAttr = HenshinFactory.eINSTANCE.createAttribute();
			
			lhsAttr.setType(rhsAttr.getType());
			lhsAttr.setValue(rhsAttr.getValue());
			
			lhsNode.getAttributes().add(lhsAttr);
		}
	}

}

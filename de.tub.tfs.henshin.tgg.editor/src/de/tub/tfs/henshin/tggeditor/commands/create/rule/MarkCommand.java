package de.tub.tfs.henshin.tggeditor.commands.create.rule;

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

import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
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
	 * the rule containing lhs and rhs node 
	 */
	private Rule rule;
	/**
	 * the lhs graph
	 */
	private Graph lhsGraph;

	/**
	 * The constructor
	 * @param newMapping the mapping between the rhs and lhs node
	 * @param rhsNode  the rhs node
	 */
	public MarkCommand(Mapping newMapping, Node rhsNode) {
		this.mapping = newMapping;
		this.rhsNode = rhsNode;
	}

	public MarkCommand(Node rhsNode) {
		this.rhsNode = rhsNode;
		this.mapping = RuleUtil.getRHSNodeMapping(rhsNode);
	}
	
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		getCommands().clear();
		Mapping oldMapping = RuleUtil.getRHSNodeMapping(rhsNode);
		
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
		super.execute();
		
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

		lhsNode = RuleUtil.getLHSNode(rhsNode);
		mapping = RuleUtil.getRHSNodeMapping(rhsNode);

		// mark all contained attributes as new
		for (Attribute attr : rhsNode.getAttributes()) {
			if (attr.getIsMarked()){ // attribute is already marked as created
			}
			else
			{   // mark attribute as created
				add(new MarkAttributeCommand(attr));
//				attr.setMarkerType(RuleUtil.NEW);
//				attr.setIsMarked(true);
				// delete LHS attribute
//				Attribute lhsAttribute = RuleUtil.getLHSAttribute(attr);
//				if (lhsAttribute!=null)
//					add(new DeleteRuleAttributeCommand(lhsAttribute));				
			}
		}
		
		
		
		for(Edge e:rhsNode.getIncoming()){
			// if edge is not marked, then mark it
			if(e.getIsMarked()!= null && !e.getIsMarked())
			 add(new MarkEdgeCommand(e));
		}
		for(Edge e:rhsNode.getOutgoing()){
			// if edge is not marked, then mark it
			if(e.getIsMarked()!= null && !e.getIsMarked())
			 add(new MarkEdgeCommand(e));
		}

		rhsNode.setMarkerType(RuleUtil.NEW);
		rhsNode.setIsMarked(true);
//		
//		Iterator<Edge> iter = lhsNode.getIncoming().iterator();
//		while (iter.hasNext()) {
//			Edge edge = iter.next();
//			
////			EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(edge);
//			edgeLayout.setNew(true);
//			edgeLayout.setLhsedge(null);
//			
//			add(new DeleteEdgeCommand(edge));
//		}
//		iter = lhsNode.getOutgoing().iterator();
//		while (iter.hasNext()) {
//			Edge edge = iter.next();
//			
//			EdgeLayout edgeLayout = EdgeUtil.getEdgeLayout(edge);
//			edgeLayout.setNew(true);
//			edgeLayout.setLhsedge(null);
//			
//			add(new DeleteEdgeCommand(edge));
//		}
		
		

		add(new SimpleDeleteEObjectCommand(lhsNode));
		add(new SimpleDeleteEObjectCommand(mapping));
		//super.execute();
	}
	
	/**
	 * marks a node as not new and changes the model accordingly
	 */
	private void demark() {
		
		// remove marker and create the corresponding node in the LHS
		lhsNode = TggFactory.eINSTANCE.createTNode();
		rule = rhsNode.getGraph().getRule();
		
		lhsGraph = rhsNode.getGraph().getRule().getLhs();
		lhsGraph.getNodes().add(lhsNode);

		lhsNode.setName(rhsNode.getName());
		lhsNode.setType(rhsNode.getType());
		
		rhsNode.setMarkerType(RuleUtil.NEW);
		rhsNode.setIsMarked(false);
		
		mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(rhsNode);
		mapping.setOrigin(lhsNode);
		rule.getMappings().add(mapping);
	}
}

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

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.interpreter.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
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
		
		// case: node is currently preserved and shall be marked as new
		// and the node marker ++ is not present
		if (!RuleUtil.NEW.equals(((TNode) rhsNode).getMarkerType())) {
			// - if the rule is consistent, then the first check for the
			// mapping is valid already, otherwise, the inconsistency will
			// be removed
			mark();

			// case: node is currently created by the rule, thus demark it and
			// create a node in the lhs of the rule
		} else {
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

		if (mapping != null) {
			// mapping is invalid
			if (mapping.getOrigin() == null)
				return true;
			else
			// case: node is used for a NAC, then marking is not possible
			if (NodeUtil.hasNodeNacMapping(mapping.getOrigin())) {
				Shell shell = new Shell();
				MessageDialog.openError(shell, "Node has NAC mapping",
						"A 'new' Marker could not be created, because the node "
								+ rhsNode.getName() + " has a NAC mapping.");
				shell.dispose();
				return false;
			}
		}
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
			if (((TAttribute) attr).getMarkerType() != null){ // attribute is already marked as created
			}
			else
			{   // mark attribute as created
				add(new MarkAttributeCommand(attr));
			}
		}
		
		
		
		for(Edge e:rhsNode.getIncoming()){
			// if edge is not marked, then mark it
			if(((TEdge) e).getMarkerType() == null)
				add(new MarkEdgeCommand(e));
		}
		for(Edge e:rhsNode.getOutgoing()){
			// if edge is not marked, then mark it
			if(((TEdge) e).getMarkerType() == null)
				add(new MarkEdgeCommand(e));
		}

		((TNode) rhsNode).setMarkerType(RuleUtil.NEW);
		
		
		if(lhsNode!=null){
			add(new SimpleDeleteEObjectCommand(lhsNode));
		}
		else
			System.out.println("!WARNING: marking rhs node -> lhs node does not exist and will not be deleted.");
		if(mapping!=null){
			add(new SimpleDeleteEObjectCommand(mapping));
		}
		else
			System.out.println("!WARNING: marking rhs node -> mapping does not exist and will not be deleted.");

		mapping=null;
		//super.execute();
	}
	
	/**
	 * marks a node as not new and changes the model accordingly
	 */
	private void demark() {


		// check and restore consistency if necessary for corrupted models
		lhsNode = RuleUtil.getLHSNode(rhsNode);
		mapping = RuleUtil.getRHSNodeMapping(rhsNode);
		if(lhsNode!=null){
			add(new SimpleDeleteEObjectCommand(lhsNode));
			System.out.println("!WARNING: demarking rhs node -> lhs node already exists and will not be replaced.");
		}
		if(mapping!=null){
			add(new SimpleDeleteEObjectCommand(mapping));
			System.out.println("!WARNING: demarking rhs node -> mapping does already exist and will be replaced.");
		}

		
		// remove marker and create the corresponding node in the LHS
		lhsNode = TggFactory.eINSTANCE.createTNode();
		rule = rhsNode.getGraph().getRule();
		
		lhsGraph = rhsNode.getGraph().getRule().getLhs();
		lhsGraph.getNodes().add(lhsNode);

		lhsNode.setName(rhsNode.getName());
		lhsNode.setType(rhsNode.getType());
		
		((TNode) rhsNode).setMarkerType(null);
		
		
		mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(rhsNode);
		mapping.setOrigin(lhsNode);
		rule.getMappings().add(mapping);
	}
}

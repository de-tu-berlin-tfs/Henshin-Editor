package tggeditor.commands.delete.rule;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.NodeLayout;
import tggeditor.commands.delete.DeleteAttributeCommand;
import tggeditor.commands.delete.DeleteEdgeCommand;
import tggeditor.commands.delete.DeleteNodeLayoutCommand;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteRuleNodeCommand deletes a node in a rule. Better: it deletes the rhs node, the
 * lhs node (if there is one), the mapping between the rhs and the lhs node and the corresponding
 * nodelayout.
 */
public class DeleteRuleNodeCommand extends CompoundCommand {
	
	/**
	 * the constructor
	 * @param node the already created, but new, node
	 */
	public DeleteRuleNodeCommand(Node node) {
		NodeLayout nodeLayout = NodeUtil.getNodeLayout(node);
		Node lhsNode = nodeLayout.getLhsnode();
		Node rhsNode = nodeLayout.getNode();
		
		handleNode(rhsNode);
		if (lhsNode != null) {
			handleNode(lhsNode);
		}
		
		/*
		 * if the node is content of a NAC, only the one mapping of the node has to be deleted
		 */
		if (node.eContainer().eContainer() instanceof NestedCondition) {
			Mapping mapping = NodeUtil.getNodeNacMapping((NestedCondition)node.eContainer().eContainer(), node);
			if(mapping!=null)
			add(new DeleteNacMappingCommand(mapping));
		} else {
			/*
			 *  if the node is not a nac node but a rule node
			 *  all mappings have to be deleted
			 */
			List<Mapping> nacMappings = NodeUtil.getNodeNacMappings(rhsNode);
			if (!nacMappings.isEmpty()) {
				for (Mapping nacMapping : nacMappings) {
					add(new DeleteNacMappingCommand(nacMapping));
				}
			}
		}
		
		add(new SimpleDeleteEObjectCommand(rhsNode));
		if (lhsNode != null) {
			add(new SimpleDeleteEObjectCommand(lhsNode));
		}
		
		// nodeLayout delete as last command, because on undo nodeLayout must be restored before
		// node, else NodeObjectEditPart will create new nodeLayout
		add(new DeleteNodeLayoutCommand(NodeUtil.getLayoutSystem(rhsNode),nodeLayout));
	}

	/**
	 * Adds the delete commands for all the edges and attributes.
	 * @param node
	 */
	private void handleNode(Node node) {
		Iterator<Edge> iter = node.getIncoming().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}
		iter = node.getOutgoing().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}
		Iterator<Attribute> iterAtt = node.getAttributes().iterator();
		while (iter.hasNext()) {
			Attribute attr = iterAtt.next();
			add(new DeleteAttributeCommand(attr));
		}
	}
	
}

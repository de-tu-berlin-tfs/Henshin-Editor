package tggeditor.commands.delete.rule;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.NodeLayout;
import tggeditor.commands.delete.DeleteAttributeCommand;
import tggeditor.commands.delete.DeleteEdgeCommand;
import tggeditor.commands.delete.DeleteNodeCommand;
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
	 * The node of the rule to be deleted
	 */
	Node node;
	Node lhsNode;
	Node rhsNode;
	
	
	
	/**
	 * the constructor
	 * @param node the already created, but new, node
	 */
	public DeleteRuleNodeCommand(Node node) {
		this.node =node;
		NodeLayout nodeLayout = NodeUtil.getNodeLayout(node);
		this.lhsNode = nodeLayout.getLhsnode();
		this.rhsNode = nodeLayout.getNode();

		// delete all rule edges, i.e. including layout
		Iterator<Edge> iter = node.getIncoming().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteRuleEdgeCommand(edge));
		}
		iter = node.getOutgoing().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteRuleEdgeCommand(edge));
		}

		
		add(new DeleteNodeCommand(rhsNode));
		
		if (lhsNode != null) {
			add(new DeleteNodeCommand(lhsNode));
		}
		add(new DeleteNodeLayoutCommand(NodeUtil.getLayoutSystem(rhsNode),nodeLayout));
		
	}

	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return (node!=null) && super.canExecute();
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return (node!=null) && super.canUndo();
	}

	@Override
	public void execute() {
		
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
			// remove the mapping from LHS to RHS
			Mapping ruleMapping = NodeUtil.getNodeMapping(rhsNode);
			if (ruleMapping != null) {
				add(new SimpleDeleteEObjectCommand(ruleMapping));
				/*
				 * notify the origin (lhs node) and the image (rhs node) of the
				 * mapping to delete the mapping in their editparts
				 */
				ruleMapping.getImage().eNotify(
						new ENotificationImpl((InternalEObject) ruleMapping
								.getImage(), Notification.REMOVE,
								HenshinPackage.MAPPING__IMAGE, ruleMapping,
								null));
				node.eNotify(new ENotificationImpl((InternalEObject) node,
						Notification.REMOVE, HenshinPackage.MAPPING__ORIGIN,
						ruleMapping, null));
			}
		}

		
		
		// TODO Auto-generated method stub
		super.execute();
	}

	@Override
	public void undo() {

		// TODO Auto-generated method stub
		super.undo();
	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub
		super.redo();
	}

	
	
	
}

/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
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
	 * @param node in the RHS or NAC of the rule
	 */
	public DeleteRuleNodeCommand(Node node) {
		this.node =node;		
		this.lhsNode = RuleUtil.getLHSNode(node);
		this.rhsNode = node;
		
		add(new DeleteNodeCommand(rhsNode));
		
		if (lhsNode != null) {
			add(new DeleteNodeCommand(lhsNode));
			Mapping mapping = RuleUtil.getRHSNodeMapping(rhsNode);
			add(new SimpleDeleteEObjectCommand(mapping));
		}
		
	}

	@Override
	public boolean canExecute() {
		return (node!=null) && super.canExecute();
	}

	@Override
	public boolean canUndo() {
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
		}
		super.execute();
	}



	
	
	
}

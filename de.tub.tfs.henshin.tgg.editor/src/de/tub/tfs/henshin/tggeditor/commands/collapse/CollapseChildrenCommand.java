/**
 * CollapseChildrenCommand.java
 * created on 16.07.2012 00:38:58
 */
package de.tub.tfs.henshin.tggeditor.commands.collapse;

import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tggeditor.util.TGGCache;

/**
 * @author huuloi
 *
 */
public class CollapseChildrenCommand extends CompoundCommand {

	private Node node;

	public CollapseChildrenCommand(Node node, Map<?, ?> editPartRegistry) {
		this.node = node;
		
			EList<Edge> outgoing = node.getOutgoing();
			for (Edge edge : outgoing) {
				if (edge.getType().isContainment() && 
					!TGGCache.getInstance().getRemovedEditParts().contains(edge)
					) {
					add(new CollapseNodeCommand(edge.getTarget(), editPartRegistry));
				}
			}
	}
	
	@Override
	public boolean canExecute() {
		return node != null;
	}
}

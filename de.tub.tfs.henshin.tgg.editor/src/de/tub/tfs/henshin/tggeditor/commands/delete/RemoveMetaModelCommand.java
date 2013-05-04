/**
 * RemoveMetaModelCommand.java
 * created on 12.02.2012 01:51:39
 */
package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * @author huuloi
 *
 */
public class RemoveMetaModelCommand extends CompoundCommand {
	
	public RemoveMetaModelCommand(Graph graph, EPackage metaModel) {
		
		for (Node node : graph.getNodes()) {
			if (node.getType().getEPackage() == metaModel) {
				add(new DeleteNodeCommand(node));
			}
		}
	}

}

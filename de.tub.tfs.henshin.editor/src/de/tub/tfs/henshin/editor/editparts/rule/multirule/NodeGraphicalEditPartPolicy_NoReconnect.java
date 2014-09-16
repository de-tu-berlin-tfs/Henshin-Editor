/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.multirule;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ReconnectRequest;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeGraphicalEditPartPolicy;

/**
 * @author Johann
 * 
 */
public class NodeGraphicalEditPartPolicy_NoReconnect extends
		NodeGraphicalEditPartPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeGraphicalEditPartPolicy#
	 * getReconnectSourceCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectSourceCommand(ReconnectRequest request) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeGraphicalEditPartPolicy#
	 * getReconnectTargetCommand(org.eclipse.gef.requests.ReconnectRequest)
	 */
	@Override
	protected Command getReconnectTargetCommand(ReconnectRequest request) {
		return null;
	}

}

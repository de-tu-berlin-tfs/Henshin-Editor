package de.tub.tfs.henshin.editor.editparts.graph;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ConnectionEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.graph.DeleteEdgeCommand;

/**
 * The Class EdgeComponentEditPolicy.
 */
public class EdgeComponentEditPolicy extends ConnectionEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ConnectionEditPolicy#getDeleteCommand(org
	 * .eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command getDeleteCommand(GroupRequest request) {
		final Edge edge = (Edge) getHost().getModel();

		return new DeleteEdgeCommand(edge);
	}

}

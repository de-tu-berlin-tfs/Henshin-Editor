package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.constraint.DeleteEdgeCommand;

public class EdgeComponentEditPolicy extends de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		final Edge edge = (Edge) getHost().getModel();

		return new DeleteEdgeCommand(edge);
	}
	
}

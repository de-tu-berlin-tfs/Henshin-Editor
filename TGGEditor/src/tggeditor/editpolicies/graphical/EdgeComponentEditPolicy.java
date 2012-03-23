package tggeditor.editpolicies.graphical;


import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.DeleteEdgeCommand;

public class EdgeComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		final Edge edge = (Edge) getHost().getModel();

		return new DeleteEdgeCommand(edge);
	}

}

package de.tub.tfs.henshin.editor.editparts.graph;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.graph.DeleteGraphCommand;

/**
 * The Class GraphComponentEditPolicy.
 */
public class GraphComponentEditPolicy extends ComponentEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		Graph model = (Graph) getHost().getModel();

		if (model.eContainer() instanceof Formula) {
			return null;
		}

		return new DeleteGraphCommand(model);
	}

}

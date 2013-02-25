package tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.CreateRequest;

import tggeditor.commands.create.rule.MarkEdgeCommand;

public class RuleEdgeXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {

	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Mapping) {
			Edge rhsEdge = (Edge) getTargetEditPart(request).getModel();
			command = new MarkEdgeCommand(rhsEdge);
		}
		return command;
	}

}

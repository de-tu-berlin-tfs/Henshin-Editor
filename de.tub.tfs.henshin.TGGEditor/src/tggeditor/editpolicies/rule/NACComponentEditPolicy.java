package tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.rule.DeleteNACCommand;
import tggeditor.editpolicies.graphical.GraphComponentEditPolicy;

public class NACComponentEditPolicy extends GraphComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNACCommand((Graph) getHost().getModel());
	}

}

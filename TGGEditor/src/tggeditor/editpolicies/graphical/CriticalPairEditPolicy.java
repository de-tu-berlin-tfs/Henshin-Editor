package tggeditor.editpolicies.graphical;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tgg.CritPair;
import tggeditor.commands.delete.DeleteCritPairCommand;

public class CriticalPairEditPolicy extends ComponentEditPolicy {
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if((CritPair) getHost().getModel() instanceof CritPair){
			return new DeleteCritPairCommand((CritPair) getHost().getModel());
		}
		
		return null;
	}
}

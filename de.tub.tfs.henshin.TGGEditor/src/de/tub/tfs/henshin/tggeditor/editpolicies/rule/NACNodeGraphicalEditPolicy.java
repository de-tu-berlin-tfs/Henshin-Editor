package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;


public class NACNodeGraphicalEditPolicy extends NodeGraphicalEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(final CreateConnectionRequest request) {
		final Object requestingObject = request.getNewObject();
		if(requestingObject instanceof Mapping){
			//??????????????????????
		//	if(getCastedModel().eContainer() instanceof NestedCondition){
				
			}
		//}
		return super.getConnectionCreateCommand(request);
	}

	
}

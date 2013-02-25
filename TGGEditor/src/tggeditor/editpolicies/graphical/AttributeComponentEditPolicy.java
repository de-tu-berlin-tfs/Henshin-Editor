package tggeditor.editpolicies.graphical;



import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tggeditor.commands.delete.DeleteAttributeCommand;

public class AttributeComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteAttributeCommand((Attribute) getHost().getModel());
	}

}

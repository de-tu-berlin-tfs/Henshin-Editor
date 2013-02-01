/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.graph.CreateAttributesCommand;

/**
 * The Class NodeXYLayoutEditPolicy.
 */
public class NodeXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {


@Override
protected Command createChangeConstraintCommand(
		ChangeBoundsRequest request, EditPart child, Object constraint) {
	// TODO Auto-generated method stub
	return super.createChangeConstraintCommand(request, child, constraint);
}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.LayoutEditPolicy#getCreateCommand(org.eclipse
	 * .gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = null;
		if (request.getNewObject() instanceof Attribute) {
			Node node = (Node) getHost().getModel();
			if (node.getType() != null)
				if ((node.getType().getEAllAttributes().size()
						- node.getAttributes().size() > 0))
					command = new CreateAttributesCommand(node);
		}
		return command;
	}

}

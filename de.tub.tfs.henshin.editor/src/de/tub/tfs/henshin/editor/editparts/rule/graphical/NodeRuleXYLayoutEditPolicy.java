/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterAndRenameNodeCommand;
import de.tub.tfs.henshin.editor.commands.transformation_unit.parameter.CreateParameterCommand;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeXYLayoutEditPolicy;
import de.tub.tfs.henshin.editor.util.ParameterUtil;

/**
 * The Class NodeRuleXYLayoutEditPolicy.
 * 
 * @author Johann
 */
public class NodeRuleXYLayoutEditPolicy extends NodeXYLayoutEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.graphs.NodeXYLayoutEditPolicy#getCreateCommand
	 * (org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Command command = super.getCreateCommand(request);
		if (request.getNewObject() instanceof Parameter) {
			Node node = (Node) getHost().getModel();
			Rule rule = (Rule) node.getGraph().eContainer();

			if (node.getName() != null && !node.getName().isEmpty()) {
				if (ParameterUtil.getParameter(node) != null) {
					return null;
				}
				command = new CreateParameterCommand(rule, node.getName());
			} else {
				command = new CreateParameterAndRenameNodeCommand(rule, node,
						"");
			}
		}

		return command;
	}

}

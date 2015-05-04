package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateConnectionRequest;

import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateNodeMappingCommand;
import de.tub.tfs.henshin.tggeditor.editparts.constraint.NodeEditPart;

public class NodeGraphicalEditPolicy extends de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy {

	@Override
	protected Command getConnectionCreateCommand(CreateConnectionRequest request) {
		command =  super.getConnectionCreateCommand(request);
		if (command instanceof CreateEdgeCommand) {
			final Object requestingObject = request.getNewObject();
			de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateEdgeCommand c = new de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateEdgeCommand((Node)getHost().getModel(),(Edge)requestingObject);
			request.setStartCommand(c);
			command = c;
			return c;
		}
		final Object requestingObject = request.getNewObject();
		if(requestingObject instanceof Mapping && getHost() instanceof NodeEditPart 
				&& ((Node)getHost().getModel()).getGraph().eContainer() instanceof NestedConstraint) { // node is node of premise
			final Mapping mapping = (Mapping)requestingObject;
			Node node = (Node) getHost().getModel();
				CreateNodeMappingCommand c = new CreateNodeMappingCommand(node, mapping);
				request.setStartCommand(c);
				command = c;
		}
		return command;
	}
	
	@Override
	protected Command getConnectionCompleteCommand(
			CreateConnectionRequest request) {
		if (command instanceof CreateNodeMappingCommand){
			final CreateNodeMappingCommand c = (CreateNodeMappingCommand ) command;
			if(c.getOrigin() != null){
				Node original = c.getOrigin();
				final Node image = (Node) getHost().getModel();
				if (image.getGraph().eContainer() instanceof NestedCondition) { // image is node of conclusion 
					NestedCondition nc = (NestedCondition)image.getGraph().eContainer();
					if((original.getType() == image.getType() // node types of origin & image are the same
							|| NodeTypes.isExtended(image.getType(), original.getType())) // node types of origin & image are in an inheritance relation
							&& (nc.getMappings().getImage(c.getOrigin(), image.getGraph()) == null) // origin node is not contained in mappings
							|| nc.getMappings().getImage(c.getOrigin(), image.getGraph()) == image) { // there is existing mapping between origin & image
						c.setImage(image);
						return c;
					}					
				}
			}
		}
		return super.getConnectionCompleteCommand(request);
	}
	
}

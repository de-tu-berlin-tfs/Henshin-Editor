package de.tub.tfs.henshin.tggeditor.editparts.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.EdgeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.EdgeEndpointEditPartPolicy;

public class EdgeEditPart extends de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart {

	public EdgeEditPart(Edge model) {
		super(model);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.CONNECTION_ROLE, new EdgeComponentEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_ENDPOINTS_ROLE, new EdgeEndpointEditPartPolicy());
	}

}

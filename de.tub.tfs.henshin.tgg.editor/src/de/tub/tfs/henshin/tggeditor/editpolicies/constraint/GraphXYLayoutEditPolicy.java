package de.tub.tfs.henshin.tggeditor.editpolicies.constraint;

import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.constraint.MoveNodeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.constraint.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.constraint.NodeEditPart;

public class GraphXYLayoutEditPolicy extends XYLayoutEditPolicy implements
		EditPolicy {

	int dview;
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof TNode){
			Graph graph = (Graph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			
			EObject formula = (Formula)graph.eContainer();
			while(!(formula instanceof Constraint)) {
				formula = formula.eContainer();
			}
			
			CreateNodeCommand c = new CreateNodeCommand((TNode)newObject, graph, location, ((Constraint)formula).getComponent());
			return c;
		}
		return null;
	}

	@Override
	protected Command getMoveChildrenCommand(Request request) {
		GraphEditPart gep = (GraphEditPart)this.getHost();
		
		dview = ((FigureCanvas)gep.getViewer().getControl()).getViewport().getClientArea().getLocation().x;
		ChangeBoundsRequest req = (ChangeBoundsRequest) request;
		List<?> editparts = req.getEditParts();
		if (!editparts.isEmpty()) {
			// move nodes
			if (editparts.get(0) instanceof NodeEditPart) {
				// target component: add divider offset
				NodeEditPart nep = (NodeEditPart) req.getEditParts().get(0);
				return new MoveNodeCommand(nep, req);	
			}
		}
		return null;
	}
	
}

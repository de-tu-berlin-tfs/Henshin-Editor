package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleNodeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;


public class RuleXYLayoutEditPolicy extends GraphXYLayoutEditPolicy {
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof TNode){
			Graph graph = (Graph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			TripleComponent nodeTripleComponent = GraphUtil.getTripleComponentForXCoordinate(((GraphEditPart)this.getHost()),location.x);
			
			CreateRuleNodeCommand c = new CreateRuleNodeCommand((TNode)newObject,graph,
					location, nodeTripleComponent);
			return c;
		}
		return null;
	}
	
	@Override
	protected Command getAlignChildrenCommand(AlignmentRequest request) {
		// TODO Auto-generated method stub
		
		
		return super.getAlignChildrenCommand(request);
	}
}

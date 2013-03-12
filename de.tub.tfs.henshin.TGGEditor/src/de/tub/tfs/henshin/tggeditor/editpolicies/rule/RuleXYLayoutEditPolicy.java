package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleNodeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;


public class RuleXYLayoutEditPolicy extends GraphXYLayoutEditPolicy {
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
//		Object newObject = request.getNewObject();
//		if (newObject instanceof Node){
//			CreateRuleNodeCommand c = new CreateRuleNodeCommand((Node)newObject,
//					(Graph)this.getHost().getModel(),
//					request.getLocation());
//			return c;
//		}
//		return null;
		
		Object newObject = request.getNewObject();
		if (newObject instanceof Node){
			Graph graph = (Graph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			NodeGraphType  nodeGraphType = GraphUtil.getNodeGraphTypeForXCoordinate(((GraphEditPart)this.getHost()),location.x);
			
			CreateRuleNodeCommand c = new CreateRuleNodeCommand((Node)newObject,graph,
					location, nodeGraphType);
			return c;
		}
		return null;
	}
}

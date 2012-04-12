package tggeditor.editpolicies.graphical;



import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.commands.create.CreateNodeCommand;
import tggeditor.commands.move.MoveDividerCommand;
import tggeditor.commands.move.MoveManyNodeObjectsCommand;
import tggeditor.commands.move.MoveNodeObjectCommand;
import tggeditor.editparts.graphical.DividerEditPart;
import tggeditor.editparts.graphical.GraphEditPart;
import tggeditor.editparts.graphical.NodeObjectEditPart;
import tggeditor.editpolicies.TggNonResizableEditPolicy;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
import tggeditor.util.NodeTypes.NodeGraphType;



public class GraphXYLayoutEditPolicy extends XYLayoutEditPolicy implements EditPolicy {
	
	DividerEditPart div;
	int dview;
	int distance = 150;
	boolean failed;

	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new TggNonResizableEditPolicy();
	}
	
	protected Command createChangeConstraintCommand(EditPart child,	Object constraint) {
		Rectangle bounds = ((FigureCanvas)child.getViewer().getControl()).getViewport().getBounds();
		Rectangle newBounds = (Rectangle) constraint;
		
		if ((newBounds.x < bounds.x || newBounds.y < bounds.y) || (newBounds.x  + newBounds.width > bounds.width || newBounds.y + newBounds.width> bounds.height)){
			((FigureCanvas)child.getViewer().getControl()).scrollSmoothTo(((Rectangle)constraint).x + ((Rectangle)constraint).width + 20, ((Rectangle)constraint).y + ((Rectangle)constraint).height + 20);
			((FigureCanvas)child.getViewer().getControl()).getViewport().repaint();
		}
		
		MoveNodeObjectCommand c = null;
		
		if(child instanceof NodeObjectEditPart) {
			NodeLayout nodeLayout = ((NodeObjectEditPart) child).getLayoutModel();
			if(newBounds.x != nodeLayout.getX() || newBounds.y != nodeLayout.getY()) {
				c = new MoveNodeObjectCommand(nodeLayout,newBounds.x,newBounds.y);
			}
		}
		return c;
	}
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof Node){
			Graph graph = (Graph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			NodeGraphType  nodeGraphType = GraphUtil.getNodeGraphTypeForXCoordinate(((GraphEditPart)this.getHost()),location.x);
			
			CreateNodeCommand c = new CreateNodeCommand((Node)newObject,graph,
					location, nodeGraphType);
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
			if (editparts.get(0) instanceof NodeObjectEditPart) {
				if (canMoveNode(req)) {
					CompoundCommand cc = new MoveManyNodeObjectsCommand(editparts, req);
					// check divider location
					div = null;
					MoveDividerCommand c = makeMoveDividerCommand(cc.getCommands(), req);
					if (c != null) {
						cc.add(c);
						if (div != null) {
							makeMoveNodeCommand(div, c.getX(), cc);
						}
					}
					else if (failed) {
						cc.dispose();
						cc = null;
					}
					return cc;
				}
			}
			// move divider
			else if (editparts.get(0) instanceof DividerEditPart) {
				CompoundCommand cc = new CompoundCommand();
				DividerEditPart divEdPart = (DividerEditPart)editparts.get(0);				
				if (divEdPart.getCastedModel().isIsSC()) {
					int reqX = req.getLocation().x + dview;
					DividerEditPart divCT = ((GraphEditPart)this.getHost()).getDividerCTpart();
					if ((divCT.getCastedModel().getDividerX() - reqX) >= distance) {
//							&& (reqX < divCT.getCastedModel().getDividerX())) {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								reqX, 
								divEdPart.getCastedModel().getMaxY());				
						cc.add(c);						
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
					else {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								divCT.getCastedModel().getDividerX() - distance, 
								divEdPart.getCastedModel().getMaxY());
						cc.add(c);
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
				}
				else { // divEdPart.getCastedModel().isIsCT()
					int reqX = req.getLocation().x + dview;
					DividerEditPart divSC = ((GraphEditPart)this.getHost()).getDividerSCpart();	
					if ((reqX - divSC.getCastedModel().getDividerX()) >= distance) {
//							&& (reqX > divSC.getCastedModel().getDividerX())) {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								reqX, 
								divEdPart.getCastedModel().getMaxY());			
						cc.add(c);
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
					else {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								divSC.getCastedModel().getDividerX() + distance, 
								divEdPart.getCastedModel().getMaxY());
						cc.add(c);
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
				}
				return cc;
			}
		}
		return null;		
	}

	private MoveDividerCommand makeMoveDividerCommand(
			List<?> commands, ChangeBoundsRequest req) {
		
		NodeObjectEditPart nodeEdPart = null;
		int maxX = 0;
		int maxW = 0;
		for (Object co : commands) {
			MoveNodeObjectCommand mnc = (MoveNodeObjectCommand) co;
			NodeObjectEditPart nep = this.getNodeEditPart(mnc.getLayoutModel());
			if (nodeEdPart == null) {
				nodeEdPart = nep;
				maxX = mnc.getX();
				maxW = nep.getFigure().getSize().width;
			}
			else if ((mnc.getX() + nep.getFigure().getSize().width) 
					> (maxX + maxW)) {
				nodeEdPart = nep;
				maxX = mnc.getX();
				maxW = nep.getFigure().getSize().width;
			}
		}
		// check dividerSC
		MoveDividerCommand c = makeMoveDividerSCCommand(
						nodeEdPart, maxX, maxW, 
						((GraphEditPart)this.getHost()).getDividerSCpart());
		div = (c != null)? ((GraphEditPart)this.getHost()).getDividerSCpart(): null;
		if (c == null) {
			// check dividerCT
			c = makeMoveDividerCTCommand(
						nodeEdPart, maxX, maxW, 
						((GraphEditPart)this.getHost()).getDividerCTpart());
			div = (c != null)? ((GraphEditPart)this.getHost()).getDividerCTpart(): null;
		}
		return c;
	}
	
	private MoveDividerCommand makeMoveDividerSCCommand(
			NodeObjectEditPart nodeEdPart, 
			int maxX, int maxW, 
			DividerEditPart divSCEdPart) {
			
		MoveDividerCommand c = null;
		TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		GraphLayout divSC = divSCEdPart.getCastedModel();
		GraphLayout divCT = ((GraphEditPart)this.getHost()).getDividerCTpart().getCastedModel();
		NodeLayout nl = nodeEdPart.getLayoutModel();
		int divSC_X = divSC.getDividerX();
		if (NodeUtil.isSourceNode(tgg, nl.getNode().getType())) {
			int x = maxX + maxW;// + reqX;
			failed = !(x < (divCT.getDividerX()-distance));
			if ((x > divSC_X) && !failed) {
				c = new MoveDividerCommand(
							divSCEdPart.getCastedModel(), 
							(x+5), divSC.getMaxY());
			}
		}
		else if (NodeUtil.isCorrespNode(tgg, nl.getNode().getType())) {
			int x = maxX;
			failed = !(x > distance);
			if ((x < divSC_X)  && !failed) {
				c = new MoveDividerCommand(
							divSCEdPart.getCastedModel(), 
							(x-5) , divSC.getMaxY());
			}
		}
		return c;
	}
	
	private MoveDividerCommand makeMoveDividerCTCommand(
			NodeObjectEditPart nodeEdPart, 
			int maxX, int maxW, 
			DividerEditPart divCTEdPart) {
				
		MoveDividerCommand c = null;
		TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		GraphLayout divCT = divCTEdPart.getCastedModel();
		GraphLayout divSC = ((GraphEditPart)this.getHost()).getDividerSCpart().getCastedModel();
		NodeLayout nl = nodeEdPart.getLayoutModel();
		int divCT_X = divCT.getDividerX();
		if (NodeUtil.isCorrespNode(tgg, nl.getNode().getType())) {
			int x = maxX + maxW;
			if (x > divCT_X) {
				c = new MoveDividerCommand(
							divCTEdPart.getCastedModel(), 
							(x+5), divCTEdPart.getCastedModel().getMaxY());
			}
		}
		else if (NodeUtil.isTargetNode(tgg, nl.getNode().getType())) {
			int x = maxX;
			failed = !(x > (divSC.getDividerX()+distance));
			if ((x < divCT_X)
					&& !failed) {
				c = new MoveDividerCommand(
							divCTEdPart.getCastedModel(), 
							(x-5), divCTEdPart.getCastedModel().getMaxY());
			}
		}
		return c;
	}
	
	private CompoundCommand makeMoveNodeCommand(DividerEditPart dep, int x, CompoundCommand cc) {
		
		TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		if (dep.getCastedModel().isIsSC()) {			
			Set<NodeLayout> set = NodeUtil.getNodeLayouts(tgg, tgg.getCorresp());
			if (set.size() > 0) {
				for (NodeLayout nl: set) {
					if (nl.getX() < x) {
						cc.add(new MoveNodeObjectCommand(nl, x+5, nl.getY()));							
					}
				}
			}
			set = NodeUtil.getNodeLayouts(tgg, tgg.getSource());
			for (NodeLayout nl: set) {
				NodeObjectEditPart nodeEdPart = getNodeEditPart(nl.getNode());
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (nl.getX()+w > x) {					
						cc.add(new MoveNodeObjectCommand(nl, (x-5 -w), nl.getY()));							
					}
				}
			}
		}
		else {
			Set<NodeLayout> set = NodeUtil.getNodeLayouts(tgg, tgg.getTarget());
			if (set.size() > 0) {
				for (NodeLayout nl: set) {
					if (nl.getX() < x) {
						cc.add(new MoveNodeObjectCommand(nl, (x+5), nl.getY()));							
					}
				}
			}
			set = NodeUtil.getNodeLayouts(tgg, tgg.getCorresp());	
			for (NodeLayout nl: set) {
				NodeObjectEditPart nodeEdPart = getNodeEditPart(nl.getNode());
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (nl.getX()+w > x) {
						cc.add(new MoveNodeObjectCommand(nl, (x-5 -w), nl.getY()));							
					}
				}
			}
		}
		return cc;
	}
	
	private boolean canMoveNode(ChangeBoundsRequest req) {
		TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		int reqX = req.getLocation().x + dview;
		NodeObjectEditPart nep = null;
		int maxX = 0;
		int maxW = 0;
		for (Object obj : req.getEditParts()) {
			if (nep == null) {
				nep = (NodeObjectEditPart) obj;
				maxX = nep.getLayoutModel().getX();
				maxW = nep.getFigure().getSize().width;
			}
			else if ((nep.getLayoutModel().getX() + nep.getFigure().getSize().width)  > (maxX + maxW)){
				nep = (NodeObjectEditPart) obj;
				maxX = nep.getLayoutModel().getX();
				maxW = nep.getFigure().getSize().width;
			}
		}
		NodeLayout nl = nep.getLayoutModel();
		int divSCx = ((GraphEditPart)this.getHost()).getDividerSCpart().getCastedModel().getDividerX();
		int divCTx = ((GraphEditPart)this.getHost()).getDividerCTpart().getCastedModel().getDividerX();
		if (NodeUtil.isSourceNode(tgg, nl.getNode().getType())) {
			int divDistns = divCTx - divSCx;
			if ((divDistns > distance) || (reqX + maxW*3/4) <= divSCx) return true;
		}
		else if (NodeUtil.isCorrespNode(tgg, nl.getNode().getType())) {
			int divDistns = divSCx;
			if (nl.getX() > divDistns) {
				if ((reqX > divDistns) && (reqX - maxW/2) > divSCx) return true;
			}
		}
		else if (NodeUtil.isTargetNode(tgg, nl.getNode().getType())) {
			int divDistns = divCTx - divSCx;
			if ((divDistns > distance) || (reqX - maxW*3/4) >= divCTx) return true;
		}
		return false;
	}
	
	private NodeObjectEditPart getNodeEditPart(Node n) {
		GraphEditPart gep = (GraphEditPart) this.getHost();
		List<?> list = gep.getChildren();
		for (Object child : list) {
			if (child instanceof NodeObjectEditPart
					&& ((NodeObjectEditPart) child).getCastedModel() == n) {
				return (NodeObjectEditPart) child;
			}
		}
		return null;
	}
	
	private NodeObjectEditPart getNodeEditPart(NodeLayout nl) {
		GraphEditPart gep = (GraphEditPart) this.getHost();
		List<?> list = gep.getChildren();
		for (Object child : list) {
			if (child instanceof NodeObjectEditPart
					&& ((NodeObjectEditPart) child).getCastedModel() == nl.getNode()) {
				return (NodeObjectEditPart) child;
			}
		}
		return null;
	}

}

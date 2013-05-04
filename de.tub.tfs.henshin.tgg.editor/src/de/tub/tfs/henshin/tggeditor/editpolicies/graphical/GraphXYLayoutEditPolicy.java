package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;

import java.util.HashMap;
import java.util.List;

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

import de.tub.tfs.henshin.model.subtree.Subtree;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.collapse.MoveSubtreeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveDividerCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveManyNodeObjectsCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveNodeObjectCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.SubtreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.TggNonResizableEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.ExceptionUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


public class GraphXYLayoutEditPolicy extends XYLayoutEditPolicy implements EditPolicy {
	
	DividerEditPart div;
	int dview;
	private final static int MINIMAL_DIV_DISTANCE = 150;
	boolean failed;

	protected EditPolicy createChildEditPolicy(EditPart child) {
		return new TggNonResizableEditPolicy();
	}
	
	protected Command createChangeConstraintCommand(EditPart child,	Object constraint) {
		Rectangle bounds = ((FigureCanvas)child.getViewer().getControl()).getViewport().getBounds();
		Rectangle newBounds = (Rectangle) constraint;
		
		// this causes a lot of shaking scroll bars, thus, deactivated
/*		if ((newBounds.x < bounds.x || newBounds.y < bounds.y) || (newBounds.x  + newBounds.width > bounds.width || newBounds.y + newBounds.width> bounds.height)){
			((FigureCanvas)child.getViewer().getControl()).scrollSmoothTo(((Rectangle)constraint).x + ((Rectangle)constraint).width + 20, ((Rectangle)constraint).y + ((Rectangle)constraint).height + 20);
			((FigureCanvas)child.getViewer().getControl()).getViewport().repaint();
		}*/
		
		
		MoveNodeObjectCommand c = null;
		
		if(child instanceof NodeObjectEditPart) {
			Node node = ((NodeObjectEditPart) child).getCastedModel();
			if(newBounds.x != node.getX() || newBounds.y != node.getY()) {
				c = new MoveNodeObjectCommand(node,newBounds.x,newBounds.y);
			}
		}
		return c;
	}
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof TNode){
			Graph graph = (Graph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			TripleComponent  nodeTripleComponent = GraphUtil.getTripleComponentForXCoordinate(((GraphEditPart)this.getHost()),location.x);
			
			CreateNodeCommand c = new CreateNodeCommand((TNode)newObject,graph,
					location, nodeTripleComponent);
			return c;
		}
		return null;
	}
	
	@Override
	protected Command getMoveChildrenCommand(Request request) {
		GraphEditPart gep = (GraphEditPart)this.getHost();
		TripleGraph tripleGraph = ((GraphEditPart)this.getHost()).getTripleGraph();
		
		dview = ((FigureCanvas)gep.getViewer().getControl()).getViewport().getClientArea().getLocation().x;
		ChangeBoundsRequest req = (ChangeBoundsRequest) request;
		List<?> editparts = req.getEditParts();
		if (!editparts.isEmpty()) {
			// move nodes
			if (editparts.get(0) instanceof NodeObjectEditPart) {
				// target component: add divider offset
				NodeObjectEditPart nep = (NodeObjectEditPart) req.getEditParts().get(0);
				Node node = nep.getCastedModel();
				TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());			
				if (NodeUtil.isTargetNode(node)){
					int posX = req.getMoveDelta().x;
					int offset = tripleGraph.getDividerCT_X();
					if (node.getX()+posX < offset)
					req.getMoveDelta().setX(posX+offset);
				}

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
			else if (editparts.get(0) instanceof SubtreeEditPart) {
				SubtreeEditPart subtreeEditPart = (SubtreeEditPart) editparts.get(0);
				Subtree subtree = subtreeEditPart.getCastedModel();
				Node root = subtree.getRoot();
				if (NodeUtil.isTargetNode(root)) {
					int posX = req.getMoveDelta().x;
					int offset = tripleGraph.getDividerCT_X();
					if (root.getX()+posX < offset)
					req.getMoveDelta().setX(posX+offset);
				}
				
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
				if (divEdPart.isSC()) {
					int reqX = req.getLocation().x + dview;
					//DividerEditPart divCT = ((GraphEditPart)this.getHost()).getDividerCTpart();
					if ((tripleGraph.getDividerCT_X() - reqX) >= MINIMAL_DIV_DISTANCE) {
//							&& (reqX < divCT.getCastedModel().getDividerX())) {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								reqX, 
								divEdPart.getCastedModel().getTripleGraph().getDividerMaxY());				
						cc.add(c);						
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
					else {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								tripleGraph.getDividerCT_X() - MINIMAL_DIV_DISTANCE, 
								tripleGraph.getDividerMaxY());
						cc.add(c);
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
				}
				else { // divEdPart.getCastedModel().isIsCT()
					int reqX = req.getLocation().x + dview;
					DividerEditPart divSC = ((GraphEditPart)this.getHost()).getDividerSCpart();	
					if ((reqX - tripleGraph.getDividerSC_X()) >= MINIMAL_DIV_DISTANCE) {
//							&& (reqX > divSC.getCastedModel().getDividerX())) {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								reqX, 
								tripleGraph.getDividerMaxY());			
						cc.add(c);
						// check nodes to move and add to cc
						makeMoveNodeCommand(divEdPart, c.getX(), cc);
					}
					else {
						MoveDividerCommand c = new MoveDividerCommand(
								divEdPart.getCastedModel(), 
								tripleGraph.getDividerSC_X() + MINIMAL_DIV_DISTANCE, 
								tripleGraph.getDividerMaxY());
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
		NodeLayout subtreeEditPart = null;
		int maxX = 0;
		int maxW = 0;
		for (Object co : commands) {
			if (co instanceof MoveNodeObjectCommand) {
				MoveNodeObjectCommand mnc = (MoveNodeObjectCommand) co;
				NodeObjectEditPart nep = this.getNodeEditPart(mnc.getNode());
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
			else if (co instanceof MoveSubtreeCommand) {
				MoveSubtreeCommand msc = (MoveSubtreeCommand) co;
				if (subtreeEditPart == null) {
					subtreeEditPart = msc.getNodeLayout();
					maxX = msc.getX();
					maxW = 25;
				}
				else if ((msc.getX() + 25) > (maxX + maxW) ) {
					subtreeEditPart = msc.getNodeLayout();
					maxX = msc.getX();
					maxW = 25;
				}
			}
		}
		
		Node node = nodeEdPart != null ? nodeEdPart.getCastedModel() : null;
		if (node == null) {
			node = subtreeEditPart != null ? subtreeEditPart.getNode() : null;
		}
		// check dividerSC
		MoveDividerCommand c = makeMoveDividerSCCommand(
						node, maxX, maxW, 
						((GraphEditPart)this.getHost()).getDividerSCpart());
		div = (c != null)? ((GraphEditPart)this.getHost()).getDividerSCpart(): null;
		if (c == null) {
			// check dividerCT
			c = makeMoveDividerCTCommand(
						node, maxX, maxW, 
						((GraphEditPart)this.getHost()).getDividerCTpart());
			div = (c != null)? ((GraphEditPart)this.getHost()).getDividerCTpart(): null;
		}
		return c;
	}
	
	private MoveDividerCommand makeMoveDividerSCCommand(
			Node node, 
			int maxX, int maxW, 
			DividerEditPart divSCEdPart) {
			
		// TODO: check when this divSCEdPart can be null
		if (divSCEdPart==null) {ExceptionUtil.error("Divider SC edit part is missing for move"); return null;}
		MoveDividerCommand c = null;
		TripleGraph tripleGraph = divSCEdPart.getCastedModel().getTripleGraph();
		int divSC_X = tripleGraph.getDividerSC_X();
		if (NodeUtil.isSourceNode(node)) {
			int x = maxX + maxW;// + reqX;
			failed = !(x < (tripleGraph.getDividerCT_X()-MINIMAL_DIV_DISTANCE));
			if ((x > divSC_X) && !failed) {
				c = new MoveDividerCommand(
							divSCEdPart.getCastedModel(), 
							(x+5), tripleGraph.getDividerMaxY());
			}
		}
		else if (NodeUtil.isCorrespondenceNode(node)) {
			int x = maxX;
			failed = !(x > MINIMAL_DIV_DISTANCE);
			if ((x < divSC_X)  && !failed) {
				c = new MoveDividerCommand(
							divSCEdPart.getCastedModel(), 
							(x-5) , tripleGraph.getDividerMaxY());
			}
		}
		return c;
	}
	
	private MoveDividerCommand makeMoveDividerCTCommand(
			Node node, 
			int maxX, int maxW, 
			DividerEditPart divCTEdPart) {
				
		// TODO: check when this divCTEdPart can be null
		if (divCTEdPart==null) {ExceptionUtil.error("Divider CT edit part is missing for move"); return null;}
		MoveDividerCommand c = null;
		TripleGraph tripleGraph = divCTEdPart.getCastedModel().getTripleGraph();
		int divCT_X = tripleGraph.getDividerCT_X();
		if (NodeUtil.isCorrespondenceNode(node)) {
			int x = maxX + maxW;
			if (x > divCT_X) {
				c = new MoveDividerCommand(
							divCTEdPart.getCastedModel(), 
							(x+5), tripleGraph.getDividerMaxY());
			}
		}
		else if (NodeUtil.isTargetNode(node)) {
			int x = maxX;
			failed = !(x > (tripleGraph.getDividerSC_X()+MINIMAL_DIV_DISTANCE));
			if ((x < divCT_X)
					&& !failed) {
				c = new MoveDividerCommand(
							divCTEdPart.getCastedModel(), 
							(x-5), tripleGraph.getDividerMaxY());
			}
		}
		return c;
	}
	
	private CompoundCommand makeMoveNodeCommand(DividerEditPart dep, int x, CompoundCommand cc) {
		
		TripleGraph tripleGraph = dep.getCastedModel().getTripleGraph();
		HashMap<TripleComponent,List<Node>> nodeSets = GraphUtil.getDistinguishedNodeSets(tripleGraph);
		if (dep.isSC()) {			
			List<Node> sourceNodes = nodeSets.get(TripleComponent.SOURCE);
			List<Node> correspondenceNodes = nodeSets.get(TripleComponent.CORRESPONDENCE);
			if (correspondenceNodes.size() > 0) {
				for (Node n: correspondenceNodes) {
					if (n.getX() < x) {
						cc.add(new MoveNodeObjectCommand(n, x+5, n.getY()));							
					}
				}
			}
			// handle source nodes
			for (Node n: sourceNodes) {
				NodeObjectEditPart nodeEdPart = getNodeEditPart(n);
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (n.getX()+w > x) {					
						cc.add(new MoveNodeObjectCommand(n, (x-5 -w), n.getY()));							
					}
				}
			}
		}
		else {
			List<Node> correspondenceNodes = nodeSets.get(TripleComponent.CORRESPONDENCE);
			List<Node> targetNodes = nodeSets.get(TripleComponent.TARGET);
			if (targetNodes.size() > 0) {
				for (Node n: targetNodes) {
					if (n.getX() < x) {
						cc.add(new MoveNodeObjectCommand(n, (x+5), n.getY()));							
					}
				}
			}
			// handle correspondence nodes
			for (Node n: correspondenceNodes) {
				NodeObjectEditPart nodeEdPart = getNodeEditPart(n);
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (n.getX()+w > x) {
						cc.add(new MoveNodeObjectCommand(n, (x-5 -w), n.getY()));							
					}
				}
			}
		}
		return cc;
	}
	
	private boolean canMoveNode(ChangeBoundsRequest req) {
		TGG tgg = NodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		int reqX;
		int divSCx = ((GraphEditPart)this.getHost()).getCastedModel().getDividerSC_X();
		int divCTx = ((GraphEditPart)this.getHost()).getCastedModel().getDividerCT_X();
		int divDistance = divCTx - divSCx;
		if (req.getEditParts().get(0) instanceof NodeObjectEditPart) {
			NodeObjectEditPart nep = (NodeObjectEditPart) req.getEditParts().get(0);
			Node n = nep.getCastedModel();
			if (req.getMoveDelta()!=null) {
				// automatic layouter: getMoveDelta
				reqX = n.getX() + req.getMoveDelta().x;// + dview;
				if (NodeUtil.isTargetNode(n)){
					return true;
				}
			}
			else 
				// request is not caused by automatic layouter, but manually
				reqX = req.getLocation().x;// + dview;
			
			// maxX is the maximal requested new X position
			int maxX = 0;
			// maxW is the maximal with under all nodes
			int maxW = 0;
			for (Object obj : req.getEditParts()) {
				if ((nep.getCastedModel().getX() + nep.getFigure().getSize().width)  > (maxX + maxW)){
					nep = (NodeObjectEditPart) obj;
					maxX = nep.getCastedModel().getX();
					maxW = nep.getFigure().getSize().width;
				}
			}
			if (NodeUtil.isSourceNode(n)) {
				if ((divDistance > MINIMAL_DIV_DISTANCE) || (reqX + maxW*3/4) <= divSCx) return true;
			}
			else if (NodeUtil.isCorrespondenceNode(n)) {
				// node is right of source divider
				if (n.getX() > divSCx) {
					// new position is between source and target dividers
					if ((divSCx < reqX) && (reqX < divCTx)) 
						return true;
				}
			}
			else if (NodeUtil.isTargetNode(n)) {
				if ((divDistance > MINIMAL_DIV_DISTANCE) || (reqX - maxW*3/4) >= divCTx) return true;
			}
		}
		else if (req.getEditParts().get(0) instanceof SubtreeEditPart) {
			SubtreeEditPart subtreeEditPart = (SubtreeEditPart) req.getEditParts().get(0);
			Node root = subtreeEditPart.getCastedModel().getRoot();
			if (req.getMoveDelta() != null) {
				reqX = root.getX() + req.getMoveDelta().x;
				if (NodeUtil.isTargetNode(root)) {
					return true;
				}
			}
			else {
				reqX = req.getLocation().x;
			}
			// maxX is the maximal requested new X position
			int maxX = 0;
			// maxW is the maximal with under all nodes
			int maxW = 0;
			for (Object obj : req.getEditParts()) {
				if ((subtreeEditPart.getLayoutModel().getX() + subtreeEditPart.getFigure().getSize().width) > (maxX + maxW)) {
					subtreeEditPart = (SubtreeEditPart) obj;
					maxX = subtreeEditPart.getLayoutModel().getX();
					maxW = subtreeEditPart.getFigure().getSize().width;
				}
			}
			if (NodeUtil.isSourceNode(root)) {
				if ((divDistance > MINIMAL_DIV_DISTANCE) || (reqX + maxW*3/4) <= divSCx) {
					return true;
				}
			}
			else if (NodeUtil.isCorrespondenceNode(root)) {
				// node is right of source divider
				if (root.getX() > divSCx) {
					// new position is between source and target dividers
					if ((divSCx < reqX) && (reqX < divCTx)) 
						return true;
				}
			}
			else if (NodeUtil.isTargetNode(root)) {
				if ((divDistance > MINIMAL_DIV_DISTANCE) || (reqX - maxW*3/4) >= divCTx) return true;
			}
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
	
	
	private SubtreeEditPart getSubtreeEditPart(Node n) {
		GraphEditPart gep = (GraphEditPart) this.getHost();
		List<?> list = gep.getChildren();
		for (Object child : list) {
			if (child instanceof SubtreeEditPart
					&& ((SubtreeEditPart) child).getCastedModel() == n) {
				return (SubtreeEditPart) child;
			}
		}
		return null;
	}
}

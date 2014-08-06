package de.tub.tfs.henshin.tggeditor.editpolicies.graphical;



import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.editpolicies.XYLayoutEditPolicy;
import org.eclipse.gef.requests.AlignmentRequest;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.NodeUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveDividerCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveManyNodeObjectsCommand;
import de.tub.tfs.henshin.tggeditor.commands.move.MoveNodeObjectCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.TggNonResizableEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;




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
		
		if(child instanceof TNodeObjectEditPart) {
			TNode node = ((TNodeObjectEditPart) child).getCastedModel();
			c = new MoveNodeObjectCommand(node,(TNodeObjectEditPart) child,newBounds.x,newBounds.y);
			
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
			if (editparts.get(0) instanceof TNodeObjectEditPart) {
							
				// target component: add divider offset
				TNodeObjectEditPart nep = (TNodeObjectEditPart) req.getEditParts().get(0);
				TNode node = nep.getCastedModel();
				TGG tgg = GraphicalNodeUtil.getLayoutSystem((Graph)this.getHost().getModel());	
				return new MoveNodeObjectCommand(nep, req);	
				
				/*if (NodeUtil.isTargetNode(node)){
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
				}*/
			}
			// move divider
			else if (editparts.get(0) instanceof DividerEditPart) {
				DividerEditPart divEdPart = (DividerEditPart)editparts.get(0);				
				this.translateFromAbsoluteToLayoutRelative(req.getLocation());
				int reqX =  req.getLocation().x;
				MoveDividerCommand c = new MoveDividerCommand(
						divEdPart.getCastedModel(), 
						reqX, 
						divEdPart.getCastedModel().getTripleGraph().getDividerMaxY());	
				return c;
				/*
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
				
				*/
			}
		}
		return null;		
	}

	private MoveDividerCommand makeMoveDividerCommand(
			List<?> commands, ChangeBoundsRequest req) {
		
		TNodeObjectEditPart nodeEdPart = null;
		int maxX = 0;
		int maxW = 0;
		for (Object co : commands) {
			MoveNodeObjectCommand mnc = (MoveNodeObjectCommand) co;
			TNodeObjectEditPart nep = this.getNodeEditPart(mnc.getNode());
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
			TNodeObjectEditPart nodeEdPart, 
			int maxX, int maxW, 
			DividerEditPart divSCEdPart) {
			
		// TODO: check when this divSCEdPart can be null
		if (divSCEdPart==null) {ExceptionUtil.error("Divider SC edit part is missing for move"); return null;}
		MoveDividerCommand c = null;
		TripleGraph tripleGraph = divSCEdPart.getCastedModel().getTripleGraph();
		TNode node = nodeEdPart.getCastedModel();
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
			TNodeObjectEditPart nodeEdPart, 
			int maxX, int maxW, 
			DividerEditPart divCTEdPart) {
				
		// TODO: check when this divCTEdPart can be null
		if (divCTEdPart==null) {ExceptionUtil.error("Divider CT edit part is missing for move"); return null;}
		MoveDividerCommand c = null;
		TripleGraph tripleGraph = divCTEdPart.getCastedModel().getTripleGraph();
		TNode node = nodeEdPart.getCastedModel();
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
		HashMap<TripleComponent,List<TNode>> nodeSets = GraphUtil.getDistinguishedNodeSets(tripleGraph);
		if (dep.isSC()) {			
			List<TNode> sourceNodes = nodeSets.get(TripleComponent.SOURCE);
			List<TNode> correspondenceNodes = nodeSets.get(TripleComponent.CORRESPONDENCE);
			if (correspondenceNodes.size() > 0) {
				for (TNode n: correspondenceNodes) {
					if (n.getX() < x) {
						cc.add(new MoveNodeObjectCommand(n,getNodeEditPart(n), x+5, n.getY()));							
					}
				}
			}
			// handle source nodes
			for (TNode n: sourceNodes) {
				TNodeObjectEditPart nodeEdPart = getNodeEditPart(n);
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (n.getX()+w > x) {					
						cc.add(new MoveNodeObjectCommand(n,getNodeEditPart(n), (x-5 -w), n.getY()));							
					}
				}
			}
		}
		else {
			List<TNode> correspondenceNodes = nodeSets.get(TripleComponent.CORRESPONDENCE);
			List<TNode> targetNodes = nodeSets.get(TripleComponent.TARGET);
			if (targetNodes.size() > 0) {
				for (TNode n: targetNodes) {
					if (n.getX() < x) {
						cc.add(new MoveNodeObjectCommand(n,getNodeEditPart(n), (x+5), n.getY()));							
					}
				}
			}
			// handle correspondence nodes
			for (TNode n: correspondenceNodes) {
				TNodeObjectEditPart nodeEdPart = getNodeEditPart(n);
				if (nodeEdPart != null) {
					int w = nodeEdPart.getFigure().getSize().width;
					if (n.getX()+w > x) {
						cc.add(new MoveNodeObjectCommand(n,getNodeEditPart(n), (x-5 -w), n.getY()));							
					}
				}
			}
		}
		return cc;
	}
	
	private boolean canMoveNode(ChangeBoundsRequest req) {
		TGG tgg = GraphicalNodeUtil.getLayoutSystem((Graph)this.getHost().getModel());
		int reqX;

		TNodeObjectEditPart nep = (TNodeObjectEditPart) req.getEditParts().get(0);
		TNode n = nep.getCastedModel();
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
			if (nep == null) {
				nep = (TNodeObjectEditPart) obj;
				maxX = nep.getCastedModel().getX();
				maxW = nep.getFigure().getSize().width;
			}
			else if ((nep.getCastedModel().getX() + nep.getFigure().getSize().width)  > (maxX + maxW)){
				nep = (TNodeObjectEditPart) obj;
				maxX = nep.getCastedModel().getX();
				maxW = nep.getFigure().getSize().width;
			}
		}
		// NodeLayout nl = nep.getLayoutModel();
		int divSCx = ((GraphEditPart)this.getHost()).getCastedModel().getDividerSC_X();
		int divCTx = ((GraphEditPart)this.getHost()).getCastedModel().getDividerCT_X();
		int divDistance = divCTx - divSCx;
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
		return false;
	}
	
	
	private TNodeObjectEditPart getNodeEditPart(Node n) {
		// Performance Hack try to find EditPart by Adapter
		for (Adapter a : n.eAdapters()) {
			try {
				Field field = a.getClass().getDeclaredField("this$0");
				field.setAccessible(true);
				Object object = field.get(a);
				if (object instanceof TNodeObjectEditPart && ((TNodeObjectEditPart) object).getCastedModel() == n)
					return (TNodeObjectEditPart) object;
			} catch (Exception ex) {
				
			}
		}
		GraphEditPart gep = (GraphEditPart) this.getHost();
		List<?> list = gep.getChildren();
		for (Object child : list) {
			if (child instanceof TNodeObjectEditPart
					&& ((TNodeObjectEditPart) child).getCastedModel() == n) {
				return (TNodeObjectEditPart) child;
			}
		}
		return null;
	}

}

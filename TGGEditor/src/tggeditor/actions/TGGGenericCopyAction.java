/**
 * 
 */
package tggeditor.actions;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.editparts.tree.GraphFolderTreeEditPart;
import tggeditor.editparts.tree.GraphTreeEditPart;
import tggeditor.editparts.tree.rule.FTRules;
import tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;

/**
 * @author Johann
 * 
 */
public class TGGGenericCopyAction extends GenericCopyAction {

	/**
	 * @param part
	 */
	public TGGGenericCopyAction(IWorkbenchPart part) {
		super(part);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.actions.GenericCopyAction#calculateEnabled()
	 */
	@Override
	public boolean calculateEnabled() {
		final List<?> selectedObjects = getSelectedObjects();
		for (final Object element : selectedObjects) {
			if (element instanceof GraphFolderTreeEditPart
					|| element instanceof RuleFolderTreeEditPart
					|| element instanceof FTRules) {
				return false;
			}
			if (element instanceof GraphTreeEditPart) {
				GraphTreeEditPart gtep = (GraphTreeEditPart) element;
//				if (gtep.ge)
				Graph g = (Graph) gtep.getCastedModel();
				if (g.eContainer() instanceof Rule || g.eContainer() instanceof NestedCondition) {
					return false;
				}
			}
			
				
//			if (element instanceof LhsRhsTreeEditPart
//					|| element instanceof SimpleListTreeEditpart<?>
//					|| element instanceof ElementsContainerTreeEditPart<?>
//					|| (element instanceof TransformationUnitTreeEditPart<?> && !(element instanceof RuleTreeEditPart))) {
//				return false;
//			}
		}
		
		boolean result = super.calculateEnabled();
		if (super.selection.isEmpty() || !result)
			return false;
		LinkedList<EObject> selectedObj = new LinkedList<EObject>(super.selection);
		
		TGG layout = null;
		for(Resource res : selectedObj.getFirst().eResource().getResourceSet().getResources()){
			for (EObject obj : res.getContents()){
				if (obj instanceof TGG)
					layout = (TGG) obj;
			}
		}
		
		if (layout == null)
			return true;
		HashSet<Node> nodes = new HashSet<Node>();
		HashSet<Edge> edges = new HashSet<Edge>();
		
		Graph g = null;
		Rule r = null;
		
		for (EObject object : super.selection) {
			if (object instanceof Node){
				nodes.add((Node) object);
			} 
			else if (object instanceof Edge){
				edges.add((Edge) object);
			}
			else if (object instanceof Rule) {
				r = (Rule) object;
				g = r.getRhs();
			}
			else if (object instanceof Graph) {
				g = (Graph) object;
			}
			else {
				TreeIterator<EObject> iterator = object.eAllContents();
				while(iterator.hasNext()){
					EObject eObj = iterator.next();
					if (eObj instanceof Node){
						nodes.add((Node) eObj);
					}
				}				
			}			
		}
		
		if (r != null) {
			//add GraphLayouts, NodeLayouts, EdgeLayouts of all NACs
						
			Formula formula = r.getLhs().getFormula();
			if (formula != null) {
				TreeIterator<EObject> iter = formula.eAllContents();
			
				while (iter.hasNext()) {
					EObject o = iter.next();
					
					if (o instanceof NestedCondition) {
						NestedCondition nc = (NestedCondition)o;
						Graph graph = nc.getConclusion();
						
						for (Node node : graph.getNodes()) {
							for (NodeLayout nL : layout.getNodelayouts()) {
								if (nL.getNode() == node) {
									super.selection.add(nL);
								}
							}
						}
						
						for (Edge edge : graph.getEdges()) {
							for (EdgeLayout eL : layout.getEdgelayouts()) {
								if (eL.getRhsedge() == edge) {
									super.selection.add(eL);
								}
							}
						}
						
						for (GraphLayout gL : layout.getGraphlayouts()) {
							if (gL.getGraph() == graph) {
								super.selection.add(gL);
							}
						}
					}
				}
			}
		}
		
		if (g != null) {
			// add GraphLayouts, NodeLayouts, EdgeLayouts in case of graph/rule copy
			for (GraphLayout layoutObj : layout.getGraphlayouts()) {
				if (layoutObj.getGraph() == g) {
					super.selection.add(layoutObj);
				}
			}
			for (Node n : g.getNodes()) {
				for (NodeLayout nodeLayout : layout.getNodelayouts()) {
					if (nodeLayout.getNode() == n) {
						super.selection.add(nodeLayout);
					}
				}
			}
			for (Edge n : g.getEdges()) {
				for (EdgeLayout edgeLayout : layout.getEdgelayouts()) {
					if (edgeLayout.getRhsedge() == n) {
						super.selection.add(edgeLayout);
					}
				}
			}
		}
		else {
			// add  NodeLayouts, EdgeLayouts of all selected node / edges
			for (NodeLayout layoutObj : layout.getNodelayouts()) {
				if (nodes.contains(layoutObj.getNode()))
					super.selection.add(layoutObj);
			}
			
			for (EdgeLayout layoutObj : layout.getEdgelayouts()) {
				if (nodes.contains(layoutObj.getRhsedge()))
					super.selection.add(layoutObj);
			}
		}
		
		return true;
	}

}

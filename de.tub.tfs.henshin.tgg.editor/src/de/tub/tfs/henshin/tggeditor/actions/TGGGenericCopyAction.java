/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.actions;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.muvitor.actions.GenericCopyAction;


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
		registerPostSelectionStep(new CopyPostSelectStep(){

			@Override
			public void postProcessSelection(Collection<EObject> selection) {
				List<EObject> obj = new LinkedList<EObject>();
				for (EObject eObject : selection) {
					if (eObject instanceof Node){
						for (Edge edge : ((Node)eObject).getOutgoing() ) {
							if (selection.contains(edge.getTarget()))
								obj.add(edge);
						}
					}
				}
				selection.addAll(obj);
			}
			
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.actions.GenericCopyAction#calculateEnabled()
	 */
//	@Override
//	public boolean calculateEnabled() {
//		final List<?> selectedObjects = getSelectedObjects();
//		for (final Object element : selectedObjects) {
//			if (element instanceof GraphFolderTreeEditPart
//					|| element instanceof RuleFolderTreeEditPart
//					|| element instanceof FTRuleFolder) {
//				return false;
//			}
//			if (element instanceof GraphTreeEditPart) {
//				GraphTreeEditPart gtep = (GraphTreeEditPart) element;
//				TripleGraph g = (TripleGraph) gtep.getCastedModel();
//				if (g.eContainer() instanceof Rule || g.eContainer() instanceof NestedCondition) {
//					return false;
//				}
//			}
//			
//				
////			if (element instanceof LhsRhsTreeEditPart
////					|| element instanceof SimpleListTreeEditpart<?>
////					|| element instanceof ElementsContainerTreeEditPart<?>
////					|| (element instanceof TransformationUnitTreeEditPart<?> && !(element instanceof RuleTreeEditPart))) {
////				return false;
////			}
//		}
//		
//		boolean result = super.calculateEnabled();
//		if (super.selection.isEmpty() || !result)
//			return false;
//		LinkedList<EObject> selectedObj = new LinkedList<EObject>(super.selection);
		
//		TGG layout = null;
//		
//		// case: connection of selected object to its resource is lost, e.g., editor is not active, because another editor is used
//		if(selectedObj.getFirst().eResource()==null) return false;
//		
//		for(Resource res : selectedObj.getFirst().eResource().getResourceSet().getResources()){
//			for (EObject obj : res.getContents()){
//				if (obj instanceof TGG)
//					layout = (TGG) obj;
//			}
//		}
//		
//		if (layout == null)
//			return true;
//		HashSet<Node> nodes = new HashSet<Node>();
//		HashSet<Edge> edges = new HashSet<Edge>();
//		
//		
//		for (EObject object : super.selection) {
//			if (object instanceof Node){
//				nodes.add((Node) object);
//			} 
//			else if (object instanceof Edge){
//				edges.add((Edge) object);
//			}
//			else if (object instanceof Rule) {
//			}
//			else if (object instanceof Graph) {
//			}
//			else {
//				TreeIterator<EObject> iterator = object.eAllContents();
//				while(iterator.hasNext()){
//					EObject eObj = iterator.next();
//					if (eObj instanceof Node){
//						nodes.add((Node) eObj);
//					}
//				}				
//			}			
//		}
		
//		if (r != null) {
//			//add GraphLayouts, NodeLayouts, EdgeLayouts of all NACs
//						
//			Formula formula = r.getLhs().getFormula();
//			if (formula != null) {
//				TreeIterator<EObject> iter = formula.eAllContents();
//			
////				while (iter.hasNext()) {
////					EObject o = iter.next();
////					
//////					if (o instanceof NestedCondition) {
//////						NestedCondition nc = (NestedCondition)o;
//////						Graph graph = nc.getConclusion();
//////						
//////						// node layout is deprecated and no longer stored in the tgg file
////////						for (Node node : graph.getNodes()) {
////////							for (NodeLayout nL : layout.getNodelayouts()) {
////////								if (nL.getNode() == node) {
////////									super.selection.add(nL);
////////								}
////////							}
////////						}
//////
//////						// edge layout is deprecated and no longer stored in the tgg file
////////						for (Edge edge : graph.getEdges()) {
////////							for (EdgeLayout eL : layout.getEdgelayouts()) {
////////								if (eL.getRhsedge() == edge) {
////////									super.selection.add(eL);
////////								}
////////							}
////////						}
//////						
////////						for (GraphLayout gL : layout.getGraphlayouts()) {
////////							if (gL.getGraph() == graph) {
////////								super.selection.add(gL);
////////							}
////////						}
//////					}
////				}
//			}
//		}
		
//		if (g != null) {
//			// add GraphLayouts, NodeLayouts, EdgeLayouts in case of graph/rule copy
////			for (GraphLayout layoutObj : layout.getGraphlayouts()) {
////				if (layoutObj.getGraph() == g) {
////					super.selection.add(layoutObj);
////				}
////			}
//			// node layouts and edge layouts in tgg model are no longer used - information explicit in henshin model
////			for (Node n : g.getNodes()) {
////				for (NodeLayout nodeLayout : layout.getNodelayouts()) {
////					if (nodeLayout.getNode() == n) {
////						super.selection.add(nodeLayout);
////					}
////				}
////			}
////			for (Edge n : g.getEdges()) {
////				for (EdgeLayout edgeLayout : layout.getEdgelayouts()) {
////					if (edgeLayout.getRhsedge() == n) {
////						super.selection.add(edgeLayout);
////					}
////				}
////			}
//		}
//		else {
//			// node layouts and edge layouts in tgg model are no longer used - information explicit in henshin model
////			// add  NodeLayouts, EdgeLayouts of all selected node / edges
////			for (NodeLayout layoutObj : layout.getNodelayouts()) {
////				if (nodes.contains(layoutObj.getNode()))
////					super.selection.add(layoutObj);
////			}
////			
////			for (EdgeLayout layoutObj : layout.getEdgelayouts()) {
////				if (nodes.contains(layoutObj.getRhsedge()))
////					super.selection.add(layoutObj);
////			}
//		}
		
//		return true;
//	}

}

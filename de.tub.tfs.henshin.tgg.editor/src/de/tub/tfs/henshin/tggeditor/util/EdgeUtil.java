package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


public class EdgeUtil {

	/**
	 * Gets the edgelayout to the given edge
	 * @param edge
	 * @return edgelayout
	 */
	public static EdgeLayout getEdgeLayout(Edge edge) {
		TGG layoutSys = NodeUtil.getLayoutSystem(edge.getSource().getGraph());
		if(layoutSys == null) 
		{
			return null;
		}
		return getEdgeLayout(edge, layoutSys);
	}

	/**
	 * Gets the edgelayout in the given layoutsystem to the given edge
	 * @param edge
	 * @param layoutModel
	 * @return edgelayout
	 */
	public static EdgeLayout getEdgeLayout(Edge edge, TGG layoutModel) {
		EdgeLayout result = null;
		if (layoutModel != null) {
			result = findEdgeLayout(edge, layoutModel);
		}
		return result;
	}

	private static EdgeLayout findEdgeLayout(Edge ruleEdgeRHS) {
		TGG layoutSys = NodeUtil.getLayoutSystem(ruleEdgeRHS.getSource().getGraph());
		return findEdgeLayout(ruleEdgeRHS,layoutSys);
	}

	
	/**
	 * finds the edge layout in layout system
	 * @param edge
	 * @param layoutSystem
	 * @return the edge layout
	 */
	protected static EdgeLayout findEdgeLayout(Edge edge, TGG layoutSystem) {
		EdgeLayout result = null;
		for (EdgeLayout edgeLayout : layoutSystem.getEdgelayouts()) {
			if (edgeLayout.getRhsedge() == edge || edgeLayout.getLhsedge() == edge) {
				result = edgeLayout;
				break;
			}
		}
		
//		if (result == null) {
//			result = TggFactory.eINSTANCE.createEdgeLayout();
//			layoutSystem.getEdgelayouts().add(result);
//		}
		return result;
	}

	
	public static void refreshIsMarked(Edge ruleEdgeRHS) {
		
			computeAndCreateIsMarked(ruleEdgeRHS);
		
	}
	
//	public static Boolean getIsMarked(Edge ruleEdgeRHS) {
//		if (ruleEdgeRHS.getIsMarked() != null)
//			return ruleEdgeRHS.getIsMarked();
//		else { // marker is not available, thus copy from layout model and
//				// delete entry in layout model
//			return computeAndCreateIsMarked(ruleEdgeRHS);
//		}
//	}

	private static void computeAndCreateIsMarked(Edge ruleEdgeRHS) {
		if (1==1)
			return;
		// marker value is not available in ruleAttributeRHS, thus compute it
		if (RuleUtil.Translated.equals(((TEdge)ruleEdgeRHS).getMarkerType())){
			return;
		}
		EdgeLayout edgeLayout = getEdgeLayout(ruleEdgeRHS);
		if (edgeLayout == null) { // no layout is found
			// determine type of marker
//			Rule rule = ruleEdgeRHS.getGraph().getContainerRule();
//			if (ModelUtil.getRuleLayout(rule)!=null)
//				ruleEdgeRHS.setMarkerType(RuleUtil.Translated);
//			else
				((TEdge) ruleEdgeRHS).setMarkerType(RuleUtil.NEW);

			// check for existing edge in LHS
			Edge lhsEdge = RuleUtil
					.getLHSEdge(ruleEdgeRHS);
			if (lhsEdge != null) {
				// edge is preserved -> no marker
				((TEdge) ruleEdgeRHS).setMarkerType(null);
			} else {
				// edge is created -> add marker
				((TEdge) ruleEdgeRHS).setMarkerType(RuleUtil.NEW);
			}

		} else { // edge layout is found
			Boolean isTranslatedLHS = edgeLayout.getLhsTranslated();
			boolean isNew = edgeLayout.isNew();
			if (isTranslatedLHS == null) {
				if (isNew)
					((TEdge) ruleEdgeRHS).setMarkerType(RuleUtil.NEW);
				else
					((TEdge) ruleEdgeRHS).setMarkerType(null);
			} else {
				if (isTranslatedLHS){
					((TEdge) ruleEdgeRHS).setMarkerType(null);
				} else {
					((TEdge) ruleEdgeRHS).setMarkerType(RuleUtil.Translated);
				}
				
			}
		}
		// delete layout entry in layout model
		while (edgeLayout != null) {
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					edgeLayout);
			cmd.execute();
			// find possible duplicates of layout
			edgeLayout = findEdgeLayout(ruleEdgeRHS);
		}
		return;
	}

	/**
	 * Find the edge between a source node and a target node with a specific type. Is just working 
	 * when there is not more than one one type of edge between the two nodes allowed.
	 * @param source source node
	 * @param target target node
	 * @param type type of the edge
	 * @return edge between the source and the target node with a specific type
	 */
	public static Edge findEdge(Node source, Node target, EReference type) {
		for (Edge e : source.getOutgoing()) {
			if (e.getType() == type &&
					e.getTarget() == target) {
				return e;
			}
		}
		return null;
	}


}

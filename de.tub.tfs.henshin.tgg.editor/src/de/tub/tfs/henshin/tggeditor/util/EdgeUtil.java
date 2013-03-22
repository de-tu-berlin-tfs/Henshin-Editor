package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Edge;

import de.tub.tfs.henshin.tgg.EdgeLayout;
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
		if(layoutSys == null) {ExceptionUtil.error("Layout model is missing for retrieving edge layout"); return null;}
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
		if (ruleEdgeRHS.getIsMarked() != null)
			return;
		else { // marker is not available, thus copy from layout model and
				// delete entry in layout model
			computeAndCreateIsMarked(ruleEdgeRHS);
		}
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
		// marker value is not available in ruleAttributeRHS, thus compute it
		EdgeLayout edgeLayout = getEdgeLayout(ruleEdgeRHS);
		if (edgeLayout == null) { // no layout is found
			// determine type of marker
//			Rule rule = ruleEdgeRHS.getGraph().getContainerRule();
//			if (ModelUtil.getRuleLayout(rule)!=null)
//				ruleEdgeRHS.setMarkerType(RuleUtil.Translated);
//			else
				ruleEdgeRHS.setMarkerType(RuleUtil.NEW);

			// check for existing edge in LHS
			Edge lhsEdge = RuleUtil
					.getLHSEdge(ruleEdgeRHS);
			if (lhsEdge != null) {
				// edge is preserved -> no marker
				ruleEdgeRHS.setIsMarked(false);
			} else {
				// edge is created -> add marker
				ruleEdgeRHS.setIsMarked(true);
			}

		} else { // edge layout is found
			Boolean isTranslatedLHS = edgeLayout.getLhsTranslated();
			boolean isNew = edgeLayout.isNew();
			if (isTranslatedLHS == null) {
				ruleEdgeRHS.setMarkerType(RuleUtil.NEW);
				ruleEdgeRHS.setIsMarked(isNew);
			} else {
				ruleEdgeRHS.setMarkerType(RuleUtil.Translated);
				ruleEdgeRHS.setIsMarked(!isTranslatedLHS);
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


}

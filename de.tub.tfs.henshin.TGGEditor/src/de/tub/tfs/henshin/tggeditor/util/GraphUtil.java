package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Graph;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes.NodeGraphType;


public class GraphUtil {
	
	static public int center = 350;
	static public int correstpondenceWidth = 100;
	
	/**
	 * calculates the NodeGraphType for specific x cooredinate in graph
	 * @param x is the given x coordinate
	 * @param graphEditPart where to calculate the type
	 * @return type
	 */
	public static NodeGraphType getNodeGraphTypeForXCoordinate(GraphEditPart graphEditPart, int x) {
		if(graphEditPart != null) {
			int SCx = graphEditPart.getDividerSCpart().getCastedModel().getDividerX();
			int CTx = graphEditPart.getDividerCTpart().getCastedModel().getDividerX();;
			correstpondenceWidth = CTx-SCx;
			center = SCx + correstpondenceWidth/2;
		}

		if(x < center - correstpondenceWidth/2) return NodeGraphType.SOURCE;
		if(x < center + correstpondenceWidth/2) return NodeGraphType.CORRESPONDENCE;
		if(x >= center + correstpondenceWidth/2) return NodeGraphType.TARGET;
		return NodeGraphType.DEFAULT;
	}
	
	public static int getMinXCoordinateForNodeGraphType(NodeGraphType type){
		switch (type) {
		case DEFAULT: return 0;
		case SOURCE : return 0;
		case RULE : return 0;
		case CORRESPONDENCE: return center-correstpondenceWidth/2;
		case TARGET: return center+correstpondenceWidth /2;
		}
		return 0;
	}

	
	/**
	 * Searches the graph layout of divider. If dividerSC is true it searches for 
	 * source-correspondence divider, if its false it searches for the correspondence-target divider.
	 * @param graph which is linked to its dividers
	 * @param dividerSC (search SC or CT divider?)
	 * @return graph layout of searched divider
	 */
	public static GraphLayout getGraphLayout(Graph graph, boolean dividerSC) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(graph);
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (dividerSC == graphLayout.isIsSC())
						return graphLayout;
				}
			}
		}
		return null;
	}
	/**
	 * Searches the graph layout for the dividers and returns them.
	 * @param graph which is linked to its dividers
	 * @return graph layouts [dividerSC,dividerCT] 
	 */
	public static GraphLayout[] getGraphLayouts(Graph graph) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(graph);
		GraphLayout[] layouts = new GraphLayout[2];
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (graphLayout.isIsSC()) layouts[0]=graphLayout;
					else layouts[1]=graphLayout;
				}
			}
		}
		return layouts;
	}
}

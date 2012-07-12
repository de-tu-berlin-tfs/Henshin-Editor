package tggeditor.util;

import org.eclipse.emf.henshin.model.Edge;

import tgg.EdgeLayout;
import tgg.TGG;

public class EdgeUtil {

	/**
	 * Gets the edgelayout to the given edge
	 * @param edge
	 * @return edgelayout
	 */
	public static EdgeLayout getEdgeLayout(Edge edge) {
		TGG layoutSys = NodeUtil.getLayoutSystem(edge.getSource().getGraph());
		if(layoutSys == null) return null;
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

	/**
	 * finds the edge layout in layout system
	 * @param edge
	 * @param layoutSystem
	 * @return the edge layout
	 */
	protected static EdgeLayout findEdgeLayout(Edge edge, TGG layoutSystem) {
		for (EdgeLayout edgeLayout : layoutSystem.getEdgelayouts()) {
			if (edgeLayout.getRhsedge() == edge || edgeLayout.getLhsedge() == edge) {
				return edgeLayout;
			}
		}
		
		//TODO create new if not found
		return null;
	}

}

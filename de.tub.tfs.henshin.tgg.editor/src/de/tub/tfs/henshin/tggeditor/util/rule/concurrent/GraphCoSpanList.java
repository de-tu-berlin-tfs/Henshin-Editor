/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.LinkedList;
import org.eclipse.emf.henshin.model.Graph;

public class GraphCoSpanList extends LinkedList<GraphCoSpan>{
	
	private final Graph graphL;
	private final Graph graphR;
	
	public Graph getGraphL() {
		return graphL;
	}

	public Graph getGraphR() {
		return graphR;
	}
	
	public GraphCoSpanList(Graph leftGraph, Graph rightGraph){
		super();
		if (leftGraph==null || rightGraph==null) throw new IllegalArgumentException("null is not a valid CoSpan argument");
		this.graphL = leftGraph;
		this.graphR = rightGraph;
		GraphLCopy2GraphRCopyIntersectionsList intersections = new GraphLCopy2GraphRCopyIntersectionsList(graphL, graphR);
		for (IntersectionHandler intersection : intersections){
			this.add(new GraphCoSpan(intersection));
		}
	}
}

/**
 * 
 */


import java.util.Hashtable;
import java.util.List;

import org.eclipse.draw2d.graph.DirectedGraph;
import org.eclipse.draw2d.graph.DirectedGraphLayout;
import org.eclipse.draw2d.graph.Edge;
import org.eclipse.draw2d.graph.Node;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;

/**
 * @author olga
 *
 */
public class DirectedDraw2dGraphLayout extends DirectedGraph {

	final Hashtable<EdNode, Node> ednode2node = new Hashtable<EdNode, Node>();
	EdGraph edgraph;
	
	/**
	 * 
	 */
	public DirectedDraw2dGraphLayout() {		
	}
		
	
	/**
	 * 
	 */
	public DirectedDraw2dGraphLayout(final EdGraph graph) {
		this.setGraph(graph);
	}
	
	public void setGraph(final EdGraph graph) {
		this.edgraph = graph;
		convertToDirectedGraph();
	}
	
	public void applyLayout() {
		if (this.edgraph == null)
			return;
		
		long time1 = System.currentTimeMillis();
		
		final DirectedGraphLayout layouter = new DirectedGraphLayout();
		layouter.visit(this);
		
		convertFromDirectedGraph();
		
		System.out.println("*** DirectedGraph  layout  don  in "
				+(System.currentTimeMillis()-time1)+"ms");
	}
	
	@SuppressWarnings("unchecked")
	private void convertToDirectedGraph()  {
		if (this.edgraph != null) {
			List<EdNode> aggnodes = this.edgraph.getVisibleNodes();
			for (int i=0; i<aggnodes.size(); i++) {
				EdNode elem = aggnodes.get(i);
				
				Node n = new Node(elem);
				n.x = elem.getX();
				n.y = elem.getY();
				n.width = elem.getWidth();
				n.height = elem.getHeight();
				this.nodes.add(n);
				
				this.ednode2node.put(elem, n);
			}
			
			List<EdArc> aggarcs = this.edgraph.getArcs();
			for (int i=0; i<aggarcs.size(); i++) {
				EdArc elem = aggarcs.get(i);
				Node src = this.ednode2node.get(elem.getSource());
				Node tar = this.ednode2node.get(elem.getTarget());
				
				if (src != null && tar != null) {
					Edge e = new Edge(elem, src, tar);
					this.edges.add(e);
				}				
			}
		}
	}

	private void convertFromDirectedGraph()  {
		if (this.edgraph != null) {
			List<EdNode> aggnodes = this.edgraph.getNodes();
			for (int i=0; i<aggnodes.size(); i++) {
				EdNode elem = aggnodes.get(i);
				Node n = this.ednode2node.get(elem);
				if (n != null) {
					elem.setX(n.x);
					elem.setY(n.y);
				}
			}
			this.edgraph.straightAllArcs();
		}
	}
	
	
	
	
}

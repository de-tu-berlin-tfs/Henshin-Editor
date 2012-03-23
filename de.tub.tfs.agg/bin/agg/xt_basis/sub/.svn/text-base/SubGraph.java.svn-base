package agg.xt_basis.sub;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;

/**
 * @version $Id: SubGraph.java,v 1.2 2010/09/23 08:28:19 olga Exp $
 * @author $Author: olga $
 * 
 * @deprecated not more supported
 */
public class SubGraph extends Graph {
	private Graph itsSuperGraph;

	public SubGraph(Graph supergraph) {
		super(supergraph.getTypeSet());
		this.itsSuperGraph = supergraph;
	}

	public final void dispose() {
		this.itsSuperGraph = null;
		super.dispose();
	}

	/** Return the graph I am a subgraph of. */
	public final Graph getSuperGraph() {
		return this.itsSuperGraph;
	}

	/** Return <code>true</code> iff I contain the specified graphobject. */
	public boolean isElement(GraphObject obj) {
		boolean res = false;
		if (obj instanceof Node) {
			if (this.itsNodes.contains(obj))
				res = true;
			else
				res = false;
		}
		if (obj instanceof Arc) {
			if (this.itsArcs.contains(obj))
				res = true;
			else
				res = false;
		}
		return res;
	}


	public Enumeration<GraphObject> getElements()
	/***************************************************************************
	 * Iterate through my Nodes and Arcs. * Enumeration elements are of type
	 * <code>GraphObject</code>.
	 * 
	 * @see agg.xt_basis.GraphObject *
	 **************************************************************************/
	{
		Vector<GraphObject> elems = new Vector<GraphObject>();
		Iterator<?> iter = this.itsNodes.iterator();
		while (iter.hasNext()) {
			elems.add((GraphObject)iter.next());
		}
		iter = this.itsArcs.iterator();
		while (iter.hasNext()) {
			elems.add((GraphObject)iter.next());
		}
		return elems.elements();
	}

	/**
	 * Take over a graphobject of my supergraph. If <code>obj</code> is an
	 * arc, its source and target objects are added as well if necessary.
	 * <p><b>Pre:</b> <code>getSuperGraph().isElement(obj)</code>.
	 */
	public final void addObject(GraphObject obj) {
		if (isElement(obj))
			return;

		if (obj != null) {
			if (obj.isArc()) {
				if (!isElement(((Arc) obj).getSource()))
					this.itsNodes.add((Node)((Arc) obj).getSource());
				if (!isElement(((Arc) obj).getTarget()))
					this.itsNodes.add((Node)((Arc) obj).getTarget());
				this.itsArcs.add((Arc)obj);
			} else
				this.itsNodes.add((Node)obj);
		}

	}

	/**
	 * Remove an object from the subgraph. The object still remains in the
	 * supergraph. All of <code>obj</code>'s incoming and outgoing arcs are
	 * removed as well.
	 * 
	 * @return <code>false</code> iff <code>obj</code> was not an element of
	 *         this subgraph.
	 */
	public final boolean removeObject(GraphObject obj) {
		if (obj == null)
			return false;

		Iterator<Arc> anEnum = ((Node)obj).getIncomingArcsSet().iterator();
		while (anEnum.hasNext()) {
			removeObject(anEnum.next());
		}

		anEnum = ((Node)obj).getOutgoingArcsSet().iterator();
		while (anEnum.hasNext()) {
			removeObject(anEnum.next());
		}

		boolean aFlag;
		if (obj instanceof Arc)
			aFlag = (this.itsArcs.remove(obj));
		else
			aFlag = (this.itsNodes.remove(obj));
		return aFlag;
	}

}

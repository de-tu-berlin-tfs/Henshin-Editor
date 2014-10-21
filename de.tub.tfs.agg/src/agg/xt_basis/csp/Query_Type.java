package agg.xt_basis.csp;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Vector;

import agg.util.csp.Query;
import agg.util.csp.Variable;
import agg.xt_basis.GraphObject;

public class Query_Type extends Query {
		
	private HashSet<GraphObject> itsObjects;
	private HashSet<GraphObject> objList;
	
	private boolean randomized;
	
	/**
	 * Construct myself to be a constant query for the objects given in the
	 * parameter <code>objects</code>.
	 */
	public Query_Type(HashSet<GraphObject> objects, Variable querytar) {
		super(querytar, 0);
		
		this.objList = objects;
		this.itsObjects = new LinkedHashSet<GraphObject>(objects);
		
		this.itsWeight = 0;
		if (querytar.getKind() == 1) // its is an Arc
			this.itsWeight += 3; 
	}

	public Query_Type(Variable querytar) {
		super(querytar, 0);
//		System.out.println(((GraphObject)this.itsTarget.getGraphObject()).getType().convertToKey());
		itsObjects = new LinkedHashSet<GraphObject>();
		
		this.itsWeight = 0;
		if (querytar.getKind() == 1) // its is an Arc
			this.itsWeight += 3; 
	}
	
	public void setObjects(final HashSet<GraphObject> objects) {		
		if (objects != null && this.objList != objects) {

			this.objList = objects;
			
			if (this.randomized && this.objList.size() > 1) {
				this.itsObjects.clear();				
				this.randomize(this.objList);
			} else {
				this.itsObjects.clear();
				this.itsObjects.addAll(this.objList);
			}
			getTarget().setDomainSize(this.itsObjects.size());
		}
	}

	public void resetObjects() {
		if (this.objList != null) {
			if (this.randomized && this.objList.size() > 1) {
				this.itsObjects.clear();
				this.randomize(this.objList);
			} else {	
				this.itsObjects.clear();
				this.itsObjects.addAll(this.objList);
			}
			
			getTarget().setDomainSize(this.itsObjects.size());
		}
	}
	
	
	public final HashSet<?> execute() { 
		return this.itsObjects;
	}

	public final void removeObject(final GraphObject obj) {
		if (obj != null && this.itsObjects.size() > 0) {
			// remove object
			this.itsObjects.remove(obj);
		}
	}
	
	public final int getSize() {
		return this.itsObjects.size();
	}

	/**
	 * Return the name of this class.
	 */
	public final String getKind() {
		return "Query_Type";
	}

	// pablo -->
	/**
	 * Caches results of getAvgOutgoingDegree().
	 * @see Query_Type.getAvgOutgoingDegree()
	 */
	int outgoing = -1;

	/**
	 * Calculates the average degree of incoming arcs of 
	 * all nodes in this type query.
	 */
	public final int getAvgOutgoingDegree() {
		if(this.getSize() <= 0)
			return 0;

		// calculate average degree
		if(this.outgoing == -1) {
			this.outgoing = 0;

			Iterator<?> iter = this.itsObjects.iterator();
			while (iter.hasNext()) {
				GraphObject go = (GraphObject)iter.next();
				this.outgoing += go.getNumberOfOutgoingArcs();
			}
			this.outgoing /= this.getSize();
		}
		
		return this.outgoing;
	}

	/**
	 * Caches results of getAvgIncomingDegree().
	 * @see Query_Type.getAvgIncomingDegree()
	 */
	int incoming = -1;
	
	/**
	 * all nodes in this type query.
	 */
	public final int getAvgIncomingDegree() {		
		if(this.getSize() <= 0)
			return 0;
		
		// calculate average degree
		if(this.incoming == -1) {
			this.incoming = 0;
			
			Iterator<?> iter = this.itsObjects.iterator();
			while (iter.hasNext()) {
				GraphObject go = (GraphObject)iter.next();
				this.incoming += go.getNumberOfIncomingArcs();
			}
			this.incoming /= this.getSize();
		}	
		
		return this.incoming;
	}

	/* (non-Javadoc)
	 * @see agg.util.csp.Query#isDomainEmpty()
	 */
	public boolean isDomainEmpty() {
		return this.itsObjects.isEmpty();
	}

	private void randomize(HashSet<GraphObject> objects) {
		final Vector<GraphObject> newDom = new Vector<GraphObject>(objects);	
		final Random r = new Random();
		while (newDom.size() > 0) {
			int domSize = newDom.size();
			int i = r.nextInt(domSize);
			GraphObject anObj = newDom.remove(i);
			this.itsObjects.add(anObj);
		}		
	}
	
	public void setRandomizedDomain(boolean randomized) {
		this.randomized = randomized;
	}
	
}

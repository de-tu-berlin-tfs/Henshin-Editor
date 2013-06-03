package agg.xt_basis.csp;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import agg.util.csp.Query;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.Node;

public class Query_Outgoing extends Query {
	
	private String arcKey;
	private boolean withNTI; // graph with Node Type Inheritance
	
	/**
	 * Construct myself to be a binary query for outgoing arcs of
	 * <code>obj</code> with abstraction <code>abs</code>.
	 */
	public Query_Outgoing(Variable obj, Variable tar) {
		super(obj, tar, 6);
		
		this.arcKey = ((Arc)this.itsTarget.getGraphObject()).convertToKey();
		this.withNTI = ((Arc)this.itsTarget.getGraphObject()).getContext().getTypeSet().hasInheritance();
	}

	public final HashSet<?> execute() {//Arc
		if (this.withNTI) {
			return ((Node) getSourceInstance(0)).getOutgoingArcsSet();
		} 
		HashSet<Arc> outs = ((Node) getSourceInstance(0)).getOutgoingArcsSet();
		HashSet<Arc> result = new LinkedHashSet<Arc>(); //outs.size());
		Iterator<Arc> iter = outs.iterator();
		while (iter.hasNext()) {
			Arc a = iter.next();
			if (a.convertToKey().equals(this.arcKey)) {
				result.add(a);
			}
		}
		return result;
	}
	
	public final int getSize() {
//		return 1; // pablo
		return getTarget().getTypeQuery().getAvgOutgoingDegree(); // pablo
	}

	/**
	 * Return the name of this class.
	 */
	public final String getKind() {
		return "Query_Outgoing";
	}

	/* (non-Javadoc)
	 * @see agg.util.csp.Query#isDomainEmpty()
	 */
	@Override
	public boolean isDomainEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}

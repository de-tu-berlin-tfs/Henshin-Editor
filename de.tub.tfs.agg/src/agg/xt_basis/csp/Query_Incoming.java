package agg.xt_basis.csp;


import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

import agg.util.csp.Query;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.Node;

public class Query_Incoming extends Query {
		
	private String arcKey;
	private boolean withNTI; // graph with Node Type Inheritance
	
	/**
	 * Construct myself to be a binary query for incoming arcs of
	 * <code>obj</code> with abstraction <code>abs</code>.
	 */
	public Query_Incoming(Variable obj, Variable tar) {
		super(obj, tar, 3);
		
		this.arcKey = ((Arc)this.itsTarget.getGraphObject()).convertToKey();
		this.withNTI = ((Arc)this.itsTarget.getGraphObject()).getContext().getTypeSet().hasInheritance();
	}

	public final HashSet<?> execute() {//Arc
		if (this.withNTI) {
			return ((Node) getSourceInstance(0)).getIncomingArcsSet();
		} 
		HashSet<Arc> ins = ((Node) getSourceInstance(0)).getIncomingArcsSet();
		HashSet<Arc> result = new LinkedHashSet<Arc>(); //ins.size());
		Iterator<Arc> iter = ins.iterator();
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
		return getTarget().getTypeQuery().getAvgIncomingDegree(); // pablo
	}

	/**
	 * Return the name of this class.
	 */
	public final String getKind() {
		return "Query_Incoming";
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

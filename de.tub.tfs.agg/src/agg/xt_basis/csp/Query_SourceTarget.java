package agg.xt_basis.csp;


import java.util.HashSet;
//import java.util.LinkedHashSet;

import agg.util.csp.Query;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;

public class Query_SourceTarget extends Query {
	
	final private HashSet<GraphObject> itsSet = new HashSet<GraphObject>(2);

	/**
	 * Construct myself to be a unary query for the source of <code>arc</code>.
	 */
	public Query_SourceTarget(Variable arc, Variable tar) {
		super(arc, tar, 0);

//		itsSet.setSize(2);
	}

	public final HashSet<?> execute() {//GraphObject
		this.itsSet.clear();
		this.itsSet.add(((Arc) getSourceInstance(0)).getSource());
		this.itsSet.add(((Arc) getSourceInstance(0)).getTarget());
		return this.itsSet;
	}
	
	public final int getSize() {
		return 2;
	}

	/**
	 * Return the name of this class.
	 */
	public final String getKind() {
		return "Query_SourceTarget";
	}

	public boolean isDomainEmpty() {
		return false;
	}

}

package agg.xt_basis.csp;

import java.util.HashSet;

import agg.util.csp.Query;
import agg.util.csp.Variable;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
//import java.util.LinkedHashSet;

public class Query_Target extends Query {
	
	final private HashSet<GraphObject> itsSet = new HashSet<GraphObject>(1);

	/**
	 * Construct myself to be a unary query for the target of <code>arc</code>.
	 */
	public Query_Target(Variable arc, Variable tar) {
		super(arc, tar, 0);

//		itsSet.setSize(1);
	}

	public final HashSet<?> execute() {//GraphObject
		this.itsSet.clear();
		this.itsSet.add(((Arc) getSourceInstance(0)).getTarget());
		return this.itsSet;
	}
	
	public final int getSize() {
		return 1;
	}

	/**
	 * Return the name of this class.
	 */
	public final String getKind() {
		return "Query_Target";
	}

	public boolean isDomainEmpty() {
		return false;
	}

}

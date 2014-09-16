/**
 * 
 */
package agg.xt_basis.agt;


import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeSet;

/**
 * Kernel rule is the subrule of an interaction rule scheme and models a common
 * subaction of two or more actions (extending rules) and used to
 * synchronize application of multi rules in parallel.
 * 
 * @author olga
 *
 */
public class KernelRule extends Rule {

	private RuleScheme itsRuleScheme;
	
	
	/**
	 * 
	 */
	public KernelRule() {
		super();		
		this.itsName = "KernelRule";
	}

	/**
	 * @param types
	 */
	public KernelRule(final TypeSet types) {
		super(types);		
		this.itsName = "KernelRule";
		this.itsOrig.setKind(GraphKind.LHS);
		this.itsImag.setKind(GraphKind.RHS);
	}

	/**
	 * @param left
	 * @param right
	 */
	public KernelRule(final Graph left, final Graph right) {
		super(left, right);		
		this.itsName = "KernelRule";
		this.itsOrig.setKind(GraphKind.LHS);
		this.itsImag.setKind(GraphKind.RHS);
	}
	
	/**
	 * Returns its full name : schemeName.ruleName
	 */
	public String getQualifiedName() {
		if (this.itsRuleScheme != null)
			return this.itsRuleScheme.getName().concat(".").concat(this.itsName);
		
		return this.itsName;
	}
	
	public void setRuleScheme(final RuleScheme rs) {
		this.itsRuleScheme = rs;
	}
	
	public RuleScheme getRuleScheme() {
		return this.itsRuleScheme;
	}
	
	public void setChanged(boolean b) {
		this.changed = b;
	}
	
	
	/*
	public final void update(Observable o, Object arg) { 

	}
	*/
}

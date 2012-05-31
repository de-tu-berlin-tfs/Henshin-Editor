package agg.xt_basis;

import java.util.Hashtable;

import agg.util.Pair;

public class InverseRuleConstructData {

	private Rule invRule;
	
	private boolean extended;
	
	private OrdinaryMorphism l2rInv, r2lInv;
	
	// key: NAC, value: Pair
	// Pair.first: L -> Lcopy+NAC, 
	// Pair.second: NAC -> Lcopy+NAC
	private Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	leftNAC2extLeft;
	
	// key: PAC, value: Pair
	// Pair.first: L -> Lcopy+PAC, 
	// Pair.second: PAC -> Lcopy+PAC
	private Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	leftPAC2extLeft;
	
	// key: PAC, value: Pair
	// Pair.first: L -> Lcopy+PAC, 
	// Pair.second: PAC -> Lcopy+PAC
	private Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	leftAC2extLeft;
	
	
	public InverseRuleConstructData(final Rule r) {
		Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		invConstruct = BaseFactory.theFactory().makeAbstractInverseRule(r);
		if (invConstruct != null) {
			this.invRule = invConstruct.first.first;
			// call this method to mark variables and conditions of the inverse rule
			invRule.isReadyToTransform();
			this.extended = invConstruct.first.second.booleanValue();
			this.l2rInv = invConstruct.second.first;
			this.r2lInv = invConstruct.second.second;
			invConstruct.first = null;
			invConstruct.second = null;
		}
	}
	
	
	public boolean isExtended() {
		return this.extended;
	}
	
	public void setExtended(boolean b) {
		this.extended = b;
	}
	
	public Rule getInverseRule() {
		return this.invRule;
	}
	
	public OrdinaryMorphism getLorig2Rinv() {
		return this.l2rInv;
	}
	
	public OrdinaryMorphism getRorig2Linv() {
		return this.r2lInv;
	}
	
	public Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	getNACsStore() {
		if (this.leftNAC2extLeft == null)
			this.leftNAC2extLeft = new Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		return this.leftNAC2extLeft;
	}
	
	public Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	getPACsStore() {
		if (this.leftPAC2extLeft == null)
			this.leftPAC2extLeft = new Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		return this.leftPAC2extLeft;
	}
	
	public Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	getACsStor() {
		if (this.leftAC2extLeft == null)
			this.leftAC2extLeft = new Hashtable<OrdinaryMorphism, Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		return this.leftAC2extLeft;
	}
	
	/**
	 * This method does not destroy the Rule and OrdinaryMorphism instances of the inverse construction.
	 * They must be disposed by the user object explicitly. 
	 */
	public void dispose() {
	
	}
	
	/**
	 * Destroys the Rule and OrdinaryMorphism instances of the inverse construction.
	 * The local references set to null.
	 */
	public void destroy() {
		if (this.invRule != null) {
			this.dispose();
			this.l2rInv.dispose();
			this.r2lInv.dispose();
			
			this.invRule = null;
			this.l2rInv = null;
			this.r2lInv = null;
			
			if (this.leftAC2extLeft != null) {
				this.leftAC2extLeft.clear();
			}
			if (this.leftNAC2extLeft != null) {
				this.leftNAC2extLeft.clear();
			}
			if (this.leftPAC2extLeft != null) {
				this.leftPAC2extLeft.clear();
			}
			this.leftAC2extLeft = null;
			this.leftNAC2extLeft = null;
			this.leftPAC2extLeft = null;
		}
	}

}

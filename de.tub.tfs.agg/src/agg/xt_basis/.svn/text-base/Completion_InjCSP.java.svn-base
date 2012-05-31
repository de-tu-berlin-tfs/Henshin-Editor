package agg.xt_basis;

import agg.xt_basis.csp.CompletionPropertyBits;
import agg.xt_basis.csp.Completion_CSP;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

/**
 * An implementation of morphism completion as a Constraint Satisfaction Problem (CSP), 
 * considering injective solutions only.
 */
public class Completion_InjCSP extends Completion_CSP {
	
	public Completion_InjCSP() {
		super();
		// set default properties:
		getProperties().set(CompletionPropertyBits.INJECTIVE);
		getProperties().set(CompletionPropertyBits.DANGLING);
		getProperties().set(CompletionPropertyBits.IDENTIFICATION);
	}

	public Completion_InjCSP(boolean randomizeDomain) {
		super(randomizeDomain);
		// set default properties:
		getProperties().set(CompletionPropertyBits.INJECTIVE);
		getProperties().set(CompletionPropertyBits.DANGLING);
		getProperties().set(CompletionPropertyBits.IDENTIFICATION);
	}

}

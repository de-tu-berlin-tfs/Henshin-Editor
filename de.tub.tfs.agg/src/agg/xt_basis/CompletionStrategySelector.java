
package agg.xt_basis;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

import agg.xt_basis.csp.Completion_CSP_NoBJ;


/**
 * This class contains an instance of every available completion strategy
 * associated with a minimal description. It provides the basic functionality to
 * implement interactive strategy selection via a GUI.
 */
public class CompletionStrategySelector {

	/**
	 * The dictionary of available Strategies, each associated with a short
	 * descriptive name.
	 */
	private static final Dictionary<MorphCompletionStrategy, String> 
	itsStrategies = new Hashtable<MorphCompletionStrategy, String>(2);

	/** The default strategy. */
	private static final MorphCompletionStrategy itsDefaultStrategy
	// = new Completion_CSP();
//	= new Completion_InjCSP();
	= new Completion_NAC(new Completion_InjCSP(true)); // randomized CSP domain

	static {
		itsStrategies.put(itsDefaultStrategy, "CSP");
		itsStrategies.put(new Completion_NAC(new Completion_CSP_NoBJ()),
				"CSP w/o BJ");
		// itsStrategies.put( new Completion_NAC( new Completion_SimpleBT() ),
		// "Simple BT" );
	}

	/**
	 * Return an enumeration of available strategies. Enumeration elements are
	 * of type <code>MorphCompletionStrategy</code>.
	 * 
	 * @see agg.xt_basis.MorphCompletionStrategy
	 */
	public static Enumeration<MorphCompletionStrategy> getStrategies() {
		return itsStrategies.keys();
	}

	/** Return the default strategy. */
	public static MorphCompletionStrategy getDefault() {
		return itsDefaultStrategy;
	}

	/**
	 * Return a short descriptive name for the given strategy. This name is
	 * intended for use e.g. as a label in a GUI listbox.
	 */
	public static String getName(MorphCompletionStrategy strat) {
		return strat.getName();
//		return itsStrategies.get(strat);
	}

}

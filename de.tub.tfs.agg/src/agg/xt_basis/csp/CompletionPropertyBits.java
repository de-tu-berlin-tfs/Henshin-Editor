package agg.xt_basis.csp;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2000
 * Company:
 * @author
 * @version 1.0
 */

/**
 * This interface defines symbolic names for the properties that can be asked
 * from a morphism completion strategy. These are names for the bits of a
 * <code>BitSet</code> that is used to define which properties are supported
 * by a concrete completion strategy and which properties are actually set.
 * <p>
 * Example 1: When you want to know if a given strategy <code>strat</code>
 * supports restriction to completions that satisfy the dangling condition, you
 * would ask <code>strat.getSupportedProperties().get(DANGLING)</code>.
 * <p>
 * Example 2: When you want a strategy to find only injective completions, you
 * would do <code>strat.getProperties().set(INJECTIVE)</code>. Setting a
 * property that is not supported will have no effect.
 * <p>
 * Example 3: You can access all available property bits - supported or
 * unsupported - in a loop, without having to name each bit explicitly. The
 * following sample code prints the names of all available bits to standard out:
 * 
 * <pre>
 * for (int i = 0; i &lt; CompletionPropertyBits.BITNAME.length; i++)
 * 	System.out.println(CompletionPropertyBits.BITNAME[i]);
 * </pre>
 * 
 * @see java.util.BitSet
 * @see agg.util.StrategyProperties
 * @see agg.xt_basis.MorphCompletionStrategy
 */
public interface CompletionPropertyBits {
	/**
	 * If this bit is set to <code>true</code>, only injective completions of
	 * a morphism will be found.
	 */
	public static final int INJECTIVE = 0;

	/**
	 * If this bit is set to <code>true</code>, only matches that satisfy the
	 * dangling condition will be found. (This bit only applies to the
	 * completion of match morphisms.)
	 */
	public static final int DANGLING = 1;

	/**
	 * If this bit is set to <code>true</code>, only matches that satisfy the
	 * identification condition will be found. (This bit only applies to the
	 * completion of match morphisms.)
	 */
	public static final int IDENTIFICATION = 2;

	/**
	 * If this bit is set to <code>true</code>, only matches will be found
	 * that satisfy all the negative application conditions (NACs) a rule may
	 * have. (This bit only applies to the completion of a match morphism.)
	 */
	public static final int NAC = 3;

	/**
	 * If this bit is set to <code>true</code>, only matches will be found
	 * that satisfy all the positive application conditions (PACs) a rule may
	 * have. (This bit only applies to the completion of a match morphism.)
	 */
	public static final int PAC = 4;
	
	/**
	 * If this bit is set to <code>true</code>, only matches will be found
	 * that satisfy all the general application conditions (GACs) a rule may
	 * have. (This bit only applies to the completion of a match morphism.)
	 */
	public static final int GAC = 5;
	

	/**
	 * A short descriptive name for each of my bits. This may be used e.g. for
	 * GUI button labels.
	 */
	public static String[] BITNAME = { "injective", "dangling",
			"identification", "NACs", "PACs", "GACs" };
}

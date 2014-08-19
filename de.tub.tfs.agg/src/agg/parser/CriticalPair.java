package agg.parser;

import agg.xt_basis.Rule;

//****************************************************************************+
/**
 * CriticalPair provides an algorithm to compute a critical pair. This algorithm
 * is than used in a container.
 * 
 * @author $Author: olga $
 * @version $Id: CriticalPair.java,v 1.7 2010/08/12 14:53:28 olga Exp $
 */
public interface CriticalPair {

	/**
	 * Exclude constant used to specify the exclude algorithm.
	 */
	public static final int EXCLUDE = 0;

	/** Conflict constant used to specify exclude algorithm, too. */
	public static final int CONFLICT = EXCLUDE;

	/** Dependency constant used to specify dependency kind. */
	public static final int TRIGGER_DEPENDENCY = 1;
	public static final int TRIGGER_SWITCH_DEPENDENCY = 2;
	/**
	 * @deprecated  replaced by TRIGGER_DEPENDENCY
	 */
	public static final int DEPENDENCY = TRIGGER_DEPENDENCY;
	
	/**
	 * Conflict free constant used to specify the conflict free algorithm.
	 */
	public static final int CONFLICTFREE = 3;
	
	// ****************************************************************************+
	/**
	 * Returns the number of kind of pairs which will be distinguished. There
	 * must be at least two kind of pairs. That means one kind has no conflicts
	 * and the second kind has conflicts.
	 * 
	 * @return The number of algorithm this object can compute.
	 */
	public int getNumberOfKindOfPairs();

	// ****************************************************************************+
	/**
	 * computes if there is a critical pair of a special kind. Remenber if ther
	 * isn null is returned if the pair is not critical otherwiser a object
	 * which can explain in which way this pair is critical. One possible object
	 * can be a <code>Vector</code> of overlaping graphs. If a kind kind is
	 * requested which cannot be computed a
	 * <code>InvalidAlgorithmException</code> is thrown.
	 * 
	 * @param kind
	 *            specifies the kind of critical pair
	 * @param r1
	 *            defines the first part which can be critical
	 * @param r2
	 *            the second part which can be critical
	 * @throws InvalidAlgorithmException
	 *             Is thrown if a desired algorithm is not provided.
	 * @return The critical object of two rules.
	 */
	public Object isCritical(int kind, Rule r1, Rule r2)
			throws InvalidAlgorithmException;

}

// End of CriticalPair.java
/*
 * $Log: CriticalPair.java,v $
 * Revision 1.7  2010/08/12 14:53:28  olga
 * tuning
 *
 * Revision 1.6  2008/05/05 09:11:51  olga
 * Graph parser - bug fixed.
 * New AGG feature - Applicability of Rule Sequences - in working.
 *
 * Revision 1.5  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.4  2007/09/10 13:05:40  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/03/01 09:55:46 olga - new CPA
 * algorithm, new CPA GUI
 * 
 * Revision 1.2 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.3 2001/03/08 10:42:50 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.6 2001/01/28 13:14:51 shultzke API fertig
 * 
 * Revision 1.1.2.5 2000/12/12 13:27:43 shultzke erste Versuche kritische Paare
 * mit XML abzuspeichern
 * 
 */

// This class belongs to the following package:
package agg.parser;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

//import com.objectspace.jgl.Pair;

import agg.util.XMLObject;
import agg.util.Pair;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;

//****************************************************************************+
/**
 * A container for critical pairs This interface represents the abstract class
 * of a stategy. All classes which implements this interface are concrete
 * strategies and can be used to confige the parser. This interface supports
 * access to critical pairs.
 * 
 * @author $Author: olga $
 * @version $Id: PairContainer.java,v 1.15 2010/12/16 17:32:14 olga Exp $
 */
public interface PairContainer extends XMLObject {

	// ****************************************************************************+
	/**
	 * Computes the critical part of two rules. This can be a
	 * <code>Vector</code> of overlaping graphs.
	 * 
	 * @param r1
	 *            The first part of a critical pair
	 * @param r2
	 *            The second part of a critical pair
	 * @param kind
	 *            The kind of critical pair
	 * @throws InvalidAlgorithmException
	 *             Thrown if a algorithm is desired which is not provided.
	 * @return The critic object of two rules.
	 */
	public Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
	getCriticalPair(Rule r1, Rule r2, int kind)
			throws InvalidAlgorithmException;

	public Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
	getCriticalPair(Rule r1, Rule r2, int kind, boolean local)
			throws InvalidAlgorithmException;
	
	/**
	 * Returned <code>CriticalPairData<\code> object which allows an access to the
	 * computed critical pairs of the specified rules in a more readable way.
	 * @see <code>CriticalPairData<\code>
	 * 
	 * @return  critical pair data if it is already computed, otherwise null
	 */
	public CriticalPairData getCriticalPairData(Rule r1, Rule r2);
	
	/**
	 * Returns a list of <code>CriticalPairData</code>  which allows an access to the
	 * computed critical pairs of the specified kind of conflict in a more readable way.
	 * @see <code>CriticalPairData</code>
	 * 
	 * @return  critical pair data if it is already computed, otherwise null
	 */
	public List<CriticalPairData> getCriticalPairDataOfKind(String kind);
	
	/**
	 * @deprecated  replaced by <code>getCriticalPair(Rule r1, Rule r2, int kind)</code>
	 */
	public Object getCritical(Rule r1, Rule r2, int kind)
	throws InvalidAlgorithmException;

	
	// ****************************************************************************+
	/**
	 * Returns the number of containers for the critical pair.
	 * 
	 * @return The number of containers.
	 */
	public int getNumberOfContainers();

	// ****************************************************************************+
	/**
	 * This container is a <code>Hashtable</code> with a rule as key. The
	 * value will be a set of rules.
	 * 
	 * @param kind
	 *            The kind of algorithm
	 * @throws InvalidAlgorithmException
	 *             Thrown if a algorithm is desired which is not provided.
	 * @return The hashtable with critical pairs.
	 */
	public Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
	getContainer(int kind) throws InvalidAlgorithmException;

	// ****************************************************************************+
	/**
	 * This method computes which rules are in a relation of a special kind.
	 * 
	 * @param kind
	 *            The kind of critical pair
	 * @param rule
	 *            The rule which is the first part of a critical pair
	 * @throws InvalidAlgorithmException
	 *             Thrown if a algorithm is desired which is not provided.
	 * @return All rules that are critic with the parameter.
	 */
	public Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
	getCriticalSet(int kind, Rule rule)
			throws InvalidAlgorithmException;

	// ****************************************************************************+
	/**
	 * Sets the graph grammar the critical pairs are computed for.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 */
	public void setGrammar(GraGra grammar);
	
	/**
	 * Returns the grammar the critical pairs a computed for.
	 * 
	 * @return The graph grammar.
	 */
	public GraGra getGrammar();

	public void setComputeAsymetrically(boolean b);
	
	public void setRules(List<Rule> ruleList);
	
	public void setRules(final List<Rule> ruleList, final List<Rule> ruleList2);
	
	public List<Rule> getRules();
	
	public List<Rule> getRules2();
	
	public void restoreExprReplacedByVarInApplConds();
	
	public void setMorphCompletionStrategy(MorphCompletionStrategy strat);
	
	public MorphCompletionStrategy getMorphCompletionStrategy();
	
	
	// ****************************************************************************+
	/**
	 * Initials all containers. So there are at least empty objects as
	 * containers.
	 */
	public void initAllContainer();

	/** Clears all container. */
	public void clear();

	/**
	 * Returns CriticalPair.CONFLICT or CriticalPair.DEPENDENCY constant.
	 */
	public int getKindOfConflict();

	/**
	 * Adds a PairEventListener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addPairEventListener(ParserEventListener l);

	public LayerFunction getLayer();

	public boolean isAlive();

	public void stop();

	public void setStop(boolean b);

	public boolean wasStopped();

	public boolean isEmpty();

	public void enableUseHostGraph(boolean enable, Graph g);

	public boolean useHostGraphEnabled();

	public boolean isComputed();
	
	public ExcludePair getActiveExcludePair();
	
}

// End of PairContainer.java
/*
 * $Log: PairContainer.java,v $
 * Revision 1.15  2010/12/16 17:32:14  olga
 * tuning
 *
 * Revision 1.14  2010/08/12 14:53:28  olga
 * tuning
 *
 * Revision 1.13  2010/04/12 16:21:10  olga
 * tuning
 *
 * Revision 1.12  2010/04/12 14:40:45  olga
 * Critical pairs table - extended
 *
 * Revision 1.11  2010/03/08 15:46:42  olga
 * code optimizing
 *
 * Revision 1.10  2008/09/22 10:02:38  olga
 * tests only
 *
 * Revision 1.9  2008/09/11 09:22:26  olga
 * Some changes in CPA: new computing of conflicts after an option changed,
 * Graph layout of overlapping graphs
 *
 * Revision 1.8  2008/04/07 09:36:51  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.7  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.6  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2006/01/16 09:45:08 olga tests
 * 
 * Revision 1.4 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.3 2005/09/27 11:13:25 olga CPs ...
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.5 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.4 2004/06/14 12:34:19 olga CP Analyse and Transformation
 * 
 * Revision 1.3 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/01/20 10:46:29 komm new events for new GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:42:51 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.4 2001/01/28 13:14:56 shultzke API fertig
 * 
 * Revision 1.1.2.3 2000/12/12 13:27:44 shultzke erste Versuche kritische Paare
 * mit XML abzuspeichern
 * 
 * Revision 1.1.2.2 2000/11/01 12:19:22 shultzke erste Regelanwendung im parser
 * CVs: ----------------------------------------------------------------------
 * 
 * Revision 1.1.2.1 2000/07/16 18:52:30 shultzke *** empty log message ***
 * 
 * Revision 1.1 2000/07/09 17:12:58 shultzke grob die GUI eingebunden
 * 
 */

package agg.parser;

import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.util.Pair;


// ---------------------------------------------------------------------------+
/**
 * This class provides a parser which needs critical pair analysis. This
 * critical pair must be <CODE>LayeredExcludePair</CODE>. So objects has to
 * be instantiated with <CODE>LayeredExcludePairContainer</CODE>.
 * Additionally there is a layer function for more optimization.
 * 
 * @see ParserFactory#createParser createParser(...)
 * @author $Author: olga $ Parser Group
 * @version $Id: LayeredSimpleExcludeParser.java,v 1.6 2007/06/13 08:32:56 olga
 *          Exp $
 */
public class LayeredSimpleExcludeParser extends LayeredExcludeParser {

	/**
	 * Creates a new parser.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 * @param hostGraph
	 *            The host graph.
	 * @param stopGraph
	 *            The stop graph.
	 * @param excludeContainer
	 *            The critical pairs.
	 * @param layer
	 *            The layer function.
	 * @deprecated
	 */
	public LayeredSimpleExcludeParser(GraGra grammar, Graph hostGraph,
			Graph stopGraph, LayeredExcludePairContainer excludeContainer,
			LayerFunction layer) {
		super(grammar, hostGraph, stopGraph, excludeContainer, layer);
	}

	/**
	 * Creates a new parser.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 * @param hostGraph
	 *            The host graph.
	 * @param stopGraph
	 *            The stop graph.
	 * @param excludeContainer
	 *            The critical pairs.
	 * @param layer
	 *            The rule layer.
	 */
	public LayeredSimpleExcludeParser(GraGra grammar, Graph hostGraph,
			Graph stopGraph, LayeredExcludePairContainer excludeContainer,
			RuleLayer layer) {
		super(grammar, hostGraph, stopGraph, excludeContainer, layer);
	}

	// -----------------------------------------------------------------------+
	/**
	 * Starts the parser.
	 * 
	 * @return true if the graph can be parsed.
	 */
	@SuppressWarnings("rawtypes")
	public boolean parse() {
//		System.out.println("Starting layered simple exclude parser ...");
		fireParserEvent(new ParserMessageEvent(this,
				"Starting layered simple exclude parser ..."));
		
		boolean result = true;
		Hashtable<Integer, HashSet<Rule>> invertedRuleLayer = this.layer.invertLayer(); // layer.getRuleLayer());
		OrderedSet<Integer> ruleLayer = new OrderedSet<Integer>(new IntComparator<Integer>());
		for (Enumeration<Integer> en = invertedRuleLayer.keys(); en.hasMoreElements();)
			ruleLayer.add(en.nextElement());
		
		Integer currentLayer = this.layer.getStartLayer();
		int i=0;
		
		/* haelt alle Matche, die kritisch sind, damit nicht an einer Stelle
		 * immer wieder angesetzt wird
		 */
		RuleInstances eri = new RuleInstances();
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflictFree = null;
		try {
			conflictFree = this.pairContainer
					.getContainer(CriticalPair.CONFLICTFREE);
		} catch (InvalidAlgorithmException iae) {
			fireParserEvent(new ParserErrorEvent(iae, "ERROR: "
					+ iae.getMessage()));
			return false;
		}
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> exclude = null;
		try {
			exclude = this.pairContainer.getContainer(CriticalPair.EXCLUDE);
		} catch (InvalidAlgorithmException iae) {
			fireParserEvent(new ParserErrorEvent(iae, "ERROR: "
					+ iae.getMessage()));
			return false;
		}
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflictFreeLight = null;
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> excludeLight = null;
		excludeLight = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();
		conflictFreeLight = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();
		makeLightContainer(exclude, excludeLight);
		makeLightContainer(conflictFree, conflictFreeLight);
		/*
		 * makeLightContainer kann nur die Elemente filtern, in denen alle teile
		 * false liefern. Mischformen fallen durch
		 */
		for (Enumeration<Rule> keys = conflictFreeLight.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			if (excludeLight.containsKey(key)) {
				conflictFreeLight.remove(key);
			}
		}
		Hashtable<Integer, Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>> layeredExcludeLight 
		= new Hashtable<Integer, Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>>();
		Hashtable<Integer, Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>> layeredConflictFreeLight 
		= new Hashtable<Integer, Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>>();
		/*
		 * es gibt ein set von rules fuer einen bestimmten layer ausserdem gibt
		 * es set von confliktfreien regeln aller layer synchronisieren beider
		 * sets. Hashtable Integer layer -> gefilterten confliktfreien Hashtable
		 */
		for (Enumeration<?> en = ruleLayer.elements(); en.hasMoreElements()
				&& !this.stop;) {
			Integer l = (Integer) en.nextElement();
			HashSet lRules = invertedRuleLayer.get(l);
			/* fuer alle regeln eines Layeres */
			for (Iterator<?> en2 = lRules.iterator(); en2.hasNext() && !this.stop;) {
				Rule r = (Rule) en2.next();
				Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> value = conflictFreeLight
						.get(r);
				/* falls die Regel im conflictFreeLight ist */
				if (value != null) {
					/* neue Hashtable aufbauen */
					Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> hashtable = layeredConflictFreeLight
							.get(l);
					if (hashtable == null) {
						hashtable = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();
						layeredConflictFreeLight.put(l, hashtable);
					}
					hashtable.put(r, value);
				} else {
					/* ansosten muss die Regel ja in exclude sein */
					value = excludeLight.get(r);
					/* vertrauen ist gut, kontrolle ist besser */
					if (value != null) {
						/* neue Hashtable aufbauen */
						Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> hashtable = layeredExcludeLight
								.get(l);
						if (hashtable == null) {
							hashtable = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();
							layeredExcludeLight.put(l, hashtable);
						}
						hashtable.put(r, value);
					}
				}
			}
		}

		fireParserEvent(new ParserMessageEvent(this,
				"Layered simple parser initialized"));
		boolean ruleApplied = false;
		while (!this.stop && !getHostGraph().isIsomorphicTo(this.stopGraph) && result
				&& (currentLayer != null)) {
			ruleApplied = false;
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> lFree = layeredConflictFreeLight
					.get(currentLayer);
			Match m = null;
			if (lFree != null)
				m = findMatch(getHostGraph(), lFree.keySet().iterator(), eri);
			if (m != null) {
				if (applyRule(m)) {
					ruleApplied = true;
					try {
						Thread.sleep(this.delay);
					} catch (InterruptedException ex1) {
					}
				}
			}
			if (!ruleApplied) {
				Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
				lExclude = layeredExcludeLight.get(currentLayer);
				Match savedMatch = null;
				if (lExclude != null) {
					fireParserEvent(new ParserMessageEvent(this,
							"Searching for difficult match"));
					m = findMatch(getHostGraph(), lExclude.keySet().iterator(), eri);
					if (m != null) {
						savedMatch = m;
					}
				}
				/*
				 * wenn keine Regel angewendet wurde, dann kann nur noch eine
				 * kritische Regel angewendet werden.
				 */
				if (!ruleApplied && savedMatch != null) {
					/* auf Stack pushen */
					OrdinaryMorphism copyMorph = getHostGraph().isomorphicCopy();
					if (copyMorph != null) {
						fireParserEvent(new ParserMessageEvent(copyMorph, "IsoCopy"));
						eri.add(savedMatch);
						/*
						 * ERI muss nicht kopiert werden, da nur an
						 * Entscheidungsstellen der Match/die Matches gemerkt werden
						 * mssen, die uns m�licherweise auf einen Holzweg fhren. Der
						 * Match in ERI ist eine Stufe tiefer (also nach
						 * Regelanwendung, denn wir l&ouml;schen) nicht mehr
						 * verfgbar. Dadurch kann ein neues ERI erzeugt werden. Auf
						 * dem Stack liegen dann nur die Ableitungen, die uns in
						 * eine Sackgasse gefhrt haben.
						 */
						TripleData tmpTriple = new TripleData(getHostGraph(), eri,
								currentLayer);
						this.stack.push(tmpTriple);
						eri = new RuleInstances();
						/*
						 * Die Regel muss auf den kopierten Graphen mit DEMSELBEN
						 * kopierten Match angewendet werden.
						 */
						setHostGraph(copyMorph.getImage());
						OrdinaryMorphism tmpMorph = savedMatch.compose(copyMorph);
						Match n = tmpMorph.makeMatch(savedMatch.getRule());
						n.setCompletionStrategy((MorphCompletionStrategy) this.grammar
								.getMorphismCompletionStrategy().clone(), true);
	
						boolean notFound = false;
						while (!n.isValid() && !notFound) {
							if (!n.nextCompletion())
								notFound = true;
						}
						if (!notFound) {
							if (applyRule(n)) {
								ruleApplied = true;
								try {
									Thread.sleep(this.delay);
								} catch (InterruptedException ex1) {
								}
							}
						}
					}
				}
				if (!ruleApplied) {
					/* naechster Layer soll verwendet werden. */
					i++;
					boolean nextLayerExists = true;
					if (i < ruleLayer.size()) {
						currentLayer = ruleLayer.get(i);
					}
					else {
						nextLayerExists = false;
					}
					if (!nextLayerExists) {
						try {
							TripleData tmpTriple = (TripleData) this.stack.pop();
							/* backtrack */
							setHostGraph(tmpTriple.getHostGraph());
							eri = tmpTriple.getRuleInstance();
							currentLayer = tmpTriple.getLayer();
						} catch (EmptyStackException ioe) {
							/* Stack ist leer */
							fireParserEvent(new ParserErrorEvent(this,
									"ERROR: This graph is not part of the language"));
							result = false;
						}
					}
				}
			}
		}
		while (!this.stack.empty()) {
			try {
				fireParserEvent(new ParserMessageEvent(this, "Cleaning stack."));
				TripleData tmpTriple = (TripleData) this.stack.pop();
				Graph g = tmpTriple.getHostGraph();
				g.dispose();
			} catch (EmptyStackException ioe) {
			}
		}
		this.correct = result;
		fireParserEvent(new ParserMessageEvent(this,
				"Stopping parser. Result is " + result + "."));
		
		return result;
	}

}

/*
 * End of Parser.java
 * ----------------------------------------------------------------------------
 * $Log: LayeredSimpleExcludeParser.java,v $
 * Revision 1.13  2010/08/18 09:26:52  olga
 * tuning
 *
 * Revision 1.12  2010/06/09 10:56:05  olga
 * tuning
 *
 * Revision 1.11  2009/07/08 16:22:01  olga
 * Multiplicity bug fixed;
 * ARS development
 *
 * Revision 1.10  2008/05/07 08:37:55  olga
 * Applicability of Rule Sequences with NACs
 *
 * Revision 1.9  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.8  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/10 13:05:40  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/06/13 08:32:56
 * olga Update: V161
 * 
 * Revision 1.5 2007/04/11 10:03:36 olga Undo, Redo tuning, Simple Parser- bug
 * fixed
 * 
 * Revision 1.4 2007/03/28 10:00:51 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.3 2006/12/13 13:32:59 enrico reimplemented code
 * 
 * Revision 1.2 2006/11/09 10:31:05 olga Matching error fixed
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.2 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.4 2001/05/31 11:57:56 olga Zusaetzliche Parser Meldungen fuer GUI
 * eingefuegt.
 * 
 * Revision 1.3 2001/05/14 12:03:01 olga Zusaetzliche Parser Events Aufrufe
 * eingebaut, um bessere Kommunikation mit GUI Status Anzeige zu ermoeglichen.
 * 
 * Revision 1.2 2001/03/08 10:44:55 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.4 2001/01/28 13:14:56 shultzke API fertig
 * 
 * Revision 1.1.2.3 2001/01/02 12:28:59 shultzke Alle Optionen angebunden
 * 
 * Revision 1.1.2.2 2001/01/01 21:24:33 shultzke alle Parser fertig inklusive
 * Layout
 * 
 * Revision 1.1.2.1 2000/12/26 10:00:05 shultzke Layered Parser hinzugefuegt
 * 
 */

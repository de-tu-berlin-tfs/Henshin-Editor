// This class belongs to the following package:
package agg.parser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.util.Pair;

//****************************************************************************+
/**
 * This class provides a parser which needs critical pairs. The critical pair
 * must be <code>ExcludePair</code>. So objects has to be instanciated with
 * <code>ExcludePairContainer</code>. To be independent of a grammar it is
 * necessary to instanciate an object with a host graph and stop graph
 * seperately.
 * 
 * @author $Author: olga $
 * @version $Id: SimpleExcludeParser.java,v 1.11 2010/08/18 09:26:52 olga Exp $
 */
public class SimpleExcludeParser extends ExcludeParser {

	/**
	 * Creates a new parser that is a little bit simpler than a <CODE>ExcludeParser</CODE>.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 * @param hostGraph
	 *            The host graph.
	 * @param stopGraph
	 *            The stop graph.
	 * @param excludeContainer
	 *            The critical pairs.
	 */
	public SimpleExcludeParser(GraGra grammar, Graph hostGraph,
			Graph stopGraph, ExcludePairContainer excludeContainer) {
		super(grammar, hostGraph, stopGraph, excludeContainer);
	}

	// ****************************************************************************+
	/**
	 * Starts the parser.
	 * 
	 * @return true if the graph can be parsed.
	 */
	@SuppressWarnings("rawtypes")
	public boolean parse() {
//		System.out.println("Starting simple exclude parser ...");
		this.correct = true;
		fireParserEvent(new ParserMessageEvent(this,
				"Starting simple exclude parser ..."));

		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflictFree = null;
		if (this.stop)
			return false;
		try {
			conflictFree = this.pairContainer
					.getContainer(CriticalPair.CONFLICTFREE);
		} catch (InvalidAlgorithmException iae) {
			fireParserEvent(new ParserErrorEvent(iae, "ERROR: "
					+ iae.getMessage()));
			return false;
		}
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> exclude = null;
		if (this.stop)
			return false;
		try {
			exclude = this.pairContainer.getContainer(CriticalPair.EXCLUDE);
		} catch (InvalidAlgorithmException iae) {
			fireParserEvent(new ParserErrorEvent(iae, "ERROR: "
					+ iae.getMessage()));
			return false;
		}
		if (this.stop)
			return false;
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> conflictFreeLight = null;
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> excludeLight = null;
		excludeLight = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();
		conflictFreeLight = new Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>>();

		if (this.stop)
			return false;

		makeLightContainer(exclude, excludeLight);

		if (this.stop)
			return false;

		makeLightContainer(conflictFree, conflictFreeLight);

		if (this.stop)
			return false;
		for (Enumeration<Rule> keys = conflictFreeLight.keys(); keys
				.hasMoreElements();) {
			Object key = keys.nextElement();
			if (excludeLight.containsKey(key)) {
				conflictFreeLight.remove(key);
			}
		}
		/*
		 * haelt alle Matche, die kritisch sind, damit nicht an einer Stelle
		 * immer wieder angesetzt wird
		 */
		RuleInstances eri = new RuleInstances();

		fireParserEvent(new ParserMessageEvent(this, "Parser initialized"));
		if (this.stop)
			return false;
		boolean ruleApplied = false;
		while (!this.stop && !this.graph.isIsomorphicTo(this.stopGraph) && this.correct) {
			ruleApplied = false;

			/* zuerst sollen alle konfliktfreien Regeln probiert werden. */
			for (Enumeration<Rule> keys = conflictFreeLight.keys(); keys
					.hasMoreElements()
					&& !ruleApplied;) {
				Rule r = keys.nextElement();

//				Report.println("versuche konfliktfreie Regel " + r.getName(),
//						Report.PARSER);

				fireParserEvent(new ParserMessageEvent(this,
						"Searching for easy match"));
				Match m = BaseFactory.theFactory().createMatch(r,
						getHostGraph());
				m.setCompletionStrategy((MorphCompletionStrategy) this.grammar
						.getMorphismCompletionStrategy().clone(), true);
				// m.getCompletionStrategy().showProperties();
				while (!this.stop && !ruleApplied && m.nextCompletion()) {
					// TODO ATTRIBUTE CHECKEN machen hier Inputparameter Sinn??
					if (m.isValid()) {
						if (applyRule(m)) {
							ruleApplied = true;
							try {
								Thread.sleep(this.delay);
							} catch (InterruptedException ex1) {
							}
						}
					}
				}
			}
			/* Die konfliktfreien Regeln sind abgearbeitet */

			/* Die Excluderegeln muessen ueberprueft werden */
			if (!this.stop && !ruleApplied) {
				/*
				 * Zuerst wird ein beliebiger Ansatz einer Regel gesucht.
				 */
				for (Enumeration<Rule> keys = excludeLight.keys(); keys
						.hasMoreElements()
						&& !ruleApplied;) {
					Rule r = keys.nextElement();
					fireParserEvent(new ParserMessageEvent(this,
							"Searching for difficult match"));
					Match m = BaseFactory.theFactory().createMatch(r,
							getHostGraph());
					m.setCompletionStrategy((MorphCompletionStrategy) this.grammar
							.getMorphismCompletionStrategy().clone(), true);

					boolean found = false;
					while (!found && m.nextCompletion()) {
						if (!eri.isIn(m) && m.isValid())
							found = true;
					}
					if (!this.stop && found) {
						OrdinaryMorphism copyMorph = getHostGraph().isomorphicCopy();
						if (copyMorph != null) {
							fireParserEvent(new ParserMessageEvent(copyMorph,
									"IsoCopy"));
							eri.add(m);
							/*
							 * ERI muss nicht kopiert werden, da nur an
							 * Entscheidungsstellen der Match/die Matches gemerkt
							 * werden mssen, die uns m�licherweise auf einen Holzweg
							 * fhren. Der Match in ERI ist eine Stufe tiefer (also
							 * nach Regelanwendung, denn wir l&ouml;schen) nicht
							 * mehr verfgbar. Dadurch kann ein neues ERI erzeugt
							 * werden. Auf dem Stack liegen dann nur die
							 * Ableitungen, die uns in eine Sackgasse gefhrt haben.
							 */
							Pair<Graph, RuleInstances> tmpPair = new Pair<Graph, RuleInstances>(
									getHostGraph(), eri);
							this.stack.push(tmpPair);
							eri = new RuleInstances();
							/*
							 * Die Regel muss auf den kopierten Graphen mit
							 * DEMSELBEN kopierten Match angewendet werden.
							 */
							setHostGraph(copyMorph.getImage());
							OrdinaryMorphism tmpMorph = m.compose(copyMorph);
							Match n = tmpMorph.makeMatch(m.getRule());
							n.setCompletionStrategy(
									(MorphCompletionStrategy) this.grammar
											.getMorphismCompletionStrategy()
											.clone(), true);
	
							boolean notFound = false;
							while (!this.stop && !n.isValid() && !notFound) {
								if (!n.nextCompletion())
									notFound = true;
							}
							if (!this.stop && !notFound) {
								if (applyRule(n)) {
									ruleApplied = true;
									try {
										Thread.sleep(this.delay);
									} catch (InterruptedException ex1) {
									}
								}
							}
						}
					} else {
						BaseFactory.theFactory().destroyMatch(m);
						m = null;
					}
				}
				if (!this.stop && !ruleApplied) {
					try {
						Pair<?,?> tmpPair = (Pair) this.stack.pop();
						/* backtrack */
						setHostGraph((Graph)tmpPair.first);
						eri = (RuleInstances)tmpPair.second;
					} catch (Exception ioe) {
						/* Stack ist leer */
						fireParserEvent(new ParserErrorEvent(this,
								"ERROR: This graph is not part of the language"));
						this.correct = false;
					}
				}
			}
		}

		/* Fertig mit den Excluderegeln */
		while (!this.stack.empty()) {
			try {
				fireParserEvent(new ParserMessageEvent(this, "Cleaning stack."));
				Pair<?,?> tmpPair = (Pair) this.stack.pop();
				
				Graph g = (Graph)tmpPair.first;
				BaseFactory.theFactory().destroyGraph(g);
				tmpPair.second = null;
			} catch (Exception ioe) {
			}
		}
		fireParserEvent(new ParserMessageEvent(this,
				"Stopping parser. Result is " + this.correct + "."));
		return this.correct;
	}

}

// End of ExcludeParser.java
/*
 * $Log: SimpleExcludeParser.java,v $
 * Revision 1.11  2010/08/18 09:26:52  olga
 * tuning
 *
 * Revision 1.10  2010/06/09 10:56:07  olga
 * tuning
 *
 * Revision 1.9  2008/04/07 09:36:51  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.8  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/07/02 08:27:33 olga Help
 * docu update, Source tuning
 * 
 * Revision 1.5 2007/06/13 08:32:58 olga Update: V161
 * 
 * Revision 1.4 2007/04/11 10:03:36 olga Undo, Redo tuning, Simple Parser- bug
 * fixed
 * 
 * Revision 1.3 2007/03/28 10:00:57 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.2 2006/11/09 10:31:05 olga Matching error fixed
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.5 2001/06/13 16:49:36 olga Parser Classen Optimierung.
 * 
 * Revision 1.4 2001/05/31 11:57:57 olga Zusaetzliche Parser Meldungen fuer GUI
 * eingefuegt.
 * 
 * Revision 1.3 2001/05/14 12:03:02 olga Zusaetzliche Parser Events Aufrufe
 * eingebaut, um bessere Kommunikation mit GUI Status Anzeige zu ermoeglichen.
 * 
 * Revision 1.2 2001/03/08 10:44:57 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.5 2001/01/28 13:14:58 shultzke API fertig
 * 
 * Revision 1.1.2.4 2001/01/03 09:45:01 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.1.2.3 2001/01/02 12:29:01 shultzke Alle Optionen angebunden
 * 
 * Revision 1.1.2.2 2000/12/26 10:00:06 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.1 2000/12/04 12:26:48 shultzke drei parser stehen zur
 * verfuegung
 * 
 */

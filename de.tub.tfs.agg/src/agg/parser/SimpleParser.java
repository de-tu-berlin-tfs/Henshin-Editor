package agg.parser;

import java.util.EmptyStackException;
import java.util.Stack;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.OrdinaryMorphism;
import agg.util.Pair;


// ---------------------------------------------------------------------------+
/**
 * This class provides a parser which works without critical pair analysis. So a
 * simple backtracking algorithm is implemented.
 * 
 * @see ParserFactory#createParser createParser(...)
 * @author $Author: olga $ Parser Group
 * @version $Id: SimpleParser.java,v 1.10 2010/08/18 09:26:52 olga Exp $
 */
public class SimpleParser extends AbstractParser implements Runnable {

	protected boolean stop;

	protected boolean correct;

	/**
	 * Created a new parser.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 * @param hostGraph
	 *            The host graph.
	 * @param stopGraph
	 *            The stop graph.
	 */
	public SimpleParser(GraGra grammar, Graph hostGraph, Graph stopGraph) {
		super(grammar, hostGraph, stopGraph, null);
		this.stop = false;
	}

	/**
	 * Usually this method is invoked by the start method from the class <CODE>Thread</CODE>.
	 * This method starts the parser.
	 */
	public void run() {
		fireParserEvent(new ParserMessageEvent(this,
				"Starting parser. Please wait ..."));
		parse();
		if (this.stop)
			fireParserEvent(new ParserMessageEvent(this, "Stopping parser."));
		else
			fireParserEvent(new ParserMessageEvent(this,
					"Finishing parser. Result is " + this.correct + "."));
	}

	/**
	 * Stops the running.
	 */
	public void stop() {
		this.stop = true;
	}

	public boolean wasStopped() {
		return this.stop;
	}

	// -----------------------------------------------------------------------+
	/**
	 * Starts the parser.
	 * 
	 * @return true if the graph can be parsed.
	 */
	public boolean parse() {
//		System.out.println("Starting simple parser ...");
		Stack<Pair<Graph, RuleInstances>> stack = new Stack<Pair<Graph, RuleInstances>>();
		this.correct = true;
		/*
		 * haelt alle Matche, die kritisch sind, damit nicht an einer Stelle
		 * immer wieder angesetzt wird
		 */
		RuleInstances eri = new RuleInstances();
		fireParserEvent(new ParserMessageEvent(this,
				"Starting simple parser ..."));
		boolean ruleApplied = false;
		while (!this.stop && !getHostGraph().isIsomorphicTo(this.stopGraph) && this.correct) {
			fireParserEvent(new ParserMessageEvent(this, "Searching for match!"));
			Match m = findMatch(getHostGraph(), this.grammar.getRuleIterator(), eri);
			if (m != null) {
				ruleApplied = false;
				/* auf Stack pushen */
				OrdinaryMorphism copyMorph = getHostGraph().isomorphicCopy();
				if (copyMorph != null) {
					/*
					 * sendet den copierten Graphen durch den Morphismus damit das
					 * Layout upgedated werden kann
					 */
					fireParserEvent(new ParserMessageEvent(copyMorph, "IsoCopy"));
					eri.add(m);
					/*
					 * ERI muss nicht kopiert werden, da nur an Entscheidungsstellen
					 * der Match/die Matches gemerkt werden mssen, die uns
					 * moeglicherweise auf einen Holzweg fhren. Der Match in ERI ist
					 * eine Stufe tiefer (also nach Regelanwendung, denn wir
					 * loeschen) nicht mehr verfuegbar. Dadurch kann ein neues ERI
					 * erzeugt werden. Auf dem Stack liegen dann nur die
					 * Ableitungen, die uns in eine Sackgasse gefuehrt haben.
					 */
					Pair<Graph, RuleInstances> tmpPair = new Pair<Graph, RuleInstances>(
							getHostGraph(), eri);
					stack.push(tmpPair);
					eri = new RuleInstances();
					/*
					 * Die Regel muss auf den kopierten Graphen mit DEMSELBEN
					 * kopierten Match angewendet werden.
					 */
					setHostGraph(copyMorph.getImage());
					OrdinaryMorphism tmpMorph = m.compose(copyMorph);
					Match n = tmpMorph.makeMatch(m.getRule());
					n.setCompletionStrategy((MorphCompletionStrategy) this.grammar
							.getMorphismCompletionStrategy().clone(), true);
					// n.getCompletionStrategy().showProperties();
	
					boolean found = true;
					while (!n.isValid() && found) {
						if (!n.nextCompletion())
							found = false;
					}
					if (found) {
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
			if (m == null || !ruleApplied) {
				/* backtracking, wenn moeglich poppen */
				try {
					Pair<Graph, RuleInstances> tmpPair = stack.pop();
					/* backtrack */
					setHostGraph(tmpPair.first);
					eri = tmpPair.second;
				} catch (EmptyStackException ioe) {
					/* Stack ist leer */
					fireParserEvent(new ParserErrorEvent(this,
							"ERROR: This graph is not part of the language"));
					this.correct = false;
				}
			}
		}
		while (!stack.empty()) {
			try {
				fireParserEvent(new ParserMessageEvent(this, "Cleaning stack."));
				Pair<Graph, RuleInstances> tmpPair = stack.pop();
				Graph g = tmpPair.first;
				BaseFactory.theFactory().destroyGraph(g);
				tmpPair.second.finalize();
				// tmpPair.second = null;
			} catch (EmptyStackException ioe) {
			}
		}
		fireParserEvent(new ParserMessageEvent(this,
				"Stopping parser. Result is " + this.correct + "."));
		return this.correct;
	}
}

/*
 * End of Parser.java
 * ----------------------------------------------------------------------------
 * $Log: SimpleParser.java,v $
 * Revision 1.10  2010/08/18 09:26:52  olga
 * tuning
 *
 * Revision 1.9  2010/06/09 10:56:06  olga
 * tuning
 *
 * Revision 1.8  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.7  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.6  2007/09/10 13:05:42  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2007/06/13 08:32:56 olga Update:
 * V161
 * 
 * Revision 1.4 2007/04/11 10:03:36 olga Undo, Redo tuning, Simple Parser- bug
 * fixed
 * 
 * Revision 1.3 2007/03/28 10:00:52 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.2 2006/11/09 10:31:05 olga Matching error fixed
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.3 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.6 2001/06/13 16:49:37 olga Parser Classen Optimierung.
 * 
 * Revision 1.5 2001/05/31 11:57:57 olga Zusaetzliche Parser Meldungen fuer GUI
 * eingefuegt.
 * 
 * Revision 1.4 2001/05/14 12:03:02 olga Zusaetzliche Parser Events Aufrufe
 * eingebaut, um bessere Kommunikation mit GUI Status Anzeige zu ermoeglichen.
 * 
 * Revision 1.3 2001/04/11 14:59:20 olga Stop Method eingefuegt.
 * 
 * Revision 1.2 2001/03/08 10:44:57 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.6 2001/01/28 13:14:59 shultzke API fertig
 * 
 * Revision 1.1.2.5 2001/01/01 21:24:33 shultzke alle Parser fertig inklusive
 * Layout
 * 
 * Revision 1.1.2.4 2000/12/26 10:00:06 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.3 2000/12/04 12:26:49 shultzke drei parser stehen zur
 * verfuegung
 * 
 * Revision 1.1.2.2 2000/11/28 09:54:50 shultzke stack aufgeraeumt nach parsing
 * 
 * Revision 1.1.2.1 2000/11/27 13:16:46 shultzke referenzparser SimpleParser
 * implementiert
 * 
 */

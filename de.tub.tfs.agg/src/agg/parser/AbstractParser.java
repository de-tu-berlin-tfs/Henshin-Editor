package agg.parser;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Morphism;
import agg.xt_basis.Rule;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;

// ---------------------------------------------------------------------------+
/**
 * This parser eats graphs which are created by AGG. A parser needs a host graph
 * and a stop graph. The graph grammar for parsing must contain reducing rules.
 * This abstract parser provides some convenient methods for parsing.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: AbstractParser.java,v 1.15 2010/08/18 09:26:52 olga Exp $
 */
public abstract class AbstractParser implements Parser {

	/**
	 * The grammar which can recognize a graph
	 */
	protected GraGra grammar;

	/**
	 * The Graph which will be parsed
	 */
	protected Graph graph;

	/**
	 * The graph which stops the algorithm
	 */
	protected Graph stopGraph;

	/**
	 * The set of critical pairs
	 */
	protected PairContainer pairContainer;

	/**
	 * All listerner which want to receive events
	 */
	protected Vector<ParserEventListener> listener;

	protected int delay;

	/**
	 * Creates a new abstract parser. This parser stores all the basic
	 * information like host graph, stop graph, critical pairs and graph
	 * grammar.
	 * 
	 * @param grammar
	 *            The graph grammar.
	 * @param hostGraph
	 *            The host graph.
	 * @param stopGraph
	 *            The stop graph.
	 * @param pairContainer
	 *            The container of the critical pairs.
	 */
	public AbstractParser(GraGra grammar, Graph hostGraph, Graph stopGraph,
			PairContainer pairContainer) {
		this.listener = new Vector<ParserEventListener>();
		setHostGraph(hostGraph);
		setStopGraph(stopGraph);
		setGrammar(grammar);
		setCriticalPairs(pairContainer);
	}

	/**
	 * Sets the host graph for the parser.
	 * 
	 * @param hostGraph
	 *            The host graph.
	 */
	public void setHostGraph(Graph hostGraph) {
		this.graph = hostGraph;
	}

	/**
	 * Sets the stop graph for the parser.
	 * 
	 * @param stopGraph
	 *            The stop graph.
	 */
	public void setStopGraph(Graph stopGraph) {
		this.stopGraph = stopGraph;
	}

	/**
	 * Sets the critical pairs for the parser.
	 * 
	 * @param pairs
	 *            The critical pairs are holded in a container.
	 */
	public void setCriticalPairs(PairContainer pairs) {
		this.pairContainer = pairs;
	}

	/**
	 * Sets the grammar for the parser. This grammar must contain reducing
	 * rules.
	 * 
	 * @param grammar
	 *            The grammar for the parser.
	 */
	public void setGrammar(GraGra grammar) {
		this.grammar = grammar;
	}

	/**
	 * Returns the host graph from the parser. This method is important to get
	 * the current state of parsing process.
	 * 
	 * @return The current host graph.
	 */
	public Graph getHostGraph() {
		return this.graph;
	}

	/**
	 * Returns the host graph from the parser. This method is important to get
	 * the current state of parsing process.
	 * 
	 * @return The current host graph.
	 */
	public Graph getGraph() {
		return getHostGraph();
	}

	/**
	 * Returns the current stop graph of the parser.
	 * 
	 * @return The stop graph.
	 */
	public Graph getStopGraph() {
		return this.stopGraph;
	}

	// -----------------------------------------------------------------------+
	/**
	 * Starts the parser. The result is true if the parser can parse the graph
	 * 
	 * @return true if the graph can be parsed.
	 */
	public abstract boolean parse();

	/**
	 * Applys a rule on a host graph. As the match provides access as well as to
	 * the rule of the match as to the host graph.
	 * 
	 * @param m
	 *            The match.
	 */
	protected boolean applyRule(Match m) {
		Morphism comatch = null;
		if (m.isValid()) {
//			TestStep s = new TestStep();
			try {
				comatch = StaticStep.execute(m);
				((agg.xt_basis.OrdinaryMorphism) comatch).dispose();
			} catch (TypeException e) {
				fireParserEvent(new ParserErrorEvent(this, "Rule  <"
						+ m.getRule().getName() + ">  cannot be applied"));
				return false;
			}
			fireParserEvent(new ParserMessageEvent(this, "Rule  <"
					+ m.getRule().getName() + ">  is applied"));
			m.dispose();
		} else {
			/*
			 * dieser Fall sollte nie eintreten, da vor dem Aufruf von applyRule
			 * der Match m ueberprueft werden sollte
			 */
			fireParserEvent(new ParserErrorEvent(this, "Rule  <"
					+ m.getRule().getName() + ">  cannot be applied"));
		}
		return true;
	}

	// ----------------------------------------------------------------------+
	/**
	 * Finds a <B>valid</B> match for a set of rules. Additionally there is a
	 * check on <CODE>RuleInstances</CODE>.
	 * 
	 * @param g
	 *            The graph to match into. Usually the host graph.
	 * @param rules
	 *            This enumeration must contain rule objects.
	 * @param eri
	 *            The rule instances.
	 * @return The valid match from a choosen rule into the graph.
	 */
	protected Match findMatch(Graph g, Iterator<?> rules, RuleInstances eri) {
		boolean found = false;
		Match resultMatch = null;
		while (rules.hasNext() && !found) {
			Rule rule = (Rule) rules.next();
			resultMatch = BaseFactory.theFactory().createMatch(rule, g);

			resultMatch.setCompletionStrategy((MorphCompletionStrategy) this.grammar
					.getMorphismCompletionStrategy().clone(), true);

			while (!found && resultMatch.nextCompletion()) {
				if (resultMatch.isValid()) {
					if (eri != null) {
						if (!eri.isIn(resultMatch))
							found = true;
					} else {
						found = true;
					}
				}
			}
			if (!found) {
				BaseFactory.theFactory().destroyMatch(resultMatch);
				resultMatch = null;
			}
		}
		return resultMatch;
	}

	// ----------------------------------------------------------------------+
	/**
	 * Finds a <B>valid</B> match for a set of rules.
	 * 
	 * @see #findMatch(Graph g, Enumeration rules, RuleInstances eri)
	 * @param g
	 *            The graph to match into. Usually the host graph.
	 * @param rules
	 *            This enumeration must contain rule objects.
	 * @return A valid match from a choosen rule into the graph.
	 */
	protected Match findMatch(Graph g, Iterator<Rule> rules) {
		return findMatch(g, rules, null);
	}

	// ----------------------------------------------------------------------+
	/**
	 * Clears some internal stuff.
	 */
	protected void finalize() {
		getHostGraph().dispose();
	}

	// ----------------------------------------------------------------------+
	/**
	 * Parse the methods and attributes of an UML-Diagram.
	 */
	public void parseString() {
	}

	// -----------------------------------------------------------------------+
	/**
	 * Adds a ParserEventListener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addParserEventListener(ParserEventListener l) {
		if (!this.listener.contains(l)) {
			this.listener.addElement(l);
		}
	}

	// -----------------------------------------------------------------------+
	/**
	 * Removes a ParserEventListener
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeParserEventListener(ParserEventListener l) {
		if (this.listener.contains(l))
			this.listener.removeElement(l);
	}

	// ***********************************************************************+
	/**
	 * Sends a event to all its listeners.
	 * 
	 * @param event
	 *            The event which will be sent
	 */
	protected synchronized void fireParserEvent(ParserEvent event) {
		for (int i = 0; i < this.listener.size(); i++) {
			this.listener.elementAt(i).parserEventOccured(event);
		}
	}

	protected void printImageGraph(Morphism m) {
		System.out.println("Image graph of match:  " + m);
		Graph left = m.getOriginal();
		Iterator<?> e = left.getNodesSet().iterator();		
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			GraphObject i = m.getImage(o);
			if (i != null)
				System.out.print(i);
		}
		e = left.getArcsSet().iterator();		
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			GraphObject i = m.getImage(o);
			if (i != null)
				System.out.print(i);
		}
		System.out.println();
	}

	protected void printGraph(Graph g) {		
		System.out.println("Graph of match:  ");
		Iterator<?> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			System.out.print(o);
		}
		e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			System.out.print(o);
		}
		System.out.println();
	}

	public void setDelayAfterApplyRule(int miliseconds) {
		this.delay = miliseconds;
	}

}

/*
 * End of Parser.java
 * ----------------------------------------------------------------------------
 * $Log: AbstractParser.java,v $
 * Revision 1.15  2010/08/18 09:26:52  olga
 * tuning
 *
 * Revision 1.14  2010/06/09 10:56:05  olga
 * tuning
 *
 * Revision 1.13  2010/05/05 16:17:01  olga
 * tuning
 *
 * Revision 1.12  2010/03/04 14:10:47  olga
 * code optimizing
 *
 * Revision 1.11  2010/02/22 15:01:21  olga
 * code optimizing
 *
 * Revision 1.10  2008/11/19 13:04:17  olga
 * Parser tuning
 *
 * Revision 1.9  2008/04/07 09:36:51  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.8  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/10/22 09:03:16  olga
 * First implementation of CPA for grammars with node type inheritance.
 * Only for internal tests.
 *
 * Revision 1.6  2007/10/04 07:44:27  olga
 * Code tuning
 *
 * Revision 1.5  2007/09/24 09:42:38  olga
 * AGG transformation engine tuning
 *
 * Revision 1.4  2007/09/10 13:05:42  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2007/06/13 08:32:56 olga Update:
 * V161
 * 
 * Revision 1.2 2007/04/11 10:03:36 olga Undo, Redo tuning, Simple Parser- bug
 * fixed
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.8 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.7 2004/01/28 17:58:38 olga Errors suche
 * 
 * Revision 1.6 2004/01/22 17:51:18 olga tests
 * 
 * Revision 1.5 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/01/20 12:11:46 olga Tests raus
 * 
 * Revision 1.3 2002/11/11 10:43:25 komm added support for multiplicity check
 * 
 * Revision 1.2 2002/09/19 16:20:15 olga Nur Testausgaben weg.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.3 2001/09/24 16:41:33 olga
 * 
 * Arbeit an LayerFunction und LayeredParser.
 * 
 * Revision 1.2 2001/03/08 10:44:50 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:50 shultzke API fertig
 * 
 * Revision 1.1.2.2 2001/01/01 21:24:31 shultzke alle Parser fertig inklusive
 * Layout
 * 
 * Revision 1.1.2.1 2000/12/04 12:26:46 shultzke drei parser stehen zur
 * verfuegung
 * 
 */

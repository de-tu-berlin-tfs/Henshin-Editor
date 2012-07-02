package agg.parser;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;

// ---------------------------------------------------------------------------+
/**
 * This parser eats graphs which are created by AGG. A parser needs a host graph
 * and a stop graph. The graph grammar for parsing must contain reducing rules.
 * 
 * @see ParserFactory#createParser createParser(...)
 * @author $Author: olga $ Parser Group
 * @version $Id: Parser.java,v 1.3 2007/09/10 13:05:41 olga Exp $
 */
public interface Parser {

	/**
	 * Sets the host graph for the parser.
	 * 
	 * @param hostGraph
	 *            The host graph.
	 */
	public void setHostGraph(Graph hostGraph);

	/**
	 * Sets the stop graph for the parser.
	 * 
	 * @param stopGraph
	 *            The stop graph.
	 */
	public void setStopGraph(Graph stopGraph);

	/**
	 * Sets the critical pairs for the parser.
	 * 
	 * @param pairs
	 *            The critical pairs are holded in a container.
	 */
	public void setCriticalPairs(PairContainer pairs);

	/**
	 * Sets the grammar for the parser. This grammar must contain reducing
	 * rules.
	 * 
	 * @param grammar
	 *            The grammar for the parser.
	 */
	public void setGrammar(GraGra grammar);

	/**
	 * Returns the host graph from the parser. This method is important to get
	 * the current state of parsing process.
	 * 
	 * @return The current host graph.
	 */
	public Graph getHostGraph();

	/**
	 * Returns the current stop graph of the parser.
	 * 
	 * @return The stop graph.
	 */
	public Graph getStopGraph();

	// -----------------------------------------------------------------------+
	/**
	 * Starts the parser. The result is true if the parser can parse the graph
	 * 
	 * @return true if the graph can be parsed.
	 */
	public boolean parse();

	/**
	 * Returns the host graph from the parser. This method is important to get
	 * the current state of parsing process.
	 * 
	 * @return The current host graph.
	 */
	public Graph getGraph();

	// -----------------------------------------------------------------------+
	/**
	 * Register a ParserEventListener.
	 * 
	 * @param l
	 *            The listener
	 */
	public void addParserEventListener(ParserEventListener l);

	// -----------------------------------------------------------------------+
	/**
	 * Removes a ParserEventListener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeParserEventListener(ParserEventListener l);

	public void setDelayAfterApplyRule(int miliseconds);
}

/*
 * End of Parser.java
 * ----------------------------------------------------------------------------
 * $Log: Parser.java,v $
 * Revision 1.3  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/06/13 08:32:56 olga Update: V161
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:42:52 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:56 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/11/01 12:19:22 shultzke erste Regelanwendung im parser
 * CVs: ----------------------------------------------------------------------
 * 
 * Revision 1.1 2000/06/13 08:57:28 shultzke Initial version, very alpha
 * 
 * Revision 1.6 1999/10/18 14:27:25 shultzke getGraph inzugefuegt
 * 
 * Revision 1.5 1999/09/26 13:50:56 shultzke Parser fuer Attribute erstellt
 * 
 * Revision 1.4 1999/09/14 10:52:32 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.3 1999/06/30 07:45:48 shultzke added event classes and changed
 * some method arguments
 * 
 * Revision 1.2 1999/06/10 10:01:23 shultzke void parse -> boolean parse
 * 
 * Revision 1.1 1999/06/10 09:55:37 shultzke added 'package ...' and Parser.java
 */

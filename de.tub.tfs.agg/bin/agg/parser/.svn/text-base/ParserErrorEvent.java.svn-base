package agg.parser;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.AttrInstance;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;

//****************************************************************************+
/**
 * This event is used if the parser has to handle any error. Typically an error
 * occurs if a graph cannot be parsed.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: ParserErrorEvent.java,v 1.6 2010/09/23 08:25:00 olga Exp $
 */
public class ParserErrorEvent extends ParserEvent {

	/**
	 * The graph.
	 * 
	 * @serial Events are serializable so this attribute.
	 */
	private Graph errorGraph;

	/**
	 * This constructor is inherited from <CODE>ParserEvent</CODE>.
	 * 
	 * @param source
	 *            The source of the event.
	 */
	public ParserErrorEvent(Object source) {
		this(source, "");
	}

	/**
	 * Creates a new event with a error message.
	 * 
	 * @param source
	 *            The source of the event.
	 * @param _message
	 *            The error message.
	 */
	public ParserErrorEvent(Object source, String _message) {
		super(source);
		setMessage(_message);
		if (source instanceof Graph)
			this.errorGraph = (Graph) source;
		else
			this.errorGraph = null;
	}

	/**
	 * Returns the error text.
	 * 
	 * @return The error message.
	 */
	public String getErrorString() {
		return getMessage();
	}

	/**
	 * If a graph is the source of this event this part of the graph is returned
	 * which causes the error.
	 * 
	 * @return A set of graph object.
	 */
	public Enumeration<Object> getErrorGraph() {
		// Da AGG zur Zeit noch nicht das Matchen von Variablen auf Variablen
		// unterstuetzt, gibt es noch keine R&uuml;ckgabe.
		// Es war vereinbart eine ID zu verwenden.
		Vector<Object> graphElements = new Vector<Object>();
		if (this.errorGraph != null) {
			for (Iterator<Node> elements = this.errorGraph.getNodesSet().iterator(); elements
					.hasNext();) {
				GraphObject grob = elements.next();
				AttrInstance ai = grob.getAttribute();
				graphElements.addElement(ai.getValueAt("id"));
			}
			for (Iterator<Arc> elements = this.errorGraph.getArcsSet().iterator(); elements
			.hasNext();) {
		GraphObject grob = elements.next();
		AttrInstance ai = grob.getAttribute();
		graphElements.addElement(ai.getValueAt("id"));
	}
		}
		return graphElements.elements();
	}
}

/*
 * End of ParserErrorEvent.java
 * ---------------------------------------------------------------------- $Log:
 * ParserErrorEvent.java,v $ Revision 1.1 2005/08/25 11:56:57 enrico *** empty
 * log message ***
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
 * Revision 1.1.2.1 2001/01/28 13:14:57 shultzke API fertig
 * 
 * Revision 1.1 2000/06/13 08:57:31 shultzke Initial version, very alpha
 * 
 * Revision 1.8 1999/10/14 13:41:50 shultzke getErrorGraph implementiert
 * 
 * Revision 1.7 1999/09/14 10:52:33 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.6 1999/09/08 17:36:23 shultzke Check fuer 3a implementiert und
 * etwas getestet
 * 
 * Revision 1.5 1999/07/20 19:29:47 shultzke es wurden nur fuer javadoc einige
 * tags hizugfuegt
 * 
 * Revision 1.4 1999/07/20 10:04:53 shultzke diese klassen sind nicht mehr
 * abstrakt
 * 
 * Revision 1.3 1999/07/11 09:22:42 shultzke *** empty log message ***
 * 
 * Revision 1.2 1999/06/30 21:24:10 shultzke added rcs key and tried to check in
 * remote
 */

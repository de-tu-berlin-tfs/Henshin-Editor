package agg.parser;

import java.util.EventObject;
import java.util.Vector;

/**
 * The parser option provides some settings for the parser. So the parser
 * algorithm can be chosen.
 * 
 * @version $Id: ParserOption.java,v 1.3 2010/09/23 08:25:00 olga Exp $
 * @author $Author: olga $
 */
public class ParserOption {

	/**
	 * A simple backtracking.
	 */
	public static final int SIMPLEPARSER = 0;

	/**
	 * Parsing with critical pairs.
	 */
	public static final int EXCLUDEPARSER = 1;

	/**
	 * A simple algorithm with critical pair analysis.
	 */
	public static final int SIMPLEEXCLUDEPARSER = 2;

	private static final int DEFAULTPARSER = EXCLUDEPARSER;

	private int selectedParser;

	private Vector<OptionEventListener> listener;

	private boolean layered;

	/**
	 * Creates new option with default settings.
	 */
	public ParserOption() {
		this.selectedParser = DEFAULTPARSER;
		this.layered = false;
		this.listener = new Vector<OptionEventListener>(2);
	}

	/**
	 * Sets the algorithm for the parser.
	 * 
	 * @param parser
	 *            The algorithm.
	 */
	public void setSelectedParser(int parser) {
		if (parser == EXCLUDEPARSER || parser == SIMPLEPARSER
				|| parser == SIMPLEEXCLUDEPARSER) {
			this.selectedParser = parser;
		}
	}

	/**
	 * Returns the algorithm of the selected parser.
	 * 
	 * @return The algorithm.
	 */
	public int getSelectedParser() {
		return this.selectedParser;
	}

	/**
	 * Returns if layers are used.
	 * 
	 * @return true if layers are used.
	 */
	public boolean layerEnabled() {
		return this.layered;
	}

	/**
	 * Enables if layers are used.
	 * 
	 * @param enable
	 *            true if layers are used.
	 */
	public void enableLayer(boolean enable) {
		if (this.layered != enable) {
			this.layered = enable;
			fireOptionEvent(new EventObject(this));
		}
	}

	/**
	 * Adds an option listener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addOptionListener(OptionEventListener l) {
		if (!this.listener.contains(l))
			this.listener.addElement(l);
	}

	/**
	 * Removes an option listener
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeOptionListener(OptionEventListener l) {
		if (this.listener.contains(l))
			this.listener.removeElement(l);
	}

	/**
	 * Sends a event to all its listeners.
	 * 
	 * @param event
	 *            The event which will be sent
	 */
	private synchronized void fireOptionEvent(EventObject event) {
		for (int i = 0; i < this.listener.size(); i++) {
			this.listener.elementAt(i).optionEventOccurred(event);
		}
	}

}
/*
 * $Log: ParserOption.java,v $
 * Revision 1.3  2010/09/23 08:25:00  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:42  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.3 2001/05/14 12:03:02 olga Zusaetzliche Parser Events Aufrufe
 * eingebaut, um bessere Kommunikation mit GUI Status Anzeige zu ermoeglichen.
 * 
 * Revision 1.2 2001/03/08 10:44:56 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.5 2001/01/28 13:14:58 shultzke API fertig
 * 
 * Revision 1.1.2.4 2000/12/19 12:52:40 shultzke Parseralgorithmusauswahl mit
 * Layer-Checkbutton
 * 
 * Revision 1.1.2.3 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */

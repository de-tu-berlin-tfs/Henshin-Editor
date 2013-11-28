package agg.gui.parser.event;

import java.util.EventObject;

/**
 * This class covers all events which occure when something happens to the GUI
 * option
 * 
 * @version $Id: GUIOptionEvent.java,v 1.3 2010/09/23 08:21:12 olga Exp $
 * @author $Author: olga $
 */
public class GUIOptionEvent extends EventObject {

	/**
	 * specifies the size of the window of critical pairs
	 */
	public final static String CRITICALPAIRWINDOWSIZE = "CriticalPairWindowSize";

	/**
	 * specify what to display when parsing
	 */
	public final static String PARSERDISPLAY = "ParserDisplay";

	/**
	 * for don't get cluttered set how many critical pairs shall be displayed
	 */
	public final static String NUMBEROFCRITICALPAIR = "NumberOfCriticalPair";

	/**
	 * The option of this event.
	 * 
	 * @serial All events a serializable.
	 */
	private String option;

	/**
	 * creates a new event
	 * 
	 * @param source
	 *            it is usefull to use the option object here
	 * @param option
	 *            specify which option has changed
	 * @see agg.gui.parser.event.GUIOptionEvent#CRITICALPAIRWINDOWSIZE
	 * @see agg.gui.parser.event.GUIOptionEvent#PARSERDISPLAY
	 * @see agg.gui.parser.event.GUIOptionEvent#NUMBEROFCRITICALPAIR
	 */
	public GUIOptionEvent(Object source, String option) {
		super(source);
		this.option = option;
	}

	/**
	 * evaluates which option has changed
	 * 
	 * @return the changed option
	 */
	public String getChangedOption() {
		return this.option;
	}

}
/*
 * $Log: GUIOptionEvent.java,v $
 * Revision 1.3  2010/09/23 08:21:12  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:59 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:21 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:03:13 olga er Anbindung gema Stand nach AGG GUI
 * Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist Stand
 * nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:49 shultzke API fertig
 * 
 * Revision 1.1.2.2 2000/11/13 10:59:16 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.1.2.1 2000/08/10 12:22:31 shultzke Einige Klassen wurden
 * umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.1 2000/08/07 10:38:54 shultzke Option erweitert
 * 
 */

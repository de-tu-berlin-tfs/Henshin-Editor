package agg.gui.parser.event;

import java.util.EventObject;

/**
 * Receives all events from the parser gui
 * 
 * @version $Id: ParserGUIEvent.java,v 1.3 2010/09/23 08:21:12 olga Exp $
 * @author $Author: olga $
 */
public class ParserGUIEvent extends EventObject {

	/**
	 * The data of this event.
	 * 
	 * @serial All events are serializable.
	 */
	private Object data;

	int msg = -1;
	
	/**
	 * Manages all events which occure in the parser gui
	 * 
	 * @param source
	 *            source of the event
	 * @param data
	 *            the data which has changed
	 */
	public ParserGUIEvent(Object source, Object data) {
		super(source);
		this.data = data;
	}

	public ParserGUIEvent(Object source, Object data, int msg) {
		super(source);
		this.data = data;
		this.msg = msg;
	}
	
	/**
	 * gets the data which are important for this event
	 * 
	 * @return the data
	 */
	public Object getData() {
		return this.data;
	}
	
	public int getMsg() {
		return this.msg;
	}
}
/*
 * $Log: ParserGUIEvent.java,v $
 * Revision 1.3  2010/09/23 08:21:12  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:51  olga
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
 * Revision 1.1.1.1 2002/07/11 12:17:21 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:03:15 olga er Anbindung gema Stand nach AGG GUI
 * Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist Stand
 * nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:49 shultzke API fertig
 * 
 * Revision 1.1.2.2 2000/11/14 14:01:40 shultzke Kommentare added
 * 
 * Revision 1.1.2.1 2000/08/10 12:22:31 shultzke Einige Klassen wurden
 * umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.2 2000/07/09 17:12:37 shultzke grob die GUI eingebunden
 * 
 */

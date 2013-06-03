package agg.gui.parser.event;

import java.util.EventListener;

/**
 * Listen if a option for the GUI has changed
 * 
 * @version $Id: GUIOptionListener.java,v 1.2 2007/09/10 13:05:50 olga Exp $
 * @author $Author: olga $
 */
public interface GUIOptionListener extends EventListener {

	/**
	 * Reacts if a GUI option has changed
	 * 
	 * @param oe
	 *            the option event
	 */
	public void optionHasChanged(GUIOptionEvent oe);
}
/*
 * $Log: GUIOptionListener.java,v $
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
 * Revision 1.1.1.1 2002/07/11 12:17:21 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:03:14 olga er Anbindung gema Stand nach AGG GUI
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
 * Revision 1.1.2.1 2000/08/07 10:38:55 shultzke Option erweitert
 * 
 */

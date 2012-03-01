package agg.parser;

import java.util.EventListener;

/**
 * A parser sends messages to all listeners.
 * 
 * @see Parser#addParserEventListener Register at the parser to receive events.
 * @author $Author: olga $ Parser Group
 * @version $Id: ParserEventListener.java,v 1.2 2007/09/10 13:05:40 olga Exp $
 */
public interface ParserEventListener extends EventListener {

	/**
	 * Invoked when a parser event occurs.
	 * 
	 * @param p
	 *            The event from the parser.
	 */
	public void parserEventOccured(ParserEvent p);
}

/*
 * End of ParserEventListener.java
 * 
 * $Log: ParserEventListener.java,v $
 * Revision 1.2  2007/09/10 13:05:40  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:42:53 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.1 2001/01/28 13:14:57 shultzke API fertig
 * 
 * Revision 1.1 2000/06/13 08:57:36 shultzke Initial version, very alpha
 * 
 * Revision 1.8 1999/09/14 10:52:36 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.7 1999/06/30 21:24:12 shultzke added rcs key and tried to check in
 * remote
 * 
 * Revision 1.6 1999/06/30 07:46:29 shultzke added event classes and changed
 * some method arguments
 * 
 * Revision 1.5 1999/06/10 09:56:04 shultzke added 'package ...' and Parser.java
 * 
 * Revision 1.4 1999/06/09 07:37:02 gragra Shared Source Working Environment
 * updated
 * 
 * Revision 1.3 1999/06/08 11:54:58 shultzke added all classes except the parser
 * 
 * Revision 1.2 1999/06/08 10:48:43 shultzke little bugfix
 */

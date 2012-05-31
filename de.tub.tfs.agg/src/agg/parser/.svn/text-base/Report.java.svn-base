package agg.parser;

/**
 * This class reports some messages to standard out.
 * 
 * @author $Author: olga $
 * @version $Id: Report.java,v 1.2 2007/09/10 13:05:41 olga Exp $
 */
public class Report {

	private static int tab = 0;

	/**
	 * General switch for showing messages.
	 */
	public static boolean ON = false;

	/**
	 * Switch for method invokation.
	 */
	public static boolean TRACE = false;

	/**
	 * Switch for the computation of exclude rules.
	 */
	public static boolean EXCLUDE = false;

	/**
	 * Switch for attribute operations.
	 */
	public static boolean ATTRIBUTES = false;

	/**
	 * Switch for CP - attribute operations.
	 */
	public static boolean ATTRIBUTES_CP = false;

	/**
	 * Switch for CP - NACs operations.
	 */
	public static boolean NAC_CP = false;

	/**
	 * Switch for overlapping graphs.
	 */
	public static boolean OVERLAPPING = false;

	/**
	 * Switch for general container operation.
	 */
	public static boolean CONTAINER = false;

	/**
	 * Switch for parser messages.
	 */
	public static boolean PARSER = false;

	/**
	 * Switch for layer messages.
	 */
	public static boolean LAYER = false;

	/**
	 * Switch for load messages.
	 */
	public static boolean LADEN = false;

	private Report() {
	}

	/**
	 * Prints a message.
	 * 
	 * @param message
	 *            The messages.
	 */
	public static void println(Object message) {
		if (ON) {
			String theMessage = "";
			if (tab >= 0) {
				for (int i = 0; i < tab; i++) {
					theMessage += " ";
				}
			} else {
				tab = 0;
			}
			theMessage += message;
			System.err.println(theMessage);
		}
	}

	/**
	 * Prints only a message if the topic is true.
	 * 
	 * @param message
	 *            The message.
	 * @param topic
	 *            A topic.
	 */
	public static void println(Object message, boolean topic) {
		if (topic) {
			System.err.println(message);
			// println(message);
		}
	}

	/**
	 * Prints messages from the mehod invokation.
	 * 
	 * @param message
	 *            The message.
	 * @param tabstop
	 *            The number of spaces preceeding the message.
	 */
	public static void trace(Object message, int tabstop) {
		if (tabstop < 0) {
			tab += tabstop;
			println(message, TRACE);
		} else {
			println(message, TRACE);
			tab += tabstop;
		}
	}
}
/*
 * $Log: Report.java,v $
 * Revision 1.2  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.8 2004/03/08 10:19:20 olga tests
 * 
 * Revision 1.7 2004/01/28 17:58:38 olga Errors suche
 * 
 * Revision 1.6 2004/01/22 17:51:18 olga tests
 * 
 * Revision 1.5 2003/02/13 15:08:21 olga NACs bei CPs
 * 
 * Revision 1.4 2003/02/05 15:53:36 olga GUI
 * 
 * Revision 1.3 2003/02/03 17:49:31 olga Tests
 * 
 * Revision 1.2 2002/11/11 10:43:27 komm added support for multiplicity check
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:44:56 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.14 2001/01/28 13:14:58 shultzke API fertig
 * 
 * Revision 1.1.2.13 2001/01/11 11:36:08 shultzke Laden und speichern der
 * kritischen Paare geht, es fehlt nur noch das Laden fuer den Parser.
 * 
 * Revision 1.1.2.12 2001/01/02 12:29:00 shultzke Alle Optionen angebunden
 * 
 * Revision 1.1.2.11 2000/12/18 13:33:41 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.10 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */

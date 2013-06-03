package agg.attribute.impl;

import agg.attribute.handler.impl.javaExpr.JexExpr;

/**
 * @author $Author: olga $
 * @version $Id: VerboseControl.java,v 1.2 2007/09/10 13:05:18 olga Exp $
 */
public class VerboseControl {

	//
	// Additional Views

	public static boolean logShowLogWindow = false;

	public static boolean logShowContextView = false;

	//
	// Log topics (filtering of GUI log messages).

	/** Errors and Warnings - should always be set! */
	public static boolean logWarning = true;

	//
	// For The Developer (temporary debugging issues)

	/** Serialization of objects. */
	public static boolean logFileIO = false;

	/** Each creation of any subclass of AttrObject */
	public static boolean logCreation = false;

	/** Context of AttrInstance (impl by ValueTuple) */
	public static boolean logContextOfInstances = false;

	/** Mapping in an AttrContext (impl by ContextView / ContextCore ) */
	public static boolean logMapping = false;

	/** Handling of AttrContext (impl by ContextView / ContextCore ) */
	public static boolean logContext = false;

	/** Context (rule) conditions AttrCond (impl by CondTuple) */
	public static boolean logCond = false;

	/** Context (rule) variables AttrVar (impl by VarTuple) */
	public static boolean logVar = false;

	/** Setting of variables. */
	public static boolean logSetValue = false;

	/** Removing of variables */
	public static boolean logRemoveValue = false;

	/** Events */
	public static boolean logEvent = false;

	/** Syntax trees of Java expressions. */
	public static boolean logParseTree = false;

	/** Java expression parser calls */
	public static boolean logJexParser = false;

	/** Traces method calls */
	public static boolean logTrace = false;

	/** Debug topic: context handling. */
	public static void setDebugContext(boolean b) {
		logContext = logCond = logVar = logSetValue = logRemoveValue = b;
	}

	/** Debug topic: context handling. */
	public static void setDebugExpr(boolean b) {
		logParseTree = b;
	}

	/** Debug topic: event handling. */
	public static void setDebugEvent(boolean b) {
		logEvent = b;
	}

	/** Enabling / Disabling debug topics. */
	public static void setDebug(boolean b) {
		// setDebugContext( b );
		// setDebugExpr( b );
		// logMapping = b;
		setDebugEvent(b);
		// Don't append after this:
		if (logParseTree)
			JexExpr.getParser().parseOutputOn();
		else
			JexExpr.getParser().parseOutputOff();
	}
}
/*
 * $Log: VerboseControl.java,v $
 * Revision 1.2  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/07/15 11:13:10 olga CPs letzter Schliff
 * 
 * Revision 1.3 2003/02/20 17:38:20 olga Tests
 * 
 * Revision 1.2 2003/01/15 11:35:44 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.9 2000/04/05 12:09:34 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.8 2000/01/06 09:52:01 shultzke logtrace ist fuer Debugwindow
 * noetig
 * 
 * Revision 1.7 1999/09/27 16:17:01 olga dispose Methoden hinzugefuegt.
 * 
 * Revision 1.6 1999/09/21 08:48:23 shultzke Performanz durch WeakReferences
 * verbessert
 * 
 * Revision 1.5 1999/08/17 10:28:59 shultzke *** empty log message ***
 */

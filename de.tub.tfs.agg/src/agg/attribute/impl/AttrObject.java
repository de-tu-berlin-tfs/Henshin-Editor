package agg.attribute.impl;

import java.util.Observable;
import java.util.Observer;

/**
 * Provides some convenience operations for its subclasses. Very useful for
 * debugging.
 * 
 * @version $Id: AttrObject.java,v 1.4 2007/11/01 09:58:13 olga Exp $
 * @author $Author: olga $
 */
public abstract class AttrObject extends Object implements Observer {

	protected static Class<?> classHandlerExpr;

	protected static Class<?> classObject;

	protected static Class<?> classString;

	protected static boolean initialized = false;

	/**
	 * A message is printed in the logging window whenever an instance of it is
	 * created.
	 */
	public AttrObject() {
		super();
		if (!initialized) {
			try {
				classHandlerExpr = Class
						.forName("agg.attribute.handler.HandlerExpr");
				classObject = Class.forName("java.lang.Object");
				classString = Class.forName("java.lang.String");
				initialized = true;
			} catch (Exception ex) {
				throw (RuntimeException) ex;
			}
		}
		logPrintln(VerboseControl.logCreation, "New instance.");
	}

	public String toString() {
		return defaultToString();
	}

	/**
	 * Even if a class has defined its own 'toString()' method, we want to see
	 * the long name and the funny code (virtual address) that makes it unique
	 * and traceable.
	 * 
	 * @return A string of the form "java.lang.reflect.Method: 7643ef8a"
	 */
	protected String defaultToString() {
		String tmp;
		String clazz = getClass().getName();
		int i1 = clazz.lastIndexOf('.');
		if (i1 != -1) {
			tmp = clazz.substring(i1 + 1);
			clazz = tmp;
		}
		return clazz + "@" + hashCode();
	}

	/**
	 * Default (empty) implementation of the only Observer interface method.
	 * Subclasses that want to become observers have to override this.
	 */
	public void update(Observable o, Object arg) {
		throw new RuntimeException("Observer Interface not implemented for "
				+ this.getClass().getName());
	}

	/**
	 * Combines the default instance representation with a specific one, if
	 * given.
	 */
	protected String getInstRepr() {
		String stdRepr = defaultToString();
		String actRepr = toString();
		String result;
		if (stdRepr.equals(actRepr)) {
			result = actRepr;
		} else {
			result = stdRepr + " " + actRepr;
		}
		return result;
	}

	/** Display itself. Empty by default. */
	public void log() {
	}

	/** Print itself and a message to logging view. */
	public void log(String msg) {
		log(true, msg);
	}

	/**
	 * Print itself and a message to logging view, if 'logTopic' == 'true'.
	 */
	public void log(boolean logTopic, String msg) {
		AttrSession.logPrintln(logTopic, getInstRepr() + ":\t " + msg);
	}

	/** Print itself and a message to error view. */
	public void err(String msg) {
		err(true, msg);
	}

	/**
	 * Print itself and a message to error view, if 'logTopic' == 'true'.
	 */
	public void err(boolean logTopic, String msg) {
		if (logTopic) {
			AttrSession.errPrintln(getInstRepr() + ":\t " + msg);
		}
	}

	public void warn(String msg) {
		AttrSession.warn(this, msg, null, false);
	}

	public void warn(String msg, boolean showStack) {
		AttrSession.warn(this, msg, null, showStack);
	}

	public void warn(String msg, Exception ex) {
		AttrSession.warn(this, msg, ex, true);
	}

	public void warn(String msg, Exception ex, boolean showStack) {
		AttrSession.warn(this, msg, ex, showStack);
	}

	/** Print itself and a message. */
	public void logPrintln(String msg) {
		AttrSession.logPrintln(getInstRepr() + ":\t " + msg);
	}

	/** Print itself and a message if 'logTopic' == 'true'. */
	public void logPrintln(boolean logTopic, String msg) {
		if (logTopic) {
			logPrintln(msg);
		}
	}

	/**
	 * Print information about entering the method 'name' with the arguments
	 * 'args' if 'debugTopic' == 'true'.
	 */
	protected void logEnteredMethod(boolean debugTopic, String name,
			Object[] args) {
		String line, arg;
		int i;
		line = name + "( ";
		for (i = 0; i < args.length; i++) {
			if (args[i] instanceof AttrObject)
				arg = ((AttrObject) args[i]).getInstRepr();
			else
				arg = args[i].toString();
			line += arg;
			if (i < args.length - 1)
				line += ",\n\t";
		}
		line += " )\n";
		AttrSession.stdoutPrintOnEnter(debugTopic, getInstRepr() + ":\n\t"
				+ line);
	}

	/**
	 * Print to stdout when entered a method, useful when synchronizing with
	 * other components.
	 */
	protected void stdoutPrintOnEnter(boolean debugTopic, String msg) {
		AttrSession
				.stdoutPrintOnEnter(debugTopic, getInstRepr() + ":\t " + msg);
	}

	/**
	 * Print to stdout when leaving a method, useful when synchronizing with
	 * other components.
	 */
	protected void stdoutPrintOnExit(boolean debugTopic, String msg) {
		AttrSession.stdoutPrintOnExit(debugTopic, getInstRepr() + ":\t " + msg);
	}

}

/*
 * $Log: AttrObject.java,v $
 * Revision 1.4  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:32:58 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.5 2004/02/25 16:35:46 olga Testausgaben aus.
 * 
 * Revision 1.4 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.2 2002/09/23 12:23:55 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:08:57 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/01/04 13:51:55 shultzke Progressbalken fuer das Laden und
 * Speichern integriert.
 */

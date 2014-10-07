package agg.attribute.impl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrMember;
import agg.attribute.AttrVariableTuple;

/**
 * Output to a log window and other session-global methods.
 * 
 * @version $Id: AttrSession.java,v 1.4 2007/11/05 09:18:17 olga Exp $
 * @author $Author: olga $
 */
public class AttrSession extends VerboseControl {

	/** Operating system name. */
	protected static String osName = System.getProperty("os.name");

	/** System architecture. */
	protected static String osArch = System.getProperty("os.arch");

	static boolean wasSystemAnalysed = false;

	static boolean isWin32 = false;

	/** System queries. */
	static public boolean isWin32() {
		if (!wasSystemAnalysed) {
			wasSystemAnalysed = true;
			if ((osName + osArch).toLowerCase().indexOf("win") != -1) {
				System.out.println("AttrManager: System recognized as Win32.");
				isWin32 = true;
			}
			if ((osName + osArch).toLowerCase().indexOf("sunos") != -1) {
				System.out.println("AttrManager: System recognized as SunOS.");
				isWin32 = false;
			}
			// System.out.println( "AttrManager: os.name="+osName+
			// ". os.arch="+osArch+".");
		}
		// For testing:
		// isWin32 = true;
		return isWin32;
	}

	/*
	 * GUI or stdout - log view.
	 */
	// protected static AttrLog logView = null;
	// protected static AttrLog errView = null;
	/**
	 * Stdout indentation count.
	 */
	protected static byte stdoutIndentCount = 0;

	/**
	 * Flag indicating if caret at the beginning of stdout line.
	 */
	protected static boolean stdoutBeginOfLine = true;

	/** Redirection stream to use instead of System.out */
	protected static ByteArrayOutputStream redirectStream;

	/**
	 * Temporary storage of the "System.out" stream, for the time of
	 * redirection.
	 */
	protected static PrintStream stdout;

	/** Initializing static state. */
	public AttrSession() {
		if (VerboseControl.logShowLogWindow) {
			// logView = new AttrLog(logShowLogWindow);
			// errView = logView;
		}
	}

	// //////////////////////
	// GUI log view output:

	/**
	 * Prints the specified message to the log view.
	 */
	public static void logPrintln(String msg) {
		// if( logView != null ) logView.println( msg );
		// else System.out.println( msg );
		System.out.println(msg);
	}

	/**
	 * Prints the specified message to the error view.
	 */
	public static void errPrintln(String msg) {
		// if( errView != null ) errView.println( msg );
		// else System.err.println( msg );
		System.out.println(msg);
	}

	/**
	 * Prints the specified message to the log view if the boolean value is
	 * true.
	 */
	public static void logPrintln(boolean logTopic, String msg) {
		if (logTopic) {
			logPrintln(msg);
		}
	}

	/**
	 * Prints the specified message to the error view if the boolean value is
	 * true.
	 */
	public static void errPrintln(boolean logTopic, String msg) {
		if (logTopic) {
			errPrintln(msg);
		}
	}

	public static void warn(Object sender, String msg) {
		warn(sender, msg, null, false);
	}

	public static void warn(Object sender, String msg, boolean showStack) {
		warn(sender, msg, null, showStack);
	}

	public static void warn(Object sender, String msg, Exception ex) {
		warn(sender, msg, ex, true);
	}

	public static void warn(Object sender, String msg, Exception ex,
			boolean showStack) {
		if (!logWarning)
			return;
		String stack = "";
		if (showStack) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw, true);
			// Thread.dumpStack(); 
			ex.printStackTrace( pw );
			stack = "\n- Stack trace:\n" + sw;
		}
		String warnText = "========== WARNING FROM THE ATTRIBUTE COMPONENT =========="
				+ "\n- Issued from: "
				+ sender
				+ " : "
				+ sender.getClass().getName()
				+ "\n- Message: "
				+ msg
				+ (ex != null ? "\n- Exception: " + ex.getMessage() + stack
						: "")
				+ "\n----------------------------------------------------------";
		errPrintln(warnText);
	}

	// //////////////////////
	// stdout output for debugging:

	/**
	 * Prints the specified message to stdout if the boolean value is true.
	 */
	public static void stdoutPrint(boolean debugTopic, String msg) {
		if (debugTopic) {
			System.out.print(msg);
			if (msg.charAt(msg.length() - 1) == '\n') {
				stdoutBeginOfLine = true;
			} else {
				stdoutBeginOfLine = false;
			}
		}
	}

	/**
	 * Prints a line with the specified message to stdout if the boolean value
	 * is true.
	 */
	public static void stdoutPrintln(boolean debugTopic, String msg) {
		if (debugTopic) {
			System.out.println(msg);
			stdoutBeginOfLine = true;
		}
	}

	/**
	 * Prints the specified message to stdout if the boolean value is true;
	 * indentation is incremented.
	 */
	public static void stdoutPrintOnEnter(boolean debugTopic, String msg) {
		if (debugTopic) {
			stdoutIndentCount++;
			stdoutPrintIndented(msg);
		}
	}

	/**
	 * Prints the specified message to stdout if the boolean value is true;
	 * indentation is decremented.
	 */
	public static void stdoutPrintOnExit(boolean debugTopic, String msg) {
		if (debugTopic) {
			stdoutPrintIndented(msg);
			stdoutIndentCount--;
		}
	}

	/**
	 * Prints the specified message indented to stdout if the boolean value is
	 * true.
	 */
	public static void stdoutPrintIndented(boolean debugTopic, String msg) {
		if (debugTopic) {
			stdoutPrintIndented(msg);
		}
	}

	// Protected methods:

	/**
	 * Prints an indented line with the specified message.
	 */
	protected static void stdoutPrintIndented(String msg) {
		StringBuffer spaces = new StringBuffer(stdoutIndentCount);
		for (int i = 0; i < stdoutIndentCount; i++) {
			spaces.append(' ');
		}
		System.out.print(spaces);
		System.out.print(msg);
	}

	/** Beginning redirection of standard output. */
	protected static void grabStdOutput() {
		stdout = System.out;
		redirectStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(redirectStream));
	}

	/**
	 * Ending redirection of standard output and flushing the data to the
	 * logging window.
	 */
	protected static void releaseStdOutput() {
		System.setOut(stdout);
		logPrintln(redirectStream.toString());
	}

	/**
	 * prints detailed information about an AttrInstance. Additionally with a
	 * short message.
	 */
	public static void logAttrInstance(AttrInstance instance, String msg) {
		logPrintln("Beginning --> " + msg);
		System.out.print(instance);
		int number = instance.getNumberOfEntries();
		logPrintln(" has " + number + " members");
		for (int i = 0; i < number; i++) {
			AttrMember am = instance.getMemberAt(i);
			System.out.print("Member #" + i);
			System.out.print(" with name \"" + am.getName());
			System.out.println("\" and value: " + instance.getValueAsString(i));
		}
		logPrintln("Ending    <-- " + msg);
	}

	/**
	 * prints detailed information about an AttrInstance. Additionally with a
	 * short message. If the boolean value is true.
	 */
	public static void logAttrInstance(boolean topic, AttrInstance instance,
			String msg) {
		if (topic)
			logAttrInstance(instance, msg);
	}

	/**
	 * prints detailed information about an AttrContext. Additionally with a
	 * short message.
	 */
	public static void logAttrContext(AttrContext context, String msg) {
		logPrintln("Beginning --> " + msg);
		System.out.print(context);
		String str = " allows: \n";
		str += "complex expressions: " + context.doesAllowComplexExpressions()
				+ "\n";
		str += "empty values: " + context.doesAllowEmptyValues() + "\n";
		str += "new variables: " + context.doesAllowNewVariables();
		System.out.println(str);
		str = "This context has ";
		AttrConditionTuple act = context.getConditions();
		int conditions = act.getNumberOfEntries();
		str += "#" + conditions + " conditions.";
		System.out.println(str);
		logAttrInstance(act, "im Context");
		str = "This context has ";
		AttrVariableTuple avt = context.getVariables();
		int variables = avt.getNumberOfEntries();
		str += "#" + variables + " varibles.";
		System.out.println(str);
		logAttrInstance(avt, "im Context");
		logPrintln("Ending    <-- " + msg);
	}

	/**
	 * prints detailed information about an AttrContext. Additionally with a
	 * short message. If the boolean value is true.
	 */
	public static void logAttrContext(boolean topic, AttrContext context,
			String msg) {
		if (topic)
			logAttrContext(context, msg);
	}
}
/*
 * $Log: AttrSession.java,v $
 * Revision 1.4  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.3  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/04/12 14:54:07 olga Restore
 * attr. values of attr. type observers after type graph imported.
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:55 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.9 2000/06/05 14:07:32 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.8 2000/04/05 12:08:59 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

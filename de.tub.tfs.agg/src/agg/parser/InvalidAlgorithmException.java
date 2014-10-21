// This class belongs to the following package:
package agg.parser;

//****************************************************************************+
/**
 * This exception should be thrown by the <code>isCritical()</code> method of
 * an object that implements a concrete stategy of a critical pair.
 * 
 * @author $Author: olga $
 * @version $Id: InvalidAlgorithmException.java,v 1.1 2005/08/25 11:56:57 enrico
 *          Exp $
 */
@SuppressWarnings("serial")
public class InvalidAlgorithmException extends Exception {

	/**
	 * kindOfAlgorithm specifies for which kind of algorithm the exception is
	 * thrown
	 */
	private int kindOfAlgorithm;

	// ****************************************************************************+
	/**
	 * InvalidAlgorithmException default constructor with a standard message and
	 * the standard kind of algorithm
	 */
	public InvalidAlgorithmException() {
		this("Algorithm is not supported.");
	}

	// ****************************************************************************+
	/**
	 * InvalidAlgorithmException creates a exception with a specified message
	 * and the standard kind of algorithm
	 * 
	 * @param message
	 *            The message.
	 */
	public InvalidAlgorithmException(String message) {
		this(message, 0);
	}

	// ****************************************************************************+
	/**
	 * InvalidAlgorithmException creates a exception with a specified message
	 * and the kind of algorithm which causes the error.
	 * 
	 * @param message
	 *            the message for exception
	 * @param algorithm
	 *            The algorithm causes the exception.
	 */
	public InvalidAlgorithmException(String message, int algorithm) {
		super(message);
		this.kindOfAlgorithm = algorithm;
	}

	// ****************************************************************************+
	/**
	 * getKindOfInvalidAlgorithm returns the kind of algorithm which causes the
	 * exception
	 * 
	 * @return The algorithm.
	 */
	public int getKindOfInvalidAlgorithm() {
		return this.kindOfAlgorithm;
	}

}

// End of InvalidAlgorithmException.java
/*
 * $Log: InvalidAlgorithmException.java,v $
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
 * Revision 1.1 2005/08/25 11:56:57
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.3 2001/03/08 10:42:51 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:53 shultzke API fertig
 * 
 * Revision 1.1.2.2 2000/11/13 10:59:16 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.1.2.1 2000/07/12 07:58:41 shultzke merged
 * 
 * Revision 1.2 2000/07/10 15:09:40 shultzke additional representtion
 * hinzugefuegt
 * 
 */

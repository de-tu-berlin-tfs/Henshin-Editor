package agg.attribute.parser.javaExpr;

/* Do not edit this line. ParseError.java Version 0.5 */
/**
 * @version $Id: ParseError.java,v 1.2 2007/09/10 13:05:47 olga Exp $
 * @author $Author: olga $
 */
public class ParseError extends Exception {
	static final long serialVersionUID = 1015230336800481404L;
	
	public ParseError() {
        super();
    }
	
	public ParseError(String message) {
        super(message);
    }
	
}
/*
 * $Log: ParseError.java,v $
 * Revision 1.2  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:15 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:11:10 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 10:59:57 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

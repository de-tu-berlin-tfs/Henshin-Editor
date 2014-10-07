package agg.attribute.parser.javaExpr;

/**
 * @version $Id: ASTWrongTypeException.java,v 1.4 2010/08/23 07:31:25 olga Exp $
 * @author $Author: olga $
 */
public class ASTWrongTypeException extends RuntimeException {

	static final long serialVersionUID = -1269956020947599174L;

	public String expected = null, found = null;

	public ASTWrongTypeException() {
		super();
	}

	public ASTWrongTypeException(String msg) {
		super(msg);
	}

	public ASTWrongTypeException(String expected, String found) {
		super();
		this.expected = expected;
		this.found = found;
	}

	public String getExpected() {
		return this.expected;
	}

	public String getFound() {
		return this.found;
	}
}
/*
 * $Log: ASTWrongTypeException.java,v $
 * Revision 1.4  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:49  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/03/28 10:01:14 olga -
 * extensive changes of Node/Edge Type Editor, - first Undo implementation for
 * graphs and Node/edge Type editing and transformation, - new / reimplemented
 * options for layered transformation, for graph layouter - enable / disable for
 * NACs, attr conditions, formula - GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:10:42 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 10:59:32 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

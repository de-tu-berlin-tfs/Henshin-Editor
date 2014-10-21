package agg.attribute.parser.javaExpr;

/* All AST nodes must implement this interface.  It provides basic
 machinery for constructing the parent and child relationships
 between nodes. */

import java.io.Serializable;
import java.util.Vector;

/**
 * @version $Id: Node.java,v 1.4 2009/10/26 10:04:48 olga Exp $
 * @author $Author: olga $
 */
public interface Node extends Serializable {

	static final long serialVersionUID = 1L;

	/**
	 * This method is called after the node has been made the current node. It
	 * indicates that child nodes can now be added to it.
	 */
	public void jjtOpen();

	/**
	 * This method is called after all the child nodes have been added.
	 */
	public void jjtClose();

	/**
	 * This pair of methods are used to inform the node of its parent.
	 */
	public void jjtSetParent(Node n);

	public Node jjtGetParent();

	/**
	 * This method tells the node to add its argument to the node's list of
	 * children.
	 */
	public void jjtAddChild(Node n);

	/**
	 * This method returns a child node. The children are numbered from zero,
	 * left to right.
	 */
	public Node jjtGetChild(int i);

	/** Return the number of children the node has. */
	int jjtGetNumChildren();

	/** *********************** Added by Sreeni. ****************** */

	/** Interpret method */
	public void interpret();

	/** This method returns an error string, if interpretting is failed */
	public String getError();

	/** *********************** Added by BM. ****************** */

	/** Finding the type and checking for consistency. */
	public void checkContext();

	public Object getRootResult();

	public void dump(String prefix);

	public void rewrite();

	public void replaceChild(Node oldChild, Node newChild);

	/** returns this node as a string with all children. */
	public String getString();

	/**
	 * fills the vector with the names of all variables which occur in this
	 * abstract syntax tree
	 */
	public void getAllVariablesinExpression(Vector<String> v) ;

	/** Copy the abstract syntax tree */
	public Node copy();
}
/*
 * $Log: Node.java,v $
 * Revision 1.4  2009/10/26 10:04:48  olga
 * tuning and tests
 *
 * Revision 1.3  2008/05/22 15:21:06  olga
 * ARS - implementing further details
 *
 * Revision 1.2  2007/09/10 13:05:46  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:11:05 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:59:54 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.4 2000/03/03 11:32:46 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */

package agg.attribute.parser.javaExpr;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Vector;

import agg.attribute.handler.SymbolTable;
import agg.attribute.impl.AttrSession;
import agg.attribute.impl.VerboseControl;

/**
 * @version $Id: SimpleNode.java,v 1.10 2010/09/23 08:15:01 olga Exp $
 * @author $Author: olga $
 */
public class SimpleNode implements Node {

	static final long serialVersionUID = 1L;

	protected Node parent;

	protected java.util.Vector<Node> children;

	protected String identifier;

	protected Object info;

	private String error;

	public SimpleNode(String id) {
		this.identifier = id;
		typeInit();
		this.error = "";
	}

	public static Node jjtCreate(String id) {
		return new SimpleNode(id);
	}

	public void jjtOpen() {
	}

	public void jjtClose() {
		if (this.children != null) {
			this.children.trimToSize();
		}
	}

	public void jjtSetParent(Node n) {
		this.parent = n;
	}

	public Node jjtGetParent() {
		return this.parent;
	}

	public void jjtAddChild(Node n) {
		if (this.children == null) {
			this.children = new java.util.Vector<Node>();
		}
		this.children.addElement(n);
	}

	public Node jjtGetChild(int i) {
		return this.children.elementAt(i);
	}

	public int jjtGetNumChildren() {
		return (this.children == null) ? 0 : this.children.size();
	}

	/*
	 * These two methods provide a very simple mechanism for attaching arbitrary
	 * data to the node.
	 */

	public void setInfo(Object i) {
		this.info = i;
	}

	public Object getInfo() {
		return this.info;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		Class<?> c = getNodeClass();

		if (c == null) {
			return this.identifier;
		} 
		return this.identifier + " [" + c.toString() + "]";
		
	}

	public String toString(String prefix) {
		return prefix + toString();
	}

	/*
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 */

	public void dump(String prefix) {
		// System.out.println( toString(prefix) );
		AttrSession.logPrintln(VerboseControl.logJexParser, toString(prefix));
		if (this.children != null) {
			for (java.util.Enumeration<Node> e = this.children.elements(); e
					.hasMoreElements();) {
				SimpleNode n = (SimpleNode) e.nextElement();
				n.dump(prefix + " ");
			}
		}
	}

	/** *********************** Added by Sreeni. ****************** */

	/** Stack for calculations. */
//	protected static Object[] stack = new Object[2048]; //[1024];	
	
	protected static ArrayList<Object> stack = new ArrayList<Object>();
	
	protected static int top = -1;

	/**
	 * This method must be overridden from all its subclasses.
	 */
	public void interpret() {
		// throw new Error(); /* It better not come here. */
		this.error = "SimpleNode.interpret:  FAILED!";
	}

	public String getError() {
		return this.error;
	}

	/** *********************** Added by BM. ****************** */

	/** Initialization flag */
	static protected boolean neverCalled = true;

	/** Symbol table */
	static protected SymbolTable symtab = null;

	/** Widening order for numeric types */
	protected static java.util.Hashtable<Class<?>, Integer> numberTypes = new java.util.Hashtable<Class<?>, Integer>();

//	/** Constructors */
//	protected static java.util.Hashtable constructors = new java.util.Hashtable();

//	/** Referencing methods for Operands */
//	protected static java.util.Hashtable refMethods = new java.util.Hashtable();

	/** String class handle for frequent comparison */
	static protected Class<?> stringClass;

	/** Object class handle for frequent comparison */
	static protected Class<?> objectClass;

	static protected void typeInit() {
		if (neverCalled) {
			neverCalled = false;
			int codeNr = 0;

			codeNr = 0;
			numberTypes.put(Byte.TYPE, new Integer(codeNr++));
			numberTypes.put(Short.TYPE, new Integer(codeNr++));
			numberTypes.put(Long.TYPE, new Integer(codeNr++));
			numberTypes.put(Integer.TYPE, new Integer(codeNr++));
			numberTypes.put(Float.TYPE, new Integer(codeNr++));
			numberTypes.put(Double.TYPE, new Integer(codeNr++));

			try {
				stringClass = Class.forName("java.lang.String");
				objectClass = Class.forName("java.lang.Object");
			} catch (Exception e) {
				throw (RuntimeException) e;
			}
		}
	}

	/** The class handle */
	private Class<?> nodeClass = null;

	/** Getting the node class. */
	// protected Class<?> getNodeClass(){
	public Class<?> getNodeClass() {
		return this.nodeClass;
	}

	/** Setting the node class. */
	protected void setNodeClass(Class<?> nodeClass) {
		this.nodeClass = nodeClass;
	}

	/** Setting the node class to that of the parameter node. */
	protected void takeNodeClassFrom(SimpleNode node) {
		setNodeClass(node.getNodeClass());
	}

	/** Checking if the node represents a member (method or field). */
	public boolean isAction() {
		return (this.identifier.equals("Action"));
	}

	/** Checking if the node represents a member (method or field). */
	public boolean isMember() {
		return (this.identifier.equals("Method") || this.identifier.equals("Action") || this.identifier
				.equals("Field"));
	}

	/** Checking if the node represents an array index). */
	public boolean isArrayIndex() {
		return (this.identifier.equals("ArrayIndex"));
	}

	/** Checking if node's type is a number type. */
	public boolean hasStringType() {
		return (getNodeClass() == stringClass);
	}

	/** Checking if node's type is a number type. */
	public boolean hasNumberType() {
		return (numberTypes.containsKey(this.nodeClass));
	}

	/** Getting the number type code of this node object */
	protected int typeCode() {
		return typeCode(getNodeClass());
	}

	/** Getting the number type code of a primitive number type */
	protected static int typeCode(Class<?> cls) {
		return numberTypes.get(cls).intValue();
	}

	/** Widening a number type as necessary. */
	protected Class<?> commonNumberType(SimpleNode n1, SimpleNode n2) {
		int numType1 = numberTypes.get(n1.getNodeClass()).intValue();
		int numType2 = numberTypes.get(n2.getNodeClass()).intValue();

		return (numType1 > numType2 ? n1.getNodeClass() : n2.getNodeClass());
	}

	protected boolean isConstantExpr() {
		int nChildren = jjtGetNumChildren();
		SimpleNode child;

		for (int i = 0; i < nChildren; i++) {
			child = (SimpleNode) jjtGetChild(i);
			if (!child.isConstantExpr()) {
				return false;
			}
		}
		return true;
	}

	/***************************************************************************
	 * Public Methods
	 **************************************************************************/

	/** Obtaining the node type and checking for consistency. */
	public void checkContext() {
		/* It better not come here. */
//		throw new Error(); 
		this.error = "SimpleNode.checkContext FAILED!";
	}

	static public void setSymbolTable(SymbolTable st) {
		symtab = st;
	}

	static public SymbolTable getSymbolTable() {
		return symtab;
	}

	static protected ClassResolver classResolver = null;

	static public void setClassResolver(ClassResolver cr) {
		classResolver = cr;
	}

	public Object getRootResult() {
//		return stack[top];
		return stack.get(top);
	}

	/**
	 * returns this node as a string with all children. Subclasses must override
	 * this.
	 */
	public String getString() {
		return toString();
	}

	/**
	 * Rewrites all children. This method must be overridden if any special
	 * handling is needed.
	 * 
	 * @see ASTId#rewrite special rewriting at class ASTId
	 */
	public void rewrite() {
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			this.error = "";
			try {
				jjtGetChild(i).interpret();

				if (jjtGetChild(i).getError().length() != 0) {
					
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							" SimpleNode.rewrite  : interpret  FAILED!");
					
					this.error = jjtGetChild(i).getError();
					break;
				}

			} catch (ASTMissingValueException amve) {
				jjtGetChild(i).rewrite();
			}
		}
	}

	/** Replaces a child */
	public void replaceChild(Node oldChild, Node newChild) {
		int pos = this.children.indexOf(oldChild);
		this.children.insertElementAt(newChild, pos);
		this.children.removeElement(oldChild);
	}

	/**
	 * fills the vector with the names of all variables which occur in this
	 * abstract syntax tree
	 */
	public void getAllVariablesinExpression(Vector<String> v) {
		for (int i = 0; i < jjtGetNumChildren(); i++) {
			jjtGetChild(i).getAllVariablesinExpression(v);
		}
	}

	public String getIdentifier() {
		return this.identifier;
	}

	/**
	 * Copys the abstract syntax tree. The information object won't be copied.
	 */
	public Node copy() {
		Node copy = null;
		/*
		 * Class<?> clazze = getClass(); boolean found = false; Constructor[]
		 * construct = clazze.getDeclaredConstructors(); int i=0; for(;i<construct.length
		 * &&!found; ){ Class<?>[] parameters = construct[i].getParameterTypes();
		 * if(parameters.length == 1){
		 *//*
			 * gesucht wir der Constructor der als Parameter nur String
			 * enthaelt. Die Laenge ist daher 1
			 *//*
			 * String s = parameters[0].getName(); found = (s.equals(
			 * "java.lang.String")); } if(!found) i++; } Object[]
			 * constructorParameter = new Object[1]; constructorParameter[0] =
			 * identifier; try{ copy =
			 * (Node)construct[i].newInstance(constructorParameter); }
			 * catch(InvocationTargetException ite){}
			 * catch(IllegalAccessException iae){} catch(InstantiationException
			 * ie){} copy.jjtSetParent(parent); ((SimpleNode) copy).info= info;
			 * ((SimpleNode)copy).setNodeClass( nodeClass); for(int c = 0; c<jjtGetNumChildren();
			 * c++){ Node child = jjtGetChild(c); Node childCopy = child.copy();
			 * copy.jjtAddChild(childCopy); childCopy.jjtSetParent(copy); }
			 */
		try {

			/*
			 * Diese Copy-Methode sei Yang Xiang <xiang@uni-hamburg.de>
			 * gewidmet, der unerschrocken ueber meine Anfrage in
			 * de.comp.lang.java eine Stunde spaeter diese Loesung parat hatte
			 */

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(out);
			oos.writeObject(this);
			oos.flush();
			ByteArrayInputStream in = new ByteArrayInputStream(out
					.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(in);
			copy = (Node) ois.readObject();
			oos.close();
			ois.close();
		} catch (IOException ioe) {
		} catch (ClassNotFoundException cnfe) {
		}
		return copy;
	}
}

/*
 * $Log: SimpleNode.java,v $
 * Revision 1.10  2010/09/23 08:15:01  olga
 * tuning
 *
 * Revision 1.9  2010/07/29 10:09:23  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.8  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.7  2010/03/08 15:38:38  olga
 * code optimizing
 *
 * Revision 1.6  2009/10/26 10:04:58  olga
 * tuning and tests
 *
 * Revision 1.5  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/10 13:05:49  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/12/13 13:32:58 enrico
 * reimplemented code
 * 
 * Revision 1.2 2006/01/16 09:37:01 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:51 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.8 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.7 2004/09/23 08:26:43 olga Fehler bei CPs weg, Debug output in
 * file
 * 
 * Revision 1.6 2003/10/16 08:21:13 olga Nur Tests
 * 
 * Revision 1.5 2003/09/25 09:38:07 olga stack groesse erhoet wegen ueberlauf
 * 
 * Revision 1.4 2003/07/21 13:18:10 olga Attrs testen
 * 
 * Revision 1.3 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.8 2000/04/05 12:11:11 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.7 2000/03/14 10:59:59 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.6 2000/03/14 10:01:32 shultzke erste version des kopierens
 * 
 * Revision 1.5 2000/03/03 11:32:50 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */

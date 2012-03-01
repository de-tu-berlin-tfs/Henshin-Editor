package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import agg.attribute.impl.AttrSession;
import agg.attribute.impl.VerboseControl;

/**
 * @version $Id: OpMemberNode.java,v 1.9 2010/09/23 08:15:01 olga Exp $
 * @author $Author: olga $
 */
public class OpMemberNode extends MemberNode {

	static final long serialVersionUID = 1L;

	protected Method method = null;

	protected Object receivingObj, returnObj;

	OpMemberNode(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new OpMemberNode(id);
	}

	protected boolean isConstantExpr() {
		return false;
	}

	protected String getMethodName() {
		Class<?> c = getNodeClass();
		String name = ((ASTMemberName) jjtGetChild(0)).name;

		if (c == null) {
			return "\"" + name + "\"";
		} 
		return "[" + this.method.toString() + "]";
		
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */

	public String toString() {
		return this.identifier + " " + getMethodName();
	}

	/*
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 */

	public void dump(String prefix) {
		// System.out.println( toString(prefix) );
		AttrSession.logPrintln(VerboseControl.logJexParser, toString(prefix));
		if (this.children != null) {
			java.util.Enumeration<Node> e = this.children.elements();
			e.nextElement();
			while (e.hasMoreElements()) {
				SimpleNode n = (SimpleNode) e.nextElement();
				n.dump(prefix + " ");
			}
		}
	}

	protected static boolean areParamsCompatible(Class<?> sigTypes[],
			Class<?> realTypes[]) {
		if (realTypes == null) {
			return (sigTypes.length == 0);
		} else if (sigTypes.length == realTypes.length) {
			for (int i = 0; i < sigTypes.length; i++) {
				if (!sigTypes[i].isAssignableFrom(realTypes[i])) {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	protected static Method getMethod(Class<?> clazz, String name,
			Class<?> paramTypes[]) {
		Method result = null, tmpMethod;
		Method allMethods[];
		try {
			result = clazz.getMethod(name, paramTypes);
		} catch (NoSuchMethodException ex) {
			allMethods = clazz.getMethods();
			for (int i = 0; i < allMethods.length; i++) {
				tmpMethod = allMethods[i];
				if (tmpMethod.getName().equals(name)
						&& areParamsCompatible(tmpMethod.getParameterTypes(),
								paramTypes)) {
					result = tmpMethod;
				}
			}
		}
		return result;
	}

	protected void findMethod(SimpleNode recipient)
			throws ASTWrongTypeException {
		Class<?> recClass = recipient.getNodeClass();
		String methodName = ((ASTMemberName) jjtGetChild(0)).name;
		int nChildren = jjtGetNumChildren();
		Node param;
		Class<?> paramTypes[] = new Class<?>[nChildren - 1];

		for (int i = 1; i < nChildren; i++) {
			param = jjtGetChild(i);
			param.checkContext();
			paramTypes[i - 1] = ((SimpleNode)param).getNodeClass();
		}
		if (nChildren == 1) {
			paramTypes = null;
		}
		this.method = getMethod(recClass, methodName, paramTypes);

		/* Error, if method not found: */
		if (this.method == null) {
			String paramText;
			if (paramTypes != null) {
				paramText = "for parameter types \n (";
				for (int i = 0; i < paramTypes.length; i++) {
					paramText = paramText + paramTypes[i].toString();
					if (i < paramTypes.length - 1) {
						paramText = paramText + ", ";
					}
				}
				paramText = paramText + ")";
			} else {
				paramText = "with an empty parameter list";
			}

			throw new ASTMemberException("No public method " + getMethodName()
					+ "\n  " + paramText + "\n  in class "
					+ recClass.toString() + " .");
		}
	}

	public void invoke(SimpleNode recipient) {

		if (this.method == null) {
			checkContext(recipient);
		}

		int nChildren = jjtGetNumChildren();
		Object params[] = new Object[nChildren - 1];

		for (int i = 1; i < nChildren; i++) {
			jjtGetChild(i).interpret();
			params[i - 1] = stack.get(top--);
		}
		if (nChildren == 1) {
			params = null;
		}
		this.receivingObj = stack.get(top);;
		try {
			this.returnObj = this.method.invoke(this.receivingObj, params);
			
//			System.out.println("receivingObj=" + receivingObj );
//			System.out.println("returnObj=" + returnObj );
			 
			AttrSession.logPrintln(VerboseControl.logJexParser,
					"OpMemberNode: receivingObj=" + this.receivingObj);
			AttrSession.logPrintln(VerboseControl.logJexParser,
					"OpMemberNode: returnObj=" + this.returnObj);
		} catch (IllegalAccessException ex1) {
			throw new ASTMemberException("Cannot access method "
					+ getMethodName() + Jex.addMessage(ex1));
		} catch (IllegalArgumentException ex2) {
			throw new ASTMemberException("Illegal arguments to method "
					+ getMethodName() + Jex.addMessage(ex2));
		} catch (InvocationTargetException ex3) {
			// System.out.println(ex3.getTargetException().getMessage());
			if (ex3.getTargetException() != null)
				throw new ASTMemberException("Error while invoking method "
						+ getMethodName() + "  "
						+ Jex.addMessage((Exception) ex3.getTargetException()));
			
			throw new ASTMemberException("Error while invoking method "
						+ getMethodName() + Jex.addMessage(ex3));
		} catch (NullPointerException ex4) {
			throw new ASTMemberException("Invoking method " + getMethodName()
					+ " on a null object." + Jex.addMessage(ex4));
		}
	}

	public void checkContext(SimpleNode recipient) throws ASTWrongTypeException {
		findMethod(recipient);
		setNodeClass(this.method.getReturnType());
	}

	public void interpret(SimpleNode recipient) {

		invoke(recipient);
//		stack[++top] = returnObj;
		stack.add(++top, this.returnObj);
	}

	public Node copy() {
		Node copy = super.copy();
		((OpMemberNode) copy).method = this.method;
		((OpMemberNode) copy).receivingObj = this.receivingObj;
		((OpMemberNode) copy).returnObj = this.returnObj;
		return copy;
	}
}
/*
 * $Log: OpMemberNode.java,v $
 * Revision 1.9  2010/09/23 08:15:01  olga
 * tuning
 *
 * Revision 1.8  2010/07/29 10:09:18  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.7  2010/05/05 16:16:27  olga
 * tuning and tests
 *
 * Revision 1.6  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.5  2010/03/08 15:38:38  olga
 * code optimizing
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:32:58 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.4 2005/05/09 11:42:04 olga -CPs: error of dangling condition check
 * fixed. -Transformation: attr. exception handling. -Omondo XMI to XSL:
 * importing
 * 
 * Revision 1.3 2003/03/05 18:24:15 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:11:08 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.4 2000/03/14 10:59:56 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

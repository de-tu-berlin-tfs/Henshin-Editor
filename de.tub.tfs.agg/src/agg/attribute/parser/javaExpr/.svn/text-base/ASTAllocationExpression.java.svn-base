package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @version $Id: ASTAllocationExpression.java,v 1.1 2005/08/25 11:56:52 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class ASTAllocationExpression extends SimpleNode {

	static final long serialVersionUID = 1L;

	Constructor<?> constructor = null;

	ASTAllocationExpression(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTAllocationExpression(id);
	}

	protected boolean isConstantExpr() {
		return false;
	}

	protected String getMethodName() {
		String name = ((ASTClassName) jjtGetChild(0)).name;

		if (this.constructor == null) {
			return "\"" + name + "\"";
		} 
		return "[" + this.constructor.toString() + "]";
	}

	public void checkContext() {
		int nChildren = jjtGetNumChildren();
		Node constrName = jjtGetChild(0);
		Node param;
		Class<?> paramClasses[] = new Class[nChildren - 1];

		try {
			constrName.checkContext();
			takeNodeClassFrom((SimpleNode)constrName);

			for (int i = 1; i < nChildren; i++) {
				param = jjtGetChild(i);
				param.checkContext();
				paramClasses[i - 1] = ((SimpleNode)param).getNodeClass();
			}
			if (nChildren == 1) {
				paramClasses = null;
			}
			this.constructor = getNodeClass().getConstructor(paramClasses);
		} catch (NoSuchMethodException ex1) {
			throw new ASTMemberException("No this.constructor " + getMethodName()
					+ " with these argument types in class "
					+ getNodeClass().toString() + Jex.addMessage(ex1));
		} catch (SecurityException ex2) {
			throw new ASTMemberException(
					"Security violation while looking up this.constructor "
							+ getMethodName() + " in class "
							+ getNodeClass().toString() + Jex.addMessage(ex2));
		}
	}

	public void interpret() {
		if (this.constructor == null) {
			checkContext();
		}
		int nChildren = jjtGetNumChildren();
		Object params[] = new Object[nChildren - 1];

		for (int i = 1; i < nChildren; i++) {
			jjtGetChild(i).interpret();
			params[i - 1] = stack.get(top--); //stack[top--];
		}
		if (nChildren == 1) {
			params = null;
		}
		try {
//			stack[++top] = this.constructor.newInstance(params);
//			Array.set(stack, ++top, this.constructor.newInstance(params));
			stack.add(++top, this.constructor.newInstance(params));
		} catch (IllegalAccessException ex1) {
			throw new ASTMemberException("Cannot access this.constructor "
					+ getMethodName() + Jex.addMessage(ex1));
		} catch (IllegalArgumentException ex2) {
			throw new ASTMemberException("Illegal arguments to this.constructor "
					+ getMethodName() + Jex.addMessage(ex2));
		} catch (InvocationTargetException ex3) {
			throw new ASTMemberException(
					"Error while instantiating with this.constructor "
							+ getMethodName() + Jex.addMessage(ex3));
		} catch (InstantiationException ex4) {
			throw new ASTMemberException("Trying to call this.constructor "
					+ getMethodName()
					+ " for an interface or an abstract class."
					+ Jex.addMessage(ex4));
		}
	}

	public String getString() {
		String argList = "";
		int nChildren = jjtGetNumChildren();
		Node construct = jjtGetChild(0);
		for (int i = 1; i < nChildren; i++) {
			if (i > 1)
				argList += ",";
			argList += jjtGetChild(i).getString();
		}
		return "new " + construct.getString() + "(" + argList + ")";
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTAllocationExpression) copy).constructor = this.constructor;
		return copy;
	}
}
/*
 * $Log: ASTAllocationExpression.java,v $
 * Revision 1.8  2010/08/23 07:31:25  olga
 * tuning
 *
 * Revision 1.7  2010/07/29 10:09:22  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.6  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.5  2010/03/08 15:38:02  olga
 * code optimizing
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/24 09:42:38  olga
 * AGG transformation engine tuning
 *
 * Revision 1.2  2007/09/10 13:05:49  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:58 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:41 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:43 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

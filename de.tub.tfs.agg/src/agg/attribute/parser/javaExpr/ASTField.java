package agg.attribute.parser.javaExpr;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * @version $Id: ASTField.java,v 1.6 2010/12/07 16:36:45 olga Exp $
 * @author $Author: olga $
 */
public class ASTField extends MemberNode {

	static final long serialVersionUID = 1L;

	protected Field field = null;

	ASTField(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTField(id);
	}

	protected String getFieldName() {
		Class<?> c = getNodeClass();
		String name = ((ASTMemberName) jjtGetChild(0)).name;

		if (c == null) {
			name = "\"" + name + "\"";
		} else {
			if (this.field == null) {
				if (name.equals("length")) {
					name = "[int length]";
				} else {
					name = "\"" + name + "\" : ???";
				}
			} else {
				name = "[" + this.field.toString() + "]";
			}
		}
		return name;
	}

	/*
	 * You can override these two methods in subclasses of SimpleNode to
	 * customize the way the node appears when the tree is dumped. If your
	 * output uses more than one line you should override toString(String),
	 * otherwise overriding toString() is probably all you need to do.
	 */
	public String toString() {
		return "." + getFieldName();
	}

	/*
	 * Override this method if you want to customize how the node dumps out its
	 * children.
	 */
//	public void dump(String prefix) {
//		System.out.println(toString(prefix));
//	}

	public void checkContext(SimpleNode recipient) throws ASTWrongTypeException {
		Class<?> recClass = recipient.getNodeClass();
		String fieldName = ((ASTMemberName) jjtGetChild(0)).name;

		if (recClass.isArray()) {
			if (fieldName.equals("length")) {
				setNodeClass(Integer.TYPE);
				return;
			}
		}

		try {
			this.field = recClass.getField(fieldName);
			setNodeClass(this.field.getType());
		} catch (NoSuchFieldException ex1) {
			throw new ASTMemberException("No field " + getFieldName()
					+ " in class " + recClass.toString() + Jex.addMessage(ex1));
		} catch (SecurityException ex2) {
			throw new ASTMemberException(
					"Security violation while looking up field "
							+ getFieldName() + " in class "
							+ recClass.toString() + Jex.addMessage(ex2));
		}
	}

	public void interpret(SimpleNode recipient) {
		Class<?> recClass = recipient.getNodeClass();
		String fieldName = ((ASTMemberName) jjtGetChild(0)).name;

		if (recClass.isArray()) {
			if (fieldName.equals("length")) {
//				stack[++top] = new Integer(Array.getLength(stack[top - 1]));
				stack.add(++top, new Integer(Array.getLength(stack.get(top-1))));
			}
			return;
		}

		if (this.field == null) {
			checkContext(recipient);
		}
		try {
//			stack[++top] = field.get(stack[top - 1]);
			stack.add(++top, this.field.get(stack.get(top-1)));
		} catch (IllegalAccessException ex1) {
			throw new ASTMemberException("Cannot access field "
					+ getFieldName() + Jex.addMessage(ex1));
		} catch (IllegalArgumentException ex2) {
			throw new ASTMemberException("Accessing field " + getFieldName()
					+ " of an object of the wrong class." + Jex.addMessage(ex2));
		} catch (NullPointerException ex3) {
			throw new ASTMemberException("Accessing field " + getFieldName()
					+ " of a null object" + Jex.addMessage(ex3));
		}
	}

	public String getString() {
		return "."+jjtGetChild(0).getString();
	}

	public Node copy() {
		Node copy = super.copy();
		((ASTField) copy).field = this.field;
		return copy;
	}
}

/*
 * $Log: ASTField.java,v $
 * Revision 1.6  2010/12/07 16:36:45  olga
 * bug fixed when a static member of a static class is used in attr expression
 *
 * Revision 1.5  2010/08/23 07:31:26  olga
 * tuning
 *
 * Revision 1.4  2010/07/29 10:09:21  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.3  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
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
 * Revision 1.3 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:01 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.7 2000/04/05 12:10:05 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.6 2000/03/14 10:59:02 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

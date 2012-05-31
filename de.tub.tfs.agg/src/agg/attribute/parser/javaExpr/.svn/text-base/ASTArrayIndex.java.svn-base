package agg.attribute.parser.javaExpr;

/* JJT: 0.2.2 */

import java.lang.reflect.Array;

/**
 * @version $Id: ASTArrayIndex.java,v 1.5 2010/07/29 10:09:24 olga Exp $
 * @author $Author: olga $
 */
public class ASTArrayIndex extends SimpleNode {

	static final long serialVersionUID = 1L;

	ASTArrayIndex(String id) {
		super(id);
	}

	public static Node jjtCreate(String id) {
		return new ASTArrayIndex(id);
	}

	public static Class<?> getClassForName(String name) {
		Class<?> clazz = null;

		if (classResolver == null) {
			try {
				clazz = Class.forName(name);
			} catch (ClassNotFoundException e) {
			}
		} else {
			clazz = classResolver.forName(name);
		}
		return clazz;
	}

	/**
	 * Since there is no possibility to retrieve the dimension of an array class
	 * directly as something like: int dimensions =
	 * java.lang.reflect.Array.getNumDimensions( anArrayClass), the textual
	 * representation of the considered array class is parsed in order to
	 * determine it's dimension.
	 */
	protected static int getNumDimensions(Class<?> arrayClass) {
		String classText = arrayClass.getName();
		int nDim = 0;

		while (classText.charAt(nDim++) == '[')
			;
		return --nDim;
	}

	/**
	 * Since there is no possibility to retrieve the component class of an array
	 * class directly as something like: int componentClass =
	 * java.lang.reflect.Array.getDimensionCount( anArrayClass), the textual
	 * representation of the considered array class is parsed in order to
	 * determine it's component class.
	 */
	protected static Class<?> getComponentClass(Class<?> arrayClass) {
		String classText = arrayClass.getName();
		String compClassName = "";
		int ptr = 0;
		Class<?> compClass = null;

		while (classText.charAt(ptr++) == '[')
			;
		switch (classText.charAt(--ptr)) {
		case 'L':
			while (classText.charAt(++ptr) != ';') {
				compClassName += classText.charAt(ptr);
			}
			compClass = getClassForName(compClassName);
			break;
		case 'B':
			compClass = Byte.TYPE;
			break;
		case 'S':
			compClass = Short.TYPE;
			break;
		case 'I':
			compClass = Integer.TYPE;
			break;
		case 'J':
			compClass = Long.TYPE;
			break;
		case 'F':
			compClass = Float.TYPE;
			break;
		case 'D':
			compClass = Double.TYPE;
			break;
		case 'C':
			compClass = Character.TYPE;
			break;
		case 'Z':
			compClass = Boolean.TYPE;
			break;
		default:
		}
		if (compClass == null) {
			throw new ASTMemberException(
					"Couldn't find the component type for the array class:\n'"
							+ arrayClass.getName()
							+ "'.\nWas looking for: '"
							+ compClassName
							+ "'.\nPlease consider extending the list of searched packages\n"
							+ "(click the 'Config' button).");
		}
		return compClass;
	}

	public void checkContext(SimpleNode arrayNode) throws ASTWrongTypeException {
		Node indexNode = jjtGetChild(0);
		Class<?> arrayClass = arrayNode.getNodeClass();
		Class<?> componentClass, resultClass;
		Object arrayInst;
		int nDim;

		if (!arrayClass.isArray()) {
			throw new ASTWrongTypeException(
					null,
					"Referencing a non-array object as an array,\n"
							+ "or the array object has less dimensions than referenced.");
		}
		indexNode.checkContext();
		if (((SimpleNode)indexNode).getNodeClass() != Integer.TYPE) {
			String reqSig = "An index must be an integer number (int).";
			String foundSig = "Tried to pass an object of type\n'"
					+ ((SimpleNode)indexNode).getNodeClass() + "' as index.";
			throw new ASTWrongTypeException(reqSig, foundSig);
		}

		componentClass = getComponentClass(arrayClass);
		nDim = getNumDimensions(arrayClass) - 1;

		if (nDim == 0) {
			resultClass = componentClass;
		} else {
			int dimArray[] = new int[nDim];
			dimArray[0] = 1;
			for (int i = 1; i < nDim; i++) {
				dimArray[i] = 0;
			}
			arrayInst = Array.newInstance(componentClass, dimArray);
			resultClass = arrayInst.getClass();
		}
		setNodeClass(resultClass);
	}

	public void interpret(SimpleNode arrayNode) {

		Node indexNode = jjtGetChild(0);
		Object array, result;
		int index, length;

		array = stack.get(top);
		indexNode.interpret();
		index = ((Integer) stack.get(top--)).intValue();
		length = Array.getLength(array);
		if (index < 0) {
			throw new RuntimeException("Array index [" + index
					+ "] is negative.");
		} else if (index >= length) {
			throw new RuntimeException("Array index [" + index
					+ "] exceeds length of array [" + length + "].");
		}
		result = Array.get(array, index);
		stack.add(++top, result);
	}

	public String getString() {
		int nChildren = jjtGetNumChildren();
		String str = "";		
		for (int i = 0; i < nChildren; i++) {
			Node child = jjtGetChild(i);
			str += child.getString(); 
//			if (child instanceof ASTIntConstNode) {
//				str = ((ASTIntConstNode)child).getString();
//				break;
//			}
//			else {
//				str = child.getString();
//			}
		}
		return "[" + str + "]";
	}
}
/*
 * $Log: ASTArrayIndex.java,v $
 * Revision 1.5  2010/07/29 10:09:24  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.4  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.3  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.2  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:52 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2002/09/23 12:23:59 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:03 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:09:46 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 2000/03/14 10:58:46 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 */

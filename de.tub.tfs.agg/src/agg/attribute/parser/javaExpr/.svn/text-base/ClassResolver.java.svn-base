package agg.attribute.parser.javaExpr;

import java.lang.reflect.Array;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author $Author: olga $
 * @version $Id: ClassResolver.java,v 1.10 2010/11/29 08:59:59 olga Exp $
 */
public class ClassResolver implements java.io.Serializable {

	protected Vector<String> packages = new Vector<String>(16);

	final static protected Hashtable<String, Object> primitives = new Hashtable<String, Object>(
			16);

	public static final long serialVersionUID = 5146841301451537847L;

	public ClassResolver() {
		init();
		this.packages.addElement("java.lang");
		this.packages.addElement("java.util");
//		this.packages.addElement("com.objectspace.jgl");
	}

	private void init() {
		if (primitives.isEmpty()) {
			primitives.put("byte", Byte.TYPE);
			primitives.put("short", Short.TYPE);
			primitives.put("int", Integer.TYPE);
			primitives.put("long", Long.TYPE);
			primitives.put("float", Float.TYPE);
			primitives.put("double", Double.TYPE);
			primitives.put("char", Character.TYPE);
			primitives.put("boolean", Boolean.TYPE);
			primitives.put("void", Void.TYPE);
		}
	}

	protected int[] getArrayDimensions(String text) {
		int[] dimArray;
		int nDimensions = 0;
		int ptr = 0;

		while (ptr < text.length()) {
			// Skipping ignorable characters:
			while (ptr < text.length()
					&& Character.isWhitespace(text.charAt(ptr))) {
				ptr++;
			}

			// More input left ?
			if (ptr < text.length()) {

				// Beginning of another dimension parameter ?
				if (text.charAt(ptr++) == '[') {
					// Skipping ignorable characters:
					while (ptr < text.length()
							&& (Character.isWhitespace(text.charAt(ptr)) || Character
									.isDigit(text.charAt(ptr)))) {
						ptr++;
					}
					// Legal termination character at the end of dimension
					// parameter ?
					if (ptr < text.length() && text.charAt(ptr++) == ']') {
						// Dimension parameter processed correctly.
						nDimensions++;
					} else {
						// Syntax error.
						return null;
					}
				} else {
					// Wrong character, syntax error
					return null;
				}
			}
		}
		// No input left, all dimension parameters were successfully processed.
		dimArray = new int[nDimensions];
		for (int i = 0; i < nDimensions; i++) {
			dimArray[i] = 1;
		}
		return dimArray;
	}

	protected Class<?> getArrayClass(String name) {

		Class<?> arrayClass;
		Class<?> componentClass;
		Object arrayInst;
		int dimensions[];
		String componentText, dimensionText;
		int iBeginDim;

		iBeginDim = name.indexOf("[");
		componentText = name.substring(0, iBeginDim).trim();
		dimensionText = name.substring(iBeginDim, name.length());

		componentClass = forName(componentText);
		if (componentClass == null)
			return null;

		dimensions = getArrayDimensions(dimensionText);
		if (dimensions == null) {
			throw new ClassResolverException(
					"Syntax error in array dimensions.");
		}
		arrayInst = Array.newInstance(componentClass, dimensions);
		arrayClass = arrayInst.getClass();

		return arrayClass;
	}

	@SuppressWarnings("rawtypes")
	public Class<?> forName(String name) {
		Class<?> c;

		init();
		
		if (name.indexOf("[") != -1) {
			// it's an array class
			return getArrayClass(name);
		}

		if (name.indexOf('.') != -1) {
			// The name is a complete path
			try {
				return Class.forName(name);
			} catch (ClassNotFoundException e) {
//				System.out.println(this.getClass().getName()+" :: ClassNotFoundException: "+e.getMessage());				
				return null;
			}
		}

		// Is a primitive type?
		if (Character.isLowerCase(name.charAt(0))) {
			c = (Class) primitives.get(name);
			if (c != null) {
				return c;
			}
		}
		else {
			// Try if the class is at root
			try {
				return Class.forName(name);
			} catch (ClassNotFoundException e) {
//				System.out.println("###  "+this.getClass().getName()+" :: ClassNotFoundException: "+e.getMessage());
			}			
			// Try every import package
			for (int i = 0; i < this.packages.size(); i++) {
				try {
					return Class.forName(this.packages.elementAt(i) + "." + name);
				} catch (ClassNotFoundException e) {
//					System.out.println("######  "+this.getClass().getName()+" :: ClassNotFoundException: "+this.packages.elementAt(i)+"     "+e.getMessage());
				}
			}	
		}
		// Once here, abandon all hope
//		System.out.println(this.getClass().getName()+" :: ClassNotFoundException:  "+name);
		return null;
	}
	
	public Vector<String> getPackages() {
		return this.packages;
	}

	public void setPackages(Vector<String> packs) {
		this.packages = packs;
	}

	public String toString() {
		return hashCode() + " " + getPackages().toString();
	}
}
/*
 * $Log: ClassResolver.java,v $
 * Revision 1.10  2010/11/29 08:59:59  olga
 * tuning
 *
 * Revision 1.9  2010/11/28 22:12:22  olga
 * tuning
 *
 * Revision 1.8  2010/08/23 07:31:26  olga
 * tuning
 *
 * Revision 1.7  2008/10/07 07:44:45  olga
 * Bug fixed: usage static methods of user own classes in attribute condition
 *
 * Revision 1.6  2008/10/02 16:40:55  olga
 * - Reset host graph - bug fixed ,
 * - improved mouse event handling,
 * - Applicability of rule sequences:  save and load grammar : layout data will be saved
 * and loaded, too
 *
 * Revision 1.5  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/13 14:57:57  olga
 * Bug fixed:  insert/append class package
 *
 * Revision 1.3  2007/09/10 13:05:47  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2005/11/16 09:48:51 olga genged
 * package deleted
 * 
 * Revision 1.1 2005/08/25 11:56:51 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.5 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.4 2003/07/17 17:20:25 olga Primitive Datentypen
 * 
 * Revision 1.3 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/02/20 17:38:25 olga Tests
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.12 2001/03/14 17:30:58 olga com.objectspace.jgl addiert
 * 
 * Revision 1.11 2000/04/05 12:10:48 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.10 2000/03/16 15:41:26 olga Ausversehen fehlerhafte Version wurde
 * eingecheckt.
 * 
 * Revision 1.9 2000/03/15 08:18:36 olga Die Aenderungen betraffen nur
 * serialVersionUID in einigen Files, um alte Beispiele zu laden. Noch zu
 * klaeren ob wir die alte Beispiele am Leben erhalten wollen.
 * 
 * Revision 1.7 2000/03/03 11:32:43 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 * 
 * Revision 1.6 1999/08/17 06:30:28 shultzke java 1.2.2 hat sich wahrscheinlich
 * daran gestoert, dass nicht im Konstuktor sondern in der Dekleration
 * initialisiert wurde. packages wird jetzt im Konstruktor erzeugt.
 */

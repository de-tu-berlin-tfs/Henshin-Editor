package agg.attribute.handler.impl.javaExpr;

import java.io.IOException;
import java.util.Vector;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.parser.javaExpr.ClassResolver;
import agg.attribute.parser.javaExpr.ClassResolverException;
import agg.attribute.parser.javaExpr.SimpleNode;

/**
 * @version $Id: JexHandler.java,v 1.6 2010/09/23 08:13:35 olga Exp $
 * @author $Author: olga $
 */
public class JexHandler implements AttrHandler {

	static final long serialVersionUID = 9042008410571344426L;

	protected ClassResolver classResolver;

	// transient protected ConfigEditor configEditor = null;

	public JexHandler() {
		this.classResolver = new ClassResolver();
	}

	/**
	 * Getting the name of this handler as known by the attribute manager. Used
	 * to obtain the instance of this handler from the manager using its
	 * getHandler() method.
	 */
	static public String getLabelName() {
		return "Java Expr";
	}

	/**
	 * Called by #see ConfigEditor
	 */
	public ClassResolver getClassResolver() {
		return this.classResolver;
	}

	public String getName() {
		return getLabelName();
	}

	public HandlerType newHandlerType(String typeString)
			throws AttrHandlerException {
		Class<?> clazz;

		try {
			clazz = this.classResolver.forName(typeString);
		} catch (ClassResolverException ex) {
			throw new AttrHandlerException(ex.getMessage());
		}
		if (clazz == null) {
			throw new AttrHandlerException("Type not found");
		}
		return new JexType(this, typeString, clazz);
	}

	public HandlerExpr newHandlerExpr(HandlerType type, String exprString)
			throws AttrHandlerException {
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"JexHandler:\n->newHandlerExpr");
				
		SimpleNode.setClassResolver(this.classResolver);
		try {
			return new JexExpr(exprString, false, (JexType) type);
		} catch (AttrHandlerException ex1) {
			throw ex1;
		} finally {
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"JexHandler:\n<-newHandlerExpr");
		}
	}

	public HandlerExpr newHandlerValue(HandlerType type, Object value)
			throws AttrHandlerException {
		try {
			return new JexExpr(value, (JexType) type);
		} catch (AttrHandlerException ex1) {
			throw ex1;
		}
	}

	/*
	 * public void configEdit( Frame parent ){
	 * 
	 * if( configEditor == null ){ configEditor = new ConfigEditor( null,
	 * getName(),10, 200, this.classResolver ); } configEditor.edit( getName(), 10,
	 * 200 ); }
	 */

	/**
	 * Appending a package path to the package list.
	 * 
	 * @param packageName
	 *            The path name, e.g. "java.io"
	 */
	public void appendPackage(String packageName) {
		Vector<String> packages = this.classResolver.getPackages();
		packages.addElement(packageName);
		this.classResolver.setPackages(packages);
	}

	public void adaptParser() {
		SimpleNode.setClassResolver(this.classResolver);
	}

	// ****************************************************************************

	private void readObject(java.io.ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		/*
		 * if(this.classResolver == null) System.out.println("ClassResolver ==
		 * null"); else System.out.println(this.classResolver);
		 */
		in.defaultReadObject();
		/*
		 * System.out.println(this.classResolver);
		 */
	}
}

/*
 * $Log: JexHandler.java,v $
 * Revision 1.6  2010/09/23 08:13:35  olga
 * tuning
 *
 * Revision 1.5  2010/03/31 21:07:29  olga
 * tuning
 *
 * Revision 1.4  2007/11/01 09:58:19  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:46  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:32:59 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:57:00 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:00 olga Imported sources
 * 
 * Revision 1.9 2001/03/14 17:30:08 olga Test
 * 
 * Revision 1.8 2000/04/05 12:08:45 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.7 2000/03/14 10:57:16 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren
 * 
 * Revision 1.6 2000/03/03 11:39:43 shultzke Aus der Expression den String durch
 * abstrakten Syntaxbaum ersetzt
 */

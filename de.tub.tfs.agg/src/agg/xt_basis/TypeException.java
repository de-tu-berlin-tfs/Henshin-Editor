package agg.xt_basis;

/**
 * This Exception will be thrown if an invalid typed graph object will be added
 * to the graph
 * 
 * @version $Id: TypeException.java,v 1.4 2010/09/23 08:27:33 olga Exp $
 * @author $Author: olga $
 */
public class TypeException extends Exception {
	TypeError typeError = null;

	// TODO: JavaDoc
	public TypeException() {
	}

	public TypeException(String message) {
		super(message);
	}

	public TypeException(TypeError error) {
		super(error.getMessage());
		this.typeError = error;
	}

	public TypeError getTypeError() {
		return this.typeError;
	}

}
// $Log: TypeException.java,v $
// Revision 1.4  2010/09/23 08:27:33  olga
// tuning
//
// Revision 1.3  2009/06/02 12:39:23  olga
// Min Multiplicity check - bug fixed
//
// Revision 1.2  2007/09/10 13:05:34  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1 2005/08/25 11:56:54 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.4 2004/04/15 10:49:48 olga
// Kommentare
//
// Revision 1.3 2003/05/14 17:56:47 komm
// Added minimum multiplicity and removed TODOs
//
// Revision 1.2 2003/03/05 18:24:03 komm
// sorted/optimized import statements
//
// Revision 1.1 2002/09/23 12:24:14 komm
// added type graph in xt_basis, editor and GUI
//

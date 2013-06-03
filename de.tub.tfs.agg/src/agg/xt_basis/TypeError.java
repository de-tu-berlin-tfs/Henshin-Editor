package agg.xt_basis;

/**
 * This object stand for an error while type checking. The
 * {@link TypeSet#checkTypeGraph} and {@link TypeSet#checkType} methods will
 * return a Enumeration with such objects, if an error occured.
 * 
 * From the object you can get some information about the kind of error and the
 * wrong typed graph objects. This object will also provide a number for the
 * error occured.
 * 
 * @version $Id: TypeError.java,v 1.6 2010/09/23 08:27:33 olga Exp $
 * @author $Author: olga $
 */
public class TypeError {

	/**
	 * Error number for undefined errors. No method in the official distribution
	 * will return this, but you can use it for testing or if your error wont
	 * fit in one of the other categories. All contained objects may be null.
	 */
	public static final int NOT_DEFINED = 0;

	/**
	 * Error number if no type graph was defined. This error will occure when
	 * you try to check the type error or when you try to check some other graph
	 * with an empty type graph or no type graph. All contained objects should
	 * be null.
	 */
	public static final int NO_TYPE_GRAPH = 1;

	/**
	 * Error number if a type is not present in the type graph.
	 * {@link agg.xt_basis.TypeError#getType()} will return the missing type and
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the used
	 * type graph.
	 */
	public static final int TYPE_UNDEFINED = 2;

	/**
	 * Error number if a type is already defined in the type graph (Two nodes of
	 * the same type or two edges of the same type between the same nodes).
	 * {@link agg.xt_basis.TypeError#getType()} will return the missing type,
	 * {@link agg.xt_basis.TypeError#getGraphObject()} will return the last
	 * found graph object with this type and
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the used
	 * type graph.
	 */
	public static final int TYPE_ALREADY_DEFINED = 3;

	/**
	 * Error number you tried to remove a graph object from the type graph, but
	 * there are graph objects in the other graphs of this type.
	 * {@link agg.xt_basis.TypeError#getType()} will return the type,
	 * {@link agg.xt_basis.TypeError#getGraphObject()} will return the graph
	 * object you tried to remove or if you tried to remove the type,
	 * {@link agg.xt_basis.TypeError#getType()} will return the type.
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the used
	 * type graph.
	 */
	public static final int TYPE_IS_IN_USE = 4;

	// merge of two type sets
	/**
	 * Error number if you tried to merge two type sets and there are used types
	 * unknown (The merging will happening, if you use another TypeSet to check
	 * a graph). {@link agg.xt_basis.TypeError#getType()} will return the
	 * missing type, {@link agg.xt_basis.TypeError#getGraphObject()} will return
	 * the graph object using this type and
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the
	 * checked graph.
	 */
	public static final int TYPE_UNKNOWN_HERE = 11;

	// type check
	/**
	 * Error number if a graph object was found which type is not defined in the
	 * type graph (but defined in the TypeSet).
	 * {@link agg.xt_basis.TypeError#getType()} will return the type,
	 * {@link agg.xt_basis.TypeError#getGraphObject()} will return the graph
	 * object with the wrong type and
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the
	 * checked graph.
	 */
	public static final int NO_SUCH_TYPE = 21;

	/**
	 * Error number if there were more arcs of a type as allowed by the type
	 * graph. {@link agg.xt_basis.TypeError#getType()} will return the type,
	 * {@link agg.xt_basis.TypeError#getGraphObject()} will return the last
	 * found graph object with this type and
	 * {@link agg.xt_basis.TypeError#getContainingGraph()} will return the
	 * checked graph. It is possible that more than one error object will be
	 * produced for one occurence of this mismatch (f.e. one for each arc).
	 */
	public static final int TO_MUCH_ARCS = 22;

	/**
	 * Error number if there were not as many arcs of a type as allowed by the
	 * type graph. {@link agg.xt_basis.TypeError#getType()} will return the type
	 * and {@link agg.xt_basis.TypeError#getContainingGraph()} will return the
	 * checked graph.
	 */
	public static final int TO_LESS_ARCS = 23;

	public static final int TO_LESS_NODES = 24;

	public static final int TO_MUCH_NODES = 25;

	public static final int PARENT_NOT_ALLOWED = 26;

	public static final int NOT_COMPATIBLE_TYPE = 27;

	public static final int UNKNOWN_ERROR = 28;
	
	public static final int NO_PARALLEL_ARC = 29;

	/**
	 * a short error message
	 */
	String message = null;

	/**
	 * a number describing the error
	 */
	int errorNumber = 0;

	/**
	 * the invalid GraphObject
	 */
	GraphObject wrongObject = null;

	/**
	 * the invalid Type
	 */
	Type wrongType = null;

	/**
	 * the graph, which was checked
	 */
	Graph containingGraph = null;

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * 
	 * @see agg.xt_basis.TypeError#setContainingGraph(Graph)
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#NO_TYPE_GRAPH
	 */
	public TypeError(int errorNumber, String message) {
		this.message = message;
		this.errorNumber = errorNumber;
	}// TypeError(int,String)

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * @param wrongType
	 *            the invalid {@link agg.xt_basis.Type}. Which role the Type
	 *            plays is described in the comment of the error number.
	 * 
	 * @see agg.xt_basis.TypeError#setContainingGraph(Graph)
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#TYPE_UNDEFINED
	 */
	public TypeError(int errorNumber, String message, Type wrongType) {
		this.message = message;
		this.errorNumber = errorNumber;
		this.wrongType = wrongType;
	}// TypeError(int,String,Type)

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * @param wrongObject
	 *            the invalid {@link agg.xt_basis.GraphObject}.
	 * @param wrongType
	 *            the invalid {@link agg.xt_basis.Type}. Which role the
	 *            GraphObject and the Type plays is described in the comment of
	 *            the error number.
	 * 
	 * @see agg.xt_basis.TypeError#setContainingGraph(Graph)
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#NO_TYPE_GRAPH
	 * @see agg.xt_basis.TypeError#TYPE_UNDEFINED
	 * @see agg.xt_basis.TypeError#TYPE_ALREADY_DEFINED
	 * @see agg.xt_basis.TypeError#TYPE_UNKNOWN_HERE
	 * @see agg.xt_basis.TypeError#NO_SUCH_TYPE
	 * @see agg.xt_basis.TypeError#TO_MUCH_ARCS
	 * @see agg.xt_basis.TypeError#TO_LESS_ARCS
	 */
	public TypeError(int errorNumber, String message, GraphObject wrongObject,
			Type wrongType) {
		this.message = message;
		this.errorNumber = errorNumber;
		this.wrongType = wrongType;
		this.wrongObject = wrongObject;
	}// TypeError(int,String,GraphObject,Type)

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * @param containingGraph
	 *            the graph which contains the wrong objects.
	 * 
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#NO_TYPE_GRAPH
	 */
	public TypeError(int errorNumber, String message, Graph contGraph) {
		this.message = message;
		this.errorNumber = errorNumber;
		this.containingGraph = contGraph;
	}// TypeError(int,String)

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * @param wrongType
	 *            the invalid {@link agg.xt_basis.Type}. Which role the Type
	 *            plays is described in the comment of the error number.
	 * @param containingGraph
	 *            the graph which contains the wrong objects.
	 * 
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#TYPE_UNDEFINED
	 */
	public TypeError(int errorNumber, String message, Type wrongType,
			Graph containingGraph) {
		this.message = message;
		this.errorNumber = errorNumber;
		this.wrongType = wrongType;
		this.containingGraph = containingGraph;
	}// TypeError(int,String,Type)

	/**
	 * creates an error object. The values can not changed after creation.
	 * 
	 * @param errorNumber
	 *            a code for the error occured. As described above (see
	 *            {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also
	 *            defines which other parameters are set.
	 * @param message
	 *            a short english describtion of the error. The describtion
	 *            should not contain more informations as given by the
	 *            errorNumber and the other parameter.
	 * @param wrongObject
	 *            the invalid {@link agg.xt_basis.GraphObject}.
	 * @param wrongType
	 *            the invalid {@link agg.xt_basis.Type}. Which role the
	 *            GraphObject and the Type plays is described in the comment of
	 *            the error number.
	 * @param containingGraph
	 *            the graph which contains the wrong objects.
	 * 
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#NO_TYPE_GRAPH
	 * @see agg.xt_basis.TypeError#TYPE_UNDEFINED
	 * @see agg.xt_basis.TypeError#TYPE_ALREADY_DEFINED
	 * @see agg.xt_basis.TypeError#TYPE_UNKNOWN_HERE
	 * @see agg.xt_basis.TypeError#NO_SUCH_TYPE
	 * @see agg.xt_basis.TypeError#TO_MUCH_ARCS
	 * @see agg.xt_basis.TypeError#TO_LESS_ARCS
	 */
	public TypeError(int errorNumber, String message, GraphObject wrongObject,
			Type wrongType, Graph containingGraph) {
		this.message = message;
		this.errorNumber = errorNumber;
		this.wrongType = wrongType;
		this.wrongObject = wrongObject;
		this.containingGraph = containingGraph;
	}// TypeError(int,String,GraphObject,Type,Graph)

	/**
	 * returns a code for the error occured. As described above (see
	 * {@link agg.xt_basis.TypeError#NOT_DEFINED}) the code also defines which
	 * other parameters are set.
	 * 
	 * @see agg.xt_basis.TypeError#NOT_DEFINED
	 * @see agg.xt_basis.TypeError#NO_TYPE_GRAPH
	 * @see agg.xt_basis.TypeError#TYPE_UNDEFINED
	 * @see agg.xt_basis.TypeError#TYPE_ALREADY_DEFINED
	 * @see agg.xt_basis.TypeError#TYPE_UNKNOWN_HERE
	 * @see agg.xt_basis.TypeError#NO_SUCH_TYPE
	 * @see agg.xt_basis.TypeError#TO_MUCH_ARCS
	 * @see agg.xt_basis.TypeError#TO_LESS_ARCS
	 */
	public int getErrorNumber() {
		return this.errorNumber;
	}// getErrorNumber

	/**
	 * returns a short english describtion of the error. The describtion should
	 * not contain more informations as given by the errorNumber and the other
	 * parameter.
	 * 
	 * @see agg.xt_basis.TypeError#getErrorNumber()
	 */
	public String getMessage() {
		return this.message;
	}// getMessage

	/**
	 * retuns the GraphObject of error. Which role this object plays is
	 * described in the comment for the error number (see
	 * {@link agg.xt_basis.TypeError#NOT_DEFINED}).
	 */
	public GraphObject getGraphObject() {
		return this.wrongObject;
	}// getGraphObject

	/**
	 * retuns the Type of error. Which role this object plays is described in
	 * the comment for the error number (see
	 * {@link agg.xt_basis.TypeError#NOT_DEFINED}).
	 */
	public Type getType() {
		return this.wrongType;
	}// getType

	/**
	 * returns the graph which was checked and which contains the errors.
	 */
	public Graph getContainingGraph() {
		return this.containingGraph;
	}// getContainingGraph

	/**
	 * sets the graph containing the error.
	 */
	public void setContainingGraph(Graph containingGraph) {
		this.containingGraph = containingGraph;
	}// setContainingGraph

	/**
	 * returns a short string with error number and message for testing
	 * purposes.
	 */
	public String toString() {
		return "TypeError: " + this.getMessage() + " [" + this.getErrorNumber()
				+ "] in " + this.getContainingGraph().getName();
	}// toString

}// class TypeError

// $Log: TypeError.java,v $
// Revision 1.6  2010/09/23 08:27:33  olga
// tuning
//
// Revision 1.5  2007/09/10 13:05:35  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.4 2007/01/29 09:44:27 olga
// Bugs fiixed, that occur during the extension a non-attributed grammar by
// attributes.
//
// Revision 1.3 2006/08/02 09:00:57 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.2 2005/10/24 09:04:49 olga
// GUI tuning
//
// Revision 1.1 2005/08/25 11:56:54 enrico
// *** empty log message ***
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.8 2004/05/19 15:41:35 olga
// Comments
//
// Revision 1.7 2004/04/15 10:49:48 olga
// Kommentare
//
// Revision 1.6 2003/12/18 16:27:46 olga
// Tests.
//
// Revision 1.5 2003/05/14 17:56:47 komm
// Added minimum multiplicity and removed TODOs
//
// Revision 1.4 2003/03/05 13:33:05 komm
// method of type graph activation changed to type graph level
//
// Revision 1.3 2002/11/11 10:36:59 komm
// multiplicity check added
//
// Revision 1.2 2002/09/30 10:12:48 komm
// dynamic type check expanded
//
// Revision 1.1 2002/09/23 12:24:14 komm
// added type graph in xt_basis, editor and GUI
//

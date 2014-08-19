package agg.attribute.gui.impl;

/**
 * Table model for tuple editors of type and value.
 * 
 * @version $Id: TupleTableModelConstants.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public interface TupleTableModelConstants {

	// Column keys. Must be [0,1,2,...]

	public static final int UNKNOWN = 0;

	public static final int HANDLER = 1;

	public static final int TYPE = 2;

	public static final int NAME = 3;

	public static final int EXPR = 4;

	public static final int VISIBILITY = 5;

	public static final int IS_INPUT_PARAMETER = 6;

	public static final int IS_OUTPUT_PARAMETER = 7;

	public static final int CORRECTNESS = 8;

	public static final int YIELDS = 9;

	public static final int N_TUPLE_KEYS = 10;
}
/*
 * $Log: TupleTableModelConstants.java,v $
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:51 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:02 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

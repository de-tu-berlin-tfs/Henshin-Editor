package agg.attribute;

/**
 * @version $Id: AttrMatchException.java,v 1.3 2010/08/23 07:30:16 olga Exp $
 * @author $Author: olga $
 */
public class AttrMatchException extends AttrException {

	public static int VARIABLE_BINDING = 10;

	protected int id;

	protected AttrInstance firstBindingTuple;

	public AttrMatchException(String msg, int id, AttrInstance firstBindingTuple) {
		super(msg);
		this.id = id;
		this.firstBindingTuple = firstBindingTuple;
	}

	public int getID() {
		return this.id;
	}

	public AttrInstance getFirstBindingTuple() {
		return this.firstBindingTuple;
	}
}
/*
 * $Log: AttrMatchException.java,v $
 * Revision 1.3  2010/08/23 07:30:16  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:47 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:06:55 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

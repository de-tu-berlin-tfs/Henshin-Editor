package agg.attribute;

/**
 * The interface for a variable tuple member.
 * 
 * @version $Id: AttrVariableMember.java,v 1.2 2007/09/10 13:05:31 olga Exp $
 * @author $Author: olga $
 */
public interface AttrVariableMember extends AttrInstanceMember {

	static final long serialVersionUID = -8961313298573825182L;

	/** Removes this member from its tuple. */
	public void delete();

	/** Test, if the variable evaluates to a definite value. */
	public boolean isDefinite();

	/** Tests if this variable is an IN-parameter. */
	public boolean isInputParameter();

	/** Sets, if the variable is to be an IN-parameter. */
	public void setInputParameter(boolean b);

	/** Tests if this variable is an OUT-parameter. */
	public boolean isOutputParameter();

	/** Sets, if the variable is to be an OUT-parameter. */
	public void setOutputParameter(boolean b);
}
/*
 * $Log: AttrVariableMember.java,v $
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
 * Revision 1.3 2003/03/05 18:24:06 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:48 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:04 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

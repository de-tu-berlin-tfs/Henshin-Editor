package agg.attribute;

import java.util.Vector;

import agg.attribute.impl.VarMember;

/**
 * The interface for a tuple of variables.
 * 
 * @version $Id: AttrVariableTuple.java,v 1.2 2007/09/10 13:05:31 olga Exp $
 */
public interface AttrVariableTuple extends AttrInstance {
	static final long serialVersionUID = -148304387902146269L;

	/** Test, if all variables evaluate to definite values. */
	public boolean isDefinite();

	/** tests if all input parameters are set */
	public boolean areInputParametersSet();

	/**
	 * tests if all output parameters are set public boolean
	 * areOutputParametersSet();
	 */

	public int getSize();

	public VarMember getVarMemberAt(String name);

	public VarMember getVarMemberAt(int index);

	public Vector<String> getVariableNames();

}
/*
 * $Log: AttrVariableTuple.java,v $
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
 * Revision 1.9 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.8 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.7 2004/03/01 15:47:55 olga Tests
 * 
 * Revision 1.6 2004/01/22 17:51:50 olga ...
 * 
 * Revision 1.5 2003/03/05 18:24:06 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/02/03 17:46:17 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.3 2002/09/23 12:23:48 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.2 2002/09/19 16:23:54 olga Test an Output-Parameter. Noch geht es
 * nicht.
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:07:06 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

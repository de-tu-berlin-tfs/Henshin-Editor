package agg.attribute;

import java.util.Vector;

/**
 * The interface for a condition tuple.
 * 
 * @version $Id: AttrConditionTuple.java,v 1.3 2007/11/01 09:58:20 olga Exp $
 * @author $Author: olga $
 */
public interface AttrConditionTuple extends AttrInstance {
	static final long serialVersionUID = 5350620835396803108L;

	/**
	 * Adding of a condition member, returning the member. For deletion, see
	 * agg.attribute.AttrConditionMember.
	 */
	public AttrConditionMember addCondition(String expr);

	/** Test, if all members can yield true or false. */
	public boolean isDefinite();

	/** Test, if ANDing of all members yields true. */
	public boolean isTrue();

	/**
	 * Test, if the tuple contains members which can be evaluated and yield
	 * 'false'.
	 */
	public boolean isFalse();

	/** Getting all variable names of conditions. */
	public Vector<String> getAllVariables();

}
/*
 * $Log: AttrConditionTuple.java,v $
 * Revision 1.3  2007/11/01 09:58:20  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:56 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.7 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.6 2004/05/19 15:41:34 olga Comments
 * 
 * Revision 1.5 2004/05/13 17:54:09 olga Fehlerbehandlung
 * 
 * Revision 1.4 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.3 2003/03/05 18:24:06 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:45 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.4 2001/02/21 15:49:10 olga Neue Methoden fuer den Parser.
 * 
 * Revision 1.3 2000/04/05 12:06:40 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

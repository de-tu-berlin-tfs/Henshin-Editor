package agg.attribute;

import java.io.Serializable;

import agg.util.XMLObject;

/**
 * An abstract tuple member interface.
 * 
 * @version $Id: AttrMember.java,v 1.2 2007/09/10 13:05:31 olga Exp $
 * @author $Author: olga $
 */
public interface AttrMember extends Serializable, XMLObject {

	static final long serialVersionUID = 7298845703304764209L;

	/**
	 * Testing if the member is consistent and complete.
	 */
	public boolean isValid();

	/**
	 * Returns a text describing the errors in this member, or null if the
	 * member is correct.
	 */
	public String getValidityReport();

	/**
	 * Returns the tuple that contains this member.
	 */
	public AttrTuple getHoldingTuple();

	/**
	 * Returns the member name.
	 */
	public String getName();

	/**
	 * Returns the member index within the containing tuple.
	 */
	public int getIndexInTuple();

}

/*
 * $Log: AttrMember.java,v $
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:06 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:47 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.4 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.3 2000/04/05 12:06:56 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

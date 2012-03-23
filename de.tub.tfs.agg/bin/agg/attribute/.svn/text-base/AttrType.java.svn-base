package agg.attribute;

import agg.attribute.handler.AttrHandler;
import agg.attribute.view.AttrViewSetting;
import agg.util.XMLObject;

/**
 * A tuple of declarations each consisting of a type and name.
 * 
 * @version $Id: AttrType.java,v 1.3 2008/10/15 07:51:22 olga Exp $
 * @author $Author: olga $
 */
public interface AttrType extends AttrTuple, XMLObject {

	/**
	 * Adding a declaration.
	 * 
	 * @param handler
	 *            attribute handler for the entry type;
	 * @param type
	 *            textual representation of the entry type;
	 * @param name
	 *            name (selector) of the entry within the attribute tuple.
	 * @return The newly created member declaration.
	 */
	public AttrTypeMember addMember(AttrHandler handler, String type,
			String name);

	/**
	 * Adding an empty declaration. The new declaration member is returned and
	 * can be extended by calling the respective AttrTypeMember methods.
	 */
	public AttrTypeMember addMember();

	/**
	 * Delete a declaration.
	 * 
	 * @param name
	 *            name (selector) of the entry within the attribute tuple.
	 */
	public void deleteMemberAt(String name);

	/**
	 * Compares an attribute type with the current one.
	 * 
	 * @param type
	 *            attribute type to be compared with.
	 */
	public boolean compareTo(AttrType type);

	/**
	 * Delete a declaration.
	 * 
	 * @param index
	 *            index of the member within the attribute tuple.
	 */
	public void deleteMemberAt(int index);

	/**
	 * Delete a declaration.
	 * 
	 * @param viewSetting
	 *            view setting to relate to.
	 * @param slot
	 *            slot of the member within the view of the attribute tuple.
	 */
	public void deleteMemberAt(AttrViewSetting viewSetting, int slot);

	/**
	 * Returns true when the member of the slot belongs to this attribute type,
	 * false - when the member belongs to a parent type. 
	 * 
	 * @param viewSetting 
	 * 				view setting to relate to
	 * @param slot
	 * 				slot of the member within the view of the attribute tuple
	 */
	public boolean isOwnMemberAt(AttrViewSetting viewSetting, int slot);
	
	//
	// Deprecated
	//

	/**
	 * Adding a declaration.
	 * 
	 * @param handler
	 *            attribute handler for the entry type;
	 * @param type
	 *            textual representation of the entry type;
	 * @param name
	 *            name (selector) of the entry within the attribute tuple.
	 * @deprecated
	 * @see #addMember
	 */
	public void addEntry(AttrHandler handler, String type, String name);

	/**
	 * Delete a declaration.
	 * 
	 * @param name
	 *            name (selector) of the entry within the attribute tuple.
	 * @deprecated
	 * @see #deleteMemberAt( String )
	 */
	public void deleteEntry(String name);
}

/*
 * $Log: AttrType.java,v $
 * Revision 1.3  2008/10/15 07:51:22  olga
 * Delete attr. member of parent type : error message dialog to warn the user
 *
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log
 * message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:07 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:47 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.7 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.6 2000/04/05 12:07:01 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

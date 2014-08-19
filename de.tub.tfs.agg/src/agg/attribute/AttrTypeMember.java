package agg.attribute;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerType;

/**
 * The interface for a member of an attribute type.
 * 
 * @version $Id: AttrTypeMember.java,v 1.2 2007/09/10 13:05:31 olga Exp $
 * @author $Author: olga $
 */
public interface AttrTypeMember extends AttrMember {

	static final long serialVersionUID = 8204242617031541644L;

	/** Removes itself from the tuple. */
	public void delete();

	/** Retrieving its name. */
	public String getName();

	/** Setting a name. */
	public void setName(String name);

	/**
	 * Retrieving its type. Returns null if no type is set or if the type is not
	 * valid.
	 */
	public HandlerType getType();

	/** Retrieving its type name as string. */
	public String getTypeName();

	/** Setting its type. */
	public void setType(String typeName);

	/** Retrieving its attribute handler. */
	public AttrHandler getHandler();

	/** Setting its attribute handler. */
	public void setHandler(AttrHandler h);

	public boolean compareTo(AttrTypeMember mem); // NEU: von Gabi

	// public void retype( AttrHandler handler, String typeString );
}
/*
 * $Id: AttrTypeMember.java,v 1.2 2007/09/10 13:05:31 olga Exp $ $Log:
 * AttrTypeMember.java,v $ Revision 1.1 2005/08/25 11:56:55 enrico *** empty log
 * message ***
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
 * Revision 1.4 2000/04/05 12:07:03 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.3 1999/10/08 12:04:57 stefan Aenderungen von Kay und Gabi
 * uebernommen.
 */


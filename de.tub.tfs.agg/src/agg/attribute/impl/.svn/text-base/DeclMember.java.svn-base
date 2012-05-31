package agg.attribute.impl;

import agg.attribute.AttrEvent;
import agg.attribute.AttrTuple;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerType;
import agg.util.XMLHelper;

/**
 * Keeps the declaration name, type and the type's handler.
 * 
 * @version $Id: DeclMember.java,v 1.13 2010/11/28 22:11:37 olga Exp $
 * @author $Author: olga $
 */
public class DeclMember extends Member implements AttrMsgCode, AttrTypeMember {

	static final long serialVersionUID = -1967468240702798334L;

	/** The Tuple containing this declaration. */
	protected DeclTuple tuple;

	/** The type of this declaration. */
	protected HandlerType type;

	/** Type name. */
	protected String typeName;

	/** The name of this declaration. */
	protected String name;

	/** The attribute handler that created the type. */
	protected AttrHandler handler;

	/** Flag if the the member name is unique within its tuple. */
	protected boolean isNameValid = false;

	/** Last error message from the attribute handler. */
	protected String handlerMessage;

	protected boolean visible;

	public DeclMember(DeclTuple tuple) {
		super();
		this.tuple = tuple;
		this.visible = true;
	}

	/** Constructing all at once. */
	public DeclMember(DeclTuple tuple, AttrHandler handler, String typeString,
			String name) {
		this(tuple);
		setName(name);
		retype(handler, typeString);
	}

	// Internal methods.

	protected void retype(AttrHandler attrhandler, String typename) {
		// System.out.println("DeclMember.retype ");
		this.handler = attrhandler;
		this.typeName = typename.replaceAll(" ", "");
		this.type = null;
		this.handlerMessage = null;
		if (attrhandler == null)
			return;
		try {
			this.type = attrhandler.newHandlerType(this.typeName);
		} catch (AttrHandlerException ex) {
			this.typeName = null;
			this.handlerMessage = ex.getMessage();
		}
		// ((TupleObject) getHoldingTuple()).memberChanged(
		// AttrEvent.MEMBER_RETYPED, this );
		fireChanged(AttrEvent.MEMBER_RETYPED);
	}

	// Public Methods.

	public void delete() {
		getTuple().deleteMemberAt(getTuple().getIndexForMember(this));
	}

	/**
	 * Setting if the name is valid (unique in the tuple). Called by DeclTuple.
	 */
	public void setNameValid(boolean b) {
		this.isNameValid = b;
	}

	// AttrTypeMember interface implementation.

	public boolean isValid() {
		return this.isNameValid && this.type != null;
	}

	public boolean isDefined() {
		return  this.typeName != null && this.name != null 
				&& !"".equals(this.typeName) && !"".equals(this.name);
	}
	
	public String getValidityReport() {
		if (isValid())
			return null;
		String report = "-------- DECLARATION : --------\n";
		if (this.handler == null)
			report += "No attribute handler.\n";
		if (this.typeName == null)
			report += "No type.\n";
		if (this.handlerMessage != null)
			report += this.handlerMessage + "\n";
		if (this.name == null)
			report += "No name.\n";
		else if (!this.isNameValid)
			report += "Name is not unique or a Java data class name.\n";

		return report;
	}

	/** Retrieving its attribute handler. */
	public AttrHandler getHandler() {
		return this.handler;
	}

	/** Setting its attribute handler. */
	public void setHandler(AttrHandler h) {
		this.handler = h;
		if (this.typeName != null) {
			retype(h, this.typeName);
		}
	}

	/** Retrieving its type. */
	public HandlerType getType() {
		return this.type;
	}

	/** Retrieving its type name as string. */
	public String getTypeName() {
		return this.typeName;
	}

	/** Setting its type. */
	public void setType(String typeName) {
		if (this.handler != null) {
			retype(this.handler, typeName);
		}
	}

	/** Retrieving its name. */
	public String getName() {
		return this.name;
	}

	/** Setting a name. */
	public void setName(String name) {
		if (getTuple() == null)
			return;
		
		String prevName = this.name;
		this.name = name.replaceAll(" ", "");
		getTuple().checkNameValidity(this.name);
		if (!this.isNameValid)
			this.name = prevName;

		fireChanged(AttrEvent.MEMBER_RENAMED);
	}

	public void setVisible(boolean vis) {
		this.visible = vis;
	}

	public boolean isVisible() {
		return this.visible;
	}

	public AttrTuple getHoldingTuple() {
		return getTuple();
	}

	protected DeclTuple getTuple() {
		return this.tuple;
	}

	protected AttrTupleManager getManager() {
		return this.getTuple().getManager();
	}

	/**
	 * This member and the specified member must be valid: its type and name is not empty string
	 * and the type is available in case of a Class.
	 * Compares the handler name, the type name and the name 
	 * of this member and the specified member. 
	 * Returns true, if all properties are equal, otherwise - false.
	 */
	public boolean compareTo(AttrTypeMember mem) {
		if (mem != null
				&& this.isValid() && mem.isValid()				
				&& getHandler().getName().equals(mem.getHandler().getName())
				&& getTypeName().equals(mem.getTypeName())
				&& getName().equals(mem.getName())) {
			return true;
		}		
		return false;
	}

	/**
	 * This member and the specified member must be defined: its type and name is not empty string.
	 * Compares the handler name, the type name and the name 
	 * of this member and the specified member. 
	 * Returns true, if all properties are equal, otherwise - false.
	 */
	public boolean weakcompareTo(AttrTypeMember mem) {
		if (mem != null
				&& this.isDefined()			
				&& getHandler().getName().equals(mem.getHandler().getName())) {
			return true;			
		}		
		return false;
	}
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("AttrType", this);
		h.addAttr("typename", getTypeName());
		h.addAttr("attrname", getName());
		if (this.visible)
			h.addAttr("visible", "true");
		else
			h.addAttr("visible", "false");
		h.close();
	}

	
	public void XreadObject(XMLHelper h) {
		if (h.isTag("AttrType", this)) {
			this.typeName = h.readAttr("typename");	
			this.name = h.readAttr("attrname");
			// this check was needed because reported garbled names of the attributes
			this.name = XMLHelper.checkNameDueToSpecialCharacters(this.name);
			
//			if (name.indexOf('¬') != -1) {
//				String test = "";
//				for(int i=0; i<name.length(); i++) {
//					Character ch = Character.valueOf(name.charAt(i));
//					if (Character.getNumericValue(name.charAt(i)) == -1) {
//						if (ch.charValue() == '('
//							|| ch.charValue() == ')'
//								|| ch.charValue() == '_'
//								|| ch.charValue() == ','
//								|| ch.charValue() == '.'
//								|| ch.charValue() == '¬'
//								|| ch.charValue() == '&'
//								|| ch.charValue() == '|'
//								|| ch.charValue() == '^') {
//							test = test.concat(String.valueOf(ch));
//						} 
//					} else {
//						test = test.concat(String.valueOf(ch));
//					}					
////					System.out.println(name.charAt(i)
////							+"   "+Character.getNumericValue(name.charAt(i))
////							+" type "+Character.getType(name.charAt(i))
////							+" def "+Character.isDefined(name.charAt(i))
////							+" ISOcont  "+Character.isISOControl(name.charAt(i)));
//				}
//				while (test.indexOf("¬¬") != -1) {
//					test = test.replaceAll("¬¬", "¬");
//				}
//				name = test;
//			}
			
			String visiblestr = h.readAttr("visible");
			if ("true".equals(visiblestr))
				this.visible = true;
			else if ("false".equals(visiblestr))
				this.visible = false;
			h.close();
		}
	}
}

/*
 * $Log: DeclMember.java,v $
 * Revision 1.13  2010/11/28 22:11:37  olga
 * new method
 *
 * Revision 1.12  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.11  2010/03/21 21:22:54  olga
 * tuning
 *
 * Revision 1.10  2010/03/08 15:37:22  olga
 * code optimizing
 *
 * Revision 1.9  2009/02/12 13:03:38  olga
 * Some optimization of match searching
 *
 * Revision 1.8  2008/07/14 07:35:46  olga
 * Applicability of RS - new option added, more tuning
 * Node animation - new animation parameter added,
 * Undo edit manager - possibility to disable it when graph transformation
 * because it costs much more time and memory
 *
 * Revision 1.7  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.6  2007/09/24 09:42:34  olga
 * AGG transformation engine tuning
 *
 * Revision 1.5  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.4 2006/08/02 09:00:57 olga Preliminary
 * version 1.5.0 with - multiple node type inheritance, - new implemented
 * evolutionary graph layouter for graph transformation sequences
 * 
 * Revision 1.3 2006/04/03 08:57:50 olga New: Import Type Graph and some bugs
 * fixed
 * 
 * Revision 1.2 2005/11/03 13:03:40 olga Tests
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.8 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.7 2004/04/28 12:46:38 olga test CSP
 * 
 * Revision 1.6 2004/04/19 12:19:13 olga DeclMember name ohne Blank
 * 
 * Revision 1.5 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.4 2002/10/30 18:06:43 olga Aenderung an der XML Ausgabe von
 * Values.
 * 
 * Revision 1.3 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.2 2002/09/23 12:23:57 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.9 2000/12/07 14:23:34 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.8 2000/06/05 13:29:13 olga In compareTo eine Abfrage auf null
 * eingebaut.
 * 
 * Revision 1.7 2000/05/17 11:56:58 olga Testversion an Gabi mit diversen
 * Aenderungen. Fehler sind moeglich!!
 * 
 * Revision 1.6 2000/04/05 12:09:10 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 1999/10/11 14:23:32 shultzke serialUID set
 * 
 * Revision 1.4 1999/10/11 13:59:58 olga *** empty log message ***
 * 
 * Revision 1.3 1999/10/11 10:23:35 shultzke kleine Bugfixes
 */

package agg.attribute.handler.gui;

import java.awt.Component;

import agg.attribute.handler.AttrHandler;

/**
 * This interface allows to interactively customize an attribute handler.
 * 
 * @version $Id: HandlerCustomizingEditor.java,v 1.1 2005/08/25 11:56:57 enrico
 *          Exp $
 * @author $Author: olga $
 */
public interface HandlerCustomizingEditor extends HandlerEditor {

	/**
	 * Returns a graphical component for customizing the handler (e.g. setting
	 * and changing options).
	 */
	public Component getComponent();

	public AttrHandler getAttrHandler();

	public void setAttrHandler(AttrHandler handler);
}
/*
 * $Log: HandlerCustomizingEditor.java,v $
 * Revision 1.2  2007/09/10 13:05:52  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:27 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:58 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:22 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

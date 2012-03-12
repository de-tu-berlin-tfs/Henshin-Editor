package agg.attribute.facade;

import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.gui.AttrContextEditor;
import agg.attribute.gui.AttrTopEditor;
import agg.attribute.gui.AttrTupleEditor;

/**
 * @version $Id: EditorFacade.java,v 1.2 2007/09/10 13:05:50 olga Exp $
 * @author $Author: olga $
 */
public interface EditorFacade {

	/**
	 * Returns the default attribute manager.
	 */
	public AttrManager getAttrManager();

	/**
	 * Returns a comprehensive editor, allowing to edit: - an attribute tuple,
	 * including its type member properties, such as the member handlers, types
	 * and names; - the hiding and moving of members is possible; - a rule or
	 * match context (its variables and application conditions); - options of
	 * the attribute component (customization).
	 */
	public AttrTopEditor getTopEditor();

	/**
	 * Returns a compact editor for an attribute tuple instance, showing the
	 * members' types, names and the instance members, where only the latter can
	 * be changed.
	 */
	public AttrTupleEditor getSmallEditorForInstance(AttrInstance inst);

	/**
	 * Setting ("loading") an attribute tuple instance into an attribute tuple
	 * editor (or into a "top editor", which is a subclass thereof.
	 */
	public void editInstance(AttrTupleEditor ed, AttrInstance inst);

	/**
	 * Setting ("loading") an attribute (rule or match) context into a "top
	 * editor".
	 */
	public void editContext(AttrContextEditor ed, AttrContext ctx);

	public AttrTupleEditor getInputParameterEditor();
}
/*
 * $Log: EditorFacade.java,v $
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:28 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:56 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:07:18 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

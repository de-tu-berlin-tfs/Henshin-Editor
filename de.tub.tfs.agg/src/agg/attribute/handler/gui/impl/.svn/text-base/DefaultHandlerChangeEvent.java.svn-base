package agg.attribute.handler.gui.impl;

import java.util.EventObject;

import agg.attribute.handler.gui.HandlerChangeEvent;
import agg.attribute.handler.gui.HandlerEditor;

/**
 * @version $Id: DefaultHandlerChangeEvent.java,v 1.1 2005/08/25 11:56:58 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class DefaultHandlerChangeEvent extends EventObject implements
		HandlerChangeEvent {

	static final long serialVersionUID = 247608297125283454L;

	public DefaultHandlerChangeEvent(HandlerEditor src) {
		super(src);
	}

	public HandlerEditor getSourceEditor() {
		return (HandlerEditor) getSource();
	}
}
/*
 * $Log: DefaultHandlerChangeEvent.java,v $
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:52 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:59 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:34 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

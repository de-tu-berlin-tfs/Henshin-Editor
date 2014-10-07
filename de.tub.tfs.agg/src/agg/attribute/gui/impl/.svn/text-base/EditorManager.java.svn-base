package agg.attribute.gui.impl;

import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.gui.AttrTopEditor;
import agg.attribute.gui.AttrTupleEditor;
import agg.attribute.handler.gui.HandlerEditorManager;
import agg.attribute.handler.gui.impl.SampleHandlerEditorManager;
import agg.attribute.view.AttrViewSetting;

//import javax.swing.*;
//import javax.swing.table.*;
//import javax.swing.border.*;
//import java.awt.*;

/**
 * @version $Id: EditorManager.java,v 1.3 2010/08/25 08:22:29 olga Exp $
 * @author $Author: olga $
 */
public class EditorManager extends Object implements AttrEditorManager {

	// Initialization

	protected static EditorManager myOnlyInstance = new EditorManager();

	protected HandlerEditorManager handlerManager = SampleHandlerEditorManager
			.self();

	protected EditorManager() {
		super();
	}

	public static EditorManager self() {
		return myOnlyInstance;
	}

	// Interface implementation

	public AttrTopEditor getTopEditor(AttrManager m, AttrViewSetting v) {
		AttrTopEditor ed = new TopEditor(m, this);
		ed.setViewSetting(v);
		return ed;
	}

	public AttrTupleEditor getSmallEditorForInstance(AttrManager m,
			AttrViewSetting v, AttrInstance inst) {
		AttrTupleEditor ed = new LightInstanceEditor(m, this);
		ed.setViewSetting(v);
		ed.setTuple(inst);
		return ed;
	}

	public HandlerEditorManager getHandlerEditorManager() {
		return this.handlerManager;
	}

	public InputParameterEditor getInputParameterEditor(AttrManager m) {
		return new InputParameterEditor(m, this);
	}
}
/*
 * $Log: EditorManager.java,v $
 * Revision 1.3  2010/08/25 08:22:29  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:50 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:07:47 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

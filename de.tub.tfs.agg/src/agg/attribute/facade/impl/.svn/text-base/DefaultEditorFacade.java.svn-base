package agg.attribute.facade.impl;

import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrManager;
import agg.attribute.facade.EditorFacade;
import agg.attribute.gui.AttrContextEditor;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.gui.AttrTopEditor;
import agg.attribute.gui.AttrTupleEditor;
import agg.attribute.gui.impl.EditorManager;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.view.AttrViewSetting;

/**
 * @version $Id: DefaultEditorFacade.java,v 1.4 2010/08/25 00:31:30 olga Exp $
 * @author $Author: olga $
 */
public class DefaultEditorFacade implements EditorFacade {

	protected static DefaultEditorFacade myOnlyInstance = new DefaultEditorFacade();

	protected AttrEditorManager factory = EditorManager.self();

	protected DefaultEditorFacade() {
		super();
	}

	public static EditorFacade self() {
		return myOnlyInstance;
	}

	public AttrManager getAttrManager() {
		return AttrTupleManager.getDefaultManager();
	}

	protected AttrViewSetting getDefaultIfNull(AttrViewSetting v) {
		if (v == null) 
			return getAttrManager().getDefaultOpenView();
		return v;
	}

	public AttrTopEditor getTopEditor() {
		return getTopEditor(null);
	}

	public AttrTopEditor getTopEditor(AttrViewSetting v) {
		return this.factory.getTopEditor(getAttrManager(), getDefaultIfNull(v)
				.getOpenView());
	}

	public AttrTupleEditor getSmallEditorForInstance(AttrInstance inst) {
		return getSmallEditorForInstance(inst, null);
	}

	public AttrTupleEditor getSmallEditorForInstance(AttrInstance inst,
			AttrViewSetting v) {
		return this.factory.getSmallEditorForInstance(getAttrManager(),
				getDefaultIfNull(v).getMaskedView(), inst);
	}

	public void editInstance(AttrTupleEditor ed, AttrInstance inst) {
		ed.setTuple(inst);
	}

	public void editContext(AttrContextEditor ed, AttrContext ctx) {
		ed.setContext(ctx);
	}

	public AttrTupleEditor getInputParameterEditor() {
		return this.factory.getInputParameterEditor(getAttrManager());
	}
}
/*
 * $Log: DefaultEditorFacade.java,v $
 * Revision 1.4  2010/08/25 00:31:30  olga
 * tuning
 *
 * Revision 1.3  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:25 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:56 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:07:22 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

package agg.attribute.handler.gui.impl;

import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.handler.gui.HandlerChangeEvent;
import agg.attribute.handler.gui.HandlerEditor;
import agg.attribute.handler.gui.HandlerEditorObserver;

/**
 * Facilitates observer handling.
 * 
 * @version $Id: AbstractHandlerEditor.java,v 1.4 2010/08/18 09:25:16 olga Exp $
 * @author $Author: olga $
 */
public class AbstractHandlerEditor extends Object implements HandlerEditor {

	/**
	 * Container with observers of this instance, all of which implement the
	 * HandlerEditorObserver interface.
	 */
	final protected transient Vector<HandlerEditorObserver> observers = new Vector<HandlerEditorObserver>(
			10, 10);

	public AbstractHandlerEditor() {
		super();
	}

	public void addEditorObserver(HandlerEditorObserver obs) {
		if (!this.observers.contains(obs)) {
			this.observers.addElement(obs);
		}
	}

	public void removeEditorObserver(HandlerEditorObserver obs) {
		this.observers.removeElement(obs);
	}

	protected void fireEditingCancelled() {
		fireEditingCancelled(new DefaultHandlerChangeEvent(this));
	}

	protected void fireEditingCancelled(HandlerChangeEvent evt) {
		HandlerEditorObserver obs;

		for (Enumeration<HandlerEditorObserver> en = this.observers.elements(); en.hasMoreElements();) {
			obs = en.nextElement();
			obs.editingCancelled(evt);
		}
	}

	protected void fireEditingStopped() {
		fireEditingStopped(new DefaultHandlerChangeEvent(this));
	}

	protected void fireEditingStopped(HandlerChangeEvent evt) {
		HandlerEditorObserver obs;

		for (Enumeration<HandlerEditorObserver> en = this.observers.elements(); en.hasMoreElements();) {
			obs = en.nextElement();
			obs.editingStopped(evt);
		}
	}

}
/*
 * $Log: AbstractHandlerEditor.java,v $
 * Revision 1.4  2010/08/18 09:25:16  olga
 * tuning
 *
 * Revision 1.3  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.4 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:52 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:59 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:08:31 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

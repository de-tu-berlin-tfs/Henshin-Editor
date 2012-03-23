package agg.attribute.gui.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyEditorSupport;

import javax.swing.JComponent;
import javax.swing.JPanel;

import agg.attribute.AttrManager;
import agg.attribute.gui.AttrEditor;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.handler.gui.HandlerEditorManager;

/**
 * Editor for all data of a value tuple.
 * 
 * @author $Author: olga $
 * @version $Id: AbstractEditor.java,v 1.3 2007/11/05 09:18:19 olga Exp $
 */
public abstract class AbstractEditor extends PropertyEditorSupport implements
		AttrEditor, ComponentListener {

	protected AttrManager attrManager;

	protected AttrEditorManager editorManager;

	protected JPanel mainPanel;

	public AbstractEditor(AttrManager m, AttrEditorManager em) {
		super();
		this.attrManager = m;
		this.editorManager = em;
		genericCreateAllViews(); // Generic: override, please;
		genericCustomizeMainLayout(); // Generic: override, please;
	}

	// Internal

	/** Creates all subviews. */
	protected abstract void genericCreateAllViews();

	/** Must create mainPanel and set it up. */
	protected abstract void genericCustomizeMainLayout();

	/** Convenience method. */
	protected HandlerEditorManager getHandlerEditorManager() {
		return getEditorManager().getHandlerEditorManager();
	}

	/** Sets the size, if it is not 0. */
	protected void setComponentSize(JComponent c, Dimension size) {
		if (size.width > 0 && size.height > 0) {
			c.setMinimumSize(size);
		}
	}

	// Implementation of the AttrEditor interface

	public AttrManager getAttrManager() {
		return this.attrManager;
	}

	public void setAttrManager(AttrManager m) {
		this.attrManager = m;
	}

	public AttrEditorManager getEditorManager() {
		return this.editorManager;
	}

	public void setEditorManager(AttrEditorManager m) {
		this.editorManager = m;
	}

	/**
	 * Implemented: returns always 'mainPanel'. As long as extending classes
	 * have their component hierarchy placed in this 'mainPanel', they don't
	 * have to redefine this method.
	 */
	public Component getComponent() {
		arrangeMainPanel();
		return this.mainPanel;
	}

	/**
	 * sets up some internal stuff this method has to be overriden by subclasses
	 */
	protected abstract void arrangeMainPanel();

	// PropertyEditor

	/** Returns true. */
	public boolean isPaintable() {
		return true;
	}

	/** Returns null. */
	public String getAsString() {
		return null;
	}

	/** Returns true. */
	public boolean supportsCustomEditor() {
		return true;
	}

	/** Same as #getComponent(). */
	public Component getCustomEditor() {
		return getComponent();
	}

	// ComponentListener

	public void componentResized(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentHidden(ComponentEvent e) {
	}
}

/*
 * $Log: AbstractEditor.java,v $
 * Revision 1.3  2007/11/05 09:18:19  olga
 * code tuning
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
 * Revision 1.3 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:48 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.9 2000/04/05 12:07:39 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.8 2000/01/17 10:50:35 shultzke check out error test nothing edited
 * 
 * Revision 1.7 1999/12/22 12:37:15 shultzke The user cannot edit the context of
 * graphs. Only in rules it is possible.
 */

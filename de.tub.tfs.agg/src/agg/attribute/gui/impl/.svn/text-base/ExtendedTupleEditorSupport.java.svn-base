package agg.attribute.gui.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agg.attribute.AttrInstance;
import agg.attribute.AttrInstanceMember;
import agg.attribute.AttrManager;
import agg.attribute.AttrMember;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.gui.AttrEditorManager;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.util.RowDragEvent;
import agg.attribute.util.RowDragListener;
import agg.attribute.util.TableRowDragger;
import agg.attribute.view.AttrViewEvent;

/**
 * Editor for all data of a tuple.
 * 
 * @author $Author: olga $
 * @version $Id: ExtendedTupleEditorSupport.java,v 1.2 2005/11/21 09:20:45 olga
 *          Exp $
 */
public abstract class ExtendedTupleEditorSupport extends BasicTupleEditor
		implements ListSelectionListener, RowDragListener {

	// Actions for tool bars and menus.

	/** Action for removing a member from the edited tuple. */
	protected Action deleteAction;

	/** Action for evaluating a member of the edited tuple. */
	protected Action evaluateAction;

	/** Resetting the layout of the edited tuple. */
	protected Action resetAction;

	/** Making all members visible. */
	protected Action showAllAction;

	/** Making all members invisible. */
	protected Action hideAllAction;

	/**
	 * Collections of tuple actions, used for collectively enabling/disabling
	 * actions depending on the state of the editor.
	 */
	protected Vector<Action> tupleActions;

	/**
	 * Collections of member actions, used for collectively enabling/disabling
	 * actions depending on the state of the editor.
	 */
	protected Vector<Action> memberActions;

	// State indicators

	/** Indicates if row selection is enabled. */
	protected boolean rowSelectionEnabled;

	/** Indicates if row dragging is enabled. */
	protected boolean rowDraggingEnabled;

	/** The row dragger instance. */
	protected TableRowDragger rowDragger;

	/**
	 * When disabling of dragging was requested while dragging was active, the
	 * operation is performed later, as soon as dragging stops.
	 */
	protected boolean rowDraggingDisablingRequested;

	// Widgets

	protected JTextArea outputTextArea;

	protected JScrollPane outputScrollPane;

	protected JPanel toolBarPanel;

	protected JSplitPane tableAndOutputSplitPane;

	// ///////////////////////////////////////////
	// Constructing
	// 

	/** Creating the tuple editor. */
	public ExtendedTupleEditorSupport(AttrManager m, AttrEditorManager em) {
		super(m, em);
	}

	protected void createTableView() {
		super.createTableView();
		// Reordering of columns.
		this.tableView.getTableHeader().setReorderingAllowed(true);

		// Enable row selection and -dragging.
		setRowSelectionEnabled(true);
		setRowDraggingEnabled(true);

		// Decorating.
		// tableScrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED));
	}

	protected void genericCreateAllViews() {
		createTableView();
		createOutputTextArea();
		createToolBar();
	}

	/**
	 * Called by createTableView(). Makes selecting of rows possible or not.
	 * Calling this method with 'true' is necessary if one desires any member
	 * actions (deleting, evaluating). Can also be called from 'outside', even
	 * in the middle of a session.
	 */
	public void setRowSelectionEnabled(boolean b) {
		// AttrSession.logPrintln(this+": setRowSelectionEnabled("+b+")");
		if (b) {
			// Selecting of rows, not columns:
			this.tableView.setRowSelectionAllowed(true);
			this.tableView.setColumnSelectionAllowed(false);

			// Just one row at a time:
			this.tableView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

			// Listen to selection events:
			if (!this.rowSelectionEnabled) {
				this.rowSelectionEnabled = true;
				this.tableView.getSelectionModel().addListSelectionListener(this);
			}
		} else {
			// Selecting of neither rows nor columns:
			this.tableView.setRowSelectionAllowed(false);
			this.tableView.setColumnSelectionAllowed(false);

			// Stop listening to selection events:
			if (this.rowSelectionEnabled) {
				this.rowSelectionEnabled = false;
				this.tableView.getSelectionModel().removeListSelectionListener(this);
			}
		}
	}

	/**
	 * Called by createTableView(). Makes dragging of rows possible or not.
	 * Calling this method with 'true' is necessary if one desires moving of
	 * rows around. Can also be called from 'outside', even in the middle of a
	 * session.
	 */
	public void setRowDraggingEnabled(boolean b) {
		// AttrSession.logPrintln(this + ": setRowDraggingEnabled("+b+")");
		if (b) {
			if (this.rowDragger == null) {
				this.rowDragger = new TableRowDragger(this.tableView);
			}
			if (!this.rowDraggingEnabled) {
				this.rowDraggingEnabled = true;
				this.rowDragger.addRowDragListener(this);
			}
		} else {
			if (this.rowDraggingEnabled) {
				// No disabling while dragging is active. Request will be
				// honoured
				// when dragging stops.
				if (isDraggingActive()) {
					this.rowDraggingDisablingRequested = true;
				} else {
					this.rowDraggingEnabled = false;
					this.rowDragger.removeRowDragListener(this);
				}
			}
		}
	}

	/**
	 * Switching sensitivity of buttons for actions which apply to the whole
	 * tuple on or off. Typically called with 'false' when the tuple is null.
	 */
	protected void setTupleActionsEnabled(boolean b) {
		for (Enumeration<Action> en = this.tupleActions.elements(); en.hasMoreElements();) {
			en.nextElement().setEnabled(b);
		}
	}

	/**
	 * Switching sensitivity of buttons for actions which apply to single tuple
	 * members on or off. Typically called with 'false' when no member is
	 * selected.
	 */
	protected void setMemberActionsEnabled(boolean b) {
		for (Enumeration<Action> en = this.memberActions.elements(); en.hasMoreElements();) {
			en.nextElement().setEnabled(b);
		}
	}

	/** Adding a tuple action to an action container. */
	protected void addTupleAction(Action a) {
		if (this.tupleActions == null)
			this.tupleActions = new Vector<Action>(4);
		this.tupleActions.addElement(a);
	}

	/** Adding a member action to an action container. */
	protected void addMemberAction(Action a) {
		if (this.memberActions == null)
			this.memberActions = new Vector<Action>(4);
		this.memberActions.addElement(a);
	}

	//
	// "Library" of TUPLE actions that can be attached to toolbars and/or menus.

	/**
	 * Action for setting the view back: all members become visible, the order
	 * is as created.
	 */
	protected Action getResetAction() {
		if (this.resetAction != null)
			return this.resetAction; // Already defined;
		Action action;
		action = new AbstractAction("Reset") {
			public void actionPerformed(ActionEvent ev) {
				if (ExtendedTupleEditorSupport.this.tuple == null)
					return;
				ExtendedTupleEditorSupport.this.viewSetting.resetTuple(ExtendedTupleEditorSupport.this.tuple);
			}
		};
		action.putValue(Action.SHORT_DESCRIPTION,
				"Back to the original layout.");
		addTupleAction(action);
		this.resetAction = action;
		return action;
	}

	/** Action for making all members visible. */
	protected Action getShowAllAction() {
		if (this.showAllAction != null)
			return this.showAllAction; // Already defined;
		Action action;
		action = new AbstractAction("Show All") {
			public void actionPerformed(ActionEvent ev) {
				if (ExtendedTupleEditorSupport.this.tuple == null)
					return;
				// System.out.println("ExtendedTupleEditorSupport.ShowAll-actionPerformed");
				ExtendedTupleEditorSupport.this.viewSetting.setAllVisible(ExtendedTupleEditorSupport.this.tuple, true);
			}
		};
		action.putValue(Action.SHORT_DESCRIPTION,
				"Makes all tuple members visible.");
		addTupleAction(action);
		this.showAllAction = action;
		return action;
	}

	/** Action for making all members invisible. */
	protected Action getHideAllAction() {
		if (this.hideAllAction != null)
			return this.hideAllAction; // Already defined;
		Action action;
		action = new AbstractAction("Hide All") {
			public void actionPerformed(ActionEvent ev) {
				if (ExtendedTupleEditorSupport.this.tuple == null)
					return;
				// System.out.println("ExtendedTupleEditorSupport.HideAll-actionPerformed");
				ExtendedTupleEditorSupport.this.viewSetting.setAllVisible(ExtendedTupleEditorSupport.this.tuple, false);
			}
		};
		action.putValue(Action.SHORT_DESCRIPTION, "Hides all tuple members.");
		addTupleAction(action);
		this.hideAllAction = action;
		return action;
	}

	//
	// "Library" of MEMBER actions that can be attached to toolbars and/or
	// menus.

	/** Action for deleting the selected member. */
	protected Action getDeleteAction() {
		if (this.deleteAction != null)
			return this.deleteAction; // Already defined;
		Action action;
		action = new AbstractAction("Delete") {
			public void actionPerformed(ActionEvent ev) {
				if (ExtendedTupleEditorSupport.this.tuple == null)
					return;
				AttrType type = ((AttrInstance) ExtendedTupleEditorSupport.this.tuple).getType();
				int slot = ExtendedTupleEditorSupport.this.tableView.getSelectedRow();
				
				if (slot >= type.getNumberOfEntries(ExtendedTupleEditorSupport.this.viewSetting)){
					return;
				}
				else if (!type.isOwnMemberAt(ExtendedTupleEditorSupport.this.viewSetting, slot)) {
					setMessage("Cannot delete this attribute member which belongs to a parent type."
							+"  Each attribute member must be deleted from its own type tuple.");
					JOptionPane.showMessageDialog(null,
							"<html><body>"
							+"Cannot delete this attribute member which belongs to a parent type."
							+"<br>"
							+"Each attribute member must be deleted from its own type tuple.",
							" Cannot delete ",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				type.deleteMemberAt(ExtendedTupleEditorSupport.this.viewSetting, slot);
			}
		};
		action
				.putValue(Action.SHORT_DESCRIPTION,
						"Removes the selected member");
		addMemberAction(action);
		this.deleteAction = action;
		return action;
	}

	/** Evaluating the expression of the selected member. */
	protected Action getEvaluateAction() {
		if (this.evaluateAction != null)
			return this.evaluateAction; // Already defined;
		Action action;
		action = new AbstractAction("Evaluate") {
			public void actionPerformed(ActionEvent ev) {
				AttrInstanceMember member = getSelectedMember();
				if (member == null)
					return;
				HandlerExpr expr = member.getExpr();
				if (expr == null)
					return;
				try {
					expr.evaluate(((AttrInstance) ExtendedTupleEditorSupport.this.tuple).getContext());
					member.setExpr(expr);
				} catch (AttrHandlerException ex) {
					setMessage(ex.getMessage());
				}
			}
		};
		action.putValue(Action.SHORT_DESCRIPTION, "Evaluates the expression");
		addMemberAction(action);
		this.evaluateAction = action;
		return action;
	}

	//
	// A message text area.
	//

	/** Creates a text area for displaying messages. */
	protected void createOutputTextArea() {
		this.outputTextArea = new JTextArea(80, 30);
		this.outputTextArea.setRows(2);
		this.outputTextArea.setEditable(false);
		this.outputTextArea.setLineWrap(false);
		this.outputScrollPane = new JScrollPane(this.outputTextArea,
				VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// outputTA.setBackground( Color.gray );
		this.outputScrollPane.setMinimumSize(new Dimension(60, 30));
		this.outputScrollPane.setPreferredSize(new Dimension(100, 30));
	}

	/** Shows the report for the currently selected row. */
	public void displayValidityReport() {
		if (this.tuple == null)
			return;
		setMessage("");
		int row = this.tableView.getSelectedRow();
		if (row < this.tuple.getNumberOfEntries() && row >= 0)
			displayValidityReport(row);
	}

	/** Shows the report for the specified row. */
	public void displayValidityReport(int row) {
		displayValidityReport(this.tableModel.getMember(this.tuple, row));
	}

	/** Shows the report for the specified attribute member. */
	public void displayValidityReport(AttrMember m) {
		setMessage(m.getValidityReport());
	}

	/** Displays <code>text</code> in the message area, if it was created. */
	public void setMessage(String text) {
		if (this.outputTextArea == null)
			return;
		if (text != null) {
			this.outputTextArea.setText(text.replaceAll("\n", "  "));
			this.outputTextArea.setRows(2);
		}
		else
			this.outputTextArea.setText(text);
	}

	//
	// A Tool Bar:

	protected abstract void createToolBar();

	//
	// Implementing the RowDragListener interface
	//

	/** Just acknowledge the fact. */
	public void draggingStarted(RowDragEvent ev) {
		setMessage("...Moving row...");
		// System.out.println("Started dragging row "+draggedRow);
	}

	/** Here, just handle dragging disabling requests, if any. */
	public void draggingStopped(RowDragEvent ev) {
		if (this.rowDraggingDisablingRequested) {
			this.rowDraggingDisablingRequested = false;
			setRowDraggingEnabled(false);
		}
		displayValidityReport();
	}

	/** Actually moving a member wrt the current view. */
	public void draggingMoved(RowDragEvent ev) {
		/*
		 * System.out.println ("draggingMoved("+ev.getSourceRow()+", "+
		 * ev.getTargetRow()+")");
		 */
		int src = ev.getSourceRow();
		int dest = ev.getTargetRow();
		src = Math.min(src, this.tuple.getNumberOfEntries() - 1);
		dest = Math.min(dest, this.tuple.getNumberOfEntries() - 1);
		if (dest == -1 || src == -1)
			return;
		if (dest == src)
			return;
		// System.out.println("Source row="+src+"; dest. row="+dest);
		if (src < dest)
			dest++; // append if moving down the table;
		this.viewSetting.moveSlotInserting(this.tuple, src, dest);
	}

	/** Convenience method. */
	protected boolean isDraggingActive() {
		if (!this.rowDraggingEnabled) {
			return false;
		} 
		return this.rowDragger.isDraggingActive();
	}

	/**
	 * Convenience method, called by the list selection event handling method
	 * valueChanged().
	 */
	protected void memberRowSelected(int row) {
		displayValidityReport();
		setMemberActionsEnabled(true);
	}

	/**
	 * Convenience method, called by the list selection event handling method
	 * valueChanged( ListSelectionEvent ev ), when the selected row is the
	 * bottom row for adding of new members..
	 */
	protected void newRowSelected() {
		setMessage("A new member can be added in this row.");
		setMemberActionsEnabled(false);
	}

	/** ListSelectionListener interface implementation. */
	public void valueChanged(ListSelectionEvent ev) {
		if (this.tuple == null)
			return;
		if (isDraggingActive())
			return;
		int row = ev.getFirstIndex();
		if (row < this.tuple.getNumberOfEntries() && row >= 0) {
			memberRowSelected(row);
		} else if (row == this.tuple.getNumberOfEntries()) {
			newRowSelected();
		}
	}

	//
	// Public methods.
	//

	// Implementation of the TupleEditor interface

	/** Implemented as parent, plus managing enabling/disabling of actions. */
	public void setTuple(AttrTuple anAttrTuple) {
		super.setTuple(anAttrTuple);
		setTupleActionsEnabled(anAttrTuple != null);
		setMemberActionsEnabled(false);
	} // setTuple()

	public void attributeChanged(AttrViewEvent event) {
		super.attributeChanged(event);
		if (!isDraggingActive())
			displayValidityReport();
	}
}
/*
 * $Log: ExtendedTupleEditorSupport.java,v $
 * Revision 1.8  2010/08/23 07:30:02  olga
 * tuning
 *
 * Revision 1.7  2010/03/08 15:36:09  olga
 * code optimizing
 *
 * Revision 1.6  2008/10/15 07:51:22  olga
 * Delete attr. member of parent type : error message dialog to warn the user
 *
 * Revision 1.5  2008/07/09 13:34:26  olga
 * Applicability of RS - bug fixed
 * Delete not used node/edge type - bug fixed
 * AGG help - extended
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2005/11/21 09:20:45
 * olga tests
 * 
 * Revision 1.1 2005/08/25 11:56:59 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.5 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.4 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.3 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:50 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:07:48 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 1999/08/17 07:32:22 shultzke GUI leicht geaendert
 */

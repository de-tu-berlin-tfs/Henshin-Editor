package agg.attribute.util;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JTable;

/**
 * Dragging of table rows.
 * 
 * @version $Id: TableRowDragger.java,v 1.4 2010/09/23 08:15:16 olga Exp $
 * @author $Author: olga $
 */
public class TableRowDragger {

	/**
	 * Container with observers of this instance, all of which implement the
	 * RowDragListener interface.
	 */
	protected transient Vector<RowDragListener> listener = new Vector<RowDragListener>(
			10, 10);

	protected JTable tableView;

	protected boolean draggingStarted = false;

	protected int draggedRow = -1;

	protected Cursor defaultCursor;

	protected Cursor moveCursor = Cursor
			.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);

	public TableRowDragger(JTable table) {
		this.tableView = table;

		MouseMotionListener dragMotionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				int row = TableRowDragger.this.tableView.rowAtPoint(e.getPoint());
				if (row == -1)
					return;
				if (TableRowDragger.this.draggedRow != -1) {
					if (!TableRowDragger.this.draggingStarted && row != -1) {
						TableRowDragger.this.draggingStarted = true;
						TableRowDragger.this.defaultCursor = TableRowDragger.this.tableView.getCursor();
						TableRowDragger.this.tableView.setCursor(TableRowDragger.this.moveCursor);
						fireDraggingStarted(TableRowDragger.this.draggedRow);
					} else if (TableRowDragger.this.draggingStarted) {
						if (row != TableRowDragger.this.draggedRow) {
							fireDraggingMoved(TableRowDragger.this.draggedRow, row);
							TableRowDragger.this.draggedRow = row;
						}
					}
				}
			}
		};

		MouseListener dragStartStopListener = new MouseAdapter() {

			public void mousePressed(MouseEvent e) {
				int row = TableRowDragger.this.tableView.rowAtPoint(e.getPoint());
				if (row != -1) { // && row < tuple.getNumberOfEntries()){
					// System
					TableRowDragger.this.draggedRow = row;
				}
			}

			public void mouseReleased(MouseEvent e) {
				TableRowDragger.this.draggedRow = -1;
				if (TableRowDragger.this.draggingStarted) {
					TableRowDragger.this.draggingStarted = false;
					TableRowDragger.this.tableView.setCursor(TableRowDragger.this.defaultCursor);
					fireDraggingStopped();
				}
			}
		};

		this.tableView.addMouseListener(dragStartStopListener);
		this.tableView.addMouseMotionListener(dragMotionListener);
	}

	public boolean isDraggingActive() {
		return this.draggingStarted;
	}

	public void addRowDragListener(RowDragListener li) {
		if (!this.listener.contains(li)) {
			this.listener.addElement(li);
		}
	}

	public void removeRowDragListener(RowDragListener li) {
		this.listener.removeElement(li);
	}

	protected void fireDraggingStarted(int row) {
		RowDragListener li;
		RowDragEvent ev = new RowDragEvent(this, RowDragEvent.STARTED, row, row);

		for (Enumeration<RowDragListener> en = this.listener.elements(); en.hasMoreElements();) {
			li = en.nextElement();
			li.draggingStarted(ev);
		}
	}

	protected void fireDraggingStopped() {
		RowDragListener li;
		RowDragEvent ev = new RowDragEvent(this, RowDragEvent.STOPPED, -1, -1);

		for (Enumeration<RowDragListener> en = this.listener.elements(); en.hasMoreElements();) {
			li = en.nextElement();
			li.draggingStopped(ev);
		}
	}

	protected void fireDraggingMoved(int src, int dest) {
		RowDragListener li;
		RowDragEvent ev = new RowDragEvent(this, RowDragEvent.MOVED, src, dest);

		for (Enumeration<RowDragListener> en = this.listener.elements(); en.hasMoreElements();) {
			li = en.nextElement();
			li.draggingMoved(ev);
		}
	}
}

/*
 * $Log: TableRowDragger.java,v $
 * Revision 1.4  2010/09/23 08:15:16  olga
 * tuning
 *
 * Revision 1.3  2007/11/01 09:58:20  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.2  2007/09/10 13:05:53  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.2 2003/03/05 18:24:29 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:06 olga Imported sources
 * 
 * Revision 1.6 2000/06/05 14:08:16 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.5 2000/04/05 12:11:21 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */

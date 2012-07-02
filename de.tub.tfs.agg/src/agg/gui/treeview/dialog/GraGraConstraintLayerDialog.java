package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
//import java.awt.Container;
import java.awt.Dimension;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
import java.awt.GridLayout;
//import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

//import agg.editor.impl.EdGraGra;
import agg.gui.help.HtmlBrowser;
import agg.cons.Formula;

/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the grammar layers for graph constraints.
 */
public class GraGraConstraintLayerDialog extends JDialog implements ActionListener {

	private JPanel contentPane;

	private JPanel constraintPanel;

	private JPanel buttonPanel;

	private JScrollPane scrollPane;

	private JTable constraintTable;

	private JButton closeButton;

	private JButton cancelButton;

	private JButton helpButton;

	private boolean isCancelled;

	private Vector<Formula> constraints;

	private Vector<String> layers;

//	private EdGraGra gragra;

	private HtmlBrowser helpBrowser;

	/** This class models a hashtable for a table. */
	public class HashTableModel extends DefaultTableModel {

		Hashtable<Object, Vector<Object>> table;

		/**
		 * Creates a new model with hashtable and the title for the columns of
		 * the table.
		 */
		public HashTableModel(Vector<Formula> constraints, Vector<String> layers) {
			super();

			layers.add(0, "Constraint / Rule Layer");
			for (int i = 0; i < layers.size(); i++) {
				addColumn(layers.get(i));
			}

			this.table = new Hashtable<Object, Vector<Object>>(constraints.size());

			for (int i = 0; i < constraints.size(); i++) {
				Formula f = constraints.get(i);
				Vector<Integer> flayers = f.getLayer();
				Vector<Object> tmpVector = new Vector<Object>();
				for (int k = 1; k < layers.size(); k++) {
					String l = layers.get(k);
					boolean found = false;
					for (int j = 0; j < flayers.size(); j++) {
						Integer v = flayers.get(j);
						if (v.intValue() == (Integer.valueOf(l)).intValue()) {
							tmpVector.addElement(l);
							found = true;
							break;
						}
					}
					if (!found)
						tmpVector.addElement("");
				}

				Vector<Object> value = new Vector<Object>();
				value.addAll(tmpVector);
				this.table.put(f, value);

				tmpVector.add(0, f);
				// System.out.println("add row: "+tmpVector);
				addRow(tmpVector);
			}
		}

		/**
		 * This method decides if a cell of a table is editable or not.
		 * 
		 * @param rowIndex
		 *            The index of the row of the cell.
		 * @param columnIndex
		 *            The index of the column of the cell.
		 * @return The layer function can only entered in the second column. So
		 *         for any other column <CODE>false</CODE> is returned.
		 */
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return (columnIndex == 1);
		}

		/**
		 * Returns the value of a cell.
		 * 
		 * @param row
		 *            The index of the row of the cell.
		 * @param column
		 *            The index of the column of the cell.
		 * @return The object of the underlaying model of this table.
		 */
		public Object getValueAt(int row, int column) {
			Object result = super.getValueAt(row, column);
			if (result instanceof Formula) {
				result = ((Formula) result).getName();
			} else {
				Object key = super.getValueAt(row, 0);
				if (key instanceof Formula) {
					Vector<Object> v = this.table.get(key);
					if (column - 1 < v.size())
						result = v.get(column - 1);
					else
						result = "";
					// System.out.println("getValueAt: "+column+" "+result);
				}
			}
			return result;
		}

		/**
		 * Sets a new value to a cell.
		 * 
		 * @param aValue
		 *            The new value of a cell.
		 * @param row
		 *            The index of the row of the cell.
		 * @param column
		 *            The index of the column of the cell.
		 */
		public void setValueAt(Object aValue, int row, int column) {
			Object key = super.getValueAt(row, 0);
			// System.out.println(((Formula)key).getName()+" setValueAt...
			// "+aValue+" at: "+column);
			try {
				super.setValueAt(aValue, row, column);
				Vector<Object> v = this.table.get(key);
				// System.out.println("v: "+v);
				v.remove(column - 1);
				v.add(column - 1, aValue);
				this.table.put(key, v);
				// System.out.println(v);
			} catch (NumberFormatException nfe) {
			}
		}

		public Hashtable<Object, Vector<Object>> getTable() {
			return this.table;
		}

		public Formula getConstraint(String name) {
			for (Enumeration<?> e = this.table.keys(); e.hasMoreElements();) {
				Formula key = (Formula) e.nextElement();
				if (key.getName().equals(name))
					return key;
			}
			return null;
		}

		public int getRow(Formula constraint) {
			for (int i = 0; i < getRowCount(); i++) {
				String rname = (String) getValueAt(i, 0);
				Formula f = getConstraint(rname);
				if ((f != null) && f.equals(constraint))
					return i;
			}
			return -1;
		}

	}

	public class LayerCellRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer, MouseListener {

		Vector<JCheckBox> checks;

		int clmn;

		Color selColor = Color.WHITE;

		public final JTable jtable;

		public LayerCellRenderer(int indx, int size, JTable table,
				Color sColor) {
			super();
			addMouseListener(this);
			this.jtable = table;
			this.jtable.addMouseListener(this);
			this.checks = new Vector<JCheckBox>(size);
			this.clmn = indx;
			// this.selColor = sColor;
			initLayers(indx, size);

			this.setEnabled(false);
		}

		private void initLayers(int indx, int size) {
			for (int i = 0; i < size; i++) {
				JCheckBox cb = new JCheckBox("", false);
				cb.setBackground(Color.WHITE);
				this.checks.addElement(cb);
				Object value = ((DefaultTableModel) this.jtable.getModel())
						.getValueAt(i, indx);
				if (!((String) value).equals(""))
					cb.setSelected(true);
			}
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// System.out.println("getTableCellRendererComponent...: "+row+"
			// "+column+" "+value);
			JCheckBox jcb = this.checks.get(row);
			return jcb;
		}

		public void mouseClicked(MouseEvent e) {
			// System.out.println("LayerCellRenderer.mouseClicked:
			// "+e.getSource());
			for (int i = 0; i < this.checks.size(); i++) {
				JCheckBox cb = this.checks.get(i);
				// System.out.println(i+" "+j+" selected:
				// "+this.jtable.isCellSelected(i, j));
				if (this.jtable.isCellSelected(i, this.clmn)) {
					cb.setSelected(!cb.isSelected());
					if (cb.isSelected()) {
						cb.setBackground(this.selColor);
						((DefaultTableModel) this.jtable.getModel()).setValueAt(
								this.jtable.getColumnName(this.clmn), i, this.clmn);
					} else {
						cb.setBackground(Color.WHITE);
						((DefaultTableModel) this.jtable.getModel()).setValueAt("",
								i, this.clmn);
					}
//					Object newValue = ((DefaultTableModel) this.jtable.getModel())
//							.getValueAt(i, this.clmn);
					// System.out.println(" newValue: "+newValue);
				}

			}
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public Vector<JCheckBox> getChecks() {
			return this.checks;
		}

	}

	/**
	 * Creates a new instanceof this class.
	 * 
	 * @param parent
	 *            the parent frame of this GUI
	 * @param constraints
	 *            the constraints of a grammar
	 * @param layersAsString
	 *            the layers of a grammar
	 */
	public GraGraConstraintLayerDialog(JFrame parent, Vector<Formula> constraints,
			Vector<String> layersAsString) {
		super(parent, true);
		// System.out.println("GraGraConstraintLayerGUI parent: "+parent);
		setTitle("Select Rule Layer");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		// this.constraintLayer = new ConstraintLayer(constraints);
		this.constraints = constraints;
		// this.layers = layersAsString;
		this.layers = new Vector<String>(layersAsString.size());
		for (int i = 0; i < layersAsString.size(); i++) {
			this.layers.add(layersAsString.get(i));
		}
		if (parent != null)
			setLocationRelativeTo(parent);
		else
			setLocation(300, 100);
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the
	 * dialog.
	 */
	private void initComponents() {
		this.contentPane = new JPanel(new BorderLayout());
		this.contentPane.setBackground(Color.lightGray);

		this.constraintPanel = new JPanel(new BorderLayout());
		this.constraintPanel.setBackground(Color.orange);
		this.constraintPanel.setBorder(new TitledBorder(
				"Select Rule Layer for Graph Constraint"));

		this.constraintTable = new JTable(new HashTableModel(this.constraints, this.layers));
		this.constraintTable.setRowSelectionAllowed(true);
		this.constraintTable.setColumnSelectionAllowed(true);
		Color sbgColor = this.constraintTable.getSelectionBackground();
		this.constraintTable.setSelectionBackground(Color.WHITE);
		for (int i = 1; i < this.constraintTable.getColumnCount(); i++) {
			TableColumn column = this.constraintTable.getColumn(this.constraintTable
					.getColumnName(i));
			column.setMaxWidth(30);
			LayerCellRenderer lcr = new LayerCellRenderer(i, this.constraintTable
					.getRowCount(), this.constraintTable, sbgColor);
			column.setCellRenderer(lcr);
		}
		// this.constraintTable.getSelectionModel().setSelectionMode(2);
		this.constraintTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// System.out.println(this.constraintTable.getSelectionModel().getSelectionMode());
		int hght = getHeight(this.constraintTable.getRowCount(), this.constraintTable
				.getRowHeight()) + 10;
		// System.out.println("this.constraintTable Height: "+hght);
		this.constraintTable.doLayout();
		this.scrollPane = new JScrollPane(this.constraintTable);
		this.scrollPane.setPreferredSize(new Dimension(300, hght));
		this.constraintPanel.add(this.scrollPane);

		this.buttonPanel = new JPanel(new GridLayout(0, 3, 5, 5));
		this.closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.setToolTipText("Accept entries and close dialog.");
		this.closeButton.addActionListener(this);

		this.cancelButton = new JButton();
		this.isCancelled = false;
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.closeButton.setToolTipText("Reject entries and close dialog.");
		this.cancelButton.addActionListener(this);

		this.helpButton = new JButton();
		this.helpButton.setActionCommand("help");
		this.helpButton.setText("Help");
		this.helpButton.addActionListener(this);

		this.buttonPanel.add(this.closeButton);
		this.buttonPanel.add(this.cancelButton);
		this.buttonPanel.add(this.helpButton);

		this.contentPane.add(this.constraintPanel, BorderLayout.CENTER);
		this.contentPane.add(this.buttonPanel, BorderLayout.SOUTH);
		this.contentPane.revalidate();

		setContentPane(this.contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}

	/** Exit the Application */
	void exitForm(WindowEvent evt) {
		if (this.helpBrowser != null) {
			this.helpBrowser.setVisible(false);
			this.helpBrowser.dispose();
		}
		setVisible(false);
		dispose();
	}

	public void showGUI() {
		setVisible(true);
	}

	private void acceptValues() {
		Hashtable<Object, Vector<Object>> table = ((HashTableModel) this.constraintTable
				.getModel()).getTable();
		for (Enumeration<?> e = table.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			// System.out.println(key);
			Vector<Object> l = table.get(key);
			// System.out.println("l: "+l);
			Vector<Integer> v = new Vector<Integer>(l.size());
			for (int i = 0; i < l.size(); i++) {
				String s = (String) l.get(i);
				if (!s.equals(""))
					v.add(Integer.valueOf(s));
			}
//			System.out.println(this.getClass().getName()+"  v: "+v);
			((Formula) key).setLayer(v);
		}
	}

	/**
	 * This handels the clicks on the different buttons.
	 * 
	 * @param e
	 *            The event from the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.closeButton) {
			acceptValues();
			setVisible(false);
			if (this.helpBrowser != null) {
				this.helpBrowser.setVisible(false);
				this.helpBrowser.dispose();
			}
			dispose();
		} else if (source == this.cancelButton) {
			this.isCancelled = true;
			setVisible(false);
			if (this.helpBrowser != null) {
				this.helpBrowser.setVisible(false);
				this.helpBrowser.dispose();
			}
			dispose();
		} else if (source == this.helpButton) {
			if (this.helpBrowser != null) {
				this.helpBrowser.dispose();
				this.helpBrowser = null;
			}
			if (this.helpBrowser == null) {
				this.helpBrowser = new HtmlBrowser("ConstraintForLayerHelp.html");
				this.helpBrowser.setSize(500, 350);
				this.helpBrowser.setLocation(50, 50);
				this.helpBrowser.setVisible(true);
			}
		}
	}

//	public void setGraGra(EdGraGra gra) {
//		gragra = gra;
//	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	private int getHeight(int rowCount, int rowHeight) {
		int h = (rowCount + 1) * rowHeight;
		if (rowCount > 10)
			h = (10 + 2) * rowHeight;
		return h;
	}

	/* constrainBuild() method
	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, int grid_width, int grid_height, int fill,
			int anchor, double weight_x, double weight_y, int top, int left,
			int bottom, int right) {
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = grid_x;
		c.gridy = grid_y;
		c.gridwidth = grid_width;
		c.gridheight = grid_height;
		c.fill = fill;
		c.anchor = anchor;
		c.weightx = weight_x;
		c.weighty = weight_y;
		c.insets = new Insets(top, left, bottom, right);
		((GridBagLayout) container.getLayout()).setConstraints(component, c);
		container.add(component);
	}
*/
}

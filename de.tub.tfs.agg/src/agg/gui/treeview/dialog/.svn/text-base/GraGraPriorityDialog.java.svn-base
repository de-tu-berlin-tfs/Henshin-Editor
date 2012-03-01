package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JOptionPane;

//import agg.editor.impl.EdGraGra;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.xt_basis.Rule;
import agg.xt_basis.RulePriority;

//import com.objectspace.jgl.HashSet;
//import com.objectspace.jgl.OrderedSet;
//import com.objectspace.jgl.OrderedSetIterator;

/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the rule priorities.
 * 
 * @author $Author: olga $
 * @version $Id: GraGraPriorityDialog.java,v 1.3 2010/09/23 08:23:04 olga Exp $
 */
public class GraGraPriorityDialog extends JDialog implements ActionListener {

	private JPanel contentPane;

	private JPanel rulePanel;

	private JPanel buttonPanel;

	private JScrollPane ruleScrollPane;

	private JTable ruleTable;

	private JButton closeButton;

	private JButton cancelButton;

	private boolean isCancelled;

	private RulePriority priority;

//	private EdGraGra gragra;

	boolean changed = false;

	/** This class models a hashtable for a table. */
	public class HashTableModel extends DefaultTableModel {

		Hashtable<Rule, Integer> table;

		RulePriority rulePriority;

		/**
		 * Creates a new model with hashtable and the title for the column of
		 * the table.
		 * 
		 * @param table
		 *            The hashtable for the modle.
		 * @param columnNames
		 *            The array with the column names.
		 */
		public HashTableModel(Hashtable<Rule, Integer> table,
				String[] columnNames) {
			super();
			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}
			this.table = table;
			Enumeration<?> keys = this.table.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = this.table.get(key);
				Vector<Object> tmpVector = new Vector<Object>();
				tmpVector.addElement(key);
				tmpVector.addElement(value);
				addRow(tmpVector);
			}
		}

		/**
		 * Creates a new model with hashtable and the title for the column of
		 * the table.
		 * 
		 * @param priority
		 *            The rule priority containing hashtable for the model.
		 * @param columnNames
		 *            The array with the column names.
		 */
		public HashTableModel(RulePriority priority, String[] columnNames) {
			super();

			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}
			this.table = priority.getRulePriority();
			this.rulePriority = priority;
			Integer startPriority = priority.getStartPriority();
			Hashtable<Integer, HashSet<Rule>> 
			invertedRulePriority = priority.invertPriority();
			
			OrderedSet<Integer> rulePrioritySet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = invertedRulePriority.keys(); en
					.hasMoreElements();) {
				rulePrioritySet.add(en.nextElement());
			}
			int i = 0;

			Integer currentPriority = startPriority;
			boolean nextPriorityExists = true;
			while (nextPriorityExists && (currentPriority != null)) {
				HashSet rulesForPriority = invertedRulePriority
						.get(currentPriority);
				Iterator<?> en = rulesForPriority.iterator();
				while (en.hasNext()) {
					Rule rule = (Rule) en.next();
					Vector<Object> tmpVector = new Vector<Object>();
					tmpVector.addElement(rule);
					tmpVector.addElement(Integer.valueOf(rule.getPriority()));
					addRow(tmpVector);
				}
				// set next priority
				i++;
				if (i < rulePrioritySet.size()) {
					currentPriority = rulePrioritySet.get(i);
				}
				else {
					nextPriorityExists = false;
				}
			}
		}

		/**
		 * This method decides if a cell of a table is editable or not.
		 * 
		 * @param rowIndex
		 *            The index of the row of the cell.
		 * @param columnIndex
		 *            The index of the column of the cell.
		 */
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 1;
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
			if (result instanceof Rule) {
				result = ((Rule) result).getName();
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
			try {
				Integer i = Integer.valueOf((String) aValue);
				super.setValueAt(i, row, column);
				// System.out.println(key+" setValueAt: "+row+" ::
				// "+i.toString());
				if (key instanceof Rule)
					this.table.put((Rule) key, i);
			} catch (NumberFormatException nfe) {
			}
		}

		public Hashtable<Rule, Integer> getTable() {
			return this.table;
		}

	}

	/**
	 * Creates new object of GraGraRulePriorityGUI
	 * 
	 * @param parent
	 *            The parent frame of this gui.
	 * @param priority
	 *            The rules to set priorities.
	 */
	public GraGraPriorityDialog(JFrame parent, RulePriority priority) {
		super(parent, true);
		setTitle("Rule Priority");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		this.priority = priority;
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

		JPanel help = new JPanel(new GridBagLayout());
		JLabel l1 = new JLabel(" Only priority > 0 allowed.");
		JLabel l2 = new JLabel(" The  smallest  priority is");
		JLabel l3 = new JLabel(" the highest.");
		constrainBuild(help, l1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 20, 0, 10);
		constrainBuild(help, l2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 20, 0, 10);
		constrainBuild(help, l3, 0, 2, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 20, 10, 10);

		this.rulePanel = new JPanel(new BorderLayout());
		this.rulePanel.setBackground(Color.orange);
		this.rulePanel.setBorder(new TitledBorder("Set Rule Priority"));
		this.ruleTable = new JTable(new HashTableModel(this.priority, new String[] {
				"Rule", "Priority" }));
		TableColumn priorityColumn = this.ruleTable.getColumn("Priority");
		priorityColumn.setMaxWidth(50);
		int hght = getHeight(this.ruleTable.getRowCount(), this.ruleTable.getRowHeight()) + 10;
		// System.out.println("this.ruleTable Height: "+hght);
		this.ruleTable.doLayout();
		this.ruleScrollPane = new JScrollPane(this.ruleTable);
		this.ruleScrollPane.setPreferredSize(new Dimension(200, hght));
		this.rulePanel.add(this.ruleScrollPane);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.setToolTipText("Accept entries and close dialog.");
		this.closeButton.addActionListener(this);

		this.cancelButton = new JButton();
		this.isCancelled = false;
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.setToolTipText("Reject entries and close dialog.");
		this.cancelButton.addActionListener(this);

		constrainBuild(this.buttonPanel, this.closeButton, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 10, 10, 5);
		constrainBuild(this.buttonPanel, this.cancelButton, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 10, 10);

		this.contentPane.add(help, BorderLayout.NORTH);
		this.contentPane.add(this.rulePanel, BorderLayout.CENTER);
		this.contentPane.add(this.buttonPanel, BorderLayout.SOUTH);
		this.contentPane.revalidate();

		setContentPane(this.contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}

	/** Exit the Application */
	void exitForm(WindowEvent evt) {
		setVisible(false);
		dispose();
	}

	public void showGUI() {
		setVisible(true);
	}

	public boolean hasChanged() {
		return this.changed;
	}

	private boolean accept() {
		Hashtable<Rule, Integer> table = ((HashTableModel) this.ruleTable.getModel()).getTable();
		for (Enumeration<Rule> e = table.keys(); e.hasMoreElements();) {
			Object key = e.nextElement();
			Integer value = table.get(key);
			if (value.intValue() <= 0)
				return false;

			// System.out.println(value.intValue()+"
			// "+((Rule)key).getPriority());
			if (value.intValue() != ((Rule) key).getPriority()) {
				((Rule) key).setPriority(value.intValue());
				this.changed = true;
			}
			// System.out.println(((Rule)key).getName()+" :
			// "+((Rule)key).getPriority());
		}
		return true;
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
			if (!accept()) {
				JOptionPane
						.showMessageDialog(
								this,
								"Please check priorities.\nOnly priority > 0 allowed.\nThe smalles priority is the highest.");
				return;
			}
			setVisible(false);
			dispose();
		} else if (source == this.cancelButton) {
			this.isCancelled = true;
			setVisible(false);
			dispose();
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

	// constrainBuild() method
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

}

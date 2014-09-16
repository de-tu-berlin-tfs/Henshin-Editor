package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import agg.editor.impl.EdGraGra;
import agg.gui.help.HtmlBrowser;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.util.Pair;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;
//import java.awt.Container;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;

//import com.objectspace.jgl.HashSet;
//import com.objectspace.jgl.OrderedSet;
//import com.objectspace.jgl.OrderedSetIterator;

/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the grammar layers.
 * 
 * @author $Author: olga $
 * @version $Id: GraGraTriggerRuleOfLayerGUI.java,v 1.5 2006/12/14 18:21:44 olga
 *          Exp $
 */
public class GraGraTriggerRuleOfLayerDialog extends JDialog implements
		ActionListener, MouseListener {

	JPanel contentPane;

	JPanel rulePanel;

	JPanel buttonPanel;

	JScrollPane ruleScrollPane;

	JTable ruleTable;

	JButton closeButton;

	JButton cancelButton;

	JButton helpButton;

	boolean isCancelled;

	RuleLayer layer;

	EdGraGra gragra;

	HtmlBrowser helpBrowser;

	/** This class models a hashtable for a table. */
	public class HashTableModel extends DefaultTableModel {

		Hashtable<Rule, Pair<Integer, String>> table;

		RuleLayer ruleLayer;

		// Hashtable triggerTable;

		/**
		 * Creates a new model with hashtable and the title for the column of
		 * the table.
		 * 
		 * @param layer
		 *            The rule layer with hashtable for the model.
		 * @param columnNames
		 *            The array with the column names.
		 */
		public HashTableModel(RuleLayer layer, String[] columnNames) {
			super();
			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}

			Hashtable<Rule, Integer> layerTable = layer.getRuleLayer();
			this.table = new Hashtable<Rule, Pair<Integer, String>>(layerTable
					.size());
			// triggerTable = new Hashtable(layerTable.size());

			for (Enumeration<Rule> e = layerTable.keys(); e.hasMoreElements();) {
				Rule key = e.nextElement();
				String trigger = "";
				if (key.isTriggerOfLayer())
					trigger = "trigger";
				Pair<Integer, String> value = new Pair<Integer, String>(
						layerTable.get(key), trigger);
				this.table.put(key, value);
			}

			this.ruleLayer = layer;
			Integer startLayer = layer.getStartLayer();
			Hashtable<Integer, HashSet<Rule>> invertedRuleLayer = layer.invertLayer();			
			OrderedSet<Integer> ruleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = invertedRuleLayer.keys(); en
					.hasMoreElements();) {
				ruleLayerSet.add(en.nextElement());
			}
			int i = 0;

			Integer currentLayer = startLayer;
			boolean nextLayerExists = true;
			while (nextLayerExists && (currentLayer != null)) {
				HashSet rulesForLayer = invertedRuleLayer.get(currentLayer);
				Iterator<?> en = rulesForLayer.iterator();
				while (en.hasNext()) {
					Rule rule = (Rule) en.next();
					Vector<Object> tmpVector = new Vector<Object>();
					tmpVector.addElement(rule);
					tmpVector.addElement(Integer.valueOf(rule.getLayer()));
					String trigger = rule.isTriggerOfLayer() ? new String(
							"trigger") : new String("");
					tmpVector.addElement(trigger);
					// System.out.println(tmpVector.lastElement());
					addRow(tmpVector);
				}
				// set next Layer
				i++;
				if (i < ruleLayerSet.size()) {
					currentLayer = ruleLayerSet.get(i);
				}
				else {
					nextLayerExists = false;
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
//			System.out.println("getValue: "+row+"   "+column);			
			Object result = super.getValueAt(row, column);
			if (result instanceof Rule) {
				result = ((Rule) result).getName();
			} else {
				Object key = super.getValueAt(row, 0);
				if (key instanceof Rule) {
					Pair<Integer, String> p = this.table.get(key);
					if (column == 1) {
						result = p.first;
					} else if (column == 2) {
						result = p.second;
					}					
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
			// System.out.println(((Rule)key).getName()+" setValueAt...");
			try {
				if (column == 1) {
					Integer i = Integer.valueOf((String) aValue);
					Pair<Integer, String> pold = this.table.get(key);
					if (pold.first.intValue() != i.intValue()) {
						super.setValueAt(i, row, column);
						Pair<Integer, String> pnew = new Pair<Integer, String>(
								i, new String(""));
						this.table.put((Rule) key, pnew);
						this.ruleLayer.getRuleLayer().put((Rule) key, i);
						// System.out.println("WARNING! Layer has been changed!
						// Check trigger rule!");
					}
				} else if (column == 2) {
					// System.out.println("set column 2...: value= "+aValue);
					String trigger = (String) aValue;
					if (key instanceof Rule) {
						Pair<Integer, String> pold = this.table.get(key);
						Pair<Integer, String> pnew = new Pair<Integer, String>(
								pold.first, trigger);
						this.table.put((Rule) key, pnew);
					}
				}
			} catch (NumberFormatException nfe) {
			}
		}

		public void refreshTriggerEntries(Object value, int row) {
			Rule rule = (Rule) super.getValueAt(row, 0);
//			System.out.println("refresh... after "+rule.getName());
			for (Enumeration<Rule> e = this.table.keys(); e.hasMoreElements();) {
				Rule key = e.nextElement();
//				System.out.println(key);
				if (!key.equals(rule)) {
					int rowOfKey = getRow(key);
					if (rowOfKey == -1)
						continue;
					
					int rlayer = ((Integer) getValueAt(row, 1)).intValue();
					int layerOfKey = ((Integer) getValueAt(rowOfKey, 1))
							.intValue();
					if (rlayer == layerOfKey) {
						String triggerOfKey = (String) getValueAt(rowOfKey, 2);
						if (triggerOfKey.equals("trigger")) {
							Pair<Integer, String> pold = this.table.get(key);
							Pair<Integer, String> pnew = new Pair<Integer, String>(
									pold.first, "");
							this.table.put(key, pnew);
							// System.out.println(key.getName()+" "+pnew);
							super.setValueAt("", rowOfKey, 2);
							break;
						}
					}
					
				}
			}
		}

		public Hashtable<Rule, Pair<Integer, String>> getTable() {
			return this.table;
		}

		public Rule getRule(String name) {
			for (Enumeration<Rule> e = this.table.keys(); e.hasMoreElements();) {
				Rule key = e.nextElement();
				if (key.getName().equals(name))
					return key;
			}
			return null;
		}

		public int getRow(Rule rule) {
			for (int i = 0; i < getRowCount(); i++) {
				String rname = (String) getValueAt(i, 0);
				Rule r = getRule(rname);
				if ((r != null) && r.equals(rule))
					return i;
			}
			return -1;
		}

	}

	public class TriggerCellRenderer extends DefaultTableCellRenderer implements
			TableCellRenderer, MouseListener // , ActionListener
	{

		Vector<JCheckBox> checks;

		public final JTable jtable;

		public TriggerCellRenderer(int size, JTable table) {
			super();
			addMouseListener(this);
			this.jtable = table;
			this.jtable.addMouseListener(this);
			this.checks = new Vector<JCheckBox>(size);
			initTriggers(size);
		}

		private void initTriggers(int size) {
			for (int i = 0; i < size; i++) {
				JCheckBox cb = new JCheckBox("", false);
				cb.addMouseListener(this); // das wirkt noch nicht!!!
				// cb.addActionListener(this);// das wirkt noch nicht!!!
				cb.setBackground(Color.WHITE);
				this.checks.addElement(cb);
				Object value = ((DefaultTableModel) this.jtable.getModel())
						.getValueAt(i, 2);
				if (((String) value).equals("trigger"))
					cb.setSelected(true);
			}
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// System.out.println("getTableCellRendererComponent...: "+row+"
			// "+column+" "+value);
			JCheckBox jcb = this.checks.get(row);
			if (!isSelected) {
				Object aValue = ((DefaultTableModel) this.jtable.getModel())
						.getValueAt(row, 2);
				if (aValue.equals("") && jcb.isSelected())
					jcb.setSelected(false);
			}
			return jcb;
		}

		/*
		 * public void actionPerformed(ActionEvent e) {
		 * System.out.println("TriggerCellRenderer.actionPerformed:
		 * "+e.getSource()); Object source = e.getSource(); }
		 */

		public void mouseClicked(MouseEvent e) {
//			 System.out.println("TriggerCellRenderer.mouseClicked: "
//			 +e.getSource());
			for (int i = 0; i < this.checks.size(); i++) {
				JCheckBox cb = this.checks.get(i);
				// System.out.println(this.jtable.isCellSelected(i, 2));
				if (this.jtable.isCellSelected(i, 2)) {
					cb.setSelected(!cb.isSelected());
					this.jtable.updateUI();
					if (cb.isSelected()) {
						cb.setBackground(this.jtable.getSelectionBackground());
						((DefaultTableModel) this.jtable.getModel()).setValueAt(
								"trigger", i, 2);
					} else {
						((DefaultTableModel) this.jtable.getModel()).setValueAt("",
								i, 2);
					}
					Object newValue = ((DefaultTableModel) this.jtable.getModel())
							.getValueAt(i, 2);
					// System.out.println(" newValue: "+newValue);
					if (cb.isSelected())
						((HashTableModel) this.jtable.getModel())
								.refreshTriggerEntries(newValue, i);
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
	 * Creates new form GraGraLayerGUI
	 * 
	 * @param parent
	 *            The parent frame of this gui.
	 * @param layer
	 *            The layer function must be changed.
	 */
	public GraGraTriggerRuleOfLayerDialog(JFrame parent, RuleLayer layer) {
		super(parent, true);
		// System.out.println("GraGraLayerRuleTriggerGUI parent: "+parent);
		setTitle("Set Trigger Rule");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		this.layer = layer;
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

		this.rulePanel = new JPanel(new BorderLayout());
		this.rulePanel.setBackground(Color.orange);
		this.rulePanel.setBorder(new TitledBorder("Set Trigger Rule for Layer"));

		this.ruleTable = new JTable(new HashTableModel(this.layer, new String[] { "Rule",
				"Layer", "Trigger" }));
		this.ruleTable.getColumn("Layer").setMaxWidth(50);
		this.ruleTable.setRowSelectionAllowed(true);
		this.ruleTable.setColumnSelectionAllowed(false);
		this.ruleTable.setSelectionBackground(Color.WHITE);
		TableColumn triggerColumn = this.ruleTable.getColumn("Trigger");
		triggerColumn.setMaxWidth(50);
		TriggerCellRenderer tcr = new TriggerCellRenderer(this.ruleTable
				.getRowCount(), this.ruleTable);
		triggerColumn.setCellRenderer(tcr);
		this.ruleTable.getSelectionModel().setSelectionMode(2);
		// System.out.println(this.ruleTable.getSelectionModel().getSelectionMode());
		int hght = getHeight(this.ruleTable.getRowCount(), this.ruleTable.getRowHeight()) + 10;
		// System.out.println("this.ruleTable Height: "+hght);
		this.ruleTable.doLayout();
		this.ruleScrollPane = new JScrollPane(this.ruleTable);
		this.ruleScrollPane.setPreferredSize(new Dimension(300, hght));
		this.rulePanel.add(this.ruleScrollPane);

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

	private void acceptValues() {
		Hashtable<Rule, Pair<Integer, String>> table = ((HashTableModel) this.ruleTable.getModel()).getTable();
		for (Enumeration<Rule> e = table.keys(); e.hasMoreElements();) {
			Rule key = e.nextElement();
			Integer l = (Integer) ((Pair) table.get(key)).first;
			key.setLayer(l.intValue());
			String trigger = (String) ((Pair) table.get(key)).second;
			if (trigger.equals(""))
				key.setTriggerForLayer(false);
			else
				key.setTriggerForLayer(true);
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
			if (this.isModal())
				this.setModal(false);
			if (this.helpBrowser == null) {
				if (this.isModal()) {
					this.setModal(false);
					this.setAlwaysOnTop(true);
				}
				this.helpBrowser = new HtmlBrowser("TriggerRuleHelp.html");
				this.helpBrowser.setSize(500, 300);
				this.helpBrowser.setLocation(50, 50);
				this.helpBrowser.setVisible(true);
			}
			else {
				this.helpBrowser.setVisible(true);
			}
		}
	}

	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
	}

	public boolean isCancelled() {
		return this.isCancelled;
	}

	private int getHeight(int rowCount, int rowHeight) {
		int h = (rowCount + 1) * rowHeight;
		if (rowCount > 10)
			h = (10 + 2) * rowHeight;
		return h;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/*
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

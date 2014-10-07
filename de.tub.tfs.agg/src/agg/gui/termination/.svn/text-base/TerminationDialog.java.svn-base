package agg.gui.termination;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import agg.termination.TerminationLGTSInterface;
import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.gui.IconResource;
import agg.gui.help.HtmlBrowser;
import agg.xt_basis.Arc;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;
import agg.xt_basis.RulePriority;


/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the layer function.
 * 
 * @author $Author: olga $
 * @version $Id: TerminationDialog.java,v 1.8 2010/09/23 08:22:17 olga Exp $
 */
@SuppressWarnings("serial")
public class TerminationDialog extends JDialog implements ActionListener {

	private JPanel rulePanel;

	private JScrollPane ruleScrollPane;

	private JTable ruleTable;

	private JPanel creationPanel;

	private JScrollPane creationScrollPane;

	private JTable creationTable;

	private JPanel deletionPanel;

	private JScrollPane deletionScrollPane;

	private JTable deletionTable;

	private JButton checkButton;

	JCheckBox generateCB;

	private JButton helpButton;

	private JButton closeButton;

	private JButton resetButton;

	private JButton acceptButton;

	private JButton moreButton;

	private JPanel contentPane;

	private JLabel statusLabel;

	private TerminationLGTSInterface terminationLGTS;

	boolean generateRuleLayer;

	private JPanel rcdPanel0;

	private boolean all;

	private boolean terminate;

	private LayerTerminationCondTable tableLTC;

	private HtmlBrowser helpBrowser;

	private boolean //layered, 
					priority;

	private static final Icon OK_ICON = IconResource
			.getIconFromURL(IconResource.getOkIcon());

	private static final Icon WRONG_ICON = IconResource
			.getIconFromURL(IconResource.getWrongIcon());

	protected agg.gui.treeview.GraGraTreeView treeView;
	
	
	/**
	 * This class models a hash table model for a table.
	 */
	public class HashTableModel extends DefaultTableModel {

		Hashtable<Object, Integer> table;

		/**
		 * Creates a new model.
		 * 
		 * @param table
		 *            The hash table for the model.
		 * @param columnNames
		 *            The array with the column names.
		 */
		public HashTableModel(Hashtable<Object, Integer> table,
				String[] columnNames) {
			super();
			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}

			this.table = table;

			// iterate by layer
			Enumeration<?> keys = table.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = table.get(key);
				Vector<Object> tmpVector = new Vector<Object>();
				tmpVector.addElement(key);
				tmpVector.addElement(value);
				addRow(tmpVector);
			}
		}

		public HashTableModel(RuleLayer layer, String[] columnNames) {
			super();

			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}

			this.table = new Hashtable<Object, Integer>();
			this.table.putAll(layer.getRuleLayer());
			// this.table=layer.getRuleLayer();

			Hashtable<Integer, HashSet<Rule>> invertedRuleLayer = layer.invertLayer();			
			OrderedSet<Integer> ruleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = invertedRuleLayer.keys(); en
					.hasMoreElements();) {
				ruleLayerSet.add(en.nextElement());
			}
			
			Integer startLayer = layer.getStartLayer();
			int i = 0;
			
			Integer currentLayer = startLayer;
			// System.out.println(currentLayer);
			boolean nextLayerExists = true;
			while (nextLayerExists && (currentLayer != null)) {
				// set current rules
				HashSet<?> rulesForLayer = invertedRuleLayer.get(currentLayer);
				Iterator<?> en = rulesForLayer.iterator();
				while (en.hasNext()) {
					Rule rule = (Rule) en.next();
					Vector<Object> tmpVector = new Vector<Object>();
					tmpVector.addElement(rule);
					tmpVector.addElement(Integer.valueOf(rule.getLayer()));
					addRow(tmpVector);
				}
				// set next layer
				i++;
				if (i < ruleLayerSet.size()) {
					currentLayer = ruleLayerSet.get(i);
				}
				else {
					nextLayerExists = false;
				}
			}
		}

		public HashTableModel(RulePriority priority, String[] columnNames) {
			super();

			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}

			this.table = new Hashtable<Object, Integer>();
			this.table.putAll(priority.getRulePriority());

			Hashtable<Integer, HashSet<Rule>> invertedRuleLayer = priority.invertPriority();			
			OrderedSet<Integer> ruleLayerSet = new OrderedSet<Integer>(new IntComparator<Integer>());
			for (Enumeration<Integer> en = invertedRuleLayer.keys(); en
					.hasMoreElements();) {
				ruleLayerSet.add(en.nextElement());
			}
			
			Integer startLayer = priority.getStartPriority();
			int i = 0;

			Integer currentLayer = startLayer;
			// System.out.println(currentLayer);
			boolean nextLayerExists = true;
			while (nextLayerExists && (currentLayer != null)) {
				// set current rules
				HashSet<Rule> rulesForLayer = invertedRuleLayer.get(currentLayer);
				Iterator<Rule> en = rulesForLayer.iterator();
				while (en.hasNext()) {
					Rule rule = en.next();
					Vector<Object> tmpVector = new Vector<Object>();
					tmpVector.addElement(rule);
					tmpVector.addElement(Integer.valueOf(rule.getPriority()));
					addRow(tmpVector);
				}
				// set next layer
				i++;
				if (i < ruleLayerSet.size()) {
					currentLayer = ruleLayerSet.get(i);
				}
				else {
					nextLayerExists = false;
				}
			}
		}

		/** Returns the table. */
		public Hashtable<Object, Integer> getTable() {
			return this.table;
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
			
			} else if (result instanceof agg.xt_basis.Type) {
				if (!((agg.xt_basis.Type) result).getStringRepr().equals(""))
					result = ((agg.xt_basis.Type) result).getStringRepr();
				else
					result = "(unnamed)";
				
			} else if (result instanceof GraphObject) {
				GraphObject go = (GraphObject) result;
				if (go instanceof Node) {
					result = getTypeStringOfNode((Node) go);				
				} else {
					result = getTypeStringOfEdge((Arc) go);
				}				
			}
			
			return result;
		}

		private String getTypeStringOfNode(final Node go) {
			String s = go.getType().getStringRepr();
			if (s.equals(""))
				s = "(unnamed)";
			return s;
		}
		
		private String getTypeStringOfEdge(final Arc go) {
			String s = getTypeStringOfNode((Node) go.getSource());
			s = s.concat("--");
			
			String s1 = go.getType().getStringRepr();
			if (s1.equals(""))
				s1 = "(unnamed)";
			s = s.concat(s1);
			
			s = s.concat("->");
			s = s.concat(getTypeStringOfNode((Node) go.getTarget()));
			return s;
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
				this.table.put(key, i);
			} catch (NumberFormatException nfe) {
			}
		}

		public Object getRuleAt(int row, int column) {
			Object result = super.getValueAt(row, column);
			if (result instanceof Rule)
				return result;
			
			return null;
		}

		public Object getTypeAt(int row, int column) {
			Object result = super.getValueAt(row, column);
			if (result instanceof GraphObject)
				return result;
			else if (result instanceof agg.xt_basis.Type)
				return result;
			else
				return null;
		}

	}

	/**
	 * Creates new termination layer GUI
	 * 
	 * @param parent
	 *            The parent frame of this gui.
	 */
	public TerminationDialog(JFrame parent, agg.gui.treeview.GraGraTreeView treeView, TerminationLGTSInterface termination) {
		super(parent, false); // true);

		this.treeView = treeView;
		setTitle("Termination of LGTS");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		setBackground(Color.lightGray);
		if (parent != null) {
			setLocationRelativeTo(parent);
			setLocation(50, 50);
		} else
			setLocation(50, 50);

		this.priority = termination.getGrammar().trafoByPriority();
//		layered = termination.getGrammar().isLayered() || !this.priority;

		this.terminationLGTS = termination;
		
		initComponents();
		
		this.terminate = false;
				
		JScrollPane scroll = new JScrollPane(this.contentPane);
		scroll.setPreferredSize(new Dimension(400, 450));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll);
		validate();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		pack();
	}
	
	/**
	 * Initialize termination layers
	 */
	public void init(TerminationLGTSInterface termination) {
		if (this.terminationLGTS != termination) {
			this.terminationLGTS = termination;						
			initComponents();
		} else {
			reinit();
		}
		this.terminate = false;
		this.statusLabel.setIcon(WRONG_ICON);
	}

	public void reinit() {		
		reinitComponents();	
		this.terminate = false;
		this.statusLabel.setIcon(WRONG_ICON);
	}
	
	public void reinit(final TerminationLGTSInterface termination) {
		this.terminationLGTS = termination;				
		reinitComponents();	
		this.terminate = false;
		this.statusLabel.setIcon(WRONG_ICON);
	}
	
	private void initComponents() {
		GridBagLayout gridbag = new GridBagLayout();

		this.contentPane = new JPanel(new BorderLayout());
		this.contentPane.setBackground(Color.lightGray);

		JPanel rcdPanel = new JPanel(new BorderLayout());

		this.rcdPanel0 = new JPanel(gridbag);
		this.rulePanel = new JPanel();
		this.rulePanel.setBackground(Color.orange);
		this.ruleScrollPane = new JScrollPane();
		this.rulePanel.setVisible(true);

		this.creationPanel = new JPanel();
		this.creationPanel.setLayout(new BorderLayout());
		this.creationPanel.setBackground(Color.orange);
		this.creationScrollPane = new JScrollPane();

		this.deletionPanel = new JPanel();
		this.deletionPanel.setLayout(new BorderLayout());
		this.deletionPanel.setBackground(Color.orange);
		this.deletionScrollPane = new JScrollPane();

		this.checkButton = new JButton();
		this.closeButton = new JButton();
		this.resetButton = new JButton();
		this.acceptButton = new JButton();
		this.moreButton = new JButton();
		this.moreButton.setEnabled(false);
		this.helpButton = new JButton();
		this.statusLabel = new JLabel();

		this.rulePanel.setLayout(new BorderLayout());

		if (this.priority) {
			this.rulePanel.setBorder(new TitledBorder("Rule Priority"));
			this.ruleTable = new JTable(new HashTableModel(new RulePriority(
					this.terminationLGTS.getListOfRules()), new String[] {
					"  Rule  ", "  Priority  " }));
		}
		else //if (layered) 
		{
			this.rulePanel.setBorder(new TitledBorder("Rule Layer"));
			this.ruleTable = new JTable(new HashTableModel(new RuleLayer(this.terminationLGTS
					.getListOfRules()), new String[] { "  Rule  ",
					"  Layer  " }));
		}
		
		this.ruleTable.doLayout();
		int h = getHeight(this.ruleTable.getRowCount(), this.ruleTable.getRowHeight());
		this.ruleScrollPane.setViewportView(this.ruleTable);
		this.ruleScrollPane.setPreferredSize(new Dimension(200, h));
		this.rulePanel.add(this.ruleScrollPane);
		
		Hashtable<Object, Integer> creationTypeTable = new Hashtable<Object, Integer>();
		creationTypeTable.putAll(this.terminationLGTS.getCreationLayer());
		this.creationTable = new JTable();
		if (this.priority) {
			this.creationPanel.setBorder(new TitledBorder("Creation Priority Layer"));
			this.creationTable.setModel(new HashTableModel(creationTypeTable,
					new String[] { "  Type  ", "  Priority  " }));
		} else //if (layered) 
		{
			this.creationPanel.setBorder(new TitledBorder("Creation Layer"));
			this.creationTable.setModel(new HashTableModel(creationTypeTable,
				new String[] { "  Type  ", "  Layer  " }));
		}
		this.creationTable.doLayout();
		this.creationTable.setEnabled(false);
		this.creationScrollPane.setViewportView(this.creationTable);
		this.creationScrollPane.setPreferredSize(new Dimension(200, 150));
		this.creationPanel.add(this.creationScrollPane);
		
		Hashtable<Object, Integer> deletionTypeTable = new Hashtable<Object, Integer>();
		deletionTypeTable.putAll(this.terminationLGTS.getDeletionLayer());
		this.deletionTable = new JTable();
		if (this.priority) {
			this.deletionPanel.setBorder(new TitledBorder("Deletion Priority Layer"));
			this.deletionTable.setModel(new HashTableModel(deletionTypeTable,
					new String[] { "  Type  ", "  Priority  " }));
		} else {
			this.deletionPanel.setBorder(new TitledBorder("Deletion Layer"));
			this.deletionTable.setModel(new HashTableModel(deletionTypeTable,
				new String[] { "  Type  ", "  Layer  " }));
		}		
		this.deletionTable.doLayout();
		this.deletionTable.setEnabled(false);
		this.deletionScrollPane.setViewportView(this.deletionTable);
		this.deletionScrollPane.setPreferredSize(new Dimension(200, 150));
		this.deletionPanel.add(this.deletionScrollPane);

		constrainBuild(this.rcdPanel0, this.rulePanel, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 5, 5);
		this.all = false;

		JPanel statusPanel = new JPanel(new BorderLayout());
		this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		this.statusLabel.setText("Are termination criteria satisfied?");
		this.statusLabel.setIcon(WRONG_ICON);
		this.statusLabel.setIconTextGap(5);
		try {
			this.statusLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		} catch (IllegalArgumentException iae) {
		}
		JLabel emptyLabel0 = new JLabel("               ");
		statusPanel.add(this.statusLabel, BorderLayout.CENTER);
		statusPanel.add(emptyLabel0, BorderLayout.SOUTH);

		rcdPanel.add(statusPanel, BorderLayout.SOUTH);
		// rcdPanel.add(this.statusLabel, BorderLayout.SOUTH);
		rcdPanel.add(this.rcdPanel0);

		JPanel checkPanel = new JPanel(new BorderLayout());

		JPanel genPanel = new JPanel(new BorderLayout());
		genPanel.setBorder(new TitledBorder(""));
		JPanel checkBoxPanel = new JPanel(new GridLayout(0, 1));
		this.generateCB = new JCheckBox("generate rule layer", null, false);
		checkBoxPanel.add(this.generateCB);
		this.generateCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (TerminationDialog.this.generateCB.isSelected())
					TerminationDialog.this.generateRuleLayer = true;
				else
					TerminationDialog.this.generateRuleLayer = false;
			}
		});
		JLabel layerLabel = new JLabel(
				"  Creation / Deletion type layer will be generated automatically  ");
		checkBoxPanel.add(layerLabel);
		genPanel.add(checkBoxPanel, BorderLayout.CENTER);

		JLabel emptyLabel = new JLabel("               ");
		checkPanel.add(emptyLabel, BorderLayout.CENTER);
		checkPanel.add(genPanel, BorderLayout.NORTH);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 4, 5, 5));
		this.checkButton.setActionCommand("check");
		this.checkButton.setText("Check");
		this.checkButton.setToolTipText(" Check layer function ");
		this.checkButton.addActionListener(this);

		this.resetButton.setActionCommand("reset");
		this.resetButton.setText("Reset");
		this.resetButton.addActionListener(this);

		this.acceptButton.setActionCommand("accept");
		this.acceptButton.setText("Accept");
		this.acceptButton.addActionListener(this);

		this.moreButton.setActionCommand("more");
		this.moreButton.setText("More Info");
		this.moreButton.addActionListener(this);

		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.addActionListener(this);

		this.helpButton.setActionCommand("help");
		this.helpButton.setText("Help");
		this.helpButton.addActionListener(this);

		buttonPanel.add(this.checkButton);
		buttonPanel.add(this.moreButton);
		buttonPanel.add(this.resetButton);
		buttonPanel.add(this.acceptButton);
		buttonPanel.add(this.closeButton);
		buttonPanel.add(this.helpButton);

		checkPanel.add(buttonPanel, BorderLayout.SOUTH);

		this.contentPane.add(rcdPanel);
		this.contentPane.add(checkPanel, BorderLayout.SOUTH);

		this.contentPane.revalidate();
	}

	private void reinitComponents() {	
		this.ruleScrollPane.setViewportView(null);
		if (this.priority) {
			this.ruleTable = new JTable(new HashTableModel(new RulePriority(
					this.terminationLGTS.getListOfRules()), new String[] {
					"  Rule  ", "  Priority  " }));
		}
		else {
			this.ruleTable = new JTable(new HashTableModel(new RuleLayer(this.terminationLGTS
					.getListOfRules()), new String[] { "  Rule  ",
					"  Layer  " }));

		}
		this.ruleTable.doLayout();
		int h = getHeight(this.ruleTable.getRowCount(), this.ruleTable.getRowHeight());
		this.ruleScrollPane.setViewportView(this.ruleTable);
		this.ruleScrollPane.setPreferredSize(new Dimension(200, h));
		
		this.creationScrollPane.setViewportView(null);
		Hashtable<Object, Integer> 
		creationTypeTable = new Hashtable<Object, Integer>();
		creationTypeTable.putAll(this.terminationLGTS.getCreationLayer());
		this.creationTable = new JTable();
		if (this.priority) {
			this.creationTable.setModel(new HashTableModel(creationTypeTable,
					new String[] { "  Type  ", "  Priority  " }));
		} else {
			this.creationTable.setModel(new HashTableModel(creationTypeTable,
				new String[] { "  Type  ", "  Layer  " }));
		}		
		this.creationTable.doLayout();
		this.creationTable.setEnabled(false);
		this.creationScrollPane.setViewportView(this.creationTable);

		this.deletionScrollPane.setViewportView(null);
		Hashtable<Object, Integer> deletionTypeTable = new Hashtable<Object, Integer>();
		deletionTypeTable.putAll(this.terminationLGTS.getDeletionLayer());
		this.deletionTable = new JTable();
		if (this.priority) {
			this.deletionTable.setModel(new HashTableModel(deletionTypeTable,
					new String[] { "  Type  ", "  Priority  " }));
		} else {
			this.deletionTable.setModel(new HashTableModel(deletionTypeTable,
				new String[] { "  Type  ", "  Layer  " }));
		}
		this.deletionTable.doLayout();
		this.deletionTable.setEnabled(false);
		this.deletionScrollPane.setViewportView(this.deletionTable);

		this.contentPane.revalidate();
		validate();
		pack();
	}

	/** Exit the Application */
	protected void exitForm(WindowEvent evt) {
		setVisible(false);
		dispose();
	}

	public void showGUI() {	
		if (this.terminationLGTS.hasGrammarChanged()) {
			reinit();
		}		
		setVisible(true);
	}

	/**
	 * This handels the clicks on the different buttons.
	 * 
	 * @param e
	 *            The event from the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.checkButton) {
			if (this.tableLTC != null) {
				this.tableLTC.setVisible(false);
				this.tableLTC.dispose();
				this.tableLTC = null;
			}
			
			this.terminationLGTS.initRuleLayer(((HashTableModel) this.ruleTable.getModel())
					.getTable());
			if (!this.terminate) {
				this.terminationLGTS.setGenerateRuleLayer(this.generateRuleLayer);
				this.terminate = this.terminationLGTS.checkTermination();
				if (this.terminate) {
					this.terminate = this.terminationLGTS.isValid();
				}
			}
			if (this.terminate) {
				if (!this.all) {
					this.ruleTable.doLayout();
					int h = getHeight(this.creationTable.getRowCount(),
							this.creationTable.getRowHeight());
					this.creationPanel.setPreferredSize(new Dimension(200, h));
					this.creationTable.doLayout();
					h = getHeight(this.deletionTable.getRowCount(), this.deletionTable
							.getRowHeight());
					this.deletionPanel.setPreferredSize(new Dimension(200, h));
					this.deletionTable.doLayout();
					constrainBuild(this.rcdPanel0, this.creationPanel, 0, 1, 1, 1,
							GridBagConstraints.BOTH, GridBagConstraints.CENTER,
							1.0, 0.0, 5, 5, 10, 5);
					constrainBuild(this.rcdPanel0, this.deletionPanel, 0, 2, 1, 1,
							GridBagConstraints.BOTH, GridBagConstraints.CENTER,
							1.0, 0.0, 5, 5, 10, 5);
					setSize(getWidth(), getHeight() + 2 * h + 50);
					validate();
					this.all = true;
				}
				HashTableModel htm = (HashTableModel) this.ruleTable.getModel();
				for (int i = 0; i < htm.getRowCount(); i++) {
					Object r = htm.getRuleAt(i, 0);
					if (r instanceof Rule) {
						if (this.generateRuleLayer) {
							int l = this.terminationLGTS.getRuleLayer((Rule) r);
							htm.setValueAt(String.valueOf(l), i, 1);
						}
					}
				}
				htm = (HashTableModel) this.creationTable.getModel();
				for (int i = 0; i < htm.getRowCount(); i++) {
					Object t = htm.getTypeAt(i, 0);
					if (t instanceof agg.xt_basis.Type) {
						int l = this.terminationLGTS.getCreationLayer((agg.xt_basis.Type) t);
						htm.setValueAt(String.valueOf(l), i, 1);
					} else if (t instanceof GraphObject) {
						int l = this.terminationLGTS.getCreationLayer((GraphObject) t);
						htm.setValueAt(String.valueOf(l), i, 1);
					}
				}
				htm = (HashTableModel) this.deletionTable.getModel();
				for (int i = 0; i < htm.getRowCount(); i++) {
					Object t = htm.getTypeAt(i, 0);
					if (t instanceof agg.xt_basis.Type) {
						int l = this.terminationLGTS.getDeletionLayer((agg.xt_basis.Type) t);
						htm.setValueAt(String.valueOf(l), i, 1);
					} else if (t instanceof GraphObject) {
						int l = this.terminationLGTS.getDeletionLayer((GraphObject) t);
						htm.setValueAt(String.valueOf(l), i, 1);
					}
				}
				this.statusLabel.setIcon(OK_ICON);
			} else {
				this.statusLabel.setIcon(WRONG_ICON);
				String errorMsg = this.terminationLGTS.getErrorMessage();
				if (errorMsg.length() == 0) {
					errorMsg = "Termination conditions could not be checked.";
				}
				javax.swing.JOptionPane.showMessageDialog(null, errorMsg,
						"  Termination check failed ",
						javax.swing.JOptionPane.ERROR_MESSAGE);
			}
			this.moreButton.setEnabled(true);
		}

		else if (source == this.closeButton) {
			if (this.terminationLGTS.isValid()) {
				HashTableModel htm = (HashTableModel) this.ruleTable.getModel();
				for (int i = 0; i < htm.getRowCount(); i++) {
					Object r = htm.getRuleAt(i, 0);
					if (r instanceof Rule) {
						Integer v = (Integer) htm.getValueAt(i, 1);
						((Rule) r).setLayer(v.intValue());
					}
				}
			}
			if (this.tableLTC != null) {
				this.tableLTC.setVisible(false);
				this.tableLTC.dispose();
				this.tableLTC = null;
			}
//			if (this.helpBrowser != null) {
//				helpBrowser.setVisible(false);
//				helpBrowser.dispose();
//			}
			setVisible(false);
			
//			treeView.getTree().treeDidChange();
			
			dispose();
		}

		else if (source == this.resetButton) {			
			if (this.generateRuleLayer) {
				this.terminationLGTS.initAll(this.generateRuleLayer);
			} else {			
				this.terminationLGTS.resetLayer();
			}
			
			resetLayer();
			this.terminate = false;
			this.statusLabel.setIcon(WRONG_ICON);
			// generateCB.setSelected(false);
			if (this.tableLTC != null) {
				this.tableLTC.setVisible(false);
				this.tableLTC.dispose();
				this.tableLTC = null;
			}
			this.moreButton.setEnabled(false);
		}

		else if (source == this.acceptButton) {
			this.terminationLGTS.saveRuleLayer();
			// refresh gragra treeView
			(new agg.gui.treeview.path.GrammarTreeNode()).refreshCurrentGraGra(
					treeView, 
					treeView.getTreePathOfGrammar(treeView.getCurrentGraGra().getBasisGraGra()), 
					treeView.getCurrentGraGra());
		}

		else if (source == this.moreButton) {
			if (this.tableLTC != null) {
				this.tableLTC.refreshView();
				this.tableLTC.setVisible(true);
			} else {
				this.tableLTC = new LayerTerminationCondTable(this.terminationLGTS);
				this.tableLTC.setLocation(getLocation().x + getWidth(),
						getLocation().y);
				this.tableLTC.showGUI();
			}
		}

		else if (source == this.helpButton) {
			if (this.helpBrowser != null) {
				this.helpBrowser.setVisible(true);
			}
			if (this.helpBrowser == null) {
				this.helpBrowser = new HtmlBrowser("TerminationHelp.html");
				this.helpBrowser.setSize(500, 300);
				this.helpBrowser.setLocation(50, 50);
				this.helpBrowser.setVisible(true);
				this.helpBrowser.toFront();
			}
		}

	}

	private int getHeight(int rowCount, int rowHeight) {
		int h = (rowCount + 3) * rowHeight;
		if (rowCount > 10)
			h = (10 + 2) * rowHeight;
		return h;
	}

	private void resetLayer() {
		HashTableModel htm = (HashTableModel) this.ruleTable.getModel();
		for (int i = 0; i < htm.getRowCount(); i++) {
			Object r = htm.getRuleAt(i, 0);
			int l = this.terminationLGTS.getRuleLayer((Rule) r);
			htm.setValueAt(String.valueOf(l), i, 1);
		}
		htm = (HashTableModel) this.creationTable.getModel();
		for (int i = 0; i < htm.getRowCount(); i++) {
			Object t = htm.getTypeAt(i, 0);
			if (t instanceof agg.xt_basis.Type) {
				int l = this.terminationLGTS.getCreationLayer((agg.xt_basis.Type) t);
				htm.setValueAt(String.valueOf(l), i, 1);
			} else if (t instanceof GraphObject) {
				int l = this.terminationLGTS.getCreationLayer((GraphObject) t);
				htm.setValueAt(String.valueOf(l), i, 1);
			}
		}
		htm = (HashTableModel) this.deletionTable.getModel();
		for (int i = 0; i < htm.getRowCount(); i++) {
			Object t = htm.getTypeAt(i, 0);
			if (t instanceof agg.xt_basis.Type) {
				int l = this.terminationLGTS.getDeletionLayer((agg.xt_basis.Type) t);
				htm.setValueAt(String.valueOf(l), i, 1);
			} else if (t instanceof GraphObject) {
				int l = this.terminationLGTS.getDeletionLayer((GraphObject) t);
				htm.setValueAt(String.valueOf(l), i, 1);
			}
		}
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

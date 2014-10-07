package agg.gui.parser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import agg.util.IntComparator;
import agg.util.OrderedSet;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;


/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the rule layers.
 * 
 * @author $Author: olga $
 * @version $Id: LayerGUI.java,v 1.7 2010/09/23 08:20:54 olga Exp $
 */
@SuppressWarnings("serial")
public class LayerGUI extends JDialog implements ActionListener {

	/**
	 * @serial This attribute is serializable.
	 */
	private JPanel rulePanel;

	/**
	 * @serial This attribute is serializable.
	 */
	private JScrollPane ruleScrollPane;

	/**
	 * @serial This attribute is serializable.
	 */
	private JTable ruleTable;

	/**
	 * @serial This attribute is serializable.
	 */
	private JButton closeButton;

	/**
	 * @serial This attribute is serializable.
	 */
	private JButton cancelButton;

	private boolean isCncld;

	/**
	 * @serial This attribute is serializable.
	 */
	private JPanel contentPane;

	private RuleLayer layer;

	/**
	 * This class models a hashtable for a table.
	 */
	public class HashTableModel extends DefaultTableModel {

		/**
		 * @serial This attribute is serializable.
		 */
		Hashtable<Rule, Integer> table;

		RuleLayer ruleLayer;

		/**
		 * Creates a new model with hashtable and the titlen for the column of
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
			Enumeration<Rule> keys = table.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				Object value = table.get(key);
				Vector<Object> tmpVector = new Vector<Object>();
				tmpVector.addElement(key);
				tmpVector.addElement(value);
				addRow(tmpVector);
			}
		}

		public HashTableModel(RuleLayer layer,
				String[] columnNames) {
			super();
			for (int i = 0; i < columnNames.length; i++) {
				addColumn(columnNames[i]);
			}
			this.table = layer.getRuleLayer();
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
				@SuppressWarnings("rawtypes")
				HashSet rulesForLayer = invertedRuleLayer.get(currentLayer);
				Iterator<?> en = rulesForLayer.iterator();
				while (en.hasNext()) {
					Rule rule = (Rule) en.next();
					Vector<Object> tmpVector = new Vector<Object>();
					tmpVector.addElement(rule);
					tmpVector.addElement(new Integer(rule.getLayer()));
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
			if (result instanceof Rule)
				result = ((Rule) result).getName();
			else if (result instanceof agg.xt_basis.Type) {
				if (!((agg.xt_basis.Type) result).getStringRepr().equals(""))
					result = ((agg.xt_basis.Type) result).getStringRepr();
				else
					result = ((agg.xt_basis.Type) result).getAdditionalRepr();
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
				Integer i = new Integer((String) aValue);
				super.setValueAt(i, row, column);
				if (key instanceof Rule)
					this.table.put((Rule) key, i);
			} catch (NumberFormatException nfe) {
			}
		}

		public Object getRuleAt(int row, int column) {
			Object result = super.getValueAt(row, column);
			if (result instanceof Rule)
				return result;
			
			return null;
		}

	}

	/**
	 * Creates new form LayerGUI
	 * 
	 * @param parent
	 *            The parent frame of this gui.
	 * @param layer
	 *            The layer function must be changed.
	 */
	public LayerGUI(JFrame parent, RuleLayer layer) {
		super(new JFrame(), true);
		setTitle("Rule Layer Editor");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		setBackground(Color.lightGray);
		if (parent != null) {
			setLocationRelativeTo(parent);
			setLocation(100, 100);
		} else
			setLocation(300, 100);
		this.layer = layer;
		initComponents();
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the FormEditor.
	 */
	private void initComponents() {
		this.contentPane = new JPanel(new BorderLayout());
		this.contentPane.setBackground(Color.lightGray);

		JPanel rcdPanel = new JPanel(new BorderLayout());
		JPanel rcdPanel0 = new JPanel(new GridLayout(0, 1));
		this.rulePanel = new JPanel();
		this.rulePanel.setBackground(Color.orange);
		this.ruleScrollPane = new JScrollPane();
		this.ruleTable = new JTable();

		this.closeButton = new JButton();
		this.cancelButton = new JButton();

		this.rulePanel.setLayout(new BorderLayout());
		this.rulePanel.setBorder(new TitledBorder("Rule Layer"));
		this.ruleTable.setModel(new HashTableModel(this.layer, new String[] {
				"Rule Name", "Layer Number" }));
		int hght = getHeight(this.ruleTable.getRowCount(), this.ruleTable.getRowHeight());
		this.ruleTable.doLayout();
		this.ruleScrollPane.setViewportView(this.ruleTable);
		this.ruleScrollPane.setPreferredSize(new Dimension(200, hght));
		this.rulePanel.add(this.ruleScrollPane);

		rcdPanel0.add(this.rulePanel);
		rcdPanel.add(rcdPanel0);

		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));

		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.addActionListener(this);

		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.addActionListener(this);

		buttonPanel.add(this.closeButton);
		buttonPanel.add(this.cancelButton);

		this.contentPane.add(buttonPanel, BorderLayout.SOUTH);
		this.contentPane.add(rcdPanel);

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

	/**
	 * This handels the clicks on the different buttons.
	 * 
	 * @param e
	 *            The event from the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (/* (source == checkButton) || */(source == this.closeButton)) {
			boolean result = true; // this.layer.checkLayer();
			if (result) {
				HashTableModel htm = (HashTableModel) this.ruleTable.getModel();
				for (int i = 0; i < htm.getRowCount(); i++) {
					Object r = htm.getRuleAt(i, 0);
					if (r instanceof Rule) {
						Integer v = (Integer) htm.getValueAt(i, 1);
						((Rule) r).setLayer(v.intValue());
					}
				}
			}
		}
		if (source == this.closeButton) {
			this.isCncld = false;
			setVisible(false);
			dispose();
		} else if (source == this.cancelButton) {
			this.isCncld = true;
			setVisible(false);
			dispose();
		}
	}

	public boolean isCancelled() {
		return this.isCncld;
	}

	private int getHeight(int rowCount, int rowHeight) {
		int h = (rowCount + 1) * rowHeight;
		if (rowCount > 10)
			h = (10 + 2) * rowHeight;
		return h;
	}

}
/*
 * $Log: LayerGUI.java,v $
 * Revision 1.7  2010/09/23 08:20:54  olga
 * tuning
 *
 * Revision 1.6  2010/03/08 15:43:09  olga
 * code optimizing
 *
 * Revision 1.5  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/12/13 13:33:04 enrico reimplemented
 * code
 * 
 * Revision 1.2 2005/09/26 08:35:15 olga CPA graph frames; bugs
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.11 2005/04/27 08:18:29 olga GraGra tree view update after changing
 * rule layer in layer editor of CPA.
 * 
 * Revision 1.10 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.9 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.8 2004/10/25 14:24:38 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.7 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.6 2002/11/11 10:44:42 komm no change
 * 
 * Revision 1.5 2002/09/26 13:59:50 olga GUI- Arbeit
 * 
 * Revision 1.4 2002/09/23 14:14:34 olga GUI fertig.
 * 
 * Revision 1.3 2002/09/23 12:24:10 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.2 2002/09/19 16:22:39 olga Arbeit im wesentlichen an GUI.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.7 2001/09/24 16:39:44 olga Korrektur an LayerFunction und
 * LayerGUI.
 * 
 * Revision 1.6 2001/08/02 15:25:33 olga Fehlerbehandlung und Meldung im Bezug
 * auf Layer Function.
 * 
 * Revision 1.5 2001/07/19 15:19:05 olga Arbeit an GUI
 * 
 * Revision 1.4 2001/07/09 13:12:44 olga Aenderungen an GUI. Version heisst ab
 * jetzt 1.1
 * 
 * Revision 1.3 2001/05/14 11:52:57 olga Parser GUI Optimierung
 * 
 * Revision 1.2 2001/03/08 11:02:44 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.8 2001/01/28 13:14:44 shultzke API fertig
 * 
 * Revision 1.1.2.7 2001/01/14 14:48:19 shultzke commentare ergaenzt
 * 
 * Revision 1.1.2.6 2001/01/03 09:44:54 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.1.2.5 2000/12/26 10:00:03 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.4 2000/12/21 13:46:01 shultzke optionen weiter veraendert
 * 
 * Revision 1.1.2.3 2000/12/18 13:33:33 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.2 2000/12/12 13:27:41 shultzke erste Versuche kritische Paare
 * mit XML abzuspeichern
 * 
 * Revision 1.1.2.1 2000/12/10 14:55:47 shultzke um Layer erweitert
 * 
 */


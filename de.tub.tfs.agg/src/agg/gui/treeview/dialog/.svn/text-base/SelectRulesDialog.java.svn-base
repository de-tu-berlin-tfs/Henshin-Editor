/**
 * 
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agg.xt_basis.GraGra;
import agg.xt_basis.Rule;

/**
 * @author olga
 *
 */
@SuppressWarnings("serial")
public class SelectRulesDialog extends JDialog implements
		ListSelectionListener {

	protected JFrame parentFrame;
	
	protected GraGra gragra;
	
	protected final List<String> ruleNames1 = new Vector<String>();
		
	protected List<Rule> list1;
	
	protected int first1, last1;
	
	protected JTable ruleTable1;
	
	protected JScrollPane scroll, scrollRule1;

	protected JButton selectAll, deselectAll, apply, close;
	
	protected final String title = " Select  Rules ";
	
	
	
	public SelectRulesDialog(
			final JFrame parent,
			final GraGra gragra,
			final Point location) {
		
		super(parent);
		
		makeDialog(parent, gragra, null, location);
	}
	
	public SelectRulesDialog(
			final JFrame parent,
			final GraGra gragra,
			final List<Rule> rules1,
			final Point location) {
		
		super(parent);
		
		makeDialog(parent, gragra, rules1, location);
	}
	
	public void setVisible(boolean b) {
		if (b) {
			this.first1 = -1;
		}
		
		super.setVisible(b);
	}
	
	private void makeDialog(
			final JFrame parent,
			final GraGra gra,
			final List<Rule> rules1,
			final Point location) {
		
		setModal(true);
		setTitle(this.title);
//		extendTitle("");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				setVisible(false);
			}
		});
		
		this.parentFrame = parent;
		this.gragra = gra;
		
		int rows = (this.gragra != null)? this.gragra.getEnabledRules().size(): 2;
		
		JPanel textPanel = makeTextPanel();
		JPanel rulePanel = makeRuleListPanel(rows);
		JPanel buttonPanel = makeButtonsPanel();
		
		if (this.gragra != null) {
			if (rules1 != null)
				this.updateRules(this.gragra, rules1);
			else 
				this.updateRules(this.gragra);
		}
												
		JPanel p = new JPanel(new BorderLayout());
		p.add(textPanel, BorderLayout.NORTH);
		p.add(rulePanel, BorderLayout.CENTER);
		p.add(buttonPanel, BorderLayout.SOUTH);
		
		this.scroll = new JScrollPane(p);
		int n = this.ruleTable1.getRowCount()+1;
		if (n*25 <= 300)
			this.scroll.setPreferredSize(new Dimension(450, n*25+200));
		else
			this.scroll.setPreferredSize(new Dimension(450, 500));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.scroll, BorderLayout.CENTER);
		validate();
	
		setLocation(location);
		pack();
	}

	private JPanel makeRuleListPanel(int rows) {
		final JPanel p = new JPanel(new GridBagLayout());
		
		this.ruleTable1 = new JTable(0,1);
		this.scrollRule1 = new JScrollPane(this.ruleTable1);
		final JPanel p1 = this.makeRuleList("   Rules", this.ruleTable1, rows, this.scrollRule1);
								
		constrainBuild(p, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 10, 10, 10);
		
		return p;
	}
	
	private JPanel makeTextPanel() {
		final JPanel p = new JPanel(new GridLayout(3,1));
		p.add(new JLabel("     "));
		p.add(new JLabel("      Select the rules to build a parallel rule."));
		p.add(new JLabel("      ( At least two selected rules required. )"));
		
		return p;
	}
	
	private JPanel makeButtonsPanel() {
		final JPanel p = new JPanel(new GridBagLayout());
		
		this.selectAll = new JButton("Select All");
		this.selectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				SelectRulesDialog.this.ruleTable1.selectAll();
				SelectRulesDialog.this.apply.setEnabled(true);
			}
		});
		
		this.deselectAll = new JButton("Deselect All");
		this.deselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				SelectRulesDialog.this.ruleTable1.clearSelection();
				SelectRulesDialog.this.apply.setEnabled(false);
			}
		});
		
		this.apply = new JButton("Apply");
		this.apply.setEnabled(false);
		this.apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (setRules()) {
					setVisible(false);
				} else {
					SelectRulesDialog.this.apply.setEnabled(false);
				}
			}
		});
		
		this.close = new JButton("Close");
		this.close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
			
		constrainBuild(p, this.selectAll, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 10, 10);
		constrainBuild(p, this.deselectAll, 1, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 10, 10);	
		constrainBuild(p, this.apply, 2, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 10, 10);
		constrainBuild(p, this.close, 3, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 10, 10);
		
		return p;
	}
	
	private JPanel makeRuleList(
			final String titleStr, 
			final JTable ruleList,
			int rows,
			final JScrollPane scrollRuleList) {
		
		int maxrows = 20;
		final JLabel l = new JLabel(titleStr);
		int h = l.getFontMetrics(l.getFont()).getHeight();
		if (rows <= maxrows)
			scrollRuleList.setPreferredSize(new Dimension(200, (rows+2)*h));
		else
			scrollRuleList.setPreferredSize(new Dimension(200, maxrows*h));
		
		final JPanel p = new JPanel(new BorderLayout());
		p.add(l, BorderLayout.NORTH);
		p.add(scrollRuleList, BorderLayout.CENTER);
		
		return p;
	}
	
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
	
	
	public void updateRules(final GraGra gra) {
		this.ruleNames1.clear();
		
		this.gragra = gra;
		
		List<Rule> list = gra.getEnabledRules();
		for (int i=0; i<list.size(); i++) {
			String name = list.get(i).getName();
			this.ruleNames1.add(name);
		}
		
		if (this.ruleTable1 != null) {
			this.scrollRule1.getViewport().remove(this.ruleTable1);
		}
		this.ruleTable1 = new JTable(this.ruleNames1.size(), 1);
		this.scrollRule1.getViewport().setView(this.ruleTable1);
		this.ruleTable1.getSelectionModel().addListSelectionListener(this);
		
		for (int i = 0; i < this.ruleNames1.size(); i++) {
			this.ruleTable1.getModel().setValueAt(this.ruleNames1.get(i), i, 0);
			((DefaultCellEditor) this.ruleTable1.getCellEditor(i, 0))
						.getComponent().setEnabled(false);			
		}
		
		if (this.list1 != null)
			this.list1.clear();
	}
	
	public void updateRules(
			final GraGra gra,
			final List<Rule> rules1) {
		
		this.ruleNames1.clear();
		
		this.gragra = gra;
		
		for (int i=0; i<rules1.size(); i++) {
			String name = rules1.get(i).getName();
			this.ruleNames1.add(name);
		}
		
		if (this.ruleTable1 != null) {
			this.scrollRule1.getViewport().remove(this.ruleTable1);
		}
		this.ruleTable1 = new JTable(this.ruleNames1.size(), 1);
		this.scrollRule1.getViewport().setView(this.ruleTable1);
		this.ruleTable1.getSelectionModel().addListSelectionListener(this);
		
		for (int i = 0; i < this.ruleNames1.size(); i++) {
			this.ruleTable1.getModel().setValueAt(this.ruleNames1.get(i), i, 0);
			((DefaultCellEditor) this.ruleTable1.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
		}
		
		if (this.list1 != null)
			this.list1.clear();
	}
	
	

	/* (non-Javadoc)
	 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
	 */
	public void valueChanged(ListSelectionEvent e) {
		if (this.ruleTable1.getSelectionModel().getMinSelectionIndex() >= 0) {
			this.apply.setEnabled(true);
		}
	}
	
	protected boolean setRules() {
		List<Rule> list = this.gragra.getEnabledRules();
		this.list1 = new Vector<Rule>();
				
		this.first1 = this.ruleTable1.getSelectionModel().getMinSelectionIndex();
		this.last1 = this.ruleTable1.getSelectionModel().getMaxSelectionIndex();
		
		if (this.first1 >= 0) {						
			for (int i=this.first1; i<=this.last1; i++) {
				if (this.ruleTable1.getSelectionModel().isSelectedIndex(i))
					this.list1.add(list.get(i));
			}
			if (this.list1.size() >= 2) {
				return true;
			} else {
				this.list1.clear();
			}
		} 
		return false;
	}

	public List<Rule> getRuleList() {
		return this.list1;
	}
	
	public boolean rulesContainsRuleScheme() {
		for (int i=0; i<this.list1.size(); i++) {
			Rule r = this.list1.get(i);
			if (r.getRuleScheme() != null)
				return true;
		}

		return false;
	}
}

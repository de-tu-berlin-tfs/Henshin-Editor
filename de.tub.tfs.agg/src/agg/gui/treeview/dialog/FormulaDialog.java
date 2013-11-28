/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import agg.editor.impl.EdAtomic;

/*
 * not more used
 */
public class FormulaDialog extends JDialog implements ActionListener {
	final JList jList1;

	final JTextField jTextField1;

	final JButton Ok, Cancel;

	final JPanel dialogPanel;

	JFrame f;

	String formula;

	boolean changed, canceled;

	public FormulaDialog(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.jList1 = new JList();
		this.jTextField1 = new JTextField("true");
		this.Ok = new JButton("Ok");
		this.Ok.addActionListener(this);
		this.Cancel = new JButton("Cancel");
		this.Cancel.addActionListener(this);
		JPanel buttons = new JPanel(new FlowLayout());
		buttons.add(this.Ok);
		buttons.add(this.Cancel);
		JPanel below = new JPanel(new BorderLayout());
		below.add(this.jTextField1, BorderLayout.NORTH);
		below.add(buttons, BorderLayout.SOUTH);

		this.dialogPanel = new JPanel(new BorderLayout());
		this.dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.dialogPanel.add(new JScrollPane(this.jList1), BorderLayout.CENTER);
		this.dialogPanel.add(below, BorderLayout.SOUTH);
		this.dialogPanel.setPreferredSize(new Dimension(300, 200));

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.dialogPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		this.formula = "";
		this.changed = true;
	}

	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		if (e.getActionCommand().equals("Ok")) {
			String s = this.jTextField1.getText();
			if (!this.formula.equals(s)) {
				this.formula = s;
				this.changed = true;
			} else
				this.changed = false;
		} else if (e.getActionCommand().equals("Cancel")) {
			this.canceled = true;
		}

	}

	public String getFormula() {
		return this.formula;
	}

	public boolean isChanged() {
		return this.changed;
	}
	
	public boolean isCanceled() {
		return this.canceled;
	}
	
	public void setVars(List<String> vars, String f) {
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < vars.size(); i++) {
			String v = vars.get(i);
			s.add(String.valueOf(i + 1).concat("  ").concat(v));
		}
		this.jList1.setListData(s);
		this.jTextField1.setText(f);
		this.formula = f;
	}
	
	public void setVars(Vector<EdAtomic> v, String f) {
		Vector<String> s = new Vector<String>();
		for (int i = 0; i < v.size(); i++) {
			EdAtomic a = v.elementAt(i);
			s.add(String.valueOf(i + 1).concat("  ").concat(a.getBasisAtomic().getAtomicName()));
		}
		this.jList1.setListData(s);
		this.jTextField1.setText(f);
		this.formula = f;
	}
}

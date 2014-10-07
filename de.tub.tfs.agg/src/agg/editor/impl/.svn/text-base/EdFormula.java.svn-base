/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.editor.impl;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class EdFormula extends JDialog {
	JButton Ok = new JButton();

	JButton Cancel = new JButton();

	Component component1;

	@SuppressWarnings("rawtypes")
	JList jList1 = new JList();

	JTextField jTextField1 = new JTextField();

	public EdFormula() {
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.component1 = Box.createHorizontalStrut(8);
		this.Ok.setActionCommand("Ok");
		this.Ok.setText("jButton1");
		this.Ok.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				Ok_actionPerformed(e);
			}
		});
		this.Cancel.setActionCommand("Cancel");
		this.Cancel.setText("jButton1");
		this.jList1.setMaximumSize(new Dimension(32000, 32000));
		this.jTextField1.setText("jTextField1");
		this.add(this.Ok, null);
		this.add(this.jList1, null);
		this.add(this.component1, null);
		this.add(this.Cancel, null);
		this.add(this.jTextField1, null);
	}

	void Ok_actionPerformed(ActionEvent e) {

	}
}
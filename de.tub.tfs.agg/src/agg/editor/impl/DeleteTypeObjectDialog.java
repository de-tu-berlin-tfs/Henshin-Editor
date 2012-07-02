package agg.editor.impl;

import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.TitledBorder;

/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the grammar layers.
 * 
 * @author $Author: olga $
 * @version $Id: DeleteTypeObjectDialog.java,v 1.3 2006/12/13 13:33:04 enrico
 *          Exp $
 */
public class DeleteTypeObjectDialog extends JDialog implements ActionListener {

	private JPanel contentPane;

	JRadioButtonMenuItem bDiscard;

	JRadioButtonMenuItem bKeep;

	JRadioButtonMenuItem bChange;

	boolean discardDelete;

	boolean keep;

	boolean changeDelete;

	private JButton closeButton;

	private EdGraphObject obj;

	public DeleteTypeObjectDialog(JFrame parent, EdGraphObject obj) {
		super(parent, true);
		this.obj = obj;
		setTitle("Delete from Type Graph");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
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

		JPanel jpanel = new JPanel();
		jpanel.setBorder(new TitledBorder(" Delete node type object "));
		if ((this.obj != null) && this.obj.isArc())
			jpanel.setBorder(new TitledBorder(" Delete edge type object "));
		jpanel.setLayout(new GridLayout(0, 1));
		jpanel.add(new JLabel("                                      "));

		String go = "node";
		if ((this.obj != null) && this.obj.isArc())
			go = "edge";
		JLabel str = new JLabel("The start graph of the grammar uses the " + go
				+ " type you want to delete.");
		jpanel.add(str);
		JLabel str2 = new JLabel("Please select:");
		jpanel.add(str2);
		JLabel ll = new JLabel("                                        ");
		jpanel.add(ll);

		ButtonGroup group = new ButtonGroup();

		JLabel lChange = new JLabel("Change the start graph and delete the "
				+ go + " type object.");
		this.bChange = new JRadioButtonMenuItem("Change & Delete");
		group.add(this.bChange);
		this.bChange.setSelected(true);
		this.bChange.setBackground(Color.orange);
		this.bChange.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (DeleteTypeObjectDialog.this.bChange.isSelected())
					DeleteTypeObjectDialog.this.changeDelete = true;
			}
		});

		JLabel lKeep = new JLabel("Keep the start graph and the " + go
				+ " type object.");
		this.bKeep = new JRadioButtonMenuItem("Keep");
		group.add(this.bKeep);
		this.bKeep.setBackground(Color.orange);
		this.bKeep.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (DeleteTypeObjectDialog.this.bKeep.isSelected())
					DeleteTypeObjectDialog.this.keep = true;
			}
		});

		JLabel lDiscard = new JLabel("Discard the start graph and delete the "
				+ go + " type object.");
		this.bDiscard = new JRadioButtonMenuItem("Discard & Delete");
		group.add(this.bDiscard);
		this.bDiscard.setBackground(Color.orange);
		this.bDiscard.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (DeleteTypeObjectDialog.this.bDiscard.isSelected())
					DeleteTypeObjectDialog.this.discardDelete = true;
			}
		});

		jpanel.add(lChange);
		jpanel.add(this.bChange);
		jpanel.add(lKeep);
		jpanel.add(this.bKeep);
		jpanel.add(lDiscard);
		jpanel.add(this.bDiscard);
		jpanel.add(new JLabel("                                       "));

		this.closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.addActionListener(this);

		this.contentPane.add(jpanel, BorderLayout.CENTER);
		this.contentPane.add(this.closeButton, BorderLayout.SOUTH);
		this.contentPane.revalidate();

		setContentPane(this.contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}

	/** Exit the Application */
	void exitForm(WindowEvent evt) {
		setVisible(false);
		// dispose();
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
		if (source == this.closeButton) {
			setVisible(false);
			// dispose();
		}
	}

	public boolean isDiscardAndDelete() {
		return this.discardDelete;
	}

	public boolean isKeep() {
		return this.keep;
	}

	public boolean isChangeAndDelete() {
		return this.changeDelete;
	}

}

package agg.gui.cpa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;


public class IntNumberDialog extends JDialog implements ActionListener,
														DocumentListener {

	private JTextField textFrom, textTo;
	private String strFrom, strTo;
	private JButton closeButton, cancelButton;
	private boolean isCanceled;
	private Point fromTo = new Point(0,0);
	private int max;
	JLabel text;
	private JFrame f;
	
	public IntNumberDialog(JFrame parent) {
		super(parent, true);
		
		setTitle(" Set Start & End Index");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				accept();
				exitForm(evt);
			}
		});
		
		if (parent != null)
			setLocationRelativeTo(parent);
		else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width / 2 - 200, screenSize.height / 2 - 200);
		}
		
		initComponents();
	}
	
	public void showGUI(int maxN) {
		this.isCanceled = false;
		if (this.max != maxN) {
			this.max = maxN;
			this.text.setText("  There are  "+String.valueOf(this.max+1)+"  critical Graphs.  ");
			this.strFrom = "0";
			this.textFrom.setText(this.strFrom );
			this.strTo = String.valueOf(maxN);
			this.textTo.setText(this.strTo);
		}
		this.setVisible(true);
	}
	
	public boolean isCanceled() {
		return this.isCanceled;
	}
	
	public Point getFromTo() {
		return this.fromTo;
	}
	
	
	private void initComponents() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.lightGray);
		
		JPanel nbPanel = new JPanel(new BorderLayout());
		
		JPanel textPanel = new JPanel(new GridLayout(3,1));
		this.text = new JLabel("    ");		
		JLabel text2 = new JLabel("  Please set from-to index of Graphs to show.  ");
		textPanel.add(new JLabel("                         "));	
		textPanel.add(this.text);
		textPanel.add(text2);
		
		nbPanel.add(textPanel, BorderLayout.NORTH);		
		JPanel contentSrc = new JPanel(new GridBagLayout());

		JPanel panelFrom = new JPanel(new BorderLayout());
		JLabel labelMin = new JLabel(" from ");
		this.textFrom = new JTextField(5);
		this.strFrom = "";
		this.textFrom.setText("");
		this.textFrom.addActionListener(this);
		this.textFrom.getDocument().addDocumentListener(this);
		panelFrom.add(labelMin, BorderLayout.NORTH);
		panelFrom.add(this.textFrom, BorderLayout.CENTER);

		JPanel panelTo = new JPanel(new BorderLayout());
		JLabel labelMax = new JLabel(" to ");
		this.textTo = new JTextField(5);
		this.strTo = "";
		this.textTo.setText("");
		this.textTo.addActionListener(this);
		this.textTo.getDocument().addDocumentListener(this);
		panelTo.add(labelMax, BorderLayout.NORTH);
		panelTo.add(this.textTo, BorderLayout.CENTER);

		constrainBuild(contentSrc, panelFrom, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 10, 10, 5);
		constrainBuild(contentSrc, panelTo, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 5, 10, 10);

		nbPanel.add(contentSrc, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new GridBagLayout());

		this.closeButton = new JButton();
		this.closeButton.setActionCommand("ok");
		this.closeButton.setText("Set");
		this.closeButton.addActionListener(this);

		this.cancelButton = new JButton();
		this.isCanceled = false;
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.addActionListener(this);

		constrainBuild(buttonPanel, this.closeButton, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 30, 20, 20);
		constrainBuild(buttonPanel, this.cancelButton, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 50, 20, 30);

		contentPane.add(nbPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.revalidate();

		setContentPane(contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}
	
	void exitForm(WindowEvent evt) {
		setVisible(false);
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.closeButton) {
			if (!accept()) {
				JOptionPane.showMessageDialog(f,
						"Input failed! Please try again.",
						"ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else 
				setVisible(false);
				
		} else if (source == this.cancelButton) {
			this.isCanceled = true;
			setVisible(false);			
		}
	}
	
	public Point getValue() {
		return this.fromTo;
	}
	
	protected boolean accept() {
		int n1 = convertTextField(this.textFrom, this.strFrom);
		int n2 = convertTextField(this.textTo, this.strTo);	
		if (n1 >= 0 && n1 < n2 && n2 < max) {
			fromTo.x = n1;
			fromTo.y = n2;
			return true;
		}
		else
			return false;
	}
	
	private int convertTextField(JTextField textField, String str) {
		int result = -1;

		if ((str != null) && !str.equals("")) {
			try {
				Integer i = Integer.valueOf(str);
				result = i.intValue();
			} catch (NumberFormatException ex) {}
		}
		return result;
	}
	
	public void insertUpdate(DocumentEvent e) {
		if (e.getDocument() == this.textFrom.getDocument()) {
			Document d = this.textFrom.getDocument();
			try {
				this.strFrom = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
		else if (e.getDocument() == this.textTo.getDocument()) {
			Document d = this.textTo.getDocument();
			try {
				this.strTo = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
	}

	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == this.textFrom.getDocument()) {
			Document d = this.textFrom.getDocument();
			try {
				this.strFrom = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
		else if (e.getDocument() == this.textTo.getDocument()) {
			Document d = this.textTo.getDocument();
			try {
				this.strTo = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
	}

	public void changedUpdate(DocumentEvent e) {}

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

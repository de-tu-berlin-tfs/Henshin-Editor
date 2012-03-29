package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import agg.xt_basis.Type;

/**
 * This class provides a window for a user dialog. This dialog is necessary to
 * enter the grammar layers.
 * 
 * @author $Author: olga $
 * @version $Id: TypeCardinalityDialog.java,v 1.4 2010/09/23 08:23:03 olga Exp $
 */
public class TypeCardinalityDialog extends JDialog implements ActionListener,
		DocumentListener {

	private JTextField textSrcMin, textSrcMax;

	private JTextField textTrgMin, textTrgMax;

	private String stringSrcMin, stringSrcMax;

	private String stringTrgMin, stringTrgMax;

	private JButton closeButton;

	private JButton cancelButton;

	private boolean isCanceled;

	/** the type of the arc */
	private Type type;

	/** the type of the source node */
	private Type sourceType;

	/** the type of the target node */
	private Type targetType;

	private int srcMin;

	private int trgMin;

	private int srcMax;

	private int trgMax;

	private String name = "";

	private boolean multiplicityOK;

	private boolean isEdgeType = true;

	private boolean changed = false;

	final private Vector<Type> failedTypes = new Vector<Type>();
	
	public TypeCardinalityDialog(JFrame parent, Type arcType, Type sourceType,
			Type targetType) {
		super(parent, true);
		this.isEdgeType = true;
		this.name = arcType.getStringRepr();
		this.multiplicityOK = false;
		if (this.name.equals(""))
			this.name = "unnamed";
		setTitle(" Multiplicity of Edge Type < " + this.name + " > ");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				accept();
				exitForm(evt);
			}
		});
		this.type = arcType;
		this.sourceType = sourceType;
		this.targetType = targetType;
		this.srcMax = arcType.getSourceMax(sourceType, targetType);
		this.srcMin = arcType.getSourceMin(sourceType, targetType);
		this.trgMax = arcType.getTargetMax(sourceType, targetType);
		this.trgMin = arcType.getTargetMin(sourceType, targetType);

		if (parent != null)
			setLocationRelativeTo(parent);
		else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width / 2 - 200, screenSize.height / 2 - 200);
		}
		initComponentsOfEdgeType();
	}

	public TypeCardinalityDialog(JFrame parent, Type nodeType) {
		super(parent, true);
		this.isEdgeType = false;
		this.name = nodeType.getStringRepr();
		this.multiplicityOK = false;
		if (this.name.equals(""))
			this.name = "unnamed";
		setTitle(" Multiplicity of Node Type < " + this.name + " > ");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		this.type = nodeType;
		this.sourceType = null;
		this.targetType = null;
		this.srcMax = nodeType.getSourceMax();
		this.srcMin = nodeType.getSourceMin();
		this.trgMax = this.srcMax;
		this.trgMin = this.srcMin;

		if (parent != null)
			setLocationRelativeTo(parent);
		else {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			setLocation(screenSize.width / 2 - 200, screenSize.height / 2 - 200);
		}
		initComponentsOfNodeType();
	}

	private void initComponentsOfEdgeType() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.lightGray);

		JPanel multiplicityPanel = new JPanel(new GridBagLayout());

		JPanel panelSrc = new JPanel(new BorderLayout());
		panelSrc.setBorder(new TitledBorder("  Source of < " + this.name + " > "));
		panelSrc.setBackground(Color.orange);
		JPanel contentSrc = new JPanel(new GridBagLayout());
		panelSrc.add(contentSrc);

		JPanel panelSrcMin = new JPanel(new BorderLayout());
		JLabel label = new JLabel(" min ");
		this.textSrcMin = new JTextField(5);
		if (this.srcMin == Type.UNDEFINED) {
			this.stringSrcMin = "";
			this.textSrcMin.setText(this.stringSrcMin);
		} else {
			this.stringSrcMin = (Integer.valueOf(this.srcMin)).toString();
			this.textSrcMin.setText(this.stringSrcMin);
		}
		this.textSrcMin.addActionListener(this);
		this.textSrcMin.getDocument().addDocumentListener(this);
		panelSrcMin.add(label, BorderLayout.NORTH);
		panelSrcMin.add(this.textSrcMin, BorderLayout.CENTER);

		JPanel panelSrcMax = new JPanel(new BorderLayout());
		label = new JLabel(" max ");
		this.textSrcMax = new JTextField(5);
		if (this.srcMax == Type.UNDEFINED) {
			this.stringSrcMax = "";
			this.textSrcMax.setText(this.stringSrcMax);
		} else {
			this.stringSrcMax = (Integer.valueOf(this.srcMax)).toString();
			this.textSrcMax.setText(this.stringSrcMax);
		}
		this.textSrcMax.addActionListener(this);
		this.textSrcMax.getDocument().addDocumentListener(this);
		panelSrcMax.add(label, BorderLayout.NORTH);
		panelSrcMax.add(this.textSrcMax, BorderLayout.CENTER);

		constrainBuild(contentSrc, panelSrcMin, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 10, 10, 5);
		constrainBuild(contentSrc, panelSrcMax, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 5, 10, 10);

		JPanel panelTrg = new JPanel(new BorderLayout());
		panelTrg.setBorder(new TitledBorder("  Target of < " + this.name + " > "));
		panelTrg.setBackground(Color.orange);
		JPanel contentTrg = new JPanel(new GridBagLayout());
		panelTrg.add(contentTrg);

		JPanel panelTrgMin = new JPanel(new BorderLayout());
		label = new JLabel(" min ");
		this.textTrgMin = new JTextField(5);
		if (this.trgMin == Type.UNDEFINED) {
			this.stringTrgMin = "";
			this.textTrgMin.setText(this.stringTrgMin);
		} else {
			this.stringTrgMin = (Integer.valueOf(this.trgMin)).toString();
			this.textTrgMin.setText(this.stringTrgMin);
		}
		this.textTrgMin.addActionListener(this);
		this.textTrgMin.getDocument().addDocumentListener(this);
		panelTrgMin.add(label, BorderLayout.NORTH);
		panelTrgMin.add(this.textTrgMin, BorderLayout.CENTER);

		JPanel panelTrgMax = new JPanel(new BorderLayout());
		label = new JLabel(" max ");
		this.textTrgMax = new JTextField(5);
		if (this.trgMax == Type.UNDEFINED) {
			this.stringTrgMax = "";
			this.textTrgMax.setText(this.stringTrgMax);
		} else {
			this.stringTrgMax = (Integer.valueOf(this.trgMax)).toString();
			this.textTrgMax.setText(this.stringTrgMax);
		}
		this.textTrgMax.addActionListener(this);
		this.textTrgMax.getDocument().addDocumentListener(this);
		panelTrgMax.add(label, BorderLayout.NORTH);
		panelTrgMax.add(this.textTrgMax, BorderLayout.CENTER);

		constrainBuild(contentTrg, panelTrgMin, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 10, 10, 5);
		constrainBuild(contentTrg, panelTrgMax, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 5, 10, 10);

		constrainBuild(multiplicityPanel, panelSrc, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				20, 20, 10, 10);
		constrainBuild(multiplicityPanel, panelTrg, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				20, 10, 10, 20);

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

		contentPane.add(multiplicityPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.revalidate();

		setContentPane(contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}

	private void initComponentsOfNodeType() {
		JPanel contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.lightGray);

		JPanel multiplicityPanel = new JPanel(new BorderLayout());
		JPanel contentSrc = new JPanel(new GridBagLayout());

		JPanel panelSrcMin = new JPanel(new BorderLayout());
		JLabel labelMin = new JLabel(" min ");
		this.textSrcMin = new JTextField(5);
		if (this.srcMin == Type.UNDEFINED) {
			this.stringSrcMin = "";
			this.textSrcMin.setText(this.stringSrcMin);
		} else {
			this.stringSrcMin = (Integer.valueOf(this.srcMin)).toString();
			this.textSrcMin.setText(this.stringSrcMin);
		}
		this.textSrcMin.addActionListener(this);
		this.textSrcMin.getDocument().addDocumentListener(this);
		panelSrcMin.add(labelMin, BorderLayout.NORTH);
		panelSrcMin.add(this.textSrcMin, BorderLayout.CENTER);

		JPanel panelSrcMax = new JPanel(new BorderLayout());
		JLabel labelMax = new JLabel(" max ");
		this.textSrcMax = new JTextField(5);
		if (this.srcMax == Type.UNDEFINED) {
			this.stringSrcMax = "";
			this.textSrcMax.setText(this.stringSrcMax);
		} else {
			this.stringSrcMax = (Integer.valueOf(this.srcMax)).toString();
			this.textSrcMax.setText(this.stringSrcMax);
		}
		this.textSrcMax.addActionListener(this);
		this.textSrcMax.getDocument().addDocumentListener(this);
		panelSrcMax.add(labelMax, BorderLayout.NORTH);
		panelSrcMax.add(this.textSrcMax, BorderLayout.CENTER);

		constrainBuild(contentSrc, panelSrcMin, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 10, 10, 5);
		constrainBuild(contentSrc, panelSrcMax, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 5, 10, 10);

		multiplicityPanel.add(contentSrc, BorderLayout.CENTER);

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

		contentPane.add(multiplicityPanel, BorderLayout.CENTER);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		contentPane.revalidate();

		setContentPane(contentPane);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		validate();
		pack();
	}

	/** This implements the insertUpdate method of the DocumentListener */
	public void insertUpdate(DocumentEvent e) {
		// System.out.println("TypeCardinalityGUI.insertUpdate
		// "+e.getDocument());
		if (e.getDocument() == this.textSrcMin.getDocument()) {
			Document d = this.textSrcMin.getDocument();
			try {
				this.stringSrcMin = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringSrcMin);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textSrcMax.getDocument()) {
			Document d = this.textSrcMax.getDocument();
			try {
				this.stringSrcMax = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringSrcMax);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMin.getDocument()) {
			Document d = this.textTrgMin.getDocument();
			try {
				this.stringTrgMin = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringTrgMin);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMax.getDocument()) {
			Document d = this.textTrgMax.getDocument();
			try {
				this.stringTrgMax = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringTrgMax);
			} catch (BadLocationException ex) {
			}
		}
	}

	/** This implements the removeUpdate method of the DocumentListener */
	public void removeUpdate(DocumentEvent e) {
		// System.out.println("TypeCardinalityGUI.removeUpdate
		// "+e.getDocument());
		if (e.getDocument() == this.textSrcMin.getDocument()) {
			Document d = this.textSrcMin.getDocument();
			try {
				this.stringSrcMin = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringSrcMin);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textSrcMax.getDocument()) {
			Document d = this.textSrcMax.getDocument();
			try {
				this.stringSrcMax = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringSrcMax);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMin.getDocument()) {
			Document d = this.textTrgMin.getDocument();
			try {
				this.stringTrgMin = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringTrgMin);
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMax.getDocument()) {
			Document d = this.textTrgMax.getDocument();
			try {
				this.stringTrgMax = d.getText(0, d.getLength());
				// System.out.println("string: "+this.stringTrgMax);
			} catch (BadLocationException ex) {
			}
		}
	}

	/** This implements the changeUpdate method of the DocumentListener */
	public void changedUpdate(DocumentEvent e) {
		// System.out.println("TypeCardinalityGUI.changedUpdate
		// "+e.getDocument());
	}

	/**
	 * This handels the clicks on the different buttons.
	 * 
	 * @param e
	 *            The event from the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		// System.out.println("TypeCardinalityGUI.actionPerformed
		// "+e.getSource());
		Object source = e.getSource();
		if (source == this.closeButton) {
			accept();
		} else if (source == this.cancelButton) {
			this.changed = false;
			this.isCanceled = true;
			setVisible(false);
			dispose();
		}
	}

	protected void accept() {
		this.changed = false;
		int value = convertTextField(this.textSrcMin, this.stringSrcMin);		
		if (this.srcMin != value) {
			this.srcMin = value;
			this.changed = true;
		}
		value = convertTextField(this.textSrcMax, this.stringSrcMax);
		if (this.srcMax != value) {
			this.srcMax = value;
			this.changed = true;
		}
		if (this.isEdgeType) {
			value = convertTextField(this.textTrgMin, this.stringTrgMin);
			if (this.trgMin != value) {
				this.trgMin = value;
				this.changed = true;
			}
			value = convertTextField(this.textTrgMax, this.stringTrgMax);
			if (this.trgMax != value) {
				this.trgMax = value;
				this.changed = true;
			}
		}
		if (this.changed) {
			if (	((this.srcMin == Type.UNDEFINED) || (this.srcMin >= 0))
					&& ((this.srcMax == Type.UNDEFINED) || (this.srcMax >= 0))
					&& ((this.trgMin == Type.UNDEFINED) || (this.trgMin >= 0))
					&& ((this.trgMax == Type.UNDEFINED) || (this.trgMax >= 0))
					&& ((this.srcMin >= 0 && this.srcMax >= 0 && this.srcMax >= this.srcMin)
							|| (this.srcMax == Type.UNDEFINED) || (this.srcMin == Type.UNDEFINED))
					&& ((this.trgMin >= 0 && this.trgMax >= 0 && this.trgMax >= this.trgMin)
							|| (this.trgMax == Type.UNDEFINED) || (this.trgMin == Type.UNDEFINED))
				) {
								
				if (this.isEdgeType) {					
					this.type.setSourceMin(this.sourceType, this.targetType, this.srcMin);
					this.type.setSourceMax(this.sourceType, this.targetType, this.srcMax);
					this.type.setTargetMin(this.sourceType, this.targetType, this.trgMin);
					this.type.setTargetMax(this.sourceType, this.targetType, this.trgMax);
					
					this.multiplicityOK = true;
					
				} else { // is Node this.type
					boolean ok = true;
					if (this.srcMin != this.type.getSourceMin()) {
						int val = this.checkMinMultiplicityOfParent(this.srcMin);
						if (val != this.srcMin) {
							ok = false;
							JOptionPane.showMessageDialog(null,
									"Min value of this child type node <"+this.type.getName()+"> is too small.");
						} 					
					}
					if (this.srcMax != this.type.getSourceMax()) {
						int val = this.checkMaxMultiplicityOfParent(this.srcMax);
						if (val != this.srcMax) {
							ok = false;
							
							JOptionPane.showMessageDialog(null,
									"Max value of the child type node <"+this.type.getName()+"> is too large.");
						} 					
					}
					if (ok) {
						this.multiplicityOK = true;
						this.type.setSourceMin(this.srcMin);
						this.type.setSourceMax(this.srcMax);
					} 					
				}
				
				if (this.multiplicityOK) {
					setVisible(false);
					dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Multiplicity value failed. \nAllowed value: -1 | empty | >= 0 | max >= min.",
						"Type Multiplicity", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			this.multiplicityOK = true;
			setVisible(false);
			dispose();
		}
	}

	public boolean isChanged() {
		return this.changed;
	}

	/**
	 * converts the content of a textfield to a multiplicity number.
	 * 
	 * @returns {@link Type.UNDEFINED} or a positive number.
	 */
	private int convertTextField(JTextField textField, String text) {
		String str = text;
		int result = -100;

		if ((str == null) || (str.equals("")) || (str.equals("*"))) {
			result = Type.UNDEFINED;
		} else {
			try {
				Integer i = Integer.valueOf(str);
				result = i.intValue();
			} catch (NumberFormatException ex) {
			}
		}
		return result;
	}// convertTextField

	public boolean isMultiplicitySet() {
		return this.multiplicityOK;
	}

	public JButton getSetButton() {
		return this.closeButton;
	}

	public void showGUI() {
		setVisible(true);
	}

	public boolean wasCanceled() {
		return this.isCanceled;
	}

	
	private int checkMaxMultiplicityOfParent(int value) {
		if (this.isEdgeType) 
			return -1;
		
		this.failedTypes.clear();
		int res = value;
		int old = this.type.getSourceMax();
		this.type.setSourceMax(value);
		int sum = -1;
		Vector<Type> v = this.type.getAllParents();
		for (int i = 1; i < v.size(); i++) {
			Type p = v.get(i);
			if (p.getSourceMax() == Type.UNDEFINED
					|| value == Type.UNDEFINED) 
				continue;
			
			sum = p.getMaxMultiplicityOfAllChildren();
			if (sum > p.getSourceMax()) {
				if (!this.failedTypes.contains(p))
					this.failedTypes.add(p);
				res = value - (sum - p.getSourceMax());
			}
//			System.out.println("sum: "+sum+" p SourceMax"+p.getSourceMax()+"   value "+value+"  res "+res);
		}
		this.type.setSourceMax(old);
		
		return res;
	}
	
	private int checkMinMultiplicityOfParent(int value) {
		if (this.isEdgeType) 
			return -1;
		
		this.failedTypes.clear();
		int res = value;
		int old = this.type.getSourceMin();
		this.type.setSourceMin(value);
		int sum = -1;
		Vector<Type> v = this.type.getAllParents();
		for (int i = 1; i < v.size(); i++) {
			Type p = v.get(i);
			if (p.getSourceMin() == Type.UNDEFINED
					|| value == Type.UNDEFINED) 
				continue;
			sum = p.getMinMultiplicityOfAllChildren();
			if (sum < p.getSourceMin()) {
				if (!this.failedTypes.contains(p))
					this.failedTypes.add(p);
				res = value + (p.getSourceMin() - sum);
			}
//			System.out.println("sum: "+sum+" p SourceMin"+p.getSourceMin()+"   value "+value+"  res "+res);
		}
		this.type.setSourceMin(old);
		return res;
	}

	/*
	private int checkMaxOfParentNode(int value) {
		int res = value;
		Vector<Type> v = this.type.getAllParents();
		for (int i = 1; i < v.size(); i++) {
			Type t = v.get(i);
			if (t.getSourceMax() != Type.UNDEFINED) {
				if (value != Type.UNDEFINED) {
					if (value > t.getSourceMax())
						res = t.getSourceMax();
				} else
					res = t.getSourceMax();
			}
			if (res != value)
				break;
		}
		return res;
	}
*/
	
	/** Exit the Application */
	void exitForm(WindowEvent evt) {
		setVisible(false);
		dispose();
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

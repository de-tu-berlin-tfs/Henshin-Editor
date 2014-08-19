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
	private agg.xt_basis.Type type;

	/** the type of the source node */
	private agg.xt_basis.Type sourceType;

	/** the type of the target node */
	private agg.xt_basis.Type targetType;

	private int srcMin;

	private int trgMin;

	private int srcMax;

	private int trgMax;

	private String name = "";

	private boolean multiplicityOK;

	private boolean isEdgeType = true;

	private boolean changed = false;

	final private Vector<agg.xt_basis.Type> failedTypes = new Vector<agg.xt_basis.Type>();
	
	public TypeCardinalityDialog(JFrame parent, agg.xt_basis.Type arcType, agg.xt_basis.Type sourceType,
			agg.xt_basis.Type targetType) {
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

	public TypeCardinalityDialog(JFrame parent, agg.xt_basis.Type nodeType) {
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
		if (this.srcMin == agg.xt_basis.Type.UNDEFINED) {
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
		if (this.srcMax == agg.xt_basis.Type.UNDEFINED) {
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
		if (this.trgMin == agg.xt_basis.Type.UNDEFINED) {
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
		if (this.trgMax == agg.xt_basis.Type.UNDEFINED) {
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
		if (this.srcMin == agg.xt_basis.Type.UNDEFINED) {
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
		if (this.srcMax == agg.xt_basis.Type.UNDEFINED) {
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
		if (e.getDocument() == this.textSrcMin.getDocument()) {
			Document d = this.textSrcMin.getDocument();
			try {
				this.stringSrcMin = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textSrcMax.getDocument()) {
			Document d = this.textSrcMax.getDocument();
			try {
				this.stringSrcMax = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMin.getDocument()) {
			Document d = this.textTrgMin.getDocument();
			try {
				this.stringTrgMin = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMax.getDocument()) {
			Document d = this.textTrgMax.getDocument();
			try {
				this.stringTrgMax = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
	}

	/** This implements the removeUpdate method of the DocumentListener */
	public void removeUpdate(DocumentEvent e) {
		if (e.getDocument() == this.textSrcMin.getDocument()) {
			Document d = this.textSrcMin.getDocument();
			try {
				this.stringSrcMin = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textSrcMax.getDocument()) {
			Document d = this.textSrcMax.getDocument();
			try {
				this.stringSrcMax = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMin.getDocument()) {
			Document d = this.textTrgMin.getDocument();
			try {
				this.stringTrgMin = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		} else if (e.getDocument() == this.textTrgMax.getDocument()) {
			Document d = this.textTrgMax.getDocument();
			try {
				this.stringTrgMax = d.getText(0, d.getLength());
			} catch (BadLocationException ex) {
			}
		}
	}

	/** This implements the changeUpdate method of the DocumentListener */
	public void changedUpdate(DocumentEvent e) {
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
			if (	((this.srcMin == agg.xt_basis.Type.UNDEFINED) || (this.srcMin >= 0))
					&& ((this.srcMax == agg.xt_basis.Type.UNDEFINED) || (this.srcMax >= 0))
					&& ((this.trgMin == agg.xt_basis.Type.UNDEFINED) || (this.trgMin >= 0))
					&& ((this.trgMax == agg.xt_basis.Type.UNDEFINED) || (this.trgMax >= 0))
					&& ((this.srcMin >= 0 && this.srcMax >= 0 && this.srcMax >= this.srcMin)
							|| (this.srcMax == agg.xt_basis.Type.UNDEFINED) || (this.srcMin == agg.xt_basis.Type.UNDEFINED))
					&& ((this.trgMin >= 0 && this.trgMax >= 0 && this.trgMax >= this.trgMin)
							|| (this.trgMax == agg.xt_basis.Type.UNDEFINED) || (this.trgMin == agg.xt_basis.Type.UNDEFINED))
				) 
			{	
				setVisible(false);
				
				if (this.isEdgeType) {					
					this.type.setSourceMin(this.sourceType, this.targetType, this.srcMin);
					this.type.setSourceMax(this.sourceType, this.targetType, this.srcMax);
					this.type.setTargetMin(this.sourceType, this.targetType, this.trgMin);
					this.type.setTargetMax(this.sourceType, this.targetType, this.trgMax);
					
					this.multiplicityOK = true;
				} 
				else { // is Node this.type
					boolean ok = true;
					if (this.srcMin != this.type.getSourceMin()) {
						int val = this.checkMinMultiplicityOfParent(this.srcMin);
						if (val != this.srcMin) {
							ok = true; //false;
							this.srcMin = val;
							JOptionPane.showMessageDialog(null,
									"Min value for this child type <"+this.type.getName()+"> failed \nand reset to its parent min value.",
									"Node Type Multiplicity", JOptionPane.INFORMATION_MESSAGE);
						} 					
					}
					if (this.srcMax != this.type.getSourceMax()) {
						int val = this.checkMaxMultiplicityOfParent(this.srcMax);
						if (val != this.srcMax) {
							ok = true; //false;	
							this.srcMax = val;
							JOptionPane.showMessageDialog(null,
									"Max value for this child type <"+this.type.getName()+"> failed \nand reset to its parent max value.",
									"Node Type Multiplicity", JOptionPane.INFORMATION_MESSAGE);
						} 					
					}
					if (ok) {
						this.multiplicityOK = true;
						this.type.setSourceMin(this.srcMin);
						if (this.srcMin != agg.xt_basis.Type.UNDEFINED)
							propagateMinMultiplicityOfParent(this.type, this.srcMin);
						
						this.type.setSourceMax(this.srcMax);
						if (this.srcMax != agg.xt_basis.Type.UNDEFINED)
							propagateMaxMultiplicityOfParent(this.type, this.srcMax);						
					} 					
				}
				
				if (this.multiplicityOK) {
					setVisible(false);
					dispose();
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Multiplicity value failed. \nAllowed value: -1 | empty | >= 0 | max >= min",
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
	 * converts the content of a text field to a multiplicity number.
	 * 
	 * @returns {@link Type.UNDEFINED} or a positive number.
	 */
	private int convertTextField(JTextField textField, String text) {
		String str = text;
		int result = -100;

		if ((str == null) || (str.equals("")) || (str.equals("*"))) {
			result = agg.xt_basis.Type.UNDEFINED;
		} else {
			try {
				Integer i = Integer.valueOf(str);
				result = i.intValue();
			} catch (NumberFormatException ex) {
			}
		}
		return result;
	}

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
		this.failedTypes.clear();
		int res = value;
		Vector<agg.xt_basis.Type> v = this.type.getAllParents();
		for (int i = 1; i < v.size(); i++) {
			agg.xt_basis.Type p = v.get(i);
			if (p.getSourceMax() == agg.xt_basis.Type.UNDEFINED)
				continue;
			else if (res == agg.xt_basis.Type.UNDEFINED
					|| res > p.getSourceMax()) {
				res = p.getSourceMax();
			}
		}
		return res;
	}
	
	private int checkMinMultiplicityOfParent(int value) {
		this.failedTypes.clear();		
		int res = value;
		Vector<agg.xt_basis.Type> v = this.type.getAllParents();
		for (int i = 1; i < v.size(); i++) {
			agg.xt_basis.Type p = v.get(i);
			if (p.getSourceMin() == agg.xt_basis.Type.UNDEFINED)
				continue;
			else if (res == agg.xt_basis.Type.UNDEFINED
					|| res < p.getSourceMin()) {
				res = p.getSourceMin();
			}
		}
		return res;
	}

	private void propagateMaxMultiplicityOfParent(agg.xt_basis.Type p, int value) {
		Vector<agg.xt_basis.Type> v = p.getChildren();
		for (int i = 0; i < v.size(); i++) {
			agg.xt_basis.Type ch = v.get(i);
			if (ch.getSourceMax() == agg.xt_basis.Type.UNDEFINED
					|| ch.getSourceMax() > value) {
				ch.setSourceMax(value);
				propagateMaxMultiplicityOfParent(ch, value);
			}
		}
	}
		
	private void propagateMinMultiplicityOfParent(agg.xt_basis.Type p, int value) {
		Vector<agg.xt_basis.Type> v = p.getChildren();
		for (int i = 0; i < v.size(); i++) {
			agg.xt_basis.Type ch = v.get(i);
			if (ch.getSourceMin() == agg.xt_basis.Type.UNDEFINED
					|| ch.getSourceMax() < value) {
				ch.setSourceMin(value);
				propagateMinMultiplicityOfParent(ch, value);
			}
		}
	}
	
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

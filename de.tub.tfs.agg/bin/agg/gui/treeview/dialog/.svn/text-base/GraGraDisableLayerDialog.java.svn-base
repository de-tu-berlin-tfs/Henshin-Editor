package agg.gui.treeview.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.JCheckBox;

import agg.editor.impl.EdGraGra;

/**
 * This class provides a window for a user dialog. This dialog allows to enable
 * or disable the grammar layers.
 */
public class GraGraDisableLayerDialog extends JDialog implements ActionListener {

	private JPanel contentPane;

	private JPanel panel;

	private JPanel buttonPanel;

	private JScrollPane scrollPane;

	private JButton closeButton;

	private JButton cancelButton;

	private boolean isCancelled;

	private Vector<String> layers;

	private Hashtable<String, JCheckBox> table;

	private EdGraGra gragra;

	private boolean changed = false;

	/**
	 * Creates new form GraGraDisableLayerGUI
	 * 
	 * @param parent
	 *            The parent frame of this gui.
	 * @param layers
	 *            The layers of a grammar.
	 */
	public GraGraDisableLayerDialog(JFrame parent, Vector<String> layers) {
		super(parent, true);

		setTitle("Layer");
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				exitForm(evt);
			}
		});
		this.layers = layers;
		this.table = new Hashtable<String, JCheckBox>(layers.size());
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

		this.panel = new JPanel(new BorderLayout());
		this.panel.setBackground(Color.orange);
		this.panel.setBorder(new TitledBorder("Disable / enable Rule Layer"));
		JPanel layerPanel = new JPanel(new GridLayout(0, 1));
		for (int i=0; i<this.layers.size(); i++) {
			String l = this.layers.get(i);
			JCheckBox cb = new JCheckBox(" Layer " + l + "    ( enabled )",
					true);
			cb.addActionListener(this);
			layerPanel.add(cb);
			this.table.put(l, cb);
		}
		int hght = getHeight(this.layers.size(), 25);
		this.scrollPane = new JScrollPane(layerPanel);
		this.scrollPane.setPreferredSize(new Dimension(200, hght));
		this.panel.add(this.scrollPane);

		this.buttonPanel = new JPanel(new GridBagLayout());
		this.closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.setToolTipText("Accept entries and close dialog.");
		this.closeButton.addActionListener(this);

		this.cancelButton = new JButton();
		this.isCancelled = false;
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.setToolTipText("Reject entries and close dialog.");
		this.cancelButton.addActionListener(this);

		constrainBuild(this.buttonPanel, this.closeButton, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 10, 10, 5);
		constrainBuild(this.buttonPanel, this.cancelButton, 1, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 10, 10);
		this.contentPane.add(this.panel, BorderLayout.CENTER);
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

	public boolean hasChanged() {
		return this.changed;
	}

	private void acceptValues() {
		Enumeration<?> e = this.table.keys();
		while (e.hasMoreElements()) {
			String l = (String) e.nextElement();
			if (this.table.get(l).isSelected())
				this.gragra.getBasisGraGra().enableRuleLayer(
						(Integer.valueOf(l)).intValue(), true);
			else
				this.gragra.getBasisGraGra().enableRuleLayer(
						(Integer.valueOf(l)).intValue(), false);
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
		if (source instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) source;
			if (cb.isSelected())
				cb.setText(cb.getText().replaceAll("disabled ", " enabled"));
			else
				cb.setText(cb.getText().replaceAll("enabled", "disabled"));
			this.changed = true;
		} else if (source == this.closeButton) {
			acceptValues();
			setVisible(false);
			dispose();
		} else if (source == this.cancelButton) {
			this.isCancelled = true;
			setVisible(false);
			dispose();
		}
	}

	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
		Enumeration<?> e = this.table.keys();
		while (e.hasMoreElements()) {
			String l = (String) e.nextElement();
			if (!this.gragra.getBasisGraGra().isRuleLayerEnabled(
					((Integer.valueOf(l)).intValue()))) {
				JCheckBox cb = this.table.get(l);
				cb.setSelected(false);
				cb.setText(cb.getText().replaceAll("enabled", "disabled"));
			}
		}
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

package agg.gui.options;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableCellRenderer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import agg.layout.evolutionary.LayoutPattern;
import agg.layout.evolutionary.EvolutionaryGraphLayout;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdType;
import agg.gui.icons.ColorDashLineIcon;
import agg.gui.icons.ColorDotLineIcon;
import agg.gui.icons.ColorSolidLineIcon;
import agg.gui.icons.CircleShapeIcon;
import agg.gui.icons.OvalShapeIcon;
import agg.gui.icons.RectShapeIcon;
import agg.gui.icons.RoundRectShapeIcon;
import agg.gui.editor.EditorConstants;
import agg.gui.event.TypeEvent;

public class GraphLayouterOptionGUI extends AbstractOptionGUI implements
		ActionListener, ListSelectionListener, ChangeListener {

	protected EvolutionaryGraphLayout layouter;

	protected JCheckBox enableLayouterCB, saveCB, metricsCB, centerCB,
			fixedNodePositionCB, usePatternCB;

	protected JRadioButtonMenuItem x_leftCB, x_rightCB, x_equalCB, y_aboveCB, y_underCB,
			y_equalCB;

	protected Vector<JRadioButtonMenuItem> edgeXgroup, edgeYgroup;

	protected JTextField iterLayoutTF, generalEdgeLengthTF, temperatureTF,
			nodeClusterSpanTF, edgeLengthTF;

	// protected JTextField iterNodeInterTF, iterEdgeInterTF;

	protected int edgeLength, iterCount, generalEdgeLength, temperature, nodeClusterSpan;

	protected JComboBox edgeTypeCB, nodeTypeCB;

	protected JButton showPattern, displaySwitch1, displaySwitch2;

	protected JPanel layoutOptions;

	protected JPanel generalLayoutPatternP, nodeLayoutPatternP, edgeLayoutPatternP;

	protected JTable patternTable;

	protected JTabbedPane tabbedPane;

	protected JScrollPane scrollpanePatternTable;

	protected JDialog tableFrame;

	protected Vector<EdType> edgeTypes, nodeTypes;

	protected EdGraGra gragra;

	public GraphLayouterOptionGUI(EvolutionaryGraphLayout graphLayouter) {
		super();
		this.layouter = graphLayouter;

		this.layoutOptions = createLayoutOptionsPanel();

		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;

		add(this.layoutOptions, c);
		validate();

		enableButtons(this.enableLayouterCB.isSelected());

		updateGraphLayouter();
	}

	private JPanel createLayoutOptionsPanel() {
		GridBagLayout gridbag = new GridBagLayout();
//		GridBagConstraints c = new GridBagConstraints();
		Border border = new TitledBorder(
				" Evolutionary Layout for Graph Sequences ");
		JPanel p = new JPanel();
		p.setLayout(gridbag);
		p.setBorder(border);

		JPanel p1 = new JPanel(new GridLayout(0, 1));
		this.enableLayouterCB = new JCheckBox(
				" perform during graph transformation ", null, false);
		this.layouter.setEnabled(false);
		this.enableLayouterCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphLayouterOptionGUI.this.layouter.setEnabled(((JCheckBox) e.getSource()).isSelected());
				enableButtons(((JCheckBox) e.getSource()).isSelected());
			}
		});
		p1.add(this.enableLayouterCB);

		JPanel p2 = new JPanel(new GridLayout(0, 1));
		p2.setBorder(new TitledBorder(" Output "));
		JLabel l = new JLabel(" Graph sequence as JPEG ( .jpg ) images ");
		p2.add(l);
		this.saveCB = new JCheckBox(" write to directory", null, false);
		this.saveCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphLayouterOptionGUI.this.layouter.setJpgOutput(((JCheckBox) e.getSource()).isSelected());
				if (((JCheckBox) e.getSource()).isSelected()) {
					GraphLayouterOptionGUI.this.metricsCB.setSelected(true);
					GraphLayouterOptionGUI.this.layouter.setWriteMetricValues(true);
				}
			}
		});
		p2.add(this.saveCB);

		l = new JLabel(" Quality metrics ");
		p2.add(l);
		this.metricsCB = new JCheckBox(" write to ( .log ) file ", null, false);
		this.metricsCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphLayouterOptionGUI.this.layouter.setWriteMetricValues(((JCheckBox) e.getSource())
						.isSelected());
			}
		});
		p2.add(this.metricsCB);

		/*
		 * //JPanel p3 = new JPanel(new GridLayout(0, 1)); //l = new JLabel("
		 * Center"); //p3.add(l); //centerCB = new JCheckBox(" to centre", null,
		 * false); // layouter.setDooCentre(false); //
		 * centerCB.addActionListener(new ActionListener(){ // public void
		 * actionPerformed(ActionEvent e) // { //
		 * layouter.setDoCenter(((JCheckBox)e.getSource()).isSelected()); //
		 * }}); //p3.add(centerCB);
		 * 
		 * JPanel p4 = new JPanel(); l = new JLabel(" iteration count of layout
		 * process "); iterCount = 100;
		 * this.layouter.setIterationCount(iterCount); iterLayoutTF = new
		 * JTextField((Integer.valueOf(iterCount)).toString(), 5);
		 * iterLayoutTF.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e){
		 * if(!((JTextField)e.getSource()).getText().equals("")){ try{ Integer
		 * nb = Integer.valueOf(((JTextField)e.getSource()).getText()); if(nb !=
		 * null){ layouter.setIterationCount(nb.intValue());
		 * //System.out.println("Iteration count:
		 * "+layouter.getIterationCount()); } }catch(NumberFormatException
		 * ex){iterLayoutTF.setText("");} } }}); p4.add(iterLayoutTF);
		 * p4.add(l);
		 * 
		 * JPanel p5 = new JPanel(); l = new JLabel(" initial temperature of
		 * cooling "); temperature = 100;
		 * this.layouter.setBeginTemperature(temperature); temperatureTF = new
		 * JTextField(((Integer.valueOf(temperature)).toString()), 5);
		 * temperatureTF.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent e){
		 * if(!((JTextField)e.getSource()).getText().equals("")){ try{ Integer
		 * nb = Integer.valueOf(((JTextField)e.getSource()).getText()); if(nb !=
		 * null){ layouter.setBeginTemperature(nb.intValue());
		 * //System.out.println("Begin temperature:
		 * "+layouter.getBeginTemperature()); } }catch(NumberFormatException
		 * ex){temperatureTF.setText("");} } }}); p5.add(temperatureTF);
		 * p5.add(l);
		 * 
		 * JPanel p6 = new JPanel(); l = new JLabel(" preferred edge length ");
		 * generalEdgeLength = 100;
		 * this.layouter.setGeneralEdgeLength(generalEdgeLength);
		 * generalEdgeLengthTF = new JTextField(((new
		 * Integer(generalEdgeLength)).toString()), 5);
		 * generalEdgeLengthTF.addActionListener(new ActionListener(){ public
		 * void actionPerformed(ActionEvent e){
		 * if(!((JTextField)e.getSource()).getText().equals("")){ try{ Integer
		 * nb = Integer.valueOf(((JTextField)e.getSource()).getText()); if(nb !=
		 * null){ layouter.setGeneralEdgeLength(nb.intValue()); }
		 * }catch(NumberFormatException ex){
		 * generalEdgeLengthTF.setText("200");} } }});
		 * p6.add(generalEdgeLengthTF); p6.add(l);
		 */

		// Layout Pattern
		JPanel p7 = new JPanel(new GridLayout(0, 1));
		this.usePatternCB = new JCheckBox(" use layout pattern ", null, false);
		this.usePatternCB.setEnabled(false);
		this.usePatternCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphLayouterOptionGUI.this.layouter
						.setUsePattern(((JCheckBox) e.getSource()).isSelected());
				enablePattern(GraphLayouterOptionGUI.this.enableLayouterCB.isSelected(), GraphLayouterOptionGUI.this.usePatternCB
						.isSelected());
			}
		});
		p7.add(this.usePatternCB);

		JPanel p8 = createLayoutPatternOptionsPanel();

		constrainBuild(p, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p, p2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 15, 5);
		// constrainBuild(p, p3, 0, 2, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		// constrainBuild(p, p4, 0, 3, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		// constrainBuild(p, p5, 0, 4, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		// constrainBuild(p, p6, 0, 5, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		constrainBuild(p, p7, 0, 6, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p, p8, 0, 7, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);

		p.validate();

		return p;
	}

	private JPanel createLayoutPatternOptionsPanel() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());

		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addChangeListener(this);

		this.generalLayoutPatternP = createGeneralPatternOptionsPanel();
		this.edgeLayoutPatternP = createEdgeLayoutPatternOptionsPanel();
		this.nodeLayoutPatternP = createNodeLayoutPatternOptionsPanel();
		this.patternTable = createLayoutPatternTable();

		this.tabbedPane.addTab(" General ", null, this.generalLayoutPatternP, "");
		this.tabbedPane.addTab(" Edge Type Pattern ", null, this.edgeLayoutPatternP, "");
		this.tabbedPane.addTab(" Node Type Pattern ", null, this.nodeLayoutPatternP, "");

		JPanel p3 = new JPanel();
		JLabel l = new JLabel(" Table of Graph Layout Pattern ");
		this.showPattern = new JButton("Show");
		this.showPattern.setEnabled(false);
		this.showPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GraphLayouterOptionGUI.this.tableFrame != null) {
					if (!GraphLayouterOptionGUI.this.tableFrame.isVisible()) {
						GraphLayouterOptionGUI.this.tableFrame.setLocation(((JButton) e.getSource())
								.getLocationOnScreen());
						GraphLayouterOptionGUI.this.tableFrame.setVisible(true);
					} else {
						GraphLayouterOptionGUI.this.tableFrame.toFront();
					}
				}
			}
		});
		p3.add(l);
		p3.add(this.showPattern);

		p.add(this.tabbedPane, BorderLayout.CENTER);
		p.add(p3, BorderLayout.SOUTH);

		return p;
	}

	private JPanel createGeneralPatternOptionsPanel() {
		GridBagLayout gridbag = new GridBagLayout();
//		GridBagConstraints c = new GridBagConstraints();
		JPanel p = new JPanel();
		p.setLayout(gridbag);

		// JPanel p3 = new JPanel(new GridLayout(0, 1));
		// l = new JLabel(" Center");
		// p3.add(l);
		// centerCB = new JCheckBox(" to centre", null, false);
		// layouter.setDooCentre(false);
		// centerCB.addActionListener(new ActionListener(){
		// public void actionPerformed(ActionEvent e)
		// {
		// layouter.setDoCenter(((JCheckBox)e.getSource()).isSelected());
		// }});
		// p3.add(centerCB);

		JPanel p4 = new JPanel();
		JLabel l = new JLabel(" iteration count of layout process ");
		this.iterCount = 100;
		this.layouter.setIterationCount(this.iterCount);
		this.iterLayoutTF = new JTextField((Integer.valueOf(this.iterCount)).toString(), 5);
		this.iterLayoutTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());

						GraphLayouterOptionGUI.this.layouter.setIterationCount(nb.intValue());
						// System.out.println("Iteration count:
						// "+layouter.getIterationCount());
					} catch (NumberFormatException ex) {
						GraphLayouterOptionGUI.this.iterLayoutTF.setText("");
					}
				}
			}
		});
		p4.add(this.iterLayoutTF);
		p4.add(l);

		JPanel p5 = new JPanel();
		l = new JLabel(" initial temperature of cooling ");
		this.temperature = 100;
		this.layouter.setBeginTemperature(this.temperature);
		this.temperatureTF = new JTextField(((Integer.valueOf(this.temperature)).toString()),
				5);
		this.temperatureTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						GraphLayouterOptionGUI.this.layouter.setBeginTemperature(nb.intValue());
						// System.out.println("Begin temperature:
						// "+layouter.getBeginTemperature());
					} catch (NumberFormatException ex) {
						GraphLayouterOptionGUI.this.temperatureTF.setText("");
					}
				}
			}
		});
		p5.add(this.temperatureTF);
		p5.add(l);

		JPanel p6 = new JPanel();
		l = new JLabel(" preferred edge length ");
		this.generalEdgeLength = 100;
		this.layouter.setGeneralEdgeLength(this.generalEdgeLength);
		this.generalEdgeLengthTF = new JTextField(((Integer.valueOf(this.generalEdgeLength))
				.toString()), 5);
		this.generalEdgeLengthTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						GraphLayouterOptionGUI.this.layouter.setGeneralEdgeLength(nb.intValue());
					} catch (NumberFormatException ex) {
						GraphLayouterOptionGUI.this.generalEdgeLengthTF.setText("200");
					}
				}
			}
		});
		p6.add(this.generalEdgeLengthTF);
		p6.add(l);

		JPanel p7 = new JPanel();
		l = new JLabel(" span of node cluster ");
		this.nodeClusterSpan = 200;
		this.layouter.getLayoutMetrics().setEpsilon(this.nodeClusterSpan);
		this.nodeClusterSpanTF = new JTextField(((Integer.valueOf(this.nodeClusterSpan))
				.toString()), 5);
		this.nodeClusterSpanTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						GraphLayouterOptionGUI.this.layouter.getLayoutMetrics().setEpsilon(nb.intValue());
					} catch (NumberFormatException ex) {
						GraphLayouterOptionGUI.this.nodeClusterSpanTF.setText("");
					}
				}
			}
		});
		p7.add(this.nodeClusterSpanTF);
		p7.add(l);

		// constrainBuild(p, p3, 0, 2, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p, p4, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		constrainBuild(p, p5, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		constrainBuild(p, p6, 0, 2, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);
		constrainBuild(p, p7, 0, 3, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 0, 5, 0, 5);

		return p;
	}

	private JPanel createEdgeLayoutPatternOptionsPanel() {
		if (this.edgeXgroup == null)
			this.edgeXgroup = new Vector<JRadioButtonMenuItem>();
		if (this.edgeYgroup == null)
			this.edgeYgroup = new Vector<JRadioButtonMenuItem>();

		GridBagLayout gridbag = new GridBagLayout();
//		GridBagConstraints c = new GridBagConstraints();
		JPanel p = new JPanel();
		p.setLayout(gridbag);

		JPanel p1 = new JPanel(new GridLayout(1, 0));
		JLabel l = new JLabel(" Selected edge type ");
		p1.add(l);
		this.edgeTypeCB = createEdgeTypeComboBox();
		p1.add(this.edgeTypeCB);

		JPanel p2 = new JPanel(new BorderLayout()); // new GridLayout(0, 1));
		p2.setBorder(new TitledBorder(" Layout Pattern "));

		JPanel p2_1 = new JPanel(new GridLayout(1, 0));

		JPanel p2_1_1 = new JPanel(new GridLayout(0, 1));
		l = new JLabel("  X - Axis");
		p2_1_1.add(l);

		this.x_leftCB = new JRadioButtonMenuItem(" Target left Source", null, false);
		this.x_leftCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeXgroup, GraphLayouterOptionGUI.this.x_leftCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.x_leftCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', -1);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', -1);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarLeftSrc",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				}
			}
		});
		this.edgeXgroup.add(this.x_leftCB);
		p2_1_1.add(this.x_leftCB);

		this.x_rightCB = new JRadioButtonMenuItem(" Target right Source", null,
				false);
		this.x_rightCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeXgroup, GraphLayouterOptionGUI.this.x_rightCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.x_rightCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', 1);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', 1);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarRightSrc",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				}
			}
		});
		this.edgeXgroup.add(this.x_rightCB);
		p2_1_1.add(this.x_rightCB);

		this.x_equalCB = new JRadioButtonMenuItem(" Target equal Source", null,
				false);
		this.x_equalCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeXgroup, GraphLayouterOptionGUI.this.x_equalCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.x_equalCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', 0);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("hor_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'x', 0);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarEqualSrc",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "hor_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 1);
				}
			}
		});
		this.edgeXgroup.add(this.x_equalCB);
		p2_1_1.add(this.x_equalCB);

		JPanel p2_1_2 = new JPanel(new GridLayout(0, 1));
		l = new JLabel("  Y - Axis");
		p2_1_2.add(l);

		this.y_aboveCB = new JRadioButtonMenuItem(" Target above Source", null,
				false);
		this.y_aboveCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeYgroup, GraphLayouterOptionGUI.this.y_aboveCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.y_aboveCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', -1);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', -1);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarAboveSrc",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 2);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 2);
				}
			}
		});
		this.edgeYgroup.add(this.y_aboveCB);
		p2_1_2.add(this.y_aboveCB);

		this.y_underCB = new JRadioButtonMenuItem(" Target under Source", null,
				false);
		this.y_underCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeYgroup, GraphLayouterOptionGUI.this.y_underCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.y_underCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', 1);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', 1);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarUnderSrc",
								indx +GraphLayouterOptionGUI.this. nodeTypes.size(), 2);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 2);
				}
			}
		});
		this.edgeYgroup.add(this.y_underCB);
		p2_1_2.add(this.y_underCB);

		this.y_equalCB = new JRadioButtonMenuItem(" Target equal Source", null,
				false);
		this.y_equalCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				updateGroup(GraphLayouterOptionGUI.this.edgeYgroup, GraphLayouterOptionGUI.this.y_equalCB);
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (GraphLayouterOptionGUI.this.y_equalCB.isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', 0);
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("ver_tree", "edge",
								GraphLayouterOptionGUI.this.edgeTypes.get(indx).getBasisType(), 'y', 0);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("TarEqualSrc",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 2);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "ver_tree");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 2);
				}
			}
		});
		this.edgeYgroup.add(this.y_equalCB);
		p2_1_2.add(this.y_equalCB);

		p2_1.add(p2_1_1);
		p2_1.add(p2_1_2);

		JPanel p2_2 = new JPanel();
		l = new JLabel(" preferred length ");
		this.edgeLength = 0; // 200;
		this.edgeLengthTF = new JTextField(String.valueOf(this.edgeLength), 5);
		this.edgeLengthTF.setColumns(5);
		this.edgeLengthTF.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indx = GraphLayouterOptionGUI.this.edgeTypeCB.getSelectedIndex();
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						if (nb.intValue() > 0) {
							if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
								GraphLayouterOptionGUI.this.gragra.createLayoutPattern("edge_length",
										"edge", GraphLayouterOptionGUI.this.edgeTypes.get(indx)
												.getBasisType(), nb.intValue());
							else if (GraphLayouterOptionGUI.this.edgeTypes != null)
								GraphLayouterOptionGUI.this.layouter.createLayoutPattern("edge_length",
										"edge", GraphLayouterOptionGUI.this.edgeTypes.get(indx)
												.getBasisType(), nb.intValue());
							if (GraphLayouterOptionGUI.this.patternTable != null)
								GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt(
										nb.toString(), indx + GraphLayouterOptionGUI.this.nodeTypes.size(),
										3);
						} else {
							GraphLayouterOptionGUI.this.edgeLengthTF.setText("0"); // String.valueOf(generalEdgeLength));
							if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
								GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
										.getBasisType(), "edge_length");
							else if (GraphLayouterOptionGUI.this.edgeTypes != null)
								GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes
										.get(indx).getBasisType(),
										"edge_length");
							if (GraphLayouterOptionGUI.this.patternTable != null
									&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
											+ GraphLayouterOptionGUI.this.nodeTypes.size())
								GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
										indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 3);
						}
					} catch (NumberFormatException ex) {
						GraphLayouterOptionGUI.this.edgeLengthTF.setText("0");
						if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
							GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
									.getBasisType(), "edge_length");
						else if (GraphLayouterOptionGUI.this.edgeTypes != null)
							GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
									.getBasisType(), "edge_length");
						if (GraphLayouterOptionGUI.this.patternTable != null
								&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
										+ GraphLayouterOptionGUI.this.nodeTypes.size())
							GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
									indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 3);
					}
				} else {
					GraphLayouterOptionGUI.this.edgeLengthTF.setText("0");
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.gragra.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "edge_length");
					else if (GraphLayouterOptionGUI.this.edgeTypes != null)
						GraphLayouterOptionGUI.this.layouter.removeLayoutPattern(GraphLayouterOptionGUI.this.edgeTypes.get(indx)
								.getBasisType(), "edge_length");
					if (GraphLayouterOptionGUI.this.patternTable != null
							&& GraphLayouterOptionGUI.this.patternTable.getRowCount() > indx
									+ GraphLayouterOptionGUI.this.nodeTypes.size())
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("",
								indx + GraphLayouterOptionGUI.this.nodeTypes.size(), 3);
				}
			}
		});

		p2_2.add(this.edgeLengthTF, BorderLayout.WEST);
		p2_2.add(l, BorderLayout.CENTER);

		p2.add(p2_1, BorderLayout.CENTER);
		p2.add(p2_2, BorderLayout.SOUTH);

		constrainBuild(p, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p, p2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);

		p.validate();

		return p;
	}

	private JPanel createNodeLayoutPatternOptionsPanel() {
		GridBagLayout gridbag = new GridBagLayout();
//		GridBagConstraints c = new GridBagConstraints();
		JPanel p = new JPanel();
		p.setLayout(gridbag);

		JPanel p1 = new JPanel(new GridLayout(1, 0));
		JLabel l = new JLabel(" Selected node type ");
		p1.add(l);
		this.nodeTypeCB = createNodeTypeComboBox();
		p1.add(this.nodeTypeCB);

		JPanel p2 = new JPanel(new GridLayout(0, 1));
		this.fixedNodePositionCB = new JCheckBox(" fix node position ", null, false);
		this.fixedNodePositionCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int indx = GraphLayouterOptionGUI.this.nodeTypeCB.getSelectedIndex();
				if (indx == -1)
					return;
				if (((JCheckBox) e.getSource()).isSelected()) {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.nodeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("frozen_node", "node",
								GraphLayouterOptionGUI.this.nodeTypes.get(indx).getBasisType(), true);
					else if (GraphLayouterOptionGUI.this.nodeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("frozen_node", "node",
								GraphLayouterOptionGUI.this.nodeTypes.get(indx).getBasisType(), true);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("true", indx, 4);
				} else {
					if (GraphLayouterOptionGUI.this.gragra != null && GraphLayouterOptionGUI.this.nodeTypes != null)
						GraphLayouterOptionGUI.this.gragra.createLayoutPattern("frozen_node", "node",
								GraphLayouterOptionGUI.this.nodeTypes.get(indx).getBasisType(), false);
					else if (GraphLayouterOptionGUI.this.nodeTypes != null)
						GraphLayouterOptionGUI.this.layouter.createLayoutPattern("frozen_node", "node",
								GraphLayouterOptionGUI.this.nodeTypes.get(indx).getBasisType(), false);
					if (GraphLayouterOptionGUI.this.patternTable != null)
						GraphLayouterOptionGUI.this.patternTable.getModel().setValueAt("", indx, 4);
				}
			}
		});
		p2.add(this.fixedNodePositionCB);

		constrainBuild(p, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p, p2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);

		p.validate();

		return p;
	}

	private JComboBox createNodeTypeComboBox() {
		if (this.nodeTypeCB == null) {
			this.nodeTypeCB = new JComboBox();
			this.nodeTypeCB.setRenderer(new MyCellRenderer(true));
			this.nodeTypeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (((JComboBox) e.getSource()).getSelectedItem() instanceof JLabel) {
						int indx = ((JComboBox) e.getSource())
								.getSelectedIndex();
						if (indx < GraphLayouterOptionGUI.this.nodeTypes.size()) {
							JLabel l = (JLabel) ((JComboBox) e.getSource())
									.getSelectedItem();
							if (l != null) {
								GraphLayouterOptionGUI.this.nodeTypeCB.getModel().setSelectedItem(l);
								updateLayoutPatternOfType(GraphLayouterOptionGUI.this.nodeTypes.get(indx),
										indx);
							}
						}
					}
				}
			});

			this.nodeTypeCB.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
//					if (e.getStateChange() == ItemEvent.SELECTED) {
//					}
				}
			});

		}
		return this.nodeTypeCB;
	}

	private JComboBox updateNodeTypeComboBox(Vector<EdType> nodetypes) {
		if (nodetypes == null) {
			if (this.patternTable != null) {
				if (this.gragra == null)
					this.layouter.clearLayoutPatterns();
				this.patternTable.removeAll();
				this.patternTable = null;
			}
			if (this.nodeTypeCB != null)
				this.nodeTypeCB.removeAllItems();
			if (this.fixedNodePositionCB != null)
				this.fixedNodePositionCB.setSelected(false);
		}

		this.nodeTypes = nodetypes;
		if (this.nodeTypes == null)
			return this.nodeTypeCB;

		if (this.nodeTypeCB != null)
			this.nodeTypeCB.removeAllItems();

		if (this.gragra == null)
			this.layouter.clearLayoutPatterns();

		if (this.nodeTypes != null) {
			// System.out.println("updateNodeTypeCB: by node types :
			// "+nodeTypes.hashCode());
			for (int i = 0; i < this.nodeTypes.size(); i++) {
				EdType t = this.nodeTypes.get(i);
				JLabel l = new JLabel(t.getName());
				// if(l.getText().equals("")) l.setText("[UNNAMED]");
				l.setIcon(getNodeTypeIcon(t));
				l.setForeground(t.getColor());
				this.nodeTypeCB.addItem(l);
				// System.out.println(nodeTypeCB.getItemCount());
			}
		} else
			this.showPattern.setEnabled(false);
		return this.nodeTypeCB;
	}

	public void updateNodeTypeComboBox(EdType t, int index, int msg) {
		if (msg == TypeEvent.MODIFIED_CREATED) {
			// System.out.println("updateNodeTypeComboBox:: MODIFIED_CREATED:
			// "+t.getName()+" "+index);
			JLabel mi = new JLabel(t.getName());
			// if(t.getName().equals("")) mi.setText("[UNNAMED]");
			mi.setIcon(getNodeTypeIcon(t));
			mi.setForeground(t.getColor());
			Vector<Object> tablerow = new Vector<Object>(5);
			tablerow.add(mi);
			tablerow.add("");
			tablerow.add("");
			tablerow.add("");
			tablerow.add("");
			if (index >= this.nodeTypeCB.getItemCount()) {
				((DefaultComboBoxModel) this.nodeTypeCB.getModel()).addElement(mi);
				// System.out.println("row added");
			} else {
				((DefaultComboBoxModel) this.nodeTypeCB.getModel()).insertElementAt(
						mi, index);
				// System.out.println("row inserted");
			}
			((DefaultTableModel) this.patternTable.getModel()).insertRow(index,
					tablerow);
		} else if (msg == TypeEvent.MODIFIED_DELETED) {
			// System.out.println("updateNodeTypeComboBox:: MODIFIED_DELETED:
			// "+t.getName()+" "+index);
			((DefaultTableModel) this.patternTable.getModel()).removeRow(index);
			this.layouter.removeLayoutPattern(t.getBasisType());
			((DefaultComboBoxModel) this.nodeTypeCB.getModel())
					.removeElementAt(index);
			// layouter.removeLayoutPattern(t.getBasisType());
			// ((DefaultTableModel) patternTable.getModel()).removeRow(index);
			// System.out.println("row deleted");
		} else if (msg == TypeEvent.MODIFIED_CHANGED) {
			// System.out.println("updateNodeTypeComboBox:: MODIFIED_CHANGED:
			// "+t.getName()+" "+index);
			JLabel mi = (JLabel) this.nodeTypeCB.getItemAt(index);
			if (!mi.getText().equals(t.getName()))
				this.layouter.removeLayoutPattern(t.getBasisType());

			mi.setText(t.getName());
			mi.setForeground(t.getColor());
			mi.setIcon(getNodeTypeIcon(t));
			if (index ==this. nodeTypeCB.getSelectedIndex()) {
				if (index < this.nodeTypeCB.getItemCount() - 1)
					this.nodeTypeCB.setSelectedIndex(this.nodeTypeCB.getItemCount() - 1);
				else
					this.nodeTypeCB.setSelectedIndex(0);
			}
			this.nodeTypeCB.setSelectedItem(mi);

			this.patternTable.setValueAt(mi, index, 0);
			this.patternTable.setValueAt("", index, 1);
			this.patternTable.setValueAt("", index, 2);
			this.patternTable.setValueAt("", index, 3);
			this.patternTable.setValueAt("", index, 4);

			updateLayoutPatternOfType(t, index);
			// System.out.println("row changed");
		}
	}

	void updateGroup(Vector<JRadioButtonMenuItem> grp, JRadioButtonMenuItem b) {
		if (b.isSelected()) {
			for (int i = 0; i < grp.size(); i++) {
				JRadioButtonMenuItem bi = grp.get(i);
				if (bi != b)
					bi.setSelected(false);
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 500);
	}

	/**
	 * Returns the text for the tab title.
	 * 
	 * @return <I>Graph Layouter</I> is returned.
	 */
	public String getTabTitle() {
		return "Layouter";
	}

	/**
	 * Returns the text for the tab tip.
	 * 
	 * @return <I>Graph Layouter Option</I> is returned.
	 */
	public String getTabTip() {
		return "Graph Layouter's Options";
	}

	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
	
		updateNodeTypeComboBox(null);
		updateEdgeTypeComboBox(null);
		if (this.tableFrame != null)
			this.tableFrame.setVisible(false);
		if (this.gragra != null) {
			this.layouter.setLayoutPatterns(this.gragra.getLayoutPatterns());
			updateNodeTypeComboBox(this.gragra.getTypeSet().getNodeTypes());
			updateEdgeTypeComboBox(this.gragra.getTypeSet().getArcTypes());
			updateLayoutPatternTable();

			try {
				int indx = this.nodeTypeCB.getSelectedIndex();
				updateLayoutPatternOfType(this.nodeTypes.get(indx), indx);
				indx = this.edgeTypeCB.getSelectedIndex();
				updateLayoutPatternOfType(this.edgeTypes.get(indx), indx
						+ this.nodeTypes.size());
			} catch (ArrayIndexOutOfBoundsException ex) {
			}
		}
	}

	private void updateLayoutPatternTable() {
		this.patternTable = createLayoutPatternTable();
		createEdgePatternTableEntries(this.patternTable);
		createNodePatternTableEntries(this.patternTable);
		createPatternTableFrame(this.patternTable);
	}

	/**
	 * Updates options setting of the graph layouter.
	 */
	public void update() {
		updateGraphLayouter();
	}

	public void addActionListener(ActionListener l) {
	}

	/**
	 * Updates options setting of the graph layouter.
	 */
	public void updateGraphLayouter() {
		this.layouter.setEnabled(this.enableLayouterCB.isSelected());
		this.layouter.setJpgOutput(this.saveCB.isSelected());
		this.layouter.setWriteMetricValues(this.metricsCB.isSelected());
		// this.layouter.setDoCenter(centerCB.isSelected());
		// this.layouter.setNodeIntersectionIterationCount(countNodeIntersectionIters);
		// this.layouter.setNodeIntersectionIterationCount(countEdgeIntersectionIters);
		this.layouter.setIterationCount(this.iterCount);
		this.layouter.setUsePattern(this.usePatternCB.isSelected());
		this.layouter.setCentre(false);
	}

	public void actionPerformed(ActionEvent e) {
	}

	/**
	 * If the another tab is selected the selected tab will be updated.
	 * 
	 * @param e
	 *            The event from the tabbeed pane.
	 */
	public void stateChanged(ChangeEvent e) {
		// JTabbedPane source = (JTabbedPane)e.getSource();
	}

	void enableButtons(boolean b) {
		this.saveCB.setEnabled(b);
		this.metricsCB.setEnabled(b);
		this.usePatternCB.setEnabled(b);

		// iterLayoutTF.setEnabled(b);
		// temperatureTF.setEnabled(b);
		// generalEdgeLengthTF.setEnabled(b);

		enablePattern(b,
				(this.usePatternCB.isEnabled() && this.usePatternCB.isSelected()));
	}

	void enablePattern(boolean layouterSelected, boolean usePatternSelected) {
		this.tabbedPane.setEnabled(layouterSelected);

		this.generalLayoutPatternP.setEnabled(layouterSelected);
		this.iterLayoutTF.setEnabled(layouterSelected);
		this.temperatureTF.setEnabled(layouterSelected);
		this.generalEdgeLengthTF.setEnabled(layouterSelected);
		this.nodeClusterSpanTF.setEnabled(layouterSelected);

		this.nodeLayoutPatternP.setEnabled(usePatternSelected);
		this.nodeTypeCB.setEnabled(usePatternSelected);
		this.fixedNodePositionCB.setEnabled(usePatternSelected);

		this.edgeLayoutPatternP.setEnabled(usePatternSelected);
		this.edgeTypeCB.setEnabled(usePatternSelected);
		this.x_leftCB.setEnabled(usePatternSelected);
		this.x_rightCB.setEnabled(usePatternSelected);
		this.x_equalCB.setEnabled(usePatternSelected);
		this.y_aboveCB.setEnabled(usePatternSelected);
		this.y_underCB.setEnabled(usePatternSelected);
		this.y_equalCB.setEnabled(usePatternSelected);
		this.edgeLengthTF.setEnabled(usePatternSelected);

		this.showPattern.setEnabled(usePatternSelected);
	}

	private JComboBox createEdgeTypeComboBox() {
		if (this.edgeTypeCB == null) {
			this.edgeTypeCB = new JComboBox();
			this.edgeTypeCB.setRenderer(new MyCellRenderer(true));
			this.edgeTypeCB.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// System.out.println(((JComboBox)
					// e.getSource()).getSelectedItem());
					if (((JComboBox) e.getSource()).getSelectedItem() instanceof JLabel) {
						int indx = ((JComboBox) e.getSource())
								.getSelectedIndex();
						if (indx < GraphLayouterOptionGUI.this.edgeTypes.size()) {
							JLabel l = (JLabel) ((JComboBox) e.getSource())
									.getSelectedItem();
							if (l != null) {
								// System.out.println("selected type:
								// "+l.getText());
								updateLayoutPatternOfType(GraphLayouterOptionGUI.this.edgeTypes.get(indx),
										indx + GraphLayouterOptionGUI.this.nodeTypes.size());
							}
						}
					}
				}
			});

			this.edgeTypeCB.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
					}
				}
			});

		}
		return this.edgeTypeCB;
	}

	private JComboBox updateEdgeTypeComboBox(Vector<EdType> arcTypes) {
		this.edgeTypes = arcTypes;
		if (this.edgeTypes == null) {
			if (this.edgeTypeCB != null)
				this.edgeTypeCB.removeAllItems();
			if (this.x_leftCB != null)
				this.x_leftCB.setSelected(false);
			if (this.x_rightCB != null)
				this.x_rightCB.setSelected(false);
			if (this.x_equalCB != null)
				this.x_equalCB.setSelected(false);
			if (this.y_aboveCB != null)
				this.y_aboveCB.setSelected(false);
			if (this.y_underCB != null)
				this.y_underCB.setSelected(false);
			if (this.y_equalCB != null)
				this.y_equalCB.setSelected(false);
			if (this.edgeLengthTF != null)
				this.edgeLengthTF.setText("0");
		}

		if (this.edgeTypeCB != null)
			this.edgeTypeCB.removeAllItems();
		if (this.gragra == null)
			this.layouter.clearLayoutPatterns();

		if (this.edgeTypes != null) {
			// System.out.println("updateEdgeTypeCB: by edge types :
			// "+edgeTypes.hashCode());
			for (int i = 0; i < this.edgeTypes.size(); i++) {
				EdType t = this.edgeTypes.get(i);
				JLabel l = new JLabel(t.getName());
				// if(l.getText().equals("")) l.setText("[UNNAMED]");
				l.setIcon(getArcTypeIcon(t));
				l.setForeground(t.getColor());
				this.edgeTypeCB.addItem(l);
				// System.out.println(edgeTypeCB.getItemCount());
			}
		}
		return this.edgeTypeCB;
	}

	public void updateEdgeTypeComboBox(EdType t, int index, int msg) {
		if (msg == TypeEvent.MODIFIED_CREATED) {
			// System.out.println("updateEdgeTypeComboBox:: MODIFIED_CREATED:
			// "+t.getName()+" "+index);
			JLabel mi = new JLabel(t.getName());
			// if(t.getName().equals("")) mi.setText("[UNNAMED]");
			mi.setIcon(getArcTypeIcon(t));
			mi.setForeground(t.getColor());
			Vector<Object> tablerow = new Vector<Object>(5);
			tablerow.add(mi);
			tablerow.add("");
			tablerow.add("");
			tablerow.add("");
			tablerow.add("");
			if (index >= this.edgeTypeCB.getItemCount()) {
				((DefaultComboBoxModel) this.edgeTypeCB.getModel()).addElement(mi);
			} else {
				((DefaultComboBoxModel) this.edgeTypeCB.getModel()).insertElementAt(
						mi, index);
			}
			((DefaultTableModel) this.patternTable.getModel()).insertRow(index,
					tablerow);
		} else if (msg == TypeEvent.MODIFIED_DELETED) {
			this.layouter.removeLayoutPattern(t.getBasisType());
			((DefaultTableModel) this.patternTable.getModel()).removeRow(index
					+ this.nodeTypes.size());
			((DefaultComboBoxModel) this.edgeTypeCB.getModel())
					.removeElementAt(index);
		} else if (msg == TypeEvent.MODIFIED_CHANGED) {
			JLabel mi = (JLabel) this.edgeTypeCB.getItemAt(index);
			if (!mi.getText().equals(t.getName()))
				this.layouter.removeLayoutPattern(t.getBasisType());

			mi.setText(t.getName());
			mi.setForeground(t.getColor());
			mi.setIcon(getArcTypeIcon(t));
			if (index == this.edgeTypeCB.getSelectedIndex()) {
				if (index < this.edgeTypeCB.getItemCount() - 1)
					this.edgeTypeCB.setSelectedIndex(this.edgeTypeCB.getItemCount() - 1);
				else
					this.edgeTypeCB.setSelectedIndex(0);
			}
			this.edgeTypeCB.setSelectedItem(mi);

			int indx = index + this.nodeTypes.size();
			this.patternTable.setValueAt(mi, indx, 0);
			this.patternTable.setValueAt("", indx, 1);
			this.patternTable.setValueAt("", indx, 2);
			this.patternTable.setValueAt("", indx, 3);
			this.patternTable.setValueAt("", indx, 4);

			updateLayoutPatternOfType(t, indx);
		}
	}

	private JTable createLayoutPatternTable() {
		TableModel dataModel = new DefaultTableModel(new String[] { "Type",
				"X-Axis", "Y-Axis", "Length", "FixNodePos" }, 5) {
			public int getColumnCount() {
				return 5;
			}

			public int getRowCount() {
				int nn = 0;
				if (GraphLayouterOptionGUI.this.nodeTypes != null)
					nn = GraphLayouterOptionGUI.this.nodeTypes.size();
				if (GraphLayouterOptionGUI.this.edgeTypes != null)
					nn = nn + GraphLayouterOptionGUI.this.edgeTypes.size();
				return nn;
			}

			public Object getValueAt(int row, int col) {
				if (row < super.getRowCount() && col < super.getColumnCount()) {
					return super.getValueAt(row, col);
				} 
				return null;
			}
		};

		JTable table = new JTable(dataModel);
		table.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultRenderer(table.getColumnClass(0),
				new MyTableCellRenderer(false));

		if (this.scrollpanePatternTable == null)
			this.scrollpanePatternTable = new JScrollPane(table);
		else
			this.scrollpanePatternTable.setViewportView(table);

		return table;
	}

	private void createEdgePatternTableEntries(JTable table) {
		for (int row = 0; row < this.edgeTypes.size(); row++) {
			EdType type = this.edgeTypes.get(row);

			JLabel typel = new JLabel(type.getName());
			typel.setIcon(getArcTypeIcon(type));
			typel.setForeground(type.getColor());

			int r = row + this.nodeTypes.size();
			table.getModel().setValueAt(typel, r, 0);
			table.getModel().setValueAt("", r, 1);
			table.getModel().setValueAt("", r, 2);
			table.getModel().setValueAt("", r, 3);
			table.getModel().setValueAt("", r, 4);

			// now set values of layout patterns
			if (this.gragra != null) {
				Vector<LayoutPattern> v = this.gragra.getLayoutPatternsForType(type
						.getBasisType());
				for (int j = 0; j < v.size(); j++) {
					LayoutPattern lp = v.get(j);
					if (lp.isEdgePattern() && lp.isXOffset()) {
						if (lp.getOffset() == 1)
							table.getModel().setValueAt("TarRightSrc", r, 1);
						else if (lp.getOffset() == -1)
							table.getModel().setValueAt("TarLeftSrc", r, 1);
						else if (lp.getOffset() == 0)
							table.getModel().setValueAt("TarEqualSrc", r, 1);
					} else if (lp.isEdgePattern() && lp.isYOffset()) {
						if (lp.getOffset() == 1)
							table.getModel().setValueAt("TarUnderSrc", r, 2);
						else if (lp.getOffset() == -1)
							table.getModel().setValueAt("TarAboveSrc", r, 2);
						else if (lp.getOffset() == 0)
							table.getModel().setValueAt("TarEqualSrc", r, 2);
					} else if (lp.isEdgePattern() && lp.isLengthPattern()) {
						table.getModel().setValueAt(
								String.valueOf(lp.getLength()), r, 3);
					}
				}
			}
		}

	}

	private void createNodePatternTableEntries(JTable table) {
		for (int row = 0; row < this.nodeTypes.size(); row++) {
			EdType type = this.nodeTypes.get(row);

			JLabel typel = new JLabel(type.getName());
			typel.setIcon(getNodeTypeIcon(type));
			typel.setForeground(type.getColor());

			table.getModel().setValueAt(typel, row, 0);
			table.getModel().setValueAt("", row, 1);
			table.getModel().setValueAt("", row, 2);
			table.getModel().setValueAt("", row, 3);
			table.getModel().setValueAt("", row, 4);

			// now set values of layout patterns
			if (this.gragra != null) {
				Vector<LayoutPattern> v = this.gragra.getLayoutPatternsForType(type
						.getBasisType());
				for (int j = 0; j < v.size(); j++) {
					LayoutPattern lp = v.get(j);
					if (lp.isNodePattern()
							&& lp.getName().equals("frozen_node")) {
						table.getModel().setValueAt("true", row, 4);
					}
				}
			}
		}
	}

	private void createPatternTableFrame(JTable table) {
		this.scrollpanePatternTable = new JScrollPane(table);
		table.getSelectionModel().addListSelectionListener(this);

		JButton closeButton = new JButton();
		closeButton.setText("Close");
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GraphLayouterOptionGUI.this.tableFrame.setVisible(false);
			}
		});

		// create a dialog to show the pattern table
		this.tableFrame = new JDialog();
		this.tableFrame.setTitle(" Node & Edge Type Layout Pattern ");
		this.tableFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				setVisible(false);
			}
		});

		this.tableFrame.setModal(false);
		this.tableFrame.getContentPane().setLayout(new BorderLayout());
		this.tableFrame.getContentPane().add(this.scrollpanePatternTable,
				BorderLayout.CENTER);
		this.tableFrame.getContentPane().add(closeButton, BorderLayout.SOUTH);
		// tableFrame.setLocation(this.getLocation().x+400,
		// this.getLocation().y+400);
		// System.out.println("Cell height:
		// "+table.getCellRect(0,0,true).height);
		int fheight = 100;
		if (table.getRowCount() > 0)
			fheight = table.getCellRect(0, 0, true).height
					* (table.getRowCount() + 6);
		this.tableFrame.setSize(new Dimension(400, fheight));
		this.tableFrame.validate();
	}

	public void valueChanged(ListSelectionEvent e) {
		// System.out.println("valueChanged: "+ e.getFirstIndex());
		int indx = e.getFirstIndex();
		if (indx < this.edgeTypeCB.getItemCount())
			this.edgeTypeCB.setSelectedIndex(indx);
	}

	private Icon getArcTypeIcon(EdType et) {
		Icon icon = null;
		switch (et.shape) {
		case EditorConstants.SOLID:
			icon = new ColorSolidLineIcon(et.color);
			break;
		case EditorConstants.DASH:
			icon = new ColorDashLineIcon(et.color);
			break;
		case EditorConstants.DOT:
			icon = new ColorDotLineIcon(et.color);
			break;
		default:
			break;
		}
		return icon;
	}

	public Icon getNodeTypeIcon(EdType et) {
		Icon icon = null;
		switch (et.shape) {
		case EditorConstants.RECT:
			icon = new RectShapeIcon(et.color);
			break;
		case EditorConstants.ROUNDRECT:
			icon = new RoundRectShapeIcon(et.color);
			break;
		case EditorConstants.CIRCLE:
			icon = new CircleShapeIcon(et.color);
			break;
		case EditorConstants.OVAL:
			icon = new OvalShapeIcon(et.color);
			break;
		default:
			break;
		}
		return icon;
	}

	void updateLayoutPatternOfType(EdType type, int indx) {
		if (this.patternTable == null)
			return;
		boolean isNode = this.nodeTypes.contains(type) ? true : false;
		if (this.patternTable.getModel().getValueAt(indx, 1) == null)
			return;
		// System.out.println("updateLayoutPatternOfType:: "+indx+":
		// "+type.getTypeName() +" 1:
		// "+(String)patternTable.getModel().getValueAt(indx, 1)+ " 2:
		// "+(String)patternTable.getModel().getValueAt(indx, 2)+" 3:
		// "+(String)patternTable.getModel().getValueAt(indx, 3));
		// System.out.println( patternTable.getModel().getValueAt(indx, 0));
		if (type.getTypeName().equals(
				((JLabel) this.patternTable.getModel().getValueAt(indx, 0))
						.getText())) {
			if (!isNode) {
				if (((String) this.patternTable.getModel().getValueAt(indx, 1))
						.equals("TarLeftSrc")) {
					this.x_leftCB.setSelected(true);
					this.x_rightCB.setSelected(false);
					this.x_equalCB.setSelected(false);
				} else if (((String) this.patternTable.getModel()
						.getValueAt(indx, 1)).equals("TarRightSrc")) {
					this.x_leftCB.setSelected(false);
					this.x_rightCB.setSelected(true);
					this.x_equalCB.setSelected(false);
				} else if (((String) this.patternTable.getModel()
						.getValueAt(indx, 1)).equals("TarEqualSrc")) {
					this.x_leftCB.setSelected(false);
					this.x_rightCB.setSelected(false);
					this.x_equalCB.setSelected(true);
				} else {
					this.x_leftCB.setSelected(false);
					this.x_rightCB.setSelected(false);
					this.x_equalCB.setSelected(false);
				}
				if (((String) this.patternTable.getModel().getValueAt(indx, 2))
						.equals("TarAboveSrc")) {
					this.y_aboveCB.setSelected(true);
					this.y_underCB.setSelected(false);
					this.y_equalCB.setSelected(false);
				} else if (((String) this.patternTable.getModel()
						.getValueAt(indx, 2)).equals("TarUnderSrc")) {
					this.y_aboveCB.setSelected(false);
					this.y_underCB.setSelected(true);
					this.y_equalCB.setSelected(false);
				} else if (((String) this.patternTable.getModel()
						.getValueAt(indx, 2)).equals("TarEqualSrc")) {
					this.y_aboveCB.setSelected(false);
					this.y_underCB.setSelected(false);
					this.y_equalCB.setSelected(true);
				} else {
					this.y_aboveCB.setSelected(false);
					this.y_underCB.setSelected(false);
					this.y_equalCB.setSelected(false);
				}

				if (!((String) this.patternTable.getModel().getValueAt(indx, 3))
						.equals(""))
					this.edgeLengthTF.setText((String) this.patternTable.getModel()
							.getValueAt(indx, 3));
				else
					this.edgeLengthTF.setText("0");
			} else {
				if (((String) this.patternTable.getModel().getValueAt(indx, 4))
						.equals("true")) {
					if (!this.fixedNodePositionCB.isSelected())
						this.fixedNodePositionCB.setSelected(true);
				} else if (this.fixedNodePositionCB.isSelected())
					this.fixedNodePositionCB.setSelected(false);
			}
		}
	}

	/*
	private void createLayoutPattern(EdType type) {
		// System.out.println("createLayoutPattern for: "+typename);
		if (x_leftCB.isSelected()) {
			layouter.createLayoutPattern("hor_tree", "edge", type
					.getBasisType(), 'x', -1);
		} else if (x_rightCB.isSelected()) {
			layouter.createLayoutPattern("hor_tree", "edge", type
					.getBasisType(), 'x', 1);
		} else if (x_equalCB.isSelected()) {
			layouter.createLayoutPattern("x_tree", "edge", type.getBasisType(),
					'x', 0);
		}

		if (y_aboveCB.isSelected()) {
			layouter.createLayoutPattern("ver_tree", "edge", type
					.getBasisType(), 'y', -1);
		} else if (y_underCB.isSelected()) {
			layouter.createLayoutPattern("ver_tree", "edge", type
					.getBasisType(), 'y', 1);
		} else if (y_equalCB.isSelected()) {
			layouter.createLayoutPattern("y_tree", "edge", type.getBasisType(),
					'y', 0);
		}

		try {
			Integer nb = Integer.valueOf(edgeLengthTF.getText());
			if (nb.intValue() > 0) {
				layouter.createLayoutPattern("edge_length", "edge", type
						.getBasisType(), nb.intValue());
			}
		} catch (NumberFormatException ex) {
		}
	}
*/
	
	// constrainBuild() method
	private void constrainBuild(Container container, Component component,
			int grid_x, int grid_y, int grid_width, int grid_height, int fill,
			int anchor, double weight_x, double weight_y, int top, int left,
			int bottom, int right) {
		// an example
		// constrainBuild(p, p4, 0, 4, 1, 1, GridBagConstraints.BOTH,
		// GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);

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

	class MyCellRenderer extends JLabel implements ListCellRenderer {

		boolean allowSelect;

		public MyCellRenderer(boolean allowSelect) {
			this.allowSelect = allowSelect;
			setOpaque(true);
		}

		public Component getListCellRendererComponent(JList listbox,
				Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			if (value instanceof JLabel) {
				JLabel l = (JLabel) value;
				// System.out.println(l.getForeground());
				setForeground(l.getForeground());
				setIcon(l.getIcon());
				setText(l.getText());
			} else if (value instanceof String) {
				setText((String) value);
			} else {
				setText("");
			}
			if (this.allowSelect && isSelected)
				setBackground(Color.lightGray);
			else
				setBackground(Color.white);
			setOpaque(true);
			return this;
		}
	}

	class MyTableCellRenderer extends JLabel implements TableCellRenderer {

		boolean allowSelect;

		public MyTableCellRenderer(boolean allowSelect) {
			this.allowSelect = allowSelect;
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column)

		{
			setOpaque(true);
			if (this.allowSelect && isSelected)
				setBackground(Color.lightGray);
			else
				setBackground(Color.white);

			if (value instanceof JLabel) {
				JLabel l = (JLabel) value;
				if (column == 0) {
					setIcon(l.getIcon());
					setForeground(l.getForeground());
					setText(l.getText());
					return this; // l;
					// System.out.println(l.getForeground());
				} 
				return new JLabel(l.getText());
				
			} else if (value instanceof String)
				return new JLabel((String) value);
			else
				return new JLabel("");
		}
	}

}

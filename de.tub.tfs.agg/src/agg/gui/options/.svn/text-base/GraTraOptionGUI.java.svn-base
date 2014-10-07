// $Id: GraTraOptionGUI.java,v 1.16 2010/10/21 11:19:07 olga Exp $

package agg.gui.options;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import agg.xt_basis.GraTraOptions;
import agg.editor.impl.EdRule;
import agg.gui.event.TransformEvent;
import agg.gui.trafo.GraGraTransform;
import agg.gui.treeview.dialog.RuleSequenceDialog;
import agg.util.Pair;

@SuppressWarnings("serial")
public class GraTraOptionGUI extends AbstractOptionGUI implements ActionListener {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GraTraOptionGUI(final JFrame frame, GraGraTransform trans) {
		super();
		
		this.applFrame = frame;
		
		this.transform = trans;

		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);

		// transformation kind
		JPanel transformKindPanel = new JPanel();
		transformKindPanel.setLayout(new GridLayout(0, 1));
		transformKindPanel.setBorder(new TitledBorder(
				"  Graph transformation  "));

		ButtonGroup groupTrans = new ButtonGroup();
		// transformation non-deterministically : default
		this.nondeterministicallyRB = new JRadioButton(
				"non-deterministically  ( NT ) ( by default )");
		groupTrans.add(this.nondeterministicallyRB);
		transformKindPanel.add(this.nondeterministicallyRB);
		this.nondeterministicallyRB.setActionCommand(GraTraOptions.NONDETERMINISTICALLY);
		this.nondeterministically = true;
		this.nondeterministicallyRB.setSelected(true);
		this.nondeterministicallyRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.nondeterministicallyRB.isSelected()) {
					if (GraTraOptionGUI.this.rsgui.isVisible())
						GraTraOptionGUI.this.rsgui.setVisible(false);
					
					GraTraOptionGUI.this.nondeterministically = true;
					GraTraOptionGUI.this.rulePriority = false;
					GraTraOptionGUI.this.layered = false;
					GraTraOptionGUI.this.ruleSequence = false;
					GraTraOptionGUI.this.eachRuleToApplyCB.setEnabled(GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.LAYERED, GraTraOptionGUI.this.layered);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.PRIORITY, GraTraOptionGUI.this.rulePriority);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.RULE_SEQUENCE, GraTraOptionGUI.this.ruleSequence);
				} else
					GraTraOptionGUI.this.nondeterministically = false;
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.NONDETERMINISTICALLY,
						GraTraOptionGUI.this.nondeterministically);
			}
		});
		// transformation by layers
		this.layeredRB = new JRadioButton("by rule layers  ( layered )   ( LT )");
		groupTrans.add(this.layeredRB);
		transformKindPanel.add(this.layeredRB);
		this.layeredRB.setActionCommand(GraTraOptions.LAYERED);
		this.layered = false;
		this.layeredRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.layeredRB.isSelected()) {
					if (GraTraOptionGUI.this.rsgui.isVisible())
						GraTraOptionGUI.this.rsgui.setVisible(false);
					
					GraTraOptionGUI.this.layered = true;
					GraTraOptionGUI.this.rulePriority = false;
					GraTraOptionGUI.this.nondeterministically = false;
					GraTraOptionGUI.this.ruleSequence = false;
					GraTraOptionGUI.this.eachRuleToApplyCB.setEnabled(GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.layers.setEnabled(true);					
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.PRIORITY, GraTraOptionGUI.this.rulePriority);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.RULE_SEQUENCE, GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.NONDETERMINISTICALLY,
							GraTraOptionGUI.this.nondeterministically);
				} else {
					GraTraOptionGUI.this.layered = false;
					GraTraOptionGUI.this.layers.setEnabled(false);
				}
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.LAYERED, GraTraOptionGUI.this.layered);
				
				GraTraOptionGUI.this.showLayerCB.setEnabled(GraTraOptionGUI.this.layered);
				GraTraOptionGUI.this.layeredLoopCB.setEnabled(GraTraOptionGUI.this.layered);
				GraTraOptionGUI.this.stopLayerAndWaitCB.setEnabled(GraTraOptionGUI.this.layered);
				GraTraOptionGUI.this.breakLayerLabel.setEnabled(GraTraOptionGUI.this.layered);
				GraTraOptionGUI.this.breakLayerRB.setEnabled(GraTraOptionGUI.this.layered);
				GraTraOptionGUI.this.breakAllLayerRB.setEnabled(GraTraOptionGUI.this.layered);
			}
		});
		// transformation by rule priority
		this.priorityRB = new JRadioButton("by rule priorities     ( PT )");
		groupTrans.add(this.priorityRB);
		transformKindPanel.add(this.priorityRB);
		this.priorityRB.setActionCommand(GraTraOptions.PRIORITY);
		this.rulePriority = false;
		this.priorityRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.priorityRB.isSelected()) {
					if (GraTraOptionGUI.this.rsgui.isVisible())
						GraTraOptionGUI.this.rsgui.setVisible(false);
					
					GraTraOptionGUI.this.rulePriority = true;
					GraTraOptionGUI.this.nondeterministically = false;
					GraTraOptionGUI.this.layered = false;
					GraTraOptionGUI.this.ruleSequence = false;
					GraTraOptionGUI.this.eachRuleToApplyCB.setEnabled(GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.LAYERED, GraTraOptionGUI.this.layered);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.RULE_SEQUENCE, GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.NONDETERMINISTICALLY,
							GraTraOptionGUI.this.nondeterministically);
				} else
					GraTraOptionGUI.this.rulePriority = false;
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.PRIORITY, GraTraOptionGUI.this.rulePriority);
			}
		});
		// transformation by rule sequence
		this.ruleSequenceRB = new JRadioButton("by rule sequences  ( ST )");
		groupTrans.add(this.ruleSequenceRB);
		transformKindPanel.add(this.ruleSequenceRB);
		this.ruleSequenceRB.setActionCommand(GraTraOptions.RULE_SEQUENCE);
		this.ruleSequence = false;
		this.ruleSequenceRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.ruleSequenceRB.isSelected()) {
					GraTraOptionGUI.this.ruleSequence = true;
					GraTraOptionGUI.this.eachRuleToApplyCB.setEnabled(GraTraOptionGUI.this.ruleSequence);
					GraTraOptionGUI.this.layered = false;
					GraTraOptionGUI.this.rulePriority = false;
					GraTraOptionGUI.this.nondeterministically = false;
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.PRIORITY, GraTraOptionGUI.this.rulePriority);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.LAYERED, GraTraOptionGUI.this.layered);
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.NONDETERMINISTICALLY,
							GraTraOptionGUI.this.nondeterministically);
				} else
					GraTraOptionGUI.this.ruleSequence = false;
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.RULE_SEQUENCE, GraTraOptionGUI.this.ruleSequence);				
			}
		});
		transformKindPanel.add(new JLabel("   ----------------------------------"));
		
		// transformation thread priority
		JPanel priorityPanel = new JPanel();
		priorityPanel.add(new JLabel("Priority of transformation engine    "), BorderLayout.WEST);
		this.priorities = new JComboBox(this.priorityList);
		this.priorities.setSelectedIndex(6);
		this.priorities.setToolTipText("Lower priority can be usful to improve synchronization of transformation engine and graph visualization.");
		this.priorities.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GraTraOptionGUI.this.priorities.getSelectedItem() != null) {
					String l = GraTraOptionGUI.this.priorities.getSelectedItem().toString();
					GraTraOptionGUI.this.transformThreadpriority = (Integer.valueOf(l)).intValue();	
					GraTraOptionGUI.this.transform.setTransformationThreadPriority(GraTraOptionGUI.this.transformThreadpriority);
				}			
			}
		});
		priorityPanel.add(this.priorities, BorderLayout.EAST);
		transformKindPanel.add(priorityPanel);
		
		// create panel for options of layered trafo
		JPanel layerPanel = new JPanel(new GridLayout(0, 1));
		layerPanel.setBorder(new TitledBorder(
				"  Options for layered rule application  "));

		this.showLayerCB = new JCheckBox("show layer before transform", null, false);
		layerPanel.add(this.showLayerCB);
		this.showLayerCB.setEnabled(this.layered);
		this.showLayerCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GraTraOptionGUI.this.showLayer = GraTraOptionGUI.this.showLayerCB.isSelected();
			}
		});

		final JPanel lp = new JPanel(new BorderLayout());
		this.layeredLoopCB = new JCheckBox("loop over layers        ", null, false);
		this.layeredLoopCB.setEnabled(this.layered);
		this.layeredLoopCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GraTraOptionGUI.this.layeredLoop = GraTraOptionGUI.this.layeredLoopCB.isSelected();
				GraTraOptionGUI.this.resetGraphCB.setEnabled(GraTraOptionGUI.this.layeredLoop);
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.LOOP_OVER_LAYER, GraTraOptionGUI.this.layeredLoop);
			}
		});
		this.resetGraphCB = new JCheckBox("reset graph before loop", null, false);
		this.resetGraphCB.setEnabled(this.layeredLoop);
		this.resetGraphCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GraTraOptionGUI.this.resetGraph = GraTraOptionGUI.this.resetGraphCB.isSelected();
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.RESET_GRAPH, GraTraOptionGUI.this.resetGraph);
			}
		});
		lp.add(this.layeredLoopCB, BorderLayout.WEST);
		lp.add(this.resetGraphCB, BorderLayout.CENTER);
		layerPanel.add(lp);
		
		final JPanel p = new JPanel(new BorderLayout());		
		this.stopLayerAndWaitCB = new JCheckBox("stop layer and wait    ", null,
				false);
		this.stopLayerAndWaitCB.setEnabled(this.layered);
		this.stopLayerAndWaitCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GraTraOptionGUI.this.stopLayerAndWait = GraTraOptionGUI.this.stopLayerAndWaitCB.isSelected();
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.STOP_LAYER_AND_WAIT,
						GraTraOptionGUI.this.stopLayerAndWait);
			}
		});
		this.layers = new JComboBox();
		this.layers.addItem("current");
		this.layers.setEnabled(this.layered);
		this.layers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (GraTraOptionGUI.this.layers.getSelectedItem() != null) {
					String l = GraTraOptionGUI.this.layers.getSelectedItem().toString();
					if (l.equals("current")) {
						l = "-1";
					}
					GraTraOptionGUI.this.layerToStop = (Integer.valueOf(l)).intValue();					
				}			
			}
		});
		p.add(this.stopLayerAndWaitCB, BorderLayout.WEST);
		p.add(this.layers, BorderLayout.CENTER);
		layerPanel.add(p);
		
		JLabel label = new JLabel("   ----------------------------------");
		layerPanel.add(label);
		this.breakLayerLabel = new JLabel(
				"   when Stop transformation button pressed");
		layerPanel.add(this.breakLayerLabel);
		this.breakLayerLabel.setEnabled(this.layered);
		ButtonGroup breakLayerGroup = new ButtonGroup();

		this.breakLayerRB = new JRadioButton("break transformation on current layer");
		breakLayerGroup.add(this.breakLayerRB);
		layerPanel.add(this.breakLayerRB);
		this.breakLayerRB.setEnabled(this.layered);
		this.breakLayerRB.setActionCommand("breakLayer");
		this.breakLayer = false;
		this.breakLayerRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.breakLayerRB.isSelected()) {
					GraTraOptionGUI.this.breakLayer = true;
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.BREAK_LAYER, GraTraOptionGUI.this.breakLayer);
					GraTraOptionGUI.this.breakAllLayer = false;
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.BREAK_ALL_LAYER, GraTraOptionGUI.this.breakAllLayer);
				}
			}
		});

		this.breakAllLayerRB = new JRadioButton("break layered transformation");
		breakLayerGroup.add(this.breakAllLayerRB);
		layerPanel.add(this.breakAllLayerRB);
		this.breakAllLayerRB.setEnabled(this.layered);
		this.breakAllLayerRB.setSelected(true);
		this.breakAllLayer = true;
		this.transform.updateGraTraOption("breakAllLayer", this.breakAllLayer);
		this.breakAllLayerRB.setActionCommand("breakAllLayer");
		this.breakAllLayerRB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (GraTraOptionGUI.this.breakAllLayerRB.isSelected()) {
					GraTraOptionGUI.this.breakAllLayer = true;
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.BREAK_ALL_LAYER, GraTraOptionGUI.this.breakAllLayer);
					GraTraOptionGUI.this.breakLayer = false;
					GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.BREAK_LAYER, GraTraOptionGUI.this.breakLayer);
				}
			}
		});

		// transformation by rule sequence
		this.rsgui = new RuleSequenceDialog(this.applFrame, new Point(200, 100));
		JPanel ruleSequencePanel = new JPanel(new GridLayout(0, 1));
		ruleSequencePanel.setBorder(new TitledBorder(
				"  Options for rule sequences  "));

		this.eachRuleToApplyCB = new JCheckBox("each rule applied at least ones", null, false);
		ruleSequencePanel.add(this.eachRuleToApplyCB);
		this.eachRuleToApplyCB.setEnabled(this.ruleSequence);
		this.eachRuleToApplyCB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				GraTraOptionGUI.this.eachRuleToApply = GraTraOptionGUI.this.eachRuleToApplyCB.isSelected();
				GraTraOptionGUI.this.transform.setEachRuleToApplyOfRuleSequence(GraTraOptionGUI.this.eachRuleToApply);
				GraTraOptionGUI.this.transform.updateGraTraOption(GraTraOptions.EACH_RULE_TO_APPLY, GraTraOptionGUI.this.eachRuleToApply);
			}
		});

		JPanel transformPanel = new JPanel(new GridBagLayout()); 
		
		constrainBuild(transformPanel, transformKindPanel, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 20, 5);
		constrainBuild(transformPanel, layerPanel, 0, 1, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 10, 5);
		constrainBuild(transformPanel, ruleSequencePanel, 0, 2, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				5, 5, 10, 5);

		constrainBuild(this, transformPanel, 0, 0, 1, 1,
				GridBagConstraints.BOTH, GridBagConstraints.CENTER, 1.0, 0.0,
				10, 5, 50, 5);
		validate();
	}

	public void updateLayerToStopIfNeeded() {		
		if (this.transform.getEditor().getGraGra() != null) {
			if (this.transform.getEditor().getGraGra().getBasisGraGra().hasRuleChangedEvailability()) {
				if (((this.layers.getItemCount()-1) 
						!= this.transform.getEditor().getGraGra().getBasisGraGra().getEnabledLayers().size())) {
					this.initLayers(this.transform.getEditor().getGraGra().getBasisGraGra().getEnabledLayers());	
				}
			} else if (this.layers.getItemCount() == 1) {
				this.initLayers(this.transform.getEditor().getGraGra().getBasisGraGra().getEnabledLayers());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public void initLayers(Vector<String> v) {
		this.layers.removeAllItems();
		this.layers.addItem("current");
		for (int i = 0; i < v.size(); i++) {
			this.layers.addItem(v.get(i));
		}
		this.layerToStop = -1; // current layer
	}
	
	public int getLayerToStop() {
		return this.layerToStop;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 450);
	}

	/**
	 * Returns the text for the tab title.
	 * 
	 * @return <I>Transformation</I> is returned.
	 */
	public String getTabTitle() {
		return "Transformation";
	}

	/**
	 * Returns the text for the tab tip.
	 * 
	 * @return <I>Transformation Option</I> is returned.
	 */
	public String getTabTip() {
		return "Transformation Options";
	}

	public void addActionListener(ActionListener l) {
		this.nondeterministicallyRB.addActionListener(l);
		this.layeredRB.addActionListener(l);
		this.priorityRB.addActionListener(l);
		this.ruleSequenceRB.addActionListener(l);
	}

	public void addActionListener(String option, ActionListener l) {
		if (option.equals(GraTraOptions.NONDETERMINISTICALLY))
			this.nondeterministicallyRB.addActionListener(l);
		else if (option.equals(GraTraOptions.LAYERED))
			this.layeredRB.addActionListener(l);
		else if (option.equals(GraTraOptions.PRIORITY))
			this.priorityRB.addActionListener(l);
		else if (option.equals(GraTraOptions.RULE_SEQUENCE))
			this.ruleSequenceRB.addActionListener(l);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source instanceof JCheckBox) { // CriticalPairOptionGUI.layered
			if (((JCheckBox) source).getText().equals("layered")) {
				if (((JCheckBox) source).isSelected())
					this.layeredRB.doClick();
				else
					this.nondeterministicallyRB.doClick();
			}
		}
	}

	/**
	 * Updates options setting.
	 */
	public void update() {
		update(this.transform.getGraTraOptionsList());
	}

	/**
	 * Updates GUI of transformation options .
	 */
	public void update(Vector<String> optionNames) {
		if (optionNames.isEmpty()) {
			return;
		}

		// use rule priority
		if (optionNames.contains(GraTraOptions.PRIORITY)) {
			if (!this.priorityRB.isSelected())
				this.priorityRB.doClick();
			this.rulePriority = true;
		}
		// use graph rule sequence
		else if (optionNames.contains(GraTraOptions.RULE_SEQUENCE)) {
			if (!this.ruleSequenceRB.isSelected())
				this.ruleSequenceRB.doClick();
			this.ruleSequence = true;
		}
		// set layered
		else if (optionNames.contains(GraTraOptions.LAYERED)) {
			if (!this.layeredRB.isSelected())
				this.layeredRB.doClick();
			this.layered = true;
			updateLayerToStopIfNeeded();	
			if (optionNames.contains(GraTraOptions.STOP_LAYER_AND_WAIT)) {
				this.stopLayerAndWaitCB.setSelected(true);
				this.stopLayerAndWait = true;
			} else {
				this.stopLayerAndWaitCB.setSelected(false);
				this.stopLayerAndWait = false;
			}

			if (optionNames.contains(GraTraOptions.LOOP_OVER_LAYER)) {
				this.layeredLoopCB.setSelected(true);
				this.layeredLoop = true;
			} else {
				this.layeredLoopCB.setSelected(false);
				this.layeredLoop = false;
			}
		} 
		else {
			if (!this.nondeterministicallyRB.isSelected())
				this.nondeterministicallyRB.doClick();
			this.nondeterministically = true;
		}

//		if (optionNames.contains(GraTraOptions.STOP_LAYER_AND_WAIT)) {
//			this.stopLayerAndWaitCB.setSelected(true);
//			this.stopLayerAndWait = true;
//		} else {
//			this.stopLayerAndWaitCB.setSelected(false);
//			this.stopLayerAndWait = false;
//		}
//
//		if (optionNames.contains(GraTraOptions.LOOP_OVER_LAYER)) {
//			this.layeredLoopCB.setSelected(true);
//			this.layeredLoop = true;
//		} else {
//			this.layeredLoopCB.setSelected(false);
//			this.layeredLoop = false;
//		}

		if (optionNames.contains(GraTraOptions.RESET_GRAPH)) {
			this.resetGraphCB.setSelected(true);
			this.resetGraph = true;
		} else {
			this.resetGraphCB.setSelected(false);
			this.resetGraph = false;
		}
		
		if (optionNames.contains(GraTraOptions.BREAK_LAYER)) {
			this.breakLayerRB.setSelected(true);
			this.breakLayer = true;
			this.breakAllLayerRB.setSelected(false);
			this.breakAllLayer = false;
		} else if (optionNames.contains(GraTraOptions.BREAK_ALL_LAYER)) {
			this.breakAllLayerRB.setSelected(true);
			this.breakAllLayer = true;
			this.breakLayerRB.setSelected(false);
			this.breakLayer = false;
		} else {
			this.breakAllLayerRB.setSelected(true);
			this.breakAllLayer = true;
		}
		
		if (optionNames.contains(GraTraOptions.EACH_RULE_TO_APPLY)) {
			this.eachRuleToApplyCB.setSelected(true);
			this.eachRuleToApply = true;
		} else {
			this.eachRuleToApplyCB.setSelected(false);
			this.eachRuleToApply = false;
		}
	}

	public void doClick(String button) {
		if (button.equals(GraTraOptions.NONDETERMINISTICALLY))
			this.nondeterministicallyRB.doClick();
		else if (button.equals(GraTraOptions.LAYERED))
			this.layeredRB.doClick();
		else if (button.equals(GraTraOptions.PRIORITY))
			this.priorityRB.doClick();
		else if (button.equals(GraTraOptions.RULE_SEQUENCE))
			this.ruleSequenceRB.doClick();
	}

	/**
	 * Returns TRUE if the transformation option - non-deterministically - is
	 * set.
	 */
	public boolean nondeterministicallyEnabled() {
		return this.nondeterministically;
	}

	/** Returns TRUE if the transformation option - by rule priorities - is set. */
	public boolean priorityEnabled() {
		return this.rulePriority;
	}

	/** Returns TRUE if the transformation option - by rule sequences - is set. */
	public boolean ruleSequenceEnabled() {
		return this.ruleSequence;
	}

	/** Returns TRUE if the transformation option - by rule layers - is set. */
	public boolean layeredEnabled() {
		return this.layered;
	}

	/** Returns TRUE if the option - show layer before transformation - is set. */
	public boolean showLayerEnabled() {
		return this.showLayer;
	}

	/** Returns TRUE if the option - loop over layers - is set. */
	public boolean layeredLoopEnabled() {
		return this.layeredLoop;
	}

	/** Returns TRUE if the option - reset graph - is set 
	 *  in this case the host graph will be reset for each loop over layers.
	 */
	public boolean resetGraphEnabled() {
		return this.resetGraph;
	}
	
	/** Returns TRUE if the option - stop current layer and wait - is set. */
	public boolean stopLayerAndWaitEnabled() {
		return this.stopLayerAndWait;
	}

	/** Returns TRUE if the option - break current layer only - is set. */
	public boolean breakLayerEnabled() {
		return this.breakLayer;
	}

	/** Returns TRUE if the option - break all layer - is set. */
	public boolean breakAllLayerEnabled() {
		return this.breakAllLayer;
	}

	public void setRulesOfRuleSequenceGUI(List<EdRule> rules) {
		this.rsgui.setGraGra(this.transform.getGraGra());
		this.rsgui.updateRules(rules);
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

	public void enableRuleSequenceGUI(boolean b) {
		this.rsgui.enableGUI(b);
	}

	public void showRuleSequenceGUI(String ruleSequenceName) {
		if (!ruleSequenceName.equals(""))
			this.rsgui.extendTitle(ruleSequenceName);
		if (!this.rsgui.isVisible())
			this.rsgui.setVisible(true);
		if (!this.rsgui.isGUIEnabled())
			this.rsgui.enableGUI(true);
		this.rsgui.toFront();
		
		this.transform.fireTransform(new TransformEvent(this,
					TransformEvent.RULE_SEQUENCE_DEFINE));
	}
	
	public void closeRuleSequenceGUI() {
		if (this.rsgui.isVisible()) {
			this.rsgui.closeObjectFlow();
			this.rsgui.setVisible(false);
		}
	}
	
	public boolean eachRuleToApplyEnabled() {
		return this.eachRuleToApply;
	}
	
	public void setRuleSequences(
			List<Pair<List<Pair<String, String>>, String>> sequences) {
		this.rsgui.updateRuleSequences(sequences);
	}

	public List<Pair<List<Pair<String, String>>, String>> getRuleSequences() {
		if (this.ruleSequence)
			return this.rsgui.getRuleSequences();
		
		return null;
	}

	public String getRuleSequencesAsText() {
		if (this.ruleSequence)
			return this.rsgui.getRuleSequencesText();
		
		return null;
	}

	protected JFrame applFrame;
	
	protected GraGraTransform transform;

	protected JCheckBox writeLogFileCB, 
	showLayerCB, layeredLoopCB, resetGraphCB, stopLayerAndWaitCB,
	eachRuleToApplyCB;

	protected JRadioButton nondeterministicallyRB, priorityRB, ruleSequenceRB,
	layeredRB, breakLayerRB, breakAllLayerRB;
	
	@SuppressWarnings("rawtypes")
	protected JComboBox layers, priorities;
	
	protected RuleSequenceDialog rsgui;

	protected boolean writeLogFile, nondeterministically,
	layered, layeredLoop, resetGraph, showLayer, stopLayerAndWait,
	breakLayer, breakAllLayer, 
	rulePriority, ruleSequence, eachRuleToApply;
	
	protected int layerToStop, transformThreadpriority;
	
	protected JLabel breakLayerLabel;
	
	private final String[] priorityList = {"1","2","3","4","5","6","7","8","9","10"};
	
}

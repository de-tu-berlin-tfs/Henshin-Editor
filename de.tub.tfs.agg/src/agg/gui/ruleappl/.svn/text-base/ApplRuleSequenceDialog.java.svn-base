package agg.gui.ruleappl;

import java.util.List;
import java.util.Vector;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.DefaultCellEditor;
//import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JDialog;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
//import javax.swing.SwingUtilities;

import agg.gui.help.HtmlBrowser;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Rule;


@SuppressWarnings("serial")
public class ApplRuleSequenceDialog extends JDialog implements TableModelListener,
		ListSelectionListener {

	public final static Color SEL_COLOR = new Color(184, 207, 229);
	static final Color RED = new Color(255, 210, 160); //(255, 10, 50);
	static final Color GREEN = new Color(155, 255, 105);
	static final Color DARK_BLUE = new Color(0, 0, 155);	
	static final Color BLUE = new Color(155, 205, 255);
	static final Color ORANGE = new Color(255, 255, 100);
	
	protected final String title = "Applicability of Rule Sequences ";
	
	protected JTable groupList, groupRuleList;

	protected JScrollPane scrollGroupList, scrollGroupRuleList;
	
	protected final List<RuleSequence> groups = new Vector<RuleSequence>();
	
	protected final List<String> groupNames = new Vector<String>();

	protected List<String> group;

	protected JButton checking, 
	checkGroup, uncheckGroup, resultGroup, refreshGroup,  
	close, save,
	help;

	protected JCheckBox useGraph, maxIntersectionOfConcurrency, 
						incompleteCPAcheck, ignoreDanglingEdgeOfDelNode,
						useObjectFlow, previousSequenceResults; //, consistentConcurrency;
	 
	protected JTextField concurrencyDepth;
	protected String depth = "";
	
	protected boolean useGraphToCheck, enabledObjectFlow, usePreviousSequenceResults;
	
	protected Integer groupCount = Integer.valueOf(0);

	protected MouseListener ml;

	protected int selGroupIndx; //, fromIndx, toIndx, 
	

	protected final List<String> groupListColumnNames = new Vector<String>(1);
	protected final List<String> groupRuleListColumnNames = new Vector<String>(1);
	
	protected JDialog dialog;
	
	protected HtmlBrowser helpBrowser;

	protected ApplicabilityRuleSequence ars;
	protected Thread checkThread;
	
	protected boolean changed, empty;
	
	protected String selectSeq = " Selected rule sequence to check :   ";	
	protected final JLabel selectSeqLabel;
	
	
	public ApplRuleSequenceDialog(
			final JFrame frame,
			final ApplicabilityRuleSequence applRuleSeq, 
			final Point location) {
		super();//frame);
		setModal(false);
		this.dialog = this;
		setTitle(this.title);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				ApplRuleSequenceDialog.this.ars.closeAllResultTables();
				ApplRuleSequenceDialog.this.changed = false;
				setVisible(false);
			}
		});
		
		this.selectSeqLabel = new JLabel(this.selectSeq);
		
		this.ars = applRuleSeq;
		
		this.groupListColumnNames.add("List  of  Rule  Sequences");
		this.groupRuleListColumnNames.add("Rules of selected  Rule  Sequence");

		JPanel content = initContentPane();
		
		JScrollPane scroll = new JScrollPane(content);
		scroll.setPreferredSize(new Dimension(600, 650));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll);
		validate();

		setLocation(location);
		pack();
	}
		
	public void toFront() {
		super.toFront();
	}
	
	public void dispose() {
		this.clear();
		this.close();
	}

	public void extendTitle(final String name) {
		String str = " ".concat(name).concat(" - ");
		this.setTitle(str.concat(this.title));
	}
	
	public void updateGraphName() {		
		int indx = this.groupList.getSelectedRow();
		if (indx >= 0) {
			updateGroupList();
			this.groupList.changeSelection(indx, 0, false, false);
			this.changed = true;
		}  		
	}
	 				
	public void updateRuleSequences(final List<RuleSequence> sequences) {
		this.selGroupIndx = -1;
		if (sequences != null) {
			this.groups.clear();
			for (int i=0; i<sequences.size(); i++) {				
				this.groups.add(sequences.get(i));
			}
			updateGroupList();
			this.empty = this.groups.isEmpty();
			
			if (this.groups.isEmpty()) {
				if (this.groupRuleList != null) {
					this.scrollGroupRuleList.getViewport().remove(this.groupRuleList);
				}
				this.setVisible(false);
			} else {				
				this.enableARSbuttons(true);
				this.enableGUIbuttons(true);				
			}
		} else {
			this.empty = true;
			this.enableARSbuttons(false);
			this.enableGUIbuttons(false);
		}
	}
	
	void updateRuleSequence(final RuleSequence sequence) {
		if (sequence != null) {
			for (int i=0; i<this.groups.size(); i++) {
				final RuleSequence ruleSeq = this.groups.get(i);
				if (ruleSeq == sequence) {
					uncheckRuleSequence(i);
					String rsTxt = (String)this.groupList.getValueAt(i, 0);
					String rulesTxt = rsTxt.substring(rsTxt.indexOf('('), rsTxt.indexOf(')')+1);
					if (!rulesTxt.equals(ruleSeq.getRuleNamesString())) {
						sequence.refresh();
					}
					updateGroupList();
					
				}
			}
		}
	}
	
	/**
	 * Returns defined rule sequences.
	 */
	public List<RuleSequence> getRuleSequences() {
		return this.groups;
	}

	public void selectRuleSequence(int indx) {
		this.selectSequence(indx);
		this.groupList.changeSelection(indx, 0, false, false);
	}
	
	public RuleSequence getSelectedRuleSequence() {
		return this.groups.get(this.groupList.getSelectedRow());
	}
	
	public int getIndexOfSelectedSequence() {
		return this.groupList.getSelectedRow();
	}
	
	private void enableARSbuttons(final boolean b) {
		this.groupList.setEnabled(b);
		this.groupRuleList.setEnabled(b);
		this.checkGroup.setEnabled(b);
		this.uncheckGroup.setEnabled(b);		
		this.resultGroup.setEnabled(b);
		this.refreshGroup.setEnabled(b);
		
		this.concurrencyDepth.setEditable(b);
		this.maxIntersectionOfConcurrency.setEnabled(b);
		this.incompleteCPAcheck.setEnabled(b);
		this.ignoreDanglingEdgeOfDelNode.setEnabled(b);
//		this.consistentConcurrency.setEnabled(b);
	}

	private void enableGUIbuttons(final boolean b) {		
		this.save.setEnabled(b);
//		this.help.setEnabled(b);
	}
	
	private JPanel initContentPane() {
		Border border = new TitledBorder("");

		this.ml = this.makeMouseAdapter();
		
		JPanel p0 = new JPanel(new GridBagLayout());
		JPanel p1 = this.makeAddRuleSequencePanel(border);		
		JPanel p2 = this.makeCheckRuleSequencePanel(border);		
		JPanel p3 = this.makeConcurrencyOptionsPanel(border);
		JPanel p31 = this.makeObjectFlowOptionPanel(border);
		JPanel p32 = this.makeUsePreviousRuleSequenceResultsOptionPanel(border);
		JPanel p4 = this.makeButtonsPanel(border);
		
		constrainBuild(p0, p1, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		
		constrainBuild(p0, p2, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p0, p3, 0, 2, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);	
		constrainBuild(p0, p31, 0, 3, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p0, p32, 0, 4, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p0, p4, 0, 5, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);

		return p0;
	}

	protected void closeWithWarning() {
		if (this.changed && !this.isEmpty()
				&& !this.ars.isEmpty()) {
			Object[] options = { "Save & Close", "Close", "Cancel" };
			int answer = JOptionPane
					.showOptionDialog(
						this,
						"<html><body>Rule sequence data changed. <br>"
								+ "Do you want to save it first?"
								+ "</bod<></html>",
						"Close Dialog", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options,
						options[0]);
			if (answer == 0) {
				this.saveRuleSequencesAndGrammar();
				close();
			} else if (answer == 1) {
				close();
			}
		} else {
			close();
		}
	}
	
	protected void close() {
		this.ars.closeAllResultTables();
//		this.clear();
		setVisible(false);
		this.changed = false;
		
		this.ars.closeGraGra();		
	}
	
	private JPanel makeButtonsPanel(final Border border) {	
		JPanel p5 = new JPanel(new GridBagLayout());
		this.close = new JButton("Close");
		this.close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeWithWarning();
			}
		});
		
		this.save = new JButton("Save");
		this.save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRuleSequencesAndGrammar();
			}
		});

		this.help = new JButton("Help");
		this.help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ApplRuleSequenceDialog.this.helpBrowser != null) {
//					helpBrowser.setVisible(true);
					ApplRuleSequenceDialog.this.helpBrowser.dispose();
					ApplRuleSequenceDialog.this.helpBrowser = null;
				}
				if (ApplRuleSequenceDialog.this.helpBrowser == null) {
					ApplRuleSequenceDialog.this.helpBrowser = new HtmlBrowser("ApplicabilityOfRuleSequencesHelp.html");				
					ApplRuleSequenceDialog.this.helpBrowser.setSize(500, 600);
					ApplRuleSequenceDialog.this.helpBrowser.setLocation(20, 20);
					ApplRuleSequenceDialog.this.helpBrowser.setVisible(true);
//					helpBrowser.toFront();
				}
			}
		});

		constrainBuild(p5, this.close, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 5, 10);
		constrainBuild(p5, this.save, 1, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 5, 10);				
		constrainBuild(p5, this.help, 2, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 5, 10);
		
		return p5;
	}
	
	private void makeUseGraphCheck() {
		this.useGraph = new JCheckBox(" Use graph ", true);
		this.useGraphToCheck = true;
		this.useGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
				ApplRuleSequenceDialog.this.useGraphToCheck = ((JCheckBox) e.getSource()).isSelected();
				if (ruleseq != null) {
					int answer = 0;
					Object[] options = { "OK", "Cancel" };					
					if (ApplRuleSequenceDialog.this.useGraphToCheck && ruleseq.getGraph() == null) {
						if (ruleseq.isObjFlowActive()
								|| ruleseq.isChecked()) {
							answer = JOptionPane.showOptionDialog(
								ApplRuleSequenceDialog.this.dialog,
								"<html><body>"
								+"Currently selected sequence contains an object flow \n"
								+"or is already checked without given start graph.\n"
								+"The result will be lost after selecting graph.",
								"Use graph selected", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						}
						if (answer == 0) {
							ruleseq.setGraph(ApplRuleSequenceDialog.this.ars.getGraph());
							ruleseq.setCheckAtGraph(true);
						} else {
							ruleseq.setCheckAtGraph(false);
						}
					} else  if (!ApplRuleSequenceDialog.this.useGraphToCheck && ruleseq.getGraph() != null) {
						if (ruleseq.isObjFlowActive()
								|| ruleseq.isChecked()) {
							answer = JOptionPane.showOptionDialog(
								ApplRuleSequenceDialog.this.dialog,
								"<html><body>"
								+"Currently selected sequence contains an object flow \n"
								+"or is already checked at given start graph.\n"
								+"The results will be lost after deselecting graph.",
								"Use graph deselected", JOptionPane.DEFAULT_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
						}
						if (answer == 0) {
							ruleseq.setGraph(null);
							ruleseq.setCheckAtGraph(false);
						} else {
							ruleseq.setCheckAtGraph(true);
						}
					} 					
				}
				if (!ApplRuleSequenceDialog.this.groups.isEmpty()) {
					updateGraphName();	
				}
			}
		});
	}
	
	private JPanel makeConcurrencyOptionsPanel(final Border border) {
		final JPanel p4 = new JPanel(new BorderLayout());
		p4.setBorder(border);
		
		final JPanel p = new JPanel(new BorderLayout());	
		this.concurrencyDepth = new JTextField(this.depth, 3);
		this.concurrencyDepth.setEditable(false);
		this.concurrencyDepth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!((JTextField) e.getSource()).getText().equals("")) {
					try {
						Integer nb = Integer.valueOf(((JTextField) e.getSource())
								.getText());
						if (nb.intValue() < 0) {
							ApplRuleSequenceDialog.this.concurrencyDepth.setText(String.valueOf(Math.abs(nb.intValue())));
							nb = Integer.valueOf(Math.abs(nb.intValue()));
						}
						int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
						if (i >= 0) {
							ApplRuleSequenceDialog.this.ars.getRuleSequence(i).setDepthOfConcurrentRule(nb.intValue());
						}
					} catch (NumberFormatException ex) {
						ApplRuleSequenceDialog.this.concurrencyDepth.setText("");
						
					}
				} else {
					int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
					if (i >= 0) {
						ApplRuleSequenceDialog.this.ars.getRuleSequence(i).setDepthOfConcurrentRule(-1);
					}
				}
			}
		});	
		p.add(this.concurrencyDepth, BorderLayout.WEST);
		p.add(new JLabel(" Number of direct enabling predecessors  "), BorderLayout.CENTER);		
		
		final JPanel pmax = new JPanel(new BorderLayout());
		this.maxIntersectionOfConcurrency = new JCheckBox(" ");
		this.maxIntersectionOfConcurrency.setEnabled(false);
		this.maxIntersectionOfConcurrency.setSelected(true);
		this.maxIntersectionOfConcurrency.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
					if (ruleseq != null) {
						ruleseq.setCompleteConcurrency(!((JCheckBox) e.getSource()).isSelected());
					}
				}
			}
		});
		pmax.add(this.maxIntersectionOfConcurrency, BorderLayout.WEST);
		pmax.add(new JLabel("Max intersection of direct enabling predecessors"),BorderLayout.CENTER);		
		p.add(pmax, BorderLayout.SOUTH);
		
		p4.add(p, BorderLayout.WEST);
		
		final JPanel p1 = new JPanel(new BorderLayout());
		this.incompleteCPAcheck = new JCheckBox(" ");
		this.incompleteCPAcheck.setEnabled(false);
		this.incompleteCPAcheck.setSelected(true);
		this.incompleteCPAcheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
					if (ruleseq != null) {
						ruleseq.setCompleteCPAOfConcurrency(!((JCheckBox) e.getSource()).isSelected());
					}
				}
			}
		});
		p1.add(this.incompleteCPAcheck, BorderLayout.WEST);
		final JPanel p2 = new JPanel(new GridLayout(0,1));
		p2.add(new JLabel("Incomplete recognition of potential"));
		p2.add(new JLabel("conflictfree summarized predecessors"));
		p1.add(p2, BorderLayout.CENTER);
				
		p4.add(p1, BorderLayout.EAST);
		
//		final JPanel p3 = new JPanel(new BorderLayout());
		this.ignoreDanglingEdgeOfDelNode = new JCheckBox(
				" Ignore the case of possible dangling edges of node-deleting rules");
		this.ignoreDanglingEdgeOfDelNode.setEnabled(false);
		this.ignoreDanglingEdgeOfDelNode.setSelected(false);
		this.ignoreDanglingEdgeOfDelNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
					if (ruleseq != null) {
						ruleseq.setIgnoreDanglingEdgeOfDelNode(((JCheckBox) e.getSource()).isSelected());
					}
				}
			}
		});
//		p1.add(this.incompleteCPAcheck, BorderLayout.WEST);
//		final JPanel p2 = new JPanel(new GridLayout(0,1));
//		p2.add(new JLabel("Incomplete recognition of potential"));
//		p2.add(new JLabel("conflictfree summarized predecessors"));
//		p1.add(p2, BorderLayout.CENTER);
				
		p4.add(this.ignoreDanglingEdgeOfDelNode, BorderLayout.SOUTH);
		
		return p4;
	}
	
	private JPanel makeObjectFlowOptionPanel(final Border border) {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(border);
		
		this.useObjectFlow = new JCheckBox("Use defined object flow ");
		this.useObjectFlow.setEnabled(false);
		this.useObjectFlow.setSelected(true);
		this.useObjectFlow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplRuleSequenceDialog.this.enabledObjectFlow = ((JCheckBox) e.getSource()).isSelected();
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
				if (ruleseq != null) {
					ruleseq.enableObjFlow(ApplRuleSequenceDialog.this.enabledObjectFlow);
					if (ApplRuleSequenceDialog.this.enabledObjectFlow 
							&& !ruleseq.isObjFlowActive()) {
						JOptionPane.showMessageDialog(null, "There is't any object flow for this sequence.");
						ApplRuleSequenceDialog.this.enabledObjectFlow = false;						
						((JCheckBox) e.getSource()).setSelected(false);
					}
				}
			}
		});
		
		p.add(this.useObjectFlow, BorderLayout.CENTER);

		return p;
	}
	
	private JPanel makeUsePreviousRuleSequenceResultsOptionPanel(final Border border) {
		final JPanel p = new JPanel(new BorderLayout());
		p.setBorder(border);
		
		this.previousSequenceResults = new JCheckBox("Use results of previous rule sequence ");
		this.previousSequenceResults.setEnabled(false);
		this.previousSequenceResults.setSelected(true);
		this.previousSequenceResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplRuleSequenceDialog.this.usePreviousSequenceResults = ((JCheckBox) e.getSource()).isSelected();
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
				if (ruleseq != null) {
					ruleseq.setUsePreviousSequenceResults(ApplRuleSequenceDialog.this.usePreviousSequenceResults);
				}
			}
		});
		
		p.add(this.previousSequenceResults, BorderLayout.CENTER);

		return p;
	}
	
	private JPanel makeCheckRuleSequencePanel(final Border border) {
		JPanel p2 = new JPanel(new GridBagLayout());
		p2.setBorder(border);
		JPanel p21 = new JPanel(new BorderLayout());
		JPanel p22 = new JPanel(new BorderLayout());
		this.groupNames.clear();
		this.scrollGroupList = new JScrollPane();
		this.scrollGroupList.setPreferredSize(new Dimension(400, 100));
		
		this.groupList = createGroupList(this.groupNames);
		
		List<JButton> buttons = new Vector<JButton>(4);
		
		makeCheckButton(buttons);
		makeResultButton(buttons);
		makeUncheckButton(buttons);
		makeUpdateButton(buttons);
		
		JPanel groupButtonPanel = makeButton(buttons);
		
		p21.add(this.selectSeqLabel, BorderLayout.NORTH);
		p21.add(this.scrollGroupList, BorderLayout.CENTER);
		p21.add(groupButtonPanel, BorderLayout.SOUTH);
		
		JLabel l = new JLabel("     ");
		this.group = new Vector<String>();
		
		this.scrollGroupRuleList = new JScrollPane();
		this.scrollGroupRuleList.setPreferredSize(new Dimension(400, 150));
		
		this.groupRuleList = createGroupRuleList(this.group);
				
		p22.add(l, BorderLayout.NORTH);
		p22.add(this.scrollGroupRuleList, BorderLayout.CENTER);
		constrainBuild(p2, p21, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		constrainBuild(p2, p22, 0, 1, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 5, 5, 5, 5);
		return p2;
	}
	
	private void makeCheckButton(final List<JButton> buttons) {
		this.checkGroup = new JButton(" Check ");
		this.checkGroup.setEnabled(false);
		this.checkGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					ApplRuleSequenceDialog.this.selGroupIndx = i;
					RuleSequence ruleseq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
					
					if (ApplRuleSequenceDialog.this.group.isEmpty()) {
						JOptionPane.showMessageDialog(ApplRuleSequenceDialog.this.dialog, 
									"<html><body>"
									+"Nothings to check!  "
									+"Empty rule sequence.");	
					} 
					else if (ruleseq.containsRuleWithGACs()) {
						JOptionPane.showMessageDialog(null,
								"Computation is not possible! \n"
								+"At least one of rules makes use of General Application Conditions.\n" 
								+"Applicability check is not jet implemented.", 
								"Cannot compute", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						Rule failedRule = BaseFactory.theFactory().checkApplCondsOfRules(ruleseq.getRules());
						if (failedRule != null) {
							JOptionPane.showMessageDialog(null,
									"Checking is not possible! \n"
									+"At least one of rules contains an invalid PAC.\n"						
									+failedRule.getName()+":    "+failedRule.getErrorMsg(),	
									"Cannot check",
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						
						if (!ruleseq.isObjFlowValid()) {
							String error = "";
							if (ruleseq.getMessageOfInvalidObjectFlow() == 0)
								error = "\n( transitive closure failed )";
							else if (ruleseq.getMessageOfInvalidObjectFlow() == 1)
								error = "\n( persistent object flow failed )";
								
							JOptionPane.showMessageDialog(null, 
									"Object flow of the current rule sequence is not valid!"
									+error, 
									"Cannot check", 
									JOptionPane.ERROR_MESSAGE);
							return;
						}
						try {
							
							ruleseq.tryCompleteObjFlowTransClosure();
							
							if (!ApplRuleSequenceDialog.this.concurrencyDepth.getText().equals("")) {
								ruleseq.setDepthOfConcurrentRule(Integer.valueOf(ApplRuleSequenceDialog.this.concurrencyDepth.getText()).intValue());
							}
							ruleseq.setCompleteConcurrency(!ApplRuleSequenceDialog.this.maxIntersectionOfConcurrency.isSelected());
							ruleseq.setCompleteCPAOfConcurrency(!ApplRuleSequenceDialog.this.incompleteCPAcheck.isSelected());
							ruleseq.setIgnoreDanglingEdgeOfDelNode(ApplRuleSequenceDialog.this.ignoreDanglingEdgeOfDelNode.isSelected());
//							ruleseq.setConsistentConcurrency(consistentConcurrency.isSelected());
							
							if (i > 0) {								
								int nextIndx = ruleseq.tryToApplyResultsOfRuleSequence(
																	ApplRuleSequenceDialog.this.ars.getRuleSequence(i-1));
								ruleseq.setStartIndexOfCheck(nextIndx);																
							}
							
							// for test only!!!
//							ars.check(selGroupIndx);
							
							
							ApplRuleSequenceDialog.this.checkThread = new Thread(ruleseq.getApplicabilityChecker());
							ApplRuleSequenceDialog.this.checkThread.setPriority(4);
							ApplRuleSequenceDialog.this.checkThread.start();
										
							final String oldtext = ApplRuleSequenceDialog.this.checking.getText();
							final Color oldcolor = ApplRuleSequenceDialog.this.checking.getBackground();
							final Border oldborder = ApplRuleSequenceDialog.this.checking.getBorder();
							ApplRuleSequenceDialog.this.checking.setEnabled(true);
							ApplRuleSequenceDialog.this.checking.setText(" PLEASE WAIT ");
							ApplRuleSequenceDialog.this.checking.setForeground(Color.RED);
							ApplRuleSequenceDialog.this.checking.setBorder(
									BorderFactory.createEtchedBorder(Color.RED, Color.RED));	
							ApplRuleSequenceDialog.this.checking.doClick();
							ApplRuleSequenceDialog.this.checking.setEnabled(false);	
									
							while(ApplRuleSequenceDialog.this.checkThread.isAlive()) {}
							ApplRuleSequenceDialog.this.checkThread = null;
						
							ApplRuleSequenceDialog.this.groupList.clearSelection();
							ApplRuleSequenceDialog.this.groupList.getSelectionModel().setLeadSelectionIndex(i);
							
							ApplRuleSequenceDialog.this.uncheckGroup.setEnabled(true);
							ApplRuleSequenceDialog.this.resultGroup.setEnabled(true);
							ApplRuleSequenceDialog.this.changed = true; 
										
							ApplRuleSequenceDialog.this.checking.setText(oldtext);
							ApplRuleSequenceDialog.this.checking.setForeground(Color.BLACK);
							ApplRuleSequenceDialog.this.checking.setBackground(oldcolor);
							ApplRuleSequenceDialog.this.checking.setBorder(oldborder);
						}catch (Exception ex) {
							setConcurrencyDepth(ruleseq.getDepthOfConcurrentRule());												
						}
					}
				} else {
					JOptionPane.showMessageDialog(ApplRuleSequenceDialog.this.dialog, 
								"<html><body>"
								+"Please select a rule sequence, first."
								+"</body></html>");	
				}
			}
		});
		buttons.add(this.checkGroup);	
	}
	
	private void makeResultButton(final List<JButton> buttons) {
		this.resultGroup = new JButton("Result");
		this.resultGroup.setEnabled(false);
		this.resultGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					ApplRuleSequenceDialog.this.selGroupIndx = i;
					if (ApplRuleSequenceDialog.this.group.isEmpty()) {
						JOptionPane.showMessageDialog(ApplRuleSequenceDialog.this.dialog, 
							"<html><body>"
							+"Empty rule sequence."
							+"</body></html>");	
					} else {
						Object[] options = { "Applicability", "Non-Applicability" };
						int answer = JOptionPane
							.showOptionDialog(ApplRuleSequenceDialog.this.dialog,
									"Please choose what do you want to see.",
									" Applicability of Rule Sequence ", JOptionPane.DEFAULT_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, options,
									options[0]);
						if (answer == 0) {
							ApplRuleSequenceDialog.this.ars.showApplicabilityResult(ApplRuleSequenceDialog.this.selGroupIndx);	
						} else {
							ApplRuleSequenceDialog.this.ars.showNonApplicabilityResult(ApplRuleSequenceDialog.this.selGroupIndx);
						}							
					}
				} else {
					JOptionPane.showMessageDialog(ApplRuleSequenceDialog.this.dialog, 
						"<html><body>"
						+"Please select a rule sequence, first."
						+"</body></html>");	
				}
			}
		});
		buttons.add(this.resultGroup);
	}
	
	private void makeUncheckButton(final List<JButton> buttons) {
		this.uncheckGroup = new JButton("Uncheck");
		this.uncheckGroup.setEnabled(false);
		this.uncheckGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
				if (i >= 0) {
					ApplRuleSequenceDialog.this.selGroupIndx = i;
					uncheckRuleSequence(i);
					
					if (i > 0) {
						if (ApplRuleSequenceDialog.this.ars.getRuleSequence(i-1).isChecked()) {
							ApplRuleSequenceDialog.this.previousSequenceResults.setEnabled(true);
						} else {
							ApplRuleSequenceDialog.this.previousSequenceResults.setEnabled(false);
						}
					} else {
						ApplRuleSequenceDialog.this.previousSequenceResults.setEnabled(false);
					}
					ApplRuleSequenceDialog.this.usePreviousSequenceResults = ApplRuleSequenceDialog.this.previousSequenceResults.isEnabled()
													&& ApplRuleSequenceDialog.this.previousSequenceResults.isSelected();
					ApplRuleSequenceDialog.this.ars.getRuleSequence(i).setUsePreviousSequenceResults(ApplRuleSequenceDialog.this.usePreviousSequenceResults);
				}
			}
		});
		buttons.add(this.uncheckGroup);	
	}
	
	void uncheckRuleSequence(int indx) {
		if (this.ars.removeResultOfSequence(indx)) {			
			this.groupList.clearSelection();
			this.groupList.getSelectionModel().setLeadSelectionIndex(indx);	
			this.checkGroup.setEnabled(true);
			this.resultGroup.setEnabled(false);	
			this.uncheckGroup.setEnabled(false);
			this.changed = true;
		}
	}
	
	private void makeUpdateButton(final List<JButton> buttons) {
		this.refreshGroup = new JButton("Refresh");
		this.refreshGroup.setEnabled(true);
		this.refreshGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ApplRuleSequenceDialog.this.ars.getApplRuleSequence().getRuleSequences().size() 
						!= ApplRuleSequenceDialog.this.ars.getGraGra().getBasisGraGra().getRuleSequences().size()) {

					ApplRuleSequenceDialog.this.ars.getApplRuleSequence().setRuleSequences(ApplRuleSequenceDialog.this.ars.getGraGra().getBasisGraGra().getRuleSequences());

					updateRuleSequences(ApplRuleSequenceDialog.this.ars.getGraGra().getBasisGraGra().getRuleSequences());
					
					if (ApplRuleSequenceDialog.this.ars.getApplRuleSequence().getRuleSequences().size() > 0) {
						ApplRuleSequenceDialog.this.selectRuleSequence(0);
					}
				} else {
					int i = ApplRuleSequenceDialog.this.groupList.getSelectedRow();
					if (i >= 0) {					
						final RuleSequence ruleSeq = ApplRuleSequenceDialog.this.ars.getRuleSequence(i);
						int answer = 0;
						if (ruleSeq.getGraph() != ApplRuleSequenceDialog.this.ars.getGraGra().getGraph().getBasisGraph()) {
							if (ruleSeq.isChecked()
										|| ruleSeq.isObjFlowActive()) {
								Object[] options = { "OK", "Cancel" };
								answer = JOptionPane.showOptionDialog(
													null,
													"<html><body>"
													+"Currently selected rule sequence contains an object flow\n"
													+"or is already checked.\n"
													+"The results will be lost after graph reset.",
													"Reset graph", JOptionPane.DEFAULT_OPTION,
													JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
							}
						}
						if (answer == 0) {
							ruleSeq.setGraph(ApplRuleSequenceDialog.this.ars.getGraGra().getGraph().getBasisGraph());
							
							updateRuleSequence(ruleSeq);
	
							ApplRuleSequenceDialog.this.groupList.clearSelection();
							ApplRuleSequenceDialog.this.selGroupIndx = -1;
							ApplRuleSequenceDialog.this.groupList.changeSelection(i, 0, false, false);
						}
					}
				}
			}
		});
		buttons.add(this.refreshGroup);
	}
	
	private JPanel makeAddRuleSequencePanel(final Border border) {
		final JPanel p = new JPanel(new GridBagLayout());
		p.setBorder(border);
				
//		this.addRS.setText(addRStext);
		this.checking = new JButton("   Checking   ");
		this.checking.setEnabled(false);
		
		final JPanel p1 = new JPanel(new BorderLayout());
		p1.add(this.checking, BorderLayout.EAST);
						
		this.makeUseGraphCheck();
		final JPanel p2 = new JPanel(new BorderLayout());
//		final JLabel useGraphText = new JLabel(
//				" If selected, a rule sequence will be checked at current graph of the grammar.");
		p2.add(this.useGraph, BorderLayout.WEST);
		p2.add(new JLabel("to check a rule sequence at current graph  "), BorderLayout.EAST);
		
		final JPanel p3 = new JPanel(new BorderLayout());
		p3.add(p1, BorderLayout.EAST);
		p3.add(p2, BorderLayout.WEST);
		p3.add(new JLabel("       "), BorderLayout.SOUTH);
		
		constrainBuild(p, p3, 0, 0, 1, 1, GridBagConstraints.BOTH,
				GridBagConstraints.CENTER, 1.0, 0.0, 10, 5, 5, 5);

		return p;
	}
	
	private MouseAdapter makeMouseAdapter() {
		return new MouseAdapter() {
			
		public void mousePressed(MouseEvent e) {
//			if (SwingUtilities.isMiddleMouseButton(e)) {
//				ApplRuleSequenceDialog.this.dialog.setCursor(new Cursor(Cursor.MOVE_CURSOR));
//				if (e.getSource() == ApplRuleSequenceDialog.this.groupList) {
//					ApplRuleSequenceDialog.this.fromIndx = ApplRuleSequenceDialog.this.groupList.rowAtPoint(new Point(e.getX(), e
//							.getY()));								
//				} 
//			}
		}

		public void mouseReleased(MouseEvent e) {
//			if (e.getSource() == ApplRuleSequenceDialog.this.groupList) {
//				ApplRuleSequenceDialog.this.toIndx = ApplRuleSequenceDialog.this.groupList
//						.rowAtPoint(new Point(e.getX(), e.getY()));
//				if (ApplRuleSequenceDialog.this.toIndx >= 0) {
//					if (SwingUtilities.isMiddleMouseButton(e)) {
//						RuleSequence seq = ApplRuleSequenceDialog.this.groups.get(
//								ApplRuleSequenceDialog.this.fromIndx);
//						ApplRuleSequenceDialog.this.ars.moveRuleSequence(
//								ApplRuleSequenceDialog.this.fromIndx, ApplRuleSequenceDialog.this.toIndx);		
//												
//						ApplRuleSequenceDialog.this.groups.remove(seq);
//						ApplRuleSequenceDialog.this.groups.add(ApplRuleSequenceDialog.this.toIndx, seq);
//						((DefaultTableModel) ApplRuleSequenceDialog.this.groupList.getModel()).moveRow(
//									ApplRuleSequenceDialog.this.fromIndx, 
//									ApplRuleSequenceDialog.this.fromIndx, 
//									ApplRuleSequenceDialog.this.toIndx);
//						ApplRuleSequenceDialog.this.selGroupIndx = ApplRuleSequenceDialog.this.toIndx;
//						updateGroups();
//						ApplRuleSequenceDialog.this.groupList.changeSelection(
//								ApplRuleSequenceDialog.this.selGroupIndx, 0, false, false);
//						ApplRuleSequenceDialog.this.changed = true;											
//					}
//				}			
//			} 
			ApplRuleSequenceDialog.this.dialog.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		};
	}
			
	protected void saveRuleSequences() {		
		this.ars.save();
		this.changed = false;
	}
	
	protected void saveRuleSequencesAndGrammar() {	
		ApplRuleSequenceSaveLoad arsLayout = new ApplRuleSequenceSaveLoad(
				this.ars.getApplRuleSequence(), this.ars.getGraGra());		
		arsLayout.save();
		this.changed = false;
	}
	
	public void loadWarning() {
		if (this.isVisible() && this.changed) {
			Object[] options = { "Save", "Cancel" };
			int answer = JOptionPane
					.showOptionDialog(
						this,
						"<html><body>Rule sequence data changed. <br>"
								+ "Do you want to save first?"
								+ "</body></html>",
						"Save Warning", JOptionPane.DEFAULT_OPTION,
						JOptionPane.WARNING_MESSAGE, null, options,
						options[0]);
			if (answer == 0) {
				this.saveRuleSequencesAndGrammar();
			}
		}
	}
	
	public void loadRuleSequences(final List<Rule> allrules, 
								final List<RuleSequence> sequences) {
		
		this.updateRuleSequences(sequences);	
		
		if (!this.groups.isEmpty()) {
			this.selectSequence(0);
			this.groupList.changeSelection(0, 0, false, false);	
		}			
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() instanceof DefaultListSelectionModel) {
			// row of groupList clicked
			int indx = ((DefaultListSelectionModel) e.getSource())
					.getLeadSelectionIndex();
			if (indx != -1 && indx < this.groups.size() && !this.groups.isEmpty()) {
//				System.out.println("valueChanged    "+selGroupIndx);
				selectSequence(indx);
			}
		} 
	}

	void selectSequence(int indx) {
		if (this.selGroupIndx != indx || this.groups.size() == 1) {
			this.selGroupIndx = indx;		
			RuleSequence ruleseq = this.ars.getRuleSequence(indx);						
			if (ruleseq == null) {
				return;
			}

			this.selectSeqLabel.setText(this.selectSeq+ruleseq.getName());
			
			this.resultGroup.setEnabled(ruleseq.isChecked());
			this.uncheckGroup.setEnabled(ruleseq.isChecked());
			this.checkGroup.setEnabled(true); 
			this.group = this.groups.get(indx).getRuleNames();
			updateGroupRuleList();	
			
			this.useGraphToCheck = ruleseq.getGraph() != null;
			this.useGraph.setSelected(this.useGraphToCheck);
			
			this.concurrencyDepth.setEditable(true);
			this.maxIntersectionOfConcurrency.setEnabled(true);
			this.incompleteCPAcheck.setEnabled(true);
//			consistentConcurrency.setEnabled(true);
			
			setConcurrencyDepth(ruleseq.getDepthOfConcurrentRule());										
			this.maxIntersectionOfConcurrency.setSelected(!ruleseq.getCompleteConcurrency());
			this.incompleteCPAcheck.setSelected(!ruleseq.getCompleteCPAOfConcurrency());
			this.ignoreDanglingEdgeOfDelNode.setSelected(ruleseq.getIgnoreDanglingEdgeOfDelNode());
//			consistentConcurrency.setSelected(ruleseq.getConsistentConcurrency());
			
			this.enabledObjectFlow = ruleseq.isObjFlowEnabled();
			this.useObjectFlow.setEnabled(this.enabledObjectFlow);
			this.useObjectFlow.setSelected(ruleseq.isObjFlowActive());
			
			
			if (indx > 0) {
				if (this.ars.getRuleSequence(indx-1).isChecked()) {
					this.previousSequenceResults.setEnabled(true);
				} else {
					this.previousSequenceResults.setEnabled(false);
				}
			}
			this.usePreviousSequenceResults = this.previousSequenceResults.isEnabled()
											&& this.previousSequenceResults.isSelected();
			ruleseq.setUsePreviousSequenceResults(this.usePreviousSequenceResults);
		}	
	}
	
	protected void setConcurrencyDepth(int concurdepth) {
		if (concurdepth < 0) {
			this.concurrencyDepth.setText("");
		} else {
			this.concurrencyDepth.setText(String.valueOf(concurdepth));
		}
	}
	
	private JTable createGroupList(final List<String> names) {
		updateGroupList();
		return this.groupList;
	}

	private JTable createGroupRuleList(final List<String> names) {
		updateGroupRuleList();
		return this.groupRuleList;
	}

	private JPanel makeButton(final List<JButton> list) {		
		JPanel p = new JPanel(new GridBagLayout());
		for (int i=0; i<list.size(); i++) {
			JButton b = list.get(i);
			constrainBuild(p, b, i, 0, 1, 1, GridBagConstraints.BOTH,
					GridBagConstraints.CENTER, 1.0, 0.0, 5, 10, 5, 10);			
		}
		return p;
	}

	protected String getRuleSequenceText(final RuleSequence sequence, final int indx) {	
		String graphStr = "";
		if (sequence.getGraph() != null) {
			graphStr = graphStr.concat(sequence.getGraph().getName());
			graphStr = graphStr.concat(" <= ");
		}
		
//		String grpStr = " ( ";		
//		for (int i = 0; i < sequence.getRules().size(); i++) {					
//			String rulename = sequence.getRules().get(i).getName();
//			grpStr = grpStr + rulename;				
//			grpStr = grpStr + " ";
//		}
//		grpStr = grpStr.concat(")");
		
		String grpStr = " "+sequence.getRuleNamesString();	
		grpStr = graphStr.concat(grpStr);
		return grpStr;
	}

	public boolean isEmpty() {
		return this.empty;
	}

	public void clear() {
		this.clearGroups();
		this.enableARSbuttons(false);
		this.enableGUIbuttons(false);
		
		this.ars.clear();
	}

	protected void clearGroups() {
		this.groups.clear();
		this.groupCount = Integer.valueOf(0);
		this.group = new Vector<String>();
		updateGroupList();
		updateGroupRuleList();
	}

	void updateGroups() {
		for (int i = 0; i < this.groupList.getRowCount(); i++) {
			String lstr = makeRuleSequenceString(i);
			this.groupList.getModel().setValueAt(lstr, i, 0);
		}
	}

	private String makeRuleSequenceString(int i) {
		String lstr = String.valueOf(i + 1);
		while (lstr.length() < 4) {
			lstr = lstr.concat(" ");
		}
		lstr = " ".concat(lstr);
		String grpStr = getRuleSequenceText(this.groups.get(i), i);
		lstr = lstr.concat(grpStr);
		return lstr;
	}
	
	@SuppressWarnings("rawtypes")
	protected void updateGroupList() {
		Vector<Vector<String>> data = new Vector<Vector<String>>(this.groups.size());
		for (int i = 0; i < this.groups.size(); i++) {
			Vector<String> rd = new Vector<String>(1);	
			String lstr = makeRuleSequenceString(i);			
			rd.add(lstr);
			data.add(rd);
		}
		
		if (this.groupList != null) {
			this.scrollGroupList.getViewport().remove(this.groupList);
		}
		
		this.groupList = new JTable(data, (Vector)this.groupListColumnNames);
		this.groupList.setDefaultRenderer(this.groupList.getColumnClass(0),
				new MyTableCellRenderer(this.ars, this.groups));		
		this.groupList.getModel().addTableModelListener(this);		
		for (int i = 0; i < this.groupList.getRowCount(); i++) {
			((DefaultCellEditor) this.groupList.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
		}
		this.groupList.addMouseListener(this.ml);
		this.scrollGroupList.getViewport().setView(this.groupList);
		
		this.groupList.getSelectionModel().setSelectionMode(0);
		this.groupList.getSelectionModel().addListSelectionListener(this);	
	}

	@SuppressWarnings("rawtypes")
	protected void updateGroupRuleList() {
		Vector<Vector<String>> data = new Vector<Vector<String>>(this.group.size());
		for (int i = 0; i < this.group.size(); i++) {
			String r = this.group.get(i);
			Vector<String> rd = new Vector<String>(1);
			rd.add(r);
			data.add(rd);
		}
		
		if (this.groupRuleList != null) {
			this.scrollGroupRuleList.getViewport().remove(this.groupRuleList);
		}
		
		this.groupRuleList = new JTable(data, (Vector)this.groupRuleListColumnNames);
		this.groupRuleList.getModel().addTableModelListener(this);
		for (int i = 0; i < this.groupRuleList.getRowCount(); i++) {
			((DefaultCellEditor) this.groupRuleList.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
		}
		this.groupRuleList.addMouseListener(this.ml);
		
		this.scrollGroupRuleList.getViewport().setView(this.groupRuleList);		
		
		this.groupRuleList.getSelectionModel().setSelectionMode(0);
	}

	@SuppressWarnings("rawtypes")
	protected void updateGroupRuleList(final List<String> aGroup) {
		List<List<String>> data = new Vector<List<String>>(aGroup.size());
		for (int i = 0; i < aGroup.size(); i++) {
			String r = aGroup.get(i);
			List<String> rd = new Vector<String>(1);
			rd.add(r);
			data.add(rd);
		}
		
		if (this.groupRuleList != null) {
			this.scrollGroupRuleList.getViewport().remove(this.groupRuleList);
		}
		
		this.groupRuleList = new JTable((Vector)data, (Vector)this.groupRuleListColumnNames);
		this.groupRuleList.getModel().addTableModelListener(this);
		for (int i = 0; i < this.groupRuleList.getRowCount(); i++) {
			((DefaultCellEditor) this.groupRuleList.getCellEditor(i, 0))
						.getComponent().setEnabled(false);
		}
		this.groupRuleList.addMouseListener(this.ml);
		this.scrollGroupRuleList.getViewport().setView(this.groupRuleList);
		this.groupRuleList.getSelectionModel().setSelectionMode(0);
	}
	
	public void tableChanged(TableModelEvent e) {
		if (e.getSource() == this.groupList.getModel()) {
			int indx1 = this.groupList.rowAtPoint(this.groupList.getMousePosition());
			this.selGroupIndx = -1;
			this.groupList.clearSelection();
			this.groupList.changeSelection(indx1, 0, false, false);				
		}
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

	class MyTableCellRenderer extends JLabel implements TableCellRenderer {
		ApplicabilityRuleSequence arsHandler;
		List<RuleSequence> sequences;
		
		public MyTableCellRenderer(final ApplicabilityRuleSequence arsHandler,
									final List<RuleSequence> sequences) {
			setOpaque(true);
			this.arsHandler = arsHandler;
			this.sequences = sequences;
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column)

		{
			
			setOpaque(true);
			
			Pair<Boolean, String> 
			resultPair = this.arsHandler.getApplicabilityResultOfRulesequence(row);
			
			Pair<Boolean, String> 
			result2Pair = this.arsHandler.getNonApplicabilityResultOfRulesequence(row);
			
			if (value instanceof JLabel) {			
				JLabel l = (JLabel) value;
				if (column > 0) {
					setBackground(l.getBackground());
					setText(l.getText());
					return this; 
				} 
				return new JLabel(l.getText());
			} else if (value instanceof String) {
				this.setText((String)value);
				
				if (resultPair != null) {
//					System.out.println("string:: "+resultPair.first.booleanValue()+ "   "+resultPair.second);
					if (resultPair.first.booleanValue()) {
						this.setBackground(GREEN);
						if (isSelected) {
							this.setForeground(DARK_BLUE);//white);
						} else {
							this.setForeground(Color.black);
						}
					} else if(result2Pair != null && result2Pair.first.booleanValue()) {
						this.setBackground(RED);
						if (isSelected) {
							this.setForeground(DARK_BLUE);//white);
						} else {
							this.setForeground(Color.black);
						}
					} else if (!"undefined".equals(resultPair.second)){
						this.setBackground(ORANGE);
						if (isSelected) {
							this.setForeground(DARK_BLUE);//white);
						} else {
							this.setForeground(Color.black);
						}
					} else if (isSelected) {					
						this.setBackground(SEL_COLOR);
						this.setForeground(Color.black);
					} else {
						this.setBackground(Color.white);
						this.setForeground(Color.black);
					}

				} else if (isSelected) {					
					this.setBackground(SEL_COLOR);
					this.setForeground(Color.black);
				} else {					
					this.setBackground(Color.white);
					this.setForeground(Color.black);
				}				
			}
			return this;
		}
	}
}

package agg.gui.cpa;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.border.MatteBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;

import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.ParserGUIListener;
import agg.gui.parser.event.CPAEventData;
import agg.parser.CriticalPairData;
import agg.parser.CriticalPairEvent;
import agg.parser.CriticalPair;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.util.Pair;

/**
 * Shows a table with a row and a column for each rule, so that each element
 * stands for a pair of rules. The color of the pairs shows the state of them
 * (critic, non-critic, unchecked).
 * 
 * @version $Id: CriticalPairPanel.java,v 1.17 2010/12/21 16:34:01 olga Exp $
 * @author $Author: olga $
 */
public class CriticalPairPanel extends JPanel implements ActionListener,
		ParserEventListener, ItemListener, PopupMenuListener {

	static final Font bfont = new Font("Helvetica-Bold", Font.BOLD, 14);
	
	/** the color for untested pairs */
	static final Color NOT_SET = Color.white;

	/** the color for pairs which will be checked soon */
	static final Color SCHEDULED = Color.gray;

	/** the color for pairs which are checked at the moment */
//	static final Color COMPUTING = Color.yellow;
	static final Color COMPUTING = new Color(255, 255, 160);

	/** the color for critic pairs */
//	static final Color CRITIC = Color.red;
//	static final Color CRITIC = new Color(255, 210, 160);
	static final Color CRITIC = new Color(255, 204, 204);
	
	/** the color for critic pairs */
//	static final Color DEPEND = Color.blue;	
//	static final Color DEPEND = new Color(155, 205, 255);
	static final Color DEPEND = new Color(204, 204, 255);
	static final Color DEPEND2 = Color.cyan;
	
	/** the color for non-critic pairs */
//	static final Color NOT_CRITIC = Color.green;
//	static final Color NOT_CRITIC = new Color(155, 255, 105);
	static final Color NOT_CRITIC = new Color(201, 255, 204);
	
	/** the color for disabled rules */
	static final Color DISABLED = Color.lightGray;

	/** the color for not related rules */
	static final Color NOT_RELATED = Color.lightGray;

	/** the PairContainer, which is displayed here */
	private ExcludePairContainer container;

	private Hashtable<JButton,CriticalPairData> b2cpData = new Hashtable<JButton,CriticalPairData>();
	
//	private Hashtable<Graph, Vector<Hashtable<GraphObject, GraphObject>>> computeAndCheckGraph;

	private Hashtable<Rule, Hashtable<Rule, JButton>> 
	buttons = new Hashtable<Rule, Hashtable<Rule, JButton>>();

	private Hashtable<JButton, Rule> firstRules = new Hashtable<JButton, Rule>();

	private Hashtable<JButton, Rule> secondRules = new Hashtable<JButton, Rule>();

	/** the listener for selections in the array */
	private Vector<ParserGUIListener> listeners = new Vector<ParserGUIListener>();

	private Rule first, second;

	private int tableW, tableH;

	final private JPopupMenu menu = new JPopupMenu();

	private JMenuItem miClear, miContinue, miComputeAndCheck, miVisibleRel, miVisibleRule;

	final static private String clearRelation = "Clear";

	final static private String continueCompute = "Continue Compute";
	
	final static private String computeAndCheck = "Compute & Check Host Graph";

	final static private String hideRelation = "Hide Relation ( in CPA Graph )";

	final static private String showRelation = "Show Relation ( in CPA Graph )";

	final static private String hideRule = "Hide Rule ( in CPA Graph )";

	final static private String showRule = "Show Rule ( in CPA Graph )";

	final static private javax.swing.border.Border 
	border = (new JButton()).getBorder();

	int borderWidth = 7;
	
	final private JScrollPane main = new JScrollPane();

	MouseInputAdapter ml;
	
	boolean active;

	
	/** constructs a new panel for the given rules */
	public CriticalPairPanel(final List<Rule> rules, final ExcludePairContainer container) {
		super(new BorderLayout(), true);
		
		makePanel(rules, rules, container);		
	}
	
	/** constructs a new panel for the given rules1 on horizontal
	 * and  rules2 on vertical of the rule pairs table 
	 */
	public CriticalPairPanel(final List<Rule> rules1, final List<Rule> rules2, final ExcludePairContainer container) {
		super(new BorderLayout(), true);
				
		makePanel(rules1, rules2, container);
	}
	
	
	private void makePanel(
			final List<Rule> somerules1, 
			final List<Rule> somerules2, 
			final ExcludePairContainer cpContainer) {
			
		if (somerules1 == null || somerules1.size() == 0
				|| somerules2 == null || somerules2.size() == 0)
			return;

		
		// store the container
		this.container = cpContainer;
		this.container.addPairEventListener(this);

		List<Rule> rules = getEnabledRules(somerules1);
		List<Rule> rules2 = (somerules1 == somerules2)? rules: getEnabledRules(somerules2);
		
		// the head of the rows
		// add all rule names and numbers to the head
		int tablesize2 = rules2.size();
		final JPanel rowHead = new JPanel();
		rowHead.setLayout(new GridLayout(tablesize2, 1));
		int nn = 0;
		for (int i=0; i<rules2.size(); i++) {	
			Rule r = rules2.get(i);	
			if (r.isEnabled()) {
				nn++;
				String rName = r.getName();
				if (r instanceof KernelRule) {
					rName = ((KernelRule)r).getQualifiedName();
				}
				else if (r instanceof MultiRule) {
					rName = ((MultiRule)r).getQualifiedName();
				}
				String text = String.valueOf(nn) + " " + rName;
				JLabel act = new JLabel(text);
				act.setToolTipText("first rule " + text);
				rowHead.add(act);
			}
		} 

		// the head of the columns
		// add all rule numbers to the head
		int tablesize = rules.size();
		final JPanel colHead = new JPanel();
		colHead.setLayout(new GridLayout(1, tablesize));
		nn = 0;
		boolean sameRules = rules.equals(rules2);
		for (int i=0; i<rules.size(); i++) {
			Rule r = rules.get(i);	
			if (r.isEnabled()) {
				nn++;
				String text = "  " +String.valueOf(nn);
				if (!sameRules) {
					String rName = r.getName();
					if (r instanceof KernelRule) {
						rName = ((KernelRule)r).getQualifiedName();
					}
					else if (r instanceof MultiRule) {
						rName = ((MultiRule)r).getQualifiedName();
					}
					text = String.valueOf(nn) + " " + rName;
				}
				JLabel act = new JLabel(text);
				act.setToolTipText("second rule " + text);
				colHead.add(act);
			}
		} // while col

		// create the center panel with a button for each rule
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(tablesize2, tablesize));
		int i = 0;
		int ii;

		// create button pop-up menu
		this.menu.addPopupMenuListener(this);
		this.miClear = new JMenuItem(clearRelation);
		this.menu.add(this.miClear);
		this.miClear.addActionListener(this);
		
		this.miContinue = new JMenuItem(continueCompute);
		this.menu.add(this.miContinue);
		this.miContinue.addActionListener(this);
		
		this.miComputeAndCheck = this.menu.add(new JMenuItem(computeAndCheck));
		this.miComputeAndCheck.addActionListener(this);
		if (this.container instanceof DependencyPairContainer)
			this.miComputeAndCheck.setEnabled(false);
		else
			this.miComputeAndCheck.setEnabled(true);
		this.menu.addSeparator();

		this.miVisibleRel = this.menu.add(new JMenuItem(hideRelation));
		this.miVisibleRel.addActionListener(this);
		this.miVisibleRule = new JMenuItem(hideRule);
		this.miVisibleRule.addActionListener(this);
		this.menu.add(this.miVisibleRule);

		this.ml = new MouseInputAdapter() {
//			public void mousePressed(MouseEvent e) {}		
//			public void mouseReleased(MouseEvent e) {}
			
			public void mouseEntered(MouseEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton b = (JButton)e.getSource();
					if (b.getBorder() instanceof MatteBorder) {
						if (((MatteBorder)b.getBorder()).getMatteColor() == DEPEND2) {
							b.setBackground(DEPEND2);
							b.setBorder(BorderFactory.createMatteBorder(
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, DEPEND)); 
						}
					}
				}
			}
			
			public void mouseExited(MouseEvent e) {
				if (e.getSource() instanceof JButton) {
					JButton b = (JButton)e.getSource();
					if (b.getBorder() instanceof MatteBorder) {
						if (((MatteBorder)b.getBorder()).getMatteColor() == DEPEND
								|| ((MatteBorder)b.getBorder()).getMatteColor() == DEPEND2) {
							b.setBackground(DEPEND);
							b.setBorder(BorderFactory.createMatteBorder(
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, 
									CriticalPairPanel.this.borderWidth, DEPEND2));
						}
					}
				}
			}						
		};
		addMouseListener(this.ml);
		
		while (i < tablesize2) {
			Rule r1 = rules2.get(i);
			ii = 0;
			while (ii < tablesize) {
				Rule r2 = rules.get(ii);
				JButton act = new JButton("?");
				act.setFont(CriticalPairPanel.bfont);
				act.setForeground(Color.black);  
				act.addMouseListener(this.ml);
				act.addActionListener(this);
				// set tool tip text
				String r1Name = r1.getName();
				if (r1 instanceof KernelRule) {
					r1Name = ((KernelRule)r1).getQualifiedName();
				}
				else if (r1 instanceof MultiRule) {
					r1Name = ((MultiRule)r1).getQualifiedName();
				}
				String r2Name = r2.getName();
				if (r2 instanceof KernelRule) {
					r2Name = ((KernelRule)r2).getQualifiedName();
				}
				else if (r2 instanceof MultiRule) {
					r2Name = ((MultiRule)r2).getQualifiedName();
				}				
				act.setToolTipText(
						"[" + r1Name + ", " + r2Name + "]");				
				act.setMinimumSize(
						new Dimension(act.getHeight(), act.getHeight()));
				// add popup menu
				act.setComponentPopupMenu(this.menu);

				addButton(r1, r2, act);
				mainPanel.add(act);
				refreshView(r1, r2, act, -1);
				ii++;
			}
			i++;
		} // while i

		// get the preferred size for the center panel
		Dimension dim = mainPanel.getPreferredSize();

		// calculate minimum/preferred size for column header
		Dimension dim2 = new Dimension();
		dim2.setSize(dim.getWidth(), colHead.getPreferredSize().getHeight());
		// System.out.println("col: "+dim2.getWidth()+" "+dim2.getHeight());
		colHead.setMinimumSize(dim2);
		colHead.setPreferredSize(dim2);
		this.tableW = (int) dim2.getWidth();
		// calculate minimum/preferred size for row header
		dim2 = new Dimension();
		dim2.setSize(rowHead.getPreferredSize().getWidth(), dim.getHeight());
		// System.out.println("row: "+dim2.getWidth()+" "+dim2.getHeight());
		rowHead.setPreferredSize(dim2);
		rowHead.setMinimumSize(dim2);
		this.tableH = (int) dim2.getHeight();
		// set the main panel to its actual size
		mainPanel.setPreferredSize(dim);
		mainPanel.setMinimumSize(dim);

		// construct JScrollPane
		this.main.setRowHeaderView(rowHead);
		this.main.setColumnHeaderView(colHead);
		this.main.setViewportView(mainPanel);
		this.main.setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, new JLabel(
				" first \\ second"));

		// add JScrollPane to this
		this.add(this.main, BorderLayout.CENTER);
		
	} // paneTest

	private List<Rule> getEnabledRules(List<Rule> rules) {
		List<Rule> result = new Vector<Rule>();
		for (int i=0; i<rules.size(); i++) {			
			if (rules.get(i).isEnabled()) {
				result.add(rules.get(i));
			}
		}
		return result;
	}
	
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		JButton b = (JButton) ((JPopupMenu) e.getSource()).getInvoker();
		if (!b.isEnabled() || b.getActionCommand().equals("VIEW")) {
			this.menu.removeAll();
			return;
		}
		
		this.first = this.firstRules.get(b);
		this.second = this.secondRules.get(b);
		ExcludePairContainer.Entry entry = this.container.getEntry(this.first, this.second);
		
		if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPUTABLE) {
			this.menu.removeAll();
			return;		
		}
		
		ExcludePairContainer.Entry entry1 = this.container.getEntry(this.first, this.first);
		ExcludePairContainer.Entry entry2 = this.container.getEntry(this.second, this.second);
		
		if (entry.getState() == ExcludePairContainer.Entry.DISABLED) {
			this.menu.removeAll();
			this.menu.add(new JMenuItem(this.first.getName() + " - disabled"));
			this.menu.getComponent(0).setEnabled(false);
			return;
		} else if (entry.getState() == ExcludePairContainer.Entry.NOT_RELATED) {
			this.menu.removeAll();
			this.menu.add(new JMenuItem(this.first.getName() + " - not related"));
			this.menu.getComponent(0).setEnabled(false);
			return;
		} else {
			if (this.menu.getComponents().length <= 1) {
				this.menu.removeAll();
				this.menu.add(this.miClear);
				this.menu.add(this.miContinue);
				this.menu.add(this.miVisibleRel);
				this.menu.add(this.miVisibleRule);
			}
		}

		if (entry.getState() != ExcludePairContainer.Entry.COMPUTED
				&& entry.getState() != ExcludePairContainer.Entry.COMPUTED2
				&& entry.getState() != ExcludePairContainer.Entry.COMPUTED12) {
			this.miClear.setEnabled(false);
			this.miContinue.setEnabled(false);
			this.miVisibleRel.setEnabled(false);
			this.miVisibleRule.setEnabled(false);
		} else {
			this.miClear.setEnabled(true);
			this.miContinue.setEnabled(entry.isProgressIndexSet());
			this.miVisibleRel.setEnabled(true);
			this.miVisibleRule.setEnabled(true);
		}

		if (entry.isRelationVisible())
			this.miVisibleRel.setText(hideRelation);
		else
			this.miVisibleRel.setText(showRelation);
		if (entry.isRuleVisible())
			this.miVisibleRule.setText(hideRule);
		else
			this.miVisibleRule.setText(showRule);

		if (this.first != this.second) {
			this.miVisibleRule.setEnabled(false);
			if (!entry.isCritical())
				this.miVisibleRel.setEnabled(false);
			else if (!entry1.isRuleVisible() || !entry2.isRuleVisible())
				this.miVisibleRel.setEnabled(false);
			else
				this.miVisibleRel.setEnabled(true);
		} else {
			this.miVisibleRule.setEnabled(true);
			if (!entry.isCritical())
				this.miVisibleRel.setEnabled(false);
			else
				this.miVisibleRel.setEnabled(true);
		}
	}

	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	public void popupMenuCanceled(PopupMenuEvent e) {
	}

	public Container getMainContainer() {
		return this.main;
	}

	public ExcludePairContainer getPairContainer() {
		return this.container;
	}

	/** changes the PairContainer displayed */
	public void setPairContainer(ExcludePairContainer container) {
		this.container = container;
		refreshView();
	}// setPairContainer

	public boolean isActive() {
		return this.active;
	}

	public boolean isEmpty() {
		return this.container.isEmpty();
	}

	/** Returns CriticalPair.CONFLICT or CriticalPair.DEPENDENCY */
	public int getKindOfPairContainer() {
		return this.container.getKindOfConflict();
	}

	public void itemStateChanged(ItemEvent e) {
		// System.out.println("itemStateChanged... "+e.getSource());
		// if(e.getSource() instanceof JMenuItem){}
	}

	public int getTableWidth() {
		return this.tableW;
	}

	public int getTableHeight() {
		return this.tableH;
	}

	/**
	 * Gets called, if a button is pressed
	 */
	synchronized public void actionPerformed(ActionEvent e) {
		// System.out.println("CriticalPairPanel.actionPerformed:: "+e.getSource());
		Object source = e.getSource();
		if (e.getSource() instanceof JMenuItem) {
			if ((this.first != null) && (this.second != null)) {
				ExcludePairContainer.Entry entry = this.container.getEntry(this.first,
						this.second);
//				System.out.println(">>> CriticalPairPanel.actionPerformed::  entry.state: "+entry.getState());
				if (entry.getState() == ExcludePairContainer.Entry.COMPUTED
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED2
						|| entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
					if (((JMenuItem) e.getSource()).getText().equals(
							clearRelation)) {
						this.container.clearEntry(this.first, this.second);
						this.container.enableUseHostGraph(false, null);
						JButton b = (JButton) ((Hashtable) this.buttons.get(this.first))
								.get(this.second);
						b.setBackground(NOT_SET);
						if (b.getBorder() instanceof MatteBorder) {						
							b.setBorder(BorderFactory.createMatteBorder(
									this.borderWidth, this.borderWidth, 
									this.borderWidth, this.borderWidth, NOT_SET)); 
						}
						b.setText("?");						
						clearRulePair(this.first, this.second);						
//						setRuleContextVisible(first, second, false);
//						setRuleVisible(first, second, false);
						fireParserGUIEvent(null);
					} else if (((JMenuItem) e.getSource()).getText().equals(
							continueCompute)) {						
						if (entry.isProgressIndexSet()) {
							if (this.b2cpData.get(e.getSource()) == null) {							
								Pair<Rule, Rule> pair = new Pair<Rule, Rule>(this.first, this.second);
								fireParserGUIEvent(pair, CriticalPairEvent.CONTINUE_COMPUTE);									
							} else {
								CriticalPairData data = this.b2cpData.get(e.getSource());
								fireParserGUIEvent(data, CriticalPairEvent.CONTINUE_COMPUTE);	
							}
						}
					} else if (((JMenuItem) e.getSource()).getText().equals(
							computeAndCheck)) {
						this.container.enableUseHostGraph(true, this.container.getGrammar()
								.getGraph());
						Pair<Rule, Rule> pair = new Pair<Rule, Rule>(this.first, this.second);
						fireParserGUIEvent(pair);
					} else if (((JMenuItem) e.getSource()).getText().equals(
							hideRelation)) {
						if (entry.isCritical()) {
							setRelationVisible(this.first, this.second, false);
							refreshView();
						}
					} else if (((JMenuItem) e.getSource()).getText().equals(
							showRelation)) {
						if (entry.isCritical()) {
							if (this.container.getEntry(this.first, this.first)
									.isRuleVisible()
									&& this.container.getEntry(this.second, this.second)
											.isRuleVisible()) {
								setRelationVisible(this.first, this.second, true);
								refreshView();
							}
						}
					} else if (((JMenuItem) e.getSource()).getText().equals(
							hideRule)) {
						setRuleContextVisible(this.first, this.second, false);
						setRuleVisible(this.first, this.second, false);
						refreshView();
					} else if (((JMenuItem) e.getSource()).getText().equals(
							showRule)) {
						setRuleContextVisible(this.first, this.second, true);
						setRuleVisible(this.first, this.second, true);
						refreshView();
					}
				} else if (((JMenuItem) e.getSource()).getText().equals(
						computeAndCheck)) {
					this.container.enableUseHostGraph(true, this.container.getGrammar()
							.getGraph());
					Pair<Rule, Rule> pair = new Pair<Rule, Rule>(this.first, this.second);
					fireParserGUIEvent(pair);
				} else if (((JMenuItem) e.getSource()).getText().equals(
						hideRule)) {
					setRuleContextVisible(this.first, this.second, false);
					setRuleVisible(this.first, this.second, false);
					refreshView();
				} else if (((JMenuItem) e.getSource()).getText().equals(
						showRule)) {
					setRuleContextVisible(this.first, this.second, true);
					setRuleVisible(this.first, this.second, true);
					refreshView();
				}
			}
		} 
		else if (e.getSource() instanceof JButton) {
			this.first = this.firstRules.get(source);
			this.second = this.secondRules.get(source);
			if ((this.first != null) && (this.second != null)) {
				if (this.b2cpData.get(e.getSource()) == null) {
					ExcludePairContainer.Entry entry = this.container.getEntry(this.first,
							this.second);
					if (entry.getState() != ExcludePairContainer.Entry.DISABLED
							&& entry.getState() != ExcludePairContainer.Entry.NOT_RELATED
							&& entry.getState() != ExcludePairContainer.Entry.COMPUTING_IS_RUNNING) {
						Pair<Rule, Rule> pair = new Pair<Rule, Rule>(this.first, this.second);
						/*
						if (entry.getState() == ExcludePairContainer.Entry.NOT_SET) {
							String what = "";
							if (this.first.hasEnabledACs(false))
								what = "The first rule: < ".concat(this.first.getName()).concat(" > ");
							else if (this.second.hasEnabledACs(false))
								what = "The second rule: < ".concat(this.second.getName()).concat(" > ");
							if (!what.isEmpty()) {
								JOptionPane.showMessageDialog(null,
										"Computation result of this critical pair may be incomplete! \n"
										+what+"\nmakes use of General Application Conditions.\n" 
										+"Unfortunately, critical pair analysis does not take GACs in account"
										+"\n(not jet implemented).\n", 
										"CPA", 
										JOptionPane.WARNING_MESSAGE);
							}
						}*/
						fireParserGUIEvent(pair);
					}
				} else {
					CriticalPairData data = this.b2cpData.get(e.getSource());
					fireParserGUIEvent(data);
				}
			}
		}
	} // actionPerformed

	private void clearRulePair(Rule rule1, Rule rule2) {
		if (this.container.getKindOfConflict() == CriticalPair.CONFLICT) {
			fireParserGUIEvent(new CriticalPairEvent(this.container, rule1, rule2,
					CriticalPairEvent.REMOVE_RELATION_ENTRY));
//			fireParserGUIEvent(new CPAEventData(rule1, rule2,
//					CPAEventData.HIDE_RELATION, "C", false));
		}
		else {
			fireParserGUIEvent(new CriticalPairEvent(this.container, rule1, rule2,
					CriticalPairEvent.REMOVE_RELATION_ENTRY));
//			fireParserGUIEvent(new CPAEventData(rule1, rule2,
//					CPAEventData.HIDE_RELATION, "D", false));
		}
	}
	
	private void setRuleVisible(Rule rule1, Rule rule2, boolean vis) {
		JButton b = (JButton) ((Hashtable) this.buttons.get(rule1)).get(rule2);
		if (!vis) {
			b.setForeground(Color.darkGray);
			b.setToolTipText(b.getToolTipText() + ":HIDDEN");
		} else {
			b.setForeground(Color.white);
			b.setToolTipText("[" + rule1.getName() + ", " + rule2.getName()
					+ "]");
		}

		if (this.container.getKindOfConflict() == CriticalPair.CONFLICT)
			fireParserGUIEvent(new CPAEventData(rule1, rule2,
					CPAEventData.SHOW_RULE, "C", vis));
		else
			fireParserGUIEvent(new CPAEventData(rule1, rule2,
					CPAEventData.SHOW_RULE, "D", vis));
		this.container.setEntryRuleVisible(rule1, rule2, vis, true, false);
	}

	private void setRuleContextVisible(Rule rule1, Rule rule2, boolean vis) {
		for (Enumeration<Rule> keys = this.container.getExcludeContainer().keys(); keys
				.hasMoreElements();) {
			Rule r1 = keys.nextElement();
			if (r1 == rule1) {
				// System.out.println("ExcludePC:: reduce: "+r1.getName());
				Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> 
				secondPart = this.container
						.getExcludeContainer().get(r1);
				for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
					Rule r2 = k2.nextElement();
//					ExcludePairContainer.Entry entry = container.getEntry(r1,r2);
					// if(entry.isCritical())
					setRelationVisible(r1, r2, vis);
				}
				for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
					Rule r2 = k2.nextElement();
					// if(r2 != rule1)
					{
//						ExcludePairContainer.Entry entry = container.getEntry(r2, r1);
						// if(entry.isCritical())
						setRelationVisible(r2, r1, vis);
					}
				}
				break;
			}
		}
	}

	private void setRelationVisible(Rule rule1, Rule rule2, boolean vis) {
		JButton b = (JButton) ((Hashtable) this.buttons.get(rule1)).get(rule2);
		if (b != null) {
			if (!vis) {
				b.setForeground(Color.darkGray);
				b.setToolTipText(b.getToolTipText() + ":HIDDEN");
			} else {
				b.setForeground(Color.white);
				b.setToolTipText("[" + rule1.getName() + ", " + rule2.getName()
						+ "]");
			}
	
			if (this.container.getKindOfConflict() == CriticalPair.CONFLICT)
				fireParserGUIEvent(new CPAEventData(rule1, rule2,
						CPAEventData.SHOW_RELATION, "C", vis));
			else
				fireParserGUIEvent(new CPAEventData(rule1, rule2,
						CPAEventData.SHOW_RELATION, "D", vis));
			this.container.setEntryRelationVisible(rule1, rule2, vis, true);
		}
	}

	/**
	 * Register here a new listener to receive events.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void addParserGUIListener(ParserGUIListener listener) {
		this.listeners.addElement(listener);
	}

	/**
	 * Remove a listener here and stop getting messages.
	 * 
	 * @param listener
	 *            The listener.
	 */
	public void removeParserGUIListener(ParserGUIListener listener) {
		this.listeners.removeElement(listener);
	}// removeParserGUIListener

	/** fire a parser gui event */
	private void fireParserGUIEvent(Object data) {
		ParserGUIEvent event = new ParserGUIEvent(this, data);
		for (int i = 0; i < this.listeners.size(); i++) {
			ParserGUIListener l = this.listeners.elementAt(i);
			l.occured(event);
		}
	}

	private void fireParserGUIEvent(Object data, int msg) {
		ParserGUIEvent event = new ParserGUIEvent(this, data, msg);
		for (int i = 0; i < this.listeners.size(); i++) {
			ParserGUIListener l = this.listeners.elementAt(i);
			l.occured(event);
		}
	}
	
	/**
	 * adds the button to the internal structure, so it can be addressed for
	 * relabeling.
	 */
	void addButton(Rule r1, Rule r2, JButton button) {
		// create buttons-Hashtable
		Hashtable<Rule, JButton> hash1 = this.buttons.get(r1);
		if (hash1 == null) {
			hash1 = new Hashtable<Rule, JButton>();
			this.buttons.put(r1, hash1);
		}
		hash1.put(r2, button);
		// save for reverse search
		this.firstRules.put(button, r1);
		this.secondRules.put(button, r2);
	} // addButton

	/** returns the button for the given rule pair (r1,r2) */
	JButton getButton(Rule r1, Rule r2) {
		Hashtable<Rule, JButton> hash1 = this.buttons.get(r1);
		if (hash1 == null) {
			return null;
		}
		return hash1.get(r2);
	} // getButton
	
	public void showCriticalPairsOfKind(String kind) {
		this.b2cpData.clear();
		clearView();
		List<CriticalPairData>  list = this.container.getCriticalPairDataOfKind(kind);
		for (int i=0; i<list.size(); i++) {
			CriticalPairData data = list.get(i);
			JButton b = getButton(data.getRule1(), data.getRule2());
			b.setActionCommand("VIEW");
			this.b2cpData.put(b, data);
			this.refreshView(data, b);
		}
	}

	/** renews the button for the given rule pair (r1, r2). */
	void refreshView(CriticalPairData pairData, JButton button) {
		if (button == null)
			return;	
		
		Rule r1 = pairData.getRule1();
		Rule r2 = pairData.getRule2();
		
		// gets the entry holding the informations of this pair
		ExcludePairContainer.Entry entry = this.container.getEntry(r1, r2);
		if (entry != null) {		
			if (entry.getState() == ExcludePairContainer.Entry.COMPUTED
					|| entry.getState() == ExcludePairContainer.Entry.COMPUTED2
					|| entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {				
				if (entry.isCritical()) {
					if (entry.getOverlapping() != null) {
						button.setText("" + pairData.getCriticalsOfKind(-1).size());				
						// and is critic, so show number of overlappings
						if (this.container.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY
								|| this.container.getKindOfConflict() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
							if (entry.getState() == ExcludePairContainer.Entry.COMPUTED) {
								button.setBackground(DEPEND);
							} else if (entry.getState() == ExcludePairContainer.Entry.COMPUTED2) {
								button.setBackground(DEPEND2);
							} else if (entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
								button.setBackground(DEPEND);
								button.setBorder(BorderFactory.createMatteBorder(
										this.borderWidth, this.borderWidth, 
										this.borderWidth, this.borderWidth, DEPEND2));                              
							}
						}
						else if (this.container.getKindOfConflict() == CriticalPair.CONFLICT) {
							button.setBackground(CRITIC);
						}
						button.setEnabled(true);
					}
				} 
			}
		}	
	}
	
	private void clearView() {
		Enumeration<Rule> en1 = this.buttons.keys();
		while (en1.hasMoreElements()) {
			Rule r1 = en1.nextElement();
			
			Enumeration<Rule> en2 = this.buttons.get(r1).keys();
			while (en2.hasMoreElements()) {
				Rule r2 = en2.nextElement();
				JButton btn = this.getButton(r1, r2);
				clearButtonView(r1, r2, btn);
			}
		}
	}
	
	private void clearButtonView(Rule r1, Rule r2, JButton button) {
		if (button == null)
			return;	
		
		if (!button.getText().equals("?")) {
			button.setBackground(NOT_CRITIC);
			button.setText("0");
			button.setToolTipText("");
			button.setEnabled(false);
		}
	}
	
	/** force the panel to update all buttons */
	public void refreshView() {	
		this.b2cpData.clear();
		Enumeration<Rule> en1 = this.buttons.keys();
		while (en1.hasMoreElements()) {
			Rule r1 = en1.nextElement();
			
			Enumeration<Rule> en2 = this.buttons.get(r1).keys();
			while (en2.hasMoreElements()) {
				Rule r2 = en2.nextElement();
				refreshView(r1, r2, getButton(r1, r2), -1);
			}
		}
	}
	
	/** renews the button for the given rule pair (r1, r2). */
	void refreshView(Rule r1, Rule r2, int key) {
		// the button for the pair
		refreshView(r1, r2, getButton(r1, r2), key);
	} // refreshView

	
	/** renews the button for the given rule pair (r1, r2). */
	void refreshView(Rule r1, Rule r2, JButton button, int key) {
		if (button == null)
			return;	
		
		button.setEnabled(true);
		button.setActionCommand("");
		// gets the entry holding the informations of this pair
		ExcludePairContainer.Entry entry = this.container.getEntry(r1, r2);
		ExcludePairContainer.Entry entry1 = this.container.getEntry(r1, r1);
		ExcludePairContainer.Entry entry2 = this.container.getEntry(r2, r2);
		
//		System.out.println("key: "+key+"  entry.getState(): "+entry.getState());
		
		// the given pair was not tested yet
		if (entry.getState() == ExcludePairContainer.Entry.NOT_SET) {
			button.setBackground(NOT_SET);
			button.setText("?");
			button.setForeground(Color.black);
			if (button.getBorder() instanceof MatteBorder) {
				button.setBorder(border);
			}
		}
		// the pair will be checked soon
		else if (entry.getState() == ExcludePairContainer.Entry.SCHEDULED_FOR_COMPUTING) {
			button.setBackground(NOT_SET);
			button.setText("?");
			if (button.getBorder() instanceof MatteBorder) {
				button.setBorder(border);
			}
		}
		// the calculation is running
		else if (entry.getState() == ExcludePairContainer.Entry.COMPUTING_IS_RUNNING) {
			button.setBackground(COMPUTING);
			button.setText("C");
			if (button.getBorder() instanceof MatteBorder) {
				button.setBorder(border);
			}
		}
		// the pair was checked
		else if (entry.getState() == ExcludePairContainer.Entry.COMPUTED
				|| entry.getState() == ExcludePairContainer.Entry.COMPUTED2
				|| entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
			if (entry.isCritical()) {
				// and is critic, so show number of overlappings
				if (this.container.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY
						|| this.container.getKindOfConflict() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
					if (entry.getState() == ExcludePairContainer.Entry.COMPUTED) {
						button.setBackground(DEPEND);
					} else if (entry.getState() == ExcludePairContainer.Entry.COMPUTED2) {
						button.setBackground(DEPEND2);
					} else if (entry.getState() == ExcludePairContainer.Entry.COMPUTED12) {
						button.setBackground(DEPEND);
						button.setBorder(BorderFactory.createMatteBorder(
								this.borderWidth, this.borderWidth, 
								this.borderWidth, this.borderWidth, DEPEND2));                              
					} 
				}
				else if (this.container.getKindOfConflict() == CriticalPair.CONFLICT) {
					button.setBackground(CRITIC);
				}
				
				if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPLETE_COMPUTABLE) {
					button.setBorder(BorderFactory.createMatteBorder(
							3, 3, 3, 3, Color.BLACK));
				}
				
				if (entry.getOverlapping() != null)
					button.setText("" + entry.getOverlapping().size());
				else
					button.setText("!");

				if (!entry.isRuleVisible() || !entry.isRelationVisible()) {
					button.setForeground(Color.white);
					if (button.getToolTipText().indexOf("HIDDEN") == -1)
						button.setToolTipText(button.getToolTipText()
								+ ":HIDDEN");
				} else if ((entry1 != null && !entry1.isRuleVisible())
						|| (entry2 != null && !entry2.isRuleVisible())) {
					button.setForeground(Color.white);
					if (button.getToolTipText().indexOf("HIDDEN") == -1)
						button.setToolTipText(button.getToolTipText()
								+ ":HIDDEN");
				} else {
					button.setForeground(Color.black);
					button.setToolTipText("[" + r1.getName() + ", "
							+ r2.getName() + "]");
				}
				
				if (key == CriticalPairEvent.UNCRITICAL
						|| entry.getStatus() == ExcludePairContainer.Entry.NON_RELEVANT) 
					button.setEnabled(false);
				else if (entry.getState() != ExcludePairContainer.Entry.DISABLED)
					button.setEnabled(true);
								
			} else {
				// or it is non-critic, so show  0
				button.setBackground(NOT_CRITIC);
				if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPLETE_COMPUTABLE) {
					button.setBorder(BorderFactory.createMatteBorder(
							3, 3, 3, 3, Color.BLACK));
				}
				button.setText("0");
				if (!entry.isRuleVisible() || !entry.isRelationVisible()) {
					button.setForeground(Color.white);
					if (button.getToolTipText().indexOf("HIDDEN") == -1)
						button.setToolTipText(button.getToolTipText()
								+ ":HIDDEN");
				} else if ((entry1 != null && !entry1.isRuleVisible())
						|| (entry2 != null && !entry2.isRuleVisible())) {
					button.setForeground(Color.white);
					if (button.getToolTipText().indexOf("HIDDEN") == -1)
						button.setToolTipText(button.getToolTipText()
								+ ":HIDDEN");
				} else {
					button.setForeground(Color.black);
					button.setToolTipText("[" + r1.getName() + ", "
							+ r2.getName() + "]");
				}
			}

			if (key == CriticalPairEvent.NON_RELEVANT
					|| entry.getStatus() == ExcludePairContainer.Entry.NON_RELEVANT)
				button.setEnabled(false);
			else if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPUTABLE) {
				button.setToolTipText("[" + r1.getName() + ", "
						+ r2.getName() + "]"+" - not computed");
				button.setBackground(Color.LIGHT_GRAY);
				button.setText("");
			}
			else if (entry.getState() != ExcludePairContainer.Entry.DISABLED) 
				button.setEnabled(true);
			
		} else if (entry.getState() == ExcludePairContainer.Entry.DISABLED) {
			if (!r1.isEnabled() || !r2.isEnabled()) {
				String s = r1.getName() + ": DISABLED";
				if (!r1.isEnabled() && !r2.isEnabled())
					s = "DISABLED";
				else if (!r2.isEnabled())
					s = r2.getName() + ": DISABLED";
				button.setBackground(DISABLED);
				button.setText("");
				button.setToolTipText(s);
			} else {
				entry.setState(ExcludePairContainer.Entry.NOT_SET);
				this.container.clearEntry(r1, r2);
				button.setBackground(NOT_SET);
				button.setText("?");
				button.setForeground(Color.black);
			}
		} else if (entry.getState() == ExcludePairContainer.Entry.NOT_RELATED) {
			button.setBackground(NOT_RELATED);
			button.setText("");
		} else if (entry.getState() == ExcludePairContainer.Entry.NON_RELEVANT) {
			button.setEnabled(false);			
		}
		
	} // refreshView

	/**
	 * gets called if something changed in the critical pair container so the
	 * display must be updated.
	 */
	public void parserEventOccured(ParserEvent p) {
		if (p instanceof CriticalPairEvent) {
			// System.out.println("CriticalPairPanel.parserEventOccured ->
			// CriticalPairEvent: "+p.getSource());
			if (((CriticalPairEvent) p).getFirstRule() == null
					|| ((CriticalPairEvent) p).getSecondRule() == null)
				return;
			Rule r1 = ((CriticalPairEvent) p).getFirstRule();
			Rule r2 = ((CriticalPairEvent) p).getSecondRule();
			refreshView(r1, r2, ((CriticalPairEvent) p).getKey());
		}
	} // parserEventOccured
} // class CriticalPairPanel

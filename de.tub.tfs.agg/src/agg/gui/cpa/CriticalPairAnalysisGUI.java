package agg.gui.cpa;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import agg.editor.impl.EdGraGra;
import agg.gui.options.ParserGUIOption;
import agg.gui.parser.event.CPAEventData;
import agg.gui.parser.event.GUIOptionEvent;
import agg.gui.parser.event.GUIOptionListener;
import agg.gui.parser.event.ParserGUIEvent;
import agg.gui.parser.event.ParserGUIListener;
import agg.gui.parser.event.StatusMessageEvent;
import agg.gui.parser.event.StatusMessageListener;
import agg.parser.CriticalPair;
import agg.parser.CriticalPairData;
import agg.parser.CriticalPairEvent;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.parser.InvalidAlgorithmException;
import agg.parser.LayeredDependencyPairContainer;
import agg.parser.LayeredExcludePairContainer;
import agg.parser.PairContainer;
import agg.util.Pair;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
//import javax.swing.JScrollPane;

/**
 * Holds the whole GUI for the critical pair analysis
 * 
 * @version $Id: CriticalPairAnalysisGUI.java,v 1.13 2006/12/13 13:33:05 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class CriticalPairAnalysisGUI implements ParserGUIListener,
		ActionListener, GUIOptionListener {

	/**
	 * The text for critical pairs
	 */
	public static final String CRITICALPAIRS = "Critical Pairs";

	/**
	 * The text for dependency pairs
	 */
	public static final String CINFLICTSPAIRS = "Minimal Conflicts";

	/**
	 * The text for dependency pairs
	 */
	public static final String DEPENDENCYPAIRS = "Minimal Dependencies";

	/**
	 * the text for the parser
	 */
	public static final String PARSER = "Parser";

	/**
	 * the load text
	 */
	public static final String LOAD = "Load Pairs";

	/**
	 * the save text
	 */
	public static final String SAVE = "Save Pairs";

	/**
	 * the exclude text
	 */
	public static final String EXCLUDE = "Exclude";

	/**
	 * the before text
	 */
	public static final String BEFORE = "Before";

	/**
	 * the pane for rules and critical pairs
	 */
	JSplitPane mainPane;

	/**
	 * this pane holds the two tree views
	 */
	JSplitPane treePane;

	/**
	 * this pane holds the the graphs
	 */
	JSplitPane graphPane;

	/**
	 * the grammar which belongs to the current critical pairs
	 */
	GraGra grammar;

	GraphDesktop gDesktop;

	Rule links, rechts;

	/**
	 * the tables of conflicts and dependencies which show progress of critical pairs 
	 */
	CriticalPairPanel pairPanel, pairPanel2; 

	boolean isPanel2 = false;

	Thread threadCP;

	boolean threadCPisAlive;

	Vector<StatusMessageListener> listener;

	EdGraGra layout;

	PairContainer beo, beo2;

	ParserGUIOption option;

	IntNumberDialog fromToDialog;
	
	
	/**
	 * the default constructor with the option to configure with
	 * 
	 * @param option
	 *            the option to configure the GUI
	 */
	public CriticalPairAnalysisGUI(JFrame applFrame, ParserGUIOption option) {
		this(applFrame, null, null, option);
	}

	/**
	 * the main constructor
	 * 
	 * @param gragra
	 *            the grammar the critical pairs belong to
	 * @param layout
	 *            the layout for the graph
	 * @param option
	 *            the option for the GUI
	 */
	public CriticalPairAnalysisGUI(JFrame applFrame, GraGra gragra, EdGraGra layout,
			ParserGUIOption option) {
		setGrammar(gragra);

		this.gDesktop = new GraphDesktop(applFrame, layout, option);
		this.gDesktop.addParserGUIListener(this);

		setLayout(layout);

		this.option = option;
		if (option != null)
			option.addOptionListener(this);

		this.listener = new Vector<StatusMessageListener>();
		if (option != null)
			this.gDesktop.setOverlappingGraphWindowSize(option
					.getCriticalPairWindowSize());
		else
			this.gDesktop.setOverlappingGraphWindowSize(new Dimension(250, 200));

		this.graphPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.graphPane.setOneTouchExpandable(false); // true);
		this.graphPane.setTopComponent(null); // gBrowser.getComponent());
		this.graphPane.setBottomComponent(this.gDesktop.getComponent());
		this.graphPane.setDividerLocation(0);

		this.mainPane = new JSplitPane();
		this.mainPane.setOneTouchExpandable(false);
		this.mainPane.setLeftComponent(null);
		this.mainPane.setRightComponent(this.graphPane);
		this.mainPane.setDividerLocation(0);
		this.mainPane.revalidate();
		
		fromToDialog = new IntNumberDialog(null); //applFrame);
	}

	public void addMouseListener(MouseListener ml) {
		this.treePane.addMouseListener(ml);
		this.gDesktop.getComponent().addMouseListener(ml);
		if (this.pairPanel != null)
			this.pairPanel.getMainContainer().addMouseListener(ml);
		if (this.pairPanel2 != null)
			this.pairPanel.getMainContainer().addMouseListener(ml);
	}

	private void setGrammar(GraGra gragra) {
		this.grammar = gragra;
	}

	/**
	 * sets the critical pairs which will be displayed
	 * 
	 * @param pairs
	 *            the pairs to display
	 */
	public void setCriticalPairs(PairContainer pairs) {
		if (pairs == null
				|| pairs.getGrammar().getListOfRules().isEmpty()) {
			return;
		}
		
		if (pairs.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY
				|| pairs.getKindOfConflict() == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
			setCriticalPairs2(pairs);
			return;
		}

		this.beo = pairs;
		if (this.pairPanel == null) {
			String cpTableName = "Minimal Conflicts";
			if (this.beo instanceof LayeredExcludePairContainer) {
				this.pairPanel = new CriticalPairPanel(
						this.beo.getRules(), this.beo.getRules2(),
						(LayeredExcludePairContainer) this.beo);
			} else {
				this.pairPanel = new CriticalPairPanel(
						this.beo.getRules(), this.beo.getRules2(),
						(ExcludePairContainer) this.beo);
			}
			this.gDesktop.addCriticalPairTable(this.pairPanel, cpTableName);
			this.mainPane.revalidate();
			this.mainPane.repaint();
			this.pairPanel.addParserGUIListener(this);
			((ExcludePairContainer) this.beo).addPairEventListener(this.pairPanel);
		} else {
			if (this.beo instanceof LayeredExcludePairContainer) {
				this.pairPanel.setPairContainer((LayeredExcludePairContainer) this.beo);
			} else {
				this.pairPanel.setPairContainer((ExcludePairContainer)this. beo);
			}
			this.gDesktop.removeAllGraphFrames();
			this.gDesktop.removeRuleFrames();
			this.mainPane.revalidate();
			this.mainPane.repaint();
			((ExcludePairContainer)this. beo).addPairEventListener(this.pairPanel);
		}
		fireStatusMessageEvent(new StatusMessageEvent(this, ""));
	}

	private void setCriticalPairs2(PairContainer pairs) {
		if ((pairs == null) 
				|| (pairs.getGrammar().getListOfRules().isEmpty())) {
			return;
		}

		this.beo2 = pairs;
		if (this.pairPanel2 == null) {
			String cpTableName = "Minimal Dependencies";
			if (this.beo2 instanceof LayeredDependencyPairContainer) {
				this.pairPanel2 = new CriticalPairPanel(
						this.beo2.getRules(), this.beo2.getRules2(),
						(LayeredDependencyPairContainer) this.beo2);
			} else if (this.beo2 instanceof DependencyPairContainer) {
				this.pairPanel2 = new CriticalPairPanel(
						this.beo2.getRules(), this.beo2.getRules2(),
						(DependencyPairContainer) this.beo2);
			}
			this.gDesktop.addCriticalPairTable(this.pairPanel2, cpTableName);
			this.mainPane.revalidate();
			this.mainPane.repaint();
			this.pairPanel2.addParserGUIListener(this);
			((ExcludePairContainer)this. beo2).addPairEventListener(this.pairPanel2);
		} else {
			if (this.beo2 instanceof LayeredDependencyPairContainer) {
				this.pairPanel2
						.setPairContainer((LayeredDependencyPairContainer) this.beo2);
			} else if (this.beo instanceof DependencyPairContainer) {
				this.pairPanel2.setPairContainer((DependencyPairContainer) this.beo2);
			}
			this.gDesktop.removeAllGraphFrames();
			this.gDesktop.removeRuleFrames();
			this.mainPane.revalidate();
			this.mainPane.repaint();
			((ExcludePairContainer) this.beo2).addPairEventListener(this.pairPanel2);
		}
		fireStatusMessageEvent(new StatusMessageEvent(this, ""));
	}

	/**
	 * gets the critical pairs
	 * 
	 * @return the critical pairs which are displayed
	 */
	public PairContainer getCriticalPairs() {
		return this.beo;
	}

	public PairContainer getCriticalPairs2() {
		return this.beo2;
	}

	public PairContainer getCriticalPairs(int kindOfConflict) {
		if (kindOfConflict == CriticalPair.CONFLICT)
			return this.beo;
		else if (kindOfConflict == CriticalPair.TRIGGER_DEPENDENCY)
			return this.beo2;
		else if (kindOfConflict == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
			return this.beo2;
		else
			return null;
	}

	private void setLayout(EdGraGra edgragra) {
		this.layout = edgragra;
		this.gDesktop.setLayout(this.layout);
	}

	public boolean isEmpty() {
		return (this.beo == null || this.beo.isEmpty())
				&& (this.beo2 == null || this.beo2.isEmpty());
	}
	
	/**
	 * get the container which contains the gui
	 * 
	 * @return the gui
	 */
	public Container getContainer() {
		return this.mainPane;
	}

	public CriticalPairPanel getCriticalPairPanel() {
		return this.pairPanel;
	}

	public CriticalPairPanel getCriticalPairPanel2() {
		return this.pairPanel2;
	}

	public CriticalPairPanel getCriticalPairPanel(int kind) {
		if (kind == CriticalPair.CONFLICT)
			return this.pairPanel;
		else if (kind == CriticalPair.TRIGGER_DEPENDENCY)
			return this.pairPanel2;
		else if (kind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
			return this.pairPanel2;
		else
			return null;
	}

	public void reinitGraphDesktop() {
		if (this.beo != null)
			((ExcludePairContainer) this.beo).removePairEventListener(this.pairPanel);
		if (this.beo2 != null)
			((ExcludePairContainer) this.beo2).removePairEventListener(this.pairPanel2);
		this.gDesktop.reinitComponents();
		this.pairPanel = null;
		this.beo = null;
		this.pairPanel2 = null;
		this.beo2 = null;
		this.gDesktop.getDesktop().repaint();
		this.showGACsWarn = true;
	}

	public GraphDesktop getGraphDesktop() {
		return this.gDesktop;
	}

	public void update() {
		this.gDesktop.refresh();
		this.mainPane.revalidate();
		this.mainPane.repaint();
	}

	/**
	 * set the grammar with layout
	 * 
	 * @param edgragra
	 *            the grammar
	 * @return error string if setting has failed
	 */
	public String setGraGra(EdGraGra edgragra) {//		boolean changed = false;
		if (edgragra == null) {
			reinitGraphDesktop();
			this.grammar = null;
			this.layout = null;
			this.links = null;
			this.rechts = null;
		} else  {
			if (this.grammar == null || edgragra.getBasisGraGra() != this.grammar) {
				reinitGraphDesktop();
				setGrammar(edgragra.getBasisGraGra());
				if (this.pairPanel != null) {
					((ExcludePairContainer) this.beo)
							.removePairEventListener(this.pairPanel);
					this.gDesktop.removePairPanelFrame(this.pairPanel);
					this.pairPanel = null;
					this.beo = null;
				}
				if (this.pairPanel2 != null) {
					((ExcludePairContainer) this.beo2)
							.removePairEventListener(this.pairPanel2);
					this.gDesktop.removePairPanelFrame(this.pairPanel2);
					this.pairPanel2 = null;
					this.beo2 = null;
				}
			}
			setLayout(edgragra);

			this.links = null;
			this.rechts = null;
			
			this.gDesktop.removeAllGraphFrames();
			this.gDesktop.removeRuleFrames();
		}
		
		return "";
	}

	/**
	 * get the grammar with layout
	 */
	public EdGraGra getGraGra() {
		return this.layout;
	}

	/**
	 * Sets the GUI options for display settings.
	 * 
	 * @param option
	 *            The GUI options for display settings.
	 */
	public void setGUIOption(ParserGUIOption opt) {
		this.option = opt;
		this.gDesktop.setGUIOption(opt);
		this.option.addOptionListener(this);
	}

	/**
	 * creates a menu of this gui
	 * 
	 * @return put this menu in a menu bar
	 */
	public JMenu createMenu() {
		JMenu m = new JMenu("Parse");

		m.add(new JCheckBoxMenuItem(CRITICALPAIRS, false));
		m.add(new JMenuItem(PARSER));
		m.addSeparator();
		JMenuItem load = new JMenuItem(LOAD);
		JMenuItem save = new JMenuItem(SAVE);

		load.setEnabled(false);
		save.setEnabled(false);
		m.add(load);
		m.add(save);
		m.addSeparator();
		JMenu beforeExcludeList = new JMenu("Mode");

		m.add(beforeExcludeList);
		JCheckBoxMenuItem exclude = new JCheckBoxMenuItem(EXCLUDE, true);

		exclude.addActionListener(this);
		JCheckBoxMenuItem before = new JCheckBoxMenuItem(BEFORE, false);

		before.addActionListener(this);
		beforeExcludeList.add(exclude);
		beforeExcludeList.add(before);
		return m;
	}

	// Implementing ParserGUIListeners
	
	void getRulesByEvent(final ParserGUIEvent pguie) {
		if (pguie.getSource() == this.pairPanel) {
			this.gDesktop.setIconOfCPAGraph(true);
			this.gDesktop.setIconOfRules(true);
			
			if (pguie.getData() instanceof Pair) {
				if ((((Pair<?,?>) pguie.getData()).first instanceof Rule)
						&& (((Pair<?,?>) pguie.getData()).second instanceof Rule)) {
					this.isPanel2 = false;
					this.links = (Rule) ((Pair<?,?>) pguie.getData()).first;
					this.rechts = (Rule) ((Pair<?,?>) pguie.getData()).second;
				}
			} else if (pguie.getData() instanceof CriticalPairData) {
					this.isPanel2 = false;
					this.links = ((CriticalPairData) pguie.getData()).getRule1();
					this.rechts = ((CriticalPairData) pguie.getData()).getRule2();
			}
		} else if (pguie.getSource() == this.pairPanel2) {
			this.gDesktop.setIconOfCPAGraph(true);
			this.gDesktop.setIconOfRules(true);
			if (pguie.getData() instanceof Pair) {
				if ((((Pair<?,?>) pguie.getData()).first instanceof Rule)
						&& (((Pair<?,?>) pguie.getData()).second instanceof Rule)) {
					this.isPanel2 = true;
					this.links = (Rule) ((Pair<?,?>) pguie.getData()).first;
					this.rechts = (Rule) ((Pair<?,?>) pguie.getData()).second;
				}
			} else if (pguie.getData() instanceof CriticalPairData) {
				this.isPanel2 = true;
				this.links = ((CriticalPairData) pguie.getData()).getRule1();
				this.rechts = ((CriticalPairData) pguie.getData()).getRule2();
			}
		}
		if (this.links != null && this.rechts != null) {
			this.gDesktop.addRule1(this.links, 300, 150);
			this.gDesktop.addRule2(this.rechts, 300, 150);
		}
	}
	
	Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
	getOverlappingsByEvent(final ParserGUIEvent pguie) {
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
		overlappings = null;
		Hashtable<Graph, Vector<Hashtable<GraphObject, GraphObject>>> 
		overlappingsForGraph = null;
		try {
			if (!CriticalPairAnalysisGUI.this.isPanel2) { // conflicts
				if (pguie.getData() instanceof Pair) {
					if (CriticalPairAnalysisGUI.this.beo.useHostGraphEnabled()) {
						overlappingsForGraph = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo)
												.getCriticalForGraph(
														CriticalPairAnalysisGUI.this.links, 
														CriticalPairAnalysisGUI.this.rechts);
						if (overlappingsForGraph != null && !overlappingsForGraph.isEmpty()) 								
							overlappings = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo)
							.getCriticalPair(CriticalPairAnalysisGUI.this.links, 
									CriticalPairAnalysisGUI.this.rechts, CriticalPair.EXCLUDE, true);						
						CriticalPairAnalysisGUI.this.gDesktop.refresh();					
					} else {
						if (pguie.getMsg() == CriticalPairEvent.CONTINUE_COMPUTE) 
							overlappings = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo)
							.continueComputeCriticalPair(
									CriticalPairAnalysisGUI.this.links, 
									CriticalPairAnalysisGUI.this.rechts, 
									CriticalPair.EXCLUDE, true);						
						else
							overlappings = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo)
										.getCriticalPair(
												CriticalPairAnalysisGUI.this.links, 
												CriticalPairAnalysisGUI.this.rechts, 
												CriticalPair.EXCLUDE, true);
					}
				}
				else if (pguie.getData() instanceof CriticalPairData) {
					overlappings = (Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> )
									((CriticalPairData)pguie.getData()).getCriticalsOfKind(-1);
				}
			} 
			else { // dependencies	
				if (pguie.getData() instanceof Pair) {
					overlappings = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo2).getCriticalPair(CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts, CriticalPair.EXCLUDE, true);								
				}
				else if (pguie.getData() instanceof CriticalPairData) {
					overlappings = (Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> )
									((CriticalPairData)pguie.getData()).getCriticalsOfKind(-1);
				}
			}
		} catch (InvalidAlgorithmException iae) {}
		return overlappings;
	}
	
	/**
	 * this gui listens for <CODE>ParserGUIEvents</CODE>. So it must
	 * implement the listener
	 * 
	 * @param pguie
	 *            the event
	 */
	public void occured(final ParserGUIEvent pguie) {
		if ( (pguie.getData() instanceof CPAEventData)
			|| ((this.getCriticalPairs()) != null
					&& ((ExcludePairContainer) this.getCriticalPairs()).isAlive())
			|| ((this.getCriticalPairs2()) != null
					&& ((ExcludePairContainer) this.getCriticalPairs2()).isAlive())
			|| (pguie.getData() instanceof CriticalPairEvent
					&& ((CriticalPairEvent)pguie.getData()).getKey() 
							== CriticalPairEvent.REMOVE_RELATION_ENTRY) )
			return;
		
		if (pguie.getData() == null) {
			this.gDesktop.removeAllGraphFrames();
			this.gDesktop.removeRuleFrames();
			this.mainPane.revalidate();
			this.mainPane.repaint();
			return;
		}
		
		if (pguie.getSource() == this.gDesktop)
			return;
		
		if ((this.threadCP == null) || !this.threadCPisAlive) {
			this.mainPane.revalidate();
			this.mainPane.repaint();

			getRulesByEvent(pguie);
			
			this.mainPane.revalidate();
			this.mainPane.repaint();

			if (this.links != null && this.rechts != null) {
				StatusMessageEvent sme = new StatusMessageEvent(this, "",
						"Overlapping graphs of rules  [  " + this.links.getName()
								+ "  ,  " + this.rechts.getName() + "  ]");
				fireStatusMessageEvent(sme);
				
				this.gDesktop.removeAllGraphFrames();
				this.mainPane.revalidate();
				this.mainPane.repaint();

				if (!this.isPanel2)
					((ExcludePairContainer) this.beo).setStop(false);
				else
					((ExcludePairContainer) this.beo2).setStop(false);
				
				// run thread when a button pressed
				this.threadCP = runCPairThread(pguie);
				this.threadCP.setPriority(4);
				this.threadCP.start();
			} 
		}
	}

	Thread runCPairThread(final ParserGUIEvent pguie) {
		// run thread when a button pressed
		final Thread th = new Thread() {
			public void run() {
				CriticalPairAnalysisGUI.this.threadCPisAlive = true;
				fireStatusMessageEvent(new StatusMessageEvent(this, "",
						"Thread  -  Computing overlapping graphs of rules  [  "
								+ CriticalPairAnalysisGUI.this.links.getName() + "  ,  "
								+ CriticalPairAnalysisGUI.this.rechts.getName()
								+ "  ]  -  is running ..."));
				
				Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
				overlappings = getOverlappingsByEvent(pguie);

				if (overlappings != null && overlappings.size() > 0) {
					int x0 = 0;
					int xn = overlappings.size()-1;
					if (overlappings.size() >= 50) {
						// here dialog (from-to)
						fromToDialog.showGUI(xn);
						if (fromToDialog.isCanceled()) {
							x0 = 0;
							xn = -1;
						}
						else {
							Point fromTo = fromToDialog.getFromTo();					
							x0 = fromTo.x;
							if (x0 > 0) x0--;
							xn = fromTo.y;
							xn--;
						}
					}
					if (!CriticalPairAnalysisGUI.this.isPanel2) {
						if (CriticalPairAnalysisGUI.this.beo instanceof DependencyPairContainer
									|| CriticalPairAnalysisGUI.this.beo instanceof ExcludePairContainer) {
							for (int x = x0; x <=xn; x++) {
								Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
								p = overlappings.elementAt(x);
								Pair<OrdinaryMorphism, OrdinaryMorphism> p1 = p.first;
								Graph graph = p1.first.getTarget();
								CriticalPairAnalysisGUI.this.gDesktop.addOverlapping(graph, p);
								CriticalPairAnalysisGUI.this.gDesktop.addGraph(graph);
							}
						} 
					} else if (CriticalPairAnalysisGUI.this.beo2 instanceof DependencyPairContainer) {
						for (int x = x0; x <=xn; x++) {
							Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
							p = overlappings.elementAt(x);
							Pair<OrdinaryMorphism, OrdinaryMorphism> p1 = p.first;
							Graph graph = p1.first.getTarget();
							CriticalPairAnalysisGUI.this.gDesktop.addOverlapping(graph, p);
							CriticalPairAnalysisGUI.this.gDesktop.addGraph(graph);
						}
					}
					showWarningWhenGACsUsed(CriticalPairAnalysisGUI.this.links,
											CriticalPairAnalysisGUI.this.rechts);
				} 
				else {
					if (!CriticalPairAnalysisGUI.this.isPanel2) {	// no any conflict							
						boolean hostGraphCheck = CriticalPairAnalysisGUI.this.beo.useHostGraphEnabled();
						ExcludePairContainer.Entry entry = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo)
						.getEntry(
								CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts, true);

						if (entry.getState() == ExcludePairContainer.Entry.COMPUTED) {
							if(hostGraphCheck)
								CriticalPairAnalysisGUI.this.gDesktop.notCriticFrame(
										CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts, 
										"on the current host graph");
							else {
								CriticalPairAnalysisGUI.this.gDesktop.notCriticFrame(
										CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts);	
							}
							showWarningWhenGACsUsed(CriticalPairAnalysisGUI.this.links,
									CriticalPairAnalysisGUI.this.rechts);
						}
					} else {	// no any dependency
						ExcludePairContainer.Entry entry = ((ExcludePairContainer) CriticalPairAnalysisGUI.this.beo2)
						.getEntry(
								CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts, true);
						
						int state = entry.getState();
						if (state == ExcludePairContainer.Entry.COMPUTED
								|| state == ExcludePairContainer.Entry.COMPUTED2
								|| state == ExcludePairContainer.Entry.COMPUTED12) {
							if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPUTABLE) {
								JOptionPane.showMessageDialog(null, 
										"This rule pair could not be computed "
										+"\nbecause reverting of the rule "
										+"<"+CriticalPairAnalysisGUI.this.links.getName()+">"
										+" failed.");
							} else {
								CriticalPairAnalysisGUI.this.gDesktop.notCriticFrame(
										CriticalPairAnalysisGUI.this.links, CriticalPairAnalysisGUI.this.rechts);
								showWarningWhenGACsUsed(CriticalPairAnalysisGUI.this.links,
										CriticalPairAnalysisGUI.this.rechts);
							}
						}
					}
				}
				
				fireStatusMessageEvent(new StatusMessageEvent(this, "",
						"Thread  -  Computing overlapping graphs of rules  [  "
								+ CriticalPairAnalysisGUI.this.links.getName() + "  ,  "
								+ CriticalPairAnalysisGUI.this.rechts.getName() + "  ]  -  finished"));

				CriticalPairAnalysisGUI.this.threadCPisAlive = false;						
			}
		};
		return th;
	}
	
	boolean showGACsWarn = true;
	void showWarningWhenGACsUsed(final Rule r1, final Rule r2) {
		String what = "";
		if (r1.hasEnabledACs(false))
			what = "The first rule: < ".concat(r1.getName()).concat(" > ");
		else if (r2.hasEnabledACs(false))
		what = "The second rule: < ".concat(r2.getName()).concat(" > ");
		if (!what.isEmpty() && this.showGACsWarn) {	
			Object[] options = { "OK", "Do not warn again" };	
			int answer = JOptionPane.showOptionDialog(null,
					"The result of this critical pair may be incomplete! \n"
					+what+"\nmakes use of General Application Conditions.\n" 
					+"Unfortunately, critical pair analysis does not take GACs in account"
					+"\n(not jet implemented).\n", 
					"CPA", 
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
					options, options[1]);
			this.showGACsWarn = (answer == 0);
			
//			JOptionPane.showMessageDialog(null,	
//				"The result of this critical pair may be incomplete! \n"
//				+what+"\nmakes use of General Application Conditions.\n" 
//				+"Unfortunately, critical pair analysis does not take GACs in account"
//				+"\n(not jet implemented).\n", 
//				"CPA", 
//				JOptionPane.WARNING_MESSAGE);
		}
	}
	
	
	public boolean isGenerating() {
		if ((this.beo != null) && ((ExcludePairContainer) this.beo).isAlive())
			return true;
		else if ((this.beo2 != null) && ((ExcludePairContainer) this.beo2).isAlive())
			return true;
		else
			return false;
	}

	public boolean pairsComputed() {
		if ((this.beo != null) && ((ExcludePairContainer) this.beo).isComputed())
			return true;
		else if ((this.beo2 != null) && ((ExcludePairContainer) this.beo2).isComputed())
			return true;
		else
			return false;
	}

	public boolean isOnePairThreadAlive() {
		if ((this.threadCP != null) && this.threadCPisAlive)
			return true;
		
		return false;
	}

	public PairContainer getActivePairContainer() {
		CriticalPairPanel p = this.gDesktop.getActivePairPanel();
		if (p != null)
			return p.getPairContainer();
		
		return null;
	}

	public void stopOnePairThread() {
		if ((this.threadCP != null) && this.threadCPisAlive) {
			if (this.beo != null)
				((ExcludePairContainer) this.beo).setStop(true);
			if (this.beo2 != null)
				((ExcludePairContainer) this.beo2).setStop(true);
			this.threadCPisAlive = false;
		}
	}

	/**
	 * register your <CODE>StatusMessageListener</CODE> to receive messages
	 * 
	 * @param sml
	 *            the listener which listen to my messages
	 */
	public void addStatusMessageListener(StatusMessageListener sml) {
		this.listener.addElement(sml);
	}

	void fireStatusMessageEvent(StatusMessageEvent sme) {
		for (int i = 0; i < this.listener.size(); i++)
			this.listener.elementAt(i).newMessage(sme);
	}

	// Implementing of ActionListeners 
	/**
	 * this GUI listens for <CODE>ActionEvents</CODE> which are created from
	 * Buttons and other GUI typical stuff
	 * 
	 * @param e
	 *            the event
	 */
	public void actionPerformed(ActionEvent e) {}

	/**
	 * this GUI listens if the option has changed
	 * 
	 * @param e
	 *            the event with the new option
	 */
	public void optionHasChanged(GUIOptionEvent e) {
		if (e.getChangedOption().equals(GUIOptionEvent.CRITICALPAIRWINDOWSIZE)) {
			this.gDesktop.setOverlappingGraphWindowSize(this.option
					.getCriticalPairWindowSize());
		} else if (e.getChangedOption().equals(GUIOptionEvent.PARSERDISPLAY)) {
		} else if (e.getChangedOption().equals(
				GUIOptionEvent.NUMBEROFCRITICALPAIR)) {
		}
	}
}

/*
 * $Log: CriticalPairAnalysisGUI.java,v $
 * Revision 1.14  2010/12/21 16:34:02  olga
 * improved - CPA for rules with GACs not implemented
 *
 * Revision 1.13  2010/12/20 20:07:14  olga
 * improved - show CPA Graph
 *
 * Revision 1.12  2010/11/13 21:36:25  olga
 * bug fixed
 *
 * Revision 1.11  2010/11/06 18:31:54  olga
 * extended and improved
 *
 * Revision 1.10  2010/08/23 07:32:57  olga
 * tuning
 *
 * Revision 1.9  2010/04/15 10:02:25  olga
 * null pointer fixed
 *
 * Revision 1.8  2010/04/12 14:44:03  olga
 * tuning
 *
 * Revision 1.7  2010/03/08 15:41:21  olga
 * code optimizing
 *
 * Revision 1.6  2010/01/31 16:44:47  olga
 * extend CPA by checking with multi rules of rule schemes
 *
 * Revision 1.5  2010/01/27 19:37:45  olga
 * tests
 *
 * Revision 1.4  2009/04/27 07:37:17  olga
 * Copy and Paste TypeGraph- bug fixed
 * CPA - dangling edge conflict when first produce second delete - extended
 *
 * Revision 1.3  2009/03/19 10:07:50  olga
 * code tuning
 *
 * Revision 1.2  2009/03/12 10:57:49  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.1  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.23  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.22  2008/02/25 08:44:49  olga
 * Extending of CPA: new class CriticalRulePairAtGraph to get critical
 * matches of two rules at a concret graph.
 *
 * Revision 1.21  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.20  2007/11/14 08:53:43  olga
 * code tuning
 *
 * Revision 1.19  2007/11/12 09:39:34  olga
 * CPA GUI bug fixed
 *
 * Revision 1.18  2007/11/12 08:48:56  olga
 * Code tuning
 *
 * Revision 1.17  2007/11/08 12:57:00  olga
 * working on CPA inconsistency for rules with pacs and inheritance
 * bugs are possible
 *
 * Revision 1.16  2007/11/05 09:18:22  olga
 * code tuning
 *
 * Revision 1.15  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.14  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.13 2006/12/13 13:33:05
 * enrico reimplemented code
 * 
 * Revision 1.12 2006/03/06 09:15:36 olga Type sorting inconsistency of unnamed
 * typs eliminated
 * 
 * Revision 1.11 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.10 2006/01/16 09:39:47 olga GUI tuning
 * 
 * Revision 1.9 2005/12/21 14:48:46 olga GUI tuning
 * 
 * Revision 1.8 2005/10/24 09:04:49 olga GUI tuning
 * 
 * Revision 1.7 2005/10/12 10:00:56 olga CPA GUI tuning
 * 
 * Revision 1.6 2005/10/10 09:13:30 olga tests
 * 
 * Revision 1.5 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.4 2005/09/26 16:41:20 olga CPA graph, CPs - visualization
 * 
 * Revision 1.3 2005/09/26 08:35:15 olga CPA graph frames; bugs
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.28 2005/05/23 09:54:30 olga CPA improved: Stop of generation
 * process or rule pair.
 * 
 * Revision 1.27 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.26 2005/01/03 13:14:43 olga Errors handling
 * 
 * Revision 1.25 2004/09/23 08:26:43 olga Fehler bei CPs weg, Debug output in
 * file
 * 
 * Revision 1.24 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.23 2004/06/21 08:35:33 olga immer noch CPs
 * 
 * Revision 1.22 2004/06/14 12:34:19 olga CP Analyse and Transformation
 * 
 * Revision 1.21 2004/06/09 11:32:54 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.20 2004/04/19 11:39:30 olga Graphname als String ohne Blanks
 * 
 * Revision 1.19 2003/06/26 11:44:33 olga Events-behandlung
 * 
 * Revision 1.18 2003/06/12 07:27:29 olga Testausgabe auskommentiert
 * 
 * Revision 1.17 2003/03/17 15:35:38 olga GUI anpassung
 * 
 * Revision 1.16 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.15 2003/03/03 17:46:59 olga GUI
 * 
 * Revision 1.14 2003/02/24 11:20:27 komm appereance changed
 * 
 * Revision 1.13 2003/02/13 17:08:08 olga GUI Anpassung
 * 
 * Revision 1.12 2003/02/10 13:37:42 komm selected rules from panel will now
 * displayed in GUI
 * 
 * Revision 1.11 2003/02/05 15:53:29 olga GUI
 * 
 * Revision 1.10 2003/02/03 17:49:11 olga GUI
 * 
 * Revision 1.9 2003/01/22 16:19:30 olga CP-Tabelle verbessert
 * 
 * Revision 1.8 2003/01/20 17:33:51 olga CP Tabelle test
 * 
 * Revision 1.7 2003/01/20 17:04:10 olga Critic Pair Table Anpassung
 * 
 * Revision 1.6 2003/01/20 12:10:55 olga CriticalPairPanel anpassung
 * 
 * Revision 1.5 2003/01/20 10:47:00 komm CriticalPairPanel integrated
 * 
 * Revision 1.4 2003/01/15 16:30:20 olga Critical pairs table eingebaut (test)
 * 
 * Revision 1.3 2003/01/13 14:28:30 komm no change
 * 
 * Revision 1.2 2002/12/09 17:53:26 olga GUI - Verbesserung
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.7 2001/07/04 10:40:05 olga Kleine GUI Aenderungen
 * 
 * Revision 1.6 2001/06/26 17:24:48 olga Unwesentliche Aenderung.
 * 
 * Revision 1.5 2001/06/18 13:39:31 olga Nur Text zu der Option "Complete"
 * geaendert.
 * 
 * Revision 1.4 2001/05/14 11:52:56 olga Parser GUI Optimierung
 * 
 * Revision 1.3 2001/04/11 14:56:59 olga Arbeit an der GUI.
 * 
 * Revision 1.2 2001/03/08 11:02:41 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.5 2001/01/02 12:28:56 shultzke Alle Optionen angebunden
 * 
 * Revision 1.1.2.4 2000/12/10 14:55:47 shultzke um Layer erweitert
 * 
 * Revision 1.1.2.3 2000/11/01 12:19:21 shultzke erste Regelanwendung im parser
 * CVs: ----------------------------------------------------------------------
 * 
 * Revision 1.1.2.2 2000/09/25 13:51:54 shultzke Report.trace veraendert
 * 
 * Revision 1.1.2.1 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.7 2000/08/07 10:38:55 shultzke Option erweitert
 * 
 * Revision 1.1.2.6 2000/07/19 14:00:01 shultzke *** empty log message ***
 * 
 * Revision 1.1.2.5 2000/07/16 18:52:25 shultzke *** empty log message ***
 * 
 * Revision 1.1.2.4 2000/07/10 15:08:11 shultzke additional representtion
 * hinzugefuegt
 * 
 * Revision 1.1.2.3 2000/07/09 17:12:36 shultzke grob die GUI eingebunden
 * 
 * Revision 1.1.2.2 2000/07/05 22:23:15 shultzke weiter Implementierung die GUI
 * einzubinden
 * 
 * Revision 1.1.2.1 2000/07/05 12:03:22 shultzke erste Version der ParserGUI
 * 
 * Revision 1.2 2000/06/26 15:51:33 shultzke keine Ahnung
 * 
 */

package agg.gui.parser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JCheckBox;
//import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import agg.gui.AGGAppl;
import agg.gui.event.EditEventListener;
import agg.gui.event.EditEvent;
import agg.attribute.impl.AttrTupleManager;
import agg.editor.impl.EdGraGra;
import agg.gui.options.OptionGUI;
import agg.gui.options.ParserGUIOption;
import agg.gui.options.ParserOptionGUI;
import agg.gui.parser.event.OptionListener;
import agg.gui.parser.event.StatusMessageEvent;
import agg.gui.parser.event.StatusMessageListener;
import agg.gui.treeview.GraGraTreeView;
import agg.parser.CriticalPairOption;
import agg.parser.ExcludePairContainer;
import agg.parser.ExcludeParser;
import agg.parser.LayerOption;
import agg.parser.LayeredExcludePairContainer;
import agg.parser.PairContainer;
import agg.parser.Parser;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.parser.ParserFactory;
import agg.parser.ParserMessageEvent;
import agg.parser.ParserOption;
import agg.parser.SimpleParser;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.RuleLayer;
import agg.util.Pair;

/**
 * The class creates an AGG parser.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class AGGParser implements ParserEventListener, OptionListener,
		EditEventListener {

	/** Creates a new instance of the AGG parser */
	public AGGParser(AGGAppl appl, GraGraTreeView treeView) {
		this.parent = appl;
		this.treeView = treeView;
		this.listener = new Vector<ParserEventListener>();
		this.pmlistener = new Vector<StatusMessageListener>();

		this.option = new ParserGUIOption();

		this.lOption = new LayerOption();
		this.lOptionGUI = new LayerOptionGUI(this.lOption);
		this.lOption.addOptionListener(this.lOptionGUI);

		this.pOption = new ParserOption();
		this.pOptionGUI = new ParserOptionGUI(this.option, this.pOption, this.cpOption);

		this.parserDesktop = new ParserDesktop(this, this.option, null, null, null);

		this.menus = new Vector<JMenu>(2);
		this.menu = new JMenu("Parser", true);
		// menu.setMnemonic('r');
		this.openP = new JMenuItem("Open        Shift+Alt+P");
		this.openP.setMnemonic('O');
		this.startP = new JMenuItem("Start         Shift+Alt+S");
		this.startP.setMnemonic('S');
		this.stopP = new JMenuItem("Stop         Shift+Alt+Q");
		this.stopP.setMnemonic('p');
		this.backP = new JMenuItem("back         Shift+Alt+Z");
		this.backP.setMnemonic('b');
		this.options = new JMenuItem("Options...");
		
		createParserMenu();

		addParserEventListener(this);

		this.changer = new GUIExchange(this.parent);
	}

	public void editEventOccurred(EditEvent e) {
		// System.out.println("AGGParser.editEventOccurred "+e.getObject());
		if (e.getMsg() == EditEvent.MENU_KEY) {
			if (e.getMessage().equals("Parser"))
				this.menu.doClick();
			else if (e.getMessage().equals("Parser Open"))
				openParserDialog();
		}
	}

	protected void showOptionGUI() {
		if (this.parent != null) {
			this.parent.getPreferences().showOptionGUI(OptionGUI.PARSER);
		}
	}
	
	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	public ParserGUIOption getParserGUIOption() {
		return this.option;
	}

	public LayerOption getLayerOption() {
		return this.lOption;
	}

	public LayerOptionGUI getLayerOptionGUI() {
		return this.lOptionGUI;
	}

	public ParserOption getParserOption() {
		return this.pOption;
	}

	public ParserOptionGUI getParserOptionGUI() {
		return this.pOptionGUI;
	}

	public void setCriticalPairOption(CriticalPairOption cpOption) {
		this.cpOption = cpOption;
		this.pOptionGUI.setCriticalPairOption(cpOption);
		cpOption.addOptionListener(this.pOptionGUI);
	}

	/* Implements agg.parser.ParserEventListener */
	public void parserEventOccured(ParserEvent e) {
		// System.out.println("AGGParser.parserEventOccured "+e.getMessage());
		if ((e.getMessage().indexOf("Critical") != -1)
				&& (e.getMessage().indexOf("finished") != -1)) {
			if (this.activeParser && this.generateCP) {
				this.generateCP = false;
				fireParserEvent(new ParserMessageEvent(this,
					" Please choice menu  - Parser / Start -  to start parsing"));
			}
		} else if (e.getMessage().indexOf("Result") != -1) {
			this.startP.setEnabled(false);
			this.stopP.setEnabled(false);

			this.pairsGraGra.setChanged(false);
			this.hostGraphGrammar.setChanged(false);
			this.stopGraphGrammar.setChanged(false);
		}
	}

	/* Implements agg.gui.parser.event.OptionListener */
	public void optionEventOccurred(agg.gui.parser.event.OptionEvent e) {
		// System.out.println("AGGParser.optionEventOccurred");
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) e.getSource();
			// System.out.println(cb.getText()+" "+cb.isSelected());
			if (cb.getText().equals("NACs")) {
				this.cpOption.enableNacs(cb.isSelected());
			} else if (cb.getText().equals("PACs")) {
				this.cpOption.enablePacs(cb.isSelected());
			}
		}
	}

	/** Creates a parser menu */
	protected void createParserMenu() {
		this.openP.setEnabled(true);
		this.menu.add(this.openP);
		this.openP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openParserDialog();
			}
		});

		this.startP.setEnabled(false);
		this.menu.add(this.startP);
		this.startP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startParser();
			}
		});

		this.stopP.setEnabled(false);
		this.menu.add(this.stopP);
		this.stopP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stopParser();
			}
		});

		this.backP.setEnabled(false);
		this.menu.add(this.backP);
		this.backP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backToGUI();
			}
		});
		
		this.menu.addSeparator();
		
		this.menu.add(this.options);
		this.options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptionGUI();
			}
		});
		
		this.menus.addElement(this.menu);
	}

	public void openParserDialog() {
		this.parserStartDialog = new ParserDialog(this.parent, this.treeView, this.pOption,
				this.cpOption, this.lOption);
		/*
		 * if(pOption.getSelectedParser() != ParserOption.SIMPLEPARSER){
		 * javax.swing.JOptionPane.showMessageDialog(null, "Please
		 * note:\nParsing is not possible for graph grammars \nwith node type
		 * inheritance.", "Warning", javax.swing.JOptionPane.WARNING_MESSAGE); }
		 */
		this.parserStartDialog.showDialog();
		if (this.parserStartDialog.isReadyToParse()) {
			this.hostGraphGrammar = this.parserStartDialog.getHostGraphGrammar();
			this.stopGraphGrammar = this.parserStartDialog.getStopGraphGrammar();
			this.tmpPairs = this.parserStartDialog.getCriticalPairs();
			this.pairsGraGra = this.stopGraphGrammar;
			if (this.pairsGraGra.getBasisGraGra() == null) {
				javax.swing.JOptionPane.showMessageDialog(null,
						"Parsing grammar does not exist.", "Warning",
						javax.swing.JOptionPane.ERROR_MESSAGE);
				fireParserEvent(new ParserMessageEvent(this,
						"Thread - Parsing -  was stopped."));
				return;
			}

//			if (pOption.getSelectedParser() != ParserOption.SIMPLEPARSER) {
//				if (hostGraphGrammar.getBasisGraGra().getTypeSet()
//						.usesInheritance()
//						|| pairsGraGra.getBasisGraGra().getTypeSet()
//								.usesInheritance()) {
//					javax.swing.JOptionPane
//							.showMessageDialog(
//									null,
//									"Sorry!\nParsing based on critical pairs is not possible "
//											+ "\nfor graph grammars with node type inheritance."
//											+ "\nPlease select:\n "
//											+ "\"Backtracking without optimization\" algoritm for parser.",
//									"Warning",
//									javax.swing.JOptionPane.WARNING_MESSAGE);
//					fireParserEvent(new ParserMessageEvent(this,
//							"Thread - Parsing -  was stopped."));
//					return;
//				}
//			}

			this.ruleLayer = null;
			if (this.tmpPairs == null) {
				if (!checkIfReadyToTransform(this.pairsGraGra)) {
					return;
				}
				if (this.pOption.getSelectedParser() != ParserOption.SIMPLEPARSER) {
					if (this.pOption.layerEnabled()) {
						RuleLayer rlayer = new RuleLayer(this.pairsGraGra
								.getBasisGraGra().getEnabledRules()); //getListOfRules());
						LayerGUI lg = new LayerGUI(this.parent, rlayer);
						lg.showGUI();
						if (lg.isCancelled()) {
							this.pOption.enableLayer(false);
							this.pOptionGUI.update();
						} 
						else {
							this.ruleLayer = rlayer;
							this.pOption.enableLayer(true);
							this.pOptionGUI.update();							
						}
					} 

					this.tmpPairs = ParserFactory.createEmptyCriticalPairs(
							this.pairsGraGra.getBasisGraGra(), this.cpOption);

					if (this.tmpPairs == null) {
						javax.swing.JOptionPane.showMessageDialog(null,
								"Generating of critical pairs is failed.",
								"Warning",
								javax.swing.JOptionPane.ERROR_MESSAGE);
						fireParserEvent(new ParserMessageEvent(this,
								"Thread - Parsing -  was stopped."));
						return;
					}
					this.generateCP = true;

					/* add PairEventListeners */
					for (int i = 0; i < this.listener.size(); i++) {
						if (this.tmpPairs instanceof LayeredExcludePairContainer)
							((LayeredExcludePairContainer) this.tmpPairs)
									.addPairEventListener(this.listener.elementAt(i));
						else if (this.tmpPairs instanceof ExcludePairContainer)
							((ExcludePairContainer) this.tmpPairs)
									.addPairEventListener(this.listener.elementAt(i));
					}
					this.activeParser = true;
					
					fireParserEvent(new ParserMessageEvent(this,
							"Generate critical pairs. Please wait ..."));					
					ParserFactory.generateCriticalPairs(this.tmpPairs);
					
				} // if( != ParserOption.SIMPLEPARSER )
				else {
					// Backtracking without CP == ParserOption.SIMPLEPARSER
					this.generateCP = false;
					if (this.pOption.layerEnabled()) {
						this.ruleLayer = new RuleLayer(this.pairsGraGra.getBasisGraGra()
								.getListOfRules());
						LayerGUI lg = new LayerGUI(this.parent, this.ruleLayer);
						lg.showGUI();
						if (lg.isCancelled()) {
							this.pOption.enableLayer(false);
							this.pOptionGUI.update();
						} 
						else {
							this.pOption.enableLayer(true);
							this.pOptionGUI.update();
						}
					} 
				}
				if (this.option.getParserDisplay() != ParserGUIOption.PARSINGINVISIBLE) {
					if (!this.changer.isSet())// || !activeParser)
						this.changer.changeWith(this.parserDesktop.getComponent());
				}
			} // end if(tmpPairs == null)
			else {
				this.generateCP = false;
				if (this.tmpPairs instanceof LayeredExcludePairContainer) {

					this.ruleLayer = new RuleLayer(this.tmpPairs.getGrammar()
							.getListOfRules());
					this.pOption.enableLayer(true);
					this.pOptionGUI.update();
				}

				if (this.option.getParserDisplay() != ParserGUIOption.PARSINGINVISIBLE) {
					if (!this.changer.isSet()) // || !activeParser)
						this.changer.changeWith(this.parserDesktop.getComponent());
				}

				/* set pairs gragra */
				if (this.pairsGraGra == null)
					this.pairsGraGra = new EdGraGra(this.tmpPairs.getGrammar());
				else if (!this.pairsGraGra.getBasisGraGra().compareTo(
						this.tmpPairs.getGrammar())) {
					this.pairsGraGra = new EdGraGra(this.tmpPairs.getGrammar());
					System.out
							.println("WARNING! The grammar loaded with critical pairs has some differences to the loaded stop grammar with parsing rules. The critical pairs grammar will be used for parsing.");
				}

				if (this.pairsGraGra == null || this.pairsGraGra.getBasisGraGra() == null) {
					javax.swing.JOptionPane.showMessageDialog(null,
							"Parsing rules are not exist.", "Warning",
							javax.swing.JOptionPane.ERROR_MESSAGE);
					fireParserEvent(new ParserMessageEvent(this,
							"Thread - Parser -  was stopped."));
					return;
				}
			}

			if (!checkIfReadyToTransform(this.hostGraphGrammar)) {
				return;
			}
			
			((AttrTupleManager) AttrTupleManager.getDefaultManager())
					.setVariableContext(false);
			// create parser
			OrdinaryMorphism 
			om = this.hostGraphGrammar.getBasisGraGra().getGraph().isomorphicCopy();
			if (om != null)
				this.excludeParser = ParserFactory.createParser(
						this.pairsGraGra.getBasisGraGra(), 
						om.getImage(), 
						this.stopGraphGrammar.getBasisGraGra().getGraph(), 
						this.tmpPairs, this.pOption, this.ruleLayer);
			if (this.excludeParser == null) {
				javax.swing.JOptionPane.showMessageDialog(null,
						"Creating parser is failed.", "Warning",
						javax.swing.JOptionPane.ERROR_MESSAGE);
				fireParserEvent(new ParserMessageEvent(this,
						"Thread - Parsing -  was stopped."));
				return;
			}
			// set delay time of parsing
			this.excludeParser.setDelayAfterApplyRule(this.option
					.getDelayAfterApplyRule());
			// add parser event listener
			for (int i = 0; i < this.listener.size(); i++) {
				if (this.excludeParser instanceof ExcludeParser)
					((ExcludeParser) this.excludeParser)
							.addParserEventListener(this.listener.elementAt(i));
				else if (this.excludeParser instanceof SimpleParser)
					((SimpleParser) this.excludeParser)
							.addParserEventListener(this.listener.elementAt(i));
			}

			this.parserDesktop.setLayout(this.hostGraphGrammar);
			this.parserDesktop.setStopLayout(this.stopGraphGrammar.getGraph());
			this.parserDesktop.setParser(this.excludeParser, om);
			fireStatusMessageEvent(new StatusMessageEvent(this, ""));
			if (!this.generateCP) {
				fireParserEvent(new ParserMessageEvent(this,
						"Please choice menu  - Parser / Start -  to start parsing"));
			}
			this.activeParser = true;
			this.openP.setEnabled(false);
			this.startP.setEnabled(true);
			this.stopP.setEnabled(false);
			this.backP.setEnabled(true);
		} // end if(parserStartDialog.isReadyToParse())
	}

	public void startParser() {
		if (this.generateCP) {
			fireParserEvent(new ParserMessageEvent(this,
					"Generating critical pairs ...  Please wait."));
			return;
		}

		this.openP.setEnabled(false);
		this.startP.setEnabled(false);
		this.stopP.setEnabled(true);
		this.backP.setEnabled(true);

		this.parserDesktop.hostFrameSetAnimationIcon();
		fireParserEvent(new ParserMessageEvent(this, "Starting parser ..."));
		/* create parser thread and start parser */
		Thread t = new Thread((Runnable) this.excludeParser);
		t.start();
	}

	public void stopParser() {
		if (this.tmpPairs != null)
			((ExcludePairContainer) this.tmpPairs).stop();
		if (this.excludeParser instanceof ExcludeParser)
			((ExcludeParser) this.excludeParser).stop();
		else if (this.excludeParser instanceof SimpleParser)
			((SimpleParser) this.excludeParser).stop();

		this.startP.setEnabled(true);
		this.stopP.setEnabled(false);
	}

	public void backToGUI() {
		if (this.changer.isSet()) {
			this.changer.restore();
			/* remove pair event listeners */
			for (int i = 0; i < this.listener.size(); i++) {
				if (this.tmpPairs instanceof LayeredExcludePairContainer) {
					/* stop CP-Thread, if it is still running */
					((LayeredExcludePairContainer) this.tmpPairs).stop();
					/* remove pair event listener */
					((LayeredExcludePairContainer) this.tmpPairs)
							.removePairEventListener(this.listener.elementAt(i));
				} else if (this.tmpPairs instanceof ExcludePairContainer) {
					/* stop CP-Thread, if it is still running */
					((ExcludePairContainer) this.tmpPairs).stop();
					/* remove pair event listener */
					((ExcludePairContainer) this.tmpPairs)
							.removePairEventListener(this.listener.elementAt(i));
				}
			}

			/* remove parser event listeners */
			for (int i = 0; i < this.listener.size(); i++) {
				if (this.excludeParser instanceof ExcludeParser) {
					/* stop P-Thread, if it is still running */
					((ExcludeParser) this.excludeParser).stop();
					/* remove parser event listener */
					((ExcludeParser) this.excludeParser)
							.removeParserEventListener(this.listener.elementAt(i));
				} else if (this.excludeParser instanceof SimpleParser) {
					/* stop P-Thread, if it is still running */
					((SimpleParser) this.excludeParser).stop();
					/* remove parser event listener */
					((SimpleParser) this.excludeParser)
							.removeParserEventListener(this.listener.elementAt(i));
				}
			}

			this.parserDesktop.disposeTestHostGraph(this.hostGraphGrammar);

			this.activeParser = false;
			this.openP.setEnabled(true);
			this.startP.setEnabled(false);
			this.stopP.setEnabled(false);
			this.backP.setEnabled(false);

			fireParserEvent(new ParserMessageEvent(this, "back to AGG editor"));
		}
	}

	private boolean checkIfReadyToTransform(EdGraGra gra) {
		Pair<Object, String> pair = gra.getBasisGraGra().isReadyToTransform(true);
		if (pair != null) {
			Object test = pair.first;
		if (test != null) {
			javax.swing.JOptionPane.showMessageDialog(null,
					"Parsing failed!  \nGrammar  \"" + gra.getName()
							+ "\"  is not OK.\n" + pair.second,
					"Warning", javax.swing.JOptionPane.ERROR_MESSAGE);
			fireParserEvent(new ParserMessageEvent(this,
					"Thread - Parsing -  was stopped."));
			return false;
		}
		}
		return true;
	}

	// -----------------------------------------------------------------------+

	/**
	 * Adds a StatusMessageListener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addStatusMessageListener(StatusMessageListener l) {
		if (!this.pmlistener.contains(l))
			this.pmlistener.add(0, l);
		this.parserDesktop.addStatusMessageListener(l);
	}

	private void fireStatusMessageEvent(StatusMessageEvent e) {
		for (int i = 0; i < this.pmlistener.size(); i++)
			this.pmlistener.elementAt(i).newMessage(e);
	}

	/**
	 * Adds a ParserEventListener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addParserEventListener(ParserEventListener l) {
		if (!this.listener.contains(l))
			this.listener.add(0, l);
	}

	/**
	 * Removes a ParserEventListener
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeParserEventListener(ParserEventListener l) {
		if (this.listener.contains(l))
			this.listener.removeElement(l);
	}

	/**
	 * Sends a event to all its listeners.
	 * 
	 * @param event
	 *            The event which will be sent
	 */
	private synchronized void fireParserEvent(ParserEvent e) {
		for (int i = 0; i < this.listener.size(); i++) {
			this.listener.elementAt(i).parserEventOccured(e);
		}
	}

	private final GUIExchange changer;

	private final ParserGUIOption option;

	private final ParserOptionGUI pOptionGUI;

	private final ParserOption pOption;

	private final LayerOptionGUI lOptionGUI;

	private final LayerOption lOption;

	private CriticalPairOption cpOption;

	private final ParserDesktop parserDesktop;

	private ParserDialog parserStartDialog;

	private Parser excludeParser;

	private PairContainer tmpPairs;

	private final JMenu menu;

	private final JMenuItem openP, startP, stopP, backP, options;

	private final Vector<ParserEventListener> listener;

	private final Vector<StatusMessageListener> pmlistener;

	private boolean activeParser;

	private boolean generateCP;

	private final agg.gui.AGGAppl parent;

	private EdGraGra pairsGraGra, hostGraphGrammar, stopGraphGrammar;

	private RuleLayer ruleLayer;

	private final agg.gui.treeview.GraGraTreeView treeView;

	private final Vector<JMenu> menus;

}

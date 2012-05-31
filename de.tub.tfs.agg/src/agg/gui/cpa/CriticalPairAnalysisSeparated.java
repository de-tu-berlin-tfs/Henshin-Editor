package agg.gui.cpa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.util.EventObject;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JLabel;
//import javax.swing.JOptionPane;
import javax.swing.JSplitPane;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.gui.options.CriticalPairOptionGUI;
import agg.gui.options.ParserGUIOption;
import agg.gui.options.ParserOptionGUI;
import agg.gui.parser.LayerGUI;
import agg.gui.parser.PairIOGUI;
import agg.gui.parser.event.OptionListener;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.parser.CriticalPair;
import agg.parser.CriticalPairOption;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.parser.LayerOption;
import agg.parser.LayeredDependencyPairContainer;
import agg.parser.LayeredExcludePairContainer;
import agg.parser.OptionEventListener;
import agg.parser.PairContainer;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.parser.ParserFactory;
import agg.parser.ParserOption;
import agg.parser.PriorityDependencyPairContainer;
import agg.parser.PriorityExcludePairContainer;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.RuleLayer;

/**
 * The class creates an AGG critical pair analizer .
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class CriticalPairAnalysisSeparated implements ParserEventListener,
		OptionListener,  // parser options
		OptionEventListener // CP options 
{

	protected final String title = "Critical Pair Analysis ";
	
	/** Creates a new instance of the CP analysis */
	public CriticalPairAnalysisSeparated(JFrame parent, PairIOGUI pairsIOGUI,
			CriticalPairOptionGUI cpOptionGUI, CriticalPairOption cpOption,
			LayerOption lOption, ParserOption pOption, ParserGUIOption option) {
		this.parent = parent;
		this.pairsIOGUI = pairsIOGUI;
		this.cpOptionGUI = cpOptionGUI;
		this.needToLoad = true;
		
		createCriticalPairAnalysis(cpOption, lOption, pOption, option);
	}

	/** Creates a new instance of the CP analysis */
	public CriticalPairAnalysisSeparated(JFrame parent,
			ExcludePairContainer conflictContainer,
			ExcludePairContainer dependContainer, CriticalPairOption cpOption,
			LayerOption lOption, ParserOption pOption, ParserGUIOption option) {
		this.parent = parent;
		this.excludePC = conflictContainer;
		this.dependPC = dependContainer;
		this.needToLoad = false;
		
		createCriticalPairAnalysis(cpOption, lOption, pOption, option);
	}

	public void extendTitle(final String name) {
		String str = " ( ".concat(name).concat(" )");
		this.frame.setTitle(this.title.concat(str));
	}
	
	private void createCriticalPairAnalysis(CriticalPairOption cpoption,
			LayerOption loption, ParserOption poption, ParserGUIOption pguioption) {
		this.cpOption = cpoption;
		this.lOption = loption;
		this.pOption = poption;
		this.option = pguioption;

		this.cpOption.addOptionListener(this);
		
		this.frame = new JFrame("Critical Pair Analysis");
		JMenuBar menuBar = new JMenuBar();
		this.label = new JLabel("          ");
		this.frame.getContentPane().add(this.label, BorderLayout.SOUTH);
		createAnalysisMenu("Critical Pair Analysis / Show");
		menuBar.add(this.menu);
		this.frame.setJMenuBar(menuBar);

		createCriticalPairAnalysisGUI();
		if (!this.needToLoad || loadCriticalPairs()) {
			// System.out.println(this.excludePC);
			// System.out.println(this.dependPC);
			if (!this.loaded) {
				if (this.excludePC != null && this.dependPC != null) {
					if (this.excludePC.getGrammar().compareTo(this.dependPC.getGrammar())) {
						this.pairsGraGra = new EdGraGra(this.excludePC.getGrammar());
						this.pairsGUI.setGraGra(this.pairsGraGra);
						this.pairsGUI.setCriticalPairs(this.excludePC);
						this.pairsGUI.setCriticalPairs(this.dependPC);
						// System.out.println("Conflicts && Dependencies set in
						// this.pairsGUI");
					} else {
						javax.swing.JOptionPane
								.showMessageDialog(
										null,
										"Show CPA Graph failed!\nConflicts and Dependencies use different grammars!",
										"CPA Graph",
										javax.swing.JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					if (this.excludePC != null) {
						this.pairsGraGra = new EdGraGra(this.excludePC.getGrammar());
						this.pairsGUI.setGraGra(this.pairsGraGra);
						this.pairsGUI.setCriticalPairs(this.excludePC);
					}
					if (this.dependPC != null) {
						this.pairsGraGra = new EdGraGra(this.dependPC.getGrammar());
						this.pairsGUI.setGraGra(this.pairsGraGra);
						this.pairsGUI.setCriticalPairs(this.dependPC);
					}
				}
			}
			/*
			 * startCPaddActionListener(); 
			 * startCP.setEnabled(true); 
			 * emptyCP.setEnabled(true); 
			 * emptyCPaddActionListener(); 
			 */
			
			this.stopCP.setEnabled(false);
			stopCPaddActionListener();
			this.showCP.setEnabled(true);
			showCPaddActionListener();
			this.saveCP.setEnabled(true);
			saveCPaddActionListener();

			this.ready = true;
		} else
			this.ready = false;

		this.frame.getContentPane().setSize(600, 500);
		this.frame.pack();
		this.frame.getRootPane().revalidate();
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public EdGraGra getGraGra() {
		return this.pairsGraGra;
	}
	
	public ExcludePairContainer getPairContainer() {
		return (ExcludePairContainer) this.excludePC;
	}

	public ExcludePairContainer getPairContainer(int kindOfConflict) {
		if (kindOfConflict == CriticalPair.CONFLICT)
			return (ExcludePairContainer) this.excludePC;
		else if (kindOfConflict == CriticalPair.TRIGGER_DEPENDENCY
				|| kindOfConflict == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
			return (ExcludePairContainer) this.dependPC;
		else
			return null;
	}

	public CriticalPairAnalysisGUI getCriticalPairAnalusisGUI() {
		return this.pairsGUI;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setExportJPEG(GraphicsExportJPEG jpg) {
		this.exportJPEG = jpg;
		if (this.pairsGUI != null)
			this.pairsGUI.getGraphDesktop().setExportJPEG(this.exportJPEG);
	}

	public void showFrame() {
		this.frame.setLocation(this.x, this.y);
		this.frame.setVisible(true);
	}

	public void toFront() {
		this.frame.toFront();
	}
	
	public void disposeFrame() {
		this.frame.dispose();
	}

	private void createAnalysisMenu(String menutitle) {
		this.menu = new JMenu(menutitle);
		// this.menu.setEnabled(false);
		
//		startCP = new JMenuItem("Generate"); startCP.setEnabled(false);
//		this.menu.add(startCP);
		 
//		reduceCP = new JMenuItem("Reduce"); reduceCP.setEnabled(false);
//		this.menu.add(reduceCP);
		 
//		consistCP = new JMenuItem("Check Consistency");
//		consistCP.setEnabled(false); this.menu.add(consistCP);
//		 
//		emptyCP = new JMenuItem("Empty"); emptyCP.setEnabled(false);
//		this.menu.add(emptyCP);
		 
		this.stopCP = new JMenuItem("Stop"); 
		this.stopCP.setEnabled(false);
		this.menu.add(this.stopCP);
		 
		this.saveCP = new JMenuItem("Save");
		this.saveCP.setEnabled(false);
		this.menu.add(this.saveCP);
		
		this.showCP = new JMenu("Show");
		this.showCP.setEnabled(false);
		this.showConflictCP = this.showCP.add(new JMenuItem("Conflicts"));
		this.showDependencyCP = this.showCP.add(new JMenuItem("Dependencies"));
		this.cpaCombiGraphCP = this.showCP.add(new JMenuItem("CPA Graph"));
		this.menu.add(this.showCP);
	}

	private void createCriticalPairAnalysisGUI() {
		this.pairsGUI = new CriticalPairAnalysisGUI(this.parent, this.option);

		((JSplitPane) this.pairsGUI.getContainer()).setPreferredSize(new Dimension(
				550, 450));
		this.frame.getContentPane()
				.add(this.pairsGUI.getContainer(), BorderLayout.CENTER);

		if (this.needToLoad) {
			this.emptyGraGra = BaseFactory.theFactory().createGraGra();
			this.cpOption.enableLayered(true);
			if (this.pairsIOGUI == null)
				this.excludePC = ParserFactory.createEmptyCriticalPairs(this.emptyGraGra,
						this.cpOption.getCriticalPairAlgorithm(), this.cpOption
								.layeredEnabled());
		}
	}

	private boolean loadCriticalPairs() {
		if (this.pairsIOGUI == null)
			this.pairsIOGUI = new PairIOGUI(this.parent);
		
		Object o = this.pairsIOGUI.load(true);
		if (o == null) {
			this.cpOption.enableLayered(false);
			return false;
		}
				
		if (this.pairsIOGUI.isCombined()) {
//			ConflictsDependenciesContainer cdc = (ConflictsDependenciesContainer) o;

			ConflictsDependenciesContainerSaveLoad cdc = (ConflictsDependenciesContainerSaveLoad) o;
						
			if (this.cpaGraph != null) {
				if (this.conflictDependGraph != null) {
					if (this.excludePC != null)
						this.pairsGUI.getGraphDesktop().getConflictPairPanel()
								.removeParserGUIListener(this.conflictDependGraph);
					if (this.dependPC != null)
						this.pairsGUI.getGraphDesktop().getDependPairPanel()
								.removeParserGUIListener(this.conflictDependGraph);
					this.pairsGUI.getGraphDesktop()
							.removeActionListenerFromCPAGraphMenu(
									this.conflictDependGraph);
				}
			}

			this.cpOption.setOptionsFromList(cdc.getLoadedCPAOptions());
			
			this.pairsGUI.reinitGraphDesktop();
			this.excludePC = null;
			this.dependPC = null;
			this.pairsGUI.getGraphDesktop().getDesktop().repaint();

			if (cdc.isLayered()) {
				if (cdc.getLayeredExcludePairContainer() != null)
					this.excludePC = cdc.getLayeredExcludePairContainer();
				if (cdc.getLayeredDependencyPairContainer() != null)
					this.dependPC = cdc.getLayeredDependencyPairContainer();
				this.cpOption.enableLayered(true);
				this.cpOption.enablePriority(false);
			} else if (cdc.isPriority()) {
				if (cdc.getPriorityExcludePairContainer() != null)
					this.excludePC = cdc.getPriorityExcludePairContainer();
				if (cdc.getPriorityDependencyPairContainer() != null)
					this.dependPC = cdc.getPriorityDependencyPairContainer();
				this.cpOption.enablePriority(true);
				this.cpOption.enableLayered(false);
			}  else {
				if (cdc.getExcludePairContainer() != null)
					this.excludePC = cdc.getExcludePairContainer();
				if (cdc.getDependencyPairContainer() != null)
					this.dependPC = cdc.getDependencyPairContainer();
				this.cpOption.enablePriority(false);
				this.cpOption.enableLayered(false);
			}
			if (cdc.getContainerCount() == 2) {
//				this.pairsGraGra = new EdGraGra(this.excludePC.getGrammar());
				this.pairsGraGra = cdc.getPairsGraGra();
				this.pairsGUI.setGraGra(this.pairsGraGra);
				resetCP_GUI(this.pairsGraGra, this.excludePC, true);
				resetCP_GUI(this.pairsGraGra, this.dependPC, true);
			} else if (cdc.getContainerCount() == 1) {
				if (this.excludePC != null) {
//					this.pairsGraGra = new EdGraGra(this.excludePC.getGrammar());
					this.pairsGraGra = cdc.getPairsGraGra();
					this.pairsGUI.setGraGra(this.pairsGraGra);
					resetCP_GUI(this.pairsGraGra, this.excludePC, true);
				} else if (this.dependPC != null) {
//					this.pairsGraGra = new EdGraGra(this.dependPC.getGrammar());
					this.pairsGraGra = cdc.getPairsGraGra();
					this.pairsGUI.setGraGra(this.pairsGraGra);
					resetCP_GUI(this.pairsGraGra, this.dependPC, true);
				}
			}

			this.cpaGraph = cdc.getCPAGraph();
			// System.out.println(this.cpaGraph);
			if (this.cpaGraph != null) {
				this.conflictDependGraph = new ConflictsDependenciesGraph(
						(ExcludePairContainer) this.excludePC,
						(ExcludePairContainer) this.dependPC, this.cpaGraph, true);
				if (this.excludePC != null)
					this.pairsGUI.getGraphDesktop().getConflictPairPanel()
							.addParserGUIListener(this.conflictDependGraph);
				if (this.dependPC != null)
					this.pairsGUI.getGraphDesktop().getDependPairPanel()
							.addParserGUIListener(this.conflictDependGraph);
				this.pairsGUI.getGraphDesktop().addActionListenerToCPAGraphMenu(
						this.conflictDependGraph);
				this.conflictDependGraph.setGraphDesktop(this.pairsGUI.getGraphDesktop());
				
				this.pairsGUI.getGraphDesktop().addGraph(this.cpaGraph, 400, 300);

				try {							
					this.pairsGUI.getGraphDesktop().getInternalCPAGraphFrame().setIcon(false);					
				} catch (java.beans.PropertyVetoException pve) {}
				
				this.pairsGUI.getGraphDesktop().refresh();
			}
			
			this.cpOptionGUI.update();
			
			this.extendTitle(this.pairsIOGUI.getFileName()+" : "+this.pairsGraGra.getName());
			this.loaded = true;
		}
		return true;
	}

	private void resetCP_GUI(EdGraGra gragra, PairContainer pc, boolean newpc) {
		if ((gragra == null) || gragra.getRules().isEmpty())
			return;

		if (pc != null) {
			if (newpc) {
				if (this.pairsGUI.getGraGra() != gragra)
					this.pairsGUI.setGraGra(gragra);
				addPairEventListenerToPairContainer(pc);
				this.pairsGUI.setCriticalPairs(pc);
			} else {
				this.pairsGUI.update();
			}
			setCPoptions((ExcludePairContainer) pc);
		}
	}

	private void setCPoptions(ExcludePairContainer pc) {
		pc.enableComplete(this.cpOption.completeEnabled());
		pc.enableReduce(this.cpOption.reduceEnabled());
		pc.enableConsistent(this.cpOption.consistentEnabled());
		pc.enableIgnoreIdenticalRules(this.cpOption.ignoreIdenticalRulesEnabled());
		pc.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
		pc.enableStrongAttrCheck(this.cpOption.strongAttrCheckEnabled());
		pc.enableEqualVariableNameOfAttrMapping(
				this.cpOption.equalVariableNameOfAttrMappingEnabled());
		pc.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
		
		if (!(pc instanceof DependencyPairContainer)) {
			pc.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
			pc.enableDirectlyStrictConfluentUpToIso(
					this.cpOption.directlyStrictConflUpToIsoEnabled());
		}
	}
	
	private void addPairEventListenerToPairContainer(PairContainer pc) {
			if (pc instanceof LayeredDependencyPairContainer)
				((LayeredDependencyPairContainer) pc)
						.addPairEventListener(this);
			else if (pc instanceof LayeredExcludePairContainer)
				((LayeredExcludePairContainer) pc)
						.addPairEventListener(this);
			else if (pc instanceof PriorityDependencyPairContainer)
				((PriorityDependencyPairContainer) pc)
						.addPairEventListener(this);
			else if (pc instanceof PriorityExcludePairContainer)
				((PriorityExcludePairContainer) pc)
						.addPairEventListener(this);
			else if (pc instanceof DependencyPairContainer)
				((DependencyPairContainer) pc).addPairEventListener(this);
			else if (pc instanceof ExcludePairContainer)
				((ExcludePairContainer) pc).addPairEventListener(this);
	}
	
	/* Implements agg.parser.ParserEventListener */
	public void parserEventOccured(ParserEvent e) {
//		System.out.println("CriticalPairAnalysisSeparated.parserEventOccured: "+e.getMessage());
		if ((e.getMessage().indexOf("Critical") != -1)
				&& (e.getMessage().indexOf("finished") != -1)) {

			updateCPAgraph();
			
			this.stopCP.setEnabled(false);
//			startCP.setEnabled(true);
//			emptyCP.setEnabled(true);
//			reduceCP.setEnabled(true);
//			consistCP.setEnabled(true);
			this.saveCP.setEnabled(true);
			
		} else if (e.getMessage().indexOf("rule pair") != -1) {
			// one rule pair computing
			if (e.getMessage().indexOf("done") == -1) {
//				startCP.setEnabled(false);
//				reduceCP.setEnabled(false);
//				consistCP.setEnabled(false);
				this.stopCP.setEnabled(true);
				this.saveCP.setEnabled(false);
				this.showCP.setEnabled(false);
			} else {
//				startCP.setEnabled(true);
//				reduceCP.setEnabled(true);
//				consistCP.setEnabled(true);
				this.stopCP.setEnabled(false);
				this.saveCP.setEnabled(true);
				this.showCP.setEnabled(true);				
			}
			
		} else if (e.getMessage().indexOf("done") != -1) {
//			startCP.setEnabled(true);
//			reduceCP.setEnabled(true);
//			consistCP.setEnabled(true);
			this.stopCP.setEnabled(false);
			this.saveCP.setEnabled(true);
			this.showCP.setEnabled(true);
		}
	}

	private void updateCPAgraph() {
		if (this.cpaGraph != null) {
			if (this.conflictDependGraph != null) {
				this.conflictDependGraph.updateGraphAlongPairContainer();
				this.conflictDependGraph.updateGraphAlongPairContainer();
				this.cpaGraph.makeGraphObjectsOfNewBasisObjects(false);
				this.cpaGraph.setTransformChangeEnabled(true);
				this.cpaGraph.updateGraph();
				this.cpaGraph.setTransformChangeEnabled(false);
				this.pairsGUI.getGraphDesktop().refresh();
			}
		}
	}

	/* Implements agg.gui.parser.event.OptionListener */
	public void optionEventOccurred(agg.gui.parser.event.OptionEvent e) {
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) e.getSource();
			if (cb.getText().equals("NACs")) {
				this.cpOption.enableNacs(cb.isSelected());
			} else if (cb.getText().equals("PACs")) {
				this.cpOption.enablePacs(cb.isSelected());
			}
		}
	}

	/* Implements agg.parser.OptionEventListener */
	public void optionEventOccurred(EventObject e) {
		if (e.getSource() instanceof CriticalPairOption) {

			if (this.excludePC != null) {
				setCPoptions((ExcludePairContainer) this.excludePC);
				
				if (this.excludePC instanceof LayeredExcludePairContainer) {
					((LayeredExcludePairContainer) this.excludePC)
							.setLayer(this.cpOption.getLayer());
				}
			}
			if (this.dependPC != null) {
				((DependencyPairContainer) this.dependPC).
					enableSwitchDependency(this.cpOption.switchDependencyEnabled());
				
				setCPoptions((ExcludePairContainer) this.dependPC);

				if (this.dependPC instanceof LayeredDependencyPairContainer) {
					((LayeredDependencyPairContainer) this.dependPC)
							.setLayer(this.cpOption.getLayer());
				}
			}
		}
	}

	public CriticalPairAnalysisGUI getCriticalPairAnalysisGUI() {
		return this.pairsGUI;
	}

	public boolean isReady() {
		return this.ready;
	}

	/*
	private void startCPaddActionListener() {
		startCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println(pairsGraGra);
				if ((pairsGraGra == null)
						|| (pairsGraGra.getBasisGraGra() == null))
					return;

				PairContainer oldPC = this.pairsGUI.getCriticalPairs();
				// System.out.println(oldPC);
				if (oldPC instanceof ExcludePairContainer) {
					if (((ExcludePairContainer) oldPC).isComputed()) {
						startCP.setEnabled(true);
						this.stopCP.setEnabled(false);
						emptyCP.setEnabled(true);
						reduceCP.setEnabled(true);
						consistCP.setEnabled(true);
						this.saveCP.setEnabled(true);
					} else if (this.pairsGUI.isOnePairThreadAlive()) {
						startCP.setEnabled(false);
						emptyCP.setEnabled(true);
						reduceCP.setEnabled(false);
						consistCP.setEnabled(false);
						this.saveCP.setEnabled(false);
					} else {
						resetLayerFunction();
						this.excludePC = ParserFactory.createEmptyCriticalPairs(
								pairsGraGra.getBasisGraGra(), cpOption
										.getCriticalPairAlgorithm(), cpOption
										.layeredEnabled());
						// System.out.println(this.excludePC);
						if (this.excludePC == null)
							return;
						this.pairsGUI.setGraGra(pairsGraGra);
						this.pairsGUI.setCriticalPairs(this.excludePC);
						ParserFactory.generateCriticalPairs(this.excludePC);
						this.label
								.setText("Generate critical pairs ...  Please wait ...");
						startCP.setEnabled(false);
						this.stopCP.setEnabled(true);
						emptyCP.setEnabled(false);
						reduceCP.setEnabled(false);
						consistCP.setEnabled(false);
						this.saveCP.setEnabled(false);
					}
				}
			}
		});
	}
*/
	
	private void stopCPaddActionListener() {
		this.stopCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((ExcludePairContainer) CriticalPairAnalysisSeparated.this.excludePC).stop();
//				CriticalPairAnalysisSeparated.this.startCP.setEnabled(true);
//				CriticalPairAnalysisSeparated.this.emptyCP.setEnabled(true);
//				CriticalPairAnalysisSeparated.this.reduceCP.setEnabled(true);
//				CriticalPairAnalysisSeparated.this.consistCP.setEnabled(true);
				
				CriticalPairAnalysisSeparated.this.stopCP.setEnabled(false);
				CriticalPairAnalysisSeparated.this.saveCP.setEnabled(true);
				CriticalPairAnalysisSeparated.this.showCP.setEnabled(true);
				
				CriticalPairAnalysisSeparated.this.label.setText("Generating critical pairs will stop. Please wait.");
			}
		});
	}

	/*
	private void reduceCPaddActionListener() {
		reduceCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (this.excludePC != null) {
					if (((ExcludePairContainer) excludePC)
							.reduceCriticalPairs())
						this.pairsGUI.setCriticalPairs(this.excludePC);
				}
			}
		});
	}

	private void consistCPaddActionListener() {
		consistCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (this.excludePC != null && !this.excludePC.isEmpty()) {
					if (!this.excludePC.getGrammar().getConstraints().hasMoreElements()) {
						JOptionPane.showMessageDialog(null, 
						"Nothing to check. Any constraint doesn't exist.");
						return;
					}
					
					Thread t = new Thread() {
						public void run() {
							((ExcludePairContainer) this.excludePC)
									.checkConsistency();
						}
					};
					t.setPriority(4);
					t.start();
					while (t.isAlive()) {}
					
					this.pairsGUI.setCriticalPairs(this.excludePC);
				}
			}
		});
	}

	
	private void emptyCPaddActionListener() {
		emptyCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pairsGUI.isOnePairThreadAlive())
					pairsGUI.stopOnePairThread();
				resetLayerFunction();
				this.excludePC = ParserFactory.createEmptyCriticalPairs(pairsGraGra
						.getBasisGraGra(), cpOption.getCriticalPairAlgorithm(),
						cpOption.layeredEnabled());
				// System.out.println(this.excludePC);
				if (this.excludePC != null) {
					pairsGUI.setGraGra(pairsGraGra);
					pairsGUI.setCriticalPairs(this.excludePC);
					startCP.setEnabled(true);
					saveCP.setEnabled(true);
				}
			}
		});
	}
*/
	
	private void saveCPaddActionListener() {
		this.saveCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (CriticalPairAnalysisSeparated.this.pairsGUI.isGenerating()
						|| CriticalPairAnalysisSeparated.this.pairsGUI.isOnePairThreadAlive()) {
					return;
				}
				if (CriticalPairAnalysisSeparated.this.pairsIOGUI == null) {
					CriticalPairAnalysisSeparated.this.pairsIOGUI = new agg.gui.parser.PairIOGUI(CriticalPairAnalysisSeparated.this.parent);
				}
				
//				ConflictsDependenciesContainer cdPC = new ConflictsDependenciesContainer(
//						this.excludePC, dependPC, cpaGraph);
				
				ConflictsDependenciesContainerSaveLoad cdPC = new ConflictsDependenciesContainerSaveLoad(
						CriticalPairAnalysisSeparated.this.excludePC, CriticalPairAnalysisSeparated.this.dependPC, CriticalPairAnalysisSeparated.this.cpaGraph, CriticalPairAnalysisSeparated.this.pairsGraGra);
				
				CriticalPairAnalysisSeparated.this.pairsIOGUI.setCriticalPairContainer(cdPC);
				CriticalPairAnalysisSeparated.this.pairsIOGUI.save();
			}
		});
	}

	protected PairContainer makeEmptyCriticalPairs(int kindOfAlgorithm) {
		// System.out.println("CriticalPairAnalysis.makeEmptyCriticalPairs::
		// kindOfAlgorithm: "+ kindOfAlgorithm);
		if (kindOfAlgorithm == CriticalPairOption.EXCLUDEONLY) {
			if (this.excludePC != null) {
				this.excludePC.clear();
				return this.excludePC;
			} 
			// System.out.println("try create EmptyCriticalPairs");
			PairContainer pc = ParserFactory.createEmptyCriticalPairs(
						this.pairsGraGra.getBasisGraGra(), kindOfAlgorithm, this.cpOption
								.layeredEnabled());
			if (this.conflictDependGraph != null && pc != null) {
				pc.addPairEventListener(this.conflictDependGraph);
				this.conflictDependGraph.setConflictPairContainer(pc);
			}
			return pc;
			
		} else if (kindOfAlgorithm == CriticalPairOption.TRIGGER_DEPEND
				|| kindOfAlgorithm == CriticalPairOption.TRIGGER_SWITCH_DEPEND) {
			if (this.dependPC != null) {
				this.dependPC.clear();
				return this.dependPC;
			} 
			// System.out.println("try create EmptyCriticalPairs 2");
			PairContainer pc = ParserFactory.createEmptyCriticalPairs(
						this.pairsGraGra.getBasisGraGra(), kindOfAlgorithm, this.cpOption
								.layeredEnabled());
			if (this.conflictDependGraph != null && pc != null) {
				pc.addPairEventListener(this.conflictDependGraph);
				this.conflictDependGraph.setDependencyPairContainer(pc);
			}
			return pc;
			
		} else
			return null;
	}

	private void showCPaddActionListener() {
		this.showConflictCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPairContainer(CriticalPair.CONFLICT);
			}
		});

		this.showDependencyCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showPairContainer(CriticalPair.TRIGGER_DEPENDENCY);
			}
		});

		showCPAGraphCPaddActionListener();
	}

	void showPairContainer(int kindOfConflict) {
		if (kindOfConflict == CriticalPair.CONFLICT) {
			if (this.excludePC == null) {
				this.excludePC = makeEmptyCriticalPairs(CriticalPairOption.EXCLUDEONLY);
				resetCP_GUI(this.pairsGraGra, this.excludePC, true);
			} else {
				this.pairsGUI.getGraphDesktop().addCriticalPairTable(
						this.pairsGUI.getGraphDesktop().getConflictPairPanel(), "");
			}
			// startCP.setEnabled(true);
			// saveCP.setEnabled(true);
		} else if (kindOfConflict == CriticalPair.TRIGGER_DEPENDENCY) {
			if (this.dependPC == null) {
				this.dependPC = makeEmptyCriticalPairs(CriticalPairOption.TRIGGER_DEPEND);
				resetCP_GUI(this.pairsGraGra, this.dependPC, true);
			} else {
				this.pairsGUI.getGraphDesktop().addCriticalPairTable(
						this.pairsGUI.getGraphDesktop().getDependPairPanel(), "");
			}
			// startCP.setEnabled(true);
			// this.saveCP.setEnabled(true);
		}
	}

	private void showCPAGraphCPaddActionListener() {
		this.cpaCombiGraphCP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CriticalPairAnalysisSeparated.this.cpaGraph != null) {
					if (CriticalPairAnalysisSeparated.this.conflictDependGraph != null) {
						if (CriticalPairAnalysisSeparated.this.excludePC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getConflictPairPanel()
									.removeParserGUIListener(
											CriticalPairAnalysisSeparated.this.conflictDependGraph);
						if (CriticalPairAnalysisSeparated.this.dependPC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDependPairPanel()
									.removeParserGUIListener(
											CriticalPairAnalysisSeparated.this.conflictDependGraph);
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop()
								.removeActionListenerFromCPAGraphMenu(
										CriticalPairAnalysisSeparated.this.conflictDependGraph);
					}

					CriticalPairAnalysisSeparated.this.conflictDependGraph = new ConflictsDependenciesGraph(
							(ExcludePairContainer) CriticalPairAnalysisSeparated.this.excludePC,
							(ExcludePairContainer) CriticalPairAnalysisSeparated.this.dependPC, 
							CriticalPairAnalysisSeparated.this.cpaGraph, false);
					if (CriticalPairAnalysisSeparated.this.excludePC != null)
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getConflictPairPanel()
								.addParserGUIListener(CriticalPairAnalysisSeparated.this.conflictDependGraph);
					if (CriticalPairAnalysisSeparated.this.dependPC != null)
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDependPairPanel()
								.addParserGUIListener(CriticalPairAnalysisSeparated.this.conflictDependGraph);
					CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().addActionListenerToCPAGraphMenu(
							CriticalPairAnalysisSeparated.this.conflictDependGraph);
					CriticalPairAnalysisSeparated.this.conflictDependGraph.setGraphDesktop(CriticalPairAnalysisSeparated.this.pairsGUI
							.getGraphDesktop());
					
					CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().removeAllGraphFrames();
					CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().removeRuleFrames();
					CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().removeCPAGraphFrame();
					CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDesktop().repaint();
					
//					if (CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getInternalLayoutGraph(
//							CriticalPairAnalysisSeparated.this.cpaGraph.getBasisGraph()) == CriticalPairAnalysisSeparated.this.cpaGraph) {
						try {
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().addGraph(CriticalPairAnalysisSeparated.this.cpaGraph, 400,
									300).setIcon(false);
						} catch (java.beans.PropertyVetoException pve) {
						}
//					}
				} else {
					if (CriticalPairAnalysisSeparated.this.conflictDependGraph != null) {
						if (CriticalPairAnalysisSeparated.this.excludePC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getConflictPairPanel()
									.removeParserGUIListener(
											CriticalPairAnalysisSeparated.this.conflictDependGraph);
						if (CriticalPairAnalysisSeparated.this.dependPC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDependPairPanel()
									.removeParserGUIListener(
											CriticalPairAnalysisSeparated.this.conflictDependGraph);
					}
					CriticalPairAnalysisSeparated.this.conflictDependGraph = null;
					if (CriticalPairAnalysisSeparated.this.excludePC != null 
							&& CriticalPairAnalysisSeparated.this.dependPC != null) {
						CriticalPairAnalysisSeparated.this.conflictDependGraph = new ConflictsDependenciesGraph(
								(ExcludePairContainer) CriticalPairAnalysisSeparated.this.excludePC,
								(ExcludePairContainer) CriticalPairAnalysisSeparated.this.dependPC);
						CriticalPairAnalysisSeparated.this.cpaGraph = CriticalPairAnalysisSeparated.this.conflictDependGraph
								.getGraph();
					} else if (CriticalPairAnalysisSeparated.this.excludePC != null
							&& CriticalPairAnalysisSeparated.this.excludePC.getKindOfConflict() == CriticalPair.CONFLICT) {
						CriticalPairAnalysisSeparated.this.conflictDependGraph = new ConflictsDependenciesGraph(
								(ExcludePairContainer) CriticalPairAnalysisSeparated.this.excludePC, null);
						CriticalPairAnalysisSeparated.this.cpaGraph = CriticalPairAnalysisSeparated.this.conflictDependGraph.getGraph();
					} else if (CriticalPairAnalysisSeparated.this.dependPC != null
							&& CriticalPairAnalysisSeparated.this.dependPC.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY) {
						CriticalPairAnalysisSeparated.this.conflictDependGraph = new ConflictsDependenciesGraph(
								null, (ExcludePairContainer) CriticalPairAnalysisSeparated.this.dependPC);
						CriticalPairAnalysisSeparated.this.cpaGraph = CriticalPairAnalysisSeparated.this.conflictDependGraph.getGraph();
					}
					
					if (CriticalPairAnalysisSeparated.this.cpaGraph != null) {
						if (CriticalPairAnalysisSeparated.this.excludePC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getConflictPairPanel()
									.addParserGUIListener(CriticalPairAnalysisSeparated.this.conflictDependGraph);
						if (CriticalPairAnalysisSeparated.this.dependPC != null)
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDependPairPanel()
									.addParserGUIListener(CriticalPairAnalysisSeparated.this.conflictDependGraph);
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop()
								.addActionListenerToCPAGraphMenu(
										CriticalPairAnalysisSeparated.this.conflictDependGraph);
						CriticalPairAnalysisSeparated.this.conflictDependGraph.setGraphDesktop(CriticalPairAnalysisSeparated.this.pairsGUI
								.getGraphDesktop());
						
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().removeAllGraphFrames();
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().removeRuleFrames();
						CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().getDesktop().repaint();
						try {
							CriticalPairAnalysisSeparated.this.pairsGUI.getGraphDesktop().addGraph(
									CriticalPairAnalysisSeparated.this.cpaGraph, 400,
									300).setIcon(false);
						} catch (java.beans.PropertyVetoException pve) {
						}
						/*
						 * CriticalPairAnalysisSeparated.this.startCP.setEnabled(true); 
						 * CriticalPairAnalysisSeparated.this.stopCP.setEnabled(false);
						 * CriticalPairAnalysisSeparated.this.emptyCP.setEnabled(true); 
						 * CriticalPairAnalysisSeparated.this.reduceCP.setEnabled(true);
						 * CriticalPairAnalysisSeparated.this.consistCP.setEnabled(true);
						 */
						CriticalPairAnalysisSeparated.this.saveCP.setEnabled(true);
						CriticalPairAnalysisSeparated.this.showCP.setEnabled(true);
					} else
						javax.swing.JOptionPane.showMessageDialog(CriticalPairAnalysisSeparated.this.frame,
								"Show CPA graph failed!.", "Warning",
								javax.swing.JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	protected boolean resetLayerFunction() {
		if (this.cpOption.layeredEnabled()) {
//			RuleLayer tmpRL = new RuleLayer(this.pairsGraGra.getBasisGraGra().getEnabledRules());
			this.rlayer = new RuleLayer(this.pairsGraGra.getBasisGraGra().getEnabledRules()); //getListOfRules());
			LayerGUI lgui = new LayerGUI(this.parent, this.rlayer);
			lgui.showGUI();
			if (lgui.isCancelled()) {
				this.cpOption.enableLayered(false);
				this.cpOptionGUI.update();
				this.rlayer = null;
			} 
//			else if (tmpRL.compareTo(rlayer)) {
//				return false;
//			}
			return true;
		} 
		return false;
	}

	protected ParserGUIOption option;

	protected ParserOptionGUI pOptionGUI;

	protected ParserOption pOption;

	protected LayerOption lOption;

	protected CriticalPairOptionGUI cpOptionGUI;

	protected CriticalPairOption cpOption;

	protected CriticalPairAnalysisGUI pairsGUI;

	protected PairIOGUI pairsIOGUI;

	protected PairContainer excludePC, dependPC;

	protected ConflictsDependenciesGraph conflictDependGraph;

	protected EdGraph cpaGraph;

	protected EdGraGra pairsGraGra;

	protected GraGra emptyGraGra;

	protected RuleLayer rlayer;

	protected JMenu menu, showCP;

	protected JMenuItem startCP, stopCP, reduceCP, consistCP, emptyCP, saveCP,
			showConflictCP, showDependencyCP, cpaCombiGraphCP;

//	protected Vector pmlistener;

	protected JFrame parent;

	protected JFrame frame;

	protected JLabel label;

	protected int x, y;

	protected boolean ready;

	protected boolean needToLoad;

	protected boolean loaded;

	protected GraphicsExportJPEG exportJPEG;
}

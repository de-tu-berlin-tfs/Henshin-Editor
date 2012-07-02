/**
 * 
 */
package agg.gui.ruleappl;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import agg.editor.impl.EdGraGra;
import agg.gui.AGGAppl;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.saveload.AGGFileFilter;
import agg.gui.treeview.GraGraTreeView;
import agg.parser.CriticalPairOption;
import agg.ruleappl.ApplRuleSequence;
import agg.ruleappl.RuleSequence;
import agg.util.Pair;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;

/**
 * @author olga
 *
 */
public class ApplicabilityRuleSequence implements 
//								ActionListener,
								TreeViewEventListener {
	
	private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
//	private AGGAppl parent;
	
	private Vector<JMenu> menus;
	private JMenu arsMenu;
	private JMenuItem validateARS, loadARS;
//	private JMenuItem startARS, stopARS, saveARS, showARS;
	
	protected ApplRuleSequence ars;
	protected ApplRuleSequenceDialog arsGUI; 	
	protected ApplicabilityAtGraphResultTable arsAtGraphResultTable;
	protected ApplicabilityWithoutGraphResultTable arsWioGraphResultTable;
	
	protected GraGraTreeView gragraTree;
    protected EdGraGra gragra;
    protected CriticalPairOption cpOption;
    
    protected String graphName = "NULL";
    protected JFileChooser chooser;
	protected String dirName = "";
	protected String fileName;
	
	private int d=-50;
	
	public ApplicabilityRuleSequence(final AGGAppl appl, 
									final GraGraTreeView graTreeView,
									final CriticalPairOption cpOption) {
//		this.parent = appl;
		this.gragraTree = graTreeView;
		this.cpOption = cpOption;
		
		this.menus = new Vector<JMenu>(2);
		createApplRuleSequnceMenu();
		
		this.ars = new ApplRuleSequence(this.cpOption);
		this.arsGUI = new ApplRuleSequenceDialog(appl, this, new Point(100, 10));
		this.arsAtGraphResultTable = new ApplicabilityAtGraphResultTable(this.ars);
		this.arsWioGraphResultTable = new ApplicabilityWithoutGraphResultTable(this.ars);
	}

	public void dispose() {		
		this.gragra = null;
		this.arsGUI.dispose();	
		this.ars.dispose();
		this.arsAtGraphResultTable.clear();
		this.arsWioGraphResultTable.clear();
	}
	
	public boolean isEmpty() {
		return this.ars.getRuleSequences().isEmpty();
	}
	
	public ApplRuleSequence getApplRuleSequence() {
		return this.ars;
	}
	
	public void clear() {
		this.arsAtGraphResultTable.clear();
		this.arsWioGraphResultTable.clear();
		this.ars.clear();
	}
	
	public boolean hasChecked(final int seqIndx) {
		return this.ars.hasChecked(seqIndx);
	}
	
	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}
	
	public RuleSequence createRuleSequence() {
		RuleSequence sequence = new RuleSequence(
				this.gragra.getBasisGraGra(),
				"RuleSequence",
				this.cpOption);
		if (!"NULL".equals(this.graphName)) {
			sequence.setGraph(this.gragra.getBasisGraGra().getGraph(this.graphName));
		}
		this.ars.addRuleSequence(sequence);
		return sequence;
	}
		
	public EdGraGra getGraGra() {
		if (this.gragra == null)			
			this.gragra = this.gragraTree.getGraGra();

		return this.gragra;
	}
	
	public boolean isGraGraLayered() {
		return this.gragra.getBasisGraGra().isLayered();
	}
	
	public Graph getGraph() {
		return this.gragra.getBasisGraGra().getGraph();
	}
	
	public String getGraphName() {
		return this.gragra.getBasisGraGra().getGraph().getName();
	}
	
	public RuleSequence getRuleSequence(int indx) {
		return this.ars.getRuleSequence(indx);
	}
	
	public Pair<Boolean, String> getApplicabilityResultOfRulesequence(final int indx) {
		return this.ars.getApplicabilityResult(indx);
	}
	
	public Pair<Boolean, String> getNonApplicabilityResultOfRulesequence(final int indx) {
		return this.ars.getNonApplicabilityResult(indx);
	}
	
	public boolean removeRuleSequence(int indx) {
		return this.ars.removeRuleSequence(indx);
	}
	
	public RuleSequence copyRuleSequence(int seqIndx) {
		return this.ars.copyRuleSequence(seqIndx);
	}
	
	public void moveRuleSequence(int from, int to) {
		this.ars.moveRuleSequence(from, to);
	}
	
	public void moveRuleInsideSequence(int seqIndx, int from, int to) {
		this.ars.moveRuleInsideSequence(seqIndx, from, to);
	}
	
	public boolean removeResultOfSequence(int indx) {
		if (this.ars.getRuleSequence(indx).getGraph() == null) {
			return removeResultOfSequenceWioGraph(indx);
		} 
		return removeResultOfSequenceAtGraph(indx);
	}
	
	public boolean removeResultOfSequenceAtGraph(int indx) {
		int nb = this.arsAtGraphResultTable.closeResultTables(this.ars.getRuleSequence(indx));
		if (nb == 1) {
			this.d = this.d-50;
		} else if (nb == 2) {
			this.d = this.d-100;
		}
		return this.ars.removeResult(indx);
	}
	
	public boolean removeResultOfSequenceWioGraph(int indx) {
		int nb = this.arsWioGraphResultTable.closeResultTables(this.ars.getRuleSequence(indx));
		if (nb == 1) {
			this.d = this.d-50;
		} else if (nb == 2) {
			this.d = this.d-100;
		}
		return this.ars.removeResult(indx);
	}
	
	protected void createApplRuleSequnceMenu() {
		/* create Critical pair menu */
		this.arsMenu = new JMenu("Applicability of Rule Sequence");
//		arsMenu.setMnemonic('S');

		makeValidateMenu();
		makeLoadMenu();

		this.menus.addElement(this.arsMenu);
	}
	
	private void makeValidateMenu() {
		this.validateARS = new JMenuItem("Validate");
		this.validateARS.setEnabled(true);
		this.arsMenu.add(this.validateARS);
		this.validateARS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ApplicabilityRuleSequence.this.gragraTree.getCurrentGraGra() != null) {
					if (ApplicabilityRuleSequence.this.ars.getGraGra() != null
							&& ApplicabilityRuleSequence.this.gragraTree.getCurrentGraGra().getBasisGraGra() != ApplicabilityRuleSequence.this.ars.getGraGra()) {
						ApplicabilityRuleSequence.this.arsGUI.loadWarning();
					}
					
					if (ApplicabilityRuleSequence.this.gragraTree.getCurrentGraGra().getBasisGraGra() 
							!= ApplicabilityRuleSequence.this.ars.getGraGra()) {
						ApplicabilityRuleSequence.this.arsGUI.clear();	
						
						ApplicabilityRuleSequence.this.gragra = ApplicabilityRuleSequence.this.gragraTree.getGraGra();
						ApplicabilityRuleSequence.this.ars.setGraGra(ApplicabilityRuleSequence.this.gragra.getBasisGraGra());						
					} 
					else if (ApplicabilityRuleSequence.this.arsGUI.isVisible()) {
						ApplicabilityRuleSequence.this.arsGUI.clear();
					}
					
					ApplicabilityRuleSequence.this.ars.setRuleSequences(ApplicabilityRuleSequence.this.gragra.getBasisGraGra().getRuleSequences());
						
					ApplicabilityRuleSequence.this.graphName = ApplicabilityRuleSequence.this.gragra.getBasisGraGra().getGraph().getName();
					ApplicabilityRuleSequence.this.arsGUI.updateRuleSequences(ApplicabilityRuleSequence.this.gragra.getBasisGraGra().getRuleSequences());
					ApplicabilityRuleSequence.this.arsGUI.extendTitle(ApplicabilityRuleSequence.this.gragra.getName());
					
					int indx = ApplicabilityRuleSequence.this.gragra.getBasisGraGra().getIndexOfCurrentRuleSequence();
					if (indx >= 0)
						ApplicabilityRuleSequence.this.arsGUI.selectRuleSequence(indx);						
				}
				ApplicabilityRuleSequence.this.arsGUI.setVisible(true);				
			}
		});	
	}
	
	private void makeLoadMenu() {
		this.loadARS = new JMenuItem("Load");
//		loadARS.setMnemonic('L');
		this.arsMenu.add(this.loadARS);
		this.loadARS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ApplicabilityRuleSequence.this.arsGUI.loadWarning();
					
					ApplRuleSequenceSaveLoad arsLoad = new ApplRuleSequenceSaveLoad();
					arsLoad.load(ApplicabilityRuleSequence.this.ars);
					
					ApplicabilityRuleSequence.this.gragra = arsLoad.layout;
					
					GraGra basegra = ApplicabilityRuleSequence.this.gragra.getBasisGraGra();
					basegra.setDirName(arsLoad.dirName);
					basegra.setFileName(arsLoad.fname);
					ApplicabilityRuleSequence.this.gragra.getTypeSet().setResourcesPath(arsLoad.dirName);
					
					ApplicabilityRuleSequence.this.gragraTree.addGraGra(ApplicabilityRuleSequence.this.gragra);
					ApplicabilityRuleSequence.this.gragra.setChanged(false);
					
					ApplicabilityRuleSequence.this.arsGUI.extendTitle(arsLoad.fname);
					ApplicabilityRuleSequence.this.arsGUI.loadRuleSequences( 
							basegra.getEnabledRules(),
							ApplicabilityRuleSequence.this.ars.getRuleSequences());
					ApplicabilityRuleSequence.this.graphName = basegra.getGraph().getName();					
					ApplicabilityRuleSequence.this.arsGUI.setVisible(true);
					
					/*
					GraGra basegra = load();		
					if (basegra != null) {
						arsGUI.loadWarning();
						gragra = new EdGraGra(basegra);
						gragra.getTypeSet().setResourcesPath(basegra.getDirName());
						gragra.update();					
						gragraTree.addGraGra(gragra);
						arsGUI.extendTitle(basegra.getFileName());
						arsGUI.loadRuleSequences(
//												basegra.getListOfRules(), 
												basegra.getEnabledRules(),
												ars.getRuleSequences());
						graphName = basegra.getGraph().getName();
						arsGUI.setVisible(true);						
					}
					*/
				} catch (Exception ex) {
					if (ex.getMessage() != null && !"".equals(ex.getMessage()))
						JOptionPane.showMessageDialog(ApplicabilityRuleSequence.this.arsGUI, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
				}
			}
		});	
	}
	
/** Implements TreeViewEventListener */
	public void treeViewEventOccurred(TreeViewEvent e) {
		int msgkey = e.getMsg();
		if (msgkey == TreeViewEvent.SELECTED) {
			if (this.arsGUI.isVisible() && e.getData().isGraGra()) {
				if (this.arsGUI.isEmpty()) {
					this.gragra = e.getData().getGraGra();	
					this.arsGUI.extendTitle(this.gragra.getName());
					this.arsGUI.updateGraphName();
				} else if (this.gragra != e.getData().getGraGra()) {
					this.arsGUI.close();
				}
			} else if (e.getData().isGraph() && !e.getData().isTypeGraph()) {
				if (this.gragra == e.getData().getGraph().getGraGra()) {
					if (this.arsGUI.isVisible()) {						
						this.graphName = this.gragra.getGraph().getName();
						/*
						if (arsGUI.useGraphToCheck) {
							int indx = arsGUI.getIndexOfSelectedSequence();	
							if (indx >= 0) {
								RuleSequence ruleSeq = ars.getRuleSequence(indx);
								int answer = 0;
								if (ruleSeq.getGraph() != gragra.getGraph().getBasisGraph()
										&& (ruleSeq.isChecked()
												|| ruleSeq.isObjectFlowActive())) {
									Object[] options = { "OK", "Cancel" };
									answer = JOptionPane.showOptionDialog(
														arsGUI,
														"<html><body>"
														+"Currently selected rule sequence contains an object flow\n"
														+"or is already checked.\n"
														+"The results will be lost after graph reset.",
														"Reset graph", JOptionPane.DEFAULT_OPTION,
														JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
								}
								if (answer == 0) {
									ruleSeq.setGraph(gragra.getGraph().getBasisGraph());
									
									arsGUI.updateGraphName();
								}
							} 
						}
						*/
					}
				} 
			}			
		}
			
		if (this.arsGUI.isVisible()) {
			if (msgkey == TreeViewEvent.DELETED) {
				if (e.getData().isGraGra()) {
					if (this.gragra == e.getData().getGraGra()) {
						dispose();
					}
				}
			} 
//			else if (msgkey == TreeViewEvent.RULE_DELETED) {
//				if (!arsGUI.isEmpty()) {
//						
//				}
//			}
		}
	}
	
	public boolean check(final int seqIndx) {
		boolean result = this.ars.check(seqIndx);
		return result;
	}
	
	public boolean check(final RuleSequence sequence) {
		return this.ars.check(sequence);
	}
	
	public void save() {
		if (this.dirName.equals("")) {
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		} else {
			this.chooser = new JFileChooser(this.dirName);
		}
		this.chooser.setFileFilter(new AGGFileFilter("rsx", "AGG Files (.rsx)"));
		int returnVal = this.chooser.showSaveDialog(this.arsGUI);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				this.ars.save(this.fileName);
			}
		}
	}
	
	protected GraGra load() throws Exception {
		GraGra gra = null;
		if (this.dirName.equals("")) {
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		} else {
			this.chooser = new JFileChooser(this.dirName);
		}
		this.chooser.setFileFilter(new AGGFileFilter("rsx", "AGG Files (.rsx)"));
		int returnVal = this.chooser.showOpenDialog(this.arsGUI);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				try {
					gra = this.ars.load(this.dirName+File.separator+this.fileName);
					if (gra != null) {
						gra.setDirName(this.dirName);
						gra.setFileName(this.fileName);
					}
				} catch (Exception ex) {
					throw ex;
				}
			}
		}		
		return gra;
	}

	
	public void refreshGraGra() {
		this.gragraTree.fireTreeViewEvent(new TreeViewEvent(this, TreeViewEvent.REFRESH_GRAGRA));
	}
	
	protected void closeGraGra(){
		if (this.gragra != null) {
			boolean grammarchanged = this.gragra.isChanged();
			this.gragra.setChanged(false);
			
//			Object[] options = { "Close", "Cancel" };
			int answer = 1; 
//						JOptionPane
//						.showOptionDialog(
//							arsGUI,
//							"<html><body>Do you want to close the grammar <br>"
//									+ "of the rule sequences, too?"
//									+ "</body></html>",
//							"Close GraGra", JOptionPane.DEFAULT_OPTION,
//							JOptionPane.WARNING_MESSAGE, null, options,
//							options[0]);				
			if (answer == 0) {
				this.gragra.setChanged(grammarchanged);	
				this.gragraTree.deleteCurrentGraGra(this.gragra);
			} else {			
				this.gragra.setChanged(grammarchanged);	
			}
		} 
	}
	
	
	public void showApplicabilityResult(final int indx) {
		if (this.getRuleSequence(indx).getGraph() == null) {
			showApplicabilityResultWithoutGraph(indx);
		} else {
			showApplicabilityResultAtGraph(indx);
		}
	}
	
	public void showNonApplicabilityResult(final int indx) {
		if (this.getRuleSequence(indx).getGraph() == null) {
			showNonApplicabilityResultWithoutGraph(indx);
		} else {
			showNonApplicabilityResultAtGraph(indx);
		}
	}
	
	public void showApplicabilityResultAtGraph(final int indx) {
		if (this.d+300 >= screenSize.width
				|| this.d+200 >= screenSize.height) {
			this.d = -50;
		}
		this.d = this.d + 50; 		
		Point location = new Point(50+this.d, 100+this.d);
		this.arsAtGraphResultTable.showApplicabilityResult(location, indx);
	}
	
	public void showNonApplicabilityResultAtGraph(final int indx) {
		if (this.d+300 >= screenSize.width
				|| this.d+200 >= screenSize.height) {
			this.d = -50;
		}
		this.d = this.d + 50; 
		Point location = new Point(50+this.d, 100+this.d);
		this.arsAtGraphResultTable.showNonApplicabilityResult(location, indx);
	}
	
	public void closeAllResultTables() {
		this.arsAtGraphResultTable.closeAllResultTables();
		this.arsWioGraphResultTable.closeAllResultTables();
		this.d = -50;
	}
	
	public void showApplicabilityResultWithoutGraph(final int indx) {
		if (this.d+300 >= screenSize.width
				|| this.d+200 >= screenSize.height) {
			this.d = -50;
		}
		this.d = this.d + 50; 
		Point location = new Point(50+this.d, 100+this.d);
		this.arsWioGraphResultTable.showApplicabilityResult(location, indx);
	}
	
	public void showNonApplicabilityResultWithoutGraph(final int indx) {
		if (this.d+300 >= screenSize.width
				|| this.d+200 >= screenSize.height) {
			this.d = -50;
		}
		this.d = this.d + 50; 
		Point location = new Point(50+this.d, 100+this.d);
		this.arsWioGraphResultTable.showNonApplicabilityResult(location, indx);
	}
	
	
//	public void actionPerformed(ActionEvent e) {}
	
}

package agg.gui.termination;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import agg.editor.impl.EdGraGra;
import agg.gui.AGGAppl;
import agg.gui.event.TreeViewEvent;
import agg.gui.event.TreeViewEventListener;
import agg.gui.treeview.GraGraTreeView;
import agg.termination.TerminationLGTS;
import agg.termination.TerminationLGTSInterface;
import agg.termination.TerminationLGTSTypedByTypeGraph;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeSet;

/**
 * The class creates an AGG termination analyzer .
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class TerminationAnalysis implements TreeViewEventListener {

	/** Creates a new instance of the AGG analysis */
	public TerminationAnalysis(AGGAppl appl, GraGraTreeView treeView) {
		this.parent = appl;
		this.treeView = treeView;
		this.terminationLGTS = new TerminationLGTS();
		this.menus = new Vector<JMenu>(1);
		createAnalysisMenu();
	}

	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	public JMenuItem getMenuItem() {
		return this.mi_terminationLGTS;
	}

	/** Sets the gragra to analyze */
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
		if (this.gragra != null) {
			if (this.terminationLGTS == null) {
				this.terminationLGTS = new TerminationLGTS();
			}
			if (this.gragra.getBasisGraGra() != this.terminationLGTS.getGrammar()) {	
				if (this.gragra.getTypeGraph() != null
						&& this.gragra.getLevelOfTypeGraphCheck() > TypeSet.DISABLED) {
					if (this.terminationLGTS instanceof TerminationLGTS) {
						((TerminationLGTS) this.terminationLGTS).dispose();
						this.terminationLGTS = new TerminationLGTSTypedByTypeGraph();
					}
				} else if (this.terminationLGTS instanceof TerminationLGTSTypedByTypeGraph) {
					((TerminationLGTSTypedByTypeGraph) this.terminationLGTS).dispose();
					this.terminationLGTS = new TerminationLGTS();
				} 
				
				this.terminationLGTS.setGrammar(this.gragra.getBasisGraGra());				
			} 
		}
	}

	/** Implements TreeViewEventListener */
	public void treeViewEventOccurred(TreeViewEvent e) {
		if (e.getMsg() == TreeViewEvent.SELECTED) {
			if (e.getData().isGraGra()) {
				boolean shouldResetGUI = this.terminationLGTS.getGrammar() 
											!= e.getData().getGraGra().getBasisGraGra();
				
				setGraGra(e.getData().getGraGra());
				
				if (shouldResetGUI && this.terminationLGTSGUI != null) {					
					this.terminationLGTSGUI.reinit(this.terminationLGTS);
				}
			}
		}
		else if (e.getMsg() == TreeViewEvent.RULE_LAYER) {
			if (e.getData().isRule()
					&& e.getData().getRule() != null) {
				if (this.terminationLGTS.getGrammar().isElement(e.getData().getRule().getBasisRule())) {
					if (this.terminationLGTSGUI != null && this.terminationLGTSGUI.isVisible()) {
						if (this.terminationLGTS.isValid()) {							
							int answ = JOptionPane.YES_OPTION;
//							answ = JOptionPane.showConfirmDialog(parent, 
							JOptionPane.showMessageDialog(this.parent,		
								"<html><body>"
								+"You have changed the rule layer.<br>"
								+"Please reset termination data and check it again."
								+"</body></html>");
							if (answ == JOptionPane.YES_OPTION) {
								this.terminationLGTS.resetGrammar();
								this.terminationLGTSGUI.reinit();
							} else if (answ == JOptionPane.NO_OPTION
									|| answ == JOptionPane.CANCEL_OPTION) {								
							}
							
						} else {
							this.terminationLGTS.resetGrammar();
							this.terminationLGTSGUI.reinit();
						}
					}
				}
			}
		} else if (e.getMsg() == TreeViewEvent.RULE_PRIORITY) {
			if (e.getData().isRule()
					&& e.getData().getRule() != null) {
				if (this.terminationLGTS.getGrammar().isElement(e.getData().getRule().getBasisRule())) {
					if (this.terminationLGTSGUI != null && this.terminationLGTSGUI.isVisible()) {
						if (this.terminationLGTS.isValid()) {							
							int answ = JOptionPane.YES_OPTION;
//							this.answ = JOptionPane.showConfirmDialog(parent, 
							JOptionPane.showMessageDialog(this.parent,		
								"<html><body>"
								+"You have changed the rule priotity.<br>"
								+"Please reset termination data and check it again."
								+"</body></html>");
							if (answ == JOptionPane.YES_OPTION) {
								this.terminationLGTS.resetGrammar();
								this.terminationLGTSGUI.reinit();
							} else if (answ == JOptionPane.NO_OPTION
									|| answ == JOptionPane.CANCEL_OPTION) {								
							}
							
						} else {
							this.terminationLGTS.resetGrammar();
							this.terminationLGTSGUI.reinit();
						}						
					}
				}
			}
		}
		else if (e.getMsg() == TreeViewEvent.DELETED) {
//			System.out.println("TerminationAnalysis.treeViewEventOccurred:: TreeViewEvent.DELETED");
			if (e.getData().isGraGra()) {
				if (this.gragra != null 
						&& this.gragra.getBasisGraGra() == this.terminationLGTS.getGrammar()) {
					if (this.terminationLGTSGUI != null && this.terminationLGTSGUI.isVisible()) {
						this.terminationLGTSGUI.setVisible(false);
					}
					this.gragra = null;
					this.terminationLGTS.setGrammar(null);
				}
			}
		}
	}

	/** Creates a menu */
	protected void createAnalysisMenu() {
		this.terminationMenu = new JMenu("Termination Check");
		// terminationMenu.setMnemonic('n');
		this.mi_terminationLGTS = new JMenuItem("Termination of LGTS");
		this.mi_terminationLGTS.setMnemonic('L');
		this.terminationMenu.add(this.mi_terminationLGTS);
		this.mi_terminationLGTS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ((TerminationAnalysis.this.terminationLGTSGUI != null) && TerminationAnalysis.this.isLGTSVisible) {
					TerminationAnalysis.this.terminationLGTSGUI.setVisible(false);
				}
				setGraGra(TerminationAnalysis.this.treeView.getCurrentGraGra());
				if (TerminationAnalysis.this.gragra != null) {
					Rule failed = BaseFactory.theFactory().checkApplCondsOfRules(TerminationAnalysis.this.gragra.getBasisGraGra().getListOfRules());
					if (failed == null) {
						if (TerminationAnalysis.this.terminationLGTSGUI == null) {
							TerminationAnalysis.this.terminationLGTSGUI = new TerminationDialog(TerminationAnalysis.this.parent,
									TerminationAnalysis.this.terminationLGTS);
						} else {
							TerminationAnalysis.this.terminationLGTSGUI.init(TerminationAnalysis.this.terminationLGTS);
						}
						TerminationAnalysis.this.terminationLGTSGUI.showGUI();
						TerminationAnalysis.this.isLGTSVisible = true;
					}
					else {
						JOptionPane.showMessageDialog(parent, 
								"Cannot check.\n"
								+"Rule set of the current grammar contains at least one invalid rule.\n"						
								+failed.getName()+":    "+failed.getErrorMsg(),	
								"Invalid Rule",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		this.menus.addElement(this.terminationMenu);
	}

	protected TerminationDialog terminationLGTSGUI;

	protected TerminationLGTSInterface terminationLGTS;

	protected agg.gui.AGGAppl parent;

	protected EdGraGra gragra;

	protected agg.gui.treeview.GraGraTreeView treeView;

	private JMenu terminationMenu;

	private JMenuItem mi_terminationLGTS;

	private Vector<JMenu> menus;

	protected boolean isLGTSVisible;

}

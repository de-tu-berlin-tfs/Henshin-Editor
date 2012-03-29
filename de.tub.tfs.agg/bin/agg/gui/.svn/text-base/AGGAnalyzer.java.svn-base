package agg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import agg.gui.cpa.CriticalPairAnalysis;
import agg.gui.event.EditEventListener;
import agg.gui.event.EditEvent;
import agg.gui.options.OptionGUI;
import agg.gui.ruleappl.ApplicabilityRuleSequence;
import agg.gui.termination.TerminationAnalysis;
import agg.gui.treeview.GraGraTreeView;

/**
 * The class creates an AGG analizer.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class AGGAnalyzer implements EditEventListener {

	/** Creates a new instance of the AGG analysis */
	public AGGAnalyzer(AGGAppl appl, GraGraTreeView tree) {
		this.parent = appl;
//		this.treeView = tree;
		this.menus = new Vector<JMenu>(1);
		this.menu = new JMenu("Analyzer", true);
		this.menu.setMnemonic('A');
		this.menus.addElement(this.menu);
		
		this.menu.addSeparator();
		final JMenuItem options = new JMenuItem("CPA Options...");
		this.menu.add(options);
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptionGUI();
			}
		});
	}

	protected void showOptionGUI() {
		if (this.parent != null) {
			this.parent.getPreferences().showOptionGUI(OptionGUI.CRITICAL_PAIRS);
		}
	}
	
	public void addCriticalPairAnalysis(CriticalPairAnalysis cpa) {
		this.criticalPairAnalysis = cpa;
		for (Enumeration<JMenu> e = cpa.getMenus(); e.hasMoreElements();) {
			this.menu.add(e.nextElement(), 0);
		}
	}

	public void addApplicabilityRuleSequence(ApplicabilityRuleSequence applRuleSeq) {
		this.applRuleSequence = applRuleSeq;
		for (Enumeration<JMenu> e = this.applRuleSequence.getMenus(); e.hasMoreElements();) {
			this.menu.add(e.nextElement(), 1);
		}
	}
	
	public void addConstraints(AGGConstraints constraints) {
		this.aggConstraints = constraints;
		for (Enumeration<JMenu> e = constraints.getMenus(); e.hasMoreElements();) {
			this.menu.add(e.nextElement(), 2);
		}
	}

	public void addTerminationAnalysis(TerminationAnalysis term) {
		this.menu.add(term.getMenuItem(), 3);
	}

	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	public CriticalPairAnalysis getCriticalPairAnalysis() {
		return this.criticalPairAnalysis;
	}

	public AGGConstraints getConstraints() {
		return this.aggConstraints;
	}

	public void editEventOccurred(EditEvent e) {
		// System.out.println("AGGAnalyzer.editEventOccurred "+e.getObject());
		if (e.getMsg() == EditEvent.MENU_KEY)
			if (e.getMessage().equals("Analyzer"))
				this.menu.doClick();
	}

	private CriticalPairAnalysis criticalPairAnalysis;

	private ApplicabilityRuleSequence applRuleSequence;
	
	private AGGConstraints aggConstraints;

	// private TerminationAnalysis terminationAnalysis;
	private final JMenu menu;

	private final Vector<JMenu> menus;

//	private final GraGraTreeView treeView;
	
	private final agg.gui.AGGAppl parent;
}

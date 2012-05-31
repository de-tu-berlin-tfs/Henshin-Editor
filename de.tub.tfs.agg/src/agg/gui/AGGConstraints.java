package agg.gui;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import agg.gui.treeview.GraGraTreeView;

public class AGGConstraints {

//	private final AGGAppl parent;

	private final Vector<JMenu> menus;

	public AGGConstraints(AGGAppl appl, GraGraTreeView tree) {
//		parent = appl;
		this.menus = new Vector<JMenu>(1);
		createMenus(tree);
	}

	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	private void createMenus(GraGraTreeView view) {
		this.menus.clear();
		JMenu m = new JMenu("Consistency Check");
		m.setMnemonic('o');

		JMenuItem it = m.add(new JMenuItem("Check Atomics"));
		it.setActionCommand("checkAtomics");
		it.addActionListener(view);

		it = m.add(new JMenuItem("Check Constraints"));
		it.setActionCommand("checkConstraints");
		it.addActionListener(view);

		// it = m.add(new JMenuItem("Create Post Conditions"));
		// it.setActionCommand("convertConstraints");
		// it.addActionListener(view);

		this.menus.add(m);
	}

}

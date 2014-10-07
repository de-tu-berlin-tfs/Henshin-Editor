// $Id: FilePopupMenu.java,v 1.5 2010/09/20 14:28:57 olga Exp $

package agg.gui.popupmenu;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JMenu;
import agg.gui.treeview.TreeViewActionAdapter;

@SuppressWarnings("serial")
public class FilePopupMenu extends JPopupMenu {

	public FilePopupMenu(ActionListener listener) {
		super("File");
		this.actionListener = listener;

		JMenuItem mi = add(new JMenuItem("New GraGra            Ctrl+N"));
//		miNewGraGra = mi;
		mi.setEnabled(true);
		mi.setActionCommand("newGraGra");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('G');

		addSeparator();

		mi = add(new JMenuItem("Open                      Ctrl+O"));
//		miOpen = mi;
		mi.setEnabled(true);
		mi.setActionCommand("open");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('O');

		mi = add(new JMenuItem("Save                       Ctrl+S"));
		this.miSave = mi;
		mi.setEnabled(false);
		mi.setActionCommand("save");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('S');

		mi = add(new JMenuItem("Save As                   Alt+S"));
		this.miSaveAs = mi;
		mi.setEnabled(false);
		mi.setActionCommand("saveAs");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic(KeyEvent.VK_A);
		// mi.setDisplayedMnemonicIndex(5);

		addSeparator();

		mi = add(new JMenuItem("Open (Base)"));
//		miOpenBase = mi;
		mi.setActionCommand("openBase");
		mi.addActionListener(this.actionListener);

		mi = add(new JMenuItem("Save As (Base)"));
		this.miSaveAsBase = mi;
		mi.setEnabled(false);
		mi.setActionCommand("saveAsBase");
		mi.addActionListener(this.actionListener);

		addSeparator();

		this.submExport = (JMenu) add(new JMenu("Export"));
		this.submExport.setEnabled(false);
		// submExport.setMnemonic('E');

		mi = this.submExport.add(new JMenuItem("JPEG         Shift+J"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGraphJPEG");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('J');

		mi = this.submExport.add(new JMenuItem("GXL          Shift+X"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGXL");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('X');

		mi = this.submExport.add(new JMenuItem("GTXL        Shift+T"));
		mi.setEnabled(true);
		mi.setActionCommand("exportGTXL");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('T');
		
		this.submImport = (JMenu) add(new JMenu("Import"));
		this.submImport.setEnabled(true);
		// submImport.setMnemonic('I');

		mi = this.submImport.add(new JMenuItem(
				"GGX                         Shift+Alt+G"));
		mi.setEnabled(true);
		mi.setActionCommand("importGGX");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('G');

		mi = this.submImport.add(new JMenuItem(
				"GXL                          Shift+Alt+X"));
		mi.setEnabled(true);
		mi.setActionCommand("importGXL");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('X');

		// mi = submImport.add(new JMenuItem("GTXL"));
		mi = new JMenuItem("GTXL");
		mi.setEnabled(false);
		mi.setActionCommand("importGTXL");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('T');

		mi = this.submImport.add(new JMenuItem("OMONDO XMI ( .ecore )   Shift+Alt+O"));
		mi.setEnabled(true);
		mi.setActionCommand("importOMONDOXMI");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('O');
		
		addSeparator();

		mi = add(new JMenuItem("Close GraGra           Ctrl+W"));
		this.miDelGraGra = mi;
		mi.setEnabled(false);
		mi.setActionCommand("deleteGraGra");
		mi.addActionListener(this.actionListener);
		// mi.setMnemonic('D');

		// mi = (JMenuItem) add(new JMenuItem("Delete Rule"));
		// mi = new JMenuItem("Delete Rule");
		// miDelRule = mi;
		// mi.setEnabled(false);
		// mi.setActionCommand("deleteRule");
		// mi.addActionListener(actionListener);

		// mi = (JMenuItem) add(new JMenuItem("Delete NAC"));
		// mi = new JMenuItem("Delete NAC");
		// miDelNAC = mi;
		// mi.setEnabled(false);
		// mi.setActionCommand("deleteNAC");
		// mi.addActionListener(actionListener);

		// addSeparator();

		mi = new JMenuItem("Print");
		// (JMenuItem) add(new JMenuItem("Print"));
		// miPrint = mi;
		// mi.setEnabled(false);
		// mi.setActionCommand("print");
		// mi.addActionListener(actionListener);

		pack();
		setBorderPainted(true);
		// setDefaultLightWeightPopupEnabled(false);
	}

	public void resetEnabledOfFileMenuItems(String command) {
		if (command.equals("newGraGra") || command.equals("open")) {
			// miNewTypeGraph.setEnabled(true);
			// miNewRule.setEnabled(true);
			// miNewNAC.setEnabled(true);
			this.miSave.setEnabled(true);
			this.miSaveAs.setEnabled(true);
			this.miSaveAsBase.setEnabled(true);
			this.submExport.setEnabled(true);
			this.submImport.setEnabled(true);
			this.miDelGraGra.setEnabled(true);
			// miDelRule.setEnabled(true);
			// miDelNAC.setEnabled(true);
			// miPrint.setEnabled(true);
		} else if (command.equals("delete") || command.equals("deleteGraGra")) {
			if (this.actionListener instanceof TreeViewActionAdapter) {
				if (((TreeViewActionAdapter) this.actionListener).getTreeView().getTree().getRowCount() == 1) {
					// miNewTypeGraph.setEnabled(false);
					// miNewRule.setEnabled(false);
					// miNewNAC.setEnabled(false);
					this.miSave.setEnabled(false);
					this.miSaveAs.setEnabled(false);
					this.miSaveAsBase.setEnabled(false);
					this.submExport.setEnabled(false);
					this.miDelGraGra.setEnabled(false);
					// miDelRule.setEnabled(false);
					// miDelNAC.setEnabled(false);
					// miPrint.setEnabled(false);
				}
			}
		}
	}

	private ActionListener actionListener;

	private JMenuItem 
//			miNewGraGra, miNewTypeGraph, miNewRule, miNewNAC, miOpen,
			miSave, miSaveAs, 
//			miOpenBase, 			 
//			miDelRule, miDelNAC, miPrint,
			miSaveAsBase, miDelGraGra;

	private JMenu submExport, submImport;

}

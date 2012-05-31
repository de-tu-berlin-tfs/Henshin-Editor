package agg.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import agg.gui.event.EditEventListener;
import agg.gui.event.EditEvent;
import agg.gui.options.AbstractOptionGUI;
import agg.gui.options.GraTraOptionGUI;
import agg.gui.options.OptionGUI;

/**
 * The class creates an AGG preferences.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class AGGPreferences implements EditEventListener {

	public AGGPreferences(JFrame f) {
		this.optionGUI = new OptionGUI(f, " Options ", false);
		this.menus = new Vector<JMenu>(2);
		this.mainMenu = new JMenu("Preferences", true);
		this.mainMenu.setMnemonic('P');
		this.optionsMenu = new JMenuItem("Options...");
		this.optionsMenu.setMnemonic('O');
//		this.defaultsMenu = new JMenu("Defaults", true);
//		this.defaultsMenu.setMnemonic('D');

		this.defaults = new Vector<JMenuItem> ();
		
		makePreferencesMenu();
	}

	public void editEventOccurred(EditEvent e) {
		if (e.getMsg() == EditEvent.MENU_KEY) {
			if (e.getMessage().equals("Preferences"))
				this.mainMenu.doClick();
		}
	}

	public Enumeration<JMenu> getMenus() {
		return this.menus.elements();
	}

	public OptionGUI getOptionGUI() {
		return this.optionGUI;
	}

	public void showOptionGUI() {
		if (!this.optionGUI.isVisible()) {
			AbstractOptionGUI optgui = this.optionGUI.getGuiComponent("Transformation");
			if (optgui instanceof GraTraOptionGUI) {
				((GraTraOptionGUI)optgui).update();
			}
			this.optionGUI.setVisible(true);
			this.optionGUI.toFront();
		} else {
			this.optionGUI.setVisible(false);
			this.optionGUI.setVisible(true);
			this.optionGUI.toFront();
		}
	}
	
	public void showOptionGUI(final int kind) {
		showOptionGUI();
		this.optionGUI.selectOptions(kind);
	}
	
	/** Makes the preferences menu */
	protected void makePreferencesMenu() {
		this.mainMenu.setEnabled(true);
		this.mainMenu.add(this.optionsMenu);
		this.optionsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showOptionGUI();				
			}
		});
		
		addDefaults();
		
		this.menus.addElement(this.mainMenu);
	}

	public void addActionListenerOfDefaults(ActionListener al) {
		for (int i = 0; i < this.defaults.size(); i++) {
			Object item = this.defaults.get(i);
			if (item instanceof JMenu) {
				final JMenu m = (JMenu) item;
				for (int j = 0; j < m.getItemCount(); j++) {
					JMenuItem mi = m.getItem(j);	
					if (mi != null) {
						mi.addActionListener(al);
					}
				}
			} 
			else if (item instanceof JCheckBoxMenuItem) {
				((JCheckBoxMenuItem)item).addActionListener(al);
			}
		}
	}

	private void addDefaults() {
		this.mainMenu.addSeparator();
		
		// font style menu
		final JMenu font = new JMenu("Font", true);
		font.setMnemonic('F');
		// String[] fontLabels = new String[] {"Plain", "Bold", "Italic"};
		// String[] fontCommands = new String[] {"plain", "bold", "italic"};
		final String[] fontLabels = new String[] {"Plain", "Bold"};
		final String[] fontCommands = new String[] {"plain", "bold"};
		final char[] fontMnemonics = new char[] { 'b', 'p' };
		font.setText("Font".concat("         ( Plain ) "));			
		for (int i = 0; i < fontLabels.length; i++) {
			final JMenuItem mi = new JMenuItem(fontLabels[i]);
			mi.setActionCommand(fontCommands[i]);
			mi.setMnemonic(fontMnemonics[i]);
			mi.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					font.setText("Font".concat("         ( ")
							.concat(((JMenuItem)e.getSource()).getText())
							.concat(" ) "));
				}});
			font.add(mi);
		}
//		defaultsMenu.add(font);
		this.defaults.add(font);
		this.mainMenu.add(font);

		// font size menu
		final JMenu size = new JMenu("Font Size", true);
		size.setMnemonic('z');
		// "tiny" has size 8 and without attributes //
		final String[] sizeLabels = new String[] { "LARGE", "large", "small",
				"tiny" };
		final String[] sizeCommands = new String[] { "LARGE", "large", "small",
				"tiny" };
		final char[] sizeMnemonics = new char[] { 'G', 'l', 's', 'y' };
		size.setText("Font Size".concat("  ( LARGE ) "));
		for (int i = 0; i < sizeLabels.length; i++) {
			final JMenuItem mi = new JMenuItem(sizeLabels[i]);
			mi.setActionCommand(sizeCommands[i]);
			mi.setMnemonic(sizeMnemonics[i]);
			mi.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					size.setText("Font Size".concat("  ( ")
							.concat(((JMenuItem)e.getSource()).getText())
							.concat(" ) "));
				}});
			size.add(mi);
		}		
//		defaultsMenu.add(size);
		this.defaults.add(size);
		this.mainMenu.add(size);
		
		// scale menu
		final JMenu scale = new JMenu("Zoom", true);
		scale.setMnemonic('s');
		final String[] scaleLabels = new String[] { "0.2", "0.3", "0.5", "0.7", "1.0", "1.5",
				"2.0" };
		final String[] scaleCommands = new String[] { "0.2", "0.3", "0.5", "0.7", "1.0",
				"1.5", "2.0" };
		final char[] scaleMnemonics = new char[] { '0', '3', '5', '7', '.', '1', '2' };
		scale.setText("Zoom".concat("        ( 1.0 ) "));
		for (int i = 0; i < scaleLabels.length; i++) {
			final JMenuItem mi = new JMenuItem(scaleLabels[i]);
			mi.setEnabled(true);
			mi.setActionCommand(scaleCommands[i]);
			mi.setMnemonic(scaleMnemonics[i]);
			mi.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					scale.setText("Zoom".concat("        ( ")
							.concat(((JMenuItem)e.getSource()).getText())
							.concat(" ) "));	
				}});
			scale.add(mi);
		}		
		scale.addSeparator();		
		final JCheckBoxMenuItem scaleGraphOnly = new JCheckBoxMenuItem("Host Graph", true);
		scaleGraphOnly.setActionCommand("scaleGraphOnly");
		scale.add(scaleGraphOnly);		
//		defaultsMenu.add(scale);
		this.defaults.add(scale);
		this.mainMenu.add(scale);
		
		this.mainMenu.addSeparator();

		this.nonparallel = new JCheckBoxMenuItem("No Parallel Edges of Graphs", false);
		this.nonparallel.setActionCommand("nonparallelArcs");		
		this.defaults.add(this.nonparallel);
		this.mainMenu.add(this.nonparallel);
		
		this.undirected = new JCheckBoxMenuItem("Undirected Edges of Graphs", false);
		this.undirected.setToolTipText("This setting is effective for new GraGra only.");
		this.undirected.setActionCommand("undirectedArcs");		
		this.defaults.add(this.undirected);
		this.mainMenu.add(this.undirected);
		
		this.mainMenu.addSeparator();
		
		// show attributes menu
		final JMenu showAttributes = new JMenu("Show Attributes", true);			
		final JCheckBoxMenuItem showAttrsOfGraph = new JCheckBoxMenuItem("Graph", true);
		showAttrsOfGraph.setActionCommand("showAttributesOfGraph");
		showAttributes.add(showAttrsOfGraph);
		final JCheckBoxMenuItem showAttrsOfRule = new JCheckBoxMenuItem("Rule", true);
		showAttrsOfRule.setActionCommand("showAttributesOfRule");
		showAttributes.add(showAttrsOfRule);
		final JCheckBoxMenuItem showAttrsOfTypeGraph = new JCheckBoxMenuItem("Type Graph", true);
		showAttrsOfTypeGraph.setActionCommand("showAttributesOfTypeGraph");
		showAttributes.add(showAttrsOfTypeGraph);
//		defaultsMenu.add(showAttributes);
		this.defaults.add(showAttributes);
		this.mainMenu.add(showAttributes);
		
		this.mainMenu.addSeparator();
				
		// menu item: keep type editor on top 
		// rights of the gragra editor 
		this.typesOnTop = new JCheckBoxMenuItem("Keep Types On Top", false);
		this.typesOnTop.setActionCommand("typesOnTop");		
		this.defaults.add(this.typesOnTop);
		this.mainMenu.add(this.typesOnTop);
	}

	public void selectTypesOnTop(boolean b) {
		if (b != this.typesOnTop.isSelected()) 
			this.typesOnTop.doClick();
	}
	
	public void selectNoArcParallel(boolean b) {
		if (b != this.nonparallel.isSelected()) 
			this.nonparallel.setSelected(b);
	}
	
	public void selectArcUndirected(boolean b) {
		if (b != this.undirected.isSelected()) 
			this.undirected.setSelected(b);
	}
	
	private final JMenu mainMenu;

	private final Vector<JMenu> menus;
	
	private final Vector<JMenuItem> defaults;
	
	private final JMenuItem optionsMenu;
	
	private JCheckBoxMenuItem typesOnTop, undirected, nonparallel;
	
//	private final JMenu defaultsMenu;

	protected final OptionGUI optionGUI;

}

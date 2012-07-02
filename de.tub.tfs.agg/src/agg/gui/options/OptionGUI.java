package agg.gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import agg.gui.parser.event.StatusMessageEvent;
import agg.gui.parser.event.StatusMessageListener;

/**
 * This is the main class for all option. This class holds a tabbed pane to
 * provide different option panels. At the moment there are parser, layer and
 * critical pair option.
 * 
 * @version $Id: OptionGUI.java,v 1.4 2010/09/23 08:20:39 olga Exp $
 * @author $Author: olga $
 */
public class OptionGUI extends JDialog implements ActionListener,
		ChangeListener {

	public final static int GENERAL = 0;
	public final static int TRANSFORMATION = 1;
	public final static int PARSER = 2;
	public final static int CRITICAL_PAIRS = 3;
	public final static int LAYOUTER = 4;
	
	private Vector<StatusMessageListener> listener;

	private JButton closeButton;

	private JTabbedPane tabbedPane;

	private JPanel dialogPanel;

	private WindowAdapter wl;

	private final Hashtable<String, AbstractOptionGUI> title2optiongui;
	/**
	 * Creates the main option window with the different option.
	 * 
	 * @param parent
	 *            The parent frame.
	 * @param title
	 *            The title for the option window.
	 * @param modal
	 *            The modality of the window.
	 */
	public OptionGUI(JFrame parent, String title, boolean modal) {
		super();
		setModal(modal);
		setTitle(title);
		// setSize(400, 500);
		setLocation(200, 100);

		this.listener = new Vector<StatusMessageListener>();

		this.wl = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				// System.out.println("windowClosing");
				setVisible(false);
			}
		};

		this.addWindowListener(this.wl);

		this.title2optiongui = new Hashtable<String, AbstractOptionGUI>();
		
		this.tabbedPane = new JTabbedPane();
		this.tabbedPane.addChangeListener(this);

		this.closeButton = new JButton("Close");
		this.closeButton.addActionListener(this);

		this.dialogPanel = new JPanel(new BorderLayout());
		this.dialogPanel.setBackground(Color.cyan);
		this.dialogPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		this.dialogPanel.add(this.tabbedPane, BorderLayout.CENTER);
		this.dialogPanel.add(this.closeButton, BorderLayout.SOUTH);

		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(this.dialogPanel, BorderLayout.CENTER);
		this.pack();
	}

	public Dimension getPreferredSize() {
		return new Dimension(400, 670);
	}

	/**
	 * Adds a new option panel to the main window to a special position. If the
	 * position is negative the option are added at the end.
	 * 
	 * @param gui
	 *            The added gui
	 * @param pos
	 *            The position of the option.
	 */
	public void addGUI(AbstractOptionGUI gui, int pos) {
		if (pos < 0)
			this.tabbedPane.addTab(gui.getTabTitle(), gui.getIcon(), gui, gui
					.getTabTip());
		else {
			this.tabbedPane.insertTab(gui.getTabTitle(), gui.getIcon(), gui, gui
					.getTabTip(), pos);
		}
		this.title2optiongui.put(gui.getTabTitle(), gui);
		pack();
		validate();
	}

	public AbstractOptionGUI getGuiComponent(String title) {
		for (int i=0; i<this.tabbedPane.getComponentCount(); i++) {
			Component comp = this.tabbedPane.getComponentAt(i);
			if (comp instanceof AbstractOptionGUI) {
				if (((AbstractOptionGUI)comp).getTabTitle().indexOf(title) != -1){
					return (AbstractOptionGUI) comp;
				}
			}
		}
				
		return null;
	}
	
	/**
	 * Adds a new option panel to the main window to the end.
	 * 
	 * @param gui
	 *            The added gui.
	 */
	public void addGUI(AbstractOptionGUI gui) {
		addGUI(gui, -1);
		pack();
	}

	/**
	 * If option are not longer needed they can removed here.
	 * 
	 * @param gui
	 *            The gui to remove
	 */
	public void removeGUI(AbstractOptionGUI gui) {
		this.tabbedPane.remove(gui);
		pack();
		validate();
	}

	/**
	 * The method for the close button.
	 * 
	 * @param e
	 *            The event from the close button.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.closeButton) {
			setVisible(false);
		}
	}

	/**
	 * Registers a listener for messages for the status bar.
	 * 
	 * @param sml
	 *            the listener to register.
	 */
	public void addStatusMessageListener(StatusMessageListener sml) {
		if (!this.listener.contains(sml))
			this.listener.addElement(sml);
	}

	/**
	 * If a listener doesn't want to receive anymore messages. The listener has
	 * to quit here.
	 * 
	 * @param sml
	 *            The listener to remove.
	 */
	public void removeStatusMessageListener(StatusMessageListener sml) {
		if (this.listener.contains(sml))
			this.listener.removeElement(sml);
	}

	/*
	private void fireStatusMessageEvent(StatusMessageEvent sme) {
		for (int i = 0; i < listener.size(); i++)
			listener.elementAt(i).newMessage(sme);
	}

	private void updateAllTabs() {
		for (int i = 0; i < tabbedPane.getTabCount(); i++)
			((AbstractOptionGUI) tabbedPane.getComponentAt(i)).update();
	}
*/
	
	/**
	 * If the another tab is selected the selected tab will be updated.
	 * 
	 * @param e
	 *            The event from the tabbeed pane.
	 */
	public void stateChanged(ChangeEvent e) {
//		JTabbedPane source = (JTabbedPane) e.getSource();
		((AbstractOptionGUI) this.tabbedPane.getSelectedComponent()).update();
	}
	
	public void selectOptions(final int kind) {
		switch (kind) {
		case GENERAL:
			this.tabbedPane.setSelectedComponent(this.title2optiongui.get("General"));
			break;
		case TRANSFORMATION:
			this.tabbedPane.setSelectedComponent(this.title2optiongui.get("Transformation"));
			break;
		case PARSER:
			this.tabbedPane.setSelectedComponent(this.title2optiongui.get("Parser"));
			break;
		case CRITICAL_PAIRS:
			this.tabbedPane.setSelectedComponent(this.title2optiongui.get("Critical Pairs"));
			break;
		case LAYOUTER:
			this.tabbedPane.setSelectedComponent(this.title2optiongui.get("Layouter"));
			break;
		}
		
	}
}
/*
 * $Log: OptionGUI.java,v $
 * Revision 1.4  2010/09/23 08:20:39  olga
 * tuning
 *
 * Revision 1.3  2010/03/08 15:42:54  olga
 * code optimizing
 *
 * Revision 1.2  2008/11/19 13:04:18  olga
 * Parser tuning
 *
 * Revision 1.1  2008/10/29 09:04:13  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.8  2008/07/28 14:57:57  olga
 * Applicability of RS - bug fixed
 * Graph transformation in debug mode, rule with input parameter - bug fixed
 * Code tuning
 *
 * Revision 1.7  2007/11/05 09:18:18  olga
 * code tuning
 *
 * Revision 1.6  2007/09/10 13:05:26  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2006/12/13 13:32:54 enrico
 * reimplemented code
 * 
 * Revision 1.4 2006/08/02 09:00:56 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.3 2006/03/01 09:55:46 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.2 2005/12/21 14:49:05 olga GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:53 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.9 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.8 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.7 2003/12/18 16:26:42 olga GUI
 * 
 * Revision 1.6 2003/03/05 18:24:17 komm sorted/optimized import statements
 * 
 * Revision 1.5 2002/09/26 14:00:10 olga GUI-Arbeit
 * 
 * Revision 1.4 2002/09/23 14:14:28 olga GUI fertig.
 * 
 * Revision 1.3 2002/09/23 12:24:09 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.2 2002/09/19 16:22:24 olga Arbeit im wesentlichen an GUI.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:11 olga Imported sources
 * 
 * Revision 1.3 2001/07/09 13:12:33 olga Aenderungen an GUI. Version heisst ab
 * jetzt 1.1
 * 
 * Revision 1.2 2001/06/26 17:27:18 olga Optimierung des Parsers und Optionen
 * Dialogs.
 * 
 * Revision 1.1 2001/03/22 15:50:05 olga Events Behandlung und Ausgabe in GUI.
 * 
 * Revision 1.2 2001/03/08 11:02:45 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.5 2001/01/28 13:14:45 shultzke API fertig
 * 
 * Revision 1.1.2.4 2000/12/19 12:11:42 shultzke Parseroptiongui und
 * criticalpairoptionGUI getrennt
 * 
 * Revision 1.1.2.3 2000/12/18 13:33:34 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.2 2000/11/27 13:16:45 shultzke referenzparser SimpleParser
 * implementiert
 * 
 * Revision 1.1.2.1 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.6 2000/08/06 22:28:59 shultzke Option Model erzeugt
 * 
 * Revision 1.1.2.5 2000/08/03 13:46:00 shultzke Die OptionenGUI scheint fertig
 * zu sein. Es fehlt nur noch die Referenz auf das Optionenmodel.
 * 
 * Revision 1.1.2.4 2000/07/30 17:42:01 shultzke OptionGUI entworfen Optionen
 * mussen noch entworfen werden
 * 
 * Revision 1.1.2.3 2000/07/27 14:23:03 shultzke ParserOptionenIcon entworfen
 * 
 * Revision 1.1.2.2 2000/07/26 19:32:51 shultzke Option etwas weiterentwickelt
 * 
 * Revision 1.1.2.1 2000/07/26 15:05:23 shultzke Anfang mit OptionFenster
 * 
 */

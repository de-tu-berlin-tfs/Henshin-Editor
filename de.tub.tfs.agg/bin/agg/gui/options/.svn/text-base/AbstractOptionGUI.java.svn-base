package agg.gui.options;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import agg.gui.parser.event.OptionEvent;
import agg.gui.parser.event.OptionListener;


/**
 * This is an abstract option display, which provides necessary feature for the
 * main AGG option window. Such a option display will be added to a tab of the
 * main AGG option window.
 * 
 * @version $Id: AbstractOptionGUI.java,v 1.2 2010/08/18 09:25:57 olga Exp $
 * @author $Author: olga $
 */
public abstract class AbstractOptionGUI extends JPanel {

	/** The set of listeners receives the messages */
	protected HashSet<OptionListener> listener;

	/**
	 * Don't really create a new GUI but initial the listener stuff.
	 */
	public AbstractOptionGUI() {
		super(true);
		this.listener = new HashSet<OptionListener>();
	}

	/**
	 * here register as a listener. A status bar would like to do so
	 */
	public void addOptionListener(OptionListener ol) {
		this.listener.add(ol);
	}

	/**
	 * just remove the listener. From now on the listener will be silent in the
	 * future.
	 */
	public void removeOptionListener(OptionListener ol) {
		this.listener.remove(ol);
	}

	/**
	 * send new events to all listener
	 */
	public void fireOptionEvent(OptionEvent oe) {
		Iterator<OptionListener> iter = this.listener.iterator();
		while (iter.hasNext()) {
			OptionListener ol = iter.next();
			ol.optionEventOccurred(oe);
		}
	}

	/**
	 * Returns a icon for the tab. The default is <code>null</code>.
	 */
	public Icon getIcon() {
		return null;
	}

	/**
	 * Returns the text for the tab title. This method must be overridden by any
	 * subclass.
	 */
	public abstract String getTabTitle();

	/**
	 * Returns the text for the tab tip. This method must be overridden by any
	 * subclass.
	 */
	public abstract String getTabTip();

	/** Updates the gui from the model */
	public abstract void update();

	/**
	 * Creates a standard panel with a etched border. The title is shown at the
	 * top left of the panel.
	 * 
	 * @param title
	 *            Enter a title for this panel.
	 */
	protected JPanel makeInitialOptionPanel(String title) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		return makeInitialOptionPanel(title, c);
	}

	/**
	 * Creates a standard panel with a etched border. The constraint belongs to
	 * title.
	 * 
	 * @param title
	 *            Enter a title for this panel.
	 */
	protected JPanel makeInitialOptionPanel(String title, GridBagConstraints c) {
		JPanel optionPanel = new JPanel();
		Border etched = BorderFactory.createEtchedBorder();
		optionPanel.setBorder(etched);
		GridBagLayout gridbag = new GridBagLayout();
		optionPanel.setLayout(gridbag);
		JLabel titleText = new JLabel(title);
		gridbag.setConstraints(titleText, c);
		optionPanel.add(titleText);
		return optionPanel;
	}

}
/*
 * $Log: AbstractOptionGUI.java,v $
 * Revision 1.2  2010/08/18 09:25:57  olga
 * tuning
 *
 * Revision 1.1  2008/10/29 09:04:14  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.3  2007/09/10 13:05:28  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:32:55 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:53 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:17 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:09 olga Imported sources
 * 
 * Revision 1.3 2001/06/18 13:42:49 olga Weitere Tests am Parser.
 * 
 * Revision 1.2 2001/03/08 11:05:28 olga Neue Files wegen Parser Anbindung.
 * 
 * Revision 1.1.2.1 2000/12/22 10:31:12 shultzke *** empty log message ***
 * 
 */

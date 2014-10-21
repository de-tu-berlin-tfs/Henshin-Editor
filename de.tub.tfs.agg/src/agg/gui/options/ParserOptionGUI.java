package agg.gui.options;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.EventObject;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import agg.gui.IconResource;
import agg.parser.CriticalPairOption;
import agg.parser.OptionEventListener;
import agg.parser.ParserOption;

/**
 * A gui is provided for parser setting. The user can change the critical pair
 * algorithm, the parser algorithm and some display setttings.
 * 
 * @version $Id: ParserOptionGUI.java,v 1.3 2010/09/23 08:20:39 olga Exp $
 * @author $Author: olga $
 */
@SuppressWarnings("serial")
public class ParserOptionGUI extends AbstractOptionGUI implements ItemListener,
		ActionListener, OptionEventListener {

	/**
	 * The choice of the critical pair algorithms.
	 */
	@SuppressWarnings("rawtypes")
	JComboBox algorithms;

	/**
	 * The choice of the parser algorithms.
	 */
	@SuppressWarnings("rawtypes")
	JComboBox parserAlgorithms;

	/**
	 * The choice if the stop graph shall be shown.
	 */
	JCheckBox stopGraphButton;

	/**
	 * The choice if the parsing process is hidden.
	 */
	JCheckBox invisibleButton;

	/**
	 * The choice if the host graph is shown.
	 */
	JCheckBox hostGraphButton;

	/**
	 * The delay time of the graph parsing.
	 */
	JTextField delayField;

	/**
	 * The choice of a layered parser.
	 */
	JCheckBox layered;

	/**
	 * This panel holds all the option.
	 */
	JPanel firstPriorityOption;

	JButton displaySwitch;

	/**
	 * Text for the critical pair choice.
	 */
	public static final String EXCLUDEONLY = " conflicts ";

	/**
	 * Text for the critical pair choice.
	 */
	public static final String EXCLUDEANDBEFORE = "exclude and befor";

	/**
	 * Text for the additional critical pair choice.
	 */
	public static final String DEPENDONLY = " dependencies ";
	public static final String TRIGGER_DEPEND = " trigger dependency ";
	public static final String SWITCH_DEPEND = " switch dependency ";
	public static final String TRIGGER_SWITCH_DEPEND = " trigger & switch dependencies ";

	/**
	 * Text for the additional critical pair choice.
	 */
	protected static final String INDEPENDING = "before independing on exclude";

	public static final String DISPLAYSETTINGS = "Display Settings...";

	/**
	 * the text for a label
	 */
	public static final String GENERALSETTINGS = " General Settings...";

	private static final String EXCLUDEPARSER = " Critical Pair Analysis";

	private static final String SIMPLEEXCLUDEPARSER = " Semi optimized backtracking";

	private static final String SIMPLEPARSER = " Backtracking without optimization";

	/**
	 * the option for the display settings
	 * 
	 * @serial A super class is serializable
	 */
	private ParserGUIOption guiOption;

	/**
	 * the option for the parser
	 * 
	 * @serial A super class is serializable
	 */
	private ParserOption pOption;

	/**
	 * The option for the critical pairs. They are needed for the settings of
	 * the algorithm.
	 */
	CriticalPairOption cpOption;

	/**
	 * Creates a new gui with the given option.
	 * 
	 * @param guiOption
	 *            the option for the display settings.
	 * @param option
	 *            the settings for the parser.
	 * @param cpOption
	 *            the option for the critical pairs.
	 */
	public ParserOptionGUI(ParserGUIOption guiOption, ParserOption option,
			CriticalPairOption cpOption) {
		super();
		this.pOption = option;
		this.guiOption = guiOption;
		this.cpOption = cpOption;
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();

		setLayout(gridbag);

		this.firstPriorityOption = makeFirstPriorityOption();
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.weightx = 1.0;
		add(this.firstPriorityOption, c);
		validate();
	}

	public Dimension getPreferredSize() {
		return new Dimension(350, 400);
	}

	public void setCriticalPairOption(CriticalPairOption cpOption) {
		this.cpOption = cpOption;
	}

	private void addIcon(JPanel optionPanel) {
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.gridwidth = 1;
		c.gridheight = 2;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.insets = new Insets(1, 1, 1, 1);
		ImageIcon optionImage = IconResource.getIconFromURL(IconResource
				.getOptionIcon());
		JLabel optionLabel = new JLabel(optionImage);
		optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionLabel.setVerticalAlignment(SwingConstants.CENTER);
		optionLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		optionLabel.setVerticalTextPosition(SwingConstants.CENTER);
		optionPanel.add(optionLabel, c);
	}

	private JPanel makeFirstPriorityOption() {
		GridBagConstraints c = new GridBagConstraints();
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.NORTHWEST;
		JPanel optionPanel = makeInitialOptionPanel(true, "", c);
//		optionPanel.setBorder(new TitledBorder(" General Settings "));

		addIcon(optionPanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		c.insets = new Insets(5, 0, 5, 0);
		c.weightx = 1.0;
		c.weighty = 0.0;
		JPanel placeHolder3 = new JPanel();
		// placeHolder3.setBackground(java.awt.Color.green);
		placeHolder3.setPreferredSize(new Dimension(200, 2));
		optionPanel.add(placeHolder3, c);

		c.weighty = 0.0;
		JPanel parserOptionPanel = makeParserPanelOption();
		optionPanel.add(parserOptionPanel, c);

		c.weighty = 0.0;
		JPanel algorithmParser = makeParserAlgorithm();
		optionPanel.add(algorithmParser, c);

		c.weightx = 1.0;
		c.weighty = 1.0;
		JPanel placeHolder1 = new JPanel();
		// placeHolder1.setBackground(java.awt.Color.magenta);
		placeHolder1.setPreferredSize(new Dimension(200, 200));
		optionPanel.add(placeHolder1, c);

		c.weightx = 0.0;
		c.weighty = 0.0;
		c.gridwidth = 1;
		JPanel placeHolder = new JPanel();
		// placeHolder.setBackground(java.awt.Color.blue);
		placeHolder.setPreferredSize(new Dimension(200, 200));
		optionPanel.add(placeHolder, c);

		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		JPanel placeHolder2 = new JPanel();
		// placeHolder2.setBackground(java.awt.Color.yellow);
		placeHolder2.setPreferredSize(new Dimension(200, 200));
		optionPanel.add(placeHolder2, c);

		return optionPanel;
	}

	private JPanel makeParserPanelOption() {
		JPanel optionPanel = makeInitialOptionPanel("");
		optionPanel.setBorder(new TitledBorder(" Parser Display Option "));

		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = 1;
		JLabel show = new JLabel(" show...");
		optionPanel.add(show, c);

		this.stopGraphButton = new JCheckBox("Stop Graph");
		this.stopGraphButton.setFocusPainted(false);
		this.stopGraphButton.addActionListener(this);
		this.stopGraphButton
				.setSelected(this.guiOption.getParserDisplay()
						- ParserGUIOption.SHOWHOSTGRAPH == ParserGUIOption.SHOWSTOPGRAPH);
		this.invisibleButton = new JCheckBox("Invisible");
		this.invisibleButton.setFocusPainted(false);
		this.invisibleButton.addActionListener(this);
		this.invisibleButton
				.setSelected(this.guiOption.getParserDisplay() == ParserGUIOption.PARSINGINVISIBLE);
		this.hostGraphButton = new JCheckBox("Host Graph");
		this.hostGraphButton.setFocusPainted(false);
		this.hostGraphButton.addActionListener(this);
		this.hostGraphButton
				.setSelected(this.guiOption.getParserDisplay()
						- ParserGUIOption.SHOWSTOPGRAPH == ParserGUIOption.SHOWHOSTGRAPH);

		/* Group the radio buttons. */
		ButtonGroup group = new ButtonGroup();
		group.add(this.invisibleButton);
		group.add(this.hostGraphButton);

		optionPanel.add(this.invisibleButton, c);
		c.gridwidth = GridBagConstraints.RELATIVE;
		optionPanel.add(this.hostGraphButton, c);
		c.weightx = 1.0;
		optionPanel.add(this.stopGraphButton, c);

		JLabel delay = new JLabel(" Delay Time (ms) ");
		this.delayField = new JTextField();
		this.delayField.addActionListener(this);
		this.delayField.setText(String.valueOf(this.guiOption.getDelayAfterApplyRule()));

		c.weightx = 1.0;
		c.gridx = 1;
		optionPanel.add(delay, c);
		c.gridx = 2;
		optionPanel.add(this.delayField, c);

		return optionPanel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JPanel makeParserAlgorithm() {
		GridBagConstraints c = new GridBagConstraints();

		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;

		JPanel optionPanel = makeInitialOptionPanel(true, "", c);
		optionPanel.setBorder(new TitledBorder(" Select algorithm for parser "));
		/* optionPanel.setBackground(Color.red); */
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		optionPanel.add(new JPanel(), c);

		c.gridwidth = 1;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.WEST;
		this.parserAlgorithms = new JComboBox();
		this.parserAlgorithms.addItem(EXCLUDEPARSER);
		this.parserAlgorithms.addItem(SIMPLEEXCLUDEPARSER);
		this.parserAlgorithms.addItem(SIMPLEPARSER);
		this.parserAlgorithms.addItemListener(this);
		optionPanel.add(this.parserAlgorithms, c);

		this.layered = new JCheckBox("layered", false);
		this.layered.setFocusPainted(false);
		this.layered.addActionListener(this);
		c.insets = new Insets(0, 20, 0, 0);
		optionPanel.add(this.layered, c);

		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 1.0;
		optionPanel.add(new JPanel(), c);

		return optionPanel;
	}

	/**
	 * Changes the settings of a algorithm.
	 * 
	 * @param e
	 *            the event for the event.
	 */
	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();
		if (source == this.parserAlgorithms) {
			if (this.parserAlgorithms.getSelectedItem().equals(EXCLUDEPARSER)) {
				this.pOption.setSelectedParser(ParserOption.EXCLUDEPARSER);
			} else if (this.parserAlgorithms.getSelectedItem().equals(
					SIMPLEEXCLUDEPARSER)) {
				this.pOption.setSelectedParser(ParserOption.SIMPLEEXCLUDEPARSER);
			} else if (this.parserAlgorithms.getSelectedItem().equals(SIMPLEPARSER)) {
				this.pOption.setSelectedParser(ParserOption.SIMPLEPARSER);
			}
		}
	}

	/**
	 * Evaluates a click at the display options.
	 * 
	 * @param e
	 *            The event for the display settings.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		// System.out.println("ParserOptionGUI.actionParformd: "+source);
		if (source.equals(this.displaySwitch)) {
		} else if (source.equals(this.invisibleButton)
				|| source.equals(this.hostGraphButton)) {
			if (source.equals(this.invisibleButton)) {
				this.stopGraphButton.setEnabled(false);
				this.guiOption.setParserDisplay(ParserGUIOption.PARSINGINVISIBLE);
			} else if (source.equals(this.hostGraphButton)) {
				this.stopGraphButton.setEnabled(true);
				int op = ParserGUIOption.SHOWHOSTGRAPH;
				if (this.stopGraphButton.isSelected())
					op = op + ParserGUIOption.SHOWSTOPGRAPH;
				this.guiOption.setParserDisplay(op);
			}
		} else if (source.equals(this.stopGraphButton)) {
			if (this.stopGraphButton.isSelected())
				this.guiOption.setParserDisplay(ParserGUIOption.SHOWSTOPGRAPH
						+ ParserGUIOption.SHOWHOSTGRAPH);
			else
				this.guiOption.setParserDisplay(ParserGUIOption.SHOWHOSTGRAPH);
		} else if (source.equals(this.layered)) {
			this.pOption.enableLayer(this.layered.isSelected());
			this.cpOption.enableLayered(this.layered.isSelected());
		} else if (source.equals(this.delayField)) {
			if (!this.delayField.getText().equals("")) {
				try {
					Integer nb = Integer.valueOf(this.delayField.getText());
					this.guiOption.setDelayAfterApplyRule(nb.intValue());
				} catch (NumberFormatException ex) {
					this.delayField.setText("100");
				}
			}
		}
		update();
	}

	/* Implements java.util.EventListener */
	public void optionEventOccurred(EventObject e) {
		// System.out.println("ParserOptionGUI.optionEventOccurred:
		// "+e.getSource());
		if (e.getSource() instanceof JCheckBox) {
			JCheckBox cb = (JCheckBox) e.getSource();
			if (cb.getText().equals("layered")) {
				this.pOption.enableLayer(cb.isSelected());
				this.layered.doClick();
			}
		} else if (e.getSource() instanceof CriticalPairOption) {
			boolean b = ((CriticalPairOption) e.getSource()).layeredEnabled();
			this.pOption.enableLayer(b);
			if (b && !this.layered.isSelected())
				this.layered.doClick();
			else if (!b && this.layered.isSelected())
				this.layered.doClick();
		}
	}

	/**
	 * Returns a icon for the tab. Pacman is returned.
	 * 
	 * @return <I>PacMan</I> is returned.
	 */
	public Icon getIcon() {
		java.net.URL url = ClassLoader.getSystemClassLoader()
								.getResource("agg/lib/icons/pacman.gif");
		if (url != null) {
		return new ImageIcon(ClassLoader
				.getSystemResource("agg/lib/icons/pacman.gif"));
		} 
		return null;
	}

	/**
	 * Returns the text for the tab title.
	 * 
	 * @return <I>Parser</I> is returned.
	 */
	public String getTabTitle() {
		return "Parser";
	}

	/**
	 * Returns the text for the tab tip.
	 * 
	 * @return <I>Parser Option</I> is returned.
	 */
	public String getTabTip() {
		return "Parser Options";
	}

	/**
	 * Updates the gui to the settings.
	 */
	public void update() {
		if (this.pOption.getSelectedParser() == ParserOption.SIMPLEPARSER)
			this.parserAlgorithms.setSelectedItem(SIMPLEPARSER);
		else if (this.pOption.getSelectedParser() == ParserOption.EXCLUDEPARSER)
			this.parserAlgorithms.setSelectedItem(EXCLUDEPARSER);
		else if (this.pOption.getSelectedParser() == ParserOption.SIMPLEEXCLUDEPARSER)
			this.parserAlgorithms.setSelectedItem(SIMPLEEXCLUDEPARSER);

		if (this.guiOption.getParserDisplay() == ParserGUIOption.PARSINGINVISIBLE) {
			this.stopGraphButton.setEnabled(false);
			this.stopGraphButton.setSelected(false);
			this.invisibleButton.setSelected(true);
			this.hostGraphButton.setSelected(false);
		} else if (this.guiOption.getParserDisplay() == ParserGUIOption.SHOWHOSTGRAPH) {
			this.stopGraphButton.setEnabled(true);
			this.stopGraphButton.setSelected(false);
			this.invisibleButton.setSelected(false);
			this.hostGraphButton.setSelected(true);
		} else if (this.guiOption.getParserDisplay() == ParserGUIOption.SHOWHOSTGRAPH
				+ ParserGUIOption.SHOWSTOPGRAPH) {
			this.stopGraphButton.setEnabled(true);
			this.stopGraphButton.setSelected(true);
			this.invisibleButton.setSelected(false);
			this.hostGraphButton.setSelected(true);
		}

		this.layered.setSelected(this.pOption.layerEnabled());
		// System.out.println("ParserOptionGUI.layered: "+layered.isSelected());
	}

}
/*
 * $Log: ParserOptionGUI.java,v $
 * Revision 1.3  2010/09/23 08:20:39  olga
 * tuning
 *
 * Revision 1.2  2010/03/08 15:42:54  olga
 * code optimizing
 *
 * Revision 1.1  2008/10/29 09:04:13  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.12  2008/09/22 10:03:48  olga
 * tests
 *
 * Revision 1.11  2008/09/04 07:50:27  olga
 * GUI extension: hide nodes, edges
 *
 * Revision 1.10  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.9  2007/11/19 08:48:41  olga
 * Some GUI usability mistakes fixed.
 * Default values in node/edge of a type graph implemented.
 * Code tuning.
 *
 * Revision 1.8  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.7  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/06/13 08:33:08 olga Update:
 * V161
 * 
 * Revision 1.5 2006/12/13 13:33:05 enrico reimplemented code
 * 
 * Revision 1.4 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.3 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2005/03/16 12:02:09 olga
 * 
 * only little changes
 * 
 * Revision 1.5 2004/10/25 14:24:38 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.4 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/09/26 13:59:50 olga GUI- Arbeit
 * 
 * Revision 1.2 2002/09/19 16:22:39 olga Arbeit im wesentlichen an GUI.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.5 2001/06/26 17:24:49 olga Unwesentliche Aenderung.
 * 
 * Revision 1.4 2001/05/14 11:52:59 olga Parser GUI Optimierung
 * 
 * Revision 1.3 2001/03/22 15:52:30 olga GUI an den veraenderten GraphEditor
 * angepasst.
 * 
 * Revision 1.2 2001/03/08 11:02:48 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.14 2001/01/14 14:48:20 shultzke commentare ergaenzt
 * 
 * Revision 1.1.2.13 2001/01/03 09:44:56 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.1.2.12 2000/12/21 13:46:02 shultzke optionen weiter veraendert
 * 
 * Revision 1.1.2.11 2000/12/19 12:52:28 shultzke Parseralgorithmusauswahl mit
 * Layer-Checkbutton
 * 
 * Revision 1.1.2.10 2000/12/19 12:11:42 shultzke Parseroptiongui und
 * criticalpairoptionGUI getrennt
 * 
 * Revision 1.1.2.9 2000/12/18 13:33:35 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.8 2000/12/04 12:26:45 shultzke drei parser stehen zur
 * verfuegung
 * 
 * Revision 1.1.2.7 2000/11/27 13:16:45 shultzke referenzparser SimpleParser
 * implementiert
 * 
 * Revision 1.1.2.6 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.5 2000/08/06 22:28:59 shultzke Option Model erzeugt
 * 
 * Revision 1.1.2.4 2000/08/03 13:46:00 shultzke Die OptionenGUI scheint fertig
 * zu sein. Es fehlt nur noch die Referenz auf das Optionenmodel.
 * 
 * Revision 1.1.2.3 2000/08/02 11:24:51 shultzke Optionen auf first und second
 * Priority vorbereitet
 * 
 * Revision 1.1.2.2 2000/07/30 17:42:01 shultzke OptionGUI entworfen Optionen
 * mussen noch entworfen werden
 * 
 * Revision 1.1.2.1 2000/07/27 14:23:04 shultzke ParserOptionenIcon entworfen
 * 
 */

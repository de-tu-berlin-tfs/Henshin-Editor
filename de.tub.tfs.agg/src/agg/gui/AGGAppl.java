package agg.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.Enumeration;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import agg.gui.cpa.CriticalPairAnalysis;
import agg.gui.editor.GraGraEditor;
import agg.gui.event.EditEventListener;
import agg.gui.event.EditEvent;
import agg.gui.help.GraGraHelp;
import agg.gui.parser.AGGParser;
import agg.gui.ruleappl.ApplicabilityRuleSequence;
import agg.gui.saveload.GraphicsExportJPEG;
import agg.gui.termination.TerminationAnalysis;
import agg.gui.treeview.GraGraTreeView;

/**
 * The main class of AGG application.
 * 
 * @author $Author: olga $
 * @version $Id: AGGAppl.java,v 1.61 2010/09/19 16:23:04 olga Exp $
 */
public class AGGAppl extends JFrame implements 
		EditEventListener {

	static final long serialVersionUID = 42L;

	/* The width and height of my frame */
	public final static int FRAME_WIDTH = 900;

	public final static int FRAME_HEIGHT = 550;

	public final static int INITIAL_WIDTH = 350;

	public final static int INITIAL_HEIGHT = 400;

	/* Create myself */
	protected final static AGGAppl appl = new AGGAppl();

	private String aggTitle = "AGG";
	/* Track progress */
	public final static int total = 10;

	public static int currentProgressValue;

	/* create a progress bar */
	public final static JProgressBar 
	progressBar = new JProgressBar();

	/* create logo frame */
//	final static JFrame logoFrame = new JFrame();
	final static JDialog logoFrame = new JDialog();

	final static JLabel gragraLogo = new JLabel();
	
	/* create a double buffered content panel */
	private final static JPanel contentPanel = new JPanel(new BorderLayout(),
			true);

	/* create a tool bar panel */
	private final static JPanel toolBarPanel = new JPanel(new GridLayout(2, 1));

	/* create a menu bar */
	private final static JMenuBar menuBar = new JMenuBar();

	/* create a status bar */
	private final static StatusBar 
	statusBar = new StatusBar();

	/* panel of my main content */
	private final static JPanel 
	mainPanel = new JPanel(new BorderLayout(), true);

	/* help menu */
	private final static JMenu 
	helpMenu = new GraGraHelp();

	/* tree view */
	protected final static GraGraTreeView 
	treeView = new GraGraTreeView(appl);

	/* editor */
	protected final static GraGraEditor 
	editor = new GraGraEditor(appl);

	/* This flag toggles if there is an menu entry for debugging stuff */
	private static final boolean DEBUGFRAME = false;

	/* create analysis */
	private final static AGGAnalyzer 
	aggAnalyzer = new AGGAnalyzer(appl, treeView);

	/* create termination analysis */
	private final static TerminationAnalysis 
	terminationAnalysis = new TerminationAnalysis(appl, treeView);

	/* create critical pair analysis */
	protected final static CriticalPairAnalysis 
	criticalPairAnalysis = new CriticalPairAnalysis(appl, treeView);
	
	/* create applicability of rule sequence */
	private final static ApplicabilityRuleSequence 
	aggApplRuleSequence = new ApplicabilityRuleSequence(appl, treeView, criticalPairAnalysis.getCriticalPairOption());
	
	/* create constraints */
	private final static AGGConstraints 
	aggConstraints = new AGGConstraints(appl, treeView);

	/* create parser */
	private final static AGGParser 
	aggParser = new AGGParser(appl, treeView);

	/* create AGG preferences */
	private final static AGGPreferences 
	aggPreferences = new AGGPreferences(appl);

	/* create JPG output */
	private final static GraphicsExportJPEG 
	exportJPEG = new GraphicsExportJPEG(appl);

	/* get logo image of the application */
	private final static ImageIcon 
	image = new ImageIcon(ClassLoader.getSystemResource("agg/lib/icons/AGG_LOGO.gif"));

	private final static JLabel 
	loadlabel = new JLabel("Loading  graph  grammar,  please wait . . .");

	/* my main content : a split pane containing toolbar, treeview, editor */
	private final static JSplitPane 
	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, editor);

	private final static JPanel progressPanel = new JPanel() {
		public Insets getInsets() {
			return new Insets(30, 10, 10, 10);
		}
	};

	protected static String fname;
	
//	protected static boolean enableParallelRule, enableRuleScheme, enableNestedAC;
	protected static boolean typesHidden;
	
	public AGGAppl() {
	}

/*	
	private static Runnable makeRunnable(final String[] argums) {
	    Runnable r = new Runnable() {
	        public void run() {
	        	final long time0 = System.currentTimeMillis();
	        	
	        	fname = "";		
	    		for (int i = 0; i < argums.length; i++) {
	    			if (argums[i].endsWith(".ggx")) {
	    				fname = argums[i];
	    			} 
	    		}
	    		
	            Thread t = Thread.currentThread();
//	            System.out.println("in run() - priority=" + t.getPriority()
//	                + ", name=" + t.getName());
	            
	    		appl.initApplication();		
	    		appl.showApplication(argums);
	    		AGGAppl.criticalPairAnalysis.allowNodeTypeInheritance = true;
	    		
	    		System.out.println("AGG Application has started in  "
	    				+ (System.currentTimeMillis() - time0) + "ms");
	        }
	      };

	      return r;
	}
	*/
	
	public static void main(String[] args) {
		// main application as Thread
//		Thread mainThread = new Thread(makeRunnable(args), "AGG Application");
//	    mainThread.setPriority(8);
//	    mainThread.start();

		final long time0 = System.currentTimeMillis();
	    
		fname = "";		
		for (int i = 0; i < args.length; i++) {
			if (args[i].endsWith(".ggx")) {
				fname = args[i];
			} 
			else if (args[i].equals("-t")) {
				// type editor not on top
				typesHidden = true;
			}
//			else if (args[i].equals("-gac")
//					|| args[i].equals("gac")
//					||args[i].equals("-nac")
//					|| args[i].equals("nac")) {
//				enableNestedAC = true;
//			}			
//			else if (args[i].equals("-pr")
//						|| args[i].equals("pr")) {
//				enableParallelRule = true;
//			}
//			else if (args[i].equals("-rs")
//						|| args[i].equals("rs")) {
//				enableRuleScheme = true;
//			}			
		}
//		enableParallelRule = true;
//		enableRuleScheme = true;
//		enableNestedAC = true;
		
		appl.initApplication();		
		appl.showApplication(args);
		
		AGGAppl.criticalPairAnalysis.allowNodeTypeInheritance = true;
		
//		treeView.enableParallelRuleElementOfGraGra(enableParallelRule);
		// ParallelRule is already enabled by default
		
//		treeView.enableRuleSchemeElementOfGraGra(enableRuleScheme);
		// RuleScheme is already enabled by default
		
		treeView.enableNestedApplCond(true); //enableNestedAC);
		editor.enableNestedApplCond(true); //enableNestedAC);
		
		aggPreferences.selectTypesOnTop(!typesHidden);
		
		System.out.println("AGG has started in  "
				+ (System.currentTimeMillis() - time0) + "ms");

	}

	/**
	 * Creates a content of the application : a main panel with a gragra
	 * tree view and a gragra editor , a menu bar with menus from the tree view and
	 * editor, a status bar
	 */
	private void createApplContent() {
		progressBar.setValue(++currentProgressValue);

		statusBar.setFrame(this);
		/* add the status bar to content panel */
		contentPanel.add(statusBar, BorderLayout.SOUTH);

		progressBar.setValue(++currentProgressValue);

		// /* create output to JPG output */
		// exportJPEG = new GraphicsExportJPEG(this);

		/* create a gragra editor */
		// editor = new GraGraEditorImpl(this);
		editor.addEditEventListener(this);
		editor.addEditEventListener(statusBar);
		editor.getTypeEditor().addTypeEventListener(statusBar);
		editor.setExportJPEG(exportJPEG);

		progressBar.setValue(++currentProgressValue);

		/* create a gragra treeview */
		// treeView = new GraGraTreeView(this);
		treeView.setExportJPEG(exportJPEG);
		treeView.addSaveEventListener(statusBar);
		treeView.addLoadEventListener(statusBar);
		treeView.addTreeViewEventListener(statusBar);
		treeView.addTreeViewEventListener(editor);
		treeView.addTreeModelListener(editor);
		editor.addEditEventListener(treeView);
		editor.getGraGraTransform().addTransformEventListener(treeView);
		aggPreferences.addActionListenerOfDefaults(treeView);
		
		progressBar.setValue(++currentProgressValue);

		/* add tool bar to tool bar panel */
		toolBarPanel.add(treeView.getToolBar());
		toolBarPanel.add(editor.getToolBar());

		/* create an AGG parser */
		// aggParser = new AGGParser(appl, treeView);
		aggParser.addStatusMessageListener(statusBar);
		aggParser.addParserEventListener(statusBar);
		editor.addEditEventListener(aggParser);
		aggPreferences.addActionListenerOfDefaults(editor.getActionListener());
		// aggParser.setExportJPEG(exportJPEG);

		progressBar.setValue(++currentProgressValue);

		/* create an AGG analyzer */
		// aggAnalyzer = new AGGAnalyzer(appl, treeView);
		editor.addEditEventListener(aggAnalyzer);

		/* create critical pair analysis */
		// criticalPairAnalysis = new CriticalPairAnalysis(appl, treeView);
		aggAnalyzer.addCriticalPairAnalysis(criticalPairAnalysis);
		treeView.addTreeViewEventListener(criticalPairAnalysis);
		criticalPairAnalysis.addCPAnalysisEventListener(statusBar);
		criticalPairAnalysis.addStatusMessageListener(statusBar);
		/* set layer option to cp-analysis */
		criticalPairAnalysis.setLayerOption(aggParser.getLayerOption());
		/* set parser option to cp-analysis */
		criticalPairAnalysis.setParserOption(aggParser.getParserOption());
		/* set GUI option to CP Analysis */
		criticalPairAnalysis.setGUIOption(aggParser.getParserGUIOption());

		criticalPairAnalysis.setExportJPEG(exportJPEG);
		
		/* set critical pair option to parser */
		aggParser.setCriticalPairOption(criticalPairAnalysis
				.getCriticalPairOption());

		progressBar.setValue(++currentProgressValue);

		/* creat applicability of rule sequence GUI */
//		aggApplRuleSequence = new ApplicabilityRuleSequence(appl, treeView);
		aggAnalyzer.addApplicabilityRuleSequence(aggApplRuleSequence);
		treeView.addTreeViewEventListener(aggApplRuleSequence);
		
		/* create the constraints GUI */
		// aggConstraints = new AGGConstraints(appl, treeView);
		aggAnalyzer.addConstraints(aggConstraints);
		/* create the termination analysis */
		// terminationAnalysis = new TerminationAnalysis(appl, treeView);
		aggAnalyzer.addTerminationAnalysis(terminationAnalysis);
		treeView.addTreeViewEventListener(terminationAnalysis);

		aggAnalyzer.addCPAOptions();
		
		progressBar.setValue(++currentProgressValue);

		/* create AGG preferences */
		// aggPreferences = new AGGPreferences(this);
		/* add transformation options GUI to options GUI of the AGG preferences */
		aggPreferences.getOptionGUI().addGUI(
				editor.getGeneralTransformOptionGUI());
		aggPreferences.getOptionGUI().addGUI(editor.getTransformOptionGUI());
		/* add graph layouter options GUI to options GUI of the AGG preferences */
		aggPreferences.getOptionGUI()
				.addGUI(editor.getGraphLayouterOptionGUI());
		/* add parser options GUI to options GUI of the AGG preferences */
		aggPreferences.getOptionGUI().addGUI(aggParser.getParserOptionGUI());
		/* add layer options GUI to options GUI of the AGG preferences */
		// aggPreferences.getOptionGUI().addGUI(aggParser.getLayerOptionGUI());
		/* add CP options GUI to options GUI of the AGG preferences */
		aggPreferences.getOptionGUI().addGUI(
				aggAnalyzer.getCriticalPairAnalysis()
						.getCriticalPairOptionGUI());
		/* add gragra editor action listener to preferences defaults menu */
		aggPreferences.addActionListenerOfDefaults(editor.getActionListener());
		editor.addEditEventListener(aggPreferences);

		editor.getTransformOptionGUI().addActionListener(
				aggAnalyzer.getCriticalPairAnalysis()
						.getCriticalPairOptionGUI());
		editor.getTransformOptionGUI().addActionListener(treeView);

		aggAnalyzer.getCriticalPairAnalysis().getCriticalPairOptionGUI()
				.addActionListener(editor.getTransformOptionGUI());
		editor.getGeneralTransformOptionGUI().addOptionListener(aggParser);
		editor.getGeneralTransformOptionGUI().addOptionListener(
				aggAnalyzer.getCriticalPairAnalysis());

		progressBar.setValue(++currentProgressValue);

		/* create main panel in which put the treeview and the gragra editor */
		mainPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

		/* add tool bar to the main panel */
		mainPanel.add(toolBarPanel, BorderLayout.NORTH);

		/* create a splitPane with treeView and editor */
		// splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,treeView,
		// editor);
		splitPane.setDividerSize(15);
		splitPane.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);

		/* add the splitPane to the main panel */
		mainPanel.add(splitPane, BorderLayout.CENTER);
		mainPanel.revalidate();

		/* add the main panel to the content panel */
		contentPanel.add(mainPanel, BorderLayout.CENTER);
		contentPanel.revalidate();

		progressBar.setValue(++currentProgressValue);

		/* add file menu */
		addMenus(treeView.getMenus());

		/* add edit, mode, transform menus */
		addMenus(editor.getMenus());

		/* add parser menus */
		addMenus(aggParser.getMenus());

		/* add analysis menus */
		addMenus(aggAnalyzer.getMenus());

		/* add preferences menus */
		addMenus(aggPreferences.getMenus());

		/* add debug menu */
		if (DEBUGFRAME)
			addDebugMenu();

		/* add help menu */
		addHelpMenu();
	}

	public  void initApplication() {
		// String javaVers = System.getProperty("java.version");
		if (System.getProperty("java.version").compareTo("1.5.0") < 0) {
			System.out.println("WARNING : AGG must be run with the "
					+ "1.5.0 or higher version of the JVM.");
		}
		final String ver = agg.xt_basis.Version.getID();
		appl.setTitle("AGG  " + ver);
		this.aggTitle = appl.getTitle();
		appl.getContentPane().setLayout(new BorderLayout());
		JOptionPane.setRootFrame(appl);

		final WindowListener wl = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				Object[] options = { "SAVE", "EXIT" };
				treeView.exitAppl(options);
			}
		};
		appl.addWindowListener(wl);

		final ComponentListener cl = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
//				System.out.println("ApplFrame: width: "+appl.getWidth());
				editor.resetTypePanelWidth();
			}
		};
		appl.addComponentListener(cl);

		/* get icon image of the application */
		java.net.URL url = ClassLoader.getSystemClassLoader()
								.getResource("agg/lib/icons/AGG_ICON64.gif");
		if (url != null) {
			final ImageIcon icon = new ImageIcon(url);
			if (icon.getImage() != null) {
				appl.setIconImage(icon.getImage());
			}
		} else {
			System.out.println("AGG_ICON64.gif not found!");
		}

	}

	private void showApplicationLogo() {
		logoFrame.setModal(false);
		logoFrame.getContentPane().setLayout(new BorderLayout());
		// logoFrame.getContentPane().setSize(INITIAL_WIDTH, INITIAL_HEIGHT);
		logoFrame.setSize(INITIAL_WIDTH, INITIAL_HEIGHT);

		if (image == null)
			System.out.println("AGG_LOGO.gif not found!");
		else {
			// System.out.println(image.getIconWidth()+"
			// "+image.getIconHeight());
			gragraLogo.setIcon(image);
			gragraLogo.setPreferredSize(new Dimension(image.getIconWidth(),
					image.getIconHeight()));
			logoFrame.getContentPane().add(gragraLogo, BorderLayout.CENTER);
		}

		final WindowListener wl = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				logoFrame.setVisible(false);
			}
		};
		logoFrame.addWindowListener(wl);

		/* create a panel of a progress bar */
		// progressPanel = new JPanel() {
		// public Insets getInsets() {
		// return new Insets(30,10,10,10);
		// }
		// };
		progressPanel.setLayout(new BoxLayout(progressPanel, BoxLayout.Y_AXIS));
		progressPanel.setBackground(Color.white);

		/* create a progress label */
		final Dimension d = new Dimension(300, 20);
		final JLabel progressLabel = new JLabel("Loading, please wait...");
		progressLabel.setAlignmentX(CENTER_ALIGNMENT);
		progressLabel.setMaximumSize(d);
		progressLabel.setPreferredSize(d);
		progressPanel.add(progressLabel);

		progressPanel.add(Box.createRigidArea(new Dimension(300, 5)));

		progressLabel.setLabelFor(progressBar);
		progressBar.setAlignmentX(CENTER_ALIGNMENT);
		progressBar.setMinimum(0);
		progressBar.setMaximum(AGGAppl.total);
		progressBar.setValue(0);
		progressBar.setPreferredSize(new Dimension(300, 20));
		progressPanel.add(progressBar);
		progressPanel.revalidate();

		/* set the panel of the progress bar in logo frame */
		logoFrame.getContentPane().add(progressPanel, BorderLayout.SOUTH);

		/* show logo frame */
		Point locationPoint = getLocationPoint(AGGAppl.INITIAL_WIDTH, AGGAppl.INITIAL_HEIGHT);
		logoFrame.setLocation(locationPoint);
		logoFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		
		logoFrame.pack();
		logoFrame.setVisible(true);
	}

	public void showApplication(String[] args) {
		appl.showApplicationLogo();

		appl.createApplContent();
		appl.setJMenuBar(menuBar);
		appl.getContentPane().add(contentPanel, BorderLayout.CENTER);

//		Point locationPoint = getLocationPoint(AGGAppl.FRAME_WIDTH, AGGAppl.FRAME_HEIGHT);		
//		appl.setLocation(locationPoint);
		appl.setLocation(new Point(100, 100));
		appl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));		
		appl.pack();

		logoFrame.setVisible(false);

		appl.setVisible(true);
		
		createFileLoadLogo();
		if (fname != null && fname.length() > 0) {
			logoFrame.setVisible(true);
			Thread tmpThread = new Thread() {
				public void run() {					
					treeView.loadGraGra(fname);
					logoFrame.setVisible(false);
				}
			};
			tmpThread.start();
		}				
	}

	private Point getLocationPoint(int wdth, int hght) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		if (screenSize.width <= 1400) {
			return new Point(screenSize.width/2 - wdth/2, 
						screenSize.height/2 - hght/2 - 100);
		}
		return new Point(screenSize.width/4 - wdth/2, 
					screenSize.height/2 - hght/2 - 200);
		
		
	}
	
	private void createFileLoadLogo() {
		logoFrame.getContentPane().remove(progressPanel);
		/* set the loading message into logo frame */
		logoFrame.getContentPane().add(loadlabel, BorderLayout.SOUTH);
		logoFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		logoFrame.pack();
	}

	public static void showFileLoadLogo() {
		if (!logoFrame.isVisible()) {
			logoFrame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));			
			logoFrame.setVisible(true);
			logoFrame.toFront();
		} else {
			logoFrame.toFront();
		}			
	}

	public static void hideFileLoadLogo() {
		if (logoFrame.isVisible()) {
			logoFrame.setVisible(false);
		}
	}

	/** Returns myself */
	public static AGGAppl sharedInstance() {
		return appl;
	}

	/** Adds menus to my menu bar */
	public void addMenus(Enumeration<JMenu> menus) {
		while (menus.hasMoreElements())
			menuBar.add( menus.nextElement());
	}

	/** Adds menu to my menu bar */
	public void addMenu(JMenu menu) {
		menuBar.add(menu);
	}

	/** Removes menus from my menu bar */
	public void removeMenus(Enumeration<JMenu> menus) {
		while (menus.hasMoreElements()) {
			JMenu m = menus.nextElement();
			removeMenu(m);
		}
	}

	/** Removes menu from my menu bar */
	public void removeMenu(JMenu menu) {
		for (int i = menuBar.getMenuCount() - 1; i >= 0; i--) {
			JMenu m = menuBar.getMenu(i);
			if (m == menu)
				menuBar.remove(i);
		}
	}

	/** Enables or disables the menu item at the index indx. */
	public void setMenuEnabled(int indx, boolean enabled) {
		menuBar.getMenu(indx).setEnabled(enabled);
	}

	/** Extends my help menu by a new help item */
	public void extendHelp(JMenuItem item) {
		if (helpMenu != null)
			helpMenu.add(item);
	}

	/** Removes the help item from my help */
	public void removeHelp(JMenuItem item) {
		if (helpMenu != null)
			helpMenu.remove(item);
	}

	/** Sets the content of my main panel */
	public void setMainContent(Component comp) {
		mainPanel.removeAll();
		mainPanel.add(comp, BorderLayout.CENTER);
		validate();
		repaint();
	}

	/** Resets the content of my main panel */
	public static void resetMainContent() {
		mainPanel.removeAll();
		mainPanel.add(toolBarPanel, BorderLayout.NORTH);
		mainPanel.add(splitPane, BorderLayout.CENTER);
		statusBar.setMode(editor.getEditMode(), (new agg.gui.event.EditEvent(
				new Object(), editor.getEditMode())).getMessage());
	}

	/** Removes the content of my main panel */
	public void removeMainContent() {
		mainPanel.removeAll();
	}

	public void addToFrameTitle(String dirname, String filename) {
		String str = "";
		if (dirname != null && !dirname.equals("")) {
			if (dirname.endsWith(File.separator))
				str = str.concat(dirname);
			else
				str = str.concat(dirname).concat(File.separator);
		}	
		if (filename != null && !filename.equals("")) {
			str = str.concat(filename);
		}
		if (str.equals("")) {
			appl.setTitle(this.aggTitle);
		} else {
			appl.setTitle(this.aggTitle.concat("     ( ".concat(str).concat(" )")));
		}
	}
	
	private void addHelpMenu() {
		((GraGraHelp) helpMenu).setParentFrame(this);
		menuBar.add(helpMenu);
	}

	private void addDebugMenu() {
		JMenu debug = menuBar.add(new JMenu("DEBUG", true));
		debug.setMnemonic('D');
		JMenuItem mi = debug.add(new JMenuItem("DebugPrefs"));
		mi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DebugFrame df = new DebugFrame();
				df.setVisible(true);
			}
		});
	}

	public void addToolBar(JToolBar aToolBar) {
		toolBarPanel.add(aToolBar);
	}

	public GraGraTreeView getGraGraTreeView() {
		return treeView;
	}

	public GraGraEditor getGraGraEditor() {
		return editor;
	}

	public AGGPreferences getPreferences() {
		return aggPreferences;
	}
	
	public CriticalPairAnalysis getCPA() {
		return criticalPairAnalysis;
	}
	
	public ApplicabilityRuleSequence getARS() {
		return aggApplRuleSequence;
	}
	
	/**
	 * Returns my main panel which contains gragra treeview and gragra editor
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void exportJPEG() {
		exportJPEG.save(editor);
	}

	public void exportAppl2JPEG() {
		exportJPEG.save(mainPanel);
	}

	public void editEventOccurred(EditEvent e) {
		if (e.getMsg() == EditEvent.MENU_KEY) {
			if (e.getMessage().equals("Help"))
				helpMenu.doClick();
		}
	}

//	public void setPreferenceArcUndirected(boolean b) {
//		aggPreferences.selectArcUndirected(b);
//	}
	
	public void setPreferenceNoArcParallel(boolean b) {
		aggPreferences.selectNoArcParallel(b);
	}
	
}
// $Log: AGGAppl.java,v $
// Revision 1.61  2010/09/19 16:23:04  olga
// tuning
//
// Revision 1.60  2010/09/17 20:22:10  olga
// added AGG call parameter -t to select the option Keep Types On Top
//
// Revision 1.59  2010/08/16 14:12:51  olga
// tuning
//
// Revision 1.58  2010/08/16 13:30:06  olga
// tuning
//
// Revision 1.57  2010/08/16 13:27:06  olga
// general (nested) appl conditions enabled by default
//
// Revision 1.56  2010/08/05 14:13:59  olga
// tuning
//
// Revision 1.55  2010/07/29 10:15:15  olga
// use option -gac to activate General Application Conditions
//
// Revision 1.54  2010/06/21 08:39:41  olga
// tuning
//
// Revision 1.53  2010/06/09 12:36:41  olga
// tuning
//
// Revision 1.52  2010/06/09 11:07:13  olga
// tuning
//
// Revision 1.51  2010/04/28 15:21:53  olga
// tuning
//
// Revision 1.50  2010/03/28 22:22:16  olga
// ParallelRule menu item
//
// Revision 1.49  2010/03/08 15:40:22  olga
// code optimizing
//
// Revision 1.48  2010/01/31 16:43:30  olga
// tuning
//
// Revision 1.47  2010/01/27 17:33:13  olga
// improved
//
// Revision 1.46  2010/01/24 17:55:12  olga
// improved
//
// Revision 1.45  2009/06/30 09:50:27  olga
// agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
// agg.xt_basis.Node, Arc: changed: save, load the object name
// agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
//
// workaround of Applicability of Rule Sequences and Object Flow
//
// Revision 1.44  2009/05/28 13:18:29  olga
// Amalgamated graph transformation - development stage
//
// Revision 1.43  2009/03/25 15:19:17  olga
// code tuning
//
// Revision 1.42  2008/11/06 08:45:37  olga
// Graph layout is extended by Zest Graph Layout ( eclipse zest plugin)
//
// Revision 1.41  2008/10/29 09:04:08  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.40  2008/10/22 14:07:50  olga
// GUI, ARS and CPA  tuning
//
// Revision 1.39  2008/10/20 07:42:32  olga
// added : set priority of transformation engine to improve synchronization
// of trafo and graph visualization
//
// Revision 1.38  2008/10/16 18:02:30  olga
// test : AGG Application as Thread
// added : set priority of transformation engine
//
// Revision 1.37  2008/10/08 15:20:05  olga
// Selection of node/edge cannot be undo-redo
//
// Revision 1.36  2008/09/04 07:49:23  olga
// GUI extension: hide nodes, edges
//
// Revision 1.35  2008/07/22 16:22:44  olga
// code tuning
//
// Revision 1.34  2008/05/07 08:37:55  olga
// Applicability of Rule Sequences with NACs
//
// Revision 1.33  2008/04/28 15:07:02  olga
// New feature implemented: Applicability Criteria of Rule Sequences
//
// Revision 1.32  2008/04/11 13:29:05  olga
// Memory usage - tuning
//
// Revision 1.31  2008/02/18 09:37:12  olga
// - an extention of rule dependency check is implemented;
// - some bugs fixed;
// - editing of graphs improved
//
// Revision 1.30  2008/01/07 09:08:39  olga
// - Applying an injective / non-injective rule at non-injective match - bug fixed;
// - Moving nodes/edges in edit mode "Select" - .;
// - CPA: Title of the overlapping graph frame for "delete-use" conflict - bug fixed
//
// Revision 1.29  2007/12/03 08:35:11  olga
// - Some bugs fixed in visualization of morphism mappings after deleting and creating
// nodes, edges
// - implemented: matching with non-injective NAC and Match morphism
//
// Revision 1.28  2007/11/21 12:18:50  olga
// CPA for grammars with node type inheritance is possible without input parameter
//
// Revision 1.27  2007/11/21 09:59:45  olga
// Update V1.6.2.1:
// new features: - default attr value can be set in a type graph and used during  transformation (experimental phase)
// - currently selected node and edge type are shown in the bottom right corner of the AGG GUI
// - Critical pair analysis for grammar with node type inheritance (experimental phase)
//
// Revision 1.26  2007/11/01 09:58:13  olga
// Code refactoring: generic types- done
//
// Revision 1.25  2007/10/22 09:03:17  olga
// First implementation of CPA for grammars with node type inheritance.
// Only for internal tests.
//
// Revision 1.24  2007/09/24 09:42:34  olga
// AGG transformation engine tuning
//
// Revision 1.23  2007/09/10 13:05:23  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.22 2007/07/09 08:00:20 olga
// GUI tuning
//
// Revision 1.21 2007/06/25 08:28:20 olga
// Tuning and Docu update
//
// Revision 1.20 2007/06/18 08:16:02 olga
// New extentions by drawing edge.
//
// Revision 1.19 2007/06/13 08:32:49 olga
// Update: V161
//
// Revision 1.18 2007/03/28 10:00:39 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.17 2007/02/19 09:10:58 olga
// Bug during loading file fixed.
// Type editor tuning
//
// Revision 1.16 2007/02/07 08:38:44 olga
// CPA bug fixed
//
// Revision 1.15 2007/02/05 12:33:42 olga
// CPA: chengeAttribute conflict/dependency : attributes with constants
// bug fixed, but the critical pairs computation has still a gap.
//
// Revision 1.14 2006/12/18 16:49:53 olga
// Optimisation...
//
// Revision 1.13 2006/12/13 13:32:54 enrico
// reimplemented code
//
// Revision 1.12 2006/11/01 11:17:29 olga
// Optimized agg sources of CSP algorithm, match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.11 2006/08/09 07:42:18 olga
// API docu
//
// Revision 1.10 2006/08/02 09:00:56 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.9 2006/03/01 09:55:47 olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.8 2005/11/16 15:20:54 olga
// ...
//
// Revision 1.7 2005/11/16 14:33:23 olga
// Comments
//
// Revision 1.6 2005/11/16 10:19:41 olga
// Memory info commented out
//
// Revision 1.5 2005/10/24 09:04:49 olga
// GUI tuning
//
// Revision 1.4 2005/10/12 10:00:56 olga
// CPA GUI tuning
//
// Revision 1.3 2005/10/10 08:05:16 olga
// Critical Pair GUI and CPA graph
//
// Revision 1.2 2005/09/19 09:12:14 olga
// CPA GUI tuning
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.3 2005/07/11 09:30:20 olga
// This is test version AGG V1.2.8alfa .
// What is new:
// - saving rule option <disabled>
// - setting trigger rule for layer
// - display attr. conditions in gragra tree view
// - CPA algorithm <dependencies>
// - creating and display CPA graph with conflicts and/or dependencies
// based on (.cpx) file
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.17 2005/02/14 09:27:01 olga
// -PAC;
// -GUI, layered graph transformation anzeigen;
// -CPs.
//
// Revision 1.16 2004/10/27 10:06:55 olga
// Version 1.2.4
// Termination of LGTS
//
// Revision 1.15 2004/10/25 14:24:37 olga
// Fehlerbehandlung bei CPs und Aenderungen im zusammenhang mit
// termination-Modul
// in AGG
//
// Revision 1.14 2004/09/23 08:26:43 olga
// Fehler bei CPs weg, Debug output in file
//
// Revision 1.13 2004/06/17 10:21:50 olga
// Start-Transformation mit Anhalten nach einer Ableitung;
// CPs Korrektur und Optimierung
//
// Revision 1.12 2004/03/25 12:34:04 olga
// ....
//
// Revision 1.11 2004/01/22 17:50:52 olga
// tests
//
// Revision 1.10 2003/12/18 16:26:41 olga
// GUI
//
// Revision 1.9 2003/05/30 13:44:52 olga
// GUI Verbesserung
//
// Revision 1.8 2003/03/05 18:24:19 komm
// sorted/optimized import statements
//
// Revision 1.7 2003/01/15 11:36:57 olga
// Kleine Aenderung
//
// Revision 1.6 2002/12/18 14:15:29 olga
// nur test
//
// Revision 1.5 2002/11/25 15:04:39 olga
// Arbeit an dem TypeEditor.
//
// Revision 1.4 2002/11/11 10:45:02 komm
// no change
//
// Revision 1.3 2002/09/23 12:24:07 komm
// added type graph in xt_basis, editor and GUI
//
// Revision 1.2 2002/09/05 16:16:53 olga
// Arbeit an GUI
//
// Revision 1.1.1.1 2002/07/11 12:17:09 olga
// Imported sources
//
// Revision 1.21 2001/08/16 14:05:42 olga
// Aenderungen wegen Layers bei Transformation, Parsieren und CP
//
// Revision 1.20 2001/07/19 15:18:55 olga
// Arbeit an GUI
//
// Revision 1.19 2001/06/26 17:27:17 olga
// Optimierung des Parsers und Optionen Dialogs.
//
// Revision 1.18 2001/06/18 13:42:48 olga
// Weitere Tests am Parser.
//
// Revision 1.17 2001/05/14 11:59:18 olga
// Das Zusammenspiel zwischen AGG GUI und Parser/CP GUIs optimiert.
// Neue Transformationsart implementiert: TransformLayered.java
//
// Revision 1.16 2001/03/22 15:50:02 olga
// Events Behandlung und Ausgabe in GUI.
//
// Revision 1.15 2001/03/08 11:00:00 olga
// Das ist Stand nach der AGG GUI Reimplementierung
// und Parser Anbindung.
//
// Revision 1.14 2000/12/21 10:28:25 olga
// Aenderung wegen LOGO und Icon fuer AGG.
//
// Revision 1.13 2000/12/21 09:48:56 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.12.4.5 2000/12/13 13:22:41 olga
// Fehler beseitigung; Verbesserung der Meldungen bei der Transfoemation.
//
// Revision 1.12.4.4 2000/12/07 14:32:19 olga
// *** empty log message ***
//
// Revision 1.12.4.3 2000/12/06 16:29:38 olga
// *** empty log message ***
//
// Revision 1.12.4.2 2000/12/04 13:25:58 olga
// Erste Stufe der GUI Reimplementierung abgeschlossen:
// - AGGAppl.java optimiert
// - Print eingebaut (GraGraPrint.java)
// - GraGraTreeView.java, GraGraEditorImpl.java optimiert
// - Event eingebaut
// - GraTra umgestellt
//
// Revision 1.12.4.1 2000/11/06 09:32:46 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.12 2000/07/19 13:50:51 shultzke
// NullpointerException aus startmenue beseitigt, hoffentlich
//
// Revision 1.11 2000/06/08 08:42:07 olga
// Priority Menu ist jetzt raus.
//
// Revision 1.10 2000/03/03 11:41:34 shultzke
// *** empty log message ***
//
// Revision 1.8 1999/12/06 08:11:45 shultzke
// A little frame provides some switches to turn debugging stuff on and off.
//
// Revision 1.7 1999/09/15 12:28:26 olga
// *** empty log message ***
//
// Revision 1.6 1999/08/19 07:31:06 gragra
// Sniff will die Klasse nicht mehr uebersetzen
// obwohl ausserhalb von Sniff AGGAppl uebersetzt
// werden kannn
//


package agg.gui.parser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Vector;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import agg.editor.impl.EdGraGra;
import agg.gui.saveload.GraGraLoad;
import agg.gui.treeview.GraGraTreeView;
import agg.parser.CriticalPairOption;
import agg.parser.LayerOption;
import agg.parser.PairContainer;
import agg.parser.LayeredPairContainer;
import agg.parser.ParserFactory;
import agg.parser.ParserOption;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;

/**
 * This dialog guides the user thru all setting which must be done before the
 * parser can start.
 * 
 * @version $Id: ParserDialog.java,v 1.11 2010/09/23 08:20:54 olga Exp $
 * @author $Author: olga $
 */
@SuppressWarnings("serial")
public class ParserDialog extends JDialog implements ActionListener,
		ListSelectionListener {

	/** the tree view from the main AGG gui */
	GraGraTreeView treeView;

	/** the gragra with layout for the host graph */
	EdGraGra hostGraph;

	/** the gragra with layout for the stop graph */
	EdGraGra stopGraph;

	/** the gragra with layout for the critical pairs */
	EdGraGra criticalPairGraGra;

	/** the pairs its self */
	PairContainer criticalPairs;

	/** the gui the dialog is child from */
	Frame parent;

	/** this list holds all gragra names */
	@SuppressWarnings("rawtypes")
	JList gragraNames;

	private Vector<String> gragraNamesVector;

	/** this JScrollPane is needed to scroll the gragra names */
	JScrollPane scrollPane;

	/** click this button to get a load dialog */
	JButton loadButton;

	/** this button cancels this dialog */
	JButton cancel;

	/** click this button to go to the next step */
	JButton nextButton;

	/** only a little panel where the state of the dialog is highlighted */
	StepPanel stepPanel;

	/** shows if all question are answers */
	boolean readyToParse;

	ParserOption pOption;

	CriticalPairOption cpOption;

	LayerOption lOption;

	/**
	 * This panel shows at which step the process halts. There are three steps:<br>
	 * 1) set the host graph<br>
	 * 2) set the stop graph<br>
	 * 3) set the critical pairs<br>
	 * at last the user has to confirm all settings
	 * 
	 * @version $Id: ParserDialog.java,v 1.11 2010/09/23 08:20:54 olga Exp $
	 * @author $Author: olga $
	 */
	public class StepPanel extends JPanel implements Runnable {
		/** step 1 */
		public final static int STEP_HOST_GRAPH = 1;

		/** step 2 */
		public final static int STEP_STOP_GRAPH = 3;

		/** step 3 */
		public final static int STEP_CRITICAL_PAIRS = 7;

		/** the finish */
		public final static int STEP_FINISH = 15;

		/** this text is shown at the different steps */
		String[] steps = { "Select Host Graph", "Select Stop Graph",
				"Select Critical Pairs" };

		/** holds the step which is enabled */
		int enabled = 0;

		/** just create a new panel */
		public StepPanel() {
			setPreferredSize(new Dimension(100, 100));
		}

		/**
		 * this paints the different steps on the panel. Only the color is set
		 * for the steps
		 */
		private void paintSteps(Graphics2D g2d, int startX, int startY,
				int stepNumber) {
			Color text, bullit;
			if (this.enabled > ((int) Math.pow(2, stepNumber + 1)) - 1) {
				/* eintrag ist visited */
				// System.out.println(enabled+"Visited: "+stepNumber);
				bullit = Color.green;
				text = Color.darkGray;
			} else if (this.enabled == ((int) Math.pow(2, stepNumber + 1)) - 1) {
				/* eintrag ist active */
				// System.out.println(enabled+"active: "+stepNumber);
				bullit = Color.red;
				text = Color.black;
			} else {
				/* eintrag ist inactive */
				// System.out.println(enabled+"inactive: "+stepNumber);
				bullit = Color.gray;
				text = Color.gray;
			}
			paintSteps(g2d, startX, startY, stepNumber, bullit, text);
		}

		/** draws the little bullit and the text */
		private void paintSteps(Graphics2D g2d, int startX, int startY,
				int stepNumber, Color bullit, Color text) {
			int x = startX;
			int y = startY;
			int ellipseSize = 5;
			y = y + stepNumber * 15;
			g2d.setColor(bullit);
			Ellipse2D.Float e = new Ellipse2D.Float(x, y, ellipseSize,
					ellipseSize);
			g2d.fillOval(x, y, ellipseSize, ellipseSize);
			g2d.draw(e);
			g2d.setColor(text);
			g2d.drawString(this.steps[stepNumber], x + 12, y + 7);
		}

		/**
		 * paints the panel. That means the bullit and text is drawn
		 * 
		 * @param g
		 *            The current graphics context.
		 */
		public void paint(Graphics g) {
			Dimension d = getSize();
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.lightGray);
			g2d.fillRect(0, 0, d.width, d.height);
			g2d.setColor(Color.black);
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			// g2d.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			g2d.setFont(new Font("Times New Roman", Font.BOLD, 14));
			int[] xs = { 2, 5, 5, 2 };
			int[] ys = { 5, 5, 69, 69 };
			g2d.drawPolyline(xs, ys, 4);
			g2d.setColor(Color.blue);
			g2d.drawString("Start", xs[1] + 10, ys[1] + 7);
			g2d.setColor(Color.black);
			if (this.enabled == ((int) Math.pow(2, 4)) - 1)
				g2d.setColor(new Color(0, 127, 0));
			g2d.setColor(Color.blue);
			g2d.drawString("Finish", xs[2] + 10, ys[2] + 3);
			g2d.setColor(Color.black);
			for (int i = 0; i < 3; i++)
				paintSteps(g2d, xs[1] - 2, ys[1] + 15, i);
		}

		/**
		 * sets the Step which is enabled
		 * 
		 * @param step
		 *            The step of the input process.
		 */
		public void setStep(int step) {
			this.enabled = step;
			repaint();
		}

		/**
		 * returns the step which is enabled
		 * 
		 * @return The current step.
		 */
		public int getStep() {
			return this.enabled;
		}

		/**
		 * sets the steps automatically
		 * 
		 * @deprecated was used for simulation
		 */
		public void run() {
			for (int k = 0; k < 5; k++) {
				// System.out.println(""+k);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
				}
			}
			for (int i = 0; i <= 4; i++) {
				for (int j = 0; j < 1000000; j++) {
				}
				setStep((int) Math.pow(2, i) - 1);
				// enabled = i;
				repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
				}
			}
			// System.out.println("ENDE");
		}

	}

	/**
	 * The created dialog guides the user thru all the needed settings for the
	 * parser.
	 * 
	 * @param parent
	 *            The parent frame of this window.
	 * @param treeView
	 *            The tree provides all current GraGras.
	 * @param pOption
	 *            The option of the parser is necessary for the correct creation
	 *            and load of the parser.
	 * @param cpOption
	 *            The critical pair option is needed for the correct pairs
	 *            (s.a.).
	 * @param lOption
	 *            If there are layer the option is needed to choose the right
	 *            one.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ParserDialog(Frame parent, GraGraTreeView treeView,
			ParserOption pOption, CriticalPairOption cpOption,
			LayerOption lOption) {
		super(parent, "Starting Parser", true);
		this.treeView = treeView;
		this.hostGraph = null;
		this.stopGraph = null;
		this.criticalPairs = null;
		this.criticalPairGraGra = null;
		this.readyToParse = false;
		this.parent = parent;
		this.pOption = pOption;
		this.cpOption = cpOption;
		this.lOption = lOption;
		this.frame = parent;
		setLocation(200, 200);
		setSize(new Dimension(450, 300));

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		GridBagConstraints constraint = new GridBagConstraints();
		// Container c = getContentPane();
		JPanel c = new JPanel();
		c.setLayout(new GridBagLayout());
		c.setBackground(Color.lightGray);
		c.setSize(new Dimension(450, 300));

		JPanel warningPanel = new JPanel(new GridLayout(0, 1));
		warningPanel.setBackground(Color.lightGray);
		warningPanel.setPreferredSize(new Dimension(300, 60));

		JLabel leer = new JLabel("  ");
		warningPanel.add(leer);
		JLabel warning = new JLabel("Parsing rules and stop graph have ");
		warning.setForeground(Color.red);
		warning.setFont(new Font("Times New Roman", Font.BOLD, 14));
		warningPanel.add(warning);
		warning = new JLabel("to be located in the same grammar.");
		warning.setForeground(Color.red);
		warning.setFont(new Font("Times New Roman", Font.BOLD, 14));
		warningPanel.add(warning);

		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = 0;
		constraint.gridheight = 2;
		constraint.weighty = 0.0;
		constraint.weightx = 1.0;
		c.add(warningPanel, constraint);

		this.stepPanel = new StepPanel();
		this.stepPanel.setStep(StepPanel.STEP_HOST_GRAPH);
		this.stepPanel.setPreferredSize(new Dimension(170, 90));

		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.EAST;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		constraint.insets = new Insets(10, 10, 0, 0);
		c.add(this.stepPanel, constraint);

		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(150, 150));
		listPanel.setBackground(Color.lightGray);
		listPanel.setLayout(new GridBagLayout());
		listPanel.setSize(100, 200);
		constraint.fill = GridBagConstraints.BOTH;
		constraint.weighty = 0.1;
		constraint.weightx = 0.0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.gridheight = 1;
		constraint.insets = new Insets(0, 0, 0, 0);
		c.add(listPanel, constraint);

		JPanel spaceDummy = new JPanel();
		spaceDummy.setBackground(Color.lightGray);
		spaceDummy.setSize(100, 1);
		constraint.weighty = 1.0;
		constraint.weightx = 1.0;
		c.add(spaceDummy, constraint);

		JSeparator js = new JSeparator();
		constraint.weighty = 0.0;
		constraint.weightx = 1.0;
		constraint.insets = new Insets(0, 0, 5, 0);
		c.add(js, constraint);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(350, 30)); // 200,30));
		buttonPanel.setBackground(Color.lightGray);
		buttonPanel.setLayout(new GridBagLayout());
		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		c.add(buttonPanel, constraint);

		/* gragra namen liste */
		this.gragraNamesVector = treeView.getGraGraNames();
		this.gragraNames = new JList(this.gragraNamesVector); // treeView.getGraGraNames());
		this.gragraNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.gragraNames.addListSelectionListener(this);

		this.scrollPane = new JScrollPane(this.gragraNames,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.scrollPane.setPreferredSize(new Dimension(150, 150));

		constraint.fill = GridBagConstraints.NONE;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.gridheight = 1;
		constraint.insets = new Insets(10, 0, 0, 10);
		JLabel topic = new JLabel("from GraGra...");
		topic.setForeground(Color.black);
		// topic.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		topic.setFont(new Font("Times New Roman", Font.BOLD, 14));
		listPanel.add(topic, constraint);

		constraint.fill = GridBagConstraints.BOTH;
		constraint.weighty = 1.0;
		constraint.weightx = 1.0;
		listPanel.add(this.scrollPane, constraint);

		this.loadButton = new JButton("Load...");
		this.loadButton.addActionListener(this);

		constraint.fill = GridBagConstraints.NONE;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.anchor = GridBagConstraints.CENTER;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		listPanel.add(this.loadButton, constraint);

		constraint.insets = new Insets(0, 0, 0, 0);
		constraint.fill = GridBagConstraints.BOTH;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		constraint.gridheight = 1;// GridBagConstraints.RELATIVE;
		/* gragra namen liste */

		/* next und cancel button */
		this.nextButton = new JButton();
		this.nextButton.setText("Next");
		this.nextButton.addActionListener(this);
		this.cancel = new JButton("Cancel");
		this.cancel.addActionListener(this);

		JPanel dragPanel = new JPanel();
		dragPanel.setBackground(Color.lightGray);
		dragPanel.setPreferredSize(new Dimension(100, 1));
		constraint.fill = GridBagConstraints.BOTH;
		constraint.weighty = 1.0;
		constraint.weightx = 1.0;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		buttonPanel.add(dragPanel, constraint);

		constraint.fill = GridBagConstraints.NONE;
		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		buttonPanel.add(this.nextButton, constraint);

		JPanel spaceDummy2 = new JPanel();
		spaceDummy2.setBackground(Color.lightGray);
		spaceDummy2.setPreferredSize(new Dimension(50, 1));
		constraint.weighty = 1.0;
		constraint.weightx = 0.0;
		buttonPanel.add(spaceDummy2, constraint);

		constraint.weighty = 0.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = 1;
		constraint.gridheight = 1;
		// constraint.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(this.cancel, constraint);

		JPanel spaceDummy3 = new JPanel();
		spaceDummy3.setBackground(Color.lightGray);
		spaceDummy3.setPreferredSize(new Dimension(20, 1));
		constraint.weighty = 1.0;
		constraint.weightx = 0.0;
		constraint.gridwidth = GridBagConstraints.REMAINDER;
		buttonPanel.add(spaceDummy3, constraint);
		/* ENDE next und cancel button */

		c.revalidate();
				
		JScrollPane scroll = new JScrollPane(c);
		scroll.setPreferredSize(new Dimension(450, 300));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(scroll, BorderLayout.CENTER);
		validate();
		
		pack();
	}

	private Frame frame;

	public void showDialog() {
		setLocationRelativeTo(this.frame);
		super.setVisible(true);
	}

	/** checks if a host graph is selected and sets the next step */
	public void checkHostGraphAndNextStep() {
		if (this.hostGraph != null) {
			this.stepPanel.setStep(StepPanel.STEP_STOP_GRAPH);
			this.gragraNames.setSelectedIndex(-1);
		}
	}

	/** checks if a stop graph is selected and sets the next step */
	public void checkStopGraphAndNextStep() {
		if (this.stopGraph != null)
			this.stepPanel.setStep(StepPanel.STEP_CRITICAL_PAIRS);
	}

	/** checks if the critical pairs are selected and sets the next step */
	public void checkPairsAndNextStep() {
		setPairs();
		if (this.criticalPairs != null || this.criticalPairGraGra != null) {
			this.stepPanel.setStep(StepPanel.STEP_FINISH);
			this.nextButton.setText("Finish");
			if (this.criticalPairs != null) {
				this.criticalPairGraGra = null;
			}
		}
	}

	/**
	 * sets either the host graph or the stop graph
	 * 
	 * @param step
	 *            choose from StepPanel which graph to set
	 */
	private void setGraph(int step) {
		if (this.gragraNames.getSelectedValue() == null)
			return;
		if (step == StepPanel.STEP_HOST_GRAPH
				|| step == StepPanel.STEP_STOP_GRAPH) {
			String name = (String) this.gragraNames.getSelectedValue();
			boolean found = false;
			for (int i = 0; i < this.treeView.getGraGras().size() && !found; i++) {
				EdGraGra gragra = this.treeView.getGraGras().elementAt(i);
				if (name.equals(gragra.getName())) {
					found = true;
					switch (step) {
					case StepPanel.STEP_HOST_GRAPH:
						this.hostGraph = gragra;
						break;
					case StepPanel.STEP_STOP_GRAPH:
						this.stopGraph = gragra;
						break;
					default:
						break;
					}
				}
			}
		}
	}

	/** sets the critical pairs */
	private void setPairs() {
		if (this.gragraNames.getSelectedValue() != null) {
			String nameStr = (String) this.gragraNames.getSelectedValue();
			boolean found = false;
			for (int i = 0; i < this.treeView.getGraGras().size() && !found; i++) {
				EdGraGra gragra = this.treeView.getGraGras().elementAt(i);
				String name = nameStr;
				int indx = nameStr.indexOf("(");
				if (indx != -1)
					name = nameStr.substring(0, indx);
				// System.out.println(nameStr+" "+name);
				if (name.equals(gragra.getName())) {
					this.criticalPairGraGra = gragra;
				}
			}
			if (this.criticalPairGraGra == null && this.stopGraph != null)
				this.criticalPairGraGra = this.stopGraph;
		} else if (this.stopGraph != null) {
			this.criticalPairGraGra = this.stopGraph;
		}
	}

	/** checks if all information for the parser are set */
	private void checkIfReadyToParse() {
		if (this.hostGraph != null)
			if (this.stopGraph != null)
				if (this.criticalPairs != null || this.criticalPairGraGra != null) {
					this.readyToParse = true;
				}
	}

	/**
	 * if this returns true all information are set to start the parser
	 * 
	 * @return Hopefully returns <I>true</I>.
	 */
	public boolean isReadyToParse() {
		return this.readyToParse;
	}

	/** just close the window */
	private void quitDialog() {
		dispose();
	}

	/** checks in which state the dialog is */
	private void checkState() {
		switch (this.stepPanel.getStep()) {
		case StepPanel.STEP_HOST_GRAPH:
			checkHostGraphAndNextStep();
			break;
		case StepPanel.STEP_STOP_GRAPH:
			checkStopGraphAndNextStep();
			break;
		case StepPanel.STEP_CRITICAL_PAIRS:
			checkPairsAndNextStep();
			break;
		case StepPanel.STEP_FINISH:
			checkIfReadyToParse();
			quitDialog();
			break;
		default:
			break;
		}
	}

	/**
	 * loads a graph
	 * 
	 * @param step
	 *            choose from StepPanel which graph to set
	 */
	@SuppressWarnings("unchecked")
	private void loadGraph(int step) {
		if (step == StepPanel.STEP_HOST_GRAPH
				|| step == StepPanel.STEP_STOP_GRAPH) {
//			String fileName = "";
//			String dirName = "";
			GraGraLoad gragraLoad = new GraGraLoad((JFrame) this.parent);
			gragraLoad.setDirName(this.treeView.getFileDirectory());
			EdGraGra loadedGraGra = null;
			gragraLoad.load();
			if (gragraLoad.getGraGra() != null) {
//				dirName = gragraLoad.getDirName();
//				fileName = gragraLoad.getFileName();

				loadedGraGra = gragraLoad.getGraGra();
				BaseFactory.theFactory().notify(loadedGraGra.getBasisGraGra());
				// System.out.println(loadedGraGra);
				// add loaded name to list of gragra names
				String str = loadedGraGra.getName() + "("
						+ loadedGraGra.getFileName() + ")";
				this.gragraNamesVector.addElement(str);
				this.gragraNames.setListData(this.gragraNamesVector);
				this.gragraNames.setSelectedIndex(this.gragraNamesVector.size() - 1);
			}
			switch (step) {
			case StepPanel.STEP_HOST_GRAPH:
				this.hostGraph = loadedGraGra;
				break;
			case StepPanel.STEP_STOP_GRAPH:
				this.stopGraph = loadedGraGra;
				break;
			default:
				break;
			}
		}
	}

	/** load the critical pairs */
	@SuppressWarnings("unchecked")
	private void loadPairs() {
		this.criticalPairs = null;
		PairContainer excludePairContainer = null;
		GraGra nullGraGra = BaseFactory.theFactory().createGraGra();
		this.cpOption.enableLayered(true);
		excludePairContainer = ParserFactory.createEmptyCriticalPairs(
				nullGraGra, this.cpOption);
		PairIOGUI pairIOgui = new PairIOGUI((JFrame) this.parent,
				excludePairContainer);
		pairIOgui.setDirectoryName(this.treeView.getFileDirectory());
		Object o = pairIOgui.load();
		if (o == null)
			return;

		String fileName = pairIOgui.getFileName();

		if (!(o instanceof LayeredPairContainer))
			this.cpOption.enableLayered(false);

		// add loaded name to list of gragra names
		String str = excludePairContainer.getGrammar().getName() + "("
				+ fileName + ")";
		this.gragraNamesVector.addElement(str);
		this.gragraNames.setListData(this.gragraNamesVector);
		this.gragraNames.setSelectedIndex(this.gragraNamesVector.size() - 1);

		// set critical pairs
		this.criticalPairs = excludePairContainer;
	}

	/** control which step will be loaded */
	private void load() {
		switch (this.stepPanel.getStep()) {
		case StepPanel.STEP_HOST_GRAPH:
			loadGraph(StepPanel.STEP_HOST_GRAPH);
			break;
		case StepPanel.STEP_STOP_GRAPH:
			loadGraph(StepPanel.STEP_STOP_GRAPH);
			break;
		case StepPanel.STEP_CRITICAL_PAIRS:
			loadPairs();
			break;
		case StepPanel.STEP_FINISH:
			break;
		default:
			break;
		}
	}

	/**
	 * evaluates the different buttons
	 * 
	 * @param e
	 *            The different events frim the buttons.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.nextButton) {
			checkState();
		} else if (source == this.cancel) {
			quitDialog();
		} else if (source == this.loadButton) {
			load();
		}
	}

	/**
	 * evaluates the list of gragra names
	 * 
	 * @param e
	 *            The event from the gragra name list.
	 */
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		if (source == this.gragraNames) {
			switch (this.stepPanel.getStep()) {
			case StepPanel.STEP_HOST_GRAPH:
				setGraph(StepPanel.STEP_HOST_GRAPH);
				break;
			case StepPanel.STEP_STOP_GRAPH:
				setGraph(StepPanel.STEP_STOP_GRAPH);
				break;
			case StepPanel.STEP_CRITICAL_PAIRS:
				setPairs();
				break;
			case StepPanel.STEP_FINISH:
				break;
			default:
				break;
			}
		}
	}

	/**
	 * return the information for the parser: here the host graph
	 * 
	 * @return The returned host graph is choosen from the list or loaded.
	 */
	public EdGraGra getHostGraphGrammar() {
		return this.hostGraph;
	}

	/**
	 * return the information for the parser: here the stop graph
	 * 
	 * @return The returned stop graph is choosen from the list or loaded.
	 */
	public EdGraGra getStopGraphGrammar() {
		return this.stopGraph;
	}

	/**
	 * return the information for the parser: here the gragra of the critical
	 * pairs. This method returns ony a gragra if the user selects one in the
	 * list.
	 * 
	 * @return The gragra from the list.
	 */
	public EdGraGra getCriticalPairGraGra() {
		return this.criticalPairGraGra;
	}

	/**
	 * return the information for the parser: here the critical pairs This
	 * method returns only the critical pairs if they are loaded.
	 * 
	 * @return The loaded critical pairs.
	 */
	public PairContainer getCriticalPairs() {
		return this.criticalPairs;
	}

	/*
	 * this method starts only the dialog
	 * 
	 * @deprecated use the main AGG gui to launch the parser
	 * @param args
	 *            The parameters.
	 */
//	public static void main(String[] args) {
//		ParserDialog theFrame = new ParserDialog(null, null, null, null, null);
//		theFrame.show();
//	}
	
}
/**
 * $Log: ParserDialog.java,v $
 * Revision 1.11  2010/09/23 08:20:54  olga
 * tuning
 *
 * Revision 1.10  2010/04/13 15:06:09  olga
 * dialog layout improved
 *
 * Revision 1.9  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.8  2007/11/05 09:18:21  olga
 * code tuning
 *
 * Revision 1.7  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/06/13 08:33:07 olga Update:
 * V161
 * 
 * Revision 1.5 2006/12/13 13:33:04 enrico reimplemented code
 * 
 * Revision 1.4 2006/04/10 09:19:30 olga Import Type Graph, Import Graph -
 * tuning. Attr. member type check: if class does not exist. Graph constraints
 * for a layer of layered grammar.
 * 
 * Revision 1.3 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.2 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/06/28 08:09:55 olga Folgefehler Besetigung nach der
 * Anpassung der Attributekomponente fuer CPs
 * 
 * Revision 1.3 2004/01/22 17:50:52 olga tests
 * 
 * Revision 1.2 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.8 2001/07/19 15:19:05 olga Arbeit an GUI
 * 
 * Revision 1.7 2001/07/09 13:12:44 olga Aenderungen an GUI. Version heisst ab
 * jetzt 1.1
 * 
 * Revision 1.6 2001/07/04 10:40:05 olga Kleine GUI Aenderungen
 * 
 * Revision 1.5 2001/05/14 11:52:58 olga Parser GUI Optimierung
 * 
 * Revision 1.4 2001/04/11 14:57:00 olga Arbeit an der GUI.
 * 
 * Revision 1.3 2001/03/08 13:46:09 olga Wenn keine kritische Paaren geladen
 * wurden, dann wird die Stop GraGra als kritische GraGra gesetzt.
 * 
 * Revision 1.2 2001/03/08 11:02:47 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.8 2001/01/28 13:14:46 shultzke API fertig
 * 
 * Revision 1.1.2.7 2001/01/11 14:11:06 shultzke Laden und speicher in Parser
 * eingearbeitet
 * 
 * Revision 1.1.2.6 2001/01/03 09:44:55 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.1.2.5 2000/11/08 14:58:02 shultzke parser erste stufe fertig
 * 
 */

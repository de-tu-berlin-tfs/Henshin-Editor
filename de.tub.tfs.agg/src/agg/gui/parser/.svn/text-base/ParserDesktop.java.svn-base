package agg.gui.parser;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Vector;

//import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.gui.IconResource;
import agg.gui.editor.GraphCanvas;
import agg.gui.editor.GraphEditor;
import agg.gui.editor.GraphPanel;
import agg.gui.options.ParserGUIOption;
import agg.gui.parser.event.StatusMessageEvent;
import agg.gui.parser.event.StatusMessageListener;
import agg.parser.Parser;
import agg.parser.ParserErrorEvent;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.parser.ParserMessageEvent;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;

//****************************************************************************+
/**
 * This desktop provides a view into the parsing process. There are two windows
 * available. One shows the host graph, the second shows the stop graph for
 * compare how close the host graph is. Attention, please check the option to be
 * sure that the parsing process is set visible.
 * 
 * @author $Author: olga $
 * @version $Id: ParserDesktop.java,v 1.22 2010/09/23 08:20:53 olga Exp $
 */
public class ParserDesktop implements InternalFrameListener,
		ParserEventListener {

	private static final int STOP_GRAPH = 1;

	private static final int HOST_GRAPH = 2;

	private AGGParser aggparser;

	/** main desktop */
	final JDesktopPane desktop = new JDesktopPane();

	/** the frame icon for the host graph */
//	private ImageIcon hostIcon;

	/** the frame icon for the stop graph */
//	private ImageIcon stopIcon;

	/** layout of the two graphs */
	private EdGraGra layout;

	/** size of the two graph windows */
	private Dimension internalFrameSize;

	private ParserGUIOption option;

	private Parser parser;

	protected JInternalFrame hostFrame;

	private GraphEditor hostGraphEditor;

	private EdGraph hostGraphLayout;

//	private Graph hostGraph;

	protected JInternalFrame stopFrame;

	private GraphEditor stopGraphEditor;

	private EdGraph stopGraphLayout;

//	private Graph stopGraph;

	private boolean graphFramesExist;
	
	private Vector<StatusMessageListener> listener;

	private KeyAdapter keyAdapter;

	private String typedKey;

	private MouseListener ml;

	protected JPopupMenu graphMenu = new JPopupMenu("Graph");
	protected JMenuItem miLayoutGraph = new JMenuItem("Layout Graph");
	protected GraphPanel activeGraphPanel;
	
	/**
	 * Creates a empty desktop configured with the option.
	 * 
	 * @param option
	 *            The option to configure the desktop.
	 */
	public ParserDesktop(AGGParser aggparser, ParserGUIOption option) {
		this(aggparser, option, null, null, null);
	}

	/**
	 * Creates a new desktop. This desktop shows the host and stop graph as it
	 * is configured in the option. The layout for the graphs is given by the
	 * graph grammar.
	 * 
	 * @param aggparser
	 * 				the parser instance
	 * @param option
	 *            the option to configure the desktop
	 * @param aLayout
	 *            the grammar provides the layout for the graphs
	 * @param aHostGraph
	 *            the host graph of the parsing process
	 * @param aStopGraph
	 *            the stop graph of the parsing process
	 */
	public ParserDesktop(final AGGParser aggparser, 
			final ParserGUIOption option,
			final EdGraGra aLayout, 
			final Graph aHostGraph, 
			final Graph aStopGraph) {
		
		this.desktop.setBackground(Color.white);
		this.desktop.setForeground(Color.white);
		
		this.aggparser = aggparser;
		this.option = option;
		setInternalFrameSize(new Dimension(200, 200));

		this.keyAdapter = new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				performShortKeyEvent(e);
			}
		};
		this.desktop.addKeyListener(this.keyAdapter);

		makeGraphMenu();
		
		this.ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					if (e.getSource() instanceof GraphCanvas) {
						ParserDesktop.this.activeGraphPanel = ((GraphCanvas) e.getSource())
									.getViewport();
						ParserDesktop.this.graphMenu.show(ParserDesktop.this.activeGraphPanel, e.getX(), e.getY());
					}
				}
			}
			
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {					
					if (e.getSource() instanceof GraphCanvas) {
						ParserDesktop.this.activeGraphPanel = ((GraphCanvas) e.getSource())
									.getViewport();
						ParserDesktop.this.graphMenu.show(ParserDesktop.this.activeGraphPanel, e.getX(), e.getY());
					}
				}
			}
			
			public void mouseClicked(MouseEvent e) {
					
			}
			
			public void mouseEntered(MouseEvent e) {
				ParserDesktop.this.desktop.requestFocusInWindow();
			}
		};
		
		this.desktop.addMouseListener(this.ml);

		this.listener = new Vector<StatusMessageListener>();

		if (aHostGraph != null && aStopGraph != null) {
			this.makeGraphFrames();
			
			setLayout(aLayout);
			setStopGraph(aStopGraph);
			setHostGraph(aHostGraph);
		}
	}

	private void makeGraphFrames() {
		makeStopGraphFrame();
		makeHostGraphFrame();
		this.graphFramesExist = true;
	}
	
	private void makeStopGraphFrame() {
		if (this.stopFrame == null) {
			// stop graph editor and frame
			this.stopGraphEditor = new GraphEditor(null);
			this.stopGraphEditor.setEditMode(agg.gui.editor.EditorConstants.VIEW);
			this.stopGraphEditor.getGraphPanel().setName("");
			this.stopGraphEditor.removeTitlePanel();
			this.stopGraphEditor.getGraphPanel().getCanvas().addMouseListener(this.ml);
			
			this.stopFrame = new JInternalFrame("Stop Graph", true, false, true, true);
			this.stopFrame.setFrameIcon(IconResource.getIconFromURL(IconResource
					.getStopIcon()));
			this.stopFrame.setBackground(Color.white);
			this.stopFrame.setForeground(Color.white);
			this.stopFrame.addInternalFrameListener(this);
			this.stopFrame.addMouseListener(this.ml);
			this.stopFrame.addKeyListener(this.keyAdapter);
			this.stopFrame.setVisible(false);
			this.stopFrame.setSize(this.internalFrameSize);
			this.stopFrame.getContentPane().add(this.stopGraphEditor);
			this.stopFrame.pack();
			
			this.desktop.add(this.stopFrame, Integer.valueOf(STOP_GRAPH));
		}
	}
	
	private void makeHostGraphFrame() {
		if (this.hostFrame == null) {
			// host graph editor and frame
			this.hostGraphEditor = new GraphEditor(null);
			this.hostGraphEditor.setEditMode(agg.gui.editor.EditorConstants.VIEW);
			this.hostGraphEditor.removeTitlePanel();
			this.hostGraphEditor.getGraphPanel().setName("");
			this.hostGraphEditor.getGraphPanel().getCanvas().addMouseListener(this.ml);
			
			this.hostFrame = new JInternalFrame("Host Graph", true, false, true, true);
			this.hostFrame.setFrameIcon(IconResource.getIconFromURL(IconResource
					.getWorkerIcon()));
			this.hostFrame.setBackground(Color.white);
			this.hostFrame.setForeground(Color.white);
			this.hostFrame.addInternalFrameListener(this);
			this.hostFrame.addMouseListener(this.ml);
			this.hostFrame.addKeyListener(this.keyAdapter);
			this.hostFrame.setVisible(false);
			this.hostFrame.setPreferredSize(new Dimension(600, 400));
			this.hostFrame.setLocation(200, 0);
			
			this.hostFrame.getContentPane().add(this.hostGraphEditor);
			this.hostFrame.pack();
			
			this.desktop.add(this.hostFrame, Integer.valueOf(HOST_GRAPH));
		}
	}

	boolean performShortKeyEvent(KeyEvent e) {
		int keyCode = e.getKeyCode();
		// System.out.println("ParserDesktop:: Shift: "+ e.isShiftDown()+" Ctrl:
		// "+e.isControlDown()+" Alt: "+ e.isAltDown());
		// System.out.println("ParserDesktop:: keyReleased:: key: Code:
		// "+keyCode+" Char: "+e.getKeyChar()+" Text:
		// "+KeyEvent.getKeyText(keyCode)/*+" ModifiersText:
		// "+KeyEvent.getKeyModifiersText(keyCode)*/);
		if (e.isShiftDown() && e.isAltDown()) {
			this.typedKey = KeyEvent.getKeyText(keyCode);
			// System.out.println("ShiftDown() && AltDown:: typedKey:
			// "+typedKey);
			if (this.typedKey.equals("S"))
				this.aggparser.startParser();
			else if (this.typedKey.equals("Q"))
				this.aggparser.stopParser();
			else if (this.typedKey.equals("Z"))
				this.aggparser.backToGUI();
			else
				return false;
		}
		return true;
	}

	/**
	 * Returns the component of the graph desktop. This component can be
	 * displayed in a frame or panel.
	 * 
	 * @return This component is a JDesktopPane.
	 */
	public Component getComponent() {
		return getDesktop();
	}

	// ****************************************************************************+
	/**
	 * Returns the desktop with the graphs of this gui.
	 * 
	 * @return The JDesktopPane
	 */
	public JDesktopPane getDesktop() {
		return this.desktop;
	}

	/**
	 * Sets the new layout for the host and stop graph. The layout is taken from
	 * the graph grammar.
	 * 
	 * @param layout
	 *            The graph grammar with layout.
	 */
	public void setLayout(EdGraGra layout) {
		this.layout = layout;
		this.hostGraphLayout = null;
	}

	public void setStopLayout(EdGraph layout) {
		this.stopGraphLayout = layout;
	}

	/**
	 * The size of the internal windows is specified here.
	 * 
	 * @param internalFrameSize
	 *            The size of the new window.
	 */
	public void setInternalFrameSize(Dimension internalFrameSize) {
		this.internalFrameSize = internalFrameSize;
	}

	private void setGraph(Graph graph, int graphType) {
		if (graphType != HOST_GRAPH && graphType != STOP_GRAPH)
			return;

		switch (graphType) {
		case STOP_GRAPH:
			if (this.stopGraphEditor.getGraph() != null)
				this.stopGraphEditor.setGraph(null);

			EdGraph eg = null;
			if (this.stopGraphLayout != null) {
				if (this.stopGraphLayout.getBasisGraph() == graph)
					eg = this.stopGraphLayout;
				else {
					eg = new EdGraph(graph, this.layout.getTypeSet());
					eg.doDefaultEvolutionaryGraphLayout(20);
				}
			} else if (graph != null) {
				eg = new EdGraph(graph, this.layout.getTypeSet());
				eg.doDefaultEvolutionaryGraphLayout(20);
			}

			if (eg != null && this.stopGraphLayout != null)
				eg.setLayoutByBasisObject(this.stopGraphLayout);

			if (this.option.getParserDisplay() == ParserGUIOption.SHOWHOSTGRAPH
					+ ParserGUIOption.SHOWSTOPGRAPH)
				this.stopFrame.setVisible(true);
			else
				this.stopFrame.setVisible(false);
			
//			this.stopGraphLayout.setEditable(false);
			this.stopGraphEditor.setGraph(this.stopGraphLayout);
			// stopgege.getGraphPanel().updateGraphics();
			break;

		case HOST_GRAPH:
			if (this.hostGraphEditor.getGraph() != null)
				this.hostGraphEditor.setGraph(null);

			if (this.hostGraphLayout == null && graph != null) {
				this.hostGraphLayout = new EdGraph(graph, this.layout.getTypeSet());
				this.hostGraphLayout.setTransformChangeEnabled(true);
				this.hostGraphLayout.setLayoutByIndex(this.layout.getGraph(), false);
			}

			if (this.option.getParserDisplay() >= ParserGUIOption.SHOWHOSTGRAPH)
				this.hostFrame.setVisible(true);
			else
				this.hostFrame.setVisible(false);
			
//			hostGraphLayout.setEditable(false);
			if (this.hostGraphEditor.getGraph() == null)
				this.hostGraphEditor.setGraph(this.hostGraphLayout);
			else
				this.hostGraphEditor.getGraphPanel().setGraph(this.hostGraphLayout, false);
			this.hostGraphEditor.getGraphPanel().updateGraphics();
			break;
		default:
			break;
		}
	}

	/**
	 * Updates a internal frame with a new graph.
	 * 
	 * @param om
	 *            The morphism holds the new graph. The morphism is necessary to
	 *            get the layout information from the old graph.
	 * @param graphType
	 *            The graph type distinguish which graph will be updated.
	 */
	protected void updateFrame(OrdinaryMorphism om, int graphType) {
		if (graphType != HOST_GRAPH)
			return;

		if (this.hostGraphLayout.getBasisGraph() != om.getImage()) {
			EdGraph oldLayout = this.hostGraphLayout;
			this.hostGraphLayout = new EdGraph(om.getImage(), this.layout.getTypeSet());
			this.hostGraphLayout.getBasisGraph().setName("");
			if (!this.hostGraphLayout.updateLayoutByIsoMorphism(om, oldLayout))
				this.hostGraphLayout.setLayoutByIndex(oldLayout, false);
			this.hostGraphLayout.resolveArcOverlappings(15);
		}
		this.hostGraphLayout.setTransformChangeEnabled(true);
		updateFrame(this.hostFrame, this.hostGraphLayout);
	}

	/**
	 * Updates a internal frame with a graph.
	 * 
	 * @param theFrame
	 *            The internal frame for the graph.
	 * @param graphLayout
	 *            The new graph with layout.
	 */
	protected void updateFrame(JInternalFrame theFrame, EdGraph graphLayout) {
		if (theFrame == this.hostFrame) {
			hostFrameSetAnimationIcon();
			this.hostGraphEditor.getGraphPanel().setGraph(graphLayout, false);
		} else if (theFrame == this.stopFrame) {
			this.stopGraphEditor.getGraphPanel().setGraph(graphLayout, false);
		}

	}

	/**
	 * Updates an internal frame with a new graph. The internal frame is
	 * selected by the graph type.
	 * 
	 * @param graph
	 *            The new graph for the internal frame.
	 * @param graphType
	 *            The graph type distinguishes the kind of graph.
	 */
	protected void updateFrame(Graph graph, int graphType) {
		if (graphType == HOST_GRAPH) {
			if (this.hostGraphLayout.getBasisGraph() != graph) {
				EdGraph eg = new EdGraph(graph, this.layout.getTypeSet());
				eg.setLayoutByIndex(this.hostGraphLayout, false);
				eg.resolveArcOverlappings(15);
				this.hostGraphLayout = eg;
				this.hostGraphLayout.setTransformChangeEnabled(true);
				updateFrame(this.hostFrame, this.hostGraphLayout);
			} else {
				this.hostGraphLayout.resolveArcOverlappings(15);
				updateFrame(this.hostFrame,this. hostGraphLayout);
			}
		}
	}

	/**
	 * The new host graph is set. A parser creates new graphs if a certain graph
	 * must be copied.
	 * 
	 * @param graph
	 *            The new graph.
	 */
	public void setHostGraph(Graph graph) {
//		hostGraph = graph;
		setGraph(graph, HOST_GRAPH);
	}

	/**
	 * The new stop graph is set.
	 * 
	 * @param graph
	 *            The new graph.
	 */
	public void setStopGraph(Graph graph) {
//		stopGraph = graph;
		setGraph(graph, STOP_GRAPH);
	}

	/**
	 * Sets a new parser for the display. All important information like host
	 * graph and so one are taken from the parser.
	 * 
	 * @param parser
	 *            The parser to display.
	 */
	public void setParser(Parser parser) {
		if (!this.graphFramesExist) {
			this.makeGraphFrames();
		}
		
		this.parser = parser;
		this.parser.addParserEventListener(this);
		
		setStopGraph(parser.getStopGraph());
		setHostGraph(parser.getHostGraph());
	}

	/**
	 * Sets a new parser for the display. All important information like host
	 * graph and so one are taken from the parser.
	 * 
	 * @param parser
	 *            The parser to display.
	 * @param om
	 *            The morphism holds the copy of the original host graph.
	 */
	public void setParser(Parser parser, OrdinaryMorphism om) {
		if (!this.graphFramesExist) {
			this.makeGraphFrames();
		}
		
		this.parser = parser;
		this.parser.addParserEventListener(this);
		
		setStopGraph(parser.getStopGraph());
		setHostGraph(om.getOriginal());
	}

	/*
	 * ======================================================================
	 * Internal Frame Listener
	 * ======================================================================
	 */
	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameActivated(InternalFrameEvent e) {		
		if (((JInternalFrame)e.getSource()) == this.hostFrame) {
			this.hostFrame.toFront();
		} else if (((JInternalFrame)e.getSource()) == this.stopFrame) {
			this.stopFrame.toFront();
		}
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameClosed(InternalFrameEvent e) {
		// Invoked when an internal frame has been closed.
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameClosing(InternalFrameEvent e) {
		// Invoked when an internal frame is in the process of being closed.
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameDeactivated(InternalFrameEvent e) {
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameDeiconified(InternalFrameEvent e) {
		// Invoked when an internal frame is de-iconified.
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameIconified(InternalFrameEvent e) {
		// Invoked when an internal frame is iconified.
	}

	/**
	 * This method is inherited from the InternalFrameListener. But it is not
	 * used.
	 * 
	 * @param e
	 *            The event from the listener.
	 */
	public void internalFrameOpened(InternalFrameEvent e) {
		// Invoked when a internal frame has been opened.
	}

	/**
	 * This method is called if the parser fires events. In this case it is
	 * important after every derivation from the parser to update the gui.
	 * 
	 * @param p
	 *            The event from the parser.
	 */
	public void parserEventOccured(ParserEvent p) {
		if (p instanceof ParserMessageEvent) {
			String message = ((ParserMessageEvent) p).getMessage();
			Object source = p.getSource();
			if (message.indexOf("applied") != -1
					|| message.indexOf("IsoCopy") != -1) {
				fireStatusMessageEvent(new StatusMessageEvent(this, "", message));
				// System.out.println("parserEventOccured: "+message);

				if (source instanceof Parser) {
					updateFrame(((Parser) source).getHostGraph(), HOST_GRAPH);
				} else if (source instanceof OrdinaryMorphism) {
					updateFrame((OrdinaryMorphism) source, HOST_GRAPH);
				}
			} else if (message.indexOf("Result") != -1) {
				if (source instanceof Parser) {
					hostFrameResetIcon();
					updateFrame(((Parser) source).getHostGraph(), HOST_GRAPH);
				}
			}
		} else if (p instanceof ParserErrorEvent) {
			String message = ((ParserErrorEvent) p).getMessage();
			fireStatusMessageEvent(new StatusMessageEvent(this, "ERROR",
					"Error: " + message));
		}
	}

	public void hostFrameSetAnimationIcon() {
		this.hostFrame.setFrameIcon(IconResource.getIconFromURL(IconResource
				.getWorkingIcon()));
	}

	public void hostFrameResetIcon() {
		this.hostFrame.setFrameIcon(IconResource.getIconFromURL(IconResource
				.getWorkerIcon()));
	}

	/**
	 * Here register all listener to receive status messages. The AGG has to
	 * register the status bar here.
	 * 
	 * @param l
	 *            The listener, e.g. the status bar.
	 */
	public void addStatusMessageListener(StatusMessageListener l) {
		if (!this.listener.contains(l))
			this.listener.addElement(l);
	}

	private void fireStatusMessageEvent(StatusMessageEvent sme) {
		for (int i = 0; i < this.listener.size(); i++)
			this.listener.elementAt(i).newMessage(sme);
	}

	public void disposeTestHostGraph(EdGraGra gra) {
		if (gra.getGraphOf(this.hostGraphLayout.getBasisGraph()) == null)
			this.hostGraphLayout.dispose();
	}

	private void makeGraphMenu() {
		this.graphMenu.add(this.miLayoutGraph);
		this.miLayoutGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (ParserDesktop.this.activeGraphPanel != null
						&& ParserDesktop.this.activeGraphPanel.getGraph() != null) {
					makeLayout(ParserDesktop.this.activeGraphPanel.getGraph(), ParserDesktop.this.activeGraphPanel
							.getSize());
					ParserDesktop.this.activeGraphPanel.updateGraphics();
				}
			}
		});
	}
	
	protected void makeLayout(EdGraph g, Dimension d) {
		g.updateVisibility();
		final List<EdNode> visiblenodes = g.getVisibleNodes();

		g.setCurrentLayoutToDefault(false);
		g.getDefaultGraphLayouter().setEnabled(true);
		Dimension dim = g.getDefaultGraphLayouter().getNeededPanelSize(visiblenodes);
		if (dim.width < 350)
			dim.width = 350;
		if (dim.width < d.width)
			dim.width = d.width;
		if (dim.height < 250)
			dim.height = 250;
		if (dim.height < d.height)
			dim.height = d.height;
		g.getDefaultGraphLayouter().setPanelSize(dim);
		g.getDefaultGraphLayouter().allowChangePanelSize(false);
		g.getDefaultGraphLayouter().setEnabled(true);
		g.doDefaultEvolutionaryGraphLayout(
				g.getDefaultGraphLayouter(), 100, 10);
	}
	
}

// End of ParserDesktop.java
/*
 * $Log: ParserDesktop.java,v $
 * Revision 1.22  2010/09/23 08:20:53  olga
 * tuning
 *
 * Revision 1.21  2010/03/08 15:43:09  olga
 * code optimizing
 *
 * Revision 1.20  2008/11/19 13:04:17  olga
 * Parser tuning
 *
 * Revision 1.19  2008/11/13 08:26:21  olga
 * some tests
 *
 * Revision 1.18  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.17  2008/09/11 09:22:26  olga
 * Some changes in CPA: new computing of conflicts after an option changed,
 * Graph layout of overlapping graphs
 *
 * Revision 1.16  2008/09/04 07:50:27  olga
 * GUI extension: hide nodes, edges
 *
 * Revision 1.15  2008/05/05 09:11:52  olga
 * Graph parser - bug fixed.
 * New AGG feature - Applicability of Rule Sequences - in working.
 *
 * Revision 1.14  2008/04/07 09:36:56  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.13  2007/11/19 08:48:41  olga
 * Some GUI usability mistakes fixed.
 * Default values in node/edge of a type graph implemented.
 * Code tuning.
 *
 * Revision 1.12  2007/11/05 09:18:21  olga
 * code tuning
 *
 * Revision 1.11  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.10  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.9 2007/07/02 08:27:34 olga Help docu
 * update, Source tuning
 * 
 * Revision 1.8 2007/06/13 08:33:07 olga Update: V161
 * 
 * Revision 1.7 2007/04/19 14:50:04 olga Loading grammar - tuning
 * 
 * Revision 1.6 2007/04/19 07:52:46 olga Tuning of: Undo/Redo, Graph layouter,
 * loading grammars
 * 
 * Revision 1.5 2007/04/11 10:03:41 olga Undo, Redo tuning, Simple Parser- bug
 * fixed
 * 
 * Revision 1.4 2007/03/28 10:01:12 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.3 2006/12/13 13:33:04 enrico reimplemented code
 * 
 * Revision 1.2 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/11/07 16:03:05 olga Anzeige von Overlap-Graphen in CPA
 * verbessert
 * 
 * Revision 1.2 2002/10/02 18:30:55 olga XXX
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.5 2001/07/04 10:40:05 olga Kleine GUI Aenderungen
 * 
 * Revision 1.4 2001/05/14 11:52:58 olga Parser GUI Optimierung
 * 
 * Revision 1.3 2001/03/22 15:52:30 olga GUI an den veraenderten GraphEditor
 * angepasst.
 * 
 * Revision 1.2 2001/03/08 11:02:46 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.9 2001/01/28 13:14:46 shultzke API fertig
 * 
 * Revision 1.1.2.8 2001/01/03 09:44:55 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.1.2.7 2001/01/02 12:28:57 shultzke Alle Optionen angebunden
 * 
 * Revision 1.1.2.6 2001/01/01 21:24:31 shultzke alle Parser fertig inklusive
 * Layout
 * 
 * Revision 1.1.2.5 2000/11/08 14:58:02 shultzke parser erste stufe fertig
 * 
 * Revision 1.1.2.4 2000/11/01 14:55:24 shultzke conflictfree part fast fertig
 * 
 * Revision 1.1.2.3 2000/11/01 12:19:21 shultzke erste Regelanwendung im parser
 * CVs: ----------------------------------------------------------------------
 * 
 * Revision 1.1.2.2 2000/09/21 14:02:08 shultzke ExcludePair bei nacs erweitert
 * 
 * Revision 1.1.2.1 2000/09/20 13:28:54 shultzke Parser Desktop entworfen
 * 
 */

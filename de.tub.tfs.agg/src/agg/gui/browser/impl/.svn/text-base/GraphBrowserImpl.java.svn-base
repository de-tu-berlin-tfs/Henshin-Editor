package agg.gui.browser.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
//import agg.editor.impl.EdTypeSet;
import agg.gui.browser.GraphBrowser;
import agg.gui.editor.EditorConstants;
import agg.gui.editor.GraphEditor;
import agg.gui.event.LoadEvent;
import agg.gui.event.LoadEventListener;
import agg.gui.event.SaveEvent;
import agg.gui.event.SaveEventListener;
import agg.gui.popupmenu.ModePopupMenu;
import agg.gui.saveload.GraGraLoad;
import agg.gui.saveload.GraGraSave;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;

/**
 * A graph browser for viewing graphs. A graph can be one of types:
 * <code>agg.editor.impl.EdGraph</code> or <code>agg.xt_basis.Graph</code>
 * 
 * @author $Author: olga $
 * @version $Id: GraphBrowserImpl.java,v 1.10 2010/09/23 08:18:19 olga Exp $
 */
public class GraphBrowserImpl extends JPanel implements GraphBrowser,
		SaveEventListener, LoadEventListener {

	static int ITS_WIDTH = 500;

	static int ITS_HEIGHT = 300;

	GraphEditor editor;

	ModePopupMenu modePopupMenu; // select + move

	private EdGraGra gragra;

	private EdGraph graph;

//	private EdTypeSet types;

	private Object myObject;

	private String msg;

	public GraphBrowserImpl() {
		super(true);
		setLayout(new BorderLayout());

		// create graph editor
		this.editor = new GraphEditor(null);
		add(this.editor, BorderLayout.CENTER);

		// create and set popup menu
		this.modePopupMenu = new ModePopupMenu();
		this.modePopupMenu.setViewModel(true);
		this.modePopupMenu.setLabel("Edit Modes");
		this.modePopupMenu.setEditor(this.editor);

		this.editor.setEditMode(EditorConstants.SELECT);
		this.editor.setEditCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getSource() == GraphBrowserImpl.this.editor.getGraphPanel().getCanvas()
						&& e.isPopupTrigger()) {
					if (GraphBrowserImpl.this.modePopupMenu.invoked(GraphBrowserImpl.this.editor, GraphBrowserImpl.this.editor.getGraphPanel(), e
							.getX(), e.getY()))
						GraphBrowserImpl.this.modePopupMenu
								.show(e.getComponent(), e.getX(), e.getY());
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (e.getSource() == GraphBrowserImpl.this.editor.getGraphPanel().getCanvas()
						&& e.isPopupTrigger()) {
					if (GraphBrowserImpl.this.modePopupMenu.invoked(GraphBrowserImpl.this.editor, GraphBrowserImpl.this.editor.getGraphPanel(), e
							.getX(), e.getY()))
						GraphBrowserImpl.this.modePopupMenu
								.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		};
		this.editor.getGraphPanel().getCanvas().addMouseListener(ml);
	}

	/* Implementing of save / load event listeners */

	public void saveEventOccurred(SaveEvent e) {
		// System.out.println("GraphBrowser.saveEventOccurred " +e.getMsg());
		int msgkey = e.getMsg();
		this.msg = "";
		if ((msgkey == SaveEvent.PROGRESS_BEGIN)
				|| (msgkey == SaveEvent.PROGRESS_FINISHED)
				|| (msgkey == SaveEvent.SAVED)) {
			this.msg = "";
			return;
		}
		this.msg = e.getMessage();
		// System.out.println("SaveEvent msg: "+msg);
	}

	public void loadEventOccurred(LoadEvent e) {
		// System.out.println("GraphBrowser.loadEventOccurred " +e.getMsg());
		int msgkey = e.getMsg();
		this.msg = "";
		if ((msgkey == LoadEvent.PROGRESS_BEGIN)
				|| (msgkey == LoadEvent.PROGRESS_FINISHED)
				|| (msgkey == LoadEvent.LOADED)) {
			this.msg = "";
			return;
		}
		this.msg = e.getMessage();
		// System.out.println("LoadEvent this.msg: "+this.msg);
	}

	/** Return my <code>JPanel</code>. */
	public JPanel getPanel() {
		return this;
	}

	/** Return my graph. */
	public Object getGraph() {
		return this.myObject;
	}

	/** Set <code>EdGraph</code> to show. */
	public void setGraph(EdGraph g) {
		this.myObject = g;
		this.graph = g;
		// this.graph.setTypeSet(types);
		this.editor.setGraph(this.graph);
	}

	/** Set <code>Graph</code> to show. */
	public void setGraph(Graph g) {
		this.myObject = g;
		this.graph = new EdGraph(g);
		// this.graph.setTypeSet(types);
		this.editor.setGraph(this.graph);
	}

	/** Show graph. */
	public void showGraph() {
		this.graph.update();
		this.editor.getGraphPanel().updateGraphics(true);
	}

	/**
	 * Return gragra. The gragra is of type
	 * <code>agg.editor.impl.EdGraGra</code>.
	 */
	public EdGraGra getGraGra() {
		return this.gragra;
	}

	/**
	 * Return gragra. The gragra is of type <code>agg.xt_basis.GraGra</code>.
	 */
	public GraGra getBaseGraGra() {
		return this.gragra.getBasisGraGra();
	}

	/**
	 * Set gragra. The gragra is of type <code>agg.editor.impl.EdGraGra</code>.
	 */
	public void setGraGra(EdGraGra gra) {
		this.gragra = gra;
//		types = this.gragra.getTypeSet();
		this.graph = this.gragra.getGraph();
	}

	/**
	 * Set gragra. The gragra is of type <code>agg.xt_basis.GraGra</code>.
	 */
	public void setGraGra(GraGra gragra) {
		this.gragra = new EdGraGra(gragra);
//		types = this.gragra.getTypeSet();
		this.graph = this.gragra.getGraph();
	}

	/** Load gragra. */
	public EdGraGra loadGraGra(JFrame frame) {
//		String dirName = "";
//		String fileName = "";
		// get load dialog
		GraGraLoad gragraLoad = new GraGraLoad(frame);
		gragraLoad.addLoadEventListener(this);
		gragraLoad.load();
		if (!this.msg.equals("")) {
//			dirName = gragraLoad.getDirName();
			JOptionPane.showMessageDialog(frame, this.msg);
		} else if (gragraLoad.getGraGra() != null) {
//			dirName = gragraLoad.getDirName();
//			fileName = gragraLoad.getFileName();
			// System.out.println(dirName+"/"+fileName);
			// get gragra
			this.gragra = gragraLoad.getGraGra();
			this.gragra.update();
			// BaseFactory.theFactory().notify(this.gragra.getBasisGraGra());

			// set types
//			types = this.gragra.getTypeSet();

			// set graph
			this.graph = this.gragra.getGraph();

			JOptionPane.showMessageDialog(frame, "Loading is finished");
		}
		return this.gragra;
	}

	/** Load base gragra. */
	public GraGra loadBaseGraGra(JFrame frame) {
//		String dirName = "";
//		String fileName = "";

		// load dialog
		GraGraLoad gragraLoad = new GraGraLoad(frame);
		gragraLoad.addLoadEventListener(this);
		gragraLoad.loadBase();
		if (!this.msg.equals("")) {
//			dirName = gragraLoad.getDirName();
			JOptionPane.showMessageDialog(frame, "Loading is failed");
			return null;
		} else if (gragraLoad.getBaseGraGra() != null) {
//			dirName = gragraLoad.getDirName();
//			fileName = gragraLoad.getFileName();

			// get base gragra
			GraGra basis = gragraLoad.getBaseGraGra();
			BaseFactory.theFactory().notify(basis);

			JOptionPane.showMessageDialog(frame, "Loading is finished");
			return basis;
		} else
			return null;
	}

	/**
	 * Save gragra. The gragra is of type <code>agg.editor.impl.EdGraGra</code>.
	 */
	public void saveAs(JFrame frame) {
		// get save dialog
		GraGraSave gragraSave = new GraGraSave(frame, "", "");
		gragraSave.addSaveEventListener(this);
		gragraSave.setGraGra(this.gragra);
		gragraSave.saveAs();
		if (!this.msg.equals(""))
			JOptionPane.showMessageDialog(frame, "Saving is failed");
		else
			JOptionPane.showMessageDialog(frame, "Saving was successful.");
	}

	/** Read base graph and update graphics. */
	public void updateGraphics() {
		if (this.editor.getGraph() != null) {
			this.editor.getGraph().update();
			this.editor.getGraphPanel().updateGraphics(true);
		}
	}

	public static void main(String[] args) {
		// create frame
		JFrame frame = new JFrame("AGG Graph Browser");

		WindowListener l = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		};
		frame.addWindowListener(l);

		frame.setBackground(Color.white);
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(screenSize.width / 2 - ITS_WIDTH / 2, screenSize.height
				/ 2 - ITS_HEIGHT / 2);
		frame.setSize(ITS_WIDTH, ITS_HEIGHT);

		ImageIcon icon = makeIcon("AGG_ICON64.gif");
		if (icon != null)
			frame.setIconImage(icon.getImage());
		else
			System.out.println("AGG_ICON64.gif not found!");

		// create browser
		GraphBrowserImpl browser = new GraphBrowserImpl();

		frame.getContentPane().add(browser, BorderLayout.CENTER);
		frame.setVisible(true);
	}

	public static ImageIcon makeIcon(String gifFile) {
		byte[] buffer = null;
		try {
			Class<?> baseClass = Class.forName("agg.gui.AGGAppl");
			InputStream resource = baseClass.getResourceAsStream(gifFile);
			if (resource == null) {
				System.err.println(baseClass.getName() + "/" + gifFile
						+ " not found.");
				return null;
			}
			BufferedInputStream in = new BufferedInputStream(resource);
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			buffer = new byte[1024];
			int n;
			while ((n = in.read(buffer)) > 0) {
				out.write(buffer, 0, n);
			}
			in.close();
			out.flush();

			buffer = out.toByteArray();
			if (buffer.length == 0) {
				System.err.println("warning: " + gifFile + " is zero-length");
				return null;
			}
		} catch (Exception ioe) {
			System.err.println(ioe.toString());
			return null;
		}
		return new ImageIcon(buffer);
	}

}
// $Log: GraphBrowserImpl.java,v $
// Revision 1.10  2010/09/23 08:18:19  olga
// tuning
//
// Revision 1.9  2010/03/08 15:40:47  olga
// code optimizing
//
// Revision 1.8  2008/10/29 09:04:13  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.7  2007/11/05 09:18:22  olga
// code tuning
//
// Revision 1.6  2007/11/01 09:58:19  olga
// Code refactoring: generic types- done
//
// Revision 1.5  2007/09/24 09:42:39  olga
// AGG transformation engine tuning
//
// Revision 1.4  2007/09/10 13:05:49  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.3 2006/12/13 13:33:05 enrico
// reimplemented code
//
// Revision 1.2 2006/08/14 08:26:24 olga
// AGG help update
// Rule editor tuning
//
// Revision 1.1 2005/08/25 11:57:00 enrico
// *** empty log message ***
//
// Revision 1.2 2005/06/20 13:37:04 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:04 olga
// Version with Eclipse
//
// Revision 1.2 2003/03/05 18:24:28 komm
// sorted/optimized import statements
//
// Revision 1.1.1.1 2002/07/11 12:17:16 olga
// Imported sources
//
// Revision 1.5 2000/12/21 09:49:17 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.4.8.2 2000/12/06 17:27:32 olga
// *** empty log message ***
//
// Revision 1.4 1999/09/09 10:25:09 mich
// Update Shared Source Working Environment
//
// Revision 1.3 1999/08/17 10:51:23 shultzke
// neues Package hinzugefuegt
//

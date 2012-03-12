package agg.gui.options;

import java.awt.Dimension;
import java.util.Vector;

import agg.gui.parser.event.GUIOptionEvent;
import agg.gui.parser.event.GUIOptionListener;

/**
 * This class provides different settings for the display of the parser.
 * Although the class name seems belonging to parser this class stores some
 * settings for the critical pair display, too.
 * 
 * @version $Id: ParserGUIOption.java,v 1.3 2010/09/23 08:20:39 olga Exp $
 * @author $Author: olga $
 */
public class ParserGUIOption {

	/**
	 * The value if the parsing process is invisible.
	 */
	public static final int PARSINGINVISIBLE = 0;

	/**
	 * The value if the parsing process shows the host graph.
	 */
	public static final int SHOWHOSTGRAPH = 1;

	/**
	 * The value if the parsing process shows the stop graph.
	 */
	public static final int SHOWSTOPGRAPH = 2;

	/**
	 * The number of critical pairs shown at the same time.
	 */
	public static final int SHOWNOPAIRS = 0;

	/**
	 * The maximum number of critical pairs makes no diffrent if there is one or
	 * two more.
	 */
	public static final int SHOWALLPAIRS = Integer.MAX_VALUE;

	private int delay;

	private int parserDisplay;

	private int numberOfCriticalPair;

	private Dimension criticalPairWindowSize;

	private int criticalStyle = 0;
	
	private Vector<GUIOptionListener> listener;

	/** Initialize the option with default values */
	public ParserGUIOption() {
		/* default werte */
		this.parserDisplay = SHOWHOSTGRAPH + SHOWSTOPGRAPH;
		this.numberOfCriticalPair = SHOWALLPAIRS;
		this.delay = 100;
		this.criticalPairWindowSize = new Dimension(200, 200);
		this.listener = new Vector<GUIOptionListener>();
	}

	/**
	 * Add here any option listener.
	 * 
	 * @param ol
	 *            The option listener to add.
	 */
	public void addOptionListener(GUIOptionListener ol) {
		this.listener.addElement(ol);
	}

	/**
	 * If a option listener doesn't want to receive any news. The option
	 * listener must be removed.
	 * 
	 * @param ol
	 *            The option listener to remove.
	 */
	public void removeOptionListener(GUIOptionListener ol) {
		this.listener.remove(ol);
	}

	private void fireOptionEvent(GUIOptionEvent o) {
		for (int i = 0; i < this.listener.size(); i++)
			this.listener.elementAt(i).optionHasChanged(o);
	}

	/**
	 * This method tells the user which graph should be displayed. There are
	 * three posibilities: invisible only host graph host and stop graph
	 * 
	 * @return The value of the display.
	 */
	public int getParserDisplay() {
		return this.parserDisplay;
	}

	/**
	 * This method sets a the desired display. Choose one of the posible option:
	 * invisible host graph stop + host graph
	 * 
	 * @param option
	 *            The option
	 */
	public void setParserDisplay(int option) {
		this.parserDisplay = option;
		fireOptionEvent(new GUIOptionEvent(this, GUIOptionEvent.PARSERDISPLAY));
	}

	/**
	 * Tells how much overlapping graph at the critical pair analysis are
	 * displayed at the same time.
	 * 
	 * @return Number of overlapping graphs.
	 */
	public int getNumberOfCriticalPair() {
		return this.numberOfCriticalPair;
	}

	/**
	 * Sets the number of overlapping graphs which are displayed at the same
	 * time.
	 * 
	 * @param option
	 *            The number of overlapping graphs.
	 */
	public void setNumberOfCriticalPair(int option) {
		this.numberOfCriticalPair = option;
		fireOptionEvent(new GUIOptionEvent(this,
				GUIOptionEvent.NUMBEROFCRITICALPAIR));
	}

	/**
	 * Get the size of the window of the overlapping graph of the critical pair
	 * analysis.
	 * 
	 * @return The size of the window.
	 */
	public Dimension getCriticalPairWindowSize() {
		return this.criticalPairWindowSize;
	}

	/**
	 * Sets the window size of the overlapping graph.
	 * 
	 * @param option
	 *            The window size.
	 */
	public void setCriticalPairWindowSize(Dimension option) {
		this.criticalPairWindowSize = option;
		fireOptionEvent(new GUIOptionEvent(this,
				GUIOptionEvent.CRITICALPAIRWINDOWSIZE));
	}

	/**
	 * Sets the window size of the overlapping graph.
	 * 
	 * @param width
	 *            The width of the window size.
	 * @param height
	 *            The height of the window size.
	 */
	public void setCriticalPairWindowSize(int width, int height) {
		setCriticalPairWindowSize(new Dimension(width, height));
	}

	/**
	 * States how to draw critical objects of CPA critical overlapping graphs:
	 * <code>EdGraphObject.CRITICAL_GREEN</code> or
	 * <code>EdGraphObject.CRITICAL_BLACK_BOLD</code>.
	 */
	public void setDrawingStyleOfCriticalObjects(int criticalStyle) {
		this.criticalStyle = criticalStyle;
	}
	
	/**
	 * Returns style (0 or 1) how to draw critical objects of CPA critical overlapping graphs:
	 * <code>EdGraphObject.CRITICAL_GREEN</code> = 0, 
	 * <code>EdGraphObject.CRITICAL_BLACK_BOLD</code> = 1.
	 */
	public int getDrawingStyleOfCriticalObjects() {
		return this.criticalStyle;
	}
	
	/**
	 * Set the delay time of the graph parsing. It is used to improve the
	 * perfomance of the transformed graph.
	 */
	public void setDelayAfterApplyRule(int miliseconds) {
		this.delay = miliseconds;
	}

	/**
	 * Gets the delay time of the graph parsing.
	 */
	public int getDelayAfterApplyRule() {
		return this.delay;
	}
}
/*
 * $Log: ParserGUIOption.java,v $
 * Revision 1.3  2010/09/23 08:20:39  olga
 * tuning
 *
 * Revision 1.2  2009/03/25 15:19:16  olga
 * code tuning
 *
 * Revision 1.1  2008/10/29 09:04:13  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.3  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/06/13 08:33:08 olga Update:
 * V161
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:47 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:47 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/08/10 12:22:12 shultzke Ausserdem wird nicht mehr eine
 * neues GUIObject erzeugt, wenn zur ParserGUI umgeschaltet wird. Einige Klassen
 * wurden umbenannt. Alle Events sind in ein eigenes Eventpackage geflogen.
 * 
 * Revision 1.1.2.2 2000/08/07 10:38:51 shultzke Option erweitert
 * 
 * Revision 1.1.2.1 2000/08/06 22:28:59 shultzke Option Model erzeugt
 * 
 */

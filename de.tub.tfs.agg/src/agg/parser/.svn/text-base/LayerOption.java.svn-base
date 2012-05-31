package agg.parser;

import java.util.EventObject;
import java.util.Vector;

/**
 * This class provides some option for the layer function.
 * 
 * @version $Id: LayerOption.java,v 1.3 2010/09/23 08:24:59 olga Exp $
 * @author $Author: olga $
 */
public class LayerOption {

	/**
	 * Layer for rules, creation, deletion and negative application condition,
	 * Rule must delete
	 */
	public static final int RCDN_LAYER = 0;

	/**
	 * Layer for rules, creation and deletio, Rule must delete
	 */
	public static final int RCD_LAYER = 1;

	/**
	 * Layer for rules, creation, deletion and negative application condition
	 * and Rule must not delete
	 */
	public static final int WEAK_RCDN_LAYER = 2;

	/**
	 * Layer for rules, creation and deletion and Rule must not delete
	 */
	public static final int WEAK_RCD_LAYER = 3;

	private int layer;

	private Vector<OptionEventListener> listener;

	/**
	 * Creates option with default values.
	 */
	public LayerOption() {
		this.layer = RCDN_LAYER;
		this.listener = new Vector<OptionEventListener>();
	}

	/**
	 * Sets the required algorithm of the layer function
	 * 
	 * @param l
	 *            The algorithm.
	 */
	public void setLayer(int l) {
		this.layer = l;
	}

	/**
	 * Sets the required algorithm of the layer function
	 * 
	 * @param l
	 *            The algorithm.
	 */
	public void setLayer(String l) {
		if (l.equals("RCDN_LAYER"))
			this.layer = RCDN_LAYER;
		else if (l.equals("WEAK_RCDN_LAYER"))
			this.layer = WEAK_RCDN_LAYER;
		else if (l.equals("RCD_LAYER"))
			this.layer = RCD_LAYER;
		else if (l.equals("WEAK_RCD_LAYER"))
			this.layer = WEAK_RCD_LAYER;
		fireOptionEvent(new EventObject(this));
	}

	/**
	 * Get the required layer function algorithm.
	 * 
	 * @return The algorithm.
	 */
	public int getLayer() {
		return this.layer;
	}

	/**
	 * Adds an option listener.
	 * 
	 * @param l
	 *            The listener.
	 */
	public void addOptionListener(OptionEventListener l) {
		if (!this.listener.contains(l))
			this.listener.addElement(l);
	}

	/**
	 * Removes an option listener
	 * 
	 * @param l
	 *            The listener.
	 */
	public void removeOptionListener(OptionEventListener l) {
		if (this.listener.contains(l))
			this.listener.removeElement(l);
	}

	/**
	 * Sends a event to all its listeners.
	 * 
	 * @param event
	 *            The event which will be sent
	 */
	private synchronized void fireOptionEvent(EventObject event) {
		for (int i = 0; i < this.listener.size(); i++) {
			this.listener.elementAt(i).optionEventOccurred(event);
		}
	}

}
/*
 * $Log: LayerOption.java,v $
 * Revision 1.3  2010/09/23 08:24:59  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.4 2001/08/16 14:14:08 olga LayerFunction erweitert:
 * ExtendedLayerFunction erbt LayerFunction (checkLayer ueberschrieben)
 * WeakLayerFunction erbt LayerFunction ( checkLayer ueberschrieben)
 * WeakExtendedLayerFunction erbt WeakLayerFunction ( checkLayer ueberschrieben)
 * 
 * Revision 1.3 2001/08/08 14:46:30 olga Default Layer Option Einstellung ist
 * RCDN_LAYER.
 * 
 * Revision 1.2 2001/03/08 10:44:52 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:54 shultzke API fertig
 * 
 * Revision 1.1.2.2 2000/12/26 10:00:04 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.1 2000/12/18 13:33:40 shultzke Optionen veraendert
 * 
 */

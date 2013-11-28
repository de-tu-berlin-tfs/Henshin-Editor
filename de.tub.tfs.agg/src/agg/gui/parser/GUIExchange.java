package agg.gui.parser;

import java.awt.Component;

import agg.gui.AGGAppl;

/**
 * This class exchanges the main gui of AGG and another component.
 * 
 * @version $Id: GUIExchange.java,v 1.5 2010/09/23 08:20:54 olga Exp $
 * @author $Author: olga $
 */
public class GUIExchange {

	private AGGAppl parent;

	private boolean hasVal;

	/**
	 * The exchanger will be initialized with the AGG which will be exchanged.
	 * 
	 * @param parent
	 *            the parent frame
	 */
	public GUIExchange(AGGAppl parent) {
		this.parent = parent;
		
		this.hasVal = false;
	}

	/**
	 * Here the component will be exchanged
	 * 
	 * @param c
	 *            The component to exchange with.
	 */
	public void changeWith(Component c) {
		this.parent.setMainContent(c);

		this.parent.repaint();
		this.parent.validate();
		this.hasVal = true;
	}

	/** To get the main gui from AGG back just call restore */
	public void restore() {
		AGGAppl.resetMainContent();

		this.parent.repaint();
		this.parent.validate();
		this.hasVal = false;
	}

	/**
	 * If a gui is exchanged and set in this object the result is true.
	 * 
	 * @return True if a component is stored.
	 */
	public boolean isSet() {
		return this.hasVal;
	}
}
/*
 * $Log: GUIExchange.java,v $
 * Revision 1.5  2010/09/23 08:20:54  olga
 * tuning
 *
 * Revision 1.4  2010/03/08 15:43:09  olga
 * code optimizing
 *
 * Revision 1.3  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/06/13 08:33:08 olga Update: V161
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:10 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:43 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.6 2001/01/28 13:14:43 shultzke API fertig
 * 
 * Revision 1.1.2.5 2001/01/10 20:19:35 shultzke load save verbessert
 * 
 * Revision 1.1.2.4 2000/12/10 14:55:47 shultzke um Layer erweitert
 * 
 * Revision 1.1.2.3 2000/08/21 07:09:02 shultzke *** empty log message ***
 * 
 * Revision 1.1.2.2 2000/07/09 17:12:33 shultzke grob die GUI eingebunden
 * 
 */

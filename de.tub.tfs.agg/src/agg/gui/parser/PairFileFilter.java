package agg.gui.parser;

import agg.gui.saveload.ExtensionFileFilter;

/**
 * This is a filter for file names. If this filter is set in a load or save
 * dialog only files with a special extension are displayed. This filter only
 * shows files with <CODE>.cpx</CODE> extension.
 * 
 * @version $Id: PairFileFilter.java,v 1.4 2008/10/29 09:04:12 olga Exp $
 * @author $Author: olga $
 */
public class PairFileFilter extends ExtensionFileFilter {

	/**
	 * Creates a new filter.
	 */
	public PairFileFilter() {
		super(".cpx", "Critical Pairs XML (.cpx)");
	}

	public PairFileFilter(String filter, String description) {
		super(filter, description);
	}
}
/*
 * $Log: PairFileFilter.java,v $
 * Revision 1.4  2008/10/29 09:04:12  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.3  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI
 * tuning
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:45 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:45 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/12/12 13:27:42 shultzke erste Versuche kritische Paare
 * mit XML abzuspeichern
 * 
 */

package agg.parser;

import agg.xt_basis.Graph;

/**
 * A triple stores three data. This triple stores only specific data. This class
 * is necessary to push three data on a stack at the same time.
 * 
 * @author $Author: olga $
 * @version $Id: TripleData.java,v 1.2 2010/09/23 08:25:00 olga Exp $
 */
public class TripleData {

	private Graph hostGraph;

	private RuleInstances eri;

	private Integer layer;

	/**
	 * Creates a new tripel.
	 * 
	 * @param hostGraph
	 *            The first data.
	 * @param eri
	 *            The second data.
	 * @param layer
	 *            The third data.
	 */
	public TripleData(Graph hostGraph, RuleInstances eri, Integer layer) {
		this.hostGraph = hostGraph;
		this.eri = eri;
		this.layer = layer;
	}

	/**
	 * Returns the first data.
	 * 
	 * @return The host graph.
	 */
	public Graph getHostGraph() {
		return this.hostGraph;
	}

	/**
	 * Returns the second data.
	 * 
	 * @return The rule instance.
	 */
	public RuleInstances getRuleInstance() {
		return this.eri;
	}

	/**
	 * The third data.
	 * 
	 * @return The layer.
	 */
	public Integer getLayer() {
		return this.layer;
	}
}
/*
 * $Log: TripleData.java,v $
 * Revision 1.2  2010/09/23 08:25:00  olga
 * tuning
 *
 * Revision 1.1  2009/07/08 16:22:02  olga
 * Multiplicity bug fixed;
 * ARS development
 *
 * Revision 1.2  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:44:57 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:59 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/12/26 10:00:06 shultzke Layered Parser hinzugefuegt
 * 
 */

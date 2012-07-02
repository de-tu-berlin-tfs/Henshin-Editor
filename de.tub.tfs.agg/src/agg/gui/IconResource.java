package agg.gui;

import java.net.URL;

import javax.swing.ImageIcon;

/**
 * This class supplies access to various icons and other pictures. Please look
 * at method description which icon can be accessed. It must be checked if you
 * get back a valid URL.
 * 
 * @author $Author: olga $
 * @version $Id: IconResource.java,v 1.7 2010/06/09 11:07:13 olga Exp $
 */
public class IconResource {

	private final static String LIBPATH = "agg/lib/";

	private final static String ICONPATH = LIBPATH + "icons/";

	private final static String GRAGRAICONFILE = "gragra.gif";

	private final static String GRAGRAEXPORTICONFILE = "gragraExport.gif";

	private final static String GRAGRAIMPORTICONFILE = "gragraImport.gif";

	private final static String CONTROLFLOWICONFILE = "controlFlow.gif";

	private final static String GRAPHICONFILE = "graph.gif";

	private final static String TYPEGRAPHICONFILE = "typegraph.gif";

	private final static String GRAPHEXPORTICONFILE = "graphExport.gif";

	private final static String GRAPHIMPORTICONFILE = "graphImport.gif";

	private final static String NACICONFILE = "nac.gif";

	private final static String PACICONFILE = "pac.jpg";

	private final static String ACICONFILE = "nestedApplCond.gif";
	
	private final static String RULEICONFILE = "rule.gif";

	private final static String RULESCHEMEICONFILE = "ruleScheme.gif";
	
	private final static String RULEEXPORTICONFILE = "ruleExport.gif";

	private final static String RULEIMPORTICONFILE = "ruleImport.gif";

	private final static String ATOMICCONSTRICONFILE = "atomic.gif";

	private final static String FORMULACONSTRICONFILE = "formula.gif";

	private final static String POSTCONSTRICONFILE = "post.gif";

	private final static String CONSTRAINTICONFILE = "constr.gif";

	private final static String ATOMCONSTRICONFILE = "atomconstr.gif";

	private final static String OVERLAPINGGRAPHICONFILE = "overlapingGraph.gif";

	private final static String STOPICONFILE = "stop.gif";

	private final static String VERSIONID = "Version.id";

	private final static String AGGLOGO = "AGG_LOGO.gif";

	private final static String AGGICON = "AGG_ICON64.gif";

	private final static String OK = "ok2.gif";

	private final static String WRONG = "answer_bad2.gif";

	private final static String OPTION = "OptionIcon.gif";

	private final static String SMALL_STOP = "stop_small.gif";

	private final static String CONSTRUCTION = "Baustelle_anim2.gif";

	private final static String WORKER = "Baustelle.gif";

	private final static String ATTRCONDITIONICONFILE = "attrCondition.gif";

	private IconResource() {
		// never create a instance of this class
	}

	private static final URL makeURL(String source) {
		return ClassLoader.getSystemClassLoader().getResource(source);
	}

	/**
	 * The location for the gragra icon which is displayed at top of a gragra in
	 * the tree.
	 */
	public final static URL getURLGraGra() {
		return makeURL(ICONPATH + GRAGRAICONFILE);
	}

	/**
	 * The location for the export gragra icon which is displayed in the tree.
	 */
	public final static URL getURLGraGraExport() {
		return makeURL(ICONPATH + GRAGRAEXPORTICONFILE);
	}

	/**
	 * The location for the import gragra icon which is displayed in the tree.
	 */
	public final static URL getURLGraGraImport() {
		return makeURL(ICONPATH + GRAGRAIMPORTICONFILE);
	}

	/**
	 * The location for the control flow icon
	 */
	public final static URL getURLControlFlow() {
		return makeURL(ICONPATH + CONTROLFLOWICONFILE);
	}

	/**
	 * The location for the graph icon which is displayed in the tree.
	 */
	public final static URL getURLGraph() {
		return makeURL(ICONPATH + GRAPHICONFILE);
	}

	/**
	 * The location for the graph icon which is displayed in the tree.for the
	 * type graph
	 */
	public final static URL getURLTypeGraph() {
		return makeURL(ICONPATH + TYPEGRAPHICONFILE);
	}

	/**
	 * The location for the export graph icon which is displayed in the tree.
	 */
	public final static URL getURLGraphExport() {
		return makeURL(ICONPATH + GRAPHEXPORTICONFILE);
	}

	/**
	 * The location for the import graph icon which is displayed in the tree.
	 */
	public final static URL getURLGraphImport() {
		return makeURL(ICONPATH + GRAPHIMPORTICONFILE);
	}

	/**
	 * The location for the negative application condition icon which is
	 * displayed in the tree.
	 */
	public final static URL getURLNAC() {
		return makeURL(ICONPATH + NACICONFILE);
	}

	public final static URL getURLPAC() {
		return makeURL(ICONPATH + PACICONFILE);
	}
	
	public final static URL getURLAC() {
		return makeURL(ICONPATH + ACICONFILE);
	}
	
	public final static URL getURLAtomic() {
		return makeURL(ICONPATH + ATOMICCONSTRICONFILE);
	}

	public final static URL getURLAttrCondition() {
		return makeURL(ICONPATH + ATTRCONDITIONICONFILE);
	}

	public final static URL getURLFormula() {
		return makeURL(ICONPATH + FORMULACONSTRICONFILE);
	}

	public final static URL getURLPost() {
		return makeURL(ICONPATH + POSTCONSTRICONFILE);
	}

	public final static URL getURLConstraint() {
		return makeURL(ICONPATH + CONSTRAINTICONFILE);
	}

	public final static URL getURLAtomConstr() {
		return makeURL(ICONPATH + ATOMCONSTRICONFILE);
	}

	/**
	 * The location for the rule icon which is displayed in the tree.
	 */
	public final static URL getURLRule() {
		return makeURL(ICONPATH + RULEICONFILE);
	}

	/**
	 * The location for the export rule icon which is displayed in the tree.
	 */
	public final static URL getURLRuleExport() {
		return makeURL(ICONPATH + RULEEXPORTICONFILE);
	}

	/**
	 * The location for the import rule icon which is displayed in the tree.
	 */
	public final static URL getURLRuleImport() {
		return makeURL(ICONPATH + RULEIMPORTICONFILE);
	}

	public final static URL getURLStop() {
		return makeURL(ICONPATH + STOPICONFILE);
	}

	/**
	 * The location for the overlaping graph icon which is displayed at the
	 * title bar.
	 */
	public final static URL getURLOverlapGraph() {
		return makeURL(ICONPATH + OVERLAPINGGRAPHICONFILE);
	}
	
	/**
	 * The location for the rule scheme icon which is displayed in the tree.
	 */
	public final static URL getURLRuleScheme() {
		return makeURL(ICONPATH + RULESCHEMEICONFILE);
	}

	/**
	 * The icon is returned which is specified. param url The location where to
	 * find the icon. Use the <code>getURL...</code> methods from this class.
	 */
	public final static ImageIcon getIconFromURL(URL url) {
		if (url != null)
			return new ImageIcon(url);
		
		return new ImageIcon();
	}

	public final static URL getURLVersionID() {
		return makeURL(ICONPATH + VERSIONID);
	}

	public final static URL getURLAGGLogo() {
		return makeURL(ICONPATH + AGGLOGO);
	}

	public final static URL getURLAGGIcon() {
		return makeURL(ICONPATH + AGGICON);
	}

	public final static URL getOkIcon() {
		return makeURL(ICONPATH + OK);
	}

	public final static URL getWrongIcon() {
		return makeURL(ICONPATH + WRONG);
	}

	public final static URL getOptionIcon() {
		return makeURL(ICONPATH + OPTION);
	}

	public final static URL getStopIcon() {
		return makeURL(ICONPATH + SMALL_STOP);
	}

	public final static URL getWorkingIcon() {
		return makeURL(ICONPATH + CONSTRUCTION);
	}

	public final static URL getWorkerIcon() {
		return makeURL(ICONPATH + WORKER);
	}

}
/*
 * $Log: IconResource.java,v $
 * Revision 1.7  2010/06/09 11:07:13  olga
 * tuning
 *
 * Revision 1.6  2010/03/08 15:40:22  olga
 * code optimizing
 *
 * Revision 1.5  2009/05/12 10:37:01  olga
 * CPA: bug fixed
 * Applicability of Rule Seq. : bug fixed
 *
 * Revision 1.4  2008/09/04 07:49:24  olga
 * GUI extension: hide nodes, edges
 *
 * Revision 1.3  2007/09/10 13:05:21  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/06/13 08:32:50 olga Update:
 * V161
 * 
 * Revision 1.1 2005/08/25 11:56:53 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:19 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:09 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:10 olga Imported sources
 * 
 * Revision 1.5 2001/07/04 10:41:48 olga GUI, neues Icon
 * 
 * Revision 1.4 2001/03/15 17:02:54 olga Icons korrektur.
 * 
 * Revision 1.3 2001/03/08 11:00:07 olga Das ist Stand nach der AGG GUI
 * Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.2 2000/12/07 14:23:37 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.1 2000/06/28 08:05:00 shultzke cover die resourcen der Icons
 * 
 * Revision 1.2 2000/06/26 09:11:14 shultzke classFiles geloescht
 * 
 * Revision 1.1.1.1 2000/06/26 08:57:33 shultzke Anfang der
 * ParserDiplomEntwicklung
 * 
 */

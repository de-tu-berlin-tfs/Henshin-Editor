package agg.parser;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;

//import com.objectspace.jgl.HashSet;

/**
 * Extends the layer function for a NAC check.
 * @deprecated
 * @author $Author: olga $
 * @version $Id: WeakLayerFunction.java,v 1.5 2010/09/23 08:25:00 olga Exp $
 */
public class WeakLayerFunction extends LayerFunction {

	/**
	 * Creates a new layer function.
	 * 
	 * @param gragra
	 *            The graph grammar.
	 */
	public WeakLayerFunction(GraGra gragra) {
		super(gragra);
		// System.out.println("WeakLayerFunction created");
	}

	/**
	 * Checks the layer function.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean checkLayer() {
		// System.out.println("WeakLayerFunction checkLayer");
		Report.trace("starte ckeckLayer()", 2);
		boolean result = true;
		/* 0<= cl(l)<=dl(l)<=n */
		for (Enumeration<?> en = getDeletionLayer().keys(); en.hasMoreElements()
				&& result;) {
			Object key = en.nextElement();
			Integer dl = getDeletionLayer().get(key);
			Integer cl = getCreationLayer().get(key);
			/* layerfunktion muss total sein */
			if (cl == null || dl == null) {
				result = false;
				this.errMsg = "Type name :  " + ((Type) key).getStringRepr() + "\n"
						+ " The condition that\n "
						+ " cl, dl are total functions \n "
						+ " is not satisfied.";
				break;
			}
			if (!(0 <= cl.intValue()) || !(cl.intValue() <= dl.intValue())) {
				result = false;
				this.errMsg = "Type name :  " + ((Type) key).getStringRepr() + "\n"
						+ " Condition \n " + " 0 <= cl(l) <= dl(l) <= n \n "
						+ " is not satisfied.";
			}
		}

		HashSet<Object> deletionSet = new HashSet<Object>();
		HashSet<Object> creationSet = new HashSet<Object>();
		Enumeration<Rule> rules = getRuleLayer().keys();
		while (result && rules.hasMoreElements()) {
			deletionSet.clear();
			creationSet.clear();
			Rule rule = rules.nextElement();
			Integer layerRule = getRuleLayer().get(rule);
			/*
			 * gibt es keinen Layer fuer eine Regel, so ist die Layerfunktion
			 * nicht korrekt
			 */
			if (layerRule == null) {
				result = false;
				this.errMsg = "Rule name :  " + rule.getName() + "\n"
						+ " The condition that\n"
						+ " rl  is a total function \n" + " is not satisfied.";
				break;
			}
			Graph leftGraph = rule.getLeft();
			Graph rightGraph = rule.getRight();
			/* alle geloeschten Objekte suchen */
			for (Enumeration<GraphObject> en = leftGraph.getElements(); en.hasMoreElements();) {
				GraphObject grob = en.nextElement();
				if (rule.getImage(grob) == null)
					deletionSet.add(grob);
			}
			Report.println("deletionSet ist " + deletionSet, Report.LAYER);

			/* dl(l) <= k */
			for (Iterator<?> en = deletionSet.iterator(); en.hasNext()
					&& result;) {
				GraphObject grob = (GraphObject) en.next();
				Type t = grob.getType();
				Integer dl = getDeletionLayer().get(t);
				Report.println("dl(" + t + ") = " + dl + "  <=  rl(" + rule
						+ ") = " + layerRule, Report.LAYER);
				if (dl.intValue() > layerRule.intValue()) {
					result = false;
					this.errMsg = "Rule name :  " + rule.getName() + "\n"
							+ "Type name :  " + t.getStringRepr() + "\n"
							+ " The condition that \n"
							+ " r  deletes only nodes and edges with labels \n"
							+ " l  such that  dl(l) <= rl(r) \n"
							+ " is not satisfied.";
				}
			}
			if (!result)
				break;
			/* alle erzeugten Objekte suchen */
			for (Enumeration<GraphObject> en = rightGraph.getElements(); en
					.hasMoreElements();)
				creationSet.add(en.nextElement());
			Report.println("creationSet ist " + creationSet, Report.LAYER);
			for (Enumeration<GraphObject> en = leftGraph.getElements(); en.hasMoreElements();) {
				try {
					creationSet.remove(rule.getImage(en.nextElement()));
				} catch (NullPointerException npe) {
				}
			}
			Report.println("creationSet reduziert auf " + creationSet,
					Report.LAYER);

			/* cl > k */
			for (Iterator<?> en = creationSet.iterator(); en.hasNext()
					&& result;) {
				GraphObject grob = (GraphObject) en.next();
				Type t = grob.getType();
				Integer cl = getCreationLayer().get(t);
				Report.println("cl(" + t + ") = " + cl + "  >  rl(" + rule
						+ ") = " + layerRule, Report.LAYER);
				if (cl.intValue() <= layerRule.intValue()) {
					result = false;
					this.errMsg = "Rule name :  " + rule.getName() + "\n"
							+ "Type name :  " + t.getStringRepr() + "\n"
							+ " The condition that\n "
							+ " r  creates only nodes and edges with labels \n"
							+ " l  such that  cl(l) > rl(r) \n"
							+ " is not satisfied.";
				}
			}
		}
		this.valid = result;
		Report.trace("beende checkLayer mit result = " + result, -2);
		return result;
	}

}
/*
 * $Log: WeakLayerFunction.java,v $
 * Revision 1.5  2010/09/23 08:25:00  olga
 * tuning
 *
 * Revision 1.4  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.3  2007/09/24 09:42:38  olga
 * AGG transformation engine tuning
 *
 * Revision 1.2  2007/09/10 13:05:40  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.3 2003/06/12 07:27:33 olga Testausgabe auskommentiert
 * 
 * Revision 1.2 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.1 2001/08/16 14:14:09 olga LayerFunction erweitert:
 * ExtendedLayerFunction erbt LayerFunction (checkLayer ueberschrieben)
 * WeakLayerFunction erbt LayerFunction ( checkLayer ueberschrieben)
 * WeakExtendedLayerFunction erbt WeakLayerFunction ( checkLayer ueberschrieben)
 * 
 * Revision 1.4 2001/08/08 14:46:30 olga Default Layer Option Einstellung ist
 * RCDN_LAYER.
 * 
 * Revision 1.3 2001/08/02 15:22:15 olga Error-Meldungen eingebaut in
 * LayerFunction und die Anzeige dieser Meldungen in GUI.
 * 
 * Revision 1.2 2001/03/08 10:44:51 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:53 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/12/26 10:00:04 shultzke Layered Parser hinzugefuegt
 * 
 */

package agg.parser;

import java.util.Enumeration;
import java.util.Iterator;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;

/**
 * Extends the layer function for a NAC check.
 * @deprecated
 * @author $Author: olga $
 * @version $Id: ExtendedLayerFunction.java,v 1.7 2010/09/23 08:25:00 olga Exp $
 */
public class ExtendedLayerFunction extends LayerFunction {

	/**
	 * Creates a new layer function.
	 * 
	 * @param gragra
	 *            The graph grammar.
	 */
	public ExtendedLayerFunction(GraGra gragra) {
		super(gragra);
		// System.out.println("ExtendedLayerFunction created");
	}

	/**
	 * Checks the layer function.
	 * 
	 * @return true if the layer functio is valid.
	 * 
	 * public boolean checkLayer(){ Report.trace("starte ckeckLayer()",2);
	 * boolean result = true; // 0<= cl(l)<=dl(l)<=n for(Enumeration enum =
	 * getDeletionLayer().keys(); enum.hasMoreElements() && result;){ Object key =
	 * enum.nextElement(); Integer dl = (Integer)getDeletionLayer().get(key);
	 * Integer cl = (Integer)getCreationLayer().get(key); // layerfunktion muss
	 * total sein if(cl == null || dl == null){ result = false; errMsg = "Type
	 * name : "+((Type) key).getStringRepr()+"\n"+ " The condition that\n "+ "
	 * cl, dl are total functions \n "+ " is not satisfied."; break; } if(!(0<=cl.intValue()) ||
	 * !(cl.intValue()<=dl.intValue())) { result = false; errMsg = "Type name :
	 * "+((Type) key).getStringRepr()+"\n"+ " Condition \n "+ " 0 <= cl(l) <=
	 * dl(l) <= n \n "+ " is not satisfied."; } }
	 * 
	 * HashSet deletionSet = new HashSet(); HashSet creationSet = new HashSet();
	 * Enumeration rules = getRuleLayer().keys(); while(result &&
	 * rules.hasMoreElements()){ deletionSet.clear(); creationSet.clear(); Rule
	 * rule = (Rule) rules.nextElement(); Integer layerRule = (Integer)
	 * getRuleLayer().get(rule); // gibt es keinen Layer fuer eine Regel, // so
	 * ist die Layerfunktion nicht korrekt if(layerRule == null){ result =
	 * false; errMsg = "Rule name : "+rule.getName()+"\n"+ " The condition
	 * that\n"+ " rl is a total function \n"+ " is not satisfied."; break; }
	 * Graph leftGraph = rule.getLeft(); Graph rightGraph = rule.getRight(); //
	 * alle geloeschten Objekte suchen for(Enumeration enum =
	 * leftGraph.getElements(); enum.hasMoreElements();){ GraphObject grob =
	 * (GraphObject) enum.nextElement(); if(rule.getImage(grob) == null)
	 * deletionSet.add(grob); } Report.println("deletionSet ist
	 * "+deletionSet,Report.LAYER); // mit und ohne dieser Bedingung (fuer
	 * 2.Funk auch) if(deletionSet.isEmpty()){ result = false; errMsg = "Rule
	 * name : "+rule.getName()+"\n"+ " The condition that\n "+ " r deletes at
	 * least one node or edge \n"+ " is not satisfied."; break; } // dl(l) <= k
	 * for(Enumeration enum = deletionSet.elements(); enum.hasMoreElements() &&
	 * result;){ GraphObject grob = (GraphObject) enum.nextElement(); Type t =
	 * grob.getType(); Integer dl = (Integer) getDeletionLayer().get(t);
	 * Report.println("dl("+t+") = "+dl+" <= rl("+rule+") =
	 * "+layerRule,Report.LAYER); if(dl.intValue()>layerRule.intValue()) {
	 * result = false; errMsg = "Rule name : "+rule.getName()+"\n"+ "Type name :
	 * "+t.getStringRepr()+"\n"+ " The condition that \n"+ " r deletes only
	 * nodes and edges with labels \n"+ " l such that dl(l) <= rl(r) \n"+ " is
	 * not satisfied."; } } if(!result) break; // alle erzeugten Objekte suchen
	 * for(Enumeration enum = rightGraph.getElements(); enum.hasMoreElements();)
	 * creationSet.add(enum.nextElement()); Report.println("creationSet ist
	 * "+creationSet,Report.LAYER); for(Enumeration enum =
	 * leftGraph.getElements(); enum.hasMoreElements();){ try{
	 * creationSet.remove(rule.getImage((GraphObject)enum.nextElement())); }
	 * catch (NullPointerException npe){} } Report.println("creationSet
	 * reduziert auf "+creationSet,Report.LAYER); // cl > k for(Enumeration enum =
	 * creationSet.elements(); enum.hasMoreElements() && result;){ GraphObject
	 * grob = (GraphObject) enum.nextElement(); Type t = grob.getType(); Integer
	 * cl = (Integer) getCreationLayer().get(t); Report.println("cl("+t+") =
	 * "+cl+" > rl("+rule+") = "+layerRule,Report.LAYER); if(cl.intValue()<=layerRule.intValue()) {
	 * result = false; errMsg = "Rule name : "+rule.getName()+"\n"+ "Type name :
	 * "+t.getStringRepr()+"\n"+ " The condition that\n "+ " r creates only
	 * nodes and edges with labels \n"+ " l such that cl(l) > rl(r) \n"+ " is
	 * not satisfied."; } } } // test: cl(l) <= rl(k) result = result &&
	 * checkLayerNAC();
	 * 
	 * valid = result; Report.trace("beende checkLayer mit result =
	 * "+result,-2);
	 * 
	 * 
	 * return result; }
	 * 
	 */

	/**
	 * Checks the layer function.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean checkLayer() {
		// System.out.println("ExtendedLayerFunction checkLayer");
		Report.trace("starte extended ckeckLayer()", 2);
		boolean result = super.checkLayer();

		// boolean result = true;
		for (Iterator<Rule> rules = this.grammar.getListOfRules().iterator(); rules.hasNext()
				&& result;) {
			Rule r = rules.next();
			for (Enumeration<OrdinaryMorphism> nacs = r.getNACs(); nacs.hasMoreElements()
					&& result;) {
				OrdinaryMorphism nac = nacs.nextElement();
				Graph nacGraph = nac.getImage();
				for (Iterator<?> grobs = nacGraph.getNodesSet().iterator(); grobs
						.hasNext()
						&& result;) {
					GraphObject grob = (GraphObject) grobs.next();
					Type t = grob.getType();
					Integer rl = getRuleLayer().get(r);
					Integer cl = getCreationLayer().get(t);
					if (!(cl.intValue() <= rl.intValue())) {
						result = false;
						this.errMsg = "Rule name :  "
								+ r.getName()
								+ "\n"
								+ "Type name :  "
								+ t.getStringRepr()
								+ "\n"
								+ " The condition that\n"
								+ " r  uses only complex negative predicates \n"
								+ " over nodes and edges with labels \n"
								+ " l  such that  cl(l) <= rl(r) \n"
								+ " is not satisfied.";
					}
				}
				for (Iterator<?> grobs = nacGraph.getArcsSet().iterator(); grobs
						.hasNext()
						&& result;) {
					GraphObject grob = (GraphObject) grobs.next();
					Type t = grob.getType();
					Integer rl = getRuleLayer().get(r);
					Integer cl = getCreationLayer().get(t);
					if (!(cl.intValue() <= rl.intValue())) {
						result = false;
						this.errMsg = "Rule name :  "
								+ r.getName()
								+ "\n"
								+ "Type name :  "
								+ t.getStringRepr()
								+ "\n"
								+ " The condition that\n"
								+ " r  uses only complex negative predicates \n"
								+ " over nodes and edges with labels \n"
								+ " l  such that  cl(l) <= rl(r) \n"
								+ " is not satisfied.";
					}
				}
			}
		}
		this.valid = result;
		Report.trace("beende extended checkLayer mit result = " + result, -2);
		return result;
	}

}
/*
 * $Log: ExtendedLayerFunction.java,v $
 * Revision 1.7  2010/09/23 08:25:00  olga
 * tuning
 *
 * Revision 1.6  2010/03/04 14:11:14  olga
 * code optimizing
 *
 * Revision 1.5  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.4  2007/11/01 09:58:18  olga
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
 * Revision 1.1 2005/08/25 11:56:57 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2003/06/12 07:27:33 olga Testausgabe auskommentiert
 * 
 * Revision 1.2 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.7 2001/08/16 15:44:08 olga cl(l) > rl(k) gilt!
 * 
 * Revision 1.6 2001/08/16 15:06:34 olga LayerFunction: di Bedingung cl(l) >
 * rl(k) gilt generell (auch mit NACs pruefung).
 * 
 * Revision 1.5 2001/08/16 14:14:08 olga LayerFunction erweitert:
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

package agg.parser;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.GraGra;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;


/**
 * This layer function layers a set of rules of a given graph grammar.
 * 
 * @deprecated
 * @author $Author: olga $
 * @version $Id: LayerFunction.java,v 1.9 2010/09/23 08:25:00 olga Exp $
 */
public class LayerFunction implements XMLObject {

	/** The graph grammar. */
	protected GraGra grammar;

	protected Hashtable<Rule, Integer> ruleLayer;

	protected Hashtable<Type, Integer> creationLayer;

	protected Hashtable<Type, Integer> deletionLayer;

	/** The error message if this layer function is not valid. */
	protected String errMsg;

	/** true if this layer function is valid. */
	protected boolean valid;

	protected String option;

	/**
	 * Creates a new layer function for a given graph grammar. Initially this
	 * layer function is invalid.
	 * 
	 * @param gragra
	 *            The graph grammar.
	 */
	public LayerFunction(GraGra gragra) {
		this.grammar = gragra;
		initRuleLayer(this.grammar);
		initCreationLayer(this.grammar);
		initDeletionLayer(this.grammar);
		this.valid = true; // false;
		this.errMsg = "";
		// System.out.println("LayerFunction created");
	}

	private void initRuleLayer(GraGra gragra) {
		this.ruleLayer = new Hashtable<Rule, Integer>();
		for (int i = 0; i < gragra.getListOfRules().size(); i++) {
			Rule rule = gragra.getListOfRules().get(i);
			this.ruleLayer.put(rule, new Integer(rule.getLayer()));
		}
	}

	private void initCreationLayer(GraGra gragra) {
		this.creationLayer = new Hashtable<Type, Integer>();
		Enumeration<Type> types = gragra.getTypes();
		while (types.hasMoreElements()) {
			Type type = types.nextElement();
			this.creationLayer.put(type, new Integer(0));
		}
	}

	private void initDeletionLayer(GraGra gragra) {
		this.deletionLayer = new Hashtable<Type, Integer>();
		Enumeration<Type> types = gragra.getTypes();
		while (types.hasMoreElements()) {
			Type type = types.nextElement();
			this.deletionLayer.put(type, new Integer(0));
		}
	}

	/**
	 * Checks the layer function.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean checkLayer() {
		return true;
	}

	/*
	private boolean checkLayer_OLD() {
		// System.out.println("LayerFunction checkLayer");
		Report.trace("starte ckeckLayer()", 2);
		boolean result = true;
		// 0<= cl(l)<=dl(l)<=n 
		for (Enumeration<?> en = getDeletionLayer().keys(); en.hasMoreElements()
				&& result;) {
			Object key = en.nextElement();
			Integer dl = getDeletionLayer().get(key);
			Integer cl = getCreationLayer().get(key);
			// layerfunktion muss total sein 
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

		HashSet deletionSet = new HashSet();
		HashSet creationSet = new HashSet();
		Enumeration<?> rules = getRuleLayer().keys();
		while (result && rules.hasMoreElements()) {
			deletionSet.clear();
			creationSet.clear();
			Rule rule = (Rule) rules.nextElement();
			Integer layerRule = getRuleLayer().get(rule);
			
//			  gibt es keinen Layer fuer eine Regel, so ist die Layerfunktion
//			  nicht korrekt
			 
			if (layerRule == null) {
				result = false;
				this.errMsg = "Rule name :  " + rule.getName() + "\n"
						+ " The condition that\n"
						+ " rl  is a total function \n" + " is not satisfied.";
				break;
			}
			Graph leftGraph = rule.getLeft();
			Graph rightGraph = rule.getRight();
			// alle geloeschten Objekte suchen 
			for (Iterator<?> en = leftGraph.getNodesSet().iterator(); en.hasNext();) {
				GraphObject grob = (GraphObject) en.next();
				if (rule.getImage(grob) == null)
					deletionSet.add(grob);
			}
			for (Iterator<?> en = leftGraph.getArcsSet().iterator(); en.hasNext();) {
				GraphObject grob = (GraphObject) en.next();
				if (rule.getImage(grob) == null)
					deletionSet.add(grob);
			}
			Report.println("deletionSet ist " + deletionSet, Report.LAYER);
			// mit und ohne dieser Bedingung (fuer 2.Funk auch)
			if (deletionSet.isEmpty()) {
				result = false;
				this.errMsg = "Rule name :  " + rule.getName() + "\n"
						+ " The condition that\n "
						+ " r  deletes at least one node or edge \n"
						+ " is not satisfied.";
				break;
			}
			// dl(l) <= k 
			for (Enumeration<?> en = deletionSet.elements(); en.hasMoreElements()
					&& result;) {
				GraphObject grob = (GraphObject) en.nextElement();
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
			// alle erzeugten Objekte suchen 
			for (Iterator<Node> en = leftGraph.getNodesSet().iterator(); en.hasNext();) {
				creationSet.add(en.next());
			}
			for (Iterator<Arc> en = leftGraph.getArcsSet().iterator(); en.hasNext();) {
				creationSet.add(en.next());
			}
			Report.println("creationSet ist " + creationSet, Report.LAYER);
			for (Iterator<Node> en = leftGraph.getNodesSet().iterator(); en.hasNext();) {
				try {
					creationSet.remove(rule.getImage(en.next()));
				} catch (NullPointerException npe) {
				}
			}
			for (Iterator<Arc> en = leftGraph.getArcsSet().iterator(); en.hasNext();) {
				try {
					creationSet.remove(rule.getImage(en.next()));
				} catch (NullPointerException npe) {
				}
			}
			Report.println("creationSet reduziert auf " + creationSet,
					Report.LAYER);

			// cl > k 
			for (Enumeration<?> en = creationSet.elements(); en.hasMoreElements()
					&& result;) {
				GraphObject grob = (GraphObject) en.nextElement();
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
*/

	/**
	 * A fast check on validity.
	 * 
	 * @return true if the layer function is valid.
	 */
	public boolean isValid() {
		return true; // this.valid;
	}

	/**
	 * Returns an error message if the layer function is not valid.
	 * 
	 * @return The error message.
	 */
	public String getErrorMessage() {
		return this.errMsg;
	}

	/**
	 * Returns the rule layer of the layer function.
	 * 
	 * @return The rule layer.
	 */
	public Hashtable<Rule, Integer> getRuleLayer() {
		int size = 0;
		Iterator<Rule> en = this.grammar.getListOfRules().iterator();
		while (en.hasNext()) {
			en.next();
			size++;
		}

		if (size != this.ruleLayer.size()) {
			initRuleLayer(this.grammar);
			return this.ruleLayer;
		}

		en = this.grammar.getListOfRules().iterator();
		while (en.hasNext()) {
			Object key = en.next();
			if (!this.ruleLayer.containsKey(key)) {
				initRuleLayer(this.grammar);
				return this.ruleLayer;
			}
		}

		return this.ruleLayer;
	}

	/**
	 * Returns the creation layer of the layer function.
	 * 
	 * @return The creation layer.
	 */
	public Hashtable<Type, Integer> getCreationLayer() {
		int size = 0;
		Enumeration<Type> en = this.grammar.getTypes();
		while (en.hasMoreElements()) {
			en.nextElement();
			size++;
		}
		if (size != this.creationLayer.size()) {
			initCreationLayer(this.grammar);
			return this.creationLayer;
		}

		en = this.grammar.getTypes();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (!this.creationLayer.containsKey(key)) {
				initCreationLayer(this.grammar);
				return this.creationLayer;
			}
		}
		return this.creationLayer;
	}

	/**
	 * Returns the deletion layer of the layer function.
	 * 
	 * @return The deletion layer.
	 */
	public Hashtable<Type, Integer> getDeletionLayer() {
		int size = 0;
		Enumeration<Type> en = this.grammar.getTypes();
		while (en.hasMoreElements()) {
			en.nextElement();
			size++;
		}

		if (size != this.deletionLayer.size()) {
			initDeletionLayer(this.grammar);
			return this.deletionLayer;
		}

		en = this.grammar.getTypes();
		while (en.hasMoreElements()) {
			Object key = en.nextElement();
			if (!this.deletionLayer.containsKey(key)) {
				initDeletionLayer(this.grammar);
				return this.deletionLayer;
			}
		}
		return this.deletionLayer;
	}

	/**
	 * Returns the smallest layer of the rule layer.
	 * 
	 * @return The smallest layer.
	 */
	public Integer getStartLayer() {
		int startLayer = Integer.MAX_VALUE;
		Integer result = null;
		/* RuleLayer sind fuer das Parsieren noetig */
		for (Enumeration<?> keys = getRuleLayer().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer layer = getRuleLayer().get(key);
			if (layer.intValue() < startLayer) {
				startLayer = layer.intValue();
				result = layer;
			}
		}
		return result;
	}

	/**
	 * Inverts a layer function so that the layer is the key and the value is a
	 * set.
	 * 
	 * @param layer
	 *            The layer function will be inverted.
	 * @return The inverted layer function.
	 */
	@SuppressWarnings("unchecked")
	public Hashtable<Integer, HashSet> invertLayer(Hashtable<?,?> layer) {
		Hashtable<Integer, HashSet> inverted = new Hashtable<Integer, HashSet>();
		for (Enumeration<?> keys = layer.keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Integer value = (Integer) layer.get(key);
			HashSet invertedValue = inverted.get(value);
			if (invertedValue == null) {
				invertedValue = new HashSet();
				invertedValue.add(key);
				inverted.put(value, invertedValue);
			} else {
				invertedValue.add(key);
			}
		}
		return inverted;
	}

	/**
	 * Returns layer option. The layer option will be initialized during loading
	 * of critical pairs file. This option can be used in the method
	 * setLayer(String l) of the LayerOption class.
	 */
	public String getOption() {
		return this.option;
	}

	/**
	 * Writes a hash table to a xml file.
	 * 
	 * @param xmlObjects
	 *            <CODE>this</CODE>
	 * @param h
	 *            A helper object.
	 */
	protected void writeHashtableToXML(Hashtable<?,?> xmlObjects, XMLHelper h) {
		for (Enumeration<?> keys = xmlObjects.keys(); keys.hasMoreElements();) {
			XMLObject r1 = (XMLObject) keys.nextElement();
			h.openSubTag("Datum");
			h.addObject("key", r1, false);
			h.addAttr("value", "" + xmlObjects.get(r1));
			h.close();
		}
	}

	/**
	 * Writes the layer function to a file in a xml format.
	 * 
	 * @param h
	 *            A helper object for storing.
	 */
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("LayerFunction", this);
		// h.addAttr("valid",""+isValid());
		/*
		 * if(this instanceof ExtendedLayerFunction)
		 * h.addAttr("option","RCDN_LAYER"); else if(this instanceof
		 * WeakExtendedLayerFunction) h.addAttr("option","WEAK_RCDN_LAYER");
		 * else if(this instanceof WeakLayerFunction)
		 * h.addAttr("option","WEAK_RCD_LAYER"); else if(this instanceof
		 * LayerFunction) h.addAttr("option","RCD_LAYER");
		 */
		h.addObject("GraGra", this.grammar, false);

		/*
		 * if(isValid()){ h.openSubTag("ruleLayer");
		 * writeHashtableToXML(getRuleLayer(),h); h.close();
		 * h.openSubTag("deletionLayer");
		 * writeHashtableToXML(getDeletionLayer(),h); h.close();
		 * h.openSubTag("creationLayer");
		 * writeHashtableToXML(getCreationLayer(),h); h.close(); }
		 */
		h.close();
	}

	/**
	 * Reads the contents of a xml file.
	 * 
	 * @param h
	 *            A helper object.
	 */
	public void XreadObject(XMLHelper h) {
		if (h.isTag("LayerFunction", this)) {
			// System.out.println("LayerFunction: wird gelesen");
			this.grammar = (GraGra) h.getObject("GraGra", null, false);
			// System.out.println("LayerFunction: CP this.grammar gelesen:
			// "+this.grammar);
			// String v = h.readAttr("valid");
			// System.out.println("LayerFunction: valid gelesen");

			// option = h.readAttr("option");
			// System.out.println("LayerFunction: option gelesen: "+option);

			this.valid = true; // false;
			this.option = "";
			initRuleLayer(this.grammar);
			initCreationLayer(this.grammar);
			initDeletionLayer(this.grammar);

			/*
			 * if(v.equals("true")){ this.valid = true; if
			 * (h.readSubTag("ruleLayer")) { if(ruleLayer == null) ruleLayer =
			 * new Hashtable(); Enumeration data = h.getEnumeration("", null,
			 * true, "Datum"); while(data.hasMoreElements()){
			 * h.peekElement(data.nextElement()); Rule r2 =
			 * (Rule)h.getObject("key",null,false); Integer i = new
			 * Integer(h.readIAttr("value")); if((r2 != null) && (i != null)) {
			 * getRuleLayer().put(r2,i); r2.setLayer(i.intValue()); } h.close(); }
			 * h.close(); //System.out.println("LayerFunction: ruleLayer
			 * gelesen"); } if (h.readSubTag("deletionLayer")){ if(deletionLayer ==
			 * null) deletionLayer = new Hashtable(); Enumeration data =
			 * h.getEnumeration("", null, true, "Datum");
			 * while(data.hasMoreElements()){ h.peekElement(data.nextElement());
			 * Type t = (Type)h.getObject("key",null,false); Integer i = new
			 * Integer(h.readIAttr("value")); if((t != null) && (i != null))
			 * getDeletionLayer().put(t,i); h.close(); } h.close();
			 * //System.out.println("LayerFunction: deletionLayer gelesen"); }
			 * if (h.readSubTag("creationLayer")){ if(creationLayer == null)
			 * creationLayer = new Hashtable(); Enumeration data =
			 * h.getEnumeration("", null, true, "Datum");
			 * while(data.hasMoreElements()){ h.peekElement(data.nextElement());
			 * Type t = (Type)h.getObject("key",null,false); Integer i = new
			 * Integer(h.readIAttr("value")); if((t != null) && (i != null))
			 * getCreationLayer().put(t,i); h.close(); } h.close();
			 * //System.out.println("LayerFunction: creationLayer gelesen"); } }
			 */
			h.close();
			;
		} else
			this.option = "";
		// System.out.println("LayerFunction: gelesen");
	}

	/**
	 * Returns the layer function in a human readable way.
	 * 
	 * @return The text.
	 */
	public String toString() {
		String resultString = super.toString() + " LayerFunction:\n";
		resultString += "\tRuleLayer:\n";
		resultString += getRuleLayer().toString() + "\n";
		resultString += "\tCreationLayer:\n";
		resultString += getCreationLayer().toString() + "\n";
		resultString += "\tDeletionLayer:\n";
		resultString += getDeletionLayer().toString() + "\n";
		return resultString;
	}
}
/*
 * $Log: LayerFunction.java,v $
 * Revision 1.9  2010/09/23 08:25:00  olga
 * tuning
 *
 * Revision 1.8  2010/03/08 15:46:42  olga
 * code optimizing
 *
 * Revision 1.7  2010/03/04 14:11:42  olga
 * code optimizing
 *
 * Revision 1.6  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.5  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/10 13:05:40  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.3 2006/03/01 09:55:46 olga - new CPA
 * algorithm, new CPA GUI
 * 
 * Revision 1.2 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.10 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.9 2004/12/20 14:53:48 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.8 2004/10/27 13:09:15 olga Anpassung von LayerFunction von
 * LayerFunction
 * 
 * Revision 1.7 2004/10/25 14:24:38 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.6 2004/01/28 17:58:38 olga Errors suche
 * 
 * Revision 1.5 2004/01/22 17:51:18 olga tests
 * 
 * Revision 1.4 2003/06/12 07:27:33 olga Testausgabe auskommentiert
 * 
 * Revision 1.3 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/19 16:20:15 olga Nur Testausgaben weg.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:23 olga Imported sources
 * 
 * Revision 1.6 2001/09/24 16:41:33 olga
 * 
 * Arbeit an LayerFunction und LayeredParser.
 * 
 * Revision 1.5 2001/08/16 14:14:08 olga LayerFunction erweitert:
 * ExtendedLayerFunction erbt LayerFunction (checkLayer ueberschrieben)
 * WeakLayerFunction erbt LayerFunction ( checkLayer ueberschrieben)
 * WeakExtendedLayerFunction erbt WeakLayerFunction ( checkLayer ueberschrieben)
 * 
 * Revision 1.4 2001/08/02 15:22:16 olga Error-Meldungen eingebaut in
 * LayerFunction und die Anzeige dieser Meldungen in GUI.
 * 
 * Revision 1.3 2001/07/19 15:20:09 olga Fehlermeldung bei Layer Function.
 * 
 * Revision 1.2 2001/03/08 10:44:52 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.6 2001/01/28 13:14:54 shultzke API fertig
 * 
 * Revision 1.1.2.5 2001/01/11 11:36:08 shultzke Laden und speichern der
 * kritischen Paare geht, es fehlt nur noch das Laden fuer den Parser.
 * 
 * Revision 1.1.2.4 2000/12/26 10:00:04 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.1.2.3 2000/12/18 13:33:39 shultzke Optionen veraendert
 * 
 * Revision 1.1.2.2 2000/12/10 20:26:08 shultzke Parser kann XML
 * 
 * Revision 1.1.2.1 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */

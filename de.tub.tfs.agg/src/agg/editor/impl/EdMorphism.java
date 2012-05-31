package agg.editor.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;

import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.util.Pair;


/**
 * @version $Id: EdMorphism.java,v 1.9 2010/09/20 14:28:38 olga Exp $
 * @author $Author: olga $
 */
public class EdMorphism {

	HashMap<GraphObject, Integer> source;
	
	HashMap<GraphObject, Integer> target1, target2;

	public EdMorphism(OrdinaryMorphism o) {
		this.source = new HashMap<GraphObject, Integer>();
		this.target1 = new HashMap<GraphObject, Integer>();
		this.target2 = new HashMap<GraphObject, Integer>();
		if (o != null)
			makeSourceTarget(o);
	}

	private void makeSourceTarget(OrdinaryMorphism o) {
		Enumeration<GraphObject> graphObjects = o.getDomain();
		int i = 1;
		while (graphObjects.hasMoreElements()) {
			GraphObject go = graphObjects.nextElement();
			this.source.put(go, new Integer(i));
			this.target1.put(o.getImage(go), new Integer(i));
			i++;
		}
	}

	public void makeVDiagram(Rule r1, Rule r2, OrdinaryMorphism o1,
			OrdinaryMorphism o2, Pair<OrdinaryMorphism, OrdinaryMorphism> morphsN2) {
		
		int i = 0;
		Enumeration<GraphObject> graphObjects = o1.getCodomain();
		while (graphObjects.hasMoreElements()) {
			GraphObject go = graphObjects.nextElement();
			i++;
			this.source.put(go, new Integer(i));
			Enumeration<GraphObject> inverse = o1.getInverseImage(go);
			while (inverse.hasMoreElements()) {
				GraphObject inv1 = inverse.nextElement();
				if (o1.getSource() == r1.getLeft()) {
					this.target1.put(inv1, new Integer(i));					
				} else if (o1.getSource() == r1.getRight()) {
					GraphObject inv2 = null;
					Enumeration<GraphObject> inverse1 = r1.getInverseImage(inv1);
					while (inverse1.hasMoreElements()) {
						inv2 = inverse1.nextElement();
						this.target1.put(inv2, new Integer(i));
					}
					this.target1.put(inv1, new Integer(i));
				}
			}
		}
		graphObjects = o2.getCodomain();
		while (graphObjects.hasMoreElements()) {
			GraphObject go = graphObjects.nextElement();
			if (this.source.get(go) == null) {
				i++;
				this.source.put(go, new Integer(i));
				if (o2.getSource() == r2.getLeft()) {
					Enumeration<GraphObject> inverse = o2.getInverseImage(go);
					while (inverse.hasMoreElements()) {
						GraphObject inv = inverse.nextElement();
						this.target2.put(inv, new Integer(i));
					}
				} else if (o2.getSource() == r2.getRight()) {
					Enumeration<GraphObject> inverse = o2.getInverseImage(go);
					while (inverse.hasMoreElements()) {
						GraphObject inv = inverse.nextElement();
						this.target2.put(inv, new Integer(i));
						
						Enumeration<GraphObject> inverse1 = r2.getInverseImage(inv);
						while (inverse1.hasMoreElements()) {
							GraphObject inv2 = inverse1.nextElement();
							this.target2.put(inv2, this.target2.get(inv));
						}
					}
				}				
			} else {
				Integer number = this.source.get(go);
				if (o2.getSource() == r2.getLeft()) {
					Enumeration<GraphObject> inverse = o2.getInverseImage(go);
					while (inverse.hasMoreElements()) {
						GraphObject inv = inverse.nextElement();
						this.target2.put(inv, new Integer(number.intValue()));
					}
				} else if (o2.getSource() == r2.getRight()) {
					Enumeration<GraphObject> inverse = o2.getInverseImage(go);
					while (inverse.hasMoreElements()) {
						GraphObject inv = inverse.nextElement();
						this.target2.put(inv, new Integer(number.intValue()));
						
						Enumeration<GraphObject> inverse1 = r2.getInverseImage(inv);
						while (inverse1.hasMoreElements()) {
							GraphObject inv2 = inverse1.nextElement();
							this.target2.put(inv2, this.target2.get(inv));
						}
					}
				}
			}
		}
		
		if (morphsN2 != null) {
//			OrdinaryMorphism nac = r2.getNAC(o2.getSource().getHelpInfo());
			OrdinaryMorphism morphL2N2 = morphsN2.first;
			OrdinaryMorphism morphNac2N2 = morphsN2.second;
			// where: morphL2N2.getSource() == r2.getLeft()
			// morphNac2N2.getSource() == nac.getTarget()
			graphObjects = o2.getCodomain();
			while (graphObjects.hasMoreElements()) {
				GraphObject go = graphObjects.nextElement();
				Integer number = this.source.get(go);
				Enumeration<GraphObject> inverse = o2.getInverseImage(go);
				if (inverse.hasMoreElements()) {
					GraphObject go2 = inverse.nextElement();
					Enumeration<GraphObject> inverse2 = morphL2N2.getInverseImage(go2);
					if (inverse2.hasMoreElements()) {
						GraphObject inv = inverse2.nextElement();
						this.target2.put(inv, new Integer(number.intValue()));
					} else {
						inverse2 = morphNac2N2.getInverseImage(go2);
						if (inverse2.hasMoreElements()) {
							GraphObject inv = inverse2.nextElement();
							this.target2.put(inv, new Integer(number.intValue()));
						}
					}
				}
			}
		}
	}

	public void makeVDiagram(OrdinaryMorphism o1, OrdinaryMorphism o2) {
		int i = 1;
		Enumeration<GraphObject> graphObjects = o1.getCodomain();
		while (graphObjects.hasMoreElements()) {
			GraphObject go = graphObjects.nextElement();
			this.source.put(go, new Integer(i));
			Enumeration<GraphObject> inverse = o1.getInverseImage(go);
			while (inverse.hasMoreElements()) {
				GraphObject inv = inverse.nextElement();
				this.target1.put(inv, new Integer(i));
			}
			i++;
		}
		graphObjects = o2.getCodomain();
		while (graphObjects.hasMoreElements()) {
			GraphObject go = graphObjects.nextElement();
			if (this.source.get(go) == null) {
				this.source.put(go, new Integer(i));
				Enumeration<GraphObject> inverse = o2.getInverseImage(go);
				while (inverse.hasMoreElements()) {
					GraphObject inv = inverse.nextElement();
					this.target2.put(inv, new Integer(i));
				}
				i++;
			} else {
				Integer number = this.source.get(go);
				Enumeration<GraphObject> inverse = o2.getInverseImage(go);
				while (inverse.hasMoreElements()) {
					GraphObject inv = inverse.nextElement();
					this.target2.put(inv, new Integer(number.intValue()));
				}
			}
		}
	}

	private HashMap<GraphObject, String> convertToStringHashMap(HashMap<GraphObject, Integer> h) {
		HashMap<GraphObject, String> result = new HashMap<GraphObject, String>();
		Iterator<GraphObject> iter = h.keySet().iterator();
		while(iter.hasNext()) {
			GraphObject key = iter.next();		
			result.put(key, h.get(key).toString());
		}
		return result;
	}

	public HashMap<?,?> getSourceOfMorphism() {
		return convertToStringHashMap(this.source);
	}

	public HashMap<?,?> getTargetOfMorphism() {
		return convertToStringHashMap(this.target1);
	}

	public HashMap<?,?> getTargetOfMorphism(int i) {
		if (i == 1)
			return convertToStringHashMap(this.target1);
		else if (i == 2)
			return convertToStringHashMap(this.target2);
		else
			return convertToStringHashMap(this.target1);
	}

}
/*
 * $Log: EdMorphism.java,v $
 * Revision 1.9  2010/09/20 14:28:38  olga
 * tuning
 *
 * Revision 1.8  2008/02/18 09:37:11  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.7  2007/11/05 09:18:16  olga
 * code tuning
 *
 * Revision 1.6  2007/11/01 09:58:11  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.5  2007/09/10 13:05:16  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.4 2006/11/01 11:17:29 olga Optimized agg
 * sources of CSP algorithm, match usability, graph isomorphic copy, node/edge
 * type multiplicity check for injective rule and match
 * 
 * Revision 1.3 2006/03/02 12:03:23 olga CPA: check host graph - done
 * 
 * Revision 1.2 2006/03/01 09:55:46 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.1 2005/08/25 11:56:56 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:02 olga Version with Eclipse
 * 
 * Revision 1.5 2003/03/05 18:24:25 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/03/05 14:54:14 olga GUI: Morphism anzeigen
 * 
 * Revision 1.3 2003/03/03 17:47:34 olga GUI
 * 
 * Revision 1.2 2003/02/24 17:50:30 olga Morphism-anzeige bei CP getestet
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:08 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:53:20 olga Das ist Stand nach der AGG GUI
 * Reimplementierung.
 * 
 * Revision 1.1.2.2 2000/07/17 16:12:34 shultzke exlude berechnung verschluckt
 * stdout und rechnet nicht richtig
 * 
 * Revision 1.1.2.1 2000/07/12 14:33:26 shultzke Morphismen koennen jetzt besser
 * gemalt werden
 * 
 */

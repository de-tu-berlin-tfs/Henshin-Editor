/**
 * 
 */
package agg.layout.evolutionary;

import java.awt.Dimension;
import java.awt.Point;
import java.util.List;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;
import agg.editor.impl.EdArc;
import agg.xt_basis.Arc;
import agg.xt_basis.Type;

/**
 * @author Dennis
 * 
 */
public class EvolutionaryGraphLayout {

	private boolean jpgOutput;

	private boolean enabled;

	private Dimension panel;

	private boolean panelChangable;

	private final LayoutMetrics lmetric;

	private EdGraph oldedgraph;

	private int gnrlEdgeLngth;

	private int temperature;

	private boolean usePattern;

	private boolean frozenPos;

	private boolean centre;

	private boolean writeMetrics;

	private int iters, nodeIntersctnIters,
			edgeIntersctnIters;

	private int overlapscnt;

	private final Hashtable<Type, Vector<LayoutPattern>> layoutPatterns;

	private final Hashtable<LayoutNode, Type> layoutNode2Type;

//	private boolean freezingOldNode, freezingOldEdge;

	private final Random random = new Random();

	/**
	 * konstruktor fuer den Layouter 
	 * 
	 * @param temp
	 *            temp gibt eine begrenzug fuer die bewegung einer node in einem
	 *            iterationsschritt an, wird hier als temperatur bezeichnet....
	 * 
	 */
	public EvolutionaryGraphLayout(final int temp, final Dimension panel) {
		this.temperature = temp;
		this.panel = panel;
		this.panelChangable = true;
		this.lmetric = new LayoutMetrics();
		this.oldedgraph = null;
		this.jpgOutput = true;
		this.enabled = false;
		this.usePattern = false;
		this.frozenPos = false;
		this.centre = false;
		this.writeMetrics = true;
		this.iters = 100;
		this.nodeIntersctnIters = 1;
		this.edgeIntersctnIters = 50;
		this.overlapscnt = 0;
		this.gnrlEdgeLngth = 200;
		this.layoutPatterns = new Hashtable<Type, Vector<LayoutPattern>>();
		this.layoutNode2Type = new Hashtable<LayoutNode, Type>();
	}

	public void setEnabled(final boolean b) {
		this.enabled = b;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * gibt die anziehungskraefte zwischen nodes wieder momentan wird die
	 * eigenschaft der kante zwischen den nodes nicht beruecksichtigt. TODO
	 * Kanteneigenschaften beruecksichtigen
	 * 
	 * @param d
	 *            distanz zwischen 2 nodes
	 * @param z
	 *            Zone um die nodes in der keine andere node liegen soll
	 * @return
	 */
	// wird nicht mehr benutzt.
	private int getAttrForce(final int d, final int z) {
		return (d * d) / z;
	}

	/**
	 * gibt die abstossenden Kraefte zwischen 2 nodes wieder diese sind
	 * unabhaengig davon, ob die nodes durch eine kante(arc) verbunden sind,
	 * oder nicht.
	 * 
	 * @param d
	 *            distanz zwischen 2 nodes
	 * @param z
	 *            zone um die nodes in der keine andere node liegen soll
	 * @return
	 */
	private int getRepulseForce(final int d, final int z) {
		return (z * z) / Math.abs(d);
	}

	private int cool(final int temp, final int it) {
		// hiermit wird temp reduziert, und zwar in jedem iterationsschritt
		// etwas weniger als im vorherigen
		// angefangen mit it+2, weil it ja im ersten schritt 0 ist, so wird
		// also t im ersten schritt halbiert, dann der rest um ein drittel
		// reduziert, dann um ein viertel........

		return temp - (temp / (it + 2));
	}

	/**
	 * reduziert die bewegungsfreiheit fuer einen knoten entsprechend seinem
	 * Alter
	 * 
	 * @param temp
	 *            Ausgangstemperatur / Bewegungsfreiheit
	 * @param age
	 *            Alter des betrachteten Knotens
	 * @param gage
	 *            Alter des Gesamtgraphen
	 * @return reduzierte Temperatur / Bewegungsfreiheit
	 */
	private int reduceTempByAge(final int temp, final int age, final int gage) {
		int ret;
		if (gage == 0) {
			ret = temp;
		} else {
			ret = temp - (temp / (gage - age + 1));
		}
		return ret;
	}

	public void createInheritancePattern(final Vector<Arc> inheritanceArcs) {
		for (int i = 0; i < inheritanceArcs.size(); i++) {
			final Arc inharc = inheritanceArcs.get(i);
//			String inhTypeName = inharc.getSource().getType().getName()
//					+ "-INHERITS-" + inharc.getTarget().getType().getName();
			final Vector<LayoutPattern> v = getLayoutPatternsForType(inharc.getType());
			if (!v.isEmpty()) {
				v.clear();
			}
			createLayoutPattern("ver_tree", "edge", inharc.getType(), 'y', -1);
			createLayoutPattern("edge_length", "edge", inharc.getType(), 150);
		}
	}

	private Vector<LayoutPattern> getInheritancePattern(final EdArc edge) {
		if (edge.getBasisArc().isInheritance()) {
//			String inhTypeName = edge.getBasisArc().getSource().getType()
//					.getName()
//					+ "-INHERITS-"
//					+ edge.getBasisArc().getTarget().getType().getName();
			return getLayoutPatternsForType(edge.getBasisArc().getType());
		}
		return null;
	}

	public void layout(final EdGraph egraph, 
						final List<EdNode> nodes,
						final List<EdArc> arcs) {
		layout(egraph, nodes, arcs, this.iters);
	}

	public void layout(final EdGraph egraph, 
						final List<EdNode> nodes,
						final List<EdArc> arcs,
						final int itrs) {	
		
		final Vector<LayoutNode> lnodes = getLayoutNodes(nodes);
		final Vector<LayoutArc> larcs = getLayoutArcs(arcs);
		int temp = this.temperature;		
		for (int i = 0; i < itrs; i++) {
			// System.out.println("iteration: "+i+" this.temperature: "+ temp);
			// abstossende kraefte:
			calcNodeRepulse(lnodes);
			// anziehungskraefte nur fuer verbundene nodes berechnen
			for (int j = 0; j < larcs.size(); j++) {
				final LayoutArc larc = larcs.get(j);
				if (!larc.getEdArc().isLine()) {
					continue;
				}
				final LayoutNode lnodev = ((EdNode) larc.getEdArc().getSource()).getLNode();
				final LayoutNode lnodeu = ((EdNode) larc.getEdArc().getTarget()).getLNode();
				final int deltax = lnodev.getAkt().x - lnodeu.getAkt().x;
				final int deltay = lnodev.getAkt().y - lnodeu.getAkt().y;
				// abfragen um division durch 0 zu vermeiden, wenn deltax oder
				// deltay 0 sind, aendert sich nix an der distanz
				if (deltax != 0) {
					lnodev.setDistX(lnodev.getDistX()
							- ((deltax / Math.abs(deltax)) * getAttrForce(
									deltax, lnodev.getZone())));
					lnodeu.setDistX(lnodeu.getDistX()
							+ ((deltax / Math.abs(deltax)) * getAttrForce(
									deltax, lnodeu.getZone())));
				}
				if (deltay != 0) {
					lnodev.setDistY(lnodev.getDistY()
							- ((deltay / Math.abs(deltay)) * getAttrForce(
									deltay, lnodev.getZone())));
					lnodeu.setDistY(lnodeu.getDistY()
							+ ((deltay / Math.abs(deltay)) * getAttrForce(
									deltay, lnodeu.getZone())));
				}
			}
			// kraefte in positionsaenderung umrechnen
			calcDistToPos(arcs, lnodes, temp, egraph.getGraphGen());
			temp = cool(temp, i);
			// wenn temp 0 ist, ist keine positionsaenderung mehr moeglich, also
			// muss auch nicht weiter berechnet werden
			if (temp == 0) {
				break;
			}
		}
		
		egraph.setGraphDim(this.panel);	
		// die neuen Layoutinfos aus dem Layoutnodes in die EdNodes uebertragen.
		egraph.updateNodePosLtoE(nodes);	
	}

	/**
	 * berechnet die positionsaenderung von knoten anhand der abstossenden
	 * kraefte die zwischen allen knoten bestehen.
	 * 
	 * @param lnodes
	 */
	private void calcNodeRepulse(final Vector<LayoutNode> lnodes) {		
		for (int j = 0; j < lnodes.size(); j++) {
			final LayoutNode lnodev = lnodes.get(j);

			if ((!this.usePattern && (lnodev.isFrozenByDefault() && this.frozenPos))
					|| isFrozen(lnodev)) {// type pattern check
				continue;
			}
			
			lnodev.setDistX(0);
			lnodev.setDistY(0);
			
			// bestimmung der kraefte anteilig in x und y richtung
			for (int k = 0; k < lnodes.size(); k++) {
				final LayoutNode lnodeu = lnodes.get(k);

				// keine werte fuer identische lnodes berechnen
				if (lnodev.equals(lnodeu)) {
					continue;
				}
				// if(!this.usepattern && (lnodeu.isFrozenAsDefault() && this.frozenPos))
				// continue;
				// else if(isFrozen(lnodeu)) // type pattern check
				// continue;

				final int deltax = lnodev.getAkt().x - lnodeu.getAkt().x;
				final int deltay = lnodev.getAkt().y - lnodeu.getAkt().y;
				// abfragen um division durch 0 zu vermeiden, wenn deltax oder
				// deltay 0 sind, aendert sich nix an der distanz
				// bloeder denkfehler: in vielen faellen muss sich dann die
				// dist, also die gewuenschte bewegung, einen wert !=0 haben, da
				// die knoten auf der selben pos liegen
//				int distx = 0, disty = 0;
				if (deltax != 0) {
					// distx = lnodev.getDistX() +
					// ((deltax/Math.abs(deltax))*getRepulseForce(deltax,lnodev.getZone()));

					lnodev.setDistX(lnodev.getDistX()
							+ ((deltax / Math.abs(deltax)) * getRepulseForce(
									deltax, lnodev.getZone())));
				}
				if (deltay != 0) {
					// disty = lnodev.getDistY() +
					// ((deltay/Math.abs(deltay))*getRepulseForce(deltay,lnodev.getZone()));

					lnodev.setDistY(lnodev.getDistY()
							+ ((deltay / Math.abs(deltay)) * getRepulseForce(
									deltay, lnodev.getZone())));
				}
				// test minus bereich
				// if(distx < 0) distx = distx * (-1);
				// if(disty < 0) disty = disty * (-1);
				// lnodev.setDistX(distx);
				// lnodev.setDistY(disty);

				if (deltax == 0 && deltay == 0) {
					// wenn die knoten auf der selben position liegen
					// zuefaellige distx und disty aenderung
					// gibt sicher noch ne bessere moeglichkeit???
					// long l = Double.doubleToLongBits(Math.random());
					// int r = ((Long)l).intValue()%200;

					int r = this.random.nextInt(this.panel.width) % 200;
					lnodev.setDistX(lnodev.getDistX() + r);
					lnodev.setDistY(lnodev.getDistY() - r);

					// test minus bereich
					// disty = lnodev.getDistY() - r;
					// if(disty < 0) disty = disty * (-1);
					// lnodev.setDistY(disty);
				}
			}
		}
	}

	private void calcDistToPos(final List<EdArc> arcs, 
								final Vector<LayoutNode> lnodes, 
								final int temp,
								final int gage) {
		
		int newx, newy, minx, miny, age, akttemp;

		for (int j = 0; j < lnodes.size(); j++) {
			final LayoutNode lnodev = lnodes.get(j);

			if ((!this.usePattern && (lnodev.isFrozenByDefault() && this.frozenPos))
					|| isFrozen(lnodev)) {
				continue;
			}
			newx = lnodev.getAkt().x;
			newy = lnodev.getAkt().y;
			minx = 0;
			miny = 0;
			age = lnodev.getAge();
			akttemp = reduceTempByAge(temp, age, gage);

			if (lnodev.getDistX() != 0) {
				// newx = lnodev.getAkt().x +
				// ((lnodev.getDistX()/Math.abs(lnodev.getDistX()))*Math.min(Math.abs(lnodev.getDistX()),temp));
				minx = Math.min(Math.abs(lnodev.getDistX()), akttemp);
				if (lnodev.getDistX() >= 0) {
					newx = newx + minx;
				} else {
					newx = newx - minx;
				}
			}
			if (lnodev.getDistY() != 0) {
				// newy = lnodev.getAkt().y +
				// ((lnodev.getDistX()/Math.abs(lnodev.getDistY()))*Math.min(Math.abs(lnodev.getDistY()),temp));
				miny = Math.min(Math.abs(lnodev.getDistY()), akttemp);
				if (lnodev.getDistY() >= 0) {
					newy = newy + miny;
				} else {
					newy = newy - miny;
				}
			}
			// System.out.println("min(distx,temp): "+minx+" min(disty,temp):
			// "+miny+" newx: "+newx+" newy:"+newy);

			// simpler ansatz um zu verhindern,
			// das die nodes aus dem Panel wandern
			// vermutlich noch zu verbessern
			// // keine negativen werte
			if ((newx - lnodev.getEdNode().getWidth()/2) <= 0)
				newx = Math.max(newx, lnodev.getEdNode().getWidth());
			if ((newy - lnodev.getEdNode().getHeight()/2) <= 0)
				newy = Math.max(newy, lnodev.getEdNode().getHeight());

			// // keine werte die groesser sind als die panelbreite
			// newx =
			// Math.min(newx,this.panel.width-lnodev.getEdNode().getWidth()*2);
			// // keine werte, die groesser als die Panelhoehe sind
			// newy =
			// Math.min(newy,this.panel.height-lnodev.getEdNode().getHeight()*2);
			//			
			// //System.out.println("aktx: "+lnodev.getAkt().x+" akty:
			// "+lnodev.getAkt().y+" distx: "+lnodev.getDistX()+" disty:
			// "+lnodev.getDistY()+" newx: "+newx+" newy: "+newy);
			// lnodev.setOpt(new Point(newx,newy));
			// lnodev.setAkt(new Point(newx,newy));
			// //TODO eventuell noch an die panelgroesse anpassen.....

			// check min, max position, change if needed
			Point newp = getRandomPosIfNeeded(newx, newy, lnodev);

			lnodev.setOpt(newp);
			lnodev.setAkt(newp);
		}

		if (this.usePattern) {
			for (int i = 0; i < arcs.size(); i++) {
				final EdArc arc = arcs.get(i);

				Vector<LayoutPattern> patterns = null;
				if (arc.isElementOfTypeGraph())
					patterns = getInheritancePattern(arc);
				if (patterns == null)
					patterns = getLayoutPatternsForType(arc.getType()
							.getBasisType());

				for (int p = 0; p < patterns.size(); p++) {
					// lp =
					// /*eg.getGraGra().*/getLayoutPatternForTypeName(arc.getTypename());
					final LayoutPattern lp = patterns.get(p);
					// System.out.println("LayoutPatternForTypeName
					// "+lp.getEffectedEdgeType());
					if (lp != null && lp.isEdgePattern()) {
						final LayoutNode source = ((EdNode) arc.getSource()).getLNode();
						final LayoutNode target = ((EdNode) arc.getTarget()).getLNode();
						if (lp.isXOffset()) {
							if (lp.getOffset() > 0) {// target muss
								// groesseres x haben
								if (!isFrozen(target)) {
									newx = Math.max(target.getAkt().x, source
											.getAkt().x + 4);
									newy = target.getAkt().y;
									target.setAkt(new Point(newx, newy));
								} else if (!isFrozen(source)) {
									newx = Math.min(target.getAkt().x - 1,
											source.getAkt().x);
									newy = source.getAkt().y;
									source.setAkt(new Point(newx, newy));
								}
							} else {// offset kleiner null -> target muss
								// kleineres x haben
								if (!isFrozen(source)) {
									newx = Math.max(target.getAkt().x + 1,
											source.getAkt().x);
									newy = source.getAkt().y;
									source.setAkt(new Point(newx, newy));
								} else if (!isFrozen(target)) {
									newx = Math.min(target.getAkt().x, source
											.getAkt().x - 4);
									newy = target.getAkt().y;
									target.setAkt(new Point(newx, newy));
								}
							}
						}// else
						if (lp.isYOffset()) {
							if (lp.getOffset() > 0) {// target muss
								// groesseres y haben
								if (!isFrozen(target)) {
									newy = Math.max(target.getAkt().y, source
											.getAkt().y + 4);
									newx = target.getAkt().x;
									target.setAkt(new Point(newx, newy));
								} else if (!isFrozen(source)) {
									newy = Math.min(target.getAkt().y - 1,
											source.getAkt().y);
									newx = source.getAkt().x;
									source.setAkt(new Point(newx, newy));
								}
							} else {// offset kleiner null -> target muss
								// kleineres x haben
								if (!isFrozen(source)) {
									newy = Math.max(target.getAkt().y + 1,
											source.getAkt().y);
									newx = source.getAkt().x;
									source.setAkt(new Point(newx, newy));
								} else if (!isFrozen(target)) {
									newy = Math.min(target.getAkt().y, source
											.getAkt().y - 4);
									newx = target.getAkt().x;
									target.setAkt(new Point(newx, newy));
								}
							}
						}
					}
				}
			}
		}
	}

	// arcLayout
	public void layoutByArcLength(final EdGraph eg,
			final List<EdNode> nodes,
			final List<EdArc> arcs) {
		// layoutByArcLength(eg, iterations, this.temperature);
		layoutByArcLength(eg, nodes, arcs, this.edgeIntersctnIters, this.temperature);
	}

	/**
	 * berechnet ein layout fuer den graphen nur anhand der aktuellen und
	 * bevorzugten Kantenlaengen
	 * 
	 * @param eg
	 *            Graph zu layout
	 * @param nodes
	 * 				sichtbare Knoten in dem Graphen
	 * @param arcs
	 * 				Kanten mit Soure und Target in sichtbaren Knoten
	 * @param iters
	 *            Anzahl der Iterationen
	 * @param temp
	 *            "temperatur" gibt an, wie stark sich die position eines Knoten
	 *            pro berechnungsschritt aendern darf
	 */
	// arcLayout
	public boolean layoutByArcLength(final EdGraph eg, 
			final List<EdNode> nodes,
			final List<EdArc> arcs,
			final int itrs, 
			final int temp) {

		// wenn der graph keine kanten enthaelt, ist diese methode irrelevant:
		if (arcs.size() == 0) {
			return true;
		}
		
		int age, akttemp;
		final int gage = eg.getGraphGen();

		// System.out.println("Layouter.layoutByArcLength ...");
		// aktuelle laenge aller arcs berechnen
		for (int i = 0; i < arcs.size(); i++) {
			final EdArc arc = arcs.get(i);
			arc.getLArc().calcAktLength();
			arc.getLArc().resetUsed();
		}
		
		int index;
		int max, abw, xchange, ychange, sxchange, sychange, txchange, tychange, sxnew, synew, txnew, tynew;
		boolean layoutDone = false;
		
		for (int i = 0; i < itrs; i++) {
			// arc mit groesster abweichung bestimmen
			max = 0;
			index = -1;
			for (int j = 0; j < arcs.size(); j++) {
				final EdArc edarc = arcs.get(j);
				final LayoutArc larc = edarc.getLArc();

				int prefLength = larc.getPrefLength();
				LayoutPattern lpat = getLayoutPatternForType(edarc
						.getBasisArc().getType(), "edge_length");
				if (lpat != null && lpat.getLength() > 0) {
					prefLength = lpat.getLength();
				}
				// System.out.println("prefLength: "+prefLength);
				abw = Math.abs(prefLength - larc.getAktLength());
				// vorzeichen interessiert hier nicht,sondern nur das ausmass
				// der abweichung.
				// evtl spaeter abweichungen in richtung zu kurz schwerer
				// gewichten als zu lang!?
				if ((abw / (larc.getUsed() + 1)) > max) {
					// somit wird vermieden, das kanten immer wieder betrachtet
					// werden,
					// aber nicht oder nur sehr wenig geaendert werden koennen
					// (neue kanten zwischen alten knoten)
					max = abw;
					index = j;
				}
			}
			
			if (index == -1) {
				continue;
			}
			
			final EdArc arc = arcs.get(index);
			final LayoutArc larc = arc.getLArc();
			larc.incUsed();
			// System.out.println("max: "+max+" arclengthx:
			// "+larc.getXLength()+" arclengthy: "+larc.getYLength());
			if (max < 20) {// bei so kleinen abweichungen lohnt der aufwand
				// nicht mehr, evtl den wert noch aendern
				// System.out.println("iter i: "+i+" max arc-length abweichung:
				// "+max+" arclengthx: "+larc.getXLength()+" arclengthy:
				// "+larc.getYLength());
				break;
			}

			// neue Positionen fuer die endknoten berechnen
			final LayoutNode source = ((EdNode) arc.getSource()).getLNode();
			final LayoutNode target = ((EdNode) arc.getTarget()).getLNode();

			int prefLength = larc.getPrefLength();
			LayoutPattern lpat = getLayoutPatternForType(arc.getBasisArc()
					.getType(), "edge_length");
			if (lpat != null && lpat.getLength() > 0) {
				prefLength = lpat.getLength();
			}
			abw = larc.getAktLength() - prefLength; // larc.getPrefLength();//abw
			// ist positiv, wenn die
			// aktuelle laenge groesser
			// als die bevorzugte ist
			// aenderungen in x und y richtung berechnen um die abweichung
			// auszugleichen
			if (larc.getXLength() != 0 && larc.getYLength() != 0) {
				// xc = ((abw*abw)/4)*larc.getXLength()*larc.getXLength();
				// xy =
				// (larc.getXLength()*larc.getXLength())+(larc.getYLength()*larc.getYLength());
				// System.out.println("xc: "+xc+" xy: "+xy);
				// xchange = (int)Math.sqrt(xc/xy);
				// ychange = (larc.getYLength()*xchange)/larc.getXLength();
				xchange = Math.abs(((abw / 2) * larc.getXLength())
						/ larc.getAktLength());
				ychange = Math.abs(((abw / 2) * larc.getYLength())
						/ larc.getAktLength());
			} else {
				if (larc.getXLength() == 0) {
					xchange = 0;
					ychange = Math.abs(abw / 2);
				} else {
					xchange = Math.abs(abw / 2);
					ychange = 0;
				}
			}

			// //System.out.println("Akt-LEngth: "+larc.getAktLength()+"
			// Abweichung: "+abw+" xchange: "+xchange+" ychange: "+ychange);
			// positionsaenderungen werden durch temperatur beschraenkt und mit
			// dem vorzeichen von abw versehen
			age = source.getAge();
			if (!(this.frozenPos && source.isFrozenByDefault())) {
				akttemp = reduceTempByAge(temp, age, gage);
			}
			else {
				akttemp = temp;
			}
			sxchange = Math.min(xchange, akttemp) * (abw / Math.abs(abw));
			sychange = Math.min(ychange, akttemp) * (abw / Math.abs(abw));

			age = target.getAge();
			if (!(this.frozenPos && target.isFrozenByDefault())) {
				akttemp = reduceTempByAge(temp, age, gage);
			}
			else {
				akttemp = temp;
			}
			akttemp = reduceTempByAge(temp, age, gage);
			txchange = Math.min(xchange, akttemp) * (abw / Math.abs(abw));
			tychange = Math.min(ychange, akttemp) * (abw / Math.abs(abw));

			if (source.getAkt().x <= target.getAkt().x) {
				sxnew = source.getAkt().x + sxchange;
				txnew = target.getAkt().x - txchange;
			} else {
				sxnew = source.getAkt().x - sxchange;
				txnew = target.getAkt().x + txchange;
			}
			if (source.getAkt().y <= target.getAkt().y) {
				synew = source.getAkt().y + sychange;
				tynew = target.getAkt().y - tychange;
			} else {
				synew = source.getAkt().y - sychange;
				tynew = target.getAkt().y + tychange;
			}
			// //nicht in negative bereiche kommen...
			// sxnew = Math.max(sxnew,source.getEdNode().getWidth());
			// synew = Math.max(synew,source.getEdNode().getHeight());
			// txnew = Math.max(txnew,target.getEdNode().getWidth());
			// tynew = Math.max(tynew,target.getEdNode().getHeight());
			// // nicht aus dem Panel herausgehen:
			// sxnew =
			// Math.min(sxnew,this.panel.width-source.getEdNode().getWidth()*2);
			// synew =
			// Math.min(synew,this.panel.height-source.getEdNode().getHeight()*2);
			// txnew =
			// Math.min(txnew,this.panel.width-target.getEdNode().getWidth()*2);
			// tynew =
			// Math.min(tynew,this.panel.height-target.getEdNode().getHeight()*2);

			// test
			if (!(this.frozenPos && source.isFrozenByDefault())) {
				Point newsp = getRandomPosIfNeeded(sxnew, synew, source);
				sxnew = newsp.x;
				synew = newsp.y;
			}

			if (!(this.frozenPos && target.isFrozenByDefault())) {
				Point newtp = getRandomPosIfNeeded(txnew, tynew, target);
				txnew = newtp.x;
				tynew = newtp.y;
			}

			if (this.usePattern) {
				Vector<LayoutPattern> patterns = null;
				if (arc.isElementOfTypeGraph()) {
					patterns = getInheritancePattern(arc);
				}
				if (patterns == null) {
					patterns = getLayoutPatternsForType(arc.getType()
							.getBasisType());
				}
				for (int p = 0; p < patterns.size(); p++) {
					//final LayoutPattern lp =
					// /*eg.getGraGra().*/getLayoutPatternForTypeName(arc.getTypename());
					final LayoutPattern lp = patterns.get(p);
					// System.out.println("LayoutPatternsForTypeName
					// "+lp.getEffectedEdgeType());
					if (lp != null && lp.isEdgePattern()) {
						if (lp.isXOffset()) {
							if (lp.getOffset() > 0) {// also target x
								// groesser als das von
								// Source
								if (!isFrozen(target))
									txnew = Math.max(txnew, sxnew + 4);
								else if (!isFrozen(source))
									sxnew = Math.min(sxnew, txnew - 4);
							} else {// also source x groesser als das von target
								if (!isFrozen(source))
									sxnew = Math.max(sxnew, txnew + 4);
								else if (!isFrozen(target))
									txnew = Math.min(txnew, sxnew - 4);
							}
						}// else
						if (lp.isYOffset()) {
							if (lp.getOffset() > 0) {// also target y
								// groesser als source y
								if (!isFrozen(target))
									tynew = Math.max(tynew, synew + 4);
								else if (!isFrozen(source))
									synew = Math.min(synew, tynew - 4);
							} else {// also source y groesser als target y
								if (!isFrozen(source))
									synew = Math.max(synew, tynew + 4);
								else if (!isFrozen(target))
									tynew = Math.min(tynew, synew - 4);
							}
						}
					}
				}
			}

			// //System.out.println("sourcex: "+sxnew+" sourcey: "+synew+"
			// targetx: "+txnew+" targety: "+tynew);

			// System.out.println("Layouter.layoutByArc:: isFrozen(source):
			// "+isFrozen(source)+" isFrozen(target): "+isFrozen(target));
			if (!(this.frozenPos && source.isFrozenByDefault()) && !isFrozen(source)) {
				source.getAkt().setLocation(sxnew, synew);
				source.getOpt().setLocation(source.getAkt());
			}
			if (!(this.frozenPos && target.isFrozenByDefault()) && !isFrozen(target)) {
				target.getAkt().setLocation(txnew, tynew);
				target.getOpt().setLocation(target.getAkt());
			}
			// aktuelle laengen neu berechnen
			for (int k = 0; k < arcs.size(); k++) {
				final EdArc a = arcs.get(k);
				if (!a.getLArc().isFrozen())
					a.getLArc().calcAktLength();
			}
			layoutDone = true;
		}
		// neue positionswerte aus den layoutnodes in die ednodes uebernehmen
		eg.updateNodePosLtoE(nodes);

		return layoutDone;
	}

	// combinedLayout1
	public void combinedLayout(final EdGraph eg,
								final List<EdNode> nodes,
								final List<EdArc> arcs) {
		combinedLayout(eg, nodes, arcs,				
				this.iters, this.iters,
				this.edgeIntersctnIters);
	}

	// combinedLayout1
	public void combinedLayout(final EdGraph eg, 
								final List<EdNode> nodes,
								final List<EdArc> arcs,
								final int itrs,
								final int nodeit, 
								final int arcit) {
		for (int i = 0; i < itrs; i++) {
			layout(eg, nodes, arcs, nodeit);
			layoutByArcLength(eg, nodes, arcs, arcit, 100);
		}
	}

	// combinedLayout2 | default Layout
	public boolean layoutGraph(final EdGraph eg,
								final List<EdNode> nodes,
								final List<EdArc> arcs) {
		return layoutGraph(eg, nodes, arcs,
							this.iters, 
							this.nodeIntersctnIters,
							this.edgeIntersctnIters);
	}

	// combinedLayout2 | default Layout
	public boolean layoutGraph(final EdGraph eg, 
			final List<EdNode> nodes,
			final List<EdArc> arcs,
			final int itrs, 
			final int nodeit, 
			final int arcit) {
//		System.out.println("Layouter.layoutGraph... iters: "+iters+" nodeit: "+nodeit+" arcit: "+arcit);
		
		for (int i = 0; i < arcs.size(); i++) {
			final EdArc a = arcs.get(i);
			if (!a.getLArc().isFrozen() && !this.frozenPos
					&& !a.getLArc().isFrozenByDefault())
				a.setAnchor(null);
		}
		
		final Vector<LayoutNode> lnodes = getLayoutNodes(nodes);
		for (int i = 0; i < itrs; i++) {
			final int temp2 = this.temperature;
			for (int j = 0; j < nodeit; j++) {
				calcNodeRepulse(lnodes);
				calcDistToPos(eg.getArcs(), lnodes, temp2, eg.getGraphGen());
				cool(temp2, j);
			}
			layoutByArcLength(eg, nodes, arcs, arcit, this.temperature);
			cool(this.temperature, i);
		}
		
		this.overlapscnt = this.lmetric.getNodeIntersect(nodes, true);
		if (/*!this.usepattern &&*/ this.overlapscnt == 0) {
			eg.updateNodePosLtoE(nodes);
			return false;
		}

		// jetzt nach metricverletzungen suchen und versuchen diese zu
		// beheben.(momentan nur knotenueberschneidungen)
		int x1, x2, y1, y2;
		int ovlNodeIndx;
		int stop = 100; //this.overlapscount;
		
		while (this.overlapscnt > 0 && stop > 0) {
			for (int i = 0; i < lnodes.size(); i++) {
				final LayoutNode lnode1 = lnodes.get(i);
				// System.out.println("this.usepattern: "+this.usepattern+" this.frozenPos:
				// "+this.frozenPos+" lnode1.isFrozenAsDefault:
				// "+lnode1.isFrozenAsDefault()+" isFrozen: "+isFrozen(lnode1));
				if ((!this.usePattern && (lnode1.isFrozenByDefault() && this.frozenPos))
						|| isFrozen(lnode1)) {// type pattern check
					continue;
				}
				if (lnode1.isOverlapping()) {
					ovlNodeIndx = this.lmetric.getOverlappingNode(lnodes, i);
					if (ovlNodeIndx < 0) {
						lnode1.unsetOverlap();
						continue;
					}
					
					final LayoutNode lnode2 = lnodes.get(ovlNodeIndx);

					if ((!this.usePattern && lnode2.isFrozenByDefault() && this.frozenPos)
							|| isFrozen(lnode2)) {// type pattern check
						continue;
					}
					// TODO spaeter vielleicht ne vernuenftige funktion zum
					// verschieben ausdenken
					if (lnode1.getAkt().x < lnode2.getAkt().x) {
						x1 = lnode1.getAkt().x - lnode1.getEdNode().getWidth();
						x2 = lnode2.getAkt().x + lnode2.getEdNode().getWidth();
					} else {
						x1 = lnode1.getAkt().x + lnode1.getEdNode().getWidth();
						x2 = lnode2.getAkt().x - lnode2.getEdNode().getWidth();
					}
					if (lnode1.getAkt().y < lnode2.getAkt().y) {
						y1 = lnode1.getAkt().y - lnode1.getEdNode().getHeight();
						y2 = lnode2.getAkt().y + lnode2.getEdNode().getHeight();
					} else {
						y1 = lnode1.getAkt().y + lnode1.getEdNode().getHeight();
						y2 = lnode2.getAkt().y - lnode2.getEdNode().getHeight();
					}

					// x1 = Math.max(x1,lnode1.getEdNode().getWidth());
					// x2 = Math.max(x2,lnode2.getEdNode().getWidth());
					// y1 = Math.max(y1,lnode1.getEdNode().getHeight());
					// y2 = Math.max(y2,lnode2.getEdNode().getHeight());
					// x1 =
					// Math.min(x1,this.panel.width-lnode1.getEdNode().getWidth()*2);
					// x2 =
					// Math.min(x2,this.panel.width-lnode2.getEdNode().getWidth()*2);
					// y1 =
					// Math.min(y1,this.panel.height-lnode1.getEdNode().getHeight()*2);
					// y2 =
					// Math.min(y2,this.panel.height-lnode2.getEdNode().getHeight()*2);

					// test
					Point p1 = getRandomPosIfNeeded(x1, y1, lnode1);
					Point p2 = getRandomPosIfNeeded(x2, y2, lnode2);
					x1 = p1.x;
					y1 = p1.y;
					x2 = p2.x;
					y2 = p2.y;

					lnode1.setAkt(new Point(x1, y1));
					lnode1.setOpt(lnode1.getAkt());
					lnode2.setAkt(new Point(x2, y2));
					lnode2.setOpt(lnode2.getAkt());

					lnode1.unsetOverlap();
					lnode2.unsetOverlap();
				}
			}
			this.overlapscnt = this.lmetric.getNodeIntersect(nodes, true);
			// System.out.println("Layouter.layoutGraph... this.overlapscount
			// "+this.overlapscount);

			stop--;
		}
		eg.updateNodePosLtoE(nodes);
		return true;
	}

	/*
	private boolean hasFreezingPattern(final Type t) {
		boolean res = false;
		final LayoutPattern lp = this.getLayoutPatternForType(t, "frozen_node");
		if (lp != null) {
			res = true;
		}
		return res;
	}
*/
	
	private boolean isFrozen(final LayoutNode ln) {
		if (this.usePattern && this.layoutNode2Type != null) {
			final Type t = this.layoutNode2Type.get(ln);
			if (t != null) {
				if (this.getLayoutPatternForType(t, "frozen_node") != null) {
					return true;
				}
				return ln.isFrozen();
			}
		}
		return ln.isFrozen();
	}

	// public void setFreezingForOldLayoutNode(boolean b){
	//
	// }
	//	
	// public void setFreezingForOldLayoutEdge(boolean b){
	//		
	// }

	// public void setNodeTypeFrozen(Type nodetype, boolean frozen){
	// if(!frozenNodeType.contains(nodetype) && frozen)
	// frozenNodeType.add(nodetype);
	// else if(frozenNodeType.contains(nodetype) && !frozen)
	// frozenNodeType.remove(nodetype);
	// }

	public boolean graphContainsNewNodes(final EdGraph eg) {
		final Vector<EdNode> nodes = eg.getNodes();
		final Vector<EdNode> oldnodes = this.oldedgraph.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			final EdNode ni = nodes.get(i);
			boolean found = false;
			for (int j = 0; j < oldnodes.size(); j++) {
				final EdNode nj = oldnodes.get(j);
				if (ni.getBasisNode().compareTo(nj.getBasisNode())) {
					found = true;
					break;
				}
			}
			if (!found)
				return true;
		}
		return false;
	}

	private Vector<LayoutNode> getLayoutNodes(final List<EdNode> nodes) {
		this.layoutNode2Type.clear();
		final Vector<LayoutNode> ret = new Vector<LayoutNode>();
		for (int i = 0; i < nodes.size(); i++) {
			final EdNode n = nodes.get(i);
			if (n != null && n.getLNode() != null) {
				this.layoutNode2Type.put(n.getLNode(), n.getBasisNode().getType());
				ret.addElement(n.getLNode());
			}
		}
		return ret;
	}

	private Vector<LayoutArc> getLayoutArcs(final List<EdArc> arcs) {
		final Vector<LayoutArc> ret = new Vector<LayoutArc>();
		for (int i = 0; i < arcs.size(); i++) {
			ret.addElement(arcs.get(i).getLArc());
		}
		return ret;
	}

	private Point getRandomPosIfNeeded(final int xpos, final int ypos, final LayoutNode lnode) {
		// keine negativen werte bzw. keine werte so das der knoten aus dem bild
		// verschwindet
		int x = Math.max(xpos, lnode.getEdNode().getWidth());
		// keine negativen werte
		int y = Math.max(ypos, lnode.getEdNode().getHeight());

		// keine werte die groesser sind als die panelbreite
		// x = Math.min(x,this.panel.width-lnode.getEdNode().getWidth()*2);
		// keine werte, die groesser als die Panelhoehe sind
		// y = Math.min(y,this.panel.height-lnode.getEdNode().getHeight()*2);

		// check minus and 0 Bereich
		if (x <= 0 || (x - lnode.getEdNode().getWidth() < 0))
			x = Math.max(x, lnode.getEdNode().getWidth());
		if (y <= 0 || (y - lnode.getEdNode().getHeight() < 0))
			y = Math.max(y, lnode.getEdNode().getHeight());
		int nn = 0;
		// keine werte die groesser sind als die panelbreite
		while (x > (this.panel.width - lnode.getEdNode().getWidth()) && nn < 6) {
			nn++;
//			int r = this.random.nextInt(this.panel.width);			
			// x = x - Math.abs(r);
			// bleib im + Bereich
			// x = Math.abs(x - r);
			x = Math.abs(x - Math.abs(this.random.nextInt(this.panel.width)));
		}
		nn = 0;
		// keine werte, die groesser als die Panelhoehe sind
		while (y > (this.panel.height - lnode.getEdNode().getHeight()) && nn < 6) {
			nn++;
//			int r = this.random.nextInt(this.panel.height);
			// y = y - Math.abs(r);
			// bleib im + Bereich
			// y = Math.abs(y - r);
			y = Math.abs(y - Math.abs(this.random.nextInt(this.panel.height)));
		}

		return new Point(x, y);
	}

	public void randomLayout(final EdGraph eg, final List<EdNode> nodes) {		
//		final int xdim = this.panel.width;
//		final int ydim = this.panel.height;
		
		final Vector<LayoutNode> lnodes = getLayoutNodes(nodes);
		for (int i = 0; i < lnodes.size(); i++) {
			final LayoutNode lnode = lnodes.get(i);
			if ((!this.usePattern && (lnode.isFrozenByDefault() && this.frozenPos))
					|| isFrozen(lnode)) {
				continue;
			}
			int x = (Math.abs(this.random.nextInt(this.panel.width))); //%xdim;
			int y = (Math.abs(this.random.nextInt(this.panel.height))); //%ydim;
			lnode.setAkt(new Point(x, y));
			lnode.setOpt(new Point(x, y));
		}

		eg.updateNodePosLtoE(nodes);
	}

	public void makeRandomLayoutOfNodes(final EdGraph eg, final List<EdNode> nodes) {
		// System.out.println("Layouter.makeNodesRandomLayout:
		// "+eg.getBasisGraph().getName());
		final Vector<LayoutNode> lnodes = getLayoutNodes(nodes);
		final int xdim = this.panel.width;
		final int ydim = this.panel.height;
		int x, y;

		for (int i = 0; i < lnodes.size(); i++) {
			final LayoutNode lnode = lnodes.get(i);
			// System.out.println(i+". :: "+this.usepattern+"
			// "+lnode.isFrozenByDefault()+" "+this.frozenPos+" "+lnode.isFrozen()+"
			// "+isFrozen(lnode));
			
			if ((!this.usePattern && (lnode.isFrozenByDefault() && this.frozenPos))
					|| isFrozen(lnode)) {
				continue;
			}
			
			if (lnode.getAge() == 0) {
				final int rdx = Math.abs(this.random.nextInt(xdim));
				// while(rdx < 50)
				// rdx = Math.abs(this.random.nextInt(xdim));

				final int rdy = Math.abs(this.random.nextInt(ydim));
				// while(rdy < 50)
				// rdy = Math.abs(this.random.nextInt(ydim));

				x = rdx % xdim;
				y = rdy % ydim;

				if (x - lnode.getEdNode().getWidth() < 0)
					x = Math.max(x, lnode.getEdNode().getWidth());
				if (y - lnode.getEdNode().getHeight() < 0)
					y = Math.max(y, lnode.getEdNode().getHeight());

				// System.out.println("Layouter.makeRandomLayoutOfNodes::: node:
				// "+lnode.getEdNode().getTypeName()+" dim: ["+xdim+" ,
				// "+ydim+"] zufallszahl: "+rdx+" , "+rdy+" x: "+x+" y: "+y);
				if (this.usePattern) {
					final EdNode node = lnode.getEdNode();
					final Vector<EdArc> arcs = eg.getArcs();
					for (int j = 0; j < arcs.size(); j++) {
						final EdArc arc = arcs.get(j);
						if (arc.getSource().equals(node)
								|| arc.getTarget().equals(node)) {

							Vector<LayoutPattern> patterns = null;
							if (arc.isElementOfTypeGraph())
								patterns = getInheritancePattern(arc);
							if (patterns == null)
								patterns = getLayoutPatternsForType(arc
										.getType().getBasisType());

							for (int p = 0; p < patterns.size(); p++) {
								final LayoutPattern lp = patterns.get(p);

								if (lp != null && lp.isEdgePattern()) {
									if (arc.getSource().equals(node)) {
										if (lp.isXOffset()) {
											x = arc.getTarget().getX()
													- (lp.getOffset() * rdx % 200);
											x = Math.max(x, node.getWidth());
											x = Math.min(x, this.panel.width
													- node.getWidth() * 2);
										}// else
										if (lp.isYOffset()) {
											y = arc.getTarget().getY()
													- (lp.getOffset() * rdy % 200);
											y = Math.max(y, node.getHeight());
											y = Math.max(y, this.panel.height
													- node.getHeight() * 2);
										}
										// System.out.println("neue Source: x:
										// "+x+" y: "+y+"\t Target: x:
										// "+arc.getTarget().getX()+" y:
										// "+arc.getTarget().getY());
									} else { // node ist target
										if (lp.isXOffset()) {
											x = arc.getSource().getX()
													+ (lp.getOffset() * rdx % 200);
											x = Math.max(x, node.getWidth());
											x = Math.min(x, this.panel.width
													- node.getWidth() * 2);
										}// else
										if (lp.isYOffset()) {
											y = arc.getSource().getY()
													+ (lp.getOffset() * rdy % 200);
											y = Math.max(y, node.getHeight());
											y = Math.max(y, this.panel.height
													- node.getHeight() * 2);
										}
										// System.out.println("Source: x:
										// "+arc.getSource().getX()+" y:
										// "+arc.getSource().getY()+"\t neues
										// Target: x: "+x+" y: "+y);
									}
								}
							}
						}
					}
				}
				lnode.setAkt(new Point(x, y));
				lnode.setOpt(new Point(x, y));
				// System.out.println("i: "+i+" dim: ["+xdim+" , "+ydim+"]
				// zufallszahl: "+rdx+" , "+rdy+" x: "+x+" y: "+y);
			}
		}
		eg.updateNodePosLtoE(nodes);
	}

	public void centreLayout(final EdGraph eg, final List<EdNode> nodes) {
		// System.out.println("Layout.centerLayout ... ");
		final Vector<LayoutNode> lnodes = getLayoutNodes(nodes);
		int left = 0, right = 0, up = 0, down = 0;
		int xmin = this.panel.width;
		int xmax = 0;
		int ymin = this.panel.height;
		int ymax = 0;

		for (int i = 0; i < lnodes.size(); i++) {
			final LayoutNode lnode = lnodes.get(i);
			if (lnode.getAkt().x < xmin) {
				xmin = lnode.getAkt().x;
				left = i;
			} else if (lnode.getAkt().x > xmax) {
				xmax = lnode.getAkt().x;
				right = i;
			}
			if (lnode.getAkt().y < ymin) {
				ymin = lnode.getAkt().y;
				up = i;
			} else if (lnode.getAkt().y < ymax) {
				ymax = lnode.getAkt().y;
				down = i;
			}
		}
		final LayoutNode lnodeleft = lnodes.get(left);
		final LayoutNode lnoderight = lnodes.get(right);
		final LayoutNode lnodeup = lnodes.get(up);
		final LayoutNode lnodedown = lnodes.get(down);
		int center = this.panel.width / 2;
		final int leftdif = center - lnodeleft.getAkt().x;
		final int rightdif = lnoderight.getAkt().x - center;
		center = this.panel.height / 2;
		final int updif = center - lnodeup.getAkt().y;
		final int downdif = lnodedown.getAkt().y - center;
		final int x_change = (leftdif - rightdif) / 2;
		final int y_change = (updif - downdif) / 2;

		for (int i = 0; i < lnodes.size(); i++) {
			final LayoutNode lnode = lnodes.get(i);
			lnode.getAkt().x = lnode.getAkt().x + x_change;
			lnode.getAkt().y = lnode.getAkt().y + y_change;
			lnode.setOpt(new Point(lnode.getAkt()));
		}
		eg.updateNodePosLtoE(nodes);
	}

	public void setPanelSize(final Dimension dim) {
		if (this.panelChangable || this.panel.width == 0 || this.panel.height == 0)
			this.panel = dim;
	}

	public void allowChangePanelSize(final boolean b) {
		this.panelChangable = b;
	}

	private Dimension getAverageNodeDim(final List<EdNode> nodes) {
		float aw = 0, ah = 0;
		if (!nodes.isEmpty()) {
			for (int i = 0; i < nodes.size(); i++) {
				EdNode e = nodes.get(i);
				aw = aw + e.getWidth();
				ah = ah + e.getHeight();
			}
		
			aw = aw / nodes.size();
			ah = ah / nodes.size();
		}

		return new Dimension((int)aw, (int)ah);
	}
	
	public Dimension getNeededPanelSize(final List<EdNode> nodes) {
		// average node size
		final Dimension averagenodesize = getAverageNodeDim(nodes);
		if (averagenodesize.width < 25 || averagenodesize.height < 25) {
			averagenodesize.width = 25;
			averagenodesize.height = 25;
		}
 
		final int ans = (averagenodesize.width >= averagenodesize.height) ? averagenodesize.width : averagenodesize.height;		
		int sizetmp = 2 * (int)Math.sqrt(nodes.size());
		int sizeX = ans * sizetmp;
		if (sizeX > 500 && sizeX < 1000)
			sizeX = 1000;
		int sizeY = sizeX*3/4;
		
		final Dimension neededpanelsize = new Dimension(sizeX, sizeY);
		
		// vergroessere zu kleine panel
		if (neededpanelsize.width < 400 || neededpanelsize.height < 400) {
			neededpanelsize.width = 400;
			neededpanelsize.height = 400;
		}
		
		return neededpanelsize;
	}

	public void setOldEdGraph(final EdGraph eg) {
		this.oldedgraph = eg;
	}

	public EdGraph getOldEdGraph() {
		return this.oldedgraph;
	}

	/**
	 * momentan schrott, wegen fehlender vergleichbarkeit eines EdNodes mit
	 * seiner kopie koordiniert die blitzalterung von knoten deren nachbarn
	 * weggefallen sind
	 * 
	 * @param eg
	 */
	public void shockAging(final EdGraph eg) { // blitzalterung
		// System.out.println("Layouter.flashAging... graph gen.:
		// "+eg.getGraphGen());
		final Vector<EdNode> nodes = eg.getNodes();
		final Vector<EdNode> oldnodes = this.oldedgraph.getNodes();
		int index;
		for (int i = 0; i < oldnodes.size(); i++) {
			final EdNode oldnode = oldnodes.get(i);
			if (oldnode.isInVectorByBasisNode(nodes) != -1) {
				// also knoten wurde geloescht
				// erst eingehende kanten...
				final Vector<EdArc> arcsIn = this.oldedgraph.getIncomingArcs(oldnode);
				for (int j = 0; j < arcsIn.size(); j++) {
					final EdNode oldnode2 = (EdNode) arcsIn.get(j).getSource();
					index = oldnode2.isInVectorByBasisNode(nodes);
					if (index != -1) {// pruefen, ob der nachbarknoten nicht
						// auch geloescht wurde
						final EdNode node = nodes.get(index);
						node.getLNode().setAge(
								Math.max(oldnode.getLNode().getAge(), node
										.getLNode().getAge()));
						// System.out.println("flashAging: incom.arcs:: nodeID:
						// "+node.getNodeID()+" age:
						// "+node.getLNode().getAge());
					}
				}
				// und fuer die ausgehenden
				final Vector<EdArc> arcsOut = this.oldedgraph.getOutgoingArcs(oldnode);
				for (int j = 0; j < arcsOut.size(); j++) {
					final EdNode oldnode2 = (EdNode) arcsOut.get(j).getTarget();
					index = oldnode2.isInVectorByBasisNode(nodes);
					if (index != -1) {// pruefen, ob der nachbarknoten nicht
						// auch geloescht wurde
						// erstmal wird das alter auf den des geloeschten
						// knotens gesetzt, die blitzalterung wird also nicht
						// wieder zurueckgenommen.
						// das wird spaeter vermutlich im alterungsprozess noch
						// beruecksichtigt
						final EdNode node = nodes.get(index);
						node.getLNode().setAge(
								Math.max(oldnode.getLNode().getAge(), node
										.getLNode().getAge()));
						// System.out.println("flashAging: outcom.arcs:: nodeID:
						// "+node.getNodeID()+" age:
						// "+node.getLNode().getAge());
					}
				}
			}
		}
	}

	public int getNodeIntersect(final List<EdNode> nodes, final boolean mark) {
		return this.lmetric.getNodeIntersect(nodes, mark);
	}

	public void setGeneralEdgeLength(final int l) {
		this.gnrlEdgeLngth = l;
	}

	public int getGeneralEdgeLength() {
		return this.gnrlEdgeLngth;
	}

	public void setBeginTemperature(final int t) {
		this.temperature = t;
	}

	public int getBeginTemperature() {
		return this.temperature;
	}

	public void setIterationCount(final int count) {
		this.iters = count;
	}

	public int getIterationCount() {
		return this.iters;
	}

	public void setNodeIntersectionIterationCount(final int count) {
		this.nodeIntersctnIters = count;
	}

	public void setEdgeIntersectionIterationCount(final int count) {
		this.edgeIntersctnIters = count;
	}

	public boolean getJpgOutput() {
		return this.jpgOutput;
	}

	public void setJpgOutput(final boolean b) {
		this.jpgOutput = b;
	}

	public boolean usePattern() {
		return this.usePattern;
	}

	public void setUsePattern(final boolean b) {
		this.usePattern = b;
	}

	public void setFrozenByDefault(final boolean b) {
		this.frozenPos = b;
	}

	public boolean isCentre() {
		return this.centre;
	}

	public void setCentre(final boolean b) {
		this.centre = b;
	}

	public boolean getWriteMetricValues() {
		return this.writeMetrics;
	}

	public void setWriteMetricValues(final boolean b) {
		this.writeMetrics = b;
	}

	public LayoutMetrics getLayoutMetrics() {
		return this.lmetric;
	}

	/**
	 * erzeugt ein neues LayoutPattern mit den uebergebenen Parametern und fuegt
	 * es dem layoutPatterns-Vector von this hinzu.
	 * 
	 * @param name
	 *            name of layout pattern
	 * @param pType
	 *            Patterntyp ("edge" oder "node") Vorerst nur "edge" sinnvoll
	 * @param type
	 *            type des edges auf das sich dieses LayoutPattern bezieht
	 * @param offsetType
	 *            in welche richtung soll das offset wirken ('x' oder 'y')
	 * @param offset
	 *            gibt an, wie source und target einer edge zueinander liegen
	 *            sollen. Bsp. offsettype='x' offset>0 target soll rechts von
	 *            source liegen.
	 */
	public void createLayoutPattern(final String name, final String pType, final Type type,
			final char offsetType, final int offset) {
		final LayoutPattern lp = new LayoutPattern(name, pType, type, offsetType,
				offset);
		Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null)
			v = new Vector<LayoutPattern>();
		v.add(lp);
		this.layoutPatterns.put(type, v);
		// System.out.println("Layouter.createLayoutPattern type: "+type+"
		// count:"+v.size());
	}

	public void createLayoutPattern(final String name, final String pType, final Type type,
			final int length) {
		final LayoutPattern lp = new LayoutPattern(name, pType, type, length);
		Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null)
			v = new Vector<LayoutPattern>();
		v.add(lp);
		this.layoutPatterns.put(type, v);
		// System.out.println("Layouter.createLayoutPattern type: "+type+"
		// count:"+v.size());
	}

	public void createLayoutPattern(final String name, final String pType, final Type type,
			final boolean frozen) {
		final LayoutPattern lp = new LayoutPattern(name, pType, type, frozen);
		Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null)
			v = new Vector<LayoutPattern>();
		v.add(lp);
		this.layoutPatterns.put(type, v);
		// System.out.println("Layouter.createLayoutPattern type:
		// "+type.getName()+" count:"+v.size());
	}

	public void removeLayoutPattern(final Type type) {
		if (this.layoutPatterns.contains(type)) {
			this.layoutPatterns.remove(type);
		}
	}

	public void removeLayoutPattern(final Type type, final String patternName) {
		final Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null || v.isEmpty())
			return;
		for (int i = 0; i < v.size(); i++) {
			final LayoutPattern lp = v.get(i);
			if (lp.getName().equals(patternName)) {
				v.remove(lp);
				i--;
			}
		}
		this.layoutPatterns.put(type, v);
		// System.out.println("Layouter.removeLayoutPattern type: "+typeName+"
		// name: "+patternName+" count:"+v.size());
	}

	public Hashtable<Type, Vector<LayoutPattern>> getLayoutPatterns() {
		return this.layoutPatterns;
	}

	public void clearLayoutPatterns() {
		this.layoutPatterns.clear();
	}

	public Vector<LayoutPattern> getLayoutPatternsForType(final Type type) {
		Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null) {
			v = new Vector<LayoutPattern>();
			this.layoutPatterns.put(type, v);
		}
		return v;
	}

	public LayoutPattern getLayoutPatternForType(final Type type, final String patternName) {
		final Vector<LayoutPattern> v = this.layoutPatterns.get(type);
		if (v == null)
			return null;

		for (int i = 0; i < v.size(); i++) {
			final LayoutPattern lp = v.get(i);
			// System.out.println(lp+" "+lp.getName()+" "+patternName);
			if (lp.getName().equals(patternName))
				return lp;
		}
		return null;
	}

	public void setLayoutPatterns(final Hashtable<Type, Vector<LayoutPattern>> table) {
		this.layoutPatterns.clear();
		final Enumeration<Type> keys = table.keys();
		while (keys.hasMoreElements()) {
			final Type key = keys.nextElement();
			this.layoutPatterns.put(key, table.get(key));
		}
	}

	// private void delBendPoints(EdGraph eg){
	// Vector arcs = eg.getArcs();
	//		
	// EdArc arc;
	//		
	// for (int i=0;i<arcs.size();i++){
	// arc = (EdArc)arcs.get(i);
	// arc.
	// }
	// }

}

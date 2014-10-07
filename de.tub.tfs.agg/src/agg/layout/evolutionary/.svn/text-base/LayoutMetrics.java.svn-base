/**
 * 
 */
package agg.layout.evolutionary;

//import java.awt.Dimension;
import java.awt.geom.Line2D;
//import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdGraGra;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdNode;

/**
 * This class implements layout metrics.
 */
public class LayoutMetrics {

	private int epsilon;

	private int clusterplus, clusterminus;

	/**
	 * 
	 */
	public LayoutMetrics() {
		super();
		this.epsilon = 200; // 300
		this.clusterminus = 0;
		this.clusterplus = 0;
	}

	/**
	 * bestimmt die Anzahl der Kantenueberschneidungen
	 * 
	 * @param arcs
	 *            Kanten die auf eine Ueberschneidung zu pruefen sind
	 * @return Anzahl der Kantenueberscheidungen
	 */
	public int getArcArcIntersect(final List<EdArc> arcs) {
		int ret = 0;
		EdArc arc1, arc2;
//		LayoutNode lnodesource1, lnodetarget1, lnodesource2, lnodetarget2;
//		Point2D source, target;
		Line2D line1, line2;
		for (int i = 0; i < arcs.size(); i++) {// einmal die source und target
			// positionen fuer alle Arcs
			// aktualisieren
			arcs.get(i).getLArc().calcSourceTargetpos();
		}
		for (int i = 0; i < arcs.size() - 1; i++) {
			arc1 = arcs.get(i);
			if (!arc1.isLine()) {
				continue;
			} // bei kanten zu sich selbst nicht berechnen
			for (int j = i + 1; j < arcs.size(); j++) {
				arc2 = arcs.get(j);
				if (!arc2.isLine()) {
					continue;
				}// bei kanten zu sich selbst nicht berechnen
				if (arc1.getSource().equals(arc2.getSource())
						|| arc1.getSource().equals(arc2.getTarget())
						|| arc1.getTarget().equals(arc2.getSource())
						|| arc1.getTarget().equals(arc2.getTarget())) {
					// wenn die kanten direkte nachbarn sind, ueberscheiden sie
					// sich zwangslaeufig in dem gemeinsamen knoten und nur
					// dort! diese ueberschneidungen sind nicht relevant, werden
					// also nicht berechnet
					continue;
				}
				line1 = new Line2D.Double(arc1.getLArc().getSourcepos(), arc1
						.getLArc().getTargetpos());
				line2 = new Line2D.Double(arc2.getLArc().getSourcepos(), arc2
						.getLArc().getTargetpos());
				if (line1.intersectsLine(line2)) {
					ret++;
				}
			}
		}

		return ret;
	}

	/**
	 * Bestimmt die Anzahl der Ueberschneidungen von Kanten und Knoten
	 */
	public int getArcNodeIntersect(final List<EdNode> nodes, final List<EdArc> arcs) {
		int ret = 0;
		EdArc arc;
		EdNode node;
		Line2D line;
		Rectangle2D rect;

		for (int i = 0; i < arcs.size(); i++) {
			arc = arcs.get(i);
			if (!arc.isLine()) {
				continue;
			}// kanten zu sich selbst werden nicht betrachtet.
			arc.getLArc().calcSourceTargetpos();// position der endpunkte der
			// kante updaten
			for (int j = 0; j < nodes.size(); j++) {
				node = nodes.get(j);
				if (arc.getSource().equals(node)
						|| arc.getTarget().equals(node)) {
					// kanten schneiden zwangslaeufig ihre source- und
					// target-knoten, diese ueberschneidungen sind also
					// irrelevant.
					continue;
				}
				line = new Line2D.Double(arc.getLArc().getSourcepos(), arc
						.getLArc().getTargetpos());
				rect = new Rectangle2D.Double(node.getX(), node.getY(), node
						.getWidth(), node.getHeight());
				if (line.intersects(rect)) {
					ret++;
				}
			}
		}
		return ret;
	}

	/**
	 * bestimmt die Anzahl der Knotenueberschneidungen 
	 * 
	 * @param nodes
	 *            Knoten die untersucht werden sollen
	 * @param mark
	 *            wenn true, sollen die EdNodes(bzw.Layoutnodes), bei denen
	 *            ueberschneidungen gefunden worden sind, markiert werden
	 * @return Anzahl der Knotenueberschneidungen.
	 */
	public int getNodeIntersect(final List<EdNode> nodes, boolean mark) {
		final Hashtable<EdNode, EdNode> intersect = new Hashtable<EdNode, EdNode>();
		int ret = 0;

		EdNode node1, node2;
//		Rectangle2D rect1, rect2;

		for (int i = 0; i < nodes.size() - 1; i++) {// bis auf das letzte
			// element alle
			node1 = nodes.get(i);
			for (int j = i + 1; j < nodes.size(); j++) {// vom naechsten bis zu
				// letzen element,
				// dadurch keine
				// doppelberechnung
				node2 = nodes.get(j);
				if (intersect.get(node2) != null)
					continue;
				if (nodesIntersect(node1, node2)) {
					intersect.put(node2, node1);
					ret++;
					if (mark) {
						node1.getLNode().setOverlap();
						node2.getLNode().setOverlap();

						// node1.getLNode().setFrozen(false);
						// node2.getLNode().setFrozen(false);
						// if(node1.getLNode().isFrozenAsDefault())
						// node1.getLNode().setFrozenAsDefault(false);
						// if(node2.getLNode().isFrozenAsDefault())
						// node2.getLNode().setFrozenAsDefault(false);
					}
				}
			}
		}
		intersect.clear();
		return ret;
	}

	private boolean nodesIntersect(EdNode n1, EdNode n2) {
		boolean ret = false;
		// System.out.println("nodesIntersect:: w/h: "+ n1.getWidth()+" /
		// "+n1.getHeight());
		// System.out.println("nodesIntersect:: w/h: "+ n2.getWidth()+" /
		// "+n2.getHeight());
		int w1 = n1.getWidth();
		int h1 = n1.getHeight();
		int x1 = n1.getLNode().getAkt().x - (w1 + 1) / 2;
		int y1 = n1.getLNode().getAkt().y - (h1 + 1) / 2;

		int w2 = n2.getWidth();
		int h2 = n2.getHeight();
		int x2 = n2.getLNode().getAkt().x - (w2 + 1) / 2;
		int y2 = n2.getLNode().getAkt().y - (h2 + 1) / 2;

		Rectangle2D rect1 = new Rectangle2D.Double(x1, y1, w1, h1);
		Rectangle2D rect2 = new Rectangle2D.Double(x2, y2, w2, h2);
		if (rect1.intersects(rect2)) {
			ret = true;
			// System.out.println("nodesIntersect:: "+rect1+" "+rect2);
			// System.out.println("nodesIntersect:: "+n1.getTypeName()+"
			// "+n2.getTypeName());
		}
		return ret;
	}

	public int getOverlappingNode(Vector<LayoutNode> lnodes, int index) {
		int ret = -1;
		LayoutNode node1, node2;
		node1 = lnodes.get(index);
		for (int i = 0; i < lnodes.size(); i++) {
			if (i == index) {
				continue;
			}
			node2 = lnodes.get(i);
			if (nodesIntersect(node1.getEdNode(), node2.getEdNode())) {
				ret = i;
				break;
			}
		}
		return ret;
	}

	/**
	 * bestimmt alle Arten von ueberschneidungen und gibt die ergebnisse
	 * gewichtet zurueck. eine Knoten-Kanten Ueberschneidung wiegt doppelt so
	 * schwer wie eine Kanten-Kantenueberschneidung, eine
	 * Knoten-Knotenueberschneidung 3 mal so schwer.
	 * 
	 * @return gewichteter wert aller Ueberschneidungsarten
	 */
	public int getOverallIntersect(final List<EdNode> nodes, final List<EdArc> arcs) {
		int ret = 0;

		ret += getArcArcIntersect(arcs);
		ret += getArcNodeIntersect(nodes, arcs) * 2;
		ret += getNodeIntersect(nodes, false) * 3;

		return ret;
	}

	// not used now
	/*
	private int getspaceusage(EdGraph eg) {
		Dimension dim = eg.getGraphDim();
		int ret = 0;
		Vector<EdNode> nodes = eg.getNodes();
		int parts = (int) Math.floor(Math.sqrt(nodes.size()));
		System.out.println("Parts: " + parts + "    dim x: " + dim.width
				+ "    dim y: " + dim.height);
		ret = parts * parts;
		EdNode node;
		Rectangle2D partrect;
		for (int i = 0; i < parts; i++) {
			for (int j = 0; j < parts; j++) {
				// TODO: hier ist noch irgendwas falsch, hin und wieder gibts ne
				// NullPointerException!!! rausfinden warum und beseitigen
				partrect = new Rectangle2D.Double(i * (dim.width / parts), j
						* (dim.height / parts), dim.width / parts, dim.height
						/ parts);
				for (int n = 0; n < nodes.size(); n++) {
					node = nodes.get(n);
					if (partrect.contains(node.getLNode().getAkt())) {
						// wenn der linke obere eckpunkt des knotens in dem
						// bereich von partrect sind, wird der returnwert um
						// eins reduziert und fuer diesen bereich keine weiteren
						// knoten betrachtet
						ret--;
						break;
					}
				}
			}
		}
		return ret;
	}
*/
	
	
	/**
	 * gibt die durchschnittliche Abweichung der tatsaechlichen zu den
	 * bevorzugten Laengen aller Kanten in eg
	 * 
	 * @param arcs
	 *            Kanten deren Laengen untersucht werden soll
	 * @return durchschnittliche abweichung der aller Kantenlaengen
	 *         real--bevorzugt
	 */
	public int getAverageArcLengthDeviation(final List<EdArc> arcs) {
		int ret = 0;
		LayoutArc larc;
		int sum = 0;

		for (int i = 0; i < arcs.size(); i++) {
			larc = arcs.get(i).getLArc();
			larc.calcAktLength();
			sum += Math.abs(larc.getPrefLength() - larc.getAktLength());
		}

		if (arcs.size() != 0) {
			ret = sum / arcs.size();
		}

		return ret;
	}

	// diferenzmetriken:
	/**
	 * bestimmt die durchschnittliche Positionsaenderung aller Nodes in
	 * newedgraph im vergleich zu ihrer Position in oldedgraph.
	 * 
	 * es werden nur nodes betrachtet die in beiden vectoren enthalten sind
	 * Dabei werden die x und y werte der abweichung addiert.
	 * 
	 * @param oldednodes
	 *            Knoten vor der aktuellen Transformation
	 * @param newednodes
	 *            Knoten nach der aktuellen Transformation und dem erneuerten
	 *            Layout.
	 */
	public int getAverageNodeMove(final List<EdNode> oldednodes, final List<EdNode> newednodes) {
		int ret = 0;
		LayoutNode newlnode;
		EdNode oldednode, newednode;

		int sum = 0;
		int oldindex;

		for (int i = 0; i < newednodes.size(); i++) {
			newednode = newednodes.get(i);
			newlnode = newednode.getLNode();
			oldindex = newednode.isInVectorByBasisNode(oldednodes);
			if (oldindex != -1) {
				oldednode = oldednodes.get(oldindex);
				sum += Math.abs(newlnode.getAkt().x - oldednode.getX());
				sum += Math.abs(newlnode.getAkt().y - oldednode.getY());
			}
		}
		ret = sum / newednodes.size();
		return ret;
	}

	public float getSingleDistance(int nodes, int arcs, int nodeOverlapping,
			int arcNodeOverlapping, int arcArcOverlapping) {
		float B = nodes;
		float C = arcs;
		float D = nodeOverlapping;
		float E = arcNodeOverlapping;
		float F = arcArcOverlapping;
		float singleDistance = B / (D + 1) + (B + C) / (E + 1) + C / (F + 1);
		return singleDistance;
	}

	public float getMentalDistance(int nodes, int arcs, int movementsOfNodes,
			int movementsOfArcs) {
		float B = nodes;
		float C = arcs;
		float H = movementsOfNodes;
		float G = movementsOfArcs;
		float mentalDistance = H / B + G / C;
		return mentalDistance;
	}

	public float getLayoutQuality(float singleDistance, float mentalDistance) {
		float lquality = singleDistance - mentalDistance;
		return lquality;
	}

	public void calcClusterDiffs(EdGraph eg) {
		Vector<EdNode> nodes = eg.getNodes();
		EdNode node;
		Vector<Integer> oldcluster, newcluster;
		int sumplus = 0, summinus = 0;
//		int oldid, newid;
		for (int i = 0; i < nodes.size(); i++) {
			node = nodes.get(i);
			oldcluster = node.getOldCluster();
			newcluster = node.getCluster();
			if (oldcluster == null) {
				sumplus += newcluster.size();
			} else {
				for (int j = 0; j < oldcluster.size(); j++) {
					if (newcluster.indexOf(oldcluster.get(j)) == -1) {
						summinus++;
					}
				}
				for (int j = 0; j < newcluster.size(); j++) {
					if (oldcluster.indexOf(newcluster.get(j)) == -1) {
						sumplus++;
					}
				}
			}
		}
		this.clusterminus = summinus / nodes.size();
		this.clusterplus = sumplus / nodes.size();
	}

	public int getClusterMinus() {
		return this.clusterminus;
	}

	public int getClusterPlus() {
		return this.clusterplus;
	}

	public int getEpsilon() {
		return this.epsilon;
	}

	public void setEpsilon(int e) {
		this.epsilon = e;
	}

	public int getPatternMistakes(EdGraph eg) {
		if (eg.getGraGra() == null)
			return 0;
		
		int ret = 0;
		EdGraGra edgra = eg.getGraGra();
		Vector<EdArc> arcs = eg.getArcs();
		EdArc arc;
		LayoutPattern lp;
		EdNode source, target;
		int sum = 0;
		int diff = 0;

		for (int i = 0; i < arcs.size(); i++) {
			arc = arcs.get(i);
			Vector<LayoutPattern> paterns = edgra.getLayoutPatternsForType(arc
					.getType().getBasisType());
			for (int j = 0; j < paterns.size(); j++) {
				lp = paterns.get(j);
				if (lp != null && lp.isEdgePattern()) {
					source = (EdNode) arc.getSource();
					target = (EdNode) arc.getTarget();

					if (lp.isXOffset()) {
						diff = target.getLNode().getAkt().x
								- source.getLNode().getAkt().x;
						if (lp.getOffset() > 0) {// target.x sollte groesser
							// als source.x sein
							if (!(diff > 0)) {// ist aber nicht so
								sum++;
							}
						} else {// offset <=0 target.x also kleiner als source.x
							if (diff > 0) { // ist nicht so
								sum++;
							}
						}
					} else {
						diff = target.getLNode().getAkt().y
								- source.getLNode().getAkt().y;
						if (lp.getOffset() > 0) {// target.y sollte groesser
							// als source.y sein
							if (!(diff > 0)) {// ist aber nicht so
								sum++;
							}
						} else {// offset <=0 target.y also kleiner als source.y
							if (diff > 0) { // ist nicht so
								sum++;
							}
						}
					}
				}
			}
		}
		ret = sum;
		return ret;
	}

}

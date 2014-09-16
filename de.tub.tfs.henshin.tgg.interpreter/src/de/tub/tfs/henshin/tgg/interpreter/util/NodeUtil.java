/*******************************************************************************
 *******************************************************************************/

package de.tub.tfs.henshin.tgg.interpreter.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;

/**
 * The Class Nodeutil Holds many helpful static functions for operating on
 * nodes.
 */
public class NodeUtil {

	private static final String EXCEPTION_NODE_IS_NOT_TNODE = "Triple component of node cannot be determined, because it is not of type TNode (node in a triple graph).";

	/**
	 * get the mapping in rule of given node of rhs
	 * 
	 * @param rhsNode
	 * @return
	 */
	public static Mapping getNodeMapping(Node rhsNode) {
		EList<Mapping> mappingList = rhsNode.getGraph().getRule().getMappings();

		for (Mapping m : mappingList) {
			if (m.getImage() == rhsNode) {
				return m;
			}
		}
		return null;
	}

	/**
	 * checks if given node has a nac mapping
	 * 
	 * @param node
	 * @return
	 */
	public static Boolean hasNodeNacMapping(Node node) {
		if (node == null || node.getGraph() == null)
			return false;
		Formula formula = node.getGraph().getFormula();

		if (formula != null) {
			TreeIterator<EObject> iter = node.getGraph().getFormula()
					.eAllContents();

			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof NestedCondition) {
					NestedCondition nc = (NestedCondition) o;
					for (Mapping m : nc.getMappings()) {
						if (m.getOrigin() == node) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * get nac mapping of given node
	 * 
	 * @param nc
	 *            the nested condition with all mappings
	 * @param node
	 * @return (returns null if there's no mapping)
	 */
	public static Mapping getNodeNacMapping(NestedCondition nc, Node node) {

		EList<Mapping> list = nc.getMappings();
		for (Mapping m : list) {
			if (m.getImage() == node) {
				return m;
			}
		}

		return null;
	}

	/**
	 * searches all mappings belongs to given node
	 * 
	 * @param rhsNode
	 * @return a set of Mappings belongs to the given rhsNode (empty list if
	 *         there are no mapping)
	 */
	public static List<Mapping> getNodeNacMappings(Node rhsNode) {
		List<Mapping> nacMappings = new ArrayList<Mapping>();
		Mapping ruleMapping = RuleUtil.getRHSNodeMapping(rhsNode);

		if (ruleMapping != null) {
			Node lhsNode = ruleMapping.getOrigin();
			Formula formula = lhsNode.getGraph().getFormula();
			if (formula != null) {
				TreeIterator<EObject> iter = ruleMapping.getOrigin().getGraph()
						.getFormula().eAllContents();

				while (iter.hasNext()) {
					EObject o = iter.next();
					if (o instanceof NestedCondition) {
						NestedCondition nc = (NestedCondition) o;
						for (Mapping m : nc.getMappings()) {
							if (m.getOrigin() == lhsNode) {
								nacMappings.add(m);
							}
						}
					}
				}
			}
		}

		return nacMappings;
	}

	public static void deleteNodeNacMapping(Node node) {

	}

	/**
	 * find all nodeLayouts to specific EPackage
	 * 
	 * @param p
	 *            EPackage for source, target oder correspondence
	 * @param g
	 *            Graph containing the nodes
	 * @return set of nodes belonging to EPackage p
	 */
	public static Set<Node> getNodes(EPackage p, Graph g) {
		Set<Node> set = new HashSet<Node>();
		if (p != null) {
			for (Node n : g.getNodes()) {
				if (p.eContents().contains(n.getType())) {
					set.add(n);
				}
			}
		}
		return set;
	}

	/**
	 * find all nodeLayouts to specified list of EPackages
	 * 
	 * @param p
	 *            EPackage for source, target oder correspondence
	 * @param g
	 *            Graph containing the nodes
	 * @return set of nodes belonging to EPackage p
	 */
	public static Set<Node> getNodes(List<EPackage> pkgs, Graph g) {
		Set<Node> nodes = new HashSet<Node>();
		for (EPackage p : pkgs) {
			nodes.addAll(getNodes(p, g));
		}
		return nodes;
	}

	/**
	 * checks whether a specific EClass is a source type in given layoutSystem
	 * 
	 * @param tgg
	 *            the layoutSystem
	 * @param type
	 *            the EClass for check
	 * @return true if specific EClass is a source type
	 */
	public static boolean isSourceClass(TGG tgg, EClass c) {
		return TripleComponent.SOURCE == TggUtil.getEObjectTripleComponent(tgg,
				c);
	}

	/**
	 * Checks if a node is a soruce node.
	 * 
	 * @param node
	 * @return true if it is a source node, else false
	 */
	public static boolean isSourceNode(TNode node) {
		return (TripleComponent.SOURCE == node.getComponent());
	}

	/**
	 * Checks if a node is a correspondence node.
	 * 
	 * @param node
	 * @return true if it is a correspondence node, else false
	 */
	public static boolean isCorrespondenceNode(TNode node) {
		return (TripleComponent.CORRESPONDENCE == node.getComponent());
	}

	/**
	 * Checks if a node is a target node.
	 * 
	 * @param node
	 * @return true if it is a target node, else false
	 */
	public static boolean isTargetNode(TNode node) {
		return (TripleComponent.TARGET == node.getComponent());
	}

	/**
	 * checks whether a specific EClass is a target type in given layoutSystem
	 * 
	 * @param tgg
	 *            the layoutSystem
	 * @param type
	 *            the EClass for check
	 * @return true if specific EClass is a target type
	 */
	public static boolean isTargetClass(TGG tgg, EClass c) {
		return TripleComponent.TARGET == TggUtil.getEObjectTripleComponent(tgg,
				c);
	}

	/**
	 * checks whether a specific EClass is a correspondence type in given
	 * layoutSystem
	 * 
	 * @param tgg
	 *            the layoutSystem
	 * @param type
	 *            the EClass for check
	 * @return true if specific EClass is a correspondence type
	 */
	public static boolean isCorrespondenceClass(TGG tgg, EClass c) {
		return TripleComponent.CORRESPONDENCE == TggUtil
				.getEObjectTripleComponent(tgg, c);
	}

	/**
	 * computes the triple component of a given graph node
	 * 
	 * @param node
	 *            the graph node to by analysed
	 * @return the triple component of the graph node
	 */
	public static TripleComponent getComponentFromPosition(TNode node) {
		if (node == null)
			return TripleComponent.SOURCE;
		TripleGraph tripleGraph = (TripleGraph) node.getGraph();
		// position is left of SC divider
		if (node.getX() <= tripleGraph.getDividerSC_X())
			return TripleComponent.SOURCE;
		// position is right of SC divider and left of CT divider
		else if (node.getX() <= tripleGraph.getDividerCT_X())
			return TripleComponent.CORRESPONDENCE;
		// position is (right of SC divider and) right of CT divider
		return TripleComponent.TARGET;
	}

	/**
	 * determines whether at least one package is loaded for each triple
	 * component
	 * 
	 * @param importedPkgs
	 * @return
	 */
	public static boolean isTypeGraphComplete(
			EList<ImportedPackage> importedPkgs) {
		boolean isSetSourceTG = false;
		boolean isSetCorrespondenceTG = false;
		boolean isSetTargetTG = false;
		ImportedPackage pkg;
		Iterator<ImportedPackage> iter = importedPkgs.iterator();
		while (iter.hasNext()) {
			pkg = iter.next();
			switch (pkg.getComponent()) {
			case SOURCE:
				isSetSourceTG = true;
			case CORRESPONDENCE:
				isSetCorrespondenceTG = true;
			case TARGET:
				isSetTargetTG = true;
			}
		}
		return (isSetSourceTG && isSetCorrespondenceTG && isSetTargetTG);

	}

	// returns whether the node is translated already in the LHS
	public static Boolean getNodeIsTranslated(Node node) {
		if (((TNode) node).getMarkerType() != null) {
			if (RuleUtil.Not_Translated_Graph.equals(((TNode) node)
					.getMarkerType()))
				// node is translated by the rule - it is not yet translated
				return false;
			else if (RuleUtil.Translated_Graph.equals(((TNode) node)
					.getMarkerType()))
				// node is context element - it is already translated
				return true;
		}
		// node is not marked with a relevant marker
		return null;
	}

	// returns true, if the node is marked with the "NEW" marker
	public static boolean isNew(Node rn) {
		return (((TNode) rn).getMarkerType() != null && ((TNode) rn)
				.getMarkerType().equals(RuleUtil.NEW));
	}

	public static TripleComponent guessTripleComponent(TNode node) {
		TGG tgg = null;
		Module m = TggUtil.getModuleFromElement(node);
		if (m instanceof TGG)
			tgg = (TGG) m;
		TripleComponent comp = guessTripleComponentRaw(node, 4,
				new HashSet<TNode>(), tgg);
		if (comp == null)
			comp = getComponentFromPosition(node);

		node.eSetDeliver(false);
		if (tgg == null)
			return comp;
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(
				tgg.getImportedPkgs(), comp);
		if (node.getType() != null
				&& NodeTypes.contains(node.getType().getEPackage(), pkgs)) {
			node.setComponent(comp);
		} else {
			pkgs = NodeTypes.getImportedPackagesOfComponent(
					tgg.getImportedPkgs(), TripleComponent.SOURCE);
			List<ImportedPackage> pkgt = NodeTypes
					.getImportedPackagesOfComponent(tgg.getImportedPkgs(),
							TripleComponent.TARGET);
			List<ImportedPackage> pkgc = NodeTypes
					.getImportedPackagesOfComponent(tgg.getImportedPkgs(),
							TripleComponent.CORRESPONDENCE);

			if (node.getType() != null && node.getType().getEPackage() != null) {
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgs)) {
					if (!NodeTypes.contains(node.getType().getEPackage(), pkgt)) {
						if (NodeTypes.contains(node.getType().getEPackage(),
								pkgc))
							comp = TripleComponent.CORRESPONDENCE;
					} else {
						comp = TripleComponent.TARGET;
					}
				} else {
					comp = TripleComponent.SOURCE;
				}
			}
			node.setComponent(comp);

		}
		node.eSetDeliver(true);
		return comp;
	}

	public static TripleComponent guessTripleComponentRaw(TNode node, TGG tgg) {
		return guessTripleComponentRaw(node, 4, new HashSet<TNode>(), tgg);
	}

	public static TripleComponent guessTripleComponentRaw(TNode node,
			int checkDeep, HashSet<TNode> sources, TGG tgg) {
		sources.add(node);
		if (tgg == null)
			return null;
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(
				tgg.getImportedPkgs(), TripleComponent.SOURCE);
		List<ImportedPackage> pkgt = NodeTypes.getImportedPackagesOfComponent(
				tgg.getImportedPkgs(), TripleComponent.TARGET);
		List<ImportedPackage> pkgc = NodeTypes.getImportedPackagesOfComponent(
				tgg.getImportedPkgs(), TripleComponent.CORRESPONDENCE);
		if (node.getComponent() != null) {
			return node.getComponent();
		}
		if (node.getType() != null && node.getType().getEPackage() != null) {
			if (!NodeTypes.contains(node.getType().getEPackage(), pkgs)) {
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgt)) {
					if (NodeTypes.contains(node.getType().getEPackage(), pkgc))
						return TripleComponent.CORRESPONDENCE;
				} else {
					return TripleComponent.TARGET;

				}
			} else {
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgt)
						&& !NodeTypes.contains(node.getType().getEPackage(),
								pkgc))
					return TripleComponent.SOURCE;
			}
		}
		if (checkDeep == 0)
			return null;
		TripleComponent c = null;
		List<Edge> outgoing = new LinkedList<Edge>();
		outgoing.addAll(node.getOutgoing());
		List<Edge> incoming = new LinkedList<Edge>();
		incoming.addAll(node.getIncoming());

		for (Edge edge : incoming) {
			if (sources.contains(edge.getSource()))
				continue;
			if (c != null) {
				if (TripleComponent.CORRESPONDENCE == c)
					c = guessTripleComponentRaw((TNode) edge.getSource(),
							checkDeep - 1, (HashSet<TNode>) sources.clone(),
							tgg);
				else {
					TripleComponent c2 = guessTripleComponentRaw(
							(TNode) edge.getSource(), checkDeep - 1,
							(HashSet<TNode>) sources.clone(), tgg);
					if (c != c2 && TripleComponent.CORRESPONDENCE != c2)
						return TripleComponent.CORRESPONDENCE;

				}

			} else {
				c = guessTripleComponentRaw((TNode) edge.getSource(),
						checkDeep - 1, (HashSet<TNode>) sources.clone(), tgg);
			}
		}
		if (c != null && c != TripleComponent.CORRESPONDENCE)
			return c;

		for (Edge edge : outgoing) {
			if (sources.contains(edge.getTarget()))
				continue;
			if (c != null) {
				if (c == TripleComponent.CORRESPONDENCE)
					c = guessTripleComponentRaw((TNode) edge.getTarget(),
							checkDeep - 1, (HashSet<TNode>) sources.clone(),
							tgg);
				else if (c != guessTripleComponentRaw((TNode) edge.getTarget(),
						checkDeep - 1, (HashSet<TNode>) sources.clone(), tgg))
					if (guessTripleComponentRaw((TNode) edge.getTarget(),
							checkDeep - 1, (HashSet<TNode>) sources.clone(),
							tgg) != null)
						return TripleComponent.CORRESPONDENCE;
			} else {
				c = guessTripleComponentRaw((TNode) edge.getTarget(),
						checkDeep - 1, (HashSet<TNode>) sources.clone(), tgg);
			}
		}

		if (TripleComponent.CORRESPONDENCE == c)
			return null;
		else
			return c;
	}

	/**
	 * Find the attribute with a specific type. Is just working when there is
	 * not more than one one type of attribute in a node.
	 * 
	 * @param graphNode
	 *            source node
	 * @param type
	 *            type of the attribute
	 * @return the corresponding attribute of graphNode
	 */
	public static Attribute findAttribute(Node graphNode, EAttribute type) {
		for (Attribute a : graphNode.getAttributes()) {
			if (a.getType() == type) {
				return a;
			}
		}
		return null;
	}

	public static Node getGraphNode(DomainSlot slot, EGraph graph) {
		return ((TggHenshinEGraph) graph).getObject2NodeMap().get(
				slot.getValue());
	}

	public static Node getGraphNode(EObject slot, EGraph graph) {
		return ((TggHenshinEGraph) graph).getObject2NodeMap().get(slot);
	}

	/**
	 * checks whether a node belongs to the source component
	 * 
	 * @param node
	 *            the graph node to be analysed
	 * @return true, if the node belongs to the source component
	 */
	public static boolean isSourceNodeByPosition(TNode node) {
		if (node == null)
			return false;
		// position has to be left of SC divider
		TripleGraph tripleGraph = (TripleGraph) node.getGraph();
		return node.getX() <= tripleGraph.getDividerSC_X();
	}

}

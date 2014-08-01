package de.tub.tfs.henshin.tgg.interpreter;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * The Class Nodeutil
 * Holds many helpful static functions for operating on nodes.
 */
public class NodeUtil {
	
	private static final String EXCEPTION_NODE_IS_NOT_TNODE = "Triple component of node cannot be determined, because it is not of type TNode (node in a triple graph).";

	



	
	/**
	 * get the mapping in rule of given node of rhs
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
	 * @param node
	 * @return
	 */
	public static Boolean hasNodeNacMapping(Node node) {
		if (node==null || node.getGraph() == null)
			return false;
		Formula formula = node.getGraph().getFormula();
		
		if (formula != null) {
			TreeIterator<EObject> iter = node.getGraph().getFormula().eAllContents();
			
			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof NestedCondition) {
					NestedCondition nc = (NestedCondition)o;
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
	 * @param nc the nested condition with all mappings
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
	 * @param rhsNode
	 * @return a set of Mappings belongs to the given rhsNode (empty list if there are no mapping)
	 */
	public static List<Mapping> getNodeNacMappings(Node rhsNode) {
		List<Mapping> nacMappings = new ArrayList<Mapping>();
		Mapping ruleMapping = RuleUtil.getRHSNodeMapping(rhsNode);
		
		if (ruleMapping != null) {
			Node lhsNode = ruleMapping.getOrigin();
			Formula formula = lhsNode.getGraph().getFormula();
			if (formula !=null) {
				TreeIterator<EObject> iter = ruleMapping.getOrigin().getGraph().getFormula().eAllContents();
	
				while (iter.hasNext()) {
					EObject o = iter.next();
					if (o instanceof NestedCondition) {
						NestedCondition nc = (NestedCondition)o;
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
	 * creates a new nodeLayout in given layoutSystem which is linked to given node
	 * @param node
	 * @param layoutSystem
	 * @return the created node layout
	 */
	public static NodeLayout createNodeLayout(Node node, TGG layoutSystem) {
		NodeLayout nodeLayout = TggFactory.eINSTANCE.createNodeLayout();
		nodeLayout.setNode(node);
		layoutSystem.getNodelayouts().add(nodeLayout);
		return nodeLayout;
	}

	/**
	 * find node layout linked to given node in layoutSystem
	 * @param node 
	 * @param tgg the layoutSystem
	 * @return the nodeLyout which belongs to given node
	 */
	public static NodeLayout findNodeLayout(Node node, TGG tgg) {
		for (NodeLayout nodeLayout : tgg.getNodelayouts()) {
			if (nodeLayout.getNode() == node || nodeLayout.getLhsnode() == node) {
				return nodeLayout;
			}
		}
		return null;
	}
	

	
//	/**
//	 * find all nodeLayouts to specific EPackage
//	 * @param tgg the layoutSystem
//	 * @param p EPackage for source, target oder correspondence
//	 * @return a set node layouts with all nodeLayouts belongs to EPackage p
//	 */
//	public static Set<NodeLayout> getNodeLayouts(TGG tgg, EPackage p) {
//		Set<NodeLayout> set = new HashSet<NodeLayout>();
//		EList<NodeLayout> l = tgg.getNodelayouts();
//		if (p != null) {
//			for (NodeLayout nl : l) {
//				if (p.eContents().contains(nl.getNode().getType())) {
//					set.add(nl);
//				}
//			}
//		}
//		return set;
//	}

	/**
	 * find all nodeLayouts to specific EPackage
	 * @param p EPackage for source, target oder correspondence
	 * @param g Graph containing the nodes
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
	 * @param p EPackage for source, target oder correspondence
	 * @param g Graph containing the nodes
	 * @return set of nodes belonging to EPackage p
	 */
	public static Set<Node> getNodes(List<EPackage> pkgs, Graph g) {
		Set<Node> nodes = new HashSet<Node>();
		for (EPackage p: pkgs){
			nodes.addAll(getNodes(p, g));
		}
		return nodes;
	}

	
	
	/**
	 * checks whether a specific EClass is a source type in given layoutSystem
	 * @param tgg the layoutSystem
	 * @param type the EClass for check
	 * @return true if specific EClass is a source type
	 */
	public static boolean isSourceClass(Module m, EClass c) {
		return TggUtil.SOURCE.equals(TggUtil.getEObjectTripleComponent(m, c));
	}
	
//	/**
//	 * checks whether a node belongs to the source component
//	 * @param node the graph node to be analysed
//	 * @return true, if the node belongs to the source component
//	 */
//	public static boolean isSourceNode(TNode node, TGG tgg) {
//		if (node==null) return false;
//		return guessTripleComponent(node, tgg) == TripleComponent.SOURCE;
//		// position has to be left of SC divider
//		//TripleGraph tripleGraph =(TripleGraph) node.getGraph();
//
//		//return node.getX() <= tripleGraph.getDividerSC_X();
//	}

	/**
	 * Checks if a node is a soruce node.
	 * @param node
	 * @return true if it is a source node, else false
	 */
	public static boolean isSourceNode(Node node) {
		return (TggUtil.SOURCE.equals(TggUtil.getNodeTripleComponent(node)));
	}
	
	
//	/**
//	 * checks whether a node belongs to the correspondence component
//	 * @param node the graph node to be analysed
//	 * @return true, if the node belongs to the correspondence component
//	 */
//	public static boolean isCorrespondenceNode(TNode node, TGG tgg) {
//		if (node==null) return false;
//		// position has to be right of SC divider and left of CT divider
//		return guessTripleComponent(node, tgg) == TripleComponent.CORRESPONDENCE;
//		//TripleGraph tripleGraph =(TripleGraph) node.getGraph(); 
//		//return node.getX() >= tripleGraph.getDividerSC_X()
//		//		&& node.getX() <= tripleGraph.getDividerCT_X();
//	}

	/**
	 * Checks if a node is a correspondence node.
	 * @param node
	 * @return true if it is a correspondence node, else false
	 */
	public static boolean isCorrespondenceNode(Node node) {
		return (TggUtil.CORRESPONDENCE.equals(TggUtil.getNodeTripleComponent(node)));
	}
	
//	/**
//	 * checks whether a node belongs to the target component
//	 * @param node the graph node to be analysed
//	 * @return true, if the node belongs to the target component
//	 */
//	public static boolean isTargetNode(TNode node, TGG tgg) {
//		if (node==null) return false;
//		// position has to be right of CT divider
//
//		return guessTripleComponent(node, tgg) == TripleComponent.TARGET;
//		
//		
//		//TripleGraph tripleGraph =(TripleGraph) node.getGraph();
//
//		//return node.getX() >= tripleGraph.getDividerCT_X();
//	}
	
	/**
	 * Checks if a node is a target node.
	 * @param node
	 * @return true if it is a target node, else false
	 */
	public static boolean isTargetNode(Node node) {
		return (TggUtil.TARGET.equals(TggUtil.getNodeTripleComponent(node)));
	}


	
	
	private static void handleCastExceptionTNode() {
		System.out.println(EXCEPTION_NODE_IS_NOT_TNODE);		
	}


	/**
	 * computes the triple component of a given graph node
	 * @param node the graph node to by analysed
	 * @return the triple component of the graph node
	 */
	public static TripleComponent getComponent(TNode node) {
		if (node==null) return TripleComponent.SOURCE;
		TripleGraph tripleGraph =(TripleGraph) node.getGraph();
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
	 * determines whether at least one package is loaded for each triple component 
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

	/**
	 * checks whether a specific EClass is a target type in given layoutSystem
	 * @param tgg the layoutSystem
	 * @param type the EClass for check
	 * @return true if specific EClass is a target type
	 */
	public static boolean isTargetClass(Module m, EClass c) {
		return TggUtil.TARGET.equals(TggUtil.getEObjectTripleComponent(m, c));
	}

	/**
	 * checks whether a specific EClass is a correspondence type in given layoutSystem
	 * @param tgg the layoutSystem
	 * @param type the EClass for check
	 * @return true if specific EClass is a correspondence type
	 */
	public static boolean isCorrespondenceClass(Module m, EClass c) {
		return TggUtil.CORRESPONDENCE.equals(TggUtil.getEObjectTripleComponent(m, c));
	}
	

	
	




	public static void refreshLayout(TNode node, NodeLayout nodeLayout) {
		
			computeAndCreateLayout(node,nodeLayout);
		
		
	}

	private static void computeAndCreateLayout(TNode node, NodeLayout nodeLayout) {
		// marker value is not available in ruleAttributeRHS, thus compute it
		if (nodeLayout == null) { // no layout is found
			// store coordinates (0,0)
			node.setX(0);
			node.setY(0);
		} else { // layout is found
			// store coordinates
			node.setX(nodeLayout.getX());
			node.setY(nodeLayout.getY());
		}
		return;
		
	}

	// returns whether the node is translated already in the LHS
	public static Boolean getNodeIsTranslated(Node node) {
		if (((TNode) node).getMarkerType() != null) {
			if (RuleUtil.Not_Translated_Graph.equals(((TNode) node).getMarkerType()))
				// node is translated by the rule - it is not yet translated
				return false;
			else if (RuleUtil.Translated_Graph.equals(((TNode) node).getMarkerType()))
				// node is context element - it is already translated
				return true;
		} 
		// node is not marked with a relevant marker
		return null;
	}
	
	// returns true, if the node is marked with the "NEW" marker
	public static boolean isNew(Node rn) {
		return (((TNode) rn).getMarkerType()!=null && ((TNode) rn).getMarkerType().equals(RuleUtil.NEW));
	}
	
	
	public static TripleComponent guessTripleComponent(TNode node, TGG tgg){
		TripleComponent comp = guessTripleComponentRaw(node,4,new HashSet<TNode>(), tgg);
		if (comp == null)
			comp = getComponent(node);
		
		
		
		node.eSetDeliver(false);
		if (tgg == null)
			return comp;
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),comp);
		if (node.getType() != null && NodeTypes.contains(node.getType().getEPackage(), pkgs)){
			node.setComponent(comp);
		} else {
			pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.SOURCE);
			List<ImportedPackage> pkgt = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.TARGET);
			List<ImportedPackage> pkgc = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.CORRESPONDENCE);
			
			
			if (node.getType() != null &&  node.getType().getEPackage() != null){
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgs)){
					if (!NodeTypes.contains(node.getType().getEPackage(), pkgt)){
						if (NodeTypes.contains(node.getType().getEPackage(), pkgc))
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
	
	
	public static TripleComponent guessTripleComponentRaw(TNode node, TGG tgg){
		return guessTripleComponentRaw(node, 4,new HashSet<TNode>(), tgg);
	}
		
		
	public static TripleComponent guessTripleComponentRaw(TNode node,int checkDeep,HashSet<TNode> sources, TGG tgg){
		sources.add(node);
		if (tgg == null)
			return null;
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.SOURCE);
		List<ImportedPackage> pkgt = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.TARGET);
		List<ImportedPackage> pkgc = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.CORRESPONDENCE);
		if (node.getComponent() != null){
			return node.getComponent();
		}
		if (node.getType() != null && node.getType().getEPackage() != null){
			if (!NodeTypes.contains(node.getType().getEPackage(), pkgs)){
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgt)){
					if (NodeTypes.contains(node.getType().getEPackage(), pkgc))
						return TripleComponent.CORRESPONDENCE;
				} else {
						return TripleComponent.TARGET;
					
				}
			} else {
				if (!NodeTypes.contains(node.getType().getEPackage(), pkgt) && !NodeTypes.contains(node.getType().getEPackage(), pkgc))
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
			if (c != null){
				if (TripleComponent.CORRESPONDENCE.equals(c))
					c = guessTripleComponentRaw((TNode) edge.getSource(),checkDeep-1,(HashSet<TNode>) sources.clone(), tgg);
				else {
					TripleComponent c2 = guessTripleComponentRaw((TNode) edge.getSource(),checkDeep-1,(HashSet<TNode>) sources.clone(), tgg);
					if (!c.equals(c2) && !TripleComponent.CORRESPONDENCE.equals(c2))
						return TripleComponent.CORRESPONDENCE;
				
				}
					
			} else {
				c = guessTripleComponentRaw((TNode) edge.getSource(),checkDeep-1,(HashSet<TNode>) sources.clone(), tgg);
			}
		}
		if (c != null && !c.equals(TggUtil.CORRESPONDENCE))
			return c;
		
		for (Edge edge : outgoing) {
			if (sources.contains(edge.getTarget()))
				continue;
			if (c != null){
				if (c.equals(TggUtil.CORRESPONDENCE))
					c = guessTripleComponentRaw((TNode) edge.getTarget(),checkDeep - 1,(HashSet<TNode>) sources.clone(), tgg);
				else if (c != guessTripleComponentRaw((TNode) edge.getTarget(),checkDeep - 1,(HashSet<TNode>) sources.clone(), tgg))
					if (guessTripleComponentRaw((TNode) edge.getTarget(),checkDeep - 1,(HashSet<TNode>) sources.clone(), tgg) != null)
						return TripleComponent.CORRESPONDENCE;
			} else {
				c = guessTripleComponentRaw((TNode) edge.getTarget(),checkDeep - 1,(HashSet<TNode>) sources.clone(), tgg);
			}
		}
		
		if (TggUtil.CORRESPONDENCE.equals(c))
			return null;
		else
			return c;
	}
	
	/**
	 * Find the attribute with a specific type. Is just working 
	 * when there is not more than one one type of attribute in a node.
	 * @param graphNode source node
	 * @param type type of the attribute
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
		return ((TggHenshinEGraph)graph).getObject2NodeMap().get(slot.getValue());
	}
	
	public static Node getGraphNode(EObject slot, EGraph graph) {
		return ((TggHenshinEGraph)graph).getObject2NodeMap().get(slot);
	}

	
	/**
	 * checks whether a node belongs to the source component
	 * @param node the graph node to be analysed
	 * @return true, if the node belongs to the source component
	 */
	public static boolean isSourceNodeByPosition(TNode node) {
		if (node==null) return false;
		//return guessTripleComponent(node) == TripleComponent.SOURCE;
		// position has to be left of SC divider
		TripleGraph tripleGraph =(TripleGraph) node.getGraph();
		return node.getX() <= tripleGraph.getDividerSC_X();
	}
	
	public static String getTripleComponent(Node n){
		Node node=n;
		if (RuleUtil.isLHSNode(n))
			node= RuleUtil.getRHSNode(n);
		
		return TggUtil.getNodeTripleComponent(node);
	}

	// checks whether the node is annotated with a triple component information
	public static boolean hasTripleComponentAnnotation(Node node){
		Iterator<Annotation> it = node.getAnnotations().iterator();
		while(it.hasNext()){
			if(TggUtil.COMPONENT_MARKER_KEY.equals(it.next().getKey()))
				return true;
		}
		return false;
	}
	
	// copies the triple component and marker annotation from sourceNode to targetNode 
	public static void initialiseAnnotations(TNode sourceNode,
			TNode targetNode) {

		String marker = "";
		String tripleComponent = TggUtil.getNodeTripleComponent(sourceNode);
		
		TggUtil.initialiseAnnotations(targetNode, marker, tripleComponent);
		
	


		
	}


	
}

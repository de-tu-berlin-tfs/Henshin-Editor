package de.tub.tfs.henshin.tggeditor.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * The Class Nodeutil
 * Holds many helpful static functions for operating on nodes.
 */
public class NodeUtil {
	
	/**
	 * Gets the layout system which holds the given EObject
	 * 
	 * @param eobject the eobject
	 * @return the layout system
	 */
	public static TGG getLayoutSystem(EObject eobject) {
		if(!(IDUtil.getHostEditor(eobject) instanceof TreeEditor)) return null;
		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor(eobject);
		if(editor == null) return null;
		return editor.getLayout();
	}

	/**
	 * Get the node layout of given node. If there's no node layout it creates one
	 * @param node
	 * @return the node layout linked to given node
	 */
	public static NodeLayout getNodeLayout(Node node) {
		TGG layoutModel = getLayoutSystem(node);
		if(layoutModel == null)return null;
		NodeLayout result = null;
		if (layoutModel != null) {
			result = findNodeLayout(node, layoutModel);
			if (result==null){
				result = createNodeLayout(node,layoutModel);
			}
		}
		return result;
	}
	
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
	private static NodeLayout createNodeLayout(Node node, TGG layoutSystem) {
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
	protected static NodeLayout findNodeLayout(Node node, TGG tgg) {
		for (NodeLayout nodeLayout : tgg.getNodelayouts()) {
			if (nodeLayout.getNode() == node || nodeLayout.getLhsnode() == node) {
				return nodeLayout;
			}
		}
		return null;
	}
	
	/**
	 * find node layout linked to given node 
	 * @param node 
	 * @return the nodeLyout which belongs to given node
	 */
	protected static NodeLayout findNodeLayout(Node node) {
		TGG tgg = getLayoutSystem(node);
		return findNodeLayout(node, tgg);
	}
	
	/**
	 * find all nodeLayouts to specific EPackage
	 * @param tgg the layoutSystem
	 * @param p EPackage for source, target oder correspondence
	 * @return a set node layouts with all nodeLayouts belongs to EPackage p
	 */
	public static Set<NodeLayout> getNodeLayouts(TGG tgg, EPackage p) {
		Set<NodeLayout> set = new HashSet<NodeLayout>();
		EList<NodeLayout> l = tgg.getNodelayouts();
		if (p != null) {
			for (NodeLayout nl : l) {
				if (p.eContents().contains(nl.getNode().getType())) {
					set.add(nl);
				}
			}
		}
		return set;
	}

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
	 * @deprecated
	 */
	public static boolean isSourceNode(TGG tgg, EClass type) {
		
		if(!isTypeGraphComplete(tgg.getImportedPkgs()))
				return true;
		else{
			List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.SOURCE);
			if ( // EcorePackage.eINSTANCE.getEPackage().eContents().contains(type)
				//(!NodeTypes.getNodeTypesOfEPackages(tgg.getCorrespondencePkgs(), true).contains(type) && !NodeTypes.getNodeTypesOfEPackages(tgg.getTargetPkgs(), true).contains(type)) 
				// ||
				 NodeTypes.getNodeTypesOfImportedPackages(pkgs, true).contains(type))
			return true;
		}

		return false;
	}
	
	/**
	 * checks whether a node belongs to the source component
	 * @param node the graph node to be analysed
	 * @return true, if the node belongs to the source component
	 */
	public static boolean isSourceNode(Node node) {
		if (node==null) return false;
		// position has to be left of SC divider
		GraphLayout divSC = GraphUtil.getGraphLayout(node.getGraph() , true);
		return node.getX() <= divSC.getDividerX();
	}
	
	/**
	 * checks whether a node belongs to the correspondence component
	 * @param node the graph node to be analysed
	 * @return true, if the node belongs to the correspondence component
	 */
	public static boolean isCorrespondenceNode(Node node) {
		if (node==null) return false;
		// position has to be right of SC divider and left of CT divider
		GraphLayout[] graphlayouts = GraphUtil.getGraphLayouts(node.getGraph());
		GraphLayout divSC= graphlayouts[0];
		GraphLayout divCT= graphlayouts[1];
		return node.getX() >= divSC.getDividerX()
				&& node.getX() <= divCT.getDividerX();
	}

	/**
	 * checks whether a node belongs to the target component
	 * @param node the graph node to be analysed
	 * @return true, if the node belongs to the target component
	 */
	public static boolean isTargetNode(Node node) {
		if (node==null) return false;
		// position has to be right of CT divider
		GraphLayout divCT = GraphUtil.getGraphLayout(node.getGraph() , false);
		return node.getX() >= divCT.getDividerX();
	}

	/**
	 * computes the triple component of a given graph node
	 * @param node the graph node to by analysed
	 * @return the triple component of the graph node
	 */
	public static TripleComponent getComponent(Node node) {
		if (node==null) return TripleComponent.SOURCE;
		GraphLayout[] graphlayouts = GraphUtil.getGraphLayouts(node.getGraph());
		GraphLayout divSC= graphlayouts[0];
		GraphLayout divCT= graphlayouts[1];
		// position is left of SC divider
		if (node.getX() <= divSC.getDividerX())
			return TripleComponent.SOURCE;
		// position is right of SC divider and left of CT divider
		else if (node.getX() <= divCT.getDividerX())
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
	 * @deprecated
	 * @return true if specific EClass is a target type
	 */
	public static boolean isTargetNode(TGG tgg, EClass type) {
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.TARGET);
		if (NodeTypes.getNodeTypesOfImportedPackages(pkgs, true).contains(type))
		return true;
	return false;
	}
	
	/**
	 * checks whether a specific EClass is a correspondence type in given layoutSystem
	 * @param tgg the layoutSystem
	 * @param type the EClass for check
	 * @deprecated
	 * @return true if specific EClass is a correspondence type
	 */
	public static boolean isCorrespNode(TGG tgg, EClass type) {
		List<ImportedPackage> pkgs = NodeTypes.getImportedPackagesOfComponent(tgg.getImportedPkgs(),TripleComponent.CORRESPONDENCE);
		if (NodeTypes.getNodeTypesOfImportedPackages(pkgs, true).contains(type))
		return true;
	return false;
	}
	

	
	/**
	 * Searches the graph layout of divider. If dividerSC is true it searches for 
	 * source-correspondence divider, if its false it searches for the correspondence-target divider.
	 * @param graph which is linked to its dividers
	 * @param dividerSC (search SC or CT divider?)
	 * @return graph layout of searched divider
	 */
	private static GraphLayout getGraphLayout(Graph graph, boolean dividerSC) {
		TGG layoutSystem = getLayoutSystem(graph);
		if (layoutSystem != null) {
			for (GraphLayout graphLayout : layoutSystem.getGraphlayouts()) {
				if (graphLayout.getGraph() == graph) {
					if (dividerSC == graphLayout.isIsSC())
						return graphLayout;
				}
			}
		}
		return null;
	}
	
	/**
	 * correct position of a node (in nodeFigure and nodeLayout) in relation to its NodeGraphType
	 * and the divider positions, this correction generates no notifications
	 * @param nodeFigure the figure of the node
	 */
	public static void correctNodeFigurePosition(NodeFigure nodeFigure) {
		if(nodeFigure == null)return;
		Node node = nodeFigure.getNode();
		if(node == null || node.getX() == null || node.getY()==null) return;
		GraphLayout divSC = NodeUtil.getGraphLayout((Graph)node.eContainer(), true);
		GraphLayout divCT = NodeUtil.getGraphLayout((Graph)node.eContainer(), false);
		if(divSC == null || divCT == null)return;
		int divSCx = divSC.getDividerX();
		int divCTx = divCT.getDividerX();
		int width = nodeFigure.getBounds().width;
		int leftX = node.getX();
		int correctionValue = 0;
		TripleComponent type = NodeTypes.getTripleComponentFromNodeGraphType(NodeTypes.getNodeGraphType(node));
		
		if (type == TripleComponent.SOURCE) {
			if (leftX+width >= divSCx)
				correctionValue = divSCx - leftX - width - 5;
		}
		else if (type == TripleComponent.CORRESPONDENCE) {
			if (leftX <= divSCx)
				correctionValue = divSCx - leftX + 5;
			else if (leftX+width >= divCTx)
				correctionValue = divCTx - leftX - width - 5;
		}
		else if (type == TripleComponent.TARGET) {
			if (leftX <= divCTx)
				correctionValue = divCTx - leftX + 5;
		}
		if(correctionValue != 0) {
//		  nodeLayout.eSetDeliver(false);
		  node.setX(leftX + correctionValue);
//		  nodeLayout.eSetDeliver(true);
		  nodeFigure.setLocation(new Point(node.getX(), node.getY()));
		}
	}

	public static void refreshIsMarked(Node ruleNodeRHS) {
		if (ruleNodeRHS.getIsMarked() != null)
			return;
		else { // marker is not available, thus copy from layout model and
				// delete entry in layout model
			computeAndCreateIsMarked(ruleNodeRHS);
		}
	}

	private static void computeAndCreateIsMarked(Node ruleNodeRHS) {
		// marker value is not available in ruleAttributeRHS, thus compute it
		NodeLayout nodeLayout = findNodeLayout(ruleNodeRHS);
		if (nodeLayout == null) { // no layout is found
			// determine type of marker
//			Rule rule = ruleNodeRHS.getGraph().getContainerRule();
//			if (ModelUtil.getRuleLayout(rule)!=null)
//				ruleNodeRHS.setMarkerType(RuleUtil.Translated);
//			else
				ruleNodeRHS.setMarkerType(RuleUtil.NEW);

			// check for existing node in LHS
			Node lhsNode = RuleUtil
					.getLHSNode(ruleNodeRHS);
			if (lhsNode != null) {
				// node is preserved -> no marker
				ruleNodeRHS.setIsMarked(false);
			} else {
				// node is created -> add marker
				ruleNodeRHS.setIsMarked(true);
			}

		} else { // layout is found
			Boolean isTranslatedLHS = nodeLayout.getLhsTranslated();
			boolean isNew = nodeLayout.isNew();
			if (isTranslatedLHS == null) {
				ruleNodeRHS.setMarkerType(RuleUtil.NEW);
				ruleNodeRHS.setIsMarked(isNew);
			} else {
				ruleNodeRHS.setMarkerType(RuleUtil.Translated);
				ruleNodeRHS.setIsMarked(!isTranslatedLHS);
			}
		}
		// delete layout entry in layout model
		while (nodeLayout != null) {
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					nodeLayout);
			cmd.execute();
			// find possible duplicates of layout
			nodeLayout = findNodeLayout(ruleNodeRHS);
		}
		return;
	}

	public static void refreshLayout(Node node) {
		if (node.getX() != null && node.getY()!=null)
			return;
		else { // layout is not available, thus copy from layout model and
				// delete entry in layout model, if not needed
			computeAndCreateLayout(node);
		}
		
	}

	private static void computeAndCreateLayout(Node node) {
		// marker value is not available in ruleAttributeRHS, thus compute it
		NodeLayout nodeLayout = findNodeLayout(node);
		if (nodeLayout == null) { // no layout is found
			// store coordinates (0,0)
			node.setX(0);
			node.setY(0);
		} else { // layout is found
			// store coordinates
			node.setX(nodeLayout.getX());
			node.setY(nodeLayout.getY());
		}
		// delete layout entry in layout model, if there is no additional marker for a rule node
		if (nodeLayout != null && nodeLayout.getLhsTranslated()!=null) {
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					nodeLayout);
			cmd.execute();
			// find possible duplicates of layout
			nodeLayout = findNodeLayout(node);
		}
		return;
		
	}

	// returns whether the node is translated already in the LHS
	public static Boolean getNodeIsTranslated(Node node) {
		if(node.getMarkerType()!=null && node.getMarkerType().equals(RuleUtil.Translated))
			return !node.getIsMarked();
		else return null;
	}
	
}

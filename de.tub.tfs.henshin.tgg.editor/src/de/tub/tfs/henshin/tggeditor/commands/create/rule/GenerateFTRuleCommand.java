package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteFTRuleCommand;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.SendNotify;


/**
 * this command generates an forward translation rule from a given triple graph
 * grammar rule
 * 
 */
public class GenerateFTRuleCommand extends Command {

	private Rule oldRule;

	private Graph tRuleLhs;

	private TripleGraph tRuleRhs;

	private TRule tRule;

	private Rule newRule;

	private TGG tgg;

	private HashMap<Node, Node> oldLhsNodes2TLhsNodes;

	private HashMap<Node, Node> oldRhsNodes2TRhsNodes;

	private HashMap<Node, Node> oldNacNodes2TLhsNodes;

	private String prefix = "FT_";

	private int truleIndex;
	private int oldruleIndex;
	private boolean update = false;

	/**
	 * the constructor
	 * 
	 * @param rule
	 * @see tggeditor.create.rule.CreateRuleCommand
	 */
	public GenerateFTRuleCommand(Rule rule) {
		this.oldRule = rule;

		oldLhsNodes2TLhsNodes = new HashMap<Node, Node>();
		oldRhsNodes2TRhsNodes = new HashMap<Node, Node>();
		oldNacNodes2TLhsNodes = new HashMap<Node, Node>();
	}

	/**
	 * executes the command
	 */
	@Override
	public void execute() {

		tgg = NodeUtil.getLayoutSystem(oldRule);

		//
		for (TRule tr : tgg.getTRules()) {
			Module module = oldRule.getModule();
			this.truleIndex = tgg.getTRules().indexOf(tr);

			if (tr.getRule().getName().equals(prefix + oldRule.getName())) {
				// there is already a TRule for this rule -> delete the old one
				this.update = true;
				this.oldruleIndex = module.getUnits().indexOf(tr.getRule());
				DeleteFTRuleCommand deleteCommand = new DeleteFTRuleCommand(
						tr.getRule());
				deleteCommand.execute();
				break;
			}
		}

		// if rule is empty: nothing to do, possibly warning

		// create new rule
		newRule = HenshinFactory.eINSTANCE.createRule();
		newRule.setName(prefix + oldRule.getName());

		// create new RHS graph
		tRuleRhs = TggFactory.eINSTANCE.createTripleGraph();
		tRuleRhs.setName(oldRule.getRhs().getName());
		newRule.setRhs(tRuleRhs);

		// create new LHS graph
		tRuleLhs = HenshinFactory.eINSTANCE.createGraph();
		tRuleLhs.setName(oldRule.getLhs().getName());
		newRule.setLhs(tRuleLhs);

		// the TRule
		tRule = TggFactory.eINSTANCE.createTRule();
		tRule.setRule(newRule);
		tRule.setType(RuleUtil.TGG_FT_RULE);

		// using new marker for the TRule
		newRule.setMarkerType(RuleUtil.TGG_FT_RULE);
		newRule.setIsMarked(true);

		if (this.update == true) {
			// add rule at previous index
			tgg.getTRules().add(truleIndex, tRule);
			oldRule.getModule().getUnits()
					.add(oldruleIndex, newRule);
		} else {
			// add rule at the end of the list
			tgg.getTRules().add(tRule);
			oldRule.getModule().getUnits().add(newRule);
		}

		setGraphLayout();

		// old graphs
		Graph oldLHS = oldRule.getLhs();
		Graph oldRHS = oldRule.getRhs();

		/*
		 * copy all nodes as well as mappings
		 */
		for (Node oldNodeRHS : oldRHS.getNodes()) {

			boolean source = NodeUtil.isSourceNode(oldNodeRHS);

			boolean notNew = true;
			if (oldNodeRHS.getIsMarked() != null)
				notNew = !oldNodeRHS.getIsMarked();

			if (source) {

				Node tNodeRHS = copyNodePure(oldNodeRHS, tRuleRhs);
				Node tNodeLHS = copyNodePure(oldNodeRHS, tRuleLhs);

				setNodeLayoutAndMarker(tNodeRHS, oldNodeRHS,
						RuleUtil.Translated);
				// set marker also in LHS, for checking the matching constraint during execution 
				setNodeMarker(tNodeLHS, oldNodeRHS,
						RuleUtil.Translated);

				setMapping(tNodeLHS, tNodeRHS);

				// update all markers for the attributes
				Attribute newAttLHS = null;
				Attribute newAttRHS = null;
				for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {
					newAttLHS = copyAtt(oldAttribute, tNodeLHS);
					newAttRHS = copyAtt(oldAttribute, tNodeRHS);
					setAttributeMarker(newAttRHS, oldAttribute,
							RuleUtil.Translated);
					// marker needed for matching constraint
					setAttributeMarker(newAttLHS, oldAttribute,
							RuleUtil.Translated);
				}

				oldRhsNodes2TRhsNodes.put(oldNodeRHS, tNodeRHS);
				oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
						tNodeLHS);
			}

			else {
				Node nodeRHS = copyNodePure(oldNodeRHS, tRuleRhs);
				Node nodeLHS = null;

				if (notNew) {
					nodeLHS = copyNodePure(oldNodeRHS, tRuleLhs);

					setMapping(nodeLHS, nodeRHS);

					oldLhsNodes2TLhsNodes.put(RuleUtil.getLHSNode(oldNodeRHS),
							nodeLHS);
				}
				setNodeLayoutAndMarker(nodeRHS, oldNodeRHS, RuleUtil.NEW);

				// update all markers for the attributes
				Attribute newAttLHS = null;
				Attribute newAttRHS = null;
				for (Attribute oldAttribute : oldNodeRHS.getAttributes()) {
					if (oldAttribute.getIsMarked()!=null && 
							!oldAttribute.getIsMarked()) {
						newAttLHS = copyAtt(oldAttribute, nodeLHS);
					}
					newAttRHS = copyAtt(oldAttribute, nodeRHS);
					setAttributeMarker(newAttRHS, oldAttribute, RuleUtil.NEW);
				}
				oldRhsNodes2TRhsNodes.put(oldNodeRHS, nodeRHS);
			}
		}

		/*
		 * copy all edges set the references in edge and node
		 */
		for (Edge oldEdgeRHS : oldRHS.getEdges()) {

			Node oldSourceNode = oldEdgeRHS.getSource();
			Node oldTargetNode = oldEdgeRHS.getTarget();

//			EdgeLayout oldEdgeLayout = EdgeUtil.getEdgeLayout(oldEdgeRHS);
//			EdgeLayout edgeLayout = TggFactory.eINSTANCE.createEdgeLayout();

			boolean oldEdgeIsNew = false;
			if (oldEdgeRHS.getIsMarked()!=null)
				oldEdgeIsNew= oldEdgeRHS.getIsMarked();

			Edge tEdgeRHS = copyEdge(oldEdgeRHS, tRuleRhs);

			// case: edge belongs to source component, i.e. it is between two
			// nodes of the source component
			// create TEdge

			if (NodeUtil.isSourceNode(oldSourceNode)
					&& NodeUtil.isSourceNode(oldTargetNode)) {

				// RHS
				

				Node sourceTNodeRHS = oldRhsNodes2TRhsNodes.get(oldSourceNode);
				Node targetTNodeRHS = oldRhsNodes2TRhsNodes.get(oldTargetNode);

				setReferences(sourceTNodeRHS, targetTNodeRHS, tEdgeRHS,
						tRuleRhs);

				setEdgeMarker(tEdgeRHS,oldEdgeRHS,RuleUtil.Translated);
				

				// LHS
				Node sourceTNodeLHS = RuleUtil.getLHSNode(sourceTNodeRHS);
				Node targetTNodeLHS = RuleUtil.getLHSNode(targetTNodeRHS);

				// LHS
				Edge tEdgeLHS = copyEdge(oldEdgeRHS, tRuleLhs);
				setReferences(sourceTNodeLHS, targetTNodeLHS, tEdgeLHS,
						tRuleLhs);
				// for matching constraint
				setEdgeMarker(tEdgeLHS,oldEdgeRHS,RuleUtil.Translated);


			} else { // edge does not belong to source component

				// RHS
				Node sourceNodeRHS = oldRhsNodes2TRhsNodes.get(oldSourceNode);
				Node targetNodeRHS = oldRhsNodes2TRhsNodes.get(oldTargetNode);

				setReferences(sourceNodeRHS, targetNodeRHS, tEdgeRHS, tRuleRhs);

				setEdgeMarker(tEdgeRHS,oldEdgeRHS,RuleUtil.NEW);

				// LHS
				// if edge is not new, then put it into the LHS
				if (!oldEdgeIsNew) {
					Edge edgeLHS = copyEdge(oldEdgeRHS, tRuleLhs);

					Node sourceNodeLHS = RuleUtil.getLHSNode(sourceNodeRHS);
					Node targetNodeLHS = RuleUtil.getLHSNode(targetNodeRHS);

					setReferences(sourceNodeLHS, targetNodeLHS, edgeLHS,
							tRuleLhs);
				}
			}
		}

		/*
		 * copy the NACs
		 */
		if (oldLHS.getFormula() != null) {
			TreeIterator<EObject> iter = oldLHS.getFormula().eAllContents();
			Formula oldF;
			NestedCondition newNac;
			Graph newNacGraph;
			Not newNot;

			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof NestedCondition) {
					NestedCondition nc = (NestedCondition) o;
					EList<Mapping> nacMappings = nc.getMappings();

					newNacGraph = HenshinFactory.eINSTANCE.createGraph();
					newNacGraph.setName(nc.getConclusion().getName());

					newNac = HenshinFactory.eINSTANCE.createNestedCondition();
					newNot = HenshinFactory.eINSTANCE.createNot();

					newNac.setConclusion(newNacGraph);
					newNot.setChild(newNac);

					if (tRuleLhs.getFormula() != null) {
						oldF = tRuleLhs.getFormula();
						newRule.getLhs().setFormula(null);
						And newF = HenshinFactory.eINSTANCE.createAnd();
						newF.setLeft(oldF);
						newF.setRight(newNot);
						newRule.getLhs().setFormula(newF);
						SendNotify
								.sendAddFormulaNotify(newRule, (EObject) newF);
					} else {
						newRule.getLhs().setFormula(newNot);
						SendNotify.sendAddFormulaNotify(newRule,
								(EObject) newNot);
					}

					setNACGraphLayout(nc.getConclusion(), newNacGraph);

					newNacGraph = copyGraph(nc.getConclusion(), newNacGraph);
					newNac.getMappings().addAll(copyNacMappings(nacMappings));
				}
			}
		}

		List<Parameter> list = oldRule.getParameters();
		for (Parameter pm : list) {
			Parameter newParam = HenshinFactory.eINSTANCE.createParameter();
			newParam.setName(pm.getName());
			newRule.getParameters().add(newParam);
		}

		super.execute();
	}

	private void setEdgeMarker(Edge newEdgeRHS, Edge oldEdgeRHS,
			String markerType) {
		newEdgeRHS.setMarkerType(markerType);
		newEdgeRHS.setIsMarked(oldEdgeRHS.getIsMarked());
	}

	private void setAttributeMarker(Attribute newAttRHS,
			Attribute oldAttribute, String markerType) {
		newAttRHS.setMarkerType(markerType);
		newAttRHS.setIsMarked(oldAttribute.getIsMarked());
	}

	private Attribute copyAtt(Attribute att, Node newNode) {
		Attribute newAtt = HenshinFactory.eINSTANCE.createAttribute();
		newAtt.setType(att.getType());
		newAtt.setValue(att.getValue());
		newAtt.setNode(newNode);
		newNode.getAttributes().add(newAtt);
		return newAtt;
	}

	/*
	 * copy NacMappings
	 */
	private List<Mapping> copyNacMappings(EList<Mapping> nacMappings) {
		List<Mapping> newMappings = new ArrayList<Mapping>();

		for (Mapping m : nacMappings) {

			Node newMappingImage = oldNacNodes2TLhsNodes.get(m.getImage());
			Node newMappingOrigin = oldLhsNodes2TLhsNodes.get(m.getOrigin());

			Mapping newM = HenshinFactory.eINSTANCE.createMapping(
					newMappingOrigin, newMappingImage);

			newMappings.add(newM);
		}

		return newMappings;
	}

	/*
	 * copy graph with all nodes and edges
	 */
	private Graph copyGraph(Graph graph, Graph newGraph) {
		// Graph newGraph = HenshinFactory.eINSTANCE.createGraph();
		newGraph.setName(graph.getName());

		for (Node oldNode : graph.getNodes()) {

			boolean source = NodeUtil.isSourceNode(oldNode);

			if (source) {
				Node tNode = copyNode(oldNode, newGraph);
				setNodeLayout(tNode, oldNode);

				oldNacNodes2TLhsNodes.put(oldNode, tNode);
			}
			// afterwards, the remaining nodes outside the source component
			else {
				Node newNode = copyNode(oldNode, newGraph);
				setNodeLayout(newNode, oldNode);

				oldNacNodes2TLhsNodes.put(oldNode, newNode);
			}
		}

		for (Edge edge : graph.getEdges()) {

			Node sourceNode = edge.getSource();
			Node targetNode = edge.getTarget();

			// only if the edge connects two source nodes, a new
			// TEdge will be created
			if (NodeUtil.isSourceNode(sourceNode)
					&& NodeUtil.isSourceNode(targetNode)) {

				Edge tEdge = copyEdge(edge, newGraph);

				Node sourceTNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node targetTNode = oldNacNodes2TLhsNodes.get(targetNode);

				setReferences(sourceTNode, targetTNode, tEdge, newGraph);

				tEdge.setMarkerType(RuleUtil.Translated);
				tEdge.setIsMarked(false);
			} else {
				Edge newEdge = copyEdge(edge, newGraph);

				Node newSourceNode = oldNacNodes2TLhsNodes.get(sourceNode);
				Node newTargetNode = oldNacNodes2TLhsNodes.get(targetNode);

				setReferences(newSourceNode, newTargetNode, newEdge, newGraph);

				newEdge.setMarkerType(RuleUtil.NEW);
				newEdge.setIsMarked(false);
			}

		}
		return newGraph;
	}

	private void setReferences(Node sourceNode, Node targetNode, Edge edge,
			Graph tRuleGraph) {

		edge.setSource(sourceNode);
		edge.setTarget(targetNode);
		edge.setGraph(tRuleGraph);

		sourceNode.getOutgoing().add(edge);
		targetNode.getIncoming().add(edge);

		tRuleGraph.getEdges().add(edge);
	}

	@Override
	public boolean canExecute() {
		return oldRule != null && !oldRule.getRhs().getNodes().isEmpty();
	}

//	private Node findLHSNode(Node sourceTNode) {
//		for (Mapping m : newRule.getMappings()) {
//			if (m.getImage() == sourceTNode) {
//				return m.getOrigin();
//			}
//		}
//		return null;
//	}

	private Node copyNode(Node originalNode, Graph destinationGraph) {
		Node newNode = HenshinFactory.eINSTANCE.createNode();
		newNode.setName(originalNode.getName());
		newNode.setType(originalNode.getType());

		for (Attribute att : originalNode.getAttributes()) {
			Attribute newAtt = HenshinFactory.eINSTANCE.createAttribute();
			newAtt.setType(att.getType());
			newAtt.setValue(att.getValue());
			newAtt.setNode(newNode);
			newNode.getAttributes().add(newAtt);
		}

		newNode.setGraph(destinationGraph);
		destinationGraph.getNodes().add(newNode);

		return newNode;
	}

	private Node copyNodePure(Node originalNode, Graph destinationGraph) {
		Node newNode = HenshinFactory.eINSTANCE.createNode();
		newNode.setName(originalNode.getName());
		newNode.setType(originalNode.getType());

		// for (Attribute att : originalNode.getAttributes()) {
		// Attribute newAtt = HenshinFactory.eINSTANCE.createAttribute();
		// newAtt.setType(att.getType());
		// newAtt.setValue(att.getValue());
		// newAtt.setNode(newNode);
		// newNode.getAttributes().add(newAtt);
		// }

		newNode.setGraph(destinationGraph);
		destinationGraph.getNodes().add(newNode);

		return newNode;
	}

	/*
	 * creates a mapping, sets image and origin and adds the mapping to the
	 * tRule
	 */
	private void setMapping(Node tNodeLHS, Node tNodeRHS) {
		Mapping mapping = HenshinFactory.eINSTANCE.createMapping();
		mapping.setImage(tNodeRHS);
		mapping.setOrigin(tNodeLHS);

		newRule.getMappings().add(mapping);

	}

	private void setNodeLayoutAndMarker(Node rhsNode, Node oldNode,
			String markerType) {
		setNodeLayout(rhsNode,oldNode);
		setNodeMarker(rhsNode,oldNode,markerType);
	}

	
	private void setNodeMarker(Node rhsNode, Node oldNode,
			String markerType) {
		rhsNode.setMarkerType(markerType);
		rhsNode.setIsMarked(oldNode.getIsMarked());
	}

	private void setNodeLayout(Node rhsNode, Node oldNode) {
		rhsNode.setX(oldNode.getX());
		rhsNode.setY(oldNode.getY());
	}

	private Edge copyEdge(Edge edge, Graph graph) {
		Edge tEdge = HenshinFactory.eINSTANCE.createEdge();
		tEdge.setType(edge.getType());
		return tEdge;
	}

	private void setGraphLayout() {
			TripleGraph oldTripleGraph = (TripleGraph) oldRule.getRhs();
			TripleGraph newTripleGraph = (TripleGraph) newRule.getRhs();
			newTripleGraph.setDividerSC_X(oldTripleGraph.getDividerSC_X());
			newTripleGraph.setDividerCT_X(oldTripleGraph.getDividerCT_X());
			newTripleGraph.setDividerMaxY(oldTripleGraph.getDividerMaxY());
	}

	private void setNACGraphLayout(Graph oldNAC, Graph newNAC) {
		TripleGraph oldTripleGraph = (TripleGraph) oldNAC;
		TripleGraph newTripleGraph = (TripleGraph) newNAC;
		newTripleGraph.setDividerSC_X(oldTripleGraph.getDividerSC_X());
		newTripleGraph.setDividerCT_X(oldTripleGraph.getDividerCT_X());
		newTripleGraph.setDividerMaxY(oldTripleGraph.getDividerMaxY());
	}
}
package tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.*;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import tgg.AttributeLayout;
import tgg.EdgeLayout;
import tgg.TRule;
import tggeditor.util.AttributeUtil;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeTypes;
import tggeditor.util.RuleUtil;
import tggeditor.util.NodeTypes.NodeGraphType;
import tggeditor.util.NodeUtil;

/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class FTRuleConstraintE implements UserConstraint,BinaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Attribute, Boolean> isTranslatedAttributeMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Edge, Boolean> isTranslatedEdgeMap;

	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private Node node;

	/**
	 * The rule containing the node.
	 */
	private Rule rule=null;
	
	
	
	/**
	 * Whether the node is marked to be translated before executing the rule.
	 */
	private Boolean nodeIsTranslated;

	/**
	 * the constructor
	 * @param node see {@link FTRuleConstraint#node}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleConstraintE(Node node, 
			HashMap<Node, Boolean> isTranslatedMap, 
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, 
			HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		
		this.node = node;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;
		this.nodeIsTranslated = NodeUtil.getNodeIsTranslated(this.node);
		
		if (this.node.eContainer().eContainer() instanceof Rule) {
			// node is contained in the RHS
			rule = (Rule) this.node.eContainer().eContainer();
		} 
		else // node is contained in a NAC 
			if(this.node.eContainer().eContainer().eContainer() instanceof Rule){
			rule = (Rule) this.node.eContainer().eContainer().eContainer();
		}
		
	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot, Variable variable, Map<Variable, DomainSlot> domainMap, EGraph graph) {
		Node graphNode = getGraphNode(slot,graph);
		if (isSourceNode(graphNode)) {
			if (this.node.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (nodeIsTranslated && isTranslatedMap.containsKey(graphNode)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return (checkAttributes(graphNode) &&
							checkEdges(slot, variable, domainMap, graphNode, graph));
				}				
				else if (!nodeIsTranslated && !isTranslatedMap.containsKey(graphNode)) {
					// since node is not yet translated, 
					// also the adjacent edges and attributes are not yet translated and do not need to be checked
					return true;
				}
			}
			// for NAC nodes (evntl only not mapped from LHS to NAC graph ???)
			else if (isTranslatedMap.containsKey(graphNode)) {
					return true;
			}
			// else
			return false;
		}
		
		// for TARGET and CORRESPONDENCE 
		return true;
	}

	private Node getGraphNode(DomainSlot slot, EGraph graph) {
		// TODO Auto-generated method stub
		return ((HenshinEGraph)graph).getObject2NodeMap().get(slot.getValue());
	}
	
	private Node getGraphNode(EObject slot, EGraph graph) {
		// TODO Auto-generated method stub
		return ((HenshinEGraph)graph).getObject2NodeMap().get(slot);
	}




	private boolean checkAttributes(Node graphNode) {
		for (Attribute ruleAttribute : node.getAttributes()) {
			//find matching graph attribute (to the rule attribute)
			Attribute graphAttribute = ExecuteFTRulesCommand.findAttribute(graphNode, ruleAttribute.getType());
			if (graphAttribute == null) 
				return false;
			if (ruleAttribute.getIsMarked()) {
				// attribute is to be translated, thus it is not yet translated
				if (isTranslatedAttributeMap.containsKey(graphAttribute))
					return false;
			}
			else // attribute is only in context but not to be translated, thus it is already translated
				if (!isTranslatedAttributeMap.containsKey(graphAttribute))
					return false;
		}			
		return true;
	}



	private boolean checkEdges(DomainSlot source, Variable sourceVariable, Map<Variable, DomainSlot> domainMap, Node sourceGraphNode, EGraph graph) {

		// check each reference constraint and remove those possible edge matches that violate the marking
		for (ReferenceConstraint refConstraint : sourceVariable.getReferenceConstraints()) {

			EReference reference = refConstraint.getReference();
			Edge ruleEdge = refConstraint.getEdge();
			// Variable targetVariable = refConstraint.getTarget();
			
			DomainSlot target = domainMap.get(refConstraint.getTargetVariable());
			
			// iterate over all possible target graph nodes
			Node targetGraphNode;
//			boolean success = false;
			
			
			Collection<EObject> newReferredObjects = new ArrayList<EObject>(1);
			Collection<EObject> currentReferredObjects = new ArrayList<EObject>(1);

			// retrieve the currently possible matches of the target node to possible target nodes in the graph 
			if(target.isLocked()) {
				if (target.getValue()!=null) 
					currentReferredObjects.add(target.getValue()); 
			}

			else if(target.getDomain()!=null)
				currentReferredObjects = target.getDomain();
			else if (target.getTemporaryDomain()!=null)
				currentReferredObjects = target.getTemporaryDomain();
			
			if (currentReferredObjects.isEmpty()) 
				return false;
			
			// if change of currently possible matches for the target node occur, it will trigger a domain change
			boolean changeOccurred = false;
			
			// iterate over each currently possible target node in the graph for this reference
			for (EObject targetNodeObject : currentReferredObjects) {
				targetGraphNode = getGraphNode(targetNodeObject, graph);
				
				// if target node is not in source component, then stop
				if (!isSourceNode(targetGraphNode)){}
				else{

					boolean ruleEdgeIsTranslated = false; 
					if (ruleEdge.getIsMarked()!= null)
						ruleEdgeIsTranslated = !ruleEdge.getIsMarked();
					Edge graphEdge = findEdge(sourceGraphNode, targetGraphNode,
							ruleEdge.getType());

					// reduce the possible target domain for the target node, if
					// translation markers do not fit
					if (ruleEdgeIsTranslated) {
						// case: context edge, thus edge has to be translated
						// already,
						if (isTranslatedEdgeMap.containsKey(graphEdge))
							newReferredObjects.add(targetNodeObject);
						else changeOccurred = true;
					} else { // case: edge is translated by rule, thus should
						// not be translated already
						if (!isTranslatedEdgeMap.containsKey(graphEdge))
							newReferredObjects.add(targetNodeObject);
						else changeOccurred = true;
					}
				}


			}
			
			if(changeOccurred){
				DomainChange change = new DomainChange(target,
						target.getTemporaryDomain());
				source.getRemoteChangeMap().put(this, change);
				target.setTemporaryDomain(new ArrayList<EObject>(newReferredObjects));

				if (change.getOriginalValues() != null)
					target.getTemporaryDomain().retainAll(
							change.getOriginalValues());

				return !target.getTemporaryDomain().isEmpty();							
			}
		}
			

		return true;
	}

	/**
	 * Find the edge between a source node and a target node with a specific type. Is just working 
	 * when there is not more than one one type of edge between the two nodes allowed.
	 * @param source source node
	 * @param target target node
	 * @param type type of the edge
	 * @return edge between the source and the target node with a specific type
	 */
	private Edge findEdge(Node source, Node target, EReference type) {
		for (Edge e : source.getOutgoing()) {
			if (e.getType() == type &&
					e.getTarget() == target) {
				return e;
			}
		}
		return null;
	}
	
	private Node findTarget(Node source, EReference type) {
		for (Edge e : source.getOutgoing()) {
			if (e.getType() == type ) {
				return e.getTarget();
			}
		}
		return null;
	}
	
	
	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */
	private boolean isSourceNode(Node graphNode) {
		NodeGraphType type = NodeTypes.getNodeGraphType(graphNode);
		return type == NodeGraphType.SOURCE;
	}



	@Override
	public boolean check(DomainSlot source, DomainSlot target) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean unlock(Variable sender, DomainSlot slot) {
		// TODO Auto-generated method stub
		return false;
	}

}

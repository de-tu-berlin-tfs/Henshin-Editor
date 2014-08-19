package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.ReferenceConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.interpreter.util.HashList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class FTRuleEdgeConstraintEMF implements BinaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<EObject, Boolean> isTranslatedMap;
	
	private Set<EObject> sourceNodeMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedAttributeMap;
	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteFTRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 * An edge is identified by the triple (source, type, target)
	 */
	private HashMap<EObject, HashMap<EReference, HashMap<EObject,Boolean>>> isTranslatedEdgeMap;

	
	
	/**
	 * The node which can be mapped to another node in the graph (see 
	 * {@link FTRuleConstraint#check(Node graphNode)}). The node could be a node in
	 * a {@link Rule} or in a nac.
	 */
	private Node ruleNode;

	
	
	
	/**
	 * Whether the node is marked to be translated before executing the rule.
	 */
	private Boolean ruleNodeIsTranslated;
	private Edge edge;
	

	/**
	 * the constructor
	 * @param ruleNode see {@link FTRuleConstraint#ruleNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleEdgeConstraintEMF(Edge edge, 
			Set<EObject> sourceNodeMap,
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		
		this.ruleNode = edge.getSource();
		this.edge = edge;
		this.sourceNodeMap = sourceNodeMap;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;
		this.ruleNodeIsTranslated = NodeUtil.getNodeIsTranslated(this.ruleNode);
		if (ruleNodeIsTranslated == null)
			ruleNodeIsTranslated = true;

	}
	

	
	
	/** 
	 * Checks if the mapping in a {@link TRule}.
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot source,DomainSlot target) {
		
		EObject graphNode = source.getValue();
		if (isSourceNode(graphNode)) {
			if (this.ruleNode.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (ruleNodeIsTranslated && isTranslatedMap.get(graphNode)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return (checkEdges(source,target, graphNode));
				}	
			}
		}
		
		return true;
	}

	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */

	private boolean isSourceNode(EObject graphNode) {
		return sourceNodeMap.contains(graphNode);
	}

	private boolean checkEdges(DomainSlot source,DomainSlot target, EObject graphSourceNodeObject) {
		// check each reference constraint and remove those possible edge matches that violate the marking

		Edge ruleEdge = edge;
		// Variable targetVariable = refConstraint.getTarget();

		// iterate over all possible target graph nodes

		Collection<EObject> newReferredObjects = new ArrayList<EObject>(1);
		Collection<EObject> currentReferredObjects = null;

		// retrieve the currently possible matches of the target node to possible target nodes in the graph 
		if(target.isLocked()) {
			currentReferredObjects = new ArrayList<EObject>(1);
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
		for (EObject graphTargetNodeObject : currentReferredObjects) {

			// if target node is not in source component, then stop
			if (!isSourceNode(graphTargetNodeObject)){
				return true;
			}
			else{

				boolean ruleEdgeIsTranslated = (((TEdge) ruleEdge).getMarkerType() != null && ((TEdge) ruleEdge).getMarkerType().equals(RuleUtil.Translated));
				
				if ( ruleEdge.getType().isDerived()){
					return true;
				}
				// reduce the possible target domain for the target node, if
				// translation markers do not fit
				if (!ruleEdgeIsTranslated) {
					// case: context edge, thus edge has to be translated
					// already,
					if (edgeIsInMap(isTranslatedEdgeMap,graphSourceNodeObject,ruleEdge.getType(),graphTargetNodeObject))
						newReferredObjects.add(graphTargetNodeObject);
					else changeOccurred = true;
				} else { // case: edge is translated by rule, thus should
					// not be translated already
					if (!edgeIsInMap(isTranslatedEdgeMap,graphSourceNodeObject,ruleEdge.getType(),graphTargetNodeObject))
						newReferredObjects.add(graphTargetNodeObject);
					else changeOccurred = true;
				}
			}


		}

		// if there are no remaining valid targets for the current reference, then stop here and backtrack the matching
		if (newReferredObjects.isEmpty()) 
			return false;

		if(changeOccurred){
			DomainChange change = new DomainChange(target,
					target.getTemporaryDomain());
			source.getRemoteChangeMap().put(this, change);
			target.setTemporaryDomain(new ArrayList<EObject>(newReferredObjects));

			if (change.getOriginalValues() != null)
				target.getTemporaryDomain().retainAll(
						change.getOriginalValues());

			boolean r = !target.getTemporaryDomain().isEmpty();
			if (!r)
				return false;
		}



		return true;
	}

	/**
	 * checks whether the edge (source,type,target) is already in the hash map
	 * @param isTranslatedEdgeMap2
	 * @param graphSourceNodeObject
	 * @param type
	 * @param graphTargetNodeObject
	 * @return
	 */
	private boolean edgeIsInMap(
			HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap2,
			EObject graphSourceNodeObject, EReference type,
			EObject graphTargetNodeObject) {
		HashMap<EReference, HashMap<EObject, Boolean>> typeMap = isTranslatedEdgeMap2.get(graphSourceNodeObject);
		if(typeMap.containsKey(type))
		 return typeMap.get(type).containsKey(graphTargetNodeObject);
		return false;
	}


}

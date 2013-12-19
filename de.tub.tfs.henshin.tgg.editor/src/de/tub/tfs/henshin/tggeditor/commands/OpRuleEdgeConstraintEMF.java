package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class OpRuleEdgeConstraintEMF implements BinaryConstraint {

	
	private Set<EObject> markedNodesMap;
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
	private TNode ruleTNode;
	private String ruleNodeMarker;

	
	
	private Edge edge;
	

	/**
	 * the constructor
	 * @param ruleTNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleEdgeConstraintEMF(Edge edge, 
			Set<EObject> markedNodesMap,
			HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		
		this.ruleTNode = (TNode)edge.getSource();
		this.ruleNodeMarker=ruleTNode.getMarkerType();
		this.edge = edge;
		this.markedNodesMap = markedNodesMap;
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;

	}
	

	
	
	/**
	 * Checks if the mapping in a {@link TRule}.
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot source, DomainSlot target) {

		EObject graphNode = source.getValue();
		if (isMarkedNode(graphNode)) {
			// case: node is in marked component

			if (RuleUtil.Translated_Graph.equals(ruleNodeMarker)
					|| RuleUtil.TR_UNSPECIFIED.equals(ruleNodeMarker)) {
				// case: node is context node, then check the edge marker
				return (checkEdge(source, target, graphNode));
			}
			// case: node is to be translated, then checking the node marker
			// ensures already that the edge marker fits, i.e., nothing to
			// do
		}

		return true;
	}

	/**
	 * Checks if a graphnode is a source node.
	 * @param graphNode
	 * @return true if it is a source node, else false
	 */

	private boolean isMarkedNode(EObject graphNode) {
		return markedNodesMap.contains(graphNode);
	}

	private boolean checkEdge(DomainSlot source,DomainSlot target, EObject graphSourceNodeObject) {
		// check each reference constraint and remove those possible edge matches that violate the marking

		Edge ruleEdge = edge;
		if (RuleUtil.TR_UNSPECIFIED.equals(((TEdge) ruleEdge).getMarkerType()))
			System.out.println("TR=?");
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

			// if target node is not in marked component, then stop
			if (!isMarkedNode(graphTargetNodeObject)){
				return true;
			}
			else{

				
				if ( ruleEdge.getType().isDerived()){
					return true;
				}

				if (null==(((TEdge) ruleEdge).getMarkerType())) {
					// case: no marker available - marker is not relevant,e.g. in NAC
					return true;
				}
				if (RuleUtil.TR_UNSPECIFIED.equals(((TEdge) ruleEdge).getMarkerType())) {
					// case: marker is not relevant
					return true;
				}
				// reduce the possible target domain for the target node, if
				// translation markers do not fit
				if (RuleUtil.Translated_Graph.equals(((TEdge) ruleEdge).getMarkerType())) {
					// edge is only in context but not to be translated, thus it is already translated
					if (edgeIsInTranslated(isTranslatedEdgeMap,graphSourceNodeObject,ruleEdge.getType(),graphTargetNodeObject))
						newReferredObjects.add(graphTargetNodeObject);
					else changeOccurred = true;
				}
				if (RuleUtil.Not_Translated_Graph.equals(((TEdge) ruleEdge).getMarkerType())) {
					// edge is to be translated, thus it is not yet translated
					if (!edgeIsInTranslated(isTranslatedEdgeMap,graphSourceNodeObject,ruleEdge.getType(),graphTargetNodeObject))
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
	 * checks whether the edge (source,type,target) is already in the hash map resp. translated
	 * @param isTranslatedEdgeMap2
	 * @param graphSourceNodeObject
	 * @param type
	 * @param graphTargetNodeObject
	 * @return
	 */
	private boolean edgeIsInTranslated(
			HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap2,
			EObject graphSourceNodeObject, EReference type,
			EObject graphTargetNodeObject) {
		HashMap<EReference, HashMap<EObject, Boolean>> typeMap = isTranslatedEdgeMap2
				.get(graphSourceNodeObject);
		if (typeMap.containsKey(type))
			if (typeMap.get(type).containsKey(graphTargetNodeObject))
				return typeMap.get(type).get(graphTargetNodeObject);
		// edge is not in map and thus, not yet translated
		return false;
	}

	

}

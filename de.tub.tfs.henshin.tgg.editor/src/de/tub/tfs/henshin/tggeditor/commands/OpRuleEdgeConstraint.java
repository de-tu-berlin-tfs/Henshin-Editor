package de.tub.tfs.henshin.tggeditor.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainChange;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.EdgeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;

public class OpRuleEdgeConstraint implements BinaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteOpRulesCommand}. The hashmap contains all the already translated edges 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Edge, Boolean> isTranslatedEdgeMap;

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}s in the 
	 * {@link ExecuteOpRulesCommand}. The hashmap contains all the already translated nodes 
	 * of the graph on which the {@link TRule}s are executed.
	 */
	private HashMap<Node, Boolean> isTranslatedMap;
	
	
	/**
	 * marker of the edge in the rule
	 */
	private String ruleEdgeMarker;
		
	
	private TEdge edge;
	private EGraph graph;

	/**
	 * the constructor
	 * @param node see {@link OpRuleConstraint#node}
	 * @param isTranslatedMap see {@link OpRuleConstraint#isTranslatedMap}
	 */
	public OpRuleEdgeConstraint(Edge edge, 
			HashMap<Node, Boolean> isTranslatedMap,
			HashMap<Edge, Boolean> isTranslatedEdgeMap,
			EGraph graph) {
		
		this.edge = (TEdge)edge;
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;
		this.isTranslatedMap = isTranslatedMap;
		this.graph = graph;
		ruleEdgeMarker = ((TEdge) edge).getMarkerType();

	}

	@Override
	public boolean check(DomainSlot source, DomainSlot target) {

		// case: no marker - e.g. element in filter NAC that has not been marked explicitly
		if(ruleEdgeMarker==null) return true;
		
		// currentReferredObjects shall contain all targets that were found up to now for this reference 
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


		// newReferredObjects shall contain all valid targets of this reference concerning the conditions on translation markers
		// this array is a subset of currentReferredObjects
		Collection<EObject> newReferredObjects = new ArrayList<EObject>(1);

		// if a change of currently possible matches for the target node occurs, it will trigger a domain change
		boolean changeOccurred = false;


		Node sourceGraphNode = NodeUtil.getGraphNode(source,graph);
		// Variable targetVariable = refConstraint.getTarget();

		// iterate over all possible target graph nodes
		Node targetGraphNode;
		//			boolean success = false;
		
		
		// iterate over each currently possible target node in the graph for this reference
		for (EObject targetNodeObject : currentReferredObjects) {
			targetGraphNode = NodeUtil.getGraphNode(targetNodeObject, graph);

			// if target node is not in marked component, then stop
			if (!isTranslatedMap.containsKey(targetGraphNode)) {
				return true;
			} else {

				Edge graphEdge = EdgeUtil.findEdge(sourceGraphNode, targetGraphNode,
						edge.getType());
				if (edge.getType().isDerived()) {
					return true;
				}

				boolean markerIsValid = RuleUtil.checkEdgeMarker(ruleEdgeMarker,isTranslatedEdgeMap,graphEdge);
				if (markerIsValid)
					newReferredObjects.add(targetNodeObject);
				else
					// reduce the possible target domain for the target node, if
					// translation markers do not fit
					changeOccurred = true;
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
				return false;;
		}
		
		return true;
	}
	



}

/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */

package de.tub.tfs.henshin.tgg.interpreter.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class OpRuleEdgeConstraintEMF implements BinaryConstraint {

	
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

	
	
	private TEdge ruleEdge;
	private String ruleEdgeMarker;
	
	private DomainSlot source;
	private DomainSlot target;
	

	/**
	 * the constructor
	 * @param ruleTNode see {@link FTRuleConstraint#ruleTNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public OpRuleEdgeConstraintEMF(Edge edge, 
			HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		
		this.ruleTNode = (TNode)edge.getSource();
		this.ruleNodeMarker=ruleTNode.getMarkerType();
		if (edge instanceof TEdge) {
			this.ruleEdge = (TEdge) edge;
			this.ruleEdgeMarker=ruleEdge.getMarkerType();
		}
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;

	}
	

	
	
	/**
	 * Checks if the mapping in a {@link TRule}.
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot source, DomainSlot target) {

		if(ruleEdge==null) 
			return false; // e.g., ruleEdge is not a TEdge - rule is not valid in HenshinTGG
		
		if(ruleEdgeMarker==null || RuleUtil.TR_UNSPECIFIED.equals(ruleEdgeMarker))
			// edge is not marked or marked with wild card - no marker restriction - only component restriction
			return true;
		
		this.source=source;
		this.target=target;

		EObject sourceObjectInGraph = source.getValue();
		
		// retrieve available marked target nodes in graph fitting to the graph edge
		HashMap<EObject, Boolean> markedTargetsInGraph=null;
		HashMap<EReference, HashMap<EObject, Boolean>> markedReferencesInGraph = isTranslatedEdgeMap.get(sourceObjectInGraph);
		if(markedReferencesInGraph!=null){
			markedTargetsInGraph = markedReferencesInGraph.get(ruleEdge.getType());
		}
		
		// check markers
		return checkEdgeMarker(markedTargetsInGraph);
			
	}







	private boolean checkEdgeMarker(
			HashMap<EObject, Boolean> markedTargetsInGraph) {

		// there are no marked edges available in graph
		if (markedTargetsInGraph == null) {
			// rule edge is not marked - all targets fit
			if (ruleEdgeMarker == null)
				return true;
			// rule edge is marked (no wild card) - no target fits
			else
				return false;
		}

		// iterate over all possible target graph nodes
		Collection<EObject> newReferredObjects = new ArrayList<EObject>(1);
		Collection<EObject> currentReferredObjects = null;

		currentReferredObjects = retrieveCurrentReferencedObjects(currentReferredObjects);

		if (currentReferredObjects.isEmpty())
			return false;

		// if change of currently possible matches for the target node occur, it
		// will trigger a domain change
		boolean changeOccurred = false;

		// iterate over each currently possible target node in the graph for
		// this reference
		for (EObject graphTargetNodeObject : currentReferredObjects) {

			if (!markedTargetsInGraph.containsKey(graphTargetNodeObject)) {
				// current matched graph target of edge is not in the list of possible
				// marked nodes, then check that the rule edge has either no marker or unspecified marker

				if (ruleEdgeMarker == null) 
					// case: ruleEdge and current nodeTarget reference are not
					// marked
					newReferredObjects.add(graphTargetNodeObject);
				else
					// case: rule edge is marked (no wild card), but graph edge has no marker -
					// do not put in new list and indicate change
					changeOccurred = true;
			} else {// the current graph edge is marked
				// check that the marker of the rule edge fits
				if (ruleEdgeMarker == null){
					// inconsistency: rule edge has no marker, but graph edge
					// has - do not put in new list
					changeOccurred = true;
				}
				Boolean edgeMarkerInGraph = markedTargetsInGraph
						.get(graphTargetNodeObject);
				if (RuleUtil.Translated_Graph.equals(ruleEdgeMarker)) {
					if (edgeMarkerInGraph == true)
						newReferredObjects.add(graphTargetNodeObject);
				}
				// edge is to be translated, thus it is not yet translated
				else if (RuleUtil.Not_Translated_Graph.equals(ruleEdgeMarker)) {
					if (edgeMarkerInGraph == false)
						newReferredObjects.add(graphTargetNodeObject);
				} else {
					changeOccurred = true;
				}
			}

		}


		// if there are no remaining valid targets for the current reference,
		// then stop here and backtrack the matching
		if (newReferredObjects.isEmpty())
			// match cannot be completed - abort
			return false;

		if (changeOccurred) {
			performChange(newReferredObjects);

			boolean remainingTargetsExist = !target.getTemporaryDomain()
					.isEmpty();
			if (!remainingTargetsExist)
				// match cannot be completed - abort
				return false;
		}

		// check was successful
		return true;
	}




	private Collection<EObject> retrieveCurrentReferencedObjects(
			Collection<EObject> currentReferredObjects) {
		// retrieve the currently possible matches of the target node to
		// possible target nodes in the graph
		if (target.isLocked()) {
			currentReferredObjects = new ArrayList<EObject>(1);
			if (target.getValue() != null)
				currentReferredObjects.add(target.getValue());
		}

		else if (target.getDomain() != null)
			currentReferredObjects = target.getDomain();
		else if (target.getTemporaryDomain() != null)
			currentReferredObjects = target.getTemporaryDomain();
		return currentReferredObjects;
	}



	private void performChange(Collection<EObject> newReferredObjects) {
		DomainChange change = new DomainChange(target,
				target.getTemporaryDomain());
		source.getRemoteChangeMap().put(this, change);
		target.setTemporaryDomain(new ArrayList<EObject>(newReferredObjects));

		if (change.getOriginalValues() != null)
			target.getTemporaryDomain().retainAll(change.getOriginalValues());
	}





	

}

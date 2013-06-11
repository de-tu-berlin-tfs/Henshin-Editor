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
import org.eclipse.emf.henshin.interpreter.matching.constraints.UserConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.Variable;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.FTRuleConstraint;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * This class is for checking the correct mapping in a execution cycle of FTRules.
 * It is given to the {@link EmfEngine} of henshin. 
 * @see ExecuteFTRulesCommand
 * @see EmfEngine#registerUserConstraint(Class, Object...)
 */
public class FTRuleConstraintEMF implements UserConstraint,BinaryConstraint {

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
	

	/**
	 * the constructor
	 * @param ruleNode see {@link FTRuleConstraint#ruleNode}
	 * @param isTranslatedMap see {@link FTRuleConstraint#isTranslatedMap}
	 */
	public FTRuleConstraintEMF(Node ruleNode, 
			Set<EObject> sourceNodeMap,
			HashMap<EObject, Boolean> isTranslatedMap, 
			HashMap<EObject,HashMap<EAttribute, Boolean>> isTranslatedAttributeMap, 
			HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		
		this.ruleNode = ruleNode;
		this.sourceNodeMap = sourceNodeMap;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
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
	public boolean check(DomainSlot slot, Variable variable, Map<Variable, DomainSlot> domainMap, EGraph graph) {
		
		EObject graphNode = slot.getValue();
		if (isSourceNode(graphNode)) {
			if (this.ruleNode.eContainer().eContainer() instanceof Rule) {
				// case: node is context node, then graph node has to be translated already
				if (ruleNodeIsTranslated && isTranslatedMap.get(graphNode)) {
					// check attributes
					// moreover, all edges have to be checked to be consistent with translatedEdgeMap
					return (checkAttributes(graphNode) &&
							checkEdges(slot, variable, domainMap, graphNode, graph));
				}				
				else if (!ruleNodeIsTranslated && !isTranslatedMap.get(graphNode)) {
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
		Node rhsNode=RuleUtil.getRHSNode(this.ruleNode);
		if (NodeUtil.isSourceNodeByPosition((TNode) rhsNode)) 
			return false;
		else
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


	private boolean checkAttributes(EObject graphNode) {
		for (Attribute ruleAttribute : ruleNode.getAttributes()) {
			//find matching graph attribute (to the rule attribute)
			EAttribute eAttribute = ruleAttribute.getType();
			if (((TAttribute) ruleAttribute).getMarkerType() != null && ((TAttribute) ruleAttribute).getMarkerType().equals(RuleUtil.Translated)) {
				// attribute is to be translated, thus it is not yet translated
				if (isTranslatedAttributeMap.get(graphNode).containsKey(eAttribute))
					return false;
			}
			else // attribute is only in context but not to be translated, thus it is already translated
				if (!isTranslatedAttributeMap.get(graphNode).containsKey(eAttribute))
					return false;
		}			
		return true;
	}







	private boolean checkEdges(DomainSlot source, Variable sourceVariable, Map<Variable, DomainSlot> domainMap, EObject graphSourceNodeObject, EGraph graph) {
		// check each reference constraint and remove those possible edge matches that violate the marking
		nextCons: for (ReferenceConstraint refConstraint : sourceVariable.getReferenceConstraints()) {

			Edge ruleEdge = refConstraint.getEdge();
			// Variable targetVariable = refConstraint.getTarget();
			
			DomainSlot target = domainMap.get(refConstraint.getTargetVariable());
			
			// iterate over all possible target graph nodes
			
			
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
			for (EObject graphTargetNodeObject : currentReferredObjects) {
				
				// if target node is not in source component, then stop
				if (!isSourceNode(graphTargetNodeObject)){
					
					continue nextCons;
				}
				else{

					boolean ruleEdgeIsTranslated = (((TEdge) ruleEdge).getMarkerType() != null && ((TEdge) ruleEdge).getMarkerType().equals(RuleUtil.Translated));

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





	
	
	


	@Override
	public boolean check(DomainSlot source, DomainSlot target) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean unlock(Variable sender, DomainSlot slot) {

		return false;
	}

}

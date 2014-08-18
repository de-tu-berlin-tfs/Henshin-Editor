/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/

package de.tub.tfs.henshin.tgg.interpreter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EGraphImpl;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.TggUtil;

public class TggTransformationImpl implements TggTransformation {

	/**
	 * the maps for the marked elements 
	 */
	protected TranslationMaps translationMaps = new TranslationMaps();
	/**
	 * the map for the marked node objects
	 */
	protected HashMap<EObject, Boolean> isTranslatedNodeMap = translationMaps.getIsTranslatedNodeMap();
	/**
	 * the map for the marked attributes
	 */
	protected HashMap<EObject, HashMap<EAttribute, Boolean>> isTranslatedAttributeMap = translationMaps.getIsTranslatedAttributeMap();
	/**
	 * the map for the marked references
	 */
	protected HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap = translationMaps.getIsTranslatedEdgeMap();

	/**
	 * the map storing the triple components for each node 
	 */
	protected HashMap<EObject, TripleComponent> tripleComponentNodeMap = new HashMap<EObject, TripleComponent>();

	/**
	 * 
	 */
	private TGGEngineImpl emfEngine;
	/**
	 * 
	 */
	private EGraph eGraph;

	/**
	 * flag, whether attribute values can be matched to null values
	 */
	public Boolean nullValueMatching = true;

	/**
	 * 
	 */
	public List<Rule> opRulesList = new Vector<Rule>();

	/**
	 * 
	 */
	protected ArrayList<RuleApplicationImpl> ruleApplicationList= new ArrayList<RuleApplicationImpl>();

	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getTripleComponentNodeMap()
	 */
	@Override
	public HashMap<EObject, TripleComponent> getTripleComponentNodeMap() {
		return tripleComponentNodeMap;
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getRuleApplicationList()
	 */
	@Override
	public ArrayList<RuleApplicationImpl> getRuleApplicationList() {
		return ruleApplicationList;
	}
	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getGraph()
	 */
	@Override
	public EGraph getGraph() {
		return eGraph;
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setGraph(org.eclipse.emf.henshin.interpreter.EGraph)
	 */
	@Override
	public void setGraph(EGraph graph) {
		this.eGraph = graph;
	}


	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getfTRuleList()
	 */
	@Override
	public List<Rule> getfTRuleList() {
		return opRulesList;
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setOpRuleList(java.util.List)
	 */
	@Override
	public void setOpRuleList(List<Rule> opRuleList) {
		this.opRulesList = opRuleList;
	}




	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getEmfEngine()
	 */
	@Override
	public TGGEngineImpl getEmfEngine() {
		return emfEngine;
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setEmfEngine(de.tub.tfs.henshin.tgg.interpreter.impl.TGGEngineImpl)
	 */
	@Override
	public void setEmfEngine(TGGEngineImpl emfEngine) {
		this.emfEngine = emfEngine;
	}

	public TggTransformationImpl() {
		
		isTranslatedNodeMap .clear();
		isTranslatedAttributeMap.clear();
		isTranslatedEdgeMap.clear();


	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#getTranslationMaps()
	 */
	@Override
	public TranslationMaps getTranslationMaps() {
		return translationMaps;
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setTranslationMaps(de.tub.tfs.henshin.tgg.interpreter.impl.TranslationMaps)
	 */
	@Override
	public void setTranslationMaps(TranslationMaps translationMaps) {
		this.translationMaps = translationMaps;
	}

	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setInput(java.util.List)
	 */
	@Override
	public void setInput(List<EObject> inputRootEObjects) {
		createInputGraph(inputRootEObjects);
		fillTranslatedMaps(inputRootEObjects);
		registerUserConstraints();
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setInput(de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph)
	 */
	@Override
	public void setInput(TggHenshinEGraph eGraph) {
		this.eGraph=eGraph;
		fillTranslatedMapsFromGraph(eGraph);
		registerUserConstraints();
	}

	private void fillTranslatedMapsFromGraph(TggHenshinEGraph eGraph) {
		Graph graph = eGraph.getHenshinGraph();
		Map<Node, EObject> node2eObject = eGraph.getNode2ObjectMap();
		
		// input graph has to be marked initially to avoid confusion if source and target meta model coincide
		// select marked nodes and put them in list of input eObjects
		EObject o=null;
		EAttribute a=null;
		EReference eRef=null;
		EObject source=null;
		EObject target=null;
		Boolean translationMarker=null;
		for(Node n:graph.getNodes()){
			if(n instanceof TNode)
				translationMarker = TggUtil.getIsTranslated(((TNode)n));
			if(translationMarker!=null){
				o = node2eObject.get(n);
				isTranslatedNodeMap.put(o,translationMarker);
				translationMarker=null;
			}
			for(Attribute att:n.getAttributes()){
				if(att instanceof TAttribute)
					translationMarker =TggUtil.getIsTranslated((TAttribute)att);
				if(	translationMarker !=null){
					a= att.getType();
					addToTranslatedAttributeMap(o, a, translationMarker);
					translationMarker=null;
				}
			}
		
		}
		for(Edge e:graph.getEdges()){
			if(e instanceof TEdge)
				translationMarker = TggUtil.getIsTranslated(((TEdge)e));
			if (translationMarker!=null){
				source=node2eObject.get(e.getSource());
				eRef=e.getType();
				target= node2eObject.get(e.getTarget());
				addToTranslatedEdgeMap(source, eRef, target,translationMarker);
				translationMarker=null;
			}
		}
	}
	
	// TODO: check triple component during matching, if a meta model is used for more than one component
	private void createInputGraph(List<EObject> inputEObjects) {
		eGraph = new EGraphImpl();
		for(EObject o: inputEObjects){
			eGraph.addTree(o);     // matching via depth first search - as in editor
			//eGraph.addGraph(o);  // matching via breath first search
		}
	}

	
	private void registerUserConstraints() {
		emfEngine = new TGGEngineImpl(eGraph) {	
			@Override
			public UnaryConstraint createUserConstraints(Attribute attribute) {
				return new OpRuleAttributeConstraintEMF(attribute, isTranslatedNodeMap, isTranslatedAttributeMap, nullValueMatching);
			}

			@Override
			public BinaryConstraint createUserConstraints(Edge edge) {
				return new OpRuleEdgeConstraintEMF(edge, isTranslatedEdgeMap);
			}

			@Override
			public UnaryConstraint createUserConstraints(Node node) {
				return new OpRuleNodeConstraintEMF(node, isTranslatedNodeMap);
			}
		};
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#applyRules()
	 */
	@Override
	public void applyRules() {
		applyRules(null,null);
	}

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#applyRules(org.eclipse.core.runtime.IProgressMonitor, java.lang.String)
	 */
	@Override
	public void applyRules(IProgressMonitor monitor, String msg) {
		// check if any rule can be applied
		long startTimeOneStep=System.nanoTime();
		long endTimeOneStep=System.nanoTime();
		long duration=0;
		RuleApplicationImpl ruleApplication = null;
		boolean foundApplication = true;
		while (foundApplication) {
			foundApplication = false;
			// apply all rules on graph
			Rule currentRule = null;
			printOpRuleList();
			try {
				for (Rule rule : opRulesList) {
					startTimeOneStep=System.nanoTime();
					if (monitor!=null)
						monitor.subTask(msg + " (" + rule.getName() + ")");
					currentRule=rule;
//					ruleApplication = new RuleApplicationImpl(emfEngine);
//					ruleApplication.setEGraph(graph);
//					ruleApplication.setRule(rule);
					// TODO: improve efficiency: reuse rule application when running without editor - node positions are not relevant then
					/*
					 * Apply a rule as long as it's possible and add each successful
					 * application to ruleApplicationlist. Then fill the
					 * isTranslatedTable Start with a fresh match.
					 */
					Boolean matchesToCheck = true;
					while (matchesToCheck) {
						Iterator<Match> matchesIterator = emfEngine
								.findMatches(rule, eGraph, new MatchImpl(rule))
								.iterator();
						while (matchesIterator.hasNext()) {
							// refresh rule application to be used for layout and debugging
							ruleApplication = new RuleApplicationImpl(emfEngine);
							ruleApplication.setEGraph(eGraph);
							ruleApplication.setRule(rule);
							ruleApplication.setPartialMatch(matchesIterator
									.next());
							try {
								foundApplication = executeOneStep(ruleApplication,
										foundApplication, rule);
							} catch (RuntimeException e){
								e.printStackTrace();
								matchesToCheck = false;
							}
						}
						matchesToCheck = false;
					}
					endTimeOneStep=System.nanoTime();
					duration=(endTimeOneStep-startTimeOneStep)/(1000000);
					if(duration>10)
						System.out.println("Rule " + rule.getName() + ":" + duration + "ms");
//					startTimeOneStep=System.nanoTime();
				}

			} catch (RuntimeException e) {
				System.out.println("Rule "
						+ currentRule.getName()
						+ " caused a runtime exception. Check input parameter settings: "
						+ e.getMessage());
			}
		}
	}

	private void printOpRuleList() {
		System.out.print("List of operational rules: ");
		for(Rule r:opRulesList){
			System.out.print(r.getName()+";");
		}
		System.out.println();
	}

	private boolean executeOneStep(RuleApplicationImpl ruleApplication,
			boolean foundApplication, Rule rule) {
		if (ruleApplication.execute(null)) {
			foundApplication = true;
			// fill isTranslatedNodeMap
			List<Node> rhsNodes = rule.getRhs().getNodes();
			Match resultMatch = ruleApplication.getResultMatch();
			for (Node n: rhsNodes) {
				TNode ruleNodeRHS = (TNode) n;
				EObject nodeEObject = resultMatch.getNodeTarget(ruleNodeRHS);
				tripleComponentNodeMap.put(nodeEObject,ruleNodeRHS.getComponent());
				if (RuleUtil.Translated.equals(ruleNodeRHS.getMarkerType())) {
					isTranslatedNodeMap.put(nodeEObject, true);
					updateTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					updateTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				} else {
					// context node, thus check whether the edges
					// and attributes are translated
					updateTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					updateTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				}
			}
			// emfEngine.postProcess(resultMatch);
			// everything successful, add the rule application
			   ruleApplicationList.add(ruleApplication);

		} else {
			// Match is not applicable, e.g. because it became invalid by a previous step - TODO: possible to improve efficiency
		}
		return foundApplication;
	}


	private void addToTranslatedAttributeMap(EObject graphNodeEObject, EAttribute eAttr, Boolean translationMarker) {
		if(!isTranslatedAttributeMap.containsKey(graphNodeEObject)) {
			isTranslatedAttributeMap.put(graphNodeEObject,new HashMap<EAttribute,Boolean>());
		}
		putAttributeInMap(graphNodeEObject, eAttr, translationMarker);	
	}

	private void addToTranslatedEdgeMap(EObject source, EReference eRef, EObject target, Boolean translationMarker) {
		if(!isTranslatedEdgeMap.containsKey(source)) {
			isTranslatedEdgeMap.put(source,new HashMap<EReference,HashMap<EObject, Boolean>>());
		}
		if(!isTranslatedEdgeMap.get(source).containsKey(eRef)) {
			isTranslatedEdgeMap.get(source).put(eRef,new HashMap<EObject, Boolean>());
		}
		putEdgeInMap(source, eRef, target, translationMarker);	
	}

	
	private void updateTranslatedAttributeMap(Node ruleNodeRHS, EObject graphNodeEObject) {
		// fill isTranslatedAttributeMap
		// scan the contained attributes for <tr>
		for (Attribute ruleAttribute : ruleNodeRHS.getAttributes()) {
			String isMarked=((TAttribute) ruleAttribute).getMarkerType();
			if (RuleUtil.Translated.equals(isMarked)) {
				//mark this attribute to be translated
				putAttributeInMap(graphNodeEObject,ruleAttribute.getType(),true);
			}
		}			
	}
	
	private void putAttributeInMap(EObject graphNodeEObject, EAttribute eAttr, Boolean value) {
		HashMap<EAttribute,Boolean> attrMap = isTranslatedAttributeMap.get(graphNodeEObject);
		if(attrMap==null) {
			System.out.println("!WARNING: Translated attribute map is missing node entry.");
			return;
		}
		attrMap.put(eAttr, value);
	}


	private void updateTranslatedEdgeMap(Node ruleNode, EObject sourceNodeEObject, Match resultMatch) {
		// fill isTranslatedEdgeMap
		EObject targetNodeeObject;
		// scan the outgoing edges for <tr>
		for (Edge ruleEdge : ruleNode.getOutgoing()) {
			if (RuleUtil.Translated.equals( (((TEdge) ruleEdge).getMarkerType()))) {
				Node ruleTarget = ruleEdge.getTarget();
				targetNodeeObject = resultMatch.getNodeTarget(ruleTarget);
				// put edge in hashmap
				putEdgeInMap(sourceNodeEObject,ruleEdge.getType(),targetNodeeObject,true);
				
			}
		}		
	}

	private void putEdgeInMap(			
			EObject sourceNodeEObject, EReference eRef, EObject targetNodeEObject, Boolean value) {
		HashMap<EReference,HashMap<EObject,Boolean>> edgeMap = isTranslatedEdgeMap.get(sourceNodeEObject);
		if(edgeMap==null) {
			System.out.println("Translated edge map is missing node entry.");
			return;
		}
		if(!edgeMap.containsKey(eRef))
			edgeMap.put(eRef,new HashMap<EObject,Boolean>());
		edgeMap.get(eRef).put(targetNodeEObject, value);
		
	}

	
	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#fillTranslatedMaps(java.util.List)
	 */
	@Override
	public void fillTranslatedMaps(List<EObject> inputEObjects) {
		// fills translated maps with all given elements of the graph
		// component(s) that shall be marked (all of inputEObjects)
		
		
		// first, put all eObjects in the marked nodes map
		TreeIterator<EObject> it = null;
		EObject obj = null;
		for (EObject o: inputEObjects){
			isTranslatedNodeMap.put(o, false);
			it = o.eAllContents();
			while(it.hasNext()){
				obj = it.next();
			isTranslatedNodeMap.put(obj, false);
			}
		}

		
		
		// fills translated maps with all given elements of the graph
		// component(s) that shall be marked
		Iterator<EObject> it2 = isTranslatedNodeMap.keySet().iterator();
		EObject o=null;
		while (it2.hasNext()){
			o = it2.next();

			final EList<EStructuralFeature> allEStructFeats = o.eClass().getEAllStructuralFeatures();
			for(EStructuralFeature esf : allEStructFeats)
			{
				// all attributes
				if (esf instanceof EAttribute){
					// if (currentEObject.eIsSet(esf)) // attribute is set // FIXME: check whether necessary
						addToTranslatedAttributeMap(o,(EAttribute)esf,false);
			    }

				// all edges
				if (esf instanceof EReference){
					EReference ref = (EReference) esf;
					Object referenceValue = o.eGet(esf);
					
					if (referenceValue == null){
						// reference is not set, nothing to do
					}
					else if (referenceValue instanceof List) {
						List<Object> references = (List<Object>) referenceValue;
						for (Object targetObj: references){
							if (targetObj instanceof EObject && isTranslatedNodeMap.containsKey(targetObj)) // target eObject has to be marked as well
							addToTranslatedEdgeMap(o,ref,(EObject) targetObj,false);
						}
					}
					else if (referenceValue instanceof EObject){
						if (isTranslatedNodeMap.containsKey(referenceValue)) // target eObject has to be marked as well
							addToTranslatedEdgeMap(o,ref,(EObject) referenceValue,false);
					}
					else{
						System.out.println("!WARNING: transformation initialisation error, references are not a list nor a plain object reference");
					}
			    }
			}	
		}
	}

	
	private void getAllRules(List<Rule> units,IndependentUnit folder){
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				getAllRules(units, (IndependentUnit) unit);
			} else {
				units.add((Rule) unit);
			}
			
		}
	}
	

	/* (non-Javadoc)
	 * @see de.tub.tfs.henshin.tgg.interpreter.impl.TggTransformation#setNullValueMatching(boolean)
	 */
	@Override
	public void setNullValueMatching(boolean matchNullValues2) {
		nullValueMatching=matchNullValues2;
		
	}


}

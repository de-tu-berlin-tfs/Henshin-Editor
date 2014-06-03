package de.tub.tfs.henshin.tgg.interpreter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;

public class Transformation {

	private HashMap<EObject, Boolean> isTranslatedNodeMap= new HashMap<EObject, Boolean>();
	private HashMap<EObject, HashMap<EAttribute,Boolean>> isTranslatedAttributeMap= new HashMap<EObject, HashMap<EAttribute,Boolean>>();
	private HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap = new HashMap<EObject,HashMap<EReference, HashMap<EObject, Boolean>>>();
	private Set<EObject> markedNodes;
	private TGGEngineImpl emfEngine;
	private EGraph graph;
	/**
	 * @return the graph
	 */
	public EGraph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(EGraph graph) {
		this.graph = graph;
	}


	public List<Rule> fTRuleList = new Vector<Rule>();


	
	/**
	 * @return the emfEngine
	 */
	public TGGEngineImpl getEmfEngine() {
		return emfEngine;
	}

	/**
	 * @param emfEngine the emfEngine to set
	 */
	public void setEmfEngine(TGGEngineImpl emfEngine) {
		this.emfEngine = emfEngine;
	}

	public Transformation(EObject inputRoot) {
		
		isTranslatedNodeMap .clear();
		isTranslatedAttributeMap.clear();
		isTranslatedEdgeMap.clear();
		fillTranslatedMaps(inputRoot);
		markedNodes =new HashSet<EObject>(isTranslatedNodeMap.keySet());
		
		TripleGraph g = TggFactory.eINSTANCE.createTripleGraph();
		if (graph != null)
			graph.clear();
		graph = new TggHenshinEGraph(g); 
		graph.addGraph(inputRoot);

		
		
		emfEngine = new TGGEngineImpl(graph) {	
			@Override
			public UnaryConstraint createUserConstraints(Attribute attribute) {
				return new OpRuleAttributeConstraintEMF(attribute, markedNodes,
						isTranslatedNodeMap, isTranslatedAttributeMap);
			}

			@Override
			public BinaryConstraint createUserConstraints(Edge edge) {
				return new OpRuleEdgeConstraintEMF(edge, markedNodes,
						isTranslatedEdgeMap);
			}

			@Override
			public UnaryConstraint createUserConstraints(Node node) {
				return new OpRuleNodeConstraintEMF(node, markedNodes,
						isTranslatedNodeMap);
			}
		};


	}
	
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
				for (Rule rule : fTRuleList) {
					startTimeOneStep=System.nanoTime();
					monitor.subTask(msg + " (" + rule.getName() + ")");
					currentRule=rule;
					ruleApplication = new RuleApplicationImpl(emfEngine);
					ruleApplication.setEGraph(graph);
					ruleApplication.setRule(rule);
					/*
					 * Apply a rule as long as it's possible and add each successful
					 * application to ruleApplicationlist. Then fill the
					 * isTranslatedTable Start with a fresh match.
					 */
					Boolean matchesToCheck = true;
					while (matchesToCheck) {
						Iterator<Match> matchesIterator = emfEngine
								.findMatches(rule, graph, new MatchImpl(rule))
								.iterator();
						if (!matchesIterator.hasNext()) {
							matchesToCheck = false;
						}
						while (matchesIterator.hasNext()) {
							ruleApplication.setPartialMatch(matchesIterator
									.next());
							try {
								foundApplication = executeOneStep(ruleApplication,
										foundApplication, rule);
								if (foundApplication) System.out.println("Executed: "+ rule.getName());
							} catch (RuntimeException e){
								matchesToCheck = false;
							}
						}
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
		System.out.print("List of FT rules: ");
		for(Rule r:fTRuleList){
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
				if (RuleUtil.Translated.equals(ruleNodeRHS.getMarkerType())) {
					isTranslatedNodeMap.put(nodeEObject, true);
					fillTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					fillTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				} else {
					// context node, thus check whether the edges
					// and attributes are translated
					fillTranslatedAttributeMap(ruleNodeRHS, nodeEObject);
					fillTranslatedEdgeMap(ruleNodeRHS, nodeEObject, resultMatch);
				}
			}
			emfEngine.postProcess(resultMatch);
		} else {
			throw new RuntimeException("Match NOT applicable!");
		}
		return foundApplication;
	}

	private void fillTranslatedAttributeMap(Node ruleNodeRHS, EObject graphNodeEObject) {
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
			System.out.println("Translated attribute map is missing node entry.");
			return;
		}
		attrMap.put(eAttr, value);
	}


	private void fillTranslatedEdgeMap(Node ruleNode, EObject sourceNodeEObject, Match resultMatch) {
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

	
	public void fillTranslatedMaps(EObject inputRoot) {
		// fills translated maps with all given elements of the graph
		// component(s) that shall be marked (all of input graph)
		
		
		// initially fill isTranslatedNodeMap with all source nodes to be not yet translated 
		// and put them also in the isTranslatedAttributeMap as keys
		TreeIterator<EObject> graphNodesIterator = inputRoot.eAllContents();
		EObject currentEObject;
		isTranslatedNodeMap.put(inputRoot, false);
		isTranslatedAttributeMap.put(inputRoot, new HashMap<EAttribute,Boolean>());
		isTranslatedEdgeMap.put(inputRoot, new HashMap<EReference,HashMap<EObject,Boolean>>());

		while(graphNodesIterator.hasNext()){
			currentEObject= graphNodesIterator.next();
			isTranslatedNodeMap.put(currentEObject, false);
			// initially put them in the isTranslatedAttributeMap and isTranslatedEdgeMap as keys
			isTranslatedAttributeMap.put(currentEObject, new HashMap<EAttribute,Boolean>());
			isTranslatedEdgeMap.put(currentEObject, new HashMap<EReference,HashMap<EObject,Boolean>>());

		}
	}

	
	protected void getAllRules(List<Rule> units,IndependentUnit folder){
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				getAllRules(units, (IndependentUnit) unit);
			} else {
				units.add((Rule) unit);
			}
			
		}
	}
	
	public void addFTRules(Module module) {

		if (module == null)
			return;
		String name_OP_RULE_FOLDER = "FTRuleFolder";
		IndependentUnit opRuleFolder = (IndependentUnit) module.getUnit(name_OP_RULE_FOLDER);
		getAllRules(fTRuleList, opRuleFolder);
	}


}

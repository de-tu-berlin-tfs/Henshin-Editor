package de.tub.tfs.henshin.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.emf.henshin.model.Xor;

import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.cons.AtomConstraint;
import agg.parser.CriticalPair;
import agg.parser.CriticalPairData;
import agg.parser.CriticalPairOption;
import agg.parser.ExcludePairContainer;
import agg.parser.InvalidAlgorithmException;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.ArcTypeImpl;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.Completion_NAC;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeGraph;
import agg.xt_basis.TypeSet;

public class AggInfo {
	protected Module emfGrammar;
	
	protected GraGra aggGrammar;
	protected TypeSet aggTypeSet;
	protected TypeGraph aggTypeGraph;
	
	protected Map<EClass, Type> nodeTypeMap;
	protected Map<EReference, Type> edgeTypeMap;
	protected Map<Rule,agg.xt_basis.Rule> henshinRuleToAGGRuleConversion = new HashMap<Rule, agg.xt_basis.Rule>();
	protected Map<agg.xt_basis.Rule,Rule> aggRuleToHenshinRuleConversion = new HashMap< agg.xt_basis.Rule,Rule>();
	
	protected Map<Object,Object> aggToHenshinConversionMap = new HashMap<Object, Object>();
	protected Map<Object,Object> henshinToAggConversionMap = new HashMap<Object, Object>();
	
	private Map<de.tub.tfs.henshin.analysis.CriticalPair, List<EObject>> criticalPairToCriticalEObjects = new HashMap<de.tub.tfs.henshin.analysis.CriticalPair, List<EObject>>();

	private LinkedList<EPackage> excludePackages = new LinkedList<EPackage>();

	private List<EClassifier> excludeClassMap = new LinkedList<EClassifier>();
	
	private EList<Rule> getRules(Module m) {
		EList<Rule> rules = new BasicEList<Rule>();
		for (Unit unit : m.getUnits()) {
			if (unit instanceof Rule) {
				rules.add((Rule) unit);
			}
		}
		return ECollections.unmodifiableEList(rules);
	}

	
	public AggInfo(Module ts) {
		aggTypeSet = new TypeSet();
		aggTypeGraph = (TypeGraph) aggTypeSet.createTypeGraph();
		
		aggGrammar = new GraGra(aggTypeSet);
		aggGrammar.setGraTraOptions(new Completion_NAC(new Completion_InjCSP()));
		// agg.xt_basis.Graph graph = aggGrammar.getGraph();
		// aggGrammar.removeGraph(graph);
		
		emfGrammar = ts;
		nodeTypeMap = new HashMap<EClass, Type>();
		edgeTypeMap = new HashMap<EReference, Type>();
		
		for (EPackage package1 : excludePackages) {
			excludeClassMap .addAll( package1.getEClassifiers());
			
		}
		
		convertImports(ts.getImports());
		
		convertRules(getRules(ts));
	}
	
	private void convertRules(Collection<Rule> rules) {
		for (Rule rule : rules) {
			agg.xt_basis.Rule aggRule = createRule(rule);
			henshinRuleToAGGRuleConversion.put(rule, aggRule);
			aggRuleToHenshinRuleConversion.put(aggRule, rule);
		}
	}
	
	private agg.xt_basis.Rule createRule(Rule henshinRule) {
		agg.xt_basis.Rule aggRule = aggGrammar.createRule();
		aggRule.setName(henshinRule.getName());
		Map<Node, agg.xt_basis.Node> lhsNodeMap = fillGraph(henshinRule.getLhs(), aggRule.getLeft());
		Map<Node, agg.xt_basis.Node> rhsNodeMap = fillGraph(henshinRule.getRhs(),
				aggRule.getRight());
		
		for (Mapping mapping : henshinRule.getMappings()) {
			agg.xt_basis.Node aggLhsNode = lhsNodeMap.get(mapping.getOrigin());
			agg.xt_basis.Node aggRhsNode = rhsNodeMap.get(mapping.getImage());
			aggRule.addMapping(aggLhsNode, aggRhsNode);
		}
		
		for (Entry<Node, agg.xt_basis.Node> entry : lhsNodeMap.entrySet()) {
			aggToHenshinConversionMap.put(entry.getValue(),entry.getKey());
			henshinToAggConversionMap.put(entry.getKey(),entry.getValue());
		}
		for (Entry<Node, agg.xt_basis.Node> entry : rhsNodeMap.entrySet()) {
			aggToHenshinConversionMap.put(entry.getValue(),entry.getKey());
			henshinToAggConversionMap.put(entry.getKey(),entry.getValue());
		}
		// maps edges between nodes
		aggRule.nextCompletion();
		
		// translate Henshin ACs into NACs and PACs
		// WARNING: if there are nestings or ORs or NOTs they will be ignored
		// and
		// the semantics of the rule will change.
		convertAC(aggRule, lhsNodeMap, normalize(henshinRule.getLhs().getFormula()));
		return aggRule;
	}
	
	private void convertAC(agg.xt_basis.Rule aggRule, Map<Node, agg.xt_basis.Node> nodeMap,
			Formula formula) {
		if (formula instanceof And) {
			convertAC(aggRule, nodeMap, ((And) formula).getLeft());
			convertAC(aggRule, nodeMap, ((And) formula).getRight());
		} else if (formula instanceof NestedCondition) {
			NestedCondition henshinAC = (NestedCondition) formula;
			OrdinaryMorphism aggAC;
			if (henshinAC.eContainer() instanceof Not)
				aggAC = aggRule.createNAC();
			else
				aggAC = aggRule.createPAC();
			Map<Node, agg.xt_basis.Node> acNodeMap = fillGraph(henshinAC.getConclusion(),
					aggAC.getImage());
			
			for (Mapping mapping : henshinAC.getMappings()) {
				agg.xt_basis.Node aggLhsNode = nodeMap.get(mapping.getOrigin());
				agg.xt_basis.Node aggRhsNode = acNodeMap.get(mapping.getImage());
				aggAC.addMapping(aggLhsNode, aggRhsNode);
			}
		
			// maps edges between nodes
			aggAC.nextCompletion();
		}
		if (formula instanceof Not){
			convertAC(aggRule, nodeMap, ((Not) formula).getChild());
		}
	}
	
	private Map<Node, agg.xt_basis.Node> fillGraph(Graph henshinGraph, agg.xt_basis.Graph aggGraph) {
		Map<Node, agg.xt_basis.Node> graphNodeMap = new HashMap<Node, agg.xt_basis.Node>();
		for (Node node : henshinGraph.getNodes()) {
			if (excludeClassMap.contains(node.eClass()))
				continue;
			try {
				if (nodeTypeMap.get(node.getType()) == null && node.getType().getEPackage() != null){
					convertImports(Arrays.asList(node.getType().getEPackage()));
					
				}
				agg.xt_basis.Node aggNode = aggGraph.createNode(nodeTypeMap.get(node.getType()));
				for (Attribute attribute : node.getAttributes()) {
					aggNode.getAttribute().setExprAt(attribute.getValue(),
							attribute.getType().getName());
				}
				graphNodeMap.put(node, aggNode);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		for (Edge edge : henshinGraph.getEdges()) {
			try {
				Arc aggEdge = aggGraph.createArc(edgeTypeMap.get(edge.getType()),
						graphNodeMap.get(edge.getSource()), graphNodeMap.get(edge.getTarget()));
				aggToHenshinConversionMap.put(aggEdge, edge);
				henshinToAggConversionMap.put(edge, aggEdge);
			} catch (Exception e) {
				System.err.println(e);
			}
		}
		
		return graphNodeMap;
	}
	
	private void convertImports(List<EPackage> imports) {
		
		for (EPackage ePackage : imports) {
			if (ePackage.getNsURI() == null)
				throw new RuntimeException("Could not find Package " + ((EPackageImpl)ePackage).eProxyURI());
			createTypeNodes(ePackage);
		}
		
		aggTypeSet.getTypeGraph();
		aggTypeSet.setLevelOfTypeGraph(TypeSet.ENABLED_MAX);
		createTypeFeatures();
		for (Entry<EClass, Type> entry : nodeTypeMap.entrySet()) {
			aggToHenshinConversionMap.put(entry.getValue(), entry.getKey());
			henshinToAggConversionMap.put(entry.getKey(),entry.getValue());
		}
		for (Entry<EReference, Type> entry : edgeTypeMap.entrySet()) {
			aggToHenshinConversionMap.put(entry.getValue(), entry.getKey());
			henshinToAggConversionMap.put(entry.getKey(),entry.getValue());
		}
		
	}
	
	private void createTypeFeatures() {
		AttrHandler handler = new JexHandler();
		
		for (EClass emfType : nodeTypeMap.keySet()) {
			if (excludeClassMap.contains(emfType))
				continue;
			
			Type aggNodeType = nodeTypeMap.get(emfType);
			AttrType attr = aggNodeType.getAttrType();
			
			// add inheritance relation
			for (EClass emfParentType : emfType.getESuperTypes()) {
				Type aggParentType = nodeTypeMap.get(emfParentType);
				if (aggParentType != null) {
					aggTypeSet.addInheritanceRelation(aggNodeType, aggParentType);
				}
			}
			
			// add attributes to types
			for (EAttribute emfAttribute : emfType.getEAttributes()) {
				String dataType = EcoreUtil.toJavaInstanceTypeName(emfAttribute.getEGenericType());
				System.out.println("DEBUG: " + dataType);
				if (dataType.indexOf('<') != -1)
					dataType = dataType.substring(0, dataType.indexOf('<'));
				AttrTypeMember member = attr.addMember(handler,dataType , emfAttribute.getName());
				if (member.getType() == null)
					System.out.println("DEBUG: " + member.getType());
				if (emfAttribute.getDefaultValue() != null)
					aggNodeType.getTypeGraphNodeObject().getAttribute().setValueAt(emfAttribute.getDefaultValue(), emfAttribute.getName());
				else
					aggNodeType.getTypeGraphNodeObject().getAttribute().setExprValueAt("null", emfAttribute.getName());
			}
			
			// add edge types
			for (EReference emfEdgeType : emfType.getEReferences()) {
				
				
				Type aggEdgeType = aggTypeSet.getTypeByName(emfEdgeType.getName());
				
				if (aggEdgeType == null)
					aggEdgeType = aggTypeSet.createArcType(true);
				
				EClass emfTargetType = (EClass) emfEdgeType.getEType();
				Type aggTargetType = nodeTypeMap.get(emfTargetType);
				
				if (aggTargetType != null) {
					
					
					agg.xt_basis.Node aggSourceNode = aggTypeSet.getTypeGraphNode(aggNodeType);
					agg.xt_basis.Node aggTargetNode = aggTypeSet.getTypeGraphNode(aggTargetType);
					aggEdgeType.setStringRepr(emfEdgeType.getName());
					aggEdgeType
					.setAdditionalRepr(":SOLID_LINE::java.awt.Color[r=0,g=0,b=0]::[EDGE]:");
					System.out.println("DEBUG: added "  + aggSourceNode.getType().getName() + "---" + aggEdgeType + "---> " +  aggTargetNode.getType().getName());
					try {
						Vector<Arc> arcs = aggTypeGraph.getArcs(aggEdgeType, aggSourceNode, aggTargetNode);
						if (!arcs.isEmpty())
							continue;
						Arc arc = aggTypeGraph.createArc(aggEdgeType, aggSourceNode, aggTargetNode);
						aggEdgeType.setTargetMax(aggNodeType, aggTargetType,
								emfEdgeType.isMany() ? -1 : 1);
						henshinToAggConversionMap.put(new de.tub.tfs.henshin.analysis.Pair<EClass,EClass>(emfEdgeType.getEContainingClass(),(EClass)emfEdgeType.getEType() ), arc);
					} catch (TypeException e) {
						e.printStackTrace();
					}
					
					edgeTypeMap.put(emfEdgeType, aggEdgeType);
				}
				
				
			}
		}
		
		for (EClass emfType : nodeTypeMap.keySet()) {
			for (EReference emfEdgeType : emfType.getEReferences()) {
				if (emfEdgeType.getEOpposite() != null) {
					AtomConstraint opCons = aggGrammar.createAtomic("op_" + emfEdgeType.getName());
					// opCons = opCons.createNextConclusion(new
					// agg.xt_basis.Graph());
					agg.xt_basis.Graph premise = opCons.getOriginal();
					opCons = opCons.getConclusion(0);
					agg.xt_basis.Graph conclusion = opCons.getImage();
					try {
						agg.xt_basis.Node pSourceNode = premise
								.createNode(nodeTypeMap.get(emfType));
						agg.xt_basis.Node pTargetNode = premise.createNode(nodeTypeMap
								.get(emfEdgeType.getEType()));
						Arc pArc = premise.createArc(edgeTypeMap.get(emfEdgeType), pSourceNode,
								pTargetNode);
						
						agg.xt_basis.Node cSourceNode = conclusion.createNode(nodeTypeMap
								.get(emfType));
						agg.xt_basis.Node cTargetNode = conclusion.createNode(nodeTypeMap
								.get(emfEdgeType.getEType()));
						Arc cArc = conclusion.createArc(edgeTypeMap.get(emfEdgeType), cSourceNode,
								cTargetNode);
						Arc cArc2 = conclusion.createArc(
								edgeTypeMap.get(emfEdgeType.getEOpposite()), cTargetNode,
								cSourceNode);
						opCons.addMapping(pSourceNode, cSourceNode);
						opCons.addMapping(pTargetNode, cTargetNode);
						opCons.addMapping(pArc, cArc);
						
					} catch (TypeException e) {
						
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (emfEdgeType.isContainment()) {
					AtomConstraint opCons = aggGrammar
							.createAtomic("cont_" + emfEdgeType.getName());
					agg.xt_basis.Graph premise = opCons.getOriginal();
					opCons = opCons.getConclusion(0);
					agg.xt_basis.Graph conclusion = opCons.getImage();
					
					agg.xt_basis.Node cSourceNode1;
					try {
						cSourceNode1 = conclusion.createNode(nodeTypeMap.get(emfType));
						agg.xt_basis.Node cSourceNode2 = conclusion.createNode(nodeTypeMap
								.get(emfType));
						agg.xt_basis.Node cTargetNode = conclusion.createNode(nodeTypeMap
								.get(emfEdgeType.getEType()));
						Arc cArc = conclusion.createArc(edgeTypeMap.get(emfEdgeType), cSourceNode1,
								cTargetNode);
						Arc cArc2 = conclusion.createArc(edgeTypeMap.get(emfEdgeType),
								cSourceNode2, cTargetNode);
					} catch (TypeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				if (emfEdgeType.isMany()) {
					AtomConstraint opCons = aggGrammar.createAtomic("p_" + emfEdgeType.getName());
					// opCons = opCons.createNextConclusion(new
					// agg.xt_basis.Graph());
					agg.xt_basis.Graph premise = opCons.getOriginal();
					opCons = opCons.getConclusion(0);
					agg.xt_basis.Graph conclusion = opCons.getImage();
					try {
						// agg.xt_basis.Node pSourceNode =
						// premise.createNode(nodeTypeMap.get(emfType));
						// agg.xt_basis.Node pTargetNode =
						// premise.createNode(nodeTypeMap.get(emfEdgeType.getEType()));
						// Arc pArc =
						// premise.createArc(edgeTypeMap.get(emfEdgeType),
						// pSourceNode, pTargetNode);
						
						agg.xt_basis.Node cSourceNode = conclusion.createNode(nodeTypeMap
								.get(emfType));
						agg.xt_basis.Node cTargetNode = conclusion.createNode(nodeTypeMap
								.get(emfEdgeType.getEType()));
						Arc cArc = conclusion.createArc(edgeTypeMap.get(emfEdgeType), cSourceNode,
								cTargetNode);
						Arc cArc2 = conclusion.createArc(edgeTypeMap.get(emfEdgeType), cSourceNode,
								cTargetNode);
						// opCons.addMapping(pSourceNode, cSourceNode);
						// opCons.addMapping(pTargetNode, cTargetNode);
						// opCons.addMapping(pArc, cArc);
						
					} catch (TypeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		if (!aggGrammar.getListOfAtomics().isEmpty()){
			String form = (aggGrammar.getListOfAtomics().get(0).getName().startsWith("op_") ? "" : "!")
					+ "1";
			for (int i = 2; i <= aggGrammar.getListOfAtomics().size(); i++) {
				form += "&"
						+ (aggGrammar.getListOfAtomics().get(i - 1).getName().startsWith("op_") ? ""
								: "!") + i;
			}
			
			agg.cons.Formula formula = aggGrammar.createConstraint("Constraints");
			System.out.println(form);
			formula.setFormula(aggGrammar.getListOfAtomicObjects(), form);
		}
	}
	
	private void createTypeNodes(EPackage ePackage) {
		TypeGraph aggTypeGraph = (TypeGraph) aggTypeSet.getTypeGraph();
		
		EClass eObjectType = EcoreFactory.eINSTANCE.createEObject().eClass();
		Type aggEObjectType = nodeTypeMap.get(eObjectType);
		if (aggEObjectType == null) {
			aggEObjectType = aggTypeSet.createNodeType(true);
			aggEObjectType.setStringRepr(eObjectType.getName());
			aggEObjectType.setAdditionalRepr(":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:");
			try {
				aggTypeGraph.createNode(aggEObjectType);
			} catch (TypeException e) {
				System.err.println(e);
			}
			
			nodeTypeMap.put(eObjectType, aggEObjectType);
		}
		
		// create nodes
		for (EClassifier emfType : ePackage.getEClassifiers()) {
			if (excludeClassMap.contains(emfType))
				continue;
			
			// create AGG type nodes
			if (emfType instanceof EClass) {
				if (nodeTypeMap.get(emfType) != null){
					continue;
				}
				Type aggType = aggTypeSet.createNodeType(true);
				aggType.setStringRepr(emfType.getName());
				aggType.setAdditionalRepr(":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:");
				try {
					aggType.addParent(aggEObjectType);
					aggTypeGraph.createNode(aggType);
					
				} catch (TypeException e) {
					System.err.println(e);
				}
				
				nodeTypeMap.put((EClass) emfType, aggType);
			}
		}
	}
	
	public GraGra getAggGrammar() {
		return aggGrammar;
	}
	
	public TypeSet getTypeSet() {
		return aggTypeSet;
	}
	
	public TypeGraph getTypeGraph() {
		return aggTypeGraph;
	}
	
	public Map<EClass, Type> getNodeTypeMap() {
		return nodeTypeMap;
	}
	
	public Map<EReference, Type> getEdgeTypeMap() {
		return edgeTypeMap;
	}
	
	public Module getEmfGrammar() {
		return emfGrammar;
	}
	
	private Map<Rule,Rule> createControlflow(){
		
		return null;
	}
	
	public boolean isCritical(CausalityType causalityType, Rule r1,Rule r2,Unit... context){
		return false;
	}	
	
	public boolean isDependentOn(Rule r1,Rule r2){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		de.tub.tfs.henshin.analysis.DependencyPairContainer conflictContainer = new de.tub.tfs.henshin.analysis.DependencyPairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();

		if (!rule1.isReadyToTransform())
			System.out.println(rule1.getErrorMsg());

		if (!rule2.isReadyToTransform())
			System.out.println(rule2.getErrorMsg());
		
		CriticalPairOption cpOption = new CriticalPairOption();
		cpOption.setCriticalPairAlgorithm(CriticalPair.TRIGGER_DEPENDENCY);
		cpOption.enableLayered(false);
		cpOption.enableConsistent(true);
		cpOption.enableStrongAttrCheck(true);
		
		conflictContainer.enableComplete(cpOption.completeEnabled());
		conflictContainer.enableReduce(cpOption.reduceEnabled());
		conflictContainer.enableConsistent(cpOption.consistentEnabled());
		conflictContainer.enableStrongAttrCheck(cpOption.strongAttrCheckEnabled());
		conflictContainer.enableEqualVariableNameOfAttrMapping(cpOption.equalVariableNameOfAttrMappingEnabled());
		conflictContainer.enableIgnoreIdenticalRules(cpOption.ignoreIdenticalRulesEnabled());
		conflictContainer.enableReduceSameMatch(cpOption.reduceSameMatchEnabled());
		
		
		conflictContainer.computeCriticalPair(rule1, rule2);
		criticalPair = conflictContainer.getDependencyContainer().get(rule1).get(rule2).second;
		if (criticalPair == null)
			return false;
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		
		return !data.hasCriticals();
	}
	
	public boolean isCritical(Rule r1,Rule r2){
		return isInConflict(r1, r2);
	}
	
	public boolean isInConflict(Rule r1,Rule r2){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		
		ConflictPairContainer conflictContainer = new ConflictPairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();
		
		CriticalPairOption cpOption = new CriticalPairOption();
		cpOption.setCriticalPairAlgorithm(CriticalPair.CONFLICT);
		cpOption.enableLayered(false);
		cpOption.enableConsistent(true);  //olga - do take Graph consistency constraints in account
		cpOption.enableStrongAttrCheck(true); //olga - do check attribute assignments more
		
		conflictContainer.enableComplete(cpOption.completeEnabled());
		conflictContainer.enableReduce(cpOption.reduceEnabled());
		conflictContainer.enableConsistent(cpOption.consistentEnabled());
		conflictContainer.enableStrongAttrCheck(cpOption.strongAttrCheckEnabled());
		conflictContainer.enableEqualVariableNameOfAttrMapping(cpOption.equalVariableNameOfAttrMappingEnabled());
		conflictContainer.enableIgnoreIdenticalRules(cpOption.ignoreIdenticalRulesEnabled());
		conflictContainer.enableReduceSameMatch(cpOption.reduceSameMatchEnabled());
		
		
		conflictContainer.computeCriticalPair(rule1, rule2);
		criticalPair = conflictContainer.getConflictContainer().get(rule1).get(rule2).second;
		if (criticalPair == null)
			return false;
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		
		return !data.hasCriticals();
	}
	
	public List<de.tub.tfs.henshin.analysis.CriticalPair> getConflictOverlappings(Rule r1,Rule r2,Unit... context){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		
		ConflictPairContainer conflictContainer = new ConflictPairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();
		
		CriticalPairOption cpOption = new CriticalPairOption();
		cpOption.setCriticalPairAlgorithm(CriticalPair.CONFLICT);
		//cpOption.enableLayered(false);
		//cpOption.enableConsistent(true);  //olga
		//cpOption.enableStrongAttrCheck(true); //olga
		
		conflictContainer.enableComplete(cpOption.completeEnabled());
		conflictContainer.enableReduce(cpOption.reduceEnabled());
		conflictContainer.enableConsistent(cpOption.consistentEnabled());
		conflictContainer.enableStrongAttrCheck(cpOption.strongAttrCheckEnabled());
		conflictContainer.enableEqualVariableNameOfAttrMapping(cpOption.equalVariableNameOfAttrMappingEnabled());
		conflictContainer.enableIgnoreIdenticalRules(cpOption.ignoreIdenticalRulesEnabled());
		conflictContainer.enableReduceSameMatch(cpOption.reduceSameMatchEnabled());
		
		
		conflictContainer.computeCriticalPair(rule1, rule2);
		criticalPair = conflictContainer.getConflictContainer().get(rule1).get(rule2).second;
		if (criticalPair == null)
			return Collections.EMPTY_LIST;
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		LinkedList<de.tub.tfs.henshin.analysis.CriticalPair> pairs = new LinkedList<de.tub.tfs.henshin.analysis.CriticalPair>();
		while(data.next()){
			de.tub.tfs.henshin.analysis.CriticalPair pair = createCriticalPair(data,context);
			pairs.add(pair);
		}
		return pairs;
	}
	
	
	public List<de.tub.tfs.henshin.analysis.CriticalPair> getDependencyOverlappings(Rule r1,Rule r2,Unit... context){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		de.tub.tfs.henshin.analysis.DependencyPairContainer conflictContainer = new de.tub.tfs.henshin.analysis.DependencyPairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();
		
		
		CriticalPairOption cpOption = new CriticalPairOption();
		cpOption.setCriticalPairAlgorithm(CriticalPair.TRIGGER_DEPENDENCY);
		cpOption.enableLayered(false);
		cpOption.enableConsistent(true);  //olga
		cpOption.enableStrongAttrCheck(true); //olga
		
		conflictContainer.enableComplete(cpOption.completeEnabled());
		conflictContainer.enableReduce(cpOption.reduceEnabled());
		conflictContainer.enableConsistent(cpOption.consistentEnabled());
		conflictContainer.enableStrongAttrCheck(cpOption.strongAttrCheckEnabled());
		conflictContainer.enableEqualVariableNameOfAttrMapping(cpOption.equalVariableNameOfAttrMappingEnabled());
		conflictContainer.enableIgnoreIdenticalRules(cpOption.ignoreIdenticalRulesEnabled());
		conflictContainer.enableReduceSameMatch(cpOption.reduceSameMatchEnabled());
		
		
		conflictContainer.computeCriticalPair(rule1, rule2);
		criticalPair = conflictContainer.getDependencyContainer().get(rule1).get(rule2).second;
		if (criticalPair == null)
			return Collections.EMPTY_LIST;
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		LinkedList<de.tub.tfs.henshin.analysis.CriticalPair> pairs = new LinkedList<de.tub.tfs.henshin.analysis.CriticalPair>();
		while(data.next()){
			de.tub.tfs.henshin.analysis.CriticalPair pair = createCriticalPair(data,context);
			pairs.add(pair);
		}
		return pairs;
	}
	
	protected agg.xt_basis.Rule getAGGRule(Rule rule){
		agg.xt_basis.Rule aggRule = this.henshinRuleToAGGRuleConversion.get(rule);
		if (aggRule == null){			
			aggRule = createRule(rule);
			henshinRuleToAGGRuleConversion.put(rule, aggRule);
			aggRuleToHenshinRuleConversion.put(aggRule, rule);
		}
		return aggRule;
	}
	
	private de.tub.tfs.henshin.analysis.CriticalPair createCriticalPair(CriticalPairData data, Unit... context) {
		de.tub.tfs.henshin.analysis.CriticalPair result = AnalysisFactory.eINSTANCE.createCriticalPair();
		if (context.length > 0){
			result.setSourceUnit(context[0]);
		}
		if (context.length > 1){
			result.setTargetUnit(context[1]);
		}
		result.setType(CriticalPairType.get(data.getKindOfCurrentCritical()));
		result.setRule1(aggRuleToHenshinRuleConversion.get(data.getRule1()));
		result.setRule2(aggRuleToHenshinRuleConversion.get(data.getRule2()));
		HashMap<agg.xt_basis.Node, Node> aggNodeToHeshinNodeConversion = new HashMap<agg.xt_basis.Node, Node>();
		HashMap<Arc, Edge> aggArcToHenshinEdgeConversion = new HashMap<Arc, Edge>();
		Graph conflictGraph = convertConflictGraph(data,aggNodeToHeshinNodeConversion,aggArcToHenshinEdgeConversion,result);
		result.setOverlapping(conflictGraph);
		aggToHenshinConversionMap.putAll(aggArcToHenshinEdgeConversion);
		aggToHenshinConversionMap.putAll(aggNodeToHeshinNodeConversion);
		LinkedList<Mapping> mappingsR1ToCG = new LinkedList<Mapping>();
		LinkedList<Mapping> mappingsR2ToCG = new LinkedList<Mapping>();
		LinkedList<Mapping> mappingsR1ToR2 = new LinkedList<Mapping>();
		HashMap<Node,Node> cGToRule1Map = new HashMap<Node, Node>();
		OrdinaryMorphism morph1 = data.getMorph1();
		for (int i = 0 ;i < morph1.getDomainObjects().size();i++) {
			
			Object obj1 = aggToHenshinConversionMap.get(morph1.getDomainObjects().get(i));
			if (obj1 == null || !(obj1 instanceof Node))
				continue;
			Node source = (Node) obj1;
			Node target = (Node) aggToHenshinConversionMap.get(morph1.getCodomainObjects().get(i));
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping(source,target);
			mappingsR1ToCG.add(mapping);
			cGToRule1Map.put(target, source);
		}
		
		
		OrdinaryMorphism morph2 = data.getMorph2DueToLHS();
		for (int i = 0 ;i < morph2.getDomainObjects().size();i++) {
			
			Object obj1 = aggToHenshinConversionMap.get(morph2.getDomainObjects().get(i));
			if (obj1 == null || !(obj1 instanceof Node))
				continue;
			Node source = (Node) obj1;
			Node target = (Node) aggToHenshinConversionMap.get(morph2.getCodomainObjects().get(i));
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping(source,target);
			mappingsR2ToCG.add(mapping);
			if (cGToRule1Map.containsKey(target)){
				Mapping mapping2 = HenshinFactory.eINSTANCE.createMapping(cGToRule1Map.get(target),source);
				mappingsR1ToR2.add(mapping2);
			}
			
		}

		OrdinaryMorphism morph3 = data.getMorph2DueToNAC();
		for (int i = 0 ;morph3 != null && i < morph3.getDomainObjects().size();i++) {
			
			Object obj1 = aggToHenshinConversionMap.get(morph3.getDomainObjects().get(i));
			if (obj1 == null || !(obj1 instanceof Node))
				continue;
			Node source = (Node) obj1;
			Node target = (Node) aggToHenshinConversionMap.get(morph3.getCodomainObjects().get(i));
			Mapping mapping = HenshinFactory.eINSTANCE.createMapping(source,target);
			mappingsR2ToCG.add(mapping);
			//if (cGToRule1Map.containsKey(target)){
			//	Mapping mapping2 = HenshinFactory.eINSTANCE.createMapping(cGToRule1Map.get(target),source);
			//	mappingsR1ToR2.add(mapping2);
			//}
			
		}
		
		result.getMappingsOverlappingToRule1().addAll(mappingsR1ToCG);
		result.getMappingsOverlappingToRule2().addAll(mappingsR2ToCG);
		result.getMappingsRule1ToRule2().addAll(mappingsR1ToR2);
		
		convertCriticalObjects(data.getCriticalGraphObjects(), result);
		
		return result;
		
	}
	
	private void convertCriticalObjects(List<GraphObject> criticalGraphObjects, de.tub.tfs.henshin.analysis.CriticalPair criticalPair) {
		
		List<EObject> criticalObjects = new ArrayList<EObject>();
		
		for (GraphObject o : criticalGraphObjects) {
			Object object = aggToHenshinConversionMap.get(o);
			if (object instanceof EObject) {
				criticalObjects.add((EObject)object);
			}
		}
		
		criticalPairToCriticalEObjects.put(criticalPair, criticalObjects);
	}
	
	public Map<de.tub.tfs.henshin.analysis.CriticalPair, List<EObject>> getCriticalObjects() {
		return criticalPairToCriticalEObjects;
	}
	
	private Graph convertConflictGraph(CriticalPairData data,
			HashMap<agg.xt_basis.Node, Node> nodeConversion,
			HashMap<Arc, Edge> arcConversion,de.tub.tfs.henshin.analysis.CriticalPair p) {
		Graph graph = HenshinFactory.eINSTANCE.createGraph();
		graph.setName(data.getCriticalGraph().getName());
		agg.xt_basis.Graph criticalGraph = data.getCriticalGraph();
		for (agg.xt_basis.Node aggNode : criticalGraph.getNodesSet()) {
			Node node = HenshinFactory.eINSTANCE.createNode();
			node.setName(aggNode.getObjectName());
			node.setType((EClass) this.aggToHenshinConversionMap.get(aggNode.getType()));
			graph.getNodes().add(node);
			nodeConversion.put(aggNode, node);
			if (aggNode.isCritical())
				p.getCriticalObjects().add(node);
			int numberOfEntries = aggNode.getAttribute().getNumberOfEntries();
			for (int i = 0; i < numberOfEntries; i++) {
				String attrName = aggNode.getAttribute().getNameAsString(i);
				if (!aggNode.getAttribute().isValueSetAt(attrName))
					continue;
				
				String value = aggNode.getAttribute().getValueAsString(i);
				
				for (EAttribute attr : node.getType().getEAllAttributes()) {
					if (attr.getName().equals(attrName)){
						Attribute attribute = HenshinFactory.eINSTANCE.createAttribute();
						attribute.setType(attr);
						attribute.setValue(value);
						node.getAttributes().add(attribute);
						break;
					}
				}
				
				
			}
		}
		for (Arc aggArc : criticalGraph.getArcsSet()) {
			Edge edge = HenshinFactory.eINSTANCE.createEdge();
			edge.setSource(nodeConversion.get(aggArc.getSource()));
			edge.setTarget(nodeConversion.get(aggArc.getTarget()));
			edge.setType((EReference) aggToHenshinConversionMap.get(aggArc.getType()));
			graph.getEdges().add(edge);
			arcConversion.put(aggArc, edge);
			if (aggArc.isCritical())
				p.getCriticalObjects().add(edge);
		}
		
		return graph;
	}
	
	public Map<Integer,Integer> getConflictType(Rule r1,Rule r2){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		
		ExcludePairContainer conflictContainer = new ExcludePairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();
		try {
			criticalPair = conflictContainer.getCriticalPair(rule1, rule2, CriticalPair.EXCLUDE);
		} catch (InvalidAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		HashMap<Integer,Integer> result = new HashMap<Integer,Integer>();
		for (int i = 0; i <= 12; i++) {
			int size = data.getCriticalDataOfKind(i).getSize();
			if (size > 0){
				result.put(i,size);
			}
		}
		return result;
	}
	
	
	public Map<Integer,Integer> getDepencyType(Rule r1,Rule r2){
		agg.xt_basis.Rule rule1 = getAGGRule(r1);
		agg.xt_basis.Rule rule2 = getAGGRule(r2);
		
		ExcludePairContainer conflictContainer = new ExcludePairContainer(getAggGrammar());
		Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> criticalPair = new Vector<Pair<Pair<OrdinaryMorphism,OrdinaryMorphism>,Pair<OrdinaryMorphism,OrdinaryMorphism>>>();
		try {
			criticalPair = conflictContainer.getCriticalPair(rule1, rule2, CriticalPair.EXCLUDE);
		} catch (InvalidAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CriticalPairData data = new CriticalPairData(rule1,rule2,criticalPair);
		HashMap<Integer,Integer> result = new HashMap<Integer,Integer>();
		for (int i = 0; i <= 12; i++) {
			int size = data.getCriticalDataOfKind(i).getSize();
			if (size > 0){
				result.put(i,size);
			}
		}
		return result;
	}
	
	public void save(String dir,String fileName){
		this.getAggGrammar().setDirName(dir);
		this.getAggGrammar().save(fileName);
	}
	
	public static Formula normalize(Formula nc){
		Formula root = EcoreUtil.copy(nc);
		System.out.println("=====================================================================");
		System.out.println("=== DEBUG: " + debugFormula(nc,new char[]{'A'}));
		System.out.println("=== DEBUG: " + debugFormula(root,new char[]{'A'}));
		System.out.println("=====================================================================");
		
		return eval(root,false);
	}
	
	
	public static Formula eval(Formula nc,boolean change){
		if (nc instanceof Not){
			change = !change;
			
			return eval(((Not) nc).getChild(),change);
		} 
		if (nc instanceof NestedCondition){
			if (change){
				/*if (((NestedCondition) nc).isNegated()){
					return nc;
				} else {*/
					Not not = HenshinFactory.eINSTANCE.createNot();
					not.setChild(nc);
					return not;
				//}
			} else {
				/*if (((NestedCondition) nc).isNegated()){
					Not not = HenshinFactory.eINSTANCE.createNot();
					not.setChild(nc);
					return not;
				}*/
				return nc;
			}
			
		}
		if (nc instanceof BinaryFormula){
			if (change){
				if (nc instanceof And){
					Or or= HenshinFactory.eINSTANCE.createOr();
					or.setLeft(eval(((BinaryFormula) nc).getLeft(),change));
					or.setRight(eval(((BinaryFormula) nc).getRight(),change));
					return or;
				}
				if (nc instanceof Or){
					And and = HenshinFactory.eINSTANCE.createAnd();
					and.setLeft(eval(((BinaryFormula) nc).getLeft(),change));
					and.setRight(eval(((BinaryFormula) nc).getRight(),change));
					return and;
				}
				if (nc instanceof Xor){
					Not not = HenshinFactory.eINSTANCE.createNot();
					And and1 = HenshinFactory.eINSTANCE.createAnd();
					And and2 = HenshinFactory.eINSTANCE.createAnd();
					Or or = HenshinFactory.eINSTANCE.createOr();
					Formula f = and1;
					and1.setLeft(or);
					and1.setRight(not);
					not.setChild(and2);
					or.setLeft(((BinaryFormula) nc).getLeft());
					or.setRight(((BinaryFormula) nc).getRight());
					and2.setLeft(((BinaryFormula) nc).getLeft());
					and2.setRight(((BinaryFormula) nc).getRight());
					return eval(f,change);					
					
				}
			} else {
				((BinaryFormula) nc).setLeft(eval(((BinaryFormula) nc).getLeft(),change));
				((BinaryFormula) nc).setRight(eval(((BinaryFormula) nc).getRight(),change));
				return nc;
			}
		}
		return nc;
		
	}
	
	public static String debugFormula(Formula f,char[] varName){
		if (f instanceof NestedCondition){
			char var = varName[0]++;
			
			return (((NestedCondition) f).getConclusion().getName() == null ? "" + var : ((NestedCondition) f).getConclusion().getName());
		}
		if (f instanceof And){
			if (!(f.eContainer() instanceof And))
				return "(" + debugFormula(((And) f).getLeft(), varName) + " and " + debugFormula(((And) f).getRight(), varName) + ")";
			else
				return debugFormula(((And) f).getLeft(), varName) + " and " + debugFormula(((And) f).getRight(), varName);
		}
		if (f instanceof Or){
			if (!(f.eContainer() instanceof Or))
				return "(" + debugFormula(((Or) f).getLeft(), varName) + " or " + debugFormula(((Or) f).getRight(), varName) + ")";
			else
				return debugFormula(((Or) f).getLeft(), varName) + " or " + debugFormula(((Or) f).getRight(), varName);
			
		}
		if (f instanceof Not){
			return " not " + debugFormula(((Not) f).getChild(), varName);
		}
		if (f instanceof Xor){
			if (!(f.eContainer() instanceof Xor))
				return "(" + debugFormula(((Xor) f).getLeft(), varName) + " xor " + debugFormula(((Xor) f).getRight(), varName) + ")";
			else
				return debugFormula(((Xor) f).getLeft(), varName) + " xor " + debugFormula(((Xor) f).getRight(), varName);
			
			
		}
		if (f == null){
			return "";
		}
		return f.toString();
	}
	
	public static void exportAsTransformationSystem(de.tub.tfs.henshin.analysis.CriticalPair criticalPair){
		@SuppressWarnings("unchecked")
		HashSet<HashSet<Node>> mappings = MappingConverter.convertMappings(criticalPair);
		Module system = HenshinFactory.eINSTANCE.createModule();
		int id = 0;
		for (HashSet<Node> hashSet : mappings) {
			for (Node node : hashSet) {
				node.setName("id:" + id);
			}
			id++;
		}
		system.getUnits().add(EcoreUtil.copy(criticalPair.getRule1()));
		system.getUnits().add(EcoreUtil.copy(criticalPair.getRule2()));
		//system.getInstances().add(criticalPair.getOverlapping());
		
		ModelHelper.saveFile(criticalPair.getType().getLiteral() + "(" + criticalPair.getRule1().getName() + "_and_" + criticalPair.getRule2().getName() + "_id:" + criticalPair.hashCode() + ").henshin", system);
		
	}
	
	
	public void registerAdditionalAttribute(EAttribute attr,String value,Node target){
		Type aggType = nodeTypeMap.get(target.getType());
		String dataType = EcoreUtil.toJavaInstanceTypeName(attr.getEGenericType());
		System.out.println("DEBUG: " + dataType);
		if (dataType.indexOf('<') != -1)
			dataType = dataType.substring(0, dataType.indexOf('<'));
		
		AttrTypeMember member = (AttrTypeMember) aggType.getAttrType().getMemberAt(attr.getName() + attr.hashCode());
		if (member == null)				
			member = aggType.getAttrType().addMember(new JexHandler(),dataType , attr.getName() + attr.hashCode());
		if (member.getType() == null)
			System.out.println("DEBUG: " + member.getType());
		if (attr.getDefaultValue() != null)
			aggType.getTypeGraphNodeObject().getAttribute().setValueAt(attr.getDefaultValue(), attr.getName() + attr.hashCode());
		else
			aggType.getTypeGraphNodeObject().getAttribute().setExprValueAt("null", attr.getName() + attr.hashCode());
		
		agg.xt_basis.Node aggNode = (agg.xt_basis.Node) henshinToAggConversionMap.get(target);
	
		aggNode.getAttribute().setExprAt(value, attr.getName() + attr.hashCode());
	
		
	}
	
	
	public void registerAdditionalAttribute(EAttribute attr,String value,Edge target){
		Type aggType = edgeTypeMap.get(target.getType());
		Type sourceType = nodeTypeMap.get(target.getType().getEContainingClass());
		Type targetType = nodeTypeMap.get(target.getType().getEType());
		Arc typeArc = aggType.getTypeGraphArcObject(sourceType, targetType);
		String dataType = EcoreUtil.toJavaInstanceTypeName(attr.getEGenericType());
		System.out.println("DEBUG: " + dataType);
		if (dataType.indexOf('<') != -1)
			dataType = dataType.substring(0, dataType.indexOf('<'));
		AttrTypeMember member = (AttrTypeMember) aggType.getAttrType().getMemberAt(attr.getName());
		if (member == null)
				member = aggType.getAttrType().addMember(new JexHandler(),dataType , attr.getName());
		if (member.getType() == null)
			System.out.println("DEBUG: " + member.getType());
		if (attr.getDefaultValue() != null)
			typeArc.getAttribute().setValueAt(attr.getDefaultValue(), attr.getName());
		else
			typeArc.getAttribute().setExprValueAt("null", attr.getName());
		
		agg.xt_basis.Arc aggArc = (agg.xt_basis.Arc) henshinToAggConversionMap.get(target);
	
		aggArc.getAttribute().setExprAt(value, attr.getName());
	
		
	}
	
	
	
	
}

package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.interpreter.impl.MatchImpl;
import org.eclipse.emf.henshin.interpreter.impl.RuleApplicationImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.impl.TggFactoryImpl;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggEngineImpl;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;
import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.RuleLhsCoSpanList.RuleLhsCoSpan;
import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;
import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * 
 * @author Jerry
 * ConcurrentRuleList generates and stores all possible concurrent rules from a left rule and a right rule 
 */
public class ConcurrentRuleList extends LinkedList<TGGRule> {

	private static final long serialVersionUID = -6194088318760268591L;
	/**
	 * Inverse of initial left rule that has to be merged with ruleR.
	 */
	private Rule ruleL; // inverse of initial left rule
	
	/**
	 * Right rule that is merged with inverse of ruleL.
	 */
	private Rule ruleR;
	
	/**
	 * Maps IDs to attribute values that are replaced temporarily by these ids. 
	 */
	private HashMap<String, String> attributeID2Value;

	/**
	 * Constructor of List that contains all possible concurrent rule constructions from ruleLeft and ruleRight.
	 * @param ruleLeft
	 * @param ruleRight
	 */
	public ConcurrentRuleList(final TGGRule ruleLeft, final TGGRule ruleRight){
		super();
		if (ruleLeft == null || ruleRight == null) {
			throw new IllegalArgumentException("Null is not a valid ConcurrentRuleList argument");
		}
		
		//copy rules because they are manipulated
		//ruleL is inverse of ruleLeft since we have to apply the inverse in order to create LHS of concurrent rule
		this.ruleL = ConcurrentRuleUtil.getInverse(ruleLeft);
		this.ruleR = ConcurrentRuleUtil.copyRule(ruleRight);
		this.attributeID2Value = new HashMap<String, String>();	
		
		//copy and remove attribute Conditions
		LinkedList<AttributeCondition> attributeConditionsL = new LinkedList<AttributeCondition>();
		LinkedList<AttributeCondition> attributeConditionsR = new LinkedList<AttributeCondition>();
		attributeConditionsL.addAll(this.ruleL.getAttributeConditions());
		attributeConditionsR.addAll(this.ruleR.getAttributeConditions());
		this.ruleL.getAttributeConditions().clear();
		this.ruleR.getAttributeConditions().clear();
		//TODO handle the NAC case
		if (this.ruleR.getLhs().getFormula() != null){
			System.out.println("rule2 has NAC --> ignore");
			return;
		}
		
		//generate all possible cospans (intersections) of the LHSides of both ruleL and ruleR
		RuleLhsCoSpanList graphsLCoSpans = new RuleLhsCoSpanList(this.ruleL, this.ruleR);
		
		//clear Lhs attributes of rules because the matching target nodes are already fixed, 
		//and no problems with supposedly matching attributes will occur that way
		for (Node nodeLRuleL : this.ruleL.getLhs().getNodes()){
			nodeLRuleL.getAttributes().clear();
		}
		
		for (Node nodeLRuleR : this.ruleR.getLhs().getNodes()){
			nodeLRuleR.getAttributes().clear();
		}
		
		//replace attribute values with id values that represent real values 
		///instead of expressions such as params in order to make rules applicable to the graphs
		replaceAttributeValueWithID(this.ruleL.getRhs());
		replaceAttributeValueWithID(this.ruleR.getRhs());
		
		int concurrentRuleIndex = 0;
		for (RuleLhsCoSpan graphsLCoSpan : graphsLCoSpans){
			//copy the generated union C (center graph) of the corresponding cospan twice 
			//because we have to apply 2 rules to it, ruleL and ruleR
			Graph2GraphCopyMappingList graphCoSpanC2graphCoSpanCCopyL = new Graph2GraphCopyMappingList(graphsLCoSpan.getGraphCoSpanC());
			Graph2GraphCopyMappingList graphCoSpanC2graphCoSpanCCopyR = new Graph2GraphCopyMappingList(graphsLCoSpan.getGraphCoSpanC());
			Graph graphCoSpanCCopyL = graphCoSpanC2graphCoSpanCCopyL.getGraphCopy();
			Graph graphCoSpanCCopyR = graphCoSpanC2graphCoSpanCCopyR.getGraphCopy();
			//after rule applications, graphCoSpanCCopyL/R will be lhs/rhs graphs of resulting concurrent Rule respectively
			graphCoSpanCCopyL.setName("ConcurrentRuleLhs"); 
			graphCoSpanCCopyR.setName("ConcurrentRuleRhs");
			
			//replace attribute values with id values that represent real target graph values 
			///instead of expressions such as params in order to make rules applicable to the graphs
			replaceAttributeValueWithID(graphCoSpanCCopyL);
			replaceAttributeValueWithID(graphCoSpanCCopyR);
			
			//avoid multiple matches by match definition
			MatchImpl matchL = new MatchImpl(ruleL);
			MatchImpl matchR = new MatchImpl(ruleR);
			
			// create EGraphs for rule application and match definition
			final TggHenshinEGraph eGraphL = new TggHenshinEGraph(graphCoSpanCCopyL);
			final TggHenshinEGraph eGraphR = new TggHenshinEGraph(graphCoSpanCCopyR);
			
			//construct mappings from CoSpanGraphs(L/R) to graphCCopy(L/R) respectively 
			MappingList graphLRuleL2graphCCopyL = new MappingComposition(
					graphsLCoSpan.getGraphCoSpanL2graphCoSpanC(),
					graphCoSpanC2graphCoSpanCCopyL, graphCoSpanCCopyL);
			MappingList graphLRuleR2graphCCopyL = new MappingComposition(
					graphsLCoSpan.getGraphCoSpanR2graphCoSpanC(),
					graphCoSpanC2graphCoSpanCCopyL, graphCoSpanCCopyL);

			MappingList graphLRuleL2graphCCopyR = new MappingComposition(
					graphsLCoSpan.getGraphCoSpanL2graphCoSpanC(),
					graphCoSpanC2graphCoSpanCCopyR, graphCoSpanCCopyR);
			MappingList graphLRuleR2graphCCopyR = new MappingComposition(
					graphsLCoSpan.getGraphCoSpanR2graphCoSpanC(),
					graphCoSpanC2graphCoSpanCCopyR, graphCoSpanCCopyR);

			for (Node nodeLRuleL : this.ruleL.getLhs().getNodes()) {
				Node nodeGraphCCopyL = graphLRuleL2graphCCopyL.getImage(
						nodeLRuleL, graphCoSpanCCopyL);
				EObject target = eGraphL.getNode2ObjectMap().get(
						nodeGraphCCopyL);
				matchL.setNodeTarget(nodeLRuleL, target); 
			}

			for (Node nodeLRuleR : this.ruleR.getLhs().getNodes()) {
				Node nodeGraphCCopyR = graphLRuleR2graphCCopyR.getImage(
						nodeLRuleR, graphCoSpanCCopyR);
				EObject target = eGraphR.getNode2ObjectMap().get(
						nodeGraphCCopyR);
				matchR.setNodeTarget(nodeLRuleR, target); 
			}

			for (Node nodeCoSpanCCopyL : graphCoSpanCCopyL.getNodes()){
				Node nodeLRuleL = graphLRuleL2graphCCopyL.getOrigin(nodeCoSpanCCopyL);
				//Node nodeLRuleR = graphLRuleR2graphCCopyL.getOrigin(nodeCoSpanCCopyL);
				if (nodeLRuleL!=null){
					HashSet<Attribute> attributesToRemove = new HashSet<Attribute>();
					for (Attribute attributeNodeCoSpanCCopyL : nodeCoSpanCCopyL.getAttributes()){
						if (RuleUtil.NEW.equals(((TAttribute)attributeNodeCoSpanCCopyL).getMarkerType())){
							attributesToRemove.add(attributeNodeCoSpanCCopyL);
						}
					}
					nodeCoSpanCCopyL.getAttributes().removeAll(attributesToRemove);
				}
			}
			
			/*
			for (Node nodeCoSpanCCopyR : graphCoSpanCCopyR.getNodes()){
				Node nodeLRuleL = graphLRuleL2graphCCopyR.getOrigin(nodeCoSpanCCopyR);
				Node nodeLRuleR = graphLRuleR2graphCCopyR.getOrigin(nodeCoSpanCCopyR);
				if (nodeLRuleL!=null && nodeLRuleR!=null){//if intersecting node
					HashSet<Attribute> atrributesNodeCoSpanCCopyRToRemove = new HashSet<Attribute>();
					for (Attribute atrributeNodeCoSpanCCopyR : nodeCoSpanCCopyR.getAttributes()){
						//if attribute is intersecting remove it 
						if (AttributeUtil.getAttributeMarker(atrributeNodeCoSpanCCopyR)!=null && AttributeUtil.getAttributeMarker(atrributeNodeCoSpanCCopyR).equals(RuleUtil.INTERSECTING)){
							atrributesNodeCoSpanCCopyRToRemove.add(atrributeNodeCoSpanCCopyR);
						}
					}
					nodeCoSpanCCopyR.getAttributes().removeAll(atrributesNodeCoSpanCCopyRToRemove);
				}else if (nodeLRuleL==null && nodeLRuleR!=null){//only part of right rule
					nodeCoSpanCCopyR.getAttributes().clear();
					Test.check(nodeCoSpanCCopyR.getAttributes().isEmpty());
				}
			}*/
			
			for (Node nodeRRuleL : this.ruleL.getRhs().getNodes()){
				for (Attribute attRnodeRRuleL: nodeRRuleL.getAttributes()){
					((TAttribute)attRnodeRRuleL).setMarkerType(RuleUtil.NEW);;
				}
			}
			for (Node nodeRRuleR : this.ruleR.getRhs().getNodes()){
				for (Attribute attRnodeRRuleR: nodeRRuleR.getAttributes()){
					((TAttribute)attRnodeRRuleR).setMarkerType(RuleUtil.NEW);;
				}
			}
			
			RuleApplication ruleAppL = applyRule(this.ruleL, eGraphL, matchL);
			//check whether ruleL was successful, if not then the concurrent rule is not valid
			if (ruleAppL!=null && ruleAppL.getResultMatch()!=null){
				RuleApplication ruleAppR = applyRule(this.ruleR, eGraphR, matchR);
				if (ruleAppR==null || ruleAppR.getResultMatch()==null) continue;
				TGGRule concurrentRule = TggFactoryImpl.init().createTGGRule();
						//new TripleGraphImpl();
				concurrentRule.setName(ConcurrentRuleUtil.getConcurrentRuleName(ruleLeft, ruleRight,concurrentRuleIndex));
				//rule applications transformed the union into lhs and rhs of concurrent rule respectively
				concurrentRule.setLhs(graphCoSpanCCopyL);
				concurrentRule.setRhs(graphCoSpanCCopyR);
				
				((TripleGraph)concurrentRule.getLhs()).setDividerCT_X(((TripleGraph)graphCoSpanCCopyL).getDividerCT_X());
				((TripleGraph)concurrentRule.getLhs()).setDividerSC_X(((TripleGraph)graphCoSpanCCopyL).getDividerSC_X());
				((TripleGraph)concurrentRule.getLhs()).setDividerMaxY(((TripleGraph)graphCoSpanCCopyL).getDividerMaxY());
				((TripleGraph)concurrentRule.getLhs()).setDividerYOffset(((TripleGraph)graphCoSpanCCopyL).getDividerYOffset());
				
				((TripleGraph)concurrentRule.getRhs()).setDividerCT_X(((TripleGraph)graphCoSpanCCopyR).getDividerCT_X());
				((TripleGraph)concurrentRule.getRhs()).setDividerSC_X(((TripleGraph)graphCoSpanCCopyR).getDividerSC_X());
				((TripleGraph)concurrentRule.getRhs()).setDividerMaxY(((TripleGraph)graphCoSpanCCopyR).getDividerMaxY());
				((TripleGraph)concurrentRule.getRhs()).setDividerYOffset(((TripleGraph)graphCoSpanCCopyR).getDividerYOffset());
				
				
				for (Node nodeRRuleL : this.ruleL.getRhs().getNodes()){
					TNode concurrentNodeL = getAppResNodeFromRuleNode(ruleAppL, nodeRRuleL);
					//if (concurrentNodeL.getX()!=0 && concurrentNodeL.getX()!=((TNode)nodeRRuleL).getX()) throw new IllegalArgumentException("remove this");
					concurrentNodeL.setX(((TNode)nodeRRuleL).getX());
					concurrentNodeL.setY(((TNode)nodeRRuleL).getY());
				}
				
				for (Node nodeRRuleR : this.ruleR.getRhs().getNodes()){
					TNode concurrentNodeR = getAppResNodeFromRuleNode(ruleAppR, nodeRRuleR);
					//if (concurrentNodeR.getX()!=0) {System.out.println("x was "+concurrentNodeR.getX()+ " and is "+((TNode)nodeRRuleR).getX());};
					concurrentNodeR.setX(((TNode)nodeRRuleR).getX());
					concurrentNodeR.setY(((TNode)nodeRRuleR).getY());
				}
				
				MappingList graphCoSpanCCopyL2graphCoSpanC = ConcurrentRuleUtil.getInverseMappingList(graphCoSpanC2graphCoSpanCCopyL);
				MappingList unionL2unionR = new MappingComposition(graphCoSpanCCopyL2graphCoSpanC, graphCoSpanC2graphCoSpanCCopyR, concurrentRule.getRhs());
				MappingList concurrentLHS2unionR = restrictToDomain(unionL2unionR, concurrentRule.getLhs());
				concurrentRule.getMappings().addAll(concurrentLHS2unionR);
				
				
//				for (Mapping m : ruleLhsCoSpan.getGraphRRuleL2GraphRRuleR()){
//					Node origin = m.getOrigin();
//					Node image = m.getImage();
//					TNode nodeLConcurrentRule = getAppResNodeFromRuleNode(ruleAppL, origin);
//					TNode nodeRConcurrentRule = getAppResNodeFromRuleNode(ruleAppR, image);
//					Test.out("ADD Mapping 0:"+Test.outNodeRepresentation(nodeLConcurrentRule)+"-->"+Test.outNodeRepresentation(nodeRConcurrentRule));
//					concurrentRule.getMappings().add(nodeLConcurrentRule, nodeRConcurrentRule);
//				}
//				
//				Test.check(!graphCoSpanC2graphCoSpanCCopyR.isEmpty());
//				
//				// mappings
//				for (Node nodeRRuleL : this.ruleL.getRhs().getNodes()){
//					
//					TNode nodeLConcurrentRule = getAppResNodeFromRuleNode(ruleAppL, nodeRRuleL);
//					Test.check(nodeLConcurrentRule!=null);
//					if (concurrentRule.getMappings().getImage(nodeLConcurrentRule, concurrentRule.getRhs())==null){
//						Node nodeGraphCoSpanC = ruleLhsCoSpan.getGraphRRuleL2GraphCoSpanC().getImage(nodeRRuleL, ruleLhsCoSpan.getGraphCoSpanC());
//						Test.check(nodeGraphCoSpanC!=null);
//						//Node nodeRRuleR = ruleLhsCoSpan.getGraphCoSpanC2graphRRuleR().getImage(nodeGraphCoSpanC, ruleR.getRhs());
//						Node nodeRConcurrentRule = graphCoSpanC2graphCoSpanCCopyR.getImage(nodeGraphCoSpanC);
//						Test.check(nodeRConcurrentRule!=null);
//						Test.out("ADD Mapping 2:"+Test.outNodeRepresentation(nodeLConcurrentRule)+"-->"+Test.outNodeRepresentation(nodeRConcurrentRule));
//						concurrentRule.getMappings().add(nodeLConcurrentRule, nodeRConcurrentRule);
//					}
//				}
//				
//				
//				
//				Test.check(!graphCoSpanC2graphCoSpanCCopyL.isEmpty());
//				for (Node nodeLRuleR : this.ruleR.getLhs().getNodes()){
//					TNode nodeRConcurrentRule = getAppResNodeFromRuleNode(ruleAppR, ruleR.getMappings().getImage(nodeLRuleR, ruleR.getRhs()));
//					Test.check(nodeRConcurrentRule!=null);
//					if (concurrentRule.getMappings().getOrigin(nodeRConcurrentRule)==null){
//						Node nodeRGraphC = ruleLhsCoSpan.getGraphCoSpanR2graphCoSpanC().getImage(nodeLRuleR, ruleLhsCoSpan.getGraphCoSpanC());
//						Test.check(nodeRGraphC!=null);
//						Node nodeLConcurrentRule = graphCoSpanC2graphCoSpanCCopyL.getImage(nodeRGraphC);
//						if (nodeLConcurrentRule!=null){
//							Test.out("ADD Mapping 3:"+Test.outNodeRepresentation(nodeLConcurrentRule)+"-->"+Test.outNodeRepresentation(nodeRConcurrentRule));
//							concurrentRule.getMappings().add(nodeLConcurrentRule, nodeRConcurrentRule);
//						}
//					}
//				}
				/**
				for (Node concurrentRuleNodeL : concurrentRule.getLhs().getNodes()){
					if (concurrentRule.getAllMappings().getImage(concurrentRuleNodeL, concurrentRule.getRhs())==null){
						
						Node ruleRNodeR = ruleCospan.getGraphC2graphRRuleR().getImage(graphCCopyL.getOrigin(concurrentRuleNodeL), ruleCospan.getRuleR().getRhs());
						TNode concurrentRuleNodeR = getAppNodeFromRuleNode(ruleRNodeR, ruleAppR);
						//TNode concurrentRuleNodeL = getAppNodeFromRuleNode(nLhs, ruleAppL);
						System.out.println("r l "+concurrentRuleNodeR);
						System.out.println("r l "+concurrentRuleNodeL);
						concurrentRule.getAllMappings().add(concurrentRuleNodeL, concurrentRuleNodeR);
						System.out.println("Good news");
					}else{
						System.out.println("good news: mapping is consistent");
					}
				}**/
				concurrentRule.getAttributeConditions().addAll(attributeConditionsR);
				concurrentRule.getAttributeConditions().addAll(attributeConditionsL);
				Test.check(
						concurrentRule.getAttributeConditions().isEmpty()==
						(attributeConditionsR.isEmpty() && attributeConditionsL.isEmpty()));
				AttributeCondition ac = graphsLCoSpan.getIntersectionHandler().getAttributeCondition();
				if (!ac.getConditionText().equals("")){
					ac.setRule(concurrentRule);
					concurrentRule.getAttributeConditions().add(ac);
				}
				
				//add params
				boolean isEmpty = ruleR.getParameters().isEmpty();
				LinkedList<Parameter> paramsR = new LinkedList<Parameter>(ruleR.getParameters());
				LinkedList<Parameter> paramsL = new LinkedList<Parameter>(ruleL.getParameters());
				if (!isEmpty){
					Test.check(!ruleR.getParameters().isEmpty());
				}
				for (Parameter paramR : paramsR){
					for (Parameter paramL : paramsL){
						if (paramR.getName().equals(paramL.getName())){
							concurrentRule.setName("W_"+concurrentRule.getName());
							break;
						}
					}
				}
				
				Iterator<Parameter> paramRIterator = paramsR.iterator();
				while(paramRIterator.hasNext()){
					Parameter param = paramRIterator.next();
					Parameter parameter = HenshinFactory.eINSTANCE.createParameter();
					parameter.setName(param.getName());
					concurrentRule.getParameters().add(parameter);
				}
				Iterator<Parameter> paramLIterator = paramsL.iterator();
				while(paramLIterator.hasNext()){
					Parameter param = paramLIterator.next();
					Parameter parameter = HenshinFactory.eINSTANCE.createParameter();
					parameter.setName(param.getName());
					concurrentRule.getParameters().add(parameter);
				}
				if (!isEmpty){
					Test.check(!ruleR.getParameters().isEmpty());
				}
				GraphUtil.removeDoubleEdges(concurrentRule.getLhs());
				GraphUtil.removeDoubleEdges(concurrentRule.getRhs());
				graphsLCoSpan.getIntersectionHandler().replaceAttributeHashValuesWithInitialExpressionsAndSetMarkers(concurrentRule, attributeID2Value);
				ConcurrentRuleUtil.mark(concurrentRule);
				this.add(concurrentRule);
				concurrentRuleIndex++;
			}
		}
		if (ConcurrentRuleUtil.isConcurrent(ruleLeft, ruleRight) && this.isEmpty()) ;
			//throw new IllegalArgumentException("Generation of Concurrent Rules Failed because of special case: "+ruleLeft.getName()+" merging with "+ruleRight.getName());
	}
	
	
	
	/*Begin static helper functions section*/
	

	
	/*End static helper functions section*/
	
	private void replaceAttributeValueWithID(Graph graph){
 		for (Node node : graph.getNodes()){
			for (Attribute attribute : node.getAttributes()){
				if (attribute.getValue()==null) return;
				int h = attribute.getValue().hashCode();
				String hash = "\""+h+"\"";
				while (this.attributeID2Value.containsKey(hash)){
					if (!this.attributeID2Value.get(hash).equals(attribute.getValue())){
						h += 1; 
						//hash = Double.toString(h);
						hash = "\""+h+"\"";
					}else{
						break;
					};
				}
				this.attributeID2Value.put(hash, attribute.getValue());
				attribute.setValue(hash);
			}
		}
 	}
	
	private MappingList restrictToDomain(MappingList mappingL, Graph lhs) {
		// TODO Auto-generated method stub
		MappingList restrictedMappingList=new MappingListImpl();
		for(Mapping m: mappingL){
			if(lhs.getNodes().contains(m.getOrigin()))
				restrictedMappingList.add(m);
		}
		
		return restrictedMappingList;
	}

	private static TNode getAppResNodeFromRuleNode(RuleApplication ruleApp, Node ruleNode){
		Match match = ruleApp.getResultMatch();
		TggHenshinEGraph henshinEGraph = (TggHenshinEGraph)(ruleApp.getEGraph());
		EObject ruleNodeTarget = match.getNodeTarget(ruleNode); //get resulting nodeObject of rule applicatoin
		return (TNode)henshinEGraph.getObject2NodeMap().get(ruleNodeTarget);
	}
	
	private static RuleApplication applyRule(Rule rule, TggHenshinEGraph henshinGraph, Match match){
		if (match==null) match = new MatchImpl(rule);
		//if (match==null || match.getRule()!=rule) throw new IllegalArgumentException("match has to correspond to rule");
		//final TggHenshinEGraph henshinGraph = new TggHenshinEGraph(graph);
		/*
		Map<Node,EObject> node2eObject;
		Map<EObject, Node> eObject2Node;
		eObject2Node = henshinGraph.getObject2NodeMap();
		node2eObject = henshinGraph.getNode2ObjectMap();*/
		TggEngineImpl emfEngine = new TggEngineImpl(henshinGraph);
		RuleApplicationImpl ruleApplication = new RuleApplicationImpl(emfEngine);
		ruleApplication.setRule(rule);
		ruleApplication.setEGraph(henshinGraph);
		try {
			/*
			Iterator<Match> matchesIterator = emfEngine
					.findMatches(rule, henshinGraph,
							match//new MatchImpl(rule)
					).iterator();
			if (!matchesIterator.hasNext()) return null;*/
			ruleApplication.setPartialMatch(match);
			boolean appliceable = ruleApplication.execute(null);
			if (!appliceable) return null;
			return ruleApplication;
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.openError(
					Display.getDefault().getActiveShell(),
					"Execute Failure",
					"The rule [" + ruleApplication.getRule().getName()
							+ "] couldn't be applied.",
					new Status(IStatus.ERROR, MuvitorActivator.PLUGIN_ID, ex
							.getMessage(), ex.getCause()));
			return null;
		}
	}

}

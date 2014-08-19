package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Rule;

public class GraphLCoSpan extends GraphCoSpan{
	
	private Rule ruleL;
	private Rule ruleR;
	
	private MappingList graphRRuleL2GraphCoSpanC;
	private MappingList graphCoSpanC2graphRRuleR;
	private MappingList graphRRuleL2graphRRuleR;
	
	public Rule getRuleL() {
		return ruleL;
	}

	public Rule getRuleR() {
		return ruleR;
	}

	public MappingList getGraphRRuleL2GraphCoSpanC() {
		return graphRRuleL2GraphCoSpanC;
	}

	public MappingList getGraphCoSpanC2graphRRuleR() {
		return graphCoSpanC2graphRRuleR;
	}

	public MappingList getGraphRRuleL2GraphRRuleR() {
		return graphRRuleL2graphRRuleR;
	}

	public GraphLCoSpan(IntersectionHandler intersection){
		super(intersection);
		if (intersection.getGraphL().getRule()==null || intersection.getGraphR().getRule()==null)
			throw new IllegalArgumentException("intersecting graphs have to be righthandside of a rule");
		this.ruleL = this.getGraphCoSpanL().getRule();
		this.ruleR = this.getGraphCoSpanR().getRule();
		Test.check(ruleL.getLhs()==this.getGraphCoSpanL());
		if (!this.getGraphCoSpanL().getNodes().isEmpty()){
			Test.check(this.getGraphCoSpanL2graphCoSpanC().get(0).getOrigin().getGraph()==this.getGraphCoSpanL());
		}
		if (!this.ruleL.getMappings().isEmpty()){
		Test.check(this.ruleL.getMappings()
				.get(0).
				getOrigin().
				getGraph()==this.getGraphCoSpanL());
		}
		
		MappingList invRuleLMapping = MappingListUtil.getInverseMappingList(this.ruleL.getAllMappings());
		MappingList GraphC2GraphLRuleR = MappingListUtil.getInverseMappingList(this.getGraphCoSpanR2graphCoSpanC());
		if (!invRuleLMapping.isEmpty()){
		Test.check(invRuleLMapping.get(0).getImage().getGraph()==this.getGraphCoSpanL());
		}
		this.graphRRuleL2GraphCoSpanC = new MappingComposition(invRuleLMapping, this.getGraphCoSpanL2graphCoSpanC(), this.getGraphCoSpanC());
		//Test.check(!this.graphRRuleL2GraphC.isEmpty(), "this.graphRRuleL2GraphC is empty");
		this.graphCoSpanC2graphRRuleR = new MappingComposition(GraphC2GraphLRuleR, this.ruleR.getAllMappings(), this.ruleR.getRhs());
		Test.check(!this.graphCoSpanC2graphRRuleR.isEmpty());
		this.graphRRuleL2graphRRuleR = new MappingComposition(this.graphRRuleL2GraphCoSpanC, this.graphCoSpanC2graphRRuleR, this.ruleR.getRhs());
		//Test.check(!this.graphRRuleL2GraphC.isEmpty(), "this.graphRRuleL2graphRRuleR is empty");
	}
}

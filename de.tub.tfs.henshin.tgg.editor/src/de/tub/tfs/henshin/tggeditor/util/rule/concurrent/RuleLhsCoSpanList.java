package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.LinkedList;

import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Rule;

public class RuleLhsCoSpanList extends LinkedList<RuleLhsCoSpanList.RuleLhsCoSpan>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7250022527170088910L;
	private Rule ruleL;
	private Rule ruleR;
	
	public RuleLhsCoSpanList(Rule ruleLeft, Rule ruleRight){
		super();
		if (ruleLeft == null || ruleRight == null){
			throw new IllegalArgumentException("Null is not a valid RuleCoSpanList argument");
		}
		this.ruleL = ruleLeft;
		this.ruleR = ruleRight;
		GraphIntersectionList intersections = new GraphIntersectionList(this.ruleL.getLhs(), this.ruleR.getLhs());
		for (GraphIntersectionHandler intersection : intersections){
			this.add(new RuleLhsCoSpan(intersection));
		}
	}

	public Rule getRuleL() {
		return ruleL;
	}

	public Rule getRuleR() {
		return ruleR;
	}
	
	public class RuleLhsCoSpan extends CoSpan{
		
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

		public RuleLhsCoSpan(GraphIntersectionHandler intersection){
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
			
			MappingList invRuleLMapping = ConcurrentRuleUtil.getInverseMappingList(this.ruleL.getAllMappings());
			MappingList GraphC2GraphLRuleR = ConcurrentRuleUtil.getInverseMappingList(this.getGraphCoSpanR2graphCoSpanC());
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
}



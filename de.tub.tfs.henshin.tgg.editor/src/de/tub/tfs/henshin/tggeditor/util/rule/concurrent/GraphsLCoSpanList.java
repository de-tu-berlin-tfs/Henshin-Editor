/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.LinkedList;

import org.eclipse.emf.henshin.model.Rule;

public class GraphsLCoSpanList extends LinkedList<GraphLCoSpan>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7250022527170088910L;
	private Rule ruleL;
	private Rule ruleR;
	
	public GraphsLCoSpanList(Rule ruleL, Rule ruleR){
		super();
		if (ruleL==null || ruleR==null) throw new IllegalArgumentException("null is not a valid RuleCoSpanList argument");
		this.ruleL = ruleL;
		this.ruleR = ruleR;
		GraphLCopy2GraphRCopyIntersectionsList intersections = new GraphLCopy2GraphRCopyIntersectionsList(this.ruleL.getLhs(), this.ruleR.getLhs());
		for (IntersectionHandler intersection : intersections){
			this.add(new GraphLCoSpan(intersection));
		}
	}

	public Rule getRuleL() {
		return ruleL;
	}

	public Rule getRuleR() {
		return ruleR;
	}
}



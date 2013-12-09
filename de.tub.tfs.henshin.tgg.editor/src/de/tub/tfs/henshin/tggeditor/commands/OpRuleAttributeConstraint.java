package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;

public class OpRuleAttributeConstraint implements UnaryConstraint {

	/**
	 * This hashmap will be filled during the execution of all the {@link TRule}
	 * s in the {@link ExecuteOpRulesCommand}. The hashmap contains all the
	 * already translated edges of the graph on which the {@link TRule}s are
	 * executed.
	 */
	private HashMap<Attribute, Boolean> isTranslatedAttributeMap;

	/**
	 * Whether the node is marked to be translated before executing the rule.
	 */
	private Attribute ruleAttribute;
	private EGraph graph;

	/**
	 * the constructor
	 * 
	 * @param node
	 *            see {@link OpRuleConstraint#node}
	 * @param isTranslatedAttributeMap
	 *            see {@link OpRuleConstraint#isAttributeTranslatedMap}
	 */
	public OpRuleAttributeConstraint(Attribute attr,
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, EGraph graph) {
		this.graph = graph;
		this.ruleAttribute = attr;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;

	}

	/**
	 * Checks if the mapping in a {@link TRule}.
	 * 
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.HenshinUserConstraint#check(org.eclipse.emf.henshin.model.Node)
	 */
	@Override
	public boolean check(DomainSlot slot) {
		// case: no marker - e.g. element in filter NAC that has not been marked explicitly
		String ruleAttributeMarker = ((TAttribute) ruleAttribute)
				.getMarkerType();
		if(ruleAttributeMarker==null) return true;

		// case: marker is present
		Node graphNode = getGraphNode(slot, graph);
		// find matching graph attribute (to the rule attribute)
		Attribute graphAttribute = NodeUtil.findAttribute(
				graphNode, ruleAttribute.getType());
		boolean markerIsValid = RuleUtil.checkAttributeMarker(
				ruleAttributeMarker, isTranslatedAttributeMap, graphAttribute);
		if (markerIsValid)
			return true;
		else
			return false;
	}

	private Node getGraphNode(DomainSlot slot, EGraph graph) {
		return ((TggHenshinEGraph) graph).getObject2NodeMap().get(
				slot.getValue());
	}

}

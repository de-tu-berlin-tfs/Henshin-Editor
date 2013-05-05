package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


public class AttributeUtil {

	/**
	 * Gets the attribute layout to the given edge
	 * 
	 * @param attribute
	 * @return attribute layout
	 */
	public static AttributeLayout getAttributeLayout(Attribute attribute) {
		TGG layoutSys = NodeUtil
				.getLayoutSystem(attribute.getNode().getGraph());
		if (layoutSys == null)
			{ExceptionUtil.error("Layout model is missing for retrieving attribute layout"); return null;}
		return getAttributeLayout(attribute, layoutSys);
	}

	/**
	 * Gets the attribute layout in the given layoutsystem to the given edge
	 * 
	 * @param attribute
	 * @param layoutModel
	 * @return edge layout
	 */
	public static AttributeLayout getAttributeLayout(Attribute attribute,
			TGG layoutModel) {
		AttributeLayout result = null;
		if (layoutModel != null) {
			result = findAttributeLayout(attribute, layoutModel);
		}
		return result;
	}
	private static AttributeLayout findAttributeLayout(
			Attribute ruleAttributeRHS) {
		TGG layoutSys = NodeUtil
				.getLayoutSystem(ruleAttributeRHS.getNode().getGraph());

		return findAttributeLayout(ruleAttributeRHS, layoutSys);
	}

	/**
	 * finds the edge layout in layout system
	 * 
	 * @param attribute
	 * @param layoutSystem
	 * @return the attribute layout
	 */
	protected static AttributeLayout findAttributeLayout(Attribute attribute,
			TGG layoutSystem) {
		AttributeLayout result = null;
		NodeLayout nodeLayout = NodeUtil.findNodeLayout(attribute.getNode());
		if (nodeLayout != null) {
			for (AttributeLayout attributeLayout : nodeLayout
					.getAttributeLayouts()) {
				if (attributeLayout.getRhsattribute() == attribute
						|| attributeLayout.getLhsattribute() == attribute) {
					result = attributeLayout;
					break;
				}
			}
		}
		return result;
	}

	public static void refreshIsMarked(Attribute ruleAttributeRHS) {
		if (((TAttribute) ruleAttributeRHS).getIsMarked() != null)
			return;
		else { // marker is not available, thus copy from layout model and
				// delete entry in layout model
			computeAndCreateIsMarked(ruleAttributeRHS);
		}
	}

	private static void computeAndCreateIsMarked(Attribute attr) {
		TAttribute ruleAttributeRHS = (TAttribute) attr;
		// marker value is not available in ruleAttributeRHS, thus compute it
		AttributeLayout attLayout = getAttributeLayout(ruleAttributeRHS);
		if (attLayout == null) { // no layout is found
			// determine type of marker
			TGGRule rule = (TGGRule) ruleAttributeRHS.getNode().getGraph()
					.getRule();
			if (ModelUtil.getRuleLayout(rule) != null)
				( ruleAttributeRHS).setMarkerType(RuleUtil.Translated);
			else
				ruleAttributeRHS.setMarkerType(RuleUtil.NEW);

			// check for existing attribute in LHS
			TAttribute lhsAttribute = (TAttribute) RuleUtil.getLHSAttribute(ruleAttributeRHS);
			if (lhsAttribute != null) {
				// attribute is preserved -> no marker
				ruleAttributeRHS.setIsMarked(false);
			} else {
				// attribute is created -> add marker
				ruleAttributeRHS.setIsMarked(true);
			}

		} else { // attribute layout is found
//			Boolean isTranslatedLHS = attLayout.getLhsTranslated();
			boolean isNew = attLayout.isNew();
//			if (isTranslatedLHS == null) {
				ruleAttributeRHS.setMarkerType(RuleUtil.NEW);
				ruleAttributeRHS.setIsMarked(isNew);
	//		} else {
	//			ruleAttributeRHS.setMarkerType(RuleUtil.Translated);
	//			ruleAttributeRHS.setIsMarked(!isTranslatedLHS);
	//		}
		}
		// delete layout entry in layout model
		while (attLayout != null) {
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					attLayout);
			cmd.execute();
			// find possible duplicates of layout
			attLayout = findAttributeLayout(ruleAttributeRHS);
		}
		return;
	}

}
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.EdgeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGFactory;
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
			return null;
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



}

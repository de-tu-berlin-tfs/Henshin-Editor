/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;

/**
 * The Class AttributeTypes.
 */
public class AttributeTypes {

	/**
	 * Gets the free attribute types.
	 * 
	 * @param node
	 *            the node
	 * @return the free attribute types
	 */
	public static List<EAttribute> getFreeAttributeTypes(Node node) {
		List<EAttribute> eattributes = new ArrayList<EAttribute>();
		if (node.getType() != null) {
			for (int i = 0; i < node.getType().getEAllAttributes().size(); i++) {
				EAttribute eO = node.getType().getEAllAttributes().get(i);
				int upperBound = eO.getUpperBound();
				for (Attribute attr : node.getAttributes()) {
					if (attr.getType() != null)
						if (attr.getType() == eO)
							upperBound--;
				}
				if (upperBound > 0)
					eattributes.add(eO);
			}
		}
		return eattributes;
	}

	/**
	 * Gets the free attribute types.
	 * 
	 * @param node
	 *            the node
	 * @param eAttribute
	 *            the e attribute
	 * @return the free attribute types
	 */
	public static List<EAttribute> getFreeAttributeTypes(Node node,
			EAttribute eAttribute) {
		List<EAttribute> eattributes = getFreeAttributeTypes(node);
		eattributes.add(0, eAttribute);
		return eattributes;
	}

}

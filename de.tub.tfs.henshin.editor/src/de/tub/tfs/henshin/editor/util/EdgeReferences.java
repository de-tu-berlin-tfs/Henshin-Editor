/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

/**
 * The Class EdgeReferences.
 */
public class EdgeReferences {

	/**
	 * Gets the source to target free references.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @return the source to target free references
	 */
	public static List<EReference> getSourceToTargetFreeReferences(Node source,
			Node target) {
		List<EReference> eReferences = getSourceToTargetReferences(source,
				target);
		for (Edge ed : source.getOutgoing()) {
			if (ed.getTarget() == target) {
				eReferences.remove(ed.getType());
			}
		}
		Iterator<EReference> iter = eReferences.iterator();
		while (iter.hasNext()) {
			EReference ref = iter.next();
			if (ref.getUpperBound() > 0) {
				int upperBound = 0;
				for (Edge ed : source.getOutgoing()) {
					if (ed.getType() == ref) {
						if (++upperBound == ref.getUpperBound()) {
							iter.remove();
							break;
						}
					}
				}
			}
		}
		return eReferences;
	}

	/**
	 * Gets the source to target references.
	 * 
	 * @param source
	 *            the source
	 * @param target
	 *            the target
	 * @return the source to target references
	 */
	public static List<EReference> getSourceToTargetReferences(Node source,
			Node target) {
		ArrayList<EReference> eReferences = new ArrayList<EReference>();
		for (EReference ref : source.getType().getEAllReferences()) {
			if (ref.getEReferenceType() == target.getType()
					|| ref.getEReferenceType() == EcorePackage.eINSTANCE
							.getEObject()) {
				eReferences.add(ref);
			} else {
				for (EClass eC : target.getType().getEAllSuperTypes()) {
					if (ref.getEReferenceType() == eC) {
						eReferences.add(ref);
						break;
					}
				}
			}
		}
		return eReferences;
	}
}

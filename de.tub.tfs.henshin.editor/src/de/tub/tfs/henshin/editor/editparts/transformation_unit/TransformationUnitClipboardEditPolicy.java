/**
 * TransformationUnitClipboardEditPolicy.java
 *
 * Created 22.01.2012 - 00:47:50
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.TransformationUnit;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.util.HenshinUtil;

/**
 * @author nam
 * 
 */
public final class TransformationUnitClipboardEditPolicy extends
		ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteTarget
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		TransformationUnit model = (TransformationUnit) getHost().getModel();

		return HenshinUtil.INSTANCE.getTransformationSystem(model);
	}
}
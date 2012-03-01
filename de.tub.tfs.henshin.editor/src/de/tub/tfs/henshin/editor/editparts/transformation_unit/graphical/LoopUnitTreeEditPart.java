/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.TransformationUnitTreeEditPart;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author nam
 * 
 */
public class LoopUnitTreeEditPart extends
		TransformationUnitTreeEditPart<LoopUnit> {

	/**
	 * @param model
	 */
	public LoopUnitTreeEditPart(LoopUnit model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.LOOP.img(16);
	}
}

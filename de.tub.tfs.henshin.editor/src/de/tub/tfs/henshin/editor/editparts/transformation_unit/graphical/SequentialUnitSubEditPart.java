/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * @author nam
 * 
 */
public class SequentialUnitSubEditPart extends
		SubUnitEditPart<TransformationUnit> {

	private SubUnitLayout layout;

	/**
	 * @param transUnitPage
	 * @param parent
	 * @param model
	 */
	public SequentialUnitSubEditPart(final TransUnitPage transUnitPage,
			final TransformationUnit container, SubUnitLayout layout) {
		super(transUnitPage, container, (TransformationUnit) layout.getModel());

		this.layout = layout;
	}

	/**
	 * @param n
	 */
	public void setCounter(int n) {
		layout.setCounter(n);
	}

	/**
	 * @return
	 */
	public int getCounter() {
		return layout.getCounter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#addChildVisual(org
	 * .eclipse.gef.EditPart, int)
	 */
	@Override
	protected void addChildVisual(EditPart childEditPart, int index) {
		IFigure child = ((GraphicalEditPart) childEditPart).getFigure();
		getContentPane().add(child, new Rectangle(270, 0, 50, 46), index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new LinkedList<Object>();

		children.add(layout);

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * SubUnitEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.TRANS_UNIT.img(16);
	}
}

/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class SequentialUnitSubEditPart extends
		SubUnitEditPart<TransformationUnit> {

	private SubUnitEditPart<TransformationUnit> container;

	private Label counter;

	/**
	 * @param transUnitPage
	 * @param parent
	 * @param model
	 */
	public SequentialUnitSubEditPart(
			SubUnitEditPart<TransformationUnit> container) {
		super(container.transUnitPage, container.transformationUnit, container
				.getCastedModel());

		this.container = container;
	}

	/**
	 * @param n
	 */
	public void setCounter(int n) {
		counter.setText(Integer.toString(n));
	}

	/**
	 * @return
	 */
	public int getCounter() {
		return Integer.parseInt(counter.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * SubUnitEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		Figure fig = (Figure) super.createFigure();
		RectangleFigure counter = new RectangleFigure();

		Label counterLabel = new Label("1");

		this.counter = counterLabel;

		counter.setLayoutManager(new XYLayout());

		counter.setFont(SWTResourceManager.getFont("Sans", 15, SWT.BOLD));
		counter.setBackgroundColor(ColorConstants.lightGray);
		counter.setOpaque(true);
		counter.setOutline(false);

		counter.add(counterLabel, new Rectangle(17, 10, 30, 30));
		counter.add(new Label("x"), new Rectangle(0, 9, 30, 30));

		fig.add(counter, new Rectangle(270, 0, 50, 46));

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.
	 * SubUnitEditPart#getImage()
	 */
	@Override
	Image getImage() {
		return container.getImage();
	}
}

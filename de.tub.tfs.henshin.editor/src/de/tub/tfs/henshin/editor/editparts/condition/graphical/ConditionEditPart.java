/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.condition.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author angel
 * 
 */
public class ConditionEditPart extends AdapterGraphicalEditPart<Formula> {

	/**
	 * The label on the up left corner.
	 */
	private Label formulaLabel;

	/**
	 * @param model
	 */
	public ConditionEditPart(Formula model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refresh()
	 */
	@Override
	public void refresh() {
		refreshChildren();
		refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		final FreeformLayer layer = new FreeformLayer();
		layer.setLayoutManager(new FreeformLayout());

		formulaLabel = new Label();
		formulaLabel.setFont(SWTResourceManager.getFont("Sans", 14, SWT.BOLD));
		formulaLabel.setForegroundColor(Display.getCurrent().getSystemColor(
				SWT.COLOR_GRAY));
		formulaLabel.setText(getCastedModel().toString());
		layer.add(formulaLabel, new Rectangle(10, 10, -1, -1));

		return layer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		if (getCastedModel().eContainer() instanceof Graph) {
			MuvitorTreeEditor.showView(getCastedModel());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		removeEditPolicy(EditPolicy.COMPONENT_ROLE);
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new ConditionXYLayoutEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		final List<EObject> children = new ArrayList<EObject>();

		children.add(getCastedModel());

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		updateFigure();
		getFigure().repaint();
		formulaLabel.setText(FormulaUtil.getText(getCastedModel()));
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		refresh();
		super.notifyChanged(notification);
	}

	/**
	 * Update figure.
	 */
	protected void updateFigure() {
		final FreeformLayer layer = (FreeformLayer) getFigure();
		layer.setBackgroundColor(ColorConstants.white);
		if (getSelected() == 0) { // not selected
			layer.setBorder(new LineBorder(1));
			layer.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));
		} else { // selected
			layer.setBorder(new LineBorder(2));
			layer.setForegroundColor(SWTResourceManager.getColor(150, 0, 0));
		}
	}
}

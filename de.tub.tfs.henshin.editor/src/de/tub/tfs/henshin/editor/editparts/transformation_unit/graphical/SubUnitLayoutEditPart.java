/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.DirectEditRequest;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;

import de.tub.tfs.henshin.editor.commands.transformation_unit.ChangeSubUnitCounterCommand;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.directedit.MuvitorDirectEditPolicy;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class SubUnitLayoutEditPart extends
		AdapterGraphicalEditPart<SubUnitLayout> implements
		IGraphicalDirectEditPart {

	private Label counterLabel;

	/**
	 * @param model
	 */
	public SubUnitLayoutEditPart(SubUnitLayout model) {
		super(model);

		registerAdapter(model.getParent());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		RectangleFigure fig = new RectangleFigure();

		counterLabel = new Label(
				Integer.toString(getCastedModel().getCounter()));

		fig.setLayoutManager(new XYLayout());

		fig.setFont(SWTResourceManager.getFont("Sans", 13, SWT.BOLD));
		fig.setBackgroundColor(ColorConstants.lightGray);
		fig.setOpaque(true);
		fig.setOutline(false);

		fig.add(counterLabel, new Rectangle(17, 10, 30, 30));
		fig.add(new Label("x"), new Rectangle(0, 9, 30, 30));

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#performRequest
	 * (org.eclipse.gef.Request)
	 */
	@Override
	public void performRequest(Request request) {
		if (!RequestConstants.REQ_OPEN.equals(request.getType())) {
			super.performRequest(request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE,
				new MuvitorDirectEditPolicy() {
					@Override
					protected Command getDirectEditCommand(
							DirectEditRequest edit) {
						int newCounter = Integer.parseInt((String) edit
								.getCellEditor().getValue());

						return new ChangeSubUnitCounterCommand(newCounter,
								getCastedModel());
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#notifyChanged
	 * (org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		int henshinMsgId = notification.getFeatureID(HenshinPackage.class);
		int msgType = notification.getEventType();

		switch (henshinMsgId) {
		case HenshinPackage.SEQUENTIAL_UNIT__SUB_UNITS:
			getCastedModel().eSetDeliver(false);

			if (msgType == Notification.ADD
					&& notification.getNewValue() == getCastedModel()
							.getModel()) {
				getCastedModel().setCounter(getCastedModel().getCounter() + 1);
			}

			getCastedModel().eSetDeliver(true);

			break;

		default:
			break;
		}

		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();

		counterLabel.setText(Integer.toString(getCastedModel().getCounter()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart#getDirectEditFeatureID
	 * ()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinLayoutPackage.SUB_UNIT_LAYOUT__COUNTER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart#getDirectEditValidator
	 * ()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new ICellEditorValidator() {

			@Override
			public String isValid(Object value) {
				String errMsg = "Invalid numeric value.";

				if (value instanceof String) {
					for (char c : ((String) value).toCharArray()) {
						if (!Character.isDigit(c)) {
							return errMsg;
						}
					}

					return null;
				}

				return errMsg;
			}
		};
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart
	 * #getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return counterLabel.getTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart
	 * #updateValueDisplay(java.lang.String)
	 */
	@Override
	public void updateValueDisplay(String value) {
		counterLabel.setText(value);
	}
}

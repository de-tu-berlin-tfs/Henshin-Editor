/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.CompoundSnapToHelper;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.SnapToGeometry;
import org.eclipse.gef.SnapToGrid;
import org.eclipse.gef.SnapToHelper;
import org.eclipse.gef.editpolicies.SnapFeedbackPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowDiagramClipBoardEditPolicy;
import de.tub.tfs.henshin.editor.figure.flow_diagram.TransitionConnectionRouter;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * @author nam
 * 
 */
public class FlowDiagramEditpart extends AdapterGraphicalEditPart<FlowDiagram> {

	private Label nameLabel;

	/**
	 * @param model
	 */
	public FlowDiagramEditpart(FlowDiagram model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		FreeformLayer fig = new FreeformLayer();

		fig.setLayoutManager(new FreeformLayout());

		ConnectionLayer connectionLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		TransitionConnectionRouter router = new TransitionConnectionRouter(fig);

		connectionLayer.setAntialias(SWT.ON);
		connectionLayer.setConnectionRouter(router);

		nameLabel = new Label();

		nameLabel.setFont(SWTResourceManager.getFont("Sans", 12, SWT.BOLD));
		nameLabel.setForegroundColor(ColorConstants.gray);
		nameLabel.setText(getCastedModel().getName());

		fig.add(nameLabel, new Rectangle(5, 5, -1, -1));

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		List<EObject> children = new LinkedList<EObject>();

		children.addAll(getCastedModel().getElements());

		return children;
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
		int msgId = notification.getFeatureID(FlowControlPackage.class);

		switch (msgId) {
		case FlowControlPackage.FLOW_DIAGRAM__NAME:
			nameLabel.setText(getCastedModel().getName());
			break;

		default:
			break;
		}

		refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshChildren()
	 */
	@Override
	protected void refreshChildren() {
		super.refreshChildren();

		for (Object child : getChildren()) {
			((EditPart) child).refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractEditPart#addChild(org.eclipse.gef.EditPart
	 * , int)
	 */
	@Override
	protected void addChild(EditPart child, int index) {
		Object childModel = child.getModel();

		if (childModel instanceof FlowElement) {
			registerAdapter(HenshinLayoutUtil.INSTANCE
					.getLayout((FlowElement) childModel));
		}

		super.addChild(child, index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new FlowDiagramXYLayoutEditPolicy(getCastedModel()));
		installEditPolicy("Snap Feedback", new SnapFeedbackPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new FlowDiagramClipBoardEditPolicy() {
					@Override
					protected boolean canCopy() {
						return false;
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#getAdapter(
	 * java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		if (key == SnapToHelper.class) {
			List<SnapToHelper> helpers = new ArrayList<SnapToHelper>();
			helpers.add(new SnapToGeometry(this));
			helpers.add(new SnapToGrid(this));

			if (helpers.size() == 0) {
				return null;
			} else {
				return new CompoundSnapToHelper(
						helpers.toArray(new SnapToHelper[0]));
			}
		}

		return super.getAdapter(key);
	}
	
	@Override
	protected IPropertySource createPropertySource() {
		return super.createPropertySource();
	}
}

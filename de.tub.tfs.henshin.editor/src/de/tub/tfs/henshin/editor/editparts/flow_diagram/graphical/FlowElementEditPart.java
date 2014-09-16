/**
 * FlowElementEditPart.java
 * 
 * Created 21.12.2011 - 22:23:03
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowElementClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.FlowElementComponentEditPolicy;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.gef.editparts.policies.MuvitorXYLayoutEditPolicy;

/**
 * A abstract {@link EditPart edit part} for {@link FlowElement flow elements}.
 * 
 * @author nam
 * 
 * @param <T>
 */
public abstract class FlowElementEditPart<T extends FlowElement> extends
		AdapterGraphicalEditPart<T> implements NodeEditPart {

	/**
	 * The layout model.
	 */
	private FlowElementLayout layoutModel;

	/**
	 * Constructs a {@link FlowDiagramEditpart} with a given model.
	 * 
	 * @param model
	 */
	public FlowElementEditPart(T model) {
		super(model);

		layoutModel = getLayoutModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .ConnectionEditPart)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getSourceConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	/**
	 * @return the layoutModel
	 */
	public FlowElementLayout getLayoutModel() {
		if (layoutModel == null) {
			layoutModel = HenshinLayoutUtil.INSTANCE
					.getLayout(getCastedModel());

			if (layoutModel != null) {
				registerAdapter(layoutModel);
			}
		}

		return layoutModel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@Override
	protected List<Transition> getModelSourceConnections() {
		List<Transition> outList = new ArrayList<Transition>(1);

		Transition defaultOut = getCastedModel().getOut();

		if (defaultOut != null) {
			outList.add(defaultOut);
		}

		return outList;
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
		int msgType = notification.getEventType();

		if (msgType != Notification.REMOVING_ADAPTER
				&& msgType != Notification.RESOLVE) {
			refresh();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		layoutFigure(getFigure());

		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@Override
	protected List<Transition> getModelTargetConnections() {
		return getCastedModel().getIn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		IFigure fig = hookCreateFigure();

		layoutFigure(fig);

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new FlowElementClipboardEditPolicy());

		installEditPolicy(EditPolicy.NODE_ROLE,
				new FlowElementGraphicalEditPolicy());

		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new FlowElementComponentEditPolicy());

		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new MuvitorXYLayoutEditPolicy() {
					@Override
					protected Command getCreateCommand(CreateRequest request) {
						return null;
					}

					@Override
					protected void setConstraint(EObject model,
							Rectangle constraint) {

					}
				});
	}

	/**
	 * @param fig
	 */
	protected void layoutFigure(IFigure fig) {
		FlowElementLayout layoutModel = getLayoutModel();

		if (layoutModel != null && fig != null) {
			fig.setLocation(new Point(layoutModel.getX(), layoutModel.getY()));
		}
	}

	/**
	 * @return
	 */
	protected abstract IFigure hookCreateFigure();
}

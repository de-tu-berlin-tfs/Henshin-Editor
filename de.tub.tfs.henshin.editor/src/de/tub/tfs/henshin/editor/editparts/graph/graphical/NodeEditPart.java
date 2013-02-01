package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.commands.graph.ChangeAttributesHideCommand;
import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.NodeClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.NodeComponentEditPolicy;
import de.tub.tfs.henshin.editor.figure.graph.NodeFigure;
import de.tub.tfs.henshin.editor.figure.graph.SimpleNodeFigure;
import de.tub.tfs.henshin.editor.figure.graph.ToolTipFigure;
import de.tub.tfs.henshin.editor.model.properties.graph.NodePropertySource;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.NodeUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class NodeEditPart.
 */
public class NodeEditPart extends AdapterGraphicalEditPart<Node> implements
		org.eclipse.gef.NodeEditPart, IGraphicalDirectEditPart, MouseListener {

	/** The width. */
	private int width;

	/** The height. */
	private int height;

	/** The default color. */
	private final Color defaultColor = ColorUtil.int2Color(0);

	/** The color. */
	private Color color = defaultColor;

	/** The layout model. */
	private NodeLayout layoutModel;
	
	private boolean collapsing = false;
	
	/**
	 * Constructs a {@link NodeEditPart} for a given {@link Node} model object.
	 * 
	 * @param model
	 *            the model
	 */
	public NodeEditPart(Node model) {
		super(model);

		if (getLayoutModel() != null) {
			registerAdapter(getLayoutModel());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.MouseListener#mouseReleased(org.eclipse.draw2d.MouseEvent
	 * )
	 */
	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		String name = new String();
		if (getCastedModel().getName() != null) {
			name = new String(getCastedModel().getName());
		}
		name += ":";
		if (getCastedModel().getType() != null)
			name += getCastedModel().getType().getName();
		return name;
	}

	/**
	 * Gets the default color.
	 * 
	 * @return the default color
	 */
	public Color getDefaultColor() {
		return defaultColor;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		getNodeFigure().setName(name);
		refreshSize();
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
	 * org.eclipse.gef.NodeEditPart#getTargetConnectionAnchor(org.eclipse.gef
	 * .Request)
	 */
	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	/**
	 * Gets the node figure.
	 * 
	 * @return the node figure
	 */
	public NodeFigure getNodeFigure() {
		return (NodeFigure) getFigure();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.NODE__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {

		return new NameEditValidator(getCastedModel().getGraph(),
				HenshinPackage.GRAPH__NODES, getCastedModel(), false);
	}

	/**
	 * Gets the layout model.
	 * 
	 * @return the layout model
	 */
	public NodeLayout getLayoutModel() {
		if (layoutModel == null) {
			layoutModel = HenshinLayoutUtil.INSTANCE
					.getLayout(getCastedModel());
		}

		return layoutModel;
	}

	/**
	 * @param layoutModel
	 *            the layoutModel to set
	 */
	public void setLayoutModel(NodeLayout layoutModel) {
		this.layoutModel = layoutModel;

		registerAdapter(layoutModel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return ((NodeFigure) getFigure()).getValueLabelTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * updateValueDisplay(java.lang.String)
	 */
	@Override
	public void updateValueDisplay(String value) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.MouseListener#mouseDoubleClicked(org.eclipse.draw2d
	 * .MouseEvent)
	 */
	@Override
	public void mouseDoubleClicked(MouseEvent me) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.draw2d.MouseListener#mousePressed(org.eclipse.draw2d.MouseEvent
	 * )
	 */
	@Override
	public void mousePressed(MouseEvent me) {
		Command command = new ChangeAttributesHideCommand(getLayoutModel());
		getViewer().getEditDomain().getCommandStack().execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		width = NodeUtil.getWeight(getCastedModel(), false);
		height = NodeUtil.getHeight(getCastedModel());
		figure = new SimpleNodeFigure(getCastedModel(), width, this);
		figure.setBackgroundColor(color);

		int x = 30;
		int y = 30;

		if (getLayoutModel() != null) {
			x = getLayoutModel().getX();
			y = getLayoutModel().getY();
			((NodeFigure) figure).setHide(getLayoutModel().isHide());
		}

		figure.setLocation(new Point(x, y));
		
		String toolTip = "Ecore model: " + getCastedModel().getType().getEPackage().getName();
		
		figure.setToolTip(new ToolTipFigure(toolTip));

		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.NODE_ROLE,
				new NodeGraphicalEditPartPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new NodeXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeComponentEditPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new NodeClipboardEditPolicy());
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
		if (notification.getEventType() == Notification.REMOVING_ADAPTER) {
			return;
		}

		if (notification.getEventType() == HenshinNotification.LAYOUT_ADDED) {
			setLayoutModel((NodeLayout) notification.getNewValue());

			refresh();

			return;
		}

		if (notification.getNotifier() instanceof NodeLayout) {
			refreshLocation();
			refreshVisuals();
			return;
		}

		if (notification.getEventType() == HenshinNotification.TREE_SELECTED) {
			getViewer().select(this);

			return;
		}

		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case -1:
			refreshSourceConnections();
			refreshTargetConnections();
			refreshChildren();
		case HenshinPackage.NODE__NAME:
			NodeFigure nodeFigure = (NodeFigure) getFigure();
			nodeFigure.setName(getName());
			if (!collapsing) {
				nodeFigure.setSize(
						NodeUtil.getWeight(getCastedModel(), !nodeFigure.isHide()),
						nodeFigure.getSize().height);
			}

			refreshVisuals();
			break;

		case HenshinPackage.NODE__OUTGOING:
			refreshSourceConnections();
			break;
		case HenshinPackage.NODE__INCOMING:
			refreshTargetConnections();
			break;
		case HenshinPackage.MODULE:
			final int type = notification.getEventType();
			switch (type) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				refreshChildren();
			case Notification.SET:
				refreshVisuals();
				break;
			}
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		NodeFigure fig = (NodeFigure) getFigure();
		int color = getLayoutModel() == null ? 0 : getLayoutModel().getColor();

		fig.setBackgroundColor(ColorUtil.int2Color(color));
		fig.setName(getName());

		refreshSize();
		refreshLocation();

		fig.invalidate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelSourceConnections
	 * ()
	 */
	@Override
	protected List<Edge> getModelSourceConnections() {
		return getCastedModel().getOutgoing();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#getModelTargetConnections
	 * ()
	 */
	@Override
	protected List<Edge> getModelTargetConnections() {
		return getCastedModel().getIncoming();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>(getCastedModel()
				.getAttributes());
		List<EObject> listSotiert = new ArrayList<EObject>();
		if (getCastedModel().getType() == null) {
			listSotiert = list;
		} else {
			for (EAttribute eA : getCastedModel().getType().getEAllAttributes()) {
				for (EObject attr : list) {
					if (((Attribute) attr).getType() == eA) {
						listSotiert.add(attr);
						list.remove(attr);
						break;
					}
				}
				if (list.size() == 0) {
					break;
				}
			}
		}
		return listSotiert;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new NodePropertySource(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#performDirectEdit()
	 */
	@Override
	protected void performDirectEdit() {
		if (getLayoutModel().isEnabled()) {
			super.performDirectEdit();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#fireSelectionChanged()
	 */
	@Override
	protected void fireSelectionChanged() {
		if (getSelected() == SELECTED_PRIMARY) {
			getCastedModel().eNotify(
					new NotificationImpl(HenshinNotification.SELECTED, false,
							true));
		}

		super.fireSelectionChanged();
	}

	/**
	 * Refresh location.
	 */
	private void refreshLocation() {
	
		NodeFigure figure = getNodeFigure();
		int x = 30;
		int y = 30;
		if (getLayoutModel() != null) {
			x = ((NodeLayout) getLayoutModel()).getX();
			y = ((NodeLayout) getLayoutModel()).getY();
			figure.setHide(getLayoutModel().isHide());
		}
		figure.setLocation(new Point(x, y));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
	}

	/**
	 * Size update.
	 */
	private void refreshSize() {
		if (!collapsing) {
			NodeFigure figure = getNodeFigure();
			
			width = NodeUtil.getWeight(getCastedModel(), !figure.isHide());
			height = NodeUtil.getHeight(getCastedModel());
			figure.setSize(width, height);
		}
	}
	
	public void collapsing() {
		collapsing = true;
		if (figure instanceof SimpleNodeFigure) {
			SimpleNodeFigure nodeFigure = (SimpleNodeFigure) figure;
			nodeFigure.collapsing(collapsing);
		}
		GraphEditPart graphEditPart = (GraphEditPart) getParent();
		graphEditPart.repaintChildren();
	}

}

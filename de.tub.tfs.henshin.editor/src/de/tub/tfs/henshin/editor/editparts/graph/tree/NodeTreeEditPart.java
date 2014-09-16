package de.tub.tfs.henshin.editor.editparts.graph.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.NodeClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.NodeComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.NodePropertySource;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class NodeTreeEditPart.
 */
public class NodeTreeEditPart extends AdapterTreeEditPart<Node> implements
		IDirectEditPart {

	protected NodeLayout nodeLayout;

	/**
	 * Instantiates a new node tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public NodeTreeEditPart(Node model) {
		super(model);

		nodeLayout = HenshinLayoutUtil.INSTANCE.getLayout(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		String name = new String();
		if (getCastedModel().getName() != null) {
			name = new String(getCastedModel().getName());
		}
		name += ":";
		if (getCastedModel().getType() != null)
			name += getCastedModel().getType().getName();
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		if (featureId == HenshinPackage.NODE__NAME) {
			refreshVisuals();
		}
		final int type = notification.getEventType();
		switch (type) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
			refreshChildren();
			refreshVisuals();
			break;
		case Notification.SET:
			refreshVisuals();
			break;
		case HenshinNotification.SELECTED:
			getViewer().select(this);
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeComponentEditPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new NodeClipboardEditPolicy());

		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("node18.png");
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getAttributes());
		return list;
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
	 * org.eclipse.gef.editparts.AbstractEditPart#understandsRequest(org.eclipse
	 * .gef.Request)
	 */
	@Override
	public boolean understandsRequest(Request req) {
		if (req.getType().equals(REQ_DIRECT_EDIT))
			return false;

		return super.understandsRequest(req);

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
					new NotificationImpl(HenshinNotification.TREE_SELECTED,
							false, true));
		}

		super.fireSelectionChanged();
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
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#performDirectEdit()
	 */
	@Override
	protected void performDirectEdit() {
		if (nodeLayout.isEnabled()) {
			super.performDirectEdit();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getGraphicalViewModel
	 * ()
	 */
	@Override
	protected EObject getGraphicalViewModel() {
		EObject container = getCastedModel().eContainer();
		EObject containerContainer = container.eContainer();

		if (containerContainer instanceof Module) {
			return container;
		} else if (containerContainer instanceof Rule
				|| containerContainer instanceof NestedCondition) {
			return containerContainer;
		} else {
			return super.getGraphicalViewModel();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.NODE, getCastedModel(), false);
	}
}

package de.tub.tfs.henshin.editor.editparts.graph.tree;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.EdgeClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.EdgeComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.EdgePropertySource;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class EdgeTreeEditPart.
 */
public class EdgeTreeEditPart extends AdapterTreeEditPart<Edge> {

	/**
	 * Instantiates a new edge tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public EdgeTreeEditPart(Edge model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		String name = "";
		if (((Edge) getModel()).getSource() != null) {
			if (((Edge) getModel()).getSource().getName() != null)
				name += ((Edge) getModel()).getSource().getName();
		}
		if (((Edge) getModel()).getTarget() != null) {
			if (((Edge) getModel()).getTarget().getName() != null)
				name += "-" + ((Edge) getModel()).getTarget().getName();
		}
		if (getCastedModel().getType() != null) {
			name += ":" + (getCastedModel().getType().getName());
		}

		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#canShowView()
	 */
	@Override
	protected boolean canShowView() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new EdgeComponentEditPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new EdgeClipboardEditPolicy());
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
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new EdgePropertySource(getCastedModel());
	}

	/**
	 * L�dt das Bild aus dem Icon Package und liefert das Bild als Image
	 * Objekt f�r die Komponente im TreeView zur�ck.
	 * 
	 * @return Bildstream als Image Objekt f�r Edge Komponente
	 */
	@Override
	protected Image getImage() {
		// Ressource nicht vorhanden, oder fehlerhaft, dann lieber kein Bild,
		// als Absturz
		try {
			return IconUtil.getIcon("edge18.png");
		} catch (Exception e) {
			return null;
		}
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
		if (notification.getEventType() == HenshinNotification.SELECTED) {
			getViewer().select(this);
		}

		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.EDGE__SOURCE:
		case HenshinPackage.EDGE__TARGET:
		case HenshinPackage.EDGE__TYPE:
			refreshVisuals();
			break;
		}
	}

}

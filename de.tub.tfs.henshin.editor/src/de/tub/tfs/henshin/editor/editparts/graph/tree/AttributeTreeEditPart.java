/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.graph.tree;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.graph.AttributeComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.AttributePropertySource;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.validator.TypeEditorValidator;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class AttributeTreeEditPart.
 * 
 * @author Johann
 */
public class AttributeTreeEditPart extends AdapterTreeEditPart<Attribute>
		implements IDirectEditPart {

	/**
	 * Instantiates a new attribute tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public AttributeTreeEditPart(Attribute model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.ATTRIBUTE__VALUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#performDirectEdit()
	 */
	@Override
	protected void performDirectEdit() {
		final NodeLayout nodeLayout = HenshinLayoutUtil.INSTANCE
				.getLayout(getCastedModel().getNode());

		if (nodeLayout != null) {
			if (nodeLayout.isEnabled()) {
				super.performDirectEdit();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new TypeEditorValidator(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		EAttribute attrType = getCastedModel().getType();
		String text = "";
		if (getCastedModel().getType() != null) {
			text += attrType.getName() + ": " + getCastedModel().getValue();
		}
		return text;
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
		if (featureId == HenshinPackage.ATTRIBUTE) {
			refreshVisuals();
		}

		if (notification.getEventType() == HenshinNotification.SELECTED) {
			getViewer().select(this);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		getParent().performRequest(new Request(REQ_OPEN));
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
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		final NodeLayout nodeLayout = HenshinLayoutUtil.INSTANCE
				.getLayout(getCastedModel().getNode());

		if (nodeLayout != null) {
			if (nodeLayout.isEnabled()) {
				installEditPolicy(EditPolicy.COMPONENT_ROLE,
						new AttributeComponentEditPolicy());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		// Ressource nicht vorhanden, oder fehlerhaft, dann lieber kein Bild,
		// als Absturz
		try {
			return IconUtil.getIcon("attr16.png");
		} catch (Exception e) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new AttributePropertySource(getCastedModel());
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
		return true;
	}
}

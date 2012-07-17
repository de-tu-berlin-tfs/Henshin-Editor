/**
 * EObjectsContainerTreeEditPart.java
 *
 * Created 24.12.2011 - 23:20:25
 */
package de.tub.tfs.henshin.editor.editparts;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.Request;
import org.eclipse.gef.editparts.AbstractTreeEditPart;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * @author nam
 * 
 */
public class EObjectsContainerTreeEditPart extends
		AdapterTreeEditPart<EContainerDescriptor> {

	private String text;

	private Image image;

	/**
	 * @param model
	 */
	public EObjectsContainerTreeEditPart(String text, Image image,
			EContainerDescriptor model) {
		super(model);

		this.text = text;
		this.image = image;

		registerAdapter(model.getContainer());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#notifyChanged(org
	 * .eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		refresh();

		int eventType = notification.getEventType();

		if (Notification.ADD == eventType) {
			AbstractTreeEditPart newChild = findEditPartFor(notification
					.getNewValue());

			if (newChild != null) {
				EditPartViewer viewer = getViewer();

				if(viewer != null){
					viewer.select(newChild);
				}
				
				newChild.performRequest(new Request(REQ_OPEN));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Object> getModelChildren() {
		List<Object> children = new LinkedList<Object>();
		EObject container = getCastedModel().getContainer();

		for (Object f : getCastedModel().getContainmentMap().values()) {
			children.addAll((Collection<Object>) container
					.eGet((EStructuralFeature) f));
		}

		return children;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getAdapter(java.
	 * lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class key) {
		for (Object c : getCastedModel().getContainmentMap().keySet()) {
			if (((EClassifier) c).getInstanceClass().equals(key)) {
				return getCastedModel().getContainer();
			}
		}

		return super.getAdapter(key);
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
		return !REQ_DIRECT_EDIT.equals(req.getType())
				&& super.understandsRequest(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new EObjectsContainerClipboardEditPolicy());
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

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}
}

/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tggeditor.internal.ContainerEObject;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;


/**
 * The Class TransformationUnitsListTreeEditPart.
 */
public abstract class ElementsContainerTreeEditPart<T extends ContainerEObject<?, ?>> extends
		AdapterTreeEditPart<T> {

	/** The closed. */
	protected boolean closed;
	
	/**
	 * Instantiates a new transformation units list tree edit part.
	 *
	 * @param model the model
	 */
	public ElementsContainerTreeEditPart(T model) {
		super(model);
		closed=true;
	}


	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		closed=!closed;
		refreshChildren();
		refreshVisuals();
		((TreeItem)this.getWidget()).setChecked(!closed);
		((TreeItem)this.getWidget()).setExpanded(!closed);
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ComponentEditPolicy_NoDelete());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	@Override
	protected Image getImage() {
		if (closed){
			return getClosedImage();
		}
		else{
			return getOpenedImage();
		}
	}

	abstract protected Image getClosedImage();
	
	abstract protected Image getOpenedImage();


	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		refresh();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getChildren()
	 */
	@Override
	public List<EObject> getModelChildren() {
		List<EObject> list = new Vector<EObject>();
		if (!closed){
			list.addAll(getCastedModel().getContainerChildren());
		}
		return list;
	}


}

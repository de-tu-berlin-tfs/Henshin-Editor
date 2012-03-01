/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides content for a tree viewer that shows only containers.
 */
public class ResourceContentProvider implements ITreeContentProvider {

	/** The content validator. */
	protected IResourceValidator contentValidator;

	/**
	 * Instantiates a new resource content provider.
	 * 
	 * @param contentValidator
	 *            the content validator
	 */
	public ResourceContentProvider(IResourceValidator contentValidator) {
		this.contentValidator = contentValidator;
	}

	/**
	 * Creates a new ResourceContentProvider.
	 */
	public ResourceContentProvider() {
		this(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
	}

	/**
	 * If {@link #contentValidator} fails on validating the given element, its
	 * children will also be skipped showing.
	 * 
	 * @param element
	 *            the element
	 * @return the children
	 * @see org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 *      Object)
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object[] getChildren(Object element) {
		if (contentValidator == null
				|| contentValidator.isValid((IResource) element)) {
			if (element instanceof IContainer) {
				IContainer container = (IContainer) element;
				if (container.isAccessible()) {
					try {
						List children = new ArrayList();
						IResource[] members = container.members();
						for (int i = 0; i < members.length; i++) {
							IResource child = members[i];
							if (contentValidator == null
									|| contentValidator.isValid(child))
								children.add(members[i]);
						}
						return children.toArray();
					} catch (CoreException e) {
						// this should never happens
					}
				}
			}
		}

		return new Object[0];
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object element) {
		return getChildren(element);
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof IResource) {
			return ((IResource) element).getParent();
		}

		return null;
	}

	/*
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		return getChildren(element).length > 0;
	}

	/*
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	/**
	 * Sets the content validator.
	 * 
	 * @param contentValidator
	 *            the contentValidator to set
	 */
	public void setContentValidator(IResourceValidator contentValidator) {
		this.contentValidator = contentValidator;
	}
}

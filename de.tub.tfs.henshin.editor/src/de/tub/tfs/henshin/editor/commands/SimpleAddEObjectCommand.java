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
package de.tub.tfs.henshin.editor.commands;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.Command;

/**
 * @author nam
 * 
 * @param <T>
 * @param <Child_T>
 */
public class SimpleAddEObjectCommand<T extends EObject, Child_T extends EObject>
		extends Command {

	final protected EStructuralFeature containingFeature;

	final protected Child_T newObject;

	final protected T parent;

	int idx = -1;
	/**
	 * @param model
	 * @param containingFeature
	 * @param parent
	 */
	public SimpleAddEObjectCommand(final Child_T model,
			final EStructuralFeature containingFeature, final T parent) {
		this.newObject = model;
		this.parent = parent;
		this.containingFeature = containingFeature;
	}
	
	/**
	 * @param model
	 * @param containingFeature
	 * @param parent
	 */
	public SimpleAddEObjectCommand(final Child_T model,
			final EStructuralFeature containingFeature, final T parent,int idx) {
		this.newObject = model;
		this.parent = parent;
		this.containingFeature = containingFeature;
		this.idx = idx;
	}

	/**
	 * @param model
	 * @param featureID
	 * @param parent
	 */
	public SimpleAddEObjectCommand(final Child_T model, int featureID,
			final T parent,int idx) {
		this(model, parent.eClass().getEStructuralFeature(featureID), parent);
		this.idx = idx;
	}

	/**
	 * @param model
	 * @param featureID
	 * @param parent
	 */
	public SimpleAddEObjectCommand(final Child_T model, int featureID,
			final T parent) {
		this(model, parent.eClass().getEStructuralFeature(featureID), parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return newObject != null && parent != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		if (idx < 0)
			((List<Object>) parent.eGet(containingFeature)).add(newObject);
		else
			((List<Object>) parent.eGet(containingFeature)).add(idx,newObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		((List<Object>) parent.eGet(containingFeature)).remove(newObject);
	}
}

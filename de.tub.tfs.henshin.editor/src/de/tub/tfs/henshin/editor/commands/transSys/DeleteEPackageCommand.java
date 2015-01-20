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
package de.tub.tfs.henshin.editor.commands.transSys;

import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * The Class DeleteEPackageCommand.
 * 
 * @author Angeline
 */
public class DeleteEPackageCommand extends CompoundCommand {

	/** The model. */
	private EPackage model;

	/** The parent. */
	private Module parent;

	/** The containing feature. */
	private EStructuralFeature containingFeature;

	/**
	 * Instantiates a new delete e package command.
	 * 
	 * @param model
	 *            the model
	 * @param parent
	 *            the parent
	 * @param featureId
	 *            the feature id
	 */
	public DeleteEPackageCommand(EPackage model, Module parent,
			int featureId) {
		super("Delete EPackage");
		this.model = model;
		this.parent = parent;

		containingFeature = parent.eClass().getEStructuralFeature(featureId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return model != null && parent != null && containingFeature != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		Object obj = parent.eGet(containingFeature);
		if (obj instanceof List<?>) {
			((List<?>) obj).remove(model);
		} else {
			parent.eUnset(containingFeature);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		Object obj = parent.eGet(containingFeature);
		if (obj instanceof List<?>) {
			((List<Object>) obj).add(model);
		} else {
			parent.eSet(containingFeature, model);
		}
	}
}
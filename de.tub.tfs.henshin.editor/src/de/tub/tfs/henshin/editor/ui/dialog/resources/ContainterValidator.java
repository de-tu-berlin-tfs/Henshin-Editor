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
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

/**
 * The Class ContainterValidator.
 * 
 * @author nam
 */
public class ContainterValidator implements IResourceValidator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * transeditor.ui.dialog.resources.IResourceValidator#isValid(java.lang.
	 * Object )
	 */
	@Override
	public boolean isValid(IResource obj) {
		return obj instanceof IContainer
				&& (!(obj instanceof IProject) || ((IProject) obj).isOpen());
	}

}

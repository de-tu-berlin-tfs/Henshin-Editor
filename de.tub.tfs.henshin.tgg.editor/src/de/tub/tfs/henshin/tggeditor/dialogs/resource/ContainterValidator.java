/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.dialogs.resource;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;

/**
 * The Class ContainterValidator.
 *
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

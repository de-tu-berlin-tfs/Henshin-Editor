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

import org.eclipse.core.resources.IResource;

/**
 * The Interface IResourceValidator.
 * 
 * @author nam
 */
public interface IResourceValidator {

	/**
	 * Checks if is valid.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is valid
	 */
	public boolean isValid(IResource obj);
}

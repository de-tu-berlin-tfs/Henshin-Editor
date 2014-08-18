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

import org.eclipse.core.resources.IResource;

/**
 * The Interface IResourceValidator.
 *
 */
public interface IResourceValidator {
	
	/**
	 * Checks if is valid.
	 *
	 * @param obj the obj
	 * @return true, if is valid
	 */
	public boolean isValid(IResource obj);
}

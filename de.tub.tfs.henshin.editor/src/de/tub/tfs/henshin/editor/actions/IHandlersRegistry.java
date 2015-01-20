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
 * IHandlersProvider.java
 *
 * Created 19.12.2011 - 21:13:17
 */
package de.tub.tfs.henshin.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IWorkbenchPart;

/**
 * @author nam
 * 
 */
public interface IHandlersRegistry {

	/**
	 * @param id
	 */
	public void registerHandler(IAction handler, String id);

	/**
	 * @param id
	 */
	public IAction getHandler(String id);

	/**
	 * @return
	 */
	public IWorkbenchPart getWorkbenchPart();
}

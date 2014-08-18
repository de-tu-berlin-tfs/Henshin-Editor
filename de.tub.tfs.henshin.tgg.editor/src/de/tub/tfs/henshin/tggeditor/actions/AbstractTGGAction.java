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
package de.tub.tfs.henshin.tggeditor.actions;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

public class AbstractTGGAction extends SelectionAction {

	public static final String ID ="";
	
	
	public AbstractTGGAction(IWorkbenchPart part) {
		super(part);
	}

	@Override
	protected boolean calculateEnabled() {
		return false;
	}

	@Override
	public void run() {
		super.run();
	}
}

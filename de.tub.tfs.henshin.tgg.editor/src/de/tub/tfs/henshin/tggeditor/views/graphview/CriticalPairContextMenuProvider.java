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
package de.tub.tfs.henshin.tggeditor.views.graphview;

import org.eclipse.gef.EditPartViewer;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class CriticalPairContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public CriticalPairContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
	}

}

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
package de.tub.tfs.muvitor.actions;

import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.muvitor.ui.MuvitorPage;

/**
 * An action that selects all edit parts in the current viewer of an
 * AbstractMultiViewerPage. Adapted from SelectAllAction for {@link MuvitorPage}
 * s.
 */
public class SelectAllInMultiViewerAction extends Action {
	
	private final MuvitorPage page;
	
	/**
	 * Constructs a <code>SelectAllAction</code> and associates it with the
	 * given part.
	 * 
	 * @param page
	 *            The workbench part associated with this SelectAllAction
	 */
	public SelectAllInMultiViewerAction(final MuvitorPage page) {
		this.page = page;
		setText("Select all");
		setToolTipText("Select all");
		setId(ActionFactory.SELECT_ALL.getId());
	}
	
	/**
	 * Selects all edit parts in the active workbench part.
	 */
	@Override
	public void run() {
		final GraphicalViewer viewer = page.getCurrentViewer();
		if (viewer != null) {
			viewer.setSelection(new StructuredSelection(viewer.getContents().getChildren()));
		}
	}
	
}

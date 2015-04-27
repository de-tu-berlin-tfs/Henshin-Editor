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

import java.util.Collection;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorConstants;

/**
 * This action trims the viewer containing the currently selected
 * GraphicalEditPart. All editparts that return an executable command for a move
 * request will be moved so that the viewers size is minimized but still showing
 * all figures. In short, all figures will be moved by the same amount to the
 * upper-left corner as far as possible.
 * 
 * @author "Tony Modica"
 */
public class TrimViewerAction extends SelectionAction {
	
	public static final String ID = "TrimViewerAction";
	
	/**
	 * the node's spacing from the viewer's border
	 */
	private static final int INSETS = 5;
	
	/**
	 * the viewer containing the currently selected GraphicalEditPart
	 */
	private EditPartViewer viewer;
	
	/**
	 * Constructor.
	 */
	public TrimViewerAction(final IWorkbenchPart part) {
		super(part);
		setText("Trim viewer");
		setToolTipText("Trim viewer");
		setId(ID);
		setImageDescriptor(MuvitorActivator
				.getImageDescriptor(MuvitorConstants.ICON_TRIM_16));
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		// when invoking directly, a viewer must be set manually!
		if (viewer == null) {
			return;
		}
		
		// initialize deltas with maximum values
		int xdelta = Integer.MAX_VALUE, ydelta = Integer.MAX_VALUE;
		// find x and y deltas to move (least x and y of some nodes)
		for (final EditPart editPart : (Collection<EditPart>) viewer.getContents().getChildren()) {
			if (editPart instanceof NodeEditPart) {
				final Rectangle bounds = ((GraphicalEditPart) editPart).getFigure().getBounds();
				xdelta = Math.min(xdelta, bounds.x);
				ydelta = Math.min(ydelta, bounds.y);
			}
		}
		
		final CompoundCommand compCommand = new CompoundCommand();
		// build commands
		final ChangeBoundsRequest request = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		request.setMoveDelta(new Point(-xdelta + INSETS, -ydelta + INSETS));
		for (final EditPart editPart : (Collection<EditPart>) viewer.getContents().getChildren()) {
			final Command command = editPart.getCommand(request);
			// Some editparts may return unexecutable commands
			if (command != null && command.canExecute()) {
				compCommand.add(command);
			}
		}
		// this allows to use this action independently from an editor
		if (getWorkbenchPart() == null || getCommandStack() == null) {
			compCommand.execute();
		} else {
			execute(compCommand);
		}
	}
	
	/**
	 * This setter allows universal usage of this action. Just call the
	 * constructor with <code>null</code> and set the viewer to trim manually.
	 * 
	 * @param viewer
	 */
	public void setViewer(final EditPartViewer viewer) {
		this.viewer = viewer;
	}
	
	/**
	 * This action is enabled if some graphical edit part is currently selected
	 * from which a viewer can be determined to be trimmed.
	 */
	@Override
	protected boolean calculateEnabled() {
		if (getSelection() == null) {
			return false;
		}
		if (getSelection() instanceof IStructuredSelection) {
			final IStructuredSelection selection = (IStructuredSelection) getSelection();
			for (final Object selectedObject : selection.toList()) {
				if (selectedObject instanceof GraphicalEditPart) {
					viewer = ((GraphicalEditPart) selectedObject).getViewer();
					return viewer != null;
				}
			}
		}
		return false;
	}
	
}

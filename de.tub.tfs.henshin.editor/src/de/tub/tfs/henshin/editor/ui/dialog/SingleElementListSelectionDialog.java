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
package de.tub.tfs.henshin.editor.ui.dialog;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Simple dialog used for convenience to select an object out of a delivered
 * list of elements.
 * 
 * @param <T>
 *            the generic type of the list elements.
 * @see ElementListSelectionDialog
 * @author nam
 */
public class SingleElementListSelectionDialog<T> extends
		ElementListSelectionDialog {

	/**
	 * Creates a new {@link SingleElementListSelectionDialog} for the given
	 * parameters.
	 * 
	 * @param parent
	 *            the parent {@link Shell}.
	 * @param renderer
	 *            the label provider for the element type.
	 * @param elements
	 *            the list of elements to select out of.
	 * @param title
	 *            the dialog window title.
	 */
	public SingleElementListSelectionDialog(Shell parent,
			ILabelProvider renderer, T[] elements, String title) {
		super(parent, renderer);

		setEmptyListMessage("No matching elements found.");
		setMultipleSelection(false); // only single element selection
		setTitle(title);
		setElements(elements);
	}

	/**
	 * Creates a new {@link SingleElementListSelectionDialog} for the given
	 * parameters
	 * 
	 * @param parent
	 *            the parent {@link Shell}
	 * @param renderer
	 *            the label provider for the element type
	 * @param elements
	 *            the list of elements to select out of
	 * @param title
	 *            the dialog window title
	 * @param msg
	 *            a message to be shown to the user
	 */
	public SingleElementListSelectionDialog(Shell parent,
			ILabelProvider renderer, T[] elements, String title, String msg) {
		this(parent, renderer, elements, title);
		setMessage(msg);
	}

	/**
	 * Opens this dialog and returns the selected element or <code>null</code>,
	 * if nothing was selected (e.g. by clicking the cancel button)
	 * 
	 * @return the selected object from the list or <code>null</code>.
	 */
	@SuppressWarnings("unchecked")
	public T run() {
		open();
		return (T) getFirstResult();
	}
}

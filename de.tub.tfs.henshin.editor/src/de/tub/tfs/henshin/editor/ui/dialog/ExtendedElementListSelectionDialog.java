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
public class ExtendedElementListSelectionDialog<T> extends
		ElementListSelectionDialog {

	/**
	 * Creates a new {@link ExtendedElementListSelectionDialog} for the given
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
	public ExtendedElementListSelectionDialog(Shell parent,
			ILabelProvider renderer, T[] elements, String title) {
		super(parent, renderer);

		setEmptyListMessage("No matching elements found.");
		setTitle(title);
		setElements(elements);
	}

	/**
	 * Creates a new {@link ExtendedElementListSelectionDialog} for the given
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
	public ExtendedElementListSelectionDialog(Shell parent,
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
	public T runSingle() {
		setMultipleSelection(false);

		open();

		return (T) getFirstResult();
	}

	/**
	 * @return
	 */
	public Object[] runMulti() {
		setMultipleSelection(true);

		open();

		return getResult();
	}

}

/**
 * 
 */
package tggeditor.util.dialogs;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Simple dialog used for convenience to select an object out of a delivered
 * list of elements.
 *
 * @param <T> the generic type
 * @see ElementListSelectionDialog
 * @author nam
 */
public class SingleElementListSelectionDialog<T> extends
		ElementListSelectionDialog {
	
	/**
	 * Constructor.
	 *
	 * @param parent the parent shell
	 * @param renderer the label provider for the element type
	 * @param elements the list of elements to select out of
	 * @param title the dialog window title
	 */
	public SingleElementListSelectionDialog(Shell parent,
											ILabelProvider renderer,
											T[] elements, String title) {
		super(parent, renderer);
		
		setEmptyListMessage("No matching elements found.");
		setMultipleSelection(false); // only single element selection
		setTitle(title);
		setElements(elements);
	}
	
	/**
	 * Instantiates a new single element list selection dialog.
	 *
	 * @param parent the parent
	 * @param renderer the renderer
	 * @param elements the elements
	 * @param title the title
	 * @param msg the msg
	 */
	public SingleElementListSelectionDialog(Shell parent,
											ILabelProvider renderer,
											T[] elements, String title,
											String msg) {
		this(parent, renderer, elements, title);
		setMessage(msg);
	}
	
	/**
	 * Opens this dialog and returns the selected element or <code>null</code>,
	 * if nothing was selected (i.e. by clicking the cancel button)
	 *
	 * @return the selected object from the list or 
	 */
	@SuppressWarnings("unchecked")
	public T run() {
		open();
		return (T) getFirstResult();
	}
}

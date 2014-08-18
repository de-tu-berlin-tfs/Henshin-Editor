/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.util.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
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
	public interface ListEntry<T> {
		public String getText();
		public T execute();
	}
	
	
	public static class DelegatingLabelProvider extends LabelProvider{
		private ILabelProvider delegate;

		public DelegatingLabelProvider(ILabelProvider delegate){
			this.delegate = delegate;
		}
		
		public void setDelegate(ILabelProvider delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public String getText(Object element) {
			if (element instanceof ListEntry){
				return ((ListEntry) element).getText();
			}
			return delegate.getText(element);
		}
	}
	private LinkedList<ListEntry<T>> additionalEntries = new LinkedList<ListEntry<T>>();

	public void addAdditionalListEntry(ListEntry<T> entry){
		additionalEntries.add(entry);
	}
	
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
											T[] elements, String title,ListEntry<T>... entries) {
		super(parent,getProvider(renderer));
		
		setEmptyListMessage("No matching elements found.");
		setMultipleSelection(false); // only single element selection
		setTitle(title);
		ArrayList list = new ArrayList(Arrays.asList(elements));
		list.addAll(additionalEntries);
		list.addAll(Arrays.asList(entries));
		setElements(list.toArray());
	}
	
	private static ILabelProvider getProvider(ILabelProvider p) {
		return new DelegatingLabelProvider(p);
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
											String msg,ListEntry<T>... entries) {
		this(parent, renderer, elements, title,entries);
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
		Object object = getFirstResult();
		if (object instanceof ListEntry){
			object = ((ListEntry) object).execute();
		}
		return (T) object;
	}
}

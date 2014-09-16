package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * IPostSelectionProvider implementation that delegates to another
 * ISelectionProvider or IPostSelectionProvider. The selection provider used for
 * delegation can be exchanged dynamically. Registered listeners are adjusted
 * accordingly. This utility class may be used in workbench parts with multiple
 * viewers.
 * 
 * @author Marc R. Hoffmann
 */
public class SelectionProviderIntermediate implements IPostSelectionProvider {
	
	private final ISelectionChangedListener postSelectionListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			if (event.getSelectionProvider() == delegate) {
				fireSelectionChanged(postSelectionListeners, event.getSelection());
			}
		}
	};
	
	private final ISelectionChangedListener selectionListener = new ISelectionChangedListener() {
		@Override
		public void selectionChanged(final SelectionChangedEvent event) {
			if (event.getSelectionProvider() == delegate) {
				fireSelectionChanged(selectionListeners, event.getSelection());
			}
		}
	};
	
	ISelectionProvider delegate;
	
	final ListenerList postSelectionListeners = new ListenerList();
	
	final ListenerList selectionListeners = new ListenerList();
	
	/*
	 * (non-Javadoc)
	 * @seeorg.eclipse.jface.viewers.IPostSelectionProvider#
	 * addPostSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addPostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionListeners.add(listener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionListeners.add(listener);
	}
	
	// IPostSelectionProvider Implementation
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	@Override
	public ISelection getSelection() {
		return delegate == null ? null : delegate.getSelection();
	}
	
	/*
	 * (non-Javadoc)
	 * @seeorg.eclipse.jface.viewers.IPostSelectionProvider#
	 * removePostSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void removePostSelectionChangedListener(final ISelectionChangedListener listener) {
		postSelectionListeners.remove(listener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener
	 * (org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		selectionListeners.remove(listener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse
	 * .jface.viewers.ISelection)
	 */
	@Override
	public void setSelection(final ISelection selection) {
		if (delegate != null) {
			delegate.setSelection(selection);
		}
	}
	
	/**
	 * Sets a new selection provider to delegate to. Selection listeners
	 * registered with the previous delegate are removed before.
	 * 
	 * @param newDelegate
	 *            new selection provider
	 */
	public void setSelectionProviderDelegate(final ISelectionProvider newDelegate) {
		if (delegate != null) {
			delegate.removeSelectionChangedListener(selectionListener);
			if (delegate instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) delegate)
						.removePostSelectionChangedListener(postSelectionListener);
			}
		}
		delegate = newDelegate;
		if (newDelegate != null) {
			newDelegate.addSelectionChangedListener(selectionListener);
			if (newDelegate instanceof IPostSelectionProvider) {
				((IPostSelectionProvider) newDelegate)
						.addPostSelectionChangedListener(postSelectionListener);
			}
			fireSelectionChanged(selectionListeners, newDelegate.getSelection());
		}
	}
	
	void fireSelectionChanged(final ListenerList list, final ISelection selection) {
		final SelectionChangedEvent event = new SelectionChangedEvent(delegate, selection);
		final Object[] listeners = list.getListeners();
		for (final Object element : listeners) {
			final ISelectionChangedListener listener = (ISelectionChangedListener) element;
			listener.selectionChanged(event);
		}
	}
	
}

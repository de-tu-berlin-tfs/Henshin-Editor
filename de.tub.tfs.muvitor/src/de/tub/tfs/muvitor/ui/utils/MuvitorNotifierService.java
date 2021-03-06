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
package de.tub.tfs.muvitor.ui.utils;

import java.util.HashMap;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

import de.tub.tfs.muvitor.gef.editparts.AdapterConnectionEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.IDUtil;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * In MuvitorKit editors, this purely static service can be used to listen to
 * and process EMF notifications fired by almost all EObject instances even
 * without being registered to these models. Usually, listeners of this service
 * perform checks for correctness conditions and generate problem markers on
 * specific model changes.
 * 
 * <p>
 * This service is hooked into MuvitorKit editors via the adapter edit parts,
 * which by default pass notifications of their models to this service by
 * calling {@link #notifyListeners(Notification)}.
 * 
 * <p>
 * As the default behavior that is this services's main purpose, if
 * MuvitorNotifierService notices that an EObject has been deleted from the
 * model it will take care of closing all MuvitorKit views showing this
 * particular EObject or one of its children.
 * 
 * <p>
 * There are several interfaces for listeners serving different purposes. The
 * most general is {@link IEMFChangeListener}, which will be notified about all
 * notifications announced via {@link #notifyListeners(Notification)}.
 * 
 * <p>
 * Listeners should use this service carefully with respect to performance
 * issues by reacting on specific notifications only (e.g. by filtering
 * notifications by their featureID) and restricting listeners to their editor
 * contexts if possible (see
 * {@link #addListener(IMuvitorListener, MuvitorTreeEditor)}).
 * 
 * @author Tony Modica
 */
public class MuvitorNotifierService {
	
	/**
	 * Listeners implementing this interface will be notified about all
	 * occurring notifications.
	 */
	public static interface IEMFChangeListener extends IMuvitorListener {
		/**
		 * @param model
		 *            the notifier causing the notification
		 * @param notification
		 *            the notification
		 */
		public void notifyChange(EObject model, Notification notification);
	}
	
	/**
	 * This interface is equivalent to {@link IEMFChangeListener} unless listening
	 * jobs will be notified after all other {@link IMuvitorListener} so that
	 * possible reactions to a notification have completed before.
	 */
	public static interface IEMFChangeListenerJob extends IEMFChangeListener {
	}
	
	/**
	 * This is the general interface for objects listening to the
	 * MuvitorNotifierService. It is used internally, only the interfaces
	 * extending this one are meant to be implemented.
	 */
	private static interface IMuvitorListener {
	}
	
	static private final HashMap<MuvitorTreeEditor, ListenerList> editor2Listeners = new HashMap<MuvitorTreeEditor, ListenerList>();
	
	static private final ListenerList globalListeners = new ListenerList();
	
	/**
	 * The last notification is kept for comparison in
	 * {@link #notifyListeners(Notification)} to avoid multiple notification
	 * distribution calls of several objects listening to the same EObject and
	 * its changes.
	 */
	static private Notification lastNotification;
	
	/**
	 * Adds a listener to the {@link MuvitorNotifierService}.
	 * 
	 * @param listener
	 *            the listener to be added
	 * @param editor
	 *            the editor in whose context notifications will be forwarded to
	 *            the listener, or <code>null</code> if it should receive all
	 *            notifications globally
	 */
	final static public void addListener(final IMuvitorListener listener,
			final MuvitorTreeEditor editor) {
		if (editor == null) {
			globalListeners.add(listener);
		} else {
			getListeners(editor).add(listener);
		}
	}
	
	/**
	 * Unregisters all listeners from the {@link MuvitorNotifierService} of a
	 * specific editor context.
	 */
	final static public void clear(final MuvitorTreeEditor editor) {
		final ListenerList listeners = editor2Listeners.remove(editor);
		if (listeners != null) {
			listeners.clear();
		}
	}
	
	/**
	 * This method passes a notification to all listeners registered to the
	 * GlobalNotifierService. This will be done according to the interfaces the
	 * listeners implement.
	 * 
	 * <p>
	 * In MuvitorKit editors all edit parts (should) extend the adapter edit
	 * parts that by default call this method if their models fire some
	 * notification.
	 * 
	 * <p>
	 * If the notification announces that an EObject has been deleted from the
	 * model all MuvitorKit views showing this particular EObject or one of its
	 * children will be closed.
	 * 
	 * @param notification
	 *            the notification to distribute to all registered listeners
	 * 
	 * @see IMuvitorListener
	 * @see IEMFChangeListener
	 * @see IChangeListenerJob
	 * 
	 * @see AdapterTreeEditPart
	 * @see AdapterGraphicalEditPart
	 * @see AdapterConnectionEditPart
	 */
	final static public void notifyListeners(final Notification notification) {
		/*
		 * little optimization to handle each notification only once even if
		 * passed by several sources consecutively. Removing an adapter and set/
		 * unset with the same result are not model changes and not important as
		 * well. And don't care if a model has is already deleted.
		 */
		final EObject notifier = (EObject) notification.getNotifier();
		
		if(notifier == null){
			return;
		}
		
		if (lastNotification == notification || notification.isTouch()
				|| notifier.eResource() == null) {
			return;
		}
		lastNotification = notification;
		
		// handle deletion of an element by hiding the view showing it or its
		// children
		if (notification.getEventType() == Notification.REMOVE
				&& notification.getOldValue() instanceof EObject
				&& ((EObject) notification.getOldValue()).eContainer() == null) {
			// a child has been removed from one of its parent's containment
			// lists and has not just been moved into another container
			final EObject deletedChild = (EObject) notification.getOldValue();
			MuvitorTreeEditor.closeViewShowing(deletedChild);
			for (final TreeIterator<EObject> it = deletedChild.eAllContents(); it.hasNext();) {
				MuvitorTreeEditor.closeViewShowing(it.next());
			}
		}
		
		final Object[] listeners = getListeners(IDUtil.getHostEditor(notifier)).getListeners();
		
		// handle all other model changes
		// handle normal listeners before starting jobs
		for (final Object listener : listeners) {
			if (listener instanceof IEMFChangeListener) {
				((IEMFChangeListener) listener).notifyChange(notifier, notification);
			}
		}
		for (final Object listener : globalListeners.getListeners()) {
			if (listener instanceof IEMFChangeListener) {
				((IEMFChangeListener) listener).notifyChange(notifier, notification);
			}
		}
		for (final Object listener : listeners) {
			if (listener instanceof IEMFChangeListenerJob) {
				((IEMFChangeListenerJob) listener).notifyChange(notifier, notification);
			}
		}
		for (final Object listener : globalListeners.getListeners()) {
			if (listener instanceof IEMFChangeListenerJob) {
				((IEMFChangeListenerJob) listener).notifyChange(notifier, notification);
			}
		}
		
	}
	
	/**
	 * Removes a listener from the {@link MuvitorNotifierService}.
	 * 
	 * @param listener
	 *            the listener to be removed
	 */
	final static public void removeListener(final IMuvitorListener listener) {
		globalListeners.remove(listener);
		for (final ListenerList listeners : editor2Listeners.values()) {
			listeners.remove(listener);
		}
	}
	
	final static private ListenerList getListeners(final MuvitorTreeEditor editor) {
		ListenerList listeners = editor2Listeners.get(editor);
		if (listeners == null) {
			listeners = new ListenerList();
			editor2Listeners.put(editor, listeners);
		}
		return listeners;
	}
	
	/**
	 * This class is purely static.
	 */
	private MuvitorNotifierService() {
	}
}

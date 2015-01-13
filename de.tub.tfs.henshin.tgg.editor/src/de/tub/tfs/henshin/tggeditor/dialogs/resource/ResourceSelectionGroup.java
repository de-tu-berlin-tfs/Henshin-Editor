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
package de.tub.tfs.henshin.tggeditor.dialogs.resource;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.part.DrillDownComposite;

/**
 * Workbench-level composite for choosing a {@link IResource}.
 */
public class ResourceSelectionGroup extends Composite implements
		ISelectionChangedListener, ModifyListener {
	
	/** The Constant DEFAULT_HEIGHT. */
	private static final int DEFAULT_HEIGHT = 300;

	/** The Constant DEFAULT_MSG_SELECT. */
	private static final String DEFAULT_MSG_SELECT = "Resource:";

	// sizing constants
	/** The Constant DEFAULT_WIDTH. */
	private static final int DEFAULT_WIDTH = 320;

	/** The allow new resource. */
	protected boolean allowNewResource = false;

	/** The error message. */
	protected String errorMessage;

	/** The resource input. */
	protected Text resourceInput;

	/** The tree viewer. */
	protected TreeViewer treeViewer;

	// Last selection made by user
	/** The selected res. */
	protected IResource selectedRes;

	/** The is valid. */
	protected boolean isValid = false;

	// The listener to notify of events
	/** The listener. */
	private Listener listener;

	/** The validator. */
	protected IResourceValidator validator = null;

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param composite the composite
	 * @param listener the listener
	 * @param allowNew the allow new
	 */
	public ResourceSelectionGroup(Composite composite, Listener listener,
			boolean allowNew) {
		this(composite, listener, allowNew, null, DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param parent the parent
	 * @param listener the listener
	 * @param allowNewResource the allow new resource
	 * @param validator the validator
	 */
	public ResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNewResource, IResourceValidator validator) {
		this(parent, listener, allowNewResource, validator, DEFAULT_MSG_SELECT,
				DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param parent the parent
	 * @param listener the listener
	 * @param allowNewResource the allow new resource
	 * @param validator the validator
	 * @param msg the msg
	 */
	public ResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNewResource, IResourceValidator validator, String msg) {
		this(parent, listener, allowNewResource, validator, msg,
				DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param parent the parent
	 * @param listener the listener
	 * @param allowNewResource the allow new resource
	 * @param validator the validator
	 * @param message the message
	 * @param hHint the h hint
	 * @param wHint the w hint
	 */
	public ResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNewResource, IResourceValidator validator,
			String message, int hHint, int wHint) {
		super(parent, SWT.NONE);

		this.listener = listener;
		this.allowNewResource = allowNewResource;
		this.validator = validator;

		createContents(message == null ? DEFAULT_MSG_SELECT : message,
				hHint <= 0 ? DEFAULT_HEIGHT : hHint, Math.max(wHint,
						DEFAULT_WIDTH));

		setInitialFocus();
	}

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param parent the parent
	 * @param listener the listener
	 * @param allowNew the allow new
	 * @param message the message
	 * @param heightHint the height hint
	 * @param widthHint the width hint
	 */
	public ResourceSelectionGroup(Composite parent, Listener listener,
			boolean allowNew, String message, int heightHint, int widthHint) {
		this(parent, listener, allowNew, null, message, heightHint, widthHint);
	}

	/**
	 * Instantiates a new resource selection group.
	 *
	 * @param composite the composite
	 * @param listener the listener
	 * @param allowNew the allow new
	 * @param msg the msg
	 */
	public ResourceSelectionGroup(Composite composite, Listener listener,
			boolean allowNew, String msg) {
		this(composite, listener, allowNew, msg, DEFAULT_HEIGHT, DEFAULT_WIDTH);
	}

	/**
	 * Gets the error message.
	 *
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Checks if is valid.
	 *
	 * @return the isValid
	 */
	public boolean isValid() {
		return isValid;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events
	 * .ModifyEvent)
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		validate();
		notifyListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		IStructuredSelection selection = (IStructuredSelection) event.getSelection();

		selectedRes = (IResource) selection.getFirstElement();

		if (selectedRes != null && allowNewResource) {
			resourceInput.removeModifyListener(this);
			resourceInput.setText(selectedRes.getFullPath().makeRelative().toString());
			resourceInput.addModifyListener(this);
		}

		validate();
		notifyListener();
	}

	/**
	 * Gives focus to one of the widgets in the group, as determined by the
	 * group.
	 */
	public void setInitialFocus() {
		if (allowNewResource) {
			resourceInput.setFocus();
		}
		else {
			treeViewer.getTree().setFocus();
		}
	}

	/**
	 * Sets the selected existing resource.
	 *
	 * @param res the new selected resource
	 */
	public void setSelectedResource(IResource res) {
		selectedRes = res;
		treeViewer.setSelection(new StructuredSelection(res), true);
	}

	/**
	 * Sets the validator.
	 *
	 * @param validator the validator to set
	 */
	public void setValidator(IResourceValidator validator) {
		this.validator = validator;
		((ResourceContentProvider) treeViewer.getContentProvider()).setContentValidator(validator);
	}

	/**
	 * Creates the contents of the composite.
	 *
	 * @param message the message
	 * @param heightHint the height hint
	 * @param widthHint the width hint
	 */
	protected void createContents(String message, int heightHint, int widthHint) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		setLayout(layout);
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTreeViewer(heightHint, widthHint);

		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		if (allowNewResource) {
			Composite searchComposite = new Composite(this, SWT.NONE);
			searchComposite.setLayout(layout);
			searchComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
					true, true));

			new Label(searchComposite, SWT.SHADOW_OUT).setText(message);
			resourceInput = new Text(searchComposite, SWT.SINGLE | SWT.BORDER
					| SWT.SEARCH);
			resourceInput.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
					true));
			resourceInput.addModifyListener(this);
		}

		Dialog.applyDialogFont(this);
	}

	/**
	 * Returns a new drill down viewer for this dialog.
	 *
	 * @param heightHint height hint for the drill down composite
	 * @param widthHint the width hint
	 */
	protected void createTreeViewer(int heightHint, int widthHint) {
		GridData spec = new GridData(SWT.FILL, SWT.FILL, true, true);
		spec.widthHint = widthHint;
		spec.heightHint = heightHint;

		DrillDownComposite drillDown = new DrillDownComposite(this, SWT.NONE);
		drillDown.setLayoutData(spec);

		// Create tree viewer inside drill down.
		treeViewer = new TreeViewer(drillDown, SWT.BORDER);

		drillDown.setChildTree(treeViewer);

		treeViewer.setContentProvider(new ResourceContentProvider(validator));
		treeViewer.setLabelProvider(WorkbenchLabelProvider.getDecoratingWorkbenchLabelProvider());
		treeViewer.setComparator(new ViewerComparator());
		treeViewer.setUseHashlookup(true);
		treeViewer.addSelectionChangedListener(this);
		treeViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				ISelection selection = event.getSelection();
				if (selection instanceof IStructuredSelection) {
					Object item = ((IStructuredSelection) selection).getFirstElement();
					if (item == null) {
						return;
					}
					if (treeViewer.getExpandedState(item)) {
						treeViewer.collapseToLevel(item, 1);
					}
					else {
						treeViewer.expandToLevel(item, 1);
					}
				}
			}
		});

		// This has to be done after the viewer has been laid out
		treeViewer.setInput(ResourcesPlugin.getWorkspace().getRoot());
	}

	/**
	 * Validate.
	 */
	protected void validate() {
		isValid = (!allowNewResource && selectedRes != null)
				|| (allowNewResource && !resourceInput.getText().isEmpty());

		if (!isValid) {
			errorMessage = "No resource is specified.";
		}
	}

	/**
	 * Gets the resource full path.
	 *
	 * @return the resource full path
	 */
	public IPath getResourceFullPath() {
		if (allowNewResource) {
			return new Path(resourceInput.getText());
		}

		return selectedRes.getFullPath();
	}

	/**
	 * Notify listener.
	 */
	protected void notifyListener() {
		// fire an event so the parent can update its controls
		if (listener != null) {
			Event changeEvent = new Event();
			changeEvent.type = SWT.Selection;
			changeEvent.widget = this;
			listener.handleEvent(changeEvent);
		}
	}
}

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
package de.tub.tfs.henshin.tggeditor.dialogs.resource;

import java.io.File;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class ResourcesDialog.
 * 
 */
public class ResourcesDialog extends TitleAreaDialog implements SelectionListener {
	
	/** The message. */
	protected String message;
	
	/** The resource group. */
	protected ResourceSelectionGroup resourceGroup;
	
	/** The title. */
	protected String title;
	
	/** The title image. */
	protected Image image;
	
	/** The result. */
	private File result;
	
	/** The type. */
	private int type;
	
	/**
	 * Instantiates a new resources dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param titleImage
	 *            the title image
	 * @param title
	 *            the title
	 * @param message
	 *            the message
	 * @param type
	 *            the type
	 */
	public ResourcesDialog(Shell parentShell, ImageDescriptor titleImage, String title,
			String message, int type) {
		super(parentShell);
		this.image = null;
		this.title = title;
		this.message = message;
		this.type = type;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		
		super.configureShell(newShell);
		newShell.setText(title);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.TitleAreaDialog#createContents(org.eclipse.
	 * swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Control content = super.createContents(parent);
		
		setTitle(title);
		setTitleImage(image);
		setMessage(message);
		
		return content;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		// top level composite
		Composite parentComposite = (Composite) super.createDialogArea(parent);
		
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parentComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = 0;// convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));
		composite.setFont(parentComposite.getFont());
		
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				setDialogComplete(validate());
			}
		};
		
		resourceGroup = new FileSelectionGroup(composite, listener, type == SWT.SAVE, "File name:");
		
		return parentComposite;
	}
	
	/**
	 * Sets the dialog complete.
	 * 
	 * @param value
	 *            the new dialog complete
	 */
	protected void setDialogComplete(boolean value) {
		getButton(OK).setEnabled(value);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		IPath path = resourceGroup.getResourceFullPath();
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(path);
		
		if (file.exists() && type == SWT.SAVE) {
			String[] buttons = new String[] { IDialogConstants.YES_LABEL,
					IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL };
			MessageDialog confirm = new MessageDialog(getShell(), "Overwrite Question", null,
					"The file" + file.getName()
							+ " already exists. Do you want to replace the existing file?",
					MessageDialog.QUESTION, buttons, 0);
			
			int result = confirm.open();
			
			switch (result) {
				case 0:
					break;
				case 1:
					return;
				default:
					cancelPressed();
					break;
			}
		}
		
		this.result = file.getRawLocation().toFile();
		super.okPressed();
	}
	
	/**
	 * Gets the result.
	 * 
	 * @return the result
	 */
	public File getResult() {
		return result;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse
	 * .swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button fileSystemBt = new Button(parent, SWT.PUSH);
		GridData layoutData = new GridData(SWT.BEGINNING, SWT.FILL, true, true, 3, 1);
		
		fileSystemBt.setLayoutData(layoutData);
		fileSystemBt.setText("File System...");
		
		fileSystemBt.addSelectionListener(this);
		
		((GridLayout) parent.getLayout()).numColumns++;
		((GridLayout) parent.getLayout()).numColumns++;
		((GridLayout) parent.getLayout()).numColumns++;
		super.createButtonsForButtonBar(parent);
		
		setDialogComplete(validate());
	}
	
	/**
	 * Returns whether this page's visual components all contain valid values.
	 * 
	 * @return otherwise
	 */
	protected boolean validate() {
		if (!resourceGroup.isValid()) {
			setErrorMessage(resourceGroup.getErrorMessage());
			return false;
		}
		
		setErrorMessage(null);
		return true;
	}
	
	/**
	 * Adds the file extensions.
	 * 
	 * @param extensions
	 *            the extensions
	 */
	public void addFileExtensions(String[] extensions) {
		if (getShell() == null) {
			create();
		}
		
		((FileSelectionGroup) resourceGroup).addFileExtensions(extensions);
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
	 * .events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		FileDialog sysDiag = new FileDialog(getShell(), type);
		Set<String> exts = ((FileSelectionGroup) resourceGroup).getFileExtensions();
		String extensions = "";
		
		for (String ext : exts) {
			extensions += "*." + ext + ";";
		}
		
		sysDiag.setText(title);
		sysDiag.setOverwrite(type == SWT.SAVE);
		sysDiag.setFilterExtensions(new String[] { extensions });
		String fileLoc = sysDiag.open();
		
		if (fileLoc != null) {
			IPath filePath = new Path(fileLoc);
			Set<String> expectedExt = ((FileSelectionGroup) resourceGroup).getFileExtensions();
			if (expectedExt.isEmpty() || expectedExt.contains(filePath.getFileExtension())) {
				this.result = filePath.toFile();
				super.okPressed();
			} else {
				MessageDialog.openError(getShell(), "Save File Error",
						"The specified file does not have the expected extension(s): "
								+ expectedExt.toString().replaceAll("[\\[,\\]]", ""));
			}
		}
	}
}

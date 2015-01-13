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
package de.tub.tfs.muvitor.ui.wizards.template;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.part.FileEditorInput;

import de.tub.tfs.muvitor.ui.MuvitorActivator;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * The "New" wizard page is used in the {@link MuvitorFileCreationWizard} and
 * allows setting the container for the new file as well as the file name. The
 * page will only accept file name without the extension OR with the extension
 * that matches the one specified in plugin.xml.
 * 
 * @author Tony Modica
 */
public class MuvitorFileCreationPage extends WizardNewFileCreationPage {
	
	private static int eCount = 1;
	
	final private IWorkbench workbench;
	
	public MuvitorFileCreationPage(final IWorkbench workbench, final IStructuredSelection selection) {
		super(MuvitorTreeEditor.fileExtension.toUpperCase() + " File Creation Wizard", selection);
		final String editorName = MuvitorActivator.getUniqueExtensionAttributeValue(
				"org.eclipse.ui.editors", "name");
		setTitle(editorName + " File");
		setDescription("This wizard creates a new file with extension '"
				+ MuvitorTreeEditor.fileExtension + "' that can be opened by the " + editorName);
		this.workbench = workbench;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		super.createControl(parent);
		setFileName(MuvitorTreeEditor.fileExtension.toUpperCase() + eCount + "."
				+ MuvitorTreeEditor.fileExtension);
		setPageComplete(validatePage());
	}
	
	public boolean finish() {
		final IFile newFile = createNewFile();
		if (newFile == null) {
			return false; // ie.- creation was unsuccessful
		}
		
		// the file resource has been created, open it for editing
		try {
			final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
			if (page != null) {
				final String editorID = MuvitorActivator.getUniqueExtensionAttributeValue(
						"org.eclipse.ui.editors", "id");
				page.openEditor(new FileEditorInput(newFile), editorID, true);
				// IDE.openEditor(page, newFile, true);
			}
		} catch (final PartInitException e) {
			e.printStackTrace();
			return false;
		}
		eCount++;
		return true;
	}
}
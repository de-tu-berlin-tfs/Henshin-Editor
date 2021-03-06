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
package de.tub.tfs.henshin.editor.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * This is a wizard to create a file resource for a Muvitor implementation. By
 * default, it is registered in the plugin.xml as creation wizard for the
 * editor.
 * 
 * <p>
 * The wizard uses a MuvitorFileCreationPage which creates one file with the
 * file extension that has been specified in plugin.xml.
 * 
 * @author Tony Modica
 */
public class HenshinFileCreationWizard extends Wizard implements INewWizard {

	/** The page. */
	private HenshinFileCreationPage page;

	/** The selection. */
	private IStructuredSelection selection;

	/** The workbench. */
	private IWorkbench workbench;

	/**
	 * Adding the RONFileCreationPage to the wizard.
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new HenshinFileCreationPage(workbench, selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 * 
	 * @return true, if successful
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return page.finish();
	}

	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 * 
	 * @param aWorkbench
	 *            the a workbench
	 * @param sel
	 *            the sel
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench aWorkbench, final IStructuredSelection sel) {
		workbench = aWorkbench;
		selection = sel;
	}

}

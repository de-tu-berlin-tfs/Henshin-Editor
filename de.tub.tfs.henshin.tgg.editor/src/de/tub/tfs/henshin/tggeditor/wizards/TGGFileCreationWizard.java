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
package de.tub.tfs.henshin.tggeditor.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import de.tub.tfs.muvitor.ui.wizards.template.MuvitorFileCreationPage;

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
public class TGGFileCreationWizard extends Wizard implements INewWizard {
	
	private MuvitorFileCreationPage page;
	
	private IStructuredSelection selection;
	
	private IWorkbench workbench;
	
	/**
	 * Adding the RONFileCreationPage to the wizard.
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#addPages()
	 */
	@Override
	public void addPages() {
		page = new MuvitorFileCreationPage(workbench, selection);
		addPage(page);
	}
	
	/**
	 * We will accept the selection in the workbench to see if we can initialize
	 * from it.
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench,
	 * org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(final IWorkbench aWorkbench, final IStructuredSelection sel) {
		workbench = aWorkbench;
		selection = sel;
	}
	
	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We
	 * will create an operation and run it using wizard as execution context.
	 */
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.wizard.IWizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		return page.finish();
	}
}

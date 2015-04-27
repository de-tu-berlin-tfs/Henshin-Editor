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
package de.tub.tfs.henshin.editor.actions.transSys;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transSys.ImportEcorModelCommand;
import de.tub.tfs.henshin.editor.ui.dialog.resources.ImportEMFModelDialog;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class ImportEcoreModelAction.
 * 
 * @author Johann, nam
 */
public class ImportEcoreModelAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ImportEcorModellAction";

	/** The transformation system. */
	private Module transformationSystem;

	/**
	 * Instantiates a new import ecore model action.
	 * 
	 * @param part
	 *            A workbench part.
	 */
	public ImportEcoreModelAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import EMF Ecore Model...");
		setToolTipText("Imports a registered EMF model");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjs = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjs.size() == 1) {
			Object selected = selectedObjs.get(0);

			if (selected instanceof EditPart) {
				EditPart host = (EditPart) selected;
				Object hostModel = host.getModel();

				if (hostModel instanceof Module) {
					transformationSystem = (Module) hostModel;
				}

				else if (hostModel instanceof EContainerDescriptor
						&& host.getAdapter(EPackage.class) != null) {
					transformationSystem = (Module) ((EContainerDescriptor) hostModel)
							.getContainer();
				}

			}
		}

		return transformationSystem != null;
	}

	/**
	 * Opens a dialog to select the ecore package(s) to import.
	 */
	@Override
	public void run() {
		ImportEMFModelDialog dialog = new ImportEMFModelDialog(
				getWorkbenchPart().getSite().getShell());

		dialog.setMultipleSelection(true);
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			List<EPackage> ePackages = new ArrayList<EPackage>();
			Object[] selections = dialog.getResult();

			if (selections != null) {
				for (Object object : selections) {
					if (object instanceof EPackage) {
						ePackages.add((EPackage) object);
					}
				}

				if (ePackages.size() > 0) {
					ImportEcorModelCommand command = new ImportEcorModelCommand(
							transformationSystem, ePackages);
					execute(command);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("epackage16.png");
	}

}

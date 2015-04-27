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
 * AddMetaModelDialog.java
 * created on 11.02.2012 21:05:56
 */
package de.tub.tfs.henshin.editor.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.provider.EcoreEditPlugin;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import de.tub.tfs.henshin.editor.interfaces.Messages;

/**
 * @author huuloi
 * 
 */
public class FilterMetaModelDialog extends ElementListSelectionDialog {

	private EPackage[] importedEPackages;

	public FilterMetaModelDialog(Shell parent, EPackage[] importedEPackages, String title, String message) {
		super(parent, new LabelProvider() {
			@Override
			public Image getImage(Object element) {
				return ExtendedImageRegistry.getInstance().getImage(
						EcoreEditPlugin.INSTANCE
								.getImage(Messages.EPACKAGE_IMG_PATH));
			}

			@Override
			public String getText(Object element) {
				if (element instanceof EPackage) {
					EPackage ePackage = (EPackage) element;
					return ePackage.getName();
				}
				return super.getText(element);
			}
		});

		setMultipleSelection(message.equals(Messages.FILTER_META_MODEL_DESC));
		setFilter(Messages.FILTER_ALL);
		setMessage(message);
		setTitle(title);

		this.importedEPackages = importedEPackages;
	}

	@Override
	protected Control createDialogArea(Composite parent) {

		super.createDialogArea(parent);

		setListElements(importedEPackages);

		return parent;
	}

	public List<EPackage> getMetaModels() {

		List<EPackage> ePackages = new ArrayList<EPackage>();

		Object[] result = super.getResult();

		for (int i = 0; i < result.length; i++) {
			if (result[i] instanceof EPackage) {
				EPackage ePackage = (EPackage) result[i];
				ePackages.add(ePackage);
			}
		}

		return ePackages;
	}
}

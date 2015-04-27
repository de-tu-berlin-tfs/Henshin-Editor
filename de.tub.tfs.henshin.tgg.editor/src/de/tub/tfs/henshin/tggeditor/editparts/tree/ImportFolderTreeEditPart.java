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
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * EditPart of the folder for imported models. 
 */

public class ImportFolderTreeEditPart  extends AdapterTreeEditPart<ImportFolder> {
	private List<ImportedPackage> imports;
	
	public ImportFolderTreeEditPart(ImportFolder model) {
		super(model);
		this.imports = model.getImports();
	}

	@Override
	protected String getText() {
		return "Imports";
	}

	@Override
	protected List<ImportedPackage> getModelChildren() {
		return getCastedModel().getImports();
	}

	@Override
	protected void notifyChanged(Notification notification) {
		refresh();
		super.notifyChanged(notification);
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("importFolder16.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	} 
	
	
}

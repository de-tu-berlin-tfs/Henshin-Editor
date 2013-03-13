package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EPackage;
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
		return this.imports;
	}

	@Override
	protected void notifyChanged(Notification notification) {
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

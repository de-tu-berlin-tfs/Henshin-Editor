package tggeditor.editparts.tree.critical;

import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class CritPairFolderTreeEditPart extends AdapterTreeEditPart<CritPairFolder> {
	/**
	 * List of checked rule pairs
	 */
	private List<CheckedRulePairFolder> checkedRulePairFolders;
	
	public CritPairFolderTreeEditPart(CritPairFolder model) {
		super(model);
		checkedRulePairFolders = model.getCheckedRulePairFolders();
	}
	
	@Override
	protected String getText() {
		return "CriticalPairs";
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("CritPairs.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List<CheckedRulePairFolder> getModelChildren() {
		return checkedRulePairFolders;
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
}

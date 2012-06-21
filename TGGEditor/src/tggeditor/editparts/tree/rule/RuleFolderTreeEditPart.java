package tggeditor.editparts.tree.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import tggeditor.util.IconUtil;

import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * TreeEditPart for the folder for rules.
 */
public class RuleFolderTreeEditPart extends AdapterTreeEditPart<RuleFolder> {
	/**
	 * A list of rules.
	 */
	private List<Rule> rules;
	
	public RuleFolderTreeEditPart(RuleFolder model) {
		super(model);
		this.rules = model.getRules();
	}

	@Override
	protected String getText() {
		return "Rules";
	}

	@Override
	protected Image getImage(){
		try {
			return IconUtil.getIcon("ruleFolder.png");
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected List<Rule> getModelChildren() {
		return this.rules;
	}

	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
}
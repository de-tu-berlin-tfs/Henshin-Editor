package tggeditor.editparts.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import tgg.CritPair;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

public class CritPairFolderTreeEditPart extends AdapterTreeEditPart<CritPairFolder> {

	/**
	 * List of critical pairs.
	 */
	private List<CritPair> critPairs;
	/**
	 * List of overlapping graphs relative to the critical pairs.
	 */
	private List<Graph> graphs;
	/**
	 * List of rules relative to the critical pairs. 
	 */
	private List<Rule> rules; 
	
	public CritPairFolderTreeEditPart(CritPairFolder model) {
		super(model);
		critPairs = model.getCritPairs();
		graphs = new ArrayList<Graph>();
		rules = new ArrayList<Rule>();
		for (CritPair c : critPairs) {
			graphs.add(c.getOverlapping());
			rules.add(c.getRule1());
			rules.add(c.getRule2());
//			mappings.add(c.get)
		}
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
	protected List<CritPair> getModelChildren() {
		return critPairs;
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
}

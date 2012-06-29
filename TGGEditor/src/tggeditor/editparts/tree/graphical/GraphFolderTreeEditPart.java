package tggeditor.editparts.tree.graphical;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * EditPart for the graph folder in the tree editor.
 */

public class GraphFolderTreeEditPart extends AdapterTreeEditPart<GraphFolder> {
	private List<Graph> graphs;
	
	public GraphFolderTreeEditPart(GraphFolder model){
		super(model);
		this.graphs = model.getGraphs();
	}

	@Override
	protected String getText() {
		return "Graphs";
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("graphFolder18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected List<Graph> getModelChildren() {
		return this.graphs;
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}

}

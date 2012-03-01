/**
 * ModelSearchToolEntry.java
 * created on 01.03.2012 09:45:29 
 */
package de.tub.tfs.henshin.editor.tools;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.ToolEntry;

import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class ModelSearchToolEntry extends ToolEntry {

	private Graph graph;
	
	public ModelSearchToolEntry() {
		this(null);
	}
	
	public ModelSearchToolEntry(String label) {
		this(label, null, null);
	}
	
	public ModelSearchToolEntry(String label, String shortDesc, Graph graph) {
		super(
			label,
			shortDesc,
			ResourceUtil.ICONS.MODEL_SEARCH_TOOL.descr(Constants.SIZE_16),
			ResourceUtil.ICONS.MODEL_SEARCH_TOOL.descr(Constants.SIZE_24),
			ModelSearchTool.class
		);
		
		this.graph = graph;
	}
	
	@Override
	public Tool createTool() {
		ModelSearchTool tool;
		try {
			tool = ModelSearchTool.class.newInstance();
		} catch (IllegalAccessException iae) {
			return null;
		} catch (InstantiationException ie) {
			return null;
		}
		tool.setGraph(graph);
		tool.setProperties(getToolProperties());
		return tool;
	}
	
}

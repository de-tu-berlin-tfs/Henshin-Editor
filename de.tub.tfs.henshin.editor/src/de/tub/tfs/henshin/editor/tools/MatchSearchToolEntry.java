/**
 * 
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
public class MatchSearchToolEntry extends ToolEntry {

	private Graph graph;
	
	public MatchSearchToolEntry() {
		this(null);
	}
	
	public MatchSearchToolEntry(String label) {
		this(label, null, null);
	}
	
	public MatchSearchToolEntry(String label, String shortDesc, Graph graph) {
		
		super(
			label, 
			shortDesc, 
			ResourceUtil.ICONS.MATCH_SEARCH_TOOL.descr(Constants.SIZE_16), 
			ResourceUtil.ICONS.MATCH_SEARCH_TOOL.descr(Constants.SIZE_24), 
			MatchSearchTool.class
		);
		
		this.graph = graph;
	}
	
	@Override
	public Tool createTool() {
		MatchSearchTool tool;
		try {
			tool = MatchSearchTool.class.newInstance();
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

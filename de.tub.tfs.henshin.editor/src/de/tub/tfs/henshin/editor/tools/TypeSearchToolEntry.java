/**
 * TypeSearchToolEntry.java
 * created on 07.02.2012 22:21:39
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
public class TypeSearchToolEntry extends ToolEntry {
	
	private Graph graph;
	
	public TypeSearchToolEntry() {
		this(null);
	}

	public TypeSearchToolEntry(String label) {
		this(label, null, null);
	}

	public TypeSearchToolEntry(String label, String shortDesc, Graph graph) {

		super(
			label, 
			shortDesc, 
			ResourceUtil.ICONS.TYPE_SEARCH_TOOL.descr(Constants.SIZE_16), 
			ResourceUtil.ICONS.TYPE_SEARCH_TOOL.descr(Constants.SIZE_24), 
			TypeSearchTool.class
		);
		this.graph = graph;
	}
	
	@Override
	public Tool createTool() {
		TypeSearchTool tool;
		try {
			tool = TypeSearchTool.class.newInstance();
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

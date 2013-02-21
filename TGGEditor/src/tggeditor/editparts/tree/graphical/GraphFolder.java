package tggeditor.editparts.tree.graphical;

import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;

/**
 * A folder for graphs in the tree editor.
 */

public class GraphFolder extends EObjectImpl {
	private Module sys;
	private List<Graph> graphs;
	
	public GraphFolder(Module sys){
		this.sys = sys;
		graphs = this.sys.getInstances();		
	}

	public List<Graph> getGraphs(){
		return this.graphs;
	}
	
}

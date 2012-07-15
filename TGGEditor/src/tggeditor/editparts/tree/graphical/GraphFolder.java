package tggeditor.editparts.tree.graphical;

import java.util.List;

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.TransformationSystem;

/**
 * A folder for graphs in the tree editor.
 */

public class GraphFolder extends EObjectImpl {
	private TransformationSystem sys;
	private List<Graph> graphs;
	
	public GraphFolder(TransformationSystem sys){
		this.sys = sys;
		graphs = this.sys.getInstances();		
	}

	public List<Graph> getGraphs(){
		return this.graphs;
	}
	
}

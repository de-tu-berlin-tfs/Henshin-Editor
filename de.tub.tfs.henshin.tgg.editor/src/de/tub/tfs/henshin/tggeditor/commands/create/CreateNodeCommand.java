/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.create;


import java.util.List;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


/**
 * The class CreateNodeCommand creates a node in a graph.
 */
public class CreateNodeCommand extends Command {
	
	private static final String LABEL = "Create Node ";
	
	public static final int Y_DEFAULT = 30;
	
	/** The graph. */
	private final TripleGraph graph;
	
	/** The node. */
	private TNode node;
	
	/** The type, e.g. classdiagram, class, table. */
	private EClass type;
	
	/** The layout system. */
	private TGG layout;
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The node graph type, whether source, correspondence or target */
	private TripleComponent nodeTripleComponent;

	/**
	 * name of the node
	 */
	private String name = "";
	
	/**the constructor
	 * @param graph the graph in which the node is to be created
	 * @param name the name of the new node
	 * @param location the location for the nodelayout
	 */
	public CreateNodeCommand(TripleGraph graph, String name, Point location) {
		this.graph = graph;
		this.node = TggFactory.eINSTANCE.createTNode();
		this.nodeTripleComponent = TripleComponent.SOURCE;
		setName(name);
		
		setLocation(location);
		this.layout = GraphicalNodeUtil.getLayoutSystem(graph);
	}

	
	/**the constructor
	 * @param n the already created node
	 * @param graph the graph in which the node is to be created
	 * @param location the location for the nodelayout
	 * @param nodeGraphType the nodeGraphType can be source, target or correspondence
	 */
	public CreateNodeCommand(TNode n, TripleGraph graph, Point location, TripleComponent component) {
		this.graph = graph;
		this.node = n;
		setLocation(location);
		this.nodeTripleComponent = component;
		type = n.getType();

		this.layout = GraphicalNodeUtil.getLayoutSystem(graph);
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (type != null) {
			node.setType(type);
		}
		
		node.setX(x);
		node.setY(y);
		node.setName(name);
		node.setComponent(nodeTripleComponent);
		graph.getNodes().add(node);
		//NEW GERARD
		//node.setMarkerType(RuleUtil.NEW_Graph);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		graph.getNodes().remove(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		if (layout == null)
			return false;
		if(RuleUtil.graphIsOpRuleRHS(graph))
			return false;
		return true;
	}


	/**Checks if the already set type (classdiagram/class/table) fits to the nodeGraphType (source/
	 * correspondence/target). Needed for the Tools which create already typed nodes. 
	 * @return true if everything is okay
	 */
	private boolean typeFitsToGraphtype() {
		boolean result = true;
		if (type != null) {
			EList<ImportedPackage> impPackages = layout.getImportedPkgs();
			List<ImportedPackage> restrictedImportedPkgsImportedPackages = NodeTypes.getImportedPackagesOfComponent(impPackages,nodeTripleComponent);
			List<EPackage> ePkgs = NodeTypes.getEPackagesFromImportedPackages(restrictedImportedPkgsImportedPackages);
			if (ePkgs != null) {
				EPackage ecorePackage = EcorePackage.eINSTANCE;
				List<EClass> ecoreNodeTypes = NodeTypes.getNodeTypesOfEPackage(ecorePackage,true);
				
				result = NodeTypes.getNodeTypesOfEPackages(ePkgs, false).contains(type)
						|| ecoreNodeTypes.contains(type);
			} else {
				result = false;
			}
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return graph != null;
	}

	/**
	 * Sets the node type.
	 *
	 * @param type the new node type
	 */
	public void setNodeType(EClass type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		graph.getNodes().add(node);
	}
	
	/**
	 * Gets the created node.
	 * @return the created node
	 */
	public Node getNode() {
		return node;
	}
	
	/**
	 * Gets the graph.
	 * @return the graph
	 */
	public Graph getGraph() {
		return this.graph;
	}
	
	
	/**
	 * @return the tgg layout model
	 */
	public TGG getLayoutModel() {
		return this.layout;
	}
	
	/**
	 * @return the location
	 */
	public Point getLocation() {
		return new Point(this.x, this.y);
	}
	
	/**
	 * @param name the name for the node
	 */
	public void setName(final String name) {
		this.name = name;
		setLabel(LABEL + "'" + name + "'");
	}
	
	/**
	 * @return the nodeGraphType (source/correspondece/target)
	 */
	public TripleComponent getNodeTripleComponent() {
		return nodeTripleComponent;
	}
	
	/**
	 * @param location the location for the nodelayout
	 */
	public void setLocation(Point location) {
		if(location != null) {
			this.x = location.x;
			this.y = location.y;
		} else {
			this.x = GraphUtil.getMinXCoordinateForNodeGraphType(nodeTripleComponent);
			this.y = Y_DEFAULT;
		}
	}
}

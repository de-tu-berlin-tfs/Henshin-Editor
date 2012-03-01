package de.tub.tfs.henshin.editor.commands.graph;

import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.util.HenshinMultiRuleUtil;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * The Class CreateNodeCommand.
 */
public class CreateNodeCommand extends Command {

	/**
	 * A default x-coordinate of the new created {@link Node}'s
	 * {@link NodeLayout}
	 */
	public static final int X_DEFAULT = 30;

	/**
	 * A default y-coordinate of the new created {@link Node}'s
	 * {@link NodeLayout}
	 */
	public static final int Y_DEFAULT = 30;

	/** The graph. */
	private final Graph graph;

	/** The node. */
	private Node node;

	/** The node layout. */
	private NodeLayout nodeLayout;

	/** The type. */
	private EClass type;

	/** The layout system. */
	private LayoutSystem layoutSystem;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** Stores the value whether a node layout is enabled or not. */
	private boolean enabled;

	/** Stores the value if a node is a multi node or a single node. */
	private boolean multi;

	
	private LinkedList<Node> multiNodes = new LinkedList<Node>();
	/**
	 * Instantiates a new command to create a node with the given parameters and
	 * initially, the node is only a single node and the node layout is enabled.
	 * 
	 * @param graph
	 *            the graph
	 * @param node
	 *            the node
	 * @param x
	 *            the Coordinate x
	 * @param y
	 *            the Coordinate y
	 */
	public CreateNodeCommand(Graph graph, Node node, int x, int y) {
		this(graph, node, null, x, y, true, false);
	}

	/**
	 * Instantiates a new command to create a node with the given parameters and
	 * initially, the node is only a single node.
	 * 
	 * @param graph
	 *            the graph
	 * @param eClass
	 *            the e class
	 */
	public CreateNodeCommand(Graph graph, EClass eClass) {
		this(graph, eClass, false);
	}

	/**
	 * Instantiates a new command to create a node with the given parameters and
	 * default x and y positions
	 * 
	 * @param graph
	 *            the graph
	 * @param eClass
	 *            the e class
	 */
	public CreateNodeCommand(Graph graph, EClass eClass, boolean multi) {
		super("Creating Node");

		Point pos = HenshinLayoutUtil.INSTANCE.calcNodeInsertPosition(graph,
				X_DEFAULT, Y_DEFAULT);

		this.graph = graph;
		this.node = HenshinFactory.eINSTANCE.createNode();
		this.type = eClass;
		this.x = pos.x;
		this.y = pos.y;
		this.multi = multi;
	}

	/**
	 * Instantiates a new command to create a node with the given parameters.
	 * 
	 * @param graph
	 *            the graph
	 * @param node
	 *            the node
	 * @param x
	 *            the Coordinate x
	 * @param y
	 *            the Coordinate y
	 */
	public CreateNodeCommand(Graph graph, Node node, int x, int y,
			boolean enabled, boolean multi) {
		super("Creating Node");

		this.graph = graph;
		this.node = node;
		this.type = node.getType();
		this.x = x;
		this.y = y;
		this.enabled = enabled;
		this.multi = multi;
	}

	/**
	 * Default constructor. Initializes all required parameters with the given
	 * ones.
	 * 
	 * @param graph
	 *            the containing {@link Graph} of the new {@link Node}
	 * @param node
	 *            the new {@link Node} to be created.
	 * @param type
	 *            a type as {@link EClass} for the new {@link Node}.
	 * @param x
	 *            the x-coordinate to layout the new {@link Node} in the
	 *            graphical view
	 * @param y
	 *            the y-coordinate to layout the new {@link Node} in the
	 *            graphical view
	 * @param enabled
	 *            sets the proper {@link NodeLayout} being enabled
	 * @param multi
	 *            sets the proper {@link NodeLayout} as a single or multi node
	 */
	public CreateNodeCommand(Graph graph, Node node, EClass type, int x, int y,
			boolean enabled, boolean multi) {
		super("Creating Node");

		this.graph = graph;
		this.node = node;
		this.type = type;
		this.x = x;
		this.y = y;
		this.enabled = enabled;
		this.multi = multi;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (type != null) {
			node.setType(type);
		}

		this.layoutSystem = ModelUtil.getModelRoot(graph, LayoutSystem.class);

		nodeLayout = getNodeLayout();
		nodeLayout.setX(x);
		nodeLayout.setY(y);
		nodeLayout.setEnabled(enabled);
		nodeLayout.setColor(0);
		nodeLayout.setMulti(multi);

		layoutSystem.getLayouts().add(nodeLayout);
		graph.getNodes().add(node);
	
		Collection<Graph> dependentGraphs = HenshinMultiRuleUtil.getDependentGraphs(graph);
		for (Graph multiGraph : dependentGraphs) {
			
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		graph.getNodes().remove(node);
		layoutSystem.getLayouts().remove(nodeLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return graph != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return graph != null;
	}

	/**
	 * Sets the node type.
	 * 
	 * @param type
	 *            the new node type
	 */
	public void setNodeType(EClass type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		layoutSystem.getLayouts().add(nodeLayout);
		graph.getNodes().add(node);
	}

	public Node getNode() {
		return node;
	}

	/**
	 * @return the nodeLayout
	 */
	public NodeLayout getNodeLayout() {
		if (nodeLayout == null) {
			nodeLayout = HenshinLayoutFactory.eINSTANCE.createNodeLayout();
		}

		nodeLayout.setModel(this.node);
		return nodeLayout;
	}
}

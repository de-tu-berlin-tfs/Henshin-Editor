package tggeditor.commands.delete;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.EdgeLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.util.EdgeUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * DeleteNodeCommand deletes a node.
 */
public class DeleteNodeCommand extends CompoundCommand {
	/**
	 * The constructor creates a compound command in all the deletion operations
	 * Be included, which are needed to the nodes of the graph to
	 * Remove. In this all incoming and outgoing edges, and
	 * The attributes individually deleted.
	 *
	 * @param node the node to delete
	 */
	public DeleteNodeCommand(Node node) {
		
		TGG layoutSystem=NodeUtil.getLayoutSystem(node);
		
		Iterator<Edge> iter = node.getIncoming().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
			if (layoutSystem != null) {
				EdgeLayout edgelayout = EdgeUtil.getEdgeLayout(edge);
				add(new DeleteEdgeLayoutCommand(layoutSystem, edgelayout));
			}
		}
		iter = node.getOutgoing().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
			if (layoutSystem != null) {
				EdgeLayout edgelayout = EdgeUtil.getEdgeLayout(edge);
				add(new DeleteEdgeLayoutCommand(layoutSystem, edgelayout));
			}
		}
		Iterator<Attribute> iterAtt = node.getAttributes().iterator();
		while (iter.hasNext()) {
			Attribute attr = iterAtt.next();
			add(new DeleteAttributeCommand(attr));
		}

		add(new SimpleDeleteEObjectCommand(node));
		
		// nodeLayout delete as last command, because on undo nodeLayout must be restored before
		// node, else NodeObjectEditPart will create new nodeLayout
		
		if (layoutSystem!=null){
			NodeLayout nodeLayout=NodeUtil.getNodeLayout(node);
			add(new DeleteNodeLayoutCommand(layoutSystem,nodeLayout));
		}
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
	}
}

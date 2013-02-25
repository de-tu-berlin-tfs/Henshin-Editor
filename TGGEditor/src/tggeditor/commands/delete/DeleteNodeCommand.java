package tggeditor.commands.delete;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

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

		Iterator<Edge> iter = node.getIncoming().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}
		iter = node.getOutgoing().iterator();
		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}
		// attributes are completey contained in the node and have no references from outside -> they do not need to be deleted explicitely
//		Iterator<Attribute> iterAtt = node.getAttributes().iterator();
//		while (iter.hasNext()) {
//			Attribute attr = iterAtt.next();
//			add(new DeleteAttributeCommand(attr));
//		}

		
		add(new SimpleDeleteEObjectCommand(node));

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

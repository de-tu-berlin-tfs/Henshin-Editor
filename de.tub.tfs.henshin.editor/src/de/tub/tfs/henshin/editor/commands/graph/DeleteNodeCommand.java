package de.tub.tfs.henshin.editor.commands.graph;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * A {@link Command command} to delete {@link Node nodes}.
 * 
 * <p>
 * All incoming an outgoing {@link Edge edges} of the given {@link Node node}
 * will also be removed.
 * </p>
 */
public class DeleteNodeCommand extends CompoundCommand {

	
	
	private Node node;

	/**
	 * Constructs a {@link DeleteNodeCommand} to delete a given {@link Node
	 * node}.
	 * 
	 * @param node
	 *            the {@link Node node} to delete
	 */
	public DeleteNodeCommand(Node node) {
		this.node = node;
		
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

		Layout layout = HenshinLayoutUtil.INSTANCE.getLayout(node);

		add(new SimpleDeleteEObjectCommand(node));

		if (layout != null) {
			add(new SimpleDeleteEObjectCommand(layout));
		}
	}
	
	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		if (HenshinLayoutUtil.INSTANCE.belongsToMultiRule(node) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(node)){
			return false;
		}
			
		return super.canExecute();
	}
}

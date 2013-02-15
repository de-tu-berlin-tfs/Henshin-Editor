package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.GraphLayout;
import tgg.TGG;
import tggeditor.commands.delete.DeleteGraphCommand;
import tggeditor.commands.delete.DeleteNodeCommand;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteRuleTGGGraphCommand extends CompoundCommand {

//	/**
//	 * The graph to be deleted by the command. The graph may belong to a rule or may be an instance graph of the transformation sytem.
//	 */
//	private Graph graph;
	/**
	 * Layout element of rule to be deleted: The the divider between source and correspondence component.
	 */
	private GraphLayout divSC;
	/**
	 * Layout element of rule to be deleted: The the divider between correspondence and target component. 
	 */
	private GraphLayout divCT;
	/**
	 * The layout system of the rule to be deleted.
	 */
	private TGG tgg;

	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteRuleTGGGraphCommand(Graph graph) {
		
//		this.graph=graph;
		tgg = NodeUtil.getLayoutSystem(graph);
		final GraphLayout[] layouts = GraphUtil.getGraphLayouts(graph);
		divSC=layouts[0];
		divCT=layouts[1];
		for(Node node:graph.getNodes()) {
			add(new DeleteRuleNodeCommand(node));
		}		
		add(new SimpleDeleteEObjectCommand(graph));
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (tgg != null) && (divSC != null) && (divCT != null)
				&& (super.canExecute());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return (tgg != null) && (divSC != null) && (divCT != null)
				&& (super.canUndo());
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
		tgg.getGraphlayouts().add(divSC);
		tgg.getGraphlayouts().add(divCT);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
		removeAdjacentElements();		
	}

	/**
	 * 
	 */
	private void removeAdjacentElements() {
		// remove graphLayouts (divider information of graph) from TGG
		tgg.getGraphlayouts().remove(divSC);
		tgg.getGraphlayouts().remove(divCT);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		super.redo();
		removeAdjacentElements();		
	}
}

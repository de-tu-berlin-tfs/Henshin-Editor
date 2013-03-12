package de.tub.tfs.henshin.tggeditor.commands.create;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

/**
 * The class CreateGraphCommand creates a tgg graph.
 */
public class CreateGraphCommand extends Command {
	
	/** The transformation system. */
	private Module module;
	
	/** The graph. */
	private Graph graph;
	
	/** The name. */
	private String name;
	
	/**
	 * Instantiates a new creates the graph command.
	 *
	 * @param transSys the transformation system
	 * @param name the name
	 */
	public CreateGraphCommand(Module transSys, String name) {
		this.module = transSys;
		this.name = name;
		this.graph = HenshinFactory.eINSTANCE.createGraph();

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		graph.setName(name);
		module.getInstances().add(graph);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		module.getInstances().remove(graph);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return name != null && module != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return graph != null && module != null;
	}
}

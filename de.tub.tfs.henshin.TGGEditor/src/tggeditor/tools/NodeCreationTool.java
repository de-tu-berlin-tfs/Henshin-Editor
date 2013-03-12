package tggeditor.tools;


import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.widgets.Shell;

import tggeditor.commands.create.CreateNodeCommand;
import tggeditor.util.dialogs.DialogUtil;

/**
 * The NodeCreationTool creates nodes in a graph.
 * @see tggeditor.commands.create.CreateNodeCommand
 */
public class NodeCreationTool extends CreationTool {
	public NodeCreationTool() {
		super();
//		setUnloadWhenFinished(false);
		// TODO Auto-generated constructor stub
	}

	/**
	 * the constructor
	 * @param factory
	 */
	public NodeCreationTool(CreationFactory factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands.Command)
	 */
	@Override
	protected void executeCommand(final Command command) {
		
		if (command instanceof CreateNodeCommand) {
			final CreateNodeCommand c = (CreateNodeCommand) command;
			//Module trafoSys;
			if (c.getGraph().eContainer() == null){
				return;
			}
			//trafoSys = (Module) EcoreUtil.getRootContainer(c.getGraph());

			
			Shell shell = new Shell();
			
			String nodeName = "";
			EClass eClass = (DialogUtil.runNodeCreationDialog(shell, c));
			shell.dispose();

			if (eClass != null) {
				c.setName(nodeName);
				c.setNodeType(eClass);
				super.executeCommand(command);
			}
		}
		
		
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
		// super.handleFinished();

	}
}

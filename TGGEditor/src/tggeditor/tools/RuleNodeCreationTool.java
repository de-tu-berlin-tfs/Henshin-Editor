package tggeditor.tools;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.widgets.Shell;

import tggeditor.commands.create.CreateNodeCommand;
import tggeditor.commands.create.rule.CreateRuleNodeCommand;
import tggeditor.util.dialogs.DialogUtil;

/**
 * The RuleNodeCreationTool creates nodes in a rule.
 * @see tggeditor.commands.create.rule.CreateRuleNodeCommand
 */
public class RuleNodeCreationTool extends CreationTool {
	/**
	 * the constructor
	 */
	public RuleNodeCreationTool() {
		super();
	}

	/**
	 * the constructor
	 * @param factory
	 */
	public RuleNodeCreationTool(CreationFactory factory) {
		super(factory);
	}

	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands.Command)
	 */
	@Override
	protected void executeCommand(final Command command) {
		
		if (command instanceof CreateNodeCommand ||
				command instanceof CreateRuleNodeCommand) {
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

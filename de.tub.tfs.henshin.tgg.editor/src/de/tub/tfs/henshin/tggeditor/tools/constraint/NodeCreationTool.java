package de.tub.tfs.henshin.tggeditor.tools.constraint;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.util.dialogs.constraint.PlainGraphDialogUtil;

public class NodeCreationTool extends CreationTool {

	@Override
	protected void executeCommand(Command command) {
		if (command instanceof CreateNodeCommand) {
			CreateNodeCommand c = (CreateNodeCommand) command;

			Shell shell = new Shell();
			
			EClass eClass = (PlainGraphDialogUtil.runPlainNodeCreationDialog(shell, c));
			shell.dispose();

			if (eClass != null) {
				c.setNodeName("");
				c.setNodeType(eClass);
				super.executeCommand(command);
			}
		}
	}
	
}

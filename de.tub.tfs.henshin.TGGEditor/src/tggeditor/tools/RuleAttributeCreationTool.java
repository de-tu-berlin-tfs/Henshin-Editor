package tggeditor.tools;

import java.util.AbstractMap.SimpleEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.PlatformUI;

import tggeditor.commands.create.CreateAttributeCommand;
import tggeditor.commands.create.rule.CreateRuleAttributeCommand;
import tggeditor.util.dialogs.DialogUtil;

/**
 * The AttributeCreationTool creates Attributes for a node.
 * @see tggeditor.commands.create.CreateAttributeCommand
 */
public class RuleAttributeCreationTool extends CreationTool {
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands.Command)
	 */
	@Override
	protected void executeCommand(final Command command) {
		if (command instanceof CreateRuleAttributeCommand) {
			CreateRuleAttributeCommand c = (CreateRuleAttributeCommand) command;
			SimpleEntry<EAttribute, String> result = DialogUtil
					.runAttributeCreationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().getActivePart().getSite().getShell(), c.getNode());
			if (result.getKey() != null && result.getValue() != null) {
				c.setLabel(result.getKey().getName()+":"+result.getValue());
				c.setAttributeType(result.getKey());
				c.setValue(result.getValue());
				super.executeCommand(c);
			}
		}
		else if (command instanceof CreateAttributeCommand) {
			CreateAttributeCommand c = (CreateAttributeCommand) command;
			SimpleEntry<EAttribute, String> result = DialogUtil
					.runAttributeCreationDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow()
							.getActivePage().getActivePart().getSite().getShell(), c.getNode());
			if (result.getKey() != null && result.getValue() != null) {
				c.setLabel(result.getKey().getName()+":"+result.getValue());
				c.setAttributeType(result.getKey());
				c.setValue(result.getValue());
				super.executeCommand(c);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.tools.AbstractTool#handleFinished()
	 */
	@Override
	protected void handleFinished() {
		reactivate();
//		super.handleFinished();
	}
}

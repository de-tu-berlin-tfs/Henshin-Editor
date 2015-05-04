package de.tub.tfs.henshin.tggeditor.tools.constraint;

import java.util.AbstractMap.SimpleEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tggeditor.commands.create.constraint.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;

public class AttributeCreationTool extends CreationTool {

	@Override
	protected void executeCommand(Command command) {
		if (command instanceof CreateAttributeCommand) {
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
	
	@Override
	protected void handleFinished() {
		reactivate();
	}
	
}

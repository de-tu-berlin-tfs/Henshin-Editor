/**
 * This class adds  Attribute types in CreateAttributtesCommand and executes the command.
 */
package de.tub.tfs.henshin.editor.actions.graph.tools;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.graph.CreateAttributesCommand;
import de.tub.tfs.henshin.editor.util.DialogUtil;

/**
 * The Class AttributeCreationTool.
 */
public class AttributeCreationTool extends CreationTool {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		if (getCurrentCommand() instanceof CreateAttributesCommand) {
			CreateAttributesCommand currentCmd = (CreateAttributesCommand) getCurrentCommand();
			Map<EAttribute, String> attributen = DialogUtil
					.runAttributeCreationDialog(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage()
							.getActivePart().getSite().getShell(),
							currentCmd.getNode());
			if (attributen.size() > 0) {
				for (EAttribute attr : attributen.keySet()) {
					currentCmd.addCreateAttributeCommand(attr,
							attributen.get(attr));
				}
				super.executeCurrentCommand();
			}
		}

	}

}

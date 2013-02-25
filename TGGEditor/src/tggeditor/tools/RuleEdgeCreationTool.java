package tggeditor.tools;

import java.util.List;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.ConnectionCreationTool;
import org.eclipse.ui.PlatformUI;

import tggeditor.commands.create.CreateEdgeCommand;
import tggeditor.commands.create.rule.CreateRuleEdgeCommand;
import tggeditor.util.EdgeReferences;
import tggeditor.util.dialogs.DialogUtil;

/**
 * The RuleEdgeCreationTool creates edges in a rule.
 * @see tggeditor.commands.create.rule.CreateRuleEdgeCommand
 */
public class RuleEdgeCreationTool extends ConnectionCreationTool {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.AbstractTool#executeCurrentCommand()
	 */
	@Override
	protected void executeCurrentCommand() {
		final Command currentCmd = getCurrentCommand();
		if (currentCmd instanceof CreateRuleEdgeCommand ||
				currentCmd instanceof CreateEdgeCommand) {
			CreateEdgeCommand createRuleEdgeCommand = (CreateEdgeCommand) currentCmd;
			Node source = createRuleEdgeCommand.getSource();
			Node target = createRuleEdgeCommand.getTarget();
			
			List<EReference> eReferences = 
				EdgeReferences.getSourceToTargetReferences(source, target);
			EReference type = DialogUtil.runEdgeTypeSelectionDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getActivePage()
					.getActivePart().getSite().getShell(), eReferences);
	
			if (type != null) {
				createRuleEdgeCommand.setTypeReference(type);
				super.executeCurrentCommand();
			}
		}
	}
	
	@Override
	protected boolean handleCreateConnection() {
		getCurrentViewer().deselectAll();
		
		return super.handleCreateConnection();
	}
}

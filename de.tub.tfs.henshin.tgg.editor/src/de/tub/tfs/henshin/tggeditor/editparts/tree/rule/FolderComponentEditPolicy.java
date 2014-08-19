package de.tub.tfs.henshin.tggeditor.editparts.tree.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteFoldercommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteGraphInstanceCommand;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteNACCommand;

public class FolderComponentEditPolicy extends ComponentEditPolicy implements
		EditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		if (((IndependentUnit)getHost().getModel()).getName().equals("RuleFolder") || ((IndependentUnit)getHost().getModel()).getName().equals("FTRuleFolder"))
			return null;
		if (((IndependentUnit)getHost().getModel()).getName().equals("BTRuleFolder"))
			return null;
		
		return new DeleteFoldercommand((IndependentUnit) getHost().getModel());
	}

}

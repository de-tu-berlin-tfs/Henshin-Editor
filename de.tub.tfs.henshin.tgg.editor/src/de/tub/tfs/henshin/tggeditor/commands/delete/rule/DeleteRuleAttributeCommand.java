package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteAttributeCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * The class DeleteRuleEdgeCommand deletes an edge in a rule. It deletes the edge in the rhs and 
 * in case of a preserved edge, it also deletes the corresponding edge in the lhs of the rule.
 */
public class DeleteRuleAttributeCommand extends CompoundCommand {

	/**
	 * thr rhs attibute
	 */
	private Attribute rhsAttribute;
	/**
	 * the lhs attribute
	 */
	private Attribute lhsAttribute;
	
	/**the constructor
	 * @param attribute the already created edge
	 */
	public DeleteRuleAttributeCommand(Attribute attribute) {
		

		rhsAttribute = attribute;
		lhsAttribute = RuleUtil.getLHSAttribute(rhsAttribute);
		

		add(new DeleteAttributeCommand(rhsAttribute));
		if (lhsAttribute != null) {
			add(new DeleteAttributeCommand(lhsAttribute));
		}
		
	}


	@Override
	public boolean canExecute() {
		return (rhsAttribute != null && super.canExecute());
	}

	@Override
	public boolean canUndo() {
		return (rhsAttribute != null && super.canUndo());
	}
}

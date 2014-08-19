package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteRuleCommand deletes the rule.
 */
public class DeleteRuleCommand extends CompoundCommand {

	/**
	 * The application condition of the rule
	 */
	private Formula nc;


	/**
	 * the constructor
	 * @param rule the rule to be deleted
	 */
	public DeleteRuleCommand(Rule rule) {
		
		super();
		// remove application condition
		nc = rule.getLhs().getFormula();
		// delete
		if (nc != null)
			add(new DeleteLhsFormulaCommand(nc));
		// lhs does not have references from outside, thus does not need to be deleted
		// the same holds for parameters
		// add(new DeleteGraphCommand(rule.getLhs()));
		add(new DeleteRuleTGGGraphCommand(rule.getRhs()));
		add(new SimpleDeleteEObjectCommand(rule));

	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
//	 */
//	@Override
//	public boolean canExecute() {
//		return (super.canExecute());
//	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
//	 */
//	@Override
//	public boolean canUndo() {
//		return (super.canUndo());		
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
//	 */
//	@Override
//	public void execute() {
//		super.execute();
//	}
//
//
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
//	 */
//	@Override
//	public void redo() {
//		super.redo();		
//	}
//
//	/* (non-Javadoc)
//	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
//	 */
//	@Override
//	public void undo() {
//		super.undo();
//	}
//	
	

}

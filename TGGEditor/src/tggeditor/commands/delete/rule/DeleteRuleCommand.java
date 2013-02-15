package tggeditor.commands.delete.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.GraphLayout;
import tgg.TGG;
import tggeditor.commands.delete.DeleteGraphCommand;
import tggeditor.commands.delete.DeleteTGGGraphCommand;
import tggeditor.commands.delete.DeleteNodeCommand;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteRuleCommand deletes the rule.
 */
public class DeleteRuleCommand extends CompoundCommand {

	/**
	 * The rule to be deleted by the command.
	 */
	private Rule rule;
	
	
	private Formula nc;
//	/**
//	 * Layout element of rule to be deleted: The the divider between source and correspondence component.
//	 */
//	private GraphLayout divSC;
//	/**
//	 * Layout element of rule to be deleted: The the divider between correspondence and target component. 
//	 */
//	private GraphLayout divCT;
//	/**
//	 * The parent of the rule to be deleted.
//	 */
//	private TransformationSystem transformationSystem;
//	/**
//	 * The layout system of the rule to be deleted.
//	 */
//	private TGG tgg;

	/**
	 * the constructor
	 * @param rule the rule to be deleted
	 */
	public DeleteRuleCommand(Rule rule) {
		
		super();
		nc = rule.getLhs().getFormula();
		// delete
		if (nc != null)
			add(new DeleteLhsFormulaCommand(nc));
			
			// TODO: the LHS should probably be deleted !
		// add(new DeleteGraphCommand(rule.getLhs()));
		add(new DeleteRuleTGGGraphCommand(rule.getRhs()));
		add(new SimpleDeleteEObjectCommand(rule));

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (super.canExecute());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return (super.canUndo());		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
		removeAdjacentElements();
	}

	/**
	 * 
	 */
	private void removeAdjacentElements() {
		// TODO: delete parameters	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		super.redo();
		removeAdjacentElements();		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
	}
	
	

}

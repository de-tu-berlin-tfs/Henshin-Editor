package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteNACCommand deletes a NAC.
 * When executed it makes all the needed changes in the tree structure of 
 * the Formula in the lhs of the rule.
 */
public class DeleteLhsFormulaCommand extends CompoundCommand {
	/**
	 * nested condition nc
	 */
	private Formula nc;
	
	/**
	 * Constructor
	 * @param nc
	 */
	public DeleteLhsFormulaCommand(Formula nc) {
		
		// TODO: handle further types of NACs and undo
		this.nc = nc;
		Graph nac = ((NestedCondition) ((Not)nc).getChild() ).getConclusion();
		add(new DeleteNACCommand(nac));
		add(new SimpleDeleteEObjectCommand(nc));
	}

	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (nc != null);
	}

//	/**
//	 * 
//	 */
//	@Override
//	public void execute() {
//		super.execute();
//	}

//	@Override
//	public boolean canUndo() {
//		return super.canUndo();
//	}

//	/**
//	 * @see org.eclipse.gef.commands.Command#redo()
//	 */
//	@Override
//	public void redo() {
//		super.redo();
//	}

//	/**
//	 * @see org.eclipse.gef.commands.Command#undo()
//	 */
//	@Override
//	public void undo() {
//		super.undo();
//	}
	

	
	
}
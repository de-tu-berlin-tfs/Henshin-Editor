package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The class DeleteFTRuleCommand deletes a forward translation rule.
 *
 */
public class DeleteFTRuleCommand extends CompoundCommand {

	private IndependentUnit cont = null;
	private Rule rule;

	/**
	 * Constructor
	 * @param Rule r
	 */
	public DeleteFTRuleCommand(Rule r){
		add(new SimpleDeleteEObjectCommand(getTRule(r)));
		add(new DeleteRuleCommand(r));
		this.rule = r;
	}

	public DeleteFTRuleCommand(Rule r,IndependentUnit container){
		add(new SimpleDeleteEObjectCommand(getTRule(r)));
		add(new DeleteRuleCommand(r));
		this.cont = container;
	}

	/**
	 * Returns the FT rule which is derived from the rule
	 * @param rule
	 * @return TRUle
	 */
	private TRule getTRule(Rule rule){
		
		
		TGG tgg  = NodeUtil.getLayoutSystem(rule);
		List<TRule> tRules = tgg.getTRules();
		for(TRule tr: tRules){
			if(tr.getRule() == rule)
				return tr;
		}
		return null;
	}

	//		/**
	//		 * Calculate if it's executable.
	//		 *  @see org.eclipse.gef.commands.Command#canExecute()
	//		 */		
	//		@Override
	//		public boolean canExecute() {
	//			return tRule != null && tgg != null && super.canExecute();
	//		}
	//
	//		/**
	//		 * Delete a FT rule from the tgg and the corresponding rule from the transformation system.
	//		 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	//		 */
	//		@Override
	//		public void execute() {
	//			tgg.getTRules().remove(tRule);
	//			sys.getRules().remove(rule); // this is needed to notify the tree viewer of the update
	//			super.execute();
	//		}
	//		
	//
	//		@Override
	//		public boolean canUndo() {
	//			return true;
	//		}
	//
	//		/**
	//		 * Undo delete.
	//		 * @see org.eclipse.gef.commands.Command#undo()
	//		 */
	//		@Override
	//		public void undo() {
	//			tgg.getTRules().add(tRule);
	//			sys.getRules().add(rule); // this is needed to notify the tree viewer of the update
	//		}
	//	
	//		/**
	//		 * Redo delete.
	//		 * @see org.eclipse.gef.commands.Command#redo()
	//		 */
	//		@Override
	//		public void redo() {
	//			execute();
	//		}
	//		
	
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		super.execute();
		if (cont != null)
			cont.getSubUnits().remove(rule);
	}
}

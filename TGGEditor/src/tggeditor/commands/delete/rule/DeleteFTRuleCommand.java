package tggeditor.commands.delete.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.CompoundCommand;

import tgg.TGG;
import tgg.TRule;
import tggeditor.util.NodeUtil;

/**
 * The class DeleteFTRuleCommand deletes a forward translation rule.
 *
 */
public class DeleteFTRuleCommand extends CompoundCommand {
		/**
		 * Transformationsystem
		 */
		private TransformationSystem sys;
		/**
		 * Layoutsystem to which the FT rule belongs.
		 */
		private TGG tgg;
		/**
		 * FT rule
		 */
		private TRule tRule;	
		/**
		 * rule from which the FT rule is derived
		 */
		private Rule rule;

		/**
		 * Constructor
		 * @param Rule r
		 */
		public DeleteFTRuleCommand(Rule r){
			this.rule = r;
			this.tRule = getTRule(rule);
			this.sys = this.rule.getTransformationSystem();
			tgg= NodeUtil.getLayoutSystem(rule);
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
		
		/**
		 * Calculate if it's executable.
		 *  @see org.eclipse.gef.commands.Command#canExecute()
		 */		
		@Override
		public boolean canExecute() {
			return this.tRule != null && tgg != null && this.sys != null;
		}

		/**
		 * Delete a FT rule from the tgg and the relative rule from the transformationsystem.
		 * @see org.eclipse.gef.commands.CompoundCommand#execute()
		 */
		@Override
		public void execute() {
			tgg.getTRules().remove(this.tRule);
			this.sys.getRules().remove(this.rule);
		}
		

		@Override
		public boolean canUndo() {
			return true;
		}

		/**
		 * Undo delete.
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			tgg.getTRules().add(this.tRule);
			this.sys.getRules().add(this.rule);
		}
	
		/**
		 * Redo delete.
		 * @see org.eclipse.gef.commands.Command#redo()
		 */
		@Override
		public void redo() {
			this.execute();
		}
		
}

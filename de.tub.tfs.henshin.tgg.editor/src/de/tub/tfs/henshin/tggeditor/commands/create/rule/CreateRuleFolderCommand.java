/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;


/**
 * The class CreateRuleCommand creates a new rule for a transformation system.
 */
public class CreateRuleFolderCommand extends Command {
	
	/** transformation system in which a rule is created */
	private Module module;
	/** rule */
	private IndependentUnit rule;
	/** name of a rule to create */
//	private String name;
	/**
	 * the lhs graph
	 */
	private Graph lhs;
	/**
	 * the rhs graph
	 */
	private TripleGraph rhs;
	private IndependentUnit unit;
	

	public CreateRuleFolderCommand(Module module, String name,IndependentUnit unit) {
		this(module,name);
		this.unit = unit;
	}
		
	/**
	 * the constructor
	 * @param module the transformationsystem
	 * @param name the name for the rule
	 */
	public CreateRuleFolderCommand(Module module, String name) {
		this.module = module;
		rule = HenshinFactory.eINSTANCE.createIndependentUnit();

		rule.setName(name);

		rule.setDescription("ruleFolder.png");
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {		
		return module != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		if (unit != null)
			unit.getSubUnits().add(rule);
		module.getUnits().add(rule);
		
		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		EList<Unit> units = module.getUnits();
		int index = units.indexOf(rule);
		units.remove(index);
		if (unit != null)
			unit.getSubUnits().remove(rule);
		super.undo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return module != null && rule != null;
	}

}

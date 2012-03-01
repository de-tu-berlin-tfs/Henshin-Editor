/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateRuleCommand.
 * 
 * @author Johann, Angeline
 */
public class CreateRuleCommand extends Command {

	/** The transformation system. */
	private TransformationSystem transformationSystem;

	/** The rule to create. */
	private Rule rule;

	/**
	 * Instantiates a new creates the rule command.
	 * 
	 * @param transformationSystem
	 *            The transformation system.
	 * @param name
	 *            The rule name
	 */
	public CreateRuleCommand(TransformationSystem transformationSystem,
			String name) {
		this.transformationSystem = transformationSystem;

		this.rule = HenshinFactory.eINSTANCE.createRule();
		this.rule.setActivated(true);
		rule.setName(name);
	}

	public CreateRuleCommand(final TransformationSystem transformationSystem,
			final String name, final boolean isKernelRule) {
		this(transformationSystem, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		transformationSystem.getRules().add(rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationSystem.getRules().remove(rule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationSystem != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return rule != null && transformationSystem != null;
	}

	/**
	 * @return the rule
	 */
	public synchronized Rule getRule() {
		return rule;
	}

}

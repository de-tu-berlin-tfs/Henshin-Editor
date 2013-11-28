package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

public class CreateAttributeConditionCommand extends Command {
	/** The transformation unit. */
	private final Unit transformationUnit;
	
	/** The parameter. */
	protected AttributeCondition parameter;
	
	
	/**
	 * Instantiates a new creates the parameter command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param name the name
	 */
	public CreateAttributeConditionCommand(Unit transformationUnit,String name) {
		super();
		this.transformationUnit = transformationUnit;
		this.parameter = HenshinFactory.eINSTANCE.createAttributeCondition();
		this.parameter.setName(name);
	}

	

	/**
	 * Instantiates a new creates the parameter command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param parameter the parameter
	 */
	public CreateAttributeConditionCommand(Unit transformationUnit, AttributeCondition parameter) {
		super();
		this.transformationUnit = transformationUnit;
		this.parameter = parameter;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationUnit!=null && parameter!=null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		parameter.setConditionText("");
		((Rule)transformationUnit).getAttributeConditions().add(parameter);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		((Rule)transformationUnit).getAttributeConditions().remove(parameter);
	}


	/**
	 * Gets the trans unit.
	 *
	 * @return the transUnit
	 */
	public synchronized Unit getTransUnit() {
		return transformationUnit;
	}

}

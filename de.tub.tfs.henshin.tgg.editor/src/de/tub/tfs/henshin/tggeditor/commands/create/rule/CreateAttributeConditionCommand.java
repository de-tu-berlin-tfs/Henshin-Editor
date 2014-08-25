/*******************************************************************************
 *******************************************************************************/
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
	
	/** The attribute condition. */
	protected AttributeCondition attCondition;
	
	
	/**
	 * Instantiates a new creates the attribute condition command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param name the name
	 */
	public CreateAttributeConditionCommand(Unit transformationUnit,String name) {
		super();
		this.transformationUnit = transformationUnit;
		this.attCondition = HenshinFactory.eINSTANCE.createAttributeCondition();
		this.attCondition.setName(name);
	}

	

	/**
	 * Instantiates a new creates the attributeCondition command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param attCondition the attribute condition
	 */
	public CreateAttributeConditionCommand(Unit transformationUnit, AttributeCondition attCondition) {
		super();
		this.transformationUnit = transformationUnit;
		this.attCondition = attCondition;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationUnit!=null && attCondition!=null;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		attCondition.setConditionText("");
		((Rule)transformationUnit).getAttributeConditions().add(attCondition);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		((Rule)transformationUnit).getAttributeConditions().remove(attCondition);
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

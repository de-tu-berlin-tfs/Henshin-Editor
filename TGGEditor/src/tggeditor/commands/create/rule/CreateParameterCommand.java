package tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;

public class CreateParameterCommand extends Command {
	/** The transformation unit. */
	private final TransformationUnit transformationUnit;
	
	/** The parameter. */
	protected Parameter parameter;
	
	
	/**
	 * Instantiates a new creates the parameter command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param name the name
	 */
	public CreateParameterCommand(TransformationUnit transformationUnit,String name) {
		super();
		this.transformationUnit = transformationUnit;
		this.parameter = HenshinFactory.eINSTANCE.createParameter();
		this.parameter.setName(name);
	}

	

	/**
	 * Instantiates a new creates the parameter command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param parameter the parameter
	 */
	public CreateParameterCommand(TransformationUnit transformationUnit, Parameter parameter) {
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
		transformationUnit.getParameters().add(parameter);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationUnit.getParameters().remove(parameter);
	}


	/**
	 * Gets the trans unit.
	 *
	 * @return the transUnit
	 */
	public synchronized TransformationUnit getTransUnit() {
		return transformationUnit;
	}


	/**
	 * Gets the parameter.
	 *
	 * @return the port
	 */
	public synchronized Parameter getParameter() {
		return parameter;
	}
}

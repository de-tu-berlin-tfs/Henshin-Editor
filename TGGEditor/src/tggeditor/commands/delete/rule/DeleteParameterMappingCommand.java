package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;

public class DeleteParameterMappingCommand extends Command {
	/** The parameter mapping. */
	private final ParameterMapping parameterMapping;
	
	/** The transformation unit. */
	private final TransformationUnit transformationUnit;
	

	/**
	 * Instantiates a new delete port mapping command.
	 *
	 * @param parameterMapping the parameter mapping
	 */
	public DeleteParameterMappingCommand(ParameterMapping parameterMapping) {
		super();
		this.parameterMapping = parameterMapping;
		this.transformationUnit = (TransformationUnit) parameterMapping.eContainer();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationUnit!=null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		transformationUnit.getParameterMappings().remove(parameterMapping);
		/** TODO Franky*/
		//SendNotify.sendRemovePortMappingNotify(parameterMapping, transformationUnit);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationUnit.getParameterMappings().add(parameterMapping);
		/** TODO Franky*/
		//SendNotify.sendAddPortMappingNotify(parameterMapping, transformationUnit);
	}
}

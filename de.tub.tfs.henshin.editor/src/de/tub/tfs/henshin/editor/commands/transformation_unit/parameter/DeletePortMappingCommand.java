/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit.parameter;

import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.SendNotify;

/**
 * The Class DeletePortMappingCommand.
 * 
 * @author Johann
 */
public class DeletePortMappingCommand extends Command {

	/** The parameter mapping. */
	private final ParameterMapping parameterMapping;

	/** The transformation unit. */
	private final TransformationUnit transformationUnit;

	/**
	 * Instantiates a new delete port mapping command.
	 * 
	 * @param parameterMapping
	 *            the parameter mapping
	 */
	public DeletePortMappingCommand(ParameterMapping parameterMapping) {
		super();
		this.parameterMapping = parameterMapping;
		this.transformationUnit = (TransformationUnit) parameterMapping
				.eContainer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return transformationUnit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		transformationUnit.getParameterMappings().remove(parameterMapping);
		SendNotify.sendRemovePortMappingNotify(parameterMapping,
				transformationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationUnit.getParameterMappings().add(parameterMapping);
		SendNotify.sendAddPortMappingNotify(parameterMapping,
				transformationUnit);
	}

}

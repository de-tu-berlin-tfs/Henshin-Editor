package tggeditor.commands.delete.rule;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteParameterCommand extends CompoundCommand {
	/**
	 * Instantiates a new delete parameter command.
	 *
	 * @param parameter the parameter
	 */
	public DeleteParameterCommand(Parameter parameter) {
		super();
		/** TODO Franky*/
		/*for (Unit tUnit:ModelUtil.getTransSystem(parameter).getTransformationUnits()){
			for (ParameterMapping parameterMapping:tUnit.getParameterMappings()){
				if (parameterMapping.getSource()==parameter || parameterMapping.getTarget()==parameter){
					add(new DeleteParameterMappingCommand(parameterMapping));
				}
			}
		}*/
		add(new SimpleDeleteEObjectCommand(parameter));
	}
}

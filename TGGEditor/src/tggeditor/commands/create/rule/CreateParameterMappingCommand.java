package tggeditor.commands.create.rule;

import java.util.Iterator;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import tggeditor.util.SendNotify;

public class CreateParameterMappingCommand extends Command {
	/** The source. */
	private final Parameter source;
	
	/** The parameter mapping. */
	private final ParameterMapping parameterMapping;
	
	/** The target. */
	private Parameter target;
	
	/** The transformation unit. */
	private Unit transformationUnit;
	
	/** The old mapping. */
	private ParameterMapping oldMapping;

	/**
	 * Instantiates a new creates the parameter mapping command.
	 *
	 * @param source the source
	 */
	public CreateParameterMappingCommand(Parameter source) {
		super();
		this.source = source;
		this.parameterMapping = HenshinFactory.eINSTANCE.createParameterMapping();
		this.parameterMapping.setSource(source);
	}

	
	/**
	 * Instantiates a new creates the parameter mapping command.
	 *
	 * @param transformationUnit the transformation unit
	 * @param source the source
	 * @param target the target
	 */
	public CreateParameterMappingCommand(Unit transformationUnit, Parameter source, Parameter target
			) {
		super();
		this.source = source;
		this.parameterMapping = HenshinFactory.eINSTANCE.createParameterMapping();
		this.parameterMapping.setSource(source);
		this.target = target;
		this.transformationUnit = transformationUnit;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return source != null && target != null && transformationUnit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		oldMapping = null;
		Iterator<ParameterMapping> iter = transformationUnit.getParameterMappings().iterator();
		while (iter.hasNext()) {
			ParameterMapping temp = iter.next();
			if (temp.getTarget() == target) {
				oldMapping = temp;
				iter.remove();
				SendNotify.sendRemovePortMappingNotify(temp, transformationUnit);
				break;
			}
		}

		parameterMapping.setTarget(target);
		transformationUnit.getParameterMappings().add(parameterMapping);
		SendNotify.sendAddPortMappingNotify(parameterMapping,transformationUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationUnit.getParameterMappings().remove(parameterMapping);
		SendNotify.sendRemovePortMappingNotify(parameterMapping, transformationUnit);
		if (oldMapping != null) {
			transformationUnit.getParameterMappings().add(oldMapping);
			SendNotify.sendAddPortMappingNotify(oldMapping,transformationUnit);

		}
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public synchronized Parameter getSource() {
		return source;
	}

	/**
	 * Sets the target.
	 *
	 * @param target the target to set
	 */
	public synchronized void setTarget(Parameter target) {
		this.target = target;
	}

	/**
	 * Sets the transformation unit.
	 *
	 * @param transformationUnit the new transformation unit
	 */
	public synchronized void setTransformationUnit(Unit transformationUnit) {
		this.transformationUnit = transformationUnit;
	}
}

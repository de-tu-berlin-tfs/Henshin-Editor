/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.List;

import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.model.ModelCreationFactory;

/**
 * The Class CreateTransformationUnitWithContentCommand.
 * 
 * @param <T>
 *            the generic type
 * @author Johann
 */
public class CreateTransformationUnitWithContentCommand<T extends TransformationUnit>
		extends CompoundCommand {

	/**
	 * Instantiates a new creates the transformation unit with content command.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 * @param parentObject
	 *            the parent object
	 * @param clazz
	 *            the clazz
	 * @param contents
	 *            the contents
	 * @param name
	 *            the name
	 */
	@SuppressWarnings({ "unchecked" })
	public CreateTransformationUnitWithContentCommand(
			TransformationSystem transformationSystem, Object parentObject,
			Class<?> clazz, List<TransformationUnit> contents, String name) {
		super();
		ModelCreationFactory m = new ModelCreationFactory(clazz);
		T unit = (T) m.getNewObject();
		add(new CreateTransformationUnitCommand<T>(transformationSystem, unit,
				name));
		add(new AddContentInNewUnitAndReplaceCommand(parentObject, unit,
				contents));
	}

}

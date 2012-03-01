/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;

/**
 * A {@link Command} to create {@link TransformationUnit}s.
 * 
 * @param <T>
 *            the {@link TransformationUnit} type of the new object.
 * 
 * @author Johann
 */
public class CreateTransformationUnitCommand<T extends TransformationUnit>
		extends CompoundCommand {

	/** The transformation system. */
	protected TransformationSystem transformationSystem;

	/** The name. */
	protected String name;

	/** The transformation unit. */
	protected T transformationUnit;

	/**
	 * @param transformationSystem
	 *            the transformation system
	 * @param unit
	 *            the unit
	 * @param name
	 *            the name
	 */
	public CreateTransformationUnitCommand(
			TransformationSystem transformationSystem, T unit, String name) {
		this(
				transformationSystem,
				unit,
				name,
				HenshinPackage.Literals.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS);
	}

	public CreateTransformationUnitCommand(
			TransformationSystem transformationSystem, T unit, String name,
			EStructuralFeature containerFeature) {
		super("Create '" + name + "'");

		if (transformationSystem != null && unit != null && name != null) {

			unit.setName(name);
			unit.setDescription("");
			unit.setActivated(true);

			add(new SimpleAddEObjectCommand<TransformationSystem, TransformationUnit>(
					unit, containerFeature, transformationSystem));
		}
	}

	/**
	 * Instantiates a new creates the transformation unit command.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 * @param clazz
	 *            the clazz
	 * @param name
	 *            the name
	 */
	@SuppressWarnings("unchecked")
	public CreateTransformationUnitCommand(
			TransformationSystem transformationSystem, Class<?> clazz,
			String name) {
		this(transformationSystem, (T) new ModelCreationFactory(clazz)
				.getNewObject(), name);
	}
}
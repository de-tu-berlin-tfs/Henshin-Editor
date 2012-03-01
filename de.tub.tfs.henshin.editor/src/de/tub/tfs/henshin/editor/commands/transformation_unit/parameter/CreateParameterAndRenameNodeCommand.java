/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.transformation_unit.parameter;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

/**
 * @author Johann
 * 
 */
public class CreateParameterAndRenameNodeCommand extends CompoundCommand {

	private final TransformationUnit transformationUnit;

	private final Node node;

	private final String name;

	/**
	 * @param node
	 * @param name
	 */
	public CreateParameterAndRenameNodeCommand(TransformationUnit transUnit,
			Node node, String name) {
		super();
		this.transformationUnit = transUnit;
		this.node = node;
		this.name = name;

		add(new CreateParameterCommand(transUnit, name));

		add(new SetEObjectFeatureValueCommand(node, name,
				HenshinPackage.NODE__NAME));
	}

	/**
	 * @return the node
	 */
	public synchronized Node getNode() {
		return node;
	}

	/**
	 * @return the transformationUnit
	 */
	public synchronized TransformationUnit getTransformationUnit() {
		return transformationUnit;
	}

	/**
	 * @return the name
	 */
	public synchronized String getName() {
		return name;
	}

}

package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateParameterCommand;
import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

public class CreateParameterAndRenameNodeCommand extends CompoundCommand {
private final Unit transformationUnit;
	
	private final Node node;
	
	private final String name;
	/**
	 * @param node
	 * @param name
	 */
	public CreateParameterAndRenameNodeCommand(Unit transUnit, Node node, String name) {
		super();
		this.transformationUnit = transUnit;
		this.node = node;
		this.name = name;
		
		add(new SetEObjectFeatureValueCommand(node, name, HenshinPackage.NODE__NAME));
		add(new CreateParameterCommand(transUnit, name));
		
		createParameterAndRenameNodesInMultiRule(node, name);
	}
	
	private void createParameterAndRenameNodesInMultiRule(Node node, String name) {
		/*final List<Node> imageNodes = ModelUtil.getImageNodesInMulti(node);
		for (Node imageNode : imageNodes) {
			add(new SetEObjectFeatureValueCommand(
					imageNode, name, HenshinPackage.NODE__NAME));
			final Unit rule = (Unit) imageNode.eContainer().eContainer();
			add(new CreateParameterCommand(rule, name));
		}*/
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
	public synchronized Unit getTransformationUnit() {
		return transformationUnit;
	}
	/**
	 * @return the name
	 */
	public synchronized String getName() {
		return name;
	}
}

package de.tub.tfs.henshin.tggeditor.commands.create.constraint;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;

public class CreateAttributeCommand extends Command {

	protected Node node;
	protected Attribute attribute;
	protected String value;
	protected EAttribute type;
	protected TGG layout;
	
	public CreateAttributeCommand (Node node, String value) {
		this.node = node;
		this.value = value;
		this.layout = GraphicalNodeUtil.getLayoutSystem(node);
	}

	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public boolean canExecute() {
		return node != null;
	}

	@Override
	public void execute() {
		// add attribute to node
		this.attribute = TggFactory.eINSTANCE.createTAttribute();
		attribute.setValue(value);
		attribute.setType(type);
		attribute.setNode(node);
		node.getAttributes().add(attribute);
		
		setParameter(node, value);
		
		// if node is node of premise, then also add attribute to conclusion if not already set
		if (node.getGraph().eContainer() instanceof NestedConstraint) {
			for (Mapping m : ((NestedCondition)node.getGraph().getFormula()).getMappings()) {
				if (m.getOrigin() == node) {
					Node cNode = m.getImage();
					if (cNode.getAttribute(attribute.getType()) == null) {
						this.attribute = TggFactory.eINSTANCE.createTAttribute();
						attribute.setValue(value);
						attribute.setType(type);
						attribute.setNode(node);
						cNode.getAttributes().add(attribute);
					}
				}
			}
		}
	}

	public static void setParameter(Node node, String value) {
		// if attribute value is parameter and parameter is not already set, set it
		if (value.matches("[a-zA-Z].*")) {
			EObject constraint = node.eContainer();
			while (!(constraint instanceof Constraint)) {
				constraint = constraint.eContainer();
			}
			boolean paramFound = false;
			for (Parameter param : ((Constraint)constraint).getParameters()) {
				if (param.getName().equals(value)) {
					paramFound = true;
					break;
				}
			}
			if (!paramFound) {
				Parameter param = HenshinFactory.eINSTANCE.createParameter(value);
				((Constraint)constraint).getParameters().add(param);
			}
		}
	}
	
	public Node getNode() {
		return node;
	}
	
	public void setAttributeType(EAttribute attributeType) {
		this.type = attributeType;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public Attribute getAttribute(){
		return attribute;
	}
	
}

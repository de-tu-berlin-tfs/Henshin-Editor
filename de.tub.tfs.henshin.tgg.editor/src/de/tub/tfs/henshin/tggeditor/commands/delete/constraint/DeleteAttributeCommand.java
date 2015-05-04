package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteAttributeCommand extends CompoundCommand {

	Attribute attribute;
	
	public DeleteAttributeCommand(Attribute attribute) {
		this.attribute = attribute;
	}
	
	@Override
	public boolean canExecute() {
		return attribute != null;
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void execute() {
		add(new SimpleDeleteEObjectCommand (attribute));
		
		EList<Parameter> params = null;
		
		// delete mapped attribute in conclusion
		Node node = attribute.getNode();
		EObject constraint = node.getGraph();
		while (!(constraint instanceof Constraint)) {
			constraint = constraint.eContainer();
		}
		if (node.getGraph().eContainer() instanceof NestedConstraint) {
			NestedCondition condition = (NestedCondition)node.getGraph().getFormula();
			Node cNode = condition.getMappings().getImage(node, condition.getConclusion());
			if (cNode != null) {
				Attribute cAttribute = cNode.getAttribute(attribute.getType());
				if (cAttribute != null) {
					add(new SimpleDeleteEObjectCommand (cAttribute));
				}
			}
			params = ((Constraint)constraint).getParameters();
		} else {
			params = ((Constraint)constraint).getParameters();
		}
		super.execute();
		removeParameters(params);
	}
	
	private static boolean findParameter(Formula formula, String value) {
		if (formula instanceof UnaryFormula) {
			return findParameter(((UnaryFormula) formula).getChild(), value);
		}
		if (formula instanceof BinaryFormula) {
			boolean left = findParameter(((BinaryFormula) formula).getLeft(), value);
			boolean right = findParameter(((BinaryFormula) formula).getRight(), value);
			return left || right;
		}
		if (formula instanceof NestedConstraint) {
			Graph premise = ((NestedConstraint)formula).getPremise();
			for (Node n : premise.getNodes()) {
				for (Attribute a : n.getAttributes()) {
					if (a.getValue().equals(value)) {
						return true;
					}
				}
			}
			Graph conclusion = ((NestedCondition)premise.getFormula()).getConclusion();
			for (Node n : conclusion.getNodes()) {
				for (Attribute a : n.getAttributes()) {
					if (a.getValue().equals(value)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void removeParameters(EList<Parameter> params) {
		List<Parameter> ps = new ArrayList<Parameter>();
		for (Parameter p : params) {
			if (!findParameter(((Constraint)p.eContainer()).getRoot(), p.getName())) {
				ps.add(p);
			}
		}
		for (Parameter p : ps) {
			((Constraint)p.eContainer()).getParameters().remove(p);
		}
	}

}

package de.tub.tfs.henshin.tggeditor.commands.delete.constraint;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tggeditor.util.SendNotify;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteNodeCommand extends CompoundCommand {

	private Node pNode;
	private Node cNode;
	
	public DeleteNodeCommand(Node node) {
		// check if node is node of premise OR conclusion
		if (node.getGraph().eContainer() instanceof NestedConstraint) {
			this.pNode = node;
		} else {
			this.cNode = node;
		}
	}
	
	@Override
	public boolean canExecute() {
		return pNode != null || cNode != null;
	}
	
	@Override
	public void execute() {
		EList<Parameter> params = null;
		// delete node and mapping between premise and conclusion
		if (pNode != null) { // node is node of premise
			MappingList mappings = ((NestedCondition)pNode.getGraph().getFormula()).getMappings();
			for (Mapping m : mappings) {
				if (m.getOrigin() == pNode) {
					// delete existing mapping
					add(new SimpleDeleteEObjectCommand(m));
					SendNotify.sendRemoveMappingNotify(m);
					// delete image node of mapping
					add(new de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand(m.getImage()));
					// delete mappings of image node
					MappingList cMappings = ((NestedCondition)m.getImage().getGraph().eContainer()).getMappings();
					for (Mapping cM : cMappings) {
						if (cM.getImage() == m.getImage()) {
							add(new SimpleDeleteEObjectCommand(cM));
							SendNotify.sendRemoveMappingNotify(cM);
						}
					}
					break;
				}
			}
			// delete premise node
			add(new de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand(pNode));
			
			EObject constraint = pNode.getGraph();
			while (!(constraint instanceof Constraint)) {
				constraint = constraint.eContainer();
			}
			params = ((Constraint)constraint).getParameters();
		} else { // node is node of conclusion
			// delete mappings of node
			MappingList mappings = ((NestedCondition)cNode.getGraph().eContainer()).getMappings();
			for (Mapping m : mappings) {
				if (m.getImage() == cNode) {
					add(new SimpleDeleteEObjectCommand(m));
					SendNotify.sendRemoveMappingNotify(m);
				}
			}
			// delete conclusion node
			add(new de.tub.tfs.henshin.tggeditor.commands.delete.DeleteNodeCommand(cNode));
			
			EObject constraint = cNode.getGraph();
			while (!(constraint instanceof Constraint)) {
				constraint = constraint.eContainer();
			}
			params = ((Constraint)constraint).getParameters();
		}
		super.execute();
		// remove unused parameters
		DeleteAttributeCommand.removeParameters(params);
	}
	
}

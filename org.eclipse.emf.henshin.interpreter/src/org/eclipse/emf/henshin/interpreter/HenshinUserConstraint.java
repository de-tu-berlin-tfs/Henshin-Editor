package org.eclipse.emf.henshin.interpreter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.matching.constraints.UserConstraint;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

public abstract class HenshinUserConstraint extends UserConstraint {
	
	
	/**
	 * 
	 * @param node the node in the LHS of the rule which this constraint belongs to
	 */
	public HenshinUserConstraint(Node node) {
		super(node);
	}

	/**
	 * 
	 * @param graphNode the mapped node in the hostgraph
	 * @return Returns whether this constraint is satisfied or not i.e. there exists a morphism between the graph containing this constraints node and the host graph. 
	 */
	public abstract boolean check(Node graphNode);
	
	@Override
	public boolean check(DomainSlot slot,EmfGraph graph){
		return check(getGraphNode(slot,graph));
	}

	protected Node getGraphNode(DomainSlot slot,EmfGraph graph){
		return getGraphNode(getValue(slot), graph);
	}
	
	protected Node getGraphNode(EObject eObject,EmfGraph graph){
		return ((HenshinGraph)graph).geteObject2nodeMap().get(eObject);
	}
	
}

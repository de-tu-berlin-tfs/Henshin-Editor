package org.eclipse.emf.henshin.matching.constraints;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Node;

public abstract class UserConstraint implements Constraint {
	
	
	public UserConstraint(Node node) {
	}


	public abstract boolean check(DomainSlot slot,EmfGraph graph);
	
	public boolean unlock(Variable sender,DomainSlot slot){
		return true;
	}
	
	protected EObject getValue(DomainSlot slot){
		return slot.value;
	}
	
}

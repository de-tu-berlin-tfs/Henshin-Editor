package org.eclipse.emf.henshin.interpreter.matching.constraints;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.model.Node;

public abstract class UserConstraint implements Constraint {
	
	
	public UserConstraint(Node node) {
	}


	public abstract boolean check(DomainSlot slot,EGraph graph);
	
	public boolean unlock(Variable sender,DomainSlot slot){
		return true;
	}
	
	protected EObject getValue(DomainSlot slot){
		return slot.value;
	}
	
}

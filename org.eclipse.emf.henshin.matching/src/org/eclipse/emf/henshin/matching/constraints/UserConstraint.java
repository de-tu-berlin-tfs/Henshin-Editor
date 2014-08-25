package org.eclipse.emf.henshin.matching.constraints;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.matching.EmfGraph;
import org.eclipse.emf.henshin.model.Node;

public abstract class UserConstraint implements Constraint {
	
	
	public UserConstraint(Node node) {
	}

	// FIXME: changed by FH
	public abstract boolean check(DomainSlot slot,Variable variable, Map<Variable, DomainSlot> domainMap, EmfGraph graph);
	
	public boolean unlock(Variable sender,DomainSlot slot){
		return true;
	}
	
	protected EObject getValue(DomainSlot slot){
		return slot.value;
	}
	
}

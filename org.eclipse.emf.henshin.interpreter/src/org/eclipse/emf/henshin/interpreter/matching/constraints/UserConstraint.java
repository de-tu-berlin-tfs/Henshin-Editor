package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.Map;

import org.eclipse.emf.henshin.interpreter.EGraph;

public interface UserConstraint extends Constraint {
	
	/**
	 * 
	 * @param domainMap 
	 * @param variable 
	 * @param graphNode the mapped node in the hostgraph
	 * @return Returns whether this constraint is satisfied or not i.e. there exists a morphism between the graph containing this constraints node and the host graph. 
	 */
	public boolean check(DomainSlot slot,Variable variable, Map<Variable, DomainSlot> domainMap, EGraph graph);
	
	public boolean unlock(Variable sender,DomainSlot slot);
	
}

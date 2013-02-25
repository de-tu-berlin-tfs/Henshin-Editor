/**
 * <copyright>
 * Copyright (c) 2010-2012 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */
package org.eclipse.emf.henshin.interpreter.matching.constraints;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;

/**
 * This constraint checks whether the value of an EReference contains objects
 * from the target domain.
 * 
 * @author Enrico Biermann, Christian Krause
 */
public class ReferenceConstraint implements BinaryConstraint {
	
	// Target variable:
	final Variable targetVariable;
	
	/**
	 * @return the targetVariable
	 */
	public Variable getTargetVariable() {
		return targetVariable;
	}

	// Edge:
	final Edge edge;

	/**
	 * @return the edge
	 */
	public Edge getEdge() {
		return edge;
	}

	/**
	 * @return the reference
	 */
	public EReference getReference() {
		return reference;
	}

	// Reference:
	final EReference reference;
	
	/**
	 * Default constructor.
	 * @param target Target variable.
	 * @param edge Reference.
	 */
	public ReferenceConstraint(Variable target, Edge edge) {
		this.targetVariable = target;
		this.edge = edge;
		this.reference = edge.getType();
	}

	/**
	 * Default constructor.
	 * @param target Target variable.
	 * @param referenceType Reference.
	 */
	public ReferenceConstraint(Variable target, EReference referenceType) {
		this.targetVariable = target;
		this.edge=null;
		this.reference = referenceType;
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint#check(org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot, org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean check(DomainSlot source, DomainSlot target) {
		
		// Source must be locked:
		if (!source.locked) {
			return false;
		}
		
		// Get the target objects:
		Collection<EObject> targetObjects;
		if (reference.isMany()) {
			targetObjects = (List<EObject>) source.value.eGet(reference);
			if (targetObjects.isEmpty()) {
				return false;
			}
		} else {
			EObject obj = (EObject) source.value.eGet(reference);
			if (obj==null) {
				return false;
			}
			targetObjects = Collections.singleton(obj);
		}
		
		// Target domain slot locked?
		if (target.locked) {
			return targetObjects.contains(target.value);
		} else {
			// Create a domain change to restrict the target domain:
			DomainChange change = new DomainChange(target, target.temporaryDomain);
			source.remoteChangeMap.put(this, change);
			target.temporaryDomain = new ArrayList<EObject>(targetObjects);
			if (change.originalValues!=null) {
				target.temporaryDomain.retainAll(change.originalValues);
			}
			return !target.temporaryDomain.isEmpty();
		}
		
	}
	
}
/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.tgg.interpreter.impl;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.BinaryConstraint;
import org.eclipse.emf.henshin.interpreter.matching.constraints.UnaryConstraint;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;

public class TggEngineOperational extends TggEngineImpl {
	/**
	 * 
	 */

	
	/**
	 */
	public TggEngineOperational(EGraph graph,TggTransformation trafo) {
		super(graph,trafo); 
		assert(this.trafo!=null):"Transformation of TggEngineOperational is not set.";
	}
	
	
	@Override
	public UnaryConstraint createUserConstraints(Attribute attribute) {
		return new OpRuleAttributeConstraintEMF(attribute,
				trafo.getTranslationMaps().isTranslatedNodeMap,
				trafo.getTranslationMaps().isTranslatedAttributeMap,
				trafo.getNullValueMatching());
	}

	@Override
	public BinaryConstraint createUserConstraints(Edge edge) {
		return new OpRuleEdgeConstraintEMF(edge,
				trafo.getTranslationMaps().isTranslatedEdgeMap);
	}

	@Override
	public UnaryConstraint createUserConstraints(Node node) {
		return new OpRuleNodeConstraintEMF(node,
				trafo.getTranslationMaps().isTranslatedNodeMap);
	}
}
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
package de.tub.tfs.henshin.tggeditor;


import java.util.Iterator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.xt_basis.Arc;
import agg.xt_basis.Type;
import de.tub.tfs.henshin.analysis.AggInfo;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;

public class TggAggInfo extends AggInfo {
	
	public TggAggInfo(Module ts) {
		super(ts);
	}

	public void extendDueToTGG(TGG layoutSystem) {
		extendSrsNodeType(layoutSystem);
		extendEdgeTypeMultiplicity(layoutSystem); //olga
		extendFTRules(layoutSystem);
	}
	
	private void extendSrsNodeType(TGG layoutSystem) {
		AttrHandler handler = new JexHandler();
		
		for (EClass emfType : nodeTypeMap.keySet()) {
			if (NodeUtil.isSourceClass(layoutSystem, emfType)) {
				Type aggNodeType = nodeTypeMap.get(emfType);
				AttrType attr = aggNodeType.getAttrType();				
				AttrTypeMember member = attr.addMember(handler,"boolean" , "translated");
				if (member.getType() == null)
					System.out.println("DEBUG: " + member.getType());
				aggNodeType.getTypeGraphNodeObject().getAttribute().setExprValueAt("false", "translated");
				((agg.attribute.impl.ValueTuple)aggNodeType.getTypeGraphNodeObject().getAttribute()).showValue();
			}
		}
	}
	
	//olga
		private EClass getEClassForType(Type t) {
			Iterator<EClass> iter = this.nodeTypeMap.keySet().iterator();
			while (iter.hasNext()) {
				EClass c = iter.next();
				if (this.nodeTypeMap.get(c) == t)
					return c;
			}
			return null;
		}
		
		//olga - set 1 at source max for multiplicity of edges from Corr to Source 
		private void extendEdgeTypeMultiplicity(TGG layoutSystem) {
			Iterator<Arc> arcs = this.aggTypeGraph.getArcsCollection().iterator();
			while (arcs.hasNext()) {
				agg.xt_basis.Arc aggEdge = arcs.next(); 
				EClass srcC = getEClassForType(aggEdge.getSourceType());
				EClass tarC = getEClassForType(aggEdge.getTargetType());
				// TODO: determine triple component from tgg rule, because a type may occur in several components
				if (NodeUtil.isCorrespondenceClass(layoutSystem, srcC)
						&& NodeUtil.isSourceClass(layoutSystem, tarC)) {
					aggEdge.getType().setSourceMax(aggEdge.getSourceType(), aggEdge.getTargetType(), 1);
				}			
			}
		}

	private void extendFTRules(TGG layoutSystem) {
		EList<Rule> rules = ModelUtil.getRules( this.emfGrammar);
		for (Rule r : rules) {
			if (r.getName().startsWith("FT") || r.getName().startsWith("CR")) {
//				System.out.println("DEBUG: " + r.getName());
				EList<TNode> rhsNodes = (EList)r.getRhs().getNodes();
				for (TNode n : rhsNodes) {
					if (NodeUtil.isSourceNode(n)) {
						agg.xt_basis.Node aggNode = (agg.xt_basis.Node) this.henshinToAggConversionMap.get(n);
						if (RuleUtil.Translated.equals(n.getMarkerType()) 
								&& aggNode.getAttribute() != null && aggNode.getAttribute().getMemberAt("translated") != null) {
							aggNode.getAttribute().setExprValueAt(String.valueOf(Boolean.TRUE), "translated");
							((agg.attribute.impl.ValueTuple)aggNode.getAttribute()).showValue();
							for (Mapping m : r.getMappings()) {
								if (m.getImage() == n) {
									Node nLhs = m.getOrigin();
									agg.xt_basis.Node aggNodeLhs = (agg.xt_basis.Node) this.henshinToAggConversionMap.get(nLhs);
									if (aggNodeLhs.getAttribute().getMemberAt("translated") != null) {
										//TODO check if this is correct
										aggNodeLhs.getAttribute().setExprValueAt(String.valueOf(!RuleUtil.Translated.equals(n.getMarkerType()) ), "translated");
										((agg.attribute.impl.ValueTuple)aggNodeLhs.getAttribute()).showValue();
									}
								}
							}
						}
					}
				}
			}
		}
	}

}

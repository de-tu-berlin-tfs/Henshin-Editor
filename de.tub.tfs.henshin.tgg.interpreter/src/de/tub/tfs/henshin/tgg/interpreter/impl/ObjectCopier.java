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


import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.script.ScriptContext;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.Engine;
import org.eclipse.emf.henshin.interpreter.Match;
import org.eclipse.emf.henshin.interpreter.impl.EngineImpl;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.interpreter.TggTransformation;

public class ObjectCopier {

	private EGraph graph;
	private Engine engine;
	private TggTransformation trafo=null;
	private boolean handleMarkers=false;

	public ObjectCopier(EGraph graph,TggTransformation trafo, Engine e) //Engine e,HashMap<Node, Boolean> isTranslatedMap,		HashMap<Attribute, Boolean> isTranslatedAttributeMap,			HashMap<Edge, Boolean> isTranslatedEdgeMap)
	{
		assert(trafo != null):"Object copier is called with transformation, but it is not set.";
		this.graph = graph;
		this.trafo = trafo;
		this.engine = e;
		handleMarkers=true;
	}

	public ObjectCopier(EGraph graph, Engine e) 
	{
		this.graph = graph;
		this.engine = (EngineImpl) e;
	}

	


	public void postProcess(Match m){
		int i = 0;
		EObject source = null;
		EObject targetEObject = null;

		Set<Entry<String, Object>> entrySet = engine.getScriptEngine().getContext().getBindings(ScriptContext.ENGINE_SCOPE).entrySet();

		for (Entry<String, Object> entry : entrySet) {

			if (!(entry.getValue() instanceof EObject))
				continue;

			source = (EObject) entry.getValue();
			if (m != null && source != null){
				for (Node rhsNode : m.getRule().getRhs().getNodes()) {
					if (rhsNode.getName() != null && rhsNode.getName().equals("copy(" + entry.getKey() + ")")){
						targetEObject = m.getNodeTarget(rhsNode);
						if (targetEObject != null){
							//assert (graph instanceof TggHenshinEGraph): "Graph should be a triple graph";
							//TggHenshinEGraph tggGraph = (TggHenshinEGraph) graph;
							//Map<EObject,Node> objToNodeMap = null;
//							if (graph instanceof TggHenshinEGraph){
							//	objToNodeMap = tggGraph.getObject2NodeMap();
//							}
//							if (graph instanceof HenshinEGraph){
//								objToNodeMap = ((HenshinEGraph) graph).getObject2NodeMap();
//							}

							//TNode targetNode = (TNode) objToNodeMap.get(targetEObject);
							TripleComponent targetComponent = TripleComponent.TARGET;
							//assert (tggGraph.getHenshinGraph() instanceof TripleGraph):"Graph should be a triple graph";
							//int targetXCoordinate = ((TripleGraph)tggGraph.getHenshinGraph()).getDividerCT_X();
							
							Copier copier = new Copier();
							EObject newTarget = copier.copy(source);
							

							copier.copyReferences();

							graph.addTree(newTarget);
						
							// set component and X-value of new target node
							//TNode newTargetNode = (TNode) objToNodeMap.get(newTarget);
							//assert (newTargetNode!=null) : "Object copier tries to access the created node, but is is not contained in the graph";
							//targetNode.setX(targetXCoordinate);
							
							// set component of new node
							TripleComponent componentOfNewNodes = trafo.getTripleComponentNodeMap().get(targetEObject);
							trafo.getTripleComponentNodeMap().put(newTarget,componentOfNewNodes);
							
							
							if(handleMarkers)
								setMarkers(source);


							for (EStructuralFeature feat : newTarget.eClass().getEAllStructuralFeatures()) {
								newTarget.eSet(feat, newTarget.eGet(feat)); //?? FIXME: this seems to have no effect
							}

							TreeIterator<EObject> iterator = source.eAllContents();
							while (iterator.hasNext()){
								EObject srcNext = iterator.next();
								EObject tgtNext = copier.get(srcNext);
								if (tgtNext == null)
									continue;
								//assert (objToNodeMap != null) : "Object to node map of Henshin graph is missing.";

								//TNode targetNextNode = (TNode) objToNodeMap.get(tgtNext);
								//targetNextNode.setX(targetXCoordinate);

								// set component of new node
								trafo.getTripleComponentNodeMap().put(tgtNext,componentOfNewNodes);

								if(handleMarkers)
									setMarkers(srcNext);

								for (EStructuralFeature feat : tgtNext.eClass().getEAllStructuralFeatures()) {
									tgtNext.eSet(feat, tgtNext.eGet(feat)); //?? FIXME: this seems to have no effect

								}
							}

							EObject container = targetEObject.eContainer();
							if (container != null){
								if (targetEObject.eContainingFeature().isMany()) {
									List<EObject> list = (List<EObject>) container
											.eGet(targetEObject
													.eContainingFeature());
									int indexOf = list.indexOf(targetEObject);
									list.add(indexOf, newTarget);
									list.remove(targetEObject);
									// container.eSet(target.eContainingFeature(),
									// newTarget);

								} else {
									container.eSet(
											targetEObject.eContainingFeature(),
											newTarget);

								}
								
							}

							graph.remove(targetEObject);
							trafo.getTripleComponentNodeMap().remove(targetEObject);

							//engine.getScriptEngine().put("source"+i, null);
							i++;
						}
					}
				}
			}
		}
	}

	private void setMarkers(EObject o) {
		assert (trafo!=null):"TggTransformation is missing.";
		trafo.fillTranslatedMaps(o,true);
	}




}

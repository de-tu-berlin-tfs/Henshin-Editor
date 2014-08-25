/*******************************************************************************
 *******************************************************************************/

package de.tub.tfs.henshin.tgg.interpreter.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TNode;

public class ObjectCopier {

	private EGraph graph;
	private EngineImpl engine;
	private HashMap<Node, Boolean> isTranslatedMap;
	private HashMap<Attribute, Boolean> isTranslatedAttributeMap;
	private HashMap<Edge, Boolean> isTranslatedEdgeMap;

	public ObjectCopier(EGraph graph,Engine e,HashMap<Node, Boolean> isTranslatedMap, 
			HashMap<Attribute, Boolean> isTranslatedAttributeMap, 
			HashMap<Edge, Boolean> isTranslatedEdgeMap){
		this.graph = graph;
		this.engine = (EngineImpl) e;
		this.isTranslatedMap = isTranslatedMap;
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;

	}

	public void copy(EObject container,String featName,EObject oldObj) {
		/*EObject obj = EcoreUtil.copy(oldObj);

		graph.addTree(obj);

		if (graph instanceof TggHenshinEGraph){
			TNode node = (TNode) ((TggHenshinEGraph) graph).getObject2NodeMap().get(obj);
			TNode oldNode = (TNode) ((TggHenshinEGraph) graph).getObject2NodeMap().get(container);
			node.setGuessedSide(oldNode.getGuessedSide());	

		}

		for (EStructuralFeature feat : obj.eClass().getEAllStructuralFeatures()) {
			obj.eSet(feat, obj.eGet(feat));
		}

		TreeIterator<EObject> iterator = obj.eAllContents();
		while (iterator.hasNext()){
			EObject next = iterator.next();
			if (graph instanceof TggHenshinEGraph){
				TNode node = (TNode) ((TggHenshinEGraph) graph).getObject2NodeMap().get(next);
				TNode oldNode = (TNode) ((TggHenshinEGraph) graph).getObject2NodeMap().get(container);
				node.setGuessedSide(oldNode.getGuessedSide());	

			}

			for (EStructuralFeature feat : next.eClass().getEAllStructuralFeatures()) {
				next.eSet(feat, next.eGet(feat));
			}

		}

		addedObjects.add(obj);
		addedObjectContainer.add(container);
		addedObjectsFeat.add(featName);*/

	}

	public void postProcess(Match m){
		int i = 0;
		EObject source = null;
		EObject target = null;

		Set<Entry<String, Object>> entrySet = engine.getScriptEngine().getContext().getBindings(ScriptContext.ENGINE_SCOPE).entrySet();

		for (Entry<String, Object> entry : entrySet) {

			if (!(entry.getValue() instanceof EObject))
				continue;

			source = (EObject) entry.getValue();
			if (m != null && source != null){
				for (Node n : m.getRule().getRhs().getNodes()) {
					if (n.getName() != null && n.getName().equals("copy(" + entry.getKey() + ")")){
						target = m.getNodeTarget(n);
						if (target != null){
							Map<EObject,Node> objToNodeMap = null;
							if (graph instanceof TggHenshinEGraph){
								objToNodeMap = ((TggHenshinEGraph) graph).getObject2NodeMap();
							}
							if (graph instanceof HenshinEGraph){
								objToNodeMap = ((HenshinEGraph) graph).getObject2NodeMap();
							}

							Copier copier = new Copier();
							EObject newTarget = copier.copy(source);
							copier.copyReferences();

							graph.addTree(newTarget);

							for (EStructuralFeature feat : newTarget.eClass().getEAllStructuralFeatures()) {
								newTarget.eSet(feat, newTarget.eGet(feat));
							}

							TreeIterator<EObject> iterator = source.eAllContents();
							while (iterator.hasNext()){
								EObject srcNext = iterator.next();
								EObject next = copier.get(srcNext);
								if (next == null)
									continue;
								if (objToNodeMap != null){
									TNode node = (TNode) objToNodeMap.get(next);
									TNode oldNode = (TNode) objToNodeMap.get(target.eContainer());
									node.setComponent(oldNode.getComponent());	
									oldNode = (TNode) objToNodeMap.get(srcNext);
									if (isTranslatedMap != null)
										isTranslatedMap.put(oldNode, true);
									if (isTranslatedAttributeMap != null)
										for (Attribute a : oldNode.getAttributes()) {
											isTranslatedAttributeMap.put(a, true);
										}
									if (isTranslatedEdgeMap != null)
										for (Edge edge : oldNode.getAllEdges()) {
											isTranslatedEdgeMap.put(edge, true);
										}

								}

								for (EStructuralFeature feat : next.eClass().getEAllStructuralFeatures()) {
									next.eSet(feat, next.eGet(feat));

								}
							}

							EObject container = target.eContainer();
							if (target.eContainingFeature().isMany()){
								List<EObject> list = (List<EObject>) container.eGet(target.eContainingFeature());
								int indexOf = list.indexOf(target);
								list.add(indexOf, newTarget);
								list.remove(target);
								//container.eSet(target.eContainingFeature(), newTarget);

								
							} else {
								container.eSet(target.eContainingFeature(), newTarget);
								
							}

							graph.remove(target);
							if (objToNodeMap != null){
								TNode node = (TNode) objToNodeMap.get(newTarget);
								TNode oldNode = (TNode) objToNodeMap.get(container);
								node.setComponent(oldNode.getComponent());	
							}
							//engine.getScriptEngine().put("source"+i, null);
							i++;
						}
					}
				}
			}
		}
	}

}

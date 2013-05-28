package de.tub.tfs.henshin.tggeditor.commands;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.util.HenshinEGraph;
import org.eclipse.emf.henshin.model.Action;
import org.eclipse.emf.henshin.model.Action.Type;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.util.TggHenshinEGraph;

public class ObjectCopier {

	private EGraph graph;
	private ArrayList<EObject> addedObjects = new ArrayList<EObject>();
	private ArrayList<EObject> addedObjectContainer = new ArrayList<EObject>();
	private ArrayList<String> addedObjectsFeat = new ArrayList<String>();
	
	public ObjectCopier(EGraph graph){
		this.graph = graph;
	}
	
	public void copy(EObject container,String featName,EObject oldObj) {
		EObject obj = EcoreUtil.copy(oldObj);
		
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
		addedObjectsFeat.add(featName);
	}
	
	public void postProcess(){
		for (int i = 0; i < addedObjects.size(); i++) {
			EObject container = addedObjectContainer.get(i);
			EStructuralFeature feat = container.eClass().getEStructuralFeature(addedObjectsFeat.get(i));
			Object old = container.eGet(feat);
			if (feat.isMany()){
				List l = (List) old;
				EObject o = (EObject) l.remove(l.size()-1);
				graph.remove(old);
				l.add(addedObjects.get(i));
			} else {
				EObject o = (EObject) old;
				container.eSet(feat, addedObjects.get(i));
				graph.remove(o);
				
			}
		}
		
		addedObjectContainer.clear();
		addedObjectsFeat.clear();
		addedObjects.clear();
	}
	
}

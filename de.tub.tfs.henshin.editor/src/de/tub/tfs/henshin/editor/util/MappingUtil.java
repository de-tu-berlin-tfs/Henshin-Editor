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
package de.tub.tfs.henshin.editor.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

public class MappingUtil {

	private static HashMap<Rule,Pair<Integer,LinkedHashSet<HashSet<Node>>>> cachedMappings = new HashMap<Rule, Pair<Integer,LinkedHashSet<HashSet<Node>>>>();
	public static HashSet<HashSet<Node>> convertMappings(Rule r){
		
		return convertMappings(r.getMappings(),getAllMappingsFromFormula(r.getLhs().getFormula()));
	}	
	
	public static int convertMappings(Rule r,Node focusedNode){

		return getMappingNumber(focusedNode,r.getMappings(),getAllMappingsFromFormula(r.getLhs().getFormula()));
	}	

	public static HashSet<HashSet<Node>> convertMappings(List<Mapping>... mappings){
		
		HashSet<HashSet<Node>> result = new LinkedHashSet<HashSet<Node>>();
		LinkedList<Mapping> allMappings = new LinkedList<Mapping>();
		for (List<Mapping> mappingList : mappings) {
			allMappings.addAll(mappingList);
		}
		for (Mapping mapping : allMappings) {
			HashSet<Node> targetSet = null;
			Iterator<HashSet<Node>> iterator = result.iterator();
			while (iterator.hasNext()) {
				HashSet<Node> mappingSet = iterator.next();
				if (mappingSet.contains(mapping.getOrigin()) || mappingSet.contains(mapping.getImage())){
					if (targetSet == null){
						targetSet = mappingSet;
					} else {
						targetSet.addAll(mappingSet);
						iterator.remove();
					}
				}
			}
			if (targetSet == null){
				targetSet = new HashSet<Node>();
				result.add(targetSet);
			}
			targetSet.add(mapping.getImage());
			targetSet.add(mapping.getOrigin());
		
		}
		
		return result;
	}
	
	public static int getMappingNumber(Node focusNode,List<Mapping>... mappings){
		LinkedHashSet<HashSet<Node>> result = null;
		LinkedList<Mapping> allMappings = new LinkedList<Mapping>();
		for (List<Mapping> mappingList : mappings) {
			allMappings.addAll(mappingList);
		}
		
		Pair<Integer, LinkedHashSet<HashSet<Node>>> pair = cachedMappings.get(focusNode.getGraph().getRule());
		
		if (pair != null && pair.getFirst() == allMappings.hashCode()){
			result = pair.getSecond();
		}
		
		if (result == null){
			result = new LinkedHashSet<HashSet<Node>>();
			
			for (Mapping mapping : allMappings) {
				HashSet<Node> targetSet = null;
				Iterator<HashSet<Node>> iterator = result.iterator();
				while (iterator.hasNext()) {
					HashSet<Node> mappingSet = iterator.next();
					if (mappingSet.contains(mapping.getOrigin()) || mappingSet.contains(mapping.getImage())){
						if (targetSet == null){
							targetSet = mappingSet;
						} else {
							
							targetSet.addAll(mappingSet);
							iterator.remove();
						}
					}
				}
				if (targetSet == null){
					targetSet = new HashSet<Node>();
					result.add(targetSet);
				}
				targetSet.add(mapping.getImage());
				targetSet.add(mapping.getOrigin());
				
			}	
			cachedMappings.put(focusNode.getGraph().getRule(), new Pair<Integer, LinkedHashSet<HashSet<Node>>>(allMappings.hashCode(), result));
		}
		int i = 0;
		for (HashSet<Node> mappedNodes : result) {
			if (mappedNodes.contains(focusNode))
				return i;
			i++;
		}
		
		return -1;
	}
	
	
	
	
	public static List<Mapping> getAllMappingsFromFormula(Formula formula){
		List<Mapping> result = new LinkedList<Mapping>();
		if (formula != null){
			TreeIterator<EObject> eAllContents = formula.eAllContents();
			while (eAllContents.hasNext()){
				EObject eObj = eAllContents.next();
				if (eObj instanceof Mapping){
					result.add((Mapping) eObj);
				}
			}
		}
		return result;
	}
}

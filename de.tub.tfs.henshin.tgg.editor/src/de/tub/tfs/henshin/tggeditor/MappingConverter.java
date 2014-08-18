/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor;

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

import de.tub.tfs.henshin.tgg.CritPair;


public class MappingConverter {
	@SuppressWarnings("unchecked")
	public static HashSet<HashSet<Node>> convertMappings(CritPair criticalPair){
		return MappingConverter.convertMappings(criticalPair.getMappingsOverToRule1(),criticalPair.getMappingsOverToRule2(),criticalPair.getMappingsRule1ToRule2(),criticalPair.getRule1().getMappings(),criticalPair.getRule2().getMappings(),MappingConverter.getAllMappingsFromFormula(criticalPair.getRule1().getLhs().getFormula()),MappingConverter.getAllMappingsFromFormula(criticalPair.getRule2().getLhs().getFormula()));
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

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
/**
 * HenshinCache.java
 * created on 13.07.2012 15:30:53
 */
package de.tub.tfs.henshin.editor.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.model.subtree.Edge;
import de.tub.tfs.henshin.model.subtree.Subtree;


/**
 * @author huuloi
 *
 */
public class HenshinCache {
	
	private static HenshinCache instance = null;
	
	private HenshinCache() {
	}
	
	public static synchronized HenshinCache getInstance() {
		if (instance == null) {
			instance = new HenshinCache();
		}
		return instance;
	}
	
	private HashMap<Node, Set<Edge>> outgoingEdgeMap;
	
	private HashMap<Node, Set<Edge>> incomingEdgeMap;
	
	private HashMap<Subtree, Set<Edge>> outgoingSubtreeEdgeMap;
	
	private HashMap<Subtree, Set<Edge>> incomingSubtreeEdgeMap;
	
	private Set<EObject> collapsedEdges;
	
	private Set<EObject> removedEditParts;
	
	

	public HashMap<Subtree, Set<Edge>> getOutgoingSubtreeEdgeMap() {
		if (outgoingSubtreeEdgeMap == null) {
			outgoingSubtreeEdgeMap = new HashMap<Subtree, Set<Edge>>();
		}
		return outgoingSubtreeEdgeMap;
	}

	public HashMap<Subtree, Set<Edge>> getIncomingSubtreeEdgeMap() {
		if (incomingSubtreeEdgeMap == null) {
			incomingSubtreeEdgeMap = new HashMap<Subtree, Set<Edge>>();
		}
		return incomingSubtreeEdgeMap;
	}

	public HashMap<Node, Set<Edge>> getOutgoingEdgeMap() {
		if (outgoingEdgeMap == null) {
			outgoingEdgeMap = new HashMap<Node, Set<Edge>>();
		}
		return outgoingEdgeMap;
	}
	
	public HashMap<Node, Set<Edge>> getIncomingEdgeMap() {
		if (incomingEdgeMap == null) {
			incomingEdgeMap = new HashMap<Node, Set<Edge>>();
		}
		return incomingEdgeMap;
	}

	public Set<EObject> getCollapsedEdges() {
		if (collapsedEdges == null) {
			collapsedEdges = new HashSet<EObject>();
		}
		return collapsedEdges;
	}

	public Set<EObject> getRemovedEditParts() {
		if (removedEditParts == null) {
			removedEditParts = new HashSet<EObject>();
		}
		return removedEditParts;
	}

	public void init() {
		outgoingEdgeMap = new HashMap<Node, Set<Edge>>();
		
		incomingEdgeMap = new HashMap<Node, Set<Edge>>();
		
		collapsedEdges = new HashSet<EObject>();
		
		removedEditParts = new HashSet<EObject>();
	}
	
	public int removedNodeEditParts() {
		int result = 0;
		
		for (EObject obj : getRemovedEditParts()) {
			if (obj instanceof Node) {
				result++;
			}
		}
		
		return result;
	}
	
}

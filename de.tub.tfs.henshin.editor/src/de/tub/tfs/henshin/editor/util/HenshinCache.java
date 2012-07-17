/**
 * HenshinCache.java
 * created on 13.07.2012 15:30:53
 */
package de.tub.tfs.henshin.editor.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.model.subtree.Edge;


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
	
	private HashMap<Node, List<Edge>> outgoingEdgeMap;
	
	private HashMap<Node, List<Edge>> incomingEdgeMap;
	
	private Set<EObject> collapsedEdges;
	
	private Set<EObject> removedEditParts;

	public HashMap<Node, List<Edge>> getOutgoingEdgeMap() {
		if (outgoingEdgeMap == null) {
			outgoingEdgeMap = new HashMap<Node, List<Edge>>();
		}
		return outgoingEdgeMap;
	}
	
	public HashMap<Node, List<Edge>> getIncomingEdgeMap() {
		if (incomingEdgeMap == null) {
			incomingEdgeMap = new HashMap<Node, List<Edge>>();
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
		outgoingEdgeMap = new HashMap<Node, List<Edge>>();
		
		incomingEdgeMap = new HashMap<Node, List<Edge>>();
		
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

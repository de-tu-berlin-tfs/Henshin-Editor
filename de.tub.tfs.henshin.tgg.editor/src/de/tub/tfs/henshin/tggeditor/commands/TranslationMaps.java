package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;

public class TranslationMaps {

	protected HashMap<Node, Boolean> isTranslatedNodeMap = new HashMap<Node, Boolean>();
	protected HashMap<Attribute, Boolean> isTranslatedAttributeMap = new HashMap<Attribute, Boolean>();
	protected HashMap<Edge, Boolean> isTranslatedEdgeMap = new HashMap<Edge, Boolean>();

	public HashMap<Node, Boolean> getIsTranslatedNodeMap() {
		return isTranslatedNodeMap;
	}


	public void setIsTranslatedNodeMap(HashMap<Node, Boolean> isTranslatedNodeMap) {
		this.isTranslatedNodeMap = isTranslatedNodeMap;
	}


	public HashMap<Attribute, Boolean> getIsTranslatedAttributeMap() {
		return isTranslatedAttributeMap;
	}


	public void setIsTranslatedAttributeMap(
			HashMap<Attribute, Boolean> isTranslatedAttributeMap) {
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
	}


	public HashMap<Edge, Boolean> getIsTranslatedEdgeMap() {
		return isTranslatedEdgeMap;
	}


	public void setIsTranslatedEdgeMap(HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;
	}



	
	public TranslationMaps() {
		super();
	}
	
	public TranslationMaps(HashMap<Node, Boolean> isTranslatedNodeMap,	 HashMap<Attribute, Boolean> isTranslatedAttributeMap ,	 HashMap<Edge, Boolean> isTranslatedEdgeMap) {
		this.isTranslatedAttributeMap=isTranslatedAttributeMap;
		this.isTranslatedEdgeMap=isTranslatedEdgeMap;
		this.isTranslatedNodeMap=isTranslatedNodeMap;
	}

}

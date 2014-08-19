package de.tub.tfs.henshin.tggeditor.commands;

import java.util.HashMap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;

public class TranslationMaps {

	protected HashMap<EObject, Boolean> isTranslatedNodeMap = new HashMap<EObject, Boolean>();
	protected HashMap<EObject, HashMap<EAttribute, Boolean>> isTranslatedAttributeMap = new HashMap<EObject,HashMap<EAttribute, Boolean>>();
	protected HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap = new HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>>();

	/**
	 * @return the isTranslatedAttributeMap
	 */
	public HashMap<EObject, HashMap<EAttribute, Boolean>> getIsTranslatedAttributeMap() {
		return isTranslatedAttributeMap;
	}


	/**
	 * @param isTranslatedAttributeMap the isTranslatedAttributeMap to set
	 */
	public void setIsTranslatedAttributeMap(
			HashMap<EObject, HashMap<EAttribute, Boolean>> isTranslatedAttributeMap) {
		this.isTranslatedAttributeMap = isTranslatedAttributeMap;
	}
	
	/**
	 * @return the isTranslatedEdgeMap
	 */
	public HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> getIsTranslatedEdgeMap() {
		return isTranslatedEdgeMap;
	}


	/**
	 * @param isTranslatedEdgeMap the isTranslatedEdgeMap to set
	 */
	public void setIsTranslatedEdgeMap(
			HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		this.isTranslatedEdgeMap = isTranslatedEdgeMap;
	}


	public HashMap<EObject, Boolean> getIsTranslatedNodeMap() {
		return isTranslatedNodeMap;
	}


	public void setIsTranslatedNodeMap(HashMap<EObject, Boolean> isTranslatedNodeMap) {
		this.isTranslatedNodeMap = isTranslatedNodeMap;
	}












	
	public TranslationMaps() {
		super();
	}
	
	public TranslationMaps(HashMap<EObject, Boolean> isTranslatedNodeMap,	 HashMap<EObject, HashMap<EAttribute, Boolean>> isTranslatedAttributeMap ,	HashMap<EObject, HashMap<EReference, HashMap<EObject, Boolean>>> isTranslatedEdgeMap) {
		this.isTranslatedAttributeMap=isTranslatedAttributeMap;
		this.isTranslatedEdgeMap=isTranslatedEdgeMap;
		this.isTranslatedNodeMap=isTranslatedNodeMap;
	}

}

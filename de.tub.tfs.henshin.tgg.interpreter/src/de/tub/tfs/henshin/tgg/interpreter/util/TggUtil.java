/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/

package de.tub.tfs.henshin.tgg.interpreter.util;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

/**
 * Class for general methods for loading triple graphs - used by HenshinTGG.
 * 
 * @author Frank Hermann
 */

public class TggUtil {


	// root annotation key
	public static final String HENSHIN_TGG_PKG_KEY = "de.tu-berlin.tfs.henshin.tgg";
	public static final String HENSHIN_TGG_ANNOTATION_VALUE = "tgg";

	public static final int MARKER_ANNOTATION_POS = 0;
	public static final int COMPONENT_ANNOTATION_POS = 1;

	// markers for graph elements
	public static final String CREATE = "<++>";
	public static final String TRANSLATE = "<tr>";
	public static final String IS_TRANSLATED = "[tr]";
	public static final String IS_NOT_TRANSLATED = "[!tr]";
	public static final String[] ELEMENT_MARKERS = {CREATE,TRANSLATE,IS_TRANSLATED,IS_NOT_TRANSLATED};

	// markers for triple rules to distinguish the types of operational rules
	public static final String FW_TRANSLATION = "ft";
	public static final String BW_TRANSLATION = "bt";
	public static final String INTEGRATION = "it";
	public static final String CONSISTENCY_CREATING = "cc";
	public static final String TGG_RULE = "tgg";
	public static final String[] RULE_MARKERS = {FW_TRANSLATION,BW_TRANSLATION,INTEGRATION,CONSISTENCY_CREATING,TGG_RULE};
	
	
	
	public TggUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initClassConversions() {
		if (EMFModelManager.hasClassConversion(HenshinPackage.eINSTANCE, "Node", TggPackage.Literals.TNODE))
			return;
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Node", TggPackage.Literals.TNODE);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Edge", TggPackage.Literals.TEDGE);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Attribute", TggPackage.Literals.TATTRIBUTE);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Rule", TggPackage.Literals.TGG_RULE);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Graph", TggPackage.Literals.TRIPLE_GRAPH);
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Module", TggPackage.Literals.TGG);
	}
	



	public static String getElemAnnotationValue(ModelElement elem,
			String key) {

		Annotation a = getElemAnnotation(elem, key);
		if (a== null)
			return null;
		return a.getValue();
	}

	
	public static Annotation getElemAnnotation(ModelElement elem,
			String key) {

		if (key != null){
			// search for key
			for (Annotation a : elem.getAnnotations()) {
				if (key.equals(a.getKey()))
					return a;
			}
		}

		// key not found
		return null;
	}


	
	/**
	 * retrieve the Tgg annotation at position
	 * @param elem - the model element
	 * @param position - the position of the annotation that shall be retrieved
	 * @return
	 */
	public static String getElemTggAnnotation(ModelElement elem, int position) {
		if (elem==null || elem.getAnnotations().size()==0)
			return null;
		Annotation a = elem.getAnnotations().get(0);
		if (a==null || (a.getAnnotations().size()<=position))
			return null;
		// nested annotation is present, thus, retrieve nested annotation
		a = a.getAnnotations().get(position);
		if(a==null)
			return null;
		// nested annotation for marker/component is present - return the value
		return a.getValue();
	}


	// retrieve the tripleComponent of an EObject
	public static TripleComponent getEObjectTripleComponent(TGG tgg, EClass c) {
		// retrieve the component
		TripleComponent component = null;
		List<ImportedPackage> pkgs = tgg.getImportedPkgs();

		for (ImportedPackage p : pkgs) {
			if (p.getPackage() == c.getEPackage())
				component = p.getComponent();
		}
		return component;
	}
	
	// retrieve the marker of elem
	public static String getElemMarker(ModelElement elem) {
		return getElemTggAnnotation(elem, MARKER_ANNOTATION_POS);
	}

	public static void addElemAnnotation(ModelElement elem,
			String key, String value) {
		Annotation a = createElemAnnotation(key, value);
		elem.getAnnotations().add(a);
	}
	
	
	// add the tgg root annotation to elem
	public static void addRootTggAnnotation(ModelElement elem) {
		TggUtil.addElemAnnotation(elem, TggUtil.HENSHIN_TGG_PKG_KEY, TggUtil.HENSHIN_TGG_ANNOTATION_VALUE);
	}

	public static Annotation createRootAnnotation() {
		return createElemAnnotation(TggUtil.HENSHIN_TGG_PKG_KEY, TggUtil.HENSHIN_TGG_ANNOTATION_VALUE);
	}

	public static Annotation createElemAnnotation(String elementMarkerKey,
			String marker) {
		Annotation a = HenshinFactoryImpl.eINSTANCE.createAnnotation();
		a.setKey(elementMarkerKey);
		a.setValue(marker);
		return a;
	}
	
	

	

	
	public static EPackage getEPackage(ModelElement elem, String nsURI){
		if(nsURI == null) return null;
		Module m = getModuleFromElement(elem);
		EList<EPackage> pkgs = m.getImports();
		for(EPackage p: pkgs){
			if(nsURI.equals(p.getNsURI()))
				return p;
		}
		return null;
	}
	
	public static Module getModuleFromElement(ModelElement elem) {
		if (elem == null)
			return null;
		return (Module) elem.eContainer().eResource().getContents().get(0);
	}

	public static Boolean getIsTranslated(TNode tNode) {
		return getIsTranslated(tNode.getMarkerType());
	}

	public static Boolean getIsTranslated(TAttribute att) {
		return getIsTranslated(att.getMarkerType());
	}

	public static Boolean getIsTranslated(TEdge tEdge) {
		return getIsTranslated(tEdge.getMarkerType());
	}

	private static Boolean getIsTranslated(String markerType) {
		if(markerType==null) return null;
		if(IS_TRANSLATED.equals(markerType))
			return true;
		return false;
	}

	
}

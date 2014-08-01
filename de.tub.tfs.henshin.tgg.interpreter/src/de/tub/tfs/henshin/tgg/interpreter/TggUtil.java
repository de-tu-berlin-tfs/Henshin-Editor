/**
 * <copyright>
 * Copyright (c) 2010-2014 Henshin developers. All rights reserved. 
 * This program and the accompanying materials are made available 
 * under the terms of the Eclipse Public License v1.0 which 
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * </copyright>
 */

package de.tub.tfs.henshin.tgg.interpreter;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Annotation;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.ModelElement;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.HenshinFactoryImpl;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;
import de.tub.tfs.muvitor.ui.utils.LoadDelegate;
import de.tub.tfs.muvitor.ui.utils.SaveDelegate;

/**
 * Class for general methods for loading triple graphs - used by HenshinTGG.
 * 
 * @author Frank Hermann
 */

public class TggUtil {

	private static boolean init = false;

	// root annotation key
	public static final String HENSHIN_TGG_PKG_KEY = "de.tu-berlin.tfs.henshin.tgg";
	public static final String HENSHIN_TGG_ANNOTATION_VALUE = "tgg";

	// imports annotation key
	public static final String HENSHIN_TGG_IMPORTS_KEY = "imports";
	public static final String HENSHIN_TGG_IMPORTS_VALUE = "packages";
	public static final String HENSHIN_TGG_IMPORTS_LOAD_DEFAULT_VALUES_KEY = "loadWithDefaults";

	
	public static final int MARKER_ANNOTATION_POS = 0;
	public static final int COMPONENT_ANNOTATION_POS = 1;

	
	// markers for triple components
	public static final String SOURCE = TripleComponent.SOURCE.toString();
	public static final String CORRESPONDENCE = TripleComponent.CORRESPONDENCE.toString();
	public static final String TARGET = TripleComponent.TARGET.toString();
	public static final String DEFAULT = "DEFAULT";
	public static final String[] COMPONENT_MARKERS = {SOURCE,CORRESPONDENCE,TARGET};
	public static final String COMPONENT_MARKER_KEY = "cmp";
	public static final String SRC = "SRC";
	public static final String COR = "COR";
	public static final String TGT = "TGT";
	public static final String DEF = "DEF";
	

	// markers for graph elements
	public static final String CREATE = "<++>";
	public static final String TRANSLATE = "<tr>";
	public static final String IS_TRANSLATED = "[tr]";
	public static final String IS_NOT_TRANSLATED = "[!tr]";
	public static final String[] ELEMENT_MARKERS = {CREATE,TRANSLATE,IS_TRANSLATED,IS_NOT_TRANSLATED};
	public static final String ELEMENT_MARKER_KEY = "elem";

	// markers for triple rules to distinguish the types of operational rules
	public static final String FW_TRANSLATION = "ft";
	public static final String BW_TRANSLATION = "bt";
	public static final String INTEGRATION = "it";
	public static final String CONSISTENCY_CREATING = "cc";
	public static final String TGG_RULE = "tgg";
	public static final String[] RULE_MARKERS = {FW_TRANSLATION,BW_TRANSLATION,INTEGRATION,CONSISTENCY_CREATING,TGG_RULE};
	public static final String RULE_MARKER_KEY = "rule";
	
	
	
	public TggUtil() {
		// TODO Auto-generated constructor stub
	}
	
	public static void initClassConversions() {
		if (!EMFModelManager.hasClassConversion(HenshinPackage.eINSTANCE, "Node", TggPackage.Literals.TNODE))
			init = false;
		if (init)
			return;
		init = true;
		HenshinFactory einstance = HenshinFactory.eINSTANCE;
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Node", TggPackage.Literals.TNODE,new SaveDelegate() {

			@Override
			public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
				//System.out.println("SAVE: " + o + " " + s);
				if (TggPackage.Literals.TNODE.getEStructuralFeatures().contains(s)){
					
					return true;
				}
				return false;
			}

						
		},
		new LoadDelegate() {
			
			@Override
			public void doLoad(EObject o) {
				//System.out.println("LOAD: " + o);
				updateEobject(o, getFragment(o));
				
			}
		});
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Edge", TggPackage.Literals.TEDGE,new SaveDelegate() {

			@Override
			public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
				//System.out.println("SAVE: " + o + " " + s);
				if (TggPackage.Literals.TEDGE.getEStructuralFeatures().contains(s)){
					
					return true;
				}
				return false;
			}

						
		},
		new LoadDelegate() {
			
			@Override
			public void doLoad(EObject o) {
				//System.out.println("LOAD: " + o);
				updateEobject(o, getFragment(o));
				
			}
		});
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Rule", TggPackage.Literals.TGG_RULE,new SaveDelegate() {

			@Override
			public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
				//System.out.println("SAVE: " + o + " " + s);
				if (TggPackage.Literals.TGG_RULE.getEStructuralFeatures().contains(s)){
					
					return true;
				}
				return false;
			}

						
		},
		new LoadDelegate() {
			
			@Override
			public void doLoad(EObject o) {
				//System.out.println("LOAD: " + o);
				updateEobject(o, getFragment(o));
				
			}
		});
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Attribute", TggPackage.Literals.TATTRIBUTE,new SaveDelegate() {

			@Override
			public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
				//System.out.println("SAVE: " + o + " " + s);
				if (TggPackage.Literals.TATTRIBUTE.getEStructuralFeatures().contains(s)){
					
					return true;
				}
				return false;
			}

						
		},
		new LoadDelegate() {
			
			@Override
			public void doLoad(EObject o) {
				//System.out.println("LOAD: " + o);
				updateEobject(o, getFragment(o));
				
			}
		});
		
		
		
		EMFModelManager.registerClassConversion(HenshinPackage.eINSTANCE, "Graph", TggPackage.Literals.TRIPLE_GRAPH,new SaveDelegate() {
			
			@Override
			public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
				//System.out.println("SAVE: " + o + " " + s);
				if (TggPackage.Literals.TRIPLE_GRAPH.getEStructuralFeatures().contains(s)){
					
					return true;
				}
				return false;
			}
		},
		new LoadDelegate() {
			
			@Override
			public void doLoad(EObject o) {
				//System.out.println("LOAD: " + o);
				updateEobject(o, getFragment(o));
			}
		});
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
	 * Check whether the annotation specifies an imported package.
	 * @param a - an annotation 
	 * @return whether the annotation specifies an imported package
	 */
	public static boolean isImportedPackageAnnotation(Annotation a){
		if (a.eContainer() instanceof Annotation)
			return TggUtil.HENSHIN_TGG_IMPORTS_KEY.equals(((Annotation)a.eContainer()).getKey());
		return false;
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

	// retrieve the tripleComponent of node
	public static String getNodeTripleComponent(Node node) {
		if (node.getAnnotations().size() == 0) {
			// retrieve the component
			Module m = (Module) node.eContainer().eResource().getContents()
					.get(0);
			List<Annotation> pkgs = NodeTypes.getImportedPackages(m);

			for (Annotation a : pkgs) {
				if (a.getKey()
						.equals(node.getType().getEPackage().getNsURI()))
					// add the annotation to node
					refreshAnnotations(node, null, a.getValue());
			}
		}
		return getElemTggAnnotation(node, COMPONENT_ANNOTATION_POS);
	}

	// retrieve the tripleComponent of an EObject
	public static String getEObjectTripleComponent(Module m, EClass c) {
		// retrieve the component
		String component = null;
		List<Annotation> pkgs = NodeTypes.getImportedPackages(m);

		for (Annotation a : pkgs) {
			if (a.getKey().equals(c.getEPackage().getNsURI()))
				component = a.getValue();
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
	
	
	public static void refreshAnnotations(Node node, String marker,
			String tripleComponent) {
			if(node.getAnnotations().size()!=0){
				Annotation rootAnnotation = getElemAnnotation(node, TggUtil.HENSHIN_TGG_PKG_KEY); 
				if(rootAnnotation==null || rootAnnotation.getAnnotations().size()!=2){
					// no TGG annotations available, remove all and initialise
					node.getAnnotations().clear();
					initialiseAnnotations(node, marker, tripleComponent);
				}
				else {
					// set keys and values
					rootAnnotation.getAnnotations().get(COMPONENT_ANNOTATION_POS).setKey(COMPONENT_MARKER_KEY);
					rootAnnotation.getAnnotations().get(COMPONENT_ANNOTATION_POS).setValue(tripleComponent);
					rootAnnotation.getAnnotations().get(MARKER_ANNOTATION_POS).setKey(ELEMENT_MARKER_KEY);
					rootAnnotation.getAnnotations().get(MARKER_ANNOTATION_POS).setValue(marker);
				}

				
			}
			else
				// no annotations available
				initialiseAnnotations(node, marker, tripleComponent);
	}

	
	public static void initialiseAnnotations(ModelElement elem, String marker,
			String tripleComponent) {

		Annotation a=TggUtil.createRootAnnotation();
		// add empty marker annotation
		a.getAnnotations().add(TggUtil.createElemAnnotation(TggUtil.ELEMENT_MARKER_KEY , marker));
		
		// add triple component annotation
		a.getAnnotations().add(TggUtil.createElemAnnotation(TggUtil.COMPONENT_MARKER_KEY, tripleComponent));
		
		// add root annotation - triggers visual refresh
		elem.getAnnotations().add(a);
		
	}

	public static void addImportAnnotation(Annotation importsAnnotation,
			String nsURI, String component, boolean loadWithDefaultValues) {

		// setting for loading default values
		Annotation defaultValuesAnnotation = createElemAnnotation(HENSHIN_TGG_IMPORTS_LOAD_DEFAULT_VALUES_KEY, Boolean.toString(loadWithDefaultValues));
		// package that is imported
		Annotation importsPackageAnnotation = createElemAnnotation(nsURI, component);
		// add the annotations
		importsPackageAnnotation.getAnnotations().add(defaultValuesAnnotation);
		importsAnnotation.getAnnotations().add(importsPackageAnnotation);
	}
	
	public static boolean getLoadWithDefaultValuesOfImportAnnotation(Annotation importsPackageAnnotation){
		if (importsPackageAnnotation==null
				|| importsPackageAnnotation.getAnnotations().size()==0) return false;
		Annotation loadWithDefaultValuesAnnotation = importsPackageAnnotation.getAnnotations().get(0);
		if(Boolean.toString(true).equals(loadWithDefaultValuesAnnotation.getValue())) return true;
		return false;
	}

	public static void setLoadWithDefaultValuesOfImportAnnotation(
			Annotation importsPackageAnnotation, boolean value) {
		if (importsPackageAnnotation == null
				|| importsPackageAnnotation.getAnnotations().size() == 0)
			return;
		Annotation loadWithDefaultValuesAnnotation = importsPackageAnnotation
				.getAnnotations().get(0);
		if (!HENSHIN_TGG_IMPORTS_LOAD_DEFAULT_VALUES_KEY
				.equals(loadWithDefaultValuesAnnotation.getKey()))
			return;
		// put the new value in the annotation
		loadWithDefaultValuesAnnotation.setValue(Boolean.toString(value));
	}

	public static String getComponentAbbreviation(String component){
		if (SOURCE.equals(component)) return SRC;
		if (CORRESPONDENCE.equals(component)) return COR;
		if (TARGET.equals(component)) return TGT;
		return DEF;
		
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
	
}

package de.tub.tfs.henshin.tgg.interpreter;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;

import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;
import de.tub.tfs.muvitor.ui.utils.LoadDelegate;
import de.tub.tfs.muvitor.ui.utils.SaveDelegate;

public class TggUtil {

	private static boolean init = false;

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

}

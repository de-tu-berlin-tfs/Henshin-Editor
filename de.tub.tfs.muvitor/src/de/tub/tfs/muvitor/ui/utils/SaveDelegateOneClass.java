package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class SaveDelegateOneClass extends SaveDelegate {
	private EClass eClass=null;

	public SaveDelegateOneClass(EClass eClass) {
		this.eClass=eClass;
	}

	@Override
	public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
		//System.out.println("SAVE: " + o + " " + s);
		if (eClass.getEStructuralFeatures().contains(s)){
			
			return true;
		}
		return false;
	}


}

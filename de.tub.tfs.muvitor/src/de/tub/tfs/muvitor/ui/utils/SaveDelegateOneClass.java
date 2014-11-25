package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class SaveDelegateOneClass extends SaveDelegate {
	private EClass sourceEClass=null;
	private EClass targetEClass=null;

	public SaveDelegateOneClass(EClass sourceEClass,EClass targetEClass) {
		this.sourceEClass = sourceEClass;
		this.targetEClass=targetEClass;
	}

	@Override
	public boolean shouldSkipSave(EObject o, EStructuralFeature s) {
		//System.out.println("SAVE: " + o + " " + s);
		String featName = s.getName();
		if (targetEClass.getEStructuralFeature(featName) != null &&
			sourceEClass.getEStructuralFeature(featName) == null	){
			
			return true;
		}
		return false;
	}


}

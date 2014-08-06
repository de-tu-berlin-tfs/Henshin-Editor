package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.ecore.EObject;

public class LoadDelegateOneClass extends LoadDelegate {

	public LoadDelegateOneClass() {
	}

	@Override
	public void doLoad(EObject o) {
		//System.out.println("LOAD: " + o);
		updateEobject(o, getFragment(o));
		
	}


}

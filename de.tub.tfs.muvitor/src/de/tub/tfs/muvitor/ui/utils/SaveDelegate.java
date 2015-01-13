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
package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public abstract class SaveDelegate {
	/**
	 *  
	 * @param o
	 * @param s
	 * @return false if the attribute should be saved in the XMI File,
	 * 		   true if this attribute should be skipped 
	 */
	public abstract boolean shouldSkipSave(EObject o,EStructuralFeature s);
	
	public FragmentResource getFragmentResource(EObject o){
		FragmentResource fragmentResource = EMFModelManager.createModelManager("henshin").requestFragmentResource(o.eResource());
		
		return fragmentResource;
		
	}
	
	public void saveToFragmentResource(EObject o,FragmentResource r){
		r.getContents().add(o);
	}
}
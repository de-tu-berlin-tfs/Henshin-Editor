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

import de.tub.tfs.muvitor.ui.IDUtil;

public abstract class LoadDelegate {
	/**
	 *  
	 * @param o
	 * @param s
	 * @return false if the attribute should be saved in the XMI File,
	 * 		   true if this attribute should be skipped 
	 */
	public abstract void doLoad(EObject o);
	
	
	public EObject getFragment(EObject o){
		if (o.eResource() == null)
			return null;
		
		FragmentResource fragmentResource = EMFModelManager.createModelManager("henshin").requestFragmentResource(o.eResource());
		
		return fragmentResource.getEObject(IDUtil.getIDForModel(o));
		
	}
	
	public void updateEobject(EObject orig,EObject frag){
		if (frag == null)
			return;
		for (EStructuralFeature feat : frag.eClass().getEStructuralFeatures()) {
			
			orig.eSet(feat, frag.eGet(feat));
		}
	}
}
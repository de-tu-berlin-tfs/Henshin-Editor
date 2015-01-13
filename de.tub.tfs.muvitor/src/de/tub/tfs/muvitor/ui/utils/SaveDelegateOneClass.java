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
		if (sourceEClass == null){
			return true;
		} else {
			String featName = s.getName();
			if (targetEClass.getEStructuralFeature(featName) != null &&
				sourceEClass.getEStructuralFeature(featName) == null	){
				
				return true;
			}
			return false;
		}

	}


}

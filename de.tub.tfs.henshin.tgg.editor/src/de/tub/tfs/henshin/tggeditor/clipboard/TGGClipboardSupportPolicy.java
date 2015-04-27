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
package de.tub.tfs.henshin.tggeditor.clipboard;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.gmf.runtime.emf.clipboard.core.IClipboardSupportPolicy;

import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.impl.TggPackageImpl;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;

public class TGGClipboardSupportPolicy implements IClipboardSupportPolicy {

	public TGGClipboardSupportPolicy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean provides(IAdaptable adaptable) {
		EObject eObj = (EObject) adaptable.getAdapter(Object.class);
		if (eObj == null)
			return false;
		if (eObj instanceof IndependentUnit){
			if (eObj instanceof IndependentUnit && (((IndependentUnit) eObj).getName().equals("FTRuleFolder") 
					 || ((IndependentUnit) eObj).getName().equals("RuleFolder")
					 || ((IndependentUnit) eObj).getName().equals("BTRuleFolder")
				  	 || ((IndependentUnit) eObj).getName().equals("CCRuleFolder")
			         || ((IndependentUnit) eObj).getName().equals("ITRuleFolder")))
				return false; 
		}
		if (eObj.eClass().getEPackage().equals(HenshinPackage.eINSTANCE))
			return true;
		if (eObj.eClass().getEPackage().equals(TggPackage.eINSTANCE))
			return true;
		
		return false;
	}

}

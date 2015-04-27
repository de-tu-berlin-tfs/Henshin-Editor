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

public class LoadDelegateOneClass extends LoadDelegate {

	public LoadDelegateOneClass() {
	}

	@Override
	public void doLoad(EObject o) {
		//System.out.println("LOAD: " + o);
		updateEobject(o, getFragment(o));
		
	}


}

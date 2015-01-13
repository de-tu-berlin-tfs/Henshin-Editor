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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class ViewRegistry {
	/**
	 * A map associating a class of EObjects with IDs of
	 * {@link MuvitorPageBookView}s being registered in plugin.xml.
	 * 
	 * @see #registerViewID(EClass, String)
	 */
	static private final Map<EClass, String> eClass2ViewIDMap = new HashMap<EClass, String>();
	
	static public final String getViewID(final EClass eClass) {
		
		
		LinkedList<EClass> sTypes = new LinkedList<EClass>();
		sTypes.add(eClass);
		
		while(!sTypes.isEmpty()){
			EClass cur = sTypes.pop();
			
			String id = eClass2ViewIDMap.get(cur);
			
			if (id != null)
				return id;
			
			for (EClass eClass2 : cur.getESuperTypes()) {
				sTypes.addLast(eClass2);
			}
		}
		
		return null;
		
	}
	
	/**
	 * Associate a class of models with the ID of a {@link MuvitorPageBookView}
	 * that has been registered in plugin.xml.
	 * 
	 * @param modelClass
	 *            a class of EObjects
	 * @param viewID
	 *            the ID of a view to show the class of EObjects
	 * @see #showView(EObject)
	 */
	static public final void registerViewID(final EClass eClass, final String viewID) {
		
		if (eClass2ViewIDMap.containsKey(eClass)) eClass2ViewIDMap.remove(eClass);
		eClass2ViewIDMap.put(eClass, viewID);
	}
}

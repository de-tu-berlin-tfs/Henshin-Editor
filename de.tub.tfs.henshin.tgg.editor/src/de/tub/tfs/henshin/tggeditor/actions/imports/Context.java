/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.util.HashMap;
import java.util.LinkedHashSet;

import org.eclipse.emf.ecore.EObject;

public class Context extends LinkedHashSet<EObject>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7857131980923093922L;
	
	public static void handleContext(HashMap<EObject,Context> contexts,EObject obj){
		Context parentContext = contexts.get(obj.eContainer());
		if (parentContext == null){
			parentContext = new Context();
			contexts.put(obj.eContainer(), parentContext);
		}
		parentContext.add(obj);
	}
}

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
package de.tub.tfs.henshin.tgg.interpreter.gui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class Import {

	public Import() {
		// TODO Auto-generated constructor stub
	}

	public synchronized static void unloadModel(ResourceSet resSet, URI uri) {
		// has to be synchronised since XText serialisation is not thread-safe
		try {
			Resource res = resSet.getResource(uri, false);
		
			if (res != null)
				res.unload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

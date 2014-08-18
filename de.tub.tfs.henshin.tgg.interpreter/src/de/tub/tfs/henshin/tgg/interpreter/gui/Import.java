/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tgg.interpreter.gui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

public class Import {

	public Import() {
		// TODO Auto-generated constructor stub
	}

	synchronized static void unloadModel(ResourceSet resSet, URI uri) {
		// has to be synchronised since XText serialisation is not thread-safe
		Resource res = resSet.getResource(uri, false);
		try {
			if (res != null)
				res.unload();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

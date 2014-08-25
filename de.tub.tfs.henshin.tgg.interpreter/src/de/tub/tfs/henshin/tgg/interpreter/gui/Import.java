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

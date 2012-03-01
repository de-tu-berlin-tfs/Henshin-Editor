package de.tub.tfs.muvitor.ui.utils;

import java.util.HashMap;
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
		return eClass2ViewIDMap.get(eClass);
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
		eClass2ViewIDMap.put(eClass, viewID);
	}
}

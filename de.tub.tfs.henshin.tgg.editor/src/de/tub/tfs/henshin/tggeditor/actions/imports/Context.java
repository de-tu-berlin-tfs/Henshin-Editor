/*******************************************************************************
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

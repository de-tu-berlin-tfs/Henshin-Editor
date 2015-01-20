package de.tub.tfs.henshin.tgg.interpreter.postprocessing;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;

public interface AbstractPostProcessor {

	public void registerSharedObjects(HashMap sharedObjects);
	
	public abstract EObject process();
	
	public abstract String processLine(String code);
	
	public abstract String processCode(String code);
	
	
}

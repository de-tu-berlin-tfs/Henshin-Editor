package de.tub.tfs.henshin.tgg.interpreter.postprocessing;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;

public interface AbstractPostProcessorFactory {
	
	public int getPriority();
	
	public boolean isValid(URI inputURI);
	
	public <T extends AbstractPostProcessor> T createPostProcessor(EObject root);

}

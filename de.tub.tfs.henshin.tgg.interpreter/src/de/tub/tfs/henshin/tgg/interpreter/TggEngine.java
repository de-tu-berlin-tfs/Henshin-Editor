/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tgg.interpreter;

import org.eclipse.emf.henshin.interpreter.Match;

/**
 * The Interface TGGEngine.
 *
 * @author frank.hermann
 */
public interface TggEngine {

	/**
	 * Post process tgg information after rule application.
	 *
	 * @param m the match of the rule application
	 */
	public abstract void postProcess(Match m);

}
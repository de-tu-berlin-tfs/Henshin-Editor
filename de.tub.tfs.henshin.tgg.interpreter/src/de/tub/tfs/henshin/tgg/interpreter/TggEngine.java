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
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
package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import de.tub.tfs.henshin.tgg.TripleGraph;

/**
 * A folder for graphs in the tree editor.
 */

public class Divider extends EObjectImpl {
	/**
	 * the containing triple graph of the divider
	 */
	private TripleGraph tripleGraph;
	
	/**
	 * the boolean value whether this divider is between the source and correspondence components
	 */
	private boolean isSC;
	
	public boolean isSC() {
		return isSC;
	}

	public void setSC(boolean isSC) {
		this.isSC = isSC;
	}

	public TripleGraph getTripleGraph() {
		return tripleGraph;
	}

	public Divider(TripleGraph tripleGraph, boolean isSC){
		this.tripleGraph = tripleGraph;
		this.isSC = isSC;			
	}

	
}

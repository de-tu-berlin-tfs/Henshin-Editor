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
/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.graph;

import java.util.EventObject;

import org.eclipse.gef.commands.CommandStackListener;

/**
 * The Class CommandStackListener2TraceRefresh.
 * 
 * @author Johann
 */
public class CommandStackListener2TraceRefresh implements CommandStackListener {

	/** The graph page. */
	private final GraphPage graphPage;

	/**
	 * Instantiates a new command stack listener2 trace refresh.
	 * 
	 * @param graphPage
	 *            the graph page
	 */
	public CommandStackListener2TraceRefresh(GraphPage graphPage) {
		super();
		this.graphPage = graphPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.commands.CommandStackListener#commandStackChanged(java
	 * .util.EventObject)
	 */
	@Override
	public void commandStackChanged(EventObject event) {
		graphPage.refreshTrace();
	}

}

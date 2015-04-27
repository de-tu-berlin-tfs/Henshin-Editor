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
package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;



/**
 *The class DeleteGraphCommand deletes a graph.
 */
public class DeleteTGGGraphCommand extends CompoundCommand {

	
	/**
	 * Instantiates a new delete graph command.
	 *
	 * @param graph the graph to be deleted
	 */
	public DeleteTGGGraphCommand(Graph graph) {
		
		add(new DeleteGraphCommand(graph));
	}




}

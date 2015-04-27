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
 * RemoveMetaModelCommand.java
 * created on 12.02.2012 01:51:39
 */
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

/**
 * @author huuloi
 *
 */
public class RemoveMetaModelCommand extends CompoundCommand {
	
	public RemoveMetaModelCommand(Graph graph, EPackage metaModel) {
		
		for (Node node : graph.getNodes()) {
			if (node.getType().getEPackage() == metaModel) {
				add(new DeleteNodeCommand(node));
			}
		}
	}

}

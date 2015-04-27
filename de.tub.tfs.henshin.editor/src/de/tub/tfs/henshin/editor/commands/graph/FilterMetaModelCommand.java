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
 * FilterMetaModelCommand.java
 * created on 14.02.2012 10:29:02
 */
package de.tub.tfs.henshin.editor.commands.graph;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * @author huuloi
 *
 */
public class FilterMetaModelCommand extends CompoundCommand {

	public FilterMetaModelCommand(Graph graph, List<EPackage> toFilter) {
		
		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);
		
		List<EPackage> toRemove = JavaUtil.subList(usedEPackages, toFilter);
		for (EPackage ePackage : toRemove) {
			add(new RemoveMetaModelCommand(graph, ePackage));
		}
	}
	
}

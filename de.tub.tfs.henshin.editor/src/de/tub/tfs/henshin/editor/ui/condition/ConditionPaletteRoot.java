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
package de.tub.tfs.henshin.editor.ui.condition;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.editor.actions.rule.NodeMappingCreationTool;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;
import de.tub.tfs.henshin.editor.ui.graph.GraphPaletteRoot;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * Extended graph pallet root.
 * 
 * @author Angeline
 */
public class ConditionPaletteRoot extends GraphPaletteRoot {

	/**
	 * Instantiates a new condition pallet root.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 */
	public ConditionPaletteRoot(Module transformationSystem) {
		super(transformationSystem);
		addToolEntry(rest, "Mapping", "Create Mapping",
				new ModelCreationFactory(Mapping.class),
				IconUtil.getDescriptor("mapping16.png"),
				IconUtil.getDescriptor("mapping24.png"),
				NodeMappingCreationTool.class);
	}

}

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
package de.tub.tfs.henshin.editor.ui.transformation_unit;

import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.editor.actions.transformation_unit.AddTransformationUnitTool;
import de.tub.tfs.henshin.editor.actions.transformation_unit.tools.ParameterCreationTool;
import de.tub.tfs.henshin.editor.actions.transformation_unit.tools.ParameterMappingCreationTool;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

/**
 * The Class TransUnitPaletteRoot.
 */
public class TransUnitPaletteRoot extends MuvitorPaletteRoot {

	/**
	 * Instantiates a new trans unit palette root.
	 */
	public TransUnitPaletteRoot() {
		addToolEntry(defaultPaletteGroup, "Transformation Unit",
				"Add Transformation Unit",
				new ModelCreationFactory(Rule.class),
				IconUtil.getDescriptor("addTU18.png"),
				IconUtil.getDescriptor("addTU25.png"),
				AddTransformationUnitTool.class);
		addToolEntry(defaultPaletteGroup, "Parameter", "Create Parameter",
				new ModelCreationFactory(Parameter.class),
				IconUtil.getDescriptor("parameter16.png"),
				IconUtil.getDescriptor("parameter20.png"),
				ParameterCreationTool.class);
		addToolEntry(defaultPaletteGroup, "Parameter mapping",
				"Create parameter mapping", new ModelCreationFactory(
						ParameterMapping.class),
				IconUtil.getDescriptor("mapping16.png"),
				IconUtil.getDescriptor("mapping24.png"),
				ParameterMappingCreationTool.class);

	}

}

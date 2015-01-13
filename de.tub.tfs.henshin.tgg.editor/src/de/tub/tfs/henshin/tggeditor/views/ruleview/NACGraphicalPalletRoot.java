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
package de.tub.tfs.henshin.tggeditor.views.ruleview;

import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;

import de.tub.tfs.henshin.tggeditor.TGGModelCreationFactory;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPaletteRoot;
import de.tub.tfs.muvitor.gef.palette.MappingCreationTool;


public class NACGraphicalPalletRoot extends GraphicalPaletteRoot {

	//protected PaletteGroup controls;	

	public NACGraphicalPalletRoot(Module transformationSystem) {
		super(transformationSystem);
		addToolEntry(super.controls, 
				"Mappping",
				"Create Mapping", 
				new TGGModelCreationFactory(NestedCondition.class),
				null, 
				null,
				MappingCreationTool.class);		
	}
	
}

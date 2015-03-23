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
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteMappingCommand.
 * 
 * @author Johann
 */
public class DeleteMappingCommand extends CompoundCommand {

	private Mapping mapping;

	/**
	 * @param mapping
	 * @param forceDeleteOrgColor
	 */
	public DeleteMappingCommand(Mapping mapping, boolean forceDeleteOrgColor) {
		this.mapping =mapping;
		// mapping colors are disabled
		

		Node imageNode = mapping.getImage();
		
		add(new SimpleDeleteEObjectCommand(mapping));
		add(new SimpleSetEFeatureCommand<Node, String>(imageNode, imageNode.getName(),
				HenshinPackage.Literals.NAMED_ELEMENT__NAME));
	}
	
	@Override
	public boolean canExecute() {
		if (!HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(mapping.getOrigin()) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(mapping.getImage()))
			return false;
		return super.canExecute();
	}

}

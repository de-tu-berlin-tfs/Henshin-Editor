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
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;

/**
 * @author nam
 * 
 */
public class MoveFlowElementCommand extends CompoundCommand {
	/**
	 * @param element
	 * @param moveDelta
	 */
	public MoveFlowElementCommand(FlowElement element, Point moveDelta) {
		super("Move Flow Element");

		Assert.isLegal(element != null && moveDelta != null);

		FlowElementLayout elementLayout = HenshinLayoutUtil.INSTANCE
				.getLayout(element);

		Integer newY = Integer.valueOf(moveDelta.y + elementLayout.getY());
		Integer newX = Integer.valueOf(moveDelta.x + elementLayout.getX());

		add(new SimpleSetEFeatureCommand<FlowElementLayout, Integer>(
				elementLayout, newX, HenshinLayoutPackage.Literals.LAYOUT__X));

		add(new SimpleSetEFeatureCommand<FlowElementLayout, Integer>(
				elementLayout, newY, HenshinLayoutPackage.Literals.LAYOUT__Y));
	}
}

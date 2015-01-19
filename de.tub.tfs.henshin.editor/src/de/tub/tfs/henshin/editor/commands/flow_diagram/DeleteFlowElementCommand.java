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
 * DeleteFlowElementCommand.java
 *
 * Created 23.12.2011 - 16:33:03
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.core.runtime.Assert;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalElement;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * @author nam
 * 
 */
public class DeleteFlowElementCommand extends CompoundCommand {

	public DeleteFlowElementCommand(FlowElement element) {
		super("Deleting Flow Element");		

		Assert.isLegal(element != null);

		for (Transition in : element.getIn()) {
			add(new DeleteTransitionCommand(in));
		}

		Transition out = element.getOut();

		if (out != null) {
			add(new DeleteTransitionCommand(out));
		}

		if (element instanceof ConditionalElement) {
			out = ((ConditionalElement) element).getAltOut();

			if (out != null) {
				add(new DeleteTransitionCommand(out));
			}
		}

		if (element instanceof Activity) {
			add(new SetActivityContentCommand((Activity) element, null));
		}

		Layout layout = HenshinLayoutUtil.INSTANCE.getLayout(element);

		add(new SimpleDeleteEObjectCommand(element));

		if (layout != null) {
			add(new SimpleDeleteEObjectCommand(layout));
		}
	}
}

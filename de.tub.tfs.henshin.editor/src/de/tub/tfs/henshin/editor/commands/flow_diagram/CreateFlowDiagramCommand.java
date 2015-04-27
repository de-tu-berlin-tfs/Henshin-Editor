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

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.StartEditpart;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.StopEditpart;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.layout.LayoutSystem;

/**
 * @author nam
 * 
 */
public class CreateFlowDiagramCommand extends CompoundCommand {

	/**
     * 
     */
	private String name;

	/**
	 * @param name
	 * @param root
	 */
	public CreateFlowDiagramCommand(String name, FlowControlSystem root) {
		super("Creating Flow Diaram '" + name + "'");

		this.name = name;

		LayoutSystem layoutRoot = HenshinLayoutUtil.INSTANCE
				.getLayoutSystem(root);
		FlowDiagram newDiagram = FlowControlFactory.eINSTANCE
				.createFlowDiagram();
		Start newStartNode = FlowControlFactory.eINSTANCE.createStart();
		End newEndNode = FlowControlFactory.eINSTANCE.createEnd();

		newDiagram.setName(name);
		newDiagram.setStart(newStartNode);

		add(new SimpleAddEObjectCommand<FlowControlSystem, FlowDiagram>(
				newDiagram,
				FlowControlPackage.Literals.FLOW_CONTROL_SYSTEM__UNITS, root));

		add(new CreateFlowElementCommand<Start>(newStartNode, newDiagram,
				StartEditpart.DEFAULT_FIG_LOC, layoutRoot));
		add(new CreateFlowElementCommand<End>(newEndNode, newDiagram,
				StopEditpart.DEFAULT_FIG_LOC, layoutRoot));

		add(new CreateTransitionCommand(
				FlowControlFactory.eINSTANCE.createTransition(), newStartNode,
				newEndNode, newDiagram));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return name != null && super.canExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return name != null && super.canUndo();
	}
}

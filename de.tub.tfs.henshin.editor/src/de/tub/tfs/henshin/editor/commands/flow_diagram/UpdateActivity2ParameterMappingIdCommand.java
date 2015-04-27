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
 * SetActivity2ParameterCommand.java
 *
 * Created 02.01.2012 - 03:16:46
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;

/**
 * @author nam
 * 
 */
public class UpdateActivity2ParameterMappingIdCommand extends Command {

	private FlowElementLayout activityLayout;

	private Activity model;

	private int oldId;

	/**
	 * @param a
	 */
	public UpdateActivity2ParameterMappingIdCommand(Activity a) {
		this(a, HenshinLayoutUtil.INSTANCE.getLayout(a));
	}

	/**
	 * @param a
	 * @param layout
	 */
	public UpdateActivity2ParameterMappingIdCommand(Activity a,
			FlowElementLayout layout) {
		super();

		model = a;
		activityLayout = layout;

		if (activityLayout != null) {
			oldId = activityLayout.getMapId();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return activityLayout != null && model != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		int newId;

		if (model.getParameters().isEmpty()) {
			newId = -1;
		} else {
			newId = activityLayout.getMapId();

			if (newId <= 0) {
				Set<Integer> usedId = new HashSet<Integer>();

				for (FlowElement e : model.getDiagram().getElements()) {
					if (e != model) {
						FlowElementLayout elementLayout = HenshinLayoutUtil.INSTANCE
								.getLayout(e);
						int id = elementLayout.getMapId();

						if (id > 0) {
							usedId.add(Integer.valueOf(id));
						}
					}
				}

				newId = 1;

				while (usedId.contains(Integer.valueOf(newId))) {
					newId++;
				}
			}
		}

		activityLayout.setMapId(newId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		activityLayout.setMapId(oldId);
	}
}

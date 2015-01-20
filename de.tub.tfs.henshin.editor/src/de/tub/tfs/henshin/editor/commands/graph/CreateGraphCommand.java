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
package de.tub.tfs.henshin.editor.commands.graph;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

/**
 * The Class CreateGraphCommand.
 */
public class CreateGraphCommand extends Command {

	/** The transformation system. */
	private Module transformationSystem;

	/** The graph. */
	private Graph graph;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new creates the graph command.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 * @param name
	 *            the name
	 */
	public CreateGraphCommand(Module transformationSystem,
			String name) {
		this.transformationSystem = transformationSystem;
		this.name = name;
		this.graph = HenshinFactory.eINSTANCE.createGraph();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		graph.setName(name);
		transformationSystem.getInstances().add(graph);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		transformationSystem.getInstances().remove(graph);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return name != null && transformationSystem != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return graph != null && transformationSystem != null;
	}

}

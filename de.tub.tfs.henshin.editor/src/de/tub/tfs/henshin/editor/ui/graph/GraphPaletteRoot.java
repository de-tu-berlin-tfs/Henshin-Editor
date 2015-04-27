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
 * GraphPaletteRoot.java
 * created on 11.02.2012 19:23:18
 */
package de.tub.tfs.henshin.editor.ui.graph;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.editor.actions.graph.tools.AttributeCreationTool;
import de.tub.tfs.henshin.editor.actions.graph.tools.EdgeCreationTool;
import de.tub.tfs.henshin.editor.actions.graph.tools.NodeCreationTool;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.NodeTypes;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

/**
 * @author huuloi
 *
 */
public class GraphPaletteRoot extends MuvitorPaletteRoot {
	
	/** The graph tools. */
	protected PaletteGroup graphTools;
	
	/** The rest. */
	protected PaletteGroup rest;

	private Module transformationSystem;
	
	public GraphPaletteRoot(Graph graph) {
		this(ModelUtil.getModelRoot(graph, Module.class), graph);
	}
	
	public GraphPaletteRoot(Module transformationSystem) {
		this(transformationSystem, null);
	}
	
	public GraphPaletteRoot(Module transformationSystem, Graph graph) {
		
		this.transformationSystem = transformationSystem;
		
		addToolEntry(defaultPaletteGroup, Messages.NODE, Messages.CREATE_NODE,
				new ModelCreationFactory(Node.class),
				ResourceUtil.ICONS.NODE.descr(Constants.SIZE_18),
				ResourceUtil.ICONS.NODE.descr(Constants.SIZE_25), NodeCreationTool.class);
		
		graphTools = createGraphPalette();
		add(1, graphTools);

		rest = new PaletteGroup(Messages.CONTROLS);
		add(rest);
		addToolEntry(rest, Messages.ATTRIBUTE, Messages.CREATE_ATTRIBUTE,
				new ModelCreationFactory(Attribute.class),
				ResourceUtil.ICONS.ATTRIBUTE.descr(Constants.SIZE_16),
				ResourceUtil.ICONS.ATTRIBUTE.descr(Constants.SIZE_20),
				AttributeCreationTool.class);

		addToolEntry(rest, Messages.EDGE, Messages.CREATE_EDGE, new ModelCreationFactory(
				Edge.class), ResourceUtil.ICONS.EDGE.descr(Constants.SIZE_18),
				ResourceUtil.ICONS.EDGE.descr(Constants.SIZE_20), EdgeCreationTool.class);

	}
	
	/**
	 * Creates the graph palette.
	 * 
	 * @return the palette group
	 */
	private PaletteGroup createGraphPalette() {
		PaletteGroup graphToolsGroup = new PaletteGroup(Messages.CONTROLS);
		for (EPackage ePackage : transformationSystem.getImports()) {
			final PaletteStack marqueeStack = new PaletteStack(Messages.EMPTY, Messages.EMPTY, null); //$NON-NLS-1$
			for (EClass eClass : getNodeTypesOfEPackage(ePackage)) {
				final ToolEntry entry = new ToolEntry(eClass.getName(),
						Messages.CREATE + Messages.SPACE + eClass.getName(),
						ResourceUtil.ICONS.NODE.descr(Constants.SIZE_18),
						ResourceUtil.ICONS.NODE.descr(Constants.SIZE_25),
						CreationTool.class) {
				};
				entry.setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY,
						new ModelCreationFactory(Node.class, eClass));
				entry.setUserModificationPermission(PERMISSION_NO_MODIFICATION);
				marqueeStack.add(entry);
			}
			graphToolsGroup.add(marqueeStack);

		}
		return graphToolsGroup;
	}

	/**
	 * Gets the node types of e package.
	 * 
	 * @param ePackage
	 *            the e package
	 * @return the node types of e package
	 */
	protected List<EClass> getNodeTypesOfEPackage(EPackage ePackage) {
		return NodeTypes.getNodeTypesOfEPackage(ePackage, false);
	}

	/**
	 * Refresh graph tools group.
	 */
	public void refreshGraphToolsGroup() {
		PaletteGroup tempGraphTools = createGraphPalette();
		remove(graphTools);
		graphTools = tempGraphTools;
		add(1, graphTools);
	}

}

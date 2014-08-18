/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.views.graphview;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tggeditor.TGGModelCreationFactory;
import de.tub.tfs.henshin.tggeditor.tools.AttributeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.EdgeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.NodeCreationTool;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

public class GraphicalPaletteRoot extends MuvitorPaletteRoot {
	
	/** The graph tools. */
	protected PaletteGroup graphTools;
	
	/** The rest. */
	protected PaletteGroup controls;

	/** The transformation system. */
	protected Module transformationSystem;
	
	public GraphicalPaletteRoot(Module transformationSystem) {
		this.transformationSystem = transformationSystem;
		
		graphTools = createGraphPalette();
		add(1,graphTools);
		
		controls = new PaletteGroup("Controls");
		add(controls);
		
		addToolEntry(controls, "Node", "Create Node", 
				new TGGModelCreationFactory(TNode.class), 
				null, 
				null, 
				NodeCreationTool.class);
		
		addToolEntry(controls, "Edge", "Create Edge", 
			new TGGModelCreationFactory(Edge.class), 
			null, 
			null, 
			EdgeCreationTool.class);
		
		addToolEntry(controls, "Attribute", "Create Attribite",
				new TGGModelCreationFactory(Attribute.class),
				null, 
				null, 
				AttributeCreationTool.class);
	
	}
	
	/**
	 * Creates the graph palette.
	 *
	 * @return the palette group
	 */
	private PaletteGroup createGraphPalette() {
		PaletteGroup graphToolsGroup = new PaletteGroup("Controls");
		for (EPackage ePackage : transformationSystem.getImports()) {
			final PaletteStack marqueeStack = new PaletteStack("", "", null); //$NON-NLS-1$
			for (EClass eClass : getNodeTypesOfEPackage(ePackage)) {
//				final ToolEntry entry = new ToolEntry(eClass.getName(),
//						"Create " + eClass.getName(), IconUtil.getDescriptor(
//								"node18.png"), IconUtil.getDescriptor(
//								"node25.png"), CreationTool.class) {
//				};
				final ToolEntry entry = new ToolEntry(eClass.getName(),
						"Create " + eClass.getName(), null, null, CreationTool.class) {
				};
				entry.setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY,
						new TGGModelCreationFactory(Node.class, eClass));
				entry.setUserModificationPermission(PERMISSION_NO_MODIFICATION);
				marqueeStack.add(entry);
			}
			graphToolsGroup.add(marqueeStack);

		}
		return graphToolsGroup;
	}
	
	/**
	 * Gets the node types von e package.
	 *
	 * @param ePackage the e package
	 * @return the node types von e package
	 */
	protected List<EClass> getNodeTypesOfEPackage(EPackage ePackage){
		return NodeTypes.getNodeTypesOfEPackage(ePackage,false);
	}
	
	/**
	 * Refresh graph tools group.
	 */
	public void refreshGraphToolsGroup() {
		PaletteGroup tempGraphTools = createGraphPalette();
		remove(graphTools);
		graphTools = tempGraphTools;
		add(1,graphTools);
	}
}

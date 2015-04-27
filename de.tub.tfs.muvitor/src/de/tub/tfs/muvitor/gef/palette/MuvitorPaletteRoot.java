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
package de.tub.tfs.muvitor.gef.palette;

import org.eclipse.gef.palette.MarqueeToolEntry;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteEntry;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.gef.tools.MarqueeSelectionTool;
import org.eclipse.gef.tools.TargetingTool;
import org.eclipse.jface.resource.ImageDescriptor;

/**
 * A default palette root that can be used in graphical views. It features a
 * flexible convenient method to add new tool entries to palette groups.
 * 
 * @author Tony Modica
 * 
 */
public class MuvitorPaletteRoot extends PaletteRoot {

	final protected PaletteGroup defaultPaletteGroup;

	public MuvitorPaletteRoot() {
		// a group of default control tools
		defaultPaletteGroup = new PaletteGroup("Controls");
		add(defaultPaletteGroup);

		// the selection tool
		final ToolEntry tool = new PanningSelectionToolEntry();
		defaultPaletteGroup.add(tool);

		// use selection tool as default entry
		setDefaultEntry(tool);
		
		// all flavors of marquee selection tools
		final PaletteStack marqueeStack = new PaletteStack("Marquee Tools", "Marquee tools with different behaviors", null); //$NON-NLS-1$
		marqueeStack.add(new MarqueeToolEntry("Nodes only"));
		MarqueeToolEntry marquee = new MarqueeToolEntry("Connections only");
		marquee.getToolProperty("pinned");
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR, Integer.valueOf(MarqueeSelectionTool.BEHAVIOR_CONNECTIONS_TOUCHED));
		marqueeStack.add(marquee);
		marquee = new MarqueeToolEntry("Nodes and connections");
		marquee.setToolProperty(MarqueeSelectionTool.PROPERTY_MARQUEE_BEHAVIOR,
		// for compatibility to Eclipse prior to 3.7
		// BEHAVIOR_NODES_AND_CONNECTIONS ==
		// BEHAVIOR_NODES_CONTAINED_AND_RELATED_CONNECTIONS
				Integer.valueOf(3));
		marqueeStack.add(marquee);
		marqueeStack.setUserModificationPermission(PaletteEntry.PERMISSION_NO_MODIFICATION);
		defaultPaletteGroup.add(marqueeStack);
	}

	/**
	 * A convenience method for simply creating tool entries to a palette group.
	 * With this you may specify a tool class different to the GEF standard
	 * CreationTool.class, e.g. if you want to override its command execution
	 * behavior to let the tool show some dialogs before creating or executing a
	 * command.
	 * 
	 * @param group
	 *            the palette group the tool entry will be added to
	 * @param label
	 *            the label of the new tool entry
	 * @param description
	 *            the description of the new tool entry
	 * @param factory
	 *            the creation factory the tool entry will use
	 * @param iconSmall
	 *            a small icon for the tool entry
	 * @param iconLarge
	 *            a large icon for the tool entry
	 * @param creationToolClass
	 *            the class of the creation tool
	 * @return the newly added tool entry for further custom modifications
	 */
	protected ToolEntry addToolEntry(final PaletteContainer group, final String label, final String description, final CreationFactory factory,
			final ImageDescriptor iconSmall, final ImageDescriptor iconLarge, final Class<? extends TargetingTool> creationToolClass) {
		// we do not use CreationToolEntry, because we want to specify our own
		// customized tools
		final ToolEntry entry = new ToolEntry(label, description, iconSmall, iconLarge, creationToolClass) {
		};
		entry.setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY, factory);
		entry.setUserModificationPermission(PERMISSION_NO_MODIFICATION);
		group.add(entry);
		return entry;
	}
}

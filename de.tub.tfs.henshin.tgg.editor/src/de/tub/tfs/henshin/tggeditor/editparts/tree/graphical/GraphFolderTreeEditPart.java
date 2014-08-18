/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.tree.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;

import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * EditPart for the graph folder in the tree editor.
 */

public class GraphFolderTreeEditPart extends AdapterTreeEditPart<GraphFolder> {
	private List<Graph> graphs = new ArrayList<Graph>();
	
	public GraphFolderTreeEditPart(GraphFolder model){
		super(model);
		for (Graph g : model.getGraphs()) {
			if (!g.getName().startsWith("("))
				graphs.add(g);
		}
	}

	@Override
	protected String getText() {
		return "Graphs";
	}

	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("graphFolder18.png");
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	protected List<Graph> getModelChildren() {
		return this.graphs;
	}
	
	@Override
	protected void performOpen() {
		if (this.widget instanceof TreeItem) {
			TreeItem item = (TreeItem) this.widget;
			item.setExpanded(!item.getExpanded());	
		}	
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		graphs.clear();
		for (Graph g : getCastedModel().getGraphs()) {
			if (!g.getName().startsWith("("))
				graphs.add(g);
		}
		refresh();
		super.notifyChanged(notification);
	}

}

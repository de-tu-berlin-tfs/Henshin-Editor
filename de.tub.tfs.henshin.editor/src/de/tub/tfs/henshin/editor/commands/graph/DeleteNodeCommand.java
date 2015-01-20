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

import java.util.Iterator;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * A {@link Command command} to delete {@link Node nodes}.
 * 
 * <p>
 * All incoming an outgoing {@link Edge edges} of the given {@link Node node}
 * will also be removed.
 * </p>
 */
public class DeleteNodeCommand extends CompoundCommand {

	
	
	private Node node;
	private boolean skipCheck = false;

	public DeleteNodeCommand(Node node,boolean skipCheck) {
		this(node);
		this.skipCheck = skipCheck;
	}
	/**
	 * Constructs a {@link DeleteNodeCommand} to delete a given {@link Node
	 * node}.
	 * 
	 * @param node
	 *            the {@link Node node} to delete
	 */
	public DeleteNodeCommand(Node node) {
		this.node = node;
		
		Iterator<Edge> iter = node.getIncoming().iterator();

		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}

		iter = node.getOutgoing().iterator();

		while (iter.hasNext()) {
			Edge edge = iter.next();
			add(new DeleteEdgeCommand(edge));
		}

		Layout layout = HenshinLayoutUtil.INSTANCE.getLayout(node);

		add(new SimpleDeleteEObjectCommand(node));

		if (layout != null) {
			add(new SimpleDeleteEObjectCommand(layout));
		}
		
		if (this.node.getGraph() != null && node.getGraph().getRule() != null && !this.node.getGraph().getRule().getAllMultiRules().isEmpty()){
			for (Rule multiRule : this.node.getGraph().getRule().getMultiRules()) {
				for (Mapping m : multiRule.getMultiMappings()) {
					if (m.getOrigin() != null && m.getOrigin().equals(node)){
						add(new DeleteNodeCommand(m.getImage(),true));
						add(new SimpleDeleteEObjectCommand(m));
						
					}
				}
			}
		}
	}
	
	@Override
	public boolean canExecute() {
		if (skipCheck)
			return true;

		if (HenshinLayoutUtil.INSTANCE.isMultiNode(node)){
			return false;
		}
			
		return super.canExecute();
	}
}

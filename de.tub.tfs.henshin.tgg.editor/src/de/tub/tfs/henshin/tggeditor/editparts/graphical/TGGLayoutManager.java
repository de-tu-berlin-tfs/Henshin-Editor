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
package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;

public class TGGLayoutManager extends FreeformLayout {
	
	@Override
	public void layout(IFigure container) {
		super.layout(container);
		for (Object fig : container.getChildren()) {
			if (fig instanceof NodeFigure){
				NodeFigure figure = (NodeFigure) fig;
				TNode node = figure.getNode();
				TripleGraph graph = (TripleGraph) node.getGraph();
				if (graph == null)
					continue;
				int dividerSrcToCor = graph.getDividerSC_X();
				int dividerCorToTar = graph.getDividerCT_X();
				
				if (NodeUtil.guessTripleComponent(node) == TripleComponent.SOURCE){
					if (node.getX() + figure.getBounds().width > dividerSrcToCor){
						node.setX(dividerSrcToCor - 5 - figure.getBounds().width);
					}
				} else if (NodeUtil.guessTripleComponent(node) == TripleComponent.CORRESPONDENCE){
					if (node.getX() < dividerSrcToCor){
						node.setX(dividerSrcToCor + 5);
					} 
					if (node.getX() + figure.getBounds().width > dividerCorToTar) {
						if (dividerSrcToCor + 10 + figure.getBounds().width > dividerCorToTar){
							// do nothing if there's not enough space
						} else {
							node.setX(dividerCorToTar - 5 - figure.getBounds().width);
						}
					}
				} else if (NodeUtil.guessTripleComponent(node) == TripleComponent.TARGET){
					if (node.getX() < dividerCorToTar){
						node.setX(dividerCorToTar + 5); 
					}
				}
			}
		}
	}
}

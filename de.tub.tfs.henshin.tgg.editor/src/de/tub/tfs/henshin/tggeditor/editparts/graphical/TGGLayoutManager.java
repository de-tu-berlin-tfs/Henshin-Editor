package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LayoutManager;
import org.eclipse.draw2d.geometry.Dimension;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;

public class TGGLayoutManager extends FreeformLayout {
	
	@Override
	public void layout(IFigure container) {
		for (Object fig : container.getChildren()) {
			if (fig instanceof NodeFigure){
				NodeFigure figure = (NodeFigure) fig;
				TNode node = figure.getNode();
				TripleGraph graph = (TripleGraph) node.getGraph();
				int dividerSrcToCor = graph.getDividerSC_X();
				int dividerCorToTar = graph.getDividerCT_X();
				
				if (NodeUtil.isSourceNode(node)){
					if (node.getX() + figure.getBounds().width > dividerSrcToCor){
						node.setX(dividerSrcToCor - 5 - figure.getBounds().width);
					}
				} else if (NodeUtil.isCorrespondenceNode(node)){
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
				} else if (NodeUtil.isTargetNode(node)){
					if (node.getX() > dividerCorToTar){
						node.setX(dividerCorToTar + 5); 
					}
				}
			}
		}
	}
}

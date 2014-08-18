package de.tub.tfs.henshin.tggeditor.util;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.interpreter.EGraph;
import org.eclipse.emf.henshin.interpreter.matching.constraints.DomainSlot;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.tgg.GraphLayout;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.impl.TggHenshinEGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes.NodeGraphType;
import de.tub.tfs.henshin.tgg.interpreter.util.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * The Class Nodeutil
 * Holds many helpful static functions for operating on nodes.
 */
public class GraphicalNodeUtil {
	
	private static final String EXCEPTION_NODE_IS_NOT_TNODE = "Triple component of node cannot be determined, because it is not of type TNode (node in a triple graph).";

	
	/**
	 * Gets the layout system which holds the given EObject
	 * 
	 * @param eobject the eobject
	 * @return the layout system
	 */
	public static TGG getLayoutSystem(EObject eobject) {
		
		if(!(IDUtil.getHostEditor(eobject) instanceof TreeEditor)) {
			return null;
		}
		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor(eobject);
		if(editor == null) {ExceptionUtil.error("Tree editor is missing for retrieving the layout model."); return null;}
		return editor.getLayout();
	}


	

	
	/**
	 * find all nodeLayouts to specific EPackage
	 * @param tgg the layoutSystem
	 * @param p EPackage for source, target oder correspondence
	 * @return a set node layouts with all nodeLayouts belongs to EPackage p
	 */
	public static Set<NodeLayout> getNodeLayouts(TGG tgg, EPackage p) {
		Set<NodeLayout> set = new HashSet<NodeLayout>();
		EList<NodeLayout> l = tgg.getNodelayouts();
		if (p != null) {
			for (NodeLayout nl : l) {
				if (p.eContents().contains(nl.getNode().getType())) {
					set.add(nl);
				}
			}
		}
		return set;
	}

	
	



	

	
	/**
	 * correct position of a node (in nodeFigure and nodeLayout) in relation to its NodeGraphType
	 * and the divider positions, this correction generates no notifications
	 * @param nodeFigure the figure of the node
	 */
	public static void correctNodeFigurePosition(NodeFigure nodeFigure) {
		if(nodeFigure == null)return;
		TNode node = nodeFigure.getNode();
		if(node == null )
			return;
		if (node.getGraph()==null) return;


		
		TripleGraph tripleGraph =(TripleGraph) node.getGraph();
		int divSCx = tripleGraph.getDividerSC_X();
		int divCTx = tripleGraph.getDividerCT_X();
		int width = nodeFigure.getBounds().width;
		int leftX = node.getX();
		int correctionValue = 0;
		TripleComponent type = NodeUtil.guessTripleComponent(node);
		
		if (type == TripleComponent.SOURCE) {
			if (leftX+width >= divSCx)
				correctionValue = divSCx - leftX - width - 5;
		}
		else if (type == TripleComponent.CORRESPONDENCE) {
			if (leftX < divSCx)
				correctionValue = divSCx - leftX + 5;
			else if (leftX+width > divCTx)
				correctionValue = divCTx - leftX - width - 5;
			// if node does not fit between the dividers: do not correct - dividers need to be moved manually
			if (leftX + correctionValue < divSCx)
				correctionValue=divSCx-leftX;
		}
		else if (type == TripleComponent.TARGET) {
			if (leftX <= divCTx)
				correctionValue = divCTx - leftX + 5;
		}
		if(correctionValue != 0) {
		  node.setX(leftX + correctionValue);
		  nodeFigure.setLocation(new Point(node.getX(), node.getY()));
		}
	}


	
}

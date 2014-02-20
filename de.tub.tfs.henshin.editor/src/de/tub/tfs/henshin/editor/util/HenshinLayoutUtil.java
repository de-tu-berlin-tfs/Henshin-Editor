/**
 * HenshinLayoutUtil.java
 *
 * Created 21.12.2011 - 19:27:15
 */
package de.tub.tfs.henshin.editor.util;

import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * @author nam
 * 
 */
public final class HenshinLayoutUtil {
	/**
	 * The singleton instance.
	 */
	public static HenshinLayoutUtil INSTANCE = new HenshinLayoutUtil();

	private static final double ESP = 0.007899;

	/**
	 * Private, since singleton class.
	 */
	private HenshinLayoutUtil() {
	}

	/**
	 * Convenient method to get the root {@link LayoutSystem layout system}.
	 * 
	 * @param model
	 *            an {@link EObject object} contained in a Henshin
	 *            {@link HenshinTreeEditor tree editor}.
	 * 
	 * @return the layout system
	 */
	public LayoutSystem getLayoutSystem(EObject model) {
		EObject root = EcoreUtil.getRootContainer(model);

		if (root instanceof LayoutSystem) {
			return (LayoutSystem) root;
		} else if (root instanceof Module
				|| root instanceof FlowControlSystem) {
			HenshinTreeEditor editor = (HenshinTreeEditor) IDUtil
					.getHostEditor(model);

			if (editor != null) {
				return editor.getModelRoot(LayoutSystem.class);
			}
		}

		return null;
	}

	/**
	 * @param node
	 * @return
	 */
	public NodeLayout getLayout(Node node) {
		return getLayout(node, HenshinLayoutPackage.Literals.LAYOUT__MODEL);
	}

	/**
	 * @param element
	 * @return
	 */
	public FlowElementLayout getLayout(FlowElement element) {
		return getLayout(element, HenshinLayoutPackage.Literals.LAYOUT__MODEL);
	}

	/**
	 * @param color
	 * @return
	 */
	public int getMappingNumber(Rule r,Node n) {
		return MappingUtil.convertMappings(r, n);
		
		//return 0;
	}
	
	

	/**
	 * @param g
	 * @param startX
	 * @param startY
	 * @return
	 */
	public Point calcNodeInsertPosition(EObject g, int startX, int startY) {
		Assert.isLegal(g != null);

		Point pos = Point.SINGLETON.setLocation(startX, startY);
		Point tmp = new Point();
		LayoutSystem root = getLayoutSystem(g);

		for (Layout l : root.getLayouts()) {
			if (g.eContents().contains(l.getModel())) {
				tmp.setLocation(l.getX(), l.getY());

				if (pos.getDistance(tmp) <= ESP) {
					pos.translate(5, 5);
				}
			}
		}

		return pos.getCopy();
	}


	/**
	 * @param model
	 * @param modelFeature
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T extends EObject, K extends EObject> K getLayout(T model,
			EStructuralFeature modelFeature) {
		LayoutSystem layoutRoot = getLayoutSystem(model);

		if (layoutRoot != null) {
			List<EObject> refs = ModelUtil.getReferences(model, layoutRoot,
					modelFeature);

			if (!refs.isEmpty()) {
				return (K) refs.get(0);
			} else {
				Layout l = null;
				if (model instanceof Node){
					l = HenshinLayoutFactory.eINSTANCE.createNodeLayout();
					
				} else if (model instanceof FlowElement){
					l = HenshinLayoutFactory.eINSTANCE.createFlowElementLayout();
				}
				l.eSet(modelFeature, model);
				layoutRoot.getLayouts().add(l);
				return (K) l;
			}
		}

		return null;
	}

//	public boolean isMultiNode(Node node) {
//		//return false;
//		return belongsToMultiRule(node)  && hasOriginInKernelRule(node); // && node.getGraph().getContainerRule().getOriginInKernelRule(node) == null;
//	}

	public boolean isMultiNode(Node node){
		if (node == null)
			return false;
		Rule rule=null;
		if (node.getGraph() != null && (node.getGraph().isLhs() || node.getGraph().isRhs()))
		// multi rule
			rule = (Rule) node.getGraph().getRule();
		return isMultiNode(node,rule);
	}
	
	public boolean isMultiNode(Node node, Rule rule) {
		if (node == null || rule == null)
			return false;
		if (rule.isMultiRule()
				&& NodeUtil.nodeIsMapped(node, rule.getMultiMappings()))
			return true;
		return false;
	}
	
	public boolean belongsToMultiRule(Node node){
		if (node == null)
			return false;
		return node.getGraph() != null && (node.getGraph().isLhs() || node.getGraph().isRhs()) && node.getGraph().getRule().isMultiRule();
	}

	
	public boolean hasOriginInKernelRule(Node node){
		
		return false; 
//				belongsToMultiRule(node) && node.getGraph().getContainerRule().getOriginInKernelRule(node) != null;
	}

	
	/*public boolean isKernelNode(Node node) {
		return node.getGraph() != null && (node.getGraph().isLhs() || node.getGraph().isRhs()) && !isMultiNode(node) && !HenshinMultiRuleUtil.getDependentNodes(node).isEmpty();
	}
	
	public boolean hasNoDependentNodes(Node node) {
		return node.getGraph() != null && (node.getGraph().isLhs() || node.getGraph().isRhs()) && !isMultiNode(node) && HenshinMultiRuleUtil.getDependentNodes(node).isEmpty();
	}*/
}

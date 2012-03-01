/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.LayoutSystem;

/**
 * @author nam
 * 
 */
public class CreateFlowElementCommand<T extends FlowElement> extends
		CompoundCommand {

	private T model;

	private FlowElementLayout layoutModel;

	/**
	 * @param e
	 * @param container
	 * @param eFeatureId
	 * @param location
	 * @param layoutRoot
	 */
	public CreateFlowElementCommand(T e, EObject container, int eFeatureId,
			Point location, LayoutSystem layoutRoot) {
		super("Creating Flow Element");

		model = e;

		EStructuralFeature eFeature = container.eClass().getEStructuralFeature(
				eFeatureId);

		if (eFeature != null) {
			layoutModel = HenshinLayoutFactory.eINSTANCE
					.createFlowElementLayout();

			layoutModel.setX(location.x);
			layoutModel.setY(location.y);
			layoutModel.setModel(e);

			add(new SimpleAddEObjectCommand<LayoutSystem, FlowElementLayout>(
					layoutModel,
					HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS,
					layoutRoot));

			add(new SimpleAddEObjectCommand<EObject, FlowElement>(e, eFeature,
					container));
		}
	}

	/**
	 * @param newElement
	 * @param parent
	 * @param location
	 */
	public CreateFlowElementCommand(T newElement, FlowDiagram parent,
			Point location) {
		this(newElement, parent, location, HenshinLayoutUtil.INSTANCE
				.getLayoutSystem(parent));
	}

	/**
	 * @param e
	 * @param container
	 * @param eFeatureId
	 * @param location
	 */
	public CreateFlowElementCommand(T e, EObject container, int eFeatureId,
			Point location) {
		this(e, container, eFeatureId, location, HenshinLayoutUtil.INSTANCE
				.getLayoutSystem(container));
	}

	/**
	 * @param newElement
	 * @param parent
	 * @param location
	 */
	public CreateFlowElementCommand(T newElement, FlowDiagram parent,
			Point location, LayoutSystem layoutRoot) {
		this(newElement, parent, FlowControlPackage.FLOW_DIAGRAM__ELEMENTS,
				location, layoutRoot);

		newElement.setDiagram(parent);
	}

	/**
	 * @return the model
	 */
	public T getModel() {
		return model;
	}

	/**
	 * @return the layoutModel
	 */
	public FlowElementLayout getLayoutModel() {
		return layoutModel;
	}
}

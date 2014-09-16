/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.flow_diagram;

import org.eclipse.emf.ecore.EStructuralFeature;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class CreateTransitionCommand extends
		SimpleAddEObjectCommand<FlowDiagram, Transition> {

	/**
     * 
     */
	private FlowElement src;

	/**
     * 
     */
	private FlowElement target;

	/**
     * 
     */
	private EStructuralFeature outGoingFeature;

	/**
	 * @param newTransition
	 * @param src
	 * @param target
	 * @param diagram
	 * @param outGoingFeature
	 */
	public CreateTransitionCommand(Transition newTransition, FlowElement src,
			FlowElement target, FlowDiagram diagram,
			EStructuralFeature outGoingFeature) {

		super(newTransition,
				FlowControlPackage.Literals.FLOW_DIAGRAM__TRANSITIONS, diagram);

		this.src = src;
		this.target = target;
		this.outGoingFeature = outGoingFeature;
	}

	/**
	 * @param newTransition
	 * @param diagram
	 */
	public CreateTransitionCommand(Transition newTransition, FlowElement src,
			FlowElement target, FlowDiagram diagram) {
		this(newTransition, src, target, diagram,
				FlowControlPackage.Literals.FLOW_ELEMENT__OUT);
	}

	/**
	 * @param src
	 * @param target
	 * @param diagram
	 */
	public CreateTransitionCommand(FlowElement src, FlowElement target,
			FlowDiagram diagram) {
		this(FlowControlFactory.eINSTANCE.createTransition(), src, target,
				diagram, FlowControlPackage.Literals.FLOW_ELEMENT__OUT);
	}

	/**
	 * @param target
	 */
	public void setTarget(FlowElement target) {
		this.target = target;
	}

	/**
	 * @param outGoingFeature
	 *            the outGoingFeature to set
	 */
	public void setOutGoingFeature(EStructuralFeature outGoingFeature) {
		this.outGoingFeature = outGoingFeature;
	}

	/**
	 * @return
	 */
	public synchronized FlowElement getSrc() {
		return src;
	}

	/**
	 * @return
	 */
	public synchronized FlowElement getTarget() {
		return target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();

		newObject.setNext(target);
		newObject.setPrevous(src);

		src.eSet(outGoingFeature, newObject);
		target.getIn().add(newObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		newObject.setNext(null);
		newObject.setPrevous(null);

		src.eSet(outGoingFeature, null);
		target.getIn().remove(newObject);

		super.undo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return src != null && target != null && super.canExecute();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return src != null && target != null && super.canUndo();
	}
}

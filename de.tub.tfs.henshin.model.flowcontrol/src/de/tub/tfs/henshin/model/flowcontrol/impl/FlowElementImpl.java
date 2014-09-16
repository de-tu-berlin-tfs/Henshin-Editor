/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Flow Element</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl#getOut <em>Out</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl#getIn <em>In</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowElementImpl#getDiagram <em>Diagram</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class FlowElementImpl extends EObjectImpl implements
		FlowElement {
	/**
	 * The cached value of the '{@link #getOut() <em>Out</em>}' reference. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getOut()
	 * @generated
	 * @ordered
	 */
	protected Transition out;

	/**
	 * The cached value of the '{@link #getIn() <em>In</em>}' reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getIn()
	 * @generated
	 * @ordered
	 */
	protected EList<Transition> in;

	/**
	 * The cached value of the '{@link #getDiagram() <em>Diagram</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getDiagram()
	 * @generated
	 * @ordered
	 */
	protected FlowDiagram diagram;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected FlowElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return FlowControlPackage.Literals.FLOW_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transition getOut() {
		if (out != null && out.eIsProxy()) {
			InternalEObject oldOut = (InternalEObject)out;
			out = (Transition)eResolveProxy(oldOut);
			if (out != oldOut) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.FLOW_ELEMENT__OUT, oldOut, out));
			}
		}
		return out;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Transition basicGetOut() {
		return out;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setOut(Transition newOut) {
		Transition oldOut = out;
		out = newOut;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_ELEMENT__OUT, oldOut, out));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Transition> getIn() {
		if (in == null) {
			in = new EObjectResolvingEList<Transition>(Transition.class, this, FlowControlPackage.FLOW_ELEMENT__IN);
		}
		return in;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FlowDiagram getDiagram() {
		if (diagram != null && diagram.eIsProxy()) {
			InternalEObject oldDiagram = (InternalEObject)diagram;
			diagram = (FlowDiagram)eResolveProxy(oldDiagram);
			if (diagram != oldDiagram) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.FLOW_ELEMENT__DIAGRAM, oldDiagram, diagram));
			}
		}
		return diagram;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public FlowDiagram basicGetDiagram() {
		return diagram;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public void setDiagram(FlowDiagram newDiagram) {
		FlowDiagram oldDiagram = diagram;
		diagram = newDiagram;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_ELEMENT__DIAGRAM, oldDiagram, diagram));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public EList<Transition> getOutGoings() {
		BasicEList<Transition> outs = new BasicEList<Transition>();

		if (getOut() != null) {
			outs.add(getOut());
		}

		return outs;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.FLOW_ELEMENT__OUT:
				if (resolve) return getOut();
				return basicGetOut();
			case FlowControlPackage.FLOW_ELEMENT__IN:
				return getIn();
			case FlowControlPackage.FLOW_ELEMENT__DIAGRAM:
				if (resolve) return getDiagram();
				return basicGetDiagram();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FlowControlPackage.FLOW_ELEMENT__OUT:
				setOut((Transition)newValue);
				return;
			case FlowControlPackage.FLOW_ELEMENT__IN:
				getIn().clear();
				getIn().addAll((Collection<? extends Transition>)newValue);
				return;
			case FlowControlPackage.FLOW_ELEMENT__DIAGRAM:
				setDiagram((FlowDiagram)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case FlowControlPackage.FLOW_ELEMENT__OUT:
				setOut((Transition)null);
				return;
			case FlowControlPackage.FLOW_ELEMENT__IN:
				getIn().clear();
				return;
			case FlowControlPackage.FLOW_ELEMENT__DIAGRAM:
				setDiagram((FlowDiagram)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FlowControlPackage.FLOW_ELEMENT__OUT:
				return out != null;
			case FlowControlPackage.FLOW_ELEMENT__IN:
				return in != null && !in.isEmpty();
			case FlowControlPackage.FLOW_ELEMENT__DIAGRAM:
				return diagram != null;
		}
		return super.eIsSet(featureID);
	}

} // FlowElementImpl

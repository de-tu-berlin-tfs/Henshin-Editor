/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.henshin.model.impl.NamedElementImpl;

import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Flow Diagram</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getElements <em>Elements</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getParameterMappings <em>Parameter Mappings</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getStart <em>Start</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#getEnd <em>End</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#isStrict <em>Strict</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.FlowDiagramImpl#isRollback <em>Rollback</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FlowDiagramImpl extends NamedElementImpl implements FlowDiagram {
        /**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
        protected EList<Parameter> parameters;

        /**
	 * The cached value of the '{@link #getElements() <em>Elements</em>}' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getElements()
	 * @generated
	 * @ordered
	 */
        protected EList<FlowElement> elements;

        /**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
        protected EList<Transition> transitions;

        /**
	 * The cached value of the '{@link #getParameterMappings() <em>Parameter Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getParameterMappings()
	 * @generated
	 * @ordered
	 */
        protected EList<ParameterMapping> parameterMappings;

        /**
	 * The cached value of the '{@link #getStart() <em>Start</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getStart()
	 * @generated
	 * @ordered
	 */
        protected Start start;

        /**
	 * The cached value of the '{@link #getEnd() <em>End</em>}' reference.
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @see #getEnd()
	 * @generated
	 * @ordered
	 */
        protected End end;

        /**
	 * The default value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_EDEFAULT = false;

								/**
	 * The cached value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected boolean strict = STRICT_EDEFAULT;

								/**
	 * The default value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected static final boolean ROLLBACK_EDEFAULT = false;

								/**
	 * The cached value of the '{@link #isRollback() <em>Rollback</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRollback()
	 * @generated
	 * @ordered
	 */
	protected boolean rollback = ROLLBACK_EDEFAULT;

								/**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        protected FlowDiagramImpl() {
		super();
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        protected EClass eStaticClass() {
		return FlowControlPackage.Literals.FLOW_DIAGRAM;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, FlowControlPackage.FLOW_DIAGRAM__PARAMETERS);
		}
		return parameters;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EList<FlowElement> getElements() {
		if (elements == null) {
			elements = new EObjectContainmentEList<FlowElement>(FlowElement.class, this, FlowControlPackage.FLOW_DIAGRAM__ELEMENTS);
		}
		return elements;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EList<Transition> getTransitions() {
		if (transitions == null) {
			transitions = new EObjectContainmentEList<Transition>(Transition.class, this, FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS);
		}
		return transitions;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EList<ParameterMapping> getParameterMappings() {
		if (parameterMappings == null) {
			parameterMappings = new EObjectContainmentEList<ParameterMapping>(ParameterMapping.class, this, FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS);
		}
		return parameterMappings;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Start getStart() {
		if (start != null && start.eIsProxy()) {
			InternalEObject oldStart = (InternalEObject)start;
			start = (Start)eResolveProxy(oldStart);
			if (start != oldStart) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.FLOW_DIAGRAM__START, oldStart, start));
			}
		}
		return start;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public Start basicGetStart() {
		return start;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setStart(Start newStart) {
		Start oldStart = start;
		start = newStart;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_DIAGRAM__START, oldStart, start));
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public End getEnd() {
		if (end != null && end.eIsProxy()) {
			InternalEObject oldEnd = (InternalEObject)end;
			end = (End)eResolveProxy(oldEnd);
			if (end != oldEnd) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.FLOW_DIAGRAM__END, oldEnd, end));
			}
		}
		return end;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public End basicGetEnd() {
		return end;
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public void setEnd(End newEnd) {
		End oldEnd = end;
		end = newEnd;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_DIAGRAM__END, oldEnd, end));
	}

        /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStrict() {
		return strict;
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrict(boolean newStrict) {
		boolean oldStrict = strict;
		strict = newStrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_DIAGRAM__STRICT, oldStrict, strict));
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isRollback() {
		return rollback;
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRollback(boolean newRollback) {
		boolean oldRollback = rollback;
		rollback = newRollback;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.FLOW_DIAGRAM__ROLLBACK, oldRollback, rollback));
	}

								/**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
				return ((InternalEList<?>)getElements()).basicRemove(otherEnd, msgs);
			case FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS:
				return ((InternalEList<?>)getTransitions()).basicRemove(otherEnd, msgs);
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS:
				return ((InternalEList<?>)getParameterMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS:
				return getParameters();
			case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
				return getElements();
			case FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS:
				return getTransitions();
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS:
				return getParameterMappings();
			case FlowControlPackage.FLOW_DIAGRAM__START:
				if (resolve) return getStart();
				return basicGetStart();
			case FlowControlPackage.FLOW_DIAGRAM__END:
				if (resolve) return getEnd();
				return basicGetEnd();
			case FlowControlPackage.FLOW_DIAGRAM__STRICT:
				return isStrict();
			case FlowControlPackage.FLOW_DIAGRAM__ROLLBACK:
				return isRollback();
		}
		return super.eGet(featureID, resolve, coreType);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @SuppressWarnings("unchecked")
        @Override
        public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
				getElements().clear();
				getElements().addAll((Collection<? extends FlowElement>)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS:
				getTransitions().clear();
				getTransitions().addAll((Collection<? extends Transition>)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				getParameterMappings().addAll((Collection<? extends ParameterMapping>)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__START:
				setStart((Start)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__END:
				setEnd((End)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__STRICT:
				setStrict((Boolean)newValue);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__ROLLBACK:
				setRollback((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public void eUnset(int featureID) {
		switch (featureID) {
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS:
				getParameters().clear();
				return;
			case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
				getElements().clear();
				return;
			case FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS:
				getTransitions().clear();
				return;
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				return;
			case FlowControlPackage.FLOW_DIAGRAM__START:
				setStart((Start)null);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__END:
				setEnd((End)null);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__STRICT:
				setStrict(STRICT_EDEFAULT);
				return;
			case FlowControlPackage.FLOW_DIAGRAM__ROLLBACK:
				setRollback(ROLLBACK_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public boolean eIsSet(int featureID) {
		switch (featureID) {
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case FlowControlPackage.FLOW_DIAGRAM__ELEMENTS:
				return elements != null && !elements.isEmpty();
			case FlowControlPackage.FLOW_DIAGRAM__TRANSITIONS:
				return transitions != null && !transitions.isEmpty();
			case FlowControlPackage.FLOW_DIAGRAM__PARAMETER_MAPPINGS:
				return parameterMappings != null && !parameterMappings.isEmpty();
			case FlowControlPackage.FLOW_DIAGRAM__START:
				return start != null;
			case FlowControlPackage.FLOW_DIAGRAM__END:
				return end != null;
			case FlowControlPackage.FLOW_DIAGRAM__STRICT:
				return strict != STRICT_EDEFAULT;
			case FlowControlPackage.FLOW_DIAGRAM__ROLLBACK:
				return rollback != ROLLBACK_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ParameterProvider.class) {
			switch (derivedFeatureID) {
				case FlowControlPackage.FLOW_DIAGRAM__PARAMETERS: return FlowControlPackage.PARAMETER_PROVIDER__PARAMETERS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

        /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        @Override
        public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ParameterProvider.class) {
			switch (baseFeatureID) {
				case FlowControlPackage.PARAMETER_PROVIDER__PARAMETERS: return FlowControlPackage.FLOW_DIAGRAM__PARAMETERS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

								/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (strict: ");
		result.append(strict);
		result.append(", rollback: ");
		result.append(rollback);
		result.append(')');
		return result.toString();
	}

} //FlowDiagramImpl

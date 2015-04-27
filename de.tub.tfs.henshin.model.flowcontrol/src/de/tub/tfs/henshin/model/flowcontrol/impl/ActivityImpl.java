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
import org.eclipse.emf.henshin.model.NamedElement;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Activity</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl#getContent <em>Content</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ActivityImpl#getParameterMappings <em>Parameter Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ActivityImpl extends FlowElementImpl implements Activity {
    /**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
    protected EList<Parameter> parameters;

    /**
	 * The cached value of the '{@link #getContent() <em>Content</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getContent()
	 * @generated
	 * @ordered
	 */
    protected NamedElement content;

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
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ActivityImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> 
	 * <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FlowControlPackage.Literals.ACTIVITY;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public EList<Parameter> getParameters() {
		if (parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, FlowControlPackage.ACTIVITY__PARAMETERS);
		}
		return parameters;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NamedElement getContent() {
		if (content != null && content.eIsProxy()) {
			InternalEObject oldContent = (InternalEObject)content;
			content = (NamedElement)eResolveProxy(oldContent);
			if (content != oldContent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.ACTIVITY__CONTENT, oldContent, content));
			}
		}
		return content;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public NamedElement basicGetContent() {
		return content;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void setContent(NamedElement newContent) {
		NamedElement oldContent = content;
		content = newContent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.ACTIVITY__CONTENT, oldContent, content));
	}

    /**
	 * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
	 * @generated
	 */
        public EList<ParameterMapping> getParameterMappings() {
		if (parameterMappings == null) {
			parameterMappings = new EObjectContainmentEList<ParameterMapping>(ParameterMapping.class, this, FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS);
		}
		return parameterMappings;
	}

/**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isNested() {
	return eContainer() instanceof CompoundActivity;
    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd,
	    int featureID, NotificationChain msgs) {
		switch (featureID) {
			case FlowControlPackage.ACTIVITY__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS:
				return ((InternalEList<?>)getParameterMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.ACTIVITY__PARAMETERS:
				return getParameters();
			case FlowControlPackage.ACTIVITY__CONTENT:
				if (resolve) return getContent();
				return basicGetContent();
			case FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS:
				return getParameterMappings();
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
			case FlowControlPackage.ACTIVITY__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>)newValue);
				return;
			case FlowControlPackage.ACTIVITY__CONTENT:
				setContent((NamedElement)newValue);
				return;
			case FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
				getParameterMappings().addAll((Collection<? extends ParameterMapping>)newValue);
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
			case FlowControlPackage.ACTIVITY__PARAMETERS:
				getParameters().clear();
				return;
			case FlowControlPackage.ACTIVITY__CONTENT:
				setContent((NamedElement)null);
				return;
			case FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS:
				getParameterMappings().clear();
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
			case FlowControlPackage.ACTIVITY__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case FlowControlPackage.ACTIVITY__CONTENT:
				return content != null;
			case FlowControlPackage.ACTIVITY__PARAMETER_MAPPINGS:
				return parameterMappings != null && !parameterMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == ParameterProvider.class) {
			switch (derivedFeatureID) {
				case FlowControlPackage.ACTIVITY__PARAMETERS: return FlowControlPackage.PARAMETER_PROVIDER__PARAMETERS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == ParameterProvider.class) {
			switch (baseFeatureID) {
				case FlowControlPackage.PARAMETER_PROVIDER__PARAMETERS: return FlowControlPackage.ACTIVITY__PARAMETERS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} // ActivityImpl

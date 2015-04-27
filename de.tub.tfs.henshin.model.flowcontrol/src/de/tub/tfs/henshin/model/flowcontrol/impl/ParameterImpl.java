/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.tub.tfs.henshin.model.flowcontrol.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.impl.NamedElementImpl;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.Parameter;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>Parameter</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link de.tub.tfs.henshin.model.flowcontrol.impl.ParameterImpl#getHenshinParameter <em>Henshin Parameter</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ParameterImpl extends NamedElementImpl implements Parameter {
    /**
	 * The cached value of the '{@link #getProvider() <em>Provider</em>}' reference.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getProvider()
	 * @generated
	 * @ordered
	 */
    protected ParameterProvider provider;

    /**
	 * The cached value of the '{@link #getHenshinParameter() <em>Henshin Parameter</em>}' reference.
	 * <!-- begin-user-doc --> <!--
     * end-user-doc -->
	 * @see #getHenshinParameter()
	 * @generated
	 * @ordered
	 */
    protected org.eclipse.emf.henshin.model.Parameter henshinParameter;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected ParameterImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return FlowControlPackage.Literals.PARAMETER;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public ParameterProvider getProvider() {
		if (provider != null && provider.eIsProxy()) {
			InternalEObject oldProvider = (InternalEObject)provider;
			provider = (ParameterProvider)eResolveProxy(oldProvider);
			if (provider != oldProvider) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.PARAMETER__PROVIDER, oldProvider, provider));
			}
		}
		return provider;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public ParameterProvider basicGetProvider() {
		return provider;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void setProvider(ParameterProvider newProvider) {
		ParameterProvider oldProvider = provider;
		provider = newProvider;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.PARAMETER__PROVIDER, oldProvider, provider));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public org.eclipse.emf.henshin.model.Parameter getHenshinParameter() {
		if (henshinParameter != null && henshinParameter.eIsProxy()) {
			InternalEObject oldHenshinParameter = (InternalEObject)henshinParameter;
			henshinParameter = (org.eclipse.emf.henshin.model.Parameter)eResolveProxy(oldHenshinParameter);
			if (henshinParameter != oldHenshinParameter) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, FlowControlPackage.PARAMETER__HENSHIN_PARAMETER, oldHenshinParameter, henshinParameter));
			}
		}
		return henshinParameter;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public org.eclipse.emf.henshin.model.Parameter basicGetHenshinParameter() {
		return henshinParameter;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    public void setHenshinParameter(
	    org.eclipse.emf.henshin.model.Parameter newHenshinParameter) {
		org.eclipse.emf.henshin.model.Parameter oldHenshinParameter = henshinParameter;
		henshinParameter = newHenshinParameter;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, FlowControlPackage.PARAMETER__HENSHIN_PARAMETER, oldHenshinParameter, henshinParameter));
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isInput() {
	ParameterProvider provider = getProvider();
	FlowDiagram diagram;

	if (provider instanceof FlowDiagram) {
	    diagram = (FlowDiagram) provider;
	    for (ParameterMapping m : diagram.getParameterMappings()) {
		if (m.getSrc() == this) {
		    return true;
		}
	    }

	} else {
	    diagram = ((Activity) provider).getDiagram();
	    for (ParameterMapping m : diagram.getParameterMappings()) {
		if (m.getTarget() == this
			&& m.getSrc().getProvider() == diagram) {
		    return true;
		}
	    }

	}

	return false;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public boolean isOutPut() {
	ParameterProvider provider = getProvider();
	FlowDiagram diagram;

	if (provider instanceof FlowDiagram) {
	    diagram = (FlowDiagram) provider;
	    for (ParameterMapping m : diagram.getParameterMappings()) {
		if (m.getTarget() == this) {
		    return true;
		}
	    }

	} else {
	    diagram = ((Activity) provider).getDiagram();
	    for (ParameterMapping m : diagram.getParameterMappings()) {
		if (m.getSrc() == this
			&& m.getTarget().getProvider() == diagram) {
		    return true;
		}
	    }

	}

	return false;

    }

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case FlowControlPackage.PARAMETER__PROVIDER:
				if (resolve) return getProvider();
				return basicGetProvider();
			case FlowControlPackage.PARAMETER__HENSHIN_PARAMETER:
				if (resolve) return getHenshinParameter();
				return basicGetHenshinParameter();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case FlowControlPackage.PARAMETER__PROVIDER:
				setProvider((ParameterProvider)newValue);
				return;
			case FlowControlPackage.PARAMETER__HENSHIN_PARAMETER:
				setHenshinParameter((org.eclipse.emf.henshin.model.Parameter)newValue);
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
			case FlowControlPackage.PARAMETER__PROVIDER:
				setProvider((ParameterProvider)null);
				return;
			case FlowControlPackage.PARAMETER__HENSHIN_PARAMETER:
				setHenshinParameter((org.eclipse.emf.henshin.model.Parameter)null);
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
			case FlowControlPackage.PARAMETER__PROVIDER:
				return provider != null;
			case FlowControlPackage.PARAMETER__HENSHIN_PARAMETER:
				return henshinParameter != null;
		}
		return super.eIsSet(featureID);
	}

} // ParameterImpl

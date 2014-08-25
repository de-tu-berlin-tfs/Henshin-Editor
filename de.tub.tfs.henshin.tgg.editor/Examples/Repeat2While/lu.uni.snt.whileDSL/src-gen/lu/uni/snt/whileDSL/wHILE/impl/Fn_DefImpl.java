/**
 */
package lu.uni.snt.whileDSL.wHILE.impl;

import lu.uni.snt.whileDSL.wHILE.Fgmnt_LST_Elem;
import lu.uni.snt.whileDSL.wHILE.Fn_Def;
import lu.uni.snt.whileDSL.wHILE.WHILEPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Fn Def</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl#getNameF <em>Name F</em>}</li>
 *   <li>{@link lu.uni.snt.whileDSL.wHILE.impl.Fn_DefImpl#getBody <em>Body</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class Fn_DefImpl extends Fgmnt_LST_ElemImpl implements Fn_Def
{
  /**
	 * The default value of the '{@link #getNameF() <em>Name F</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getNameF()
	 * @generated
	 * @ordered
	 */
  protected static final String NAME_F_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getNameF() <em>Name F</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getNameF()
	 * @generated
	 * @ordered
	 */
  protected String nameF = NAME_F_EDEFAULT;

  /**
	 * The cached value of the '{@link #getBody() <em>Body</em>}' containment reference.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getBody()
	 * @generated
	 * @ordered
	 */
  protected Fgmnt_LST_Elem body;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected Fn_DefImpl()
  {
		super();
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  protected EClass eStaticClass()
  {
		return WHILEPackage.Literals.FN_DEF;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getNameF()
  {
		return nameF;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setNameF(String newNameF)
  {
		String oldNameF = nameF;
		nameF = newNameF;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WHILEPackage.FN_DEF__NAME_F, oldNameF, nameF));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public Fgmnt_LST_Elem getBody()
  {
		return body;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public NotificationChain basicSetBody(Fgmnt_LST_Elem newBody, NotificationChain msgs)
  {
		Fgmnt_LST_Elem oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WHILEPackage.FN_DEF__BODY, oldBody, newBody);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setBody(Fgmnt_LST_Elem newBody)
  {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject)body).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WHILEPackage.FN_DEF__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject)newBody).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WHILEPackage.FN_DEF__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, WHILEPackage.FN_DEF__BODY, newBody, newBody));
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
		switch (featureID) {
			case WHILEPackage.FN_DEF__BODY:
				return basicSetBody(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
		switch (featureID) {
			case WHILEPackage.FN_DEF__NAME_F:
				return getNameF();
			case WHILEPackage.FN_DEF__BODY:
				return getBody();
		}
		return super.eGet(featureID, resolve, coreType);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public void eSet(int featureID, Object newValue)
  {
		switch (featureID) {
			case WHILEPackage.FN_DEF__NAME_F:
				setNameF((String)newValue);
				return;
			case WHILEPackage.FN_DEF__BODY:
				setBody((Fgmnt_LST_Elem)newValue);
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
  public void eUnset(int featureID)
  {
		switch (featureID) {
			case WHILEPackage.FN_DEF__NAME_F:
				setNameF(NAME_F_EDEFAULT);
				return;
			case WHILEPackage.FN_DEF__BODY:
				setBody((Fgmnt_LST_Elem)null);
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
  public boolean eIsSet(int featureID)
  {
		switch (featureID) {
			case WHILEPackage.FN_DEF__NAME_F:
				return NAME_F_EDEFAULT == null ? nameF != null : !NAME_F_EDEFAULT.equals(nameF);
			case WHILEPackage.FN_DEF__BODY:
				return body != null;
		}
		return super.eIsSet(featureID);
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  @Override
  public String toString()
  {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (nameF: ");
		result.append(nameF);
		result.append(')');
		return result.toString();
	}

} //Fn_DefImpl

/**
 */
package lu.uni.snt.repeat.rEPEAT.impl;

import lu.uni.snt.repeat.rEPEAT.REPEATPackage;
import lu.uni.snt.repeat.rEPEAT.Sym;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sym</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link lu.uni.snt.repeat.rEPEAT.impl.SymImpl#getSym <em>Sym</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SymImpl extends Log_Expr_UnaryImpl implements Sym
{
  /**
	 * The default value of the '{@link #getSym() <em>Sym</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSym()
	 * @generated
	 * @ordered
	 */
  protected static final String SYM_EDEFAULT = null;

  /**
	 * The cached value of the '{@link #getSym() <em>Sym</em>}' attribute.
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @see #getSym()
	 * @generated
	 * @ordered
	 */
  protected String sym = SYM_EDEFAULT;

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  protected SymImpl()
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
		return REPEATPackage.Literals.SYM;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public String getSym()
  {
		return sym;
	}

  /**
	 * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
	 * @generated
	 */
  public void setSym(String newSym)
  {
		String oldSym = sym;
		sym = newSym;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, REPEATPackage.SYM__SYM, oldSym, sym));
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
			case REPEATPackage.SYM__SYM:
				return getSym();
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
			case REPEATPackage.SYM__SYM:
				setSym((String)newValue);
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
			case REPEATPackage.SYM__SYM:
				setSym(SYM_EDEFAULT);
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
			case REPEATPackage.SYM__SYM:
				return SYM_EDEFAULT == null ? sym != null : !SYM_EDEFAULT.equals(sym);
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
		result.append(" (sym: ");
		result.append(sym);
		result.append(')');
		return result.toString();
	}

} //SymImpl

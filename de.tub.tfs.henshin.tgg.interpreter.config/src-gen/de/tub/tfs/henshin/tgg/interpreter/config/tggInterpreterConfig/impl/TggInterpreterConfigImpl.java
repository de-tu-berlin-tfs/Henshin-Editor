/**
 */
package de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl;

import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig;
import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tgg Interpreter Config</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.impl.TggInterpreterConfigImpl#getOptions <em>Options</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TggInterpreterConfigImpl extends MinimalEObjectImpl.Container implements TggInterpreterConfig
{
  /**
   * The cached value of the '{@link #getOptions() <em>Options</em>}' map.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getOptions()
   * @generated
   * @ordered
   */
  protected EMap<String, String> options;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TggInterpreterConfigImpl()
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
    return TggInterpreterConfigPackage.Literals.TGG_INTERPRETER_CONFIG;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EMap<String, String> getOptions()
  {
    if (options == null)
    {
      options = new EcoreEMap<String,String>(TggInterpreterConfigPackage.Literals.PROCESSING_MAP_ENTRY, ProcessingMapEntryImpl.class, this, TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS);
    }
    return options;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS:
        return ((InternalEList<?>)getOptions()).basicRemove(otherEnd, msgs);
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
    switch (featureID)
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS:
        if (coreType) return getOptions();
        else return getOptions().map();
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
    switch (featureID)
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS:
        ((EStructuralFeature.Setting)getOptions()).set(newValue);
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
    switch (featureID)
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS:
        getOptions().clear();
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
    switch (featureID)
    {
      case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG__OPTIONS:
        return options != null && !options.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //TggInterpreterConfigImpl

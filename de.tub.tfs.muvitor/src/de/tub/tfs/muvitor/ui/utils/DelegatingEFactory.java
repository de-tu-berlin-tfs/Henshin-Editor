package de.tub.tfs.muvitor.ui.utils;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;

final class DelegatingEFactory extends EFactoryImpl {

	private EFactory delegate;
	private EPackage delegatePackage;
	public DelegatingEFactory(EFactory delegate,EPackage del) {
		this.delegate = delegate;
		this.delegatePackage = del;
	}

	@Override
	public EObject create(EClass eClass) {
		if (EMFModelManager.replacedClasses.contains(eClass))
			return eClass.getEPackage().getEFactoryInstance().create(eClass);
		return delegate.create(eClass);
	}

	@Override
	public EList<EAnnotation> getEAnnotations() {
		// TODO Auto-generated method stub
		return delegate.getEAnnotations();
	}

	@Override
	public EAnnotation getEAnnotation(String source) {
		// TODO Auto-generated method stub
		return delegate.getEAnnotation(source);
	}

	@Override
	public EClass eClass() {
		// TODO Auto-generated method stub
		return delegate.eClass();
	}

	@Override
	public Resource eResource() {
		// TODO Auto-generated method stub
		return delegate.eResource();
	}

	@Override
	public EObject eContainer() {
		// TODO Auto-generated method stub
		return delegate.eContainer();
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		// TODO Auto-generated method stub
		return delegate.eContainingFeature();
	}

	@Override
	public EReference eContainmentFeature() {
		// TODO Auto-generated method stub
		return delegate.eContainmentFeature();
	}

	@Override
	public EList<EObject> eContents() {
		// TODO Auto-generated method stub
		return delegate.eContents();
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		// TODO Auto-generated method stub
		return delegate.eAllContents();
	}

	@Override
	public boolean eIsProxy() {
		// TODO Auto-generated method stub
		return delegate.eIsProxy();
	}

	@Override
	public EList<EObject> eCrossReferences() {
		// TODO Auto-generated method stub
		return delegate.eCrossReferences();
	}

	@Override
	public Object eGet(EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return delegate.eGet(feature);
	}

	@Override
	public Object eGet(EStructuralFeature feature, boolean resolve) {
		// TODO Auto-generated method stub
		return delegate.eGet(feature, resolve);
	}

	@Override
	public void eSet(EStructuralFeature feature, Object newValue) {
		delegate.eSet(feature, newValue);
	}

	@Override
	public boolean eIsSet(EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return delegate.eIsSet(feature);
	}

	@Override
	public void eUnset(EStructuralFeature feature) {
		delegate.eUnset(feature);
	}

	@Override
	public Object eInvoke(EOperation operation, EList<?> arguments)
			throws InvocationTargetException {
		return delegate.eInvoke(operation, arguments);

	}

	@Override
	public EList<Adapter> eAdapters() {
		// TODO Auto-generated method stub
		return delegate.eAdapters();
	}

	@Override
	public boolean eDeliver() {
		// TODO Auto-generated method stub
		return delegate.eDeliver();
	}

	@Override
	public void eSetDeliver(boolean deliver) {
		delegate.eSetDeliver(deliver);
	}

	@Override
	public void eNotify(Notification notification) {
		delegate.eNotify(notification);

	}

	@Override
	public EPackage getEPackage() {
		// TODO Auto-generated method stub
		return delegatePackage;
	}

	@Override
	public void setEPackage(EPackage value) {
		delegate.setEPackage(value);
	}

	@Override
	public Object createFromString(EDataType eDataType, String literalValue) {
		// TODO Auto-generated method stub
		return delegate.createFromString(eDataType, literalValue);
	}

	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		// TODO Auto-generated method stub
		return delegate.convertToString(eDataType, instanceValue);
	}


}
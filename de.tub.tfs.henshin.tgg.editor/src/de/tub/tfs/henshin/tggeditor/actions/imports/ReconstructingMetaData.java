package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EClassifierExtendedMetaData;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData.EPackageExtendedMetaData;

class ReconstructingMetaData extends BasicExtendedMetaData {
		
		EPackage reconstructedPackage;
		public Map<Object, EStructuralFeature> getMap() {
			return map;
		}

		public void setReconstructedPackage(EPackage reconstructedPackage) {
			this.reconstructedPackage = reconstructedPackage;
		}

		public void setDocumentRoot(String documentRoot) {
			this.documentRoot = documentRoot;
		}

		private Stack<String> currentElement;
		public void setCurrentElement(Stack<String> currentElement) {
			this.currentElement = currentElement;
		}

		public void setMap(Map<Object, EStructuralFeature> map) {
			this.map = map;
		}

		String documentRoot = null;
		private Map<Object, EStructuralFeature> map;
		String contextURI;
		String xmlFile;

		String getDocumentRoot() {
			return documentRoot;
		}
		
		public EPackage getReconstructedPackage() {
			return reconstructedPackage;
		}
		
		public ReconstructingMetaData(String xmlFile,Stack<String> currentElement,Map<Object,EStructuralFeature> map) {
			this.currentElement= currentElement;
			this.map = map;
			this.xmlFile = xmlFile;
		}
		
		@Override
		public Collection<EPackage> demandedPackages() {
			// TODO Auto-generated method stub
			return Arrays.asList(reconstructedPackage);
		}


		@Override
		public EStructuralFeature getAttribute(EClass eClass,
				String namespace, String name) {
			System.out.println("get attr " + eClass.getName()
					+ " queried " + name);
			EStructuralFeature feature = eClass
					.getEStructuralFeature(name.toLowerCase());
			if (feature != null)
				return feature;
			return super.getAttribute(eClass, namespace, name);
		}

		@Override
		public EStructuralFeature getAttribute(String namespace,
				String name) {
			System.out.println("get attr : " + name);
			//map.clear();
			return null;
		}


		@Override
		public EStructuralFeature getElement(EClass eClass,
				String namespace, String name) {
			EStructuralFeature feature = eClass
					.getEStructuralFeature(name.toLowerCase());
			if (feature != null)
				return feature;
			return super.getElement(eClass, namespace, name);
		}

		@Override
		public EStructuralFeature getElement(String namespace,
				String name) {
			return null;
		}
		private boolean firstType = true;
		@Override
		public EClassifier getType(EPackage ePackage, String name) {
			firstType = false;
			if (name == "")
				return ePackage.getEClassifier(documentRoot);
			if (ePackage == null
					|| ePackage.equals(reconstructedPackage))
				return reconstructedPackage.getEClassifier(name);
			return super.getType(ePackage, name);
		}
		
		
	@Override
	public List<EStructuralFeature> getAllAttributes(EClass eClass) {
		// TODO Auto-generated method stub
		return super.getAllAttributes(eClass);
	}
	@Override
	public List<EStructuralFeature> getAllElements(EClass eClass) {
		
		return super.getAllElements(eClass);
	}
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.emf.ecore.util.BasicExtendedMetaData#
		 * demandFeature(java.lang.String, java.lang.String,
		 * boolean, boolean)
		 */
		@Override
		public EStructuralFeature demandFeature(String namespace,
				String name, boolean isElement, boolean isReference) {

			System.out.println("demanded Feature ns:" + namespace
					+ " name:" + name + " isElement:" + isElement
					+ " isRef:" + isReference);
			String origName = name;
			if (name.contains(":")){
				name = name.substring(name.lastIndexOf(":")+1);
			}
			name = name.toLowerCase();
			//name = name.substring(0, 1).toLowerCase() + name.substring(1);
			EStructuralFeature feat = super.demandFeature(
					namespace, name, isElement, isReference);
			feat.setDerived(false);
			feat.setTransient(false);
			feat.setVolatile(false);
			getDocumentRoot(reconstructedPackage)
					.getEStructuralFeatures().remove(feat);
			feat.setUpperBound(-1);
			// this.getExtendedMetaData(feat).setGroup(feat);

			if (!isReference) {
				feat.setEType(EcorePackage.Literals.ESTRING);

				EClass cont = (EClass) this.demandType(namespace,
						currentElement.elementAt(currentElement
								.size() - 1));
				if (cont.getEStructuralFeature(name) == null)
					cont.getEStructuralFeatures().add(feat);
				else
					feat = cont.getEStructuralFeature(name);
				feat.setUpperBound(1);
				System.out.println("created feat " + name + " for "
						+ cont.getName());
				
				this.setAnnotation(feat, "name", origName);
				this.setAnnotation(feat, "kind", "attribute");
				if (isElement)
					this.setAnnotation(feat, "namespace", "##targetNamespace");
				
				
			} else if (isElement) {
				// getDocumentRoot(reconstructedPackage)
				if (currentElement.size() - 1 > 0) {
					String container = currentElement
							.elementAt(currentElement.size() - 2);
					EClass cont = (EClass) this.demandType(
							namespace, container);
					if (cont.getEStructuralFeature(name) == null) {

						EClassifier targetType = this.demandType(
								namespace, origName);

						feat.setEType(targetType);

						feat.setUpperBound(-1);

						cont.getEStructuralFeatures().add(feat);

					} else {
						feat = cont.getEStructuralFeature(name);

						EClassifier targetType = this.demandType(
								namespace, origName);

						feat.setEType(targetType);

						feat.setUpperBound(-1);

					}

					this.setAnnotation(feat, "name", origName);
					this.setAnnotation(feat, "kind", "element");
					this.setAnnotation(feat, "namespace", "##targetNamespace");
					
					System.out.println("created feat " + name
							+ " for " + cont.getName());
				} else {
					this.setAnnotation(feat, "name", origName);
					this.setAnnotation(feat, "kind", "element");
					this.setAnnotation(feat, "namespace", "##targetNamespace");
					
				}

			}
			if (isElement && isReference
					&& feat instanceof EReference) {
				((EReference) feat).setContainment(true);
			}

			return feat;
		}

		@Override
		public EPackage demandPackage(String namespace) {
			System.out.println("demanded Package " + namespace);
			if (namespace != null && contextURI == null) {
				contextURI = namespace;
			} else {
				namespace = contextURI;
			}
			
			if (namespace == null){
				namespace = xmlFile.replaceAll("\\\\", "/");
				contextURI = namespace;
			}
			if (reconstructedPackage != null)
				return reconstructedPackage;

			reconstructedPackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE
					.createEPackage();
			reconstructedPackage.setName(namespace);
			reconstructedPackage.setNsPrefix("xml");
			reconstructedPackage.setNsURI(LoadReconstructXMLForSource.generateReconstructedPackageURI(namespace));
//			if (BASESCHEME.equals(namespace))
//				EPackage.Registry.INSTANCE.put(null,
//						reconstructedPackage);

			EPackage.Registry.INSTANCE.put(reconstructedPackage.getNsURI(),
					reconstructedPackage);

			System.out.println("created Package "
					+ reconstructedPackage.toString());

			return reconstructedPackage;
		}

		@Override
		public String getName(EClassifier eClassifier) {

			String name = super.getName(eClassifier);
			if (name.endsWith("_._type")){
				name = name.substring(0, name.length() - 7);
			}
			return name;
		}
		
		
		@Override
		public EClassifier demandType(String namespace, String name) {
			System.out.println("demanded Type " + namespace
					+ " name:" + name);
			
			if (name.contains(":")){
				name = name.substring(name.indexOf(":")+1);
			}
			
			
			if (reconstructedPackage == null)
				reconstructedPackage = demandPackage(null);

			if (reconstructedPackage.getEClassifier(name) != null)
				return reconstructedPackage.getEClassifier(name);

			if (getDocumentRoot(reconstructedPackage) == null || firstType) {
				firstType = false;
				documentRoot = "";
				if (getDocumentRoot(reconstructedPackage) ==null){

					EClass documentRootEClass = EcoreFactory.eINSTANCE
							.createEClass();
					// documentRootEClass.getESuperTypes().add(
					// XMLTypePackage.eINSTANCE
					// .getXMLTypeDocumentRoot());
					documentRootEClass.setName("DocumentRoot");
					reconstructedPackage.getEClassifiers().add(
							documentRootEClass);
					documentRoot = "";
					setDocumentRoot(documentRootEClass);
					documentRoot = "DocumentRoot";

					this.setAnnotation(documentRootEClass, "name", "");
					this.setAnnotation(documentRootEClass, "kind", "mixed");
				}
				EStructuralFeature ref = demandFeature(namespace, name, true, true);
				getDocumentRoot(reconstructedPackage).getEStructuralFeatures().add(ref);
				this.setAnnotation(ref, "name", name);
				
				EClassifier type = demandType(namespace, name);
				
				ref.setEType(type);
				return type;
			}

			EClassImpl eClass = (EClassImpl) EcoreFactoryImpl.eINSTANCE
					.createEClass();
			
			eClass.setName(name);
			this.setAnnotation(eClass, "name", eClass.getName() + "_._type");
			this.setAnnotation(eClass, "kind", "elementOnly");
			reconstructedPackage.getEClassifiers().add(eClass);

			System.out.println("created Type " + eClass.toString());

			EAttribute eAttribute = EcoreFactory.eINSTANCE
					.createEAttribute();
			eAttribute.setName(LoadReconstructXMLForSource.MIXEDELEMENTFEATURE);
			
			eAttribute.setEType(EcorePackage.Literals.EFEATURE_MAP);
			eAttribute.setDerived(false);
			eAttribute.setTransient(true);
			eAttribute.setVolatile(false);
			eAttribute
					.setUpperBound(1);
			this.setAnnotation(eAttribute, "name", ":mixed");
			this.setAnnotation(eAttribute, "kind", "elementWildcard");
			eClass.getEStructuralFeatures().add(eAttribute);
			
			eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
			eAttribute.setName(LoadReconstructXMLForSource.XML_ELEMENT_TEXT);
			eAttribute.setEType(EcorePackage.Literals.ESTRING);
			eAttribute.setDerived(false);
			eAttribute.setTransient(false);
			eAttribute.setVolatile(false);
			eAttribute.setUpperBound(1);
			this.setAnnotation(eAttribute, "name", eAttribute.getName() + "_._type");
			this.setAnnotation(eAttribute, "kind", "attribute");
			eClass.getEStructuralFeatures().add(eAttribute);
			return eClass;
		}

		@Override
		public EStructuralFeature getAffiliation(EClass eClass,
				EStructuralFeature eStructuralFeature) {
			// TODO Auto-generated method stub

			return super.getAffiliation(eClass, eStructuralFeature);
		}

		@Override
		public EStructuralFeature getAffiliation(
				EStructuralFeature eStructuralFeature) {
			// TODO Auto-generated method stub
			return super.getAffiliation(eStructuralFeature);
		}

		@Override
		public EStructuralFeature getAttributeWildcardAffiliation(
				EClass eClass, String namespace, String name) {

			return super.getAttributeWildcardAffiliation(eClass,
					namespace, name);

		}

		@Override
		public EClass getDocumentRoot(EPackage ePackage) {
			EClassifier type = getExtendedMetaData(ePackage).getType("");
			if (type != null)
				return (EClass) type;
			return (EClass) ePackage.getEClassifier(documentRoot);
		}

		@Override
		public EStructuralFeature getElementWildcardAffiliation(
				EClass eClass, String namespace, String name) {

			return super.getElementWildcardAffiliation(eClass,
					namespace, name);

		}

		@Override
		public EPackage getPackage(String namespace) {
			// TODO Auto-generated method stub
			if (reconstructedPackage != null
					&& reconstructedPackage.getNsURI().equals(
							namespace))
				return reconstructedPackage;
			if (reconstructedPackage != null
					&& (namespace == null || namespace.isEmpty()))
				return reconstructedPackage;
			return super.getPackage(namespace);
		}

		@Override
		public EClassifier getType(String namespace, String name) {
			if (name.equals(documentRoot))
				return super.getType(namespace, "");
			return super.getType(namespace, name);
		}

		@Override
		public boolean isDocumentRoot(EClass eClass) {
			return documentRoot.equals(getName(eClass));
		}

		@Override
		public void setDocumentRoot(EClass eClass) {
			setName(eClass, documentRoot);
			setContentKind(eClass, MIXED_CONTENT);
		}
		private void setAnnotation(EModelElement eClassifier,String name,String value){
			EAnnotation eAnnotation = getAnnotation(eClassifier, true);
		    eAnnotation.getDetails().put(name, value); 
		}
		

		public static void cleanExtendedMetaData(EPackage ePkg){
			for (EClassifier eClassifier : ePkg.getEClassifiers()) {
				EClassifierExtendedMetaData.Holder holder = (EClassifierExtendedMetaData.Holder)eClassifier;
				holder.setExtendedMetaData(null);
				for (EObject obj : eClassifier.eContents()) {
					if (obj instanceof EStructuralFeature){
						EStructuralFeatureExtendedMetaData.Holder h = (EStructuralFeatureExtendedMetaData.Holder)obj;
						h.setExtendedMetaData(null);
						try {
							Field field = EStructuralFeatureImpl.class.getDeclaredField("settingDelegate");
							boolean accessible = field.isAccessible();
							field.setAccessible(true);
							field.set(obj, null);
							field.setAccessible(accessible);
						} catch (Exception ex){
							
						}
					}
				}
			}
			EPackageExtendedMetaData.Holder holder = (EPackageExtendedMetaData.Holder)ePkg;
			holder.setExtendedMetaData(null);

		}

	}
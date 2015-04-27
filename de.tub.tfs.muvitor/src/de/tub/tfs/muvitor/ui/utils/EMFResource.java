/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.muvitor.ui.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.BinaryIO.Version;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.EObjectInputStream;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl.EObjectOutputStream;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.DOMHandler;
import org.eclipse.emf.ecore.xmi.DOMHelper;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

public class EMFResource extends XMIResourceImpl {
	/**
	 * 
	 */
	private final EMFModelManager emfModelManager;
	private boolean loadTime = false;;

	EMFResource(EMFModelManager emfModelManager, URI uri) {
		super(uri);
		this.emfModelManager = emfModelManager;
	}

	@Override
	protected boolean useUUIDs() {
		return true;
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		// TODO Auto-generated method stub
		FragmentResource fragmentResource = this.emfModelManager.requestFragmentResource(this);
		fragmentResource.getContents().clear();
		try {
			super.save(options);
				
		} catch (Exception ex){
			fragmentResource = this.emfModelManager.getFragmentResource(this);
			if (fragmentResource != null){

				fragmentResource.save(null);
				fragmentResource.cleanUp();
			}
			ex.printStackTrace();
			throw ex;
		}
		fragmentResource = this.emfModelManager.getFragmentResource(this);
		if (fragmentResource != null){

			fragmentResource.save(null);
			fragmentResource.cleanUp();
		}
	}

	@Override
	protected XMLHelper createXMLHelper() {
		return new XMIHelperImpl(this){							
		
			@Override
			public String getQName(EClass c) {

				if (EMFModelManager.replacedClasses.contains(c)){
					for (EClass cl : c.getEAllSuperTypes()) {
						if (cl.getName().equals(EMFModelManager.replacedClassesToStringMap.get(c)))
							return super.getQName(cl);
					}
				}
				return super.getQName(c);
			}
			
			@Override
			public EClassifier getType(EFactory eFactory,
					String typeName) {
				if (eFactory != null)
				{
					EPackage ePackage = eFactory.getEPackage();
					
					EAnnotation annotation = ePackage.getEAnnotation("EMFModelManager");
					if (annotation != null){
						String typeMapping = annotation.getDetails().get(typeName);
						if (typeMapping != null && !typeMapping.isEmpty())
							typeName = typeMapping;
					}
					HashMap<String, EClassifier> map = EMFModelManager.conversionsClass.get(ePackage);
					if (map != null){
						EClassifier cl = map.get(typeName);
						if (cl == null) {
							
							if (extendedMetaData != null) {
								return extendedMetaData.getType(ePackage, typeName);
							} else {
								return ePackage.getEClassifier(typeName);
							}
						}
							
						return cl;
					}	
					if (extendedMetaData != null) {
						return extendedMetaData.getType(ePackage, typeName);
					}

				}
				return super.getType(eFactory, typeName);
			}
		};
		
	}

	@Override
	public void load(Map options) throws IOException {
		loadTime = true;
		try {
		FragmentResource fragmentResource = this.emfModelManager.getFragmentResource(this);
		if (fragmentResource != null){
			fragmentResource.cleanUp();
		}
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE, Boolean.TRUE);
		super.load(options);
		XMIResource r = (XMIResource) this;
		if (!r.getEObjectToExtensionMap().isEmpty()){
			for (Entry<EObject, AnyType> missingReference : r.getEObjectToExtensionMap().entrySet()) {
				if (!missingReference.getValue().getMixed().isEmpty())
					System.err.println("Class " + missingReference.getKey().getClass().getName() + " is missing reference " + missingReference.getValue().getMixed().get(0).getEStructuralFeature().getName() );
				else if (!missingReference.getValue().getAnyAttribute().isEmpty())
					System.err.println("Class " + missingReference.getKey().getClass().getName() + " is missing attribute " + missingReference.getValue().getAnyAttribute().get(0).getEStructuralFeature().getName() );
				else if (!missingReference.getValue().getAny().isEmpty())
					System.err.println("Class " + missingReference.getKey().getClass().getName() + " is missing something " + missingReference.getValue().getAnyAttribute().get(0).getEStructuralFeature().getName() );

			}

		}
		fragmentResource = this.emfModelManager.getFragmentResource(this);
		if (fragmentResource != null)
			fragmentResource.cleanUp();
		} finally {
			loadTime = false;
		}
	}

	@Override
	protected XMLLoad createXMLLoad() {
		// TODO Auto-generated method stub
		return new XMILoadImpl(createXMLHelper()){
		
			
			
			@Override
			protected DefaultHandler makeDefaultHandler() {
				
				return new SAXXMIHandler(resource,
						helper, options) {

					@Override
					protected void setFeatureValue(
							EObject object,
							EStructuralFeature feature,
							Object value, int position) {
						try
						{
							helper.setValue(object, feature, value, position);
						}
						catch (RuntimeException e)
						{
							System.out.println("skipped: " + object.eClass().getName() + "." + feature.getName() + "("+value+")");
							//e.printStackTrace();
							// ignore illegal values and skip
							//error
							//(new IllegalValueException
							//		(object,
							//				feature,
							//				value,
							//				e,
							//				getLocation(),
							//				getLineNumber(),
							//				getColumnNumber()));
						}
					}
					@Override
					protected void handleProxy(
							InternalEObject proxy,
							String uriLiteral) {
						if (uriLiteral.contains("#")){
							String name = uriLiteral.substring(uriLiteral.lastIndexOf("#")+2);
							String newname = name;
							String pkgUri = uriLiteral.substring(0,uriLiteral.lastIndexOf("#"));
							Object obj = EPackageRegistryImpl.INSTANCE.get(pkgUri);
							if (obj instanceof EPackage){
								EPackage pkg = (EPackage) obj;
								EAnnotation annotation = pkg.getEAnnotation("EMFModelManager");
								if (annotation != null){
									String typeMapping = annotation.getDetails().get(name);
									if (typeMapping != null && !typeMapping.isEmpty())
										newname = typeMapping;
								}
								uriLiteral = uriLiteral.replace(name,	newname);
							}
						}
						super.handleProxy(proxy, uriLiteral);
					}
					
					@Override
					protected void processObject(
							EObject object) {
						super.processObject(object);
						
						if (object != null && EMFModelManager.replacedClasses.contains(object.eClass())){
							EMFModelManager.loadDelegates.get(object.eClass()).doLoad(object);
						}	
						
						if (EMFResource.this.emfModelManager.monitor != null){
							int line = getLineNumber();
							
							final int work = line - EMFResource.this.emfModelManager.lastLine;
							if (work < 20)
								return;
							EMFResource.this.emfModelManager.lastLine = line;
							Display.getDefault().asyncExec(new Runnable() {
								
								@Override
								public void run() {
									EMFResource.this.emfModelManager.monitor.worked(work);
									
								}
							});
							if (EMFResource.this.emfModelManager.monitor.isCanceled())
								throw new RuntimeException("Aborted by User!");
						}
						
						
					}
					
				};
				
				
			}
		};
	}

	@Override
	protected XMLSave createXMLSave()
	{
		return new XMISaveImpl(createXMLHelper()){

			private boolean checkForDelegates(EObject o,EStructuralFeature f){
				if (EMFModelManager.replacedClasses.contains(o.eClass()) && o.eClass().getEStructuralFeatures().contains(f)){
					
					boolean result = EMFModelManager.saveDelegates.get(o.eClass()).shouldSkipSave(o, f);
															
							
					return result;
				}
				return false;
			}


			@Override
			protected boolean saveFeatures(EObject o, boolean attributesOnly)
			{
				EClass eClass = o.eClass();   
				
				if (EMFModelManager.replacedClasses.contains(eClass)){
					EMFResource.this.emfModelManager.requestFragmentResource(o.eResource()).getContents().add(o);
				}
				
				int contentKind = extendedMetaData == null ? ExtendedMetaData.UNSPECIFIED_CONTENT : extendedMetaData.getContentKind(eClass);     
				if (!toDOM)
				{
					switch (contentKind)
					{
					case ExtendedMetaData.MIXED_CONTENT:
					case ExtendedMetaData.SIMPLE_CONTENT: 
					{
						doc.setMixed(true);
						break;
					}
					}
				}

				if (o == root)
				{
					writeTopAttributes(root);
				}

				EStructuralFeature[] features = featureTable.getFeatures(eClass);
				int[] featureKinds = featureTable.getKinds(eClass, features);
				int[] elementFeatures = null;
				int elementCount = 0;

				String content = null;

				// Process XML attributes
				LOOP:
					for (int i = 0; i < features.length; i++ )
					{
						int kind = featureKinds[i];
						EStructuralFeature f = features[i];

						if (checkForDelegates(o,features[i])){
							
							continue;
						}
							

						if (kind != TRANSIENT && shouldSaveFeature(o, f))
						{
							switch (kind)
							{
							case DATATYPE_ELEMENT_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getDataTypeElementSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case DATATYPE_SINGLE:
							{
								saveDataTypeSingle(o, f);
								continue LOOP;
							}
							case DATATYPE_SINGLE_NILLABLE:
							{
								if (!isNil(o, f))
								{
									saveDataTypeSingle(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ATTRIBUTE_SINGLE:
							{
								saveEObjectSingle(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_MANY:
							{
								saveEObjectMany(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_IDREF_SINGLE:
							{
								saveIDRefSingle(o, f);
								continue LOOP;
							}
							case OBJECT_ATTRIBUTE_IDREF_MANY:
							{
								saveIDRefMany(o, f);
								continue LOOP;
							}
							case OBJECT_HREF_SINGLE_UNSETTABLE:
							{
								if (isNil(o, f))
								{
									break;
								}
								// it's intentional to keep going
							}
							case OBJECT_HREF_SINGLE:
							{
								if (useEncodedAttributeStyle)
								{
									saveEObjectSingle(o, f);
									continue LOOP;
								}
								else
								{
									switch (sameDocSingle(o, f))
									{
									case SAME_DOC:
									{
										saveIDRefSingle(o, f);
										continue LOOP;
									}
									case CROSS_DOC:
									{
										break;
									}
									default:
									{
										continue LOOP;
									}
									}
								}
								break;
							}
							case OBJECT_HREF_MANY_UNSETTABLE:
							{
								if (isEmpty(o, f))
								{
									saveManyEmpty(o, f);
									continue LOOP;
								}
								// It's intentional to keep going.
							}
							case OBJECT_HREF_MANY:
							{
								if (useEncodedAttributeStyle)
								{
									saveEObjectMany(o, f);
									continue LOOP;
								}
								else
								{
									switch (sameDocMany(o, f))
									{
									case SAME_DOC:
									{
										saveIDRefMany(o, f);
										continue LOOP;
									}
									case CROSS_DOC:
									{
										break;
									}
									default:
									{
										continue LOOP;
									}
									}
								}
								break;
							}
							case OBJECT_ELEMENT_SINGLE_UNSETTABLE:
							case OBJECT_ELEMENT_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementReferenceSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_MANY:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementReferenceManySimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE:
							case OBJECT_ELEMENT_IDREF_SINGLE:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementIDRefSingleSimple(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_ELEMENT_IDREF_MANY:
							{
								if (contentKind == ExtendedMetaData.SIMPLE_CONTENT)
								{
									content = getElementIDRefManySimple(o, f);
									continue LOOP;
								}
								break;
							}
							case DATATYPE_ATTRIBUTE_MANY:
							{
								break;
							}
							case OBJECT_CONTAIN_MANY_UNSETTABLE:
							case DATATYPE_MANY:
							{
								if (isEmpty(o, f))
								{
									saveManyEmpty(o, f);
									continue LOOP;
								}
								break;
							}
							case OBJECT_CONTAIN_SINGLE_UNSETTABLE:
							case OBJECT_CONTAIN_SINGLE:
							case OBJECT_CONTAIN_MANY:
							case ELEMENT_FEATURE_MAP:
							{
								break;
							}
							case ATTRIBUTE_FEATURE_MAP:
							{
								saveAttributeFeatureMap(o, f);
								continue LOOP;
							}
							default:
							{
								continue LOOP;
							}
							}

							if (attributesOnly)
							{
								continue LOOP;
							}

							// We only get here if we should do this.
							//
							if (elementFeatures == null)
							{
								elementFeatures = new int[features.length];
							}
							elementFeatures[elementCount++] = i;
						}
					}

				processAttributeExtensions(o);

				if (elementFeatures == null)
				{
					if (content == null)
					{
						content = getContent(o, features);
					}

					if (content == null)
					{
						if (o == root && writeTopElements(root))
						{
							endSaveFeatures(o, 0, null);
							return true;
						}
						else
						{
							endSaveFeatures(o, EMPTY_ELEMENT, null);
							return false;
						}
					}
					else
					{
						endSaveFeatures(o, CONTENT_ELEMENT, content);
						return true;
					}
				}

				if (o == root)
				{
					writeTopElements(root);
				}

				// Process XML elements
				for (int i = 0; i < elementCount; i++ )
				{
					int kind = featureKinds[elementFeatures[i]];
					EStructuralFeature f = features[elementFeatures[i]];
					
					if (checkForDelegates(o,f))
						continue;
					
					switch (kind)
					{
					case DATATYPE_SINGLE_NILLABLE:
					{
						saveNil(o, f);
						break;
					}
					case ELEMENT_FEATURE_MAP:
					{
						saveElementFeatureMap(o, f);
						break;
					}
					case DATATYPE_MANY:
					{
						saveDataTypeMany(o, f);
						break;
					}
					case DATATYPE_ATTRIBUTE_MANY:
					{
						saveDataTypeAttributeMany(o, f);
						break;
					}
					case DATATYPE_ELEMENT_SINGLE:
					{
						saveDataTypeElementSingle(o, f);
						break;
					}
					case OBJECT_CONTAIN_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_CONTAIN_SINGLE:
					{
						saveContainedSingle(o, f);
						break;
					}
					case OBJECT_CONTAIN_MANY_UNSETTABLE:
					case OBJECT_CONTAIN_MANY:
					{
						saveContainedMany(o, f);
						break;
					}
					case OBJECT_HREF_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_HREF_SINGLE:
					{
						saveHRefSingle(o, f);
						break;
					}
					case OBJECT_HREF_MANY_UNSETTABLE:
					case OBJECT_HREF_MANY:
					{
						saveHRefMany(o, f);
						break;
					}
					case OBJECT_ELEMENT_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_ELEMENT_SINGLE:
					{
						saveElementReferenceSingle(o, f);
						break;
					}
					case OBJECT_ELEMENT_MANY:
					{
						saveElementReferenceMany(o, f);
						break;
					}
					case OBJECT_ELEMENT_IDREF_SINGLE_UNSETTABLE:
					{
						if (isNil(o, f))
						{
							saveNil(o, f);
							break;
						}
						// it's intentional to keep going
					}
					case OBJECT_ELEMENT_IDREF_SINGLE:
					{
						saveElementIDRefSingle(o, f);
						break;
					}
					case OBJECT_ELEMENT_IDREF_MANY:
					{
						saveElementIDRefMany(o, f);
						break;
					}
					}
				}
				endSaveFeatures(o, 0, null);
				return true;
			}
		};

	}
	
	@Override
	protected EObject getEObjectByID(String id) {
		if (loadTime){
			if (idToEObjectMap != null)
			{
				EObject eObject = idToEObjectMap.get(id);
				if (eObject != null)
				{
					return eObject;
				}
			}
			Map<String, EObject> map = getIntrinsicIDToEObjectMap();
			if (map != null)
			{
				EObject eObject = map.get(id);
				if (eObject != null)
				{
					return eObject;
				}
			}
			return null;// skip search for attribute feature as we are not using those for Henshin
		}  else {
			return super.getEObjectByID(id);
		}
		//
	}	
	
}
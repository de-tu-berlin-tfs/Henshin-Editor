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
package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.util.Collection;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.IllegalValueException;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

final class LoggingXMLLoad extends XMLLoadImpl {
	private Stack<String> stack;

	LoggingXMLLoad(XMLHelper helper,Stack<String> elemStack) {
		super(helper);
		this.stack = elemStack;
	}

	@Override
	protected DefaultHandler makeDefaultHandler() {
		// TODO Auto-generated method stub
		return new SAXXMLHandler(resource,
				helper, options) {
			@Override
			protected EObject validateCreateObjectFromFactory(EFactory factory, String typeName, EObject newObject, EStructuralFeature feature)
			{
				if (newObject != null)
				{
					if (extendedMetaData != null)
					{
						Collection<EPackage> demandedPackages = extendedMetaData.demandedPackages();
						if (!demandedPackages.isEmpty() && demandedPackages.contains(newObject.eClass().getEPackage()))
						{
							if (recordUnknownFeature)
							{
								EObject peekObject = objects.peekEObject();

								return newObject;
							}
							else
							{
								String namespace = extendedMetaData.getNamespace(feature);
								String name = extendedMetaData.getName(feature);
								EStructuralFeature wildcardFeature = 
										extendedMetaData.getElementWildcardAffiliation((objects.peekEObject()).eClass(), namespace, name);
								if (wildcardFeature != null)
								{
									int processingKind = laxWildcardProcessing ? ExtendedMetaData.LAX_PROCESSING : extendedMetaData.getProcessingKind(wildcardFeature);
									switch (processingKind)
									{
									case ExtendedMetaData.LAX_PROCESSING:
									case ExtendedMetaData.SKIP_PROCESSING:
									{
										return newObject;
									}
									}
								}
							}

							newObject = null;
						}
					}
				}
				else if (feature != null && factory != null && extendedMetaData != null)
				{
					// processing unknown feature with xsi:type (xmi:type)
					if (recordUnknownFeature || processAnyXML)
					{

						EObject result = null;
						String namespace = extendedMetaData.getNamespace(factory.getEPackage());
						if (namespace == null)
						{
							usedNullNamespacePackage = true;
						}
						if (useNewMethods)
						{
							EClassifier type = extendedMetaData.demandType(namespace, typeName);
							result = createObject(type.getEPackage().getEFactoryInstance(), type, false);
						}
						else
						{
							factory = extendedMetaData.demandType(namespace, typeName).getEPackage().getEFactoryInstance();
							result = createObjectFromFactory(factory, typeName);
						}
						EObject peekObject = objects.peekEObject();
						if (!(peekObject instanceof AnyType))
						{

						}
						return result;
					}
					else
					{
						String namespace = extendedMetaData.getNamespace(feature);
						String name = extendedMetaData.getName(feature);
						EStructuralFeature wildcardFeature = 
								extendedMetaData.getElementWildcardAffiliation((objects.peekEObject()).eClass(), namespace, name);
						if (wildcardFeature != null)
						{
							int processingKind = laxWildcardProcessing ? ExtendedMetaData.LAX_PROCESSING : extendedMetaData.getProcessingKind(wildcardFeature);
							switch (processingKind)
							{
							case ExtendedMetaData.LAX_PROCESSING:
							case ExtendedMetaData.SKIP_PROCESSING:
							{
								// EATM Demand create metadata; needs to depend on processing mode...
								String factoryNamespace = extendedMetaData.getNamespace(factory.getEPackage());
								if (factoryNamespace == null)
								{
									usedNullNamespacePackage = true;
								}
								if (useNewMethods)
								{
									EClassifier type = extendedMetaData.demandType(factoryNamespace, typeName);
									return createObject(type.getEPackage().getEFactoryInstance(), type, false);
								}
								else
								{
									factory = extendedMetaData.demandType(factoryNamespace, typeName).getEPackage().getEFactoryInstance();
									return createObjectFromFactory(factory, typeName);
								}
							}
							}
						}
					}
				}

				validateCreateObjectFromFactory(factory, typeName, newObject);

				return newObject;
			}

			
			@Override
			public void endElement(String uri,
					String localName,
					String name) {

				super.endElement(uri,
						localName, name);
				String cur = stack
						.pop();
				if (!cur.equals(name)) {
					System.out
					.println("ERROR!");
				}
			}

			@Override
			public void endEntity(String name) {
				// TODO Auto-generated
				// method stub
				super.endEntity(name);
			}

			@Override
			public void startElement(
					String uri,
					String localName,
					String name) {
				stack.push(name);
				super.startElement(uri,
						localName, name);

			}

			@Override
			public void startEntity(String name) {
				// TODO Auto-generated
				// method stub
				super.startEntity(name);
			}

			@Override
			public void characters(char[] ch, int start, int length) {

				super.characters(ch, start, length);
			}


			@Override
			protected void processObject(EObject object) {
				EStructuralFeature mixed = null;
				if (object == null){
					super.processObject(object);
					return;
				}
				if ((mixed = object.eClass().getEStructuralFeature(LoadReconstructXMLForSource.MIXEDELEMENTFEATURE) ) != null){
					object.eSet(mixed, new BasicFeatureMap((InternalEObject) object,XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT.getFeatureID(), XMLTypePackage.Literals.XML_TYPE_DOCUMENT_ROOT__TEXT));
				}
				super.processObject(object);
			}

			@Override
			protected void handleMixedText() {
				if (text.length() == 0){
					text = null;
					return;
				}
				if (text.charAt(0) == '\n'){
					text = null;
					return;
				}
				super.handleMixedText();
			}

			@Override
			protected void setFeatureValue(EObject object,
					EStructuralFeature feature, Object value,
					int position) {
				try
				{
					helper.setValue(object, feature, value, position);
				}
				catch (RuntimeException e)
				{

				}
			}
		};
	}

	@Override
	protected SAXParser makeParser()
			throws ParserConfigurationException,
			SAXException {
		SAXParserFactory f = SAXParserFactory
				.newInstance();

		return f.newSAXParser();
	}
}
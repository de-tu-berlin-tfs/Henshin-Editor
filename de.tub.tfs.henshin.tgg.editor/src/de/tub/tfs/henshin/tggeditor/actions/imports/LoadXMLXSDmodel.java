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

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EStructuralFeatureImpl.ContainmentUpdatingFeatureMapEntry;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;

public class LoadXMLXSDmodel extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadXMLXSDmodel";
	private TGG transSys;

	public LoadXMLXSDmodel(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Load XML and XSD File");
		setToolTipText("Loads XML File and reconstructs model.");
		
	}

	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);

		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			if ((editpart instanceof GraphFolderTreeEditPart)) {
				transSys = (TGG) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}

	@Override
	public void run() {
		Shell shell = new Shell();
		try {
			FileDialog dialog = new FileDialog(shell);
			dialog.setText("Please select the xml file you want to import.");
			String xmlURI = dialog.open();
			dialog.setText("Please select the xsd scheme file you want to import.");
			String xsdURI = dialog.open();
			
			XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
			Collection<EObject> generatedPackages = xsdEcoreBuilder.generate(URI.createFileURI(xsdURI));

			// register the packages loaded from XSD
			for (EObject generatedEObject : generatedPackages) {
			    if (generatedEObject instanceof EPackage) {
			        EPackage generatedPackage = (EPackage) generatedEObject;
			        EPackage.Registry.INSTANCE.put(generatedPackage.getNsURI(),
			            generatedPackage);
			    }
			}

			// add file extension to registry
			ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap()
			    .put("xml", new GenericXMLResourceFactoryImpl());

			ResourceSet resourceSet = new ResourceSetImpl();
			try {
				Resource resource = resourceSet.getResource(URI.createFileURI(xmlURI), true);
			
				resource.load(null);
				
				EObject root = resource.getContents().get(0);
				
				ImportInstanceModelAction action = new ImportInstanceModelAction(
						null);
				action.setModule(transSys);
				action.createAndAddGraph((ResourceImpl) resource,
						URI.createFileURI(xmlURI));

				action.dispose();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		} finally {
			shell.close();
		}

	}
}

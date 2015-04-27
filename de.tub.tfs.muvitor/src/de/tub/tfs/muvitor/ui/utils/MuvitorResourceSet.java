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

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.muvitor.ui.MuvitorActivator;

public class MuvitorResourceSet extends ResourceSetImpl {
	protected HashSet<String> missingEpackages = new HashSet<String>();

	@Override
	public Resource getResource(URI uri, boolean loadOnDemand){
		Resource r = super.getResource(uri, loadOnDemand);
		if (r != null && !r.getContents().isEmpty()){
			if (r.getContents().get(0) instanceof EPackage){
				EPackage epkg = (EPackage) r.getContents().get(0);
				String u = epkg.getNsURI();
				String oldURI = r.getURI().toString();
				EPackageRegistryImpl.INSTANCE.put(oldURI, epkg);
				r.setURI(URI.createURI(u));
				
				
			}
		}
		return r;
	}

	@Override
	protected Resource delegatedGetResource(URI uri, boolean loadOnDemand) {
		Resource val = null;
		try {
			val = super.delegatedGetResource(uri, loadOnDemand);
		} catch (WrappedException ex) {

		}
		if (val == null && uri.toString().startsWith("http")
				&& !missingEpackages.contains(uri.toString())) {
			try {
				// IWorkspaceRoot root =
				// ResourcesPlugin.getWorkspace().getRoot();

				IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

				IProject[] projects = root.getProjects();

				for (IProject iProject : projects) {
					if (!iProject.isOpen())
						continue;

					LinkedList<File> ecoreFiles = EMFModelManager.findEcoreFiles(iProject.getLocation().toFile().listFiles());

					for (File file : ecoreFiles) {
						Resource resource2 = loadEcoreModel(uri,
								file.getAbsolutePath());
						if (resource2 != null){
							resource2.setURI(uri);
							return resource2;
						}
							
					}
				}

				Shell shell = new Shell();
				FileDialog dialog = new FileDialog(shell);
				dialog.setText("EMF Package with URI \""
						+ uri.toString()
						+ "\" could not be found. Please select the ECore Model File for this URI.");
				dialog.setFilterExtensions(new String[] { "*.ecore" });

				// dialog.setText(uri.toString());

				String p = "";

				if (MuvitorActivator.getDefault().isDebugging())
					dialog.open();
				else
					return super.delegatedGetResource(uri, loadOnDemand);
				shell.dispose();
				return loadEcoreModel(uri, p);
			} catch (Exception e) {
				return super.delegatedGetResource(uri, loadOnDemand);
			}
		}
		if (val != null && val.getURI().toString().startsWith("file")){
			
		}
		return val;
	}

	private Resource loadEcoreModel(URI uri, String fileLoc) {
		try {
			if (fileLoc == null)
				return null;
			ResourceSet set = new ResourceSetImpl();
			ResourceImpl r = (ResourceImpl) set.getResource(
					URI.createFileURI(fileLoc), true);

			boolean foundMulti = true;
			boolean found = false;

			LinkedList<String> foundURIs = new LinkedList<String>();
			LinkedList<EPackage> foundPkgs = new LinkedList<EPackage>();
			for (EObject pkg : r.getContents()) {
				if (pkg instanceof EPackage) {
					if (((EPackage) pkg).getNsURI().equals(uri.toString())) {
						found = true;
					}
					foundMulti &= ((EPackage) pkg).getNsURI().startsWith(
							uri.toString());

					foundURIs.add(((EPackage) pkg).getNsURI());
					foundPkgs.add((EPackage) pkg);
				}
			}
			if (foundURIs.isEmpty())
				foundMulti = false;
			if (!found && !foundMulti) {

				missingEpackages.add(uri.toString());

				return super.delegatedGetResource(uri, true);

			}

			if (!found && foundMulti) {
				// missingEpackages.add(uri.toString());
				EPackageImpl ePackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE.createEPackage();
				ePackage.setName(uri.segment(0));
				ePackage.setNsPrefix(uri.segment(0));
				ePackage.setNsURI(uri.toString());
				ePackage.eSetResource(r, null);
				EPackage.Registry.INSTANCE.put(uri.toString(), ePackage);
				for (EObject obj : r.getContents()) {
					if (obj instanceof EPackage) {
						ePackage.getESubpackages().add((EPackage) obj);

					}
				}

				// r.getContents().add(0, ePackage);
			}

			//r.setURI(uri);
			for (EPackage pkg : foundPkgs) {
				if (!found && foundMulti) {
					ResourceImpl r1 = (ResourceImpl) set.getResource(
							URI.createFileURI(fileLoc), true);
					r1.setURI(URI.createURI(pkg.getNsURI()));
					r1.getContents().clear();

					r1.getContents().add(pkg);
					EMFModelManager.resourceSet.getResources().add(r1);
				}
				EPackage.Registry.INSTANCE.put(((EPackage) pkg).getNsURI(),
						pkg);
			}
			/**/
			// EPackage.Registry.INSTANCE.get(uri.toString());
			if (found || foundMulti)
				EMFModelManager.resourceSet.getResources().add(r);

			return super.delegatedGetResource(uri, true);
		} catch (WrappedException ex) {

		}
		return null;
	}
}
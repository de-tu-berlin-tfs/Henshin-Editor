package de.tub.tfs.henshin.analysis.ui;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import agg.xt_basis.GraGra;
import de.tub.tfs.henshin.analysis.AggInfo;

public class TranslateAgg extends AbstractHandler {

	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getActiveMenuSelection(event);
		Object firstElement = selection.getFirstElement();
		
		if (firstElement instanceof IFile) {
			IFile file = (IFile) firstElement;
			
			String filename = file.getRawLocationURI().getRawPath();
			
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource res = resourceSet.getResource(
					URI.createFileURI(filename), true);
			res.unload();
			try {
				res.load(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Object obj: res.getContents()) {
				if (obj instanceof Module) {
					String missingPackages = "missing packages:\n";
					for (EPackage pkg : ((Module)obj).getImports() ) {
						if(((EPackage)pkg).getName() == null){
							
							Resource resource = loadEcoreModelsWithUri( ((EPackageImpl)pkg).eProxyURI(), ((Module) obj).eResource().getResourceSet());
							if (resource == null){
								missingPackages += (((EPackageImpl)pkg).eProxyURI()) + "\n";
							}
						}							
					}
					try {
						AggInfo aggInfo = new AggInfo((Module)obj);
						System.out.println(res.getURI().toFileString());
						aggInfo.getAggGrammar().save(res.getURI().toFileString() + ".ggx");
					}catch (Exception ex){
						if (missingPackages.length() > 18)
							ErrorDialog.openError(Display.getDefault().getActiveShell(), "Convert failed!", "Could not find all referenced packages.\n",new Status(IStatus.ERROR,"org.eclipse.emft.henshin",missingPackages));
					}
					//HenshinRegistry.instance.registerTransformationSystem((Module) obj);
				}
			}
		}
		
		return null;
	}


	public static GraGra exportToAGG(String henshinFileName,String exportFileName){
		String filename = henshinFileName;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource res = resourceSet.getResource(
				URI.createFileURI(filename), true);
		res.unload();
		try {
			res.load(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (Object obj: res.getContents()) {
			if (obj instanceof Module) {
				String missingPackages = "missing packages:\n";
				for (EPackage pkg : ((Module)obj).getImports() ) {
					if(((EPackage)pkg).getName() == null){
						
						Resource resource = loadEcoreModelsWithUri( ((EPackageImpl)pkg).eProxyURI(), ((Module) obj).eResource().getResourceSet());
						if (resource == null){
							missingPackages += (((EPackageImpl)pkg).eProxyURI()) + "\n";
						}
					}							
				}
				try {
					AggInfo aggInfo = new AggInfo((Module)obj);
				
					aggInfo.getAggGrammar().save(res.getURI().toFileString() + ".ggx");
					return aggInfo.getAggGrammar();
				}catch (Exception ex){
					if (missingPackages.length() > 18)
						ErrorDialog.openError(Display.getDefault().getActiveShell(), "Convert failed!", "Could not find all referenced packages.\n",new Status(IStatus.ERROR,"org.eclipse.emft.henshin",missingPackages));
				}
				//HenshinRegistry.instance.registerTransformationSystem((Module) obj);
			}
		}
		return null;
	}

	
	public static GraGra exportToAGG(Module system,String exportFileName){
		String missingPackages = "missing packages:\n";
		for (EPackage pkg : system.getImports() ) {
			if(((EPackage)pkg).getName() == null){
				Resource resource = loadEcoreModelsWithUri( ((EPackageImpl)pkg).eProxyURI(), system.eResource().getResourceSet());
				if (resource == null){
					missingPackages += (((EPackageImpl)pkg).eProxyURI()) + "\n";
				}
			}							
		}
		try {
			AggInfo aggInfo = new AggInfo(system);
			aggInfo.getAggGrammar().save(exportFileName + ".ggx");
			return aggInfo.getAggGrammar();
		}catch (Exception ex){
			if (missingPackages.length() > 18)
				ErrorDialog.openError(Display.getDefault().getActiveShell(), "Convert failed!", "Could not find all referenced packages.\n",new Status(IStatus.ERROR,"org.eclipse.emft.henshin",missingPackages));
		}
		return null;
		//HenshinRegistry.instance.registerTransformationSystem((Module) obj);

	}
	
	
	private static HashSet<String> missingEpackages = new HashSet<String>();
	private static ResourceSet resourceSet = new ResourceSetImpl();
	private static Resource loadEcoreModel(URI uri, String fileLoc) {
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

				return null;//resourceSet.delegatedGetResource(uri, true);

			}

			if (!found && foundMulti) {
				// missingEpackages.add(uri.toString());
				EPackageImpl ePackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE
						.createEPackage();
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

			r.setURI(uri);
			for (EPackage pkg : foundPkgs) {
				if (!found && foundMulti) {
					ResourceImpl r1 = (ResourceImpl) set.getResource(
							URI.createFileURI(fileLoc), true);
					r1.setURI(URI.createURI(pkg.getNsURI()));
					r1.getContents().clear();

					r1.getContents().add(pkg);
					resourceSet.getResources().add(r1);
				}
				EPackage.Registry.INSTANCE.put(((EPackage) pkg).getNsURI(),
						pkg);
			}
			/**/
			// EPackage.Registry.INSTANCE.get(uri.toString());
			if (found || foundMulti)
				resourceSet.getResources().add(r);

			return r;//super.delegatedGetResource(uri, true);
		} catch (WrappedException ex) {

		}
		return null;
	}



	private static LinkedList<File> findEcoreFiles(File[] listFiles) {
		LinkedList<File> result = new LinkedList<File>();
		if (listFiles == null)
			return result;
		for (File file : listFiles) {
			if (file.isDirectory()) {
				LinkedList<File> f = findEcoreFiles(file.listFiles());
				result.addAll(f);
			} else {
				if (file.getName().endsWith("ecore"))
					result.add(file);
			}
		}
		return result;
	}

	public static Resource loadEcoreModelsWithUri(URI uri,ResourceSet context){
		try {
			// IWorkspaceRoot root =
			// ResourcesPlugin.getWorkspace().getRoot();

			if (context != null)
				resourceSet = context;
			if (uri.toString().contains("#")){
				uri = URI.createURI(uri.toString().substring(0, uri.toString().indexOf("#")));
			}
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace()
					.getRoot();

			IProject[] projects = root.getProjects();

			for (IProject iProject : projects) {
				LinkedList<File> ecoreFiles = findEcoreFiles(iProject
						.getLocation().toFile().listFiles());

				for (File file : ecoreFiles) {
					Resource resource2 = loadEcoreModel(uri,
							file.getAbsolutePath());
					if (resource2 != null)
						return resource2;
				}
			}

			Shell shell = new Shell();
			FileDialog dialog = new FileDialog(shell);
			dialog.setText("EMF Package with URI \""
					+ uri.toString()
					+ "\" could not be found. Please select the ECore Model File for this URI.");
			dialog.setFilterExtensions(new String[] { "*.ecore" });

			// dialog.setText(uri.toString());

			String p = dialog.open();
			shell.dispose();
			Resource r = loadEcoreModel(uri, p);
			return r;
		} catch (Exception e) {
			return null;
		}
		
	}

	
}

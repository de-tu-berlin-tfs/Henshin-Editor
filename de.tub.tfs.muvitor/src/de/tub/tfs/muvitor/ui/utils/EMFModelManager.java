package de.tub.tfs.muvitor.ui.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.ResourceEntityHandlerImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMLHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.muvitor.ui.MuvitorActivator;

/**
 * Manager for persistence operations on an EMF model.
 * 
 * @author Tony Modica
 */
public class EMFModelManager {

	
	
	private static final class DelegatingEFactory extends EFactoryImpl {
		
		private EFactory delegate;
		private EPackage delegatePackage;
		public DelegatingEFactory(EFactory delegate,EPackage del) {
			this.delegate = delegate;
			this.delegatePackage = del;
		}
		
		@Override
		public EObject create(EClass eClass) {
			if (replacedClasses.contains(eClass))
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

	private static HashMap<EPackage,HashMap<String, EClassifier>> conversionsClass = new HashMap<EPackage, HashMap<String,EClassifier>>();
	private static HashSet<EClassifier> replacedClasses = new HashSet<EClassifier>();
	
	public static boolean registerClassConversion(EPackage sourceUri,String sourceClass,EClassifier targetClass){
		if (!(sourceUri.getEFactoryInstance() instanceof DelegatingEFactory))
			sourceUri.setEFactoryInstance(new DelegatingEFactory(sourceUri.getEFactoryInstance(),sourceUri));
		
		HashMap<String, EClassifier> hashMap = conversionsClass.get(sourceUri);
		if (hashMap == null)
			conversionsClass.put(sourceUri, hashMap = new HashMap<String, EClassifier>());
		hashMap.put(sourceClass, targetClass);
		
		replacedClasses.add(targetClass);
		
		return true;
	}
	
	
    /**
     * The ResourceSet
     */
    private static final ResourceSet resourceSet = new ResourceSetImpl() {

	private HashSet<String> missingEpackages = new HashSet<String>();

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

			LinkedList<File> ecoreFiles = findEcoreFiles(iProject.getLocation().toFile().listFiles());

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

		return super.delegatedGetResource(uri, true);
	    } catch (WrappedException ex) {

	    }
	    return null;
	};

    };

    protected static LinkedList<File> findEcoreFiles(File[] listFiles) {
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

    private final Map<String, Object> options = new HashMap<String, Object>();

    /**
     * FIXED: This ensures compatibility if models are changed from using
     * {@link ENamedElement}s to using a custom NamedElement with default name
     * "" or vice versa. All names of {@link ENamedElement} that are
     * <code>null</code> are set to " " when saving the file.
     * 
     * FIXED: Additionally, we do not allow empty names "", because these
     * models' URIs can not be used to resolve the models again. This only
     * affects old models, MuvitorTreeEditor cares about this before saving as
     * well.
     */
    private static void recursiveSetNamesIfUnset(final List<EObject> models) {
    	for (final EObject model : models) {
    		if (model instanceof ENamedElement) {
    			final ENamedElement namedElement = (ENamedElement) model;
    			final String name = namedElement.getName();
    			if (name == null || name.equals("")) {
    				namedElement.setName(" ");
    			}
    			recursiveSetNamesIfUnset(model.eContents());
    		}
    		recursiveSetNamesIfUnset(model.eContents());
    	}
    }

    /**
     * The top level models in the resource.
     */
    private List<EObject> models = null;

    /**
     * In EMF, a Resource provides the way to have access to the model content.
     */
    private Resource resource = null;

    /**
     * This constructor initializes the EMF model package and registers a file
     * extension.
     * 
     * @param extension
     *            The file extension
     */

    public EMFModelManager(final String extension) {

	options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
	options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
	options.put(XMLResource.OPTION_EXTENDED_META_DATA, new BasicExtendedMetaData(){
	
		
		
		@Override
		public EClassifier getType(EPackage ePackage, String name) {
			
			HashMap<String, EClassifier> map = conversionsClass.get(ePackage);
			if (map != null){
				EClassifier cl = map.get(name);
				if (cl == null)
					return super.getType(ePackage, name);
				return cl;
			}
			return super.getType(ePackage, name);
		}
		
		
	
	});
	
	
	/*
	 * Register the XMI resource factory for the editor's file extension. We
	 * use a subclass that creates a special XMIResource that makes use of
	 * UUID to support EMF Clipboard as described in GMF's
	 * "Tutorial: EMF Clipboard Copy and Paste"
	 */
	final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
	reg.getExtensionToFactoryMap().put(extension,
		new XMIResourceFactoryImpl() {
		    @Override
		    public Resource createResource(final URI uri) {
			return new XMIResourceImpl(uri) {
			    @Override
			    protected boolean useUUIDs() {
				return true;
			    }
			    
			};
		    }
		});
    }

    /**
     * Loads the model from the file. It this fails then the passed list of
     * default models will be set in the resource for saving later with
     * {@link #save(IPath)}. Return the loaded or the default models,
     * respectively.
     * 
     * @param path
     *            A {@link IPath} to a file containing a {@link Resource}.
     * @param defaultModels
     *            a list of default models to use when loading fails
     * @return the loaded models or the default models
     */
    public List<EObject> load(final IPath path,
	    final List<EObject> defaultModels) {

	try {
		
		
		resource = resourceSet.getResource(
		    URI.createPlatformResourceURI(path.toString(), true), true);
	    /*
	     * FIX: without calling unload() reverting does not work correctly
	     */
	    resource.unload();
	    resource.load(options);
	    /*
	     * copy the contents because the resource will be emptied on
	     * "save as"
	     */
	    models = new BasicEList<EObject>(resource.getContents());

	} catch (final Exception e) {
	    // FIXME eigentlich sollte getResource schon eine Resource erzeugen
	    // something failed, so try again without loading the model and use
	    // the defaultModel instead
	    if (resource == null) {
		// create a resource if getting one has failed before
		resource = resourceSet.createResource(URI.createPlatformResourceURI(
			path.toString(), true));
	    }
	    if (resource == null) {
		MuvitorActivator.logError(
			"Unerwarteter Fehler in EMFModelmanager: Keine Resource erzeugbar.",
			e);
		throw new NullPointerException();
	    }
	    resource.getContents().clear();
	    resource.getContents().addAll(defaultModels);
	    models = defaultModels;
	}
	recursiveSetNamesIfUnset(models);
	return models;
    }

    public void save(final IPath path, boolean isPlatform) throws IOException {
	// This sets the model as contents in a new resource when using save as.
	URI uri = isPlatform ? URI.createPlatformResourceURI(path.toString(),
		true) : URI.createFileURI(path.toString());

	try {
	    resource = resourceSet.getResource(uri, true);
	} catch (final Exception e) {
	    resource = resourceSet.createResource(uri);
	}

	options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);

	options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
	recursiveSetNamesIfUnset(models);
	resource.getContents().clear();
	resource.getContents().addAll(models);
	resource.save(options);

    }

    /**
     * Saves the content of the model to the file.
     */
    public void save(final IPath path) throws IOException {
	save(path, true);
    }
}

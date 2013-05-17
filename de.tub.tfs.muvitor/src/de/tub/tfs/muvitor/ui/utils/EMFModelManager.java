package de.tub.tfs.muvitor.ui.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
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
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.XMLSave;
import org.eclipse.emf.ecore.xmi.impl.SAXXMIHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMILoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.ecore.xmi.impl.XMISaveImpl;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.xml.sax.helpers.DefaultHandler;

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
	private static HashMap<EClassifier,String> replacedClassesToStringMap = new HashMap();

	private IProgressMonitor monitor = null;
	private int lastLine = 0;
	
	public static boolean registerClassConversion(EPackage sourceUri,String sourceClass,EClassifier targetClass,SaveDelegate delegate,LoadDelegate load){
	if (!(sourceUri.getEFactoryInstance() instanceof DelegatingEFactory)){
			
			sourceUri.setEFactoryInstance(new DelegatingEFactory(sourceUri.getEFactoryInstance(),sourceUri));
		}
		HashMap<String, EClassifier> hashMap = conversionsClass.get(sourceUri);
		if (hashMap == null)
			conversionsClass.put(sourceUri, hashMap = new HashMap<String, EClassifier>());
		hashMap.put(sourceClass, targetClass);
		saveDelegates.put(targetClass, delegate);
		loadDelegates.put(targetClass, load);
		replacedClasses.add(targetClass);
		replacedClassesToStringMap.put(targetClass, sourceClass);
		return true;
	}

	private static HashMap<EClassifier,SaveDelegate> saveDelegates = new HashMap<>();
	private static HashMap<EClassifier,LoadDelegate> loadDelegates = new HashMap<>();

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

	public static FragmentResource requestFragmentResource(Resource r){
		
		try {
			return (FragmentResource) resourceSet.getResource(r.getURI().appendFileExtension("fragment"),true);
		} catch (Exception ex){
			return (FragmentResource) resourceSet.getResource(r.getURI().appendFileExtension("fragment"),true);
		}
		
		
	}
	
	public static FragmentResource getFragmentResource(Resource r){
		
		return (FragmentResource) resourceSet.getResource(r.getURI().appendFileExtension("fragment"), false);
	}
	
	
	private static HashMap<String, EMFModelManager> modelmanager = new HashMap<String,EMFModelManager>();
	public static EMFModelManager createModelManager(String extension){
		EMFModelManager m = modelmanager.get(extension);
		if (m == null) {
			m = new EMFModelManager(extension);
			modelmanager.put(extension, m);
		}
		return m;
	}
	
	private EMFModelManager(final String extension) {

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

			@Override
			public EClassifier getType(String namespace, String name) {
				// TODO Auto-generated method stub
				return super.getType(namespace, name);
			}

		});


		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		reg.getExtensionToFactoryMap().put("fragment" ,
				new XMIResourceFactoryImpl() {
			
			
				@Override
				public Resource createResource(final URI uri) {
					
					return new FragmentResource(uri);
				}
			}
		);


		/*
		 * Register the XMI resource factory for the editor's file extension. We
		 * use a subclass that creates a special XMIResource that makes use of
		 * UUID to support EMF Clipboard as described in GMF's
		 * "Tutorial: EMF Clipboard Copy and Paste"
		 */
		reg.getExtensionToFactoryMap().put(extension,
				new XMIResourceFactoryImpl() {
			@Override
			public Resource createResource(final URI uri) {
				return new XMIResourceImpl(uri) {
										
					@Override
					protected boolean useUUIDs() {
						return true;
					}

					@Override
					public void save(Map<?, ?> options) throws IOException {
						// TODO Auto-generated method stub
						FragmentResource fragmentResource = requestFragmentResource(this);
						fragmentResource.getContents().clear();
						try {
							super.save(options);
								
						} catch (Exception ex){
							fragmentResource = getFragmentResource(this);
							if (fragmentResource != null){

								fragmentResource.save(null);
								fragmentResource.cleanUp();
							}
							ex.printStackTrace();
							throw ex;
						}
						fragmentResource = getFragmentResource(this);
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

								if (replacedClasses.contains(c)){
									for (EClass cl : c.getEAllSuperTypes()) {
										if (cl.getName().equals(replacedClassesToStringMap.get(c)))
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
									
								
									
									if (extendedMetaData != null)
									{
										return extendedMetaData.getType(ePackage, typeName);
									}
									else
									{
										
										HashMap<String, EClassifier> map = conversionsClass.get(ePackage);
										if (map != null){
											EClassifier cl = map.get(typeName);
											if (cl == null)
												return ePackage.getEClassifier(typeName);
											return cl;
										}	
									}
								}
								return super.getType(eFactory, typeName);
							}
						};
						
					}
					
					@Override
					public void load(Map<?, ?> options) throws IOException {
						
						FragmentResource fragmentResource = getFragmentResource(this);
						if (fragmentResource != null){
							fragmentResource.cleanUp();
						}
						super.load(options);
						
						fragmentResource = getFragmentResource(this);
						if (fragmentResource != null)
							fragmentResource.cleanUp();
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
									protected void processObject(
											EObject object) {
										super.processObject(object);
										
										if (object != null && replacedClasses.contains(object.eClass())){
											loadDelegates.get(object.eClass()).doLoad(object);
										}	
										
										if (monitor != null){
											int line = getLineNumber();
											
											final int work = line - lastLine;
											if (work < 20)
												return;
											lastLine = line;
											Display.getDefault().asyncExec(new Runnable() {
												
												@Override
												public void run() {
													monitor.worked(work);
													
												}
											});
											if (monitor.isCanceled())
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
								if (replacedClasses.contains(o.eClass()) && o.eClass().getEStructuralFeatures().contains(f)){
									
									boolean result = saveDelegates.get(o.eClass()).shouldSkipSave(o, f);
																			
											
									return result;
								}
								return false;
							}


							@Override
							protected boolean saveFeatures(EObject o, boolean attributesOnly)
							{
								EClass eClass = o.eClass();   
								
								if (replacedClasses.contains(eClass)){
									requestFragmentResource(o.eResource()).getContents().add(o);
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
									
									if (checkForDelegates(o,features[i]))
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

			resourceSet.getLoadOptions().putAll(options);
			resource = resourceSet.getResource(
					URI.createPlatformResourceURI(path.toString(), true), false);
			if (resource != null)
				resource.unload();
			resource = resourceSet.getResource(
					URI.createPlatformResourceURI(path.toString(), true), true);
			
			/*
			 * FIX: without calling unload() reverting does not work correctly
			 */
			//resource.unload();
			//resource.load(options);
			/*
			 * copy the contents because the resource will be emptied on
			 * "save as"
			 */
			models = new BasicEList<EObject>(resource.getContents());

		} catch (final Exception e) {
			e.printStackTrace();
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

	public void save(final IPath path, boolean isPlatform,EObject... rootObjects) throws IOException {
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
		if (rootObjects.length > 0)
			resource.getContents().addAll(Arrays.asList(rootObjects));
		else
			resource.getContents().addAll(models);
		resource.save(options);

	}

	/**
	 * Saves the content of the model to the file.
	 */
	public void save(final IPath path,EObject... rootObjects) throws IOException {
		save(path, true,rootObjects);
	}
	
	public void setMonitor(IProgressMonitor mon){
		this.monitor = mon;
		this.lastLine = 0;
	}
	
}

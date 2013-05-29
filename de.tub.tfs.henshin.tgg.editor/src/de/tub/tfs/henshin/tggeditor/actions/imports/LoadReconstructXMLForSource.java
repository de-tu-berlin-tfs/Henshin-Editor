package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.BasicEObjectImpl;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLMapImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.FileSelectionDialog;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.eclipse.xsd.util.XSDResourceFactoryImpl;
import org.w3c.dom.Document;

import com.sun.org.apache.xalan.internal.lib.ExsltDynamic;

import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class LoadReconstructXMLForSource extends SelectionAction {

	static final String XML_ELEMENT_TEXT = "tggXmlElementText";
	public static final String MIXEDELEMENTFEATURE = "tggMixedElementText";
	public static final String BASESCHEME = "de.tub.tfs.tgg.generated.xml";

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadReconstructXMLForSource";
	private Module transSys;
	private boolean loadedPackage;
	private EPackage p;



/*	private final class DelegatingMetaData extends BasicExtendedMetaData {
		
		private EPackage pkg;
		private Stack<String> stack;
		private String root;
		private EClass docRoot = null;
		
		public DelegatingMetaData(EPackage pkg,String documentRoot,Stack<String> stack) {
			this.pkg = pkg;
			this.stack = stack;
			this.root = documentRoot;
		}
		
		@Override
		public EStructuralFeature demandFeature(String namespace,
				String name, boolean isElement, boolean isReference) {
			String container;
			if (root.equals(name)
					&& stack.size() <= 1) {

				return docRoot.getEStructuralFeature(name);
			}
			name = name.toLowerCase();
			if (stack.size() > 0) {
				if (!isReference) {
					container = stack
							.elementAt(stack.size() - 1);

					EClass type = (EClass) this.demandType(
							namespace, container);

					return type.getEStructuralFeature(name);
				} else if (stack.size() > 1) {
					container = stack
							.elementAt(stack.size() - 2);

					EClass type = (EClass) this.demandType(
							namespace, container);

					return type.getEStructuralFeature(name);
				}
			}

			throw new RuntimeException("Feature not found: " + name);
		}

		@Override
		public EPackage demandPackage(String namespace) {
			System.out.println(namespace);
			return pkg;
		}

		@Override
		public EClassifier demandType(String namespace, String name) {
			if (root == null)
				root = name;
			System.out.println("demanded " + name);

			return pkg.getEClassifier(name);
		}

		@Override
		public EStructuralFeature getAttribute(EClass eClass,
				String namespace, String name) {
			EStructuralFeature feature = eClass
					.getEStructuralFeature(name);
			if (feature != null)
				return feature;
			return super.getAttribute(eClass, namespace, name.toLowerCase());
		}

		@Override
		public EStructuralFeature getAttribute(String namespace,
				String name) {
			// TODO Auto-generated method stub
			return super.getAttribute(namespace, name);
		}

		@Override
		public EClass getDocumentRoot(EPackage ePackage) {
			if (docRoot == null) {
				if (ePackage.getEClassifier("DocumentRoot") != null){
					docRoot = (EClass) ePackage.getEClassifier("DocumentRoot");
					
					if (docRoot.getEStructuralFeature(root) == null){
						EStructuralFeature feature = super.demandFeature(
								null, root, true, true);
						EClass type = (EClass) this.demandType(null,
								root);
						feature.setEType(type);

						docRoot.getEStructuralFeatures().add(feature);
					}
					
					return docRoot;
				}
				
				docRoot = EcoreFactory.eINSTANCE.createEClass();
				docRoot.setName("DocumentRoot");
				pkg.getEClassifiers().add(docRoot);
				super.setDocumentRoot(docRoot);
				String rootFeat = root.toLowerCase();
				EStructuralFeature feature = super.demandFeature(
						null, rootFeat, true, true);
				EClass type = (EClass) this.demandType(null,
						root);
				feature.setEType(type);

				this.setAnnotation(feature, "name", root);
				this.setAnnotation(feature, "kind", "attribute");
				this.setAnnotation(feature, "namespace", "##targetNamespace");
				
				
				docRoot.getEStructuralFeatures().add(feature);

			}
			return docRoot;
		}
		
		private void setAnnotation(EModelElement eClassifier,String name,String value){
			EAnnotation eAnnotation = getAnnotation(eClassifier, true);
		    eAnnotation.getDetails().put(name, value); 
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

		@Override
		public EAttribute getMixedFeature(EClass eClass) {
			if (pkg.getEClassifiers().contains(
					eClass)) {
				return (EAttribute) eClass
						.getEStructuralFeature(MIXEDELEMENTFEATURE);
			}
			return super.getMixedFeature(eClass);
		}

		@Override
		public EClassifier getType(EPackage ePackage, String name) {
			if (name == "")
				return docRoot;
			if (ePackage == null
					|| ePackage.equals(pkg))
				return pkg.getEClassifier(name);
			return super.getType(ePackage, name);
		}

		@Override
		public EClassifier getType(String namespace, String name) {
			return pkg.getEClassifier(name);
		}

		@Override
		public boolean isDocumentRoot(EClass eClass) {
			return eClass.equals(docRoot);
		}

		@Override
		public void setDocumentRoot(EClass eClass) {
			docRoot = eClass;
		}
		

		@Override
		public String getName(EClassifier eClassifier) {

			String name = super.getName(eClassifier);
			if (name.endsWith("_._type")){
				name = name.substring(0, name.length() - 7);
			}
			return name;
		}
		
		
	}*/

	public LoadReconstructXMLForSource(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Load XML File as Source Model");
		setToolTipText("Loads XML File and reconstructs model.");
	}

	private HashMap<String, Object> buildOptions(Map<Object,EStructuralFeature> map) {
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_ANY_TYPE, null);
		
		options.put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
				Boolean.TRUE);
		options.put(
				XMLResource.OPTION_RECORD_ANY_TYPE_NAMESPACE_DECLARATIONS,
				Boolean.TRUE);

		XMLOptions xmlOptions = (XMLOptions) options.get(
				XMLResource.OPTION_XML_OPTIONS);
		if (xmlOptions != null) {
			xmlOptions.setProcessSchemaLocations(true);

		} else {
			xmlOptions = new XMLOptionsImpl();
			xmlOptions.setProcessAnyXML(false);
			xmlOptions.setProcessSchemaLocations(true);

			options.put(XMLResource.OPTION_XML_OPTIONS, xmlOptions);
		}
		options.put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, map);
		
		return options;
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
				transSys = (Module) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}

	private void cleanUp() {
//		HashMap<String, Object> options = buildOptions();
//		reconstructedPackage = null;
//
//		anyTypetoObjectMapping = new HashMap<AnyType, EObject>();
//
//		currentElement = new Stack<String>();
//
//		documentRoot = "";
//		contextURI = BASESCHEME;
//		docRoot = null;
//
//		reconstructedPackage = null;
//		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
//		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
//		options.put(XMLResource.OPTION_ANY_TYPE, null);
//		addMetaData();
		
		
		ReconstructingMetaData.cleanExtendedMetaData(p);
	}

	private void exportGeneratedEcoreModel(EPackage p, String uri) {
		if (uri == null && p.eResource() != null){
			try {
				p.eResource().save(null);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		
		
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("ecore", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Create a resource
		Resource resource = null;
		try {
			resource = resSet.createResource(URI.createFileURI(uri
					+ "reconstructedModel("
					+ p.getName().substring(p.getName().lastIndexOf("/") + 1)
					+ ").ecore"));

		} catch (Exception ex) {

		}

		resource.getContents().add(p);

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public BasicExtendedMetaData extractModelInformation(String xmlURI,final Stack<String> stack,BasicExtendedMetaData data) {
		
		if (!(data instanceof ReconstructingMetaData))
			return data;
		
		String fileType = xmlURI.substring(xmlURI.lastIndexOf(".")+1);
		
			ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				fileType, new XMLResourceFactoryImpl() {
					@Override
					public Resource createResource(final URI uri) {
						return new XMLResourceImpl(uri) {
							@Override
							protected XMLLoad createXMLLoad() {
								return new LoggingXMLLoad(createXMLHelper(),stack);
							}

							@Override
							protected boolean useUUIDs() {
								return false;
							}
						};

					}
				});

		HashMap<Object, EStructuralFeature> map =  new HashMap<Object, EStructuralFeature>(){
			
			@Override
			public EStructuralFeature put(Object key,
					EStructuralFeature value) {
				return null;
			}
		};
		HashMap<String, Object> options = buildOptions(map);
		if (data instanceof ReconstructingMetaData){
			((ReconstructingMetaData) data).setMap(map);
			((ReconstructingMetaData) data).setCurrentElement(stack);
		}
		options.put(XMLResource.OPTION_EXTENDED_META_DATA,data);
		
		
		ResourceSetImpl set = new ResourceSetImpl() {
			@Override
			public Resource getResource(URI uri, boolean loadOnDemand) {
				return super.getResource(uri, loadOnDemand);
			}

			@Override
			protected void handleDemandLoadException(Resource resource,
					IOException exception) throws RuntimeException {
				
				super.handleDemandLoadException(resource, exception);
			}
		};
		set.getLoadOptions().putAll(options);
		

		Resource resource = null;
		try {
			resource = set.getResource(URI.createFileURI(xmlURI), true);
		} catch (Exception ex) {
			try {
				if (resource == null)
					resource = set.getResource(URI.createFileURI(xmlURI), true);
			} catch (Exception ex1){

			}
		}
		if (resource != null)
			try{
				resource.unload();
			} catch (Exception ex){

			}
		HashSet<EStructuralFeature> criticalFeatures = new HashSet<EStructuralFeature>();
		HashSet<EStructuralFeature> invalidFeat = new HashSet<EStructuralFeature>();
		
		
		EPackage reconstructedPackage = ((ReconstructingMetaData) data).getReconstructedPackage();
		
		for (EClassifier clazz : reconstructedPackage.getEClassifiers()) {
			if (clazz instanceof EClass){
				EClass eclass = (EClass) clazz;
				for (EStructuralFeature feat : eclass.getEStructuralFeatures()) {
					if (feat instanceof EReference)
						if (((EClass) feat.getEType()).getEStructuralFeatures()
								.isEmpty())
							criticalFeatures.add(feat);
						else if (((EClass) feat.getEType())
								.getEStructuralFeatures().size() == 2
								&& ((EClass) feat.getEType())
								.getEStructuralFeatures().get(0).getName()
								.equals(MIXEDELEMENTFEATURE)&& ((EClass) feat.getEType())
								.getEStructuralFeatures().get(1).getName()
								.equals(XML_ELEMENT_TEXT) )
							criticalFeatures.add(feat);
					if (feat instanceof EAttribute){
						if (feat.getName() == null || feat.getName().isEmpty()){
							invalidFeat.add(feat);
						}else 
							if (!(((EAttribute)feat).getEType() instanceof EDataType )){
								criticalFeatures.add(feat);
							}
					}
				}
			}
		}

		for (EStructuralFeature eStructuralFeature : criticalFeatures) {
			EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
			eAttribute.setName(eStructuralFeature.getName());
			eAttribute.setEType(EcorePackage.Literals.ESTRING);
			eAttribute.setDerived(false);
			eAttribute.setTransient(false);
			eAttribute.setVolatile(false);
			eAttribute.setUpperBound(1);

			EAnnotation eAnnotation = eAttribute.getEAnnotation("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
			if (eAnnotation == null){
				eAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
				eAnnotation.setSource("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
				eAttribute.getEAnnotations().add(eAnnotation);
				
			}
			
			eAnnotation.getDetails().put( "name", eStructuralFeature.getEAnnotation("http:///org/eclipse/emf/ecore/util/ExtendedMetaData").getDetails().get("name"));
			eAnnotation.getDetails().put( "kind", "element");
			eAnnotation.getDetails().put( "namespace", "##targetNamespace");
			
			reconstructedPackage.getEClassifiers().remove(
					reconstructedPackage.getEClassifier(eStructuralFeature
							.getName()));

			EClass cont = (EClass) eStructuralFeature.eContainer();
			cont.getEStructuralFeatures().add(eAttribute);
			cont.getEStructuralFeatures().remove(eStructuralFeature);

		}
		
		for (EStructuralFeature eStructuralFeature : invalidFeat) {
			((List)eStructuralFeature.eContainer().eGet(eStructuralFeature.eContainingFeature())).remove(eStructuralFeature);
		}
		
		map.clear();
		stack.clear();
		if (loadedPackage){
			
		} else {
			//Resource resource2 = transSys.eResource().getResourceSet()
			//	.createResource(URI.createURI(reconstructedPackage.getNsURI()));
			//resource2.getContents().add(reconstructedPackage);
			//transSys.eResource().getResourceSet().getResources().add(resource2);
		}
		return data;
	}
	
	@Override
	public void run() {
		Shell shell = new Shell();
		try {
			FileDialog dialog = new FileDialog(shell,SWT.MULTI);
	
		
			dialog.setText("Please select the xml file you want to import.");
			String str = dialog.open();
			str = str.substring(0, str.lastIndexOf(File.separator + "")+1);
			for (String xmlURI : dialog.getFileNames()){
				xmlURI = str + xmlURI;
				Stack<String> stack = new Stack<String>();
				BasicExtendedMetaData data = null;
				data = loadModelInformations(xmlURI);
				try {
					data = extractModelInformation(xmlURI,stack,data);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Resource r = null;
				ResourceSet set = new ResourceSetImpl(){
					@Override
					public Resource getResource(URI uri, boolean loadOnDemand) {

						return super.getResource(uri, loadOnDemand);
					}
				};

				if (data instanceof ReconstructingMetaData){

					p = ((ReconstructingMetaData) data).getReconstructedPackage();

					exportGeneratedEcoreModel(p,
							xmlURI.substring(0, xmlURI.lastIndexOf(File.separator))
							+ File.separator);
				} else {

					
				}

				cleanUp();
			}


			boolean r = MessageDialog.openQuestion(shell, "XSD Replacement", "Do you want to create compatibility entries with an existing XSD Model?");
			if (r){
				FileDialog d = new FileDialog(shell);
				d.setText("Please select the ecore model corresponding to the XSD.");
				
			
				
				String ecoreModel = d.open();
				ResourceSet set = new ResourceSetImpl(); 
				
				
				EObject resPkg = null;
				if (ecoreModel.endsWith("ecore")){
					Resource resource = set.getResource(URI.createFileURI(ecoreModel), true);
					resPkg = resource.getContents().get(0);
				} else {
					XSDEcoreBuilder xsdEcoreBuilder = new XSDEcoreBuilder();
					Collection<EObject> generatedPackages = xsdEcoreBuilder.generate(URI.createFileURI(ecoreModel));

					// register the packages loaded from XSD
					for (EObject generatedEObject : generatedPackages) {
					    if (generatedEObject instanceof EPackage) {
					    	if (resPkg == null){
					    		resPkg = generatedEObject;
					    		break;
					    	}
					    }
					}

				}
				
				
				if (resPkg instanceof EPackage){
					EPackage pkg = (EPackage) resPkg;
					p.setName(pkg.getName());
					p.setNsPrefix(pkg.getNsPrefix());
					p.setNsURI(pkg.getNsURI());
					
					EAnnotation annotation = p.getEAnnotation("EMFModelManager");
					if (annotation == null){
						annotation = EcoreFactory.eINSTANCE.createEAnnotation();
						annotation.setSource("EMFModelManager");
						p.getEAnnotations().add(annotation);
					}
					
					for (EClassifier cl : pkg.getEClassifiers()) {
						EClassifier newCl = p.getEClassifier(cl.getName());
						if (newCl == null)
							newCl = p.getEClassifier(cl.getName().replaceAll("(\\w+)[Tt][Yy][Pp][Ee]\\d*", "$1"));

						String guessedNewName = "";
						if (newCl != null){
							guessedNewName = newCl.getName();
						} else {
							guessedNewName = annotation.getDetails().get(cl.getName());
							if (guessedNewName != null && !guessedNewName.isEmpty()){
								newCl = p.getEClassifier(guessedNewName);
							}
						}			
						if (newCl == null){
							annotation.getDetails().put(cl.getName(), "");
							continue;
						}
						
						annotation.getDetails().put(cl.getName(), guessedNewName);

						
						outer: for (EObject attr : cl.eContents()) {
							if (attr instanceof EAttribute){
								EAttribute newAttr = null;
								
								for (EObject obj : newCl.eContents()) {
									if (obj instanceof EAttribute){
										if (((EAttribute) obj).getName().toLowerCase().equals(((EAttribute) attr).getName().toLowerCase())){
											newAttr = (EAttribute) obj;
											break;
										}
									} if (obj instanceof EReference){
										if (((EReference) obj).getName().toLowerCase().equals(((EAttribute) attr).getName().toLowerCase())){
											continue outer;
										}
											
									}
									
								}
								if (newAttr == null){
									EObject copy = EcoreUtil.copy(attr);
									((EClass)newCl).getEStructuralFeatures().add((EStructuralFeature) copy);
									newAttr = (EAttribute) copy;
								}
								if (((EAttribute) attr).getEAttributeType().eContainer().equals(cl.eContainer())){
									EDataType dataType = (EDataType) p.getEClassifier(((EAttribute) attr).getEType().getName());
									if (dataType == null){
										dataType = EcoreUtil.copy(((EAttribute) attr).getEAttributeType());
										p.getEClassifiers().add(dataType);
									}
									newAttr.setEType(dataType);
								} else {
									newAttr.setEType(((EAttribute) attr).getEAttributeType());
								}
								EAnnotation meta = ((EModelElement) attr).getEAnnotation("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
								if (meta != null){
									String ns = meta.getDetails().get("namespace");
									if (ns == null && newCl != null){
										meta = newAttr.getEAnnotation("http:///org/eclipse/emf/ecore/util/ExtendedMetaData");
										meta.getDetails().remove("namespace");				
									}
								}
							
							}
						}						
						
					}
				}
//(eClassifiers xsi:type="ecore:EClass" name="[^"]*")\s*>
				exportGeneratedEcoreModel(p,
						null);
			}
			cleanUp();
			 EPackage.Registry.INSTANCE.put(p.getNsURI(), p);
			
			for (String xmlURI : dialog.getFileNames()){
				xmlURI = str + xmlURI;
				
				ResourceImpl res = null;
				try {
					String fileType = xmlURI.substring(xmlURI.lastIndexOf(".")+1);
					
				

					res = (ResourceImpl) new XMLResourceFactoryImpl().createResource(URI.createFileURI(xmlURI));
					
					
					res.unload();
					
					HashMap<String,Object> options = new HashMap<String,Object>();
					options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);
					options.put(XMLResource.OPTION_EXTENDED_META_DATA, Boolean.TRUE);

					options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

					options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);
					options.put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE, Boolean.TRUE);

					options.put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);		
					
					res.load(options);
				} catch (Exception ex) {
					res = (ResourceImpl) transSys.eResource()
							.getResourceSet().getResource(URI.createFileURI(xmlURI), true);
					ex.printStackTrace();
				}

				postprocessModel(res.getContents());
				
				ImportInstanceModelAction action = new ImportInstanceModelAction(
						null);
				action.setModule(transSys);
				action.createAndAddGraph((ResourceImpl) res,
						URI.createFileURI(xmlURI));

				action.dispose();
			}
			

			MessageDialog.openInformation(shell, "Importing XML", "XML import finished.");
		} finally {
			shell.close();
		}
		
		System.out.println("import finished!");
	}

	private void postprocessModel(EList<EObject> contents) {
		HashSet<EObject> objects = new HashSet<EObject>();
		objects.addAll(contents);
		Iterator<EObject> iterator = objects.iterator();
		while(!objects.isEmpty()){
			EObject obj = iterator.next();
			iterator.remove();
			if (obj == null)
				continue;
			EStructuralFeature mixed = null;
			if ( (mixed = obj.eClass().getEStructuralFeature(MIXEDELEMENTFEATURE)) != null){
				FeatureMap map = (FeatureMap) obj.eGet(mixed);
				if (map == null)
					continue;
				String str = "";
				Iterator<Entry> itr = map.iterator();
				while(itr.hasNext()){
					Entry entry = itr.next();
					Object o = entry.getValue();
					if (o instanceof String){
						str += (String) o;						
					}
				}
				if (!str.isEmpty()){
					EStructuralFeature feat = obj.eClass().getEStructuralFeature(XML_ELEMENT_TEXT);
					if (feat != null){
						obj.eSet(feat, str);
					}
				}
				obj.eSet(mixed, null);
			}
			
			EList<EStructuralFeature> eAllContainments = obj.eClass().getEStructuralFeatures();
			for (EStructuralFeature feat : eAllContainments) {
				if (feat instanceof EReference){
					EReference ref =(EReference) feat;
					if (ref.isContainment()){
						if (ref.isMany()){
							objects.addAll((List<EObject>)obj.eGet(ref));
						} else {
							objects.add((EObject) obj.eGet(ref));							
								
						}
						iterator = objects.iterator();	
					}
				}
			}
			
		}
		
	}

	

	private BasicExtendedMetaData loadModelInformations(String xmlFile) {
		ReconstructingMetaData result = new ReconstructingMetaData(xmlFile,	null, null);
		String pkgUri = null;
		Document document = null;
		EPackage pkg = null;
		String rootName = null;
		String ns = null;
		try {

	      // getting the default implementation of DOM builder
	      DocumentBuilderFactory factory 
	         = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder = factory.newDocumentBuilder();

	      // parsing the XML file
	      document = builder.parse(new File(xmlFile));
	      
	      ns = document.getDocumentElement().getAttribute("xsi:noNamespaceSchemaLocation");
	      if (ns == "")
	    	  ns = document.getDocumentElement().getAttribute("xmlns");
	    			  
	      File f = new File( xmlFile.substring(0,xmlFile.lastIndexOf(File.separator)) + File.separator + ns);
	      loadedPackage = true;
	      if (f.exists()){
	    	  String xsdURI = f.getAbsoluteFile().toString();
				
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

				return new BasicExtendedMetaData();
	      } else {

	    	  if (ns == null || ns.isEmpty()){
	    		  ns = xmlFile;
	    	  } else {
	    		  if (!ns.contains("http://")){
		    		  ns = xmlFile.substring(0,xmlFile.lastIndexOf(File.separator)) + File.separator + ns;  
	    		  }  
	    	  }
	    	  ns = ns.replaceAll("\\\\", "/");
	    	  rootName = document.getDocumentElement().getNodeName();


	    	  pkgUri = generateReconstructedPackageURI(ns);



	    	  pkg = EPackageRegistryImpl.INSTANCE.getEPackage(pkgUri);

	    	  if (pkg == null){
	    		  Resource r = null;
	    		  try {
	    			  r = transSys.eResource().getResourceSet().getResource(URI.createURI(pkgUri),true);
	    			  if (r != null && !r.getContents().isEmpty() && (r.getContents().get(0) instanceof EPackage)){
	    				  pkg = (EPackage) r.getContents().get(0);
	    				  EPackageRegistryImpl.INSTANCE.put(pkgUri, pkg);
	    			  }
	    		  } finally {
	    			  if (r != null && pkg == null)
	    				  r.unload();
	    		  }
	    	  }
	      }
	    } catch (Exception e) {
	    
	    }
		result.reconstructedPackage = pkg;
		if (pkg == null){
			result.contextURI = null;
			loadedPackage = false;
		} else
			result.contextURI = ns;
		result.xmlFile = xmlFile;
		result.documentRoot =  rootName;
		return result;
	}
	
	public static String generateReconstructedPackageURI(String namespace) {
		
		
		return "http://"
				+ BASESCHEME
				+ "."
				+ namespace.substring(namespace
						.lastIndexOf("/") + 1);
	}
	
}

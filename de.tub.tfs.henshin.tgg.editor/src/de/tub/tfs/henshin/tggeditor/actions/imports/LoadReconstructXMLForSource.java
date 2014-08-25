package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
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



import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.SingleElementListSelectionDialog;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;
import de.tub.tfs.muvitor.ui.utils.FragmentResource;

public class LoadReconstructXMLForSource extends SelectionAction {

	static final String XML_ELEMENT_TEXT = "mixedText";
	public static final String MIXEDELEMENTFEATURE = "mixed";
	public static final String BASESCHEME = "de.tub.tfs.tgg.generated.xml";

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadReconstructXMLForSource";
	private TGG transSys;
	private boolean loadedPackage;
	private EPackage p;


	public LoadReconstructXMLForSource(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Load XML File");
		setToolTipText("Loads XML File and reconstructs model.");
	}

	private HashMap<String, Object> buildOptions(Map<Object,EStructuralFeature> map) {
		HashMap<String, Object> options = new HashMap<String, Object>();
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_ANY_TYPE, null);
		options.put(XMLResource.OPTION_SUPPRESS_DOCUMENT_ROOT, false);
		
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
			xmlOptions.setProcessSchemaLocations(false);

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
				transSys = (TGG) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}

	private void cleanUp() {
		
		ReconstructingMetaData.cleanExtendedMetaData(p);
	}
	
	private static HashMap<String,String> uriToFileMap = new HashMap<String, String>();
	
	private void exportGeneratedEcoreModel(EPackage p, String uri) {
		if (p == null)
			return;
		String fileUri = uriToFileMap.get(p.getNsURI());
		if (fileUri == null){
			Shell shell = new Shell();
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			dialog.setOverwrite(true);
			dialog.setText("Please specify the location where the new ecore model "+p.getName()+"("+p.getNsURI()+") should be saved.");
			fileUri = dialog.open();
			if (fileUri == null || fileUri.isEmpty()){
				shell.close();
				return;
			}
				
			uriToFileMap.put(p.getNsURI(), fileUri);
			shell.close();
		}
		Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("ecore", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Create a resource
		Resource resource = null;
		try {
			
			resource = transSys.eResource().getResourceSet().createResource(URI.createFileURI(fileUri));

		} catch (Exception ex) {

		}
		Resource old = p.eResource();
		resource.getContents().add(p);

		try {
			resource.save(Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (old != null)
			old.getContents().add(p);
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
				ex.printStackTrace();
				if (resource == null)
					resource = set.getResource(URI.createFileURI(xmlURI), true);
			} catch (Exception ex1){

			}
		}
		
		if (resource != null)
			try{
				//resource.unload();
			} catch (Exception ex){

			}
		//HashSet<EStructuralFeature> wrongRefs = improveModel(data);
		//System.out.println(Arrays.deepToString(wrongRefs.toArray()));
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

	public HashSet<EStructuralFeature> improveModel(BasicExtendedMetaData data) {
		HashSet<EStructuralFeature> criticalFeatures = new HashSet<EStructuralFeature>();
		HashSet<EStructuralFeature> invalidFeat = new HashSet<EStructuralFeature>();
		HashSet<EStructuralFeature> wrongRefs = new HashSet<EStructuralFeature>();
		
		
		EPackage reconstructedPackage = ((ReconstructingMetaData) data).getReconstructedPackage();
		
		for (EClassifier clazz : reconstructedPackage.getEClassifiers()) {
			if (clazz instanceof EClass){
				EClass eclass = (EClass) clazz;
				for (EStructuralFeature feat : eclass.getEStructuralFeatures()) {
					if (feat instanceof EReference)
						if (feat.getEType() instanceof EDataType)
							wrongRefs.add(feat);
						else
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
		Shell shell = new Shell();
		for (EStructuralFeature eStructuralFeature : criticalFeatures) {
			if (!MessageDialog.openQuestion(shell, "Process " + eStructuralFeature.getName(), "Dou you want to change the EReference " + ((EClass)eStructuralFeature.eContainer()).getName() + "." + eStructuralFeature.getName() + " into an EAttribute with the Type EString?")){
				continue;
			}
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
			
			reconstructedPackage.getEClassifiers().remove(eStructuralFeature.getEType());

			EClass cont = (EClass) eStructuralFeature.eContainer();
			cont.getEStructuralFeatures().add(eAttribute);
			cont.getEStructuralFeatures().remove(eStructuralFeature);

		}
		shell.close();
		for (EStructuralFeature eStructuralFeature : invalidFeat) {
			((List)eStructuralFeature.eContainer().eGet(eStructuralFeature.eContainingFeature())).remove(eStructuralFeature);
		}
		return wrongRefs;
	}
	
	private Boolean manual = null;
	private EClassifier newCl;
	private boolean copied;
	private HashMap<Object,Object> mappedClasses = new HashMap<Object,Object>();
	@Override
	public void run() {
		manual = null;
		Shell shell = new Shell();
		try {
			FileDialog dialog = new FileDialog(shell,SWT.MULTI);
	
			BasicExtendedMetaData data = null;
			dialog.setText("Please select the xml file you want to import.");
			String str = dialog.open();
			str = str.substring(0, str.lastIndexOf(File.separator + "")+1);
			for (String xmlURI : dialog.getFileNames()){
				xmlURI = str + xmlURI;
				Stack<String> stack = new Stack<String>();
				
				data = loadModelInformations(xmlURI);
				try {
					data = extractModelInformation(xmlURI,stack,data);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				if (data instanceof ReconstructingMetaData){

					p = ((ReconstructingMetaData) data).getReconstructedPackage();

				} else {

					
				}

				cleanUp();
			}
			if (MessageDialog.openQuestion(shell, "PostProcessing", "Do you want to run the post processor for the generated ecore model?")){
				improveModel(data);
			}
			
			exportGeneratedEcoreModel(p,null);
			
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
					EPackageRegistryImpl.INSTANCE.put(pkg.getNsURI(), p);
					EAnnotation annotation = p.getEAnnotation("EMFModelManager");
					if (annotation == null){
						annotation = EcoreFactory.eINSTANCE.createEAnnotation();
						annotation.setSource("EMFModelManager");
						p.getEAnnotations().add(annotation);
					}
					LinkedList<EClass> copiedClasses = new LinkedList<EClass>();
					for (final EClassifier cl : pkg.getEClassifiers()) {
						newCl = p.getEClassifier(cl.getName());
						if (newCl == null)
							newCl = p.getEClassifier(cl.getName().replaceAll("(\\w+)[Tt][Yy][Pp][Ee]\\d*", "$1"));

						String guessedNewName = "";
						if (newCl != null){
							guessedNewName = newCl.getName();
						} else {
							guessedNewName = annotation.getDetails().get("/" + cl.getName());
							;
							if (guessedNewName != null && !guessedNewName.isEmpty()){
								guessedNewName = guessedNewName.substring(1);
								newCl = p.getEClassifier(guessedNewName);
							}
						}			
						if (newCl == null){
							if (manual == null){
								boolean b = MessageDialog.openQuestion(shell, "", "At least one class could not be mapped. Do you want to select the mapped classes by hand?");
								manual = b;
							}
							if (manual && !(cl instanceof EDataType)){
								EList<EClassifier> eClassifiers = p.getEClassifiers();
								copied = false;
								newCl = DialogUtil.runClassSelectionDialog(shell, eClassifiers,cl,new SingleElementListSelectionDialog.ListEntry(){

									@Override
									public String getText() {
										return "Copy EClass to new model.";
									}

									@Override
									public Object execute() {
										copied = true;
										newCl = EcoreUtil.copy(cl);
										p.getEClassifiers().add(newCl);
										return newCl;
									}
									
								});
								if (newCl != null)
									guessedNewName = newCl.getName();
								else {
									annotation.getDetails().put( "/" + cl.getName(), "");
									continue;
								}
								if (copied){
									if (newCl instanceof EClass){
										copiedClasses.add((EClass) newCl);
									}
								}
							} else {
								annotation.getDetails().put( "/" + cl.getName(), "");
								continue;
							}
							
							
						}
						
						annotation.getDetails().put( "/" + cl.getName(),  "/" + guessedNewName);

						mappedClasses.put(cl, newCl);
						mappedClasses.put(newCl, cl);
						outer: for (EObject attr : cl.eContents()) {
							if (attr instanceof EStructuralFeature){
								if (!annotation.getDetails().contains( "/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName()))
									annotation.getDetails().put( "/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName(), "");
							}
							if (attr instanceof EReference){
								EReference newAttr = null;
								String attrName = annotation.getDetails().get("/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName());
								if (attrName != null && !attrName.isEmpty()){
									attrName = attrName.substring(attrName.lastIndexOf("/")+1);
									for (EObject obj : newCl.eContents()) {
										if (obj instanceof EReference){
											if (attrName.equals(((EReference) attr).getName())){
												newAttr = (EReference) obj;
												break;
											}
										}										
									}
								}
								if (newAttr == null)
									for (EObject obj : newCl.eContents()) {
										if (obj instanceof EReference){
											if (((EReference) obj).getName().toLowerCase().equals(((EReference) attr).getName().toLowerCase())){
												newAttr = (EReference) obj;
												break;
											}
										} if (obj instanceof EAttribute){
											if (((EAttribute) obj).getName().toLowerCase().equals(((EReference) attr).getName().toLowerCase())){
												continue outer;
											}
										}
									}
								
								if (newAttr != null){
									mappedClasses.put(attr, newAttr);
									mappedClasses.put(newAttr, attr);
									
									annotation.getDetails().put( "/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName(), "/" + newCl.getName() + "/" + ((EStructuralFeature)newAttr).getName());
								} else {
									annotation.getDetails().put( "/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName(), "");
								}
							}
							if (attr instanceof EAttribute){
								EAttribute newAttr = null;
								
								String attrName = annotation.getDetails().get("/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName());
								if (attrName != null && !attrName.isEmpty()){
									attrName = attrName.substring(attrName.lastIndexOf("/")+1);
									for (EObject obj : newCl.eContents()) {
										if (obj instanceof EAttribute){
											if (attrName.equals(((EAttribute) attr).getName())){
												newAttr = (EAttribute) obj;
												break;
											}
										}										
									}
								}
								if (newAttr == null)
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
								mappedClasses.put(attr, newAttr);
								mappedClasses.put(newAttr, attr);
								
								annotation.getDetails().put( "/" + cl.getName() + "/" + ((EStructuralFeature)attr).getName(),  "/" + newCl.getName() + "/" + newAttr.getName());
								
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
					for (EClass eClass : copiedClasses) {
						updateSuperTypes(eClass);
					}
					for (EClassifier classifier : pkg.getEClassifiers()) {
						if (classifier instanceof EClass){
							for (EClass superType : ((EClass) classifier).getESuperTypes()) {
								EClass newSuperType = (EClass) mappedClasses.get(superType);
								if (newSuperType != null){
									EClass mappedClass = (EClass) mappedClasses.get(classifier);
									if (mappedClass != null && !mappedClass.getESuperTypes().contains(newSuperType) && mappedClass != newSuperType){
										if (mappedClass.getESuperTypes().contains(superType)){
											mappedClass.getESuperTypes().remove(superType);
										}
										mappedClass.getESuperTypes().add(newSuperType);
										
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
			
			cleanUp();
			MessageDialog.openInformation(shell, "Importing XML", "XML import finished.");
		} finally {
			shell.close();
		}
		
		System.out.println("import finished!");
	}

	protected void updateSuperTypes(EClass eClass) {
		LinkedList<EClass> superTypes = new LinkedList<EClass>();
		for (EClass eClass2 : eClass.getESuperTypes()) {
			EClass newType = (EClass) mappedClasses.get(eClass2);
			newType = eClass2;
			
			superTypes.add(newType);
		}
		eClass.getESuperTypes().clear();
		eClass.getESuperTypes().addAll(superTypes);
		
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
	      if (f.exists() && !f.isDirectory()){
	    	  String xsdURI = f.getAbsoluteFile().toString();
				try {
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
				} catch (Exception e){

				}
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
	    		  } catch (Exception ex) {


	    		  } finally {
	    			  if (r != null && pkg == null)
	    				  r.unload();
	    		  }
	    	  }
	    	
	    	  if (pkg == null){
	    		  Resource r = null;
	    		  try {
	    			  r = transSys.eResource().getResourceSet().getResource(URI.createURI(ns),true);
	    			  if (r != null && !r.getContents().isEmpty() && (r.getContents().get(0) instanceof EPackage)){
	    				  pkg = (EPackage) r.getContents().get(0);
	    				  EPackageRegistryImpl.INSTANCE.put(ns, pkg);
	    				  EPackageRegistryImpl.INSTANCE.put(pkgUri,pkg);
	    			  }
	    		  } catch (Exception ex) {


	    		  } finally {
	    			  if (r != null && pkg == null)
	    				  r.unload();
	    		  }
	    	  }
	      }
	    } catch (Exception e) {
	    	pkg = p;
	    	if (p != null)
	    		ns = p.getNsURI();
	    	e.printStackTrace();
	    	if (pkg == null)
	    		return null;
	    	System.out.println("Could not parse " + xmlFile + " assuming package: " + p.getNsURI());
	    }
		result.reconstructedPackage = pkg;
		if (pkg == null){
			result.contextURI = null;
			loadedPackage = false;
		} else
			result.contextURI = ns;
		result.xmlFile = xmlFile;
		result.documentRoot =  null;
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

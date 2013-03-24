package de.tub.tfs.henshin.tggeditor.actions.create.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.validation.ValidatorHandler;

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
import org.eclipse.emf.ecore.util.ExtendedMetaData;
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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;

public class LoadReconstructXMLForSource extends SelectionAction {

	private static final String DEFAULTSCHEME = "de.tub.tfs.tgg.generated.xml";
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadReconstructXMLForSource";
	private Module transSys;

	private HashMap<String, Object> options = new HashMap<String, Object>();
	private EPackage reconstructedPackage = null;

	private static HashMap<AnyType, EObject> anyTypetoObjectMapping = new HashMap<AnyType, EObject>();

	private Stack<String> currentElement = new Stack<String>();

	
	private String documentRoot = "";
	private String contextURI = DEFAULTSCHEME;
	private ResourceSetImpl set;

	
	
	public LoadReconstructXMLForSource(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Load XML File as Source Model");
		setToolTipText("Loads XML File and reconstructs model.");
		reconstructedPackage = null;
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_ANY_TYPE, null);
		addMetaData();

	}

	private void addMetaData() {

		options.put(XMLResource.OPTION_EXTENDED_META_DATA,
				new BasicExtendedMetaData() {

			@Override
			public EClass getDocumentRoot(EPackage ePackage) {
				return (EClass) ePackage.getEClassifier(documentRoot);
			}

			@Override
			public void setDocumentRoot(EClass eClass) {
				setName(eClass, documentRoot);
				setContentKind(eClass, MIXED_CONTENT);
			}

			@Override
			public boolean isDocumentRoot(EClass eClass) {
				return documentRoot.equals(getName(eClass));
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
			public Collection<EPackage> demandedPackages() {
				// TODO Auto-generated method stub
				return Arrays.asList(reconstructedPackage);
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

				
				EStructuralFeature feat = super.demandFeature(
						namespace, name, isElement, isReference);
				getDocumentRoot(reconstructedPackage).getEStructuralFeatures().remove(feat);
				feat.setUpperBound(-1);
				// this.getExtendedMetaData(feat).setGroup(feat);
	
				 if (!isReference) {
						feat.setEType(EcorePackage.Literals.ESTRING);
						
						EClass cont = (EClass) this.demandType(namespace, currentElement.elementAt(currentElement.size()-1));
						if (cont.getEStructuralFeature(name) == null)
							cont.getEStructuralFeatures().add(feat);
						else
							feat = cont.getEStructuralFeature(name);
						
				 } else if (isElement) {
					//		getDocumentRoot(reconstructedPackage)
							if (currentElement.size() - 1 > 0) {
								String container = currentElement
										.elementAt(currentElement.size() - 2);
								EClass cont = (EClass) this.demandType(
										namespace, container);
								if (cont.getEStructuralFeature(name) == null) {
									EClassifier targetType = this.demandType(
											namespace, name);

									feat.setEType(targetType);

									feat.setUpperBound(-1);
									
									cont.getEStructuralFeatures().add(feat);

								} else { 
									feat = cont.getEStructuralFeature(name);
									
									EClassifier targetType = this.demandType(
											namespace, name);

									feat.setEType(targetType);

									feat.setUpperBound(-1);

								}
							}

				}
				if (isElement && isReference && feat instanceof EReference){
					((EReference)feat).setContainment(true);
				}
				setName(feat, name);
				return feat;
			}

			@Override
			public EPackage demandPackage(String namespace) {
				// TODO Auto-generated method stub
				System.out.println("demanded Package " + namespace);
				if (namespace != null){
					contextURI = namespace;
				} else {
					namespace = contextURI;
				}
				
				if (reconstructedPackage != null)
					return reconstructedPackage;

				reconstructedPackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE
						.createEPackage();
				reconstructedPackage.setName(namespace);
				reconstructedPackage.setNsPrefix(namespace
						.substring(namespace.indexOf(".") + 1));
				reconstructedPackage.setNsURI(namespace);
				if (DEFAULTSCHEME.equals(namespace))
					EPackage.Registry.INSTANCE.put(null,
							reconstructedPackage);
	
				EPackage.Registry.INSTANCE.put(namespace,
						reconstructedPackage);

				System.out.println("created Package "
						+ reconstructedPackage.toString());

				return reconstructedPackage;
			}

			@Override
			public EClassifier demandType(String namespace, String name) {
				// TODO Auto-generated method stub
				System.out.println("demanded Type " + namespace
						+ " name:" + name);
				if (reconstructedPackage == null)
					reconstructedPackage = demandPackage(DEFAULTSCHEME);
				
				if (reconstructedPackage.getEClassifier(name) != null)
					return reconstructedPackage.getEClassifier(name);

				if (getDocumentRoot(reconstructedPackage) == null) {

					EClass documentRootEClass = EcoreFactory.eINSTANCE
							.createEClass();
//					documentRootEClass.getESuperTypes().add(
//							XMLTypePackage.eINSTANCE
//							.getXMLTypeDocumentRoot());
					documentRootEClass.setName(name);
					reconstructedPackage.getEClassifiers().add(
							documentRootEClass);
					documentRoot = name;
					setDocumentRoot(documentRootEClass);

					return documentRootEClass;
				}

				EClassImpl eClass = (EClassImpl) EcoreFactoryImpl.eINSTANCE
						.createEClass();

				eClass.setName(name);
				setName(eClass, name);

				reconstructedPackage.getEClassifiers().add(eClass);

				System.out.println("created Type " + eClass.toString());

				return eClass;
			}

			@Override
			public EClassifier getType(String namespace, String name) {
				if (name.equals(documentRoot))
					return super.getType(namespace, "");
				return super.getType(namespace, name);
			}
			
			
		});

	}

	@Override
	protected boolean calculateEnabled() {
		reconstructedPackage = null;
		addMetaData();
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

	private class AnyTypeEntry {
		private EObject obj;
		private EStructuralFeature feat;
		private AnyType type;

		private AnyTypeEntry(AnyType type,
				EStructuralFeature containingFeature, EObject containingObject) {
			this.type = type;
			this.feat = containingFeature;
			this.obj = containingObject;
		}

	}

	private class ObjectToInspect {
		private EObject lastObject;
		private EStructuralFeature lastFeat;
		private Object current;

		private ObjectToInspect(Object current, EStructuralFeature lastFeat,
				EObject lastObject) {
			this.current = current;
			this.lastFeat = lastFeat;
			this.lastObject = lastObject;
		}

	}

	@Override
	public void run() {
		Shell shell = new Shell();
		try {
			FileDialog dialog = new FileDialog(shell);
			dialog.setText("Please select the xml file you want to import.");
			String xmlURI = dialog.open();
			
			try {
				extractModelInformation(xmlURI);			
			} catch (Exception ex){
				
			}
			
			Resource r = null;
			try {
				
				/*
				ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap()
			    .put("xml", new GenericXMLResourceFactoryImpl());

				ResourceSet resourceSet = reconstructedPackage.eResource().getResourceSet();
				HashMap<String, Object> opt = new HashMap<String, Object>();
				
				//opt.put(XMLResource.OPTION_SCHEMA_LOCATION, );
				
				resourceSet.getLoadOptions().putAll(opt);
				XMLOptionsImpl xmlOpt = new XMLOptionsImpl();
				xmlOpt.setProcessAnyXML(true);
				HashMap<String, URI> schemes = new HashMap<String, URI>();
				//schemes.put(null, URI.createURI(DEFAULTSCHEME));
				//schemes.put("", URI.createURI(DEFAULTSCHEME));
				//xmlOpt.setExternalSchemaLocations(schemes);
				resourceSet.getLoadOptions().put(XMLResource.OPTION_XML_OPTIONS, xmlOpt);
				
				
				*/		  
				final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData(){
					EClass docRoot = null;
					@Override
					public EClass getDocumentRoot(EPackage ePackage) {
						if (docRoot == null){
							docRoot = EcoreFactory.eINSTANCE
									.createEClass();
//							docRoot.getESuperTypes().add(
//									XMLTypePackage.eINSTANCE
//									.getXMLTypeDocumentRoot());
							docRoot.setName("DocumentRoot");
							reconstructedPackage.getEClassifiers().add(
									docRoot);
							super.setDocumentRoot(docRoot);
							
						}
						return docRoot;
					}
	
					@Override
					public boolean isDocumentRoot(EClass eClass) {
						// TODO Auto-generated method stub
						return eClass.equals(docRoot);
					}
					
					@Override
					public void setDocumentRoot(EClass eClass) {
						docRoot = eClass;
					}
					
					@Override
					public EPackage demandPackage(String namespace) {
						System.out.println(namespace);
						return reconstructedPackage;
					}
					
					@Override
					public EAttribute getMixedFeature(EClass eClass) {
						if (reconstructedPackage.getEClassifiers().contains(eClass)){
							return (EAttribute) eClass.getEStructuralFeature("tggEditorMixedElementText");
						}
						return super.getMixedFeature(eClass);
					}
					
					@Override
					public EStructuralFeature demandFeature(String namespace,
							String name, boolean isElement, boolean isReference) {
						String container;
						if (documentRoot.equals(name) && currentElement.size() <= 1){
							EStructuralFeature feature = super.demandFeature(namespace, name, isElement, isReference);
							EClass type = (EClass) this.demandType(namespace, name);
							feature.setEType(type);
							return feature;
						}
						if (currentElement.size() > 0) {
							container = currentElement
									.elementAt(currentElement.size() - 1);

							EClass type = (EClass) this.demandType(namespace, container);
							
							return type.getEStructuralFeature(name);
						} 
						
						throw new RuntimeException("Feature not found: " + name);
					}
					
					@Override
					public EClassifier demandType(String namespace, String name) {
						if (documentRoot == null)
							documentRoot = name;
						System.out.println("demanded " + name);
						
						
						return reconstructedPackage.getEClassifier(name);
					}
					
					@Override
					public EClassifier getType(String namespace, String name) {
						// TODO Auto-generated method stub
						return reconstructedPackage.getEClassifier(name);
					}
					
					@Override
					public EClassifier getType(EPackage ePackage, String name) {
						if (name == "")
							return docRoot;
						if (ePackage == null || ePackage.equals(reconstructedPackage))
							return reconstructedPackage.getEClassifier(name);
						return super.getType(ePackage, name);
					}
				};
				
				
				set.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetaData);
				set.getLoadOptions().put(XMLResource.OPTION_SUPPRESS_DOCUMENT_ROOT, Boolean.FALSE);
					
				
				currentElement.clear();
				r = set.getResource(URI.createFileURI(xmlURI), true);
				r.unload();
				r = set.getResource(URI.createFileURI(xmlURI), true);
				//set.getResource(URI.createFileURI(xmlURI), true).unload();
				//r.load(opt);
				//reconstructedPackage
			} catch (Exception ex){
				ex.printStackTrace();
			}
			
			ImportInstanceModelAction action = new ImportInstanceModelAction(
					null);
			action.setModule(transSys);
			action.createAndAddGraph((ResourceImpl) r,
					URI.createFileURI(xmlURI));

			action.dispose();

		} finally {
			shell.close();
		}

	}

	public void extractModelInformation(String xmlURI) {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap()
		.put("xml", new XMLResourceFactoryImpl() {
			@Override
			public Resource createResource(final URI uri) {
				return new XMLResourceImpl(uri) {
					@Override
					protected boolean useUUIDs() {
						return false;
					}

					@Override
					protected XMLLoad createXMLLoad() {
						return new XMLLoadImpl(createXMLHelper()) {

							
							@Override
							protected SAXParser makeParser()
									throws ParserConfigurationException,
									SAXException {
								SAXParserFactory f = SAXParserFactory.newInstance();
							   
								return f.newSAXParser();
							}
							
							
							@Override
							protected DefaultHandler makeDefaultHandler() {
								// TODO Auto-generated method stub
								return new SAXXMLHandler(resource,
										helper, options) {
									@Override
									public void startElement(
											String uri,
											String localName,
											String name) {
										currentElement.push(name);
										super.startElement(uri,
												localName, name);

									}

									@Override
									public void startEntity(
											String name) {
										// TODO Auto-generated
										// method stub
										super.startEntity(name);
									}

									@Override
									public void endElement(
											String uri,
											String localName,
											String name) {

										super.endElement(uri,
												localName, name);
										String cur = currentElement
												.pop();
										if (!cur.equals(name)) {
											System.out
											.println("ERROR!");
										}
									}

									@Override
									public void endEntity(
											String name) {
										// TODO Auto-generated
										// method stub
										super.endEntity(name);
									}
									
									
								};
							}

						};
					}
				};

			}
		});

		set = new ResourceSetImpl(){
			@Override
			public Resource getResource(URI uri, boolean loadOnDemand) {
				if (DEFAULTSCHEME.equals(uri.toString()))
					return reconstructedPackage.eResource();
				return super.getResource(uri, loadOnDemand);
			}
			
			@Override
			protected void handleDemandLoadException(Resource resource,
					IOException exception) throws RuntimeException {
				// TODO Auto-generated method stub
				super.handleDemandLoadException(resource, exception);
			}
		};
		set.getLoadOptions().putAll(options);
		set.getLoadOptions().put(XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
				Boolean.TRUE);
		set.getLoadOptions().put(
				XMLResource.OPTION_RECORD_ANY_TYPE_NAMESPACE_DECLARATIONS,
				Boolean.TRUE);
		
		XMLOptions xmlOptions = (XMLOptions) set.getLoadOptions().get(
				XMLResource.OPTION_XML_OPTIONS);
		if (xmlOptions != null) {
			xmlOptions.setProcessSchemaLocations(true);

		} else {
			xmlOptions = new XMLOptionsImpl();
			xmlOptions.setProcessAnyXML(false);
			xmlOptions.setProcessSchemaLocations(true);
			
			set.getLoadOptions().put(XMLResource.OPTION_XML_OPTIONS,
					xmlOptions);
		}
		Resource resource = null;
		try {
			resource = set.getResource(URI.createFileURI(xmlURI), true);

			// resource.unload();
			// resource.load(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			set.getResource(URI.createFileURI(xmlURI), true).unload();
			e.printStackTrace();
		}

		HashSet<EStructuralFeature> criticalFeatures = new HashSet<EStructuralFeature>();
		
		for (EClassifier clazz : reconstructedPackage.getEClassifiers()) {
			EClass eclass = (EClass) clazz;
			for (EStructuralFeature feat : eclass.getEStructuralFeatures()) {
				if (feat instanceof EReference)
				if (((EClass)feat.getEType()).getEStructuralFeatures().isEmpty())
					criticalFeatures.add(feat);
			}
		}
		
		
		
		for (EStructuralFeature eStructuralFeature : criticalFeatures) {
			EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		    eAttribute.setName(eStructuralFeature.getName());
		    eAttribute.setEType(EcorePackage.Literals.ESTRING);
		    eAttribute.setDerived(true);
		    eAttribute.setTransient(true);
		    eAttribute.setVolatile(true);
		    eAttribute.setUpperBound(ETypedElement.UNSPECIFIED_MULTIPLICITY);
		    
		    reconstructedPackage.getEClassifiers().remove(reconstructedPackage.getEClassifier(eStructuralFeature.getName()));
		    
		    EClass cont = (EClass) eStructuralFeature.eContainer();
		    cont.getEStructuralFeatures().add( eAttribute);
		    cont.getEStructuralFeatures().remove(eStructuralFeature);
		    
			
			
		}
		Resource resource2 = transSys.eResource().getResourceSet().createResource(URI.createURI(reconstructedPackage.getNsURI()));
		resource2.getContents().add(reconstructedPackage);
		transSys.eResource().getResourceSet().getResources().add(resource2);
	}
}

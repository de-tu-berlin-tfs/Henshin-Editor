package de.tub.tfs.henshin.tggeditor.actions.create.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.ETypedElement;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;

public class LoadReconstructXMLForSource extends SelectionAction {

	private static final String MIXEDELEMENTFEATURE = "tggEditorMixedElementText";
	private static final String BASESCHEME = "de.tub.tfs.tgg.generated.xml";

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadReconstructXMLForSource";
	private Module transSys;

	private HashMap<String, Object> options = new HashMap<String, Object>();

	private EPackage reconstructedPackage = null;

	private static HashMap<AnyType, EObject> anyTypetoObjectMapping = new HashMap<AnyType, EObject>();
	private Stack<String> currentElement = new Stack<String>();
	private String documentRoot = "";
	private String contextURI = BASESCHEME;
	private ResourceSetImpl set;

	private HashMap<Object, EStructuralFeature> map;

	private EClass docRoot = null;

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
						getDocumentRoot(reconstructedPackage)
								.getEStructuralFeatures().remove(feat);
						feat.setUpperBound(-1);
						// this.getExtendedMetaData(feat).setGroup(feat);

						if (!isReference) {
							feat.setEType(EcorePackage.Literals.ESTRING);

							EClass cont = (EClass) this.demandType(namespace,
									currentElement.elementAt(currentElement
											.size() - 1));
							if (cont.getEStructuralFeature(name) == null)
								cont.getEStructuralFeatures().add(feat);
							else
								feat = cont.getEStructuralFeature(name);
							System.out.println("created feat " + name + " for "
									+ cont.getName());
						} else if (isElement) {
							// getDocumentRoot(reconstructedPackage)
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

								System.out.println("created feat " + name
										+ " for " + cont.getName());
							}

						}
						if (isElement && isReference
								&& feat instanceof EReference) {
							((EReference) feat).setContainment(true);
						}

						setName(feat,
								currentElement.elementAt(currentElement.size() - 1)
										+ ":" + name);
						return feat;
					}

					@Override
					public EPackage demandPackage(String namespace) {
						System.out.println("demanded Package " + namespace);
						if (namespace != null) {
							contextURI = namespace;
						} else {
							namespace = contextURI;
						}

						if (reconstructedPackage != null)
							return reconstructedPackage;

						reconstructedPackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE
								.createEPackage();
						reconstructedPackage.setName(namespace);
						reconstructedPackage.setNsPrefix("xml");
						reconstructedPackage.setNsURI("http://"
								+ BASESCHEME
								+ "."
								+ namespace.substring(namespace
										.lastIndexOf("/") + 1));
						if (BASESCHEME.equals(namespace))
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
						System.out.println("demanded Type " + namespace
								+ " name:" + name);
						if (reconstructedPackage == null)
							reconstructedPackage = demandPackage(BASESCHEME);

						if (reconstructedPackage.getEClassifier(name) != null)
							return reconstructedPackage.getEClassifier(name);

						if (getDocumentRoot(reconstructedPackage) == null) {

							EClass documentRootEClass = EcoreFactory.eINSTANCE
									.createEClass();
							// documentRootEClass.getESuperTypes().add(
							// XMLTypePackage.eINSTANCE
							// .getXMLTypeDocumentRoot());
							documentRootEClass.setName(name);
							reconstructedPackage.getEClassifiers().add(
									documentRootEClass);
							documentRoot = name;
							setDocumentRoot(documentRootEClass);
							//

							EAttribute eAttribute = EcoreFactory.eINSTANCE
									.createEAttribute();
							eAttribute.setName(MIXEDELEMENTFEATURE);
							eAttribute
									.setEType(EcorePackage.Literals.EFEATURE_MAP);
							eAttribute.setDerived(true);
							eAttribute.setTransient(true);
							eAttribute.setVolatile(true);
							eAttribute
									.setUpperBound(ETypedElement.UNSPECIFIED_MULTIPLICITY);

							documentRootEClass.getEStructuralFeatures().add(
									eAttribute);
							return documentRootEClass;
						}

						EClassImpl eClass = (EClassImpl) EcoreFactoryImpl.eINSTANCE
								.createEClass();

						eClass.setName(name);
						setName(eClass, name);

						reconstructedPackage.getEClassifiers().add(eClass);

						System.out.println("created Type " + eClass.toString());

						EAttribute eAttribute = EcoreFactory.eINSTANCE
								.createEAttribute();
						eAttribute.setName(MIXEDELEMENTFEATURE);
						eAttribute.setEType(EcorePackage.Literals.EFEATURE_MAP);
						eAttribute.setDerived(true);
						eAttribute.setTransient(true);
						eAttribute.setVolatile(true);
						eAttribute
								.setUpperBound(ETypedElement.UNSPECIFIED_MULTIPLICITY);

						eClass.getEStructuralFeatures().add(eAttribute);

						return eClass;
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
					public EStructuralFeature getAttribute(EClass eClass,
							String namespace, String name) {
						System.out.println("get attr " + eClass.getName()
								+ " queried " + name);
						EStructuralFeature feature = eClass
								.getEStructuralFeature(name);
						if (feature != null)
							return feature;
						return super.getAttribute(eClass, namespace, name);
					}

					@Override
					public EStructuralFeature getAttribute(String namespace,
							String name) {
						System.out.println("get attr : " + name);
						map.clear();
						return null;
					}

					@Override
					public EStructuralFeature getAttributeWildcardAffiliation(
							EClass eClass, String namespace, String name) {

						return super.getAttributeWildcardAffiliation(eClass,
								namespace, name);

					}

					@Override
					public EClass getDocumentRoot(EPackage ePackage) {
						return (EClass) ePackage.getEClassifier(documentRoot);
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
					public EClassifier getType(String namespace, String name) {
						if (name.equals(documentRoot))
							return super.getType(namespace, "");
						return super.getType(namespace, name);
					}

					@Override
					public boolean isDocumentRoot(EClass eClass) {
						return documentRoot.equals(getName(eClass));
					}

					@Override
					public void setDocumentRoot(EClass eClass) {
						setName(eClass, documentRoot);
						setContentKind(eClass, MIXED_CONTENT);
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

	private void cleanUp() {
		options = new HashMap<String, Object>();
		reconstructedPackage = null;

		anyTypetoObjectMapping = new HashMap<AnyType, EObject>();

		currentElement = new Stack<String>();

		documentRoot = "";
		contextURI = BASESCHEME;
		docRoot = null;

		reconstructedPackage = null;
		options.put(XMLResource.OPTION_DECLARE_XML, Boolean.TRUE);
		options.put(XMLResource.OPTION_KEEP_DEFAULT_CONTENT, Boolean.TRUE);
		options.put(XMLResource.OPTION_ANY_TYPE, null);
		addMetaData();

	}

	private void exportGeneratedEcoreModel(EPackage p, String uri) {
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

	public void extractModelInformation(String xmlURI) {
		ResourceFactoryRegistryImpl.INSTANCE.getExtensionToFactoryMap().put(
				"xml", new XMLResourceFactoryImpl() {
					@Override
					public Resource createResource(final URI uri) {
						return new XMLResourceImpl(uri) {
							@Override
							protected XMLLoad createXMLLoad() {
								return new XMLLoadImpl(createXMLHelper()) {

									@Override
									protected DefaultHandler makeDefaultHandler() {
										// TODO Auto-generated method stub
										return new SAXXMLHandler(resource,
												helper, options) {
											@Override
											public void endElement(String uri,
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
												currentElement.push(name);
												super.startElement(uri,
														localName, name);

											}

											@Override
											public void startEntity(String name) {
												// TODO Auto-generated
												// method stub
												super.startEntity(name);
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

								};
							}

							@Override
							protected boolean useUUIDs() {
								return false;
							}
						};

					}
				});

		set = new ResourceSetImpl() {
			@Override
			public Resource getResource(URI uri, boolean loadOnDemand) {
				if (BASESCHEME.equals(uri.toString()))
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

			set.getLoadOptions()
					.put(XMLResource.OPTION_XML_OPTIONS, xmlOptions);
		}
		map = new HashMap<Object, EStructuralFeature>();
		set.getLoadOptions().put(
				XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, map);
		Resource resource = null;
		try {
			resource = set.getResource(URI.createFileURI(xmlURI), true);
		} catch (Exception e) {
			set.getResource(URI.createFileURI(xmlURI), true).unload();
		}

		HashSet<EStructuralFeature> criticalFeatures = new HashSet<EStructuralFeature>();

		for (EClassifier clazz : reconstructedPackage.getEClassifiers()) {
			EClass eclass = (EClass) clazz;
			for (EStructuralFeature feat : eclass.getEStructuralFeatures()) {
				if (feat instanceof EReference)
					if (((EClass) feat.getEType()).getEStructuralFeatures()
							.isEmpty())
						criticalFeatures.add(feat);
					else if (((EClass) feat.getEType())
							.getEStructuralFeatures().size() == 1
							&& ((EClass) feat.getEType())
									.getEStructuralFeatures().get(0).getName()
									.equals("tggEditorMixedElementText"))
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

			reconstructedPackage.getEClassifiers().remove(
					reconstructedPackage.getEClassifier(eStructuralFeature
							.getName()));

			EClass cont = (EClass) eStructuralFeature.eContainer();
			cont.getEStructuralFeatures().add(eAttribute);
			cont.getEStructuralFeatures().remove(eStructuralFeature);

		}
		Resource resource2 = transSys.eResource().getResourceSet()
				.createResource(URI.createURI(reconstructedPackage.getNsURI()));
		resource2.getContents().add(reconstructedPackage);
		transSys.eResource().getResourceSet().getResources().add(resource2);
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
			} catch (Exception ex) {

			}

			Resource r = null;
			try {

				final ExtendedMetaData extendedMetaData = new BasicExtendedMetaData() {

					@Override
					public EStructuralFeature demandFeature(String namespace,
							String name, boolean isElement, boolean isReference) {
						String container;
						if (documentRoot.equals(name)
								&& currentElement.size() <= 1) {

							return docRoot.getEStructuralFeature(name);
						}
						if (currentElement.size() > 0) {
							if (!isReference) {
								container = currentElement
										.elementAt(currentElement.size() - 1);

								EClass type = (EClass) this.demandType(
										namespace, container);

								return type.getEStructuralFeature(name);
							} else if (currentElement.size() > 1) {
								container = currentElement
										.elementAt(currentElement.size() - 2);

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
						return reconstructedPackage;
					}

					@Override
					public EClassifier demandType(String namespace, String name) {
						if (documentRoot == null)
							documentRoot = name;
						System.out.println("demanded " + name);

						return reconstructedPackage.getEClassifier(name);
					}

					@Override
					public EStructuralFeature getAttribute(EClass eClass,
							String namespace, String name) {
						EStructuralFeature feature = eClass
								.getEStructuralFeature(name);
						if (feature != null)
							return feature;
						return super.getAttribute(eClass, namespace, name);
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
							docRoot = EcoreFactory.eINSTANCE.createEClass();
							docRoot.setName("DocumentRoot");
							reconstructedPackage.getEClassifiers().add(docRoot);
							super.setDocumentRoot(docRoot);

							EStructuralFeature feature = super.demandFeature(
									null, documentRoot, true, true);
							EClass type = (EClass) this.demandType(null,
									documentRoot);
							feature.setEType(type);

							docRoot.getEStructuralFeatures().add(feature);

						}
						return docRoot;
					}

					@Override
					public EStructuralFeature getElement(EClass eClass,
							String namespace, String name) {
						EStructuralFeature feature = eClass
								.getEStructuralFeature(name);
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
						if (reconstructedPackage.getEClassifiers().contains(
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
								|| ePackage.equals(reconstructedPackage))
							return reconstructedPackage.getEClassifier(name);
						return super.getType(ePackage, name);
					}

					@Override
					public EClassifier getType(String namespace, String name) {
						return reconstructedPackage.getEClassifier(name);
					}

					@Override
					public boolean isDocumentRoot(EClass eClass) {
						return eClass.equals(docRoot);
					}

					@Override
					public void setDocumentRoot(EClass eClass) {
						docRoot = eClass;
					}
				};

				set.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
						extendedMetaData);
				set.getLoadOptions().put(
						XMLResource.OPTION_SUPPRESS_DOCUMENT_ROOT,
						Boolean.FALSE);

				map.clear();
				currentElement.clear();
				try {
					r = set.getResource(URI.createFileURI(xmlURI), true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			EPackage p = reconstructedPackage;
			ImportInstanceModelAction action = new ImportInstanceModelAction(
					null);
			action.setModule(transSys);
			action.createAndAddGraph((ResourceImpl) r,
					URI.createFileURI(xmlURI));

			action.dispose();

			exportGeneratedEcoreModel(p,
					xmlURI.substring(0, xmlURI.lastIndexOf(File.separator))
							+ File.separator);

			cleanUp();
		} finally {
			shell.close();
		}

	}
}

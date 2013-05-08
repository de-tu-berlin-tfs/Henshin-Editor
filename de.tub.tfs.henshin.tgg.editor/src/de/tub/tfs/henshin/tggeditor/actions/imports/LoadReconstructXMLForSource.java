package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.eclipse.emf.common.util.EList;
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
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.impl.EcoreFactoryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryRegistryImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicExtendedMetaData;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.XMLLoad;
import org.eclipse.emf.ecore.xmi.XMLOptions;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.SAXXMLHandler;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLLoadImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLOptionsImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.xsd.ecore.XSDEcoreBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.muvitor.ui.utils.EMFModelManager;

public class LoadReconstructXMLForSource extends SelectionAction {

	private static final String XML_ELEMENT_TEXT = "tggXmlElementText";
	public static final String MIXEDELEMENTFEATURE = "tggMixedElementText";
	public static final String BASESCHEME = "de.tub.tfs.tgg.generated.xml";

	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.create.graph.LoadReconstructXMLForSource";
	private Module transSys;



	private final class DelegatingMetaData extends BasicExtendedMetaData {
		
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

				EStructuralFeature feature = super.demandFeature(
						null, root, true, true);
				EClass type = (EClass) this.demandType(null,
						root);
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
	}

	private final class LoggingXMLLoad extends XMLLoadImpl {
		private Stack<String> stack;

		private LoggingXMLLoad(XMLHelper helper,Stack<String> elemStack) {
			super(helper);
			this.stack = elemStack;
		}

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
							if ((mixed = object.eClass().getEStructuralFeature(MIXEDELEMENTFEATURE) ) != null){
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

	private final class ReconstructingMetaData extends BasicExtendedMetaData {
		
		private EPackage reconstructedPackage;
		public Map<Object, EStructuralFeature> getMap() {
			return map;
		}

		public void setReconstructedPackage(EPackage reconstructedPackage) {
			this.reconstructedPackage = reconstructedPackage;
		}

		public void setDocumentRoot(String documentRoot) {
			this.documentRoot = documentRoot;
		}

		private Stack<String> currentElement;
		public void setCurrentElement(Stack<String> currentElement) {
			this.currentElement = currentElement;
		}

		public void setMap(Map<Object, EStructuralFeature> map) {
			this.map = map;
		}

		private String documentRoot;
		private Map<Object, EStructuralFeature> map;
		private String contextURI;
		private String xmlFile;

		private String getDocumentRoot() {
			return documentRoot;
		}
		
		public EPackage getReconstructedPackage() {
			return reconstructedPackage;
		}
		
		public ReconstructingMetaData(String xmlFile,Stack<String> currentElement,Map<Object,EStructuralFeature> map) {
			this.currentElement= currentElement;
			this.map = map;
			this.xmlFile = xmlFile;
		}
		
		@Override
		public Collection<EPackage> demandedPackages() {
			// TODO Auto-generated method stub
			return Arrays.asList(reconstructedPackage);
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
			//map.clear();
			return null;
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
		public EClassifier getType(EPackage ePackage, String name) {
			if (name == "")
				return ePackage.getEClassifier(documentRoot);
			if (ePackage == null
					|| ePackage.equals(reconstructedPackage))
				return reconstructedPackage.getEClassifier(name);
			return super.getType(ePackage, name);
		}
		
	@Override
	public List<EStructuralFeature> getAllAttributes(EClass eClass) {
		// TODO Auto-generated method stub
		return super.getAllAttributes(eClass);
	}
	@Override
	public List<EStructuralFeature> getAllElements(EClass eClass) {
		
		return super.getAllElements(eClass);
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

			if (name.contains(":")){
				name = name.substring(name.lastIndexOf(":")+1);
			}
			//name = name.substring(0, 1).toLowerCase() + name.substring(1);
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
				feat.setUpperBound(1);
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
					name);
			return feat;
		}

		@Override
		public EPackage demandPackage(String namespace) {
			System.out.println("demanded Package " + namespace);
			if (namespace != null && contextURI == null) {
				contextURI = namespace;
			} else {
				namespace = contextURI;
			}
			
			if (namespace == null){
				namespace = xmlFile.replaceAll("\\\\", "/");
				contextURI = namespace;
			}
			if (reconstructedPackage != null)
				return reconstructedPackage;

			reconstructedPackage = (EPackageImpl) EcoreFactoryImpl.eINSTANCE
					.createEPackage();
			reconstructedPackage.setName(namespace);
			reconstructedPackage.setNsPrefix("xml");
			reconstructedPackage.setNsURI(generateReconstructedPackageURI(namespace));
//			if (BASESCHEME.equals(namespace))
//				EPackage.Registry.INSTANCE.put(null,
//						reconstructedPackage);

			EPackage.Registry.INSTANCE.put(reconstructedPackage.getNsURI(),
					reconstructedPackage);

			System.out.println("created Package "
					+ reconstructedPackage.toString());

			return reconstructedPackage;
		}

		@Override
		public EClassifier demandType(String namespace, String name) {
			System.out.println("demanded Type " + namespace
					+ " name:" + name);
			
			if (name.contains(":")){
				name = name.substring(name.indexOf(":")+1);
			}
			
			
			if (reconstructedPackage == null)
				reconstructedPackage = demandPackage(null);

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
						.setUpperBound(1);

				documentRootEClass.getEStructuralFeatures().add(
						eAttribute);
				
				eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
				eAttribute.setName(XML_ELEMENT_TEXT);
				eAttribute.setEType(EcorePackage.Literals.ESTRING);
				eAttribute.setDerived(true);
				eAttribute.setTransient(true);
				eAttribute.setVolatile(true);
				eAttribute.setUpperBound(1);

				documentRootEClass.getEStructuralFeatures().add(eAttribute);
				
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
					.setUpperBound(1);

			eClass.getEStructuralFeatures().add(eAttribute);
			
			eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
			eAttribute.setName(XML_ELEMENT_TEXT);
			eAttribute.setEType(EcorePackage.Literals.ESTRING);
			eAttribute.setDerived(true);
			eAttribute.setTransient(true);
			eAttribute.setVolatile(true);
			eAttribute.setUpperBound(1);

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
	}

	

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
				// TODO Auto-generated method stub
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
			resource.unload();
		HashSet<EStructuralFeature> criticalFeatures = new HashSet<EStructuralFeature>();
		
		EPackage reconstructedPackage = ((ReconstructingMetaData) data).getReconstructedPackage();
		
		for (EClassifier clazz : reconstructedPackage.getEClassifiers()) {
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
				if (feat instanceof EAttribute)
					if (!(((EAttribute)feat).getEType() instanceof EDataType )){
						criticalFeatures.add(feat);
					}
						
			}
		}

		for (EStructuralFeature eStructuralFeature : criticalFeatures) {
			EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
			eAttribute.setName(eStructuralFeature.getName());
			eAttribute.setEType(EcorePackage.Literals.ESTRING);
			eAttribute.setDerived(true);
			eAttribute.setTransient(true);
			eAttribute.setVolatile(true);
			eAttribute.setUpperBound(1);

			reconstructedPackage.getEClassifiers().remove(
					reconstructedPackage.getEClassifier(eStructuralFeature
							.getName()));

			EClass cont = (EClass) eStructuralFeature.eContainer();
			cont.getEStructuralFeatures().add(eAttribute);
			cont.getEStructuralFeatures().remove(eStructuralFeature);

		}
		
		map.clear();
		stack.clear();
		
		Resource resource2 = transSys.eResource().getResourceSet()
				.createResource(URI.createURI(reconstructedPackage.getNsURI()));
		resource2.getContents().add(reconstructedPackage);
		transSys.eResource().getResourceSet().getResources().add(resource2);
		
		return data;
	}

	@Override
	public void run() {
		Shell shell = new Shell();
		try {
			FileDialog dialog = new FileDialog(shell);
			
			dialog.setText("Please select the xml file you want to import.");
			String xmlURI = dialog.open();
			if (xmlURI == null)
				return;
			
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
				final ExtendedMetaData extendedMetaData = new DelegatingMetaData(((ReconstructingMetaData) data).getReconstructedPackage(),((ReconstructingMetaData) data).getDocumentRoot(),stack);

				set.getLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA,
						extendedMetaData);
				set.getLoadOptions().put(
						XMLResource.OPTION_SUPPRESS_DOCUMENT_ROOT,
						Boolean.FALSE);
				set.getLoadOptions().put(
						XMLResource.OPTION_RECORD_UNKNOWN_FEATURE,
						Boolean.TRUE);
				HashMap<Object,EStructuralFeature> map = new HashMap<Object, EStructuralFeature>(){

					@Override
					public EStructuralFeature put(Object key,
							EStructuralFeature value) {
						return null;
					}
				};
				set.getLoadOptions().put(XMLResource.OPTION_USE_XML_NAME_TO_FEATURE_MAP, map);


				try {
					r = set.getResource(URI.createFileURI(xmlURI), true);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				map.clear();
				postprocessModel(r.getContents());

				EPackage p = ((ReconstructingMetaData) data).getReconstructedPackage();

				exportGeneratedEcoreModel(p,
						xmlURI.substring(0, xmlURI.lastIndexOf(File.separator))
								+ File.separator);
			} else {
				ResourceSet resourceSet = new ResourceSetImpl();
				
				r = resourceSet.getResource(URI.createFileURI(xmlURI), true);
				
			}
			ImportInstanceModelAction action = new ImportInstanceModelAction(
					null);
			action.setModule(transSys);
			action.createAndAddGraph((ResourceImpl) r,
					URI.createFileURI(xmlURI));

			action.dispose();


			cleanUp();
		} finally {
			shell.close();
		}

	}

	private void postprocessModel(EList<EObject> contents) {
		HashSet<EObject> objects = new HashSet<EObject>();
		objects.addAll(contents);
		Iterator<EObject> iterator = objects.iterator();
		while(!objects.isEmpty()){
			EObject obj = iterator.next();
			iterator.remove();
			EStructuralFeature mixed = null;
			if ((mixed = obj.eClass().getEStructuralFeature(MIXEDELEMENTFEATURE)) != null){
				FeatureMap map = (FeatureMap) obj.eGet(mixed);
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
		if (pkg == null)
			result.contextURI = null;
		else
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

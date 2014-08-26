/**
 * RefreshEcoreModelAction.java
 * created on 28.10.2012 10:56:44
 */
package de.tub.tfs.henshin.editor.actions.transSys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.graph.DeleteAttributeCommand;
import de.tub.tfs.henshin.editor.commands.graph.DeleteNodeCommand;
import de.tub.tfs.henshin.editor.ui.graph.GraphPage;
import de.tub.tfs.henshin.editor.util.HenshinCache;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.editor.wizards.RefreshAttributeWizardPage;
import de.tub.tfs.henshin.editor.wizards.RefreshClassWizardPage;
import de.tub.tfs.henshin.editor.wizards.RefreshWizard;

/**
 * @author huuloi
 */
public class RefreshEcoreModelAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.editor.actions.transSys.RefreshEcoreModelAction"; //$NON-NLS-1$
	
	private EPackage importedEPackage;
	
	private Resource resource;

	public RefreshEcoreModelAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Refresh");
		setToolTipText("Refreshes a registered EMF model");
	}


	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		
		if (selectedObjects == null || selectedObjects.size() != 1) {
			return false;
		}
		
		Object selected = selectedObjects.get(0);
		if (selected instanceof EditPart) {
			EditPart editPart = (EditPart) selected;
			Object model = editPart.getModel();
			if (model instanceof EPackage) {
				importedEPackage = (EPackage) model;
			}
		}
		
		return importedEPackage != null;
	}

	
	@Override
	public void run() {
		Set<EObject> modelRoots = HenshinCache.getInstance().getModelRoots();
		Map<EClassifier, List<Node>> classifier2NodeMap = new HashMap<EClassifier, List<Node>>();
		
		initUsedNodes(modelRoots, classifier2NodeMap);

		EList<EClassifier> oldClasses = importedEPackage.getEClassifiers();
		Map<String, EClassifier> name2OldClassMap = new HashMap<String, EClassifier>();
		for (EClassifier eClassifier : oldClasses) {
			name2OldClassMap.put(eClassifier.getName(), eClassifier);
		}
		
		reloadEPackage();
		
		List<EClassifier> currentClasses = importedEPackage.getEClassifiers();
		
		// because Node.getType() returns an EClass and EPackage.getEClassifiers returns a list of EClassifier
		// therefore we can't compare them. So we need to compare their names.
		Map<String, EClassifier> name2ClassMap = new HashMap<String, EClassifier>(currentClasses.size());
		for (EClassifier eClassifier : currentClasses) {
			name2ClassMap.put(eClassifier.getName(), eClassifier);
		}
		
		Map<String, EClassifier> name2NodeTypeMap = new HashMap<String, EClassifier>();
		for (EClassifier eClassifier : classifier2NodeMap.keySet()) {
			name2NodeTypeMap.put(eClassifier.getName(), eClassifier);
		}
		
		List<String> changedClassNames = JavaUtil.subList(name2NodeTypeMap.keySet(), name2ClassMap.keySet());
		List<EClassifier> changedClasses = new ArrayList<EClassifier>(changedClassNames.size());
		for (String string : changedClassNames) {
			changedClasses.add(name2NodeTypeMap.get(string));
		}
		List<String> addedClassNames = JavaUtil.subList(name2ClassMap.keySet(), name2OldClassMap.keySet());
		List<EClassifier> addedClasses = new ArrayList<EClassifier>(addedClassNames.size());
		for (String string : addedClassNames) {
			addedClasses.add(name2ClassMap.get(string));
		}

		// handle attribute changes 
		Map<EStructuralFeature, List<Attribute>> feature2AttributeMap = new HashMap<EStructuralFeature, List<Attribute>>();
		initUsedAttributes(classifier2NodeMap.values(), feature2AttributeMap);
		
		List<EStructuralFeature> currentFeatures = new ArrayList<EStructuralFeature>();
		for (EClassifier eClassifier : currentClasses) {
			if (eClassifier instanceof EClass) {
				currentFeatures.addAll(((EClass)eClassifier).getEStructuralFeatures());
			}
		}
		
		List<EStructuralFeature> changedFeatures = subFeatureList(feature2AttributeMap.keySet(), currentFeatures);
		List<EStructuralFeature> addedFeatures = subFeatureList(currentFeatures, feature2AttributeMap.keySet());
		
		if ((changedClasses.size() > 0 && addedClasses.size() > 0) ||
			(changedFeatures.size() > 0 && addedFeatures.size() >0)
		) {
			System.out.println("open refactor class wizard");
			RefreshWizard wizard = new RefreshWizard();
			
			if (addedClassNames.size() > 0) {
				wizard.setRefreshClassWizardPage(new RefreshClassWizardPage());
				wizard.setRemovedClasses(changedClasses);
				wizard.setAddedClasses(addedClasses);
				wizard.setClassifier2NodeMap(classifier2NodeMap);
			}
			else {
				removeNodesOfEClasses(changedClasses, classifier2NodeMap);
			}
			
			if (addedFeatures.size() > 0) {
				wizard.setRefreshAttributeWizardPage(new RefreshAttributeWizardPage());
				wizard.setRemovedAttributes(changedFeatures);
				wizard.setAddedAttributes(addedFeatures);
				wizard.setFeature2AttributeMap(feature2AttributeMap);
			}
			else {
				removeAttributesOfFeatures(changedFeatures, feature2AttributeMap);
			}
			
			Shell shell = PlatformUI.getWorkbench().getDisplay().getActiveShell();
			WizardDialog dialog = new WizardDialog(shell, wizard);
			dialog.create();
			dialog.open();
		}
		else {
			if (changedClasses.size() > 0) {
				removeNodesOfEClasses(changedClasses, classifier2NodeMap);
			}
			if (changedFeatures.size() > 0) {
				removeAttributesOfFeatures(changedFeatures, feature2AttributeMap);
			}
		}
		
		// save changes after refresh
//		for (EObject eObject : modelRoots) {
//			Resource resource = eObject.eResource();
//			try {
//				resource.save(null);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		if (resource != null) {
			try {
				resource.save(null);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// refresh layout
		List<GraphPage> openedPages = HenshinSelectionUtil.getInstance().getOpenedPages();
		for (GraphPage graphPage : openedPages) {
			graphPage.refreshPallets();
		}
	}


	private List<EStructuralFeature> subFeatureList(Collection<EStructuralFeature> bigList, Collection<EStructuralFeature> smallList) {
		// TODO Auto-generated method stub
		List<EStructuralFeature> subList = new ArrayList<EStructuralFeature>();
		for (EStructuralFeature feature : bigList) {
			boolean found = false;
			for (EStructuralFeature eStructuralFeature : smallList) {
				if (eStructuralFeature.getName().equals(feature.getName()) &&
					feature.eContainer() instanceof EClassifier &&
					eStructuralFeature.eContainer() instanceof EClassifier
				) {
					String name1 = ((EClassifier)feature.eContainer()).getName();
					String name2 = ((EClassifier)eStructuralFeature.eContainer()).getName();
					found = name1.equals(name2);
				}
			}
			if (!found) {
				subList.add(feature);
			}
		}
		return subList;
	}


	private void removeAttributesOfFeatures(List<EStructuralFeature> changedFeatures, Map<EStructuralFeature, List<Attribute>> feature2AttributeMap) {
		for (EStructuralFeature eStructuralFeature : changedFeatures) {
			List<Attribute> list = feature2AttributeMap.get(eStructuralFeature);
			for (Attribute attribute : list) {
				DeleteAttributeCommand command = new DeleteAttributeCommand(attribute);
				command.execute();
			}
		}
	}


	private void removeNodesOfEClasses(List<EClassifier> changedClasses, Map<EClassifier, List<Node>> classifier2NodeMap) {
		for (EClassifier eClassifier : changedClasses) {
			List<Node> list = classifier2NodeMap.get(eClassifier);
			for (Node node : list) {
				DeleteNodeCommand command = new DeleteNodeCommand(node);
				command.execute();
			}
		}
	}


	private void initUsedAttributes(
		Collection<List<Node>> collection,
		Map<EStructuralFeature, List<Attribute>> eStructuralFeature2AttributeMap
	) {
		for (List<Node> nodes : collection) {
			for (Node node : nodes) {
				List<Attribute> attributes = node.getAttributes();
				if (attributes.size() > 0) {
					for (Attribute attribute : attributes) {
						List<Attribute> list = eStructuralFeature2AttributeMap.get(attribute.getType());
						if (list == null) {
							list = new ArrayList<Attribute>();
							eStructuralFeature2AttributeMap.put(attribute.getType(), list);
						}
						eStructuralFeature2AttributeMap.get(attribute.getType()).add(attribute);
					}
				}
			}
		}
	}


	private void reloadEPackage() {
		URI epackageLocation = null;
		Map<String, URI> ePackageNsURIToGenModelLocationMap = EcorePlugin.getEPackageNsURIToGenModelLocationMap();
		if (ePackageNsURIToGenModelLocationMap.keySet().contains(importedEPackage.getNsURI())) {
			epackageLocation = ePackageNsURIToGenModelLocationMap.get(importedEPackage.getNsURI());
		}
		else {
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();

			IProject[] projects = root.getProjects();

			for (IProject iProject : projects) {
				if (!iProject.isOpen())
					continue;

				LinkedList<File> ecoreFiles = findEcoreFiles(iProject
						.getLocation().toFile().listFiles());

				for (File file : ecoreFiles) {
					URI fileURI = URI.createFileURI(file.getAbsolutePath());
					ResourceSet resSet = new ResourceSetImpl();
					Resource src = resSet.getResource(fileURI, true);
					if (!src.getContents().isEmpty()
						&& src.getContents().get(0) instanceof EPackage
						&& ((EPackage) src.getContents().get(0)).getNsURI().equals(importedEPackage.getNsURI())
					) {
						epackageLocation = fileURI;
						break;
					}
				}
			}
		}
		
		if (importedEPackage.eResource() != null) {
			importedEPackage.eResource().unload();
		}
		ResourceSet resSet = new ResourceSetImpl();
		resource = resSet.getResource(epackageLocation, true);
		if (!resource.getContents().isEmpty()
			&& resource.getContents().get(0) instanceof EPackage
		) {
			resource.setURI(URI.createURI(((EPackage) resource.getContents().get(0)).getNsURI()));
		}
		importedEPackage = (EPackage) resource.getContents().get(0);
	}
	
	
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


	@SuppressWarnings("deprecation")
	private void initUsedNodes(
		Set<EObject> modelRoots, 
		Map<EClassifier, List<Node>> classifier2NodeMap
	) {
		for (EObject eObject : modelRoots) {
			if (eObject instanceof TransformationSystem) {
				TransformationSystem transformationSystem = (TransformationSystem) eObject;
				List<Graph> instances = transformationSystem.getInstances();
				for (Graph graph : instances) {
					List<Node> nodes = graph.getNodes();
					for (Node node : nodes) {
						if (node.getType().getEPackage().getNsURI().equals(importedEPackage.getNsURI())) {
							List<Node> list = classifier2NodeMap.get(node.getType());
							if (list == null) {
								list = new ArrayList<Node>();
								classifier2NodeMap.put(node.getType(), list);
							}
							classifier2NodeMap.get(node.getType()).add(node);
						}
					}
				}
			}
		}
	}
	
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("refresh16.png");
	}
	
}

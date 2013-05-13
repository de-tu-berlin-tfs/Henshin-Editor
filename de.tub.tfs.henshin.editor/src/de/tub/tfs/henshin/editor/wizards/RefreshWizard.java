/**
 * RefreshWizard.java
 * created on 03.11.2012 19:45:53
 */
package de.tub.tfs.henshin.editor.wizards;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.jface.wizard.Wizard;

import de.tub.tfs.henshin.editor.commands.graph.DeleteAttributeCommand;
import de.tub.tfs.henshin.editor.commands.graph.DeleteNodeCommand;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.Pair;

/**
 * @author huuloi
 */
public class RefreshWizard extends Wizard {
	
	private RefreshClassWizardPage refreshClassWizardPage;
	
	private RefreshAttributeWizardPage refreshAttributeWizardPage;
	
	private List<EClassifier> removedClasses;
	
	private List<EClassifier> addedClasses;
	
	private Map<EClassifier, List<Node>> classifier2NodeMap;
	
	private List<EStructuralFeature> removedAttributes;
	
	private List<EStructuralFeature> addedAttributes;
	
	private Map<EStructuralFeature, List<Attribute>> feature2AttributeMap;
	
	private boolean skipRefreshAttributeWizardPage;
	
	
	@Override
	public void addPages() {
		setWindowTitle(Messages.REFRESH);
		
		if (refreshClassWizardPage != null) {
			addPage(refreshClassWizardPage);
		}
		
		if (refreshAttributeWizardPage != null) {
			addPage(refreshAttributeWizardPage);
		}
	}
	
	
	@Override
	public boolean canFinish() {
		boolean result = true;
		if (refreshClassWizardPage != null) {
			result &= refreshClassWizardPage.isPageComplete();
		}
		if (refreshAttributeWizardPage != null) {
			result &= refreshAttributeWizardPage.isPageComplete();
		}
		return result;
	}
	
	
	@Override
	public boolean performFinish() {
		boolean result = true;
		if (!skipRefreshAttributeWizardPage) {
			if (refreshAttributeWizardPage != null) {
				List<Pair<EStructuralFeature,EStructuralFeature>> value = refreshAttributeWizardPage.getValue();
				for (Pair<EStructuralFeature, EStructuralFeature> pair : value) {
					List<Attribute> list = feature2AttributeMap.get(pair.getFirst());
					if (pair.getSecond() == null) {
						for (Attribute attribute : list) {
							DeleteAttributeCommand command = new DeleteAttributeCommand(attribute);
							command.execute();
						}
					}
					else {
						for (Attribute attribute : list) {
							attribute.setType((EAttribute) pair.getSecond());
						}
					}
				}
			}
		}

		if (refreshClassWizardPage != null) {
			List<Pair<EClassifier,EClassifier>> value = refreshClassWizardPage.getValue();
			for (Pair<EClassifier, EClassifier> pair : value) {
				List<Node> list = classifier2NodeMap.get(pair.getFirst());
				if (pair.getSecond() == null) {
					for (Node node : list) {
						DeleteNodeCommand command = new DeleteNodeCommand(node);
						command.execute();
					}
				}
				else {
					for (Node node : list) {
						node.setType((EClass) pair.getSecond());
					}
				}
			}
		}
		
		return result;
	}

	
	public void setRemovedClasses(List<EClassifier> removedClasses) {
		this.removedClasses = removedClasses;
	}

	
	public void setAddedClasses(List<EClassifier> addedClasses) {
		this.addedClasses = addedClasses;
	}


	public void setClassifier2NodeMap(Map<EClassifier, List<Node>> classifier2NodeMap) {
		this.classifier2NodeMap = classifier2NodeMap;
	}
	
	
	public RefreshClassWizardPage getRefreshClassWizardPage() {
		return refreshClassWizardPage;
	}


	public void setRemovedAttributes(List<EStructuralFeature> removedAttributes) {
		this.removedAttributes = removedAttributes;
	}


	public void setAddedAttributes(List<EStructuralFeature> addedAttributes) {
		this.addedAttributes = addedAttributes;
	}


	public void setFeature2AttributeMap(Map<EStructuralFeature, List<Attribute>> feature2AttributeMap) {
		this.feature2AttributeMap = feature2AttributeMap;
	}


	public RefreshAttributeWizardPage getRefreshAttributeWizardPage() {
		return refreshAttributeWizardPage;
	}
	
	
	public void setRefreshClassWizardPage(RefreshClassWizardPage refreshClassWizardPage) {
		this.refreshClassWizardPage = refreshClassWizardPage;
	}
	
	
	public void setRefreshAttributeWizardPage(RefreshAttributeWizardPage refreshAttributeWizardPage) {
		this.refreshAttributeWizardPage = refreshAttributeWizardPage;
	}


	public List<EClassifier> getRemovedClasses() {
		return removedClasses;
	}


	public List<EClassifier> getAddedClasses() {
		return addedClasses;
	}


	public Map<EClassifier, List<Node>> getClassifier2NodeMap() {
		return classifier2NodeMap;
	}


	public List<EStructuralFeature> getRemovedAttributes() {
		return removedAttributes;
	}


	public List<EStructuralFeature> getAddedAttributes() {
		return addedAttributes;
	}


	public Map<EStructuralFeature, List<Attribute>> getFeature2AttributeMap() {
		return feature2AttributeMap;
	}


	public boolean isSkipRefreshAttributeWizardPage() {
		return skipRefreshAttributeWizardPage;
	}


	public void setSkipRefreshAttributeWizardPage(
			boolean skipRefreshAttributeWizardPage) {
		this.skipRefreshAttributeWizardPage = skipRefreshAttributeWizardPage;
	}
	
	
	public List<String> getChangedAttributeNames() {
		List<String> names = new ArrayList<String>();
		if (removedAttributes != null) {
			for (EStructuralFeature feature : removedAttributes) {
				names.add(feature.getName());
			}
		}
		return names;
	}
	
	
	public List<String> getAddedAttributeNames() {
		List<String> names = new ArrayList<String>();
		if (addedAttributes != null) {
			for (EStructuralFeature feature : addedAttributes) {
				names.add(feature.getName());
			}
		}
		return names;
	}
	
	
	public List<String> getChangedClassNames() {
		List<String> names = new ArrayList<String>();
		if (removedClasses != null) {
			for (EClassifier clazz : removedClasses) {
				names.add(clazz.getName());
			}
		}
		return names;
	}
	
	
	public List<String> getAddedClassNames() {
		List<String> names = new ArrayList<String>();
		if (addedClasses != null) {
			for (EClassifier clazz : addedClasses) {
				names.add(clazz.getName());
			}
		}
		return names;
	}
	
}
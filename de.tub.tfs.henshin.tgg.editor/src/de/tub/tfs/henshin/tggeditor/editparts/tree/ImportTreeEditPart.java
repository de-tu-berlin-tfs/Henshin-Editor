package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.tggeditor.model.properties.tree.ImportPropertySource;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.editpolicies.ImportedModellEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * EditPart for imported models in the tree editor.
 */
public class ImportTreeEditPart extends AdapterTreeEditPart<ImportedPackage> implements
IDirectEditPart{
	private TGG tgg;
	
	public ImportTreeEditPart(ImportedPackage model, TGG tgg) {
		super(model);
		this.tgg = tgg;
		//refreshDeprecatedEntries();
	}
	
    /**
     * There is now view for this editpart. TODO: set focus to Property page
     * 
     * @return {@code false} per default
     */
    protected boolean canShowView() {
	return false;
    }
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new ImportPropertySource(getCastedModel());
	}
	
@SuppressWarnings("deprecation")
private void refreshDeprecatedEntries() {
		// put all packages source, corr, and target into the corresponding lists and remove the explicit pointers.
		ImportedPackage pkg;
		// single imported packages
		if(tgg.getSource()!=null){
			pkg = TggFactory.eINSTANCE.createImportedPackage();
			pkg.setPackage(tgg.getSource());
			pkg.setComponent(TripleComponent.SOURCE);
			tgg.getImportedPkgs().add(pkg);
			tgg.setSource(null);
		}
		if(tgg.getTarget()!=null){
			pkg = TggFactory.eINSTANCE.createImportedPackage();
			pkg.setPackage(tgg.getTarget());
			pkg.setComponent(TripleComponent.TARGET);
			tgg.getImportedPkgs().add(pkg);
			tgg.setTarget(null);
		}
		if(tgg.getCorresp()!=null){
			pkg = TggFactory.eINSTANCE.createImportedPackage();
			pkg.setPackage(tgg.getCorresp());
			pkg.setComponent(TripleComponent.CORRESPONDENCE);
			tgg.getImportedPkgs().add(pkg);
			tgg.setCorresp(null);
		}
		// lists of imported packages
		
		List<ImportedPackage> pkgs;
		if(tgg.getSourcePkgs()!=null){
			pkgs =  NodeTypes.getImportedPackagesFromEPackages(tgg.getSourcePkgs(),TripleComponent.SOURCE);
			markImportedPackages(pkgs,TripleComponent.SOURCE);
			tgg.getImportedPkgs().addAll(pkgs);
			tgg.getSourcePkgs().clear();
		}
		if(tgg.getCorrespondencePkgs()!=null){
			pkgs =  NodeTypes.getImportedPackagesFromEPackages(tgg.getCorrespondencePkgs(),TripleComponent.CORRESPONDENCE);
			markImportedPackages(pkgs,TripleComponent.CORRESPONDENCE);
			tgg.getImportedPkgs().addAll(pkgs);
			tgg.getCorrespondencePkgs().clear();
		}
		if(tgg.getTargetPkgs()!=null){
			pkgs =  NodeTypes.getImportedPackagesFromEPackages(tgg.getTargetPkgs(),TripleComponent.TARGET);
			markImportedPackages(pkgs,TripleComponent.TARGET);
			tgg.getImportedPkgs().addAll(pkgs);
			tgg.getTargetPkgs().clear();
		}

		
		
	}



	
	/**
	 * Marks all packages in the list with the specified triple component
	 * @param pkgs
	 * @param component
	 */
	private void markImportedPackages(List<ImportedPackage> pkgs,
		TripleComponent component) {
		for(ImportedPackage p: pkgs){
			p.setComponent(component);
		}
	}


	@Override
	protected String getText() {
		if(getCastedModel() == null){
			return "";
		}
		String name = getCastedModel().getPackage().getName();
		if (name==null){
			return ((EPackageImpl)getCastedModel()).eProxyURI() + " not found!";
		}

		switch(getCastedModel().getComponent()){
		case SOURCE:
			 return "[SRC:] " + name + " (source model)"  ;
		case CORRESPONDENCE:
			 return "[COR:] " + name + " (correspondence model)"  ;
		case TARGET:
			 return "[TGT:] " + name + " (target model)"  ;
		default:
			return "[DEF:] " + name + " (default component model)"  ;
		}
	}


	@Override
	public int getDirectEditFeatureID() {		
		return HenshinPackage.NAMED_ELEMENT;		// ????????????????
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, 
				new ImportedModellEditPolicy(tgg));

	}
	
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("epackage16.png");
		} catch (Exception e) {
			return null;
		}
	}
	
}
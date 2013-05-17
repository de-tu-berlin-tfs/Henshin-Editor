package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


/**
 * Folder for the imported models in the tree editor.
 */

public class ImportFolder extends EObjectImpl {
//	private Module sys;
	private List<ImportedPackage> imports;
	private TGG tgg;
	private boolean isRefreshedDeprecatedItems = false;
	private Module sys;
	
//	private EPackage source;
//	private EPackage corr;
//	private EPackage target;
	
	/**
	 * Constructor.
	 */	
	public ImportFolder(Module sys) {
		this.sys = sys;
		//this.imports = sys.getImports();	
		
		tgg = NodeUtil.getLayoutSystem(sys);		
		if(!isRefreshedDeprecatedItems)
			refreshDeprecatedEntries();
		this.imports = tgg.getImportedPkgs();	
		TreeIterator<EObject> iter = tgg.eAllContents();		
		while(iter.hasNext()){
			EObject o = iter.next();
			if(o instanceof ImportedPackage){
				imports.add((ImportedPackage) o); 
			}
		}
	}
	
	public void setImports(ImportedPackage imp){
		this.imports.add(imp);		
	}
	
	/**
	 * Get imported models.
	 * @return
	 */
	public List<ImportedPackage> getImports(){
		return this.imports;
	}

//	public Module getTransformationSystem(){
//		return this.sys;
//	}
	
	/**
	 * Get the Layoutsystem.
	 * @return
	 */
	public TGG getTGGModel(){
		return this.tgg;
	}
	
	/**
	 * retrieves all information from deprecated lists of imported packages and stores them in the currrent list of imported packages
	 */
	private void refreshDeprecatedEntries() {
			ImportedPackage pkg;
//	public EPackage getSource(){
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
//	
			List<ImportedPackage> pkgs;
			if(tgg.getSourcePkgs()!=null){
				pkgs =  NodeTypes.getImportedPackagesFromEPackages(tgg.getSourcePkgs(),TripleComponent.SOURCE);
				markImportedPackages(pkgs,TripleComponent.SOURCE);
				tgg.getImportedPkgs().addAll(pkgs);
				tgg.getSourcePkgs().clear();
//		return this.corr;
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
			isRefreshedDeprecatedItems=true;
		}
	private void markImportedPackages(List<ImportedPackage> pkgs,
		TripleComponent component) {
		for(ImportedPackage p: pkgs){
			p.setComponent(component);
		}
	}

	public void update() {
		if(!isRefreshedDeprecatedItems)
			refreshDeprecatedEntries();
		this.imports = tgg.getImportedPkgs();	
		
		
		eNotify(new ENotificationImpl(this, Notification.ADD, 0, null, null));
	}
}

package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


/**
 * Folder for the imported models in the tree editor.
 */

public class ImportFolder extends EObjectImpl {
//	private Module sys;
	private List<EPackage> imports;
	private TGG tgg;
	
//	private EPackage source;
//	private EPackage corr;
//	private EPackage target;
	
	/**
	 * Constructor.
	 */	
	public ImportFolder(Module sys) {
//		this.sys = sys;
		this.imports = sys.getImports();		
		tgg = NodeUtil.getLayoutSystem(sys);		
		TreeIterator<EObject> iter = tgg.eAllContents();		
		while(iter.hasNext()){
			EObject o = iter.next();
			if(o instanceof EPackage){
				imports.add((EPackage) o); 
			}
		}
	}
	
	public void setImports(EPackage imp){
		this.imports.add(imp);		
	}
	
	/**
	 * Get imported models.
	 * @return
	 */
	public List<EPackage> getImports(){
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
	
//	public EPackage getSource(){
//		return this.source;
//	}
//	
//	public EPackage getCorresp(){
//		return this.corr;
//	}
//	
//	public EPackage getTraget(){
//		return this.target;
//	}
}

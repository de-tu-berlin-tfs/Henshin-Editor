/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


/**
 * Folder for the imported models in the tree editor.
 */

public class ImportFolder extends EObjectImpl {
	private List<ImportedPackage> imports;
	private TGG tgg;
	
	
	/**
	 * Constructor.
	 */	
	public ImportFolder(Module sys) {
		
		tgg = GraphicalNodeUtil.getLayoutSystem(sys);		
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

	
	/**
	 * Get the Layoutsystem.
	 * @return
	 */
	public TGG getTGGModel(){
		return this.tgg;
	}
	

	public void update() {
		this.imports = tgg.getImportedPkgs();	
		
		
		eNotify(new ENotificationImpl(this, Notification.ADD, 0, null, null));
	}
}

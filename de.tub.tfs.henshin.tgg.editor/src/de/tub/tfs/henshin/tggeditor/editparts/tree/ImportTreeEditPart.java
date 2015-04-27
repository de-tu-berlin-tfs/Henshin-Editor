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
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.editpolicies.ImportedModellEditPolicy;
import de.tub.tfs.henshin.tggeditor.model.properties.tree.ImportPropertySource;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
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
//		if (name==null){
//			return ((EPackageImpl)getCastedModel()).eProxyURI() + " not found!";
//		}

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
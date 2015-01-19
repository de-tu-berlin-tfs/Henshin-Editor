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
/**
 * RuleClipboardEditPolicy.java
 *
 * Created 21.01.2012 - 13:46:03
 */
package de.tub.tfs.henshin.editor.editparts.rule;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.CopyRequest;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;

/**
 * @author nam
 * 
 */
public final class RuleClipboardEditPolicy extends ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy #performCopy
	 * (de.tub.tfs.henshin.editor.editparts.CopyRequest)
	 */
	@Override
	public void performCopy(CopyRequest req) {
		Rule model = (Rule) getHost().getModel();

		for (TreeIterator<EObject> it = model.eAllContents(); it.hasNext();) {
			EObject next = it.next();

			if (next instanceof Node) {
				req.getContents().add(
						HenshinLayoutUtil.INSTANCE.getLayout((Node) next));
			}
		}

		super.performCopy(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy
	 * #getPasteTarget (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		Rule model = (Rule) getHost().getModel();

		Object toPaste = req.getPastedObject();

		if (toPaste instanceof Layout) {
			return HenshinLayoutUtil.INSTANCE.getLayoutSystem(model);
		}

		return HenshinUtil.INSTANCE.getTransformationSystem(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy
	 * #getPasteFeature (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	protected EStructuralFeature getPasteFeature(PasteRequest req) {
		Object toPaste = req.getPastedObject();

		if (toPaste instanceof Layout) {
			return HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS;
		}

		return HenshinPackage.Literals.MODULE__UNITS;
	}
}
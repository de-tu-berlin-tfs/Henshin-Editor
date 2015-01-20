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
 * EdgeClipboardEditPolicy.java
 *
 * Created 20.01.2012 - 20:14:52
 */
package de.tub.tfs.henshin.editor.editparts.graph;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.CopyRequest;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.Layout;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * @author nam
 * 
 */
public final class EdgeClipboardEditPolicy extends ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#performCopy(de
	 * .tub.tfs.henshin.editor.editparts.CopyRequest)
	 */
	@Override
	public void performCopy(CopyRequest req) {
		Edge model = (Edge) getHost().getModel();

		Node src = model.getSource();
		Node target = model.getTarget();

		Layout srcLayout = HenshinLayoutUtil.INSTANCE.getLayout(src);
		Layout targetLayout = HenshinLayoutUtil.INSTANCE.getLayout(target);

		req.getContents().add(srcLayout);
		req.getContents().add(targetLayout);
		req.getContents().add(src);
		req.getContents().add(target);

		super.performCopy(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#canPaste(java
	 * .lang.Object)
	 */
	@Override
	protected boolean canPaste(Object o) {
		return o instanceof Edge || o instanceof Node
				|| o instanceof NodeLayout;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteTarget
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		Object o = req.getPastedObject();
		Edge model = (Edge) getHost().getModel();

		if (o instanceof Edge || o instanceof Node) {
			return model.getGraph();
		}

		return HenshinLayoutUtil.INSTANCE.getLayoutSystem(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteFeature
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	protected EStructuralFeature getPasteFeature(PasteRequest req) {
		Object toPaste = req.getPastedObject();

		if (toPaste instanceof NodeLayout) {
			return HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS;
		}

		if (toPaste instanceof Node) {
			return HenshinPackage.Literals.GRAPH__NODES;
		}

		return HenshinPackage.Literals.GRAPH__EDGES;
	}
}
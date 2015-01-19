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
 * GraphClipboardEditPolicy.java
 *
 * Created 20.01.2012 - 12:01:49
 */
package de.tub.tfs.henshin.editor.editparts.graph;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;

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
public class GraphClipboardEditPolicy extends ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#performCopy(de
	 * .tub.tfs.henshin.editor.editparts.CopyRequest)
	 */
	@Override
	public void performCopy(CopyRequest req) {
		Graph model = (Graph) getHost().getModel();

		for (Node n : model.getNodes()) {
			Layout l = HenshinLayoutUtil.INSTANCE.getLayout(n);

			req.getContents().add(l);
		}

		req.getContents().add(model);
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
		return o instanceof Node || o instanceof Edge || o instanceof Graph
				|| o instanceof Layout;
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
		Graph model = (Graph) getHost().getModel();

		Object toPaste = req.getPastedObject();

		if (toPaste instanceof Layout) {
			return HenshinLayoutUtil.INSTANCE.getLayoutSystem(model);
		}

		if (toPaste instanceof Graph) {
			return HenshinUtil.INSTANCE.getTransformationSystem(model);
		}

		return (EObject) getHost().getModel();
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

		if (toPaste instanceof Layout) {
			return HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS;
		}

		if (toPaste instanceof Node) {
			return HenshinPackage.Literals.GRAPH__NODES;
		}

		if (toPaste instanceof Edge) {
			return HenshinPackage.Literals.GRAPH__EDGES;
		}

		return HenshinPackage.Literals.MODULE__INSTANCES;
	}
}
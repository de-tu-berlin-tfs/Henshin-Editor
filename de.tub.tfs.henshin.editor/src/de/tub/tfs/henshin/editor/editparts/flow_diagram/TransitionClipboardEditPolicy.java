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
 * TransitionClipboardEditPolicy.java
 *
 * Created 22.01.2012 - 16:10:56
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram;

import de.tub.tfs.henshin.editor.editparts.CopyRequest;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.Start;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * @author nam
 * 
 */
public class TransitionClipboardEditPolicy extends
		FlowElementClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#canCopy()
	 */
	@Override
	protected boolean canCopy() {
		Transition model = (Transition) getHost().getModel();

		return !(model.getNext() instanceof End && model.getPrevous() instanceof Start);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#performCopy(de
	 * .tub.tfs.henshin.editor.editparts.CopyRequest)
	 */
	@Override
	public void performCopy(CopyRequest req) {
		Transition model = (Transition) getHost().getModel();

		FlowElement prev = model.getPrevous();
		FlowElement next = model.getNext();

		req.getContents().add(HenshinLayoutUtil.INSTANCE.getLayout(prev));
		req.getContents().add(HenshinLayoutUtil.INSTANCE.getLayout(next));
		req.getContents().add(prev);
		req.getContents().add(next);
		req.getContents().add(model);
	}
}

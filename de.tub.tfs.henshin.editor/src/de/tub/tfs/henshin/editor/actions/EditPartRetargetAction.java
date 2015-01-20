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
 * AbstractEdipartRetargetAction.java
 *
 * Created 19.12.2011 - 20:53:08
 */
package de.tub.tfs.henshin.editor.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.IAction;

/**
 * @author nam
 * 
 */
public class EditPartRetargetAction extends SelectionAction {

	private IHandlersRegistry registry;

	private IAction enabledHandler;

	private String defaultId;

	private List<String> handlers;

	/**
	 * @param reg
	 * @param id
	 */
	public EditPartRetargetAction(IHandlersRegistry reg, String id) {
		super(reg.getWorkbenchPart());

		this.registry = reg;
		this.handlers = new LinkedList<String>();
		defaultId = null;

		setId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		enabledHandler.run();
	}

	/**
	 * @param id
	 */
	public void registerHandler(String id) {
		handlers.add(id);
	}

	public void setDefaultHandler(IAction defaultHanler) {
		defaultId = getId() + "___default___";

		registry.registerHandler(defaultHanler, defaultId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		enabledHandler = null;

		if (defaultId != null) {
			if (!handlers.contains(defaultId)) {
				handlers.add(defaultId);
			}
		}

		for (String id : handlers) {
			IAction handler = registry.getHandler(id);

			if (handler != null) {
				if (handler.isEnabled()) {
					enabledHandler = handler;

					transformTo(enabledHandler);

					break;
				}
			}
		}

		return enabledHandler != null;
	}

	/**
	 * @param handler
	 */
	private void transformTo(final IAction handler) {
		setText(handler.getText());
		setToolTipText(handler.getToolTipText());
		setImageDescriptor(handler.getImageDescriptor());
	}
}

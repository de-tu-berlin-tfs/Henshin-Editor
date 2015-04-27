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
 * CreateNodeLayoutCommand.java
 *
 * Created 13.01.2012 - 14:18:26
 */
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.common.notify.impl.NotificationImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinNotification;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.NodeLayout;

/**
 * @author nam
 * 
 */
public class CreateNodeLayoutCommand extends CompoundCommand {

	private Node n;

	private NodeLayout layout;

	/**
	 * @param n
	 * @param x
	 * @param y
	 */
	public CreateNodeLayoutCommand(final Node n, final int x, final int y) {
		super();

		this.n = n;

		NodeLayout l = HenshinLayoutFactory.eINSTANCE.createNodeLayout();

		layout = l;

		l.setModel(n);
		l.setX(x);
		l.setY(y);

		add(new SimpleAddEObjectCommand<EObject, EObject>(l,
				HenshinLayoutPackage.Literals.LAYOUT_SYSTEM__LAYOUTS,
				HenshinLayoutUtil.INSTANCE.getLayoutSystem(n)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();

		n.eNotify(new NotificationImpl(HenshinNotification.LAYOUT_ADDED, null,
				layout));
	}
}

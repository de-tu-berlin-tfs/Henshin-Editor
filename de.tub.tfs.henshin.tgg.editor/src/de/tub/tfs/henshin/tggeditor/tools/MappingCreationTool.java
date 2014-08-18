/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.tools;

public class MappingCreationTool extends
		de.tub.tfs.muvitor.gef.palette.MappingCreationTool {
	
	@Override
	protected boolean handleCreateConnection() {
		//getCurrentViewer().getFocusEditPart().getParent().getViewer().deselectAll();
		//getCurrentViewer().deselectAll();
		getCurrentViewer().select(getCurrentViewer().getFocusEditPart());
		
		return super.handleCreateConnection();
	}

}

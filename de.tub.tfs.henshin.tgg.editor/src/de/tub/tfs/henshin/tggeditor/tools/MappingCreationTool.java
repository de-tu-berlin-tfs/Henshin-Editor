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

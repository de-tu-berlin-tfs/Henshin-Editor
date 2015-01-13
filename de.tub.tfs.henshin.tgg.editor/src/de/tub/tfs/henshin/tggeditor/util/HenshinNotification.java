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
 * 
 */
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.common.notify.Notification;

/**
 * The Interface HenshinNotification.
 */
public interface HenshinNotification extends Notification {

	/** The EXECUTED. */
	int EXECUTED = 11;
	
	/** The TRANSFORMATIO n_ undo. */
	int TRANSFORMATION_UNDO = 12;
	
	/** The TRANSFORMATIO n_ redo. */
	int TRANSFORMATION_REDO = 13;
	
	/** AND -> OR, OR -> AND */
	int BINARY_FORMULA_SWAP = 21;
}

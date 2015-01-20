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
 * HenshinModelCreationFactory.java
 *
 * Created 21.12.2011 - 16:59:52
 */
package de.tub.tfs.henshin.editor.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.HenshinFactory;

/**
 * @author nam
 * 
 */
public class HenshinModelCreationFactory extends EObjectsModelCreationFactory {

	/**
	 * @param factoryInstance
	 * @param modelClass
	 */
	public HenshinModelCreationFactory(EClass modelClass) {
		super(HenshinFactory.eINSTANCE, modelClass);
	}

}

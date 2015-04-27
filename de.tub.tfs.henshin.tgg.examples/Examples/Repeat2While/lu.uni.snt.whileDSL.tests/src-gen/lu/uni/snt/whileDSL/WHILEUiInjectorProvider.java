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
/*
* generated by Xtext
*/
package lu.uni.snt.whileDSL;

import org.eclipse.xtext.junit4.IInjectorProvider;

import com.google.inject.Injector;

public class WHILEUiInjectorProvider implements IInjectorProvider {
	
	public Injector getInjector() {
		return lu.uni.snt.whileDSL.ui.internal.WHILEActivator.getInstance().getInjector("lu.uni.snt.whileDSL.WHILE");
	}
	
}

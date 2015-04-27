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
package lu.uni.snt.secan.ttc_java;

/**
 * Initialization support for running Xtext languages 
 * without equinox extension registry
 */
public class TTC_JavaStandaloneSetup extends TTC_JavaStandaloneSetupGenerated{

	public static void doSetup() {
		new TTC_JavaStandaloneSetup().createInjectorAndDoEMFRegistration();
	}
}


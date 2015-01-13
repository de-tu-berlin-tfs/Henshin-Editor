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
package de.tub.tfs.henshin.analysis.sandbox;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.impl.HenshinPackageImpl;

import de.tub.tfs.henshin.analysis.AggInfo;

public class AggConvert {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HenshinPackageImpl.init();
		
		ModelHelper.registerFileExtension("henshin");
		ModelHelper.registerFileExtension("ecore");
		
		EPackage sierpinskiPackage = (EPackage) ModelHelper.loadFile("tests/sierpinski.ecore");
		EPackage.Registry.INSTANCE.put(sierpinskiPackage.getNsURI(), sierpinskiPackage);
		
		EPackage statechartPackage = (EPackage) ModelHelper.loadFile("tests/statechart.ecore");
		EPackage.Registry.INSTANCE.put(statechartPackage.getNsURI(), statechartPackage);
		
		EPackage ooPackage = (EPackage) ModelHelper.loadFile("tests/OO.ecore");
		EPackage.Registry.INSTANCE.put(ooPackage.getNsURI(), ooPackage);
		
		EPackage oo2rdbPackage = (EPackage) ModelHelper.loadFile("tests/OO2RDB.ecore");
		EPackage.Registry.INSTANCE.put(oo2rdbPackage.getNsURI(), oo2rdbPackage);
		
		EPackage rdbPackage = (EPackage) ModelHelper.loadFile("tests/RDB.ecore");
		EPackage.Registry.INSTANCE.put(rdbPackage.getNsURI(), rdbPackage);
		
		// the most simple henshin example
		Module ts = (Module) ModelHelper
				.loadFile("tests/sierpinski.henshin");
		AggInfo aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/sierpinski.ggx");
		
		// the most complex henshin example
		ts = (Module) ModelHelper.loadFile("tests/statechart.henshin");
		aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/statechart.ggx");
		
		ts = (Module) ModelHelper.loadFile("tests/final.henshin");
		aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/final.ggx");
	}
	
}
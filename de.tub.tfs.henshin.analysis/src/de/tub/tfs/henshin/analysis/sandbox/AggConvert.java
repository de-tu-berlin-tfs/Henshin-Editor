package de.tub.tfs.henshin.analysis.sandbox;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.interpreter.util.ModelHelper;
import org.eclipse.emf.henshin.model.TransformationSystem;
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
		TransformationSystem ts = (TransformationSystem) ModelHelper
				.loadFile("tests/sierpinski.henshin");
		AggInfo aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/sierpinski.ggx");
		
		// the most complex henshin example
		ts = (TransformationSystem) ModelHelper.loadFile("tests/statechart.henshin");
		aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/statechart.ggx");
		
		ts = (TransformationSystem) ModelHelper.loadFile("tests/final.henshin");
		aggInfo = new AggInfo(ts);
		aggInfo.getAggGrammar().save("tests/final.ggx");
	}
	
}
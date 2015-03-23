package de.tub.tfs.henshin.tgg.interpreter.config;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.script.ScriptEngine;

import org.eclipse.core.runtime.Platform;

public class JavaScriptTggInitializer {

	public void initEMFPackage(String bundle,String clazz){
		try {
			Class<?> loadClass = Platform.getBundle(bundle).loadClass(clazz);
			Field declaredField = loadClass.getDeclaredField("eINSTANCE");
			Object instance = declaredField.get(null);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initXtext(String bundle,String clazz){
		try {
			Object newInstance = Platform.getBundle(bundle).loadClass(clazz).newInstance();
			Method declaredMethod = newInstance.getClass().getMethod("createInjectorAndDoEMFRegistration");
			declaredMethod.invoke(newInstance);
		} catch (Exception e) {
			try {
				Object newInstance = Platform.getBundle(bundle).loadClass(clazz).newInstance();
				Method declaredMethod = newInstance.getClass().getDeclaredMethod("createInjectorAndDoEMFRegistration");
			
				declaredMethod.invoke(newInstance);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 

		} 
	}
	
	public Class<?> loadClass(String bundle,String clazz){
		try {
			return Platform.getBundle(bundle).loadClass(clazz);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void registerWithScriptingEngine(ScriptEngine engine){
		engine.put("Init", new JavaScriptTggInitializer());
	}
	
}

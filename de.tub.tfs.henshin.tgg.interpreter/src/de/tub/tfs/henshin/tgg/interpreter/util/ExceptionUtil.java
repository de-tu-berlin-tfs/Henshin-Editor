/*******************************************************************************
 *******************************************************************************/

package de.tub.tfs.henshin.tgg.interpreter.util;

public class ExceptionUtil {

	public static void error(String errorSstring) {
		System.out.println("TGG interpreter error: " + errorSstring);
		Throwable t = new Throwable();
		t.printStackTrace();
	}
	
	

}
